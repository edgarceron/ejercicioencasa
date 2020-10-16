package com.example.ejercicioencasa.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

import com.example.ejercicioencasa.databasehelper.DatabaseOpenHelper;
import com.example.ejercicioencasa.databasehelper.UtilitiesDatabase;

import java.util.ArrayList;

public class InfoUsuarioDao {
    private DatabaseOpenHelper databaseOpenHelper;
    private SQLiteDatabase db;

    public InfoUsuarioDao(Context context){
        databaseOpenHelper = new DatabaseOpenHelper(context);
        db = databaseOpenHelper.getWritableDatabase();
    }

    public long insertInfoUsuario(InfoUsuario infoUsuario){
        ContentValues registro = new ContentValues();
        registro.put(UtilitiesDatabase.TablaInfoUsuario.CODIGO, infoUsuario.codigo);
        registro.put(UtilitiesDatabase.TablaInfoUsuario.VALOR, infoUsuario.valor);
        long id = db.insert(UtilitiesDatabase.TablaInfoUsuario.TABLE_NAME, null, registro);

        db.close();
        return id;
    }

    public long updateInfoUsuario(InfoUsuario infoUsuario){
        ContentValues registro = new ContentValues();
        registro.put(UtilitiesDatabase.TablaInfoUsuario.CODIGO, infoUsuario.codigo);
        registro.put(UtilitiesDatabase.TablaInfoUsuario.VALOR, infoUsuario.valor);
        String  whereClausule = UtilitiesDatabase.TablaInfoUsuario.CODIGO + "=?";
        return db.update(UtilitiesDatabase.TablaInfoUsuario.TABLE_NAME, registro, whereClausule, new String[]{infoUsuario.codigo});
    }

    public ArrayList<InfoUsuario> consultarInfoUsuario(){
        ArrayList<InfoUsuario> lista = new ArrayList<>();
        Cursor cursor = db.rawQuery(UtilitiesDatabase.TablaInfoUsuario.CREATE_TABLE_INFO_USUARIO, null);
        while (cursor.moveToNext()){
            lista.add(new InfoUsuario(cursor.getString(1), cursor.getString(2)));
        }
        db.close();
        cursor.close();
        return lista;
    }

    public InfoUsuario consultarinfo(String codigo){
        InfoUsuario infoUsuario = null;
        String[] campos = new String[]{UtilitiesDatabase.TablaInfoUsuario.CODIGO,
                UtilitiesDatabase.TablaInfoUsuario.VALOR};
        String[] parametros = new String[]{UtilitiesDatabase.TablaInfoUsuario.CODIGO};
        String[] argumentos = new String[]{codigo};
        Cursor cursor = db.query(UtilitiesDatabase.TablaInfoUsuario.TABLE_NAME, campos, parametros[0] + "=?", argumentos,
                null, null, null);
        if(cursor.moveToNext()){
            infoUsuario = new InfoUsuario(cursor.getString(0), cursor.getString(1));
        }
        db.close();
        cursor.close();
        return infoUsuario;
    }

    public long  alterInfoUsuario(InfoUsuario infoUsuario){
        if(this.consultarinfo(infoUsuario.codigo) == null){
            return insertInfoUsuario(infoUsuario);
        }
        else {
            return updateInfoUsuario(infoUsuario);
        }
    }
}
