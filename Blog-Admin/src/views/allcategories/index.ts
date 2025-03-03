import { http } from "@/utils/http";
import { baseUrlApi } from "@/api/utils";

export interface Category {
  categoryKey: number;
  categoryTitle: string;
  pathName: string;
  introduce: string;
  icon: string;
  color: string;
}

export async function getCategoriesList() {
  return http.request<any>("get", baseUrlApi("public/category"));
}

export async function addCategories(data?: object) {
  return http.request<any>("post", baseUrlApi("protected/category"), {
    data
  });
}

export async function deleteCategories(data?: object) {
  return http.request<any>("delete", baseUrlApi("protected/category"), {
    data
  });
}

export async function updateCategories(id: number, data?: object) {
  return http.request<any>("post", baseUrlApi(`protected/category/${id}`), {
    data
  });
}
