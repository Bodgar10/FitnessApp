package com.appfitnessapp.app.fitnessapp.BaseDatos;

import android.content.Context;

import com.appfitnessapp.app.fitnessapp.Arrays.Chats;
import com.appfitnessapp.app.fitnessapp.BaseDatos.Utils.SharedPrefUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChatInteractorUsuario implements ChatContractUsuario.Interactor {
    private static final String TAG = "ChatInteractor";

    private ChatContractUsuario.OnSendMessageListener mOnSendMessageListener;
    private ChatContractUsuario.OnGetMessagesListener mOnGetMessagesListener;

    public ChatInteractorUsuario(ChatContractUsuario.OnSendMessageListener onSendMessageListener) {

    }

    public ChatInteractorUsuario(ChatContractUsuario.OnGetMessagesListener onGetMessagesListener) {
        this.mOnGetMessagesListener = onGetMessagesListener;
    }

    public ChatInteractorUsuario(ChatContractUsuario.OnSendMessageListener onSendMessageListener,
                                 ChatContractUsuario.OnGetMessagesListener onGetMessagesListener) {
        this.mOnSendMessageListener = onSendMessageListener;
        this.mOnGetMessagesListener = onGetMessagesListener;
    }

    @Override
    public void sendMessageToFirebaseUser(Context context, Chats chat, String receiverFirebaseToken, String id_usuario, String id_admin) {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        DBProvider dbProvider =  new DBProvider();

        databaseReference.child(Contants.CHAT).getRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    //Toast.makeText(context, "KEY: "+databaseReference.push().getKey(), Toast.LENGTH_SHORT).show()
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mOnSendMessageListener.onSendMessageFailure("Unable to send message: " + databaseError.getMessage());
            }
        });

        dbProvider.subirChats(id_admin,id_usuario,chat.getSenderid(),chat.getText());

        // send push notification to the receiver
        sendPushNotificationToReceiver("Nuevo mensaje",
                chat.getText(),
                chat.getSenderid(),
                new SharedPrefUtil(context).getString(receiverFirebaseToken),
                receiverFirebaseToken);
        mOnSendMessageListener.onSendMessageSuccess();
    }

    private void sendPushNotificationToReceiver(String username,
                                                String message,
                                                String uid,
                                                String firebaseToken,
                                                String receiverFirebaseToken) {
        FcmNotificationBuilder.initialize()
                .title(username)
                .message(message)
                .username(username)
                .uid(uid)
                .firebaseToken(firebaseToken)
                .receiverFirebaseToken(receiverFirebaseToken)
                .send();
    }

    @Override
    public void getMessageFromFirebaseUser(final String uid_vendedor, final String uid_usuario) {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child(Contants.CHAT).getRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mOnGetMessagesListener.onGetMessagesFailure("Unable to get message: " + databaseError.getMessage());
            }
        });
    }
}

