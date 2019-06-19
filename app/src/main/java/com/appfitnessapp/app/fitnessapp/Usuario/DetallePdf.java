package com.appfitnessapp.app.fitnessapp.Usuario;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appfitnessapp.app.fitnessapp.R;
import com.appfitnessapp.app.fitnessapp.prueba;
import com.appfitnessapp.app.fitnessapp.viewPdf;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.BaseMultiplePermissionsListener;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class DetallePdf extends AppCompatActivity {

    LinearLayout btnComprar;
    TextView txtPrecio,txtTitulo,txtDescripcion;
    private static final int PDF_CODE = 1000 ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_14_feed_pdf_detalle);

        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new BaseMultiplePermissionsListener(){
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        super.onPermissionsChecked(report);
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        super.onPermissionRationaleShouldBeShown(permissions, token);
                    }
                }).check();


        btnComprar=findViewById(R.id.linearComprarPDF);

        txtPrecio=findViewById(R.id.txtPrecioPDF);
        txtTitulo=findViewById(R.id.txtTituloPDF);
        txtDescripcion=findViewById(R.id.txtDescripcionPDF);

        txtDescripcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(DetallePdf.this,PantallaPDF.class);
                intent.putExtra("ViewType","internet");
                startActivity(intent);

            }
        });

/*
        long date =System.currentTimeMillis();
        SimpleDateFormat sfd = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
       // String text = sfd.format(date);
        //txtDescripcion.setText("Hace "+text+"minutos");



     //   Calendar calendar = Calendar.getInstance();
      //  TimeZone tz = TimeZone.getDefault();
       // calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));

        SimpleDateFormat minutos = new SimpleDateFormat("mm", Locale.getDefault());
        java.util.Date currenTimeZoneMin=new java.util.Date((long)1559177288*1000);
        String textMin = minutos.format(currenTimeZoneMin);
        int min=Integer.parseInt(textMin);


        SimpleDateFormat horas = new SimpleDateFormat("HH", Locale.getDefault());
        java.util.Date currenTimeZoneHoras=new java.util.Date((long)1559177288*1000);
        String textHor = horas.format(currenTimeZoneHoras);
        int hour=Integer.parseInt(textHor);



        if (hour ==0 && (min>=1 && min <=59)){

            txtDescripcion.setText("Hace "+textMin+" min");

        }
        else if (min <60 && (hour>=1 && hour <= 23)){
            txtDescripcion.setText("Hace "+textHor+" hr.");

        }
        else {
            txtDescripcion.setText("");

        }



        Toast.makeText(DetallePdf.this, sfd.format(currenTimeZoneHoras), Toast.LENGTH_SHORT).show();

*/

        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DetallePdf.this,MetodoPago.class);
                startActivity(intent);


            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PDF_CODE && resultCode == RESULT_OK && data != null){

            Uri selecPdf=data.getData();
            Intent intent=new Intent(DetallePdf.this,PantallaPDF.class);
            intent.putExtra("ViewType","storage");
            intent.putExtra("FileUri",selecPdf.toString());
            startActivity(intent);


        }

    }
}
