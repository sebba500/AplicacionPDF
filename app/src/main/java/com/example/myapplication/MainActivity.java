package com.example.myapplication;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.model.EmpresaDBContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {


    /*
    private String[]header={"Control de Roedores","Control de Insectos","Control de Microorganismos"};
    private String nEmpresa="NOMBRRE EMPRESA: ";
    private String dEmpresa="DIRECCION EMPRESA: ";
    private String fecha="FECHA: ";
    private String rbd="RBD: ";
    private TemplatePDF templatePDF;
    private Intent emailIntent;
    */



    private String rutA,clave,pago;
    private EditText txtRut, txtPassword;

    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //INICIAR SESION AUTOMATICAMENTE SI YA INICIÓ ANTERIORMENTE
        SharedPreferences sesion = getSharedPreferences(EmpresaDBContract.Sesion.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        boolean loggedIn = sesion.getBoolean(EmpresaDBContract.Sesion.FIELD_SESSION, false);
        if (loggedIn) {
            Intent i = new Intent(MainActivity.this, Formulario.class);
            startActivity(i);
            finish();
        }


        //pregunto por los permisos.
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

            }
        }










    }





    public void formulario(View view){

        final String url = "http://cybertechnology.online/api/empresa";

        final RequestQueue queue = Volley.newRequestQueue(this);



        txtRut= findViewById(R.id.input_rut);
        txtPassword= findViewById(R.id.input_contraseña);

        final String rut = txtRut.getText().toString();
        final String password = txtPassword.getText().toString();

        final Intent intent = new Intent(this, Formulario.class);

        if (rut.isEmpty()) {

            txtRut.setError("El Campo esta vacio");
            txtRut.requestFocus();

            return;
        }else if (password.isEmpty()){

            txtPassword.setError("El Campo esta vacio");
            txtPassword.requestFocus();

            return;

        }

        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Autenticando...");
        progressDialog.show();

        final JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        String array = response.toString();
                        try {
                            JSONArray json = new JSONArray(array);
                            for (int i = 0; i < json.length(); i++) {

                                JSONObject o = json.getJSONObject(i);

                                 rutA = o.getString("rut");
                                 clave = o.getString("password");
                                 pago= o.getString("pago");




                                if (rut.equals(rutA) &&password.equals(clave)&& pago.equals("1")){

                                    startActivity(intent);
                                    progressDialog.dismiss();

                                    SharedPreferences sesion = getSharedPreferences(EmpresaDBContract.Sesion.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sesion.edit();

                                    editor.putBoolean(EmpresaDBContract.Sesion.FIELD_SESSION, true);
                                    editor.putString(EmpresaDBContract.Sesion.FIELD_USERNAME, rut);




                                    editor.putString(EmpresaDBContract.Sesion.FIELD_ID, "1");




                                    editor.commit();

                                    finish();

                                } else if (!rut.equals(rutA) || !password.equals(clave)){

                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Datos incorrectos", Toast.LENGTH_SHORT).show();

                                }



                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.d("Response", response.toString());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());

                    }
                }
        );

        queue.add(getRequest);





    }

    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1 ;


}
