package com.appfitnessapp.app.fitnessapp.Admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

public class SolicitudAsesoria extends AppCompatActivity {

    CircularImageView imagen;
    TextView txtPeso,txtEstatura,txtObjetivo,txtNombre;
    LinearLayout btnComenzarAsesoria;


    private ProgressDialog progressDialog;
    private static final String TAG = "BAJARINFO:";
    static DBProvider dbProvider;
    BajarInfo bajarInfo;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_05_asesorias);

        Toolbar toolbarback=findViewById(R.id.include);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("Solicitud de asesoría");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getString("id");
        }

        bajarUsuarios();

        dbProvider = new DBProvider();
        bajarInfo = new BajarInfo();
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);

        imagen=findViewById(R.id.imgPersona);

        txtPeso=findViewById(R.id.txtPesoActual);
        txtEstatura=findViewById(R.id.txtEstatura);
        txtObjetivo=findViewById(R.id.txtObjetivo);
        txtNombre=findViewById(R.id.txtNombreUsuario);


        btnComenzarAsesoria=findViewById(R.id.linearAceptar);

        btnComenzarAsesoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SolicitudAsesoria.this, RecetasEditar.class);
                Bundle bundle = new Bundle();
                bundle.putString("id",id);
                intent.putExtras(bundle);
                startActivity(intent);


            }
        });


    }

    public void bajarUsuarios(){
        Log.e(TAG,"Usuarios 2: ");
        dbProvider = new DBProvider();

        dbProvider.usersRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e(TAG,"Usuarios 4: ");
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                        Log.e(TAG, "Usuarios: " + snapshot);
                        Usuarios usuarios = snapshot.getValue(Usuarios.class);

                        if (usuarios.getId_usuario()!=null) {

                                if (usuarios.getId_usuario().equals(id)) {

                                    txtPeso.setText(usuarios.getPeso_actual());
                                    txtEstatura.setText(usuarios.getEstatura());
                                    txtNombre.setText(usuarios.getNombre_usuario());
                                    txtObjetivo.setText(usuarios.getObjetivo());

                                    if (usuarios.getFoto_usuario().equals("nil")) {
                                        try {
                                            URL urlfeed = new URL(usuarios.getFoto_usuario());
                                            Picasso.get().load(String.valueOf(urlfeed))
                                                    .error(R.mipmap.ic_launcher)
                                                    .fit()
                                                    .noFade()
                                                    .into(imagen);
                                        } catch (MalformedURLException e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        loadImageFromUrl(usuarios.getFoto_usuario());
                                        progressDialog.dismiss();
                                    }

                                }


                        }

                    }
                }else{
                    Log.e(TAG,"Usuarios 3: ");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG,"ERROR: ");
            }
        });
    }


    private void loadImageFromUrl(String url) {

        Picasso.get().load(url).into(imagen);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onBackPressed() {
        finish();
    }
}
