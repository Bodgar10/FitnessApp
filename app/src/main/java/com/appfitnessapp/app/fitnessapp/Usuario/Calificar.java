package com.appfitnessapp.app.fitnessapp.Usuario;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.appfitnessapp.app.fitnessapp.BaseDatos.Contants;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.R;
import com.appfitnessapp.app.fitnessapp.subirArchivos;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Calificar extends AppCompatActivity {

    RatingBar ratingValoracion;
    EditText edtExperiencia;
    LinearLayout btnCalificar;
    Spinner spinnerPeso;
    TextView txtRating,txtQuit,txtQuit2;

    ImageButton  btnFotoAntes,btnFotoDespues;
    ProgressDialog progressDialog;

    Uri pdfUri,pdfUri2;
    FirebaseStorage storage;
    FirebaseDatabase database;

    ImageView imDespues,imgAntes;



    private static final String TAG = "BAJARINFO:";
    static DBProvider dbProvider;

    String id;
     StorageReference mStorage;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    Date date = new Date();

    String fecha = dateFormat.format(date);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_24_calificar);

        Toolbar toolbarback=findViewById(R.id.include);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("Calificar asesoria");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        storage=FirebaseStorage.getInstance();
        database=FirebaseDatabase.getInstance();

        id = FirebaseAuth.getInstance().getCurrentUser().getUid();


        mStorage= FirebaseStorage.getInstance().getReference();
        dbProvider = new DBProvider();

        imDespues = findViewById(R.id.imDespues);
        imgAntes = findViewById(R.id.imgAntes);


        ratingValoracion=findViewById(R.id.Valoracion);

        edtExperiencia=findViewById(R.id.edtExperiencia);

        btnFotoAntes=findViewById(R.id.btnFotoAntes);
        btnFotoDespues=findViewById(R.id.btnFotoDespues);
        btnCalificar=findViewById(R.id.linearCalificar);

        spinnerPeso=findViewById(R.id.spinnerPeso);
        txtRating=findViewById(R.id.txtRating);
        txtQuit=findViewById(R.id.txtQuit);
        txtQuit2=findViewById(R.id.txtQuit2);



        ratingValoracion.setRating(0.0f);

        imDespues.setVisibility(View.GONE);
        imgAntes.setVisibility(View.GONE);


        ratingValoracion.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                txtRating.setText(""+rating);
            }
        });

        btnFotoAntes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(Calificar.this, Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
                    selectPDF2();
                }
                else {
                    ActivityCompat.requestPermissions(Calificar.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},9);
                }

            }
        });


        btnFotoDespues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (ContextCompat.checkSelfPermission(Calificar.this, Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
                    selectPDF();
                }
                else {
                    ActivityCompat.requestPermissions(Calificar.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},9);
                }

            }
        });

        btnCalificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String txtExperiencia = edtExperiencia.getText().toString();
                String valor = txtRating.getText().toString();

                String key = dbProvider.valoracionAsesoria().getKey();

                if (txtExperiencia.isEmpty()&&pdfUri==null&&pdfUri2==null){
                    Toast.makeText(Calificar.this, "Escribe tu experiencia y pon una calificación.", Toast.LENGTH_SHORT).show();
                }

                else if (pdfUri==null&&pdfUri2==null&&!txtExperiencia.isEmpty()){

                    Toast.makeText(Calificar.this, "Selecciona las imagenes", Toast.LENGTH_SHORT).show();

                }


                else{
                    dbProvider.subirValoraciones(txtExperiencia,fecha,"",key, "",
                            "",id,valor);
                    edtExperiencia.getText().clear();
                    uploadFile(pdfUri);
                    uploadFile2(pdfUri2);


                }
            }
        });




    }

    private void uploadFile(Uri pdfUri) {

        /*
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Subiendo...");
        progressDialog.setProgress(0);
        progressDialog.show();
*/
        final String fileName =System.currentTimeMillis()+id;
        final StorageReference storageReference1 =  mStorage.child(Contants.TABLA_VALORACIONES_ASESORIA).child(fileName);



        try {
            Bitmap bmp;
            bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), pdfUri);
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

                            dbProvider.updateImgAntes(uri.toString(), id);
                        }
                    });

                    /*
                    String url = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                    DatabaseReference reference=database.getReference();
                    reference.child(fileName).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){

                                Toast.makeText(Calificar.this, "Subio bien", Toast.LENGTH_SHORT).show();
                                // progressDialog.dismiss();

                            }
                            else {
                                Toast.makeText(Calificar.this, "No subio bien", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
*/
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Calificar.this, "No subio bien", Toast.LENGTH_SHORT).show();

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

    private void uploadFile2(Uri pdfUri2) {

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Subiendo...");
        progressDialog.setProgress(0);
        progressDialog.show();

        final String fileName =System.currentTimeMillis()+id;
       final StorageReference storageReference1 =  mStorage.child(Contants.TABLA_VALORACIONES_ASESORIA).child(fileName);


        try {
            Bitmap bmp;
            bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), pdfUri2);
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


                            dbProvider.updateImgDespues(uri.toString(), id);
                            progressDialog.dismiss();
                            Toast.makeText(Calificar.this, "Se subio bien todo ", Toast.LENGTH_SHORT).show();

                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Calificar.this, "No subio bien", Toast.LENGTH_SHORT).show();

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
            selectPDF();
        }

        else if (requestCode==10 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
            selectPDF2();


        }
        else {
            Toast.makeText(Calificar.this, "Permite el permiso", Toast.LENGTH_SHORT).show();
        }

    }

    private void selectPDF() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,86);
    }

    private void selectPDF2() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,87);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode==86&& resultCode ==RESULT_OK && data!= null){
            pdfUri=data.getData();

            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),pdfUri);
                imDespues.setVisibility(View.VISIBLE);
                imDespues.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }

            btnFotoDespues.setVisibility(View.GONE);
            txtQuit.setVisibility(View.GONE);

        }

       else if (requestCode==87&& resultCode ==RESULT_OK && data!= null){

            pdfUri2=data.getData();

            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),pdfUri2);
                imgAntes.setVisibility(View.VISIBLE);
                imgAntes.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }


            btnFotoAntes.setVisibility(View.GONE);
            txtQuit2.setVisibility(View.GONE);

        }

        else {
            btnFotoAntes.setVisibility(View.VISIBLE);
            txtQuit2.setVisibility(View.VISIBLE);
            btnFotoDespues.setVisibility(View.VISIBLE);
            txtQuit.setVisibility(View.VISIBLE);
            Toast.makeText(Calificar.this,"Selecciona archivo", Toast.LENGTH_SHORT).show();
        }


    }
}
