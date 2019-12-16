package com.appfitnessapp.app.fitnessapp.Admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appfitnessapp.app.fitnessapp.Adapters.AdapterPreguntas;
import com.appfitnessapp.app.fitnessapp.Arrays.Preguntas;
import com.appfitnessapp.app.fitnessapp.BaseDatos.Contants;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FormularioLista extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterPreguntas adapter;
    ArrayList<Preguntas> preguntas;


    LinearLayout btnAsesoria,btnChat,btnFeed;


    TextView txtAgregar;

    String nombre,nombre1,nombre2,nombre3,nombre4,nombre5,nombre6,nombre7,nombre8,nombre9;
    private ProgressDialog progressDialog;
    private static final String TAG = "BAJARINFO:";
    static DBProvider dbProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_lista_formulario);

        Toolbar toolbarback=findViewById(R.id.toolbar);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("Formulario");


        btnAsesoria=findViewById(R.id.btnAsesoria);
        btnChat=findViewById(R.id.btnChat);
        btnFeed=findViewById(R.id.btnFeed);

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);

        txtAgregar=findViewById(R.id.txtAgregar);

        bajarPreguntas();




        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        preguntas=new ArrayList<>();
        adapter = new AdapterPreguntas(preguntas);
        recyclerView.setAdapter(adapter);



        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormularioLista.this, ListaChat.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.move_in, R.anim.move_leeft_in);
            }
        });

        btnFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormularioLista.this, AdminAgregarFeed.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.move_in, R.anim.move_leeft_in);
            }
        });


        btnAsesoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormularioLista.this, AsesoriasAdmin.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                overridePendingTransition(
                        getIntent().getIntExtra("anim id in", R.anim.move_in),
                        getIntent().getIntExtra("anim id out", R.anim.move_leeft_in));
            }
        });

        txtAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormularioLista.this, AgregarPregunta.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);


            }
        });

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormularioLista.this, EditarPregunta.class);
                Bundle bundle = new Bundle();
                bundle.putString("id_pregunta",preguntas.get(recyclerView.getChildAdapterPosition(v)).getId_pregunta());
                bundle.putString("nombre",preguntas.get(recyclerView.getChildAdapterPosition(v)).getNombre_pregunta());
                bundle.putString("pregunta",preguntas.get(recyclerView.getChildAdapterPosition(v)).getPregunta());
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

    }


    public void bajarPreguntas(){
        dbProvider = new DBProvider();

        progressDialog.setMessage("Cargando Información...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        dbProvider.formulario().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                preguntas.clear();
                Log.e(TAG, "Usuarios 4: ");
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        //Log.e(TAG,"Usuarios: "+ snapshot);
                        Preguntas pregunta = snapshot.getValue(Preguntas.class);
                        if (pregunta.getId_pregunta()!=null){
                            preguntas.add(pregunta);
                            adapter.notifyDataSetChanged();
                            progressDialog.dismiss();

                            nombre1=pregunta.nombre_pregunta;
                            nombre2=pregunta.nombre_pregunta;
                            nombre3=pregunta.nombre_pregunta;
                            nombre4=pregunta.nombre_pregunta;
                            nombre5=pregunta.nombre_pregunta;
                            nombre6=pregunta.nombre_pregunta;
                            nombre7=pregunta.nombre_pregunta;
                            nombre8=pregunta.nombre_pregunta;
                            nombre9=pregunta.nombre_pregunta;
                            nombre=pregunta.nombre_pregunta;
                           Log.e(TAG, "nombr:"+nombre);
                            if (nombre1.equals(Contants.PREGUNTA_1)&&nombre2.equals(Contants.PREGUNTA_2)&&
                                    nombre3.equals(Contants.PREGUNTA_3)&&nombre4.equals(Contants.PREGUNTA_4)&&
                                    nombre5.equals(Contants.PREGUNTA_5)&&nombre6.equals(Contants.PREGUNTA_6)&&
                                    nombre7.equals(Contants.PREGUNTA_7)&&nombre8.equals(Contants.PREGUNTA_8)&&
                                    nombre9.equals(Contants.PREGUNTA_9)){
                                txtAgregar.setVisibility(View.VISIBLE);
                            }
                            else if(nombre.equals(Contants.PREGUNTA_10)){
                                txtAgregar.setVisibility(View.INVISIBLE);

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
