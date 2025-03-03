import { http } from "@/utils/http";
import { baseUrlApi } from "@/api/utils";

export function getAllNotesFormBackend() {
  return http.request<any>("get", baseUrlApi("public/notes"));
}

export function getCategoriesFormBackend() {
  return http.request<any>("get", baseUrlApi("public/category"));
}

export function getNoteTageListFormBackend() {
  return http.request<any>("get", baseUrlApi("public/tagone"));
}

export function deleteNotes(data?: object) {
  return http.request<any>("delete", baseUrlApi(`protected/notes`), {
    data
  });
}

export function searchNotes(data?: object) {
  return http.request<any>("post", baseUrlApi("public/notes/search"), {
    data
  });
}

