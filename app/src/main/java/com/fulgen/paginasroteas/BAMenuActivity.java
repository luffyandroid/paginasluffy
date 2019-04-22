package com.fulgen.paginasroteas;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Html;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class BAMenuActivity extends AppCompatActivity {

    //ETIQUETA EXTRA PARA PASAR INFO A TODOS LOS ACTIVITYS
    static final String EXTRA_ANUNCIO = "ANUNCIO";
    static final String EXTRA_CATEGORIA = "CATEGORIA";

    //TIEMPO DE ACCIÓN BOTÓN
    private static final long TIEMPO_BOTON = 500;

    //ETIQUETAS SUBIDA DE BASE DE DATOS
    private DatabaseReference dbAnuncio;
    private ValueEventListener eventListener;


    //VARIANTES DE DECLARADAS
    //GridView listBA;
    TextView tvAlimentacionocultoBA, tvAsocioacionesocultoBA, tvComprasocultoBA, tvDeporteocultoBA, tvEducacionocultoBA, tvHotelesocultoBA, tvInmobiliariaocultaBA, tvInstitucionesocultoBA,
            tvMonumentosocultoBA, tvOcioocultoBA, tvParqueocultoBA, tvPlayaocultoBA, tvRestauracionocultoBA, tvSaludocultoBA, tvServiciosocultoBA, tvSeguridadocultoBA, tvTransporteocultoBA, tvVacioBA,
            tvidiomaba;

    LottieAnimationView ivAlimentacionBA, ivAsociacionesBA, ivComprasBA, ivDeporteBA, ivEducacionBA, ivHotelesBA,
                    ivInmobiliariaBA, ivInstitucionesBA, ivMonumentosBA, ivOcioBA, ivParqueBA, ivPlayaBA, ivRestauracionBA,
                    ivSaludBA, ivSeguridadBA, ivServiciosBA, ivTransporteBA;

    //ADAPTADOR
    //ArrayList<ZCategoria> lista_anuncios = new ArrayList<ZCategoria>();

    //VARIANTES DE DECLARADAS
    private FloatingActionsMenu menu_fabBA;
    EditText etfootbuscarBA;
    final Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bamenu);

        //ENLAZO VARIANTES DECLARADAS

        //MENU FLOATING BUTTON
        menu_fabBA = (FloatingActionsMenu)findViewById(R.id.menu_fabBA);

        //TEXTVIEWS QUE BORRO FULL
        tvAlimentacionocultoBA = (TextView)findViewById(R.id.tvAlimentacionocultoBA);
        tvAsocioacionesocultoBA = (TextView)findViewById(R.id.tvAsociacionesocultoBA);
        tvComprasocultoBA = (TextView)findViewById(R.id.tvComprasocultoBA);
        tvDeporteocultoBA = (TextView)findViewById(R.id.tvDeporteocultoBA);
        tvEducacionocultoBA = (TextView)findViewById(R.id.tvEducacionocultoBA);
        tvHotelesocultoBA = (TextView)findViewById(R.id.tvHotelesocultoBA);
        tvInmobiliariaocultaBA = (TextView)findViewById(R.id.tvInmobiliariaocultoBA);
        tvInstitucionesocultoBA = (TextView)findViewById(R.id.tvInstitucionesocultoBA);
        tvMonumentosocultoBA = (TextView)findViewById(R.id.tvMonumentosocultoBA);
        tvOcioocultoBA = (TextView)findViewById(R.id.tvOcioocultoBA);
        tvParqueocultoBA = (TextView)findViewById(R.id.tvParqueocultoBA);
        tvPlayaocultoBA = (TextView)findViewById(R.id.tvPlayacultoBA);
        tvRestauracionocultoBA = (TextView)findViewById(R.id.tvRestauracionocultoBA);
        tvSaludocultoBA = (TextView)findViewById(R.id.tvSaludocultoBA);
        tvSeguridadocultoBA = (TextView)findViewById(R.id.tvSeguridadocultoBA);
        tvServiciosocultoBA = (TextView)findViewById(R.id.tvServiciosocultoBA);
        tvTransporteocultoBA = (TextView)findViewById(R.id.tvTransporteocultoBA);
        tvidiomaba = (TextView)findViewById(R.id.tvidiomaba);

        //ESTILO BOTON ANIMADO ▼

        //ALIMENTACION
        ivAlimentacionBA = (LottieAnimationView) findViewById(R.id.ivAlimentacionBA);
        ivAlimentacionBA.setAnimation("cat_a_alimentacion.json");
        ivAlimentacionBA.loop(false);
        ivAlimentacionBA.pauseAnimation();
        ivAlimentacionBA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickalientacion(view);
                ivAlimentacionBA.playAnimation();
            }
        });

        //ASOCIACIONES
        ivAsociacionesBA = (LottieAnimationView) findViewById(R.id.ivAsociacionesBA);
        ivAsociacionesBA.setAnimation("cat_asociaciones.json");
        ivAsociacionesBA.loop(false);
        ivAsociacionesBA.pauseAnimation();
        ivAsociacionesBA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickasociaciones(view);
                ivAsociacionesBA.playAnimation();
            }
        });

        //COMPRAS
        ivComprasBA = (LottieAnimationView) findViewById(R.id.ivComprasBA);
        ivComprasBA.setAnimation("cat_c_compras.json");
        ivComprasBA.loop(false);
        ivComprasBA.pauseAnimation();
        ivComprasBA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickcompras(view);
                ivComprasBA.playAnimation();
            }
        });

        //DEPORTE
        ivDeporteBA = (LottieAnimationView) findViewById(R.id.ivDeporteBA);
        ivDeporteBA.setAnimation("cat_d_deporte.json");
        ivDeporteBA.loop(false);
        ivDeporteBA.pauseAnimation();
        ivDeporteBA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickdeportes(view);
                ivDeporteBA.playAnimation();
            }
        });

        //EDUCACIÓN
        ivEducacionBA = (LottieAnimationView) findViewById(R.id.ivEducacionBA);
        ivEducacionBA.setAnimation("cat_e_educacion.json");
        ivEducacionBA.loop(false);
        ivEducacionBA.pauseAnimation();
        ivEducacionBA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickeducacion(view);
                ivEducacionBA.playAnimation();
            }
        });

        //HOTELES
        ivHotelesBA = (LottieAnimationView) findViewById(R.id.ivHotelesBA);
        ivHotelesBA.setAnimation("cat_h_hoteles.json");
        ivHotelesBA.loop(false);
        ivHotelesBA.pauseAnimation();
        ivHotelesBA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickhoteles(view);
                ivHotelesBA.playAnimation();
            }
        });

        //INMOBILIARIA
        ivInmobiliariaBA = (LottieAnimationView) findViewById(R.id.ivInmobiliariaBA);
        ivInmobiliariaBA.setAnimation("cat_inmobiliaria.json");
        ivInmobiliariaBA.loop(false);
        ivInmobiliariaBA.pauseAnimation();
        ivInmobiliariaBA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickinmoviliaria(view);
                ivInmobiliariaBA.playAnimation();
            }
        });

        //INSTITUCIONES
        ivInstitucionesBA = (LottieAnimationView) findViewById(R.id.ivInstitucionesBA);
        ivInstitucionesBA.setAnimation("cat_i_instituciones.json");
        ivInstitucionesBA.loop(false);
        ivInstitucionesBA.pauseAnimation();
        ivInstitucionesBA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickinstituciones(view);
                ivInstitucionesBA.playAnimation();
            }
        });

        //MONUMENTOS
        ivMonumentosBA = (LottieAnimationView) findViewById(R.id.ivMonumentosBA);
        ivMonumentosBA.setAnimation("cat_m_monumentos.json");
        ivMonumentosBA.loop(false);
        ivMonumentosBA.pauseAnimation();
        ivMonumentosBA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickmonumentos(view);
                ivMonumentosBA.playAnimation();
            }
        });

        //OCIO
        ivOcioBA = (LottieAnimationView) findViewById(R.id.ivOcioBA);
        ivOcioBA.setAnimation("cat_o_ocio.json");
        ivOcioBA.loop(false);
        ivOcioBA.pauseAnimation();
        ivOcioBA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickocio(view);
                ivOcioBA.playAnimation();
            }
        });

        //PARQUE
        ivParqueBA = (LottieAnimationView) findViewById(R.id.ivParqueBA);
        ivParqueBA.setAnimation("cat_pa_parque.json");
        ivParqueBA.loop(false);
        ivParqueBA.pauseAnimation();
        ivParqueBA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickparque(view);
                ivParqueBA.playAnimation();
            }
        });

        //PLAYA
        ivPlayaBA = (LottieAnimationView) findViewById(R.id.ivPlayaBA);
        ivPlayaBA.setAnimation("cat_pl_playa.json");
        ivPlayaBA.loop(false);
        ivPlayaBA.pauseAnimation();
        ivPlayaBA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickplaya(view);
                ivPlayaBA.playAnimation();
            }
        });

        //RESTAURACIÓN
        ivRestauracionBA = (LottieAnimationView) findViewById(R.id.ivRestauracionBA);
        ivRestauracionBA.setAnimation("cat_r_restauracion.json");
        ivRestauracionBA.loop(false);
        ivRestauracionBA.pauseAnimation();
        ivRestauracionBA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickrestauracion(view);
                ivRestauracionBA.playAnimation();
            }
        });

        //SALUD
        ivSaludBA = (LottieAnimationView) findViewById(R.id.ivSaludBA);
        ivSaludBA.setAnimation("cat_sa_salud.json");
        ivSaludBA.loop(false);
        ivSaludBA.pauseAnimation();
        ivSaludBA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicksalud(view);
                ivSaludBA.playAnimation();
            }
        });

        //SEGURIDAD
        ivSeguridadBA = (LottieAnimationView) findViewById(R.id.ivSeguridadBA);
        ivSeguridadBA.setAnimation("cat_seg_seguridad.json");
        ivSeguridadBA.loop(false);
        ivSeguridadBA.pauseAnimation();
        ivSeguridadBA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickseguridad(view);
                ivSeguridadBA.playAnimation();
            }
        });

        //SERVICIO
        ivServiciosBA = (LottieAnimationView) findViewById(R.id.ivServiciosBA);
        ivServiciosBA.setAnimation("cat_se_servicios.json");
        ivServiciosBA.loop(false);
        ivServiciosBA.pauseAnimation();
        ivServiciosBA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickservicios(view);
                ivServiciosBA.playAnimation();
            }
        });

        //TRANSPORTE
        ivTransporteBA = (LottieAnimationView) findViewById(R.id.ivTransporteBA);
        ivTransporteBA.setAnimation("cat_t_transporte.json");
        ivTransporteBA.loop(false);
        ivTransporteBA.pauseAnimation();
        ivTransporteBA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicktransporte(view);
                ivTransporteBA.playAnimation();
            }
        });

        //TODO GRID VIEW COMENTADO POR POSIBLE CAMBIO
        //CATEGORIA MENU
        /*ArrayList<ZCategoria> menu = new ArrayList<ZCategoria>();

        menu.add(new ZCategoria(getString(R.string.alimentacion), "alimentacion", R.drawable.ic_cat_a_alimentacion));
        menu.add(new ZCategoria(getString(R.string.asociaciones), "asociaciones", R.drawable.ic_cat_asociaciones));
        menu.add(new ZCategoria(getString(R.string.compras), "compras", R.drawable.ic_cat_c_compras));
        menu.add(new ZCategoria(getString(R.string.deporte), "deporte", R.drawable.ic_cat_d_deporte));
        menu.add(new ZCategoria(getString(R.string.educacion), "educacion", R.drawable.ic_cat_e_educacion));
        menu.add(new ZCategoria(getString(R.string.hoteles), "hoteles", R.drawable.ic_cat_h_hoteles));
        menu.add(new ZCategoria(getString(R.string.instituciones), "instituciones", R.drawable.ic_cat_i_instituciones));
        menu.add(new ZCategoria(getString(R.string.inmobiliaria), "inmobiliarias", R.drawable.ic_cat_inmobiliaria));
        menu.add(new ZCategoria(getString(R.string.monumentos), "monumentos", R.drawable.ic_cat_m_monumentos));
        menu.add(new ZCategoria(getString(R.string.ocio), "ocio", R.drawable.ic_cat_o_ocio));
        menu.add(new ZCategoria(getString(R.string.parque), "parque", R.drawable.ic_cat_pa_parque));
        menu.add(new ZCategoria(getString(R.string.playa), "playa", R.drawable.ic_cat_pl_playa));
        menu.add(new ZCategoria(getString(R.string.restauracion), "restauracion", R.drawable.ic_cat_r_restauracion));
        menu.add(new ZCategoria(getString(R.string.salud), "salud", R.drawable.ic_cat_sa_salud));
        menu.add(new ZCategoria(getString(R.string.servicios), "servicios", R.drawable.ic_cat_se_servicios));
        menu.add(new ZCategoria(getString(R.string.seguridad), "seguridad", R.drawable.ic_cat_seg_seguridad));
        menu.add(new ZCategoria(getString(R.string.transporte), "transporte", R.drawable.ic_cat_t_transporte));

        listBA = (GridView) findViewById(R.id.listBA);
        tvCategoria = (TextView) findViewById(R.id.tvCategoria);

        listBA.setAdapter(new ZAdaptadorCategoria(this, menu));

        //AL HACER CLICK
        listBA.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //DESCARGAR ELEMENTOS QUE NECESITO DE FIREBASE

                //RECOGER NOMBRE DE LA BASE
                ZCategoria catanuncio = ((ZCategoria) parent.getItemAtPosition(position));
                Toast.makeText(context, "la categoria es: "+catanuncio.getNombreinterno().toString(), Toast.LENGTH_SHORT).show();

                        //ETIQUETA + INDICAR A QUE MAINACTIVITY VA A IR
                        Intent i = new Intent(getApplicationContext(), CACategoriaActivity.class);
                        i.putExtra("EXTRA_CATEGORIA", catanuncio.getNombreinterno().toString());

                        //INICIAR ACTIVITY
                        startActivity(i);

            }
        });*/

    }//FIN ONCREATE

    //CLICK CATEGORIAS

    public  void clickalientacion(View v){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent mainIntent = new Intent().setClass(
                        BAMenuActivity.this, CACategoriaActivity.class);
                String categoria = tvAlimentacionocultoBA.getText().toString();
                    mainIntent.putExtra("EXTRA_CATEGORIA", categoria);
                    mainIntent.putExtra("EXTRA_IDIOMA", tvidiomaba.getText().toString());
                startActivity(mainIntent);
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, TIEMPO_BOTON);
    }
    public  void clickasociaciones(View v){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent mainIntent = new Intent().setClass(
                        BAMenuActivity.this, CACategoriaActivity.class);
                String categoria = tvAsocioacionesocultoBA.getText().toString();
                mainIntent.putExtra("EXTRA_CATEGORIA", categoria);
                mainIntent.putExtra("EXTRA_IDIOMA", tvidiomaba.getText().toString());
                startActivity(mainIntent);
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, TIEMPO_BOTON);
    }
    public  void clickcompras(View v){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent mainIntent = new Intent().setClass(
                        BAMenuActivity.this, CACategoriaActivity.class);
                String categoria = tvComprasocultoBA.getText().toString();
                mainIntent.putExtra("EXTRA_CATEGORIA", categoria);
                mainIntent.putExtra("EXTRA_IDIOMA", tvidiomaba.getText().toString());
                startActivity(mainIntent);
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, TIEMPO_BOTON);
    }
    public  void clickdeportes(View v){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent mainIntent = new Intent().setClass(
                        BAMenuActivity.this, CACategoriaActivity.class);
                String categoria = tvDeporteocultoBA.getText().toString();
                mainIntent.putExtra("EXTRA_CATEGORIA", categoria);
                mainIntent.putExtra("EXTRA_IDIOMA", tvidiomaba.getText().toString());
                startActivity(mainIntent);
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, TIEMPO_BOTON);
    }
    public  void clickeducacion(View v){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent mainIntent = new Intent().setClass(
                        BAMenuActivity.this, CACategoriaActivity.class);
                String categoria = tvEducacionocultoBA.getText().toString();
                mainIntent.putExtra("EXTRA_CATEGORIA", categoria);
                mainIntent.putExtra("EXTRA_IDIOMA", tvidiomaba.getText().toString());
                startActivity(mainIntent);
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, TIEMPO_BOTON);
    }
    public  void clickhoteles(View v){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent mainIntent = new Intent().setClass(
                        BAMenuActivity.this, CACategoriaActivity.class);
                String categoria = tvHotelesocultoBA.getText().toString();
                mainIntent.putExtra("EXTRA_CATEGORIA", categoria);
                mainIntent.putExtra("EXTRA_IDIOMA", tvidiomaba.getText().toString());
                startActivity(mainIntent);
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, TIEMPO_BOTON);
    }
    public  void clickinmoviliaria(View v){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent mainIntent = new Intent().setClass(
                        BAMenuActivity.this, CACategoriaActivity.class);
                String categoria = tvInmobiliariaocultaBA.getText().toString();
                mainIntent.putExtra("EXTRA_CATEGORIA", categoria);
                mainIntent.putExtra("EXTRA_IDIOMA", tvidiomaba.getText().toString());
                startActivity(mainIntent);
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, TIEMPO_BOTON);
    }
    public  void clickinstituciones(View v){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent mainIntent = new Intent().setClass(
                        BAMenuActivity.this, CACategoriaActivity.class);
                String categoria = tvInstitucionesocultoBA.getText().toString();
                mainIntent.putExtra("EXTRA_CATEGORIA", categoria);
                mainIntent.putExtra("EXTRA_IDIOMA", tvidiomaba.getText().toString());
                startActivity(mainIntent);
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, TIEMPO_BOTON);
    }
    public  void clickmonumentos(View v){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent mainIntent = new Intent().setClass(
                        BAMenuActivity.this, CACategoriaActivity.class);
                String categoria = tvMonumentosocultoBA.getText().toString();
                mainIntent.putExtra("EXTRA_CATEGORIA", categoria);
                mainIntent.putExtra("EXTRA_IDIOMA", tvidiomaba.getText().toString());
                startActivity(mainIntent);
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, TIEMPO_BOTON);
    }
    public  void clickocio(View v){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent mainIntent = new Intent().setClass(
                        BAMenuActivity.this, CACategoriaActivity.class);
                String categoria = tvOcioocultoBA.getText().toString();
                mainIntent.putExtra("EXTRA_CATEGORIA", categoria);
                mainIntent.putExtra("EXTRA_IDIOMA", tvidiomaba.getText().toString());
                startActivity(mainIntent);
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, TIEMPO_BOTON);
    }
    public  void clickparque(View v){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent mainIntent = new Intent().setClass(
                        BAMenuActivity.this, CACategoriaActivity.class);
                String categoria = tvParqueocultoBA.getText().toString();
                mainIntent.putExtra("EXTRA_CATEGORIA", categoria);
                mainIntent.putExtra("EXTRA_IDIOMA", tvidiomaba.getText().toString());
                startActivity(mainIntent);
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, TIEMPO_BOTON);
    }
    public  void clickplaya(View v){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent mainIntent = new Intent().setClass(
                        BAMenuActivity.this, CACategoriaActivity.class);
                String categoria = tvPlayaocultoBA.getText().toString();
                mainIntent.putExtra("EXTRA_CATEGORIA", categoria);
                mainIntent.putExtra("EXTRA_IDIOMA", tvidiomaba.getText().toString());
                startActivity(mainIntent);
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, TIEMPO_BOTON);
    }
    public  void clickrestauracion(View v){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent mainIntent = new Intent().setClass(
                        BAMenuActivity.this, CACategoriaActivity.class);
                String categoria = tvRestauracionocultoBA.getText().toString();
                mainIntent.putExtra("EXTRA_CATEGORIA", categoria);
                mainIntent.putExtra("EXTRA_IDIOMA", tvidiomaba.getText().toString());
                startActivity(mainIntent);
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, TIEMPO_BOTON);
    }
    public  void clicksalud(View v){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent mainIntent = new Intent().setClass(
                        BAMenuActivity.this, CACategoriaActivity.class);
                String categoria = tvSaludocultoBA.getText().toString();
                mainIntent.putExtra("EXTRA_CATEGORIA", categoria);
                mainIntent.putExtra("EXTRA_IDIOMA", tvidiomaba.getText().toString());
                startActivity(mainIntent);
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, TIEMPO_BOTON);
    }
    public  void clickseguridad(View v){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent mainIntent = new Intent().setClass(
                        BAMenuActivity.this, CACategoriaActivity.class);
                String categoria = tvSeguridadocultoBA.getText().toString();
                mainIntent.putExtra("EXTRA_CATEGORIA", categoria);
                mainIntent.putExtra("EXTRA_IDIOMA", tvidiomaba.getText().toString());
                startActivity(mainIntent);
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, TIEMPO_BOTON);
    }
    public  void clickservicios(View v){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent mainIntent = new Intent().setClass(
                        BAMenuActivity.this, CACategoriaActivity.class);
                String categoria = tvServiciosocultoBA.getText().toString();
                mainIntent.putExtra("EXTRA_CATEGORIA", categoria);
                mainIntent.putExtra("EXTRA_IDIOMA", tvidiomaba.getText().toString());
                startActivity(mainIntent);
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, TIEMPO_BOTON);
    }
    public  void clicktransporte(View v){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent mainIntent = new Intent().setClass(
                        BAMenuActivity.this, CACategoriaActivity.class);
                String categoria = tvTransporteocultoBA.getText().toString();
                mainIntent.putExtra("EXTRA_CATEGORIA", categoria);
                mainIntent.putExtra("EXTRA_IDIOMA", tvidiomaba.getText().toString());
                startActivity(mainIntent);
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, TIEMPO_BOTON);
    }

    //BOTONES FLOATMENU

    public void clickSpainBA(View v) {

        Locale locale = new Locale("es");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        Toast.makeText(this, getResources().getString(R.string.spaintoast), Toast.LENGTH_SHORT).show();


            //PARA QUE SE CIERRE AL PULSAR
        menu_fabBA.collapse();
    }

    public void clickUkBA(View v) {

        Locale locale = new Locale("en");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        Toast.makeText(this, getResources().getString(R.string.englishtoast), Toast.LENGTH_SHORT).show();

        //PARA QUE SE CIERRE AL PULSAR
        menu_fabBA.collapse();

        Intent mainIntent = new Intent().setClass(
                BAMenuActivity.this, BAMenuActivity.class);
        startActivity(mainIntent);

    }

    public void clickFranceBA(View v) {

        Locale locale = new Locale("fr");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        Toast.makeText(this, getResources().getString(R.string.frenchtoast), Toast.LENGTH_SHORT).show();

        //PARA QUE SE CIERRE AL PULSAR
        menu_fabBA.collapse();
    }

    public void clickGermanBA(View v) {

        Locale locale = new Locale("de");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        Toast.makeText(this, getResources().getString(R.string.germantoast), Toast.LENGTH_SHORT).show();

        //PARA QUE SE CIERRE AL PULSAR
        menu_fabBA.collapse();
    }

    public void clickMapaBA(View v) {
        Intent mainIntent = new Intent().setClass(
                BAMenuActivity.this, BBMapaActivity.class);
        startActivity(mainIntent);
        //PARA QUE SE CIERRE AL PULSAR
        menu_fabBA.collapse();
    }

    public void clickInfoBA(View v) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_info);

        TextView tvInfoApp = (TextView) dialog.findViewById(R.id.tvInfoApp);
        //TextView volvermenu = (TextView) dialog.findViewById(R.id.tvFooterDialogBA);
        Button volvermenu = (Button) dialog.findViewById(R.id.volverBotonDialog);

        tvInfoApp.setText(Html.fromHtml(getString(R.string.infomensaje)));


        volvermenu.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
        dialog.show();

        //PARA QUE SE CIERRE AL PULSAR
        menu_fabBA.collapse();
    }

    //BOTONE BUSCAR
    public void clickBuscarBA(View v) {

        //PARA QUE SE CIERRE AL PULSAR
        menu_fabBA.collapse();
    }

    public void onBackPressed() {
        //super.onBackPressed();
        new AlertDialog.Builder(this)
                .setTitle("Salir de la aplicación")
                .setMessage("¿Seguro que desea salir de la aplicación?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO accion de salir de la app
                        finishAffinity();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO accion de quedarse en el menu
                    }
                })
                .create().show();
    }


}
