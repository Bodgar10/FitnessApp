package com.appfitnessapp.app.fitnessapp.Usuario.MenuRegistro;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import com.appfitnessapp.app.fitnessapp.Usuario.ListaComprado;
import com.appfitnessapp.app.fitnessapp.Usuario.PantallaPDF;
import com.appfitnessapp.app.fitnessapp.Usuario.UsuarioHome;
import com.appfitnessapp.app.fitnessapp.Usuario.UsuarioPerfil;
import com.appfitnessapp.app.fitnessapp.Usuario.Video;
import com.appfitnessapp.app.fitnessapp.videoplayer.VideoPlayer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.BaseMultiplePermissionsListener;

import java.util.ArrayList;
import java.util.List;

public class Home  extends AppCompatActivity {

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

    TextView txtComprado;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_02_feed);

        Toolbar toolbarback=findViewById(R.id.toolbar);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("");

        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new BaseMultiplePermissionsListener(){
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        super.onPermissionsChecked(report);
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        super.onPermissionRationaleShouldBeShown(permissions, token);
                    }
                }).check();

        bajarFeed();

        dbProvider = new DBProvider();
        bajarInfo = new BajarInfo();

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);


        imgAsesoria = findViewById(R.id.btnAsesoria);
       // imgPerfil = findViewById(R.id.imgPerfil);

        linearAsesoria = findViewById(R.id.linearAsesoria);

        txtComprado = findViewById(R.id.txtComprado);


/*

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

        */

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

        txtComprado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, ListaComprado.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
        recyclerView.setHasFixedSize(true);

        feeds = new ArrayList<>();
        adapterFeed = new AdapterFeed(feeds);
        recyclerView.setAdapter(adapterFeed);
        adapterFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (feeds.get(recyclerView.getChildAdapterPosition(view)).getTipo_feed().equals(Contants.VIDEO)) {

                    Intent intent = new Intent(Home.this, VideoPlayer.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("video",feeds.get(recyclerView.getChildAdapterPosition(view)).getUrl_tipo());
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
                        bundle.putString("HomeInicio","Asesoria");
                        bundle.putString("descripcion",feeds.get(recyclerView.getChildAdapterPosition(view)).getDescripcion());
                        bundle.putString("url",feeds.get(recyclerView.getChildAdapterPosition(view)).getUrl_tipo());
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
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finish();
        overridePendingTransition(
                getIntent().getIntExtra("anim id in", R.anim.move_in),
                getIntent().getIntExtra("anim id out", R.anim.move_leeft_in));

    }

}