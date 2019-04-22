package com.example.myapplication.model;

import android.provider.BaseColumns;

public class EmpresaDBContract {
    private EmpresaDBContract(){}

    public static class EmpresaTabla implements BaseColumns{
        public static final String TABLE_NAME ="empresa";

        public static final String COLUMN_NAME_RUT ="rut";
        public static final String COLUMN_NAME_NOMBRE ="nombre";
        public static final String COLUMN_NAME_PASSWORD ="password";
        public static final String COLUMN_NAME_PAGO="pago";
        public static final String COLUMN_NAME_CORRELATIVO="correlativo";
    }
    public static class Sesion{
        public static final String SHARED_PREFERENCES_NAME ="sesiones";
        public static final String FIELD_SESSION ="sesion";
        public static final String FIELD_USERNAME="name";
        public static final String FIELD_ID="id";
        public static final String FIELD_PAGO="pago";
        public static final String FIELD_CORRELATIVO="correlativo";
        public static final String FIELD_RUT="rut";


    }


}
