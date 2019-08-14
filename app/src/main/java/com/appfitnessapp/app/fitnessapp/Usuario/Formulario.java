package com.appfitnessapp.app.fitnessapp.Usuario;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.appfitnessapp.app.fitnessapp.Adapters.AdapterPreguntas;
import com.appfitnessapp.app.fitnessapp.Adapters.AdapterRespuestas;
import com.appfitnessapp.app.fitnessapp.Arrays.Preguntas;
import com.appfitnessapp.app.fitnessapp.Arrays.Respuestas;
import com.appfitnessapp.app.fitnessapp.Arrays.Usuarios;
import com.appfitnessapp.app.fitnessapp.BaseDatos.Contants;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Formulario extends AppCompatActivity {


    EditText edtRespuesta1,edtRespuesta2,edtRespuesta3,edtRespuesta4,edtRespuesta5,edtRespuesta6,edtRespuesta7,edtRespuesta8,
            edtRespuesta9,edtRespuesta10;

    TextView txtPregunta1,txtPregunta2,txtPregunta3,txtPregunta4,txtPregunta5,txtPregunta6,txtPregunta7,txtPregunta8,
            txtPregunta9,txtPregunta10;

    String id_1="No",id_2="No",id_3="No",id_4="No",id_5="No",id_6="No",id_7="No",id_8="No",id_9="No",id_10="No";

    private ProgressDialog progressDialog;
    private static final String TAG = "BAJARINFO:";
    static DBProvider dbProvider;

    String id;

    Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_10_datos_formulario);

        Toolbar toolbarback=findViewById(R.id.toolbar);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("Formulario");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);

        id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        bajarPreguntas();

        txtPregunta1=findViewById(R.id.txtPregunta1);
        txtPregunta2=findViewById(R.id.txtPregunta2);
        txtPregunta3=findViewById(R.id.txtPregunta3);
        txtPregunta4=findViewById(R.id.txtPregunta4);
        txtPregunta5=findViewById(R.id.txtPregunta5);
        txtPregunta6=findViewById(R.id.txtPregunta6);
        txtPregunta7=findViewById(R.id.txtPregunta7);
        txtPregunta8=findViewById(R.id.txtPregunta8);
        txtPregunta9=findViewById(R.id.txtPregunta9);
        txtPregunta10=findViewById(R.id.txtPregunta10);


        edtRespuesta1=findViewById(R.id.edtRespuesta1);
        edtRespuesta2=findViewById(R.id.edtRespuesta2);
        edtRespuesta3=findViewById(R.id.edtRespuesta3);
        edtRespuesta4=findViewById(R.id.edtRespuesta4);
        edtRespuesta5=findViewById(R.id.edtRespuesta5);
        edtRespuesta6=findViewById(R.id.edtRespuesta6);
        edtRespuesta7=findViewById(R.id.edtRespuesta7);
        edtRespuesta8=findViewById(R.id.edtRespuesta8);
        edtRespuesta9=findViewById(R.id.edtRespuesta9);
        edtRespuesta10=findViewById(R.id.edtRespuesta10);


        btnGuardar=findViewById(R.id.btnGuardar);


        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = dbProvider.respuestas().getKey();

                if (id_1!="No"){

                    String respuesta1=edtRespuesta1.getText().toString();
                    if (respuesta1.isEmpty()){
                        Toast.makeText(Formulario.this, "Falta información. Revisa que tengas todo.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        dbProvider.subirRespuestas(id_1,key,id,respuesta1);
                    }

                }
                if (id_2!="No"){

                    String respuesta2=edtRespuesta2.getText().toString();
                    if (respuesta2.isEmpty()){
                        Toast.makeText(Formulario.this, "Falta información. Revisa que tengas todo.", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        dbProvider.subirRespuestas(id_2,key,id,respuesta2);
                    }

                }
                if (id_3!="No"){

                    String respuesta3=edtRespuesta3.getText().toString();
                    if (respuesta3.isEmpty()){
                        Toast.makeText(Formulario.this, "Falta información. Revisa que tengas todo.", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        dbProvider.subirRespuestas(id_3,key,id,respuesta3);
                    }

                }
                if (id_4!="No"){

                    String respuesta4=edtRespuesta4.getText().toString();
                    if (respuesta4.isEmpty()){
                        Toast.makeText(Formulario.this, "Falta información. Revisa que tengas todo.", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        dbProvider.subirRespuestas(id_4,key,id,respuesta4);
                    }

                }
                if (id_5!="No"){

                    String respuesta5=edtRespuesta5.getText().toString();
                    if (respuesta5.isEmpty()){
                        Toast.makeText(Formulario.this, "Falta información. Revisa que tengas todo.", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        dbProvider.subirRespuestas(id_5,key,id,respuesta5);
                    }

                }
                if (id_6!="No"){

                    String respuesta6=edtRespuesta6.getText().toString();
                    if (respuesta6.isEmpty()){
                        Toast.makeText(Formulario.this, "Falta información. Revisa que tengas todo.", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        dbProvider.subirRespuestas(id_6,key,id,respuesta6);
                    }

                }
                if (id_7!="No"){

                    String respuesta7=edtRespuesta7.getText().toString();
                    if (respuesta7.isEmpty()){
                        Toast.makeText(Formulario.this, "Falta información. Revisa que tengas todo.", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        dbProvider.subirRespuestas(id_7,key,id,respuesta7);
                    }

                }
                if (id_8!="No"){

                    String respuesta8=edtRespuesta8.getText().toString();
                    if (respuesta8.isEmpty()){
                        Toast.makeText(Formulario.this, "Falta información. Revisa que tengas todo.", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        dbProvider.subirRespuestas(id_8,key,id,respuesta8);
                    }

                }
                if (id_9!="No"){

                    String respuesta9=edtRespuesta9.getText().toString();
                    if (respuesta9.isEmpty()){
                        Toast.makeText(Formulario.this, "Falta información. Revisa que tengas todo.", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        dbProvider.subirRespuestas(id_9,key,id,respuesta9);
                    }

                }
                if (id_10!="No"){

                    String respuesta10=edtRespuesta10.getText().toString();
                    if (respuesta10.isEmpty()){
                        Toast.makeText(Formulario.this, "Falta información. Revisa que tengas todo.", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        dbProvider.subirRespuestas(id_10,key,id,respuesta10);
                    }

                }
            }
        });



    }


    public void bajarPreguntas(){
        dbProvider = new DBProvider();

        progressDialog.setMessage("Cargando Información...");
        progressDialog.show();
        dbProvider.formulario().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e(TAG, "Usuarios 4: ");
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        //Log.e(TAG,"Usuarios: "+ snapshot);
                        Preguntas preguntas = snapshot.getValue(Preguntas.class);

                        switch (preguntas.getNombre_pregunta()){
                            case Contants.PREGUNTA_1:
                                txtPregunta1.setText(preguntas.getPregunta());
                                id_1=preguntas.getId_pregunta();
                                txtPregunta1.setVisibility(View.VISIBLE);
                                edtRespuesta1.setVisibility(View.VISIBLE);

                                break;

                            case Contants.PREGUNTA_2:
                                txtPregunta2.setText(preguntas.getPregunta());
                                id_2=preguntas.getId_pregunta();
                                txtPregunta2.setVisibility(View.VISIBLE);
                                edtRespuesta2.setVisibility(View.VISIBLE);
                                break;

                            case Contants.PREGUNTA_3:
                                txtPregunta3.setText(preguntas.getPregunta());
                                id_3=preguntas.getId_pregunta();
                                txtPregunta3.setVisibility(View.VISIBLE);
                                edtRespuesta3.setVisibility(View.VISIBLE);
                                break;
                            case Contants.PREGUNTA_4:
                                txtPregunta4.setText(preguntas.getPregunta());
                                id_4=preguntas.getId_pregunta();
                                txtPregunta4.setVisibility(View.VISIBLE);
                                edtRespuesta4.setVisibility(View.VISIBLE);
                                break;

                            case Contants.PREGUNTA_5:
                                txtPregunta5.setText(preguntas.getPregunta());
                                id_5=preguntas.getId_pregunta();
                                txtPregunta5.setVisibility(View.VISIBLE);
                                edtRespuesta5.setVisibility(View.VISIBLE);
                                break;

                            case Contants.PREGUNTA_6:
                                txtPregunta6.setText(preguntas.getPregunta());
                                id_6=preguntas.getId_pregunta();
                                txtPregunta6.setVisibility(View.VISIBLE);
                                edtRespuesta6.setVisibility(View.VISIBLE);
                                break;

                            case Contants.PREGUNTA_7:
                                txtPregunta7.setText(preguntas.getPregunta());
                                id_7=preguntas.getId_pregunta();
                                txtPregunta7.setVisibility(View.VISIBLE);
                                edtRespuesta7.setVisibility(View.VISIBLE);
                                break;

                            case Contants.PREGUNTA_8:
                                txtPregunta8.setText(preguntas.getPregunta());
                                id_8=preguntas.getId_pregunta();
                                txtPregunta8.setVisibility(View.VISIBLE);
                                edtRespuesta8.setVisibility(View.VISIBLE);
                                break;

                            case Contants.PREGUNTA_9:
                                txtPregunta9.setText(preguntas.getPregunta());
                                id_9=preguntas.getId_pregunta();
                                txtPregunta9.setVisibility(View.VISIBLE);
                                edtRespuesta9.setVisibility(View.VISIBLE);
                                break;

                            case Contants.PREGUNTA_10:
                                txtPregunta10.setText(preguntas.getPregunta());
                                id_10=preguntas.getId_pregunta();
                                txtPregunta10.setVisibility(View.VISIBLE);
                                edtRespuesta10.setVisibility(View.VISIBLE);
                                break;

                              default:
                                  break;
                        }
                        progressDialog.dismiss();

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

}
