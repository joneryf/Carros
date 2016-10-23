package com.jonerysantos.carros.activity;

import android.os.Bundle;

import com.jonerysantos.carros.R;
import com.jonerysantos.carros.domain.Carro;
import com.jonerysantos.carros.fragments.CarroFragment;
import com.jonerysantos.carros.fragments.CarrosFragment;

public class CarroActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carro);
        setUpToolbar();
        //Titulo da toolbar e botao navigation up
        Carro c = (Carro) getIntent().getSerializableExtra("carro");
        getSupportActionBar().setTitle(c.nome);
        //Liga o botao up navigation para voltar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(savedInstanceState ==null){
            //Cria o fragment com o mesmo Bundle (args) da intent
            CarroFragment frag = new CarroFragment();
            frag.setArguments(getIntent().getExtras());
            //adiciona o fragmento no layout
            getSupportFragmentManager().beginTransaction().add(R.id.CarroFragment, frag).commit();
        }
    }
}
