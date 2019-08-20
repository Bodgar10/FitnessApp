package com.appfitnessapp.app.fitnessapp.Admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appfitnessapp.app.fitnessapp.Adapters.AdapterAsesorias;
import com.appfitnessapp.app.fitnessapp.Adapters.AdapterIngredientes;
import com.appfitnessapp.app.fitnessapp.Arrays.Asesorias;
import com.appfitnessapp.app.fitnessapp.Arrays.Inscritos;
import com.appfitnessapp.app.fitnessapp.Arrays.Usuarios;
import com.appfitnessapp.app.fitnessapp.BaseDatos.BajarInfo;
import com.appfitnessapp.app.fitnessapp.BaseDatos.Contants;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.Login.IniciarSesion;
import com.appfitnessapp.app.fitnessapp.R;
import com.appfitnessapp.app.fitnessapp.Usuario.Asesoria;
import com.appfitnessapp.app.fitnessapp.Usuario.DetalleRecetas;
import com.appfitnessapp.app.fitnessapp.Usuario.InformacionCompra;
import com.appfitnessapp.app.fitnessapp.Usuario.UsuarioHome;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AsesoriasAdmin extends AppCompatActivity {

    AdapterAsesorias adapter;
    ArrayList<Usuarios> asesorias;
    RecyclerView recyclerReciente,recyclerFinalizar;
    TextView txtPendientes;
    CircularImageView imgPostPersona;

    private ProgressDialog progressDialog;
    private static final String TAG = "BAJARINFO:";
    static DBProvider dbProvider;
    BajarInfo bajarInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_03_asesorias);

        Toolbar toolbarback=findViewById(R.id.toolbar);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("Asesorias");


        dbProvider = new DBProvider();
        bajarInfo = new BajarInfo();
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);



        recyclerFinalizar=findViewById(R.id.recyclerFinalizar);
        recyclerReciente=findViewById(R.id.recyclerRecientes);
        txtPendientes=findViewById(R.id.txtPendientes);

        imgPostPersona=findViewById(R.id.imgPostPersona);



        recyclerFinalizar.setLayoutManager(new LinearLayoutManager(this));
        recyclerReciente.setLayoutManager(new LinearLayoutManager(this));
        asesorias=new ArrayList<>();
        adapter=new AdapterAsesorias(asesorias);
        recyclerFinalizar.setAdapter(adapter);
        recyclerReciente.setAdapter(adapter);


/*

        Asesorias asesorias0=new Asesorias("Pedro Ortiz","68 kg","Bajar  de peso","");
        Asesorias asesorias1=new Asesorias("Fernanda Ramirez","70 kg","Aumentar musculo","");
        Asesorias asesorias2=new Asesorias("Mauricio Garcia","60 kg","Subir de peso","");
        Asesorias asesorias3=new Asesorias("Pedro Ortiz","68 kg","Bajar  de peso","");


        asesorias.add(asesorias0);
        asesorias.add(asesorias1);
        asesorias.add(asesorias2);
        asesorias.add(asesorias3);

*/


      //  adapter.notifyDataSetChanged();

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AsesoriasAdmin.this, PerfilUsuarioAdmin.class);
                startActivity(intent);

            }
        });


        txtPendientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AsesoriasAdmin.this, AsesoriasPendientes.class);
                startActivity(intent);

            }
        });

        imgPostPersona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AsesoriasAdmin.this, AdminPerfil.class);
                startActivity(intent);

            }
        });

        bajarUsuarios();

    }


    public void bajarUsuarios(){
        Log.e(TAG,"Usuarios 2: ");
        dbProvider = new DBProvider();

        dbProvider.usersRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                asesorias.clear();
                Log.e(TAG,"Usuarios 4: ");
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                        Log.e(TAG, "Usuarios: " + snapshot);
                        Usuarios usuarios = snapshot.getValue(Usuarios.class);

                        //tabla inscritos checar si estaa activo o no y luego poner fecha si es actual o no
                        if (usuarios.getTipo_usuario().equals(Contants.USUARIO)) {

                        asesorias.add(usuarios);
                        adapter.notifyDataSetChanged();
                        progressDialog.dismiss();
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

    public void bajarInscritos(){
        Log.e(TAG,"Usuarios 2: ");
        dbProvider = new DBProvider();

        dbProvider.tablaInscritos().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                asesorias.clear();
                Log.e(TAG,"Usuarios 4: ");
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                        Log.e(TAG, "Usuarios: " + snapshot);
                        Inscritos inscritos = snapshot.getValue(Inscritos.class);

                        //fechaHoy
                        SimpleDateFormat dateMes = new SimpleDateFormat("MM", Locale.getDefault());
                        SimpleDateFormat dateAnio = new SimpleDateFormat("yyyy", Locale.getDefault());
                        Date date = new Date();
                        String mesHoy = dateMes.format(date);
                        String anioHoy = dateAnio.format(date);


                        //fechaBase
                        String fechaBase=inscritos.getFecha_limite();
                        SimpleDateFormat format1=new SimpleDateFormat("dd/MM/yyyy");
                        Date dt1;
                        try {
                            dt1 = format1.parse(fechaBase);
                            DateFormat format2=new SimpleDateFormat("MM");
                            DateFormat format3=new SimpleDateFormat("yyyy");
                            String mesBase=format2.format(dt1);
                            String AnioBase=format3.format(dt1);

                                if (inscritos.getId_pendiente().equals(false)){
                                    if (mesBase.equals(mesHoy)&&anioHoy.equals(AnioBase)) {




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
}
