package com.appfitnessapp.app.fitnessapp.Admin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.appfitnessapp.app.fitnessapp.R;

public class AdminPerfil extends AppCompatActivity {

    TextView txtNombre,txtAsesorias,txtPendiente,txtCalificacion,txtCorreo,txtContrasena,txtGanancias;
    ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_12_perfil);

        txtNombre=findViewById(R.id.txtNombreAdmin);
        txtAsesorias=findViewById(R.id.txtNumeroAsesorias);
        txtPendiente=findViewById(R.id.txtPendientes);
        txtCalificacion=findViewById(R.id.txtCalificacion);
        txtCorreo=findViewById(R.id.txtEmail);
        txtContrasena=findViewById(R.id.txtContrasena);
        txtGanancias=findViewById(R.id.txtGanancias);

        imagen=findViewById(R.id.imgAdmin);




    }
}
