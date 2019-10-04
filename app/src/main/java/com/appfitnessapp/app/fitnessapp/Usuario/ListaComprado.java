package com.appfitnessapp.app.fitnessapp.Usuario;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appfitnessapp.app.fitnessapp.Adapters.AdapterComprado;
import com.appfitnessapp.app.fitnessapp.Adapters.AdapterPreguntas;
import com.appfitnessapp.app.fitnessapp.Admin.AsesoriasPendientes;
import com.appfitnessapp.app.fitnessapp.Admin.SolicitudAsesoria;
import com.appfitnessapp.app.fitnessapp.Arrays.Comprado;
import com.appfitnessapp.app.fitnessapp.Arrays.Preguntas;
import com.appfitnessapp.app.fitnessapp.Arrays.Respuestas;
import com.appfitnessapp.app.fitnessapp.BaseDatos.BajarInfo;
import com.appfitnessapp.app.fitnessapp.BaseDatos.ChatContractUsuario;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.BaseMultiplePermissionsListener;

import java.util.ArrayList;
import java.util.List;

public class ListaComprado extends AppCompatActivity {

    TextView txtAgregar;
    private ProgressDialog progressDialog;
    private static final String TAG = "BAJARINFO:";
    static DBProvider dbProvider;
    RecyclerView recyclerView;

    AdapterComprado adapter;
    ArrayList<Comprado> comprados;

    String id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_listaejericicos);

        Toolbar toolbarback=findViewById(R.id.toolbar);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("Comprado");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        dbProvider = new DBProvider();


        bajarPDF();


        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new BaseMultiplePermissionsListener(){
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        super.onPermissionsChecked(report);
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        super.onPermissionRationaleShouldBeShown(permissions, token);
                    }
                }).check();





        txtAgregar=findViewById(R.id.txtNuevo);
        txtAgregar.setVisibility(View.GONE);


        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        comprados=new ArrayList<>();
        adapter = new AdapterComprado(comprados);
        recyclerView.setAdapter(adapter);


        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListaComprado.this, PantallaPDF.class);
                intent.putExtra("ViewType","internet");
                Bundle bundle = new Bundle();
                bundle.putString("pdf",comprados.get(recyclerView.getChildAdapterPosition(v)).getUrl_pdf());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }


    public void bajarPDF(){
        dbProvider = new DBProvider();
        progressDialog.setMessage("Cargando Información...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        dbProvider.tablaPdf().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                comprados.clear();
                Log.e(TAG, "Usuarios 4: ");
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Comprado comprado = snapshot.getValue(Comprado.class);
                        if (comprado.getId_pdf() != null){
                            if (comprado.getId_usuario().equals(id)) {
                                comprados.add(comprado);
                                adapter.notifyDataSetChanged();
                                progressDialog.dismiss();

                            }

                    }
                    }
                } else {
                    progressDialog.dismiss();
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
        Intent intent;
        intent = new Intent(this, UsuarioPerfil.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        overridePendingTransition(
                getIntent().getIntExtra("anim id in", R.anim.move_in),
                getIntent().getIntExtra("anim id out", R.anim.move_leeft_in));

    }

}
