package com.appfitnessapp.app.fitnessapp.Admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appfitnessapp.app.fitnessapp.Adapters.AdapterRecetas;
import com.appfitnessapp.app.fitnessapp.Arrays.PlanAlimenticio;
import com.appfitnessapp.app.fitnessapp.Arrays.Usuarios;
import com.appfitnessapp.app.fitnessapp.BaseDatos.Contants;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.R;
import com.appfitnessapp.app.fitnessapp.Usuario.UsuarioPlan;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class AdminPlanUsuario extends AppCompatActivity {

    TextView btnWorkouts;
    RecyclerView recyclerView,recyclerView2,recyclerView3;
    AdapterRecetas adapter,adapter2,adapter3;
    ArrayList<PlanAlimenticio> recetas,recetas2,recetas3;

    ImageButton btnAgregarPlan;


    private ProgressDialog progressDialog;
    private static final String TAG = "BAJARINFO:";
    static DBProvider dbProvider;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_09_plan);

        Toolbar toolbarback=findViewById(R.id.include);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("Plan");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getString("id");
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);

        dbProvider = new DBProvider();

        bajarPlan();

        btnWorkouts=findViewById(R.id.btnWorkouts);

        btnAgregarPlan=findViewById(R.id.btnAgregarPlan);

        btnAgregarPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminPlanUsuario.this, AgregarRecetas.class);
                Bundle bundle = new Bundle();
                bundle.putString("id",id);
                intent.putExtras(bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });



        recyclerView=findViewById(R.id.recycler1);
        recyclerView2=findViewById(R.id.recycler2);
        recyclerView3=findViewById(R.id.recycler3);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        recyclerView3.setLayoutManager(new LinearLayoutManager(this));


        int spanCount = 1;
        int spacing_left = 10;
        int spacing_top=0;
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing_left, spacing_top));
        recyclerView2.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing_left, spacing_top));
        recyclerView3.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing_left, spacing_top));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);


        recyclerView.setLayoutManager(layoutManager);
        recyclerView2.setLayoutManager(layoutManager2);
        recyclerView3.setLayoutManager(layoutManager3);

        recetas = new ArrayList<>();
        recetas2 = new ArrayList<>();
        recetas3 = new ArrayList<>();
        adapter=new AdapterRecetas(recetas);
        adapter2=new AdapterRecetas(recetas2);
        adapter3=new AdapterRecetas(recetas3);


        recyclerView.setAdapter(adapter);
        recyclerView2.setAdapter(adapter2);
        recyclerView3.setAdapter(adapter3);


        btnWorkouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminPlanUsuario.this, AdminWorkouts.class);
                Bundle bundle = new Bundle();
                bundle.putString("id",id);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminPlanUsuario.this, AdminRecetaDetalle.class);
                Bundle bundle = new Bundle();
                bundle.putString("id_usuario",id);
                bundle.putString("id_receta",recetas.get(recyclerView.getChildAdapterPosition(v)).getId_plan_alimenticio());
                bundle.putString("nombre",recetas.get(recyclerView.getChildAdapterPosition(v)).getNombre_alimento());
                bundle.putString("imagen",recetas.get(recyclerView.getChildAdapterPosition(v)).getImagen_alimento());
                bundle.putString("tipo",recetas.get(recyclerView.getChildAdapterPosition(v)).getTipo_alimento());
                bundle.putString("calorias",recetas.get(recyclerView.getChildAdapterPosition(v)).getKilocalorias());
                bundle.putString("minutos",recetas.get(recyclerView.getChildAdapterPosition(v)).getMin_alimento());
                bundle.putString("porciones",recetas.get(recyclerView.getChildAdapterPosition(v)).getPorciones());
                intent.putExtras(bundle);
                startActivity(intent);


            }
        });

        adapter2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminPlanUsuario.this, AdminRecetaDetalle.class);
                Bundle bundle = new Bundle();
                bundle.putString("id_usuario",id);
                bundle.putString("id_receta",recetas2.get(recyclerView2.getChildAdapterPosition(v)).getId_plan_alimenticio());
                bundle.putString("nombre",recetas2.get(recyclerView2.getChildAdapterPosition(v)).getNombre_alimento());
                bundle.putString("imagen",recetas2.get(recyclerView2.getChildAdapterPosition(v)).getImagen_alimento());
                bundle.putString("tipo",recetas2.get(recyclerView2.getChildAdapterPosition(v)).getTipo_alimento());
                bundle.putString("calorias",recetas2.get(recyclerView2.getChildAdapterPosition(v)).getKilocalorias());
                bundle.putString("minutos",recetas2.get(recyclerView2.getChildAdapterPosition(v)).getMin_alimento());
                bundle.putString("porciones",recetas2.get(recyclerView2.getChildAdapterPosition(v)).getPorciones());
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        adapter3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminPlanUsuario.this, AdminRecetaDetalle.class);
                Bundle bundle = new Bundle();
                bundle.putString("id_usuario",id);
                bundle.putString("id_receta",recetas3.get(recyclerView3.getChildAdapterPosition(v)).getId_plan_alimenticio());
                bundle.putString("nombre",recetas3.get(recyclerView3.getChildAdapterPosition(v)).getNombre_alimento());
                bundle.putString("imagen",recetas3.get(recyclerView3.getChildAdapterPosition(v)).getImagen_alimento());
                bundle.putString("tipo",recetas3.get(recyclerView3.getChildAdapterPosition(v)).getTipo_alimento());
                bundle.putString("calorias",recetas3.get(recyclerView3.getChildAdapterPosition(v)).getKilocalorias());
                bundle.putString("minutos",recetas3.get(recyclerView3.getChildAdapterPosition(v)).getMin_alimento());
                bundle.putString("porciones",recetas3.get(recyclerView3.getChildAdapterPosition(v)).getPorciones());
                intent.putExtras(bundle);
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


    public void bajarPlan(){
        dbProvider = new DBProvider();

        dbProvider.tablaPlanAlimenticio().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                recetas.clear();
                recetas2.clear();
                recetas3.clear();
                Log.e(TAG, "Usuarios 4: ");
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        //Log.e(TAG,"Usuarios: "+ snapshot);
                        Log.e(TAG, "Usuarios: " + snapshot);
                        PlanAlimenticio plan = snapshot.getValue(PlanAlimenticio.class);


                        if (plan.getId_usuario() != null){
                            if (plan.getId_usuario().equals(id)) {

                                if (plan.getTipo_alimento().equals(Contants.DESAYUNO)) {
                                    recetas.add(plan);
                                    adapter.notifyDataSetChanged();
                                }

                                if (plan.getTipo_alimento().equals(Contants.ALMUERZO)) {
                                    recetas2.add(plan);
                                    adapter2.notifyDataSetChanged();
                                }

                                if (plan.getTipo_alimento().equals(Contants.CENA)) {

                                    recetas3.add(plan);
                                    adapter3.notifyDataSetChanged();


                                }


                            }

                    }

                    }
                } else {
                    Log.e(TAG, "Usuarios 3: ");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "ERROR: ");
            }
        });
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
        finish();
    }




}
