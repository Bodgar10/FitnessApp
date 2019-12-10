package com.appfitnessapp.app.fitnessapp.menu_nuevo.Menu_UPago;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.appfitnessapp.app.fitnessapp.Adapters.AdapterEbook;
import com.appfitnessapp.app.fitnessapp.Adapters.AdapterRecetas;
import com.appfitnessapp.app.fitnessapp.Arrays.PlanAlimenticio;
import com.appfitnessapp.app.fitnessapp.BaseDatos.BajarInfo;
import com.appfitnessapp.app.fitnessapp.BaseDatos.Contants;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.Login.IniciarSesion;
import com.appfitnessapp.app.fitnessapp.R;
import com.appfitnessapp.app.fitnessapp.Usuario.ChatActivityUsuario;
import com.appfitnessapp.app.fitnessapp.Usuario.DetallePdf;
import com.appfitnessapp.app.fitnessapp.Usuario.DetalleRecetas;
import com.appfitnessapp.app.fitnessapp.Usuario.Imagen;
import com.appfitnessapp.app.fitnessapp.Usuario.PantallaPDF;
import com.appfitnessapp.app.fitnessapp.Usuario.UsuarioHome;
import com.appfitnessapp.app.fitnessapp.Usuario.UsuarioPerfil;
import com.appfitnessapp.app.fitnessapp.Usuario.UsuarioPlan;
import com.appfitnessapp.app.fitnessapp.Usuario.UsuarioPlanWorkouts;
import com.appfitnessapp.app.fitnessapp.videoplayer.VideoPlayer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.BaseMultiplePermissionsListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MenuAsesoriaPago extends Fragment {

    RecyclerView recyclerView,recyclerView2,recyclerView3;
    AdapterRecetas adapter,adapter2,adapter3;
    ArrayList<PlanAlimenticio> recetas,recetas2,recetas3;
    TextView btnWorkouts,txtAlmuerzo,txtCena,txtDesayuno,txtNada;


    String id;


    static DBProvider dbProvider;
    BajarInfo bajarInfo;
    private static final String TAG = "BAJARINFO:";

    public MenuAsesoriaPago() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.usuario_15_plan, container, false);



        dbProvider = new DBProvider();
        bajarInfo = new BajarInfo();

        id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        btnWorkouts=view.findViewById(R.id.btnWorkouts);

        recyclerView=view.findViewById(R.id.recycler1);
        recyclerView2=view.findViewById(R.id.recycler2);
        recyclerView3=view.findViewById(R.id.recycler3);

        txtAlmuerzo=view.findViewById(R.id.txtAlmuerzo);
        txtCena=view.findViewById(R.id.txtCena);
        txtDesayuno=view.findViewById(R.id.txtDesayuno);

        txtNada=view.findViewById(R.id.txtNada);


        bajarRecetas();




        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView3.setLayoutManager(new LinearLayoutManager(getContext()));


        int spanCount = 1;
        int spacing_left = 10;
        int spacing_top=0;
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing_left, spacing_top));
        recyclerView2.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing_left, spacing_top));
        recyclerView3.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing_left, spacing_top));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);


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



        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), DetalleRecetas.class);
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

                Intent intent = new Intent(getContext(), DetalleRecetas.class);
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

                Intent intent = new Intent(getContext(), DetalleRecetas.class);
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



        btnWorkouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), UsuarioPlanWorkouts.class);
                startActivity(intent);

            }
        });



        return view;
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





}
