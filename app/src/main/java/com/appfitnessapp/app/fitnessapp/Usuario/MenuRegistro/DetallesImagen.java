package com.appfitnessapp.app.fitnessapp.Usuario.MenuRegistro;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.appfitnessapp.app.fitnessapp.R;

public class DetallesImagen extends AppCompatActivity {


    String nombre;

    ImageView imgInstructor;

    TextView txtNombre,txtDescripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_imagen);

        Toolbar toolbarback=findViewById(R.id.toolbar);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            nombre  =extras.getString("Nombre");
        }

        imgInstructor=findViewById(R.id.imgAsesor);

        txtNombre=findViewById(R.id.txtNombreIns);
        txtDescripcion=findViewById(R.id.txtDescripcionAsesor);


        if (nombre.equals("Andie")){

            txtNombre.setText(R.string.andie_illanes);
            txtDescripcion.setText(R.string.andie);
            imgInstructor.setImageResource(R.drawable.img1);

        }

        else if (nombre.equals("Federico")){

            imgInstructor.setImageResource(R.drawable.img2);
            txtNombre.setText(R.string.federico_boyselle);
            txtDescripcion.setText(R.string.federico);

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
        overridePendingTransition(
                getIntent().getIntExtra("anim id in", R.anim.move_in),
                getIntent().getIntExtra("anim id out", R.anim.move_leeft_in));

    }
}
