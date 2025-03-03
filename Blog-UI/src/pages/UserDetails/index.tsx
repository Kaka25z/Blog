import { Avatar, Button, Card, Flex, Form, Input, message } from "antd"
import { useDispatch, useSelector } from "react-redux"
import "./index.sass"
import { useEffect, useState } from "react";
import ImageUploadAndCrop from "./cropper";
import { formatDateTime } from "../../components/formatTime";
import http from "../../apis/axios";
import { setUserDetails } from "../../store/components/userDetail";

const UserDetail = () => {

    const userDetail = useSelector((state: any) => state.userDetail);
    const [form] = Form.useForm();
    const [isButtonDisable, setButtonDisable] = useState(true);
    const [isEditing, setIsEditing] = useState(false);
    const { id, username, password, email, phone, userAvatar } = userDetail;
    const dispatch = useDispatch();

    const modifyProfile = () => {
        setButtonDisable(false);
        setIsEditing(!isEditing);
    };

    const onFinish = async (value) => {
        const updateTime = formatDateTime((new Date));
        const finalValues = { ...value, id, userAvatar, updateTime}
        const token = sessionStorage.getItem('token');
        const response = await http({
            headers: {
                'Authorization': `Bearer ${token}`
            },
            url: '/api/protected/update-userinfo',
            method: 'POST',
            data: finalValues
        })
        if (response.data.code === 200) {
            message.success(response.data.message)
        } else {
            message.error(response.data.message)
        }
        console.log(finalValues)
    }

    const resetForm = () => {
        form.setFieldsValue({
            username: '',
            password: '',
            email: '',
            phone: '',
        });
        setIsEditing(false);
        setButtonDisable(true);
    }

    const rules = {
        username: [
            {
                required: true,
                message: '用户名不能为空',
                trigger: ['blur', 'change']
            },
            {
                min: 6, max: 20, message: '用户名长度为6-20个字符'
            }
        ],
        password: [
            {
                required: true,
                message: '密码不能为空',
                trigger: ['blur', 'change']
            }
        ],
        email: [
            {
                required: true,
                message: '邮箱不能为空',
                trigger: ['blur', 'change']
            }
        ]
    }

    useEffect(() => {
        form.setFieldsValue({
            username: userDetail.username,
            password: userDetail.password,
            email: userDetail.email,
            phone: userDetail.phone
        })
    }, [])

    return (
    <div className="container-main">
        <Flex justify="space-between">
            <Flex vertical justify="flex-start">
                <Avatar src={userDetail.userAvatar} size={80} style={{marginBottom: '40px'}}/>
                <ImageUploadAndCrop></ImageUploadAndCrop>
            </Flex>
            <Flex justify="flex-start">
                <Form form={form} onFinish={onFinish} initialValues={{username, password, email, phone}}>
                    <Form.Item name='username' label='用户名' style={{marginRight: '14px'}} rules={rules.username}>
                        <Input defaultValue={form.username} placeholder="用户名" disabled={isButtonDisable}></Input>
                    </Form.Item>
                    <Form.Item name='password' label='密码' rules={rules.password}>
                        <Input defaultValue={form.password} type="password" placeholder="密码" disabled={isButtonDisable}></Input>
                    </Form.Item>
                    <Form.Item name='email' label='邮箱' rules={rules.email}>
                        <Input defaultValue={form.email} type="email" placeholder="电子邮箱" disabled={isButtonDisable}></Input>
                    </Form.Item>
                    <Form.Item name='phone' label='电话' style={{marginLeft: '11px'}}>
                        <Input defaultValue={form.phone} type="text" placeholder="联系电话" disabled={isButtonDisable}></Input>
                    </Form.Item>
                    <Form.Item>
                        <Button type="primary" onClick={modifyProfile} style={{ display: isEditing ? 'none' : 'block' }}>修改</Button>
                        <Button type="primary" htmlType="submit" style={{ display: isEditing ? 'block' : 'none' }}>提交</Button>
                        <Button onClick={resetForm} disabled={isButtonDisable} style={{marginTop: '10px'}}>重置</Button>
                    </Form.Item>
                </Form>
            </Flex>
            
        </Flex>
    </div>
    )
}

export default UserDetail