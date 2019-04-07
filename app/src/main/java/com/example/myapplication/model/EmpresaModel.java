package com.example.myapplication.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import static com.example.myapplication.model.EmpresaDBContract.EmpresaTabla.COLUMN_NAME_NOMBRE;
import static com.example.myapplication.model.EmpresaDBContract.EmpresaTabla.TABLE_NAME;

public class EmpresaModel {

    private EmpresaDBHelper dbHelper;

    public EmpresaModel(Context context){this.dbHelper= new EmpresaDBHelper(context);}



    public void crearEmpresa(ContentValues empresa){

        SQLiteDatabase db= this.dbHelper.getWritableDatabase();

        db.insert(TABLE_NAME,null,empresa);
    }

    public boolean ExisteONo(String nombre) {
        SQLiteDatabase db=this.dbHelper.getReadableDatabase();
        String Query = "Select * from " + TABLE_NAME + " where " + COLUMN_NAME_NOMBRE + " = " + nombre;
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }






    public ContentValues obtenerNombrePorNombre(String nombre){

        //CREAR CONEXION CON BASE DE DATOS
        SQLiteDatabase db= this.dbHelper.getReadableDatabase();

        //DEFINIR COLUMNAS QUE INTERESAN
        String []projection={
                EmpresaDBContract.EmpresaTabla.COLUMN_NAME_NOMBRE,

        };

        String selection=EmpresaDBContract.EmpresaTabla.COLUMN_NAME_NOMBRE + " = ? ";
        String[] selectionArgs={nombre};

        //CONSULTA
        Cursor cursor =db.query(
                TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,null,null
        );

        if (cursor.getCount()==0) return null;

        //MOVERSE AL PRIMER RESULTADO
        cursor.moveToFirst();

        //PEDIR LOS DATOS AL CURSOR

        String cursor_id=(cursor.getString(cursor.getColumnIndex(EmpresaDBContract.EmpresaTabla.COLUMN_NAME_NOMBRE)));


        ContentValues usuario=new ContentValues();

        usuario.put(EmpresaDBContract.EmpresaTabla.COLUMN_NAME_NOMBRE,cursor_id);





        return usuario;
    }



}
