package com.appfitnessapp.app.fitnessapp.Usuario.FeedSinRegistro;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.appfitnessapp.app.fitnessapp.Adapters.AdapterFeed;
import com.appfitnessapp.app.fitnessapp.Arrays.Feed;
import com.appfitnessapp.app.fitnessapp.BaseDatos.BajarInfo;
import com.appfitnessapp.app.fitnessapp.BaseDatos.Contants;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.R;
import com.appfitnessapp.app.fitnessapp.Usuario.Asesoria;
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

    LinearLayout imgPerfil,linearAsesoria ;
    private static final int PDF_CODE = 1000 ;

    ImageButton imgAsesoria;

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

        bajarFeed();

        dbProvider = new DBProvider();
        bajarInfo = new BajarInfo();


        imgAsesoria=findViewById(R.id.btnAsesoria);
        imgPerfil=findViewById(R.id.imgPerfil);

        linearAsesoria=findViewById(R.id.linearAsesoria);



        imgPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


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
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        feeds=new ArrayList<>();
        adapterFeed=new AdapterFeed(feeds);
        recyclerView.setAdapter(adapterFeed);
        adapterFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(feeds.get(recyclerView.getChildAdapterPosition(view)).getTipo_feed().equals(Contants.VIDEO)){

                    Intent intent = new Intent(HomeSinRegistro.this, Video.class);
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
                        Intent intent = new Intent(HomeSinRegistro.this, DetallePdf.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("pdf",feeds.get(recyclerView.getChildAdapterPosition(view)).getDescripcion());
                        bundle.putString("precio",feeds.get(recyclerView.getChildAdapterPosition(view)).getCosto_pdf());

                        intent.putExtras(bundle);
                        startActivity(intent);

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
