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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appfitnessapp.app.fitnessapp.Arrays.AsesoriasInfo;
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
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class EditarAsesoria extends AppCompatActivity {

    EditText edtDescripcionAsesoria,edtDescripcionAlimentos,edtDescripcionRutina,edtCosto;
    ImageView imgAsesoria,imgRutina,imgAlimentos,imgVideo;

    LinearLayout btnGuardarAsesoria;


    private ProgressDialog progressDialog;
    private static final String TAG = "BAJARINFO:";
    static DBProvider dbProvider;

    String videoUrl,costoE,alimentosE,rutinaE,asesoriaE,idAsesoria;


    StorageReference mStorage;
    Uri videoUri;
    TextView txtVideo,txtVideoExplicativo;



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


        mStorage= FirebaseStorage.getInstance().getReference();
        dbProvider = new DBProvider();

        bajarAsesoria();

        btnGuardarAsesoria=findViewById(R.id.btnGuardarAsesoria);

        txtVideo=findViewById(R.id.txtVideo);
        txtVideoExplicativo=findViewById(R.id.txtVideoExplicativo);


        edtDescripcionAsesoria=findViewById(R.id.edtDescripcion);
        edtDescripcionAlimentos=findViewById(R.id.edtAlimentos);
        edtDescripcionRutina=findViewById(R.id.edtRutina);
        edtCosto=findViewById(R.id.edtCosto);


        imgAsesoria=findViewById(R.id.imgAsesoria);
        imgAlimentos=findViewById(R.id.imgAlimentos);
        imgRutina=findViewById(R.id.imgRutina);

        imgVideo=findViewById(R.id.imgVideo);



        imgVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(EditarAsesoria.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                    selectVideo();
                }
                else {
                    ActivityCompat.requestPermissions(EditarAsesoria.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},10);
                }
            }
        });


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

                if (!videoUrl.equals(txtVideo.getText().toString())&&videoUri!=null){
                    uploadVideo();


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

                if (costo.equals(costoE)&&rutina.equals(rutinaE)&&alimentos.equals(alimentosE)&&asesoria.equals(asesoriaE)&&
                        videoUri==null){
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
                            txtVideo.setText(asesorias.getVideo_explicativo());
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

    private void uploadVideo() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Subiendo...");
        progressDialog.setProgress(0);
        progressDialog.show();
        progressDialog.setCancelable(false);

        final String fileName =System.currentTimeMillis()+"";
        final StorageReference storageReference1 =  mStorage.child(Contants.TABLA_ASESORIA_INFO).child(fileName);

        storageReference1.putFile(videoUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                dbProvider.updateVideoAsesoria(idAsesoria,uri.toString());
                                progressDialog.dismiss();

                                Toast.makeText(EditarAsesoria.this, "Se subió correctamente la información", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(EditarAsesoria.this, AdminAgregarFeed.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }
                        });


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(EditarAsesoria.this, "Hubo un error al subir el vídeo.", Toast.LENGTH_SHORT).show();

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

         if (requestCode==10 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
            selectVideo();
        }

        else {
            Toast.makeText(EditarAsesoria.this, "Permite el acceso a la galeria.", Toast.LENGTH_SHORT).show();
        }

    }



    private void selectVideo() {

        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,87);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {



         if (requestCode==87&& resultCode ==RESULT_OK && data!= null){
            videoUri=data.getData();
             txtVideoExplicativo.setText("Tu video esta listo para subirse.");
             txtVideo.setText("VIDEO: "+data.getData().getLastPathSegment());
        }

        else {
            Toast.makeText(EditarAsesoria.this,"Selecciona un archivo", Toast.LENGTH_SHORT).show();
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
