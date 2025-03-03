import {Avatar, Button, Card, ConfigProvider, Flex, Modal, message} from 'antd'
import './index.sass'
import {Key, ReactElement, ReactNode, ReactPortal, useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import {debounce, set} from 'lodash';
import Switch from "../../components/Switch";
import SearchButton2 from "../../components/Buttons/SearchButton2";
import TopMao from "../../components/TopMao";
import {fetchCategories} from "../../store/components/categories.tsx";
import {useDispatch, useSelector} from "react-redux";
import {fetchTags} from "../../store/components/tags.tsx";
import { fetchUserInfo} from "../../store/components/user.tsx";
import {fetchNoteList} from "../../store/components/note.tsx";
import '../main.css'
import MoonToSun from "../MoonToSun";
import { Icon } from '@iconify/react/dist/iconify.js';
import { searchNotes } from '../../apis/NoteMethods.tsx';
import Meta from 'antd/es/card/Meta';
import { setUserDetails } from "../../store/components/userDetail";
import UserDetail from '../../pages/UserDetails/index.tsx';
import http from '../../apis/axios.tsx';
interface HeadProps {
    setDark: (value: (((prevState: boolean) => boolean) | boolean)) => void,
    isDark: boolean,
    scrollHeight: number
}

const Head = ({ setDark, isDark, scrollHeight }: HeadProps) => {
    const [showStatus, setShowStatus] = useState(false);
    const [phoneBarShow, setPhoneBarShow] = useState(false);
    const [isHovered, setIsHovered] = useState(false);
    const [isLogin, setLogin] = useState(0)
    const dispatch = useDispatch()
    const navigate = useNavigate();
    const [animation,setAnimation] = useState('');
    const categoryList = useSelector((state: any) => state.categories.categories)
    const userDetail = useSelector((state: any) => state.userDetail);
    const blogTitle = useSelector((state:{user:{blogTitle: string}}) => state.user.blogTitle);

    useEffect(() => {
        dispatch<any>(fetchCategories())
        dispatch<any>(fetchTags())
        dispatch<any>(fetchUserInfo())
        dispatch<any>(fetchNoteList())
        const status = sessionStorage.getItem('token')
        if (status !== null) {
            setLogin(1)
        }

    }, []);

    useEffect(() => {
        const userId = sessionStorage.getItem('userId');
        if (userId) {
          const fetchUserDetails = async () => {
            const response = await http({
              url: `/api/public/userinfo?id=${userId}`,
              method: 'GET',
            });
            dispatch(setUserDetails({
              email: response.data.data.email,
              id: response.data.data.id,
              phone: response.data.data.phone,
              password: '',
              userAvatar: response.data.data.userAvatar,
              username: response.data.data.username,
              updateTime: ''
            }))
          };
        fetchUserDetails();
        }
    }, []);

    // 定义防抖函数，设置延迟时间为 300 毫秒
    const startAnimationDebounced = debounce(() => {
        setShowStatus(true);
    }, 300);

    const handleMouseEnter = () => {
        startAnimationDebounced();
        setIsHovered(true);
    };

    const handleMouseLeave = () => {
        setShowStatus(false);
        setIsHovered(false);
    };

    const [isModalOpen, setIsModalOpen] = useState(false);

    const showModal = () => {
        setIsModalOpen(true);
    };


    const handleCancel = () => {
        setIsModalOpen(false);
        setSearchResults([])
    };

    const handleModeSwitch = () => {
        setDark(!isDark)
        setAnimation(isDark === true ? "sun" : "moon");
        localStorage.setItem("isDarkMode", JSON.stringify(!isDark));
    };

    const [isUserInfoModalOpen, setIsUserInfoModalOpen] = useState(false);

    const handleUserInfoClick = () => {
        setIsUserInfoModalOpen(true);
    };

    const handleUserInfoModalCancel = () => {
        setIsUserInfoModalOpen(false);
    };


    const [searchValue, setSearchValue] = useState("");

    const [searchResults, setSearchResults] = useState<any[]>([]);

    const handleKeyDown = async (event: React.KeyboardEvent) => {
        if (event.key === 'Enter') {
            const result = await searchNotes({ 
                title: searchValue,
                top: 0,
            });

            console.log(result.data.data)

            setSearchResults(result.data.data)
        }
    };

    const LogOut = async () => {
        const respone = await http({
            headers: {
                'Authorization': 'Bearer ' + sessionStorage.getItem('token')
            },
            url: '/api/protected/logout',
            method: 'POST',
        })
        if (respone.data.code === 200) {
            message.success('退出成功');
            dispatch(setUserDetails({
                email: '',
                id: 0,
                phone: '',
                password: '',
                userAvatar: '',
                username: '',
                updateTime: ''
            }))
            sessionStorage.removeItem('token')
            sessionStorage.removeItem('userId')
            setLogin(0)
            navigate('/')            
        } else {
            message.error(respone.data.message)
        }
    }
    return (
        <header style={{display: 'flex', flexDirection: 'row', position: 'sticky', width: '100%', top: 0, zIndex: '999'}} className={isDark ? 'frontDark' : ''}>
            <div className={`${phoneBarShow ? 'openBar' : ''} phoneSide`} style={{position: "sticky"}}>
                <div className="phoneBarContainer">
                    <div className="barLogo">
                    </div>
                    <input className="mSearchInput" type="search" placeholder="搜索..."/>
                    <div className="barContent">
                        <ul className='oneBar'>
                            <li onClick={() => navigate('')}><Icon icon={'logos:google-home'}></Icon>首页
                            </li>
                            <li onClick={() => navigate('times')}><Icon icon={'flat-color-icons:timeline'} />时间线
                            </li>
                            <li>
                                <div style={{height: 30}}><Icon icon={'flat-color-icons:generic-sorting-desc'} height="25"></Icon>分类</div>
                            </li>
                            <ul className='twoBar'>
                                {categoryList.map((item: { categoryKey: Key | null | undefined; pathName: any; icon: any; categoryTitle: string | number | boolean | ReactElement | Iterable<ReactNode> | ReactPortal | null | undefined; }) =>
                                    <li key={item.categoryKey} onClick={() => navigate(`category/${item.pathName}`)} style={{fontSize: 15}}><i className={`fa ${item.icon}`} aria-hidden="true" style={{verticalAlign: 'middle'}}></i>{item.categoryTitle}</li>
                                )}
                            </ul>
                            <li onClick={() => navigate('talk')}><Icon icon={'icon-park:messages'} height='30'></Icon>说说
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <TopMao currentScrollHeight={scrollHeight}/>
            <div className="headContainer" style={{
                margin: scrollHeight ? 0 : '',
                borderRadius: scrollHeight ? 0 : '',
                background: scrollHeight ? 'rgba(0,0,0,0.66)' : '',
                width: scrollHeight ? '100%' : '',
                backdropFilter: scrollHeight ? 'blur(10px)' : ''
            }}>
                <div className="phoneBar">
                    {phoneBarShow ? <i className="iconfont icon-guanbi2" style={{
                            fontSize: 35,
                            marginLeft: 260,
                            cursor: 'pointer',
                            transition: '0.5s'
                        }} onClick={() => setPhoneBarShow(false)}></i> :
                        <i className="iconfont icon-bars"
                           style={{fontSize: 35, marginLeft: 10, cursor: 'pointer', transition: '0.5s'}}
                           onClick={() => setPhoneBarShow(true)}></i>}
                </div>
                <div className="webTitle" onClick={()=>navigate('/')}>
                    <h2><span className="firstTitle">{blogTitle}</span>Blog</h2>
                </div>
                <div className="headBar">
                    <ul>
                        <li onClick={() => navigate('/')}><Icon icon={'logos:google-home'} height="20"></Icon>首页
                        </li>
                        <li onClick={() => navigate('times')}><Icon icon={'flat-color-icons:timeline'} height="23" />时间线
                        </li>
                        <li style={{position: 'relative'}} className='Category'><Icon icon={'flat-color-icons:generic-sorting-desc'} height="25"></Icon>分类
                            <div className='CategoryList'>
                                <i className="iconfont icon-Rrl_s_045" style={{
                                    fontSize: 40,
                                    position: 'absolute',
                                    left: 25,
                                    top: -29,
                                    color: 'rgba(0, 0, 0, 0.83)'
                                }}></i>
                                <ul>
                                    {categoryList.map((item: { categoryKey: Key | null | undefined; pathName: any; icon: any; categoryTitle: string | number | boolean | ReactElement | Iterable<ReactNode> | ReactPortal | null | undefined; }) =>
                                        <li key={item.categoryKey} onClick={() => navigate(`category/${item.pathName}`)}><i className={`iconfont ${item.icon}`} style={{fontSize: 20}}></i>{item.categoryTitle}</li>
                                    )}
                                </ul>
                            </div>
                        </li>
                        <li onClick={()=>navigate('talk')}><Icon icon={'icon-park:messages'} height='30'></Icon>说说</li>
                    </ul>
                </div>

                <div className="homeRight">
                    <div onClick={showModal}><SearchButton2 /></div>
                    <div className={'homeSwitch'}><Switch handleModeSwitch={handleModeSwitch} isDarkMode={isDark}/></div>
                    <div className={`homeLogo ${isHovered&&'BigAvatar'}`} onMouseEnter={handleMouseEnter} onMouseLeave={handleMouseLeave}>
                        {userDetail.userAvatar
                            ? <Avatar src={userDetail.userAvatar} size={45}></Avatar>
                            : <Icon icon={'logos:gravatar-icon'} height='35' />}
                        <div className="loginCard" style={{display: (showStatus && isHovered) ? 'flex' : 'none'}}>
                            <Flex vertical justify='center'>
                                {isLogin?<Button type={"primary"} style={{width:50,height:20,marginTop:20,fontSize:'10px',padding:"0 0 0" +
                                        " 2px"}} onClick={handleUserInfoClick}>个人信息</Button>:<Button type={"primary"} style={{width:50,height:20,fontSize:'10px',padding:"0 0 0 2px"}} onClick={()=>navigate('login')}>登录</Button>}
                                {Boolean(isLogin) && (<Button danger type={"primary"} style={{width:50,height:20,marginTop:5,fontSize:'10px',padding:"0 0 0 2px"}} onClick={LogOut}>退出登录</Button>)}                                
                            </Flex>
                        </div>
                    </div>
                </div>
            </div>

            <ConfigProvider
                theme={{
                    token: {
                        boxShadow: 'none'
                    },
                    components: {
                        Modal: {
                            contentBg: 'transparent'
                        },
                    },
                }}
            >
                <Modal open={isModalOpen} onCancel={handleCancel} footer={null} width={'100vh'} >
                    <div style={{height:'80vh'}} className='searchModal'>
                        <input type="search" className='searchModalInput' placeholder={'输入你想搜索的内容吧 ...'} value={searchValue} onChange={e => setSearchValue(e.target.value)} onKeyDown={handleKeyDown} />
                        <div style={{width:'80%',height: '90%',overflowY:'auto'}}>
                        {searchResults.map(result => (
                            <Card hoverable cover={<img src={result.cover} />} style={{margin: '20px'}} key={result.noteKey} onClick={() => navigate(`/article/${result.noteKey}`)}>
                                <Meta title={result.noteTitle} description={result.description} />
                            </Card>
                        ))}
                        </div>
                    </div>
                </Modal>
            </ConfigProvider>
            {animation !== '' && <MoonToSun status={animation} />}

            <Modal open={isUserInfoModalOpen} onCancel={handleUserInfoModalCancel} width={'450px'} footer={null} >
                <div style={{height: '350px'}}>
                    <UserDetail />    
                </div>
                
            </Modal>
        </header>
    );
};

export default Head;
