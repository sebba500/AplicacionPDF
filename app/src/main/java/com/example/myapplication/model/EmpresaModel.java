package com.example.myapplication.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import static com.example.myapplication.model.EmpresaDBContract.EmpresaTabla.TABLE_NAME;

public class EmpresaModel {

    private EmpresaDBHelper dbHelper;

    public EmpresaModel(Context context){this.dbHelper= new EmpresaDBHelper(context);}



    public void crearEmpresa(ContentValues empresa){

        SQLiteDatabase db= this.dbHelper.getWritableDatabase();

        db.insert(TABLE_NAME,null,empresa);
    }



}
