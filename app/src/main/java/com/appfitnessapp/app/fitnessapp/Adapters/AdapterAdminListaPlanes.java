package com.appfitnessapp.app.fitnessapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appfitnessapp.app.fitnessapp.Arrays.Planes;
import com.appfitnessapp.app.fitnessapp.R;

import java.util.ArrayList;

public class AdapterAdminListaPlanes extends RecyclerView.Adapter<AdapterAdminListaPlanes.PlanesViewHolder>
        implements View.OnClickListener {

    AdapterAdminListaPlanes adapterPlanes;

    private View.OnClickListener listener;
    ArrayList<Planes> planes;


    public static class PlanesViewHolder extends RecyclerView.ViewHolder{


        TextView txtTipoPlan,txtPrecio;

        public PlanesViewHolder (View itemView) {
            super(itemView);



            txtTipoPlan=itemView.findViewById(R.id.txtNombrePlan);
            txtPrecio=itemView.findViewById(R.id.txtPrecio);



        }

    }

    public AdapterAdminListaPlanes(ArrayList<Planes>planes){

        this.planes=planes;
    }

    @Override
    public AdapterAdminListaPlanes.PlanesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_diseno_planlista,null,false);
        v.setOnClickListener(this);

        AdapterAdminListaPlanes.PlanesViewHolder holder=new AdapterAdminListaPlanes.PlanesViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAdminListaPlanes.PlanesViewHolder holder, int position) {
        Planes plan = planes.get(position);



        holder.txtTipoPlan.setText(plan.getNombre_plan());
        holder.txtPrecio.setText("$"+plan.getCosto_plan());


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
