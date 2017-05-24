package com.kachidoki.me.onenettest.app;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;


import java.lang.reflect.Field;


/**
 * Created by Frank on 16/8/14.
 */

public class BaseActivity extends AppCompatActivity {

    private void releaseHandlers(){
        try {
            Class<?> clazz = getClass();
            Field[] fields = clazz.getDeclaredFields();
            if (fields == null || fields.length <= 0 ){
                return;
            }
            for (Field field: fields){
                field.setAccessible(true);
                if(!Handler.class.isAssignableFrom(field.getType())) continue;
                Handler handler = (Handler)field.get(this);
                if (handler != null && handler.getLooper() == Looper.getMainLooper()){
                    handler.removeCallbacksAndMessages(null);
                }
                field.setAccessible(false);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseHandlers();
    }
}
