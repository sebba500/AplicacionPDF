package com.example.myapplication.controller;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.example.myapplication.model.EmpresaDBContract;
import com.example.myapplication.model.EmpresaModel;

import static java.lang.String.valueOf;

public class EmpresaController {

    private EmpresaModel empresaModel;


    public EmpresaController (Context context) {this.empresaModel = new EmpresaModel(context);}

    public void crearEmpresa(Integer idEmpresa, String rEmpresa, String nEmpresa, String passEmpresa,String pagoEmpresa,String correlativoEmpresa)throws Exception{

        ContentValues empresa=new ContentValues();


        empresa.put(EmpresaDBContract.EmpresaTabla._ID,idEmpresa);
        empresa.put(EmpresaDBContract.EmpresaTabla.COLUMN_NAME_RUT,rEmpresa);
        empresa.put(EmpresaDBContract.EmpresaTabla.COLUMN_NAME_NOMBRE,nEmpresa);
        empresa.put(EmpresaDBContract.EmpresaTabla.COLUMN_NAME_PASSWORD,passEmpresa);
        empresa.put(EmpresaDBContract.EmpresaTabla.COLUMN_NAME_PAGO,pagoEmpresa);
        empresa.put(EmpresaDBContract.EmpresaTabla.COLUMN_NAME_CORRELATIVO,correlativoEmpresa);

        this.empresaModel.crearEmpresa(empresa);

    }

    public boolean obtenerCount(){


        if (this.empresaModel.obtenerCantidad()==0){
            return false;
        }else
        {
            return true;
        }

    }

    public boolean eliminarRegistros(){

        this.empresaModel.eliminarRegistros();

        return true;
    }



    public String obtenerIDusuario(String rut){

        String usuario = valueOf(this.empresaModel.obtenerIdPorRut(rut));


        String usuarioN=usuario.replaceFirst("_id=","");


        return usuarioN;
    }

    public String obtenerPAGOusuarioString(String rut){

        String usuario = valueOf(this.empresaModel.obtenerPAGOPorRut(rut));

        String usuarioN = usuario.replaceFirst("pago=", "");



        return usuarioN;
    }




    public String obtenerCORRELATIVOusuario(String rut){

        String usuario = valueOf(this.empresaModel.obtenerCORRELATIVOPorRut(rut));

        String usuarioN = usuario.replaceFirst("correlativo=", "");


        return usuarioN;
    }

    public boolean usuarioLogin(String rut, String password){
        //PEDIR DATOS DEL USUARIO
        ContentValues usuario= this.empresaModel.obtenerUsuarioPorRut(rut);

        if (usuario ==null){

            return false;

        }

        if (password.equals(usuario.get(EmpresaDBContract.EmpresaTabla.COLUMN_NAME_PASSWORD))){

            return true;

        }

        return false;
    }

}
