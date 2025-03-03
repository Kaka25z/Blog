import ReactDOM from 'react-dom/client'
import {Provider} from "react-redux";
import './index.css'
import { RouterProvider } from 'react-router-dom';
import router from './router/index.tsx';
import store from './store/index.tsx';

ReactDOM.createRoot(document.getElementById('root')!).render(
  <Provider store={store}>
    <RouterProvider router={router}>
    </RouterProvider>
  </Provider>,
)
