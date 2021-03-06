package com.appfitnessapp.app.fitnessapp.Usuario.FeedSinRegistro;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appfitnessapp.app.fitnessapp.Adapters.AdapterFeed;
import com.appfitnessapp.app.fitnessapp.Arrays.Feed;
import com.appfitnessapp.app.fitnessapp.BaseDatos.BajarInfo;
import com.appfitnessapp.app.fitnessapp.BaseDatos.Contants;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.Login.IniciarSesion;
import com.appfitnessapp.app.fitnessapp.Login.Registro;
import com.appfitnessapp.app.fitnessapp.R;
import com.appfitnessapp.app.fitnessapp.Usuario.DetallePdf;
import com.appfitnessapp.app.fitnessapp.Usuario.Imagen;
import com.appfitnessapp.app.fitnessapp.Usuario.PantallaPDF;
import com.appfitnessapp.app.fitnessapp.Usuario.UsuarioHome;
import com.appfitnessapp.app.fitnessapp.Usuario.Video;
import com.appfitnessapp.app.fitnessapp.videoplayer.VideoPlayer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeSinRegistro  extends AppCompatActivity {

    LinearLayout linearAsesoria ;
    private static final int PDF_CODE = 1000 ;

    ImageButton imgAsesoria,imgPerfil;

    static DBProvider dbProvider;
    BajarInfo bajarInfo;
    private static final String TAG = "BAJARINFO:";

    AdapterFeed adapterFeed;
    ArrayList<Feed> feeds;

    RecyclerView recyclerView;
    private ProgressDialog progressDialog;

    LinearLayoutManager linearLayout;

    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_02_feed);

        toolbar=findViewById(R.id.toolbar);

        toolbar.setVisibility(View.GONE);

        bajarFeed();

        dbProvider = new DBProvider();
        bajarInfo = new BajarInfo();

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);


        imgAsesoria=findViewById(R.id.btnAsesoria);
       // imgPerfil=findViewById(R.id.imgPerfil);

        linearAsesoria=findViewById(R.id.linearAsesoria);


/*
        imgPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(HomeSinRegistro.this);
                dialogo1.setTitle("No has iniciado sesión");
                dialogo1.setMessage("");
                dialogo1.setPositiveButton("Iniciar Sesión", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        Intent intent = new Intent(HomeSinRegistro.this, IniciarSesion.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                });
                dialogo1.setNegativeButton("Registrarse", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        Intent intent = new Intent(HomeSinRegistro.this, Registro.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                });
                dialogo1.show();

            }
        });

        */

        linearAsesoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeSinRegistro.this, Asesoria.class);
                startActivity(intent);
            }
        });
        imgAsesoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HomeSinRegistro.this, Asesoria.class);
                startActivity(intent);

            }
        });


        recyclerView=findViewById(R.id.recyclerview);

        linearLayout =new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayout);
        //poner orden inverso el recycler
        linearLayout.setReverseLayout(true);
        linearLayout.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);

        feeds=new ArrayList<>();
        adapterFeed=new AdapterFeed(feeds);
        recyclerView.setAdapter(adapterFeed);

        adapterFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(feeds.get(recyclerView.getChildAdapterPosition(view)).getTipo_feed().equals(Contants.VIDEO)){

                    Intent intent = new Intent(HomeSinRegistro.this, VideoPlayer.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("video",feeds.get(recyclerView.getChildAdapterPosition(view)).getUrl_tipo());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

                else if(feeds.get(recyclerView.getChildAdapterPosition(view)).getTipo_feed().equals(Contants.IMAGEN)){

                    Intent intent = new Intent(HomeSinRegistro.this, Imagen.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("imagen",feeds.get(recyclerView.getChildAdapterPosition(view)).getUrl_tipo());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

                else if(feeds.get(recyclerView.getChildAdapterPosition(view)).getTipo_feed().equals(Contants.PDF)){

                    if (feeds.get(recyclerView.getChildAdapterPosition(view)).getIs_gratis()){
                        Intent intent = new Intent(HomeSinRegistro.this, PantallaPDF.class);
                        intent.putExtra("ViewType","internet");
                        Bundle bundle = new Bundle();
                        bundle.putString("pdf",feeds.get(recyclerView.getChildAdapterPosition(view)).getUrl_tipo());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                    else {

                        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(HomeSinRegistro.this);
                        dialogo1.setTitle("No has iniciado sesión");
                        dialogo1.setMessage("");
                        dialogo1.setPositiveButton("Iniciar Sesión", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogo1, int id) {
                                Intent intent = new Intent(HomeSinRegistro.this, IniciarSesion.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }
                        });
                        dialogo1.setNegativeButton("Registrarse", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogo1, int id) {
                                Intent intent = new Intent(HomeSinRegistro.this, Registro.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }
                        });
                        dialogo1.show();
                    }

                }
                else {

                }


            }
        });



    }

    public void bajarFeed(){

        //  feeds.removeAll(feeds);

        dbProvider = new DBProvider();
        dbProvider.tablaFeed().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                feeds.clear();

                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Log.e(TAG, "Feed: " + dataSnapshot);
                        Feed feed = snapshot.getValue(Feed.class);


                        feeds.add(feed);
                        adapterFeed.notifyDataSetChanged();


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






    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PDF_CODE && resultCode == RESULT_OK && data != null){

            Uri selecPdf=data.getData();
            Intent intent=new Intent(HomeSinRegistro.this,PantallaPDF.class);
            intent.putExtra("ViewType","storage");
            intent.putExtra("FileUri",selecPdf.toString());
            startActivity(intent);


        }

    }





}
