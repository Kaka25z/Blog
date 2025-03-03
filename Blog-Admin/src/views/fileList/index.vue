<script setup lang="ts">
import { onMounted, ref } from "vue";
import { http } from "@/utils/http";
import { baseUrlApi } from "@/api/utils";
import { Delete, Plus } from "@element-plus/icons-vue";
import "element-plus/theme-chalk/base.css";
import { message } from "@/utils/message";

defineOptions({
  name: "FileList"
});

onMounted(() => {
  getFileList();
});

const FileList = ref<FileList>([]);
const Selected = ref([]);

const size2Num = size => {
  const units = { B: 1, KB: 1e3, MB: 1e6, GB: 1e9 };
  const unit = size.match(/[\d.\-+]*\s*(.*)/)[1] || "B";
  return parseFloat(size) * units[unit];
};

const sortChange = ({ prop, order }) => {
  if (prop === "fileName") {
    if (order === "ascending") {
      FileList.value.sort((a, b) => (a[prop] > b[prop] ? 1 : -1));
    } else if (order === "descending") {
      FileList.value.sort((a, b) => (a[prop] < b[prop] ? 1 : -1));
    }
  } else if (prop === "fileSize") {
    if (order === "ascending") {
      FileList.value.sort((a, b) =>
        size2Num(a[prop]) > size2Num(b[prop]) ? 1 : -1
      );
    } else if (order === "descending") {
      FileList.value.sort((a, b) =>
        size2Num(a[prop]) < size2Num(b[prop]) ? 1 : -1
      );
    }
  } else {
    null;
  }
};

function handleSelectionChange(selection: FileList[]) {
  Selected.value = selection;
}

async function deleteFile() {
  const selectedFiles = Selected.value;
  const res = await http.request<any>(
    "delete",
    baseUrlApi("protected/del-file"),
    {
      data: selectedFiles
    }
  );

  if (res.code === 200) {
    message("删除成功", { type: "success" });
    getFileList();
  } else {
    message(res.message, { type: "error" });
  }
}

async function handleDelete(row) {
  const res = await http.request<any>(
    "delete",
    baseUrlApi("protected/del-file"),
    {
      data: [row]
    }
  );

  if (res.code === 200) {
    message("删除成功", { type: "success" });
    getFileList();
  } else {
    message(res.message, { type: "error" });
  }
}

async function getFileList() {
  const res = await http.request<any>(
    "get",
    baseUrlApi("protected/list-file-info")
  );

  if (res.code === 200) {
    FileList.value = res.data;
  } else {
    message(res.message, { type: "error" });
  }
}
</script>

<template>
  <div>
    <transition name="el-fade-in-linear">
      <div
        style="
          background-color: white;
          margin-bottom: 30px;
          border-radius: 20px;
        "
      >
        <el-button
          v-show="Selected.length > 0"
          type="danger"
          circle
          size="large"
          :icon="Delete"
          plain
          style="margin: 20px"
          @click="deleteFile"
        />
      </div>
    </transition>
    <div>
      <el-table
        :data="FileList"
        @sort-change="sortChange"
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
        <el-table-column fixed prop="fileName" sortable="custom" label="文件名">
          <template #default="scope">
            <div style="display: flex; align-items: center">
              <IconifyIconOnline
                icon="icon-park:image-files"
                v-if="scope.row.contentType.startsWith('image')"
              />
              <IconifyIconOnline
                icon="icon-park:file-text"
                v-else-if="scope.row.contentType.startsWith('text')"
              />
              <span>{{ scope.row.fileName }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="fileSize" sortable="custom" label="文件大小" />
        <el-table-column prop="contentType" label="文件类型">
          <template #default="scope">
            <el-tag disable-transitions> {{ scope.row.contentType }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="lastModifiedTime" label="修改时间" />
        <el-table-column fixed="right" label="操作">
          <template #default="scope">
            <el-button
              link
              type="danger"
              size="default"
              @click="handleDelete(scope.row)"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<style scoped lang="sass"></style>
