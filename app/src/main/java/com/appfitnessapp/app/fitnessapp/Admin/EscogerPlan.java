package com.appfitnessapp.app.fitnessapp.Admin;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.appfitnessapp.app.fitnessapp.BaseDatos.Contants;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.R;
import com.appfitnessapp.app.fitnessapp.Usuario.Calificar;
import com.appfitnessapp.app.fitnessapp.subirArchivos;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class EscogerPlan extends AppCompatActivity {

    TextView btnRecetas,btnGuardar;
    ImageView btnAgregarEjercicio;
    EditText edtDescripcion,edtTiempo,edtNumEjercicios;

    ArrayAdapter<String> Sdia;
    ArrayAdapter<String> SNivel;

    Spinner spinnerDia,spinnerNivel;

    RecyclerView recyclerView;

    private static final String TAG = "BAJARINFO:";
    static DBProvider dbProvider;

    String id,keyPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_07_escogerplan);

        Toolbar toolbarback=findViewById(R.id.toolbar);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("Plan de ejercicios");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getString("id");
        }

        dbProvider = new DBProvider();

        recyclerView = findViewById(R.id.recyclerview);

        spinnerDia = findViewById(R.id.spinnerDia);

        spinnerNivel = findViewById(R.id.spinnerNivel);


        Sdia = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, Contants.dias_ejercicios);

        SNivel = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, Contants.nivel_ejercicio);



        spinnerDia.setAdapter(Sdia);
        spinnerDia.setPrompt("Día de ejercicio");

        spinnerNivel.setAdapter(SNivel);
        spinnerNivel.setPrompt("Nivel de ejercicio");

        spinnerDia.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ((TextView) spinnerDia.getSelectedView()).setTextColor(Color.GRAY);
            }
        });


        spinnerNivel.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ((TextView) spinnerNivel.getSelectedView()).setTextColor(Color.GRAY);
            }
        });

        btnRecetas=findViewById(R.id.btnRecetas);
        btnAgregarEjercicio=findViewById(R.id.btnAgregarEjercicio);

        edtDescripcion=findViewById(R.id.edtDescripcion);

        edtTiempo=findViewById(R.id.edtTiempo);
        edtNumEjercicios=findViewById(R.id.edtNumEjercicios);


        btnGuardar=findViewById(R.id.txtGuardar);


        btnRecetas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnAgregarEjercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });



        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = dbProvider.tablaPlanEntrenamiento().push().getKey();
                String descripcion = edtDescripcion.getText().toString();
                String tiempo = edtTiempo.getText().toString();
                String ejercicios = edtNumEjercicios.getText().toString();
                String diaEscogido = spinnerDia.getSelectedItem().toString();
                String nivelEjercicio = spinnerNivel.getSelectedItem().toString();

                keyPlan=key;

                if (!descripcion.isEmpty()&&!tiempo.isEmpty()&&!ejercicios.isEmpty()){


                    //ejercicios Solo
                    /*
                String keyEjercicio = dbProvider.tablaEjercicios().push().getKey();
                dbProvider.subirEjercicios(tiempo,nivelEjercicio,ejercicios,descripcion,keyEjercicio,id,"1");
                */

                    switch (diaEscogido) {
                        case "Domingo":
                            dbProvider.subirPlanEjercicio(tiempo, nivelEjercicio, ejercicios, descripcion, key, id, "1");
                            Intent intent = new Intent(EscogerPlan.this, EjerciciosLista.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("key",keyPlan);
                            bundle.putString("id",id);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            //dbProvider.subirImagenesEjercicios("nil","nil","nil",key);
                            break;
                        case "Lunes":
                            dbProvider.subirPlanEjercicio(tiempo, nivelEjercicio, ejercicios, descripcion, key, id, "2");
                            Intent intent1 = new Intent(EscogerPlan.this, EjerciciosLista.class);
                            Bundle bundle1 = new Bundle();
                            bundle1.putString("key",keyPlan);
                            bundle1.putString("id",id);
                            intent1.putExtras(bundle1);
                            startActivity(intent1);

                            break;
                        case "Martes":
                            dbProvider.subirPlanEjercicio(tiempo, nivelEjercicio, ejercicios, descripcion, key, id, "3");
                            Intent intent2 = new Intent(EscogerPlan.this, EjerciciosLista.class);
                            Bundle bundle2 = new Bundle();
                            bundle2.putString("key",keyPlan);
                            bundle2.putString("id",id);
                            intent2.putExtras(bundle2);
                            startActivity(intent2);
                            break;
                        case "Miercoles":
                            dbProvider.subirPlanEjercicio(tiempo, nivelEjercicio, ejercicios, descripcion, key, id, "4");
                            Intent intent3 = new Intent(EscogerPlan.this, EjerciciosLista.class);
                            Bundle bundle3 = new Bundle();
                            bundle3.putString("key",keyPlan);
                            bundle3.putString("id",id);
                            intent3.putExtras(bundle3);
                            startActivity(intent3);

                            break;
                        case "Jueves":
                            dbProvider.subirPlanEjercicio(tiempo, nivelEjercicio, ejercicios, descripcion, key, id, "5");
                            Intent intent4 = new Intent(EscogerPlan.this, EjerciciosLista.class);
                            Bundle bundle4 = new Bundle();
                            bundle4.putString("key",keyPlan);
                            bundle4.putString("id",id);
                            intent4.putExtras(bundle4);
                            startActivity(intent4);

                            break;
                        case "Viernes":
                            dbProvider.subirPlanEjercicio(tiempo, nivelEjercicio, ejercicios, descripcion, key, id, "6");
                            Intent intent5 = new Intent(EscogerPlan.this, EjerciciosLista.class);
                            Bundle bundle5 = new Bundle();
                            bundle5.putString("key",keyPlan);
                            bundle5.putString("id",id);
                            intent5.putExtras(bundle5);
                            startActivity(intent5);

                            break;
                        case "Sabado":
                            dbProvider.subirPlanEjercicio(tiempo, nivelEjercicio, ejercicios, descripcion, key, id, "7");
                            Intent intent6 = new Intent(EscogerPlan.this, EjerciciosLista.class);
                            Bundle bundle6 = new Bundle();
                            bundle6.putString("key",keyPlan);
                            bundle6.putString("id",id);
                            intent6.putExtras(bundle6);
                            startActivity(intent6);

                            break;
                    }
            }
                else {
                    Toast.makeText(EscogerPlan.this, "Revisa  que todos los campos esten llenos.", Toast.LENGTH_SHORT).show();

                }




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
