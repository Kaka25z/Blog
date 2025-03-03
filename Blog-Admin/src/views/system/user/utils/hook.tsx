import { h, ref } from "vue";
import { http } from "@/utils/http";
import { baseUrlApi } from "@/api/utils";
import { message } from "@/utils/message";
import { addDialog } from "@/components/ReDialog/index";
import { deviceDetection } from "@pureadmin/utils";
import reCropperPreview from "@/components/ReCropper/ReCropperPreview.vue";
import { withInstall } from "@pureadmin/utils";
import { useUserStoreHook } from "@/store/modules/user";

/** 图片裁剪预览组件 */
export const ReCropperPreview = withInstall(reCropperPreview);

export default ReCropperPreview;

export const userInfo = ref([]);

export const confirmDeleteDialogVisible = ref(false);

export function handleClose(done: () => void) {
  done();
}

export function fetchUserInfo() {
  const getUserData = (data?: object) => {
    return http.request<UserInfoResult>("get", baseUrlApi("public/user"), {
      data
    });
  };

  getUserData().then(res => {
    if (res.code === 200) {
      userInfo.value = res.data.map(userInfo => ({
        ...userInfo
      }));
    } else {
      message("获取用户信息失败", { type: "error" });
    }
  });
}

export function updateUser(data?: object) {
  return http.request<UpdateUser>("post", baseUrlApi("protected/updateuser"), {
    data
  });
}

export function uploadUserAvatar(row: any) {
  const cropRef = ref();
  const formattedDateTime = formatDateTime(new Date());

  addDialog({
    title: "裁剪、上传头像",
    width: "40%",
    closeOnClickModal: false,
    fullscreen: deviceDetection(),
    contentRenderer: () =>
      h(ReCropperPreview, {
        ref: cropRef,
        imgSrc: row.userAvatar,
        onCropper: info => (row.userAvatar = info)
      }),
    beforeSure: done => {
      console.log("裁剪后的图片信息：", row.userAvatar.base64);
      const userId = row.id;
      console.log("用户id：", userId);

      type UpdateUserAvatar = {
        userAvatar: string;
        id: number;
      };

      const Data = {
        image: row.userAvatar.base64,
        updateTime: formattedDateTime,
        id: row.id
      };

      console.log("Data：", Data);

      const updateUserAvatar = (data?: object) => {
        return http.request<UpdateUserAvatar>(
          "post",
          baseUrlApi("protected/update-useravatar"),
          {
            data
          }
        );
      };

      updateUserAvatar(Data).then(res => {
        if (res.code === 200) {
          message("上传成功", { type: "success" });
          fetchUserInfo();
          if (row.role === "管理员") {
            updateAvatarById(row.id);
          }
        } else {
          message("上传失败", { type: "error" });
        }
      });
      done(); // 关闭弹框
    },
    closeCallBack: () => {
      cropRef.value.hidePopover();
      fetchUserInfo();
    }
  });
}

export async function confirmDelete(id: string) {
  const deleteUser = (data?: string) => {
    return http.request<DeleteUser>(
      "delete",
      baseUrlApi("protected/deleteuser"),
      {
        data
      }
    );
  };

  confirmDeleteDialogVisible.value = false;
  try {
    deleteUser(id).then(res => {
      if (res.code === 200) {
        message("删除成功", { type: "success" });
        fetchUserInfo();
      } else {
        message("删除失败", { type: "error" });
      }
    });
  } catch (e) {
    message("删除过程中发生错误", { type: "error" });
  }
}

export function formatDateTime(date: Date): string {
  const year = date.getFullYear();
  const month = (date.getMonth() + 1).toString().padStart(2, "0"); // 月份从0开始，所以加1
  const day = date.getDate().toString().padStart(2, "0");
  const hours = date.getHours().toString().padStart(2, "0");
  const minutes = date.getMinutes().toString().padStart(2, "0");
  const seconds = date.getSeconds().toString().padStart(2, "0");

  return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
}

function updateAvatarById(id: number) {
  const getUserInfo = () => {
    return http.request<UserInfoResult>(
      "get",
      baseUrlApi(`public/userinfo?id=${id}`)
    );
  };

  getUserInfo().then(res => {
    if (res.code === 200) {
      useUserStoreHook().SET_AVATAR(res.data.userAvatar);
    } else {
      message("获取用户信息失败", { type: "error" });
    }
  });
}
