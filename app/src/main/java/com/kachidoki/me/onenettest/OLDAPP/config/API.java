package com.kachidoki.me.onenettest.OLDAPP.config;

/**
 * Created by Frank on 16/8/14.
 */

public class API {
    public static String APIKey = "MFnzR7wfXJVFZOBj9Nvz9AJ=q3w=";
    public static String mTempDeviceId = "3225457";
    public static String COMMAND_ON="{SWITCH1}";
    public static String COMMAND_OFF="{SWITCH0}";
    public static String COMMAND_CANCELTIME="{timeoff}";
    public static String COMMAND_TIME(int time){
        return "{time:"+time+"}";
    }
    public static String COMMAND_WIFI(String name,String psw){
        return "{SSID:"+name+",KEY:"+psw+"}";
    }
    public static String COMMAND_SEARCH="{search}";

}
