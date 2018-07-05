package Dao.response;/**
 * @Author: WuJian
 * @Description:
 * @Date: Created in 14:29 2018/3/12
 * @Modified By:
 */

import Dao.request.BaseMessage;

/**
 *@ClassName : RImageMessage
 *@Description : TODO
 *@Author : admin
 *@Date : 2018/3/12 14:29
 *@Version : 1.0
 */
public class ImageMessage extends BaseMessage {
    private Image Image;

    public Image getImage() {
        return Image;
    }

    public void setImage(Image image) {
        Image = image;
    }
}
