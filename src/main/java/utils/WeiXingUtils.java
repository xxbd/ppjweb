package utils;/**
 * @Author: WuJian
 * @Description:
 * @Date: Created in 10:28 2018/3/15
 * @Modified By:
 */

import Dao.AccessToken;
import Dao.menu.Button;
import Dao.menu.ClickButton;
import Dao.menu.Menu;
import Dao.menu.ViewButton;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.EntityTemplate;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *@ClassName : WeiXingUtils
 *@Description : TODO
 *@Author : admin
 *@Date : 2018/3/15 10:28
 *@Version : 1.0
 */
public class WeiXingUtils {

    private static final String APPID ="wx31a40b8263b7449b";
    private static final String APPSECREPT="4a20695072a46658a3b65c2d008d0899";
    private static final String AccessURL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
   //创建菜单
    private static final String MenuURL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS";
   //删除菜单
    private static final String deleteMenuURL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS";
    //查看菜单
    private static final String selectMenuURL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS";
    //上传文件URL
    private static final String uploadURL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
    //获取用户基本信息
    private static final String getUserInfo = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
    /**
    * @Author WuJian
    * @Description get请求
    * @Param 
    * @Date 14:22 2018/3/15
    * @Return 
    **/
    public static JSONObject doGetStr(String url){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        JSONObject getObject = null;

        try {
            CloseableHttpResponse execute = httpClient.execute(httpGet);
            HttpEntity httpEntity = execute.getEntity();
            if(httpEntity!=null){
                String http = EntityUtils.toString(httpEntity,"UTF-8");
                getObject = JSONObject.fromObject(http);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return getObject;


    }
/**
* @Author WuJian
* @Description post 请求
* @Param 
* @Date 14:22 2018/3/15
* @Return 
**/
    public static JSONObject doPostStr(String url,String outstr){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        JSONObject postObject = null;
        try {
            httpPost.setEntity(new StringEntity(outstr,"UTF-8"));
            CloseableHttpResponse execute = httpClient.execute(httpPost);
            String result = EntityUtils.toString( execute.getEntity(),"UTF-8");
            postObject = JSONObject.fromObject(result);

        } catch (IOException e) {
            e.printStackTrace();
        }


    return postObject;
    }
    /**
    * @Author WuJian
    * @Description 初始化菜单
    * @Param
    * @Date 16:55 2018/3/15
    * @Return
    **/
    public static Menu initMenu(){
        Menu menu = new Menu();

        ClickButton b11 = new ClickButton();
        b11.setName("文章");
        b11.setType("click");
        b11.setKey("article_11");
        ClickButton b21 = new ClickButton();
        b21.setName("图片");
        b21.setType("click");
        b21.setKey("picture_21");

        ViewButton b31 = new ViewButton();
        b31.setType("view");
        b31.setName("我的博客");
        b31.setUrl("https://xxbd.github.io/");

        ClickButton b32 = new ClickButton();
        b32.setType("location_select");
        b32.setName("地理位置");
        b32.setKey("location_32");

        ClickButton b33 = new ClickButton();
        b33.setType("scancode_push");
        b33.setName("扫描");
        b33.setKey("scancode_33");

        ClickButton b34 = new ClickButton();
        b34.setType("click");
        b34.setName("赞");
        b34.setKey("click_34");

       Button button = new Button();
       button.setName("其它");

       button.setSub_button(new Button[]{b31,b32,b33,b34});
       menu.setButton(new Button[]{b11,b21,button});
       return menu;
    }
    /**
    * @Author WuJian
    * @Description 创建菜单
    * @Param
    * @Date 9:31 2018/3/19
    * @Return
    **/
    public static  int createMenu(String token,String menu){
        int end = 0;
        String menuURL = MenuURL.replace("ACCESS",token);

        JSONObject jsonObject = doPostStr(menuURL, menu);
        if(jsonObject != null){

             end = jsonObject.getInt("errcode");
        }
       return end;

    }
    /**
    * @Author WuJian
    * @Description 删除菜单
    * @Param 
    * @Date 9:33 2018/3/19
    * @Return 
    **/
    public static  int deleteMenu(String token){
        int end = 0;
        String menuURL = deleteMenuURL.replace("ACCESS",token);

        JSONObject jsonObject = doGetStr(menuURL);
        if(jsonObject != null){

            end = jsonObject.getInt("errcode");
        }
        return end;

    }
    /**
    * @Author WuJian
    * @Description 临时上传素材
    * @Param 
    * @Date 10:34 2018/3/20
    * @Return 
    **/
    public static String upload(String filePath,String accessToken,String type) throws IOException {
        String result = null ;
        File file = new File(filePath);
        if(!file.exists()||!file.isFile()){
            throw new IOException("文件不存在");
        }
        String url = uploadURL.replace("ACCESS_TOKEN",accessToken).replace("TYPE",type);
        URL urlObj =new URL(url);
        //创建连接
        HttpURLConnection con=(HttpURLConnection)urlObj.openConnection();
        con.setDoInput(true);

        con.setDoOutput(true);

        con.setUseCaches(false); // post方式不能使用缓存

        // 设置请求头信息

        con.setRequestProperty("Connection", "Keep-Alive");

        con.setRequestProperty("Charset", "UTF-8");
       // 设置边界

        String BOUNDARY = "----------" + System.currentTimeMillis();

        con.setRequestProperty("Content-Type",
                "multipart/form-data; boundary="

                        + BOUNDARY);

// 请求正文信息

// 第一部分：

        StringBuilder sb = new StringBuilder();

        sb.append("--"); // 必须多两道线

        sb.append(BOUNDARY);

        sb.append("\r\n");

        sb.append("Content-Disposition: form-data;name=\"media\";filelength=\""+file.length()+"\";filename=\""

                + file.getName() + "\"\r\n");

        sb.append("Content-Type:application/octet-stream\r\n\r\n");

        byte[] head = sb.toString().getBytes("utf-8");

// 获得输出流

        OutputStream out = new DataOutputStream(con.getOutputStream());

// 输出表头

        out.write(head);

// 文件正文部分

// 把文件以流文件的方式 推入到url中

        DataInputStream in = new DataInputStream(new FileInputStream(file));

        int bytes = 0;

        byte[] bufferOut = new byte[1024];

        while ((bytes = in.read(bufferOut)) != -1) {

            out.write(bufferOut, 0, bytes);

        }

        in.close();

// 结尾部分

        byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线

        out.write(foot);

        out.flush();

        out.close();

        StringBuffer buffer = new StringBuffer();

        BufferedReader reader = null;

        try {

            // 定义BufferedReader输入流来读取URL的响应

            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String line = null;

            while ((line = reader.readLine()) != null) {

                buffer.append(line);

            }

            if (result == null) {

                result = buffer.toString();

            }

        } catch (IOException e) {

            System.out.println("发送POST请求出现异常！" + e);

            e.printStackTrace();

            throw new IOException("数据读取异常");

        } finally {

            if (reader != null) {

                reader.close();

            }

        }
        JSONObject uploadResult = JSONObject.fromObject(result);
        String typeName = "media_id";
        if(!"image".equals(type)){
            typeName = type+"_media_id";
        }
        String media_id = uploadResult.getString(typeName);
        return media_id;



    }
    /**
    * @Author WuJian
    * @Description 查询菜单
    * @Param
    * @Date 10:34 2018/3/19
    * @Return
    **/
    public static  JSONObject selectMenu(String token){
        JSONObject menu = null;
        String menuURL = selectMenuURL.replace("ACCESS",token);

        JSONObject jsonObject = doGetStr(menuURL);
        if(jsonObject != null){

            menu = jsonObject.getJSONObject("menu");

        }

        return menu;

    }
    public static JSONObject getUser(String token,String openId){
        JSONObject user = null;
        String url = getUserInfo.replace("ACCESS_TOKEN",token).replace("OPENID",openId);
        user = doGetStr(url);
        return user;

    }
    /**
    * @Author WuJian
    * @Description 获取token
    * @Param
    * @Date 16:36 2018/3/15
    * @Return
    **/
    public static AccessToken getToken(){
        AccessToken accessToken = new AccessToken();
        String url = AccessURL.replace("APPID",APPID).replace("APPSECRET",APPSECREPT);
        JSONObject jsonObject = doGetStr(url);
        if(jsonObject!=null){
             accessToken.setAccess_token(jsonObject.getString("access_token"));
            accessToken.setExpires_in(jsonObject.getInt("expires_in"));
        }
        return  accessToken;
    }

}
