package com.appfitnessapp.app.fitnessapp.Usuario;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.appfitnessapp.app.fitnessapp.R;

public class DetallePost  extends AppCompatActivity {

    LinearLayout btnCancelar,btnComprar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_detalle_item);


        btnCancelar=findViewById(R.id.linearCancelarPost);
        btnComprar=findViewById(R.id.linearComprarPost);



        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
