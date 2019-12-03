package com.appfitnessapp.app.fitnessapp.menu_nuevo.FragmentsU;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appfitnessapp.app.fitnessapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuPerfilU extends Fragment {


    public MenuPerfilU() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_menu_perfil_u, container, false);


        return view;

    }

}
