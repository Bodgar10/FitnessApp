package com.appfitnessapp.app.fitnessapp.Usuario;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appfitnessapp.app.fitnessapp.Adapters.AdapterIngredientes;
import com.appfitnessapp.app.fitnessapp.Adapters.AdapterPasos;
import com.appfitnessapp.app.fitnessapp.Arrays.Ingredientes;
import com.appfitnessapp.app.fitnessapp.Arrays.Preparacion;
import com.appfitnessapp.app.fitnessapp.BaseDatos.BajarInfo;
import com.appfitnessapp.app.fitnessapp.BaseDatos.Contants;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.Login.SplashPantalla;
import com.appfitnessapp.app.fitnessapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DetalleRecetas extends AppCompatActivity {

    ImageView imagen;
    TextView txtNombreReceta,txtTiempo,txtPorciones,txtCalorias,txtInversion,txtEditar;

    Switch switchComida;

    AdapterIngredientes adapterIngredientes;
    ArrayList<Ingredientes> ingredientes;

    AdapterPasos adapterPasos;
    ArrayList<Preparacion>pasos;

    RecyclerView recyclerIngredientes,recyclerPasos;

    String imagenComida,nombre,tipo,porciones,calorias,minutos,idReceta,id_usuario;

    static DBProvider dbProvider;
    BajarInfo bajarInfo;
    private static final String TAG = "BAJARINFO:";

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    Date date = new Date();

    String fecha = dateFormat.format(date);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_17_desayuno);


        dbProvider = new DBProvider();
        bajarInfo = new BajarInfo();


        Bundle extras = getIntent().getExtras();
        if (extras != null){
            idReceta = extras.getString("id");
            imagenComida = extras.getString("imagen");
            nombre = extras.getString("nombre");
            tipo = extras.getString("tipo");
            porciones = extras.getString("porciones");
            calorias = extras.getString("calorias");
            minutos = extras.getString("minutos");
            id_usuario=extras.getString("id_usuario");

            bajarIngredientes();
            bajarPasos();
        }

        Toolbar toolbarback=findViewById(R.id.toolbar);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle(tipo);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);



        imagen=findViewById(R.id.imgReceta);
        imagen.setScaleType(ImageView.ScaleType.FIT_XY);

        txtNombreReceta=findViewById(R.id.txtNombreReceta);
        txtTiempo=findViewById(R.id.txtTiempo);
        txtPorciones=findViewById(R.id.txtPorciones);
        txtCalorias=findViewById(R.id.txtCalorias);
        txtInversion=findViewById(R.id.txtInversion);

        switchComida=findViewById(R.id.switchComida);
        txtEditar=findViewById(R.id.txtEditar);

        txtEditar.setVisibility(View.GONE);
        switchComida.setVisibility(View.VISIBLE);


        //datos
        txtNombreReceta.setText(nombre);
        loadImageFromUrl(imagenComida);
        txtCalorias.setText(calorias);
        txtPorciones.setText(porciones);
        txtTiempo.setText(minutos);


        recyclerIngredientes=findViewById(R.id.recyclerIngrediente);
        recyclerPasos=findViewById(R.id.recyclerPreparacion);

        recyclerIngredientes.setNestedScrollingEnabled(false);
        recyclerPasos.setNestedScrollingEnabled(false);

        recyclerIngredientes.setLayoutManager(new LinearLayoutManager(this));
        recyclerPasos.setLayoutManager(new LinearLayoutManager(this));
        ingredientes=new ArrayList<>();
        pasos=new ArrayList<>();

        adapterIngredientes=new AdapterIngredientes(ingredientes);
        adapterPasos=new AdapterPasos(pasos);
        recyclerIngredientes.setAdapter(adapterIngredientes);
        recyclerPasos.setAdapter(adapterPasos);



        adapterIngredientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DetalleRecetas.this, InformacionCompra.class);
                Bundle bundle = new Bundle();
                bundle.putString("nombre",ingredientes.get(recyclerIngredientes.getChildAdapterPosition(v)).getNombre_ingrediente());
                bundle.putString("cantidad",ingredientes.get(recyclerIngredientes.getChildAdapterPosition(v)).getCantidad());
                intent.putExtras(bundle);
                intent.putExtra("anim id in", R.anim.down_in);
                intent.putExtra("anim id out", R.anim.down_out);
                startActivity(intent);
                overridePendingTransition(R.anim.up_in, R.anim.up_out);

            }
        });

        switchComida.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    AlertDialog.Builder dialogo1 = new AlertDialog.Builder(DetalleRecetas.this);
                    dialogo1.setTitle("");
                    dialogo1.setMessage("¿Quieres marcar esta comida como lista?");
                    dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id_) {
                            Toast.makeText(DetalleRecetas.this, "Tu "+tipo+" se registro.", Toast.LENGTH_SHORT).show();
                            String key = dbProvider.estadisticaAlimentos().push().getKey();
                            dbProvider.subirEstadisticaAlimentos(fecha,tipo,id_usuario,key);

                        }
                    });
                    dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {
                            switchComida.setChecked(false);
                            dialogo1.dismiss();
                        }
                    });
                    dialogo1.show();
                }
                
                else {

                    Toast.makeText(DetalleRecetas.this, "Mal", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    public void bajarIngredientes(){
        Log.e(TAG, "Receta: " + idReceta);
        dbProvider.tablaPlanAlimenticio().child(idReceta).child(Contants.INGREDIENTES).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e(TAG, "Feed: " +dataSnapshot );
                ingredientes.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Log.e(TAG, "Feed: " + dataSnapshot);
                        Ingredientes ingrediente = snapshot.getValue(Ingredientes.class);

                        if (ingrediente.getId_ingrediente()!=null) {

                            ingredientes.add(ingrediente);
                            adapterIngredientes.notifyDataSetChanged();

                        }
                    }
                }
                else {
                    Log.e(TAG, "Feed: ");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "ERROR: ");
            }
        });

    }



    public void bajarPasos(){

        dbProvider.tablaPlanAlimenticio().child(idReceta).child(Contants.PREPARACION).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pasos.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Log.e(TAG, "Feed: " + dataSnapshot);
                        Preparacion paso = snapshot.getValue(Preparacion.class);

                        if (paso.getId_preparacion()!=null) {
                            pasos.add(paso);
                            adapterPasos.notifyDataSetChanged();

                        }
                    }
                }
                else {
                    Log.e(TAG, ": ");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "ERROR: ");
            }
        });

    }



    private void loadImageFromUrl(String url) {

        Picasso.get().load(url).into(imagen);
    }
}
