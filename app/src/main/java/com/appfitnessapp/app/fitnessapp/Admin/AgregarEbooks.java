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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AgregarEbooks extends AppCompatActivity {

    LinearLayout btnImg,btnPdf;
    EditText edtDescripcion,edtPrecio;
    TextView btnSubir,txtPdf,txtSiguiente;
    ImageView imgPrincipal;
    RadioButton rdGratis,rdPago;

    static DBProvider dbProvider;
    StorageReference mStorage;
    Uri imgUri,pdfUri;
    ProgressDialog progressDialog;
    TextView txtSeleccionar,txtQueTipo;

    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_agregar_pdf);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Toolbar toolbarback = findViewById(R.id.toolbar);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("Agregar Ebook");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        id = FirebaseAuth.getInstance().getCurrentUser().getUid();


        mStorage= FirebaseStorage.getInstance().getReference();
        dbProvider = new DBProvider();

        btnImg=findViewById(R.id.btnImagenPrincipal);
        btnPdf=findViewById(R.id.btnAgregarPdf);

        edtDescripcion=findViewById(R.id.edtDescripcionPDF);
        edtPrecio=findViewById(R.id.edtDescripcionImagen);

        btnSubir=findViewById(R.id.txtSubir);
        txtPdf=findViewById(R.id.txtNombrePdf);

        imgPrincipal=findViewById(R.id.imgPdf);

        rdGratis=findViewById(R.id.checkGratis);
        rdPago=findViewById(R.id.checkPago);

        txtSiguiente=findViewById(R.id.txtSiguiente);

        txtSeleccionar=findViewById(R.id.txtSeleccionar);

        txtQueTipo=findViewById(R.id.txtQueTipo);


        txtSeleccionar.setText("Seleccionar E-book");
        txtQueTipo.setText("El E-book de que tipo sera:");



        btnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(AgregarEbooks.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                    selectImage();
                }
                else {
                    ActivityCompat.requestPermissions(AgregarEbooks.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},9);
                }

            }
        });


        btnPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(AgregarEbooks.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                    selectPDF();
                }
                else {
                    ActivityCompat.requestPermissions(AgregarEbooks.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},10);
                }
            }
        });



        rdGratis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (rdGratis.isChecked()){
                    rdPago.setChecked(false);
                    edtPrecio.setVisibility(View.GONE);

                }
            }
        });

        rdPago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (rdPago.isChecked()){
                    rdGratis.setChecked(false);
                    edtPrecio.setVisibility(View.VISIBLE);

                }
            }
        });


        btnSubir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String descripcion =edtDescripcion.getText().toString();
                String precio = edtPrecio.getText().toString();

                Long timeStamp = System.currentTimeMillis()/1000;
                String currentTimeStamp = timeStamp.toString();

                if (!descripcion.isEmpty()&&pdfUri!=null&&imgUri!=null){

                    if (rdGratis.isChecked()){
                        uploadPDF(descripcion,imgUri.toString(),currentTimeStamp,"nil",true);
                        txtSiguiente.setVisibility(View.VISIBLE);
                        btnSubir.setVisibility(View.GONE);
                    }

                    else if (rdPago.isChecked()&&!precio.isEmpty()){
                        uploadPDF(descripcion,imgUri.toString(),currentTimeStamp,precio,false);
                        txtSiguiente.setVisibility(View.VISIBLE);
                        btnSubir.setVisibility(View.GONE);

                    }
                    else {

                        Toast.makeText(AgregarEbooks.this, "Selecciona el tipo de pdf a subir.", Toast.LENGTH_SHORT).show();
                    }

                }

                else {

                    Toast.makeText(AgregarEbooks.this, "Revisa que tengas todos los campos completos.", Toast.LENGTH_SHORT).show();
                }


            }
        });

        txtSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i  = new Intent(AgregarEbooks.this,AdminAgregarFeed.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });
    }


    private void uploadPDF( final String descripcion, final String imgPrincipal, final String timestamp, final String costo,
                            final Boolean gratis) {

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Subiendo...");
        progressDialog.setProgress(0);
        progressDialog.show();
        progressDialog.setCancelable(false);

        final String fileName =System.currentTimeMillis()+"";
        final StorageReference storageReference1 =  mStorage.child(Contants.TABLA_EBOOK).child(fileName);

        storageReference1.putFile(pdfUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                uploadImagen(descripcion,imgPrincipal,timestamp,uri.toString(),costo,gratis);
                                progressDialog.dismiss();

                                Toast.makeText(AgregarEbooks.this, "Se subió correctamente la información", Toast.LENGTH_SHORT).show();
                            }
                        });


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(AgregarEbooks.this, "Hubo un error al subir el vídeo.", Toast.LENGTH_SHORT).show();

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
                              final String pdf,final String costo,final Boolean gratis) {



        /*
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Subiendo...");
        progressDialog.setProgress(0);
        progressDialog.show();
        */

        final String fileName =System.currentTimeMillis()+"";
        final StorageReference storageReference1 =  mStorage.child(Contants.TABLA_EBOOK).child(fileName);

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
                            dbProvider.subirEbook(Contants.EBOOKS,gratis,uri.toString(),costo,pdf,timestamp,
                                    descripcion,id);

                        }
                    });


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AgregarEbooks.this, "Hubo un error al subir la imágen.", Toast.LENGTH_SHORT).show();

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
            selectPDF();
        }

        else {
            Toast.makeText(AgregarEbooks.this, "Permite el acceso a la galeria.", Toast.LENGTH_SHORT).show();
        }

    }

    private void selectImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,86);
    }

    private void selectPDF() {

        Intent intent = new Intent();
        intent.setType("application/pdf");
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
            pdfUri=data.getData();
            txtPdf.setText("PDF: "+data.getData().getLastPathSegment());
        }

        else {
            Toast.makeText(AgregarEbooks.this,"Selecciona un archivo", Toast.LENGTH_SHORT).show();
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
