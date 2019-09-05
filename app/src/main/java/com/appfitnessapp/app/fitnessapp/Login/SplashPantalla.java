package com.appfitnessapp.app.fitnessapp.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.appfitnessapp.app.fitnessapp.Admin.AsesoriasAdmin;
import com.appfitnessapp.app.fitnessapp.Arrays.Usuarios;
import com.appfitnessapp.app.fitnessapp.BaseDatos.BajarInfo;
import com.appfitnessapp.app.fitnessapp.BaseDatos.Contants;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.R;
import com.appfitnessapp.app.fitnessapp.Usuario.DetallePdf;
import com.appfitnessapp.app.fitnessapp.Usuario.FeedSinRegistro.HomeSinRegistro;
import com.appfitnessapp.app.fitnessapp.Usuario.UsuarioHome;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class SplashPantalla extends AppCompatActivity {

    private static final String TAG = "Principal: ";

    Button btnIniciarSesion,btnRegistro,btnOmitir;

    BajarInfo bajarInfo;
    DBProvider dbProvider;

    private ProgressDialog progressDialog;
    private static FirebaseAuth mAuth;

    private AlphaAnimation buttonClick = new AlphaAnimation(3F, 0.9F);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_01_splash);

        Toolbar toolbarback=findViewById(R.id.toolbar);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        FirebaseApp.initializeApp(this);


        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);

        bajarInfo = new BajarInfo();
        dbProvider = new DBProvider();

        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            progressDialog.setMessage("Recopilando información importante...");
            progressDialog.show();
            progressDialog.setCancelable(false);
            bajarUsuarios();
        }


        btnIniciarSesion=findViewById(R.id.btnIniciarSesionsplash);
        btnRegistro=findViewById(R.id.btnRegistroSplash);
        btnOmitir=findViewById(R.id.btnOmitir);


        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(buttonClick);
                Intent intent = new Intent(SplashPantalla.this, IniciarSesion.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.move, R.anim.move_leeft);


            }
        });

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(buttonClick);
                Intent intent = new Intent(SplashPantalla.this, Registro.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.move, R.anim.move_leeft);


            }
        });


        btnOmitir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(buttonClick);
                Intent intent = new Intent(SplashPantalla.this, HomeSinRegistro.class);
                startActivity(intent);


            }
        });
    }

    public void bajarUsuarios(){
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Log.e(TAG,"Usuarios 2: ");
        dbProvider = new DBProvider();

        dbProvider.usersRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e(TAG,"Usuarios 4: ");

                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                        Log.e(TAG,"Usuarios: "+ snapshot);
                        Usuarios usuario = snapshot.getValue(Usuarios.class);

                            if (usuario.getId_usuario().equals(user.getUid())) {
                                //yaCreado = true;
                                progressDialog.dismiss();
                                if (usuario.getTipo_usuario().equals(Contants.USUARIO)) {
                                    progressDialog.dismiss();
                                    Intent intent = new Intent(SplashPantalla.this, UsuarioHome.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                }

                                else if (usuario.getTipo_usuario().equals(Contants.ADMIN)) {
                                    progressDialog.dismiss();
                                    Intent intent = new Intent(SplashPantalla.this, AsesoriasAdmin.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    Toast.makeText(SplashPantalla.this, "Aún tu cuenta no está activa, te contactaremos cuando esté lista.", Toast.LENGTH_SHORT).show();
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
        overridePendingTransition(
                getIntent().getIntExtra("anim id in", R.anim.move_in),
                getIntent().getIntExtra("anim id out", R.anim.move_leeft_in));
    }
}
