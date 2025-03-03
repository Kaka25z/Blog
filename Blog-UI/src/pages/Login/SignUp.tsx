import { Button, Flex, Form, Input, message } from "antd";
import { RegisterType } from "./type";
import http from "../../apis/axios";
import { useEffect, useState } from "react";
import { Register } from "../../apis/LoginMethods";
import { useNavigate } from "react-router-dom";

const SignUpForm = () => {

  const [RegisterForm] = Form.useForm<RegisterType>();
  const [isCodeButtonDisabled, setCodeButtonDisabled] = useState(false);
  const [countdown, setCountdown] = useState(60);
  const navigate = useNavigate();

  const onFinish = async () => {
    const subminValues = RegisterForm.getFieldsValue();
    delete subminValues.repeat_password;
    const res = await Register(subminValues);
    if(res.data.code === 200) {
      message.success("注册成功")
      navigate('/')
    } else {
      message.error(res.data.message)
    }
  }

  const validatePassword =(_: any, value: string) => {
    if(value === '')
      return Promise.reject('请输入密码');
    else if(value !== RegisterForm.getFieldValue('password'))
      return Promise.reject('两次输入的密码不一致');
    return Promise.resolve();
  }

  const getCode = () => {
    return http({
      url: `/api/public/register-email?email=${RegisterForm.getFieldValue('email')}&type=register`,
      method: 'GET'
    })
  }

  const getCodeReslut = async () => {
    const res = await getCode();
    if(res.data.code === 400) {
      message.error(res.data.message)
    } else if(res.data.code === 200) {
      message.success('发送成功')
      setCodeButtonDisabled(true);
      let timer = setInterval(() => {
        setCountdown(prevCountdown => {
          if(prevCountdown <= 1){
            clearInterval(timer);
            setCodeButtonDisabled(false)
            return 60;
          } else {
            return prevCountdown -1;
          }
        })
      }, 1000)

    }
  }

  const rules = {
    username: [
      { required: true, message: '请输入用户名', trigger: ['blur', 'change'] },
      { min: 6, max: 20, message: '用户名长度为6-20个字符'}
    ],
    password: [
      { required: true, message: '请输入密码', trigger: ['blur', 'change'] },
      { min: 6, max: 20, message: '密码长度为6-20个字符'}
    ],
    repeat_password: [
      { validator: validatePassword },
    ],
    email: [
      { required: true, message: '请输入邮箱', trigger: ['blur', 'change'] }
    ],
    code: [
      { required: true, message: '请输入验证码', trigger: ['blur', 'change'] }
    ]
  }

  useEffect(() => {
    if (countdown === 0) {
      setCodeButtonDisabled(false);
    }
  }, [countdown]);

  return (
    <div className="form-container sign-up-container">
      <Form onFinish={onFinish} form={RegisterForm}>
        <h1 style={{fontFamily: 'MapleMono-NF-CN', marginBottom: '20px'}}>注册</h1>
        <Form.Item name="username" rules={rules.username}>
          <Input
            type="text"
            placeholder="用户名"
            className="input"
          />          
        </Form.Item>
        <Form.Item name="password" rules={rules.password}>
          <Input
            type="password"
            placeholder="请输入密码"
            className="input"
          />          
        </Form.Item>
        <Form.Item name='repeat_password' rules={rules.repeat_password}>
          <Input
            type="password"
            placeholder="请再次输入密码"
            className="input"
          />          
        </Form.Item>   
        <Form.Item name='email' rules={rules.email}>
          <Input
            type="email"
            placeholder="电子邮箱"
            className="input"
          />          
        </Form.Item>
        <Form.Item name='code' rules={rules.code}>
          <Flex justify="center" align="center" gap="middle">
            <Input
              type="text"
              name="code"
              placeholder="邮箱验证码"
              className="input"
              style={{width: "110px"}}
            />
            <Button style={{width: '100px', height: '50px'}} onClick={getCodeReslut} disabled={isCodeButtonDisabled}>
              {isCodeButtonDisabled ? `${countdown}秒后重试` : '获取验证码'}
            </Button>
          </Flex>
        </Form.Item>
        <Form.Item>
          <Button type="primary" htmlType="submit" className="btn-grad">立即注册</Button>          
        </Form.Item>
      </Form>
    </div>
  );
}

export default SignUpForm;
