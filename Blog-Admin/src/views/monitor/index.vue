<script setup lang="ts">
import IFrame from "@/views/monitor/i-frame.vue";
import { ref } from "vue";

defineOptions({
  name: "Monitor"
});

const url =
  "http://192.168.5.158:3000/d/springboot/spring-boot-3-x-statistics?orgId=1&refresh=5s";
</script>

<template>
  <div style="height: calc(100vh - 150px)">
    <i-frame v-model:src="url" style="border-radius: 20px" />
  </div>
</template>

<style scoped lang="scss"></style>
