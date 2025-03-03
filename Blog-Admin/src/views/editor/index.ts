import { http } from "@/utils/http";
import { baseUrlApi } from "@/api/utils";

export async function getNoteById(id: string) {
  return http.request<any>("get", baseUrlApi(`public/notes/${id}`));
}

export async function updateNote(id: string, data: any) {
  return http.request<any>("post", baseUrlApi(`protected/notes/${id}`), {
    data
  });
}

export async function createNote(data: any) {
  return http.request<any>("post", baseUrlApi(`protected/notes`), {
    data
  });
}
