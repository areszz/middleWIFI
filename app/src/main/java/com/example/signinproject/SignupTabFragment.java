package com.example.signinproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SignupTabFragment extends Fragment {
    EditText phone,username,password,confirm,verify,verify2,email;
    Button signup,ok;
    //用户注册信息
    String phonenumber,user,pass,pass2,vercode,vercode2,emailtext;
    float v = 0;

    public SMSService smss;

    //    public SMTPClient smtpc;
    public SMTPClient smtpc;
    public int vernum,vernum1;
    String judge="";
    String judgee="";
    String judgep="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup)inflater.inflate(R.layout.signup_fragment,container,false);

        username = root.findViewById(R.id.name);
        password = root.findViewById(R.id.pass1);
        phone = root.findViewById(R.id.phone2);
        confirm = root.findViewById(R.id.pass2);
        verify = root.findViewById(R.id.verify);
        verify2 = root.findViewById(R.id.verify2);

        signup = root.findViewById(R.id.signup);
        ok = root.findViewById(R.id.ok);
        email = root.findViewById(R.id.email1);

        phone.setTranslationY(800);
        password.setTranslationY(800);
        username.setTranslationY(800);
        confirm.setTranslationY(800);
        signup.setTranslationY(800);
        ok.setTranslationY(800);
        verify.setTranslationY(800);
        verify2.setTranslationY(800);
        email.setTranslationY(800);


        phone.setAlpha(v);
        password.setAlpha(v);
        username.setAlpha(v);
        confirm.setAlpha(v);
        ok.setAlpha(v);
        signup.setAlpha(v);
        signup.setAlpha(v);
        verify.setAlpha(v);
        verify2.setAlpha(v);
        email.setAlpha(v);


        phone.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(700).start();
        password.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(500).start();
        username.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600).start();
        confirm.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(500).start();
        ok.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(300).start();
        signup.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(300).start();
        verify.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(300).start();
        verify2.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(300).start();
        email.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(700).start();



        //监听

        //注册按钮监听
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取用户输入+比对两次密码输入+比对手机号是否已经存在+存入数据库
                phonenumber = phone.getText().toString();
                user = username.getText().toString();
                pass = password.getText().toString();
                pass2 = confirm.getText().toString();
                vercode = verify.getText().toString();
                vercode2 = verify2.getText().toString();
                emailtext = email.getText().toString();

                //判断是否为空
                if(phonenumber.length()==0||user.length()==0||pass.length()==0||pass2.length()==0||vercode.length()==0||emailtext.length()==0){
                    Toast.makeText(getContext(),"输入框不能为空",Toast.LENGTH_SHORT).show();
                }else{
                    if(phonenumber.length()==11) {//电话输入正确
                        if (pass.equals(pass2)) {//两次密码输入相同
                            /****
                             * 此处需要比对数据库中是否已经存在手机号和邮箱是否已经存在
                             * 判断验证码是否正确
                             * 且需要将新注册信息存入数据库
                             */
                            Thread t2 = new Thread() {
                                public void run() {
                                    String url = "http://47.93.11.41:8080/mainGraph/ifExist/"+user;
                                    String url1 = "http://47.93.11.41:8080/mainGraph/phone/"+phonenumber;
                                    String url2 = "http://47.93.11.41:8080/mainGraph/email/"+emailtext;
                                    judge = (sendRequest(url, "GET"));
                                    judgee = (sendRequest(url2, "GET"));
                                    judgep = (sendRequest(url1, "GET"));


                                }
                            };

                            t2.start();

                            if (judge.equals("true")||judgee.equals("true")||judgep.equals("true")) {
                                Toast.makeText(getContext(), "用户已经存在，换个名字" + vercode + String.valueOf(vernum), Toast.LENGTH_SHORT).show();
                            }
                            else {
                                //判断验证码是否正确
                                if (vernum != Integer.parseInt(vercode)) {
                                    Toast.makeText(getContext(), "手机验证码输入错误" + vercode + String.valueOf(vernum), Toast.LENGTH_SHORT).show();
                                } else if (vernum1 != Integer.parseInt(vercode2)) {
                                    Toast.makeText(getContext(), "邮箱验证码输入错误" + vercode2 + String.valueOf(vernum1), Toast.LENGTH_SHORT).show();
                                }
                                //将注册信息写入服务器
                                else {
                                    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                                    SimpleDateFormat formatter2 = new SimpleDateFormat("EEEE");
                                    Date curDate = new Date(System.currentTimeMillis());
                                    String str = formatter.format(curDate);
                                    String str2 = formatter2.format(curDate);
                                    String time = str2 + " " + str;

                                    Thread t3 = new Thread() {
                                        public void run() {
                                            String url = "http://47.93.11.41:8080/mainGraph/insert/" + user + "/" + pass + "/" + phonenumber + "/" + emailtext + "/" + time + "/" + time;
                                            System.out.println(sendRequest(url, "GET"));
                                        }

                                    };
                                    t3.start();

                                    Intent intent = new Intent(getActivity(), DashBoardActivity.class);
                                    startActivity(intent);
                                }
                            }
                        }
                        else{
                            Toast.makeText(getContext(),"两次密码输入不相同",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getContext(),"输入电话不正确",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //send验证码监听
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /***
                 * 此处发送验证码
                 */
                smss = new SMSService();
                if (android.os.Build.VERSION.SDK_INT > 9) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                }
                try {
                    smtpc = new SMTPClient("smtp.111.com", 25);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                vernum = (int)(100000+Math.random()*(999999-100000+1));
                try {
                    smss.send(vernum, phone.getText().toString());
                    vernum1 = smtpc.aaa(email.getText().toString());
                } catch (Exception e) {
                    Log.e("TAG", "Error! " + e);
                }

            }
        });

//        ok2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                /***
//                 * 此处发送邮箱验证码
//                 */
//                if (android.os.Build.VERSION.SDK_INT > 9) {
//                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//                    StrictMode.setThreadPolicy(policy);
//                }
//                try {
//                    smtpc = new SMTPClient("smtp.111.com", 25);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    smtpc.aaa(email.getText().toString());
//                } catch (Exception e) {
//                    Log.e("TAG", "Error! " + e);
//                }
//            }
//        });

        return root;
    }

    public static String sendRequest(String urlParam,String requestType) {
        HttpURLConnection con = null;

        BufferedReader buffer = null;
        StringBuffer resultBuffer = null;

        try {
            URL url = new URL(urlParam);
            //得到连接对象
            con = (HttpURLConnection) url.openConnection();
            //设置请求类型
            con.setRequestMethod(requestType);
            //设置请求需要返回的数据类型和字符集类型
            con.setRequestProperty("Content-Type", "application/json;charset=GBK");
            //允许写出
            con.setDoOutput(true);
            //允许读入
            con.setDoInput(true);
            //不使用缓存
            con.setUseCaches(false);
            //得到响应码
            int responseCode = con.getResponseCode();

            if(responseCode == HttpURLConnection.HTTP_OK){
                //得到响应流
                InputStream inputStream = con.getInputStream();
                //将响应流转换成字符串
                resultBuffer = new StringBuffer();
                String line;
                buffer = new BufferedReader(new InputStreamReader(inputStream, "GBK"));
                while ((line = buffer.readLine()) != null) {
                    resultBuffer.append(line);
                }
                return resultBuffer.toString();
            }

        }catch(Exception e) {
            e.printStackTrace();
        }

        return "";
    }

}
