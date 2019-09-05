package com.appfitnessapp.app.fitnessapp.Admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appfitnessapp.app.fitnessapp.Adapters.AdapterAsesorias;
import com.appfitnessapp.app.fitnessapp.Adapters.AdapterIngredientes;
import com.appfitnessapp.app.fitnessapp.Arrays.Asesorias;
import com.appfitnessapp.app.fitnessapp.Arrays.Inscritos;
import com.appfitnessapp.app.fitnessapp.Arrays.Usuarios;
import com.appfitnessapp.app.fitnessapp.BaseDatos.BajarInfo;
import com.appfitnessapp.app.fitnessapp.BaseDatos.Contants;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.Login.IniciarSesion;
import com.appfitnessapp.app.fitnessapp.Login.SplashPantalla;
import com.appfitnessapp.app.fitnessapp.R;
import com.appfitnessapp.app.fitnessapp.Usuario.Asesoria;
import com.appfitnessapp.app.fitnessapp.Usuario.DetalleRecetas;
import com.appfitnessapp.app.fitnessapp.Usuario.InformacionCompra;
import com.appfitnessapp.app.fitnessapp.Usuario.UsuarioHome;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AsesoriasAdmin extends AppCompatActivity {

    AdapterAsesorias adapter,adapter2;
    ArrayList<Usuarios> asesorias,asesorias2;
    RecyclerView recyclerReciente,recyclerFinalizar;
    TextView txtPendientes;
    CircularImageView imgPostPersona;

    private ProgressDialog progressDialog;
    private static final String TAG = "BAJARINFO:";
    static DBProvider dbProvider;
    BajarInfo bajarInfo;

    String id;

    private static FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_03_asesorias);

        Toolbar toolbarback=findViewById(R.id.toolbar);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("Asesorías");

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user==null){
            Intent intent=new Intent(AsesoriasAdmin.this, SplashPantalla.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        else{
            id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        }



        bajarUsuarios();



        dbProvider = new DBProvider();
        bajarInfo = new BajarInfo();
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);



        recyclerFinalizar=findViewById(R.id.recyclerFinalizar);
        recyclerReciente=findViewById(R.id.recyclerRecientes);
        txtPendientes=findViewById(R.id.txtPendientes);

        imgPostPersona=findViewById(R.id.imgPostPersona);



        recyclerFinalizar.setLayoutManager(new LinearLayoutManager(this));
        recyclerReciente.setLayoutManager(new LinearLayoutManager(this));
        asesorias=new ArrayList<>();
        asesorias2=new ArrayList<>();
        adapter=new AdapterAsesorias(asesorias);
        adapter2=new AdapterAsesorias(asesorias2);
        recyclerFinalizar.setAdapter(adapter);
        recyclerReciente.setAdapter(adapter2);



      //  adapter.notifyDataSetChanged();

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AsesoriasAdmin.this, PerfilUsuarioAdmin.class);
                Bundle bundle = new Bundle();
                bundle.putString("id",asesorias.get(recyclerFinalizar.getChildAdapterPosition(v)).getId_usuario());
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });


        txtPendientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AsesoriasAdmin.this, AsesoriasPendientes.class);
                startActivity(intent);

            }
        });

        imgPostPersona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AsesoriasAdmin.this, AdminAgregarFeed.class);
                startActivity(intent);

            }
        });


    }


    public void bajarUsuarios(){
        Log.e(TAG,"Usuarios 2: ");
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

                        if (usuarios.getId_usuario()!=null) {

                            if (usuarios.getTipo_usuario().equals(Contants.USUARIO)) {

                                bajarInscritos();
                                asesorias.add(usuarios);
                                asesorias2.add(usuarios);
                                adapter.notifyDataSetChanged();
                                adapter2.notifyDataSetChanged();
                                progressDialog.dismiss();

                            } else if (usuarios.getTipo_usuario().equals(Contants.ADMIN)) {

                                if (usuarios.getId_usuario().equals(id)) {

                                    if (usuarios.getFoto_usuario().equals("nil")) {
                                        try {
                                            URL urlfeed = new URL(usuarios.getFoto_usuario());
                                            Picasso.get().load(String.valueOf(urlfeed))
                                                    .error(R.mipmap.ic_launcher)
                                                    .fit()
                                                    .noFade()
                                                    .into(imgPostPersona);
                                        } catch (MalformedURLException e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        loadImageFromUrl(usuarios.getFoto_usuario());
                                        progressDialog.dismiss();
                                    }

                                }
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

    public void bajarInscritos(){
        dbProvider = new DBProvider();

        dbProvider.tablaInscritos().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                        Log.e(TAG, "Usuarios: " + snapshot);
                        Inscritos inscritos = snapshot.getValue(Inscritos.class);



                        //fechaHoy
                        SimpleDateFormat dateMes = new SimpleDateFormat("MM", Locale.getDefault());
                        SimpleDateFormat dateAnio = new SimpleDateFormat("yyyy", Locale.getDefault());
                        Date date = new Date();
                        String mesHoy = dateMes.format(date);
                        String anioHoy = dateAnio.format(date);


                        //fechaBase
                        String fechaBase=inscritos.getFecha_limite();
                        SimpleDateFormat format1=new SimpleDateFormat("dd/MM/yyyy");
                        Date dt1;
                        try {
                            dt1 = format1.parse(fechaBase);
                            DateFormat format2=new SimpleDateFormat("MM");
                            DateFormat format3=new SimpleDateFormat("yyyy");
                            String mesBase=format2.format(dt1);
                            String AnioBase=format3.format(dt1);

                                if (inscritos.getId_pendiente().equals(false)){
                                    if (mesBase.equals(mesHoy)&&anioHoy.equals(AnioBase)) {


                                            adapter.notifyDataSetChanged();


                                    }

                                    else {

                                        adapter2.notifyDataSetChanged();

                                    }
                                }




                        } catch (ParseException e) {
                            e.printStackTrace();
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

    private void loadImageFromUrl(String url) {

        Picasso.get().load(url).into(imgPostPersona);
    }



}
