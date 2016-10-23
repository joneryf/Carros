package com.jonerysantos.carros.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.jonerysantos.carros.R;
import com.jonerysantos.carros.domain.Carro;
import com.jonerysantos.carros.domain.CarrosDB;


public class CarroFragment extends BaseFragment {
    private Carro carro;
    private FloatingActionButton fab;
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
        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTask("favorito", taskFavoritar());
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Verifica ser o carro está favoritado e troca a cor do botão FAB
        startTask("checkFavorito", checkFavorito());
    }

    private TaskListener checkFavorito() {
        return new BaseTask<Boolean>(){
            @Override
            public Boolean execute() throws Exception {
                CarrosDB db = new CarrosDB(getContext());
                //Verifica se este carro já foi salvo
                boolean exists = db.exists(carro.nome);
                return exists;
            }

            @Override
            public void updateView(Boolean exists) {
                setFabColor(exists);
            }
        };
    }

    private TaskListener taskFavoritar() {
        return new BaseTask<Boolean>(){
            @Override
            public Boolean execute() throws Exception {
                CarrosDB db = new CarrosDB(getContext());
                //Verifica se este carro já foi salvo
                boolean exists = db.exists(carro.nome);
                if(!exists){
                    //Adiciona nos favoritos
                    db.save(carro);
                    return true;
                } else {
                    //Remove dos favoritos
                    db.delete(carro);
                    return false;
                }
            }

            @Override
            public void updateView(Boolean favoritou) {
                //Mostra a msg na UI Thread
                if(favoritou){
                    snack(getView(), "Carro adicionado aos favoritos");
                } else {
                    snack(getView(), "Carro removido dos favoritos");
                }
                setFabColor(favoritou);
            }
        };
    }
    private void setFabColor(Boolean favorito){
        if(favorito){
            //Cor do botão FAB se está favoritado
            fab.setImageTintList(ContextCompat.getColorStateList(getContext(), R.color.accent));
            fab.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.yellow));
        } else{
            //Cor do botão FAB se não está favoritado
            fab.setImageTintList(ContextCompat.getColorStateList(getContext(), R.color.accent));
            fab.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.gray));
        }
    }
}
