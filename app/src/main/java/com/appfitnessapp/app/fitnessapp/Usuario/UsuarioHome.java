package com.appfitnessapp.app.fitnessapp.Usuario;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.appfitnessapp.app.fitnessapp.Adapters.AdapterFeed;
import com.appfitnessapp.app.fitnessapp.Arrays.Feed;
import com.appfitnessapp.app.fitnessapp.BaseDatos.BajarInfo;
import com.appfitnessapp.app.fitnessapp.BaseDatos.Contants;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.Login.Registro;
import com.appfitnessapp.app.fitnessapp.Login.SplashPantalla;
import com.appfitnessapp.app.fitnessapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UsuarioHome  extends AppCompatActivity {

    ImageButton imgPlan,imgPerfil,imgChat;

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

        imgPlan=findViewById(R.id.imgPlan);
        imgPerfil=findViewById(R.id.imgPerfil);
        imgChat=findViewById(R.id.imgChat);

        imgPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(UsuarioHome.this, UsuarioPlan.class);
                startActivity(intent);

            }
        });

        imgPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UsuarioHome.this, UsuarioPerfil.class);
                startActivity(intent);
            }
        });

        imgChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UsuarioHome.this, UsuarioChat.class);
                startActivity(intent);

            }
        });


        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        feeds=new ArrayList<>();
        adapterFeed=new AdapterFeed(feeds);
        recyclerView.setAdapter(adapterFeed);

        Feed feed=new Feed(Contants.VIDEO,true,"","$45","","","Fernando Guzman",
                "5:00", "Pdf con muestras para tonificar los brazos y tener mejor actitud en las cosas que tienen todo");

        Feed feed1=new Feed("video",true,"","$45","","","Fernando Guzman",
                "5:00", "Pdf con muestras para tonificar los brazos y tener mejor actitud en las cosas que tienen todo");

        Feed feed2=new Feed("PDF",false,"","$45","","","Fernando Guzman",
                "5:00", "Pdf con muestras para tonificar los brazos y tener mejor actitud en las cosas que tienen todo");

        Feed feed3=new Feed("PDF",true,"","$45","","","Fernando Guzman",
                "5:00", "Pdf con muestras para tonificar los brazos y tener mejor actitud en las cosas que tienen todo");
        Feed feed4=new Feed("PDF",true,"","$45","","","Fernando Guzman",
                "5:00", "Pdf con muestras para tonificar los brazos y tener mejor actitud en las cosas que tienen todo");

        feeds.add(feed);
        feeds.add(feed1);
        feeds.add(feed2);
        feeds.add(feed3);
        feeds.add(feed4);

        adapterFeed.notifyDataSetChanged();

        adapterFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                bajarFeed();


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

                        if (feed.getTipo_feed().equals(Contants.VIDEO)) {
                            Intent intent = new Intent(UsuarioHome.this, Video.class);
                            startActivity(intent);

                        } else if (feed.getTipo_feed().equals(Contants.IMAGEN)) {
                            Intent intent = new Intent(UsuarioHome.this, Imagen.class);
                            startActivity(intent);
                        } else if (!feed.is_grati) {
                            Intent intent = new Intent(UsuarioHome.this, DetallePdf.class);
                            startActivity(intent);

                            if (feed.is_grati) {
                                Intent intent1 = new Intent(UsuarioHome.this, PantallaPDF.class);
                                intent1.putExtra("ViewType","internet");
                                startActivity(intent1);


                            }

                        }
                    }

                }

                else{
                        Log.e(TAG, "Usuarios 3: ");
                    }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "ERROR: ");
            }
        });

    }
}
