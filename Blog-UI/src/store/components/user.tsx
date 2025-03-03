import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import http from '../../apis/axios.tsx';
import { Dispatch } from 'react';
import UserState from "../../interface/UserState";
import UserData from "../../interface/UserData";
import deleteToken from "../../apis/deleteToken.tsx";

const initialState: UserState = {
    token: localStorage.getItem('tokenKey') || null,
    avatar: '',
    talk: '',
    name: '',
};

const userSlice = createSlice({
    name: 'user',
    initialState,
    reducers: {
        setToken: (state: UserState, action: PayloadAction<{ token: string }>) => {
            state.token = action.payload.token;
            localStorage.setItem('token', action.payload.token);
        },
        setUserInfo: (state: UserState,action: PayloadAction<{avatar:string,talk:string,name:string}>) => {
            state.avatar = action.payload.avatar;
            state.talk = action.payload.talk;
            state.name = action.payload.name;
        },
    },
});

const { setToken,setUserInfo } = userSlice.actions;
const userReducer = userSlice.reducer;

const fetchToken = (data: UserData) => {
    return async (dispatch: Dispatch<PayloadAction<{ token: string }>>) => {
        try {
            const res = await http({
                url: '/api/login',
                method: 'POST',
                data: data
            });
                const token = res.data.data.token;
                console.log(token)
                dispatch(setToken({ token: token}));
            return res.data.data.code;
        } catch (error) {
            throw error;
        }
    };
};

const fetchUserInfo = () => {
    return async (dispatch: Dispatch<PayloadAction<{avatar:string,talk:string}>>) => {
        try{
            const userinfo = await http({
                url: '/api/public/userinfo?id=2',
                method: "GET"
            })
            const res = {
                avatar: userinfo.data.data.userAvatar,
                talk: userinfo.data.data.userTalk,
                name: userinfo.data.data.username
            }
            dispatch(setUserInfo(res))
        }catch (error){
            deleteToken()
        }
    }
}

export { setToken, fetchToken,fetchUserInfo };
export default userReducer;
