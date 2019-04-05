package com.example.myapplication;

import android.content.ActivityNotFoundException;
import android.content.Intent;
//import android.media.Image;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;


public class Formulario extends AppCompatActivity {

    //Variables
    private EditText txtNombreEmpresa, txtDireccionEmpresa, txtObservaciones;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);


        txtNombreEmpresa = (EditText)findViewById(R.id.EditTextNombreEmpresa);
        txtDireccionEmpresa = (EditText)findViewById(R.id.EditTextDireccionEmpresa);
        btnGuardar = (Button)findViewById(R.id.button_save);
        btnEnviar = (Button)findViewById(R.id.button_send);
        RGroup = (RadioGroup)findViewById(R.id.RGroup);
        RadioServicio = (RadioButton)findViewById(R.id.RServicio);
        RadioControl = (RadioButton) findViewById(R.id.RControl);
        //Checkboxs
        //---Primer----
        CheckRBpe = (CheckBox) findViewById(R.id.RBpe);
        CheckRBpi = (CheckBox) findViewById(R.id.RBpi);
        CheckRBb = (CheckBox) findViewById(R.id.RBb);
        //----Segundo----
        CheckRbext = (CheckBox) findViewById(R.id.RBext);
        CheckRbint = (CheckBox) findViewById(R.id.RBint);
        CheckRbbo = (CheckBox) findViewById(R.id.RBbo);
        //----tercero-----
        CheckRbsha = (CheckBox) findViewById(R.id.RBsha);
        CheckRbshc = (CheckBox) findViewById(R.id.RBshc);
        CheckRbcamF = (CheckBox) findViewById(R.id.RBcamF);
        //-----Cuarto-----
        CheckRbbroma = (CheckBox) findViewById(R.id.RBbroma);
        CheckRbtrampa = (CheckBox) findViewById(R.id.RBtrampa);
        CheckRbnotoxico = (CheckBox) findViewById(R.id.RBnotoxico);
        //---------Quinto---------
        CheckRbcipermetrina = (CheckBox) findViewById(R.id.RBCipermetrina);
        CheckRbdelta = (CheckBox) findViewById(R.id.RBDeltametrina);
        CheckRbaguatrin = (CheckBox) findViewById(R.id.RBAquatrin);
        CheckRbagita = (CheckBox) findViewById(R.id.RBAgita);
        //-----sexto-----
        CheckRbsanicitrex = (CheckBox) findViewById(R.id.RBSanicitrex);

        txtObservaciones = (EditText) findViewById(R.id.EditTextObservacion);


    }//cerrar onCreate



    public void pdfApp(View view) throws IOException, DocumentException {


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
        }else {

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


        //Capturando Fecha y Hora
        Date date = new Date() ;
        String timeStamp = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(date);
        final TemplatePDF templatePDF = new TemplatePDF(getApplicationContext());



        AssetManager mngr = getAssets();
        InputStream is = mngr.open("antimouse.png");
        Bitmap bmp = BitmapFactory.decodeStream(is);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        Image image = Image.getInstance(stream.toByteArray());






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


        templatePDF.createTable(header,getFila1());
        templatePDF.createTable(header2,getFila2());

        templatePDF.addParagraph("\n");
        templatePDF.addParagraph("Observaciones");
        templatePDF.addParagraph(txtObservaciones.getText().toString());
        templatePDF.closeDocument();


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


    public void sendEmail(View view) {
        String[] correo = {""};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        Uri uri =Uri.fromFile((new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"/PDF/OrdenDeTrabajo.pdf")));
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, correo);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Orden de Trabajo");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");
        emailIntent.putExtra(Intent.EXTRA_STREAM,uri);

        try {
            startActivity(Intent.createChooser(emailIntent, "Enviar email..."));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(Formulario.this,
                    "No tienes clientes de email instalados.", Toast.LENGTH_SHORT).show();
        }
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
