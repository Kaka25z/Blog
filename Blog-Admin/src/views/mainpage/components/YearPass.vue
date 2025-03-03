<template>
  <div class="process_container">
    <div class="today">
      <div class="process" :style="{ width: yearProgress + '%' }" />
      <p class="time_today">{{ today }}</p>
      <p class="say_today">新的一天，今天也要加油呦！</p>
      <span class="percent_progress">{{ yearProgress }}%</span>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import dayjs from "dayjs";

const today = ref(dayjs(new Date()).format("YYYY年MM月DD日"));
const yearProgress = ref("");

onMounted(() => {
  const calculateYearProgress = (): string => {
    const today: Date = new Date();
    const startOfYear: Date = new Date(today.getFullYear(), 0, 1); // January 1st of the current year
    const endOfYear: Date = new Date(today.getFullYear() + 1, 0, 1); // January 1st of the next year

    const totalDaysInYear: number =
      (endOfYear.getTime() - startOfYear.getTime()) / (24 * 60 * 60 * 1000); // milliseconds to days
    const daysPassed: number =
      (today.getTime() - startOfYear.getTime()) / (24 * 60 * 60 * 1000); // milliseconds to days

    const percentage: number = (daysPassed / totalDaysInYear) * 100;

    return percentage.toFixed(1); // Return the percentage as a string with one decimal place
  };

  yearProgress.value = calculateYearProgress();
});
</script>

<style scoped src="./YearPass.sass"></style>
