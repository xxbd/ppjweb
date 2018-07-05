package Dao.response;/**
 * @Author: WuJian
 * @Description:
 * @Date: Created in 14:30 2018/3/12
 * @Modified By:
 */

/**
 *@ClassName : Video
 *@Description : TODO
 *@Author : admin
 *@Date : 2018/3/12 14:30
 *@Version : 1.0
 */
public class Video {
    // 媒体文件id
    private String MediaId;
    // 缩略图的媒体id
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
