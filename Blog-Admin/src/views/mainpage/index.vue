<script setup lang="ts">
import { storageLocal } from "@pureadmin/utils";
import { onMounted, ref, nextTick } from "vue";
import { getCategoriesList } from "../allcategories";
import {
  getAllNotesFormBackend,
  getNoteTageListFormBackend
} from "../allnotes/utils";
import ArticleRecord from "@/views/mainpage/components/ArticleRecord.vue";
import YearPass from "@/views/mainpage/components/YearPass.vue";
import axios from "axios";

defineOptions({
  name: "Welcome"
});

const userinfo = ref({});
const titleElement = ref<HTMLElement | null>(null);
const LoadedCount = ref(0);

const list = [
  {
    index: 1,
    logoClass: "logo2",
    backgroundColor: "rgba(230,240,0,0.3)",
    logo: "✨️",
    name: "文章总数",
    value: 0
  },
  {
    index: 2,
    logoClass: "logo2",
    backgroundColor: "rgba(255,0,0,0.3)",
    logo: "❤️️",
    name: "分类总数",
    value: 0
  },
  {
    index: 3,
    logoClass: "logo2",
    backgroundColor: "rgb(147,154,216,0.3)",
    logo: "🎯",
    name: "标签总数",
    value: 0
  }
];

onMounted(() => {
  userinfo.value = storageLocal().getItem("user-info");
});

onMounted(async () => {
  await getCategoriesList().then(res => {
    list[1].value = res.data.length;
    LoadedCount.value = 1;
  });
  await getAllNotesFormBackend().then(res => {
    list[0].value = res.data.length;
    LoadedCount.value = 2;
  });
  await getNoteTageListFormBackend().then(res => {
    list[2].value = res.data.length;
    LoadedCount.value = 3;
  });
});

onMounted(async () => {
  await nextTick();

  const getSay = async () => {
    const res = await axios.get("https://tenapi.cn/v2/yiyan?format=json");
    console.log(res.data.data.hitokoto);
    return res.data.data.hitokoto;
  };

  if (titleElement.value) {
    titleElement.value.textContent = "";

    const message = await getSay();
    for (let i = 0; i < message.length; i++) {
      await new Promise(resolve => setTimeout(resolve, 250));
      titleElement.value.textContent += message[i];
    }
  }
});
</script>

<template>
  <div class="main">
    <el-row style="padding: 40px" justify="center">
      <el-col :span="3">
        <el-avatar :src="userinfo.userAvatar" :size="140" class="avatar"/>
      </el-col>
      <el-col
        :span="10"
        style="
          text-align: center;
          display: flex;
          align-items: center;
          justify-content: center;
        "
      >
        <h2 ref="titleElement" />
      </el-col>
    </el-row>
    <el-row
      style="flex-direction: row; width: 100%; height: calc(100% - 100px)"
    >
      <el-col :span="5">
        <el-card
          v-for="item in list"
          v-if="LoadedCount === 3"
          :key="item.index"
          style="margin: 20px; height: 25%; border-radius: 20px"
          shadow="hover"
        >
          <p>
            <span
              :class="item.logoClass"
              :style="{ backgroundColor: item.backgroundColor }"
            >
              {{ item.logo }}
            </span>
            {{ item.name }}
          </p>
          <p style="margin-top: 20px">{{ item.value }}</p>
        </el-card>
      </el-col>
      <el-col
        :span="12"
        style="position: relative; top: -58px; margin-left: 10px"
      >
        <ArticleRecord />
      </el-col>
      <el-col
        :span="6"
        style="position: relative; top: 12px; margin-left: 30px"
      >
        <YearPass style="z-index: 1" />
        <el-calendar
          style="
            border-bottom-left-radius: 20px;
            border-bottom-right-radius: 20px;
            background: rgba(255, 255, 255, 0.9);
          "
        >
          <template #header>
            <span />
          </template>
        </el-calendar>
      </el-col>
    </el-row>
  </div>
</template>

<style scoped lang="sass">
.main
  background: radial-gradient(ellipse at top left, #65DAFF 0%, #8b91c7 50%, #FF9089 100%)
  border-radius: 20px
  height: 100vh
  display: block
  flex-direction: row

.avatar
  animation: animated-border 1.5s infinite
  transition: transform 0.8s

  &:hover
    transform: scale(1.2) rotate(45deg)

@keyframes animated-border
  0%
    box-shadow: 0 0 0 0 rgba(255,255,255,0.8)

  100%
    box-shadow: 0 0 0 20px rgba(255,255,255,0)
</style>
