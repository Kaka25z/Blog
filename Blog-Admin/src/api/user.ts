import { http } from "@/utils/http";
import { baseUrlApi } from "@/api/utils";
import { getToken } from "@/utils/auth";

export type UserResult = {
  code: number;
  data: {
    token: string;
    userAvatar: string;
    expires: Date;
  };
  message: string;
};

export type RefreshTokenResult = {
  code: number;
  data: {
    token: string;
    expires: Date;
  };
};

/** 登录 */
export const getLogin = (data?: object) => {
  return http.request<UserResult>("post", baseUrlApi("login"), { data });
};

/** 刷新`token` */
export const refreshTokenApi = (data?: object) => {
  return http.request<RefreshTokenResult>("post", baseUrlApi("refreshtoken"), {
    data
  });
};

export const getLogout = () => {
  return http.request<any>("post", baseUrlApi("protected/logout"), {
    headers: { Authorization: `Bearer ${getToken().token}` }
  });
};
