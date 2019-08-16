package com.appfitnessapp.app.fitnessapp;

import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;
import com.github.barteksc.pdfviewer.listener.OnTapListener;
import com.krishna.fileloader.FileLoader;
import com.krishna.fileloader.listener.FileRequestListener;
import com.krishna.fileloader.pojo.FileResponse;
import com.krishna.fileloader.request.FileLoadRequest;

import java.io.File;

public class viewPdf extends AppCompatActivity {

    PDFView pdfView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_14_feed_pdf_vista);

        pdfView = findViewById(R.id.pdf_view);
        progressBar = findViewById(R.id.progress_);
/*
        if (getIntent() != null){

            String viewType=getIntent().getStringExtra("ViewType");
            if (viewType.equals("assets")){

                pdfView.fromAsset("la_despedida.pdf").password(null).defaultPage(0).enableSwipe(true)
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
                                Toast.makeText(viewPdf.this, "Error al abrir la pagina"+page, Toast.LENGTH_SHORT).show();
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
                    public void onInitiallyRendered(int nbPages, float pageWidth, float pageHeight) {
                        pdfView.fitToWidth();
                    }
                })

                        .enableAnnotationRendering(true)
                        .invalidPageColor(Color.WHITE)
                        .load();

            }

            else if(viewType.equals("storage")){

                Uri pdFile = Uri.parse(getIntent().getStringExtra("FileUri"));


                pdfView.fromUri(pdFile).password(null).defaultPage(0).enableSwipe(true)
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
                                Toast.makeText(viewPdf.this, "Error al abrir la pagina"+page, Toast.LENGTH_SHORT).show();
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
                    public void onInitiallyRendered(int nbPages, float pageWidth, float pageHeight) {
                        pdfView.fitToWidth();
                    }
                })

                        .enableAnnotationRendering(true)
                        .invalidPageColor(Color.WHITE)
                        .load();


            }

            else if (viewType.equals("internet")){

                progressBar.setVisibility(View.VISIBLE);

                FileLoader.with(this)
                        .load("http://unec.edu.az/application/uploads/2014/12/pdf-sample.pdf")
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
                                                Toast.makeText(viewPdf.this, "Error al abrir la pagina"+page, Toast.LENGTH_SHORT).show();
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
                                    public void onInitiallyRendered(int nbPages, float pageWidth, float pageHeight) {
                                        pdfView.fitToWidth();
                                    }
                                })

                                        .enableAnnotationRendering(true)
                                        .invalidPageColor(Color.WHITE)
                                        .load();

                            }

                            @Override
                            public void onError(FileLoadRequest fileLoadRequest, Throwable throwable) {
                                Toast.makeText(viewPdf.this, ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);

                            }
                        });


            }




        }

    }*/
    }
}
