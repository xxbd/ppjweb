package Dao.request;/**
 * @Author: WuJian
 * @Description:
 * @Date: Created in 11:29 2018/3/12
 * @Modified By:
 */

/**
 *@ClassName : ImageMessage
 *@Description : 图片消息
 *@Author : admin
 *@Date : 2018/3/12 11:29
 *@Version : 1.0
 */
public class ImageMessage extends BaseMessage {
    // 图片链接
    private String PicUrl;
    private String MediaId;

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

}
