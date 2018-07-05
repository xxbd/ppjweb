package Dao.request;/**
 * @Author: WuJian
 * @Description:
 * @Date: Created in 14:24 2018/3/12
 * @Modified By:
 */

/**
 *@ClassName : LocationMessage
 *@Description : 地理消息
 *@Author : admin
 *@Date : 2018/3/12 14:24
 *@Version : 1.0
 */
public class LocationMessage extends BaseMessage {

    // 地理位置维度
    private String Location_X;
    // 地理位置经度
    private String Location_Y;
    // 地图缩放大小
    private String Scale;
    // 地理位置信息
    private String Label;


    public String getLocation_X() {
        return Location_X;
    }

    public void setLocation_X(String location_X) {
        Location_X = location_X;
    }

    public String getLocation_Y() {
        return Location_Y;
    }

    public void setLocation_Y(String location_Y) {
        Location_Y = location_Y;
    }

    public String getScale() {
        return Scale;
    }

    public void setScale(String scale) {
        Scale = scale;
    }

    public String getLabel() {
        return Label;
    }

    public void setLabel(String label) {
        Label = label;
    }
}
