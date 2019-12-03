package com.appfitnessapp.app.fitnessapp.menu_nuevo.FragmentsU;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.appfitnessapp.app.fitnessapp.R;
import com.appfitnessapp.app.fitnessapp.Usuario.FeedSinRegistro.Asesoria;
import com.appfitnessapp.app.fitnessapp.Usuario.FeedSinRegistro.HomeSinRegistro;

/**
 * A simple {@link Fragment} subclass.
 */
public class Menu_Inicio_U extends Fragment {


    LinearLayout btnAsesoria, btnMktPlace;


    public Menu_Inicio_U() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu__inicio__u, container, false);


        btnAsesoria=view.findViewById(R.id.btnAsesoria);

        btnMktPlace=view.findViewById(R.id.btnMktPlace);




        btnAsesoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), QuienAsesoria.class);
                startActivity(intent);

            }
        });


        btnMktPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });




        return view;
    }

}
