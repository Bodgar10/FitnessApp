package com.appfitnessapp.app.fitnessapp.Usuario;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.appfitnessapp.app.fitnessapp.Adapters.AdapterPreguntas;
import com.appfitnessapp.app.fitnessapp.Adapters.AdapterRespuestas;
import com.appfitnessapp.app.fitnessapp.Admin.AgregarIngredientes;
import com.appfitnessapp.app.fitnessapp.Arrays.Preguntas;
import com.appfitnessapp.app.fitnessapp.Arrays.Respuestas;
import com.appfitnessapp.app.fitnessapp.Arrays.Usuarios;
import com.appfitnessapp.app.fitnessapp.BaseDatos.Contants;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.Login.SplashPantalla;
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

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id =extras.getString("id");


        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);


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

                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(Formulario.this);
                dialogo1.setTitle("");
                dialogo1.setMessage("¿Todas las preguntas se contestarón?");
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id_) {
                        aceptar();
                        Toast.makeText(Formulario.this, "Se subio la información correctamente.", Toast.LENGTH_SHORT).show();
                        new CountDownTimer(1000,1){
                            @Override
                            public void onTick(long l) {
                            }

                            @Override
                            public void onFinish() {
                                Intent intent=new Intent(Formulario.this, UsuarioHome.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();

                            }
                        }.start();


                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        dialogo1.dismiss();
                    }
                });
                dialogo1.show();


            }
        });



    }

    private void aceptar() {

        String key = dbProvider.respuestas().push().getKey();
        String key2 = dbProvider.respuestas().push().getKey();
        String key3 = dbProvider.respuestas().push().getKey();
        String key4 = dbProvider.respuestas().push().getKey();
        String key5 = dbProvider.respuestas().push().getKey();
        String key6 = dbProvider.respuestas().push().getKey();
        String key7 = dbProvider.respuestas().push().getKey();
        String key8 = dbProvider.respuestas().push().getKey();
        String key9 = dbProvider.respuestas().push().getKey();
        String key10 = dbProvider.respuestas().push().getKey();


        String respuesta1 = edtRespuesta1.getText().toString();
        String respuesta2 = edtRespuesta2.getText().toString();
        String respuesta3 = edtRespuesta3.getText().toString();
        String respuesta4 = edtRespuesta4.getText().toString();
        String respuesta5 = edtRespuesta5.getText().toString();
        String respuesta6 = edtRespuesta6.getText().toString();
        String respuesta7 = edtRespuesta7.getText().toString();
        String respuesta8 = edtRespuesta8.getText().toString();
        String respuesta9 = edtRespuesta9.getText().toString();
        String respuesta10 = edtRespuesta10.getText().toString();


        if (!id_1.equals("No")){
            if (!respuesta1.isEmpty()){
                dbProvider.subirRespuestas(id_1,key,id,respuesta1);

            }
        }

        if (!id_2.equals("No")){
            if (!respuesta2.isEmpty()){
                dbProvider.subirRespuestas(id_2,key2,id,respuesta2);

            }
        }
        if (!id_3.equals("No")){
            if (!respuesta3.isEmpty()){
                dbProvider.subirRespuestas(id_3,key3,id,respuesta3);

            }

        }
        if (!id_4.equals("No")){
            if (!respuesta4.isEmpty()){
                dbProvider.subirRespuestas(id_4,key4,id,respuesta4);

            }

        }
        if (!id_5.equals("No")){
            if (!respuesta5.isEmpty()){
                dbProvider.subirRespuestas(id_5,key5,id,respuesta5);

            }

        }
        if (!id_6.equals("No")){
            if (!respuesta6.isEmpty()){
                dbProvider.subirRespuestas(id_6,key6,id,respuesta6);

            }

        }
        if (!id_7.equals("No")){
            if (!respuesta7.isEmpty()){
                dbProvider.subirRespuestas(id_7,key7,id,respuesta7);

            }

        }
        if (!id_8.equals("No")){
            if (!respuesta8.isEmpty()){
                dbProvider.subirRespuestas(id_8,key8,id,respuesta8);

            }

        }
        if (!id_9.equals("No")){
            if (!respuesta9.isEmpty()){
                dbProvider.subirRespuestas(id_9,key9,id,respuesta9);

            }
        }

        if (!id_10.equals("No")){
            if (!respuesta10.isEmpty()){
                dbProvider.subirRespuestas(id_10,key10,id,respuesta10);

            }
        }


    }


    public void bajarPreguntas(){
        dbProvider = new DBProvider();

        progressDialog.setMessage("Cargando Información...");
        progressDialog.show();
        progressDialog.setCancelable(false);
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
                                Log.e(TAG, "Id1:"+id_1);
                                break;

                            case Contants.PREGUNTA_2:
                                txtPregunta2.setText(preguntas.getPregunta());
                                id_2=preguntas.getId_pregunta();
                                txtPregunta2.setVisibility(View.VISIBLE);
                                edtRespuesta2.setVisibility(View.VISIBLE);
                                Log.e(TAG, "Id2:"+id_2);

                                break;

                            case Contants.PREGUNTA_3:
                                txtPregunta3.setText(preguntas.getPregunta());
                                id_3=preguntas.getId_pregunta();
                                txtPregunta3.setVisibility(View.VISIBLE);
                                edtRespuesta3.setVisibility(View.VISIBLE);
                                Log.e(TAG, "Id3:"+id_3);

                                break;
                            case Contants.PREGUNTA_4:
                                txtPregunta4.setText(preguntas.getPregunta());
                                id_4=preguntas.getId_pregunta();
                                txtPregunta4.setVisibility(View.VISIBLE);
                                edtRespuesta4.setVisibility(View.VISIBLE);
                                Log.e(TAG, "I4:"+id_4);

                                break;

                            case Contants.PREGUNTA_5:
                                txtPregunta5.setText(preguntas.getPregunta());
                                id_5=preguntas.getId_pregunta();
                                txtPregunta5.setVisibility(View.VISIBLE);
                                edtRespuesta5.setVisibility(View.VISIBLE);
                                Log.e(TAG, "Id5:"+id_5);

                                break;

                            case Contants.PREGUNTA_6:
                                txtPregunta6.setText(preguntas.getPregunta());
                                id_6=preguntas.getId_pregunta();
                                txtPregunta6.setVisibility(View.VISIBLE);
                                edtRespuesta6.setVisibility(View.VISIBLE);
                                Log.e(TAG, "Id6:"+id_6);
                                break;

                            case Contants.PREGUNTA_7:
                                txtPregunta7.setText(preguntas.getPregunta());
                                id_7=preguntas.getId_pregunta();
                                txtPregunta7.setVisibility(View.VISIBLE);
                                edtRespuesta7.setVisibility(View.VISIBLE);
                                Log.e(TAG, "Id7:"+id_7);

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

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Por favor llena el formulario para continuar.", Toast.LENGTH_SHORT).show();
        
    }
    
}
