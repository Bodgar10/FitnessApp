package com.appfitnessapp.app.fitnessapp.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.appfitnessapp.app.fitnessapp.Admin.AsesoriasAdmin;
import com.appfitnessapp.app.fitnessapp.Arrays.Usuarios;
import com.appfitnessapp.app.fitnessapp.BaseDatos.Contants;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.R;
import com.appfitnessapp.app.fitnessapp.Usuario.UsuarioHome;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

public class IniciarSesion extends AppCompatActivity {

    private static final String TAG = "Ingresar:";

    String mensaje;
    String id, email, name;
    Boolean yaCreado;

    private ProgressDialog progressDialog;
    private static FirebaseAuth mAuth;
    static DBProvider dbProvider;

    String refreshedToken = FirebaseInstanceId.getInstance().getToken();

    TextInputEditText edtCorreo,edtContrasena;
    LinearLayout btnIniciarSesion,btnRegistrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_05_inicio_sesion);

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        yaCreado = false;


        edtCorreo=findViewById(R.id.edtCorreo);
        edtContrasena=findViewById(R.id.editContrasena);

        btnIniciarSesion=findViewById(R.id.linearIniciarSesion);
        btnRegistrar=findViewById(R.id.linearRegistrar);


        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);


        yaCreado = false;


        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String correo = edtCorreo.getText().toString();
                String pass = edtContrasena.getText().toString();

                if (!correo.isEmpty() && !pass.isEmpty()){
                    progressDialog.setMessage("Ingresando...");
                    progressDialog.show();
                    login(correo,pass);
                }else{
                    Toast.makeText(IniciarSesion.this, "Falta información.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IniciarSesion.this, Registro.class);
                startActivity(intent);

            }
        });


    }

    public void login(String email, String pass) {

        //Log.e(TAG,"LOGIN: "+email);
        //mensaje = "////////////////////////////////////////prueba/////////////////////////////////////////////";
        mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.e(TAG,"LOGIN 2: ");
                if (task.isSuccessful()){
                    bajarUsuarios();
                    Log.e(TAG,"LOGIN 40: ");
                }else{
                    Log.e(TAG,"LOGIN 50: ");
                    progressDialog.dismiss();
                    Toast.makeText(IniciarSesion.this, "Hubo un error, revisa que el correo o contraseña sean correctas.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

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
                        Log.e(TAG,"Usuarios: "+ snapshot);
                        Usuarios usuario = snapshot.getValue(Usuarios.class);

                        if (usuario.getId_usuario() != null) {
                            if (usuario.getId_usuario().equals(user.getUid())) {
                                yaCreado = true;
                                progressDialog.dismiss();
                                if (usuario.getTipo_usuario().equals(Contants.USUARIO)) {
                                    progressDialog.dismiss();
                                    Intent intent = new Intent(IniciarSesion.this, UsuarioHome.class);
                                    startActivity(intent);
                                    IniciarSesion.this.finish();
                                }
                                else if (usuario.getTipo_usuario().equals(Contants.ADMIN)) {
                                    progressDialog.dismiss();
                                    Intent intent = new Intent(IniciarSesion.this, AsesoriasAdmin.class);
                                    startActivity(intent);
                                    IniciarSesion.this.finish();
                                }else{
                                        Toast.makeText(IniciarSesion.this, "Aún tu cuenta no está activa, te contactaremos cuando esté lista.", Toast.LENGTH_SHORT).show();
                                    }
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

        /*
        if (!yaCreado){
            dbProvider.createUser(user.getEmail(),user.getUid(),name,"nil","nil","nil",refreshedToken,Contants.USUARIO,"nil",
                    "nil","nil");
        }

        */
    }

}
