package com.example.myapplication.model;

import android.provider.BaseColumns;

public class EmpresaDBContract {
    private EmpresaDBContract(){}

    public static class EmpresaTabla implements BaseColumns{
        public static final String TABLE_NAME ="empresa";
        public static final String COLUMN_NAME_NOMBRE ="name";
        public static final String COLUMN_NAME_DIRECCION ="direccion";
        public static final String COLUMN_NAME_RBD ="rbd";
    }
}
