package com.appfitnessapp.app.fitnessapp.BaseDatos;

import android.content.Context;

import com.appfitnessapp.app.fitnessapp.Arrays.Chat;
import com.appfitnessapp.app.fitnessapp.Arrays.Chats;

public interface ChatContractUsuario {
    interface View {
        void onSendMessageSuccess();

        void onSendMessageFailure(String message);

        void onGetMessagesSuccess(Chat chat);

        void onGetMessagesFailure(String message);
    }

    interface Presenter {
        void sendMessage(Context context, Chats chat, String receiverFirebaseToken, String id_usuario, String id_servicio);

        void getMessage(String senderUid, String receiverUid);
    }

    interface Interactor {

        void sendMessageToFirebaseUser(Context context, Chats chat, String receiverFirebaseToken, String id_usuario, String id_servicio);

        void getMessageFromFirebaseUser(String senderUid, String receiverUid);
    }

    interface OnSendMessageListener {
        void onSendMessageSuccess();

        void onSendMessageFailure(String message);
    }

    interface OnGetMessagesListener {
        void onGetMessagesSuccess(Chat chat);

        void onGetMessagesFailure(String message);

    }
}
