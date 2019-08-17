package com.appfitnessapp.app.fitnessapp.Adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appfitnessapp.app.fitnessapp.Arrays.Ingredientes;
import com.appfitnessapp.app.fitnessapp.Arrays.Pasos;
import com.appfitnessapp.app.fitnessapp.R;

import java.util.ArrayList;

public class AdapterPasos  extends RecyclerView.Adapter<AdapterPasos.PasosViewHolder>
        implements View.OnClickListener {

        AdapterPasos adapterPlanes;
        private View.OnClickListener listener;
        ArrayList<Pasos> pasos;
        private static final String TAG = "BAJARINFO:";


public static class PasosViewHolder extends RecyclerView.ViewHolder{


    TextView txtPaso,txtDescripcion;

    public PasosViewHolder (View itemView) {
        super(itemView);



        txtPaso=itemView.findViewById(R.id.txtPasoD);
        txtDescripcion=itemView.findViewById(R.id.txtDescripcionPaso);


    }

}

    public AdapterPasos(ArrayList<Pasos> pasos){

        this.pasos=pasos;
    }

    @Override
    public AdapterPasos.PasosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_11_diseno_pasos,null,false);
        v.setOnClickListener(this);

        AdapterPasos.PasosViewHolder holder=new AdapterPasos.PasosViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPasos.PasosViewHolder holder, int position) {
        Pasos paso = pasos.get(position);



        holder.txtPaso.setText(paso.getNombre_paso());
        holder.txtDescripcion.setText(paso.getDescripcion_paso());



    }



    @Override
    public int getItemCount() {
        return pasos.size();
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