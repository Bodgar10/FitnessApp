package com.appfitnessapp.app.fitnessapp.Admin;

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
import com.google.firebase.auth.FirebaseAuth;

public class EditarPlanes extends AppCompatActivity {


    EditText edtNombre, edtMeses,edtDescripcion,edtCosto;
    LinearLayout btnGuardar;

    private static final String TAG = "BAJARINFO:";
    static DBProvider dbProvider;

    String id,nombre,costo,descripcion,meses;

    TextView txtButtonGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_planes);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Toolbar toolbarback = findViewById(R.id.toolbar);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("Planes");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        dbProvider = new DBProvider();

        id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            id = extras.getString("id");
            nombre = extras.getString("nombre");
            costo = extras.getString("costo");
            descripcion = extras.getString("descripcion");
            meses = extras.getString("meses");

        }


        edtCosto=findViewById(R.id.edtCostoPlan);
        edtDescripcion=findViewById(R.id.edtDescripcionPlan);
        edtMeses=findViewById(R.id.edtMesesPlan);
        edtNombre=findViewById(R.id.edtNombrePlan);
        btnGuardar=findViewById(R.id.btnGuardarPlan);


        edtCosto.setText(costo);
        edtDescripcion.setText(descripcion);
        edtMeses.setText(meses);
        edtNombre.setText(nombre);

        txtButtonGuardar=findViewById(R.id.txtButtonGuardar);


        txtButtonGuardar.setText("Actualizar plan");


        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String Snombre=edtNombre.getText().toString();
                String Scosto=edtCosto.getText().toString();
                String Sdescripcion=edtDescripcion.getText().toString();
                String Smeses=edtMeses.getText().toString();


                if (!Snombre.equals(nombre)){
                    dbProvider.updatePlanNombre(id,Snombre);
                    Toast.makeText(EditarPlanes.this, "Se actualizó el nombre.", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(EditarPlanes.this, AdminPlanes.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();

                }

                if (!Scosto.equals(costo)){
                    dbProvider.updatePlanCosto(id,Scosto);
                    Toast.makeText(EditarPlanes.this, "Se actualizó el costo.", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(EditarPlanes.this, AdminPlanes.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }

                if(!Sdescripcion.equals(descripcion)){
                    dbProvider.updatePlanDescripcion(id,Sdescripcion);
                    Toast.makeText(EditarPlanes.this, "Se actualizó la descripción.", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(EditarPlanes.this, AdminPlanes.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();

                }

                if (!Smeses.equals(meses)){
                    dbProvider.updatePlanMeses(id,Smeses);
                    Toast.makeText(EditarPlanes.this, "Se actualizó los meses.", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(EditarPlanes.this, AdminPlanes.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();

                }

                if (Snombre.isEmpty()&&Scosto.isEmpty()&&Sdescripcion.isEmpty()&&Smeses.isEmpty()){
                    Toast.makeText(EditarPlanes.this, "Necesitas rellenar los campos ", Toast.LENGTH_SHORT).show();
                }

                if (Snombre.equals(nombre)&&Scosto.equals(costo)&&Sdescripcion.equals(descripcion)&&Smeses.equals(meses)){
                    Intent intent=new Intent(EditarPlanes.this, AdminPlanes.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
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
