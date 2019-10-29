package com.appfitnessapp.app.fitnessapp.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appfitnessapp.app.fitnessapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class OlvidarContrasena extends AppCompatActivity {

    EditText edtCorreoIngresarU;

    LinearLayout linearRecuperar;

    private ProgressDialog progressDialog;

    private static final String TAG = "INGRESAR:";
    private static FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvidar_contrasena);

        mAuth = FirebaseAuth.getInstance();

        edtCorreoIngresarU=findViewById(R.id.edtCorreo);

        linearRecuperar=findViewById(R.id.linearRecuperar);


        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);


        linearRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo = edtCorreoIngresarU.getText().toString().trim();

                if (!correo.isEmpty()) {
                    progressDialog.setMessage("Enviando email...");
                    progressDialog.show();
                    progressDialog.setCancelable(false);
                    mAuth.sendPasswordResetEmail(correo)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "Email sent.");
                                        progressDialog.dismiss();
                                        Toast.makeText(OlvidarContrasena.this, "Se ha enviado un email para recuperar tu contraseña.", Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        Toast.makeText(OlvidarContrasena.this, "Escribe un correo válido.", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();

                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(OlvidarContrasena.this,"Escribe un correo válido.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    Toast.makeText(OlvidarContrasena.this, "Escribe un correo válido.", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}
