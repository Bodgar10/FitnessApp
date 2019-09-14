package com.appfitnessapp.app.fitnessapp.Admin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appfitnessapp.app.fitnessapp.Adapters.ChatRecyclerAdapterUsuario;
import com.appfitnessapp.app.fitnessapp.Arrays.Chat;
import com.appfitnessapp.app.fitnessapp.Arrays.Chats;
import com.appfitnessapp.app.fitnessapp.Arrays.PushNotificationEvent;
import com.appfitnessapp.app.fitnessapp.Arrays.Usuarios;
import com.appfitnessapp.app.fitnessapp.BaseDatos.ChatContractUsuario;
import com.appfitnessapp.app.fitnessapp.BaseDatos.ChatPresenterUsuario;
import com.appfitnessapp.app.fitnessapp.BaseDatos.Contants;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

public class ChatFragmentAdmin extends AppCompatActivity implements ChatContractUsuario.View, TextView.OnEditorActionListener, ChatContractUsuario.Interactor {



    private static final String TAG = "INGRESAR:";

    private RecyclerView mRecyclerViewChat;
    private EditText mETxtMessage;

    private ProgressDialog mProgressDialog;

    private ChatRecyclerAdapterUsuario mChatRecyclerAdapter;

    private ChatPresenterUsuario mChatPresenter;

    ArrayList<Usuarios> usuarios;
    ArrayList<Chats> chats;
    static DBProvider dbProvider;

    private String id_relper, id_usuario, id_servicio ;
    private FirebaseAuth mAuth;

    String name, uid, id_admin,  token, id_servicio1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_chat);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            name = extras.getString("name");
            uid = extras.getString("uid");
            id_admin = extras.getString("id_admin");
            token = extras.getString("token");
            id_servicio1 = extras.getString("id_servicio");
        }


        mRecyclerViewChat = findViewById(R.id.recycler_view_chat);
        mRecyclerViewChat.setLayoutManager(new LinearLayoutManager(this));
        mETxtMessage =  findViewById(R.id.edit_text_message);

        init();

        mAuth = FirebaseAuth.getInstance();
        chats = new ArrayList<>();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        id_relper = Contants.ARG_FIREBASE_TOKEN;
        id_servicio = Contants.ID_SERVICIO;
        id_usuario = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mChatRecyclerAdapter = new ChatRecyclerAdapterUsuario(chats);
        mRecyclerViewChat.setAdapter(mChatRecyclerAdapter);

        usuarios = new ArrayList<>();


        database.getReference().child(Contants.CHATS)
                .child(id_usuario)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
                        if (dataSnapshot.exists()){
                            Chats chat = dataSnapshot.getValue(Chats.class);
                            if (chat.getText() != null) {

                                System.out.println("SE ENCONTRARON MENSAJES DEL USUARIO.");
                                chats.add(chat);
                                mChatRecyclerAdapter.notifyDataSetChanged();
                                mRecyclerViewChat.smoothScrollToPosition(mChatRecyclerAdapter.getItemCount() - 1);
                            }
                        }
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



    }


    private void init() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Cargando...");
        mProgressDialog.setMessage("Por favor, espere");
        mProgressDialog.setIndeterminate(true);

        mETxtMessage.setOnEditorActionListener(this);

        mChatPresenter = new ChatPresenterUsuario(this);
        mChatPresenter.getMessage(id_relper, Contants.ARG_RECEIVER_UID);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEND) {
            sendMessage();
            return true;
        }
        return false;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);

    }

    private void sendMessage() {
        String text = mETxtMessage.getText().toString();
        String id_admin = Contants.ID_ADMIN;
        String senderUid = id_usuario;
        String token_vendedor = Contants.TOKEN;
        Chats chat = new Chats(id_usuario, senderUid, text,id_admin);

        mChatPresenter.sendMessage(this, chat, token_vendedor, senderUid, id_admin);
    }

    @Override
    public void onSendMessageSuccess() {
        mETxtMessage.setText("");
        Toast.makeText(this, "Message sent", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSendMessageFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetMessagesSuccess(Chat chat) {

    }


    @Override
    public void onGetMessagesFailure(String message) {
    }

    @Subscribe
    public void onPushNotificationEvent(PushNotificationEvent pushNotificationEvent) {
        if (mChatRecyclerAdapter == null || mChatRecyclerAdapter.getItemCount() == 0) {
            mChatPresenter.getMessage(FirebaseAuth.getInstance().getCurrentUser().getUid(),
                    pushNotificationEvent.getUid());
        }
    }

    @Override
    public void sendMessageToFirebaseUser(Context context, Chats chat, String receiverFirebaseToken, String id_usuario, String id_admin) {

    }

    @Override
    public void getMessageFromFirebaseUser(String uid_vendedor, String uid_usuario) {

        System.out.println("UIDDDD: "+uid_vendedor+" "+uid_usuario);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference().child(Contants.CHAT)
                .child(id_servicio)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        if (dataSnapshot.exists()){
                            chats.removeAll(chats);
                            Chats chat = dataSnapshot.getValue(Chats.class);
                            System.out.println("SE ENCONTRARON MENSAJES DEL USUARIO.");
                            chats.add(chat);
                            mChatRecyclerAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }





}
