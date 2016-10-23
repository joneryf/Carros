package com.jonerysantos.carros.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.jonerysantos.carros.R;
import com.jonerysantos.carros.domain.Carro;
import com.jonerysantos.carros.fragments.CarroFragment;
import com.jonerysantos.carros.fragments.CarrosFragment;
import com.squareup.picasso.Picasso;

public class CarroActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carro);
        setUpToolbar();
        //Titulo da toolbar e botao navigation up
        Carro c = (Carro) getIntent().getParcelableExtra("carro");
        getSupportActionBar().setTitle(c.nome);
        //Imagem do cabeçalho na AppBar
        ImageView appBarImg = (ImageView) findViewById(R.id.appBarImg);
        Picasso.with(getContext()).load(c.urlFoto).into(appBarImg);
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
