package Dao.request;/**
 * @Author: WuJian
 * @Description:
 * @Date: Created in 11:29 2018/3/12
 * @Modified By:
 */

/**
 *@ClassName : VoiceMessage
 *@Description : 语音消息
 *@Author : admin
 *@Date : 2018/3/12 11:29
 *@Version : 1.0
 */
public class VoiceMessage extends BaseMessage {

    // 媒体ID
    private String MediaId;
    // 语音格式
    private String Format;

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

    public String getFormat() {
        return Format;
    }

    public void setFormat(String format) {
        Format = format;
    }
}
