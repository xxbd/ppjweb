package Dao.response;/**
 * @Author: WuJian
 * @Description:
 * @Date: Created in 14:40 2018/3/12
 * @Modified By:
 */

import Dao.request.BaseMessage;

/**
 *@ClassName : MusicMessage
 *@Description : TODO
 *@Author : admin
 *@Date : 2018/3/12 14:40
 *@Version : 1.0
 */
public class MusicMessage extends BaseMessage{

    private Music Music;

    public Dao.response.Music getMusic() {
        return Music;
    }

    public void setMusic(Dao.response.Music music) {
        Music = music;
    }
}
