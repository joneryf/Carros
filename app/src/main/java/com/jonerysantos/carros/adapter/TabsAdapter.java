package com.jonerysantos.carros.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jonerysantos.carros.R;
import com.jonerysantos.carros.domain.Carro;
import com.jonerysantos.carros.fragments.CarrosFragment;

/**
 * Created by Jonery on 23/10/2016.
 */

public class TabsAdapter extends FragmentPagerAdapter {
    private Context context;
    public TabsAdapter(Context context, FragmentManager fm){
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        //quantidade de paginas
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        //Esse titulo sera mostrado nas Tabs
        if(position == 0){
            return context.getString(R.string.classicos);
        } else if (position == 1){
            return context.getString(R.string.esportivos);
        }
        return context.getString(R.string.luxo);
    }

    @Override
    public Fragment getItem(int position) {
        //cria o fragment pra cada pagina
        Fragment f = null;
        if (position == 0){
            f = CarrosFragment.newInstance(R.string.classicos);
        } else if (position == 1 ){
            f = CarrosFragment.newInstance(R.string.esportivos);
        } else {
            f = CarrosFragment.newInstance(R.string.luxo);
        }
        return f;
    }
}
