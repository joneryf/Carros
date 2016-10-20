package com.jonerysantos.carros.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jonerysantos.carros.R;

/**
 * Created by Jonery on 19/10/2016.
 */

public class BaseActivity extends livroandroid.lib.activity.BaseActivity {
    protected DrawerLayout drawerLayout;
    //Configura a toolbar
    protected void setUpToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar!=null){
            setSupportActionBar(toolbar);
        }
    }
    //Configura o nav drawer
    protected void setupNavDrawer(){
        //Drawer Layout
        final ActionBar actionBar = getSupportActionBar();
        //icone do menu do nav drawer
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if(navigationView!=null && drawerLayout != null){
            //Atualiza a imagem e textos do header do menu lateral
            setNavViewValues(navigationView, R.string.nav_drawer_username,
                    R.string.nav_drawer_email, R.mipmap.ic_launcher);
            //Trata o evento de clique no menu
            navigationView.setNavigationItemSelectedListener(
                    new NavigationView.OnNavigationItemSelectedListener(){
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                            //Seleciona a linha
                            menuItem.setChecked(true);
                            //fecha o menu
                            drawerLayout.closeDrawers();
                            //Trata o evento do menu
                            onNavDrawerItemSelected(menuItem);
                            return true;
                        }
                    }
            );
        }
    }
    //Atualiza os dados do header do Navigation View
    public static void setNavViewValues(NavigationView navView, int nome, int email, int img){
        View headerView = navView.getHeaderView(0);
        TextView tNome = (TextView) headerView.findViewById(R.id.tNome);
        TextView tEmail = (TextView) headerView.findViewById(R.id.tEmail);
        ImageView imgView = (ImageView) headerView.findViewById(R.id.img);
        tNome.setText(nome);
        tEmail.setText(email);
        imgView.setImageResource(img);
    }
    //trata o evento do menu lateral
    private void onNavDrawerItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.nav_item_carros_todos:
                toast("Clicou em carros");
                break;
            case R.id.nav_item_carros_classicos:
                toast("Clicou em carros clássicos");
                break;
            case R.id.nav_item_carros_esportivos:
                toast("Clicou em carros esportivos");
                break;
            case R.id.nav_item_carros_luxo:
                toast("Clicou em carros luxo");
                break;
            case R.id.nav_item_site_livro:
                snack(drawerLayout, "Clicou em site do livro");
                break;
            case R.id.nav_item_settings:
                toast("Clicou em configurações");
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                //Trata o clique no botao que abre o menu
                if(drawerLayout != null){
                    openDrawer();
                    return true;
                }
        }
        return super.onOptionsItemSelected(item);
    }
    //Abre o menu lateral
    protected void openDrawer(){
        if(drawerLayout !=null){
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }
    //fecha o menu lateral
    protected void closeDrawer(){
        if(drawerLayout!=null){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
}
