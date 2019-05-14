package com.appfitnessapp.app.fitnessapp.BaseDatos;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class DBProvider {

    private static final String TAG = "DBPROVIDER: ";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener listener;
    DatabaseReference reference;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    public DatabaseReference dbRef()
    {

        reference = database.getReference();
        return reference;
    }

    public DatabaseReference usersRef() {
        return dbRef().child(Contants.TABLA_USUARIOS);
    }



    //Creacion usuario
    public void createUser( String email, String id,
                           String name, String pass, String phone,
                           String photo, String token, String type) {
        Map<String, Object> updates = new HashMap<>();

        updates.put(Contants.ID_USUARIO, id);
        updates.put(Contants.TELEFONO_USUARIO, phone);
        updates.put(Contants.NOMBRE_USUARIO, name);
        updates.put(Contants.EMAIL_USUARIO, email);
        updates.put(Contants.TOKEN_USUARIO, token);
        updates.put(Contants.TIPO_USUARIO, type);
        updates.put(Contants.FOTO_USUARIO, photo);
        updates.put(Contants.CONTRASENA_USUARIO, pass);


        usersRef().child(id).updateChildren(updates);
    }

}
