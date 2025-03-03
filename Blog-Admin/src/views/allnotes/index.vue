<script setup lang="ts">
import { ref, computed, onMounted } from "vue";
import {
  getAllNotesFormBackend,
  getCategoriesFormBackend,
  getNoteTageListFormBackend,
  deleteNotes,
  searchNotes
} from "@/views/allnotes/utils";
import { formatNote, NoteType, SearchNoteType } from "@/views/allnotes/types";
import {
  ElImage,
  ElTag,
  ElButton,
  ElPopconfirm,
  ElMessageBox,
  ElMessage
} from "element-plus";
import { Edit, Delete } from "@element-plus/icons-vue";
import { useRouter } from "vue-router";
import { message } from "@/utils/message";
defineOptions({
  name: "AllNotes"
});

const router = useRouter();
const staticDate = ref<NoteType[]>([]);
const noteCategories = ref([]);
const noteTageList = ref([]);

const searchNote = ref<SearchNoteType>({});

onMounted(() => {
  initNotes();
  initCategories();
  initTageList();
});

const initNotes = async () => {
  const res = await getAllNotesFormBackend();
  staticDate.value = res.data.map((item: formatNote) => {
    return {
      ...item,
      key: item.noteKey,
      noteTags: item.noteTags
        ? item.noteTags.split(",").map(tag => parseInt(tag, 10))
        : []
    };
  });
};

const initCategories = async () => {
  const res = await getCategoriesFormBackend();
  noteCategories.value = res.data;
};

const initTageList = async () => {
  const res = await getNoteTageListFormBackend();
  noteTageList.value = res.data;
};

const getStatusIcon = status => {
  switch (status) {
    case "public":
      return "ic:baseline-public";
    case "private":
      return "ri:git-repository-private-fill";
    case "draft":
      return "fluent:drafts-20-regular";
    default:
      return "carbon:unknown";
  }
};

const getCategories = noteCategory => {
  return noteCategories.value.filter(
    category => category.categoryKey === noteCategory
  );
};

const renderNoteTags = noteTags => {
  return noteTags.map(tagKey => {
    const tag = noteTageList.value.find(t => t.tagKey === tagKey);
    return tag || {};
  });
};

function editNote(id) {
  router.push({ path: `/notes/editnotes/index`, query: { id: id } });
}

function resetSearchNote() {
  searchNote.value = {};
}

async function onSearch() {
  console.log(searchNote.value);
  if (!searchNote.value.top) {
    message("请选择是否置顶", { type: "warning" });
    return;
  }

  const res = await searchNotes(searchNote.value);
  if (res.code === 200) {
    staticDate.value = res.data.map((item: formatNote) => {
      return {
        ...item,
        key: item.noteKey,
        noteTags: item.noteTags
          ? item.noteTags.split(",").map(tag => parseInt(tag, 10))
          : []
      };
    });

    message("搜索成功", { type: "success" });
  } else {
    message("搜索失败", { type: "error" });
    return;
  }
}

const deleteNote = noteKey => {
  try {
    ElMessageBox.confirm("确定删除此文章？", "删除确认", {
      confirmButtonText: "删除",
      cancelButtonText: "取消",
      type: "warning"
    }).then(async () => {
      let noteKeyArray: string[] = [noteKey];
      const res = await deleteNotes(noteKeyArray);
      if (res.code === 200) {
        message("删除成功", { type: "success" });
        await initNotes();
      }
    });
  } catch (error) {
    message("删除失败", { type: "error" });
  }
  console.log(noteKey);
};
</script>

<template>
  <div>
    <div class="form">
      <el-form :inline="true" :model="searchNote" style="width: 100%">
        <el-form-item prop="title" label="文章标题：" style="width: 20%">
          <el-input
            v-model="searchNote.title"
            placeholder="请输入文章标题"
            clearable
          />
        </el-form-item>
        <el-form-item
          prop="top"
          label="是否置顶："
          style="width: 20%"
          :rules="[
            {
              required: true,
              message: '请选择是否置顶',
              trigger: ['blur', 'change', 'input']
            }
          ]"
        >
          <el-select v-model="searchNote.top" placeholder="请选择" clearable>
            <el-option label="默认" value="-1" />
            <el-option label="是" value="1" />
            <el-option label="否" value="0" />
          </el-select>
        </el-form-item>
        <el-form-item label="文章分类：" style="width: 20%">
          <el-select
            v-model="searchNote.categories"
            placeholder="请选择"
            clearable
          >
            <el-option
              v-for="category in noteCategories"
              :key="category.categoryKey"
              :label="category.categoryTitle"
              :value="category.categoryTitle"
            />
          </el-select>
        </el-form-item>
        <el-form-item prop="status" label="文章状态：" style="width: 20%">
          <el-select v-model="searchNote.status" placeholder="请选择" clearable>
            <el-option label="公开" value="public" />
            <el-option label="私密" value="private" />
            <el-option label="草稿" value="draft" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="default" plain @click="resetSearchNote"
            >重置</el-button
          >
          <el-button type="primary" plain @click="onSearch">搜索</el-button>
        </el-form-item>
      </el-form>
    </div>

    <div
      style="
        background-color: white;
        border-radius: 10px;
        padding: 20px;
        margin-top: 30px;
      "
    >
      <el-table :data="staticDate" style="width: 100%">
        <el-table-column prop="cover" label="封面缩略图" align="center">
          <template #default="{ row }">
            <el-image
              :src="row.cover"
              fit="cover"
              style="max-width: 200px; border-radius: 5px"
              :preview-src-list="[row.cover]"
              :preview-teleported="true"
            />
          </template>
        </el-table-column>

        <el-table-column prop="noteTitle" label="文章标题" align="center" />

        <el-table-column prop="noteCategory" label="文章分类" align="center">
          <template #default="{ row }">
            <el-tag
              v-for="category in getCategories(row.noteCategory)"
              :key="category.categoryKey"
              :color="category.color"
            >
              <i :class="`iconfont ${category.icon}`" />
              <span>{{ category.categoryTitle }}</span>
            </el-tag>
          </template>
        </el-table-column>

        <el-table-column prop="tags" label="文章标签" align="center">
          <template #default="{ row }">
            <el-tag
              v-for="tag in renderNoteTags(row.noteTags)"
              :key="tag.tagKey"
              :color="tag.color"
            >
              <span>{{ tag.title }}</span>
            </el-tag>
          </template>
        </el-table-column>

        <el-table-column prop="isTop" label="是否置顶" align="center">
          <template #default="{ row }">
            <div
              style="
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100%;
              "
            >
              <IconifyIconOnline
                :icon="
                  row.isTop === 1
                    ? 'clarity:success-standard-line'
                    : 'radix-icons:cross-circled'
                "
                width="20"
              />
            </div>
          </template>
        </el-table-column>

        <el-table-column prop="updateTime" label="最近更新时间">
          <template #default="{ row }">
            <div
              style="
                width: 160px;
                height: 26px;
                color: rgba(0, 0, 0.88);
                font-weight: 600;
                border-radius: 10px;
              "
            >
              {{ row.updateTime }}
            </div>
          </template>
        </el-table-column>

        <el-table-column prop="status" label="文章状态" align="center">
          <template #default="{ row }">
            <div
              style="
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100%;
              "
            >
              <IconifyIconOnline :icon="getStatusIcon(row.status)" width="20" />
            </div>
          </template>
        </el-table-column>

        <el-table-column fixed="right" label="操作" align="center">
          <template #default="{ row }">
            <el-button
              type="primary"
              :icon="Edit"
              size="large"
              plain
              circle
              @click="editNote(row.noteKey)"
            />
            <el-button
              type="danger"
              size="large"
              :icon="Delete"
              plain
              circle
              @click="deleteNote(row.noteKey)"
            />
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<style scoped lang="scss">
.form {
  background-color: white;
  border-radius: 10px;
  .el-form-item {
    margin: 0;
    padding: 20px;
  }
  .el-button {
    margin-left: 40px;
  }
}
</style>
