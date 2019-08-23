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
    EditText edtDescripcion,edtTiempo,edtNivel,edtNumEjercicios;

    ArrayAdapter<String> Sdia;
    Spinner spinnerDia;

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

        Sdia = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, Contants.dias_ejercicios);


        spinnerDia.setAdapter(Sdia);
        spinnerDia.setPrompt("Dia de ejercicio");

        spinnerDia.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ((TextView) spinnerDia.getSelectedView()).setTextColor(Color.GRAY);
            }
        });



        btnRecetas=findViewById(R.id.btnRecetas);
        btnAgregarEjercicio=findViewById(R.id.btnAgregarEjercicio);

        edtDescripcion=findViewById(R.id.edtDescripcion);

        edtTiempo=findViewById(R.id.edtTiempo);
        edtNivel=findViewById(R.id.edtNivel);
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
                String nivel = edtNivel.getText().toString();
                String ejercicios = edtNumEjercicios.getText().toString();
                String diaEscogido = spinnerDia.getSelectedItem().toString();
                keyPlan=key;

                if (!descripcion.isEmpty()&&!tiempo.isEmpty()&&!nivel.isEmpty()&&!ejercicios.isEmpty()){

                    switch (diaEscogido) {
                        case "Domingo":
                            dbProvider.subirPlanEjercicio(tiempo, nivel, ejercicios, descripcion, key, id, "1");
                            Intent intent = new Intent(EscogerPlan.this, AgregarEjercicios.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("key",keyPlan);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            //dbProvider.subirImagenesEjercicios("nil","nil","nil",key);
                            break;
                        case "Lunes":
                            dbProvider.subirPlanEjercicio(tiempo, nivel, ejercicios,
                                    descripcion, key, id, "2");

                            break;
                        case "Martes":
                            dbProvider.subirPlanEjercicio(tiempo, nivel, ejercicios, descripcion, key, id, "3");
                            break;
                        case "Miercoles":
                            dbProvider.subirPlanEjercicio(tiempo, nivel, ejercicios, descripcion, key, id, "4");

                            break;
                        case "Jueves":
                            dbProvider.subirPlanEjercicio(tiempo, nivel, ejercicios, descripcion, key, id, "5");

                            break;
                        case "Viernes":
                            dbProvider.subirPlanEjercicio(tiempo, nivel, ejercicios, descripcion, key, id, "6");

                            break;
                        case "Sabado":
                            dbProvider.subirPlanEjercicio(tiempo, nivel, ejercicios, descripcion, key, id, "7");

                            break;
                    }
            }
                else {
                    Toast.makeText(EscogerPlan.this, "Revisa  que todos los campos esten llenos.", Toast.LENGTH_SHORT).show();

                }


                //ejercicios Solos
                /*
                String key = dbProvider.tablaEjercicios().push().getKey();
                uploadVideo(videoUri,"irjirjf","nil","nil",key);
                uploadImage3(key,imagen1Uri.toString(),imagen2Uri.toString(),imagen3Uri.toString());

                */

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
