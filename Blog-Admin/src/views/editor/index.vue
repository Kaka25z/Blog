<script setup lang="ts">
import { MdEditor } from "md-editor-v3";
import "md-editor-v3/lib/style.css";
import { onMounted, ref, watch } from "vue";
import { createNote, getNoteById, updateNote } from "@/views/editor/index";
import { useRoute, useRouter } from "vue-router";
import { Plus } from "@element-plus/icons-vue";
import {
  getCategoriesFormBackend,
  getNoteTageListFormBackend
} from "@/views/allnotes/utils";
import { http } from "@/utils/http";
import { baseUrlApi } from "@/api/utils";
import Compressor from "compressorjs";
import { formatToken, getToken } from "@/utils/auth";
import { message } from "@/utils/message";
import { formatDateTime } from "@/views/system/user/utils/hook";

defineOptions({
  name: "EditNotes"
});

const noteContent = ref({
  noteContent: ""
});
const form = ref({
  noteTitle: "",
  noteContent: "",
  noteCategory: 0,
  isTop: 0,
  status: "",
  description: "",
  cover: "",
  updateTime: "",
  noteTags: ""
});
const route = useRoute();
const router = useRouter();
const id = ref(route.query.id);
const description = ref("");
const noteTitle = ref("");
const NoteTag = ref([]);
const isDialogVisible = ref(false);
const disabled = ref(false);

function openDialog() {
  isDialogVisible.value = true;
  form.value.noteTitle = noteTitle.value;
}

watch(
  () => route.query.id,
  newId => {
    id.value = newId;
    initNote();
  },
  { immediate: true }
);

async function initNote() {
  if (id.value) {
    const res = await getNoteById(id.value);
    form.value = {
      noteTitle: res.data.noteTitle,
      noteCategory: res.data.noteCategory,
      isTop: res.data.isTop,
      status: res.data.status,
      description: res.data.description
    };
    console.log(form.value);
    description.value = res.data.noteContent;
    noteTitle.value = res.data.noteTitle;
    noteContent.value.noteContent = res.data.noteContent;
    NoteTag.value = res.data.noteTags
      .split(",")
      .map((tag: string) => parseInt(tag, 10));
  }
}
const noteCategories = ref([]);
const noteTageList = ref([]);
const dialogImageUrl = ref("");
const dialogVisible = ref(false);

const initCategories = async () => {
  const res = await getCategoriesFormBackend();
  noteCategories.value = res.data;
};

const initTageList = async () => {
  const res = await getNoteTageListFormBackend();
  noteTageList.value = res.data;
};

const handleRemove = async () => {
  const deleteImages = (data?: object) => {
    return http.request<any>("delete", baseUrlApi("protected/delImg"), {
      data
    });
  };
  try {
    let ImgArray: string[] = [form.value.cover];
    await deleteImages(ImgArray).then(res => {
      if (res.code === 200) {
        message("删除成功", { type: "success" });
        form.value.cover = "";
      }
    });
  } catch (error) {
    console.log(error);
    message("删除失败", { type: "error" });
  }
};

const handlePictureCardPreview = () => {
  dialogVisible.value = true;
};

const onUploadImg = async (files, callback) => {
  const res = await Promise.all(
    files.map(file => {
      return new Promise((resolve, reject) => {
        // 使用 Compressor 来压缩图片
        new Compressor(file, {
          quality: 0.6,
          success(result) {
            // 创建压缩后的文件对象
            const compressedFile = new File([result], file.name, {
              type: result.type,
              lastModified: Date.now()
            });

            // 准备FormData
            const form = new FormData();
            form.append("file", compressedFile);

            // 上传函数
            const upload = (data?: object) => {
              return http.request<any>("post", baseUrlApi("protected/upload"), {
                headers: { "Content-Type": "multipart/form-data" },
                data
              });
            };

            // 上传压缩后的文件
            upload(form)
              .then(response => resolve(response))
              .catch(error => reject(error));
          },
          error(err) {
            console.error("图片压缩失败", err);
            reject(err);
          }
        });
      });
    })
  );

  // 处理上传后的结果
  callback(res.map(item => item.data));
};

const onBefore = (file: File): false | Promise<File> => {
  if (!["image/jpeg", "image/png", "image/gif"].includes(file.type)) {
    message("只能上传图片");
    return false;
  }
  const isExceed = file.size / 1024 / 1024 > 30;
  if (isExceed) {
    message(`单个图片大小不能超过30MB`);
    return false;
  }
  return new Promise((resolve, reject) => {
    new Compressor(file, {
      quality: 0.6,
      success(result) {
        const compressedFile = new File([result], result.name, {
          type: result.type,
          lastModified: Date.now()
        });
        resolve(compressedFile);
      },
      error(err) {
        message("图片压缩失败", { type: "error" });
        reject(err);
      }
    });
  });
};

const handleCancel = () => {
  isDialogVisible.value = false;
};

const onSuccess = response => {
  message("上传图片成功", { type: "success" });
  dialogImageUrl.value = response.data;
  form.value.cover = response.data;
};

const currentTime: Date = new Date();
const beijingTime = currentTime.toLocaleString("en-US", {
  timeZone: "Asia/Shanghai"
});

async function submitForm() {
  form.value.updateTime = formatDateTime(currentTime);
  form.value.noteContent = noteContent.value.noteContent;
  form.value.noteTitle = noteTitle.value;
  form.value.noteTags = form.value.noteTags.join(",");
  if (id.value) {
    await updateNote(id.value, form.value).then(res => {
      if (res.code === 200) {
        message("修改成功", { type: "success" });
        router.push({ path: "/notes/allnotes/index" });
      } else {
        message("修改失败", { type: "error" });
      }
    });
  } else {
    await createNote(form.value).then(res => {
      if (res.code === 200) {
        message("发布成功", { type: "success" });
        router.push({ path: "/notes/allnotes/index" });
      } else {
        message("发布失败", { type: "error" });
      }
    });
  }
  isDialogVisible.value = false;
}

const token: string = formatToken(getToken().token);

function handleTagsChange(value) {
  console.log("选中的标签:", value);
  // 这里可以添加额外的逻辑，如果需要的话
}

onMounted(() => {
  initNote();
  initCategories();
  initTageList();
  console.log(id.value);
});
</script>

<template>
  <div>
    <div
      style="
        display: flex;
        justify-content: center;
        align-items: center;
        padding: 20px;
        margin-bottom: 30px;
        background-color: white;
        border-radius: 20px;
      "
    >
      <span>文章标题：</span>
      <el-input v-model="noteTitle" style="width: 80%" />
      <el-button type="primary" style="margin-left: 20px" @click="openDialog"
        >发布</el-button
      >
    </div>
    <div
      style="
        height: calc(100vh - 200px);
        border-radius: 20px;
        padding: 20px;
        background-color: white;
      "
    >
      <MdEditor
        v-model="noteContent.noteContent"
        style="height: 100%"
        @onUploadImg="onUploadImg"
      />
    </div>

    <el-dialog
      v-model="isDialogVisible"
      title="发布文章"
      style="max-width: 600px; border-radius: 20px; padding: 15px"
    >
      <el-form :model="form" style="max-width: 600px; padding: 20px">
        <el-form-item
          label="文章标题："
          prop="noteTitle"
          :rules="[{ required: true, message: 'Please input!' }]"
        >
          <el-input v-model="form.noteTitle" />
        </el-form-item>
        <el-form-item
          label="文章描述："
          prop="description"
          :rules="[{ required: true, message: 'Please input!' }]"
        >
          <el-input
            v-model="form.description"
            type="textarea"
            auto-size
            min-rows="4"
            max-rows="8"
          />
        </el-form-item>
        <el-form-item
          label="文章分类："
          prop="noteCategory"
          :rules="[{ required: true, message: '请选择分类' }]"
        >
          <el-select v-model="form.noteCategory" placeholder="请选择">
            <el-option
              v-for="item in noteCategories"
              :key="item.categoryKey"
              :label="item.categoryTitle"
              :value="item.categoryKey"
            />
          </el-select>
        </el-form-item>
        <el-form-item
          label="文章标签："
          prop="noteTags"
          :rules="[{ required: true, message: '请选择标签' }]"
        >
          <el-select
            v-model="form.noteTags"
            :multiple="noteTageList.length > 1"
            placeholder="请选择"
            @change="handleTagsChange"
          >
            <el-option
              v-for="item in noteTageList"
              :key="item.tagKey"
              :label="item.title"
              :value="item.tagKey"
            />
          </el-select>
        </el-form-item>
        <el-form-item
          label="文章封面："
          prop="cover"
          :rules="[{ required: true }]"
        >
          <el-upload
            action="http://127.0.0.1:8080/api/protected/upload"
            :headers="{ Authorization: `${token}` }"
            list-type="picture-card"
            :before-upload="onBefore"
            :on-success="onSuccess"
            :on-remove="handleRemove"
          >
            <el-icon><Plus /></el-icon>
          </el-upload>

          <el-dialog v-model="dialogVisible">
            <img w-full :src="dialogImageUrl" alt="Preview Image" />
          </el-dialog>
        </el-form-item>
        <el-form-item label="&nbsp; 是否置顶：" prop="isTop">
          <el-switch
            v-model="form.isTop"
            :active-value="1"
            :inactive-value="0"
          />
        </el-form-item>
        <el-form-item label="&nbsp; 文章状态：" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio value="public" size="large">公开</el-radio>
            <el-radio value="private" size="large">私密</el-radio>
            <el-radio value="draft" size="large">草稿</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item style="position: relative; left: 73%">
          <el-button @click="handleCancel">返回</el-button>
          <el-button type="primary" @click="submitForm">发布</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<style lang="scss" scoped></style>
