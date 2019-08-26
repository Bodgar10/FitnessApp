package com.appfitnessapp.app.fitnessapp.Usuario;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appfitnessapp.app.fitnessapp.Adapters.AdapterImagenes;
import com.appfitnessapp.app.fitnessapp.Adapters.AdapterRutinas;
import com.appfitnessapp.app.fitnessapp.Arrays.Ejercicios;
import com.appfitnessapp.app.fitnessapp.Arrays.ImagenesEjercicios;
import com.appfitnessapp.app.fitnessapp.BaseDatos.BajarInfo;
import com.appfitnessapp.app.fitnessapp.BaseDatos.Contants;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RutinaUsuario extends AppCompatActivity {

    ImageView imgRutina,imgVideo,img1,img2,img3;
    TextView txtDescripcion,txtRutina;
    RecyclerView recyclerView,recyclerViewImg;
    AdapterRutinas adapter;
    ArrayList<Ejercicios>ejercicios;
    AdapterImagenes adapterImg;
    ArrayList<ImagenesEjercicios>ejerciciosImg;

    ArrayList<String>imagenes;


    String descripcion,idEjercicio,videoUrl, id_ejercicioTabla;

    static DBProvider dbProvider;
    BajarInfo bajarInfo;
    private static final String TAG = "BAJARINFO:";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_20_rutina);

        Toolbar toolbarback=findViewById(R.id.include);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("Rutina");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        Bundle extras = getIntent().getExtras();
        if (extras != null){
            descripcion = extras.getString("descripcion");
            idEjercicio = extras.getString("id");
            bajarEjercicios();

        }



        imgRutina=findViewById(R.id.imgRutina);

        img1=findViewById(R.id.img1);
        img2=findViewById(R.id.img2);
        img3=findViewById(R.id.img3);



        imgVideo=findViewById(R.id.imgVideo);

        txtDescripcion=findViewById(R.id.txtDescripcion);

        recyclerView=findViewById(R.id.recyclerview);
        recyclerViewImg=findViewById(R.id.recycler_viewImg);

        txtDescripcion.setText(descripcion);

        imgVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RutinaUsuario.this, Video.class);
                Bundle bundle = new Bundle();
                bundle.putString("video",videoUrl);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewImg.setLayoutManager(new LinearLayoutManager(this));

        ejercicios=new ArrayList<>();
        ejerciciosImg = new ArrayList<>();

        imagenes = new ArrayList<>();



        adapter=new AdapterRutinas(ejercicios);
        adapterImg=new AdapterImagenes(ejerciciosImg);

        recyclerView.setAdapter(adapter);
        recyclerViewImg.setAdapter(adapterImg);


        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }



    public void bajarEjercicios(){

        Log.e(TAG, "Ejercicio: " + idEjercicio);
        dbProvider = new DBProvider();
        dbProvider.tablaPlanEntrenamiento().child(idEjercicio).child(Contants.EJERCICIOS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ejercicios.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Log.e(TAG, "Ejercicio2: " + snapshot);

                        Ejercicios ejercicio = snapshot.getValue(Ejercicios.class);

                        if (ejercicio.getId_ejercicio()!=null){
                            videoUrl=ejercicio.getVideo_ejercicio();
                           // Picasso.get().load(ejercicio.getImagenes_ejercicio()).into(imgRutina);
                            ejercicios.add(ejercicio);
                            adapter.notifyDataSetChanged();
                            bajarImagenes(ejercicio.getId_ejercicio(),ejercicio.getNombre_ejercicio());
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

    public void bajarImagenes(String id_ejercicio, final String titulo){

        dbProvider = new DBProvider();
        dbProvider.tablaPlanEntrenamiento().child(idEjercicio).child(Contants.EJERCICIOS).child(id_ejercicio).child(Contants.IMAGENES_EJERCICIO).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ejerciciosImg.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Log.e(TAG, "Feed: " + dataSnapshot);
                        ImagenesEjercicios ejercicio = dataSnapshot.getValue(ImagenesEjercicios.class);
                        ejercicio.setNombre(titulo);
                        ejerciciosImg.add(ejercicio);
                        adapterImg.notifyDataSetChanged();

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menuswitch, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
        final private int spanCount, spacing, spacing_top;
        final private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing_left, int spacing_top) {
            this.spanCount = spanCount;
            this.spacing = spacing_left;
            this.includeEdge = true;
            this.spacing_top = spacing_top;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item phases_position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount;
                outRect.right = (column + 1) * spacing / spanCount;

                if (position < spanCount) { // top edge
                    outRect.top = spacing_top;
                }
                outRect.bottom = spacing_top; // item bottom
            } else {
                outRect.left = column * spacing / spanCount;
                outRect.right = spacing - (column + 1) * spacing / spanCount;
                if (position >= spanCount) {
                    outRect.top = spacing_top; // item top
                }
            }
        }
    }
}
