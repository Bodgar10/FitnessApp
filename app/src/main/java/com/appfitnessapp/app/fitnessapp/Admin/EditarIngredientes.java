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

public class EditarIngredientes extends AppCompatActivity {

    EditText edtNombre, edtCantidad;
    TextView btnGuardar;
    String id_receta,id_ingrediente,nombre,cantidad,idUsuario;
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
        getSupportActionBar().setTitle("Editar Ingredientes");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        dbProvider = new DBProvider();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idUsuario = extras.getString("idUsuario");
            id_ingrediente = extras.getString("id");
            id_receta = extras.getString("id_receta");
            nombre = extras.getString("nombre");
            cantidad = extras.getString("cantidad");

        }

        edtNombre = findViewById(R.id.edtNombreIngrediente);
        edtCantidad = findViewById(R.id.edtCantidad);

        edtNombre.setText(nombre);
        edtCantidad.setText(cantidad);

        btnGuardar = findViewById(R.id.txtGuardar);

        btnSubirIngredientes = findViewById(R.id.btnSubirIngredientes);
        btnSubirIngredientes.setVisibility(View.GONE);

        btnGuardar.setText("Actualizar");
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Snombre = edtNombre.getText().toString();
                String Scantidad = edtCantidad.getText().toString();

                if (!Snombre.equals(nombre)&&!Snombre.isEmpty()&&!Scantidad.isEmpty()){
                    dbProvider.actualizarIngredientesNombre(id_receta,id_ingrediente, Snombre);
                    Toast.makeText(EditarIngredientes.this, "Se actualizo el ingrediente: ", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(EditarIngredientes.this, AdminPlanUsuario.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Bundle bundle = new Bundle();
                    bundle.putString("id",idUsuario);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

                if (!Scantidad.equals(cantidad)&&!Scantidad.isEmpty()&&!Snombre.isEmpty()) {
                    dbProvider.actualizarIngredientesCantidad(id_receta,id_ingrediente, Scantidad);
                    Toast.makeText(EditarIngredientes.this, "Se actualizo la cantidad", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(EditarIngredientes.this, AdminPlanUsuario.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Bundle bundle = new Bundle();
                    bundle.putString("id",idUsuario);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

                if (Scantidad.equals(cantidad)&&Snombre.equals(nombre)){
                    Intent intent=new Intent(EditarIngredientes.this, AdminPlanUsuario.class);
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
