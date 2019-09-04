package com.appfitnessapp.app.fitnessapp.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
import android.widget.TextView;
import android.widget.Toast;

import com.appfitnessapp.app.fitnessapp.BaseDatos.Contants;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.R;
import com.appfitnessapp.app.fitnessapp.subirArchivos;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AgregarImagen extends AppCompatActivity {

    LinearLayout btnAgregarFoto;
    EditText edtDescripcion;
    ImageView imgFeed;

    static DBProvider dbProvider;
    StorageReference mStorage;
    Uri imgUri;
    ProgressDialog progressDialog;
    TextView txtSubir,txtSiguiente;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_agregar_imagen);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Toolbar toolbarback = findViewById(R.id.toolbar);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("Agregar Imagen");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        mStorage= FirebaseStorage.getInstance().getReference();
        dbProvider = new DBProvider();


        btnAgregarFoto=findViewById(R.id.btnAgregarFoto);
        edtDescripcion=findViewById(R.id.edtDescripcionImagen);
        imgFeed=findViewById(R.id.imgFeed);
        txtSubir=findViewById(R.id.txtSubir);
        txtSiguiente=findViewById(R.id.txtSiguiente);


        btnAgregarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(AgregarImagen.this, Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
                    selectImage();
                }
                else {
                    ActivityCompat.requestPermissions(AgregarImagen.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},9);
                }


            }
        });

        txtSubir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                String descripcion=edtDescripcion.getText().toString();

                Long timeStamp = System.currentTimeMillis()/1000;
                String currentTimeStamp = timeStamp.toString();
                
                
                if (!descripcion.isEmpty()&&imgUri!=null){
                    uploadImagenFeed(descripcion,imgUri.toString(),imgUri.toString(),currentTimeStamp);
                    txtSiguiente.setVisibility(View.VISIBLE);
                    txtSubir.setVisibility(View.GONE);
                }
                
                else{
                    Toast.makeText(AgregarImagen.this, "Revisa que tengas una descripción y la imagen.", Toast.LENGTH_SHORT).show();
                } 
            }
        });

        txtSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i  = new Intent(AgregarImagen.this,AdminAgregarFeed.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });


    }


    private void uploadImagenFeed(final String descripcion, final String imgPrincipal, String imgFeed, final String timestamp) {



        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Subiendo...");
        progressDialog.setProgress(0);
        progressDialog.show();
        progressDialog.setCancelable(false);

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
                            dbProvider.subirFeed(Contants.IMAGEN,false,uri.toString(),"0",uri.toString(),timestamp,
                                    descripcion);
                            progressDialog.dismiss();
                            Toast.makeText(AgregarImagen.this, "Se ha subido la imagen correctamente.", Toast.LENGTH_SHORT).show();


                        }
                    });


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AgregarImagen.this, "Hubo un error al subir la imágen.", Toast.LENGTH_SHORT).show();

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
            Toast.makeText(AgregarImagen.this, "Permite el acceso a la galeria.", Toast.LENGTH_SHORT).show();
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
                imgFeed.setVisibility(View.VISIBLE);
                imgFeed.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        else {
            Toast.makeText(AgregarImagen.this,"Selecciona un archivo", Toast.LENGTH_SHORT).show();
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
