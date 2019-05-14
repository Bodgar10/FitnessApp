package com.appfitnessapp.app.fitnessapp.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.appfitnessapp.app.fitnessapp.Arrays.Usuarios;
import com.appfitnessapp.app.fitnessapp.BaseDatos.Contants;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.R;
import com.appfitnessapp.app.fitnessapp.Usuario.MenuUsuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

public class Registro extends AppCompatActivity {

    private static final String TAG = "CREAR CUENTA:";

    private ProgressDialog progressDialog;
    private static FirebaseAuth mAuth;
    static DBProvider dbProvider;
    String mensaje;
    String id, email, name;
    Boolean yaCreado;

    String refreshedToken = FirebaseInstanceId.getInstance().getToken();

    EditText edtCorreo,edtContrasena,edtNombre,edtTelefono;
    LinearLayout btnIniciarSesion,btnRegistrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        yaCreado = false;


        edtCorreo=findViewById(R.id.edtCorreo);
        edtContrasena=findViewById(R.id.editContrasena);
        edtNombre=findViewById(R.id.edtNombreUsuario);
        edtTelefono=findViewById(R.id.edtTelefono);

        btnIniciarSesion=findViewById(R.id.linearIniciarSesion);
        btnRegistrar=findViewById(R.id.linearRegistrar);


        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String correo = edtCorreo.getText().toString();
                String contrasena = edtContrasena.getText().toString();
                String nombre = edtNombre.getText().toString();
                String telefono = edtTelefono.getText().toString();


                if (!correo.isEmpty() && !contrasena.isEmpty() && !nombre.isEmpty() && !telefono.isEmpty()) {
                    progressDialog.setMessage("Creando cuenta...");
                    progressDialog.show();
                    register(correo,"nil",nombre,contrasena,telefono,"nil",refreshedToken,Contants.USUARIO);
                }else{
                    Toast.makeText(Registro.this, "Verifica que tengas todo rellenado.", Toast.LENGTH_SHORT).show();
                }


            }
        });

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Registro.this, IniciarSesion.class);
                startActivity(intent);
            }
        });

    }

    public void register( final String email, final String id,
                          final String name,final String pass,  final String phone,
                          final String photo, final String token,final String type) {

        Log.e(TAG, "REGISTRO: " + email);
        mAuth = FirebaseAuth.getInstance();
        final String[] mensaje = new String[1];

        final String[] id_user = new String[1];
        dbProvider = new DBProvider();

        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener( this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(Registro.this, "Hubo un error, Revisa si el correo no esta ya existente o si tu ontraseña es mayor a 6 caracteres ", Toast.LENGTH_SHORT).show();
                }
                if (task.isSuccessful()) {
                    FirebaseUser user = task.getResult().getUser();
                    dbProvider.createUser(email,user.getUid(),name,pass,phone,photo,token,type);
                    Intent intent = new Intent(Registro.this, MenuUsuario.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }


    /*
    public void bajarUsuarios(){
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Log.e(TAG,"Usuarios 2: ");
        dbProvider = new DBProvider();

        dbProvider.usersRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e(TAG,"Usuarios 4: ");


                if (dataSnapshot.exists()){


                    for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                        Usuarios usuarios = snapshot.getValue(Usuarios.class);

                        //Log.e(TAG,"Usuarios: "+ snapshot);
                        Log.e(TAG,"Usuarios: "+ snapshot);
                        if (usuarios.getId_usuario().equals(user.getUid())){
                            yaCreado = true;
                            progressDialog.dismiss();

                            if (usuarios.getTipo_usuario().equals(Contants.USUARIO)){
                                progressDialog.dismiss();
                                Intent intent = new Intent(Registro.this, MenuUsuario.class);
                                startActivity(intent);
                                Registro.this.finish();
                            }

                        }

                    }
                }else{
                    Log.e(TAG,"Usuarios 3: ");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG,"ERROR: ");
            }
        });

        if (!yaCreado){
            dbProvider.createUser(email,user.getUid(),name,"nil","","",refreshedToken,Contants.USUARIO);
        }
    }


*/
}
