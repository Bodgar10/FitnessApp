package com.appfitnessapp.app.fitnessapp.menu_nuevo.Menu_UPago;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appfitnessapp.app.fitnessapp.Arrays.PlanEntrenamiento;
import com.appfitnessapp.app.fitnessapp.BaseDatos.BajarInfo;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.R;
import com.appfitnessapp.app.fitnessapp.Usuario.RutinaUsuario;
import com.appfitnessapp.app.fitnessapp.Usuario.UsuarioChat;
import com.appfitnessapp.app.fitnessapp.Usuario.UsuarioHome;
import com.appfitnessapp.app.fitnessapp.Usuario.UsuarioPerfil;
import com.appfitnessapp.app.fitnessapp.Usuario.UsuarioPlan;
import com.appfitnessapp.app.fitnessapp.Usuario.UsuarioPlanWorkouts;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;

public class MenuRutinasPago extends Fragment {

    ImageButton imgHome, imgPerfil, imgChat;
    TextView txtTiempo, txtIntensidad, txtEjericios;
    ImageView imgVideo, imgPrincipal;

    static DBProvider dbProvider;
    BajarInfo bajarInfo;
    private static final String TAG = "BAJARINFO:";

    String descripcion, idEjercicio;

    String id;


    public MenuRutinasPago() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.usuario_19_plan_entrenamiento, container, false);


        dbProvider = new DBProvider();
        bajarInfo = new BajarInfo();
        bajarPlanEjercicios();

        id = FirebaseAuth.getInstance().getCurrentUser().getUid();


        imgHome = view.findViewById(R.id.imgHome);
        imgPerfil = view.findViewById(R.id.imgPerfil);
        imgChat = view.findViewById(R.id.imgChat);

        imgVideo = view.findViewById(R.id.imgVideo);
        imgPrincipal = view.findViewById(R.id.imgPrincipal);


        txtTiempo = view.findViewById(R.id.txtTiempo);
        txtIntensidad = view.findViewById(R.id.txtIntensidad);
        txtEjericios = view.findViewById(R.id.txtEjericicios);


        imgVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            mandarPantalla();


            }


        });


        return view;


    }

    public void mandarPantalla() {

        Intent intent = new Intent(getContext(), RutinaUsuario.class);
        Bundle bundle = new Bundle();
        bundle.putString("descripcion", descripcion);
        bundle.putString("id", idEjercicio);
        intent.putExtras(bundle);
        startActivity(intent);

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

                        Date date = new Date();
                        //diaBase
                        String dia = planEntrenamiento.getDia_ejercicio();
                        //diaSemana
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        int fechaAc = calendar.get(Calendar.DAY_OF_WEEK);

                        if (planEntrenamiento.getId_plan_ejercicio()!=null) {

                            if (planEntrenamiento.getId_usuario().equals(id)){
                                if (fechaAc == 1&&dia.equals("1")) {
                                            idEjercicio = planEntrenamiento.getId_plan_ejercicio();
                                            descripcion = planEntrenamiento.getDescripcion_ejercicios();
                                            txtTiempo.setText(planEntrenamiento.getMin_ejercicio() + " min");
                                            txtEjericios.setText(planEntrenamiento.getNum_ejercicios() + " ejercicios");
                                            txtIntensidad.setText(planEntrenamiento.getNivel_ejercicio() + " Intensidad");
                                            imgVideo.setVisibility(View.VISIBLE);



                                }


                                else if (fechaAc == 2&&dia.equals("2")) {

                                            idEjercicio = planEntrenamiento.getId_plan_ejercicio();
                                            descripcion = planEntrenamiento.getDescripcion_ejercicios();
                                            txtTiempo.setText(planEntrenamiento.getMin_ejercicio() + " min");
                                            txtEjericios.setText(planEntrenamiento.getNum_ejercicios() + " ejercicios");
                                            txtIntensidad.setText(planEntrenamiento.getNivel_ejercicio() + " Intensidad");
                                            imgVideo.setVisibility(View.VISIBLE);



                                }

                                else if (fechaAc == 3&&dia.equals("3")) {
                                            idEjercicio = planEntrenamiento.getId_plan_ejercicio();
                                            descripcion = planEntrenamiento.getDescripcion_ejercicios();
                                            txtTiempo.setText(planEntrenamiento.getMin_ejercicio() + " min");
                                            txtEjericios.setText(planEntrenamiento.getNum_ejercicios() + " ejercicios");
                                            txtIntensidad.setText(planEntrenamiento.getNivel_ejercicio() + " Intensidad");
                                            imgVideo.setVisibility(View.VISIBLE);


                                }
                                else if (fechaAc == 4&&dia.equals("4")) {
                                            idEjercicio = planEntrenamiento.getId_plan_ejercicio();
                                            descripcion = planEntrenamiento.getDescripcion_ejercicios();
                                            txtTiempo.setText(planEntrenamiento.getMin_ejercicio() + " min");
                                            txtEjericios.setText(planEntrenamiento.getNum_ejercicios() + " ejercicios");
                                            txtIntensidad.setText(planEntrenamiento.getNivel_ejercicio() + " Intensidad");
                                            imgVideo.setVisibility(View.VISIBLE);

                                }

                                else if (fechaAc == 5&&dia.equals("5")) {

                                            idEjercicio = planEntrenamiento.getId_plan_ejercicio();
                                            descripcion = planEntrenamiento.getDescripcion_ejercicios();
                                            txtTiempo.setText(planEntrenamiento.getMin_ejercicio() + " min");
                                            txtEjericios.setText(planEntrenamiento.getNum_ejercicios() + " ejercicios");
                                            txtIntensidad.setText(planEntrenamiento.getNivel_ejercicio() + " Intensidad");
                                            imgVideo.setVisibility(View.VISIBLE);

                                }


                                else if (fechaAc == 6&&dia.equals("6")) {
                                            idEjercicio = planEntrenamiento.getId_plan_ejercicio();
                                            descripcion = planEntrenamiento.getDescripcion_ejercicios();
                                            txtTiempo.setText(planEntrenamiento.getMin_ejercicio() + " min");
                                            txtEjericios.setText(planEntrenamiento.getNum_ejercicios() + " ejercicios");
                                            txtIntensidad.setText(planEntrenamiento.getNivel_ejercicio() + " Intensidad");
                                            imgVideo.setVisibility(View.VISIBLE);

                                }


                                else if (fechaAc == 7&&dia.equals("7")) {

                                            idEjercicio = planEntrenamiento.getId_plan_ejercicio();
                                            descripcion = planEntrenamiento.getDescripcion_ejercicios();
                                            txtTiempo.setText(planEntrenamiento.getMin_ejercicio() + " min");
                                            txtEjericios.setText(planEntrenamiento.getNum_ejercicios() + " ejercicios");
                                            txtIntensidad.setText(planEntrenamiento.getNivel_ejercicio() + " Intensidad");
                                            imgVideo.setVisibility(View.VISIBLE);





                                }

                                else{
                                    imgVideo.setVisibility(View.GONE);
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
