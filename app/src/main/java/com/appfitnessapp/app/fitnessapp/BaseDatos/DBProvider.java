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

    public DatabaseReference tablaFeed() {
        return dbRef().child(Contants.TABLA_FEED);
    }

    public DatabaseReference asesoriaInfo() {
        return dbRef().child(Contants.TABLA_ASESORIA_INFO);
    }

    public DatabaseReference formulario() {
        return dbRef().child(Contants.TABLA_FORMULARIO);
    }

    public DatabaseReference planes() {
        return dbRef().child(Contants.TABLA_PLANES);
    }

    public DatabaseReference respuestas() {
        return dbRef().child(Contants.TABLA_RESPUESTA);
    }

    public DatabaseReference valoracionAsesoria() {
        return dbRef().child(Contants.TABLA_VALORACIONES_ASESORIA);
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


    public void  subirFeed(String tipo_feed,Boolean is_gratis,String imagen_feed,String costo_pdf,String url_tipo,
                           String timestamp,String descripcion){
        Map<String, Object> updates = new HashMap<>();

        updates.put(Contants.TIPO_FEED, tipo_feed);
        updates.put(Contants.IS_GRATIS, is_gratis);
        updates.put(Contants.IMAGEN_FEED, imagen_feed);
        updates.put(Contants.COSTO_PDF, costo_pdf);
        updates.put(Contants.URL_TIPO, url_tipo);
        updates.put(Contants.TIMESTAMP, timestamp);
        updates.put(Contants.DESCRIPCION, descripcion);

        tablaFeed().child(tipo_feed).updateChildren(updates);



    }

    public void updatePhoto(String photo, String id){
        Map<String, Object> updates = new HashMap<>();

        updates.put(Contants.FOTO_USUARIO, photo);
        usersRef().child(id).updateChildren(updates);
    }

    public void updateName(String name, String id) {

        Map<String, Object> updates = new HashMap<>();

        updates.put(Contants.NOMBRE_USUARIO, name);
        usersRef().child(id).updateChildren(updates);
    }

    public void updateInfo(String name/*,String photo*/,String email,String password,String id) {

        Map<String, Object> updates = new HashMap<>();

        updates.put(Contants.NOMBRE_USUARIO, name);
     //   updates.put(Contants.FOTO_USUARIO, photo);
        updates.put(Contants.EMAIL_USUARIO, email);
        updates.put(Contants.CONTRASENA_USUARIO, password);
        usersRef().child(id).updateChildren(updates);
    }

    public void updateEmail(String email, String id) {
        Map<String, Object> updates = new HashMap<>();

        updates.put(Contants.EMAIL_USUARIO, email);
        usersRef().child(id).updateChildren(updates);
    }


    public void updatePass(String pass, String id) {
        Map<String, Object> updates = new HashMap<>();

        updates.put(Contants.CONTRASENA_USUARIO, pass);
        usersRef().child(id).updateChildren(updates);

    }



    //Asesoria
    public void subirAsesoria(String alimentos_descripcion,String alimentos_imagen, String costo_asesoria, String descripcion_asesoria,
                              String id_asesoria, String imagen_portada, String rutinas_descripcion, String rutinas_imagen,
                              String video_explicativo){

        Map<String, Object> updates = new HashMap<>();
        updates.put(Contants.ALIMENTOS_DESCRIPCION, alimentos_descripcion);
        updates.put(Contants.ALIMENTOS_IMAGEN, alimentos_imagen);
        updates.put(Contants.COSTO_ASESORIA, costo_asesoria);
        updates.put(Contants.DESCRIPCION_ASESORIA, descripcion_asesoria);
        updates.put(Contants.ID_ASESORIA, id_asesoria);
        updates.put(Contants.IMAGEN_PORTADA, imagen_portada);
        updates.put(Contants.RUTINAS_DESCRIPCION, rutinas_descripcion);
        updates.put(Contants.RUTINAS_IMAGEN, rutinas_imagen);
        updates.put(Contants.VIDEO_EXPLICATIVO, video_explicativo);

        asesoriaInfo().child(id_asesoria).updateChildren(updates);
    }



    //Formulario
    public void subirFormulario(String id_pregunta,String pregunta){
        Map<String, Object> updates = new HashMap<>();

        updates.put(Contants.ID_PREGUNTA , id_pregunta);
        updates.put(Contants.PREGUNTA , pregunta);

        formulario().child(id_pregunta).updateChildren(updates);

    }


    //Planes
    public void subirPlanes(String costo_plan,String descripcion_plan, String id_plan, Boolean isVendida, String meses_plan,
                            String nombre_plan){
        Map<String, Object> updates = new HashMap<>();

        updates.put(Contants.COSTO_PLAN , costo_plan);
        updates.put(Contants.DESCRIPCION_PLAN , descripcion_plan);
        updates.put(Contants.ID_PLAN , id_plan);
        updates.put(Contants.IS_VENDIDA , isVendida );
        updates.put(Contants.MESES_PLAN , meses_plan);
        updates.put(Contants.NOMBRE_PLAN , nombre_plan);

        planes().child(id_plan).updateChildren(updates);
    }



    //Respuestas
    public void subirRespuestas(String id_pregunta,String id_respuesta, String id_usuario, String respuesta){
        Map<String, Object> updates = new HashMap<>();

        updates.put(Contants.ID_PREGUNTA , id_pregunta);
        updates.put(Contants.ID_RESPUESTA , id_respuesta);
        updates.put(Contants.ID_USUARIO , id_usuario);
        updates.put(Contants.RESPUESTA , respuesta);

        respuestas().child(id_respuesta).updateChildren(updates);
    }

    //Valoraciones
    public void subirValoraciones(String descripcion_valoracion,String fecha_valoracion, String id_asesoria, String id_valoracion,
                                  String imagen_antes,String imagen_despues, String nombre_usuario_valoracion,String valoracion){
        Map<String, Object> updates = new HashMap<>();

        updates.put(Contants.DESCRIPCION_VALORACION , descripcion_valoracion);
        updates.put(Contants.FECHA_VALORACION , fecha_valoracion);
        updates.put(Contants.ID_ASESORIA , id_asesoria);
        updates.put(Contants.ID_VALORACION , id_valoracion);
        updates.put(Contants.IMAGEN_ANTES , imagen_antes);
        updates.put(Contants.IMAGEN_DESPUES , imagen_despues);
        updates.put(Contants.ID_USUARIO_VALORACION , nombre_usuario_valoracion);
        updates.put(Contants.VALORACION , valoracion);

        valoracionAsesoria().child(id_valoracion).updateChildren(updates);
        }




}
