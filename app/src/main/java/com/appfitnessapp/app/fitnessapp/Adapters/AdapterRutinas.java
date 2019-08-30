package com.appfitnessapp.app.fitnessapp.Adapters;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class AdapterRutinas extends RecyclerView.Adapter<AdapterRutinas.RutinasViewHolder>
        implements View.OnClickListener {

    AdapterRutinas adapterPlanes;

    private View.OnClickListener listener;
    ArrayList<Ejercicios> ejercicios;
    private static final String TAG = "BAJARINFO:";


    public static class RutinasViewHolder extends RecyclerView.ViewHolder{


        TextView txtTipo,txtRepeticiones,txtRondas;

        public RutinasViewHolder (View itemView) {
            super(itemView);



            txtTipo=itemView.findViewById(R.id.txtNomRutina);
            txtRepeticiones=itemView.findViewById(R.id.txtRepeticiones);
            txtRondas=itemView.findViewById(R.id.txtRondas);




        }

    }

    public AdapterRutinas(ArrayList<Ejercicios>ejercicios){

        this.ejercicios=ejercicios;
    }

    @Override
    public AdapterRutinas.RutinasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.usuario_20_diseno,null,false);
        v.setOnClickListener(this);

        AdapterRutinas.RutinasViewHolder holder=new AdapterRutinas.RutinasViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRutinas.RutinasViewHolder holder, int position) {
        Ejercicios ejercicio = ejercicios.get(position);



        holder.txtTipo.setText(ejercicio.getNombre_ejercicio());
        holder.txtRepeticiones.setText(ejercicio.getRepeticiones()+" repeticiones.");
        holder.txtRondas.setText(ejercicio.getRondas()+" rondas de ");




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



