export interface LoginType {
    username: string;
    password: string;
    verifyCodeKey: string;
    verifyCode: string;
}

export interface VerifyCode {
    key: string;
    image: string;
}

export interface RegisterType {
    username: string;
    password: string;
    repeat_password?: string;
    email: string;
    code: string;
}

export interface SubmitRegisterType {
    username: string;
    password: string;
    email: string;
    code: string;
}