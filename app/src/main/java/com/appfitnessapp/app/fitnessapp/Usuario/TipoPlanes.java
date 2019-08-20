package com.appfitnessapp.app.fitnessapp.Usuario;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appfitnessapp.app.fitnessapp.Adapters.AdapterPlanes;
import com.appfitnessapp.app.fitnessapp.Arrays.Feed;
import com.appfitnessapp.app.fitnessapp.Arrays.Planes;
import com.appfitnessapp.app.fitnessapp.BaseDatos.BajarInfo;
import com.appfitnessapp.app.fitnessapp.BaseDatos.Contants;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.Login.IniciarSesion;
import com.appfitnessapp.app.fitnessapp.Login.Registro;
import com.appfitnessapp.app.fitnessapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class TipoPlanes extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterPlanes adapter;
    ArrayList<Planes> plan;
    BajarInfo bajarInfo;
    private static final String TAG = "BAJARINFO:";
    static DBProvider dbProvider;

    String costo;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_06_planes);

        bajarInfo = new BajarInfo();
        dbProvider = new DBProvider();

        bajarPlanes();

        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        int spanCount = 1;
        int spacing_left = 5;
        int spacing_top=0;
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing_left, spacing_top));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        //  recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));
        recyclerView.setLayoutManager(layoutManager);
        plan = new ArrayList<>();
        adapter=new AdapterPlanes(plan);
        recyclerView.setAdapter(adapter);



        adapter.notifyDataSetChanged();

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TipoPlanes.this, MetodoPago.class);
                Bundle bundle = new Bundle();
                bundle.putString("costo",plan.get(recyclerView.getChildAdapterPosition(v)).getCosto_plan());
                bundle.putString("meses",plan.get(recyclerView.getChildAdapterPosition(v)).getMeses_plan());
                intent.putExtras(bundle);
                startActivity(intent);


            }
        });

    }


    public void bajarPlanes(){


        dbProvider = new DBProvider();
        dbProvider.planes().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                plan.clear();

                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Log.e(TAG, "Feed: " + dataSnapshot);
                        Planes planes = snapshot.getValue(Planes.class);

                       // costo =String.valueOf(planes.getCosto_plan());

                        plan.add(planes);
                        adapter.notifyDataSetChanged();


                        SimpleDateFormat dateDia = new SimpleDateFormat("dd", Locale.getDefault());
                        SimpleDateFormat dateMes = new SimpleDateFormat("MM", Locale.getDefault());
                        SimpleDateFormat dateAnio = new SimpleDateFormat("yyyy", Locale.getDefault());

                        Date date = new Date();

                        String diaHoy = dateDia.format(date);
                        String mesHoy = dateMes.format(date);
                        String anioHoy = dateAnio.format(date);



                        if (planes.getMeses_plan().equals("12")){

                            anioHoy +=1;


                        }

                        else if (planes.getMeses_plan().equals("03")){

                            if(mesHoy.equals("12")){
                                mesHoy= String.valueOf(03);
                                anioHoy +=1;

                            }
                            else if (mesHoy.equals("11")){

                                mesHoy= String.valueOf(02);
                                anioHoy +=1;

                            }

                            else if (mesHoy.equals("10")){

                                mesHoy= String.valueOf(01);
                                anioHoy +=1;

                            }
                            else {

                                mesHoy +=3;
                            }


                        }

                        else {

                            if (mesHoy.equals("12")){

                                mesHoy = String.valueOf(06);
                                anioHoy +=1;

                            }
                            else if(mesHoy.equals("11")){

                                mesHoy = String.valueOf(05);
                                anioHoy +=1;

                            }

                            else if (mesHoy.equals("10")){

                                mesHoy = String.valueOf(04);
                                anioHoy +=1;

                            }

                            else if (mesHoy.equals("09")){

                                mesHoy = String.valueOf(03);
                                anioHoy +=1;

                            }

                            else if (mesHoy.equals("08")){

                                mesHoy = String.valueOf(02);
                                anioHoy +=1;

                            }
                            else if (mesHoy.equals("07")){

                                mesHoy = String.valueOf(01);
                                anioHoy +=1;
                            }

                            else {

                                mesHoy+=6;
                            }
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
