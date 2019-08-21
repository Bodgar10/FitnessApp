package com.appfitnessapp.app.fitnessapp.Admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appfitnessapp.app.fitnessapp.Adapters.AdapterAsesorias;
import com.appfitnessapp.app.fitnessapp.Arrays.Asesorias;
import com.appfitnessapp.app.fitnessapp.Arrays.Usuarios;
import com.appfitnessapp.app.fitnessapp.BaseDatos.BajarInfo;
import com.appfitnessapp.app.fitnessapp.BaseDatos.Contants;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

public class AsesoriasPendientes extends AppCompatActivity {

    AdapterAsesorias adapter;
    ArrayList<Usuarios> asesorias;
    RecyclerView recyclerView;
    TextView txtActivos;
    CircularImageView imgPostPersona;


    private ProgressDialog progressDialog;
    private static final String TAG = "BAJARINFO:";
    static DBProvider dbProvider;
    BajarInfo bajarInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_04_asesorias);

        Toolbar toolbarback=findViewById(R.id.toolbar);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("Asesorias");


        bajarUsuarios();

        dbProvider = new DBProvider();
        bajarInfo = new BajarInfo();
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);

        recyclerView=findViewById(R.id.recyclerview);
        txtActivos=findViewById(R.id.txtActivos);

        imgPostPersona=findViewById(R.id.imgPostPersona);



        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        asesorias=new ArrayList<>();
        adapter=new AdapterAsesorias(asesorias);
        recyclerView.setAdapter(adapter);




        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AsesoriasPendientes.this, SolicitudAsesoria.class);
                Bundle bundle = new Bundle();
                bundle.putString("id",asesorias.get(recyclerView.getChildAdapterPosition(v)).getId_usuario());
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });


        txtActivos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AsesoriasPendientes.this, AsesoriasAdmin.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        imgPostPersona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AsesoriasPendientes.this, AdminPerfil.class);
                startActivity(intent);

            }
        });

    }

    public void bajarUsuarios(){
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
}
