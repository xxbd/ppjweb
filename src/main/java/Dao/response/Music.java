package Dao.response;/**
 * @Author: WuJian
 * @Description:
 * @Date: Created in 9:36 2018/3/20
 * @Modified By:
 */

/**
 *@ClassName : Music
 *@Description : TODO
 *@Author : admin
 *@Date : 2018/3/20 9:36
 *@Version : 1.0
 */
public class Music {
    private String Title;
    private String Description;
    private String MusicUrl;

    public String getMusicUrl() {
        return MusicUrl;
    }

    public void setMusicUrl(String musicUrl) {
        MusicUrl = musicUrl;
    }

    private String HQMusicUrl;
    private String thumbMediaId;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }


    public String getHQMusicUrl() {
        return HQMusicUrl;
    }

    public void setHQMusicUrl(String HQMusicUrl) {
        this.HQMusicUrl = HQMusicUrl;
    }

    public String getThumbMediaId() {
        return thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
    }
}
