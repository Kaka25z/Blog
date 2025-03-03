export default {
  path: "/error",
  redirect: "/error/403",
  meta: {
    icon: "fluent:error-circle-24-regular",
    // showLink: false,
    title: "异常页面",
    rank: 9
  },
  children: [
    {
      path: "/error/403",
      name: "403",
      component: () => import("@/views/error/403.vue"),
      meta: {
        title: "403",
        icon: "solar:forbidden-broken",
        roles: ["管理员", "用户"]
      }
    },
    {
      path: "/error/404",
      name: "404",
      component: () => import("@/views/error/404.vue"),
      meta: {
        title: "404",
        icon: "iconoir:file-not-found"
      }
    },
    {
      path: "/error/500",
      name: "500",
      component: () => import("@/views/error/500.vue"),
      meta: {
        title: "500",
        icon: "mdi:server-network-off",
      }
    }
  ]
} satisfies RouteConfigsTable;
