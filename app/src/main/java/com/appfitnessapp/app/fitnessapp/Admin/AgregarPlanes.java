package com.appfitnessapp.app.fitnessapp.Admin;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class AgregarPlanes extends AppCompatActivity {


    EditText edtNombre, edtMeses,edtDescripcion,edtCosto;
    LinearLayout btnGuardar;

    private static final String TAG = "BAJARINFO:";
    static DBProvider dbProvider;

    String id,key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_planes);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Toolbar toolbarback = findViewById(R.id.toolbar);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("Plan Alimenticio");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        dbProvider = new DBProvider();

        id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        edtCosto=findViewById(R.id.edtCostoPlan);
        edtDescripcion=findViewById(R.id.edtDescripcionPlan);
        edtMeses=findViewById(R.id.edtMesesPlan);
        edtNombre=findViewById(R.id.edtNombrePlan);
        btnGuardar=findViewById(R.id.btnGuardarPlan);


        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String nombre=edtNombre.getText().toString().trim();
                String meses=edtMeses.getText().toString().trim();
                String costo=edtCosto.getText().toString().trim();
                String descripcion=edtDescripcion.getText().toString().trim();

                key = dbProvider.planes().push().getKey();



                if (!nombre.isEmpty() && !meses.isEmpty()&&!costo.isEmpty() && !descripcion.isEmpty()) {
                    dbProvider.subirPlanes(costo,descripcion,key,true,meses,nombre,id);
                    Toast.makeText(AgregarPlanes.this, "Se subio el plan correctamente.", Toast.LENGTH_SHORT).show();



                } else {
                    Toast.makeText(AgregarPlanes.this, "Revisa que tengas todo completo.", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}
