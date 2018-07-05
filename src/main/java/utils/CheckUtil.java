package utils;

import java.util.Arrays;

public class CheckUtil {
    private static final String token = "hadoop";
    /*
    * @Author WuJian
    * @Description 
    * @Param [signature, timestamp, nonce]
    * @Date 10:11 2018/3/9
    * @Return boolean
    **/
    public static boolean check(String signature,String timestamp,String nonce){
        String arr[] = new String[]{token,timestamp,nonce};
        Arrays.sort(arr);
        //生成字符串
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i <arr.length ; i++) {
            stringBuffer.append(arr[i]);
        }
        //加密
        String temp = SHA1.encode(stringBuffer.toString());


     return signature.equals(temp);
    }


}
