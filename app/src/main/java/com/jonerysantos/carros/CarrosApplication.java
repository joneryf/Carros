package com.jonerysantos.carros;

import android.app.Application;
import android.util.Log;
import com.squareup.otto.Bus;

/**
 * Created by Jonery on 19/10/2016.
 */

public class CarrosApplication extends Application {
    private static final String TAG = "CarrosApplication";
    private static CarrosApplication instance = null;
    private Bus bus = new Bus();
    public static CarrosApplication getInstance(){
        return instance; //Singleton
    }

    //sempre que precisar salvar ou ler informações globais só utilizar
    //CarrosApplication app = CarrosApplication.getInstance();
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "CarrosApplication.onCreate()");
        //Salva a instância para termos acesso como Singleton
        instance = this;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d(TAG, "CarrosApplication.onTerminate()");
    }
    public Bus getBus(){
        return bus;
    }
}
