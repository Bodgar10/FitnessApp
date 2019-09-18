package com.appfitnessapp.app.fitnessapp.Admin;

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

import com.appfitnessapp.app.fitnessapp.Adapters.AdapterAsesorias;
import com.appfitnessapp.app.fitnessapp.Adapters.AdapterChat;
import com.appfitnessapp.app.fitnessapp.Arrays.Inscritos;
import com.appfitnessapp.app.fitnessapp.Arrays.Usuarios;
import com.appfitnessapp.app.fitnessapp.BaseDatos.BajarInfo;
import com.appfitnessapp.app.fitnessapp.BaseDatos.Contants;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ListaChat extends AppCompatActivity {


    AdapterChat adapter;
    ArrayList<Usuarios> asesorias;
    RecyclerView recyclerView;
    String id;

    private ProgressDialog progressDialog;
    private static final String TAG = "BAJARINFO:";
    static DBProvider dbProvider;
    BajarInfo bajarInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_lista_chat);

        Toolbar toolbarback=findViewById(R.id.toolbar);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("Chats");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        bajarUsuarios();
        dbProvider = new DBProvider();
        bajarInfo = new BajarInfo();
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);

        recyclerView=findViewById(R.id.recyclerview);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        asesorias=new ArrayList<>();
        adapter=new AdapterChat(asesorias);
        recyclerView.setAdapter(adapter);




        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ListaChat.this, ChatActivityAdmin.class);
                startActivity(intent);

            }
        });



    }

    public void bajarUsuarios(){
        dbProvider = new DBProvider();
        dbProvider.usersRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                asesorias.clear();
                Log.e(TAG,"Usuarios 4: ");
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                        Log.e(TAG, "Usuarios: " + snapshot);
                        Usuarios usuarios = snapshot.getValue(Usuarios.class);

                        if (usuarios.getId_usuario() != null){
                            if (usuarios.getTipo_usuario().equals(Contants.USUARIO)) {
                                asesorias.add(usuarios);
                                adapter.notifyDataSetChanged();
                                progressDialog.dismiss();
                            }


                        }
                    }
                }else{
                    Log.e(TAG,"Usuarios 3: ");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG,"ERROR: ");
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
        intent = new Intent(this, AsesoriasAdmin.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
        overridePendingTransition(
                getIntent().getIntExtra("anim id in", R.anim.move_in),
                getIntent().getIntExtra("anim id out", R.anim.move_leeft_in));

    }

}
