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

public class EditarPasos extends AppCompatActivity {

    EditText edtPaso,edtDescripcion;

    TextView btnActualizar;

    LinearLayout btnSubirPaso;
    TextView txtPaso;

    String id_receta,id_paso,nombre,descripcion,idUsuario;
    private static final String TAG = "BAJARINFO:";
    static DBProvider dbProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_06_agregarpasos);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Toolbar toolbarback=findViewById(R.id.toolbar);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("Editar Ingredientes");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        dbProvider = new DBProvider();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idUsuario = extras.getString("idUsuario");
            id_receta = extras.getString("id_receta");
            id_paso = extras.getString("id");
            nombre = extras.getString("nombre");
            descripcion = extras.getString("descripcion");

        }

        txtPaso=findViewById(R.id.txtPaso);

        edtDescripcion=findViewById(R.id.edtDescripcionPaso);
        edtPaso=findViewById(R.id.edtPaso);


        edtPaso.setVisibility(View.GONE);
        txtPaso.setVisibility(View.VISIBLE);
        txtPaso.setText(nombre);
        edtDescripcion.setText(descripcion);


        btnActualizar=findViewById(R.id.txtGuardar);

        btnSubirPaso=findViewById(R.id.btnSubirPaso);
        btnSubirPaso.setVisibility(View.GONE);


        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Sdescripcion= edtDescripcion.getText().toString();

                if (!Sdescripcion.equals(descripcion)&&!Sdescripcion.isEmpty()){
                    dbProvider.actualizarPreparacionDescripcion(id_receta, id_paso, Sdescripcion);
                    Toast.makeText(EditarPasos.this, "Se actualizó el procedimiento.", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(EditarPasos.this, AdminPlanUsuario.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Bundle bundle = new Bundle();
                    bundle.putString("id",idUsuario);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

                if (Sdescripcion.equals(descripcion)){
                    Intent intent=new Intent(EditarPasos.this, AdminPlanUsuario.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Bundle bundle = new Bundle();
                    bundle.putString("id",idUsuario);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

            }
        });

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
