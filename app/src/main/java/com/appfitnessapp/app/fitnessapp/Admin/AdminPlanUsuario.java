package com.appfitnessapp.app.fitnessapp.Admin;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.appfitnessapp.app.fitnessapp.Adapters.AdapterRecetas;
import com.appfitnessapp.app.fitnessapp.Arrays.Recetas;
import com.appfitnessapp.app.fitnessapp.R;
import com.appfitnessapp.app.fitnessapp.Usuario.UsuarioPlan;

import java.util.ArrayList;

public class AdminPlanUsuario extends AppCompatActivity {

    TextView btnWorkouts;
    RecyclerView recyclerView;
    AdapterRecetas adapter;
    ArrayList<Recetas> recetas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_09_plan);

        btnWorkouts=findViewById(R.id.btnWorkouts);


        recyclerView=findViewById(R.id.recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        int spanCount = 1;
        int spacing_left = 10;
        int spacing_top=0;
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing_left, spacing_top));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        //  recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));
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


            }
        });


        btnWorkouts.setOnClickListener(new View.OnClickListener() {
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
