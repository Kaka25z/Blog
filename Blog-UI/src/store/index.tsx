import {configureStore} from "@reduxjs/toolkit";
import userReducer from "./components/user.tsx";
import talksReducer from "./components/talks.tsx";
import categoriesReducer from "./components/categories.tsx";
import tagReducer from "./components/tags.tsx";
import NoteReducer from "./components/note.tsx";
import userDetailReducer from "./components/userDetail.tsx";

const store = configureStore({
    reducer: {
        user: userReducer,
        talks: talksReducer,
        categories: categoriesReducer,
        tags: tagReducer,
        notes: NoteReducer,
        userDetail: userDetailReducer
    }
})

export default store