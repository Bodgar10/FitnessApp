package com.appfitnessapp.app.fitnessapp.Usuario.MenuRegistro;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

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
import com.appfitnessapp.app.fitnessapp.Usuario.FeedSinRegistro.Asesoria;
import com.appfitnessapp.app.fitnessapp.Usuario.FeedSinRegistro.HomeSinRegistro;
import com.appfitnessapp.app.fitnessapp.Usuario.Imagen;
import com.appfitnessapp.app.fitnessapp.Usuario.PantallaPDF;
import com.appfitnessapp.app.fitnessapp.Usuario.Video;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Home   extends AppCompatActivity {

    LinearLayout linearAsesoria;
    private static final int PDF_CODE = 1000;

    ImageButton imgAsesoria, imgPerfil;

    static DBProvider dbProvider;
    BajarInfo bajarInfo;
    private static final String TAG = "BAJARINFO:";

    AdapterFeed adapterFeed;
    ArrayList<Feed> feeds;

    RecyclerView recyclerView;
    private ProgressDialog progressDialog;

    LinearLayoutManager linearLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_02_feed);

        bajarFeed();

        dbProvider = new DBProvider();
        bajarInfo = new BajarInfo();

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);


        imgAsesoria = findViewById(R.id.btnAsesoria);
        imgPerfil = findViewById(R.id.imgPerfil);

        linearAsesoria = findViewById(R.id.linearAsesoria);


        imgPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Home.this, Perfil.class);
                intent.putExtra("anim id in", R.anim.move_in);
                intent.putExtra("anim id out", R.anim.move_leeft_in);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.move, R.anim.move_leeft);
            }
        });


        linearAsesoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Home.this, AsesoriaRegistro.class);
                intent.putExtra("anim id in", R.anim.move_in);
                intent.putExtra("anim id out", R.anim.move_leeft_in);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.move, R.anim.move_leeft);
            }
        });
        imgAsesoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Home.this, AsesoriaRegistro.class);
                intent.putExtra("anim id in", R.anim.move_in);
                intent.putExtra("anim id out", R.anim.move_leeft_in);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.move, R.anim.move_leeft);
            }
        });




        recyclerView = findViewById(R.id.recyclerview);

        linearLayout =new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayout);
        //poner orden inverso el recycler
        linearLayout.setReverseLayout(true);
        linearLayout.setStackFromEnd(true);

        feeds = new ArrayList<>();
        adapterFeed = new AdapterFeed(feeds);
        recyclerView.setAdapter(adapterFeed);
        adapterFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (feeds.get(recyclerView.getChildAdapterPosition(view)).getTipo_feed().equals(Contants.VIDEO)) {

                    Intent intent = new Intent(Home.this, Video.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("video", feeds.get(recyclerView.getChildAdapterPosition(view)).getUrl_tipo());
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else if (feeds.get(recyclerView.getChildAdapterPosition(view)).getTipo_feed().equals(Contants.IMAGEN)) {

                    Intent intent = new Intent(Home.this, Imagen.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("imagen", feeds.get(recyclerView.getChildAdapterPosition(view)).getUrl_tipo());
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else if (feeds.get(recyclerView.getChildAdapterPosition(view)).getTipo_feed().equals(Contants.PDF)) {

                    if (feeds.get(recyclerView.getChildAdapterPosition(view)).getIs_gratis()) {
                        Intent intent = new Intent(Home.this, PantallaPDF.class);
                        intent.putExtra("ViewType", "internet");
                        Bundle bundle = new Bundle();
                        bundle.putString("pdf", feeds.get(recyclerView.getChildAdapterPosition(view)).getUrl_tipo());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(Home.this, DetallePdf.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("pdf", feeds.get(recyclerView.getChildAdapterPosition(view)).getDescripcion());
                        bundle.putString("precio", feeds.get(recyclerView.getChildAdapterPosition(view)).getCosto_pdf());

                        intent.putExtras(bundle);
                        startActivity(intent);

                    }

                } else {


                }


            }
        });


    }

    public void bajarFeed() {

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


                } else {
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
        if (requestCode == PDF_CODE && resultCode == RESULT_OK && data != null) {

            Uri selecPdf = data.getData();
            Intent intent = new Intent(Home.this, PantallaPDF.class);
            intent.putExtra("ViewType", "storage");
            intent.putExtra("FileUri", selecPdf.toString());
            startActivity(intent);


        }

    }

}