package com.jonerysantos.carros.activity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.jonerysantos.carros.R;

/**
 * Created by Jonery on 19/10/2016.
 */

public class BaseActivity extends livroandroid.lib.activity.BaseActivity {
    protected void setUpToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar!=null){
            setSupportActionBar(toolbar);
        }
    }
}
