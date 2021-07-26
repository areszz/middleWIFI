package com.example.signinproject;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.signinproject.Calculation.Calculate;
import com.example.signinproject.Calculation.Navigation;
import com.example.signinproject.Calculation.Signin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UItest extends AppCompatActivity {
    StringBuffer sb;
    Calculate cal;
    Calculate cal1;
    public Signin signit;
    public String[] rooms = new String[35];
    public String[] corridors = new String[12];
    public String[] showco = new String[12];
    public Navigation nvg;
    private TextView showTv;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private EditText des;
    public String nowroom;
    public String str;
    HashMap<String, String> timeboard = new HashMap<>();
    HashMap<String, Integer> allList = new HashMap<>();
    public  SQLiteDbManager sdm;
    public boolean gpsflag=true;
    public boolean start=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uitest);

        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.CHANGE_NETWORK_STATE,
                    Manifest.permission.CHANGE_WIFI_STATE,
                    Manifest.permission.ACCESS_WIFI_STATE,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE
            };
            //验证是否许可权限
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                }
            }


        }


        cal = new Calculate();
        cal1 = new Calculate();
        nvg = new Navigation();
        sdm = new SQLiteDbManager();
        tv1 = (TextView) findViewById(R.id.testword);
        tv2 = (TextView) findViewById(R.id.testword2);
        tv3 = (TextView) findViewById(R.id.testword3);
        showTv = (TextView) findViewById(R.id.showID);
        showTv.setMovementMethod(ScrollingMovementMethod.getInstance());
        des=(EditText) findViewById(R.id.desText);
        for(int i =0;i<12;i++){
            corridors[i] = String.valueOf(i+1);
        }
        SQLiteDatabase sqLiteDatabase = sdm.openDatabase(this);

        try (Cursor cursor = sqLiteDatabase.query("room", null, null, null, null, null, null)) {
            int x = 0;
            while (cursor.moveToNext()) {
                String stt = cursor.getString(0);
                rooms[x] = stt;
                x++;
            }
        }
        try (Cursor cursor = sqLiteDatabase.query("timeboard", null, "ID = ?", new String[]{"1"}, null, null, null)){
            while (cursor.moveToNext()) {
                timeboard.put(cursor.getString(1),cursor.getString(2));
            }
        }


        sqLiteDatabase.close();
        Toast.makeText(this, "开始定位咯！", Toast.LENGTH_SHORT).show();
        Thread t = new Thread() {
            public void run() {
                while (true) {
                    sb = new StringBuffer();
                    allList = getWifiList();
                    cal.getwifi(allList);
                    nowroom=cal.search(rooms);
                    tv1.setText(nowroom);
                    showTv.setText(sb);
                    try {
                        sleep(500);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        };
        t.start();





        Button timebt = findViewById(R.id.timebt);
        timebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signit =new Signin();
                String timestr=signit.judge(nowroom,timeboard);
                tv3.setText(timestr);
            }
        });

        Button stopbt = findViewById(R.id.stopbt);
        stopbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gpsflag=true;
                start=false;
                tv2.setText("");
            }
        });

        Button gpsbt = findViewById(R.id.gpsbt);
        gpsbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String corrdes="";
                boolean flag=true;
                if(des.getText().toString().equals("301")||des.getText().toString().equals("313")){
                    corrdes="2";
                }else if(des.getText().toString().equals("302")||des.getText().toString().equals("312")){
                    corrdes="3";
                }else if(des.getText().toString().equals("303")){
                    corrdes="5";
                }else if(des.getText().toString().equals("310")){
                    corrdes="6";
                }else if(des.getText().toString().equals("309")){
                    corrdes="8";
                }else if(des.getText().toString().equals("304")||des.getText().toString().equals("308")){
                    corrdes="9";
                }else if(des.getText().toString().equals("305")){
                    corrdes="10";
                }else if(des.getText().toString().equals("307")){
                    corrdes="11";
                }else if(des.getText().toString().equals("306")){
                    corrdes="12";
                }else{
                    tv2.setText("输入目标有误");
                    flag=false;
                }
                if(flag&&gpsflag) {
                    start=true;
                    gpsflag=false;
                    String finalCorrdes = corrdes;
                    Thread t1 = new Thread() {
                        public void run() {
                            while (start) {
                                nvg = new Navigation();
                                sb = new StringBuffer();
                                cal1.getwifi(getWifiList());
                                String sear = cal1.search(corridors);
                                showco = nvg.GPS(sear, finalCorrdes);
                                String test2 = "";
                                for (int i = 0; i < 12; i++) {
                                    test2 += showco[i];
                                    test2 += "-";
                                }
                                tv2.setText(test2);
                                try {
                                    sleep(500);
                                } catch (InterruptedException e) {
                                    Thread.currentThread().interrupt();
                                }
                            }
                        }
                    };
                    t1.start();
                }

            }
        });
    }



    public HashMap<String, Integer> getWifiList() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        HashMap<String, Integer> allList = new HashMap<>();
        //判断wifi 是否开启
        if (wifiManager.isWifiEnabled()) {
            List<ScanResult> scanWifiList = wifiManager.getScanResults();
            List<ScanResult> wifiList = new ArrayList<>();
            List<Integer> levelList = new ArrayList<Integer>();
            if (scanWifiList != null && scanWifiList.size() > 0) {
                for (int i = 0; i < scanWifiList.size(); i++) {
                    ScanResult scanResult = scanWifiList.get(i);
                    sb.append(scanResult.SSID + "---" + scanResult.BSSID + "@" +scanResult.level+"\n");
                    Log.d("list", "daole");
                    allList.put(String.valueOf(scanResult.BSSID),scanResult.level);
                }
                return allList;
            } else {
            }
        } else {
        }
        return null;
    }


}