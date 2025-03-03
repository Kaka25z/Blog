import './index.sass'
import {Avatar, Tag} from "antd";
import {useEffect,  useState} from "react";
import {useSelector} from "react-redux";
import UserState from "../../../interface/UserState";
import {formatNote, NoteType} from "../../../interface/NoteType";
import {categoryList} from "../../../store/components/categories.tsx";
import Article from "./Article.tsx";
import {useNavigate} from "react-router-dom";
import {getNotePage, getTopNotes} from "../../../apis/NoteMethods.tsx";
import { Icon } from '@iconify/react/dist/iconify.js';
import dayjs from "dayjs";
const ContentHome = () => {
    const [currentTop,setCurrentTop] = useState(0)
    const [currentPage,setCurrentPage] = useState(1)
    const [hasMoreArticles, setHasMoreArticles] = useState(true);
    const [loading, setLoading] = useState(false);
    const avatar = useSelector((state:{user:UserState}) => state.user.avatar)
    const name = useSelector((state:{user:UserState}) => state.user.name)
    const oneSay = useSelector((state:{user:UserState}) => state.user.talk)
    const navigate = useNavigate()
    const [otherArticles,setOtherArticles] = useState<NoteType[]>([])
    const [topArticles,setTopArticles] = useState<NoteType[]>([])
    const Categories = useSelector((state: { categories: categoryList }) => state.categories.categories);
    const tagList = useSelector((state: {tags: any}) => state.tags.tag)
    const author =  useSelector((state: { user: UserState }) => state.user.name);

    useEffect(() => {
        const timer = setInterval(() => {
            setCurrentTop(prevTop => (prevTop + 1) % topArticles.length);
        }, 3000);
        if(topArticles.length === 0)
            clearInterval(timer)
        return () => clearInterval(timer);
    },[currentTop])

    useEffect(() => {
        getNotePage({
            page: 1,
            pageSize: 6
        }).then(res => {
            setOtherArticles(res.data.data.map((item: formatNote) => {
                return {
                    ...item,
                    key: item.noteKey,
                    noteTags: item.noteTags ? item.noteTags.split(',').map(tag => parseInt(tag, 10)) : [],
                }
            }))
        })
    }, []);

    useEffect(() => {
        getTopNotes().then(res => {
            setTopArticles(res.data.data.map((item: formatNote) => {
                return {
                    ...item,
                    key: item.noteKey,
                    noteTags: item.noteTags ? item.noteTags.split(',').map(tag => parseInt(tag, 10)) : [],
                }
            }))
        })
    }, []);
    const handleScrollDown = () => {
        window.scrollTo({
            top: window.innerHeight,
            behavior: 'smooth'
        });
    }
    const getMore = () => {
        setLoading(true)
        getNotePage({
            page: currentPage + 1,
            pageSize: 6
        }).then(res => {
            if (res.data.data.length === 0) {
                setHasMoreArticles(false);
            } else {
                setCurrentPage(currentPage + 1);
                setOtherArticles(prevArticles => [
                    ...prevArticles,
                    ...res.data.data.map((item: formatNote) => ({
                        ...item,
                        key: item.noteKey,
                        noteTags: item.noteTags ? item.noteTags.split(',').map(tag => parseInt(tag, 10)) : [],
                    }))
                ]);
                if(res.data.data.length < 6)
                    setHasMoreArticles(false)
            }
        }).finally(() => {
            setLoading(false);
        });
    };

    return <>
        <div className="SelfDescription">
            <div className="SayWords">
               <div>
                   <h2>Hi!👋</h2>
                   <h2>欢迎来到我的博客</h2>
               </div>
            </div>
        </div>
        <div className="ContentContainer dark-pic">
            {topArticles.length>0&&<div className="TopArticle" onClick={() => navigate(`/article/${topArticles[currentTop]?.key}`)}>
                <div className="Top"><Icon icon={'akar-icons:align-to-top'} height="18" />置顶</div>
                <div className="TopCover">
                    {topArticles.map((item,index) => (
                        <img
                            src={item.cover}
                            key={item.key}
                            className={currentTop === index ? 'fade-in-out show' : 'fade-in-out'}
                        />
                    ))}

                    <span className="thumbnail-screen"></span>
                    <div className="topDots">
                        {topArticles.map((item,index) => <div className={`topDot ${currentTop===index&&'dotCurrent'}`} key={item.key} onMouseEnter={()=>setCurrentTop(index)}></div>)}
                    </div>
                </div>
                <div className="topContent">
                    <h4># {Categories.find(item => item.categoryKey === topArticles[currentTop]?.noteCategory)?.categoryTitle}</h4>
                    <h3 className="contentTitle">{topArticles[currentTop]?.noteTitle}</h3>
                    <p> {topArticles[currentTop]?.description}</p>
                    <div className='tags' style={{ width: '100%', marginTop: '10px' }}>
                        {topArticles[currentTop]?.noteTags.map(noteTag => {
                            let color;
                            let name;
                            tagList.forEach((tag: { tagKey: number; color: string; title: string; children: any[]; }) => {
                                if (tag.tagKey === noteTag) {
                                    color = tag.color;
                                    name = tag.title;
                                } else if (tag.children && tag.children.some(child => child.tagKey === noteTag)) {
                                    color = tag.color;
                                    name = tag.children.find(child => child.tagKey === noteTag).title;
                                }
                            });

                            return (
                                <Tag color={color} key={noteTag} style={{ margin: 5 }}>
                                    {name}
                                </Tag>
                            );
                        })}
                    </div>
                    <div className="topFooter">
                        <Avatar src={avatar} size={40} style={{marginRight:10}}/>
                        <span style={{marginRight:10}}>{name}</span>
                        <span style={{fontSize:13,color:'#7f7e7e'}} className='post-date'><i className="iconfont icon-naozhong icon" style={{fontSize: 22, display: 'inline',verticalAlign: 'sub'}}></i>发布于 {dayjs(topArticles[currentTop].createTime).format("YYYY-MM-DD")}</span>
                    </div>
                </div>
            </div>}


        {/*  其他文章  */}
            <div style={{width:'78%'}}>
                <div className='allContent'><Icon icon={'mage:checklist-note'} height="20" />文章</div>
            </div>

            <div className="allArticles">

                {otherArticles.map((item,index) => (
                    <Article item={item} index={index} Categories={Categories} avatar={avatar} name={name} tagList={tagList} key={index}/>
                ))}
            </div>
            {loading ? (
                <div className="loadingio-spinner-spinner-69tfms83mg9">
                    <div className="ldio-se504dvlmh">
                        <div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div>
                    </div>
                </div>
            ) : (
                <div>
                    {hasMoreArticles ? (
                        <div className='allContent more' style={{ padding: '20px 50px 20px 50px', borderRadius: 20, fontSize: 20 }} onClick={getMore}>More</div>
                    ) : null}
                </div>
            )}
        </div>
    </>
}

export default ContentHome