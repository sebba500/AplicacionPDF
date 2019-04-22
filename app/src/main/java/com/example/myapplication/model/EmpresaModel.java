package com.example.myapplication.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import static com.example.myapplication.model.EmpresaDBContract.EmpresaTabla.COLUMN_NAME_NOMBRE;
import static com.example.myapplication.model.EmpresaDBContract.EmpresaTabla.TABLE_NAME;

public class EmpresaModel {

    private EmpresaDBHelper dbHelper;

    public EmpresaModel(Context context){this.dbHelper= new EmpresaDBHelper(context);}



    public void crearEmpresa(ContentValues empresa){

        SQLiteDatabase db= this.dbHelper.getWritableDatabase();

        db.insert(TABLE_NAME,null,empresa);

        db.close();
    }

    public ContentValues eliminarRegistros() {


        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);


        db.close();

        return null;


    }



    public int obtenerCantidad(){
        SQLiteDatabase db=this.dbHelper.getWritableDatabase();
        int row = (int) DatabaseUtils.queryNumEntries(db,TABLE_NAME);

        db.close();
        return row;
    }



    public ContentValues obtenerIdPorRut(String rut){

        //CREAR CONEXION CON BASE DE DATOS
        SQLiteDatabase db= this.dbHelper.getReadableDatabase();

        //DEFINIR COLUMNAS QUE INTERESAN
        String []projection={
                EmpresaDBContract.EmpresaTabla._ID,

        };

        String selection=EmpresaDBContract.EmpresaTabla.COLUMN_NAME_RUT + " = ? LIMIT 1";
        String[] selectionArgs={rut};

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

        String cursor_id= String.valueOf(cursor.getInt(cursor.getColumnIndex(EmpresaDBContract.EmpresaTabla._ID)));


        ContentValues usuario=new ContentValues();

        usuario.put(EmpresaDBContract.EmpresaTabla._ID,cursor_id);





        return usuario;
    }

    public ContentValues obtenerUsuarioPorRut(String rut){

        //CREAR CONEXION CON BASE DE DATOS
        SQLiteDatabase db= this.dbHelper.getReadableDatabase();

        //DEFINIR COLUMNAS QUE INTERESAN
        String []projection={
                EmpresaDBContract.EmpresaTabla._ID,
                EmpresaDBContract.EmpresaTabla.COLUMN_NAME_RUT,
                EmpresaDBContract.EmpresaTabla.COLUMN_NAME_NOMBRE,
                EmpresaDBContract.EmpresaTabla.COLUMN_NAME_PASSWORD,
                EmpresaDBContract.EmpresaTabla.COLUMN_NAME_PAGO,
                EmpresaDBContract.EmpresaTabla.COLUMN_NAME_CORRELATIVO
        };

        String selection=EmpresaDBContract.EmpresaTabla.COLUMN_NAME_RUT + " = ? LIMIT 1";
        String[] selectionArgs={rut};

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
        String cursor_rut=cursor.getString(cursor.getColumnIndex(EmpresaDBContract.EmpresaTabla.COLUMN_NAME_RUT));
        String cursor_password=cursor.getString(cursor.getColumnIndex(EmpresaDBContract.EmpresaTabla.COLUMN_NAME_PASSWORD));
        String cursor_pago=cursor.getString(cursor.getColumnIndex(EmpresaDBContract.EmpresaTabla.COLUMN_NAME_PAGO));

        ContentValues usuario=new ContentValues();
        usuario.put(EmpresaDBContract.EmpresaTabla.COLUMN_NAME_RUT,cursor_rut);
        usuario.put(EmpresaDBContract.EmpresaTabla.COLUMN_NAME_PASSWORD,cursor_password);
        usuario.put(EmpresaDBContract.EmpresaTabla.COLUMN_NAME_PAGO,cursor_pago);

        db.close();
        return usuario;
    }


    public ContentValues obtenerPAGOPorRut(String rut){

        //CREAR CONEXION CON BASE DE DATOS
        SQLiteDatabase db= this.dbHelper.getReadableDatabase();

        //DEFINIR COLUMNAS QUE INTERESAN
        String []projection={
                EmpresaDBContract.EmpresaTabla._ID,
                EmpresaDBContract.EmpresaTabla.COLUMN_NAME_RUT,
                EmpresaDBContract.EmpresaTabla.COLUMN_NAME_NOMBRE,
                EmpresaDBContract.EmpresaTabla.COLUMN_NAME_PASSWORD,
                EmpresaDBContract.EmpresaTabla.COLUMN_NAME_PAGO,
                EmpresaDBContract.EmpresaTabla.COLUMN_NAME_CORRELATIVO
        };


        String selection=EmpresaDBContract.EmpresaTabla.COLUMN_NAME_RUT + " = ? LIMIT 1";
        String[] selectionArgs={rut};

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
        String cursor_pago=cursor.getString(cursor.getColumnIndex(EmpresaDBContract.EmpresaTabla.COLUMN_NAME_PAGO));


        ContentValues usuario=new ContentValues();
        usuario.put(EmpresaDBContract.EmpresaTabla.COLUMN_NAME_PAGO,cursor_pago);


        db.close();
        return usuario;
    }

    public ContentValues obtenerCORRELATIVOPorRut(String rut){

        //CREAR CONEXION CON BASE DE DATOS
        SQLiteDatabase db= this.dbHelper.getReadableDatabase();

        //DEFINIR COLUMNAS QUE INTERESAN
        String []projection={
                EmpresaDBContract.EmpresaTabla._ID,
                EmpresaDBContract.EmpresaTabla.COLUMN_NAME_RUT,
                EmpresaDBContract.EmpresaTabla.COLUMN_NAME_NOMBRE,
                EmpresaDBContract.EmpresaTabla.COLUMN_NAME_PASSWORD,
                EmpresaDBContract.EmpresaTabla.COLUMN_NAME_PAGO,
                EmpresaDBContract.EmpresaTabla.COLUMN_NAME_CORRELATIVO
        };


        String selection=EmpresaDBContract.EmpresaTabla.COLUMN_NAME_RUT + " = ? LIMIT 1";
        String[] selectionArgs={rut};

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
        String cursor_corr=cursor.getString(cursor.getColumnIndex(EmpresaDBContract.EmpresaTabla.COLUMN_NAME_CORRELATIVO));


        ContentValues usuario=new ContentValues();
        usuario.put(EmpresaDBContract.EmpresaTabla.COLUMN_NAME_CORRELATIVO,cursor_corr);


        db.close();
        return usuario;
    }



}
