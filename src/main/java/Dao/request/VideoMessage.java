package Dao.request;/**
 * @Author: WuJian
 * @Description:
 * @Date: Created in 11:30 2018/3/12
 * @Modified By:
 */

/**
 *@ClassName : VideoMessage
 *@Description : 视频消息
 *@Author : admin
 *@Date : 2018/3/12 11:30
 *@Version : 1.0
 */
public class VideoMessage  extends BaseMessage{

    // 媒体ID
    private String MediaId;
    // 语音格式
    private String ThumbMediaId;

    public String getMediaId() {
        return MediaId;
    }
    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }
    public String getThumbMediaId() {
        return ThumbMediaId;
    }
    public void setThumbMediaId(String thumbMediaId) {
        ThumbMediaId = thumbMediaId;
    }



}
