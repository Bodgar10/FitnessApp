package com.appfitnessapp.app.fitnessapp.Admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.appfitnessapp.app.fitnessapp.Arrays.Usuarios;
import com.appfitnessapp.app.fitnessapp.BaseDatos.BajarInfo;
import com.appfitnessapp.app.fitnessapp.BaseDatos.Contants;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;

public class PerfilUsuarioAdmin extends AppCompatActivity {

    CircularImageView imgPersona;
    TextView txtNombre,txtPeso,txtAltura,txtEmail;
    LinearLayout btnMensaje,btnPlan;

    String id,nombre,peso,estatura,correo,imagen;


    private ProgressDialog progressDialog;
    private static final String TAG = "BAJARINFO:";
    static DBProvider dbProvider;
    BajarInfo bajarInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_08_perfil);


        dbProvider = new DBProvider();
        bajarInfo = new BajarInfo();
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getString("id");
            nombre = extras.getString("nombre");
            peso = extras.getString("peso");
            correo = extras.getString("correo");
            estatura = extras.getString("estatura");
            imagen = extras.getString("imagen");
        }


        imgPersona=findViewById(R.id.imgPersona);
        txtNombre=findViewById(R.id.txtNombreUsuario);
        txtPeso=findViewById(R.id.txtPesoActual);
        txtAltura=findViewById(R.id.txtEstatura);
        txtEmail=findViewById(R.id.txtCorreo);


        txtNombre.setText(nombre);
        txtPeso.setText(peso);
        txtEmail.setText(correo);
        txtAltura.setText(estatura);

        if (imagen.equals("nil")) {
            try {
                URL urlfeed = new URL(imagen);
                Picasso.get().load(String.valueOf(urlfeed))
                        .error(R.mipmap.ic_launcher)
                        .fit()
                        .noFade()
                        .into(imgPersona);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        else {
            loadImageFromUrl(imagen);
            progressDialog.dismiss();
        }


        btnMensaje=findViewById(R.id.btnMensaje);
        btnPlan=findViewById(R.id.btnPlan);


        btnPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PerfilUsuarioAdmin.this, AdminPlanUsuario.class);
                Bundle bundle = new Bundle();
                bundle.putString("id",id);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        btnMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


    private void loadImageFromUrl(String url) {

        Picasso.get().load(url).into(imgPersona);
    }

}
