package com.appfitnessapp.app.fitnessapp.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import com.appfitnessapp.app.fitnessapp.Arrays.Respuestas;
import com.appfitnessapp.app.fitnessapp.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterRespuestas  extends RecyclerView.Adapter<AdapterRespuestas.RespuestasViewHolder>
        implements View.OnClickListener {

    AdapterRespuestas adapterPreguntas;
    private View.OnClickListener listener;
    ArrayList<Respuestas> respuestas;



    public static class RespuestasViewHolder extends RecyclerView.ViewHolder{


        TextView txtPregunta;
        EditText edtRespuesta;

        public RespuestasViewHolder (View itemView) {
            super(itemView);
            txtPregunta=itemView.findViewById(R.id.txtPregunta);
            edtRespuesta=itemView.findViewById(R.id.edtPregunta);


        }

    }

    public AdapterRespuestas(ArrayList<Respuestas>respuestas){

        this.respuestas=respuestas;
    }

    @Override
    public AdapterRespuestas.RespuestasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.respuestas_diseno,null,false);
        v.setOnClickListener(this);

        AdapterRespuestas.RespuestasViewHolder holder=new AdapterRespuestas.RespuestasViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRespuestas.RespuestasViewHolder holder,  int position) {
        Respuestas respuesta = respuestas.get(position);


        holder.txtPregunta.setText(respuesta.getPregunta());
      //  holder.edtRespuesta.setText(respuesta.getRespuesta());



    }




    @Override
    public int getItemCount() {
        return respuestas.size();
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
