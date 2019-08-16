package com.appfitnessapp.app.fitnessapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class subirArchivos extends AppCompatActivity {


    TextView btnRecetas,btnGuardar,txtprueba;
    ImageView btnAgregarFoto,btnAgregarEjercicio;
    EditText edtDescripcion;


    ProgressDialog progressDialog;

    Uri pdfUri;
    FirebaseStorage storage;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_07_escogerplan);

        Toolbar toolbarback=findViewById(R.id.toolbar);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("Desayuno");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        storage=FirebaseStorage.getInstance();
        database=FirebaseDatabase.getInstance();


        btnRecetas=findViewById(R.id.btnRecetas);
        btnAgregarFoto=findViewById(R.id.btnAgregarFoto);
        btnAgregarEjercicio=findViewById(R.id.btnAgregarEjercicio);

        edtDescripcion=findViewById(R.id.edtDescripcion);

        btnGuardar=findViewById(R.id.txtGuardar);

        txtprueba=findViewById(R.id.txtprueba);


        btnRecetas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnAgregarEjercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        btnAgregarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(subirArchivos.this, Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
                    selectPDF();
                }
                else {
                    ActivityCompat.requestPermissions(subirArchivos.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},9);
                }
            }
        });




        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pdfUri!=null){
                    uploadFile(pdfUri);
                }
                
                else {
                    Toast.makeText(subirArchivos.this, "Selecciona", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void uploadFile(Uri pdfUri) {

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Subiendo...");
        progressDialog.setProgress(0);
        progressDialog.show();

        final String fileName =System.currentTimeMillis()+"";
        StorageReference storageReference =storage.getReference();

        storageReference.child("Uploads").child(fileName).putFile(pdfUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        String url = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                        DatabaseReference  reference=database.getReference();
                        reference.child(fileName).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()){

                                    Toast.makeText(subirArchivos.this, "Subio bien", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }

                                else {

                                    Toast.makeText(subirArchivos.this, "No subio bien", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(subirArchivos.this, "No subio bien", Toast.LENGTH_SHORT).show();

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                int currentProgess = (int) (100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                progressDialog.setProgress(currentProgess);

            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode==9 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            selectPDF();
        }
        else {
            Toast.makeText(subirArchivos.this, "Permite el permiso", Toast.LENGTH_SHORT).show();
        }

    }

    private void selectPDF() {

        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,86);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode==86&& resultCode ==RESULT_OK && data!= null){
            pdfUri=data.getData();
            txtprueba.setText("pdf"+data.getData().getLastPathSegment());
        }
        else {
            Toast.makeText(subirArchivos.this,"Selecciona archivo", Toast.LENGTH_SHORT).show();
        }


    }

}