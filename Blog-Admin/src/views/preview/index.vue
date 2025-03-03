<script setup lang="ts">
import { ref, computed, watchEffect, reactive } from "vue";
import { http } from "@/utils/http";
import { baseUrlApi } from "@/api/utils";
import { ElDialog, ElImage } from "element-plus";
import { message } from "@/utils/message";
import { LazyImg, Waterfall } from "vue-waterfall-plugin-next";
import "vue-waterfall-plugin-next/dist/style.css";

defineOptions({
  name: "Preview"
});

const images = ref([]);
const previewImageUrl = ref("");
const showViewer = ref(false);

const getImagesFromBack = (data?: object) => {
  return http.request<any>("get", baseUrlApi("protected/images"), {
    data
  });
};

const getImages = async () => {
  const res = await getImagesFromBack();
  images.value = res.data;
};

const anyImageSelected = computed(() => {
  return images.value.some(image => image.checked);
});

const openPreview = imageUrl => {
  previewImageUrl.value = imageUrl;
  showViewer.value = true;
};

const deleteSelectedImages = async () => {
  const selectedImages = images.value
    .filter(image => image.checked)
    .map(image => image.imageUrl);

  const deleteSelectedImages = (data?: object) => {
    return http.request<any>("delete", baseUrlApi("protected/delImg"), {
      data
    });
  };
  try {
    await deleteSelectedImages(selectedImages).then(res => {
      if (res.code === 200) {
        message("删除成功", { type: "success" });
        images.value = images.value.filter(image => !image.checked);
        getImages();
      }
    });
  } catch (error) {
    console.log(error);
    message("删除失败", { type: "error" });
  }
};

const options = reactive({
  // 唯一key值
  rowKey: "id",
  // 卡片之间的间隙
  gutter: 15,
  // 是否有周围的gutter
  hasAroundGutter: true,
  // 卡片在PC上的宽度
  width: 320,
  // 自定义行显示个数，主要用于对移动端的适配
  breakpoints: {
    1200: {
      // 当屏幕宽度小于等于1200
      rowPerView: 4
    },
    800: {
      // 当屏幕宽度小于等于800
      rowPerView: 3
    },
    500: {
      // 当屏幕宽度小于等于500
      rowPerView: 2
    }
  },
  // 动画效果 https://animate.style/
  animationEffect: "animate__zoomIn",
  // 动画时间
  animationDuration: 1000,
  // 动画延迟
  animationDelay: 300,
  // 背景色
  backgroundColor: "#ffffff",
  // 图片字段选择器，如果层级较深，使用 xxx.xxx.xxx 方式
  imgSelector: "src.original",
  // 是否懒加载
  lazyload: true
});

getImages();
</script>

<template>
  <div>
    <div style="background-color: white; border-radius: 15px; padding: 15px">
      <el-button
        type="danger"
        plain
        :disabled="!anyImageSelected"
        @click="deleteSelectedImages"
        >删除选中图片</el-button
      >
    </div>
    <div style="background-color: white; border-radius: 15px">
      <el-scrollbar
        max-height="calc(100vh - 120px)"
        class="content"
        style="margin-top: 30px; border-radius: 15px"
      >
        <Waterfall :list="images" v-bind="options">
          <template #item="{ item }">
            <div class="image-container">
              <el-checkbox v-model="item.checked" class="checkbox" />
              <LazyImg
                :url="item.imageUrl"
                style="
                  width: auto;
                  height: auto;
                  border-radius: 15px;
                  border-color: #39c5bb;
                  border-width: 1px;
                  border-style: solid;
                "
                @click="openPreview(item.imageUrl)"
              />
            </div>
          </template>
        </Waterfall>
      </el-scrollbar>
    </div>

    <el-image-viewer
      v-if="showViewer"
      :url-list="[previewImageUrl]"
      @close="showViewer = false"
    />
  </div>
</template>

<style scoped lang="scss">
.image-col {
  position: relative;
}

.main {
  margin: 0;
}

.image-container {
  position: relative;
  border-radius: 10px;
}

.checkbox {
  position: absolute;
  top: 0;
  left: 10px;
  z-index: 2;
  transform: scale(1.5);
}
</style>
