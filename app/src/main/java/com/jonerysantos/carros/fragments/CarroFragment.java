package com.jonerysantos.carros.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jonerysantos.carros.R;
import com.jonerysantos.carros.domain.Carro;
import com.squareup.picasso.Picasso;


public class CarroFragment extends BaseFragment {
    private Carro carro;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_carro, container, false);
        //Lê o objeto carro dos parametros
        carro = (Carro) getArguments().getParcelable("carro");
        //Atualiza a descrição do carro no TextView
        TextView tDesc = (TextView) view.findViewById(R.id.tDesc);
        tDesc.setText(carro.desc);
        //Mostra a foto do carro no ImageView
        return view;
    }
}
