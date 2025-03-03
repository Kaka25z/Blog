export default {
  path: "/monitor",
  meta: {
    title: "系统监控",
    icon: "ic:outline-monitor-heart",
    rank: 7
  },
  children: [
    {
      path: "/monitor",
      name: "Monitor",
      component: () => import("@/views/monitor/index.vue"),
      meta: {
        title: "系统监控"
      }
    }
  ]
};
