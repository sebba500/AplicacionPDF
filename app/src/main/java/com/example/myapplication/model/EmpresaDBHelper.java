package com.example.myapplication.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EmpresaDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME ="prueba";
    public static final int DATABASE_VERSION=1;

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + EmpresaDBContract.EmpresaTabla.TABLE_NAME +
                    "(" + EmpresaDBContract.EmpresaTabla._ID + " INTEGER PRIMARY KEY," +
                    EmpresaDBContract.EmpresaTabla.COLUMN_NAME_RUT + " TEXT," +
                    EmpresaDBContract.EmpresaTabla.COLUMN_NAME_NOMBRE + " TEXT," +
                    EmpresaDBContract.EmpresaTabla.COLUMN_NAME_PASSWORD + " TEXT," +
                    EmpresaDBContract.EmpresaTabla.COLUMN_NAME_PAGO + " TEXT," +
                    EmpresaDBContract.EmpresaTabla.COLUMN_NAME_CORRELATIVO + " TEXT)";

    public EmpresaDBHelper(Context context){ super(context,DATABASE_NAME, null,DATABASE_VERSION);}

    @Override
    public void onCreate(SQLiteDatabase db) { db.execSQL(SQL_CREATE_ENTRIES);}



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
