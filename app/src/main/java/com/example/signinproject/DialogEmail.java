package com.example.signinproject;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DialogEmail extends DialogFragment {
    Button sign,send;
    EditText email,code;
    String emailNumber, vCode;
//    SMSService smss;
    SMTPClient smtpc;
    int vernum1;
    String judge = "";
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.login_email_fragment, null, false);
        dialog.addContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogWindow.setAttributes(lp);
        dialogWindow.setGravity(Gravity.BOTTOM);


        sign = dialog.findViewById(R.id.logine);
        send = dialog.findViewById(R.id.sende);
        email = dialog.findViewById(R.id.emaile);
        code = dialog.findViewById(R.id.verifye);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                smss = new SMSService();
                if (android.os.Build.VERSION.SDK_INT > 9) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                }
                try {
                    smtpc = new SMTPClient("smtp.111.com", 25);
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                vernum = (int)(100000+Math.random()*(999999-100000+1));
                try {
//                    smss.send(vernum, phone.getText().toString());
                    vernum1 = smtpc.aaa(email.getText().toString());
                } catch (Exception e) {
                    Log.e("TAG", "Error! " + e);
                }

            }
        });

        //??????????????????
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //??????????????????
                emailNumber = email.getText().toString();
                vCode = code.getText().toString();
                //??????????????????
                if(emailNumber.length() == 0||vCode.length() == 0){
                    Toast.makeText(getContext(),"???????????????????????????",Toast.LENGTH_SHORT).show();
                }else {
                    /*****************************
                     * **??????????????????????????????????????????
                     * ???????????????????????????
                     */
                    Thread t2 = new Thread() {
                        public void run() {
                            String url = "http://47.93.11.41:8080/mainGraph/email/"+emailNumber;
                            judge = (sendRequest(url, "GET"));

                        }
                    };
                    t2.start();
                    if (judge.equals("false")) {
                        Toast.makeText(getContext(), "??????????????????", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if (vernum1 == Integer.parseInt(vCode)) {
                            Intent intent = new Intent(getActivity(), DashBoardActivity.class);
                            startActivity(intent);
                        } else {
                            //if????????????
                            Toast.makeText(getContext(), "?????????????????????", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
                }
        });


        return dialog;
    }
    public static String sendRequest(String urlParam,String requestType) {
        HttpURLConnection con = null;

        BufferedReader buffer = null;
        StringBuffer resultBuffer = null;

        try {
            URL url = new URL(urlParam);
            //??????????????????
            con = (HttpURLConnection) url.openConnection();
            //??????????????????
            con.setRequestMethod(requestType);
            //?????????????????????????????????????????????????????????
            con.setRequestProperty("Content-Type", "application/json;charset=GBK");
            //????????????
            con.setDoOutput(true);
            //????????????
            con.setDoInput(true);
            //???????????????
            con.setUseCaches(false);
            //???????????????
            int responseCode = con.getResponseCode();

            if(responseCode == HttpURLConnection.HTTP_OK){
                //???????????????
                InputStream inputStream = con.getInputStream();
                //??????????????????????????????
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
