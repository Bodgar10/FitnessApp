package com.appfitnessapp.app.fitnessapp.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appfitnessapp.app.fitnessapp.Arrays.Planes;
import com.appfitnessapp.app.fitnessapp.R;

import java.util.ArrayList;

public class AdapterPlanes extends RecyclerView.Adapter<AdapterPlanes.PlanesViewHolder>
        implements View.OnClickListener {

    AdapterPlanes adapterPlanes;

    private View.OnClickListener listener;
    ArrayList<Planes> planes;


    public static class PlanesViewHolder extends RecyclerView.ViewHolder{


        TextView txtTipoPlan,oferta,txtMeses,txtDescripcion;

        public PlanesViewHolder (View itemView) {
            super(itemView);



            txtTipoPlan=itemView.findViewById(R.id.txtNombrePlan);
            oferta=itemView.findViewById(R.id.txtOferta);
            txtMeses=itemView.findViewById(R.id.txtMeses);
            txtDescripcion=itemView.findViewById(R.id.txtDescripcionM);


        }

    }

    public AdapterPlanes(ArrayList<Planes>planes){

        this.planes=planes;
    }

    @Override
    public AdapterPlanes.PlanesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.usuario_06_planes_diseno,null,false);
        v.setOnClickListener(this);

        AdapterPlanes.PlanesViewHolder holder=new AdapterPlanes.PlanesViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPlanes.PlanesViewHolder holder, int position) {
        Planes plan = planes.get(position);



        holder.txtTipoPlan.setText(plan.getTipo_plan());
        holder.txtDescripcion.setText(plan.getDescripcion());
        holder.txtMeses.setText(plan.getMeses());
        holder.oferta.setText(plan.getOferta());




    }



    @Override
    public int getItemCount() {
        return planes.size();
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
