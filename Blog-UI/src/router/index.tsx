import { createBrowserRouter } from "react-router-dom";
import App from "../App";
import ContentHome from "../Home/Content/ContentHome";
import ArticleIndex from "../Home/Content/Article";
import Times from "../Home/Content/Times";
import Categories from "../Home/Content/Categories";
import TalkList from "../Home/Content/Talk";
import Login from "../pages/Login";
import UserDetail from "../pages/UserDetails";

const router = createBrowserRouter([
    {
        path: "/",
        element: <App/>,
        children: [
            {
                index: true,
                element: <ContentHome />
            },
            {
                path: "/article/:id",
                element: <ArticleIndex />
            },
            {
                path: "times",
                element: <Times />
            },
            {
                path: "category/:id",
                element: <Categories />
            },
            {
                path: "talk",
                element: <TalkList />
            }
        ]
    },
    {
        path: 'login',
        element: <Login />
    },
])

export default router