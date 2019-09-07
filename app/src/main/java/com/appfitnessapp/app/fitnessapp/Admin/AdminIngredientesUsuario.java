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

public class AdminIngredientesUsuario extends AppCompatActivity {


    EditText edtNombre, edtCantidad;
    TextView btnGuardar;
    String idReceta,id_usuario;
    LinearLayout btnSubirIngredientes;

    private static final String TAG = "BAJARINFO:";
    static DBProvider dbProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_06_agregar_ingredientes);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Toolbar toolbarback = findViewById(R.id.toolbar);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("Plan Alimenticio");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        dbProvider = new DBProvider();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idReceta = extras.getString("id_receta");
            id_usuario = extras.getString("idUsuario");
        }

        edtNombre = findViewById(R.id.edtNombreIngrediente);
        edtCantidad = findViewById(R.id.edtCantidad);

        btnGuardar = findViewById(R.id.txtGuardar);

        btnSubirIngredientes = findViewById(R.id.btnSubirIngredientes);


        btnSubirIngredientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre = edtNombre.getText().toString();
                String cantidad = edtCantidad.getText().toString();

                if (!nombre.isEmpty() && !cantidad.isEmpty()) {
                    dbProvider.subirIngredientes(idReceta, nombre, cantidad);
                    Toast.makeText(AdminIngredientesUsuario.this, "Se subieron los ingredientes.", Toast.LENGTH_SHORT).show();
                    edtNombre.getText().clear();
                    edtCantidad.getText().clear();


                } else {
                    Toast.makeText(AdminIngredientesUsuario.this, "Revisa que tengas todo completo.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(AdminIngredientesUsuario.this);
                dialogo1.setTitle("Ingredientes");
                dialogo1.setMessage("¿Se pusieron todos los ingredientes?");
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
        Toast t=Toast.makeText(this,"Los ingredientes  se subieron.", Toast.LENGTH_SHORT);
        t.show();
        Intent intent = new Intent(AdminIngredientesUsuario.this, AdminPlanUsuario.class);
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
