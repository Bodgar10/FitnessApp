package com.appfitnessapp.app.fitnessapp.Usuario;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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


    String tipo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_14_feed_pdf_vista);



        Toolbar toolbarback=findViewById(R.id.include);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        pdfView = findViewById(R.id.pdf_view);
        progressBar = findViewById(R.id.progress_);

        Bundle extras = getIntent().getExtras();
        assert extras != null;
        tipo =extras.getString("pdf");


        verPdf(tipo);


    }


    public void verPdf(String url){
        if (getIntent() != null) {
            String viewType = getIntent().getStringExtra("ViewType");
            if (viewType.equals("internet")){
                progressBar.setVisibility(View.VISIBLE);
                FileLoader.with(this)
                        .load(url)
                        //.fromDirectory("PDFFiles",FileLoader.DIR_EXTERNAL_PUBLIC)
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

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onBackPressed() {
        finish();
    }
}
