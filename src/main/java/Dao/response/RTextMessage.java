package Dao.response;/**
 * @Author: WuJian
 * @Description:
 * @Date: Created in 14:28 2018/3/12
 * @Modified By:
 */

import Dao.request.BaseMessage;

/**
 *@ClassName : RTextMessage
 *@Description : TODO
 *@Author : admin
 *@Date : 2018/3/12 14:28
 *@Version : 1.0
 */
public class RTextMessage extends BaseMessage {
    // 回复的消息内容
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
