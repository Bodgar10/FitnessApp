package com.appfitnessapp.app.fitnessapp.Adapters;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appfitnessapp.app.fitnessapp.Arrays.Asesorias;
import com.appfitnessapp.app.fitnessapp.Arrays.Ejercicios;
import com.appfitnessapp.app.fitnessapp.R;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class AdapterEjercicios extends RecyclerView.Adapter<AdapterEjercicios.EjericiosViewHolder>
        implements View.OnClickListener {

    AdapterEjercicios adapterPlanes;

    private View.OnClickListener listener;
    ArrayList<Ejercicios> ejercicios;
    private static final String TAG = "BAJARINFO:";


    public static class EjericiosViewHolder extends RecyclerView.ViewHolder{



        EditText txtTipoEjercicio,txtRepeticiones,txtRondas;

        public EjericiosViewHolder (View itemView) {
            super(itemView);



            txtTipoEjercicio=itemView.findViewById(R.id.edtTipoEjercicio);
            txtRepeticiones=itemView.findViewById(R.id.edtRepeticiones);
            txtRondas=itemView.findViewById(R.id.edtRondas);




        }

    }

    public AdapterEjercicios(ArrayList<Ejercicios>ejercicios){

        this.ejercicios=ejercicios;
    }

    @Override
    public AdapterEjercicios.EjericiosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_07_diseno_ejercicio,null,false);
        v.setOnClickListener(this);

        AdapterEjercicios.EjericiosViewHolder holder=new AdapterEjercicios.EjericiosViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterEjercicios.EjericiosViewHolder holder, int position) {
        Ejercicios ejercicio = ejercicios.get(position);



        holder.txtTipoEjercicio.setText(ejercicio.getNombre_ejercicio());
        holder.txtRepeticiones.setText(ejercicio.getRepeticiones());
        holder.txtRondas.setText(ejercicio.getRondas());

    }



    @Override
    public int getItemCount() {
        return ejercicios.size();
    }


    public void setOnClickListener(View.OnClickListener listener) {



        this.listener = listener;
    }


    @Override
    public void onClick(View v) {
        if (listener!=null){
            listener.onClick(v);
        }
    }


}



