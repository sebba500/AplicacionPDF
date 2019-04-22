package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class PagoActivity extends AppCompatActivity {



    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pago);

    }


    public void volverInicio(View view){

        Intent intent=new Intent(PagoActivity.this,MainActivity.class);

        startActivity(intent);

    }


}
