package com.appfitnessapp.app.fitnessapp.Usuario;

import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.appfitnessapp.app.fitnessapp.Adapters.AdapterFeed;
import com.appfitnessapp.app.fitnessapp.Arrays.Feed;
import com.appfitnessapp.app.fitnessapp.BaseDatos.BajarInfo;
import com.appfitnessapp.app.fitnessapp.BaseDatos.Contants;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.FullScreenMediaController;
import com.appfitnessapp.app.fitnessapp.MyMediaController;
import com.appfitnessapp.app.fitnessapp.R;
import com.appfitnessapp.app.fitnessapp.VideoControllerView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class Video extends AppCompatActivity {


    VideoView videoView;
    VideoControllerView videoContr;
    String tipo;
    private int position = 0;
    MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        goFullScreen();

        // requestWindowFeature(Window.FEATURE_NO_TITLE);
       // getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        setContentView(R.layout.usuario_14_feed_video);

        /*
        this.setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

*/
        videoView=findViewById(R.id.videoView);

        mediaController = new MyMediaController(this, (FrameLayout) findViewById(R.id.controllerAnchor));



        mediaController = new MediaController(Video.this){
            @Override
            public void show(int timeout) {
                super.show(0);
            }
        };

       // mediaController.setAnchorView(videoView);

        videoView.setMediaController(mediaController);

        mediaController.setMediaPlayer(mcEvents);


        Bundle extras = getIntent().getExtras();
        assert extras != null;
        tipo =extras.getString("video");

        setUpVideoView(tipo);


    }

    @Override
    protected void onStop() {
        super.onStop();

    }


    private void setUpVideoView(String url) {

        Uri uri = Uri.parse(url);

        try {
            // Asigna la URI del vídeo que será reproducido a la vista.
            videoView.setVideoURI(uri);
            // Se asigna el foco a la VideoView.
            videoView.requestFocus();
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        /*
         * Se asigna un listener que nos informa cuando el vídeo
         * está listo para ser reproducido.
         */
        videoView.setOnPreparedListener(videoViewListener);
    }

    private MediaPlayer.OnPreparedListener videoViewListener =
            new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {

                    /*
                     * Se indica al reproductor multimedia que el vídeo
                     * se reproducirá en un loop (on repeat).
                     */
                    FrameLayout controllerAnchor = (FrameLayout) findViewById(R.id.controllerAnchor);
                    mediaController.setAnchorView(controllerAnchor);
                    mediaPlayer.setLooping(true);

                    if (position == 0) {
                        /*
                         * Si tenemos una posición en savedInstanceState,
                         * el vídeo debería comenzar desde aquí.
                         */
                        videoView.start();
                    }

                    else {
                        /*
                         * Si venimos de un Activity "resumed",
                         * la reproducción del vídeo será pausada.
                         */
                        videoView.pause();
                    }
                }
            };


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        /* Usamos onSaveInstanceState para guardar la posición de
           reproducción del vídeo en caso de un cambio de orientación. */
        savedInstanceState.putInt("Position",
                videoView.getCurrentPosition());
        videoView.pause();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        /*
         * Usamos onRestoreInstanceState para reproducir el vídeo
         * desde la posición guardada.
         */
        position = savedInstanceState.getInt("Position");
        videoView.seekTo(position);


    }


    /*

    public void bajar(){

        storageReference=firebaseStorage.getReference();
        ref=storageReference.child("video");
        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url =uri.toString();
                bajarVideo(Video.this,"Video de prueba",".mp4",DIRECTORY_DOWNLOADS,url);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


    }

    public void bajarVideo(Context context,String fileName,String fileExtesion,String destinationDirectory,String url){

        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

        Uri uri= Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);


        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context,destinationDirectory,fileName+fileExtesion);

        downloadManager.enqueue(request);


    }


*/


    private void goFullScreen() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        makeAllGone();
    }
    private void makeAllGone(){

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window w = getWindow(); // in Activity's onCreate() for instance
                w.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
                w.setNavigationBarColor(Color.TRANSPARENT);
            }

    }




    private MediaController.MediaPlayerControl mcEvents = new MediaController.MediaPlayerControl() {
        public void start() {
            videoView.start();
        }

        public void seekTo(int pos) {
            videoView.seekTo(pos);
        }

        public void pause() {
            videoView.pause();
        }

        public boolean isPlaying() {
            return videoView.isPlaying();
        }

        @Override
        public int getBufferPercentage() {
            return 0;
        }

        public int getDuration() {
            return videoView.getDuration();
        }

        public int getCurrentPosition() {
            return videoView.getCurrentPosition();
        }



        public boolean canSeekForward() {
            return true;
        }

        @Override
        public int getAudioSessionId() {
            return 0;
        }

        public boolean canSeekBackward() {
            return true;
        }

        public boolean canPause() {
            return true;
        }
    };
}
