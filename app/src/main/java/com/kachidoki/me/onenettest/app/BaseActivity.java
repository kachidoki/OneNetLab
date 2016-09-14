package com.kachidoki.me.onenettest.app;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.kachidoki.me.onenettest.R;


/**
 * Created by Frank on 16/8/14.
 */

public class BaseActivity extends AppCompatActivity {
    private Toolbar toolbar;
    public Toolbar getToolbar() {
        return toolbar;
    }
    protected void setToolbar(boolean returnable){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar!=null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(returnable);
        }
    }
}
