package com.example.signinproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
//import android.support.annotation.RequiresApi;
//import android.support.v4.content.ContextCompat;
//import android.support.v4.view.ViewCompat;
//import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.signinproject.Calculation.*;

import com.airbnb.lottie.LottieAnimationView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class StartActivity extends AppCompatActivity {
    private Handler handler = new Handler();
    private LottieAnimationView mAnimationView;
    ImageView logo,backImg;
    //登录动画
    Animation anim;

    private ViewPager viewPager;
    private ScreenSlidePagerAdapter pagerAdapter;
    private static final int NUM_PAGES=3;

    private static final String TAG = "MainActivity---wifi";
    private TextView showTv;
    private EditText editText;
//    private TextView tv1;
//    private TextView tv2;
    public String str;
    public String str1;
    public Calculate cal;
    public Calculate cal1;
    public Navigation nvg;
    public AP ap1;
    public SQLiteDbManager sdm;
    public String[] rooms = new String[35];
    public String[] corridors = new String[12];
    public String[] showco = new String[12];
    HashMap<String, String> timeboard = new HashMap<>();
    public UItest ui;


    public String room;
    public String destination;

    public boolean start = false;

    StringBuffer sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start);

        logo = findViewById(R.id.logo);
        backImg = findViewById(R.id.Img);
        mAnimationView = findViewById(R.id.lottie);


        backImg.animate().translationY(-2600).setDuration(1000).setStartDelay(2500);
        logo.animate().translationY(2200).setDuration(1000).setStartDelay(2500);
        mAnimationView.animate().translationY(2000).setDuration(1000).setStartDelay(2500);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //页面动画
                viewPager = findViewById(R.id.pager);
                pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
                viewPager.setAdapter(pagerAdapter);
            }
        }, 3500);

        //登录
        //anim = AnimationUtils.loadAnimation(this,R.anim.o_n_anim);
        //viewPager.setAdapter(pagerAdapter);


/*
        Thread myThread = new Thread() {//创建子线程
            @Override
            public void run() {
                try {

                    sleep(2000);//使程序休眠2秒
                    /*
                    //动画
                    backImg.animate().translationY(-2600).setDuration(900);
                    logo.animate().translationY(2400).setDuration(900);
                    mAnimationView.animate().translationY(2000).setDuration(900);

                    sleep(1510);//使程序休眠2秒


                    viewPager.setAdapter(pagerAdapter);

                    Intent it = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(it);
                    finish();//关闭当前活动
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
        */

        showTv = (TextView) findViewById(R.id.showID);
//        showTv.setMovementMethod(ScrollingMovementMethod.getInstance());

        for (int i = 0; i < 12; i++) {
            corridors[i] = String.valueOf(i + 1);
        }
        str = "";
//        sdm = new SQLiteDbManager();
//        nvg = new Navigation();
//        SQLiteDatabase sqLiteDatabase = sdm.openDatabase(this);
////        try (Cursor cursor = sqLiteDatabase.query("room", null, null, null, null, null, null)) {
////            int x = 0;
////            while (cursor.moveToNext()) {
////                String stt = cursor.getString(0);
////                rooms[x] = stt;
////                x++;
////            }
////        }
//        try (Cursor cursor = sqLiteDatabase.query("timeboard", null, "ID = ?", new String[]{"1"}, null, null, null)) {
//            while (cursor.moveToNext()) {
//                timeboard.put(cursor.getString(1), cursor.getString(2));
//            }
//        }

//        sqLiteDatabase.close();
//        Toast.makeText(this, "初始化成功！", Toast.LENGTH_SHORT).show();

//        if (Build.VERSION.SDK_INT >= 23) {
//            int REQUEST_CODE_CONTACT = 101;
//            String[] permissions = {
//                    Manifest.permission.ACCESS_COARSE_LOCATION,
//                    Manifest.permission.ACCESS_FINE_LOCATION,
//                    Manifest.permission.CHANGE_NETWORK_STATE,
//                    Manifest.permission.CHANGE_WIFI_STATE,
//                    Manifest.permission.ACCESS_WIFI_STATE,
//                    Manifest.permission.ACCESS_FINE_LOCATION,
//                    Manifest.permission.ACCESS_NETWORK_STATE
//            };
//            //验证是否许可权限
//            for (String str : permissions) {
//                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
//                    //申请权限
//                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
//                }
//            }
//
//
//        }

//        //getWifiList();
//        showTv.setText(sb);
//
//        cal = new Calculate();
//        cal1 = new Calculate();
//        Thread t = new Thread() {
//            public void run() {
//                while (true) {
//                    sb = new StringBuffer();
//                    cal.getwifi(getWifiList());
////                    tv1.setText(cal.search(rooms));
//                    showTv.setText(sb);
//                    try {
//                        sleep(500);
//                    } catch (InterruptedException e) {
//                        Thread.currentThread().interrupt();
//                    }
//                }
//            }
//        };
//        t.start();
//
//        sb = new StringBuffer();
//        Thread t1 = new Thread() {
//            public void run() {
//                while (true) {
//                    cal1.getwifi(getWifiList());
//                    showco = nvg.GPS(cal1.search(corridors), "2");
//                    String test2 = "";
//                    for (int i = 0; i < 12; i++) {
//                        test2 += showco[i];
//                        test2 += "-";
//                    }
////                    tv2.setText(test2);
//                    try {
//                        sleep(500);
//                    } catch (InterruptedException e) {
//                        Thread.currentThread().interrupt();
//                    }
//                }
//            }
//        };
//        t1.start();
//        ui = new UItest();
//        sb = new StringBuffer();
//        rooms[0] = "3051";
//        rooms[1] = "3052";
//        rooms[2] = "3053";
//        rooms[3] = "3071";
//        rooms[4] = "3072";
//        rooms[5] = "3073";
//        rooms[6] = "306";
//        rooms[7] = "3081";
//        rooms[8] = "3082";
//        rooms[9] = "3083";
//        rooms[10] = "3041";
//        rooms[11] = "3042";
//        rooms[12] = "3043";
//        rooms[13] = "3091";
//        rooms[14] = "3092";
//        rooms[15] = "3093";
//        rooms[16] = "3101";
//        rooms[17] = "3102";
//        rooms[18] = "3103";
//        rooms[19] = "3031";
//        rooms[20] = "3032";
//        rooms[21] = "3033";
//        rooms[22] = "311";
//        rooms[23] = "3021";
//        rooms[24] = "3022";
//        rooms[25] = "3023";
//        rooms[26] = "3121";
//        rooms[27] = "3122";
//        rooms[28] = "3123";
//        rooms[29] = "3011";
//        rooms[30] = "3012";
//        rooms[31] = "3013";
//        rooms[32] = "3131";
//        rooms[33] = "3132";
//        rooms[34] = "3133";
//        ui.getList(getWifiList(),showTv,rooms);
    }


//    public HashMap<String, Integer> getWifiList() {
//        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
//        HashMap<String, Integer> allList = new HashMap<>();
//        //判断wifi 是否开启
//        if (wifiManager.isWifiEnabled()) {
//            Log.e(TAG, " wifi 打开");
//            List<ScanResult> scanWifiList = wifiManager.getScanResults();
//            List<ScanResult> wifiList = new ArrayList<>();
//            List<Integer> levelList = new ArrayList<Integer>();
//            if (scanWifiList != null && scanWifiList.size() > 0) {
//                for (int i = 0; i < scanWifiList.size(); i++) {
//                    ScanResult scanResult = scanWifiList.get(i);
//
//                    sb.append(scanResult.SSID + "---" + scanResult.BSSID + "@" +scanResult.level+"\n");
//                    Log.d("list", "daole");
//                    allList.put(String.valueOf(scanResult.BSSID),scanResult.level);
//                }
//                return allList;
//            } else {
//                Log.e(TAG, "非常遗憾搜索到wifi");
//            }
//        } else {
//            Log.e(TAG, " wifi 关闭");
//        }
//        return null;
//    }


    //页面适配器
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(@NotNull FragmentManager fm) {
            super(fm);
        }

        @NotNull
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                OnBoardingFragment1 tab1 = new OnBoardingFragment1();
                return tab1;
                case 1:
                    OnBoardingFragment2 tab2 = new OnBoardingFragment2();
                    return tab2;
                case 2:
                    OnBoardingFragment3 tab3 = new OnBoardingFragment3();
                    return tab3;
            }
            return null;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

}


