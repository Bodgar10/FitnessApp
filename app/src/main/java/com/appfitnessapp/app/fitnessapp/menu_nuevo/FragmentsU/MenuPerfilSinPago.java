package com.appfitnessapp.app.fitnessapp.menu_nuevo.FragmentsU;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.appfitnessapp.app.fitnessapp.Arrays.Usuarios;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.R;

import com.appfitnessapp.app.fitnessapp.Usuario.EditarPerfil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuPerfilSinPago extends Fragment {


    CircularImageView imgPersona;
    TextView txtNombre,txtPeso,txtAltura,txtObjetivo;

    private ProgressDialog progressDialog;
    private static final String TAG = "BAJARINFO:";
    static DBProvider dbProvider;



    private static FirebaseAuth mAuth;

    String id;


    public MenuPerfilSinPago() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.usuario_perfil_asesoria, container, false);


        setHasOptionsMenu(true);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);

        dbProvider = new DBProvider();
        id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mAuth = FirebaseAuth.getInstance();

        bajarUsuarios();


        imgPersona = view.findViewById(R.id.imgPersona);
        //linearCerrar = view.findViewById(R.id.linearCerrar);



        txtNombre = view.findViewById(R.id.txtNombreUsuario);
        txtPeso = view.findViewById(R.id.txtPesoActual);
        txtAltura = view.findViewById(R.id.txtEstatura);
        txtObjetivo = view.findViewById(R.id.txtObjetivo);



        return view;

    }

    public void bajarUsuarios(){
        dbProvider = new DBProvider();

        progressDialog.setMessage("Cargando Información...");
        progressDialog.show();
        dbProvider.usersRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e(TAG, "Usuarios 4: ");
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        //Log.e(TAG,"Usuarios: "+ snapshot);
                        Log.e(TAG, "Usuarios: " + snapshot);
                        Usuarios usuarios = snapshot.getValue(Usuarios.class);


                        if (usuarios.getId_usuario().equals(id)) {



                            if (usuarios.getEstatura().equals("nil")){
                                txtAltura.setText("");

                            }
                            else {
                                txtAltura.setText(usuarios.getEstatura());

                            }

                            if (usuarios.getObjetivo().equals("nil")){
                                txtObjetivo.setText("");

                            }
                            else {
                                txtObjetivo.setText(usuarios.getObjetivo());

                            }

                            if (usuarios.getPeso_actual().equals("nil")){
                                txtPeso.setText("");

                            }
                            else {
                                txtPeso.setText(usuarios.getPeso_actual());

                            }


                            txtNombre.setText(usuarios.getNombre_usuario());


                            if (usuarios.getFoto_usuario().equals("nil")) {
                                try {
                                    URL urlfeed = new URL(usuarios.getFoto_usuario());
                                    Picasso.get().load(String.valueOf(urlfeed))
                                            .error(R.mipmap.ic_launcher)
                                            .fit()
                                            .noFade()
                                            .into(imgPersona);
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                                progressDialog.dismiss();
                            }
                            else {
                                loadImageFromUrl(usuarios.getFoto_usuario());
                                progressDialog.dismiss();
                            }
                        }




                    }
                } else {
                    Log.e(TAG, "Usuarios 3: ");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "ERROR: ");
            }
        });
    }


    private void loadImageFromUrl(String url) {

        Picasso.get().load(url).fit().centerInside().noFade().into(imgPersona);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.editar_usuario, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.editar_U:
                Click();
                return true;
            default:
                return super.onOptionsItemSelected(item); }
    }

    private void Click(){
        Intent intent = new Intent(getActivity(), EditarPerfil.class);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.move, R.anim.move_leeft);

    }



}
