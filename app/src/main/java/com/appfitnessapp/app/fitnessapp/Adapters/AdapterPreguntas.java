package com.appfitnessapp.app.fitnessapp.Adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appfitnessapp.app.fitnessapp.Arrays.Preguntas;
import com.appfitnessapp.app.fitnessapp.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterPreguntas extends RecyclerView.Adapter<AdapterPreguntas.PreguntasViewHolder>
        implements View.OnClickListener {

    AdapterPreguntas adapterPreguntas;

    private View.OnClickListener listener;
    ArrayList<Preguntas> preguntas;


    public static class PreguntasViewHolder extends RecyclerView.ViewHolder{


        TextView txtPregunta;

        public PreguntasViewHolder (View itemView) {
            super(itemView);
            txtPregunta=itemView.findViewById(R.id.txtPregunta);

        }

    }

    public AdapterPreguntas(ArrayList<Preguntas>preguntas){

        this.preguntas=preguntas;
    }

    @Override
    public AdapterPreguntas.PreguntasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.pregunta_diseno,null,false);
        v.setOnClickListener(this);

        AdapterPreguntas.PreguntasViewHolder holder=new AdapterPreguntas.PreguntasViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPreguntas.PreguntasViewHolder holder, final int position) {
        Preguntas pregunta = preguntas.get(position);



        holder.txtPregunta.setText(pregunta.getPregunta());



    }




    @Override
    public int getItemCount() {
        return preguntas.size();
    }

    public void updateList(List<Preguntas> student)
    {
        student = student;
        notifyDataSetChanged();
    }
    public void addItem(int position, Preguntas stud)
    {
        preguntas.add(position, stud);
        notifyItemInserted(position);
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
