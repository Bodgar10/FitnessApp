package com.appfitnessapp.app.fitnessapp.Usuario;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.appfitnessapp.app.fitnessapp.Adapters.AdapterPlanes;
import com.appfitnessapp.app.fitnessapp.Arrays.Planes;
import com.appfitnessapp.app.fitnessapp.R;

import java.util.ArrayList;

public class TipoPlanes extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterPlanes adapter;
    ArrayList<Planes> planes;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_06_planes);

        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        int spanCount = 1;
        int spacing_left = 5;
        int spacing_top=0;
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing_left, spacing_top));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        //  recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));
        recyclerView.setLayoutManager(layoutManager);
        planes = new ArrayList<>();
        adapter=new AdapterPlanes(planes);
        recyclerView.setAdapter(adapter);


        Planes  planes0 = new Planes("Basico","Lo mas vendido","6 meses",
                "El mejor plan para empezar con los ejercicios y a perder peso");

        Planes  plane1 = new Planes("Intermedio","Para susperarse","8 meses",
                "Plan mas completo para poder llevar a cabo una rutina mas espec ofkofkokfok fokfofkok ofkfofkfok ofkfofkfokfofko ializada");

        Planes  planes2 = new Planes("Experto","Para susperarse","11 meses",
                "El plan mas completo con todo lo que necesitas para perder peso tonificar el cuerpo y tener buena salud");



        planes.add(planes0);
        planes.add(plane1);
        planes.add(planes2);

        adapter.notifyDataSetChanged();

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


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

}
