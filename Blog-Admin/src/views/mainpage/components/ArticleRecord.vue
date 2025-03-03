<script setup lang="ts">
import { ref, onMounted } from "vue";
import dayjs from "dayjs";
import { getTagListAll } from "@/views/alltags/index";
import { http } from "@/utils/http";
import { baseUrlApi } from "@/api/utils";
import { getNoteTageListFormBackend } from "@/views/allnotes/utils";
import { ElTag } from "element-plus";

const newNotes = ref([]);
const noteTageList = ref([]);

async function getNotePage() {
  return http.request<any>("get", baseUrlApi("public/notes/page"));
}

const renderNoteTags = noteTags => {
  return noteTags.map(tagKey => {
    const tag = noteTageList.value.find(t => t.tagKey === tagKey);
    return tag || {};
  });
};

const initTageList = async () => {
  const res = await getNoteTageListFormBackend();
  noteTageList.value = res.data;
};

onMounted(async () => {
  const res = await getNotePage();
  newNotes.value = res.data.map(item => {
    return {
      ...item,
      noteTags: item.noteTags
        ? item.noteTags.split(",").map(tag => parseInt(tag, 10))
        : []
    };
  });
  initTageList();
});

const formatDate = date => {
  return dayjs(date).format("YYYY-MM-DD");
};
</script>

<template>
  <div class="articleRecord">
    <div class="articleRecordBox" style="overflow-y: auto">
      <h3>最新文章</h3>
      <el-card
        v-for="item in newNotes"
        :key="item.key"
        class="articleRecordCard in"
        style="
          background: rgb(255 255 255 / 50%);
          backdrop-filter: blur(10px);
          border: 0;
          border-radius: 20px;
        "
        shadow="hover"
      >
        <div class="post-date">
          <span>
            <i
              class="iconfont icon-naozhong icon"
              style=" display: inline;font-size: 22px; vertical-align: middle"
            />
            {{ formatDate(item.updateTime) }}
          </span>
        </div>
        <div class="article_cord">
          <h3>" {{ item.noteTitle }} "</h3>
          <div
            style="
              max-height: 2.5em;
              margin-top: 8px;
              margin-right: 60px;
              margin-left: 25px;
              overflow: hidden;
              font-weight: 500;
              line-height: 1.3em;
              text-align: left;
              text-indent: 2em;
              text-overflow: ellipsis;
              white-space: normal;
            "
          >
            <p>{{ item.description }}</p>
          </div>

          <div class="tags">
            <el-space wrap>
              <el-tag
                v-for="tag in renderNoteTags(item.noteTags)"
                :key="tag.tagKey"
                :color="tag.color"
                class="tag"
              >
                <span>{{ tag.title }}</span>
              </el-tag>
            </el-space>
          </div>
        </div>
      </el-card>
    </div>
  </div>
</template>

<style scoped lang="sass">
.in
  opacity: 0
  transform: translateY(-25px)
  animation: fadeIn 1s forwards


.articleRecord
  padding-top: 30px
  width: 100%
  height: 100%
  border-radius: 10px
  box-sizing: border-box
  display: flex
  flex-direction: column
  align-items: center
  .articleRecordImg
    width: 100%
    height: 19%
    background-color: white
    border-radius: 10px
    box-shadow: 2px 12px 12px 2px #d7d6d6
    @media only screen and (max-width: 1530px)
    height: auto
    img
      width: 98%

    img
      margin: 10px

  .articleRecordBox
    height: 80%
    width: 100%
    margin-top: 10px
    overflow-y: auto
    overflow-x: hidden

    h3
      margin-left: 15px

    .articleRecordCard
      width: 98%
      height: 27%
      margin: 1.3%
      border-radius: 10px
      padding: 1%
      @media only screen and (max-width: 1530px)
        padding: 10px
        height: auto
        height: 120px

      .tags
        position: absolute
        bottom: 10px
        left: 20px

        .tag
          height: max-content
          width: max-content
          background-color: #795da3
          border-radius: 5px
          padding: 5px
          border: 0


      &::before
        content: ''
        width: 3px
        height: 90%
        background-color: #484f58
        position: absolute
        left: -5px
        border-radius: 10px
        transform: translateX(-5px)


      h3
        display: inline

      .post-date
        float: right
        background-color: #1e73be26
        font-size: 12px
        width: max-content
        padding: 4px 10px 4px 10px
        border-radius: 6px
        color: #505050
        white-space: nowrap
        transition: all 0.8s ease!important
        line-height: 20px


@keyframes fadeIn
  from
    opacity: 0
    transform: translateY(-25px)
  to
    opacity: 1
    transform: translateY(0)
</style>
