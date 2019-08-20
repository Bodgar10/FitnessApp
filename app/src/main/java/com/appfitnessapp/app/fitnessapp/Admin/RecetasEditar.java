package com.appfitnessapp.app.fitnessapp.Admin;

import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appfitnessapp.app.fitnessapp.Adapters.AdapterIngredientes;
import com.appfitnessapp.app.fitnessapp.Arrays.Ingredientes;
import com.appfitnessapp.app.fitnessapp.BaseDatos.Contants;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.R;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class RecetasEditar extends AppCompatActivity {

    TextView btnWorkouts,btnGuardar;
    EditText edtNombreComida,edtTiempo,edtCantidad, edtCalorias;
    ImageButton btnIngrediente,btnPaso;

    ArrayList<Ingredientes> ingredientes;
    RecyclerView recyclerView,recyclerPreparacion,recyclerviewIngrediente;
    AdapterIngredientes adapterIngredientes;

    String id;
    private static final String TAG = "BAJARINFO:";
    static DBProvider dbProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_06_escogerplan);

        Toolbar toolbarback=findViewById(R.id.toolbar);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("Desayuno");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        btnWorkouts=findViewById(R.id.txtWorkoutsAdmin);
        btnGuardar=findViewById(R.id.txtGuardar);

        edtNombreComida=findViewById(R.id.edtNombreComida);
        edtTiempo=findViewById(R.id.edtTiempo);
        edtCantidad=findViewById(R.id.edtCantidad);
        edtCalorias=findViewById(R.id.edtCalorias);


        btnIngrediente=findViewById(R.id.btnAgregarIngrediente);
        btnPaso=findViewById(R.id.btnPaso);

        recyclerView=findViewById(R.id.recyclerview);
        recyclerPreparacion=findViewById(R.id.recyclerPreparacion);
        recyclerviewIngrediente=findViewById(R.id.recyclerviewIngrediente);


        recyclerviewIngrediente.setLayoutManager(new LinearLayoutManager(this));
        recyclerPreparacion.setLayoutManager(new LinearLayoutManager(this));
        ingredientes=new ArrayList<>();
        adapterIngredientes=new AdapterIngredientes(ingredientes);
        recyclerviewIngrediente.setAdapter(adapterIngredientes);

        dbProvider = new DBProvider();
//        id = FirebaseAuth.getInstance().getCurrentUser().getUid();



        adapterIngredientes.notifyDataSetChanged();
        adapterIngredientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnWorkouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RecetasEditar.this, EscogerPlan.class);
                startActivity(intent);

            }
        });


        btnIngrediente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RecyclerView.LayoutManager lmmanager = new LinearLayoutManager(getApplicationContext()); recyclerviewIngrediente.setLayoutManager(lmmanager);
                ingredientes.add(new Ingredientes("","",""));
                adapterIngredientes = new AdapterIngredientes(ingredientes);
                recyclerviewIngrediente.setAdapter(adapterIngredientes);


            }
        });


        btnPaso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String nombre = Objects.requireNonNull(edtNombreComida.getText()).toString();
                String cantidad = Objects.requireNonNull(edtCantidad.getText()).toString();
                String calorias = Objects.requireNonNull(edtCalorias.getText()).toString();
                String tiempo = Objects.requireNonNull(edtTiempo.getText()).toString();

                String key =dbProvider.tablaPlanEntrenamiento().push().getKey();


                /*
                String key = dbProvider.tablaPlanAlimenticio().push().getKey();
                    dbProvider.subirPlanAlimenticio(key,id,"",
                            "2000","30 min","6 porciones","Tacos Veganos",
                            "desayuno", "$200", "$100");

                    dbProvider.subirPreparacion(key,"Paso 1","Picar la verdura que ocuparas");
                    dbProvider.subirIngredientes(key,"Ensalada","1 Pieza");
*/


                dbProvider.subirPlanEjercicio("12","Alta intensidad","8 ejercicios",
                        "Buen ejericico para ponerte en forma", key,"nil","1");


                dbProvider.subirEjerciciosPlan("Saltos de 30 rondas","12"," 20","",
                        key);

                dbProvider.subirImagenesEjercicios("nil",key);

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
