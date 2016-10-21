package com.jonerysantos.carros.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jonerysantos.carros.R;

import static android.R.attr.fragment;

public class CarrosFragment extends BaseFragment {
    private int tipo;
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
        TextView text = (TextView) view.findViewById(R.id.text);
        text.setText("Carros " + getString(tipo));
        return view;
    }
}
