import { useSelector } from "react-redux"
import UserState from "../../../interface/UserState"
import { useParams } from "react-router-dom"
import { useEffect, useState } from "react"
import { NoteType } from "../../../interface/NoteType"
import { getNoteById } from "../../../apis/NoteMethods"
import { createRoot } from "react-dom/client"
import {PrismAsyncLight as SyntaxHighlighter} from 'react-syntax-highlighter'
import {oneDark} from 'react-syntax-highlighter/dist/esm/styles/prism'
import MarkdownNavbar from 'markdown-navbar'
import Markdown from "react-markdown"
import Loading from "../../Loading";
import remarkGfm from "remark-gfm"
import remarkToc from "remark-toc"
import scrollToTop from "../../../utils/scrollToTop"
import { motion } from "framer-motion"
import { Avatar, Divider, Flex } from "antd"
import './index.sass'
import dayjs from "dayjs";
import { Icon } from "@iconify/react/dist/iconify.js"

const ArticleIndex = () => {
    const avatar = useSelector((state:{user:UserState}) => state.user.avatar)
    const name = useSelector((state:{user:UserState}) => state.user.name)
    const {id} = useParams()
    const[isLoading, setLoading] = useState(true)
    const [article,setArticle] = useState<NoteType|null>(null)
    useEffect(() => {
        if(id){
            getNoteById(id).then((res) => {
                setArticle({
                    ...res.data.data
                });
                const contentDiv = document.getElementById('content');
                const tocDiv = document.getElementById('toc');
                if(contentDiv){
                    createRoot(contentDiv).render(<Markdown
                        children={res.data.data.noteContent}
                        remarkPlugins={[[remarkGfm, {singleTilde: false}],[remarkToc,{heading: 'structure'}]]}
                        className="markdown-body"
                        components={{
                            code(props){
                                const {children, className, node, ...rest} = props
                                const match = /language-(\w+)/.exec(className || '')
                                return match ? (
                                    //@ts-ignore
                                    <SyntaxHighlighter 
                                        {...rest}
                                        PreTag="div"
                                        children={String(children).replace(/\n$/, '')}
                                        language={match[1]}
                                        inline={true}
                                        style={oneDark}
                                    />
                                ) : (
                                    <code {...rest} style={{color: 'rgba(53,148,248)', backgroundColor: 'rgba(235,246,254,0.9)', padding: '0px 6px', borderRadius: '5px', fontFamily: 'MapleMono-NF-CN', fontWeight: '20', fontSize: '13px'}}>
                                        {children}
                                    </code>
                                )
                            }
                        }}
                    />);
                }
                if(tocDiv){
                    createRoot(tocDiv).render(<MarkdownNavbar source={res.data.data.noteContent} ordered={false} headingTopOffset={100} />);
                }
            }).catch(() => {
                console.error('获取失败')
                setLoading(false)
            });
        }
        scrollToTop()
        setLoading(false)
    }, [id]);

    return <div className="readContainer">
        {isLoading ? <div style={{width:'100vw',height:'100vh',display:'flex',justifyContent:'center',alignItems:'center'}}>
            <Loading />
        </div> : (<>
            <div className="readCover">
                <motion.img
                    src={article?.cover}
                    initial={{ filter: "blur(10px)" }}
                    animate={{ filter: "blur(0px)" }}
                    transition={{ duration: 1 }}
                />
                <motion.div
                    initial={{ opacity: 0, x: -30 }}
                    animate={{ opacity: 1, x: 0 }}
                    transition={{ duration: 1 }}
                >
                    <div className="readInfo">
                        <Flex gap={"small"} justify={"center"} align={"center"} style={{marginRight: 10}}>
                            <Avatar src={avatar} size={40} className="frontAvatar" />
                            {name}
                        </Flex>
                        <h1>{article?.noteTitle}</h1>
                        <h3>{dayjs(article?.updateTime).format("YYYY-MM-DD")}</h3>
                        <motion.div
                            initial={{ scaleX: 0 }}
                            animate={{ scaleX: 1 }}
                            transition={{ duration: 1, delay: 0.5 }}
                            style={{
                                position:'absolute',
                                bottom: -20,
                                width: "100%",
                                height: "2px",
                                background: "#aec8c8",
                                marginTop: "10px",
                                transformOrigin: "left",
                            }}
                        />
                    </div>
                </motion.div>
            </div>
            <Divider ></Divider>
            <div className='readDescription'>
                <p style={{color:'rgb(9,10,21)',fontSize:15,fontWeight:600,marginRight:10}}><Icon icon={'material-symbols:vertical-split-rounded'} />文章概述:</p>
                <p>{article?.description}</p>
            </div>
            <div className='readContent markdown-body'>
                <motion.div
                    initial={{ opacity: 0, y: 20 }}
                    animate={{ opacity: 1, y: 0 }}
                    transition={{ duration: 1 }}
                >
                    <div id="content" className='markdown-body'></div>
                </motion.div>
                <div className="navigation" id='toc'></div>
            </div></>)}
    </div>
}

export default ArticleIndex