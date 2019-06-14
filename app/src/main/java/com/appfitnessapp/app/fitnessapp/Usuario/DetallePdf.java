package com.appfitnessapp.app.fitnessapp.Usuario;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appfitnessapp.app.fitnessapp.R;
import com.appfitnessapp.app.fitnessapp.viewPdf;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DetallePdf extends AppCompatActivity {

    LinearLayout btnComprar;
    TextView txtPrecio,txtTitulo,txtDescripcion;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_14_feed_pdf_detalle);



        btnComprar=findViewById(R.id.linearComprarPDF);

        txtPrecio=findViewById(R.id.txtPrecioPDF);
        txtTitulo=findViewById(R.id.txtTituloPDF);
        txtDescripcion=findViewById(R.id.txtDescripcionPDF);


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



        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(DetallePdf.this,viewPdf.class);
                intent.putExtra("ViewType","internet");
                startActivity(intent);

            }
        });


    }
}
