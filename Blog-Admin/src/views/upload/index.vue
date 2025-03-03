<script setup lang="ts">
import { UploadFilled } from "@element-plus/icons-vue";
import { ref, computed } from "vue";
import { message } from "@/utils/message";
import { DataInfo, formatToken, getToken } from "@/utils/auth";
import Compressor from "compressorjs";

defineOptions({
  name: "Upload"
});

const fileList = ref([]);

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

const onExceed = () => {
  message("最多上传100张图片");
};

const onError = () => {
  message("上传图片失败", { type: "error" });
};

const onSuccess = () => {
  message("上传图片成功", { type: "success" });
};

const token: string = formatToken(getToken().token);
</script>

<template>
  <div>
    <el-upload
      class="upload-demo"
      drag
      accept="image/jpeg,image/png,image/gif"
      action="http://127.0.0.1:8080/api/protected/upload"
      multiple
      :limit="100"
      :headers="{ Authorization: `${token}` }"
      :before-upload="onBefore"
      :on-exceed="onExceed"
      :file-list="fileList"
      :on-error="onError"
      :on-success="onSuccess"
    >
      <el-icon class="el-icon--upload"><upload-filled /></el-icon>
      <div class="el-upload__text">可以拖拽上传或 <em>点击上传</em></div>
      <template #tip>
        <div class="el-upload__tip">
          可拖拽上传最多3张单个不超过30MB且格式为jpeg/png/gif的图片
        </div>
      </template>
    </el-upload>
  </div>
</template>

<style scoped lang="scss">

</style>
