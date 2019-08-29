package com.appfitnessapp.app.fitnessapp.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.appfitnessapp.app.fitnessapp.BaseDatos.Contants;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AgregarEjercicios extends AppCompatActivity {


    TextView txtPrueba,btnGuardar;
    ImageView btnAgregarEjercicio,img1,img2,img3;
    EditText edtNombreEjercicio,edtRepeticiones,edtRonda;

    LinearLayout btnAgregarFoto;
    ImageButton btnFoto1,btnFoto2,btnFoto3;

    ProgressDialog progressDialog;
    Uri videoUri,imagen1Uri,imagen2Uri,imagen3Uri;

    StorageReference mStorage;
    FirebaseStorage storage;
    FirebaseDatabase database;

    private static final String TAG = "BAJARINFO:";
    static DBProvider dbProvider;

    String key,idEjercicio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_07_agregar_ejercicios);


        Toolbar toolbarback=findViewById(R.id.toolbar);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("Plan de ejercicios");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            key = extras.getString("key");
        }

        dbProvider = new DBProvider();

        storage=FirebaseStorage.getInstance();
        database=FirebaseDatabase.getInstance();
        mStorage= FirebaseStorage.getInstance().getReference();

        btnFoto1=findViewById(R.id.btnFoto1);
        btnFoto2=findViewById(R.id.btnFoto2);
        btnFoto3=findViewById(R.id.btnFoto3);

        img1=findViewById(R.id.img1);
        img2=findViewById(R.id.img2);
        img3=findViewById(R.id.img3);


        btnAgregarFoto=findViewById(R.id.btnAgregarFoto);
        btnAgregarEjercicio=findViewById(R.id.btnAgregarEjercicio);


        edtNombreEjercicio=findViewById(R.id.edtNombreEjercicio);
        edtRepeticiones=findViewById(R.id.edtRepeticiones);
        edtRonda=findViewById(R.id.edtRondas);


        txtPrueba=findViewById(R.id.txtprueba);
        btnGuardar=findViewById(R.id.txtGuardar);


        btnAgregarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(AgregarEjercicios.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                    selectVideo();
                }
                else {
                    ActivityCompat.requestPermissions(AgregarEjercicios.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},9);
                } }
        });

        btnFoto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(AgregarEjercicios.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                    selectFoto1();
                }
                else {
                    ActivityCompat.requestPermissions(AgregarEjercicios.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},9);
                } }
        });

        btnFoto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(AgregarEjercicios.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                    selectFoto2();
                }
                else {
                    ActivityCompat.requestPermissions(AgregarEjercicios.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},9);
                } }
        });

        btnFoto3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(AgregarEjercicios.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                    selectFoto3(); }
                else {
                    ActivityCompat.requestPermissions(AgregarEjercicios.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},9); } }
        });



        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre = edtNombreEjercicio.getText().toString();
                String ronda = edtRonda.getText().toString();
                String repeticiones = edtRepeticiones.getText().toString();

                if (!nombre.isEmpty()&&!ronda.isEmpty()&&!repeticiones.isEmpty()){
                    uploadVideo(nombre,ronda,repeticiones,key);
                    Log.e(TAG, "Ejercicio2: " + idEjercicio);
                    Toast.makeText(AgregarEjercicios.this, "Se subio toda la información correctamente.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(AgregarEjercicios.this, "Revisa  que todos los campos esten llenos.", Toast.LENGTH_SHORT).show();

                }
            }
        });

        //ejercicios Solos
        /*
                String key = dbProvider.tablaEjercicios().push().getKey();
                uploadVideo(videoUri,nombre,ronda,repeticiones,key);
                uploadImage3(key,imagen1Uri.toString(),imagen2Uri.toString(),imagen3Uri.toString());
        */



    }



    private void uploadVideo( final String nombre_ejercicio, final String rondas, final String repeticiones, final String id_ejercicio) {

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Subiendo...");
        progressDialog.setProgress(0);
        progressDialog.show();

        final String fileName =System.currentTimeMillis()+"";
        final StorageReference storageReference1 =  mStorage.child(Contants.EJERCICIOS).child(fileName);

        storageReference1.putFile(videoUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                String key = dbProvider.tablaPlanEntrenamiento().child(id_ejercicio).child(Contants.EJERCICIOS).push().getKey();

                                idEjercicio = key;
                                dbProvider.subirEjerciciosPlan(nombre_ejercicio, rondas, repeticiones, uri.toString(), id_ejercicio,key);
                                uploadImage3(id_ejercicio,idEjercicio,imagen1Uri.toString(),imagen2Uri.toString(),imagen3Uri.toString());


                                //ejerciciosSolos
                                //dbProvider.subirEjercicios(nombre_ejercicio,rondas,repeticiones,uri.toString(),id_ejercicio);
                                progressDialog.dismiss();
                                Toast.makeText(AgregarEjercicios.this, "Se subio correctamente la informacion", Toast.LENGTH_SHORT).show();
                            }
                        });


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(AgregarEjercicios.this, "Hubo un error al subir el video.", Toast.LENGTH_SHORT).show();

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                int currentProgess = (int) (100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                progressDialog.setProgress(currentProgess);

            }
        });
    }

    private void uploadImage3(final String keyPlan,final String keyEjercicio, final String img1, final String img2, String img3) {

/*
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Subiendo...");
        progressDialog.setProgress(0);
        progressDialog.show();
*/
        final String fileName =System.currentTimeMillis()+"";
        final StorageReference storageReference1 =  mStorage.child(Contants.EJERCICIOS).child(fileName);

        try {
            Bitmap bmp;
            bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), imagen3Uri);
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

                            uploadImage2(keyPlan,keyEjercicio,img1,img2,uri.toString());
                           // progressDialog.dismiss();
                            Toast.makeText(AgregarEjercicios.this, "Se subieron las imagenes correctamente. ", Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AgregarEjercicios.this, "Hubo un error al subir las imagenes.", Toast.LENGTH_SHORT).show();

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


    private void uploadImage2( final String keyPlan,final String keyEjercicio,final String imagen1,final String imagen2, final String imagen3) {

        /*
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Subiendo...");
        progressDialog.setProgress(0);
        progressDialog.show();
*/
        final String fileName =System.currentTimeMillis()+"";
        final StorageReference storageReference1 =  mStorage.child(Contants.EJERCICIOS).child(fileName);

        try {
            Bitmap bmp;
            bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), imagen2Uri);
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

                            uploadImage1(keyPlan,keyEjercicio,imagen1,uri.toString(),imagen3);
                        }
                    });


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AgregarEjercicios.this, "No subio bien", Toast.LENGTH_SHORT).show();

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


    private void uploadImage1( final String keyPlan,final String keyEjercicio, String imagen1, final String imagen2, final String imagen3) {

        /*
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Subiendo...");
        progressDialog.setProgress(0);
        progressDialog.show();
*/
        final String fileName =System.currentTimeMillis()+"";
        final StorageReference storageReference1 =  mStorage.child(Contants.EJERCICIOS).child(fileName);

        try {
            Bitmap bmp;
            bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), imagen1Uri);
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

                            //dbProvider.subirImagenes(uri.toString(),key,imagen2,imagen3);
                            dbProvider.subirImagenesEjercicios(uri.toString(),imagen2,imagen3,keyPlan,keyEjercicio);

                        }
                    });


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AgregarEjercicios.this, "No subio bien", Toast.LENGTH_SHORT).show();

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

        if (requestCode==9 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            selectVideo();
        }

        else if (requestCode==10 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
            selectFoto1();


        }

        else if (requestCode==11 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
            selectFoto2();


        }

        else if (requestCode==12 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
            selectFoto3();


        }
        else {
            Toast.makeText(AgregarEjercicios.this, "Permite el acceso", Toast.LENGTH_SHORT).show();
        }

    }

    private void selectVideo() {

        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,86);
    }

    private void selectFoto1() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,87);
    }

    private void selectFoto2() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,88);
    }

    private void selectFoto3() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,89);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode==86&& resultCode ==RESULT_OK && data!= null){
            videoUri=data.getData();
            txtPrueba.setText("video"+data.getData().getLastPathSegment());
        }


        else if (requestCode==87&& resultCode ==RESULT_OK && data!= null){

            imagen1Uri=data.getData();

            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imagen1Uri);
                img1.setVisibility(View.VISIBLE);
                img1.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }

        }

        else if (requestCode==88&& resultCode ==RESULT_OK && data!= null){

            imagen2Uri=data.getData();

            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imagen2Uri);
                img2.setVisibility(View.VISIBLE);
                img2.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }

        }

        else if (requestCode==89&& resultCode ==RESULT_OK && data!= null){

            imagen3Uri=data.getData();

            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imagen3Uri);
                img3.setVisibility(View.VISIBLE);
                img3.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }

        }

        else {
            Toast.makeText(AgregarEjercicios.this,"Selecciona archivo", Toast.LENGTH_SHORT).show();
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
