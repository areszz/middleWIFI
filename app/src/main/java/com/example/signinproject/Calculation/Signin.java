package com.example.signinproject.Calculation;

import android.icu.text.SimpleDateFormat;
import android.util.Log;

import java.util.Date;
import java.util.HashMap;

public class Signin {
    HashMap<String, String> timeboard = new HashMap<>();
//    public Signin(){
//
//    }
    public String judge(String room,HashMap board){
        this.timeboard=board;
        String fintime="";
        SimpleDateFormat formatter   =   new   SimpleDateFormat   ("HH:mm:ss");
        SimpleDateFormat formatter2   =   new   SimpleDateFormat   ("EEEE");
        Date curDate =  new Date(System.currentTimeMillis());
        String   str   =   formatter.format(curDate);
        String  str2 =formatter2.format(curDate);
        String[] timecon = str.split(":");
        String hour=timecon[0];
        String minute=timecon[1];
        int minuteint=Integer.parseInt(minute);
        String second=timecon[2];

        switch(str2){
            case "星期一":
                fintime+="1";
                break;
            case "星期二":
                fintime+="2";
                break;
            case "星期三":
                fintime+="3";
                break;
            case "星期四":
                fintime+="4";
                break;
            case "星期五":
                fintime+="5";
                break;
            case "星期六":
                fintime+="6";
                break;
            case "星期日":
                fintime+="7";
                break;
        }

        if("08".equals(hour)||("09".equals(hour)&&minuteint<=50)){
            fintime+=".1";
        }else if(("10".equals(hour)&&minuteint>=10)||"11".equals(hour)){
            fintime+=".2";
        }else if(("14".equals(hour)&&minuteint>=10)||"15".equals(hour)){
            fintime+=".3";
        }else if(("16".equals(hour)&&minuteint>=20)||"17".equals(hour)||("18".equals(hour)&&minuteint<=10)){
            fintime+=".4";
        }else if("19".equals(hour)||("20".equals(hour)&&minuteint<=50)){
            fintime+=".5";
        }else{
            return str2+str+"\n课堂时间外";
        }

        String realroom=room.substring(0,3);
        Log.i("room", realroom);
        Log.i("room", room);
        if(realroom.equals(timeboard.get(fintime))){
            return str2+str+"\n"+realroom+"教室签到成功";
        }else{
            return str2+str+"\n不在对应教室，签到失败";
        }
    }
}
