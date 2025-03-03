import { http } from "@/utils/http";
import { baseUrlApi } from "@/api/utils";

export interface TagList {
  tagKey: number;
  title: string;
  color: string;
}

export async function getTagListAll() {
  return http.request<any>("get", baseUrlApi("public/tagone"));
}

export async function addTag(data?: object) {
  return http.request<any>("post", baseUrlApi("protected/tagone"), {
    data
  });
}

export async function deleteTag(data?: object) {
  return http.request<any>("delete", baseUrlApi("protected/tag"), {
    data
  });
}
