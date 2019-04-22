package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.itextpdf.text.Image;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FirmaActivity extends AppCompatActivity {

    private Button btnClear, btnSave;
    private File file;
    private LinearLayout canvasLL;
    private View view;
    private signature mSignature;
    private Bitmap bitmap;

    public static File firmaPNG;

    // Creating Separate Directory for saving Generated Images
    String DIRECTORY = Environment.getExternalStorageDirectory().getPath() + "/Signature/";
    String pic_name = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
    String StoredPath = DIRECTORY + pic_name + ".png";

    //VARIABLES PASADAS POR LA ACTIVIDAD FORMULARIO PARA SER DEVUELTA
    private String formularioNombre, formularioDireccion, formularioRBD, formularioObservaciones, formularioRadio;

    //CHECKBOX
    private String formularioPE,formularioPI,formularioBO;

    private String formularioEX,formularioIN,formularioBO2;

    private String formularioADM,formularioCAMARINES,formularioCAMF;

    private String formularioBroma,formularioTrampa,formularioTox;

    private String formularioCIPE,formularioDELTA,formularioAQUA,formularioAGITA;

    private String formularioSANI;

    private String formularioCORR;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firma);

        Bundle extras = this.getIntent().getExtras();

        if (extras!=null){


            formularioNombre=extras.getString("KEY_NOMBRE");
            formularioDireccion=extras.getString("KEY_DIRECCION");
            formularioRBD=extras.getString("KEY_RBD");
            formularioObservaciones=extras.getString("KEY_OBSERVACIONES");
            formularioRadio=extras.getString("KEY_RADIO");

            formularioPE=extras.getString("KEY_PERIMETROEX");
            formularioPI=extras.getString("KEY_PERIMETROIN");
            formularioBO=extras.getString("KEY_BODEGAS");

            formularioEX=extras.getString("KEY_EXTERIOR");
            formularioIN=extras.getString("KEY_INTERIOR");
            formularioBO2=extras.getString("KEY_BODEGAS2");

            formularioADM=extras.getString("KEY_ADM");
            formularioCAMARINES=extras.getString("KEY_CAMARINES");
            formularioCAMF=extras.getString("KEY_CAMF");

            formularioBroma=extras.getString("KEY_BROMA");
            formularioTrampa=extras.getString("KEY_TRAMPA");
            formularioTox=extras.getString("KEY_TOX");

            formularioCIPE=extras.getString("KEY_CIPE");
            formularioDELTA=extras.getString("KEY_DELTA");
            formularioAQUA=extras.getString("KEY_AQUA");
            formularioAGITA=extras.getString("KEY_AGITA");

            formularioSANI=extras.getString("KEY_SANI");

            formularioCORR=extras.getString("KEY_CORRELATIVO");


        }


        canvasLL = (LinearLayout) findViewById(R.id.canvasLL);
        mSignature = new signature(getApplicationContext(), null);
        mSignature.setBackgroundColor(Color.WHITE);
        // Dynamically generating Layout through java code
        canvasLL.addView(mSignature, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        btnClear = (Button) findViewById(R.id.btnclear);
        btnSave = (Button) findViewById(R.id.btnsave);

        view = canvasLL;

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSignature.clear();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                view.setDrawingCacheEnabled(true);
                mSignature.save(view,StoredPath);


                Toast.makeText(getApplicationContext(), "Firma Guardada", Toast.LENGTH_SHORT).show();


                Bitmap signature = mSignature.getBitmap();
                Intent intent = new Intent(FirmaActivity.this, Formulario.class);


                firmaPNG = new File(DIRECTORY,pic_name+".png");


                Bundle bundle =new Bundle();
                bundle.putString("KEY_NOMBRE",formularioNombre);
                bundle.putString("KEY_DIRECCION",formularioDireccion);
                bundle.putString("KEY_RBD",formularioRBD);
                bundle.putString("KEY_OBSERVACIONES",formularioObservaciones);

                bundle.putString("KEY_RADIO",formularioRadio);

                bundle.putString("KEY_PERIMETROEX",formularioPE);
                bundle.putString("KEY_PERIMETROIN",formularioPI);
                bundle.putString("KEY_BODEGAS",formularioBO);

                bundle.putString("KEY_EXTERIOR",formularioEX);
                bundle.putString("KEY_INTERIOR",formularioIN);
                bundle.putString("KEY_BODEGAS2",formularioBO2);

                bundle.putString("KEY_ADM",formularioADM);
                bundle.putString("KEY_CAMARINES",formularioCAMARINES);
                bundle.putString("KEY_CAMF",formularioCAMF);

                bundle.putString("KEY_BROMA",formularioBroma);
                bundle.putString("KEY_TRAMPA",formularioTrampa);
                bundle.putString("KEY_TOX",formularioTox);

                bundle.putString("KEY_CIPE",formularioCIPE);
                bundle.putString("KEY_DELTA",formularioDELTA);
                bundle.putString("KEY_AQUA",formularioAQUA);
                bundle.putString("KEY_AGITA",formularioAGITA);

                bundle.putString("KEY_SANI",formularioSANI);

                bundle.putString("KEY_CORRELATIVO",formularioCORR);

                intent.putExtras(bundle);

                startActivity(intent);




            }
        });

        // Method to create Directory, if the Directory doesn't exists
        file = new File(DIRECTORY);
        if (!file.exists()) {
            file.mkdir();
        }

    }



    public class signature extends View {

        private static final float STROKE_WIDTH = 5f;
        private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
        private Paint paint = new Paint();
        private Path path = new Path();
        private float lastTouchX;
        private float lastTouchY;
        private final RectF dirtyRect = new RectF();

        public signature(Context context, AttributeSet attrs) {
            super(context, attrs);
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeWidth(STROKE_WIDTH);
        }

        public Bitmap getBitmap() {
            View v = (View) this.getParent();
            Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(b);
            v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
            v.draw(c);

            return b;
        }

        public FileOutputStream save(View v, String StoredPath) {
            Log.v("log_tag", "Width: " + v.getWidth());
            Log.v("log_tag", "Height: " + v.getHeight());
            if (bitmap == null) {
                bitmap = Bitmap.createBitmap(canvasLL.getWidth(), canvasLL.getHeight(), Bitmap.Config.RGB_565);
            }
            Canvas canvas = new Canvas(bitmap);
            try {
                // Output the file
                FileOutputStream mFileOutStream = new FileOutputStream(StoredPath);
                v.draw(canvas);

                // Convert the output file to Image such as .png
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, mFileOutStream);






                mFileOutStream.flush();
                mFileOutStream.close();




            } catch (Exception e) {
                Log.v("log_tag", e.toString());
            }


            return null;
        }

        public void clear() {
            path.reset();
            invalidate();

        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawPath(path, paint);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float eventX = event.getX();
            float eventY = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    path.moveTo(eventX, eventY);
                    lastTouchX = eventX;
                    lastTouchY = eventY;
                    return true;

                case MotionEvent.ACTION_MOVE:

                case MotionEvent.ACTION_UP:

                    resetDirtyRect(eventX, eventY);
                    int historySize = event.getHistorySize();
                    for (int i = 0; i < historySize; i++) {
                        float historicalX = event.getHistoricalX(i);
                        float historicalY = event.getHistoricalY(i);
                        expandDirtyRect(historicalX, historicalY);
                        path.lineTo(historicalX, historicalY);
                    }
                    path.lineTo(eventX, eventY);
                    break;

                default:
                    debug("Ignored touch event: " + event.toString());
                    return false;
            }

            invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.top - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.right + HALF_STROKE_WIDTH),
                    (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

            lastTouchX = eventX;
            lastTouchY = eventY;

            return true;
        }

        private void debug(String string) {

            Log.v("log_tag", string);

        }

        private void expandDirtyRect(float historicalX, float historicalY) {
            if (historicalX < dirtyRect.left) {
                dirtyRect.left = historicalX;
            } else if (historicalX > dirtyRect.right) {
                dirtyRect.right = historicalX;
            }

            if (historicalY < dirtyRect.top) {
                dirtyRect.top = historicalY;
            } else if (historicalY > dirtyRect.bottom) {
                dirtyRect.bottom = historicalY;
            }
        }

        private void resetDirtyRect(float eventX, float eventY) {
            dirtyRect.left = Math.min(lastTouchX, eventX);
            dirtyRect.right = Math.max(lastTouchX, eventX);
            dirtyRect.top = Math.min(lastTouchY, eventY);
            dirtyRect.bottom = Math.max(lastTouchY, eventY);
        }
    }
}
