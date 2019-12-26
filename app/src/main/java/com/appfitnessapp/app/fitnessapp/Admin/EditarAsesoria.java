package com.appfitnessapp.app.fitnessapp.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.appfitnessapp.app.fitnessapp.Arrays.AsesoriasInfo;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class EditarAsesoria extends AppCompatActivity {

    EditText edtDescripcionAsesoria,edtDescripcionAlimentos,edtDescripcionRutina,edtCosto;
    ImageView imgAsesoria,imgRutina,imgAlimentos;

    LinearLayout btnGuardarAsesoria;


    private ProgressDialog progressDialog;
    private static final String TAG = "BAJARINFO:";
    static DBProvider dbProvider;

    String videoUrl,costoE,alimentosE,rutinaE,asesoriaE,idAsesoria;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_editar_asesoria);


        Toolbar toolbarback = findViewById(R.id.toolbar);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("Editar Asesoría");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);


        dbProvider = new DBProvider();

        bajarAsesoria();

        btnGuardarAsesoria=findViewById(R.id.btnGuardarAsesoria);



        edtDescripcionAsesoria=findViewById(R.id.edtDescripcion);
        edtDescripcionAlimentos=findViewById(R.id.edtAlimentos);
        edtDescripcionRutina=findViewById(R.id.edtRutina);
        edtCosto=findViewById(R.id.edtCosto);


        imgAsesoria=findViewById(R.id.imgAsesoria);
        imgAlimentos=findViewById(R.id.imgAlimentos);
        imgRutina=findViewById(R.id.imgRutina);



        btnGuardarAsesoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String costo=edtCosto.getText().toString().trim();
                String rutina=edtDescripcionRutina.getText().toString().trim();
                String alimentos=edtDescripcionAlimentos.getText().toString().trim();
                String asesoria=edtDescripcionAsesoria.getText().toString().trim();


                if (!costo.equals(costoE)){
                    dbProvider.updateCostoAsesoria(idAsesoria,costo);
                    Toast.makeText(EditarAsesoria.this, "Se actualizó el costo.", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(EditarAsesoria.this, AdminAgregarFeed.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();

                }

                if (!rutina.equals(rutinaE)){
                    dbProvider.updateDescripcionRutinaAsesoria(idAsesoria,rutina);
                    Toast.makeText(EditarAsesoria.this, "Se actualizó el costo.", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(EditarAsesoria.this, AdminAgregarFeed.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();

                }

                if (!alimentos.equals(alimentosE)){
                    dbProvider.updateDescripcionAlimentosAsesoria(idAsesoria,alimentos);
                    Toast.makeText(EditarAsesoria.this, "Se actualizó el costo.", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(EditarAsesoria.this, AdminAgregarFeed.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();

                }

                if (!asesoria.equals(asesoriaE)){
                    dbProvider.updateDescripcionAsesoria(idAsesoria,asesoria);
                    Toast.makeText(EditarAsesoria.this, "Se actualizó el costo.", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(EditarAsesoria.this, AdminAgregarFeed.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();

                }

                if (costo.isEmpty()&&rutina.isEmpty()&&alimentos.isEmpty()&&asesoria.isEmpty()){
                    Toast.makeText(EditarAsesoria.this, "Necesitas rellenar los campos ", Toast.LENGTH_SHORT).show();
                }

                if (costo.equals(costoE)&&rutina.equals(rutinaE)&&alimentos.equals(alimentosE)&&asesoria.equals(asesoriaE)){
                    Intent intent=new Intent(EditarAsesoria.this, AdminAgregarFeed.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }


            }
        });


    }



    public void bajarAsesoria(){
        dbProvider = new DBProvider();

        progressDialog.setMessage("Cargando Información...");
        progressDialog.show();


        dbProvider.asesoriaInfo().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e(TAG, "Usuarios 4: ");
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        //Log.e(TAG,"Usuarios: "+ snapshot);
                        Log.e(TAG, "Usuarios: " + snapshot);
                        AsesoriasInfo asesorias = snapshot.getValue(AsesoriasInfo.class);

                        if (snapshot.getKey().equals(asesorias.getId_asesoria())){

                            edtCosto.setText( asesorias.getCosto_asesoria());
                            edtDescripcionRutina.setText( asesorias.getRutinas_descripcion());
                            edtDescripcionAlimentos.setText( asesorias.getAlimentos_descripcion());
                            edtDescripcionAsesoria.setText( asesorias.getDescripcion_asesoria());


                            videoUrl=asesorias.getVideo_explicativo();
                            costoE=asesorias.getCosto_asesoria();
                            alimentosE=asesorias.getAlimentos_descripcion();
                            rutinaE=asesorias.getRutinas_descripcion();
                            asesoriaE=asesorias.getDescripcion_asesoria();
                            idAsesoria=asesorias.getId_asesoria();

                            Picasso.get().load(asesorias.getImagen_portada()).into(imgAsesoria);
                            Picasso.get().load(asesorias.getRutinas_imagen()).into(imgRutina);
                            Picasso.get().load(asesorias.getAlimentos_imagen()).into(imgAlimentos);


                        }

                        progressDialog.dismiss();




                    }
                } else {
                    Log.e(TAG, "Usuarios 3: ");

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "ERROR: ");

                progressDialog.dismiss();

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
