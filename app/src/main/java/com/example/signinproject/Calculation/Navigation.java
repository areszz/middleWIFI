package com.example.signinproject.Calculation;

import android.util.Log;

import java.util.HashMap;

public class Navigation {
    public int corridor;
    public int destination;
    public String[] corridors = new String[20];
    HashMap<String, Integer> allList = new HashMap<>();

    public Navigation(){
        for(int i =0;i<12;i++){
            corridors[i] = String.valueOf(i+1)+"-"+String.valueOf(i+2);
        }
    }

    public String[] GPS(String corridorr,String destinationn){
        Log.i("qian",corridorr);
        Log.i("qian2",destinationn);
        corridor=Integer.parseInt(corridorr);
        destination = Integer.parseInt(destinationn);
        Log.i("hou",String.valueOf(corridor));
        Log.i("hou2",String.valueOf(destination));
        if(corridor<destination){
            for(int i =0;i<corridor-1;i++)
                corridors[i] = "";
            for(int j =11;j>destination-2;j--)
                corridors[j] = "";
        }
        if(corridor>destination){
            for(int i =0;i<destination-1;i++)
                corridors[i] = "";
            for(int j =11;j>corridor-2;j--)
                corridors[j] = "";
        }

        return corridors;
    }

}
