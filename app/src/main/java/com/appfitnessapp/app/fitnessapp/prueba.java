package com.appfitnessapp.app.fitnessapp;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.BaseMultiplePermissionsListener;

import java.util.List;

public class prueba extends AppCompatActivity {

    private static final int PDF_CODE = 1000 ;
    Button btnAssets,btntelefono,btnInternet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ejemplopdf);

        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new BaseMultiplePermissionsListener(){
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        super.onPermissionsChecked(report);
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        super.onPermissionRationaleShouldBeShown(permissions, token);
                    }
                }).check();

        btnAssets=findViewById(R.id.assets);
        btntelefono=findViewById(R.id.telefono);

        btnInternet=findViewById(R.id.internet);


        btnAssets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(prueba.this,viewPdf.class);
                intent.putExtra("ViewType","assets");
                startActivity(intent);

            }
        });

        btntelefono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent buscarPdf=new Intent(Intent.ACTION_GET_CONTENT);
                buscarPdf.setType("application/pdf");
                buscarPdf.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(Intent.createChooser(buscarPdf,"Selecciona PDF"),PDF_CODE);

            }
        });


        btnInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(prueba.this,viewPdf.class);
                intent.putExtra("ViewType","internet");
                startActivity(intent);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PDF_CODE && resultCode == RESULT_OK && data != null){

            Uri selecPdf=data.getData();
            Intent intent=new Intent(prueba.this,viewPdf.class);
            intent.putExtra("ViewType","storage");
            intent.putExtra("FileUri",selecPdf.toString());
            startActivity(intent);


        }

    }
}
