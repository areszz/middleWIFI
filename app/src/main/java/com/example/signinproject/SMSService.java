package com.example.signinproject;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class SMSService {

    private static final String addr = "http://api.sms.cn/sms/";
    private static final String userId = "frankcy";

    /*
    * 如uid是：test，登录密码是：123123
    pwd=md5(123123test),即
    pwd=b9887c5ebb23ebb294acab183ecf0769

    */
    private static final String pwd = "ef297756440b72d8af30b9d68c8d7b61";

    private static final String encode = "utf8";

    public void send(int verifyCode, String mobile) throws Exception {

//组建请求
        String straddr = addr +
                "?ac=send&uid="+userId+
                "&pwd="+pwd+
                "&mobile="+mobile+
                "&encode="+encode+
                "&content=" + "【BJTUSignIn签到】欢迎您使用BJTUSignIn签到服务，您的验证码是：" + verifyCode + "，请妥善保管！";

        StringBuffer sb = new StringBuffer(straddr);
        //System.out.println("URL:"+sb);
        Log.e("TAG", "URL:"+sb);

//发送请求
        URL url = new URL(sb.toString());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        BufferedReader in = new BufferedReader(new InputStreamReader(
                url.openStream()));

//返回结果
        String inputline = in.readLine();
        //System.out.println("Response:"+inputline);
        Log.e("TAG", "Response:"+inputline);
    }


    /*public static void main(String[] args) {
        try {
            send(222666, "手机号");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/



}