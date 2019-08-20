package com.appfitnessapp.app.fitnessapp.Usuario;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.appfitnessapp.app.fitnessapp.Arrays.PlanEntrenamiento;
import com.appfitnessapp.app.fitnessapp.BaseDatos.BajarInfo;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UsuarioPlanWorkouts extends AppCompatActivity {

    ImageButton imgHome,imgPerfil,imgChat;
    TextView btnRecetas,txtTiempo,txtIntensidad,txtEjericios;
    ImageView imgVideo,imgPrincipal;

    static DBProvider dbProvider;
    BajarInfo bajarInfo;
    private static final String TAG = "BAJARINFO:";

    String descripcion;

    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_19_plan_entrenamiento);


        dbProvider = new DBProvider();
        bajarInfo = new BajarInfo();
        bajarPlanEjercicios();

        id = FirebaseAuth.getInstance().getCurrentUser().getUid();


        imgHome=findViewById(R.id.imgHome);
        imgPerfil=findViewById(R.id.imgPerfil);
        imgChat=findViewById(R.id.imgChat);

        imgVideo=findViewById(R.id.imgVideo);
        imgPrincipal=findViewById(R.id.imgPrincipal);


        btnRecetas=findViewById(R.id.btnRecetas);

        txtTiempo=findViewById(R.id.txtTiempo);
        txtIntensidad=findViewById(R.id.txtIntensidad);
        txtEjericios=findViewById(R.id.txtEjericicios);




        imgPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(UsuarioPlanWorkouts.this, UsuarioPerfil.class);
                startActivity(intent);
                finish();

            }
        });

        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UsuarioPlanWorkouts.this, UsuarioHome.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        imgChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UsuarioPlanWorkouts.this, UsuarioChat.class);
                startActivity(intent);
                finish();

            }
        });


        imgVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UsuarioPlanWorkouts.this, RutinaUsuario.class);
                Bundle bundle = new Bundle();
                bundle.putString("descripcion",descripcion);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        btnRecetas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UsuarioPlanWorkouts.this, UsuarioPlan.class);
                startActivity(intent);
            }
        });

    }


    public void bajarPlanEjercicios(){

        dbProvider = new DBProvider();
        dbProvider.tablaPlanEntrenamiento().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Log.e(TAG, "Feed: " + dataSnapshot);
                        PlanEntrenamiento planEntrenamiento = snapshot.getValue(PlanEntrenamiento.class);

                        if (planEntrenamiento.getId_usuario().equals(id)){

                            Date date = new Date();
                            //diaBase
                            String dia = planEntrenamiento.getDia_ejercicio();
                            //diaSemana
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(date);
                            int fechaAc = calendar.get(Calendar.DAY_OF_WEEK);
                            if (fechaAc==1){
                                if (dia.equals("1")){
                                    descripcion=planEntrenamiento.getDescripcion_ejercicios();
                                    txtTiempo.setText(planEntrenamiento.getMin_ejercicio()+" min");
                                    txtEjericios.setText(planEntrenamiento.getNum_ejercicios()+" ejercicios");
                                    txtIntensidad.setText(planEntrenamiento.getNivel_ejercicio()+" Intensidad");
                                }

                            }
                            if (fechaAc==2){
                                if (dia.equals("2")){
                                    descripcion=planEntrenamiento.getDescripcion_ejercicios();
                                    txtTiempo.setText(planEntrenamiento.getMin_ejercicio()+" min");
                                    txtEjericios.setText(planEntrenamiento.getNum_ejercicios()+" ejercicios");
                                    txtIntensidad.setText(planEntrenamiento.getNivel_ejercicio()+" Intensidad");
                                }

                            }

                            if (fechaAc==3){
                                if (dia.equals("3")){
                                    descripcion=planEntrenamiento.getDescripcion_ejercicios();
                                    txtTiempo.setText(planEntrenamiento.getMin_ejercicio()+" min");
                                    txtEjericios.setText(planEntrenamiento.getNum_ejercicios()+" ejercicios");
                                    txtIntensidad.setText(planEntrenamiento.getNivel_ejercicio()+" Intensidad");
                                }

                            }
                            if (fechaAc==4){
                                if (dia.equals("4")){
                                    descripcion=planEntrenamiento.getDescripcion_ejercicios();
                                    txtTiempo.setText(planEntrenamiento.getMin_ejercicio()+" min");
                                    txtEjericios.setText(planEntrenamiento.getNum_ejercicios()+" ejercicios");
                                    txtIntensidad.setText(planEntrenamiento.getNivel_ejercicio()+" Intensidad");
                                }
                            }

                            if (fechaAc==5){
                                if (dia.equals("5")){
                                    descripcion=planEntrenamiento.getDescripcion_ejercicios();
                                    txtTiempo.setText(planEntrenamiento.getMin_ejercicio()+" min");
                                    txtEjericios.setText(planEntrenamiento.getNum_ejercicios()+" ejercicios");
                                    txtIntensidad.setText(planEntrenamiento.getNivel_ejercicio()+" Intensidad");
                                }
                            }


                            if (fechaAc==6){
                                if (dia.equals("6")){
                                    descripcion=planEntrenamiento.getDescripcion_ejercicios();
                                    txtTiempo.setText(planEntrenamiento.getMin_ejercicio()+" min");
                                    txtEjericios.setText(planEntrenamiento.getNum_ejercicios()+" ejercicios");
                                    txtIntensidad.setText(planEntrenamiento.getNivel_ejercicio()+" Intensidad");
                                }
                            }


                            if (fechaAc==7){
                                if (dia.equals("7")){
                                    descripcion=planEntrenamiento.getDescripcion_ejercicios();
                                    txtTiempo.setText(planEntrenamiento.getMin_ejercicio()+" min");
                                    txtEjericios.setText(planEntrenamiento.getNum_ejercicios()+" ejercicios");
                                    txtIntensidad.setText(planEntrenamiento.getNivel_ejercicio()+" Intensidad");
                                }
                            }
                        }
                    }
                }
                else {
                    Log.e(TAG, "Feed: ");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "ERROR: ");
            }
        });

    }



}
