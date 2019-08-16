package com.appfitnessapp.app.fitnessapp.Usuario;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.appfitnessapp.app.fitnessapp.Arrays.Usuarios;
import com.appfitnessapp.app.fitnessapp.BaseDatos.BajarInfo;
import com.appfitnessapp.app.fitnessapp.BaseDatos.Contants;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.Login.SplashPantalla;
import com.appfitnessapp.app.fitnessapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class EditarPerfil extends AppCompatActivity {

    CircularImageView imgPersona;
    TextView btnCambiarFoto;
    TextInputEditText edtNombreUsuario,editContrasena,edtCorreo,edtTelefono;

    Spinner spinnerPeso,spinnerEstatura,spinnerBuscando;
    LinearLayout btnAceptar;

    public static final int REQUEST_CODE = 1234;
    private Uri imgUri;

    private StorageReference mStorage;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener listener;

    private static final String TAG = "BAJARINFO:";
    static DBProvider dbProvider;
    BajarInfo bajarInfo;

    String id,email,password,name,telefono,txtObjetivo,txtPeso,txtEstatura;
    Boolean isFotoReady = false;
    String selectDay;


    ArrayAdapter<String> Saltura;
    ArrayAdapter<String> Speso;
    ArrayAdapter<String> Sbuscando;

    int selectionEstatura,selectionPeso,selectionObjetivo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_22_editar_perfil);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Toolbar toolbarback=findViewById(R.id.include);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("Editar Perfil");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);


        id = FirebaseAuth.getInstance().getCurrentUser().getUid();


        mStorage= FirebaseStorage.getInstance().getReference();
        dbProvider = new DBProvider();


        bajarUsuarios();

        imgPersona=findViewById(R.id.imgPersona);

        btnCambiarFoto=findViewById(R.id.txtCambiarFoto);


        edtNombreUsuario=findViewById(R.id.edtNombreUsuario);
        editContrasena=findViewById(R.id.edtContrasena);
        edtCorreo=findViewById(R.id.edtCorreo);
        edtTelefono=findViewById(R.id.edtTelefono);


        spinnerPeso=findViewById(R.id.spinnerPeso);
        spinnerEstatura=findViewById(R.id.spinnerEstatura);
        spinnerBuscando=findViewById(R.id.spinnerBuscando);

        btnAceptar=findViewById(R.id.linearAceptar);


        Saltura = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, Contants.estatura);
       Speso = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, Contants.peso);
       Sbuscando = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, Contants.objetivos);


        spinnerEstatura.setAdapter(Saltura);
        spinnerEstatura.setPrompt("¿Cual es tu estatura?");
        spinnerPeso.setAdapter(Speso);
        spinnerPeso.setPrompt("¿Cual es tu peso?");
        spinnerBuscando.setAdapter(Sbuscando);
        spinnerBuscando.setPrompt("¿Que estas objetivos?");

        spinnerEstatura.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ((TextView) spinnerEstatura.getSelectedView()).setTextColor(Color.GRAY);
            }
        });

        spinnerPeso.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ((TextView) spinnerPeso.getSelectedView()).setTextColor(Color.GRAY);
            }
        });
        spinnerBuscando.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ((TextView) spinnerBuscando.getSelectedView()).setTextColor(Color.GRAY);
            }
        });


        btnCambiarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select image"), REQUEST_CODE);

            }
        });


        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String edtNombre = Objects.requireNonNull(edtNombreUsuario.getText()).toString();
                String edtContra = Objects.requireNonNull(editContrasena.getText()).toString();
                String editCorreo = Objects.requireNonNull(edtCorreo.getText()).toString();
                String editTelefono = Objects.requireNonNull(edtTelefono.getText()).toString();


                //seleccionar el texto que se pone en el spinner
                String objetivo = spinnerBuscando.getSelectedItem().toString();
                String estatura = spinnerEstatura.getSelectedItem().toString();
                String peso = spinnerPeso.getSelectedItem().toString();


                if (!name.equals(edtNombre)){
                    dbProvider.updateName(edtNombre, id);
                }

                if (!email.equals(editCorreo)){
                    changeEmail(id, editCorreo);
                }
                if (!password.equals(edtContra)){
                    changePass(id, edtContra);
                }
                if (!telefono.equals(editTelefono)){
                    dbProvider.updatePhone(editTelefono, id);
                }
                if (!String.valueOf(selectionObjetivo).equals(objetivo)){
                    dbProvider.updateObjetivo(objetivo, id);
                }
                if (!String.valueOf(selectionEstatura).equals(estatura)){
                    dbProvider.updateEstatura(estatura, id);
                }
                if (!String.valueOf(selectionPeso).equals(peso)){
                    dbProvider.updatePeso(peso,id);
                }
                if (name.equals(edtNombre)&&email.equals(editCorreo)&&password.equals(edtContra)&&telefono.equals(editTelefono)){

                    Intent intent=new Intent(EditarPerfil.this, UsuarioPerfil.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }



            }
        });


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


                            name = usuarios.getNombre_usuario();
                            email = usuarios.getEmail_usuario();
                            password = usuarios.getContrasena_usuario();
                            telefono = usuarios.getTelefono_usuario();

                            edtNombreUsuario.setText(usuarios.getNombre_usuario());
                            editContrasena.setText(usuarios.getContrasena_usuario());
                            edtCorreo.setText(usuarios.getEmail_usuario());
                            edtTelefono.setText(usuarios.getTelefono_usuario());

                            //para bajar la info y ponerle en el spinner
                            selectionEstatura= Saltura.getPosition(usuarios.getEstatura());
                            spinnerEstatura.setSelection(selectionEstatura);

                            selectionPeso= Speso.getPosition(usuarios.getPeso_actual());
                            spinnerPeso.setSelection(selectionPeso);

                            selectionObjetivo= Sbuscando.getPosition(usuarios.getObjetivo());
                            spinnerBuscando.setSelection(selectionObjetivo);

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
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUri = data.getData();

            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(this.getApplicationContext().getContentResolver(), imgUri);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG,25,bytes);
                imgPersona.setImageBitmap(bm);

                final ProgressDialog dialog = new ProgressDialog(this);
                dialog.setTitle("Guardando Imagen");
                dialog.show();
                final StorageReference ref = mStorage.child(Contants.TABLA_USUARIOS).child(edtNombreUsuario.getText().toString());

                ref.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        //Dimiss dialog when success
                        //Display success toast msg
                        dialog.dismiss();
                        Toast.makeText(EditarPerfil.this,"Imagen guardada",Toast.LENGTH_SHORT).show();

                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                dbProvider.updatePhoto(uri.toString(), id);
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();


                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {

                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                        double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        dialog.setMessage("Uploaded " + (int) progress + "%");

                    }
                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void loadImageFromUrl(String url) {
        Picasso.get().load(url).into(imgPersona);
    }


   public void changeEmail(final String id, final String email) {
       mAuth = FirebaseAuth.getInstance();
       final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
       assert user != null;
        user.updateEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(EditarPerfil.this, "Se ha cambiado el correo correctamente.Inicia sesion de nuevo.", Toast.LENGTH_LONG).show();
                    dbProvider.updateEmail(id,email);
                    mAuth.signOut();
                    Intent intent=new Intent(EditarPerfil.this, SplashPantalla.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                } else {
                    Toast.makeText(EditarPerfil.this, "Lo siento, hubo un error al cambiar el correo", Toast.LENGTH_SHORT).show();
                }

               // progressDialog.dismiss();
            }
        });
    }
    public void changePass(final String id, final String pass) {
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        user.updatePassword(pass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(EditarPerfil.this, "Se ha cambiado la contraseña correctamente.Inicia sesión de nuevo.", Toast.LENGTH_LONG).show();
                    dbProvider.updatePass(id,pass);
                    mAuth.signOut();
                    Intent intent=new Intent(EditarPerfil.this, SplashPantalla.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Toast.makeText(EditarPerfil.this, "Lo siento, hubo un error al cambiar la contraseña", Toast.LENGTH_SHORT).show();
                }
              //  progressDialog.dismiss();
            }
        });
    }

}
