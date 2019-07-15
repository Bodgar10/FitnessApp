package com.appfitnessapp.app.fitnessapp.Usuario;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.appfitnessapp.app.fitnessapp.Adapters.AdapterComentarios;
import com.appfitnessapp.app.fitnessapp.Adapters.AdapterPlanes;
import com.appfitnessapp.app.fitnessapp.Arrays.AsesoriasInfo;
import com.appfitnessapp.app.fitnessapp.Arrays.Planes;
import com.appfitnessapp.app.fitnessapp.Arrays.Usuarios;
import com.appfitnessapp.app.fitnessapp.Arrays.Valoraciones;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.R;
import com.appfitnessapp.app.fitnessapp.VideoControllerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Asesoria extends AppCompatActivity {

    ImageView imgAsesoria,imgEjercicios,imgAlimentos;
    TextView txtAsesorias,txtPrecio,txtCalificacion,txtDescripcion,txtNombre,txtProfesion,
    txtDescripcionRutina,txtPrecioFinal,txtDescripcionAlimentos;
    VideoView videoView;
    LinearLayout btnComprar;

    String id;

    RecyclerView recyclerView;
    AdapterComentarios adapter;
    ArrayList<Valoraciones> plan;

    private ProgressDialog progressDialog;
    private static final String TAG = "BAJARINFO:";
    static DBProvider dbProvider;



    private int position = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_04_asesoria);

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);

        dbProvider = new DBProvider();




        Toolbar toolbarback=findViewById(R.id.toolbar);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("Asesoria personalizada");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        imgAsesoria=findViewById(R.id.imgAsesoria);
        imgEjercicios=findViewById(R.id.imgRutina);
        imgAlimentos=findViewById(R.id.imgAlimentos);


        txtAsesorias=findViewById(R.id.txtNumeroAsesorias);
        txtPrecio=findViewById(R.id.txtPendientes);
        txtCalificacion=findViewById(R.id.txtCalificacion);
        txtDescripcion=findViewById(R.id.txtDescripcion);
        txtNombre=findViewById(R.id.txtNombreAsesoria);
        txtProfesion=findViewById(R.id.txtProfesion);
        txtDescripcionRutina=findViewById(R.id.txtDescripcionPlan);
        txtPrecioFinal=findViewById(R.id.txtPrecioFinAsesoria);
        txtDescripcionAlimentos=findViewById(R.id.txtDescripcionAlimentos);


        videoView=findViewById(R.id.videoViewAsesoria);

        btnComprar=findViewById(R.id.btnComprarAsesoria);

        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        plan = new ArrayList<>();
        adapter=new AdapterComentarios(plan);
        recyclerView.setAdapter(adapter);





        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Asesoria.this, MetodoPago.class);
                startActivity(intent);

            }
        });


        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        bajarAsesoria();
        bajarComentarios();

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

                            txtPrecio.setText(asesorias.getCosto_asesoria());
                            txtPrecioFinal.setText(asesorias.getCosto_asesoria());
                            txtDescripcion.setText(asesorias.getDescripcion_asesoria());
                            txtDescripcionRutina.setText(asesorias.getRutinas_descripcion());
                            txtDescripcionAlimentos.setText(asesorias.getAlimentos_descripcion());
                            Picasso.get().load(asesorias.getImagen_portada()).into(imgAsesoria);
                            Picasso.get().load(asesorias.getRutinas_imagen()).into(imgEjercicios);
                            Picasso.get().load(asesorias.getAlimentos_imagen()).into(imgAlimentos);

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

                        if (valoraciones.getId_valoracion() != null){

                            bajarUsuarios(valoraciones.getNombre_usuario_valoracion(),valoraciones.getId_valoracion(),
                                    valoraciones.getDescripcion_valoracion(),valoraciones.getFecha_valoracion(),valoraciones.getImagen_antes(),
                                    valoraciones.getImagen_despues(), valoraciones.getValoracion());
                        }
                    }
                } else {
                    Log.e(TAG, "Valoracion 3: "); }
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
                plan.clear();
                Log.e(TAG, "Usuarios 4: ");
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Log.e(TAG, "Usuarios: " + snapshot);
                        Usuarios usuarios = snapshot.getValue(Usuarios.class);

                        if (usuarios.getId_usuario() != null) {

                            if (id_usuario.equals(usuarios.getId_usuario())) {
                                Valoraciones valoraciones = new Valoraciones(usuarios.getId_usuario(), id_valoracion, descripcion,
                                        fecha, imgAntes, imgDespues, valoracion, usuarios.getNombre_usuario(), usuarios.getFoto_usuario());

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



    private void setUpVideoView(String url)     {

/*
        String uriPath = "android.resource://" + getPackageName()
                + "/" + R.raw.video_example;

  */

        Uri uri = Uri.parse(url);

        MediaController mediaController = new MediaController(this){
            @Override
            public void show(int timeout) {
                super.show(0);
            }

        };
        videoView.setMediaController(mediaController);


        try {
            // Asigna la URI del vídeo que será reproducido a la vista.
            videoView.setVideoURI(uri);
            // Se asigna el foco a la VideoView.
            videoView.requestFocus();
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        /*
         * Se asigna un listener que nos informa cuando el vídeo
         * está listo para ser reproducido.
         */
        videoView.setOnPreparedListener(videoViewListener);
    }


    private MediaPlayer.OnPreparedListener videoViewListener =
            new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    /*
                     * Se indica al reproductor multimedia que el vídeo
                     * se reproducirá en un loop (on repeat).
                     */
                    mediaPlayer.setLooping(true);

                    if (position == 0) {
                        /*
                         * Si tenemos una posición en savedInstanceState,
                         * el vídeo debería comenzar desde aquí.
                         */
                        videoView.start();

                    } else {
                        /*
                         * Si venimos de un Activity "resumed",
                         * la reproducción del vídeo será pausada.
                         */
                        videoView.pause();
                    }
                }
            };




    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        /* Usamos onSaveInstanceState para guardar la posición de
           reproducción del vídeo en caso de un cambio de orientación. */
        savedInstanceState.putInt("Position",
                videoView.getCurrentPosition());
        videoView.pause();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        /*
         * Usamos onRestoreInstanceState para reproducir el vídeo
         * desde la posición guardada.
         */
        position = savedInstanceState.getInt("Position");
        videoView.seekTo(position);
    }




}
