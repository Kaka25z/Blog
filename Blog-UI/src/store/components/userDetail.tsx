import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import UserDetails from "../../interface/UserDetail";

const initialState: UserDetails = {
    email: '',
    id: null,
    password: '',
    phone: '',
    userAvatar: '',
    username: '',
    updateTime: '',
}

const UserDetailSlice = createSlice({
    name: 'userDetail',
    initialState,
    reducers: {
        setUserDetails: (state: UserDetails, action: PayloadAction<{ email: string; id: number; password: string; phone: string; userAvatar: string; username: string; updateTime: string }>) => {
            state.email = action.payload.email;
            state.id = action.payload.id;
            state.password = action.payload.password;
            state.phone = action.payload.phone;
            state.userAvatar = action.payload.userAvatar;
            state.username = action.payload.username;
            state.updateTime = action.payload.updateTime;
        },
    }
})

const { setUserDetails } = UserDetailSlice.actions;
const userDetailReducer = UserDetailSlice.reducer;

export {setUserDetails};
export default userDetailReducer;