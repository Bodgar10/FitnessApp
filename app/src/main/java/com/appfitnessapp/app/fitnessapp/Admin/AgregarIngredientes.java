package com.appfitnessapp.app.fitnessapp.Admin;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.R;

public class AgregarIngredientes extends AppCompatActivity  {


    EditText edtNombre,edtCantidad;
    TextView btnGuardar;


    String key;
    private static final String TAG = "BAJARINFO:";
    static DBProvider dbProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_06_agregar_ingredientes);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Toolbar toolbarback=findViewById(R.id.toolbar);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("Plan Alimenticio");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        dbProvider = new DBProvider();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            key = extras.getString("key");
        }

        edtNombre=findViewById(R.id.edtNombreIngrediente);
        edtCantidad=findViewById(R.id.edtCantidad);

        btnGuardar=findViewById(R.id.txtGuardar);


        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre= edtNombre.getText().toString();
                String cantidad = edtCantidad.getText().toString();

                if (!nombre.isEmpty()&&!cantidad.isEmpty()){
                    dbProvider.subirIngredientes(key, nombre, cantidad);
                    Toast.makeText(AgregarIngredientes.this, "Se subieron los ingredientes.", Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(AgregarIngredientes.this, "Revisa que tengas todo completo.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
