package com.appfitnessapp.app.fitnessapp.BaseDatos;

public class Contants {

    //Tablas
    final public static String TABLA_USUARIOS = "tabla_usuarios";
    final public static String TABLA_CHAT = "chats" ;// ---> YA



    //USERS
    final public static String NOMBRE_USUARIO = "nombre_usuario";
    final public static String TELEFONO_USUARIO = "telefono_usuario";
    final public static String EMAIL_USUARIO = "email_usuario";
    final public static String CONTRASENA_USUARIO = "contrasena_usuario";
    final public static String FOTO_USUARIO = "foto_usuario";
    final public static String ID_USUARIO = "id_usuario";
    final public static String TOKEN_USUARIO = "token_usuario";
    final public static String TIPO_USUARIO = "tipo_usuario";
    final public static  String PESO_ACTUAL = "peso_actual";
    final public static  String ESTATURA = "estatura";
    final public static String  OBJETIVO = "objetivo";


    //Tipo usuario
    final public static String ADMIN = "admin";
    final public static String USUARIO = "usuario";
    final public static String MESSAGE_OK = "ok";
    final public static String MESSAGE_FAIL = "fail";

    //Feed

    final public static String TABLA_FEED = "tabla_feed";
    final public static String TIPO_FEED = "tipo_feed";
    final public static String IS_GRATIS = "is_gratis";
    final public static String IMAGEN_FEED = "imagen_feed";
    final public static String COSTO_PDF = "costo_pdf";
    final public static String URL_TIPO = "url_tipo";
    final public static String TIMESTAMP = "timestamp";
    final public static String DESCRIPCION = "descripcion";

    final public static String VIDEO = "video";
    final public static String PDF = "pdf";
    final public static String IMAGEN = "imagen";


    //Asesoria
    final public static String TABLA_ASESORIA_INFO = "asesoria_info";
    final public static String ID_ASESORIA = "id_asesoria";
    final public static String IMAGEN_PORTADA = "imagen_portada";
    final public static String DESCRIPCION_ASESORIA = "descripcion_asesoria";
    final public static String COSTO_ASESORIA = "costo_asesoria";
    final public static String VIDEO_EXPLICATIVO = "video_explicativo";
    final public static String RUTINAS_IMAGEN = "rutinas_imagen";
    final public static String RUTINAS_DESCRIPCION = "rutinas_descripcion";
    final public static String ALIMENTOS_IMAGEN = "alimentos_imagen";
    final public static String ALIMENTOS_DESCRIPCION = "alimentos_descripcion";


    //Valoraciones
    final public static String TABLA_VALORACIONES_ASESORIA = "valoraciones_asesoria";
    final public static String ID_VALORACION = "id_valoracion";
    final public static String DESCRIPCION_VALORACION = "descripcion_valoracion";
    final public static String ID_USUARIO_VALORACION = "nombre_usuario_valoracion";
    final public static String FECHA_VALORACION = "fecha_valoracion";
    final public static String IMAGEN_ANTES = "imagen_antes";
    final public static String IMAGEN_DESPUES = "imagen_despues";
    final public static String VALORACION = "valoracion";


    //Planes
    final public static String TABLA_PLANES = "planes";
    final public static String ID_PLAN = "id_plan";
    final public static String MESES_PLAN = "meses_plan";
    final public static String DESCRIPCION_PLAN = "descripcion_plan";
    final public static String COSTO_PLAN = "costo_plan";
    final public static String NOMBRE_PLAN = "nombre_plan";
    final public static String IS_VENDIDA = "isVendida";


    //Formulario
    final public static String TABLA_FORMULARIO = "formulario";
    final public static String ID_PREGUNTA = "id_pregunta";
    final public static String PREGUNTA = "pregunta";

    //Respuestas
    final public static String TABLA_RESPUESTA = "respuestas";
    final public static String ID_RESPUESTA = "id_respuesta";
    final public static String RESPUESTA = "respuesta";

    //Forma pago
    final public static String PAYPAL = "paypal";
    final public static String TARJETA = "tarjeta";


    final public static String[] estatura = {"1.45 cm","1.46 cm","1.47 cm","1.48 cm","1.49 cm","1.50 cm","1.51 cm","1.52 cm","1.53 cm",
            "1.54 cm","1.55 cm","1.56 cm","1.57 cm","1.58 cm","1.59 cm","1.60 cm","1.61 cm","1.62 cm","1.63 cm","1.64 cm","1.65 cm",
            "1.66 cm","1.67 cm","1.68 cm","1.69 cm","1.70 cm","1.71 cm","1.72 cm","1.73 cm","1.74 cm","1.75 cm","1.76 cm","1.77 cm",
            "1.78 cm","1.79 cm","1.80 cm","1.81 cm","1.82 cm","1.83 cm","1.84 cm","1.85 cm","1.86 cm","1.87 cm","1.88 cm","1.89 cm",
            "1.90 cm","1.91 cm","1.92 cm","1.93 cm","1.94 cm","1.95 cm","1.96 cm","1.97 cm","1.98 cm","1.99 cm","2.00 cm"};


    final public static String[] peso = {"35 Kg","36 Kg","37 Kg","38 Kg","39 Kg","40 Kg","41 Kg","42 Kg","43 Kg","44 Kg","45 Kg","46 Kg",
            "47 Kg","48 Kg","49 Kg","50 Kg","51 Kg","52 Kg","53 Kg","54 Kg","55 Kg","56 Kg","57 Kg","58 Kg","59 Kg","60 Kg","61 Kg",
            "62 Kg","63 Kg","64 Kg","65 Kg","66 Kg","67 Kg","68 Kg","69 Kg","70 Kg","71 Kg","72 Kg","73 Kg","74 Kg","75 Kg","76 Kg",
            "77 Kg","78 Kg","79 Kg","80 Kg","81 Kg","82 Kg","83 Kg","84 Kg","85 Kg","86 Kg","87 Kg","88 Kg","89 Kg","90 Kg","91 Kg",
            "92 Kg","93 Kg","94 Kg","95 Kg","96 Kg","97 Kg","98 Kg","99 Kg","100 Kg","101 Kg","102 Kg","103 Kg","104 Kg","105 Kg",
            "106 Kg","107 Kg","108 Kg","109 Kg","110 Kg","111 Kg","112 Kg","113 Kg","114 Kg","115 Kg","116 Kg","117 Kg","118 Kg","119 Kg",
            "120 Kg","121 Kg","122 Kg","123 Kg","124 Kg","125 Kg","126 Kg","127 Kg","128 Kg","129 Kg","130 Kg","131 Kg","132 Kg","133 Kg",
            "134 Kg","135 Kg","136 Kg","137 Kg","138 Kg","139 Kg","140 Kg","141 Kg","142 Kg","143 Kg","144 Kg","145 Kg","146 Kg","147 Kg",
            "148 Kg","149 Kg","150 Kg"};


    final public static String[] objetivos = {"Bajar de peso", "Musculatura", "Abdomen plano", "Aumentar masa muscular"};

    //TABLA CHATS
    //--> ID_SERVICIO
    final public static String ID_SERVICIO = "id_servicio";
    final public static String ID_MENSAJE = "mensaje";
    final public static String SENDERID = "senderid";
    final public static String TEXT = "text";

    //TIPO_CONEXION
    final public static String CHATS = "chats";
    final public static String CHAT = "chat";

    public static final String ARG_USERS = "users";
    public static final String ARG_RECEIVER = "receiver";
    public static final String ARG_RECEIVER_UID = "receiver_uid";
    public static final String ARG_CHAT_ROOMS = "chat_rooms";
    public static final String ARG_FIREBASE_TOKEN = "firebaseToken";
    public static final String ARG_FRIENDS = "friends";
    public static final String ARG_UID = "uid";


}