package com.appfitnessapp.app.fitnessapp.Usuario;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ChatFragmentUsuario extends AppCompatActivity implements ChatContractUsuario.View, TextView.OnEditorActionListener, ChatContractUsuario.Interactor {


    CircularImageView imgAdmin;
    TextView txtNombreAdmin;
    String id;
    private RecyclerView mRecyclerViewChat;
    private EditText mETxtMessage;

    private ProgressDialog mProgressDialog;

    private ChatRecyclerAdapter mChatRecyclerAdapter;

    private ChatPresenterUsuario mChatPresenter;


    ImageButton imgHome,imgPlan,imgPerfil;

    ArrayList<Usuarios> usuarios;
    ArrayList<Chats> chats;
    static DBProvider dbProvider;

    private String id_relper, id_usuario;
    private FirebaseAuth mAuth;

    String name, uid, id_admin,  token, id_servicio1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_23_chat);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            name = extras.getString("name");
            uid = extras.getString("uid");
            id_admin = extras.getString("id_admin");
            token = extras.getString("token");
            id_servicio1 = extras.getString("id_servicio");
        }

        mRecyclerViewChat = (RecyclerView) findViewById(R.id.recycler_view_chat);

        imgAdmin = findViewById(R.id.imgAdmin);
        txtNombreAdmin =  findViewById(R.id.txtNombreUsuarioChat);
        mRecyclerViewChat.setLayoutManager(new LinearLayoutManager(this));
        mETxtMessage = findViewById(R.id.edit_text_message);

        imgHome=findViewById(R.id.imgHome);
        imgPlan=findViewById(R.id.imgPlan);
        imgPerfil=findViewById(R.id.imgPerfil);


        mAuth = FirebaseAuth.getInstance();
        chats = new ArrayList<>();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        init();

        id_relper = Contants.ARG_FIREBASE_TOKEN;

        usuarios = new ArrayList<>();

        id_usuario = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mChatRecyclerAdapter = new ChatRecyclerAdapter(chats);

        mRecyclerViewChat.setAdapter(mChatRecyclerAdapter);

        bajarUsuarios();



        //chats.removeAll(chats);
        database.getReference().child(Contants.CHATS)
                .child(id_usuario)
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





    }


    private void init() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Cargando...");
        mProgressDialog.setMessage("Por favor, espere");
        mProgressDialog.setIndeterminate(true);

        mETxtMessage.setOnEditorActionListener(this);

        mChatPresenter = new ChatPresenterUsuario(this);
        mChatPresenter.getMessage(id_relper,Contants.ARG_RECEIVER_UID);
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

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEND) {
            sendMessage();
            return true;
        }
        return false;
    }

    private void sendMessage() {
        String text = mETxtMessage.getText().toString();
        String senderUid = id_usuario;
        String token_vendedor = Contants.TOKEN;
        Chats chat = new Chats(id_usuario, senderUid, text,id_admin);

        mChatPresenter.sendMessage(this, chat, token_vendedor, senderUid,id_admin);
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

    }

    public void bajarUsuarios(){
        dbProvider = new DBProvider();

        dbProvider.usersRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                        Usuarios usuarios = snapshot.getValue(Usuarios.class);

                        if (usuarios.getId_usuario()!=null) {

                            if (usuarios.getTipo_usuario().equals(Contants.ADMIN)) {

                                id_admin = usuarios.getId_usuario();
                                txtNombreAdmin.setText(usuarios.getNombre_usuario());

                                if (!usuarios.getFoto_usuario().equals("nil")) {
                                    try {
                                        URL urlfeed = new URL(usuarios.getFoto_usuario());
                                        Picasso.get().load(String.valueOf(urlfeed))
                                                .error(R.mipmap.ic_launcher)
                                                .fit()
                                                .noFade()
                                                .into(imgAdmin);
                                    } catch (MalformedURLException e) {
                                        e.printStackTrace();
                                    }
                                }

                            }


                        }

                    }
                }else{

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


}
