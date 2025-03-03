<script setup lang="ts">
import { onMounted, ref } from "vue";
import {
  addCategories,
  deleteCategories,
  getCategoriesList,
  updateCategories
} from "@/views/allcategories/index";
import type { Category } from "@/views/allcategories/index";
import { message } from "@/utils/message";
import { Plus, Delete } from "@element-plus/icons-vue";

defineOptions({
  name: "AllCategories"
});

const CategoriesList = ref<Category>([]);
const SelectedCategories = ref([]);
const dialogVisible = ref(false);
const updateForm = ref({
  categoryKey: 0,
  categoryTitle: "",
  color: "",
  icon: "",
  introduce: "",
  pathName: ""
});

function openDialog(row: Category) {
  dialogVisible.value = true;
  updateForm.value = JSON.parse(JSON.stringify(row));
}

function handleCloseDialog(done: () => void) {
  // 清除 updateForm 的内容
  updateForm.value = {
    categoryKey: 0,
    categoryTitle: "",
    color: "",
    icon: "",
    introduce: "",
    pathName: ""
  };
  // 然后关闭对话框
  done();
}

function handleSelectionChange(selection: Category[]) {
  SelectedCategories.value = selection;
}

function resetForm() {
  updateForm.value = {
    categoryKey: 0,
    categoryTitle: "",
    color: "",
    icon: "",
    introduce: "",
    pathName: ""
  };
}

async function submitForm() {
  if (updateForm.value.categoryKey !== 0) {
    const res = await updateCategories(
      updateForm.value.categoryKey,
      updateForm.value
    );
    if (res.code === 200) {
      dialogVisible.value = false;
      await getCategoriesList().then(res => {
        if (res.code === 200) CategoriesList.value = res.data;
      });
      message("修改成功", { type: "success" });
    } else {
      message("修改失败", { type: "error" });
    }
  } else {
    const res = await addCategories(updateForm.value);
    if (res.code === 200) {
      dialogVisible.value = false;
      await getCategoriesList().then(res => {
        if (res.code === 200) CategoriesList.value = res.data;
      });
      message("添加成功", { type: "success" });
    } else {
      message("添加失败", { type: "error" });
    }
  }
}

async function deleteSelectedCategories() {
  if (SelectedCategories.value.length === 0) {
    message("请选择要删除的分类", { type: "warning" });
    return;
  }

  const res = await deleteCategories(
    SelectedCategories.value.map(id => id.categoryKey)
  );

  if (res.code === 200) {
    message("删除成功", { type: "success" });
    await getCategoriesList().then(res => {
      if (res.code === 200) CategoriesList.value = res.data;
    });
  } else {
    message("删除失败", { type: "error" });
  }

  SelectedCategories.value = [];
}

async function handleDelete(categoryKey: number) {
  const res = await deleteCategories([categoryKey]);
  if (res.code === 200) {
    message("删除成功", { type: "success" });
    await getCategoriesList().then(res => {
      if (res.code === 200) CategoriesList.value = res.data;
    });
  } else {
    message("删除失败", { type: "error" });
  }
}

onMounted(async () => {
  await getCategoriesList().then(res => {
    if (res.code === 200) CategoriesList.value = res.data;
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
        plain
        @click="dialogVisible = true"
      />
      <el-button
        v-show="SelectedCategories.length > 0"
        type="danger"
        circle
        size="large"
        :icon="Delete"
        plain
        @click="deleteSelectedCategories"
      />
    </div>
    <div>
      <el-table
        :data="CategoriesList"
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
        <el-table-column fixed prop="categoryKey" label="ID" />
        <el-table-column prop="categoryTitle" label="分类名称" />
        <el-table-column prop="introduce" label="分类介绍" />
        <el-table-column prop="color" label="分类颜色">
          <template #default="scope">
            <el-tag :color="scope.row.color" disable-transitions>
              {{ scope.row.color }}</el-tag
            >
          </template>
        </el-table-column>
        <el-table-column prop="pathName" label="分类路径" />
        <el-table-column fixed="right" label="操作">
          <template #default="scope">
            <el-button
              link
              type="primary"
              size="default"
              @click="openDialog(scope.row)"
              >修改</el-button
            >
            <el-button
              link
              type="danger"
              size="default"
              @click="handleDelete(scope.row.categoryKey)"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </div>

    <el-dialog
      v-model="dialogVisible"
      title="修改分类信息"
      width="30%"
      draggable
      style="border-radius: 20px; max-width: 600px; min-width: 500px"
      :before-close="handleCloseDialog"
    >
      <el-form
        ref="ruleFormRef"
        :model="updateForm"
        status-icon
        label-width="120px"
        style="margin: 10px 0 0 0; padding: 0 30px 0 0"
      >
        <el-form-item
          label="分类名称"
          prop="categoryTitle"
          :rules="[
            {
              required: true,
              message: '请输入分类名称',
              trigger: ['blur', 'change', 'input']
            }
          ]"
        >
          <el-input
            v-model="updateForm.categoryTitle"
            type="text"
            autocomplete="off"
          />
        </el-form-item>
        <el-form-item
          label="分类介绍"
          prop="introduce"
          :rules="[
            {
              required: true,
              message: '请输入分类介绍',
              trigger: ['blur', 'change', 'input']
            }
          ]"
        >
          <el-input
            v-model="updateForm.introduce"
            type="textarea"
            auto-size
            min-rows="4"
            max-rows="8"
            autocomplete="off"
          />
        </el-form-item>
        <el-form-item
          label="分类颜色"
          prop="color"
          :rules="[
            {
              required: true,
              message: '请选择分类颜色',
              trigger: ['blur', 'change', 'input']
            }
          ]"
        >
          <el-input
            v-model="updateForm.color"
            type="text"
            autocomplete="off"
            style="width: 25%"
          />
          <el-color-picker v-model="updateForm.color" />
        </el-form-item>
        <el-form-item
          label="分类路径"
          prop="pathName"
          :rules="[
            {
              required: true,
              message: '请输入分类路径',
              trigger: ['blur', 'change', 'input']
            }
          ]"
        >
          <el-input
            v-model="updateForm.pathName"
            type="text"
            autocomplete="off"
          />
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
