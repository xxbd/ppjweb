/**
 * @Author: WuJian
 * @Description:
 * @Date: Created in 14:55 2018/3/15
 * @Modified By:
 */

import Dao.AccessToken;
import net.sf.json.JSONObject;
import utils.WeiXingUtils;

import java.io.IOException;

/**
 *@ClassName : access_tokenTest
 *@Description : TODO
 *@Author : admin
 *@Date : 2018/3/15 14:55
 *@Version : 1.0
 */
public class access_tokenTest {
    private AccessToken accessToken;
    public  access_tokenTest(){
        if(accessToken==null){
            accessToken = WeiXingUtils.getToken();
        }


    }
    public static void main(String[] args) {
        AccessToken accessToken = WeiXingUtils.getToken();
/*        System.out.println(accessToken.getAccess_token()+"票据");
        System.out.println(accessToken.getExpires_in()+"时间");
        String media_id ="上传失败";
        try {
            media_id = WeiXingUtils.upload("C:/Users/admin/Desktop/picture/timg.jpg", accessToken.getAccess_token(), "image");
        } catch (IOException e) {
            e.printStackTrace();
            e.getMessage();
        }
        System.out.println(media_id);*/
        String menu = JSONObject.fromObject(WeiXingUtils.initMenu()).toString();
        System.out.println("menu是"+menu);
        //int result = WeiXingUtils.deleteMenu(accessToken.getAccess_token());
        int result =WeiXingUtils.createMenu(accessToken.getAccess_token(),menu);
        JSONObject jsonObject = WeiXingUtils.selectMenu(accessToken.getAccess_token());
        System.out.println(jsonObject.toString());
        if(result==0){
            System.out.println("请求成功");
        }else{
            System.out.println(result);
        }
    }
    //上传素材
    public String uploadFile(){
         System.out.println(accessToken.getAccess_token()+"票据");
        System.out.println(accessToken.getExpires_in()+"时间");
        String media_id ="上传失败";
        try {
            media_id = WeiXingUtils.upload("C:/Users/admin/Desktop/picture/timg.jpg", accessToken.getAccess_token(), "image");
        } catch (IOException e) {
            e.printStackTrace();
            e.getMessage();
        }
        return media_id;

    }

}
