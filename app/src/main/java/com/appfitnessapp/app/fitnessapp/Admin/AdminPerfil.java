package com.appfitnessapp.app.fitnessapp.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.appfitnessapp.app.fitnessapp.R;
import com.appfitnessapp.app.fitnessapp.Usuario.EditarPerfil;
import com.appfitnessapp.app.fitnessapp.Usuario.UsuarioPerfil;
import com.mikhaellopez.circularimageview.CircularImageView;

public class AdminPerfil extends AppCompatActivity {

    TextView txtNombre,txtAsesorias,txtPendiente,txtCalificacion,txtCorreo,txtContrasena,txtGanancias;
    ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_12_perfil);

        Toolbar toolbarback=findViewById(R.id.toolbar);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        txtNombre=findViewById(R.id.txtNombreAdmin);
        txtAsesorias=findViewById(R.id.txtNumeroAsesorias);
        txtPendiente=findViewById(R.id.txtPendientes);
        txtCalificacion=findViewById(R.id.txtCalificacion);
        txtCorreo=findViewById(R.id.txtEmail);
        txtContrasena=findViewById(R.id.txtContrasena);
        txtGanancias=findViewById(R.id.txtGanancias);

        imagen=findViewById(R.id.imgAdmin);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.editar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.editar:
                Abrir();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void Abrir() {
        //Intent intent=new Intent(AdminPerfil.this,EditarPerfil.class);
        //startActivity(intent);

    }
}
