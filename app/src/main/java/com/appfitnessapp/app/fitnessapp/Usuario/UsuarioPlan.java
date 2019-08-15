package com.appfitnessapp.app.fitnessapp.Usuario;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.appfitnessapp.app.fitnessapp.Adapters.AdapterPlanes;
import com.appfitnessapp.app.fitnessapp.Adapters.AdapterRecetas;
import com.appfitnessapp.app.fitnessapp.Arrays.Feed;
import com.appfitnessapp.app.fitnessapp.Arrays.Recetas;
import com.appfitnessapp.app.fitnessapp.BaseDatos.BajarInfo;
import com.appfitnessapp.app.fitnessapp.BaseDatos.Contants;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.Login.IniciarSesion;
import com.appfitnessapp.app.fitnessapp.Login.Registro;
import com.appfitnessapp.app.fitnessapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UsuarioPlan  extends AppCompatActivity {

    ImageButton imgHome,imgPerfil,imgChat;
    RecyclerView recyclerView;
    AdapterRecetas adapter;
    ArrayList<Recetas> recetas;
    TextView btnWorkouts;


    static DBProvider dbProvider;
    BajarInfo bajarInfo;
    private static final String TAG = "BAJARINFO:";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_15_plan);

        dbProvider = new DBProvider();
        bajarInfo = new BajarInfo();

        imgHome=findViewById(R.id.imgHome);
        imgPerfil=findViewById(R.id.imgPerfil);
        imgChat=findViewById(R.id.imgChat);
        btnWorkouts=findViewById(R.id.btnWorkouts);

        recyclerView=findViewById(R.id.recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        int spanCount = 1;
        int spacing_left = 10;
        int spacing_top=0;
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing_left, spacing_top));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recetas = new ArrayList<>();
        adapter=new AdapterRecetas(recetas);
        recyclerView.setAdapter(adapter);


        Recetas recetas0=new Recetas("","Desayuno","Huevo con jamon");
        Recetas recetas1=new Recetas("","Comida","Bistec de cerdo");
        Recetas recetas2=new Recetas("","Cena","Te de limon");
        Recetas recetas3=new Recetas("","Comida","Filete de pescado");

        recetas.add(recetas0);
        recetas.add(recetas1);
        recetas.add(recetas2);
        recetas.add(recetas3);





        adapter.notifyDataSetChanged();

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UsuarioPlan.this, DetalleRecetas.class);
                Bundle bundle = new Bundle();
                bundle.putString("nombre",recetas.get(recyclerView.getChildAdapterPosition(v)).getNombre_comida());
                bundle.putString("imagen",recetas.get(recyclerView.getChildAdapterPosition(v)).getImagen_receta());
                bundle.putString("tipo",recetas.get(recyclerView.getChildAdapterPosition(v)).getTiempo_comida());
                intent.putExtras(bundle);
                startActivity(intent);


            }
        });

        imgPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(UsuarioPlan.this, UsuarioPerfil.class);
                startActivity(intent);
                finish();

            }
        });

        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UsuarioPlan.this, UsuarioHome.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        imgChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UsuarioPlan.this, UsuarioChat.class);
                startActivity(intent);
                finish();

            }
        });

        btnWorkouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UsuarioPlan.this, UsuarioPlanWorkouts.class);
                startActivity(intent);

            }
        });

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


    public void bajarFeed(){

        dbProvider = new DBProvider();
        dbProvider.tablaFeed().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                recetas.clear();

                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Log.e(TAG, "Feed: " + dataSnapshot);
                        Recetas receta = snapshot.getValue(Recetas.class);

                        recetas.add(receta);
                        adapter.notifyDataSetChanged();

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
}
