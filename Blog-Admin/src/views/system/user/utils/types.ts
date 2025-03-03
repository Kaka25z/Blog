interface UserInfoResult {
  code: number;
  data: {
    id: number;
    username: string;
    password: string;
    phone: string;
    email: string;
    userAvatar: string;
    role: string;
    createTime: string;
    updateTime: string;
  };
  message: string;
}

interface UpdateUser {
  id: number;
  username: string;
  password: string;
  phone: string;
  email: string;
  userAvatar: string;
  role: string;
  createTime: string;
  updateTime: string;
}

interface DeleteUser {
  id: number;
}


