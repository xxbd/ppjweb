package Controller;

import Service.CoreService;
import org.aspectj.bridge.MessageUtil;
import org.dom4j.DocumentException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import utils.CheckUtil;
import utils.MessageUtils;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;


@Controller
@RequestMapping("/weixin")
public class WeixinClient {
    //明文模式
    @RequestMapping(value="hand",method= RequestMethod.POST)
    public void Wei(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 消息的接收、处理、响应
        // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        Map<String, String> map = MessageUtils.parseXmlww(request);



            PrintWriter out = response.getWriter();




    }



    //安全模式
    @RequestMapping(value="handle",method= RequestMethod.POST)
    public void Weixng(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException {
        // 消息的接收、处理、响应
        // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        //加密类型
        String encryptType = request.getParameter("encrypt_type");
        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");

       // 响应消息
       try{
            PrintWriter out = response.getWriter();
            if (CheckUtil.check(signature, timestamp, nonce)) {
                Map<String, String> requestMap = null;
                if("aes".equals(encryptType)){
                    String respXml=CoreService.processRequest(request);
                    respXml= MessageUtils.getWxCrypt().encryptMsg(respXml,timestamp,nonce);
                    System.out.println(respXml);
                    out.print(respXml);
                    out.close();
                }else if(encryptType==null){
                    //调用明文解析方法
                    Map<String, String> map = MessageUtils.parseXmlww(request);
                    //生成返回值
                    String buildResponseMessage = MessageUtils.buildResponseMessage(map);
                    out.print(buildResponseMessage);
                    System.out.println(buildResponseMessage);
                    out.close();
                }else{
                    String respXml=CoreService.processRequest(request);
                    //respXml=MessageUtil.getWxCrypt().encryptMsg(respXml,timestamp,nonce);
                    out.print(respXml);
                    System.out.println(respXml);
                    out.close();
                }

                // 调用核心业务类接收消息、处理消息
                //String respMessage = CoreService.processRequest(request);


               // out.close();
            }

        }catch(Exception e){
            e.printStackTrace();
        }





        // 调用核心业务类接收消息、处理消息
        //String respXml = CoreService.processRequest(request);

        // 响应消息
       // PrintWriter out = response.getWriter();
       // out.print(respXml);
        //out.close();
    }





    @RequestMapping(value = "handle",method = RequestMethod.GET)
    public void TestWeixin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        PrintWriter writer = response.getWriter();
        if(CheckUtil.check(signature,timestamp,nonce)){
            writer.print(echostr);
        }

    }

}
