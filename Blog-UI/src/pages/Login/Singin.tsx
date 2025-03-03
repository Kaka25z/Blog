import { Button, Form, FormProps, Input, message } from "antd";
import { useEffect, useState } from "react";
import { LoginType, VerifyCode } from "./type";
import { getLogin, getVerifyCode } from "../../apis/LoginMethods";
import { useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import http from "../../apis/axios";
import { setUserDetails } from "../../store/components/userDetail";
import UserDetails from "../../interface/UserDetail";

const SignInForm = () => {
  const[login, setLogin] = useState<LoginType>()
  const[verifyCode, setVerifyCode] = useState<VerifyCode>()
  const [loginClicked, setLoginClicked] = useState(false);
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const userDetails = useSelector((state: { userDetail: UserDetails }) => state.userDetail);


  async function getCode() {
    const res = await getVerifyCode()
    setVerifyCode({
      key: res.data.key,
      image: res.data.image
    })
  }

  useEffect(() => {
    getCode()
  },[])

  const onFinish: FormProps<LoginType>["onFinish"] = async (values) => {
    setLogin({...values, verifyCodeKey: verifyCode?.key || ''})
  }

  useEffect(() => {
    if (loginClicked) {
      const fetchLogin = async () => {
        const res = await getLogin(login!);
        if (res.data.code === 200) {
          sessionStorage.setItem('token', res.data.data.token);
          message.success('登录成功');
          if (res.data.data.id) {
            const fetchUserDetails = async () => {
              const response = await http({
                url: `/api/public/userinfo?id=${res.data.data.id}`,
                method: 'GET',
              });
              sessionStorage.setItem('userId', res.data.data.id)
              await dispatch(setUserDetails({
                email: response.data.data.email,
                id: response.data.data.id,
                phone: response.data.data.phone,
                password: '',
                userAvatar: response.data.data.userAavatar,
                username: response.data.data.username,
                updateTime: '',
              }))
            };
            await fetchUserDetails();
          }
          navigate('/');
        } else {
          message.error(res.data.message)
        }
      };
      fetchLogin();
    }
  }, [loginClicked]);

  const handleValuesChange = (changedValues: any, allValues: LoginType) => {
    setLogin({ ...allValues, verifyCodeKey: verifyCode?.key || '' });
  };

  return (
    <div className="form-container sign-in-container">
      <Form
        name="login"
        onFinish={onFinish}
        onValuesChange={handleValuesChange}
      >
        <h1 style={{fontFamily: 'MapleMono-NF-CN', marginBottom: '20px'}}>登录</h1>
        <Form.Item<LoginType>
          name="username"
          >
          <Input
          placeholder="请输入用户名"
          className="input"
          type="text"
        /> 
        </Form.Item>
        <Form.Item<LoginType>
          name="password"  
        >
        <Input
          placeholder="请输入密码"
          className="input"
          type="password"
        />          
        </Form.Item>
        <Form.Item<LoginType>  
          name="verifyCode"
        >
          <div style={{display: 'flex', alignItems: 'center'}}>
            <Input
              placeholder="请输入验证码"
              style={{height: '50px', width: '110px', fontFamily: 'MapleMono-NF-CN'}}
              type="text"
            />
            <img src={verifyCode?.image} alt="" onClick={getCode} style={{height: '50px', width: '120px'}} />            
          </div>

        </Form.Item>
        <Form.Item>
          <Button type="primary" htmlType="submit" className="btn-grad" onClick={() => setLoginClicked(true)}>立即登录</Button>          
        </Form.Item>

      </Form>
    </div>
  );
}

export default SignInForm;
