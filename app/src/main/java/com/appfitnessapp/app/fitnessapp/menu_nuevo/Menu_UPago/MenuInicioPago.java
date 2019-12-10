package com.appfitnessapp.app.fitnessapp.menu_nuevo.Menu_UPago;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appfitnessapp.app.fitnessapp.R;
import com.appfitnessapp.app.fitnessapp.Usuario.UsuarioHome;
import com.appfitnessapp.app.fitnessapp.Usuario.UsuarioPlan;
import com.appfitnessapp.app.fitnessapp.menu_nuevo.FragmentsU.QuienAsesoria;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuInicioPago extends Fragment {

    LinearLayout btnAsesoria, btnMktPlace;



    public MenuInicioPago() {
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

                Intent intent = new Intent(getActivity(), UsuarioPlan.class);
                startActivity(intent);

            }
        });


        btnMktPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), UsuarioHome.class);
                startActivity(intent);

            }
        });




        return view;
    }
}
