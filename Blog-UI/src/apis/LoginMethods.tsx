import { LoginType, SubmitRegisterType } from "../pages/Login/type.tsx";
import http from "./axios.tsx";

async function getVerifyCode() {
    return http({
        url: '/api/public/verifycode',
        method: 'GET'
    })
}

function getLogin(data: LoginType) {
    return http({
        url: '/api/login',
        method: 'POST',
        data: data
    })
}

function Register(data: SubmitRegisterType) {
    return http({
        url: '/api/public/register',
        method: 'POST',
        data: data
    })
}

export {getVerifyCode, getLogin, Register}