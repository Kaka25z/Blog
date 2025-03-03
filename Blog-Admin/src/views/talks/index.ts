import { http } from "@/utils/http";
import { baseUrlApi } from "@/api/utils";

export interface Talk {
  talkKey: number;
  talkTitle: string;
  content: string;
  updateTime: string;
}

export async function getTalks() {
  return http.request<any>("get", baseUrlApi("public/talk"));
}

export async function addTalk(data?: object) {
  return http.request<any>("post", baseUrlApi("protect/talk"), {
    data
  });
}

export async function updateTalkById(id: number, data?: object) {
  return http.request<any>("post", baseUrlApi(`protect/talk/${id}`), {
    data
  });
}

export async function deleteTalkById(id: number) {
  return http.request<any>("delete", baseUrlApi(`protect/talk/${id}`));
}
