package com.appfitnessapp.app.fitnessapp.Usuario;

import android.graphics.Rect;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appfitnessapp.app.fitnessapp.Adapters.AdapterAsesorias;
import com.appfitnessapp.app.fitnessapp.Adapters.AdapterImagenes;
import com.appfitnessapp.app.fitnessapp.Adapters.AdapterRecetas;
import com.appfitnessapp.app.fitnessapp.Adapters.AdapterRutinas;
import com.appfitnessapp.app.fitnessapp.Arrays.Ejercicios;
import com.appfitnessapp.app.fitnessapp.Arrays.ImagenesEjercicios;
import com.appfitnessapp.app.fitnessapp.R;

import java.util.ArrayList;

public class RutinaUsuario extends AppCompatActivity {

    ImageView imgRutina;
    TextView txtDescripcion,txtRutina;
    RecyclerView recyclerView,recyclerViewImg;
    AdapterRutinas adapter;
    ArrayList<Ejercicios>ejercicios;
    AdapterImagenes adapterImg;
    ArrayList<ImagenesEjercicios>ejerciciosImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_20_rutina);

        Toolbar toolbarback=findViewById(R.id.include);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("Rutina");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        imgRutina=findViewById(R.id.imgRutina);

        txtDescripcion=findViewById(R.id.txtDescripcion);
        txtRutina=findViewById(R.id.txtTipoRutina);

        recyclerView=findViewById(R.id.recyclerview);

        recyclerViewImg=findViewById(R.id.recycler_viewImg);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewImg.setLayoutManager(new LinearLayoutManager(this));
        int spanCount = 1;
        int spacing_left = 5;
        int spacing_top=0;
        recyclerViewImg.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing_left, spacing_top));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewImg.setLayoutManager(layoutManager);

        ejercicios=new ArrayList<>();
        ejerciciosImg = new ArrayList<>();


        adapter=new AdapterRutinas(ejercicios);
        adapterImg=new AdapterImagenes(ejerciciosImg);

        recyclerView.setAdapter(adapter);
        recyclerViewImg.setAdapter(adapterImg);




        ImagenesEjercicios ejerciciosimg=new ImagenesEjercicios("","");
        ImagenesEjercicios ejerciciosimg1=new ImagenesEjercicios("","");
        ImagenesEjercicios ejerciciosimg2=new ImagenesEjercicios("","");
        ImagenesEjercicios ejerciciosimg3=new ImagenesEjercicios("","");
        ImagenesEjercicios ejerciciosimg4=new ImagenesEjercicios("","");
        ImagenesEjercicios ejerciciosimg5=new ImagenesEjercicios("","");






        Ejercicios ejercicio0=new Ejercicios("20 pull ups (dominadas)","20 repeticiones","5 rondas de ");
        Ejercicios ejercicio1=new Ejercicios("30 pull ups (flexiones)","40 repeticiones","2 rondas de ");
        Ejercicios ejercicio2=new Ejercicios("50 pull ups (abdominales)","10 repeticiones","3 rondas de ");
        Ejercicios ejercicio3=new Ejercicios("10 pull ups (sentadillas)","10 repeticiones","3 rondas de ");


        ejercicios.add(ejercicio0);
        ejercicios.add(ejercicio1);
        ejercicios.add(ejercicio2);
        ejercicios.add(ejercicio3);


        ejerciciosImg.add(ejerciciosimg);
        ejerciciosImg.add(ejerciciosimg1);
        ejerciciosImg.add(ejerciciosimg2);
        ejerciciosImg.add(ejerciciosimg3);
        ejerciciosImg.add(ejerciciosimg4);
        ejerciciosImg.add(ejerciciosimg5);


        adapter.notifyDataSetChanged();

        adapterImg.notifyDataSetChanged();


        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        adapterImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
