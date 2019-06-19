package com.appfitnessapp.app.fitnessapp.Usuario.FeedSinRegistro;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.appfitnessapp.app.fitnessapp.Adapters.AdapterFeed;
import com.appfitnessapp.app.fitnessapp.Arrays.Feed;
import com.appfitnessapp.app.fitnessapp.BaseDatos.BajarInfo;
import com.appfitnessapp.app.fitnessapp.BaseDatos.Contants;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.R;
import com.appfitnessapp.app.fitnessapp.Usuario.DetallePdf;
import com.appfitnessapp.app.fitnessapp.Usuario.Imagen;
import com.appfitnessapp.app.fitnessapp.Usuario.PantallaPDF;
import com.appfitnessapp.app.fitnessapp.Usuario.UsuarioChat;
import com.appfitnessapp.app.fitnessapp.Usuario.UsuarioHome;
import com.appfitnessapp.app.fitnessapp.Usuario.UsuarioPerfil;
import com.appfitnessapp.app.fitnessapp.Usuario.UsuarioPlan;
import com.appfitnessapp.app.fitnessapp.Usuario.Video;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeSinRegistro  extends AppCompatActivity  {

    LinearLayout imgAsesoria,imgPerfil ;

    static DBProvider dbProvider;
    BajarInfo bajarInfo;
    private static final String TAG = "BAJARINFO:";

    AdapterFeed adapterFeed;
    ArrayList<Feed> feeds;

    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_02_feed);

        dbProvider = new DBProvider();
        bajarInfo = new BajarInfo();

        imgAsesoria=findViewById(R.id.btnAsesoria);
        imgPerfil=findViewById(R.id.imgPerfil);

        bajarFeed();



        imgPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        imgAsesoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        feeds=new ArrayList<>();
        adapterFeed=new AdapterFeed(feeds);
        recyclerView.setAdapter(adapterFeed);

        /*

         final Feed feed0=new Feed(Contants.VIDEO,false,"","$45","","","Fernando Guzman",
                "5:00", "Pdf con muestras para tonificar los brazos y tener mejor actitud en las cosas que tienen todo");

        final Feed feed1=new Feed(Contants.IMAGEN,false,"","$45","","","Fernando Guzman",
                "5:00", "Pdf con muestras para tonificar los brazos y tener mejor actitud en las cosas que tienen todo");

        final Feed feed2=new Feed(Contants.PDF,false,"","$45","","","Fernando Guzman",
                "5:00", "Pdf con muestras para tonificar los brazos y tener mejor actitud en las cosas que tienen todo");

        final Feed feed3=new Feed(Contants.PDF,true,"","$45","","","Fernando Guzman",
                "5:00", "Pdf con muestras para tonificar los brazos y tener mejor actitud en las cosas que tienen todo");
        Feed feed4=new Feed(Contants.PDF,true,"","$45","","","Fernando Guzman",
                "5:00", "Pdf con muestras para tonificar los brazos y tener mejor actitud en las cosas que tienen todo");


        feeds.add(feed0);
        feeds.add(feed1);
        feeds.add(feed2);
        feeds.add(feed3);
        feeds.add(feed4);
*/
        adapterFeed.notifyDataSetChanged();


        adapterFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {





            }
        });



    }

    public void bajarFeed(){

        dbProvider = new DBProvider();
        dbProvider.tablaFeed().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Log.e(TAG, "Feed: " + dataSnapshot);
                        Feed feed = snapshot.getValue(Feed.class);





                            adapterFeed.notifyDataSetChanged();



                        }
                    }

                }






            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "ERROR: ");
            }
        });

    }



}
