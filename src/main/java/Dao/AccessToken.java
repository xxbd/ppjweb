package Dao;/**
 * @Author: WuJian
 * @Description:
 * @Date: Created in 14:40 2018/3/15
 * @Modified By:
 */

/**
 *@ClassName : AccessToken
 *@Description : TODO
 *@Author : admin
 *@Date : 2018/3/15 14:40
 *@Version : 1.0
 */
public class AccessToken {
    private String access_token;
    private int expires_in;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }
}
