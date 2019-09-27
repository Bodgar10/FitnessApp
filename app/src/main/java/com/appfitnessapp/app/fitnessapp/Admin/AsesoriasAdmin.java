package com.appfitnessapp.app.fitnessapp.Admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appfitnessapp.app.fitnessapp.Adapters.AdapterAsesorias;
import com.appfitnessapp.app.fitnessapp.Arrays.Inscritos;
import com.appfitnessapp.app.fitnessapp.Arrays.Usuarios;
import com.appfitnessapp.app.fitnessapp.BaseDatos.BajarInfo;
import com.appfitnessapp.app.fitnessapp.BaseDatos.Contants;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.Login.SplashPantalla;
import com.appfitnessapp.app.fitnessapp.R;
import com.appfitnessapp.app.fitnessapp.Usuario.Formulario;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AsesoriasAdmin extends AppCompatActivity {

    AdapterAsesorias adapter,adapter2;
    ArrayList<Usuarios> asesorias,asesorias2;
    RecyclerView recyclerReciente,recyclerFinalizar;
    TextView txtPendientes;
    CircularImageView imgPostPersona;
    LinearLayout btnFormulario;
    ImageButton  imgFormulario;

    private ProgressDialog progressDialog;
    private static final String TAG = "BAJARINFO:";
    static DBProvider dbProvider;
    BajarInfo bajarInfo;

    String id;

    private static FirebaseAuth mAuth;

    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy",Locale.getDefault());
    Date date = new Date();
    Calendar cal = Calendar.getInstance();
    int anioActual,mesActual;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_03_asesorias);

        Toolbar toolbarback=findViewById(R.id.toolbar);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("Asesorías");

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user==null){
            Intent intent=new Intent(AsesoriasAdmin.this, SplashPantalla.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        else{
            id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        }





        dbProvider = new DBProvider();
        bajarInfo = new BajarInfo();
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);

        btnFormulario=findViewById(R.id.btnFormulario);


        recyclerFinalizar=findViewById(R.id.recyclerFinalizar);
        recyclerReciente=findViewById(R.id.recyclerRecientes);
        txtPendientes=findViewById(R.id.txtPendientes);

        imgPostPersona=findViewById(R.id.imgPostPersona);
        imgFormulario=findViewById(R.id.imgFormulario);



        recyclerFinalizar.setLayoutManager(new LinearLayoutManager(this));
        recyclerReciente.setLayoutManager(new LinearLayoutManager(this));
        asesorias=new ArrayList<>();
        asesorias2=new ArrayList<>();
        adapter=new AdapterAsesorias(asesorias);
        adapter2=new AdapterAsesorias(asesorias2);
        recyclerFinalizar.setAdapter(adapter);
        recyclerReciente.setAdapter(adapter2);




        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AsesoriasAdmin.this, PerfilUsuarioAdmin.class);
                Bundle bundle = new Bundle();
                bundle.putString("id",asesorias.get(recyclerFinalizar.getChildAdapterPosition(v)).getId_usuario());
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        adapter2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AsesoriasAdmin.this, PerfilUsuarioAdmin.class);
                Bundle bundle = new Bundle();
                bundle.putString("id",asesorias2.get(recyclerReciente.getChildAdapterPosition(v)).getId_usuario());
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });



        txtPendientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AsesoriasAdmin.this, AsesoriasPendientes.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.move_in, R.anim.move_leeft_in);


            }
        });

        imgPostPersona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AsesoriasAdmin.this, AdminPerfil.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.move_in, R.anim.move_leeft_in);

            }
        });


        btnFormulario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AsesoriasAdmin.this, FormularioLista.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.move_in, R.anim.move_leeft_in);
            }
        });

        imgFormulario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AsesoriasAdmin.this, FormularioLista.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.move_in, R.anim.move_leeft_in);
            }
        });

        bajarInscritos();

    }



    public void bajarInscritos(){

        dbProvider = new DBProvider();

        dbProvider.tablaInscritos().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    asesorias.clear();
                    asesorias2.clear();
                    adapter.notifyDataSetChanged();
                    adapter2.notifyDataSetChanged();
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                        Log.e(TAG, "Usuarios: " + snapshot);
                        Inscritos inscritos = snapshot.getValue(Inscritos.class);

                        //semana actual y mes
                        cal.setTime(date);
                        anioActual = cal.get(Calendar.YEAR);
                        mesActual = cal.get(Calendar.MONTH) + 1;

                        String fechaBase =inscritos.getFecha_limite();
                        DateFormat dateFormattt = new SimpleDateFormat("dd-MM-yyyy",Locale.getDefault());
                        try {
                            Date convertedDate=dateFormattt.parse(fechaBase);
                            Calendar c = Calendar.getInstance();
                            c.setTime(convertedDate);
                            //sacar mes y anio base

                            int anioBase = c.get(Calendar.YEAR);
                            int mesBase = c.get(Calendar.MONTH) + 1;
                            int diaBase = c.get(Calendar.DAY_OF_MONTH);

                            if (inscritos.getId_pendiente().equals(false)){
                                Log.e(TAG, "INSCRITOS AÑO Y MES DEL USUARIO: " + anioBase + " " + mesBase + " " + diaBase + " " + fechaBase);
                                Log.e(TAG, "INSCRITOS AÑO Y MES DEL HOY: " + anioActual + " " + mesActual);

                                if (anioBase == anioActual) {
                                    if (mesBase == mesActual) {
                                        Log.e(TAG, "INSCRITOS BIEN: " + snapshot);
                                        bajarUsuarios2(inscritos.getId_usuario());
                                    }else if (mesBase > mesActual) {
                                        Log.e(TAG, "INSCRITOS BIEN 2: " + snapshot);
                                        bajarUsuarios(inscritos.getId_usuario());
                                    }
                                }else if (anioBase > anioActual) {
                                    Log.e(TAG, "INSCRITOS BIEN 2: " + snapshot);
                                    bajarUsuarios(inscritos.getId_usuario());
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
            }
        });
    }


    public void bajarUsuarios(final String id_usuario){
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
                            if (usuarios.getTipo_usuario().equals(Contants.USUARIO)) {
                                if (usuarios.getId_usuario().equals(id_usuario)) {
                                    asesorias2.add(usuarios);
                                    adapter2.notifyDataSetChanged();
                                    progressDialog.dismiss();
                                }

                            }
                            else if (usuarios.getTipo_usuario().equals(Contants.ADMIN)) {

                                if (usuarios.getId_usuario().equals(id)) {

                                    if (usuarios.getFoto_usuario().equals("nil")) {
                                        try {
                                            URL urlfeed = new URL(usuarios.getFoto_usuario());
                                            Picasso.get().load(String.valueOf(urlfeed))
                                                    .error(R.mipmap.ic_launcher)
                                                    .fit()
                                                    .noFade()
                                                    .into(imgPostPersona);
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


    public void bajarUsuarios2(final String id_usuario2){
        Log.e(TAG,"Pendiente 2: ");
        dbProvider = new DBProvider();

        dbProvider.usersRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e(TAG,"Pendiente 4: ");
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                        Log.e(TAG, "Pendiente: " + snapshot);
                        Usuarios usuarios = snapshot.getValue(Usuarios.class);

                        if (usuarios.getId_usuario()!=null) {
                            if (usuarios.getId_usuario().equals(id_usuario2)) {
                                Log.e(TAG, "Pendiente Bien: " + snapshot);
                                asesorias.add(usuarios);
                                adapter.notifyDataSetChanged();
                                progressDialog.dismiss();
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

        Picasso.get().load(url).into(imgPostPersona);
    }


}
