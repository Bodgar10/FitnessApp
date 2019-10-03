package com.appfitnessapp.app.fitnessapp.Usuario;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appfitnessapp.app.fitnessapp.Adapters.AdapterRecetas;
import com.appfitnessapp.app.fitnessapp.Arrays.Ingredientes;
import com.appfitnessapp.app.fitnessapp.Arrays.PlanAlimenticio;
import com.appfitnessapp.app.fitnessapp.Arrays.Preparacion;
import com.appfitnessapp.app.fitnessapp.BaseDatos.BajarInfo;
import com.appfitnessapp.app.fitnessapp.BaseDatos.Contants;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UsuarioPlan  extends AppCompatActivity {

    ImageButton imgHome,imgPerfil,imgChat;
    RecyclerView recyclerView,recyclerView2,recyclerView3;
    AdapterRecetas adapter,adapter2,adapter3;
    ArrayList<PlanAlimenticio> recetas,recetas2,recetas3;
    TextView btnWorkouts,txtAlmuerzo,txtCena,txtDesayuno,txtNada;
    ImageView  btnRecordatorio;


    String id;


    static DBProvider dbProvider;
    BajarInfo bajarInfo;
    private static final String TAG = "BAJARINFO:";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_15_plan);

        Toolbar toolbarback=findViewById(R.id.toolbar);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("");


        dbProvider = new DBProvider();
        bajarInfo = new BajarInfo();

        id = FirebaseAuth.getInstance().getCurrentUser().getUid();


        imgHome=findViewById(R.id.imgHome);
        imgPerfil=findViewById(R.id.imgPerfil);
        imgChat=findViewById(R.id.imgChat);
        btnWorkouts=findViewById(R.id.btnWorkouts);

        recyclerView=findViewById(R.id.recycler1);
        recyclerView2=findViewById(R.id.recycler2);
        recyclerView3=findViewById(R.id.recycler3);

        txtAlmuerzo=findViewById(R.id.txtAlmuerzo);
        txtCena=findViewById(R.id.txtCena);
        txtDesayuno=findViewById(R.id.txtDesayuno);

        txtNada=findViewById(R.id.txtNada);

        btnRecordatorio=findViewById(R.id.btnRecordatorio);

        bajarRecetas();




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


        /*
        txtNada.setVisibility(View.GONE);
        imagsplash.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        txtDesayuno.setVisibility(View.VISIBLE);
        txtAlmuerzo.setVisibility(View.VISIBLE);
        recyclerView2.setVisibility(View.VISIBLE);
        txtCena.setVisibility(View.VISIBLE);
        recyclerView3.setVisibility(View.VISIBLE);




          txtNada.setVisibility(View.VISIBLE);
                                    imagsplash.setVisibility(View.VISIBLE);
                                    recyclerView.setVisibility(View.GONE);
                                    recyclerView2.setVisibility(View.GONE);
                                    recyclerView3.setVisibility(View.GONE);
                                    txtAlmuerzo.setVisibility(View.GONE);
                                    txtCena.setVisibility(View.GONE);
                                    txtDesayuno.setVisibility(View.GONE);
*/


        btnRecordatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UsuarioPlan.this, ListaRecordatorio.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.move_in, R.anim.move_leeft_in);

            }
        });

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UsuarioPlan.this, DetalleRecetas.class);
                Bundle bundle = new Bundle();
                bundle.putString("id",recetas.get(recyclerView.getChildAdapterPosition(v)).getId_plan_alimenticio());
                bundle.putString("nombre",recetas.get(recyclerView.getChildAdapterPosition(v)).getNombre_alimento());
                bundle.putString("imagen",recetas.get(recyclerView.getChildAdapterPosition(v)).getImagen_alimento());
                bundle.putString("tipo",recetas.get(recyclerView.getChildAdapterPosition(v)).getTipo_alimento());
                bundle.putString("calorias",recetas.get(recyclerView.getChildAdapterPosition(v)).getKilocalorias());
                bundle.putString("minutos",recetas.get(recyclerView.getChildAdapterPosition(v)).getMin_alimento());
                bundle.putString("porciones",recetas.get(recyclerView.getChildAdapterPosition(v)).getPorciones());
                bundle.putString("id_usuario",id);

                intent.putExtras(bundle);
                startActivity(intent);


            }
        });


        adapter2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UsuarioPlan.this, DetalleRecetas.class);
                Bundle bundle = new Bundle();
                bundle.putString("id",recetas2.get(recyclerView2.getChildAdapterPosition(v)).getId_plan_alimenticio());
                bundle.putString("nombre",recetas2.get(recyclerView2.getChildAdapterPosition(v)).getNombre_alimento());
                bundle.putString("imagen",recetas2.get(recyclerView2.getChildAdapterPosition(v)).getImagen_alimento());
                bundle.putString("tipo",recetas2.get(recyclerView2.getChildAdapterPosition(v)).getTipo_alimento());
                bundle.putString("calorias",recetas2.get(recyclerView2.getChildAdapterPosition(v)).getKilocalorias());
                bundle.putString("minutos",recetas2.get(recyclerView2.getChildAdapterPosition(v)).getMin_alimento());
                bundle.putString("porciones",recetas2.get(recyclerView2.getChildAdapterPosition(v)).getPorciones());
                bundle.putString("id_usuario",id);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        adapter3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UsuarioPlan.this, DetalleRecetas.class);
                Bundle bundle = new Bundle();
                bundle.putString("id",recetas3.get(recyclerView3.getChildAdapterPosition(v)).getId_plan_alimenticio());
                bundle.putString("nombre",recetas3.get(recyclerView3.getChildAdapterPosition(v)).getNombre_alimento());
                bundle.putString("imagen",recetas3.get(recyclerView3.getChildAdapterPosition(v)).getImagen_alimento());
                bundle.putString("tipo",recetas3.get(recyclerView3.getChildAdapterPosition(v)).getTipo_alimento());
                bundle.putString("calorias",recetas3.get(recyclerView3.getChildAdapterPosition(v)).getKilocalorias());
                bundle.putString("minutos",recetas3.get(recyclerView3.getChildAdapterPosition(v)).getMin_alimento());
                bundle.putString("porciones",recetas3.get(recyclerView3.getChildAdapterPosition(v)).getPorciones());
                bundle.putString("id_usuario",id);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });


        imgPerfil.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View view) {

                Intent intent = new Intent(UsuarioPlan.this, UsuarioPerfil.class);
                startActivity(intent);
                overridePendingTransition(R.anim.move, R.anim.move_leeft);
                finish();

            }
        });

        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UsuarioPlan.this, UsuarioHome.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.move_in, R.anim.move_leeft_in);
                finish();
            }
        });

        imgChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UsuarioPlan.this, ChatActivityUsuario.class);
                startActivity(intent);
                overridePendingTransition(R.anim.move, R.anim.move_leeft);
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


    public void bajarRecetas(){

        dbProvider = new DBProvider();
        dbProvider.tablaPlanAlimenticio().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                recetas.clear();
                recetas2.clear();
                recetas3.clear();

                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Log.e(TAG, "Feed: " + dataSnapshot);
                        PlanAlimenticio plan = snapshot.getValue(PlanAlimenticio.class);

                        if (plan.getId_plan_alimenticio() != null){
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
                            else{

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


    private void updateUi(ArrayList<PlanAlimenticio> books,ArrayList<PlanAlimenticio> books1,ArrayList<PlanAlimenticio> books2) {

        if(books.size() > 0 || books1.size()>0 || books2.size()>0) {
            txtNada.setVisibility(View.GONE);
            txtAlmuerzo.setVisibility(View.VISIBLE);
            txtCena.setVisibility(View.VISIBLE);
            txtDesayuno.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView2.setVisibility(View.VISIBLE);
            recyclerView3.setVisibility(View.VISIBLE);

        } else {
            txtNada.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            recyclerView2.setVisibility(View.GONE);
            recyclerView3.setVisibility(View.GONE);
            txtAlmuerzo.setVisibility(View.GONE);
            txtCena.setVisibility(View.GONE);
            txtDesayuno.setVisibility(View.GONE);
        }
    }




    private String getNextDate(String inputDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        inputDate = dateFormat.format(date);
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(date);

            c.add(Calendar.DATE, +1);
            inputDate = dateFormat.format(c.getTime());
            Log.d("asd", "selected date : "+inputDate);

            System.out.println(date);
        } catch (Exception e) {
            e.printStackTrace();
            inputDate ="";
        }
        return inputDate;
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
        overridePendingTransition(
                getIntent().getIntExtra("anim id in", R.anim.move_in),
                getIntent().getIntExtra("anim id out", R.anim.move_leeft_in));

    }





}
