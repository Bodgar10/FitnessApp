package com.appfitnessapp.app.fitnessapp.menu_nuevo.FragmentsU;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appfitnessapp.app.fitnessapp.Adapters.AdapterComentarios;
import com.appfitnessapp.app.fitnessapp.Arrays.AsesoriasInfo;
import com.appfitnessapp.app.fitnessapp.Arrays.Usuarios;
import com.appfitnessapp.app.fitnessapp.Arrays.Valoraciones;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.R;
import com.appfitnessapp.app.fitnessapp.Usuario.MenuRegistro.AsesoriaRegistro;
import com.appfitnessapp.app.fitnessapp.Usuario.MenuRegistro.DetallesImagen;
import com.appfitnessapp.app.fitnessapp.Usuario.MenuRegistro.Home;
import com.appfitnessapp.app.fitnessapp.Usuario.TipoPlanes;
import com.appfitnessapp.app.fitnessapp.videoplayer.VideoPlayer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.taufiqrahman.reviewratings.BarLabels;
import com.taufiqrahman.reviewratings.RatingReviews;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Menu_Asesorias_U extends Fragment {


    ImageView imgAsesoria,imgEjercicios,imgAlimentos;
    TextView txtAsesorias,txtPrecio,txtCalificacion,txtDescripcion,txtNombre,txtProfesion,
            txtDescripcionRutina,txtPrecioFinal,txtDescripcionAlimentos;
    ImageView videoView,imgVideo,imgAsesor1;
    LinearLayout btnComprar,btnAndie,btnFederico;

    String id,videoUrl,precioTotal;

    RecyclerView recyclerView;
    AdapterComentarios adapter;
    ArrayList<Valoraciones> plan;



    int valor,valor1,valor2,valor3,valor4;

    private ProgressDialog progressDialog;
    private static final String TAG = "BAJARINFO:";
    static DBProvider dbProvider;



    public Menu_Asesorias_U() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.usuario_04_asesoria, container, false);


        progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(true);


        dbProvider = new DBProvider();



        videoView=view.findViewById(R.id.videoViewAsesoria);
        imgVideo=view.findViewById(R.id.imgVideo);

        if (isOnline(getActivity())){
            bajarAsesoria();
            bajarComentarios();
            imgVideo.setVisibility(View.VISIBLE);


        }
        else {
            imgVideo.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "No hay conexion a internet...", Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }



        RatingReviews ratingReviews = (RatingReviews) view.findViewById(R.id.rating_reviews);



        int star =100;
        int star1 =80;
        int star2 =20;
        int star3 =5;
        int star5 =5;

        int total=(star+star1+star2+star3+star5)/5;

        int colors[] = new int[]{
                Color.parseColor("#0e9d58"),
                Color.parseColor("#bfd047"),
                Color.parseColor("#ffc105"),
                Color.parseColor("#ef7e14"),
                Color.parseColor("#d36259")};

        int rango[]=new int[]{
                valor,valor1,valor2,valor3,valor4
        };

        ratingReviews.createRatingBars(total+150, BarLabels.STYPE1, colors, rango);



        imgAsesoria=view.findViewById(R.id.imgAsesoria);
        imgEjercicios=view.findViewById(R.id.imgRutina);
        imgAlimentos=view.findViewById(R.id.imgAlimentos);


        txtAsesorias=view.findViewById(R.id.txtNumeroAsesorias);
        txtPrecio=view.findViewById(R.id.txtPendientes);
        txtCalificacion=view.findViewById(R.id.txtCalificacion);
        txtDescripcion=view.findViewById(R.id.txtDescripcion);
        txtNombre=view.findViewById(R.id.txtNombreAsesoria);
        txtProfesion=view.findViewById(R.id.txtProfesion);
        txtDescripcionRutina=view.findViewById(R.id.txtDescripcionPlan);
        txtPrecioFinal=view.findViewById(R.id.txtPrecioFinAsesoria);
        txtDescripcionAlimentos=view.findViewById(R.id.txtDescripcionAlimentos);




        btnComprar=view.findViewById(R.id.btnComprarAsesoria);

        btnAndie=view.findViewById(R.id.btnAndie);
        btnFederico=view.findViewById(R.id.btnFedrico);


        btnAndie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), DetallesImagen.class);
                Bundle bundle = new Bundle();
                bundle.putString("Nombre","Andie");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        btnFederico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), DetallesImagen.class);
                Bundle bundle = new Bundle();
                bundle.putString("Nombre","Federico");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });



        imgVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), VideoPlayer.class);
                Bundle bundle = new Bundle();
                bundle.putString("video",videoUrl);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        recyclerView=view.findViewById(R.id.recyclerview);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        plan = new ArrayList<>();
        adapter=new AdapterComentarios(plan);
        recyclerView.setAdapter(adapter);





        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), QuienAsesoria.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.move, R.anim.move_leeft);


            }
        });





        return view;
    }


    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }


    public void bajarAsesoria(){
        dbProvider = new DBProvider();

        progressDialog.setMessage("Cargando Información...");
        progressDialog.show();


        dbProvider.asesoriaInfo().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e(TAG, "Usuarios 4: ");
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        //Log.e(TAG,"Usuarios: "+ snapshot);
                        Log.e(TAG, "Usuarios: " + snapshot);
                        AsesoriasInfo asesorias = snapshot.getValue(AsesoriasInfo.class);

                        if (snapshot.getKey().equals(asesorias.getId_asesoria())){

                            precioTotal=asesorias.getCosto_asesoria();
                            txtPrecio.setText("$"+asesorias.getCosto_asesoria());
                            txtPrecioFinal.setText("$"+asesorias.getCosto_asesoria());
                            txtDescripcion.setText(asesorias.getDescripcion_asesoria());
                            txtDescripcionRutina.setText(asesorias.getRutinas_descripcion());
                            txtDescripcionAlimentos.setText(asesorias.getAlimentos_descripcion());
                            videoUrl=asesorias.getVideo_explicativo();
                            Picasso.get().load(asesorias.getImagen_portada()).into(imgAsesoria);
                            Picasso.get().load(asesorias.getRutinas_imagen()).into(imgEjercicios);
                            Picasso.get().load(asesorias.getAlimentos_imagen()).into(imgAlimentos);
                            Picasso.get().load(asesorias.getImagen_portada()).into(videoView);


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

                progressDialog.dismiss();

            }
        });
    }

    public void bajarComentarios(){
        dbProvider = new DBProvider();
        dbProvider.valoracionAsesoria().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                plan.clear();
                Log.e(TAG, "Valoracion 4: ");
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        //Log.e(TAG,"Usuarios: "+ snapshot);
                        Log.e(TAG, "Valoracion: " + snapshot);
                        Valoraciones valoraciones = snapshot.getValue(Valoraciones.class);

                        String valor5 = valoraciones.getValoracion();
                        if (valoraciones.getId_valoracion() != null){

                            bajarUsuarios(valoraciones.getNombre_usuario_valoracion(),valoraciones.getId_valoracion(),
                                    valoraciones.getDescripcion_valoracion(),valoraciones.getFecha_valoracion(),valoraciones.getImagen_antes(),
                                    valoraciones.getImagen_despues(), valoraciones.getValoracion());
                        }



                        if (valor5=="5"){
                            valor= Integer.parseInt(valoraciones.getValoracion());

                        }
                        if (valor5=="4"){
                            valor1= Integer.parseInt(valoraciones.getValoracion());


                        }

                        if (valor5=="3"){
                            valor2= Integer.parseInt(valoraciones.getValoracion());

                        }

                        if (valor5=="2"){
                            valor3= Integer.parseInt(valoraciones.getValoracion());


                        }

                        if (valor5=="1"){
                            valor4= Integer.parseInt(valoraciones.getValoracion());

                        }
                    }
                } else {
                    Log.e(TAG, "Valoracion 3: ");

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "ERROR: ");
            }
        }); }

    public void bajarUsuarios(final String id_usuario,final String id_valoracion,final String descripcion, final String fecha,
                              final String imgAntes,final String imgDespues,
                              final String valoracion){
        dbProvider = new DBProvider();

        dbProvider.usersRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e(TAG, "Usuarios 4: ");
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Log.e(TAG, "Usuarios: " + snapshot);
                        Usuarios usuarios = snapshot.getValue(Usuarios.class);

                        if (usuarios.getId_usuario() != null) {

                            if (id_usuario.equals(usuarios.getId_usuario())) {
                                Valoraciones valoraciones =new Valoraciones(usuarios.getNombre_usuario(),id_valoracion,descripcion,fecha,
                                        imgAntes,imgDespues,valoracion,usuarios.getFoto_usuario());

                                plan.add(valoraciones);
                                adapter.notifyDataSetChanged();
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







}
