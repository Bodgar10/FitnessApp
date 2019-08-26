package com.appfitnessapp.app.fitnessapp.Admin;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
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
import com.appfitnessapp.app.fitnessapp.Arrays.Ingredientes;
import com.appfitnessapp.app.fitnessapp.BaseDatos.Contants;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.R;
import com.appfitnessapp.app.fitnessapp.Usuario.Calificar;
import com.appfitnessapp.app.fitnessapp.subirArchivos;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class RecetasEditar extends AppCompatActivity {

    TextView btnWorkouts,btnGuardar;
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
                Intent intent = new Intent(RecetasEditar.this, EscogerPlan.class);
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
                if (ContextCompat.checkSelfPermission(RecetasEditar.this, Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
                    selectImage();
                }
                else {
                    ActivityCompat.requestPermissions(RecetasEditar.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},9);
                }
            }
        });

        btnIngrediente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RecyclerView.LayoutManager lmmanager = new LinearLayoutManager(getApplicationContext()); recyclerviewIngrediente.setLayoutManager(lmmanager);
                ingredientes.add(new Ingredientes("","",""));
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
                        dbProvider.subirPreparacion(key, "Paso 1", "Picar la verdura que ocuparas");



                        Intent intent = new Intent(RecetasEditar.this, AgregarIngredientes.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("key",keyPlan);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    } else if (checkComida.isChecked()) {
                        uploadImage(key, id, imgUri.toString(),
                                calorias + " Kcal", tiempo + " min", cantidad + " porciones",
                                nombre, "almuerzo", "$200", "100");
                        dbProvider.subirPreparacion(key, "Paso 1", "Picar la verdura que ocuparas");


                        Intent intent1 = new Intent(RecetasEditar.this, AgregarIngredientes.class);
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("key",keyPlan);
                        intent1.putExtras(bundle1);
                        startActivity(intent1);
                    } else if (checkCena.isChecked()) {
                        uploadImage(key, id, imgUri.toString(),
                                calorias + " Kcal", tiempo + " min", cantidad + " porciones",
                                nombre, "cena", "200", "100");
                        dbProvider.subirPreparacion(key, "Paso 1", "Picar la verdura que ocuparas");


                        Intent intent2 = new Intent(RecetasEditar.this, AgregarIngredientes.class);
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("key",keyPlan);
                        intent2.putExtras(bundle2);
                        startActivity(intent2);
                    }
                    else {
                        Toast.makeText(RecetasEditar.this, "Selecciona un tipo de comida", Toast.LENGTH_SHORT).show();
                    }
                }

                else {
                    Toast.makeText(RecetasEditar.this, "Revisa que todos los campos esten llenos.", Toast.LENGTH_SHORT).show();
                }



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
                            Toast.makeText(RecetasEditar.this, "Se subio bien todo ", Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RecetasEditar.this, "No subio bien", Toast.LENGTH_SHORT).show();

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


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode==9 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
            selectImage();
        }
        else {
            Toast.makeText(RecetasEditar.this, "Permite el permiso", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(RecetasEditar.this,"Selecciona archivo", Toast.LENGTH_SHORT).show();
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
