package com.appfitnessapp.app.fitnessapp.Usuario.MenuRegistro;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appfitnessapp.app.fitnessapp.Adapters.AdapterComentarios;
import com.appfitnessapp.app.fitnessapp.Arrays.AsesoriasInfo;
import com.appfitnessapp.app.fitnessapp.Arrays.Usuarios;
import com.appfitnessapp.app.fitnessapp.Arrays.Valoraciones;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.Login.IniciarSesion;
import com.appfitnessapp.app.fitnessapp.Login.Registro;
import com.appfitnessapp.app.fitnessapp.R;
import com.appfitnessapp.app.fitnessapp.Usuario.FeedSinRegistro.Asesoria;
import com.appfitnessapp.app.fitnessapp.Usuario.MetodoPago;
import com.appfitnessapp.app.fitnessapp.Usuario.TipoPlanes;
import com.appfitnessapp.app.fitnessapp.Usuario.UsuarioHome;
import com.appfitnessapp.app.fitnessapp.Usuario.UsuarioPerfil;
import com.appfitnessapp.app.fitnessapp.videoplayer.VideoPlayer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.taufiqrahman.reviewratings.BarLabels;
import com.taufiqrahman.reviewratings.RatingReviews;

import java.util.ArrayList;

public class AsesoriaRegistro extends AppCompatActivity {

    ImageView imgAsesoria,imgEjercicios,imgAlimentos;
    TextView txtAsesorias,txtPrecio,txtCalificacion,txtDescripcion,txtNombre,txtProfesion,
            txtDescripcionRutina,txtPrecioFinal,txtDescripcionAlimentos;
    ImageView videoView,imgVideo;
    LinearLayout btnComprar;

    String id,videoUrl,precioTotal;

    RecyclerView recyclerView;
    AdapterComentarios adapter;
    ArrayList<Valoraciones> plan;

    int valor,valor1,valor2,valor3,valor4;

    private ProgressDialog progressDialog;
    private static final String TAG = "BAJARINFO:";
    static DBProvider dbProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_04_asesoria);

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);



        dbProvider = new DBProvider();


        bajarAsesoria();
        bajarComentarios();

        RatingReviews ratingReviews = (RatingReviews) findViewById(R.id.rating_reviews);



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



        Toolbar toolbarback=findViewById(R.id.toolbar);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("Asesoría personalizada");
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
        imgVideo=findViewById(R.id.imgVideo);

        btnComprar=findViewById(R.id.btnComprarAsesoria);


        imgVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AsesoriaRegistro.this, VideoPlayer.class);
                Bundle bundle = new Bundle();
                bundle.putString("video",videoUrl);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        plan = new ArrayList<>();
        adapter=new AdapterComentarios(plan);
        recyclerView.setAdapter(adapter);





        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AsesoriaRegistro.this, TipoPlanes.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.move, R.anim.move_leeft);


            }
        });






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
        Intent intent;
        intent = new Intent(this, Home.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
        overridePendingTransition(
                getIntent().getIntExtra("anim id in", R.anim.move_in),
                getIntent().getIntExtra("anim id out", R.anim.move_leeft_in));

    }


}
