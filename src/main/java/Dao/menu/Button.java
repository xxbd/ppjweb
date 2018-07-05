package Dao.menu;/**
 * @Author: WuJian
 * @Description:
 * @Date: Created in 16:21 2018/3/15
 * @Modified By:
 */

/**
 *@ClassName : Button
 *@Description : TODO
 *@Author : admin
 *@Date : 2018/3/15 16:21
 *@Version : 1.0
 */
public class Button {
    private String name;
    private String type;
    private Button[] sub_button;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Button[] getSub_button() {
        return sub_button;
    }

    public void setSub_button(Button[] subbutton) {
        this.sub_button = subbutton;
    }
}
