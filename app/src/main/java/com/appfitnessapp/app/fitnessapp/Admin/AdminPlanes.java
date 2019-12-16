package com.appfitnessapp.app.fitnessapp.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.appfitnessapp.app.fitnessapp.Adapters.AdapterAdmin;
import com.appfitnessapp.app.fitnessapp.Adapters.AdapterAdminListaPlanes;
import com.appfitnessapp.app.fitnessapp.Adapters.AdapterPlanes;
import com.appfitnessapp.app.fitnessapp.Adapters.AdapterPreguntas;
import com.appfitnessapp.app.fitnessapp.Arrays.Planes;
import com.appfitnessapp.app.fitnessapp.Arrays.Preguntas;
import com.appfitnessapp.app.fitnessapp.BaseDatos.BajarInfo;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminPlanes extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterAdminListaPlanes adapter;
    ArrayList<Planes> plan;
    BajarInfo bajarInfo;
    private static final String TAG = "BAJARINFO:";
    static DBProvider dbProvider;


    private ProgressDialog progressDialog;

    ImageButton btnAgregar;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_planes);


        Toolbar toolbarback=findViewById(R.id.toolbar);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("Planes");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        bajarPlanes();


        id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);


        btnAgregar=findViewById(R.id.btnAgregarPlan);

        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        plan=new ArrayList<>();
        adapter = new AdapterAdminListaPlanes(plan);
        recyclerView.setAdapter(adapter);


        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminPlanes.this, AgregarPlanes.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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

                        if (planes.getId_plan()!=null){
                            if (planes.getAdmin().equals(id)){
                                plan.add(planes);
                                adapter.notifyDataSetChanged();
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
