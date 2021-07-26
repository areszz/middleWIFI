package com.example.signinproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    FloatingActionButton email,help,phone;
    float v = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        email = findViewById(R.id.emial);
        help = findViewById(R.id.help);
        phone = findViewById(R.id.phone);

        tabLayout.addTab(tabLayout.newTab().setText("Login"));
        tabLayout.addTab(tabLayout.newTab().setText("Signup"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final LoginAdapter adapter = new LoginAdapter(getSupportFragmentManager(),this,tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        //设置视图移动
        email.setTranslationY(300);
        help.setTranslationY(300);
        phone.setTranslationY(300);
        tabLayout.setTranslationY(300);

        //不透明度
        email.setAlpha(v);
        help.setAlpha(v);
        phone.setAlpha(v);
        tabLayout.setAlpha(v);


        email.animate().translationY(0).alpha(1).setDuration(1000).start();
        help.animate().translationY(0).alpha(1).setDuration(1000).start();
        phone.animate().translationY(0).alpha(1).setDuration(1000).start();
        tabLayout.animate().translationY(0).alpha(1).setDuration(1000).start();


        //监听
        //监听邮箱验证登录
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  //跳转邮箱验证界面
                DialogEmail dialogTest2 = new DialogEmail();
                dialogTest2.setStyle(DialogFragment.STYLE_NORMAL, R.style.MyDialogStyle);
                dialogTest2.show(getSupportFragmentManager(), "dialog");
            }
        });

        //监听手机验证登录
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogPhone dialogTest = new DialogPhone();
                dialogTest.setStyle(DialogFragment.STYLE_NORMAL, R.style.MyDialogStyle);
                dialogTest.show(getSupportFragmentManager(), "dialog");
            }
        });

        //监听帮助
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogHelp dialogTest3 = new DialogHelp();
                dialogTest3.setStyle(DialogFragment.STYLE_NORMAL, R.style.MyDialogStyle);
                dialogTest3.show(getSupportFragmentManager(), "dialog");
            }
        });

    }
}