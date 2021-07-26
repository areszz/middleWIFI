package com.example.signinproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginTabFragment extends Fragment{
    EditText username,pass;
    TextView forgetPass;
    Button login;
    //提示框
    AlertDialog.Builder builder;

    String uName;
    String pWord;
    String judge = "ss";
    String judge1;
    String url;
    boolean jg = false;
    float v = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup)inflater.inflate(R.layout.login_tab_fragment,container,false);

        pass = root.findViewById(R.id.pass);
        username = root.findViewById(R.id.user);
       forgetPass = root.findViewById(R.id.forget);
        login = root.findViewById(R.id.login);

        pass.setTranslationY(800);
        username.setTranslationY(800);
        forgetPass.setTranslationY(800);
        login.setTranslationY(800);

        username.setAlpha(v);
        pass.setAlpha(v);
        forgetPass.setAlpha(v);
        login.setAlpha(v);

        username.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(300).start();
        pass.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(300).start();
        forgetPass.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(500).start();
        login.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(700).start();


        //监听

        //登录按钮监听
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取用户名密码
                uName = username.getText().toString();
                pWord = pass.getText().toString();
                //判断是否为空
                if(uName.length() == 0||pWord.length() == 0){
                    Toast.makeText(getContext(),"用户名密码不能为空",Toast.LENGTH_SHORT).show();
                }else{
                    /*****************************
                     * **此处需要和数据库进行比对！！
                     * 用户名和密码均为string类型，分别为uName和pWord
                     * 比对成功后跳转页面
                     */

                    Thread t2 = new Thread() {
                        public void run() {
                            url = "http://47.93.11.41:8080/mainGraph/passExist/"+uName+"/"+pWord;
//                            judge = sendRequest(url,"GET");
//                            Toast.makeText(getContext(),judge,Toast.LENGTH_SHORT).show();
                            judge = sendRequest(url,"GET");
                            jg = false;
                            if(judge.equals("true")){
                                jg = true;
                            }
                        }
                    };

                    t2.start();
//                    judge = sendRequest(url,"GET");
//                    username.setText(judge);
                    if(jg){
                        Toast.makeText(getContext(),"成功登陆",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(),DashBoardActivity.class);
                        startActivity(intent);
//                        jg = false;
                    }
                    else {
                        //对比失败
                        Toast.makeText(getContext(), judge+"ss", Toast.LENGTH_SHORT).show();
                    }

                    /*
                    //跳转页面
                    Intent intent = new Intent(getActivity(),mainHome.class);
                    startActivity(intent);*/
                }
            }
        });

        //监听forget password

        /***
         * 没写呢
         */

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
