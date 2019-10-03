package com.appfitnessapp.app.fitnessapp.Usuario;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.appfitnessapp.app.fitnessapp.Alarma.AlarmaCena;
import com.appfitnessapp.app.fitnessapp.Alarma.AlarmaComida;
import com.appfitnessapp.app.fitnessapp.Alarma.AlarmaDesayuno;
import com.appfitnessapp.app.fitnessapp.R;

public class ListaRecordatorio extends AppCompatActivity {

    LinearLayout btnComida,btnDesayuno,btnCena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_recordatorio);

        Toolbar toolbarback=findViewById(R.id.toolbar);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("Recordatorio");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        btnDesayuno=findViewById(R.id.btnDesayunoR);
        btnComida=findViewById(R.id.btnComidaR);
        btnCena=findViewById(R.id.btnCenaR);



        btnDesayuno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ListaRecordatorio.this, AlarmaDesayuno.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

        btnComida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListaRecordatorio.this, AlarmaComida.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

        btnCena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListaRecordatorio.this, AlarmaCena.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
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
        overridePendingTransition(
                getIntent().getIntExtra("anim id in", R.anim.move_in),
                getIntent().getIntExtra("anim id out", R.anim.move_leeft_in));

    }
}
