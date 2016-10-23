package com.jonerysantos.carros.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jonerysantos.carros.CarrosApplication;
import com.jonerysantos.carros.R;
import com.jonerysantos.carros.activity.CarroActivity;
import com.jonerysantos.carros.adapter.CarroAdapter;
import com.jonerysantos.carros.domain.Carro;
import com.jonerysantos.carros.domain.CarroService;
import com.squareup.otto.Subscribe;
import java.io.IOException;
import java.util.List;


public class CarrosFragment extends BaseFragment {
    protected RecyclerView recyclerView;
    private int tipo;
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
        //Registra a classe para receber eventos
        CarrosApplication.getInstance().getBus().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Cancela o recebimento de eventos
        CarrosApplication.getInstance().getBus().unregister(this);
    }
    @Subscribe
    public void onBusAtualizarListaCarros(String refresh){
        //Recebeu o evento, atualiza a lista
        taskCarros();
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
       new GetCarrosTask().execute();
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
    //Task para buscar os carros
    private class GetCarrosTask extends AsyncTask<Void, Void, List<Carro>> {
        @Override
        protected List<Carro> doInBackground(Void... params) {
//            boolean internetOk = AndroidUtils.isNetworkAvailable(getContext());
                try {
                    //Busca os carros em background (Thread)
                    return CarroService.getCarros(getContext(), tipo);
                } catch (IOException e) {
                    Log.d("Erro: ", e.getMessage());
                    return null;
                }
        }
        //Atualiza a interface
        @Override
        protected void onPostExecute(List<Carro> carros) {
            if(carros != null){
                CarrosFragment.this.carros = carros;
                //Atualiza a view na UI Thread
                recyclerView.setAdapter(new CarroAdapter(getContext(), carros, onClickCarro()));
            }
        }
    }
}
