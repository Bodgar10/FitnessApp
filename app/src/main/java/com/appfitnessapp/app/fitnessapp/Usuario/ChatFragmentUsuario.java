package com.appfitnessapp.app.fitnessapp.Usuario;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;

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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appfitnessapp.app.fitnessapp.Adapters.ChatRecyclerAdapter;
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
import com.mikhaellopez.circularimageview.CircularImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

public class ChatFragmentUsuario extends Fragment implements ChatContractUsuario.View, TextView.OnEditorActionListener, ChatContractUsuario.Interactor {


    CircularImageView imgUsuariioChat;

    String id;
    private RecyclerView mRecyclerViewChat;
    private EditText mETxtMessage;

    private ProgressDialog mProgressDialog;

    private ChatRecyclerAdapter mChatRecyclerAdapter;

    private ChatPresenterUsuario mChatPresenter;

    private android.app.AlertDialog alertDialog;

    ArrayList<Usuarios> usuarios;
    ArrayList<Chats> chats;
    static DBProvider dbProvider;

    private String id_relper, id_usuario, category, id_servicio, name_relper;
    private FirebaseAuth mAuth;

    public static ChatFragmentUsuario newInstance(String name,
                                                  String uid,
                                                  String id_admin, String token, String id_servicio) {
        Bundle args = new Bundle();
        args.putString(Contants.ARG_RECEIVER, name);
        args.putString(Contants.ARG_RECEIVER_UID, uid);
        args.putString(Contants.ARG_FIREBASE_TOKEN, id_admin);
        args.putString(Contants.TOKEN, token);
        args.putString(Contants.ID_SERVICIO, id_servicio);
        ChatFragmentUsuario fragment = new ChatFragmentUsuario();
        fragment.setArguments(args);
        return fragment;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.usuario_23_chat, container, false);
        bindViews(fragmentView);

        /*Toolbar toolbar=(Toolbar)fragmentView.findViewById(R.id.toolbar_b);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle("Chat");*/
        // activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);

        mAuth = FirebaseAuth.getInstance();
        chats = new ArrayList<>();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        id_relper = getArguments().getString(Contants.ARG_FIREBASE_TOKEN);
        id_servicio = getArguments().getString(Contants.ID_SERVICIO);

        usuarios = new ArrayList<>();



        id_usuario = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mChatRecyclerAdapter = new ChatRecyclerAdapter(chats);

        mRecyclerViewChat.setAdapter(mChatRecyclerAdapter);

        //chats.removeAll(chats);
        database.getReference().child(Contants.CHATS)
                .child(id_servicio)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
                        if (dataSnapshot.exists()) {
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



        return fragmentView;

    }

    private void bindViews(View view) {
        mRecyclerViewChat = (RecyclerView) view.findViewById(R.id.recycler_view_chat);

        //imgUsuariioChat = (CircularImageView) view.findViewById(R.id.imgUsuariioChat);
        //txtNombreUsuarioChat = (TextView) view.findViewById(R.id.txtNombreUsuarioChat);
        mRecyclerViewChat.setLayoutManager(new LinearLayoutManager(getContext()));
        mETxtMessage = (EditText) view.findViewById(R.id.edit_text_message);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setTitle("Cargando...");
        mProgressDialog.setMessage("Por favor, espere");
        mProgressDialog.setIndeterminate(true);

        mETxtMessage.setOnEditorActionListener(this);

        mChatPresenter = new ChatPresenterUsuario(this);
        mChatPresenter.getMessage(id_relper,
                getArguments().getString(Contants.ARG_RECEIVER_UID));
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEND) {
            sendMessage();
            return true;
        }
        return false;
    }

    private void sendMessage() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        String text = mETxtMessage.getText().toString();
        String name = getArguments().getString(Contants.ARG_RECEIVER);
        String senderUid = id_usuario;
        String uid_user = getArguments().getString(Contants.ARG_RECEIVER_UID);
        String token_vendedor = getArguments().getString(Contants.TOKEN);
        Chats chat = new Chats(id_servicio, id_usuario, text);

        mChatPresenter.sendMessage(getActivity().getApplicationContext(), chat, token_vendedor, senderUid, id_servicio);
    }

    @Override
    public void onSendMessageSuccess() {
        mETxtMessage.setText("");
        Toast.makeText(getActivity(), "Message sent", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSendMessageFailure(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetMessagesSuccess(Chat chat) {
        /*if (mChatRecyclerAdapter == null) {
            mChatRecyclerAdapter = new ChatRecyclerAdapterUsuario(new ArrayList<Chat>());
            mRecyclerViewChat.setAdapter(mChatRecyclerAdapter);
        }
        mChatRecyclerAdapter.add(chat);
        mRecyclerViewChat.smoothScrollToPosition(mChatRecyclerAdapter.getItemCount() - 1);*/
    }


    @Override
    public void onGetMessagesFailure(String message) {
        //Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Subscribe
    public void onPushNotificationEvent(PushNotificationEvent pushNotificationEvent) {
        if (mChatRecyclerAdapter == null || mChatRecyclerAdapter.getItemCount() == 0) {
            mChatPresenter.getMessage(FirebaseAuth.getInstance().getCurrentUser().getUid(),
                    pushNotificationEvent.getUid());
        }
    }

    @Override
    public void sendMessageToFirebaseUser(Context context, Chats chat, String receiverFirebaseToken, String id_usuario, String id_servicio) {

    }

    @Override
    public void getMessageFromFirebaseUser(String uid_vendedor, String uid_usuario) {

    }

}
