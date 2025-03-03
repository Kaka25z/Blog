<script setup lang="ts">
import { onMounted, ref } from "vue";
import { addTalk, deleteTalk, deleteTalkById, getTalks, Talk, updateTalkById } from "@/views/talks/index";
import { Edit, Delete } from "@element-plus/icons-vue";
import { formatDateTime } from "@/views/system/user/utils/hook";
import { message } from "@/utils/message";

defineOptions({
  name: "Talks"
});

const TalkList = ref<Talk>([]);
const isDialog = ref(false);
const currentTime: Date = new Date();
const updateTalk = ref<Talk>({
  content: "",
  talkKey: 0,
  talkTitle: "",
  updateTime: ""
});

function openDialog(item) {
  isDialog.value = true;
  updateTalk.value = JSON.parse(JSON.stringify(item));
}

function resetForm() {
  updateTalk.value = {
    content: "",
    talkKey: 0,
    talkTitle: "",
    updateTime: ""
  };
}

function closeDialog() {
  isDialog.value = false;
  resetForm();
}

async function getTalkList() {
  const res = await getTalks();
  if (res.code === 200) TalkList.value = res.data;
}

async function handleDelete(id) {
  deleteTalkById(id).then(res => {
    if (res.code === 200) {
      message("删除成功", { type: "success" });
      getTalkList();
    } else {
      message("删除失败", { type: "error" });
    }
  });
}

async function submitForm() {
  updateTalk.value.updateTime = formatDateTime(currentTime);
  if (updateTalk.value.talkKey !== 0) {
    updateTalkById(updateTalk.value.talkKey, updateTalk.value).then(res => {
      if (res.code === 200) {
        message("修改成功", { type: "success" });
        getTalkList();
        resetForm();
        isDialog.value = false;
      } else {
        message("修改失败", { type: "error" });
      }
    });
  } else {
    addTalk(updateTalk.value).then(res => {
      if (res.code === 200) {
        message("添加成功", { type: "success" });
        getTalkList();
        resetForm();
        isDialog.value = false;
      } else {
        message("添加失败", { type: "error" });
      }
    });
  }
}

onMounted(() => {
  getTalkList();
});
</script>

<template>
  <div>
    <div
      style="
        border-radius: 20px;
        padding: 15px;
        background-color: white;
        margin-bottom: 30px;
      "
    >
      <el-button type="primary" @click="openDialog({ talkKey: 0 })">
        添加说说
      </el-button>
    </div>
    <el-space fill warp :fill-ratio="15">
      <el-card
        v-for="item in TalkList"
        :key="item.talkKey"
        class="card"
        style="width: 300px; border-radius: 20px"
        shadow="hover"
      >
        <template #header>
          <div class="card-header" style="font-weight: bold">
            <span>{{ item.talkTitle }}</span>
          </div>
        </template>
        <div style="font-weight: lighter; font-size: 13px">
          {{ item.content }}
        </div>
        <template #footer>
          <div
            style="display: flex; align-items: center; justify-content: center"
          >
            <el-button
              size="default"
              type="primary"
              plain
              circle
              :icon="Edit"
              style="margin-right: 20%"
              @click="openDialog(item)"
            />
            <el-divider direction="vertical" />
            <el-button
              size="default"
              type="danger"
              plain
              circle
              :icon="Delete"
              style="margin-left: 20%"
              @click="handleDelete(item.talkKey)"
            />
          </div>
        </template>
      </el-card>
    </el-space>

    <el-dialog
      v-model="isDialog"
      title="修改说说"
      width="30%"
      style="border-radius: 20px"
      :before-close="closeDialog"
    >
      <el-form ref="ruleFormRef" :model="updateTalk">
        <el-form-item
          prop="talkTitle"
          label="说说标题："
          :rules="[
            {
              required: true,
              message: '请输入说说标题',
              trigger: ['blur', 'change', 'input']
            }
          ]"
        >
          <el-input v-model="updateTalk.talkTitle" />
        </el-form-item>
        <el-form-item
          prop="content"
          label="说说内容："
          :rules="[
            {
              required: true,
              message: '说说内容',
              trigger: ['blur', 'change', 'input']
            }
          ]"
        >
          <el-input v-model="updateTalk.content" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="resetForm">重置</el-button>
          <el-button type="primary" @click="submitForm">提交</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<style scoped lang="scss">
.flex-container {
  display: flex;
  flex-wrap: wrap;
}

.card {
  margin: 20px;
  background-color: rgba(255, 255, 255, 0.66);
  transition: 0.3s;
  &:hover {
    transform: scale(1.1);
  }
}
</style>
