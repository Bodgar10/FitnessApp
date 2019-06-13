package com.appfitnessapp.app.fitnessapp.Usuario;

import android.graphics.Color;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.appfitnessapp.app.fitnessapp.BaseDatos.Contants;
import com.appfitnessapp.app.fitnessapp.R;
import com.mikhaellopez.circularimageview.CircularImageView;

public class EditarPerfil extends AppCompatActivity {

    CircularImageView imgPersona;
    TextView btnCambiarFoto,btnCambiarContra;
    TextInputEditText edtNombreUsuario,editContrasena,edtCorreo;

    Spinner spinnerPeso,spinnerEstatura,spinnerBuscando;
    LinearLayout btnAceptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_22_editar_perfil);

        Toolbar toolbarback=findViewById(R.id.include);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("Editar Perfil");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        imgPersona=findViewById(R.id.imgPersona);

        btnCambiarFoto=findViewById(R.id.txtCambiarFoto);
        btnCambiarContra=findViewById(R.id.txtCambiarContra);

        edtNombreUsuario=findViewById(R.id.edtNombreUsuario);
        editContrasena=findViewById(R.id.editContrasena);
        edtCorreo=findViewById(R.id.edtCorreo);


        spinnerPeso=findViewById(R.id.spinnerPeso);
        spinnerEstatura=findViewById(R.id.spinnerEstatura);
        spinnerBuscando=findViewById(R.id.spinnerBuscando);

        btnAceptar=findViewById(R.id.linearAceptar);


        ArrayAdapter<String> altura = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, Contants.altura);

        ArrayAdapter<String> peso = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, Contants.peso);

        ArrayAdapter<String> buscando = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, Contants.buscando);


        spinnerEstatura.setAdapter(altura);
        spinnerEstatura.setPrompt("¿Cual es tu altura?");
        spinnerPeso.setAdapter(peso);
        spinnerPeso.setPrompt("¿Cual es tu peso?");
        spinnerBuscando.setAdapter(buscando);
        spinnerBuscando.setPrompt("¿Que estas buscando?");



        spinnerEstatura.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ((TextView) spinnerEstatura.getSelectedView()).setTextColor(Color.GRAY);
            }
        });


        spinnerPeso.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ((TextView) spinnerPeso.getSelectedView()).setTextColor(Color.GRAY);
            }
        });

        spinnerBuscando.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ((TextView) spinnerBuscando.getSelectedView()).setTextColor(Color.GRAY);
            }
        });



        btnCambiarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnCambiarContra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
