<script setup lang="ts">
import { ref } from "vue";
import { formRules } from "@/views/system/user/utils/rule";
import {
  userInfo,
  confirmDeleteDialogVisible,
  fetchUserInfo,
  handleClose,
  updateUser,
  uploadUserAvatar,
  confirmDelete,
  formatDateTime
} from "@/views/system/user/utils/hook";
import { message } from "@/utils/message";

defineOptions({
  name: "User"
});

fetchUserInfo();

const ruleForm = ref({
  id: "",
  username: "",
  password: "",
  phone: "",
  email: "",
  role: "",
  updateTime: ""
});

const currentTime: Date = new Date();
const userIdToDelete = ref("");
const usernameToDelete = ref("");
const dialogVisible = ref(false);

async function submitForm() {
  ruleForm.value.updateTime = formatDateTime(currentTime);
  updateUser(ruleForm.value).then(res => {
    if (res.code === 200) {
      message("修改成功", { type: "success" });
      dialogVisible.value = false;
      restRuleForm();
      fetchUserInfo();
    } else {
      message("修改失败", { type: "error" });
    }
  });
}

function restRuleForm() {
  ruleForm.value = {
    id: "",
    username: "",
    password: "",
    phone: "",
    email: "",
    role: "",
    updateTime: ""
  };
}

function openDeleteConfirmDialog(userId, username) {
  usernameToDelete.value = username;
  userIdToDelete.value = userId;
  confirmDeleteDialogVisible.value = true;
}

function openDialog(rowData: any) {
  dialogVisible.value = true;
  ruleForm.value = rowData;
}
</script>

<template>
  <div>
    <el-table
      :data="userInfo"
      style="
        width: 100%;
        z-index: 0;
        border-radius: 15px;
        background-color: white;
        padding: 20px;
      "
      table-layout="auto"
      size="large"
    >
      <el-table-column fixed prop="id" label="用户ID" />
      <el-table-column prop="username" label="用户名" />
      <el-table-column prop="userAvatar" label="用户头像">
        <template v-slot="scope">
          <el-image
            fit="cover"
            :preview-teleported="true"
            :src="scope.row.userAvatar"
            :preview-src-list="[scope.row.userAvatar]"
            class="w-[50px] h-[50px] rounded-full align-middle"
          />
        </template>
      </el-table-column>
      <el-table-column prop="phone" label="手机号" />
      <el-table-column prop="role" label="用户身份">
        <template v-slot="scope">
          <el-tag size="small">{{ scope.row.role }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="email" label="电子邮箱" />
      <el-table-column prop="updateTime" label="更新时间" />
      <el-table-column prop="createTime" label="创建时间" />
      <el-table-column fixed="right" label="选项">
        <template v-slot="scope">
          <el-button
            link
            type="primary"
            size="default"
            @click="openDialog(scope.row)"
          >
            修改
          </el-button>
          <el-button
            link
            type="primary"
            size="default"
            @click="uploadUserAvatar(scope.row)"
          >
            上传头像
          </el-button>
          <el-button
            link
            type="danger"
            size="default"
            @click="openDeleteConfirmDialog(scope.row.id, scope.row.username)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <div>
      <el-dialog
        v-model="dialogVisible"
        title="修改用户"
        width="30%"
        :before-close="handleClose"
        style="border-radius: 15px"
      >
        <el-form
          ref="ruleFormRef"
          style="max-width: 600px"
          status-icon
          :model="ruleForm"
          :rules="formRules"
          label-width="auto"
          class="demo-ruleForm"
          label-position="top"
        >
          <el-form-item label="用户名" prop="username">
            <el-input
              v-model="ruleForm.username"
              type="text"
              autocomplete="off"
            />
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input
              v-model="ruleForm.password"
              type="password"
              autocomplete="off"
            />
          </el-form-item>
          <el-form-item label="手机号" prop="phone">
            <el-input v-model.number="ruleForm.phone" />
          </el-form-item>
          <el-form-item label="电子邮箱" prop="email">
            <el-input v-model="ruleForm.email" />
          </el-form-item>
          <el-form-item>
            <el-button
              style="margin-top: 10px"
              type="primary"
              @click="submitForm()"
            >
              确认
            </el-button>
            <el-button style="margin-top: 10px" @click="restRuleForm()"
              >重置</el-button
            >
          </el-form-item>
        </el-form>
      </el-dialog>

      <el-dialog
        v-model="confirmDeleteDialogVisible"
        title="确认删除"
        :before-close="handleClose"
        style="border-radius: 15px"
        width="30%"
      >
        <span>是否确认删除{{ usernameToDelete }}?</span>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="confirmDeleteDialogVisible = false"
              >取消</el-button
            >
            <el-button type="danger" @click="confirmDelete(userIdToDelete)"
              >确认</el-button
            >
          </span>
        </template>
      </el-dialog>
    </div>
  </div>
</template>

<style scoped lang="scss">
:deep(.el-dropdown-menu__item i) {
  margin: 0;
}

:deep(.el-button:focus-visible) {
  outline: none;
}

.main-content {
  margin: 24px 24px 0 !important;
}

.search-form {
  :deep(.el-form-item) {
    margin-bottom: 12px;
  }
}

.el-image-viewer__mask {
  z-index: 999;
}
</style>
