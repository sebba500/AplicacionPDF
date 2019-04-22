package com.example.myapplication;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.controller.EmpresaController;
import com.example.myapplication.model.EmpresaDBContract;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.myapplication.FirmaActivity.firmaPNG;

//import android.media.Image;


public class Formulario extends AppCompatActivity {


    private static final Byte Base64 = null;
    //Variables
    private EditText txtNombreEmpresa, txtDireccionEmpresa, txtRBDEmpresa, txtObservaciones;
    private Button btnGuardar, btnEnviar;
    private RadioGroup RGroup;
    private RadioButton RadioServicio, RadioControl;
    private CheckBox CheckRBpe, CheckRBpi, CheckRBb, CheckRbext, CheckRbint, CheckRbbo, CheckRbsha, CheckRbshc, CheckRbcamF, CheckRbbroma, CheckRbtrampa, CheckRbnotoxico, CheckRbcipermetrina, CheckRbdelta, CheckRbaguatrin, CheckRbagita, CheckRbsanicitrex;
    private File myFile;

    private String TipoServicio = "";
    private String Cell = "CEL: 9 8370 1407 – 2 2966 3828";
    private String web = "www.antimouse.cl";
    private String[]header={"Control de Roedores","Control de Insectos","Control de Microorganismos"};
    private String[]header2={"Desratizacion","Desinsectacion","Sanitizacion"};


    //VARIABLES QUE RECIBEN DE FIRMA PARA MANTENER FORMULARIO
    private String nombreVuelta, direccionVuelta, RBDVuelta, observacionesVuelta;
    //RADIO BUTTON
    private String radioVuelta;
    //CHECKBOX CONTROL DE RODEDORES
    private String perimetroExtVuelta,perimetroIntVuelta,bodegasVuelta;
    //CHECKBOX CONTROL DE INSECTOS
    private String exteriorVuelta,interiorVuelta,bodegas2Vuelta;
    //CHECKBOX CONTROL DE MICROORGANISMOS
    private String admVuelta,camarinesVuelta,camFVuelta;
    //CHECKBOX DESRATIZACION
    private String bromaVuelta,trampaVuelta,toxVuelta;
    //CHECKBOX DESINSECTACION
    private String cipeVuelta,deltaVuelta,aquaVuelta,agitaVuelta;
    //CKECKBOX SANITIZACION
    private String saniVuelta;




    String correlativo="";

    //----
    String perimetroExt = " ";
    String perimetroInt = " ";
    String bodegas = "  ";
    //----
    String Exterior = " ";
    String Interior = " ";
    String bodegas2 = "  ";
    //----
    String ServHig = "  ";
    String ServHigCamarines = " ";
    String CamaraFrio = "   ";
    //----
    String Bromadiolona = " ";
    String Trampacapturaviva = "    ";
    String Notoxicas = "    ";
    //----
    String Cipermetrina = "    ";
    String Deltametrina = "    ";
    String Aquatrin = "    ";
    String Agita = "    ";
    //----
    String Sanicitrex = "   ";



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the main_menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal_salir, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.action_salir:

                final AlertDialog.Builder alerta=new AlertDialog.Builder(this);
                alerta.setMessage("Realmente desea cerrar sesion?");
                alerta.setTitle("Cerrar sesion");
                alerta.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        SharedPreferences sesiones = getSharedPreferences(EmpresaDBContract.Sesion.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sesiones.edit();

                        editor.putBoolean(EmpresaDBContract.Sesion.FIELD_SESSION, false);
                        editor.putString(EmpresaDBContract.Sesion.FIELD_USERNAME, "");
                        editor.commit();

                        Intent i = new Intent(Formulario.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                });
                alerta.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog dialog=alerta.create();
                dialog.show();




                break;

            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);








        txtNombreEmpresa = findViewById(R.id.EditTextNombreEmpresa);
        txtDireccionEmpresa = findViewById(R.id.EditTextDireccionEmpresa);
        txtRBDEmpresa=findViewById(R.id.EditTextRBD);
        btnGuardar =findViewById(R.id.button_save);
        btnEnviar =findViewById(R.id.button_send);
        RGroup = findViewById(R.id.RGroup);
        RadioServicio = findViewById(R.id.RServicio);
        RadioControl =  findViewById(R.id.RControl);
        //Checkboxs
        //---Primer----
        CheckRBpe =findViewById(R.id.RBpe);
        CheckRBpi =findViewById(R.id.RBpi);
        CheckRBb =  findViewById(R.id.RBb);
        //----Segundo----
        CheckRbext = findViewById(R.id.RBext);
        CheckRbint =  findViewById(R.id.RBint);
        CheckRbbo = findViewById(R.id.RBbo);
        //----tercero-----
        CheckRbsha =  findViewById(R.id.RBsha);
        CheckRbshc =  findViewById(R.id.RBshc);
        CheckRbcamF =  findViewById(R.id.RBcamF);
        //-----Cuarto-----
        CheckRbbroma =  findViewById(R.id.RBbroma);
        CheckRbtrampa =  findViewById(R.id.RBtrampa);
        CheckRbnotoxico =  findViewById(R.id.RBnotoxico);
        //---------Quinto---------
        CheckRbcipermetrina = findViewById(R.id.RBCipermetrina);
        CheckRbdelta =  findViewById(R.id.RBDeltametrina);
        CheckRbaguatrin = findViewById(R.id.RBAquatrin);
        CheckRbagita =  findViewById(R.id.RBAgita);
        //-----sexto-----
        CheckRbsanicitrex = findViewById(R.id.RBSanicitrex);

        txtObservaciones =  findViewById(R.id.EditTextObservacion);


        Bundle extras = this.getIntent().getExtras();

        if (extras!=null){

            nombreVuelta=extras.getString("KEY_NOMBRE");
            direccionVuelta=extras.getString("KEY_DIRECCION");
            RBDVuelta=extras.getString("KEY_RBD");
            observacionesVuelta=extras.getString("KEY_OBSERVACIONES");

            radioVuelta=extras.getString("KEY_RADIO");

            perimetroExtVuelta=extras.getString("KEY_PERIMETROEX");
            perimetroIntVuelta=extras.getString("KEY_PERIMETROIN");
            bodegasVuelta=extras.getString("KEY_BODEGAS");

            exteriorVuelta=extras.getString("KEY_EXTERIOR");
            interiorVuelta=extras.getString("KEY_INTERIOR");
            bodegas2Vuelta=extras.getString("KEY_BODEGAS2");

            admVuelta=extras.getString("KEY_ADM");
            camarinesVuelta=extras.getString("KEY_CAMARINES");
            camFVuelta=extras.getString("KEY_CAMF");

            bromaVuelta=extras.getString("KEY_BROMA");
            trampaVuelta=extras.getString("KEY_TRAMPA");
            toxVuelta=extras.getString("KEY_TOX");

            cipeVuelta=extras.getString("KEY_CIPE");
            deltaVuelta=extras.getString("KEY_DELTA");
            aquaVuelta=extras.getString("KEY_AQUA");
            agitaVuelta=extras.getString("KEY_AGITA");

            saniVuelta=extras.getString("KEY_SANI");

            correlativo=extras.getString("KEY_CORRELATIVO");



            txtNombreEmpresa.setText(nombreVuelta);
            txtDireccionEmpresa.setText(direccionVuelta);
            txtRBDEmpresa.setText(RBDVuelta);
            txtObservaciones.setText(observacionesVuelta);

            if (radioVuelta.equals("radio1")){ RadioServicio.setChecked(true); }
            if (radioVuelta.equals("radio2")){ RadioControl.setChecked(true); }

            if (perimetroExtVuelta.equals("checked")){CheckRBpe.setChecked(true); }
            if (perimetroIntVuelta.equals("checked")){CheckRBpi.setChecked(true); }
            if (bodegasVuelta.equals("checked")){CheckRBb.setChecked(true); }

            if (exteriorVuelta.equals("checked")){CheckRbext.setChecked(true); }
            if (interiorVuelta.equals("checked")){CheckRbint.setChecked(true); }
            if (bodegas2Vuelta.equals("checked")){CheckRbbo.setChecked(true); }

            if (admVuelta.equals("checked")){CheckRbsha.setChecked(true); }
            if (camarinesVuelta.equals("checked")){CheckRbshc.setChecked(true); }
            if (camFVuelta.equals("checked")){CheckRbcamF.setChecked(true); }

            if (bromaVuelta.equals("checked")){CheckRbbroma.setChecked(true); }
            if (trampaVuelta.equals("checked")){CheckRbtrampa.setChecked(true); }
            if (toxVuelta.equals("checked")){CheckRbnotoxico.setChecked(true); }

            if (cipeVuelta.equals("checked")){CheckRbcipermetrina.setChecked(true); }
            if (deltaVuelta.equals("checked")){CheckRbdelta.setChecked(true); }
            if (aquaVuelta.equals("checked")){CheckRbaguatrin.setChecked(true); }
            if (agitaVuelta.equals("checked")){CheckRbagita.setChecked(true); }

            if (saniVuelta.equals("checked")){CheckRbsanicitrex.setChecked(true); }


        }
    }//cerrar onCreate



    public void pdfApp(View view) throws IOException, DocumentException {





        final String url1 = "http://cybertechnology.online/api/empresa/3";

        final RequestQueue queue1 = Volley.newRequestQueue(this);


        final JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url1, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        String array = response.toString();
                        try {
                            JSONArray json = new JSONArray(array);
                            for (int i = 0; i < json.length(); i++) {

                                JSONObject o = json.getJSONObject(i);

                                String correlativo1 = o.getString("correlativo");

                                Integer  correlativo2 = Integer.valueOf(correlativo1.replaceFirst("0000", ""));

                                correlativo2++;

                                correlativo = String.format("%05d", correlativo2);  // 0009

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
        queue1.add(getRequest);
        //----------------Comprobacion---------------------

        //Comprobacion de campos
        if (txtNombreEmpresa.getText().toString().isEmpty()){
            txtNombreEmpresa.setError("El Campo esta vacio");
            txtNombreEmpresa.requestFocus();
            return;
        }
        if (txtDireccionEmpresa.getText().toString().isEmpty()){
            txtDireccionEmpresa.setError("El Campo esta vacio");
            txtDireccionEmpresa.requestFocus();
            return;
        }



        //Comprobacion de los RadioButton
        if (RadioControl.isChecked() == true) {
            TipoServicio = "Control";
        } else if (RadioServicio.isChecked() == true) {
            TipoServicio = "Servicio";
        } if (RGroup.getCheckedRadioButtonId() == -1){

            Toast.makeText(this ,"Debe marcar un tipo de servicio",Toast.LENGTH_LONG).show();

            RGroup.requestFocus();
            return;
        }



        //Comprobacion Checkbox
        //Control de Roedores
        if (CheckRBpe.isChecked()){
            perimetroExt = "Perimetro Exterior";
        }
        if (CheckRBpi.isChecked()){
            perimetroInt = "Perimetro Interior";
        }
        if(CheckRBb.isChecked()){
            bodegas = "Bodegas";
        }
        //Control insectos
        if (CheckRbext.isChecked()){
            Exterior = "Exterior";
        }
        if (CheckRbint.isChecked()){
            Interior = "Interior";
        }
        if(CheckRbbo.isChecked()){
            bodegas2 = "Bodegas";
        }
        //---- Control de Microorganismos
        if (CheckRbsha.isChecked()){
            ServHig = "Ser. Hig. Adm.";
        }
        if (CheckRbshc.isChecked()){
            ServHigCamarines = "Ser. Hig. Camarines";
        }
        if(CheckRbcamF.isChecked()){
            CamaraFrio = "Camara de Frio";
        }
        //---- Desratizacion
        if (CheckRbbroma.isChecked()){
            Bromadiolona = "Bromadiolona";
        }
        if (CheckRbtrampa.isChecked()){
            Trampacapturaviva = "Trampa captura viva";
        }
        if(CheckRbnotoxico.isChecked()){
            Notoxicas = "No toxico";
        }
        //---- Desinsectacion
        if (CheckRbcipermetrina.isChecked()){
            Cipermetrina = "Cipermetrina 25%";
        }
        if (CheckRbdelta.isChecked()){
            Deltametrina = "Deltametrina";
        }
        if(CheckRbaguatrin.isChecked()){
            Aquatrin = "Aquatrin";
        }
        if(CheckRbagita.isChecked()){
            Agita = "Agita";
        }
        //---- Sanitizacion
        if(CheckRbsanicitrex.isChecked()){
            Sanicitrex = "Sanicitrex";
        }

        //------ Observaciones --------




        //------------------------Fin de comprobacion----------------------------

        final RequestQueue queue = Volley.newRequestQueue(this);

        final String url = "http://cybertechnology.online/api/empresau/3";
        StringRequest putRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {


                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {

            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("correlativo", correlativo);


                return params;
            }

        };

        queue.add(putRequest);

        if (FirmaActivity.firmaPNG==null){



            final AlertDialog.Builder alerta=new AlertDialog.Builder(this);
            alerta.setMessage("Crear documento sin firma?");
            alerta.setTitle("Guardar documento");
            alerta.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    //Capturando Fecha y Hora
                    Date date = new Date() ;
                    String timeStamp = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(date);
                    final TemplatePDF templatePDF = new TemplatePDF(getApplicationContext());



                    //Añadir imagen
                    AssetManager mngr = getAssets();

                    InputStream is = null;
                    try {
                        is = mngr.open("antimouse.png");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Bitmap bmp = BitmapFactory.decodeStream(is);

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();

                    bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);

                    Image image = null;
                    try {
                        image = Image.getInstance(stream.toByteArray());
                    } catch (BadElementException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    templatePDF.crearPDF();

                    templatePDF.abrirDocumento();
                    templatePDF.addMetaData("OrdenDeTrabajo","OrdenDeTrabajo","Antimouse");
                    //templatePDF.addContact(Cell,web);
                    templatePDF.addImage(image);





                    templatePDF.addTitles("ORDEN DE TRABAJO","N°000001");
                    templatePDF.addParagraph("Nombre empresa: " + txtNombreEmpresa.getText().toString());
                    templatePDF.addParagraph("Direccion empresa: " + txtDireccionEmpresa.getText().toString());


        /*
           ----------------------------------------------------------------------
           |            poner servicio y RBD                                    |
           ----------------------------------------------------------------------
         */
                    templatePDF.addParagraph("Fecha: "+ timeStamp);
                    templatePDF.addParagraph("RBD: "+ txtRBDEmpresa.getText().toString());


                    templatePDF.createTable(header,getFila1());
                    templatePDF.createTable(header2,getFila2());

                    templatePDF.addParagraph("\n");
                    templatePDF.addParagraph("Observaciones");
                    templatePDF.addParagraph(txtObservaciones.getText().toString());
                    templatePDF.addParagraph(correlativo);
                    templatePDF.closeDocument();





                    EmpresaController controller = new EmpresaController(getApplicationContext());


       /* if (controller.existeono(txtNombreEmpresa.getText().toString())) {


            try {
                controller.crearEmpresa(txtNombreEmpresa.getText().toString(), txtDireccionEmpresa.getText().toString(), txtRBDEmpresa.getText().toString());
                Toast.makeText(getApplicationContext(), "creado", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();



            }
        }else {
            Toast.makeText(getApplicationContext(), "ya existe", Toast.LENGTH_LONG).show();

        }

       */




                    //Ver PDF
                    if (TemplatePDF.archivoPDF.exists()) {
                        Uri uri = FileProvider.getUriForFile(Formulario.this,BuildConfig.APPLICATION_ID+".provider",TemplatePDF.archivoPDF);
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intent.setDataAndType(uri, "application/pdf");
                        try {


                            startActivity(intent);


                        } catch (ActivityNotFoundException e) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.adobe.reader")));
                            Toast.makeText(getApplicationContext(), "No cuentas con una aplicacion para visualizar pdf", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "No se encontro el archivo", Toast.LENGTH_LONG).show();
                    }


                }
            });
            alerta.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            AlertDialog dialog=alerta.create();
            dialog.show();

            //Capturando Fecha y Hora



        }else{

            Date date = new Date() ;
            String timeStamp = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(date);
            final TemplatePDF templatePDF = new TemplatePDF(getApplicationContext());



            //Añadir imagen LOGO
            AssetManager mngr = getAssets();
            InputStream is = mngr.open("antimouse.png");
            Bitmap bmp = BitmapFactory.decodeStream(is);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            Image image = Image.getInstance(stream.toByteArray());





            //Añadir imagen FIRMA
            Bitmap bitmap = BitmapFactory.decodeFile(firmaPNG.getAbsolutePath());
            ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream1);
            Image firma = Image.getInstance(stream1.toByteArray());





            templatePDF.crearPDF();

            templatePDF.abrirDocumento();
            templatePDF.addMetaData("OrdenDeTrabajo","OrdenDeTrabajo","Antimouse");
            //templatePDF.addContact(Cell,web);
            templatePDF.addImage(image);

            templatePDF.addTitles("ORDEN DE TRABAJO","N°000001");
            templatePDF.addParagraph("Nombre empresa: " + txtNombreEmpresa.getText().toString());
            templatePDF.addParagraph("Direccion empresa: " + txtDireccionEmpresa.getText().toString());


        /*
           ----------------------------------------------------------------------
           |            poner servicio y RBD                                    |
           ----------------------------------------------------------------------
         */
            templatePDF.addParagraph("Fecha: "+ timeStamp);
            templatePDF.addParagraph("RBD: "+ txtRBDEmpresa.getText().toString());


            templatePDF.createTable(header,getFila1());
            templatePDF.createTable(header2,getFila2());

            templatePDF.addParagraph("\n");
            templatePDF.addParagraph("Observaciones");
            templatePDF.addParagraph(txtObservaciones.getText().toString());
            templatePDF.addParagraph(correlativo);
            templatePDF.addImage2(firma);
            templatePDF.closeDocument();





            EmpresaController controller = new EmpresaController(getApplicationContext());


       /* if (controller.existeono(txtNombreEmpresa.getText().toString())) {


            try {
                controller.crearEmpresa(txtNombreEmpresa.getText().toString(), txtDireccionEmpresa.getText().toString(), txtRBDEmpresa.getText().toString());
                Toast.makeText(getApplicationContext(), "creado", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();



            }
        }else {
            Toast.makeText(getApplicationContext(), "ya existe", Toast.LENGTH_LONG).show();

        }

       */




            //Ver PDF
            if (TemplatePDF.archivoPDF.exists()) {
                Uri uri = FileProvider.getUriForFile(Formulario.this,BuildConfig.APPLICATION_ID+".provider",TemplatePDF.archivoPDF);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(uri, "application/pdf");
                try {


                    startActivity(intent);


                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.adobe.reader")));
                    Toast.makeText(getApplicationContext(), "No cuentas con una aplicacion para visualizar pdf", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "No se encontro el archivo", Toast.LENGTH_LONG).show();
            }

        }


    }





    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);





    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);



    }



    public void sendEmail(View view) {



        if (TemplatePDF.archivoPDF!=null) {
            String[] correo = {""};
            String[] CC = {""};
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            Uri uri2 = FileProvider.getUriForFile(Formulario.this, BuildConfig.APPLICATION_ID + ".provider", TemplatePDF.archivoPDF);

            Uri uri = Uri.fromFile((new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/DCIM/PDF/OT_CyberPunk_Calle Falsa #123_20190405_153700.pdf")));

            emailIntent.setData(Uri.parse("mailto:"));
            emailIntent.setType("text/plain");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, correo);
            emailIntent.putExtra(Intent.EXTRA_CC, CC);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Orden de Trabajo");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "");
            emailIntent.putExtra(Intent.EXTRA_STREAM, uri2);

            try {
                startActivity(Intent.createChooser(emailIntent, "Enviar Orden de Trabajo"));

            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(Formulario.this,
                        "No tienes clientes de email instalados.", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getApplicationContext(), "Debe crear el archivo primero", Toast.LENGTH_LONG).show();
        }

    }

    public void pdfFirma(View view){



        Intent intent = new Intent(Formulario.this, FirmaActivity.class);

        String rbstring = "";
        String perimetroExt="",perimetroInt="",bodegas="";
        String exterior="",interior="",bodegas2="";
        String adm="",camarines="",camF="";
        String broma="",trampa="",tox="";
        String cipe="",delta="",aqua="",agita="";
        String sani="";








         if (RadioServicio.isChecked()){ rbstring = "radio1";}
         if (RadioControl.isChecked()){ rbstring="radio2";}

         if (CheckRBpe.isChecked()){ perimetroExt="checked"; }
         if (CheckRBpi.isChecked()){ perimetroInt="checked"; }
         if (CheckRBb.isChecked()){ bodegas="checked"; }

        if (CheckRbext.isChecked()){ exterior="checked"; }
        if (CheckRbint.isChecked()){ interior="checked"; }
        if (CheckRbbo.isChecked()){ bodegas2="checked"; }

        if (CheckRbsha.isChecked()){ adm="checked"; }
        if (CheckRbshc.isChecked()){ camarines="checked"; }
        if (CheckRbcamF.isChecked()){ camF="checked"; }

        if (CheckRbbroma.isChecked()){ broma="checked"; }
        if (CheckRbtrampa.isChecked()){ trampa="checked"; }
        if (CheckRbnotoxico.isChecked()){ tox="checked"; }

        if (CheckRbcipermetrina.isChecked()){ cipe="checked"; }
        if (CheckRbdelta.isChecked()){ delta="checked"; }
        if (CheckRbaguatrin.isChecked()){ aqua="checked"; }
        if (CheckRbagita.isChecked()){ agita="checked"; }

        if (CheckRbsanicitrex.isChecked()){ sani="checked"; }






        Bundle bundle =new Bundle();
        bundle.putString("KEY_NOMBRE",txtNombreEmpresa.getText().toString());
        bundle.putString("KEY_DIRECCION",txtDireccionEmpresa.getText().toString());
        bundle.putString("KEY_RBD",txtRBDEmpresa.getText().toString());
        bundle.putString("KEY_OBSERVACIONES",txtObservaciones.getText().toString());

        bundle.putString("KEY_RADIO",rbstring);

        bundle.putString("KEY_PERIMETROEX",perimetroExt);
        bundle.putString("KEY_PERIMETROIN",perimetroInt);
        bundle.putString("KEY_BODEGAS",bodegas);

        bundle.putString("KEY_EXTERIOR",exterior);
        bundle.putString("KEY_INTERIOR",interior);
        bundle.putString("KEY_BODEGAS2",bodegas2);

        bundle.putString("KEY_ADM",adm);
        bundle.putString("KEY_CAMARINES",camarines);
        bundle.putString("KEY_CAMF",camF);

        bundle.putString("KEY_BROMA",broma);
        bundle.putString("KEY_TRAMPA",trampa);
        bundle.putString("KEY_TOX",tox);

        bundle.putString("KEY_CIPE",cipe);
        bundle.putString("KEY_DELTA",delta);
        bundle.putString("KEY_AQUA",aqua);
        bundle.putString("KEY_AGITA",agita);

        bundle.putString("KEY_SANI",sani);

        bundle.putString("KEY_CORRELATIVO",correlativo);


        intent.putExtras(bundle);

        startActivity(intent);


    }


    private ArrayList<String[]> getFila1(){
        ArrayList<String[]>rows=new ArrayList<>();

        rows.add(new String[]{perimetroExt +"\n" + perimetroInt + "\n" + bodegas
                ,Exterior + "\n" + Interior + "\n" + bodegas2 ,
                ServHig + "\n" + ServHigCamarines + "\n" + CamaraFrio});

        rows.add(new String[]{perimetroExt +"\n" + perimetroInt + "\n" + bodegas
                ,Exterior + "\n" + Interior + "\n" + bodegas2 ,
                ServHig + "\n" + ServHigCamarines + "\n" + CamaraFrio});

        rows.add(new String[]{perimetroExt +"\n" + perimetroInt + "\n" + bodegas
                ,Exterior + "\n" + Interior + "\n" + bodegas2 ,
                ServHig + "\n" + ServHigCamarines + "\n" + CamaraFrio});


        return rows;
    }
    private ArrayList<String[]> getFila2(){
        ArrayList<String[]>rows=new ArrayList<>();

        rows.add(new String[]{Bromadiolona +"\n" + Trampacapturaviva + "\n" + Notoxicas
                ,Cipermetrina +"\n"+ Deltametrina +"\n" + Aquatrin + "\n" + Agita,
                Sanicitrex});
        rows.add(new String[]{Bromadiolona +"\n" + Trampacapturaviva + "\n" + Notoxicas
                ,Cipermetrina +"\n"+ Deltametrina +"\n" + Aquatrin +"\n" + Agita,
                Sanicitrex});
        rows.add(new String[]{Bromadiolona +"\n" + Trampacapturaviva + "\n" + Notoxicas
                ,Cipermetrina +"\n"+ Deltametrina +"\n" + Aquatrin +"\n" + Agita,
                Sanicitrex});

        return rows;
    }




}
