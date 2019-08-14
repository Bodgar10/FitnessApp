package com.appfitnessapp.app.fitnessapp.Usuario;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appfitnessapp.app.fitnessapp.Arrays.EstadisticaAlimentos;
import com.appfitnessapp.app.fitnessapp.Arrays.EstadisticaEjercicio;
import com.appfitnessapp.app.fitnessapp.Arrays.Usuarios;
import com.appfitnessapp.app.fitnessapp.BaseDatos.BajarInfo;
import com.appfitnessapp.app.fitnessapp.BaseDatos.Contants;
import com.appfitnessapp.app.fitnessapp.BaseDatos.DBProvider;
import com.appfitnessapp.app.fitnessapp.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class UsuarioPerfil  extends AppCompatActivity {

    ImageButton imgHome,imgPlan,imgChat,btnDerecha,btnIzquierda;
    CircularImageView imgPersona;
    TextView txtNombre,txtPeso,txtAltura,txtObjetivo,txtDiasSemana;
    LinearLayout btnCalificar;

    private ProgressDialog progressDialog;
    private static final String TAG = "BAJARINFO:";
    static DBProvider dbProvider;

    String id;

    int index_almuerzo = 0;
    int index_desayuno = 0;
    int index_cena = 0;

    int index_domingo = 0;
    int index_lunes = 0;
    int index_martes = 0;
    int index_miercoles = 0;
    int index_jueves = 0;
    int index_viernes = 0;
    int index_sabado = 0;
    int index_ejercicio=0;



    int porcentDesayuno,porcentComida,porcentCena;


    int porcentDomingo,porcentLunes,porcentMartes,porcentMiercoles,porcentJueves,porcentViernes,porcentSabado;

    BarChart chart,chartHorizontal;

    ArrayList<BarEntry> BARENTRY;
    ArrayList<String> BarEntryLabels;

    ArrayList<BarEntry> BARENTRYH;
    ArrayList<String> BarEntryLabelsH;

    BarDataSet set,setHorizontal ;

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    Date date = new Date();
    Calendar cal = Calendar.getInstance();
    int semanaActual,mesActual;


    String fecha = dateFormat.format(date);

       @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_21_perfil);

        Toolbar toolbarback=findViewById(R.id.toolbarU);
        setSupportActionBar(toolbarback);
        getSupportActionBar().setTitle("");

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);

        dbProvider = new DBProvider();
        id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        bajarUsuarios();
        bajarEstadisticasAlimentos();
        bajarEstadisticasEjercicios();

        imgHome=findViewById(R.id.imgHome);
        imgPlan=findViewById(R.id.imgPlan);
        imgChat=findViewById(R.id.imgChat);

        btnDerecha=findViewById(R.id.btnDerecha);
        btnIzquierda=findViewById(R.id.btnIzquierda);


           imgPersona=findViewById(R.id.imgPersona);

        txtNombre=findViewById(R.id.txtNombreUsuario);
        txtPeso=findViewById(R.id.txtPesoActual);
        txtAltura=findViewById(R.id.txtEstatura);
        txtObjetivo=findViewById(R.id.txtObjetivo);

        txtDiasSemana=findViewById(R.id.txtDiasSemana);


        txtDiasSemana.setText(fecha);



        btnCalificar=findViewById(R.id.linearCalificar);

        btnIzquierda.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   String fecha = "";

                   if (btnIzquierda.isClickable()){

                       txtDiasSemana.setText(getPreviousDate(fecha));


                   }

               }
           });

        btnDerecha.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   if (btnIzquierda.isClickable()){
                       String fecha = "";
                       txtDiasSemana.setText(getNextDate(fecha));


                   }

               }
           });



        btnCalificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UsuarioPerfil.this, Calificar.class);
                startActivity(intent);

            }
        });


        imgPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(UsuarioPerfil.this, UsuarioPlan.class);
                startActivity(intent);
                finish();

            }
        });

        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UsuarioPerfil.this, UsuarioHome.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        imgChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UsuarioPerfil.this, UsuarioChat.class);
                startActivity(intent);
                finish();

            }
        });

        chart = (BarChart) findViewById(R.id.chart1);
        chartHorizontal = (BarChart) findViewById(R.id.chartHorizontal);



        CustomBarChartRender barChartRender = new CustomBarChartRender(chart,chart.getAnimator(), chart.getViewPortHandler());
        barChartRender.setRadius(10);
        CustomBarChartRender barChartRenderH = new CustomBarChartRender(chartHorizontal,chartHorizontal.getAnimator(),
                chartHorizontal.getViewPortHandler());
        barChartRenderH.setRadius(10);

        chart.setRenderer(barChartRender);
        chartHorizontal.setRenderer(barChartRenderH);


        //normal
        BARENTRY = new ArrayList<>();
        BarEntryLabels = new ArrayList<String>();
        AddValuesToBARENTRY();
        AddValuesToBarEntryLabels();
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        set = new BarDataSet(BARENTRY, "");
        set.setColors(Collections.singletonList(Color.BLACK));
        //eliminar texto arriba
        set.setDrawValues(false);
        dataSets.add(set);
        BarData data = new BarData(dataSets);
        data.setBarWidth(0.5f);


        //horizontal
        int[] colors = {Color.rgb(255, 255, 255),Color.rgb(255, 50, 0), Color.rgb(0, 194, 176),
                Color.rgb(18, 27, 34), Color.rgb(255, 255, 255)};


        BARENTRYH = new ArrayList<>();
        BarEntryLabelsH = new ArrayList<String>();
        AddValuesToBARENTRYHorizontal();
        AddValuesToBarEntryLabelsHorizontal();
        ArrayList<IBarDataSet> dataSetsH = new ArrayList<>();
        setHorizontal = new BarDataSet(BARENTRYH, "");
        setHorizontal.setColors(colors);
        //eliminar texto arriba
        setHorizontal.setDrawValues(false);
        dataSetsH.add(setHorizontal);
        BarData dataHorizontal = new BarData(dataSetsH);
        dataHorizontal.setBarWidth(0.5f);



        //normal
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(9);
        xAxis.setAxisLineColor(Color.WHITE);
        YAxis yAxisRight = chart.getAxisLeft();
        YAxis yAxisLeft = chart.getAxisRight();
        yAxisRight.setEnabled(false);
        yAxisLeft.setEnabled(false);

        chart.getAxisRight().setEnabled(false);
        chart.getLegend().setEnabled(false);

        chart.setLogEnabled(true);
        chart.setTouchEnabled(false);
        chart.setPinchZoom(false);
        chart.setDoubleTapToZoomEnabled(false);
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getXAxis().setDrawGridLines(false);
        chart.getDescription().setEnabled(false);
        chart.setDescription(null);
        chart.animateY(3000);
        chart.setData(data);
        chart.invalidate();


        //esto para los datos
        IAxisValueFormatter formatter = new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return BarEntryLabels.get((int)value);
            }
            // we don't draw numbers, so no decimal digits needed
            public int getDecimalDigits() {  return 0; }
        };
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(formatter);



        //horizontal
        XAxis xAxisH = chartHorizontal.getXAxis();
        xAxisH.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxisH.setDrawGridLines(false);
        xAxisH.setTextColor(Color.BLACK);
        xAxisH.setTextSize(9);
        xAxisH.setAxisLineColor(Color.WHITE);
        YAxis yAxisRightH = chartHorizontal.getAxisLeft();
        YAxis yAxisLeftH = chartHorizontal.getAxisRight();
        yAxisRightH.setEnabled(false);
        yAxisLeftH.setEnabled(false);

        chartHorizontal.getAxisRight().setEnabled(false);
        chartHorizontal.getLegend().setEnabled(false);

        chartHorizontal.setLogEnabled(true);
        chartHorizontal.setTouchEnabled(false);
        chartHorizontal.setPinchZoom(false);
        chartHorizontal.setDoubleTapToZoomEnabled(false);
        chartHorizontal.getAxisLeft().setDrawGridLines(false);
        chartHorizontal.getXAxis().setDrawGridLines(false);
        chartHorizontal.getDescription().setEnabled(false);
        chartHorizontal.setDescription(null);
        chartHorizontal.animateY(3000);
        chartHorizontal.setData(dataHorizontal);
        chartHorizontal.invalidate();


        //esto para los datos
        IAxisValueFormatter formatterH = new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return BarEntryLabelsH.get((int)value);
            }
            // we don't draw numbers, so no decimal digits needed
            public int getDecimalDigits() {  return 0; }
        };
        xAxisH.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxisH.setValueFormatter(formatterH);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.editar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.editar:
                Abrir();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void Abrir() {
        Intent intent=new Intent(UsuarioPerfil.this,EditarPerfil.class);
        startActivity(intent);

    }

    public void bajarUsuarios(){
        dbProvider = new DBProvider();

        progressDialog.setMessage("Cargando Información...");
        progressDialog.show();
        dbProvider.usersRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e(TAG, "Usuarios 4: ");
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        //Log.e(TAG,"Usuarios: "+ snapshot);
                        Log.e(TAG, "Usuarios: " + snapshot);
                        Usuarios usuarios = snapshot.getValue(Usuarios.class);


                        if (usuarios.getId_usuario().equals(id)) {


                            txtNombre.setText(usuarios.getNombre_usuario());
                            txtAltura.setText(usuarios.getEstatura());
                            txtPeso.setText(usuarios.getPeso_actual());
                            txtObjetivo.setText(usuarios.getObjetivo());


                            if (usuarios.getFoto_usuario().equals("nil")) {
                                try {
                                    URL urlfeed = new URL(usuarios.getFoto_usuario());
                                    Picasso.get().load(String.valueOf(urlfeed))
                                            .error(R.mipmap.ic_launcher)
                                            .fit()
                                            .noFade()
                                            .into(imgPersona);
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                                progressDialog.dismiss();
                            }
                            else {
                            loadImageFromUrl(usuarios.getFoto_usuario());
                            progressDialog.dismiss();
                        }
                        }




                    }
                } else {
                    Log.e(TAG, "Usuarios 3: ");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "ERROR: ");
            }
        });
    }


    public void bajarEstadisticasAlimentos(){
        dbProvider = new DBProvider();


        dbProvider.estadisticaAlimentos().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e(TAG, "Usuarios 4: ");
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        //Log.e(TAG,"Usuarios: "+ snapshot);
                        Log.e(TAG, "Usuarios: " + snapshot);
                        EstadisticaAlimentos estadisticaAlimentos = snapshot.getValue(EstadisticaAlimentos.class);

                        cal.setTime(date);
                        semanaActual = cal.get(Calendar.WEEK_OF_MONTH);
                        mesActual = cal.get(Calendar.MONTH);
                        String semana= String.valueOf(semanaActual);
                        String mes= String.valueOf(mesActual);



                        String semanaBase =estadisticaAlimentos.getFecha_cumplida();
                        SimpleDateFormat dateFormattt = new SimpleDateFormat("dd/MM/yyyy");
                        Date convertedDate = new Date();
                        try {
                            convertedDate=dateFormattt.parse(semanaBase);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Calendar c = Calendar.getInstance();
                        c.setTime(convertedDate);
                        int diaBase = c.get(Calendar.WEEK_OF_MONTH);
                        int mesBase = c.get(Calendar.MONTH);




                        if (estadisticaAlimentos.getId_usuario().equals(id)) {
                            if (semana.equals(diaBase)&&mes.equals(mesBase)){

                                if (estadisticaAlimentos.getTipo_alimento().equals(Contants.ALMUERZO)){
                                    index_almuerzo +=1;
                                    porcentComida = (index_almuerzo/7)*100;
                                }
                                else if(estadisticaAlimentos.getTipo_alimento().equals(Contants.DESAYUNO)){
                                    index_desayuno +=1;
                                    porcentDesayuno = (index_desayuno/7)*100;

                                }
                                else {
                                    index_cena +=1;
                                    porcentCena = (index_cena/7)*100;

                                }

                            }

                        }




                    }
                } else {
                    Log.e(TAG, "Usuarios 3: ");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "ERROR: ");
            }
        });
    }


    public void bajarEstadisticasEjercicios(){
        dbProvider = new DBProvider();

        dbProvider.estadisticaEjercicios().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e(TAG, "Usuarios 4: ");
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        //Log.e(TAG,"Usuarios: "+ snapshot);
                        Log.e(TAG, "Usuarios: " + snapshot);
                        EstadisticaEjercicio estadisticaEjercicio = snapshot.getValue(EstadisticaEjercicio.class);

                        cal.setTime(date);
                        semanaActual = cal.get(Calendar.WEEK_OF_MONTH);
                        mesActual = cal.get(Calendar.MONTH);
                        String semana= String.valueOf(semanaActual);
                        String mes= String.valueOf(mesActual);

                        String semanaBase =estadisticaEjercicio.getFecha_cumplida();
                        SimpleDateFormat dateFormattt = new SimpleDateFormat("dd/MM/yyyy");
                        Date convertedDate = new Date();
                        try {
                            convertedDate=dateFormattt.parse(semanaBase);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Calendar c = Calendar.getInstance();
                        c.setTime(convertedDate);
                        int diaBase = c.get(Calendar.WEEK_OF_MONTH);
                        int mesBase = c.get(Calendar.MONTH);

                        if (estadisticaEjercicio.getId_usuario().equals(id)) {
                            if (semana.equals(diaBase)&&mes.equals(mesBase)){
                                index_ejercicio +=1;
                                Calendar calendar = Calendar.getInstance();
                                int day = calendar.get(Calendar.DAY_OF_WEEK);
                                switch (day) {


                  case Calendar.SUNDAY:
                 index_domingo +=1;
                 porcentDomingo =(index_domingo/index_ejercicio)*100;
                 break;

               case Calendar.MONDAY:
                   index_lunes +=1;
                   porcentLunes =(index_lunes/index_ejercicio)*100;

                   break;
               case Calendar.TUESDAY:
                   index_martes +=1;
                   porcentMartes =(index_martes/index_ejercicio)*100;

                   break;
               case Calendar.WEDNESDAY:
                   index_miercoles +=1;
                   porcentMiercoles =(index_miercoles/index_ejercicio)*100;

                   break;

               case Calendar.THURSDAY:
                   index_jueves +=1;
                   porcentJueves =(index_jueves/index_ejercicio)*100;

                   break;
               case Calendar.FRIDAY:
                   index_viernes +=1;
                   porcentViernes =(index_viernes/index_ejercicio)*100;
                   break;

               case Calendar.SATURDAY:
                   index_sabado +=1;
                   porcentSabado =(index_sabado/index_ejercicio)*100;
                   break;
           }
                }
                    }

                    }
                } else {
                    Log.e(TAG, "Usuarios 3: ");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "ERROR: ");
            }
        });
    }


    private void loadImageFromUrl(String url) {

        Picasso.get().load(url).into(imgPersona);
    }




    public void AddValuesToBARENTRY(){

        BARENTRY.add(new BarEntry(0, porcentDomingo));
        BARENTRY.add(new BarEntry(1, porcentLunes));
        BARENTRY.add(new BarEntry(2,  porcentMartes));
        BARENTRY.add(new BarEntry(3, porcentMiercoles));
        BARENTRY.add(new BarEntry(4, porcentJueves));
        BARENTRY.add(new BarEntry(5, porcentViernes));
        BARENTRY.add(new BarEntry(6, porcentSabado));

    }

    public void AddValuesToBarEntryLabels(){

        BarEntryLabels.add("D");
        BarEntryLabels.add("L");
        BarEntryLabels.add("M");
        BarEntryLabels.add("Mi");
        BarEntryLabels.add("J");
        BarEntryLabels.add("V");
        BarEntryLabels.add("S");
    }




    public void AddValuesToBARENTRYHorizontal(){

        BARENTRYH.add(new BarEntry(0, 0));
        BARENTRYH.add(new BarEntry(1, porcentDesayuno));
        BARENTRYH.add(new BarEntry(2,  porcentComida));
        BARENTRYH.add(new BarEntry(3,  porcentCena));
        BARENTRYH.add(new BarEntry(4,  0));

    }

    public void AddValuesToBarEntryLabelsHorizontal(){

        BarEntryLabelsH.add("");
        BarEntryLabelsH.add("");
        BarEntryLabelsH.add("");
        BarEntryLabelsH.add("");
        BarEntryLabelsH.add("");


    }



    private String getPreviousDate(String inputDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        inputDate = dateFormat.format(date);
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(date);

            c.add(Calendar.DATE, -1);
            inputDate = dateFormat.format(c.getTime());
            Log.d("asd", "selected date : "+inputDate);

            System.out.println(date);
        } catch (Exception e) {
            e.printStackTrace();
            inputDate ="";
        }
        return inputDate;
    }

    private String getNextDate(String inputDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        inputDate = dateFormat.format(date);
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(date);

            c.add(Calendar.DATE, +1);
            inputDate = dateFormat.format(c.getTime());
            Log.d("asd", "selected date : "+inputDate);

            System.out.println(date);
        } catch (Exception e) {
            e.printStackTrace();
            inputDate ="";
        }
        return inputDate;
    }




    public static class GetWeekOfMonthAndYear {

        public static void main(String[] args) {

            //Create calendar instance
            Calendar calendar = Calendar.getInstance();

            System.out.println("Current week of this month = " + calendar.get(Calendar.WEEK_OF_MONTH));


            System.out.println("Current week of this year = " + calendar.get(Calendar.WEEK_OF_YEAR));

        }
    }

}
