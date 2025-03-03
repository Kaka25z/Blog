<script setup lang="ts">
import { onMounted, ref } from "vue";
import {
  addTag,
  deleteTag,
  getTagListAll,
  TagList
} from "@/views/alltags/index";
import { Delete, Plus } from "@element-plus/icons-vue";
import { message } from "@/utils/message";

defineOptions({
  name: "AllTags"
});

const TagListAll = ref<TagList>([]);
const dialogVisible = ref(false);
const SelectedTags = ref([]);
const addTagForm = ref<TagList>({
  color: "",
  tagKey: 0,
  title: ""
});

function openDialog() {
  dialogVisible.value = true;
}

function handleSelectionChange(selection: TagList[]) {
  SelectedTags.value = selection;
}

function resetForm() {
  addTagForm.value = {
    color: "",
    tagKey: 0,
    title: ""
  };
}

async function deleteSelectedTags() {
  if (SelectedTags.value.length === 0) {
    message("请选择要删除的标签", { type: "warning" });
    return;
  }

  const res = await deleteTag(SelectedTags.value.map(id => id.tagKey));

  if (res.code === 200) {
    message("删除成功", { type: "success" });
    await getTagListAll().then(res => {
      if (res.code === 200) TagListAll.value = res.data;
    });
  } else {
    message("删除失败", { type: "error" });
  }

  SelectedTags.value = [];
}

async function handleDelete(tagKey: number) {
  const res = await deleteTag([tagKey]);
  if (res.code === 200) {
    message("删除成功", { type: "success" });
    await getTagListAll().then(res => {
      if (res.code === 200) TagListAll.value = res.data;
    });
  } else {
    message("删除失败", { type: "error" });
  }
}

async function submitForm() {
  const res = await addTag(addTagForm.value);
  if (res.code === 200) {
    dialogVisible.value = false;
    await getTagListAll().then(res => {
      if (res.code === 200) TagListAll.value = res.data;
    });
    message("添加成功", { type: "success" });
  } else {
    message("添加失败", { type: "error" });
  }
}

onMounted(async () => {
  await getTagListAll().then(res => {
    if (res.code === 200) TagListAll.value = res.data;
  });
});
</script>

<template>
  <div>
    <div
      style="
        background-color: white;
        padding: 15px;
        margin-bottom: 30px;
        border-radius: 20px;
      "
    >
      <el-button
        type="primary"
        circle
        size="large"
        :icon="Plus"
        @click="openDialog"
        plain
      />
      <el-button
        v-show="SelectedTags.length > 0"
        type="danger"
        circle
        size="large"
        @click="deleteSelectedTags"
        :icon="Delete"
        plain
      />
    </div>
    <div>
      <el-table
        :data="TagListAll"
        style="
          width: 100%;
          z-index: 0;
          border-radius: 15px;
          background-color: white;
          padding: 20px;
        "
        table-layout="auto"
        size="large"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" />
        <el-table-column fixed prop="tagKey" label="标签ID" />
        <el-table-column prop="title" label="标签名称" />
        <el-table-column prop="color" label="标签颜色">
          <template #default="scope">
            <el-tag :color="scope.row.color" disable-transitions>
              {{ scope.row.color }}</el-tag
            >
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作">
          <template #default="scope">
            <el-button
              link
              type="danger"
              size="default"
              @click="handleDelete(scope.row.tagKey)"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </div>

    <el-dialog
      v-model="dialogVisible"
      title="修改标签信息"
      width="30%"
      draggable
      style="border-radius: 20px; max-width: 600px; min-width: 500px"
    >
      <el-form
        ref="ruleFormRef"
        :model="addTagForm"
        status-icon
        label-width="100px"
        style="margin: 10px 0 0 0; padding: 0 30px 0 0"
      >
        <el-form-item
          label="标签名称"
          prop="title"
          :rules="[
            {
              required: true,
              message: '请输入标签名称',
              trigger: ['blur', 'change', 'input']
            }
          ]"
        >
          <el-input v-model="addTagForm.title" type="text" autocomplete="off" />
        </el-form-item>
        <el-form-item
          label="标签颜色"
          prop="color"
          :rules="[
            {
              required: true,
              message: '请选择标签颜色',
              trigger: ['blur', 'change', 'input']
            }
          ]"
        >
          <el-input v-model="addTagForm.color" type="text" style="width: 50%" />
          <el-color-picker v-model="addTagForm.color" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="resetForm">重置</el-button>
          <el-button type="primary" @click="submitForm">确认</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<style scoped lang="scss"></style>
