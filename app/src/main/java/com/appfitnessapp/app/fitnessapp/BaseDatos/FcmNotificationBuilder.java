package com.appfitnessapp.app.fitnessapp.BaseDatos;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FcmNotificationBuilder {


    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String TAG = "FcmNotificationBuilder";
    private static final String SERVER_API_KEY = "AIzaSyB5-VHAmY9oe-dpO7Znksa1otuyKHbR1VA";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";
    private static final String AUTHORIZATION = "Authorization";
    private static final String AUTH_KEY = "key=AAAAlFNNowg:APA91bHzga0QIvAieLuh6eWSjOikOKjM43hiTg5ItdJu3mstmuZbFoRmnmewWylSg-IkqqZ7ChWlIeRsTs7vNURhOtel4tUNzTJLbMov-9njgYKFJKhCSBDibHB5z5SjPJtkB9HLx2qw";
    private static final String FCM_URL = "https://fcm.googleapis.com/fcm/send";
    // json related keys
    private static final String KEY_TO = "to";
    private static final String KEY_NOTIFICATION = "notification";
    private static final String KEY_TITLE = "title";
    private static final String KEY_TEXT = "text";
    private static final String KEY_DATA = "data";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_UID = "uid";
    private static final String KEY_FCM_TOKEN = "fcm_token";

    private String mTitle;
    private String mMessage;
    private String mUsername;
    private String mUid;
    private String mFirebaseToken;
    private String mReceiverFirebaseToken;

    private FcmNotificationBuilder() {

    }

    public static FcmNotificationBuilder initialize() {
        return new FcmNotificationBuilder();
    }

    public FcmNotificationBuilder title(String title) {
        mTitle = title;
        return this;
    }

    public FcmNotificationBuilder message(String message) {
        mMessage = message;
        return this;
    }

    public FcmNotificationBuilder username(String username) {
        mUsername = username;
        return this;
    }

    public FcmNotificationBuilder uid(String uid) {
        mUid = uid;
        return this;
    }

    public FcmNotificationBuilder firebaseToken(String firebaseToken) {
        mFirebaseToken = firebaseToken;
        return this;
    }

    public FcmNotificationBuilder receiverFirebaseToken(String receiverFirebaseToken) {
        mReceiverFirebaseToken = receiverFirebaseToken;
        return this;
    }

    public void send() {
        RequestBody requestBody;
        try {
            requestBody = RequestBody.create(MEDIA_TYPE_JSON, String.valueOf(getValidJsonBody()));

            Request request = new Request.Builder()
                    .addHeader("Authorization", "key=AAAAlFNNowg:APA91bHzga0QIvAieLuh6eWSjOikOKjM43hiTg5ItdJu3mstmuZbFoRmnmewWylSg-IkqqZ7ChWlIeRsTs7vNURhOtel4tUNzTJLbMov-9njgYKFJKhCSBDibHB5z5SjPJtkB9HLx2qw")
                    .addHeader("Content-Type", "application/json")
                    .url("https://fcm.googleapis.com/fcm/send")
                    .post(requestBody)
                    .build();
            System.out.println("SI SE PUDO ENVIAR LA NOTIFICACIÓN");

            //Connection conn =

            Call call = new OkHttpClient().newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e(TAG, "onGetAllUsersFailure: " + e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.e(TAG, "onResponse: " + response.body().string());
                }
            });
        } catch (JSONException e) {
            System.out.println("HUBO UN ERROR AL ENVIAR NOTIFICACIÓN");
            e.printStackTrace();
        }
    }

    private JSONObject getValidJsonBody() throws JSONException {

        System.out.println("PRUEBAAAAAAA");
        JSONObject jsonObjectBody = new JSONObject();
        jsonObjectBody.put(KEY_TO, mReceiverFirebaseToken);

        JSONObject jsonObjectData = new JSONObject();
        jsonObjectData.put(KEY_TITLE, "Nueva notificación.");
        jsonObjectData.put(KEY_TEXT, mMessage);
        System.out.println("MENSAJE: "+mMessage);
        //jsonObjectData.put(KEY_USERNAME, mUsername);
        jsonObjectData.put(KEY_UID, mUid);
        jsonObjectData.put(KEY_FCM_TOKEN, mFirebaseToken);
        jsonObjectBody.put(KEY_NOTIFICATION, jsonObjectData);

        return jsonObjectBody;
    }

}
