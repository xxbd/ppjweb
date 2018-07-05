package utils;

import Dao.AccessToken;
import Dao.response.ImageMessage;
import Dao.request.VideoMessage;
import Dao.request.VoiceMessage;
import Dao.response.*;
import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import net.sf.json.JSONObject;
import org.dom4j.Document;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;

public class MessageUtils {

    // 请求消息类型：文本
    public static final String REQ_MESSAGE_TYPE_TEXT = "text";
    // 请求消息类型：图片
    public static final String REQ_MESSAGE_TYPE_IMAGE = "image";
    // 请求消息类型：语音
    public static final String REQ_MESSAGE_TYPE_VOICE = "voice";
    // 请求消息类型：视频
    public static final String REQ_MESSAGE_TYPE_VIDEO = "video";
    // 请求消息类型：小视频
    public static final String REQ_MESSAGE_TYPE_SHORTVIDEO = "shortvideo";
    // 请求消息类型：地理位置
    public static final String REQ_MESSAGE_TYPE_LOCATION = "location";
    // 请求消息类型：链接
    public static final String REQ_MESSAGE_TYPE_LINK = "link";

    // 请求消息类型：事件推送
    public static final String REQ_MESSAGE_TYPE_EVENT = "event";

    // 事件类型：subscribe(订阅)
    public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";
    // 事件类型：unsubscribe(取消订阅)
    public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
    // 事件类型：scan(用户已关注时的扫描带参数二维码)
    public static final String EVENT_TYPE_SCAN = "scan";
    //事件类型：扫描二维码
    public static final String EVENT_TYPE_SCANCODE_PUSH = "scancode_push";
    // 事件类型：LOCATION(上报地理位置)
    public static final String EVENT_TYPE_LOCATION = "location_select";
    // 事件类型：CLICK(自定义菜单)
    public static final String EVENT_TYPE_CLICK = "CLICK";

    // 响应消息类型：文本
    public static final String RESP_MESSAGE_TYPE_TEXT = "text";
    // 响应消息类型：图片
    public static final String RESP_MESSAGE_TYPE_IMAGE = "image";
    // 响应消息类型：语音
    public static final String RESP_MESSAGE_TYPE_VOICE = "voice";
    // 响应消息类型：视频
    public static final String RESP_MESSAGE_TYPE_VIDEO = "video";
    // 响应消息类型：音乐
    public static final String RESP_MESSAGE_TYPE_MUSIC = "music";
    // 响应消息类型：图文
    public static final String RESP_MESSAGE_TYPE_NEWS = "news";

    /**
     * 解析微信发来的请求（XML）
     *
     * @param request
     * @return Map<String ,   String>
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
        // 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<String, String>();

        // 从request中取得输入流
        InputStream inputStream = request.getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        StringBuffer buf = new StringBuffer();
        while ((line = reader.readLine()) != null) {
            buf.append(line);
        }
        reader.close();
        inputStream.close();

        WXBizMsgCrypt wxCeypt = MessageUtils.getWxCrypt();
        // 微信加密签名
        String msgSignature = request.getParameter("msg_signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");

        String respXml = wxCeypt.decryptMsg(msgSignature, timestamp, nonce, buf.toString());

        //SAXReader reader = new SAXReader();
        Document document = DocumentHelper.parseText(respXml);
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        List<Element> elementList = root.elements();

        // 遍历所有子节点
        for (Element e : elementList)
            map.put(e.getName(), e.getText());

        // 释放资源
        //inputStream.close();
        //inputStream = null;

        return map;
    }

    /**
     * 根据消息类型构造返回消息
     *
     * @param map 封装了解析结果的Map
     * @return responseMessage(响应消息)
     */
    public static String buildResponseMessage(Map map) {
        //响应消息
        String responseMessage = "";
        //得到消息类型
        String msgType = map.get("MsgType").toString();
        //得到发送者与接受者
        String ToUserName = map.get("ToUserName").toString();
        String FromUserName = map.get("FromUserName").toString();

        System.out.println("MsgType:" + msgType);
        //消息类型
        //如果是文本消息
        if ("text".equals(msgType)) {
            String Content = map.get("Content").toString();
            if ("1".equals(Content)) {
                responseMessage = initImage(ToUserName, FromUserName);
            }else if("2".equals(Content)){
                 responseMessage = initMusicMessage(ToUserName,FromUserName);

            }else if("3".equals(Content)){
                responseMessage = initMusicMessageLove(ToUserName,FromUserName);

            }  else if("4".equals(Content)){
                AccessToken token = WeiXingUtils.getToken();
                String t = token.getAccess_token();
                JSONObject user = WeiXingUtils.getUser(t, FromUserName);
                String nick = user.getString("nickname");

                responseMessage = initText(ToUserName,FromUserName,"您的昵称是"+nick);

            } else{
                responseMessage = initMessage(ToUserName, FromUserName);
            }
            return responseMessage;
        }
        //如果是事件消息
        else if (REQ_MESSAGE_TYPE_EVENT.equals(msgType)) {
            String eventType = map.get("Event").toString();
            //订阅事件
            if (EVENT_TYPE_SUBSCRIBE.equals(eventType)) {
                responseMessage = initText(ToUserName, FromUserName, "谢谢关注哈！来听两首曲子散散心吧！2.see you agin 3. love story ");
                return responseMessage;
                //扫描二维码
            } else if (EVENT_TYPE_SCANCODE_PUSH.equals(eventType)) {
                //获取key值
                String eventKey = map.get("EventKey").toString();
                responseMessage = initText(ToUserName, FromUserName, eventKey);
                return responseMessage;
                // 事件类型：CLICK(自定义菜单)
            } else if (EVENT_TYPE_CLICK.equals(eventType)) {
                //获取key值
                String eventKey = map.get("EventKey").toString();
                if ("click_34".equals(eventKey)) {
                    responseMessage = initText(ToUserName, FromUserName, "谢谢老板！！！");
                } else if ("picture_21".equals(eventKey)) {
                    responseMessage =initImage(ToUserName, FromUserName);;
                }else if ("article_11".equals(eventKey)) {
                    responseMessage = initText(ToUserName, FromUserName, "还没写！！！");
                }
                return responseMessage;
                // 事件类型：LOCATION(上报地理位置)
            } else if (EVENT_TYPE_SCANCODE_PUSH.equals(eventType)) {
                //获取key值
                String eventKey = map.get("EventKey").toString();
                responseMessage = initText(ToUserName, FromUserName, eventKey);
                return responseMessage;
            } else {
                return responseMessage;
            }
            //地理位置消息
        } else if ("location".equals(msgType)) {
            String label = map.get("Label").toString();
            responseMessage = initText(ToUserName, FromUserName, label);
            return responseMessage;
        } else if (REQ_MESSAGE_TYPE_IMAGE.equals(msgType)) {
            responseMessage = "您发送的是图片消息";
            //返回响应消息
            return responseMessage;
        } else if (REQ_MESSAGE_TYPE_VOICE.equals(msgType)) {
            responseMessage = "您发送的是语音消息";
            //返回响应消息
            return responseMessage;
        } else if (REQ_MESSAGE_TYPE_VIDEO.equals(msgType)) {
            responseMessage = "您发送的是视频消息";
            //返回响应消息
            return responseMessage;
        } else if (REQ_MESSAGE_TYPE_LINK.equals(msgType)) {
            responseMessage = "您发送的是链接消息";
            //返回响应消息
            return responseMessage;
        } else if (REQ_MESSAGE_TYPE_SHORTVIDEO.equals(msgType)) {
            responseMessage = "您发送的是小视频消息";
            //返回响应消息
            return responseMessage;
        }
        return responseMessage;
    }

    /**
     * 2      * 解析微信发来的明文请求（XML）
     * 3      *
     * 4      * @param request 封装了请求信息的HttpServletRequest对象
     * 5      * @return map 解析结果
     * 6      * @throws Exception
     * 7
     */
    public static Map<String, String> parseXmlww(HttpServletRequest request) throws Exception {
        // 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<String, String>();
        // 从request中取得输入流
        InputStream inputStream = request.getInputStream();
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        List<Element> elementList = root.elements();

        // 遍历所有子节点
        for (Element e : elementList) {
            System.out.println(e.getName() + "|" + e.getText());
            map.put(e.getName(), e.getText());
        }

        // 释放资源
        inputStream.close();
        inputStream = null;
        return map;
    }

    /**
     * 扩展xstream使其支持CDATA
     */
    private static XStream xstream = new XStream(new XppDriver() {
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                // 对所有xml节点的转换都增加CDATA标记
                boolean cdata = true;

                @SuppressWarnings("unchecked")
                public void startNode(String name, Class clazz) {
                    super.startNode(name, clazz);
                }

                protected void writeText(QuickWriter writer, String text) {
                    if (cdata) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    } else {
                        writer.write(text);
                    }
                }
            };
        }
    });

    /**
     * 文本消息对象转换成xml
     *
     * @param textMessage 文本消息对象
     * @return xml
     */
    public static String messageToXml(TextMessage textMessage) {
        xstream.alias("xml", textMessage.getClass());
        return xstream.toXML(textMessage);
    }

    /**
     * 图片消息对象转换成xml
     *
     * @param imageMessage 图片消息对象
     * @return xml
     */
    public static String messageToXml(ImageMessage imageMessage) {
        xstream.alias("xml", imageMessage.getClass());
        return xstream.toXML(imageMessage);
    }

    /**
     * 语音消息对象转换成xml
     *
     * @param voiceMessage 语音消息对象
     * @return xml
     */
    public static String messageToXml(VoiceMessage voiceMessage) {
        xstream.alias("xml", voiceMessage.getClass());
        return xstream.toXML(voiceMessage);
    }

    /**
     * 视频消息对象转换成xml
     *
     * @param videoMessage 视频消息对象
     * @return xml
     */
    public static String messageToXml(VideoMessage videoMessage) {
        xstream.alias("xml", videoMessage.getClass());
        return xstream.toXML(videoMessage);
    }

    /**
     * 音乐消息对象转换成xml
     *
     * @param musicMessage 音乐消息对象
     * @return xml
     */
    public static String messageToXml(MusicMessage musicMessage) {
        xstream.alias("xml", musicMessage.getClass());
        return xstream.toXML(musicMessage);
    }

    /**
     * 图文消息对象转换成xml
     *
     * @param newsMessage 图文消息对象
     * @return xml
     */
    public static String messageToXml(RArticleMessage newsMessage) {
        xstream.alias("xml", newsMessage.getClass());
        xstream.alias("item", new Article().getClass());
        return xstream.toXML(newsMessage);
    }

    /**
     * @Author WuJian
     * @Description 生成图文消息
     * @Param
     * @Date 15:57 2018/3/14
     * @Return String
     **/
    public static String initMessage(String ToUserName, String FromUserName) {
        RArticleMessage rm = new RArticleMessage();
        List<Article> listarticle = new ArrayList<Article>();
        Article article = new Article();
        article.setTitle("有朋自远方来，不亦乐乎！");
        article.setDescription("初次见面，幸会，幸会");
        article.setPicUrl("http://6qb5bp.natappfree.cc/ppjweb/image/lovee.jpg");
        article.setUrl("www.baidu.com");
        listarticle.add(article);
        rm.setArticleCount(listarticle.size());
        rm.setArticles(listarticle);
        rm.setFromUserName(ToUserName);
        rm.setToUserName(FromUserName);
        rm.setCreateTime(new Date().getTime());
        rm.setMsgType(RESP_MESSAGE_TYPE_NEWS);
        return messageToXml(rm);

    }

    /**
     * @Author WuJian
     * @Description 生成图片消息
     * @Param
     * @Date 9:07 2018/3/20
     * @Return
     **/
    public static String initImage(String ToUserName, String FromUserName) {

        Image image = new Image();
        image.setMediaId("I1tUQ4rc3I2D-scTxhsXFL8R3Qj6IfXeAVOJV97cySg-ATnvtAGduxaHB9XCCbd6");
        ImageMessage rImageMessage = new ImageMessage();
        rImageMessage.setCreateTime(new Date().getTime());
        rImageMessage.setFromUserName(ToUserName);
        rImageMessage.setToUserName(FromUserName);
        rImageMessage.setMsgType(RESP_MESSAGE_TYPE_IMAGE);
        rImageMessage.setImage(image);

        return messageToXml(rImageMessage);

    }

    /**
     * @Author WuJian
     * @Description 生成文本消息
     * @Param
     * @Date 9:10 2018/3/20
     * @Return
     **/
    public static String initText(String ToUserName, String FromUserName, String content) {
        TextMessage textMessage = new TextMessage();
        textMessage.setFromUserName(ToUserName);
        textMessage.setToUserName(FromUserName);
        textMessage.setContent(content);
        textMessage.setMsgType(RESP_MESSAGE_TYPE_TEXT);
        textMessage.setCreateTime(new Date().getTime() + "");
        return messageToXml(textMessage);
    }

    public static String initMusicMessage(String ToUserName, String FromUserName) {
        Music music =new Music();
        music.setTitle("see you again");
        music.setDescription("速度与激情");
        music.setMusicUrl("http://6qb5bp.natappfree.cc/ppjweb/music/11.mp3");
        music.setHQMusicUrl("http://6qb5bp.natappfree.cc/ppjweb/music/11.mp3");
        music.setThumbMediaId("I1tUQ4rc3I2D-scTxhsXFL8R3Qj6IfXeAVOJV97cySg-ATnvtAGduxaHB9XCCbd6");
        MusicMessage musicMessage = new MusicMessage();
        musicMessage.setMusic(music);
        musicMessage.setCreateTime(new Date().getTime());
        musicMessage.setFromUserName(ToUserName);
        musicMessage.setToUserName(FromUserName);
        musicMessage.setMsgType("music");
        return messageToXml(musicMessage);

    }
    /**
    * @Author WuJian
    * @Description love story音乐
    * @Param
    * @Date 11:18 2018/3/20
    * @Return
    **/
    public static String initMusicMessageLove(String ToUserName, String FromUserName) {
        Music music =new Music();
        music.setTitle("love story");
        music.setDescription("我霉美如画");
        music.setMusicUrl("http://6qb5bp.natappfree.cc/ppjweb/music/1234.mp3");
        music.setHQMusicUrl("http://6qb5bp.natappfree.cc/ppjweb/music/1234.mp3");
        music.setThumbMediaId("I1tUQ4rc3I2D-scTxhsXFL8R3Qj6IfXeAVOJV97cySg-ATnvtAGduxaHB9XCCbd6");
        MusicMessage musicMessage = new MusicMessage();
        musicMessage.setMusic(music);
        musicMessage.setCreateTime(new Date().getTime());
        musicMessage.setFromUserName(ToUserName);
        musicMessage.setToUserName(FromUserName);
        musicMessage.setMsgType("music");
        return messageToXml(musicMessage);

    }

    //获得 WXBizMsgCrypt
    public static WXBizMsgCrypt getWxCrypt() {
        WXBizMsgCrypt crypt = null;
        try {
            crypt = new WXBizMsgCrypt("hadoop", "MGoa2jlnI1BUTXNHR8KzzrG9VzMfhtj7EFTyAXg9y3y", "wx4abfe26578fee369");
        } catch (AesException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return crypt;
    }
}
