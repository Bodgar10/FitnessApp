package com.appfitnessapp.app.fitnessapp.Admin;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appfitnessapp.app.fitnessapp.Adapters.AdapterIngredientes;
import com.appfitnessapp.app.fitnessapp.Arrays.AsesoriasInfo;
import com.appfitnessapp.app.fitnessapp.Arrays.Ingredientes;
import com.appfitnessapp.app.fitnessapp.Arrays.Inscritos;
import com.appfitnessapp.app.fitnessapp.BaseDatos.Contants;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class AgregarRecetas extends AppCompatActivity {

    TextView btnWorkouts,btnGuardar,txtSiguiente;
    EditText edtNombreComida,edtTiempo,edtCantidad, edtCalorias;
    ImageButton btnIngrediente,btnPaso;
    RadioButton checkDesayuno,checkComida,checkCena;
    LinearLayout btnImagen;
    ImageView imgReceta;
    Uri imgUri;
    ProgressDialog progressDialog;
    StorageReference mStorage;

    String keyPlan;

    ArrayList<Ingredientes> ingredientes;
    RecyclerView recyclerView,recyclerPreparacion,recyclerviewIngrediente;
    AdapterIngredientes adapterIngredientes;

    String id;
    private static final String TAG = "BAJARINFO:";
    static DBProvider dbProvider;

    String id_inscrito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_06_escogerplan);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        Toolbar toolbarback=findViewById(R.id.toolbar);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("Plan Alimenticio");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getString("id");
            bajarInscritos();
        }

        mStorage= FirebaseStorage.getInstance().getReference();

        btnWorkouts=findViewById(R.id.txtWorkoutsAdmin);
        btnGuardar=findViewById(R.id.txtGuardar);

        checkDesayuno=findViewById(R.id.checkDesayuno);
        checkComida=findViewById(R.id.checkComida);
        checkCena=findViewById(R.id.checkCena);

        btnImagen=findViewById(R.id.btnImagen);
        imgReceta=findViewById(R.id.imgReceta);


        edtNombreComida=findViewById(R.id.edtNombreComida);
        edtTiempo=findViewById(R.id.edtTiempo);
        edtCantidad=findViewById(R.id.edtCantidad);
        edtCalorias=findViewById(R.id.edtCalorias);


        btnIngrediente=findViewById(R.id.btnAgregarIngrediente);
        btnPaso=findViewById(R.id.btnPaso);

        recyclerView=findViewById(R.id.recyclerview);
        recyclerPreparacion=findViewById(R.id.recyclerPreparacion);
        recyclerviewIngrediente=findViewById(R.id.recyclerviewIngrediente);

        txtSiguiente=findViewById(R.id.txtSiguiente);

        recyclerviewIngrediente.setLayoutManager(new LinearLayoutManager(this));
        recyclerPreparacion.setLayoutManager(new LinearLayoutManager(this));
        ingredientes=new ArrayList<>();
        adapterIngredientes=new AdapterIngredientes(ingredientes);
        recyclerviewIngrediente.setAdapter(adapterIngredientes);

        dbProvider = new DBProvider();
//        id = FirebaseAuth.getInstance().getCurrentUser().getUid();



        adapterIngredientes.notifyDataSetChanged();
        adapterIngredientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnWorkouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AgregarRecetas.this, EscogerPlan.class);
                Bundle bundle = new Bundle();
                bundle.putString("id",id);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        checkDesayuno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkDesayuno.isChecked()) {
                    checkCena.setChecked(false);
                    checkComida.setChecked(false);
                }
            }
        });
        checkCena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCena.isChecked()) {
                    checkDesayuno.setChecked(false);
                    checkComida.setChecked(false);

                }
            }
        });
        checkComida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkComida.isChecked()) {
                    checkCena.setChecked(false);
                    checkDesayuno.setChecked(false);

                }
            }
        });


        btnImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(AgregarRecetas.this, Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
                    selectImage();
                }
                else {
                    ActivityCompat.requestPermissions(AgregarRecetas.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},9);
                }
            }
        });

        btnIngrediente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RecyclerView.LayoutManager lmmanager = new LinearLayoutManager(getApplicationContext()); recyclerviewIngrediente.setLayoutManager(lmmanager);
                ingredientes.add(new Ingredientes("","","",""));
                adapterIngredientes = new AdapterIngredientes(ingredientes);
                recyclerviewIngrediente.setAdapter(adapterIngredientes);
            }
        });


        btnPaso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String key = dbProvider.tablaPlanAlimenticio().push().getKey();

                String nombre = Objects.requireNonNull(edtNombreComida.getText()).toString();
                String cantidad = Objects.requireNonNull(edtCantidad.getText()).toString();
                String calorias = Objects.requireNonNull(edtCalorias.getText()).toString();
                String tiempo = Objects.requireNonNull(edtTiempo.getText()).toString();

                keyPlan = key;
                if (!nombre.isEmpty()&&!cantidad.isEmpty()&&!calorias.isEmpty()&&!tiempo.isEmpty()&&imgUri!=null){
                    if (checkDesayuno.isChecked()) {
                        uploadImage(key, id, imgUri.toString(),
                                calorias + " Kcal", tiempo + " min", cantidad + " porciones",
                                nombre, "desayuno", "200", "$100");
                        dbProvider.actualizarPendiente(id_inscrito,false);
                        btnGuardar.setVisibility(View.GONE);
                        txtSiguiente.setVisibility(View.VISIBLE);


                    } else if (checkComida.isChecked()) {
                        uploadImage(key, id, imgUri.toString(),
                                calorias + " Kcal", tiempo + " min", cantidad + " porciones",
                                nombre, "almuerzo", "$200", "100");
                        dbProvider.actualizarPendiente(id_inscrito,false);
                        btnGuardar.setVisibility(View.GONE);
                        txtSiguiente.setVisibility(View.VISIBLE);


                    } else if (checkCena.isChecked()) {
                        uploadImage(key, id, imgUri.toString(),
                                calorias + " Kcal", tiempo + " min", cantidad + " porciones",
                                nombre, "cena", "200", "100");
                        dbProvider.actualizarPendiente(id_inscrito,false);
                        btnGuardar.setVisibility(View.GONE);
                        txtSiguiente.setVisibility(View.VISIBLE);
                        
                    }
                    else {
                        Toast.makeText(AgregarRecetas.this, "Selecciona un tipo de comida", Toast.LENGTH_SHORT).show();
                    }
                }

                else {
                    Toast.makeText(AgregarRecetas.this, "Revisa que todos los campos esten llenos.", Toast.LENGTH_SHORT).show();
                }



            }
        });

        txtSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AgregarRecetas.this, AgregarIngredientes.class);
                Bundle bundle = new Bundle();
                bundle.putString("key",keyPlan);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


    }



    private void uploadImage( final String key,final String id_usuario,final String imagen, final String kilocalorias, final String min_alimentos,
                               final String porciones,final String nombre_alimento,final String tipo_alimento,
                               final String precioAlto,final String precioBajo) {


        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Subiendo...");
        progressDialog.setProgress(0);
        progressDialog.show();
        progressDialog.setCancelable(false);

        final String fileName =System.currentTimeMillis()+"";
        final StorageReference storageReference1 =  mStorage.child(Contants.TABLA_PLAN_ALIMENTICIO).child(fileName);

        try {
            Bitmap bmp;
            bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 25, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTask2 = storageReference1.putBytes(data);

            uploadTask2.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            dbProvider.subirPlanAlimenticio(key, id_usuario, uri.toString(),
                                    kilocalorias , min_alimentos, porciones,
                                    nombre_alimento, tipo_alimento, precioAlto, precioBajo);
                            progressDialog.dismiss();
                            Toast.makeText(AgregarRecetas.this, "Se subio correctamente la información. ", Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AgregarRecetas.this, "Hubo un error al subir la información", Toast.LENGTH_SHORT).show();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                    int currentProgess = (int) (100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    progressDialog.setProgress(currentProgess);

                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }


    }



    public void bajarInscritos(){
        dbProvider = new DBProvider();

        dbProvider.tablaInscritos().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e(TAG, "Usuarios 4: ");
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        //Log.e(TAG,"Usuarios: "+ snapshot);
                        Inscritos inscritos = snapshot.getValue(Inscritos.class);

                        if (inscritos.getId_inscrito()!=null){

                            if (inscritos.getId_usuario().equals(id)){

                                id_inscrito=inscritos.getId_inscrito();

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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode==9 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
            selectImage();
        }
        else {
            Toast.makeText(AgregarRecetas.this, "Permite el permiso", Toast.LENGTH_SHORT).show();
        }

    }

    private void selectImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,86);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode==86&& resultCode ==RESULT_OK && data!= null){
            imgUri=data.getData();

            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imgUri);
                imgReceta.setVisibility(View.VISIBLE);
                imgReceta.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }

        }
        else {
            Toast.makeText(AgregarRecetas.this,"Selecciona un archivo", Toast.LENGTH_SHORT).show();
        }


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
