<script setup lang="ts">
import { computed, onMounted, ref } from "vue";

const props = defineProps({
  src: {
    type: String,
    required: true
  }
});

const height = ref(`${document.documentElement.clientHeight - 215}px;`);
const url = computed(() => props.src);

onMounted(() => {
  window.onresize = function temp() {
    height.value = `${document.documentElement.clientHeight - 215}px;`;
  };
});
</script>

<template>
  <iframe :src="url" style="width: 100%; height: 100%" />
</template>

<style scoped lang="scss"></style>
