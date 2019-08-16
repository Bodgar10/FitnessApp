package com.appfitnessapp.app.fitnessapp.Admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.appfitnessapp.app.fitnessapp.Arrays.Usuarios;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.R;
import com.appfitnessapp.app.fitnessapp.Usuario.EditarPerfil;
import com.appfitnessapp.app.fitnessapp.Usuario.UsuarioPerfil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;

public class AdminPerfil extends AppCompatActivity {

    TextView txtNombre,txtAsesorias,txtPendiente,txtCalificacion,txtCorreo,txtContrasena,txtGanancias;
    ImageView imagen;

    private ProgressDialog progressDialog;
    private static final String TAG = "BAJARINFO:";
    static DBProvider dbProvider;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_12_perfil);

        Toolbar toolbarback=findViewById(R.id.toolbar);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);

        dbProvider = new DBProvider();

        id = FirebaseAuth.getInstance().getCurrentUser().getUid();


        txtNombre=findViewById(R.id.txtNombreAdmin);
        txtAsesorias=findViewById(R.id.txtNumeroAsesorias);
        txtPendiente=findViewById(R.id.txtPendientes);
        txtCalificacion=findViewById(R.id.txtCalificacion);
        txtCorreo=findViewById(R.id.txtEmail);
        txtContrasena=findViewById(R.id.txtContrasena);
        txtGanancias=findViewById(R.id.txtGanancias);

        imagen=findViewById(R.id.imgAsesoria);

        bajarUsuarios();
    }


    public void bajarUsuarios(){
        dbProvider = new DBProvider();

        progressDialog.setMessage("Cargando Información...");
        progressDialog.show();


        dbProvider.usersRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e(TAG, "Usuarios 4: ");
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        //Log.e(TAG,"Usuarios: "+ snapshot);
                        Log.e(TAG, "Usuarios: " + snapshot);
                        Usuarios usuarios = snapshot.getValue(Usuarios.class);


                        if (usuarios.getId_usuario().equals(id)) {

                            txtNombre.setText(usuarios.getNombre_usuario());
                            txtCorreo.setText(usuarios.getEmail_usuario());
                            txtContrasena.setText(usuarios.getContrasena_usuario());


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
                                progressDialog.dismiss();
                            }
                            else {
                                loadImageFromUrl(usuarios.getFoto_usuario());
                                progressDialog.dismiss();
                            }
                        }


                    }
                } else {
                    Log.e(TAG, "Usuarios 3: ");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "ERROR: ");
            }
        });
    }

    private void loadImageFromUrl(String url) {

        Picasso.get().load(url).into(imagen);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.editar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.editar:
                Abrir();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void Abrir() {
        //Intent intent=new Intent(AdminPerfil.this,EditarPerfil.class);
        //startActivity(intent);

    }
}
