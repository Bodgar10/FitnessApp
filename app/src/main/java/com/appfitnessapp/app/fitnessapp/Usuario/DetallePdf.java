package com.appfitnessapp.app.fitnessapp.Usuario;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.appfitnessapp.app.fitnessapp.R;
import com.appfitnessapp.app.fitnessapp.Usuario.MenuRegistro.MetodoPagoAsesoria;
import com.appfitnessapp.app.fitnessapp.prueba;
import com.appfitnessapp.app.fitnessapp.viewPdf;
import com.google.firebase.auth.FirebaseAuth;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.BaseMultiplePermissionsListener;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class DetallePdf extends AppCompatActivity {

    LinearLayout btnComprar;
    TextView txtPrecio, txtTitulo, txtDescripcion;
    private static final int PDF_CODE = 1000;


    String descripcion, precio, url, id, inicioSesion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_14_feed_pdf_detalle);

        Toolbar toolbarback = findViewById(R.id.include);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("PDF");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        id = FirebaseAuth.getInstance().getCurrentUser().getUid();


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            descripcion = extras.getString("descripcion");
            precio = extras.getString("precio");
            url = extras.getString("url");
            inicioSesion = extras.getString("HomeInicio");

        }

        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new BaseMultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        super.onPermissionsChecked(report);
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        super.onPermissionRationaleShouldBeShown(permissions, token);
                    }
                }).check();


        btnComprar = findViewById(R.id.linearComprarPDF);

        txtPrecio = findViewById(R.id.txtPrecioPDF);
        txtDescripcion = findViewById(R.id.txtDescripcionPDF);


        txtDescripcion.setText(descripcion);
        txtPrecio.setText("$"+precio);


        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (inicioSesion.equals("Pagado")) {
                    Intent intent = new Intent(DetallePdf.this, MetodoPagoPdf.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("costo", precio);
                    bundle.putString("descripcion", descripcion);
                    bundle.putString("url", url);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else if (inicioSesion.equals("Asesoria")) {
                    Intent intent = new Intent(DetallePdf.this, MetodoPagoAsesoria.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("costo", precio);
                    bundle.putString("descripcion", descripcion);
                    bundle.putString("url", url);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }


            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PDF_CODE && resultCode == RESULT_OK && data != null) {

            Uri selecPdf = data.getData();
            Intent intent = new Intent(DetallePdf.this, PantallaPDF.class);
            intent.putExtra("ViewType", "storage");
            intent.putExtra("FileUri", selecPdf.toString());
            startActivity(intent);
            Toast.makeText(this, "Hola a todos", Toast.LENGTH_SHORT).show();


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
