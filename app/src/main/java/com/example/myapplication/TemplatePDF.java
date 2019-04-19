package com.example.myapplication;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class TemplatePDF extends AppCompatActivity {



    private Context context;
    public static File archivoPDF;
    private Document documento;
    private PdfWriter pdfWriter;
    private Paragraph paragraph;
    private Font fTitle = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
    private Font fSubTitle = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private Font fText = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD);
    private Font fHighText = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD, BaseColor.RED);
    private Font Celular = new Font(Font.FontFamily.TIMES_ROMAN,10, Font.BOLD, BaseColor.BLACK);
    private Font PaginaWeb = new Font(Font.FontFamily.TIMES_ROMAN,10, Font.BOLD, BaseColor.BLACK);
    public String nombreArchivo;

    private String NombreEmpresa = "CyberPunk";
    private String Direccion = "Calle Falsa #123";

    private String Cell = "CEL: 9 8370 1407 – 2 2966 3828";
    private String web = "www.antimouse.cl";


    public TemplatePDF(Context context) {



        this.context = context;
    }

    //METODO PARA ABRIR EL DOCUMENTO
    public void abrirDocumento() {






        try {

            documento = new Document(PageSize.A4);
            pdfWriter = PdfWriter.getInstance(documento, new FileOutputStream(archivoPDF));
            documento.open();


        } catch (Exception e) {
            Log.e("openDocument", e.toString());

        }

    }

    //METODO PARA CREAR EL DOCUMENTO
    public void crearPDF() {





        Date date = new Date() ;
        String timeStamp = new SimpleDateFormat("dd-MM-yyyy").format(date);
        File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),"PDF");

        if (!folder.exists())
            folder.mkdir();
        archivoPDF = new File(folder, "OT_"+ NombreEmpresa +"_" + Direccion + "_" + timeStamp +".pdf");
        nombreArchivo = "OT_"+ NombreEmpresa +"_" + Direccion + "_" + timeStamp +".pdf";




    }

    public void closeDocument() {
        documento.close();
    }

    public void addMetaData(String title, String subject, String autor){
        documento.addTitle(title);
        documento.addSubject(subject);
        documento.addAuthor(autor);
    }






    public void addTitles(String title, String subTitle) {


        try {

            paragraph = new Paragraph();
            addChildP(new Paragraph(title, fTitle));
            addChildP(new Paragraph(subTitle, fSubTitle));
            paragraph.setSpacingAfter(30);


            documento.add(paragraph);

        } catch (Exception e) {
            Log.e("addTitles", e.toString());
        }
    }


    public void addContact(String Cell, String Web){
        try {

            paragraph = new Paragraph();
            addChildP2(new Paragraph(Cell, Celular));
            addChildP2(new Paragraph(Web, PaginaWeb));
            paragraph.setSpacingAfter(30);

            documento.add(paragraph);
        } catch (Exception e) {
            Log.e("addTitles", e.toString());
        }
    }


    private void addChildP2(Paragraph childParagraph){
        childParagraph.setAlignment(Element.ALIGN_LEFT);
        paragraph.add(childParagraph);
    }

    private void addChildP(Paragraph childParagraph) {
        childParagraph.setAlignment(Element.ALIGN_RIGHT);
        paragraph.add(childParagraph);
    }


    public void addImage(Image image){
        try {

            image.setAlignment(Image.LEFT);
            image.scalePercent(50);
            documento.add(image);
        }catch (Exception e){}

    }

    public void addImage2(Image image){
        try{

            image.setAlignment(Image.LEFT);
            image.scalePercent(20);
            documento.add(image);
        }catch (Exception e){}

    }

    public void addParagraph(String text) {
        try {
            paragraph = new Paragraph(text, fText);
            paragraph.setSpacingAfter(5);
            paragraph.setSpacingBefore(5);
            documento.add(paragraph);
        } catch (Exception e) {
            Log.e("addParagraph", e.toString());
        }
    }


    public void createTable(String[] header, ArrayList<String[]> clients) {
        paragraph = new Paragraph();
        paragraph.setFont(fText);
        PdfPTable pdfPTable = new PdfPTable(header.length);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setSpacingBefore(20);
        PdfPCell pdfPCell;
        int indexC = 0;

        while (indexC < header.length) {
            pdfPCell = new PdfPCell(new Phrase(header[indexC++], fText));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPCell.setBackgroundColor(BaseColor.GREEN);
            pdfPTable.addCell(pdfPCell);
        }


        for (int indexR = 2; indexR < clients.size(); indexR++) {
            String[] row = clients.get(indexR);
            for (indexC = 0; indexC < clients.size(); indexC++) {
                pdfPCell = new PdfPCell(new Phrase(row[indexC]));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setFixedHeight(40);


                pdfPTable.addCell(pdfPCell);

            }
        }

        paragraph.add(pdfPTable);

        try {

            documento.add(paragraph);
        } catch (Exception e) {
            Log.e("createTable", e.toString());
        }

    }





}
