package com.jonerysantos.carros.activity;

import android.os.Bundle;

import com.jonerysantos.carros.R;
import com.jonerysantos.carros.fragments.SiteLivroFragment;

public class SiteLivroActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_livro);
        setUpToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.site_do_livro);
        if(savedInstanceState == null){
            SiteLivroFragment frag = new SiteLivroFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.container, frag).commit();
        }
    }
}
