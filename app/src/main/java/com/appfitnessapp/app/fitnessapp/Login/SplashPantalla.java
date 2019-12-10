package com.appfitnessapp.app.fitnessapp.Login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.appfitnessapp.app.fitnessapp.Arrays.Inscritos;
import com.appfitnessapp.app.fitnessapp.Arrays.Usuarios;
import com.appfitnessapp.app.fitnessapp.BaseDatos.BajarInfo;
import com.appfitnessapp.app.fitnessapp.BaseDatos.Contants;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.R;
import com.appfitnessapp.app.fitnessapp.Usuario.DatosUsuario;
import com.appfitnessapp.app.fitnessapp.Usuario.DetallePdf;
import com.appfitnessapp.app.fitnessapp.Usuario.FeedSinRegistro.HomeSinRegistro;
import com.appfitnessapp.app.fitnessapp.Usuario.MenuRegistro.Home;
import com.appfitnessapp.app.fitnessapp.Usuario.UsuarioHome;
import com.appfitnessapp.app.fitnessapp.menu_nuevo.Menu_UPago.Menu_UsuarioPago;
import com.appfitnessapp.app.fitnessapp.menu_nuevo.Menu_Usuario;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SplashPantalla extends AppCompatActivity {

    private static final String TAG = "Principal: ";

    Button btnIniciarSesion,btnRegistro;

    BajarInfo bajarInfo;
    DBProvider dbProvider;


    private ProgressDialog progressDialog;
    private static FirebaseAuth mAuth;

    private AlphaAnimation buttonClick = new AlphaAnimation(3F, 0.9F);

    Boolean isPagado =false;

    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy",Locale.getDefault());
    Date date = new Date();
    Calendar cal = Calendar.getInstance();
    int anioActual,mesActual,diaActual;

    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


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


        isPagado=true;


        if (isOnline(this)){

            if (FirebaseAuth.getInstance().getCurrentUser() != null){
                progressDialog.setMessage("Recopilando información importante...");
                progressDialog.show();
                progressDialog.setCancelable(false);
                bajarInscritos(user.getUid());
                new CountDownTimer(2000,1){

                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {

                        bajarUsuarios();
                    }
                }.start();
            }
        }
        else {
            Toast.makeText(this, "No hay conexion a internet...", Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }





        btnIniciarSesion=findViewById(R.id.btnIniciarSesionsplash);
        btnRegistro=findViewById(R.id.btnRegistroSplash);


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


    }

    public void bajarInscritos(final String id_usuario){
        dbProvider = new DBProvider();

        dbProvider.tablaInscritos().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                        Log.e(TAG, "Usuarios: " + snapshot);
                        Inscritos inscritos = snapshot.getValue(Inscritos.class);


                        //semana actual y mes
                        cal.setTime(date);
                        anioActual = cal.get(Calendar.YEAR);
                        mesActual = cal.get(Calendar.MONTH)+1;
                        diaActual = cal.get(Calendar.DAY_OF_MONTH);


                        String fechaBase =inscritos.getFecha_limite();
                        DateFormat dateFormattt = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                        try {
                            Date convertedDate=dateFormattt.parse(fechaBase);
                            Calendar c = Calendar.getInstance();
                            c.setTime(convertedDate);
                            //sacar mes y anio base
                            int anioBase = c.get(Calendar.YEAR);
                            int mesBase = c.get(Calendar.MONTH)+1;
                            int diaBase = c.get(Calendar.DAY_OF_MONTH);


                            if (inscritos.getId_usuario().equals(id_usuario)) {
                                    if (anioActual == anioBase) {
                                        if (mesBase == mesActual) {
                                            if (diaBase<diaActual){
                                                dbProvider.updateIsPagado(id_usuario, false);
                                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                            Query inscitos = ref.child(Contants.TABLA_INSCRITOS).orderByChild(Contants.ID_USUARIO).equalTo(id_usuario);
                                            inscitos.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    for (DataSnapshot inscritosSnapshot : dataSnapshot.getChildren()) {
                                                        inscritosSnapshot.getRef().removeValue();
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {
                                                    Log.e(TAG, "onCancelled", databaseError.toException());
                                                }
                                            });
                                        }
                                        }
                                       else if (mesBase < mesActual) {
                                            dbProvider.updateIsPagado(id_usuario, false);
                                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                            Query inscitos = ref.child(Contants.TABLA_INSCRITOS).orderByChild(Contants.ID_USUARIO).equalTo(id_usuario);
                                            inscitos.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    for (DataSnapshot inscritosSnapshot: dataSnapshot.getChildren()) {
                                                        inscritosSnapshot.getRef().removeValue();
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {
                                                    Log.e(TAG, "onCancelled", databaseError.toException());
                                                }
                                            });
                                        }

                                    }

                                }



                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                    }
                }else{
                    Log.e(TAG,"Usuarios 3: ");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG,"ERROR: ");
                progressDialog.dismiss();

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
                                isPagado = true;
                                progressDialog.dismiss();
                                if (usuario.getTipo_usuario().equals(Contants.USUARIO)) {
                                    if (!usuario.getPagado()==false){
                                        progressDialog.dismiss();
                                        Intent intent = new Intent(SplashPantalla.this, Menu_UsuarioPago.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        finish();

                                    }
                                    else {
                                        progressDialog.dismiss();
                                        Intent intent = new Intent(SplashPantalla.this, Menu_Usuario.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        SplashPantalla.this.finish();
                                    }

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
                    progressDialog.dismiss();

                }
            }



            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG,"ERROR: ");
            }
        });


    }

    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
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
