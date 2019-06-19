package com.appfitnessapp.app.fitnessapp.Usuario;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.appfitnessapp.app.fitnessapp.Arrays.Feed;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;
import com.github.barteksc.pdfviewer.listener.OnTapListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.krishna.fileloader.FileLoader;
import com.krishna.fileloader.listener.FileRequestListener;
import com.krishna.fileloader.pojo.FileResponse;
import com.krishna.fileloader.request.FileLoadRequest;

import java.io.File;

public class PantallaPDF extends AppCompatActivity {

    PDFView pdfView;
    ProgressBar progressBar;
    private static final String TAG = "BAJARINFO:";

    static DBProvider dbProvider;

    String tipo,UrlPDF;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_14_feed_pdf_vista);

        pdfView = findViewById(R.id.pdf_view);
        progressBar = findViewById(R.id.progress_);

        Bundle extras = getIntent().getExtras();
        assert extras != null;
        tipo =extras.getString("pdf");



        bajarFeed();


    }

    public void bajarFeed(){

        dbProvider = new DBProvider();
        dbProvider.tablaFeed().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.e(TAG, "Feed: " + dataSnapshot);
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Feed feed = snapshot.getValue(Feed.class);

                        if (feed.getTipo_feed() != null) {
                            if (feed.getTipo_feed().equals(tipo)&&feed.getIs_gratis()) {


                                verPdf(feed.getUrl_tipo());

                            }


                        }
                    }
                }
                else {
                    Log.e(TAG, "Feed: ");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "ERROR: ");
            }
        });

    }

    public void verPdf(String url){

        if (getIntent() != null) {

            String viewType = getIntent().getStringExtra("ViewType");

            if (viewType.equals("internet")){


                progressBar.setVisibility(View.VISIBLE);

                FileLoader.with(this)
                        .load(url)
                        .fromDirectory("PDFFiles",FileLoader.DIR_EXTERNAL_PUBLIC)
                        .asFile(new FileRequestListener<File>() {
                            @Override
                            public void onLoad(FileLoadRequest fileLoadRequest, FileResponse<File> fileResponse) {
                                progressBar.setVisibility(View.GONE);

                                File pdfFile = fileResponse.getBody();

                                pdfView.fromFile(pdfFile).password(null).defaultPage(0).enableSwipe(true)
                                        .swipeHorizontal(false)
                                        .enableDoubletap(true)
                                        .onDraw(new OnDrawListener() {
                                            @Override
                                            public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {

                                            }
                                        })
                                        .onDrawAll(new OnDrawListener() {
                                            @Override
                                            public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {

                                            }
                                        })
                                        .onPageError(new OnPageErrorListener() {
                                            @Override
                                            public void onPageError(int page, Throwable t) {
                                                Toast.makeText(PantallaPDF.this, "Error al abrir la pagina"+page, Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .onPageChange(new OnPageChangeListener() {
                                            @Override
                                            public void onPageChanged(int page, int pageCount) {

                                            }
                                        })
                                        .onTap(new OnTapListener() {
                                            @Override
                                            public boolean onTap(MotionEvent e) {
                                                return true;
                                            }
                                        }).onRender(new OnRenderListener() {
                                    @Override
                                    public void onInitiallyRendered(int nbPages) {

                                    }


                                    public void onInitiallyRendered(int nbPages, float pageWidth, float pageHeight) {
                                        pdfView.fitToWidth(nbPages);
                                    }
                                })

                                        .enableAnnotationRendering(true)
                                        .load();

                            }

                            @Override
                            public void onError(FileLoadRequest fileLoadRequest, Throwable throwable) {
                                Toast.makeText(PantallaPDF.this, ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);

                            }
                        });




            }
            else {

            }


        }

    }
}
