package com.jonerysantos.carros.domain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.R.attr.name;

/**
 * Created by Jonery on 23/10/2016.
 */

public class CarrosDB extends SQLiteOpenHelper {
    private static final String TAG = "sql";
    //Nome do banco
    public static final String NOME_BANCO = "livro_android.sqlite";
    private static final int VERSAO_BANCO = 1;
    public CarrosDB(Context context) {
        //context, nome do banco, factory, versao
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "Criando a Tabela carro...");
        db.execSQL("create table if not exists carro (_id integer primary key" +
                "autoincrement, nome text, desc text, url_foto text, url_video text, " +
                "latitute text, longitude text, tipo text);");
        Log.d(TAG, "Tabela carroc criada com sucesso.");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Caso mude a versão do banco de dados, podemos executar um SQL aqui
    }
    //Insere um novo carro, ou atualiza se já existe
    public long save(Carro carro){
        long id = carro.id;
        SQLiteDatabase db = getWritableDatabase();
        try{
            ContentValues values = new ContentValues();
            values.put("nome", carro.nome);
            values.put("desc", carro.desc);
            values.put("url_foto", carro.urlFoto);
            values.put("url_video", carro.urlVideo);
            values.put("latitude", carro.latitude);
            values.put("longitude", carro.longitude);
            values.put("tipo", carro.tipo);
            if(id != 0){
                String _id = String.valueOf(carro.id);
                String[] whereArgs = new String[]{_id};
                //update carro set values = ... where _id=?
                int count = db.update("carro", values, "_id=?", whereArgs);
                return count;
            } else {
                //insert into carro values(...)
                id = db.insert("carro", "", values);
                return id;
            }
        } finally {
            db.close();
        }
    }
    //Deleta o carro
    public int delete(Carro carro){
        SQLiteDatabase db = getWritableDatabase();
        try{
            //delete from carro where _id=?
            int count = db.delete("carro", "_id=?", new String[]{String.valueOf(carro.id)});
            Log.i(TAG, "Delete [" + count + "] registro.");
            return count;
        } finally {
            db.close();
        }
    }
    //Consulta a lista com todos os carros
    public List<Carro> findAll(){
        SQLiteDatabase db = getWritableDatabase();
        try{
            //select * from carro
            Cursor c = db.query("carro", null, null, null, null, null, null, null);
            return toList(c);
        } finally {
            db.close();
        }
    }
    //Lê o cursor e cria a lista de carros
    private List<Carro> toList(Cursor c){
        List<Carro> carros = new ArrayList<Carro>();
        if (c.moveToFirst()){
            do{
                Carro carro = new Carro();
                carros.add(carro);
                //recupera os atrubutos de carro
                carro.id = c.getLong(c.getColumnIndex("_id"));
                carro.nome = c.getString(c.getColumnIndex("nome"));
                carro.desc = c.getString(c.getColumnIndex("desc"));
                carro.urlFoto = c.getString(c.getColumnIndex("url_foto"));
                carro.urlVideo = c.getString(c.getColumnIndex("url_video"));
                carro.latitude = c.getString(c.getColumnIndex("latitude"));
                carro.longitude = c.getString(c.getColumnIndex("longitude"));
                carro.tipo = c.getString(c.getColumnIndex("tipo"));
            } while (c.moveToNext());
        }
        return carros;
    }
    public boolean exists(String nome){
        SQLiteDatabase db = getReadableDatabase();
        try{
            //select * from carro
            Cursor c = db.query("carro", null, "nome=?", new String[]{nome}, null, null, null, null);
            boolean exists = c.getCount() > 0;
            return exists;
        } finally {
            db.close();
        }
    }
    //Executa um SQL
    public void execSQL(String sql){
        SQLiteDatabase db = getWritableDatabase();
        try{
            db.execSQL(sql);
        } finally {
            db.close();
        }
    }
    //Executa um SQL
    public void execSQL(String sql, Objects[] args){
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.execSQL(sql, args);
        } finally {
            db.close();
        }
    }
}