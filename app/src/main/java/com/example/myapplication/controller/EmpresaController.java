package com.example.myapplication.controller;

import android.content.ContentValues;
import android.content.Context;

import com.example.myapplication.model.EmpresaDBContract;
import com.example.myapplication.model.EmpresaModel;

public class EmpresaController {

    private EmpresaModel empresaModel;


    public EmpresaController (Context context) {this.empresaModel = new EmpresaModel(context);}

    public void crearEmpresa( String nEmpresa, String dEmpresa, String rbdEmpresa)throws Exception{

        ContentValues empresa=new ContentValues();


        empresa.put(EmpresaDBContract.EmpresaTabla.COLUMN_NAME_NOMBRE,nEmpresa);
        empresa.put(EmpresaDBContract.EmpresaTabla.COLUMN_NAME_DIRECCION,dEmpresa);
        empresa.put(EmpresaDBContract.EmpresaTabla.COLUMN_NAME_RBD,rbdEmpresa);

        this.empresaModel.crearEmpresa(empresa);

    }

    public boolean existeono(String nombre){
        ContentValues usuario= this.empresaModel.obtenerNombrePorNombre(nombre);

        if (usuario ==null){

            return false;

        }

        if (nombre.equals(usuario.get(EmpresaDBContract.EmpresaTabla.COLUMN_NAME_NOMBRE))){

            return true;

        }

        return false;
    }

}
