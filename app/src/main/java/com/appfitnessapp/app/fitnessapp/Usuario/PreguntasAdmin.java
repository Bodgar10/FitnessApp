package com.appfitnessapp.app.fitnessapp.Usuario;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.appfitnessapp.app.fitnessapp.Adapters.AdapterPreguntas;
import com.appfitnessapp.app.fitnessapp.Arrays.Preguntas;
import com.appfitnessapp.app.fitnessapp.R;
import java.util.ArrayList;

public class PreguntasAdmin extends AppCompatActivity {

    AdapterPreguntas adapter;
    ArrayList<Preguntas> preguntas;
    RecyclerView recyclerView;

    LinearLayout btnAgregar;
    EditText edtPregunta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preguntas);


        recyclerView=findViewById(R.id.recyclerview);
        btnAgregar=findViewById(R.id.btnAgregar);
        edtPregunta=findViewById(R.id.edtPregunta);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        preguntas=new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        adapter = new AdapterPreguntas(preguntas);
        recyclerView.setAdapter(adapter);



      btnAgregar.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              String pregunta=edtPregunta.getText().toString();

              if (!pregunta.isEmpty()){
                  RecyclerView.LayoutManager lmmanager = new LinearLayoutManager(getApplicationContext()); recyclerView.setLayoutManager(lmmanager);
                //  preguntas.add(new Preguntas("", pregunta,""));
                  adapter = new AdapterPreguntas(preguntas);
                  recyclerView.setAdapter(adapter);
                  edtPregunta.getText().clear();
              }
              else {
                  Toast.makeText(PreguntasAdmin.this, "Agrega una pregunta", Toast.LENGTH_SHORT).show();
              }



          }
      });

    }
}
