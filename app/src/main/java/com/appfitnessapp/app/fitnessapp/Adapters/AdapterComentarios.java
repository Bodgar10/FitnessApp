package com.appfitnessapp.app.fitnessapp.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appfitnessapp.app.fitnessapp.Arrays.Planes;
import com.appfitnessapp.app.fitnessapp.Arrays.Valoraciones;
import com.appfitnessapp.app.fitnessapp.R;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

public class AdapterComentarios extends RecyclerView.Adapter<AdapterComentarios.ComentariosViewHolder>
        implements View.OnClickListener {

    AdapterComentarios adapterPlanes;

    private View.OnClickListener listener;
    ArrayList<Valoraciones> planes;


    public static class ComentariosViewHolder extends RecyclerView.ViewHolder{


        TextView txtNombre,txtFecha,txtComentario;
        CircularImageView imgPersona;

        public ComentariosViewHolder (View itemView) {
            super(itemView);



            txtNombre=itemView.findViewById(R.id.txtNombreComen);
            txtFecha=itemView.findViewById(R.id.txtFechaComen);
            txtComentario=itemView.findViewById(R.id.txtComentario);

            imgPersona=itemView.findViewById(R.id.imgPersonaComen);




        }

    }

    public AdapterComentarios(ArrayList<Valoraciones>planes){

        this.planes=planes;
    }

    @Override
    public AdapterComentarios.ComentariosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.usuario_04_asesoria_comentarios,null,false);
        v.setOnClickListener(this);

        AdapterComentarios.ComentariosViewHolder holder=new AdapterComentarios.ComentariosViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterComentarios.ComentariosViewHolder holder, int position) {
        Valoraciones plan = planes.get(position);



        holder.txtNombre.setText(plan.getNombre_usuario());
        holder.txtFecha.setText(plan.getFecha_valoracion());
        holder.txtComentario.setText(plan.getDescripcion_valoracion());


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
