import { http } from "@/utils/http";
import { baseUrlApi } from "@/api/utils";

type Result = {
  code: number;
  data: Array<any>;
};

export const getAsyncRoutes = () => {
  return http.request<any>("get", baseUrlApi("protected/listMenu"));
};
