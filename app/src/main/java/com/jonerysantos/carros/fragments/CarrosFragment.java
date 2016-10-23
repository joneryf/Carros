package com.jonerysantos.carros.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jonerysantos.carros.R;
import com.jonerysantos.carros.activity.CarroActivity;
import com.jonerysantos.carros.adapter.CarroAdapter;
import com.jonerysantos.carros.domain.Carro;
import com.jonerysantos.carros.domain.CarroService;

import java.io.IOException;
import java.util.List;

import static android.R.attr.fragment;

public class CarrosFragment extends BaseFragment {
    protected RecyclerView recyclerView;
    private int tipo;
    private ProgressDialog dialog;
    //Lista de carros
    private List<Carro> carros;
    //Método para instanciar esse fragment pelo tipo
    public static CarrosFragment newInstance(int tipo) {
        CarrosFragment f = new CarrosFragment();
        Bundle args = new Bundle();
        args.putInt("tipo", tipo);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.tipo = getArguments().getInt("tipo");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_carros, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        taskCarros();
    }
    private void taskCarros(){
        //Mostra uma janela de progresso
        dialog = ProgressDialog.show(getActivity(), "Exemplo",
                "Por favor, aguarde...", false, true);
        new Thread(){
            @Override
            public void run() {
                try {
                    //Busca os carros em uma thread
                    carros = CarroService.getCarros(getContext(), tipo);
                    //Atualiza a lista na UI Thread
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //É aqui que utiliza o adapter. O adapter fornece o conteudo para a lista
                            recyclerView.setAdapter(new CarroAdapter(getContext(), carros, onClickCarro()));
                        }
                    });
                } catch (IOException e){
                    Log.e("livro",e.getMessage(), e);
                } finally {
                    //Fecha a janela de progresso
                    dialog.dismiss();
                }
            }
        }.start();
    }
    //Da mesma forma tratar o click
    private CarroAdapter.CarroOnClickListener onClickCarro(){
        return new CarroAdapter.CarroOnClickListener() {
            @Override
            public void onClickCarro(View view, int idx) {
                //Carro selecionado
                Carro c = carros.get(idx);
                //Abre os detalhes do carro
                Intent i = new Intent(getContext(), CarroActivity.class);
                i.putExtra("carro", c);
                startActivity(i);
            }
        };
    }
}
