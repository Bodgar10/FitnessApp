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
import android.widget.EditText;
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

import com.appfitnessapp.app.fitnessapp.BaseDatos.Contants;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AgregarVideo extends AppCompatActivity {


    LinearLayout btnImg,btnPdf;
    EditText edtDescripcion;
    TextView btnSubir,txtPdf,txtSiguiente;
    ImageView imgPrincipal;


    static DBProvider dbProvider;
    StorageReference mStorage;
    Uri imgUri,videoUri;
    ProgressDialog progressDialog;
    TextView txtSubir;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_agregar_video);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Toolbar toolbarback = findViewById(R.id.toolbar);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("Agregar Video");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        mStorage= FirebaseStorage.getInstance().getReference();
        dbProvider = new DBProvider();

        btnImg=findViewById(R.id.btnImagenPrincipal);
        btnPdf=findViewById(R.id.btnAgregarPdf);

        edtDescripcion=findViewById(R.id.edtDescripcionPDF);

        btnSubir=findViewById(R.id.txtSubir);
        txtPdf=findViewById(R.id.txtNombrePdf);

        imgPrincipal=findViewById(R.id.imgPdf);

        txtSiguiente=findViewById(R.id.txtSiguiente);




        btnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(AgregarVideo.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                    selectImage();
                }
                else {
                    ActivityCompat.requestPermissions(AgregarVideo.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},9);
                }

            }
        });


        btnPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(AgregarVideo.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                    selectVideo();
                }
                else {
                    ActivityCompat.requestPermissions(AgregarVideo.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},10);
                }
            }
        });

        btnSubir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String descripcion =edtDescripcion.getText().toString();

                Long timeStamp = System.currentTimeMillis()/1000;
                String currentTimeStamp = timeStamp.toString();

                if (!descripcion.isEmpty()&&videoUri!=null&&imgUri!=null){
                  uploadVideo(descripcion,imgUri.toString(),currentTimeStamp,videoUri.toString());
                    txtSiguiente.setVisibility(View.VISIBLE);
                    btnSubir.setVisibility(View.GONE);
                }
                else {
                    Toast.makeText(AgregarVideo.this, "Revisa que tengas todos los campos completos.", Toast.LENGTH_SHORT).show();
                }


            }
        });

        txtSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i  = new Intent(AgregarVideo.this,AdminAgregarFeed.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });

    }


    private void uploadVideo( final String descripcion, final String imgPrincipal, final String timestamp,final String video) {

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Subiendo...");
        progressDialog.setProgress(0);
        progressDialog.show();
        progressDialog.setCancelable(false);

        final String fileName =System.currentTimeMillis()+"";
        final StorageReference storageReference1 =  mStorage.child(Contants.TABLA_FEED).child(fileName);

        storageReference1.putFile(videoUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                uploadImagen(descripcion,imgPrincipal,timestamp,uri.toString());
                                progressDialog.dismiss();

                                Toast.makeText(AgregarVideo.this, "Se subió correctamente la información", Toast.LENGTH_SHORT).show();
                            }
                        });


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(AgregarVideo.this, "Hubo un error al subir el vídeo.", Toast.LENGTH_SHORT).show();

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                int currentProgess = (int) (100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                progressDialog.setProgress(currentProgess);

            }
        });
    }



    private void uploadImagen(final String descripcion, final String imgPrincipal, final String timestamp,
                              final String video) {



        /*
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Subiendo...");
        progressDialog.setProgress(0);
        progressDialog.show();
        */

        final String fileName =System.currentTimeMillis()+"";
        final StorageReference storageReference1 =  mStorage.child(Contants.TABLA_FEED).child(fileName);

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
                            dbProvider.subirFeed(Contants.VIDEO,false,uri.toString(),"0",video,timestamp,
                                    descripcion);

                        }
                    });


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AgregarVideo.this, "Hubo un error al subir la imágen.", Toast.LENGTH_SHORT).show();

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

        else if (requestCode==10 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
            selectVideo();
        }

        else {
            Toast.makeText(AgregarVideo.this, "Permite el acceso a la galeria.", Toast.LENGTH_SHORT).show();
        }

    }

    private void selectImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,86);
    }

    private void selectVideo() {

        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,87);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode==86&& resultCode ==RESULT_OK && data!= null){

            imgUri=data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imgUri);
                imgPrincipal.setVisibility(View.VISIBLE);
                imgPrincipal.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        else if (requestCode==87&& resultCode ==RESULT_OK && data!= null){
            videoUri=data.getData();
            txtPdf.setText("VIDEO: "+data.getData().getLastPathSegment());
        }

        else {
            Toast.makeText(AgregarVideo.this,"Selecciona un archivo", Toast.LENGTH_SHORT).show();
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
