package com.example.myapplication;


import android.Manifest;
import android.content.Intent;
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


    private EditText txtRut, txtPassword;

    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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



        final String url = "http://192.168.1.101/DoorSystem/public/api/profesor";

        final RequestQueue queue = Volley.newRequestQueue(this);

        final JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        String array = response.toString();
                        try {
                            JSONArray json = new JSONArray(array);
                            for (int i = 0; i < json.length(); i++) {

                                JSONObject o = json.getJSONObject(i);
                                String idUsuario = o.getString("id");
                                String nombre = o.getString("name");
                                String clave = o.getString("password");







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





    public void formulario(View view){

        txtRut= findViewById(R.id.input_rut);
        txtPassword= findViewById(R.id.input_contraseña);

        String rut = txtRut.getText().toString();
        String password = txtPassword.getText().toString();

        Intent intent = new Intent(this, Formulario.class);


        if (rut.isEmpty()) {

            txtRut.setError("El Campo esta vacio");
            txtRut.requestFocus();
            return;
        }else if (password.isEmpty()){

            txtPassword.setError("El Campo esta vacio");
            txtPassword.requestFocus();
            return;

        }else if (rut.equals("198153567")  &&password.equals("contraseña")){

            startActivity(intent);

        }else{

            Toast.makeText(getApplicationContext(), "Datos incorrectos", Toast.LENGTH_SHORT).show();

        }

    }

    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1 ;


}
