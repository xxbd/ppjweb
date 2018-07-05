package Dao.request;/**
 * @Author: WuJian
 * @Description:
 * @Date: Created in 9:32 2018/3/12
 * @Modified By:
 */

/**
 *@ClassName : TextMessage
 *@Description : 文本消息处理
 *@Author : admin
 *@Date : 2018/3/12 9:32
 *@Version : 1.0
 */
public class TextMessage extends BaseMessage {

    // 消息内容
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
