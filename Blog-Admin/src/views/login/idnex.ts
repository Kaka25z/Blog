import { baseUrlApi } from "@/api/utils";
import { http } from "@/utils/http";

export async function getVerifyCode() {
  return http.request<any>("get", baseUrlApi("public/verifycode"))
}