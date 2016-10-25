package com.jonerysantos.carros.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jonerysantos.carros.PermissionUtils;
import com.jonerysantos.carros.R;
import com.jonerysantos.carros.domain.Carro;

public class MapaFragment extends BaseFragment implements OnMapReadyCallback {
    //Objeto que controla o GoogleMaps
    private GoogleMap map;
    private Carro carro;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle b) {
        View view = inflater.inflate(R.layout.fragment_mapa, container, false);
        //Recupera o fragment que esta no layout
        //utiliza getChild, pois é um fragment dentro do outro
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment);
        //Inicia o GoogleMaps dentro do fragment
        mapFragment.getMapAsync(this);
        this.carro = (Carro) getArguments().getParcelable("carro");
        return view;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        //O metodo onMapRead(map) é chamado quando a inicializacao do mapa estiver OK
        this.map = map;
        if (carro != null) {
            //Ativa o botao para mostrar minha localização
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                String[] permissoes = new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                };
                PermissionUtils.validate(getActivity(), 0, permissoes);
                return;
            }
            map.setMyLocationEnabled(true);
            //Cria o objeto LatLng com a coordenada da fabrica
            LatLng location = new LatLng(Double.parseDouble(carro.latitude),Double.parseDouble(carro.longitude));
            //Posiciona o mapa na coordenada da fábrica (zoom = 13)
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(location, 13);
            map.moveCamera(update);
            //Marcador no local da fabrica
            map.addMarker(new MarkerOptions().title(carro.nome).snippet(carro.desc).position(location));
            //Tipo do mapa
            //(normal, satelite, terreno ou hibrido
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
    }
}
