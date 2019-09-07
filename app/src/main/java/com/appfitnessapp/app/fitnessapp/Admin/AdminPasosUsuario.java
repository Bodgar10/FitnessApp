package com.appfitnessapp.app.fitnessapp.Admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.R;

public class AdminPasosUsuario extends AppCompatActivity {

    EditText edtPaso,edtDescripcion;

    TextView btnGuardar;

    LinearLayout btnSubirPaso;

    String idReceta,id_usuario;
    private static final String TAG = "BAJARINFO:";
    static DBProvider dbProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_06_agregarpasos);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Toolbar toolbarback=findViewById(R.id.toolbar);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("Agregar Pasos");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        dbProvider = new DBProvider();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idReceta = extras.getString("id_receta");
            id_usuario = extras.getString("idUsuario");
        }

        edtDescripcion=findViewById(R.id.edtDescripcionPaso);
        edtPaso=findViewById(R.id.edtPaso);

        btnGuardar=findViewById(R.id.txtGuardar);

        btnSubirPaso=findViewById(R.id.btnSubirPaso);

        btnSubirPaso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String descripcion= edtDescripcion.getText().toString();
                String paso = edtPaso.getText().toString();

                if (!descripcion.isEmpty()&&!paso.isEmpty()){
                    dbProvider.subirPreparacion(idReceta, "Paso "+paso, descripcion);
                    Toast.makeText(AdminPasosUsuario.this, "Se subio el paso: "+paso, Toast.LENGTH_SHORT).show();
                    edtDescripcion.getText().clear();
                    edtPaso.getText().clear();

                }
                else {
                    Toast.makeText(AdminPasosUsuario.this, "Revisa que tengas los campos rellenos.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(AdminPasosUsuario.this);
                dialogo1.setTitle("Pasos");
                dialogo1.setMessage("¿Se pusieron todos los pasos?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        aceptar();
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {

                        dialogo1.dismiss();

                    }
                });
                dialogo1.show();
            }
        });

    }



    private void aceptar() {
        Toast t=Toast.makeText(this,"Plan alimenticio completo.", Toast.LENGTH_SHORT);
        t.show();
        Intent intent = new Intent(AdminPasosUsuario.this, AdminPlanUsuario.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Bundle bundle = new Bundle();
        bundle.putString("id",id_usuario);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();


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