package com.fulgen.paginasroteas;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Html;
import android.text.Layout;
import android.util.Log;
import android.view.KeyEvent;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
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

    private static final String TAGLOG = "BAMenuActivity";


    //VARIANTES DE DECLARADAS
    //GridView listBA;
    TextView tvAlimentacionocultoBA, tvAsocioacionesocultoBA, tvComprasocultoBA, tvDeporteocultoBA, tvEducacionocultoBA, tvHotelesocultoBA, tvInmobiliariaocultaBA, tvInstitucionesocultoBA,
            tvMonumentosocultoBA, tvOcioocultoBA, tvParqueocultoBA, tvPlayaocultoBA, tvRestauracionocultoBA, tvSaludocultoBA, tvServiciosocultoBA, tvSeguridadocultoBA, tvTransporteocultoBA, tvVacioBA,
            tvidiomaba, tvAlimentacionocultoicoBA,
            tvWebDialogg, tvImagDialogg, tvVisibleDialogg;

    ListView listBA;
    ScrollView scrollBA;

    LottieAnimationView ivAlimentacionBA, ivAsociacionesBA, ivComprasBA, ivDeporteBA, ivEducacionBA, ivHotelesBA,
            ivInmobiliariaBA, ivInstitucionesBA, ivMonumentosBA, ivOcioBA, ivParqueBA, ivPlayaBA, ivRestauracionBA,
            ivSaludBA, ivSeguridadBA, ivServiciosBA, ivTransporteBA, ivMapaBA;

    //ADAPTADOR
    //ArrayList<ZCategoria> lista_anuncios = new ArrayList<ZCategoria>();

    //VARIANTES DE DECLARADAS
    private FloatingActionsMenu menu_fabBA;
    EditText etfootbuscarBA;
    final Context context = this;

    //ADAPTADOR
    ArrayList<ZAnuncio> lista_anunciosBA = new ArrayList<ZAnuncio>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bamenu);

        //TOAST SI HAY INTERNET
        if (!compruebaConexion(this)) {
            Toast.makeText(getBaseContext(),"yo te voy avisando, pero NO TIENES conexión a INTERNET y puede que no se te cargue todo ⛔", Toast.LENGTH_LONG).show();
        }

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

        tvWebDialogg = (TextView)findViewById(R.id.tvWebDialogg);
        tvImagDialogg = (TextView)findViewById(R.id.tvImagDialogg);
        tvVisibleDialogg = (TextView)findViewById(R.id.tvVisibleDialogg);

        tvAlimentacionocultoicoBA= (TextView)findViewById(R.id.tvAlimentacionocultoicoBA);
        listBA = (ListView)findViewById(R.id.listBA);
        scrollBA = (ScrollView)findViewById(R.id.scrollBA);
        etfootbuscarBA = (EditText)findViewById(R.id.etfootbuscarBA);
        //COSA DE PULSAR EL ENTER EN EL TECLADO
        etfootbuscarBA.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    //aqui iria tu codigo al presionar el boton enter
                    buscartodo();
                    return true;
                }
                return false;
            }
        });

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

        //MAPA
        ivMapaBA = (LottieAnimationView) findViewById(R.id.ivMapaBA);
        ivMapaBA.setAnimation("cat_z_mapa.json");
        ivMapaBA.loop(false);
        ivMapaBA.pauseAnimation();
        ivMapaBA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickmapamenu(view);
                ivMapaBA.playAnimation();
            }
        });

        //CARGA DE ANUNCIOPOST (SI HAY)

        //ACCEDE AL ARBOL
        dbAnuncio = FirebaseDatabase.getInstance().getReference().child("aanuncio");

        //ESTO ES PARA QUE QUEDE ARCHIVOS FIREBASE EN LOCAL
        dbAnuncio.keepSynced(true);
        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //REVISA LAS VARIABLES
                tvVisibleDialogg.setText(dataSnapshot.child("visible").getValue().toString());
                tvWebDialogg.setText(dataSnapshot.child("url").getValue().toString());
                tvImagDialogg.setText(dataSnapshot.child("imagen").getValue().toString());


                //SI SE PONE ANUNCIOPOST

                String comprobacionanunciosplash = getIntent().getStringExtra("EXTRA_ANUNCIOSPLASH");
                if (comprobacionanunciosplash.equals("si")) {


                    if (tvVisibleDialogg.getText().toString().equals("si")) {

                        //ABRE EL DIALOGO ANUNCIO
                        final Dialog dialog = new Dialog(context);
                        dialog.setContentView(R.layout.dialog_anuncio);

                        //VARIABLES DEL DIALOGO
                        ImageView imagAnuncioDialog = (ImageView) dialog.findViewById(R.id.imagAnuncioDialog);
                        Button cerrarDialog = (Button) dialog.findViewById(R.id.cerrarDialog);

                        final String url = tvWebDialogg.getText().toString();

                        //CARGA DE LA IMAGEN DEL ANUNCIO
                        String anunciopostimag = tvImagDialogg.getText().toString();
                        Glide
                                .with(getApplicationContext())
                                .load(anunciopostimag)
                                .into(imagAnuncioDialog);

                        //CLICK PARA IR A LA URL
                        imagAnuncioDialog.setOnClickListener(
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Uri uri = Uri.parse(url);
                                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                        startActivity(intent);

                                        dialog.dismiss();
                                        onStop();
                                        //onPause();
                                        //onDestroy();
                                    }
                                });

                        //CERRAR DIALOGO
                        cerrarDialog.setOnClickListener(
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                        onStop();
                                        // onPause();
                                        //onDestroy();

                                    }
                                });
                        dialog.show();

                    }

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAGLOG, "Error!", databaseError.toException());
            }
        };

        dbAnuncio.addValueEventListener(eventListener);



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
                mainIntent.putExtra("EXTRA_ICO",tvAlimentacionocultoicoBA.getText().toString());
                mainIntent.putExtra("EXTRA_ICO", "ic_cat_a_alimentacion");
                startActivity(mainIntent);
                //TRANSICIÓN LATERAL PARA ADELANTE
                overridePendingTransition(R.anim.arrastrar_derecha_in, R.anim.arrastrar_derecha_out);
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
                mainIntent.putExtra("EXTRA_ICO", "ic_cat_asociaciones");
                startActivity(mainIntent);
                //TRANSICIÓN LATERAL PARA ADELANTE
                overridePendingTransition(R.anim.arrastrar_derecha_in, R.anim.arrastrar_derecha_out);
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
                mainIntent.putExtra("EXTRA_ICO", "ic_cat_c_compras");
                startActivity(mainIntent);
                //TRANSICIÓN LATERAL PARA ADELANTE
                overridePendingTransition(R.anim.arrastrar_derecha_in, R.anim.arrastrar_derecha_out);
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
                mainIntent.putExtra("EXTRA_ICO", "ic_cat_d_deporte");
                startActivity(mainIntent);
                //TRANSICIÓN LATERAL PARA ADELANTE
                overridePendingTransition(R.anim.arrastrar_derecha_in, R.anim.arrastrar_derecha_out);
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
                mainIntent.putExtra("EXTRA_ICO", "ic_cat_e_educacion");
                startActivity(mainIntent);
                //TRANSICIÓN LATERAL PARA ADELANTE
                overridePendingTransition(R.anim.arrastrar_derecha_in, R.anim.arrastrar_derecha_out);
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
                mainIntent.putExtra("EXTRA_ICO", "ic_cat_h_hoteles");
                startActivity(mainIntent);
                //TRANSICIÓN LATERAL PARA ADELANTE
                overridePendingTransition(R.anim.arrastrar_derecha_in, R.anim.arrastrar_derecha_out);
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
                mainIntent.putExtra("EXTRA_ICO", "ic_cat_inmoviliaria");
                startActivity(mainIntent);
                //TRANSICIÓN LATERAL PARA ADELANTE
                overridePendingTransition(R.anim.arrastrar_derecha_in, R.anim.arrastrar_derecha_out);
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
                mainIntent.putExtra("EXTRA_ICO", "ic_cat_i_instituciones");
                startActivity(mainIntent);
                //TRANSICIÓN LATERAL PARA ADELANTE
                overridePendingTransition(R.anim.arrastrar_derecha_in, R.anim.arrastrar_derecha_out);
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
                mainIntent.putExtra("EXTRA_ICO", "ic_cat_m_monumentos");
                startActivity(mainIntent);
                //TRANSICIÓN LATERAL PARA ADELANTE
                overridePendingTransition(R.anim.arrastrar_derecha_in, R.anim.arrastrar_derecha_out);
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
                mainIntent.putExtra("EXTRA_ICO", "ic_cat_o_ocio");
                startActivity(mainIntent);
                //TRANSICIÓN LATERAL PARA ADELANTE
                overridePendingTransition(R.anim.arrastrar_derecha_in, R.anim.arrastrar_derecha_out);
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
                mainIntent.putExtra("EXTRA_ICO", "ic_cat_pa_parque");
                startActivity(mainIntent);
                //TRANSICIÓN LATERAL PARA ADELANTE
                overridePendingTransition(R.anim.arrastrar_derecha_in, R.anim.arrastrar_derecha_out);
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
                mainIntent.putExtra("EXTRA_ICO", "ic_cat_pl_playa");
                startActivity(mainIntent);
                //TRANSICIÓN LATERAL PARA ADELANTE
                overridePendingTransition(R.anim.arrastrar_derecha_in, R.anim.arrastrar_derecha_out);
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
                mainIntent.putExtra("EXTRA_ICO", "ic_cat_r_restauracion");
                startActivity(mainIntent);
                //TRANSICIÓN LATERAL PARA ADELANTE
                overridePendingTransition(R.anim.arrastrar_derecha_in, R.anim.arrastrar_derecha_out);
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
                mainIntent.putExtra("EXTRA_ICO", "ic_cat_sa_salud");
                startActivity(mainIntent);
                //TRANSICIÓN LATERAL PARA ADELANTE
                overridePendingTransition(R.anim.arrastrar_derecha_in, R.anim.arrastrar_derecha_out);
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
                mainIntent.putExtra("EXTRA_ICO", "ic_cat_seg_seguridad");
                startActivity(mainIntent);
                //TRANSICIÓN LATERAL PARA ADELANTE
                overridePendingTransition(R.anim.arrastrar_derecha_in, R.anim.arrastrar_derecha_out);
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
                mainIntent.putExtra("EXTRA_ICO", "ic_cat_se_servicios");
                startActivity(mainIntent);
                //TRANSICIÓN LATERAL PARA ADELANTE
                overridePendingTransition(R.anim.arrastrar_derecha_in, R.anim.arrastrar_derecha_out);
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
                mainIntent.putExtra("EXTRA_ICO", "ic_cat_t_transporte");
                startActivity(mainIntent);
                //TRANSICIÓN LATERAL PARA ADELANTE
                overridePendingTransition(R.anim.arrastrar_derecha_in, R.anim.arrastrar_derecha_out);
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, TIEMPO_BOTON);
    }
    public  void clickmapamenu(View v){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent mainIntent = new Intent().setClass(
                        BAMenuActivity.this, BBMapaActivity.class);
                mainIntent.putExtra("EXTRA_PERMANENTE","todo");
                mainIntent.putExtra("EXTRA_FILTRO","todo");
                startActivity(mainIntent);
                //TRANSICIÓN LATERAL PARA ADELANTE
                overridePendingTransition(R.anim.arrastrar_derecha_in, R.anim.arrastrar_derecha_out);
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, TIEMPO_BOTON);
    }



    //BUSCADORES

    private void cargardatosalimentacion(){
        dbAnuncio = FirebaseDatabase.getInstance().getReference().child("alimentacion");
        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot anuncioDataSnapShot : dataSnapshot.getChildren()) {
                    cargarListViewalimentacion(anuncioDataSnapShot);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("CACategoriaActivity", "DATABASE ERROR");
            }
        };
        dbAnuncio.addListenerForSingleValueEvent(eventListener);
    }
    private void cargarListViewalimentacion(DataSnapshot dataSnapshot) {
        ZAnuncio anun =dataSnapshot.getValue(ZAnuncio.class);
        String busqueda=etfootbuscarBA.getText().toString();
        String mayusBusqueda = busqueda.substring(0,1).toUpperCase()+busqueda.substring(1);
        if(anun.getCategoria().contains(etfootbuscarBA.getText().toString()) || anun.getNombre().contains(etfootbuscarBA.getText().toString()) || anun.getSubcategoria().contains(etfootbuscarBA.getText().toString()) || anun.getDireccion().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortaes().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargaes().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortaen().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargaen().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortade().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargade().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortafr().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargafr().contains(etfootbuscarBA.getText().toString())
                || anun.getCategoria().contains(mayusBusqueda) || anun.getNombre().contains(mayusBusqueda) || anun.getSubcategoria().contains(mayusBusqueda) || anun.getDireccion().contains(mayusBusqueda)
                || anun.getDescripcioncortaes().contains(mayusBusqueda) || anun.getDescripcionlargaes().contains(mayusBusqueda)
                || anun.getDescripcioncortaen().contains(mayusBusqueda) || anun.getDescripcionlargaen().contains(mayusBusqueda)
                || anun.getDescripcioncortade().contains(mayusBusqueda) || anun.getDescripcionlargade().contains(mayusBusqueda)
                || anun.getDescripcioncortafr().contains(mayusBusqueda) || anun.getDescripcionlargafr().contains(mayusBusqueda)) {
            //ENLAZAR DATOS FIREBASE
            lista_anunciosBA.add(dataSnapshot.getValue(ZAnuncio.class));
            //ADAPTADOR
            ZAdaptadorAnuncio adaptador = new ZAdaptadorAnuncio(this, lista_anunciosBA);
            listBA.setAdapter(adaptador);
            //CREAR EL CLICK CORTO EN LA LISTVIEW (QUE VA A IR A OTRA MAINACTIVITY)
            listBA.setOnItemClickListener
                    (new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //REFERENCIA A LA CLASE
                            ZAnuncio a = ((ZAnuncio) parent.getItemAtPosition(position));
                            ZAnuncio anuncioenviado = new ZAnuncio(a.getImagen(), a.getNombre(), a.getDescripcionlargaes(),
                                    a.getDescripcionlargade(), a.getDescripcionlargaen(), a.getDescripcionlargafr(), a.getDescuentoes(),
                                    a.getDescuentode(), a.getDescuentoen(), a.getDescuentofr(), a.getFacebook(), a.getTwitter(),
                                    a.getTelefono(), a.getMail(), a.getMaps(), a.getExtra(), a.getDescripcioncortaes(), a.getDescripcioncortade(),
                                    a.getDescripcioncortaen(), a.getDescripcioncortafr(), a.getHorarioes(), a.getHorariode(), a.getHorarioen(),
                                    a.getHorariofr(), a.getDireccion(), a.getCategoria(), a.getSubcategoria(), a.getLatitud(), a.getLongitud());
                            String idioma = tvidiomaba.getText().toString();
                            Intent i = new Intent(getApplicationContext(), DAEmpresaActivity.class);
                            i.putExtra("EXTRA_ANUNCIO", anuncioenviado);
                            i.putExtra("EXTRA_IDIOMACAT", idioma);
                            i.putExtra("EXTRA_CATEGORIA", a.getCategoria());
                            startActivity(i);
                        }
                    });
        }
    }
    private void cargardatosasociacion(){
        dbAnuncio = FirebaseDatabase.getInstance().getReference().child("asociacion");
        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot anuncioDataSnapShot : dataSnapshot.getChildren()) {
                    cargarListViewasociacion(anuncioDataSnapShot);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("CACategoriaActivity", "DATABASE ERROR");
            }
        };
        dbAnuncio.addListenerForSingleValueEvent(eventListener);
    }
    private void cargarListViewasociacion(DataSnapshot dataSnapshot) {
        ZAnuncio anun =dataSnapshot.getValue(ZAnuncio.class);
        String busqueda=etfootbuscarBA.getText().toString();
        String mayusBusqueda = busqueda.substring(0,1).toUpperCase()+busqueda.substring(1);
        if(anun.getCategoria().contains(etfootbuscarBA.getText().toString()) || anun.getNombre().contains(etfootbuscarBA.getText().toString()) || anun.getSubcategoria().contains(etfootbuscarBA.getText().toString()) || anun.getDireccion().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortaes().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargaes().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortaen().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargaen().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortade().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargade().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortafr().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargafr().contains(etfootbuscarBA.getText().toString())
                || anun.getCategoria().contains(mayusBusqueda) || anun.getNombre().contains(mayusBusqueda) || anun.getSubcategoria().contains(mayusBusqueda) || anun.getDireccion().contains(mayusBusqueda)
                || anun.getDescripcioncortaes().contains(mayusBusqueda) || anun.getDescripcionlargaes().contains(mayusBusqueda)
                || anun.getDescripcioncortaen().contains(mayusBusqueda) || anun.getDescripcionlargaen().contains(mayusBusqueda)
                || anun.getDescripcioncortade().contains(mayusBusqueda) || anun.getDescripcionlargade().contains(mayusBusqueda)
                || anun.getDescripcioncortafr().contains(mayusBusqueda) || anun.getDescripcionlargafr().contains(mayusBusqueda)) {
            //ENLAZAR DATOS FIREBASE
            lista_anunciosBA.add(dataSnapshot.getValue(ZAnuncio.class));
            //ADAPTADOR
            ZAdaptadorAnuncio adaptador = new ZAdaptadorAnuncio(this, lista_anunciosBA);
            listBA.setAdapter(adaptador);
            //CREAR EL CLICK CORTO EN LA LISTVIEW (QUE VA A IR A OTRA MAINACTIVITY)
            listBA.setOnItemClickListener
                    (new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //REFERENCIA A LA CLASE
                            ZAnuncio a = ((ZAnuncio) parent.getItemAtPosition(position));
                            ZAnuncio anuncioenviado = new ZAnuncio(a.getImagen(), a.getNombre(), a.getDescripcionlargaes(),
                                    a.getDescripcionlargade(), a.getDescripcionlargaen(), a.getDescripcionlargafr(), a.getDescuentoes(),
                                    a.getDescuentode(), a.getDescuentoen(), a.getDescuentofr(), a.getFacebook(), a.getTwitter(),
                                    a.getTelefono(), a.getMail(), a.getMaps(), a.getExtra(), a.getDescripcioncortaes(), a.getDescripcioncortade(),
                                    a.getDescripcioncortaen(), a.getDescripcioncortafr(), a.getHorarioes(), a.getHorariode(), a.getHorarioen(),
                                    a.getHorariofr(), a.getDireccion(), a.getCategoria(), a.getSubcategoria(), a.getLatitud(), a.getLongitud());
                            String idioma = tvidiomaba.getText().toString();
                            Intent i = new Intent(getApplicationContext(), DAEmpresaActivity.class);
                            i.putExtra("EXTRA_ANUNCIO", anuncioenviado);
                            i.putExtra("EXTRA_IDIOMACAT", idioma);
                            i.putExtra("EXTRA_CATEGORIA", a.getCategoria());
                            startActivity(i);
                        }
                    });
        }
    }
    private void cargardatoscompras(){
        dbAnuncio = FirebaseDatabase.getInstance().getReference().child("compras");
        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot anuncioDataSnapShot : dataSnapshot.getChildren()) {
                    cargarListViewcompras(anuncioDataSnapShot);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("CACategoriaActivity", "DATABASE ERROR");
            }
        };
        dbAnuncio.addListenerForSingleValueEvent(eventListener);
    }
    private void cargarListViewcompras(DataSnapshot dataSnapshot) {
        ZAnuncio anun =dataSnapshot.getValue(ZAnuncio.class);
        String busqueda=etfootbuscarBA.getText().toString();
        String mayusBusqueda = busqueda.substring(0,1).toUpperCase()+busqueda.substring(1);
        if(anun.getCategoria().contains(etfootbuscarBA.getText().toString()) || anun.getNombre().contains(etfootbuscarBA.getText().toString()) || anun.getSubcategoria().contains(etfootbuscarBA.getText().toString()) || anun.getDireccion().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortaes().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargaes().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortaen().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargaen().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortade().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargade().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortafr().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargafr().contains(etfootbuscarBA.getText().toString())
                || anun.getCategoria().contains(mayusBusqueda) || anun.getNombre().contains(mayusBusqueda) || anun.getSubcategoria().contains(mayusBusqueda) || anun.getDireccion().contains(mayusBusqueda)
                || anun.getDescripcioncortaes().contains(mayusBusqueda) || anun.getDescripcionlargaes().contains(mayusBusqueda)
                || anun.getDescripcioncortaen().contains(mayusBusqueda) || anun.getDescripcionlargaen().contains(mayusBusqueda)
                || anun.getDescripcioncortade().contains(mayusBusqueda) || anun.getDescripcionlargade().contains(mayusBusqueda)
                || anun.getDescripcioncortafr().contains(mayusBusqueda) || anun.getDescripcionlargafr().contains(mayusBusqueda)) {
            //ENLAZAR DATOS FIREBASE
            lista_anunciosBA.add(dataSnapshot.getValue(ZAnuncio.class));
            //ADAPTADOR
            ZAdaptadorAnuncio adaptador = new ZAdaptadorAnuncio(this, lista_anunciosBA);
            listBA.setAdapter(adaptador);
            //CREAR EL CLICK CORTO EN LA LISTVIEW (QUE VA A IR A OTRA MAINACTIVITY)
            listBA.setOnItemClickListener
                    (new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //REFERENCIA A LA CLASE
                            ZAnuncio a = ((ZAnuncio) parent.getItemAtPosition(position));
                            ZAnuncio anuncioenviado = new ZAnuncio(a.getImagen(), a.getNombre(), a.getDescripcionlargaes(),
                                    a.getDescripcionlargade(), a.getDescripcionlargaen(), a.getDescripcionlargafr(), a.getDescuentoes(),
                                    a.getDescuentode(), a.getDescuentoen(), a.getDescuentofr(), a.getFacebook(), a.getTwitter(),
                                    a.getTelefono(), a.getMail(), a.getMaps(), a.getExtra(), a.getDescripcioncortaes(), a.getDescripcioncortade(),
                                    a.getDescripcioncortaen(), a.getDescripcioncortafr(), a.getHorarioes(), a.getHorariode(), a.getHorarioen(),
                                    a.getHorariofr(), a.getDireccion(), a.getCategoria(), a.getSubcategoria(), a.getLatitud(), a.getLongitud());
                            String idioma = tvidiomaba.getText().toString();
                            Intent i = new Intent(getApplicationContext(), DAEmpresaActivity.class);
                            i.putExtra("EXTRA_ANUNCIO", anuncioenviado);
                            i.putExtra("EXTRA_IDIOMACAT", idioma);
                            i.putExtra("EXTRA_CATEGORIA", a.getCategoria());
                            startActivity(i);
                        }
                    });
        }
    }
    private void cargardatosdeporte(){
        dbAnuncio = FirebaseDatabase.getInstance().getReference().child("deporte");
        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot anuncioDataSnapShot : dataSnapshot.getChildren()) {
                    cargarListViewdeporte(anuncioDataSnapShot);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("CACategoriaActivity", "DATABASE ERROR");
            }
        };
        dbAnuncio.addListenerForSingleValueEvent(eventListener);
    }
    private void cargarListViewdeporte(DataSnapshot dataSnapshot) {
        ZAnuncio anun =dataSnapshot.getValue(ZAnuncio.class);
        String busqueda=etfootbuscarBA.getText().toString();
        String mayusBusqueda = busqueda.substring(0,1).toUpperCase()+busqueda.substring(1);
        if(anun.getCategoria().contains(etfootbuscarBA.getText().toString()) || anun.getNombre().contains(etfootbuscarBA.getText().toString()) || anun.getSubcategoria().contains(etfootbuscarBA.getText().toString()) || anun.getDireccion().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortaes().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargaes().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortaen().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargaen().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortade().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargade().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortafr().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargafr().contains(etfootbuscarBA.getText().toString())
                || anun.getCategoria().contains(mayusBusqueda) || anun.getNombre().contains(mayusBusqueda) || anun.getSubcategoria().contains(mayusBusqueda) || anun.getDireccion().contains(mayusBusqueda)
                || anun.getDescripcioncortaes().contains(mayusBusqueda) || anun.getDescripcionlargaes().contains(mayusBusqueda)
                || anun.getDescripcioncortaen().contains(mayusBusqueda) || anun.getDescripcionlargaen().contains(mayusBusqueda)
                || anun.getDescripcioncortade().contains(mayusBusqueda) || anun.getDescripcionlargade().contains(mayusBusqueda)
                || anun.getDescripcioncortafr().contains(mayusBusqueda) || anun.getDescripcionlargafr().contains(mayusBusqueda)) {
            //ENLAZAR DATOS FIREBASE
            lista_anunciosBA.add(dataSnapshot.getValue(ZAnuncio.class));
            //ADAPTADOR
            ZAdaptadorAnuncio adaptador = new ZAdaptadorAnuncio(this, lista_anunciosBA);
            listBA.setAdapter(adaptador);
            //CREAR EL CLICK CORTO EN LA LISTVIEW (QUE VA A IR A OTRA MAINACTIVITY)
            listBA.setOnItemClickListener
                    (new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //REFERENCIA A LA CLASE
                            ZAnuncio a = ((ZAnuncio) parent.getItemAtPosition(position));
                            ZAnuncio anuncioenviado = new ZAnuncio(a.getImagen(), a.getNombre(), a.getDescripcionlargaes(),
                                    a.getDescripcionlargade(), a.getDescripcionlargaen(), a.getDescripcionlargafr(), a.getDescuentoes(),
                                    a.getDescuentode(), a.getDescuentoen(), a.getDescuentofr(), a.getFacebook(), a.getTwitter(),
                                    a.getTelefono(), a.getMail(), a.getMaps(), a.getExtra(), a.getDescripcioncortaes(), a.getDescripcioncortade(),
                                    a.getDescripcioncortaen(), a.getDescripcioncortafr(), a.getHorarioes(), a.getHorariode(), a.getHorarioen(),
                                    a.getHorariofr(), a.getDireccion(), a.getCategoria(), a.getSubcategoria(), a.getLatitud(), a.getLongitud());
                            String idioma = tvidiomaba.getText().toString();
                            Intent i = new Intent(getApplicationContext(), DAEmpresaActivity.class);
                            i.putExtra("EXTRA_ANUNCIO", anuncioenviado);
                            i.putExtra("EXTRA_IDIOMACAT", idioma);
                            i.putExtra("EXTRA_CATEGORIA", a.getCategoria());
                            startActivity(i);
                        }
                    });
        }
    }
    private void cargardatoseducacion(){
        dbAnuncio = FirebaseDatabase.getInstance().getReference().child("educacion");
        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot anuncioDataSnapShot : dataSnapshot.getChildren()) {
                    cargarListVieweducacion(anuncioDataSnapShot);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("CACategoriaActivity", "DATABASE ERROR");
            }
        };
        dbAnuncio.addListenerForSingleValueEvent(eventListener);
    }
    private void cargarListVieweducacion(DataSnapshot dataSnapshot) {
        ZAnuncio anun =dataSnapshot.getValue(ZAnuncio.class);
        String busqueda=etfootbuscarBA.getText().toString();
        String mayusBusqueda = busqueda.substring(0,1).toUpperCase()+busqueda.substring(1);
        if(anun.getCategoria().contains(etfootbuscarBA.getText().toString()) || anun.getNombre().contains(etfootbuscarBA.getText().toString()) || anun.getSubcategoria().contains(etfootbuscarBA.getText().toString()) || anun.getDireccion().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortaes().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargaes().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortaen().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargaen().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortade().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargade().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortafr().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargafr().contains(etfootbuscarBA.getText().toString())
                || anun.getCategoria().contains(mayusBusqueda) || anun.getNombre().contains(mayusBusqueda) || anun.getSubcategoria().contains(mayusBusqueda) || anun.getDireccion().contains(mayusBusqueda)
                || anun.getDescripcioncortaes().contains(mayusBusqueda) || anun.getDescripcionlargaes().contains(mayusBusqueda)
                || anun.getDescripcioncortaen().contains(mayusBusqueda) || anun.getDescripcionlargaen().contains(mayusBusqueda)
                || anun.getDescripcioncortade().contains(mayusBusqueda) || anun.getDescripcionlargade().contains(mayusBusqueda)
                || anun.getDescripcioncortafr().contains(mayusBusqueda) || anun.getDescripcionlargafr().contains(mayusBusqueda)) {
            //ENLAZAR DATOS FIREBASE
            lista_anunciosBA.add(dataSnapshot.getValue(ZAnuncio.class));
            //ADAPTADOR
            ZAdaptadorAnuncio adaptador = new ZAdaptadorAnuncio(this, lista_anunciosBA);
            listBA.setAdapter(adaptador);
            //CREAR EL CLICK CORTO EN LA LISTVIEW (QUE VA A IR A OTRA MAINACTIVITY)
            listBA.setOnItemClickListener
                    (new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //REFERENCIA A LA CLASE
                            ZAnuncio a = ((ZAnuncio) parent.getItemAtPosition(position));
                            ZAnuncio anuncioenviado = new ZAnuncio(a.getImagen(), a.getNombre(), a.getDescripcionlargaes(),
                                    a.getDescripcionlargade(), a.getDescripcionlargaen(), a.getDescripcionlargafr(), a.getDescuentoes(),
                                    a.getDescuentode(), a.getDescuentoen(), a.getDescuentofr(), a.getFacebook(), a.getTwitter(),
                                    a.getTelefono(), a.getMail(), a.getMaps(), a.getExtra(), a.getDescripcioncortaes(), a.getDescripcioncortade(),
                                    a.getDescripcioncortaen(), a.getDescripcioncortafr(), a.getHorarioes(), a.getHorariode(), a.getHorarioen(),
                                    a.getHorariofr(), a.getDireccion(), a.getCategoria(), a.getSubcategoria(), a.getLatitud(), a.getLongitud());
                            String idioma = tvidiomaba.getText().toString();
                            Intent i = new Intent(getApplicationContext(), DAEmpresaActivity.class);
                            i.putExtra("EXTRA_ANUNCIO", anuncioenviado);
                            i.putExtra("EXTRA_IDIOMACAT", idioma);
                            i.putExtra("EXTRA_CATEGORIA", a.getCategoria());
                            startActivity(i);
                        }
                    });
        }
    }
    private void cargardatoshoteles(){
        dbAnuncio = FirebaseDatabase.getInstance().getReference().child("hoteles");
        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot anuncioDataSnapShot : dataSnapshot.getChildren()) {
                    cargarListViewhoteles(anuncioDataSnapShot);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("CACategoriaActivity", "DATABASE ERROR");
            }
        };
        dbAnuncio.addListenerForSingleValueEvent(eventListener);
    }
    private void cargarListViewhoteles(DataSnapshot dataSnapshot) {
        ZAnuncio anun =dataSnapshot.getValue(ZAnuncio.class);
        String busqueda=etfootbuscarBA.getText().toString();
        String mayusBusqueda = busqueda.substring(0,1).toUpperCase()+busqueda.substring(1);
        if(anun.getCategoria().contains(etfootbuscarBA.getText().toString()) || anun.getNombre().contains(etfootbuscarBA.getText().toString()) || anun.getSubcategoria().contains(etfootbuscarBA.getText().toString()) || anun.getDireccion().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortaes().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargaes().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortaen().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargaen().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortade().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargade().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortafr().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargafr().contains(etfootbuscarBA.getText().toString())
                || anun.getCategoria().contains(mayusBusqueda) || anun.getNombre().contains(mayusBusqueda) || anun.getSubcategoria().contains(mayusBusqueda) || anun.getDireccion().contains(mayusBusqueda)
                || anun.getDescripcioncortaes().contains(mayusBusqueda) || anun.getDescripcionlargaes().contains(mayusBusqueda)
                || anun.getDescripcioncortaen().contains(mayusBusqueda) || anun.getDescripcionlargaen().contains(mayusBusqueda)
                || anun.getDescripcioncortade().contains(mayusBusqueda) || anun.getDescripcionlargade().contains(mayusBusqueda)
                || anun.getDescripcioncortafr().contains(mayusBusqueda) || anun.getDescripcionlargafr().contains(mayusBusqueda)) {
            //ENLAZAR DATOS FIREBASE
            lista_anunciosBA.add(dataSnapshot.getValue(ZAnuncio.class));
            //ADAPTADOR
            ZAdaptadorAnuncio adaptador = new ZAdaptadorAnuncio(this, lista_anunciosBA);
            listBA.setAdapter(adaptador);
            //CREAR EL CLICK CORTO EN LA LISTVIEW (QUE VA A IR A OTRA MAINACTIVITY)
            listBA.setOnItemClickListener
                    (new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //REFERENCIA A LA CLASE
                            ZAnuncio a = ((ZAnuncio) parent.getItemAtPosition(position));
                            ZAnuncio anuncioenviado = new ZAnuncio(a.getImagen(), a.getNombre(), a.getDescripcionlargaes(),
                                    a.getDescripcionlargade(), a.getDescripcionlargaen(), a.getDescripcionlargafr(), a.getDescuentoes(),
                                    a.getDescuentode(), a.getDescuentoen(), a.getDescuentofr(), a.getFacebook(), a.getTwitter(),
                                    a.getTelefono(), a.getMail(), a.getMaps(), a.getExtra(), a.getDescripcioncortaes(), a.getDescripcioncortade(),
                                    a.getDescripcioncortaen(), a.getDescripcioncortafr(), a.getHorarioes(), a.getHorariode(), a.getHorarioen(),
                                    a.getHorariofr(), a.getDireccion(), a.getCategoria(), a.getSubcategoria(), a.getLatitud(), a.getLongitud());
                            String idioma = tvidiomaba.getText().toString();
                            Intent i = new Intent(getApplicationContext(), DAEmpresaActivity.class);
                            i.putExtra("EXTRA_ANUNCIO", anuncioenviado);
                            i.putExtra("EXTRA_IDIOMACAT", idioma);
                            i.putExtra("EXTRA_CATEGORIA", a.getCategoria());
                            startActivity(i);
                        }
                    });
        }
    }
    private void cargardatosinmobiliaria(){
        dbAnuncio = FirebaseDatabase.getInstance().getReference().child("inmobiliaria");
        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot anuncioDataSnapShot : dataSnapshot.getChildren()) {
                    cargarListViewinmobiliaria(anuncioDataSnapShot);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("CACategoriaActivity", "DATABASE ERROR");
            }
        };
        dbAnuncio.addListenerForSingleValueEvent(eventListener);
    }
    private void cargarListViewinmobiliaria(DataSnapshot dataSnapshot) {
        ZAnuncio anun =dataSnapshot.getValue(ZAnuncio.class);
        String busqueda=etfootbuscarBA.getText().toString();
        String mayusBusqueda = busqueda.substring(0,1).toUpperCase()+busqueda.substring(1);
        if(anun.getCategoria().contains(etfootbuscarBA.getText().toString()) || anun.getNombre().contains(etfootbuscarBA.getText().toString()) || anun.getSubcategoria().contains(etfootbuscarBA.getText().toString()) || anun.getDireccion().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortaes().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargaes().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortaen().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargaen().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortade().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargade().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortafr().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargafr().contains(etfootbuscarBA.getText().toString())
                || anun.getCategoria().contains(mayusBusqueda) || anun.getNombre().contains(mayusBusqueda) || anun.getSubcategoria().contains(mayusBusqueda) || anun.getDireccion().contains(mayusBusqueda)
                || anun.getDescripcioncortaes().contains(mayusBusqueda) || anun.getDescripcionlargaes().contains(mayusBusqueda)
                || anun.getDescripcioncortaen().contains(mayusBusqueda) || anun.getDescripcionlargaen().contains(mayusBusqueda)
                || anun.getDescripcioncortade().contains(mayusBusqueda) || anun.getDescripcionlargade().contains(mayusBusqueda)
                || anun.getDescripcioncortafr().contains(mayusBusqueda) || anun.getDescripcionlargafr().contains(mayusBusqueda)) {
            //ENLAZAR DATOS FIREBASE
            lista_anunciosBA.add(dataSnapshot.getValue(ZAnuncio.class));
            //ADAPTADOR
            ZAdaptadorAnuncio adaptador = new ZAdaptadorAnuncio(this, lista_anunciosBA);
            listBA.setAdapter(adaptador);
            //CREAR EL CLICK CORTO EN LA LISTVIEW (QUE VA A IR A OTRA MAINACTIVITY)
            listBA.setOnItemClickListener
                    (new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //REFERENCIA A LA CLASE
                            ZAnuncio a = ((ZAnuncio) parent.getItemAtPosition(position));
                            ZAnuncio anuncioenviado = new ZAnuncio(a.getImagen(), a.getNombre(), a.getDescripcionlargaes(),
                                    a.getDescripcionlargade(), a.getDescripcionlargaen(), a.getDescripcionlargafr(), a.getDescuentoes(),
                                    a.getDescuentode(), a.getDescuentoen(), a.getDescuentofr(), a.getFacebook(), a.getTwitter(),
                                    a.getTelefono(), a.getMail(), a.getMaps(), a.getExtra(), a.getDescripcioncortaes(), a.getDescripcioncortade(),
                                    a.getDescripcioncortaen(), a.getDescripcioncortafr(), a.getHorarioes(), a.getHorariode(), a.getHorarioen(),
                                    a.getHorariofr(), a.getDireccion(), a.getCategoria(), a.getSubcategoria(), a.getLatitud(), a.getLongitud());
                            String idioma = tvidiomaba.getText().toString();
                            Intent i = new Intent(getApplicationContext(), DAEmpresaActivity.class);
                            i.putExtra("EXTRA_ANUNCIO", anuncioenviado);
                            i.putExtra("EXTRA_IDIOMACAT", idioma);
                            i.putExtra("EXTRA_CATEGORIA", a.getCategoria());
                            startActivity(i);
                        }
                    });
        }
    }
    private void cargardatosinstituciones(){
        dbAnuncio = FirebaseDatabase.getInstance().getReference().child("instituciones");
        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot anuncioDataSnapShot : dataSnapshot.getChildren()) {
                    cargarListViewinstituciones(anuncioDataSnapShot);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("CACategoriaActivity", "DATABASE ERROR");
            }
        };
        dbAnuncio.addListenerForSingleValueEvent(eventListener);
    }
    private void cargarListViewinstituciones(DataSnapshot dataSnapshot) {
        ZAnuncio anun =dataSnapshot.getValue(ZAnuncio.class);
        String busqueda=etfootbuscarBA.getText().toString();
        String mayusBusqueda = busqueda.substring(0,1).toUpperCase()+busqueda.substring(1);
        if(anun.getCategoria().contains(etfootbuscarBA.getText().toString()) || anun.getNombre().contains(etfootbuscarBA.getText().toString()) || anun.getSubcategoria().contains(etfootbuscarBA.getText().toString()) || anun.getDireccion().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortaes().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargaes().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortaen().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargaen().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortade().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargade().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortafr().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargafr().contains(etfootbuscarBA.getText().toString())
                || anun.getCategoria().contains(mayusBusqueda) || anun.getNombre().contains(mayusBusqueda) || anun.getSubcategoria().contains(mayusBusqueda) || anun.getDireccion().contains(mayusBusqueda)
                || anun.getDescripcioncortaes().contains(mayusBusqueda) || anun.getDescripcionlargaes().contains(mayusBusqueda)
                || anun.getDescripcioncortaen().contains(mayusBusqueda) || anun.getDescripcionlargaen().contains(mayusBusqueda)
                || anun.getDescripcioncortade().contains(mayusBusqueda) || anun.getDescripcionlargade().contains(mayusBusqueda)
                || anun.getDescripcioncortafr().contains(mayusBusqueda) || anun.getDescripcionlargafr().contains(mayusBusqueda)) {
            //ENLAZAR DATOS FIREBASE
            lista_anunciosBA.add(dataSnapshot.getValue(ZAnuncio.class));
            //ADAPTADOR
            ZAdaptadorAnuncio adaptador = new ZAdaptadorAnuncio(this, lista_anunciosBA);
            listBA.setAdapter(adaptador);
            //CREAR EL CLICK CORTO EN LA LISTVIEW (QUE VA A IR A OTRA MAINACTIVITY)
            listBA.setOnItemClickListener
                    (new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //REFERENCIA A LA CLASE
                            ZAnuncio a = ((ZAnuncio) parent.getItemAtPosition(position));
                            ZAnuncio anuncioenviado = new ZAnuncio(a.getImagen(), a.getNombre(), a.getDescripcionlargaes(),
                                    a.getDescripcionlargade(), a.getDescripcionlargaen(), a.getDescripcionlargafr(), a.getDescuentoes(),
                                    a.getDescuentode(), a.getDescuentoen(), a.getDescuentofr(), a.getFacebook(), a.getTwitter(),
                                    a.getTelefono(), a.getMail(), a.getMaps(), a.getExtra(), a.getDescripcioncortaes(), a.getDescripcioncortade(),
                                    a.getDescripcioncortaen(), a.getDescripcioncortafr(), a.getHorarioes(), a.getHorariode(), a.getHorarioen(),
                                    a.getHorariofr(), a.getDireccion(), a.getCategoria(), a.getSubcategoria(), a.getLatitud(), a.getLongitud());
                            String idioma = tvidiomaba.getText().toString();
                            Intent i = new Intent(getApplicationContext(), DAEmpresaActivity.class);
                            i.putExtra("EXTRA_ANUNCIO", anuncioenviado);
                            i.putExtra("EXTRA_IDIOMACAT", idioma);
                            i.putExtra("EXTRA_CATEGORIA", a.getCategoria());
                            startActivity(i);
                        }
                    });
        }
    }
    private void cargardatosmonumentos(){
        dbAnuncio = FirebaseDatabase.getInstance().getReference().child("monumentos");
        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot anuncioDataSnapShot : dataSnapshot.getChildren()) {
                    cargarListViewmonumentos(anuncioDataSnapShot);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("CACategoriaActivity", "DATABASE ERROR");
            }
        };
        dbAnuncio.addListenerForSingleValueEvent(eventListener);
    }
    private void cargarListViewmonumentos(DataSnapshot dataSnapshot) {
        ZAnuncio anun =dataSnapshot.getValue(ZAnuncio.class);
        String busqueda=etfootbuscarBA.getText().toString();
        String mayusBusqueda = busqueda.substring(0,1).toUpperCase()+busqueda.substring(1);
        if(anun.getCategoria().contains(etfootbuscarBA.getText().toString()) || anun.getNombre().contains(etfootbuscarBA.getText().toString()) || anun.getSubcategoria().contains(etfootbuscarBA.getText().toString()) || anun.getDireccion().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortaes().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargaes().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortaen().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargaen().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortade().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargade().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortafr().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargafr().contains(etfootbuscarBA.getText().toString())
                || anun.getCategoria().contains(mayusBusqueda) || anun.getNombre().contains(mayusBusqueda) || anun.getSubcategoria().contains(mayusBusqueda) || anun.getDireccion().contains(mayusBusqueda)
                || anun.getDescripcioncortaes().contains(mayusBusqueda) || anun.getDescripcionlargaes().contains(mayusBusqueda)
                || anun.getDescripcioncortaen().contains(mayusBusqueda) || anun.getDescripcionlargaen().contains(mayusBusqueda)
                || anun.getDescripcioncortade().contains(mayusBusqueda) || anun.getDescripcionlargade().contains(mayusBusqueda)
                || anun.getDescripcioncortafr().contains(mayusBusqueda) || anun.getDescripcionlargafr().contains(mayusBusqueda)) {
            //ENLAZAR DATOS FIREBASE
            lista_anunciosBA.add(dataSnapshot.getValue(ZAnuncio.class));
            //ADAPTADOR
            ZAdaptadorAnuncio adaptador = new ZAdaptadorAnuncio(this, lista_anunciosBA);
            listBA.setAdapter(adaptador);
            //CREAR EL CLICK CORTO EN LA LISTVIEW (QUE VA A IR A OTRA MAINACTIVITY)
            listBA.setOnItemClickListener
                    (new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //REFERENCIA A LA CLASE
                            ZAnuncio a = ((ZAnuncio) parent.getItemAtPosition(position));
                            ZAnuncio anuncioenviado = new ZAnuncio(a.getImagen(), a.getNombre(), a.getDescripcionlargaes(),
                                    a.getDescripcionlargade(), a.getDescripcionlargaen(), a.getDescripcionlargafr(), a.getDescuentoes(),
                                    a.getDescuentode(), a.getDescuentoen(), a.getDescuentofr(), a.getFacebook(), a.getTwitter(),
                                    a.getTelefono(), a.getMail(), a.getMaps(), a.getExtra(), a.getDescripcioncortaes(), a.getDescripcioncortade(),
                                    a.getDescripcioncortaen(), a.getDescripcioncortafr(), a.getHorarioes(), a.getHorariode(), a.getHorarioen(),
                                    a.getHorariofr(), a.getDireccion(), a.getCategoria(), a.getSubcategoria(), a.getLatitud(), a.getLongitud());
                            String idioma = tvidiomaba.getText().toString();
                            Intent i = new Intent(getApplicationContext(), DAEmpresaActivity.class);
                            i.putExtra("EXTRA_ANUNCIO", anuncioenviado);
                            i.putExtra("EXTRA_IDIOMACAT", idioma);
                            i.putExtra("EXTRA_CATEGORIA", a.getCategoria());
                            startActivity(i);
                        }
                    });
        }
    }
    private void cargardatosocio(){
        dbAnuncio = FirebaseDatabase.getInstance().getReference().child("ocio");
        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot anuncioDataSnapShot : dataSnapshot.getChildren()) {
                    cargarListViewocio(anuncioDataSnapShot);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("CACategoriaActivity", "DATABASE ERROR");
            }
        };
        dbAnuncio.addListenerForSingleValueEvent(eventListener);
    }
    private void cargarListViewocio(DataSnapshot dataSnapshot) {
        ZAnuncio anun =dataSnapshot.getValue(ZAnuncio.class);
        String busqueda=etfootbuscarBA.getText().toString();
        String mayusBusqueda = busqueda.substring(0,1).toUpperCase()+busqueda.substring(1);
        if(anun.getCategoria().contains(etfootbuscarBA.getText().toString()) || anun.getNombre().contains(etfootbuscarBA.getText().toString()) || anun.getSubcategoria().contains(etfootbuscarBA.getText().toString()) || anun.getDireccion().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortaes().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargaes().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortaen().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargaen().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortade().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargade().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortafr().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargafr().contains(etfootbuscarBA.getText().toString())
                || anun.getCategoria().contains(mayusBusqueda) || anun.getNombre().contains(mayusBusqueda) || anun.getSubcategoria().contains(mayusBusqueda) || anun.getDireccion().contains(mayusBusqueda)
                || anun.getDescripcioncortaes().contains(mayusBusqueda) || anun.getDescripcionlargaes().contains(mayusBusqueda)
                || anun.getDescripcioncortaen().contains(mayusBusqueda) || anun.getDescripcionlargaen().contains(mayusBusqueda)
                || anun.getDescripcioncortade().contains(mayusBusqueda) || anun.getDescripcionlargade().contains(mayusBusqueda)
                || anun.getDescripcioncortafr().contains(mayusBusqueda) || anun.getDescripcionlargafr().contains(mayusBusqueda)) {
            //ENLAZAR DATOS FIREBASE
            lista_anunciosBA.add(dataSnapshot.getValue(ZAnuncio.class));
            //ADAPTADOR
            ZAdaptadorAnuncio adaptador = new ZAdaptadorAnuncio(this, lista_anunciosBA);
            listBA.setAdapter(adaptador);
            //CREAR EL CLICK CORTO EN LA LISTVIEW (QUE VA A IR A OTRA MAINACTIVITY)
            listBA.setOnItemClickListener
                    (new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //REFERENCIA A LA CLASE
                            ZAnuncio a = ((ZAnuncio) parent.getItemAtPosition(position));
                            ZAnuncio anuncioenviado = new ZAnuncio(a.getImagen(), a.getNombre(), a.getDescripcionlargaes(),
                                    a.getDescripcionlargade(), a.getDescripcionlargaen(), a.getDescripcionlargafr(), a.getDescuentoes(),
                                    a.getDescuentode(), a.getDescuentoen(), a.getDescuentofr(), a.getFacebook(), a.getTwitter(),
                                    a.getTelefono(), a.getMail(), a.getMaps(), a.getExtra(), a.getDescripcioncortaes(), a.getDescripcioncortade(),
                                    a.getDescripcioncortaen(), a.getDescripcioncortafr(), a.getHorarioes(), a.getHorariode(), a.getHorarioen(),
                                    a.getHorariofr(), a.getDireccion(), a.getCategoria(), a.getSubcategoria(), a.getLatitud(), a.getLongitud());
                            String idioma = tvidiomaba.getText().toString();
                            Intent i = new Intent(getApplicationContext(), DAEmpresaActivity.class);
                            i.putExtra("EXTRA_ANUNCIO", anuncioenviado);
                            i.putExtra("EXTRA_IDIOMACAT", idioma);
                            i.putExtra("EXTRA_CATEGORIA", a.getCategoria());
                            startActivity(i);
                        }
                    });
        }
    }
    private void cargardatosparque(){
        dbAnuncio = FirebaseDatabase.getInstance().getReference().child("parque");
        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot anuncioDataSnapShot : dataSnapshot.getChildren()) {
                    cargarListViewparque(anuncioDataSnapShot);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("CACategoriaActivity", "DATABASE ERROR");
            }
        };
        dbAnuncio.addListenerForSingleValueEvent(eventListener);
    }
    private void cargarListViewparque(DataSnapshot dataSnapshot) {
        ZAnuncio anun =dataSnapshot.getValue(ZAnuncio.class);
        String busqueda=etfootbuscarBA.getText().toString();
        String mayusBusqueda = busqueda.substring(0,1).toUpperCase()+busqueda.substring(1);
        if(anun.getCategoria().contains(etfootbuscarBA.getText().toString()) || anun.getNombre().contains(etfootbuscarBA.getText().toString()) || anun.getSubcategoria().contains(etfootbuscarBA.getText().toString()) || anun.getDireccion().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortaes().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargaes().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortaen().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargaen().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortade().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargade().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortafr().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargafr().contains(etfootbuscarBA.getText().toString())
                || anun.getCategoria().contains(mayusBusqueda) || anun.getNombre().contains(mayusBusqueda) || anun.getSubcategoria().contains(mayusBusqueda) || anun.getDireccion().contains(mayusBusqueda)
                || anun.getDescripcioncortaes().contains(mayusBusqueda) || anun.getDescripcionlargaes().contains(mayusBusqueda)
                || anun.getDescripcioncortaen().contains(mayusBusqueda) || anun.getDescripcionlargaen().contains(mayusBusqueda)
                || anun.getDescripcioncortade().contains(mayusBusqueda) || anun.getDescripcionlargade().contains(mayusBusqueda)
                || anun.getDescripcioncortafr().contains(mayusBusqueda) || anun.getDescripcionlargafr().contains(mayusBusqueda)) {
            //ENLAZAR DATOS FIREBASE
            lista_anunciosBA.add(dataSnapshot.getValue(ZAnuncio.class));
            //ADAPTADOR
            ZAdaptadorAnuncio adaptador = new ZAdaptadorAnuncio(this, lista_anunciosBA);
            listBA.setAdapter(adaptador);
            //CREAR EL CLICK CORTO EN LA LISTVIEW (QUE VA A IR A OTRA MAINACTIVITY)
            listBA.setOnItemClickListener
                    (new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //REFERENCIA A LA CLASE
                            ZAnuncio a = ((ZAnuncio) parent.getItemAtPosition(position));
                            ZAnuncio anuncioenviado = new ZAnuncio(a.getImagen(), a.getNombre(), a.getDescripcionlargaes(),
                                    a.getDescripcionlargade(), a.getDescripcionlargaen(), a.getDescripcionlargafr(), a.getDescuentoes(),
                                    a.getDescuentode(), a.getDescuentoen(), a.getDescuentofr(), a.getFacebook(), a.getTwitter(),
                                    a.getTelefono(), a.getMail(), a.getMaps(), a.getExtra(), a.getDescripcioncortaes(), a.getDescripcioncortade(),
                                    a.getDescripcioncortaen(), a.getDescripcioncortafr(), a.getHorarioes(), a.getHorariode(), a.getHorarioen(),
                                    a.getHorariofr(), a.getDireccion(), a.getCategoria(), a.getSubcategoria(), a.getLatitud(), a.getLongitud());
                            String idioma = tvidiomaba.getText().toString();
                            Intent i = new Intent(getApplicationContext(), DAEmpresaActivity.class);
                            i.putExtra("EXTRA_ANUNCIO", anuncioenviado);
                            i.putExtra("EXTRA_IDIOMACAT", idioma);
                            i.putExtra("EXTRA_CATEGORIA", a.getCategoria());
                            startActivity(i);
                        }
                    });
        }
    }
    private void cargardatosplaya(){
        dbAnuncio = FirebaseDatabase.getInstance().getReference().child("playa");
        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot anuncioDataSnapShot : dataSnapshot.getChildren()) {
                    cargarListViewplaya(anuncioDataSnapShot);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("CACategoriaActivity", "DATABASE ERROR");
            }
        };
        dbAnuncio.addListenerForSingleValueEvent(eventListener);
    }
    private void cargarListViewplaya(DataSnapshot dataSnapshot) {
        ZAnuncio anun =dataSnapshot.getValue(ZAnuncio.class);
        String busqueda=etfootbuscarBA.getText().toString();
        String mayusBusqueda = busqueda.substring(0,1).toUpperCase()+busqueda.substring(1);
        if(anun.getCategoria().contains(etfootbuscarBA.getText().toString()) || anun.getNombre().contains(etfootbuscarBA.getText().toString()) || anun.getSubcategoria().contains(etfootbuscarBA.getText().toString()) || anun.getDireccion().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortaes().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargaes().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortaen().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargaen().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortade().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargade().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortafr().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargafr().contains(etfootbuscarBA.getText().toString())
                || anun.getCategoria().contains(mayusBusqueda) || anun.getNombre().contains(mayusBusqueda) || anun.getSubcategoria().contains(mayusBusqueda) || anun.getDireccion().contains(mayusBusqueda)
                || anun.getDescripcioncortaes().contains(mayusBusqueda) || anun.getDescripcionlargaes().contains(mayusBusqueda)
                || anun.getDescripcioncortaen().contains(mayusBusqueda) || anun.getDescripcionlargaen().contains(mayusBusqueda)
                || anun.getDescripcioncortade().contains(mayusBusqueda) || anun.getDescripcionlargade().contains(mayusBusqueda)
                || anun.getDescripcioncortafr().contains(mayusBusqueda) || anun.getDescripcionlargafr().contains(mayusBusqueda)) {
            //ENLAZAR DATOS FIREBASE
            lista_anunciosBA.add(dataSnapshot.getValue(ZAnuncio.class));
            //ADAPTADOR
            ZAdaptadorAnuncio adaptador = new ZAdaptadorAnuncio(this, lista_anunciosBA);
            listBA.setAdapter(adaptador);
            //CREAR EL CLICK CORTO EN LA LISTVIEW (QUE VA A IR A OTRA MAINACTIVITY)
            listBA.setOnItemClickListener
                    (new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //REFERENCIA A LA CLASE
                            ZAnuncio a = ((ZAnuncio) parent.getItemAtPosition(position));
                            ZAnuncio anuncioenviado = new ZAnuncio(a.getImagen(), a.getNombre(), a.getDescripcionlargaes(),
                                    a.getDescripcionlargade(), a.getDescripcionlargaen(), a.getDescripcionlargafr(), a.getDescuentoes(),
                                    a.getDescuentode(), a.getDescuentoen(), a.getDescuentofr(), a.getFacebook(), a.getTwitter(),
                                    a.getTelefono(), a.getMail(), a.getMaps(), a.getExtra(), a.getDescripcioncortaes(), a.getDescripcioncortade(),
                                    a.getDescripcioncortaen(), a.getDescripcioncortafr(), a.getHorarioes(), a.getHorariode(), a.getHorarioen(),
                                    a.getHorariofr(), a.getDireccion(), a.getCategoria(), a.getSubcategoria(), a.getLatitud(), a.getLongitud());
                            String idioma = tvidiomaba.getText().toString();
                            Intent i = new Intent(getApplicationContext(), DAEmpresaActivity.class);
                            i.putExtra("EXTRA_ANUNCIO", anuncioenviado);
                            i.putExtra("EXTRA_IDIOMACAT", idioma);
                            i.putExtra("EXTRA_CATEGORIA", a.getCategoria());
                            startActivity(i);
                        }
                    });
        }
    }
    private void cargardatosrestauracion(){
        dbAnuncio = FirebaseDatabase.getInstance().getReference().child("restauracion");
        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot anuncioDataSnapShot : dataSnapshot.getChildren()) {
                    cargarListViewrestauracion(anuncioDataSnapShot);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("CACategoriaActivity", "DATABASE ERROR");
            }
        };
        dbAnuncio.addListenerForSingleValueEvent(eventListener);
    }
    private void cargarListViewrestauracion(DataSnapshot dataSnapshot) {
        ZAnuncio anun =dataSnapshot.getValue(ZAnuncio.class);
        String busqueda=etfootbuscarBA.getText().toString();
        String mayusBusqueda = busqueda.substring(0,1).toUpperCase()+busqueda.substring(1);
        if(anun.getCategoria().contains(etfootbuscarBA.getText().toString()) || anun.getNombre().contains(etfootbuscarBA.getText().toString()) || anun.getSubcategoria().contains(etfootbuscarBA.getText().toString()) || anun.getDireccion().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortaes().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargaes().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortaen().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargaen().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortade().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargade().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortafr().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargafr().contains(etfootbuscarBA.getText().toString())
                || anun.getCategoria().contains(mayusBusqueda) || anun.getNombre().contains(mayusBusqueda) || anun.getSubcategoria().contains(mayusBusqueda) || anun.getDireccion().contains(mayusBusqueda)
                || anun.getDescripcioncortaes().contains(mayusBusqueda) || anun.getDescripcionlargaes().contains(mayusBusqueda)
                || anun.getDescripcioncortaen().contains(mayusBusqueda) || anun.getDescripcionlargaen().contains(mayusBusqueda)
                || anun.getDescripcioncortade().contains(mayusBusqueda) || anun.getDescripcionlargade().contains(mayusBusqueda)
                || anun.getDescripcioncortafr().contains(mayusBusqueda) || anun.getDescripcionlargafr().contains(mayusBusqueda)) {
            //ENLAZAR DATOS FIREBASE
            lista_anunciosBA.add(dataSnapshot.getValue(ZAnuncio.class));
            //ADAPTADOR
            ZAdaptadorAnuncio adaptador = new ZAdaptadorAnuncio(this, lista_anunciosBA);
            listBA.setAdapter(adaptador);
            //CREAR EL CLICK CORTO EN LA LISTVIEW (QUE VA A IR A OTRA MAINACTIVITY)
            listBA.setOnItemClickListener
                    (new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //REFERENCIA A LA CLASE
                            ZAnuncio a = ((ZAnuncio) parent.getItemAtPosition(position));
                            ZAnuncio anuncioenviado = new ZAnuncio(a.getImagen(), a.getNombre(), a.getDescripcionlargaes(),
                                    a.getDescripcionlargade(), a.getDescripcionlargaen(), a.getDescripcionlargafr(), a.getDescuentoes(),
                                    a.getDescuentode(), a.getDescuentoen(), a.getDescuentofr(), a.getFacebook(), a.getTwitter(),
                                    a.getTelefono(), a.getMail(), a.getMaps(), a.getExtra(), a.getDescripcioncortaes(), a.getDescripcioncortade(),
                                    a.getDescripcioncortaen(), a.getDescripcioncortafr(), a.getHorarioes(), a.getHorariode(), a.getHorarioen(),
                                    a.getHorariofr(), a.getDireccion(), a.getCategoria(), a.getSubcategoria(), a.getLatitud(), a.getLongitud());
                            String idioma = tvidiomaba.getText().toString();
                            Intent i = new Intent(getApplicationContext(), DAEmpresaActivity.class);
                            i.putExtra("EXTRA_ANUNCIO", anuncioenviado);
                            i.putExtra("EXTRA_IDIOMACAT", idioma);
                            i.putExtra("EXTRA_CATEGORIA", a.getCategoria());
                            startActivity(i);
                        }
                    });
        }
    }
    private void cargardatossalud(){
        dbAnuncio = FirebaseDatabase.getInstance().getReference().child("salud");
        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot anuncioDataSnapShot : dataSnapshot.getChildren()) {
                    cargarListViewsalud(anuncioDataSnapShot);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("CACategoriaActivity", "DATABASE ERROR");
            }
        };
        dbAnuncio.addListenerForSingleValueEvent(eventListener);
    }
    private void cargarListViewsalud(DataSnapshot dataSnapshot) {
        ZAnuncio anun =dataSnapshot.getValue(ZAnuncio.class);
        String busqueda=etfootbuscarBA.getText().toString();
        String mayusBusqueda = busqueda.substring(0,1).toUpperCase()+busqueda.substring(1);
        if(anun.getCategoria().contains(etfootbuscarBA.getText().toString()) || anun.getNombre().contains(etfootbuscarBA.getText().toString()) || anun.getSubcategoria().contains(etfootbuscarBA.getText().toString()) || anun.getDireccion().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortaes().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargaes().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortaen().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargaen().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortade().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargade().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortafr().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargafr().contains(etfootbuscarBA.getText().toString())
                || anun.getCategoria().contains(mayusBusqueda) || anun.getNombre().contains(mayusBusqueda) || anun.getSubcategoria().contains(mayusBusqueda) || anun.getDireccion().contains(mayusBusqueda)
                || anun.getDescripcioncortaes().contains(mayusBusqueda) || anun.getDescripcionlargaes().contains(mayusBusqueda)
                || anun.getDescripcioncortaen().contains(mayusBusqueda) || anun.getDescripcionlargaen().contains(mayusBusqueda)
                || anun.getDescripcioncortade().contains(mayusBusqueda) || anun.getDescripcionlargade().contains(mayusBusqueda)
                || anun.getDescripcioncortafr().contains(mayusBusqueda) || anun.getDescripcionlargafr().contains(mayusBusqueda)) {
            //ENLAZAR DATOS FIREBASE
            lista_anunciosBA.add(dataSnapshot.getValue(ZAnuncio.class));
            //ADAPTADOR
            ZAdaptadorAnuncio adaptador = new ZAdaptadorAnuncio(this, lista_anunciosBA);
            listBA.setAdapter(adaptador);
            //CREAR EL CLICK CORTO EN LA LISTVIEW (QUE VA A IR A OTRA MAINACTIVITY)
            listBA.setOnItemClickListener
                    (new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //REFERENCIA A LA CLASE
                            ZAnuncio a = ((ZAnuncio) parent.getItemAtPosition(position));
                            ZAnuncio anuncioenviado = new ZAnuncio(a.getImagen(), a.getNombre(), a.getDescripcionlargaes(),
                                    a.getDescripcionlargade(), a.getDescripcionlargaen(), a.getDescripcionlargafr(), a.getDescuentoes(),
                                    a.getDescuentode(), a.getDescuentoen(), a.getDescuentofr(), a.getFacebook(), a.getTwitter(),
                                    a.getTelefono(), a.getMail(), a.getMaps(), a.getExtra(), a.getDescripcioncortaes(), a.getDescripcioncortade(),
                                    a.getDescripcioncortaen(), a.getDescripcioncortafr(), a.getHorarioes(), a.getHorariode(), a.getHorarioen(),
                                    a.getHorariofr(), a.getDireccion(), a.getCategoria(), a.getSubcategoria(), a.getLatitud(), a.getLongitud());
                            String idioma = tvidiomaba.getText().toString();
                            Intent i = new Intent(getApplicationContext(), DAEmpresaActivity.class);
                            i.putExtra("EXTRA_ANUNCIO", anuncioenviado);
                            i.putExtra("EXTRA_IDIOMACAT", idioma);
                            i.putExtra("EXTRA_CATEGORIA", a.getCategoria());
                            startActivity(i);
                        }
                    });
        }
    }
    private void cargardatosseguridad(){
        dbAnuncio = FirebaseDatabase.getInstance().getReference().child("seguridad");
        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot anuncioDataSnapShot : dataSnapshot.getChildren()) {
                    cargarListViewseguridad(anuncioDataSnapShot);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("CACategoriaActivity", "DATABASE ERROR");
            }
        };
        dbAnuncio.addListenerForSingleValueEvent(eventListener);
    }
    private void cargarListViewseguridad(DataSnapshot dataSnapshot) {
        ZAnuncio anun =dataSnapshot.getValue(ZAnuncio.class);
        String busqueda=etfootbuscarBA.getText().toString();
        String mayusBusqueda = busqueda.substring(0,1).toUpperCase()+busqueda.substring(1);
        if(anun.getCategoria().contains(etfootbuscarBA.getText().toString()) || anun.getNombre().contains(etfootbuscarBA.getText().toString()) || anun.getSubcategoria().contains(etfootbuscarBA.getText().toString()) || anun.getDireccion().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortaes().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargaes().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortaen().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargaen().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortade().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargade().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortafr().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargafr().contains(etfootbuscarBA.getText().toString())
                || anun.getCategoria().contains(mayusBusqueda) || anun.getNombre().contains(mayusBusqueda) || anun.getSubcategoria().contains(mayusBusqueda) || anun.getDireccion().contains(mayusBusqueda)
                || anun.getDescripcioncortaes().contains(mayusBusqueda) || anun.getDescripcionlargaes().contains(mayusBusqueda)
                || anun.getDescripcioncortaen().contains(mayusBusqueda) || anun.getDescripcionlargaen().contains(mayusBusqueda)
                || anun.getDescripcioncortade().contains(mayusBusqueda) || anun.getDescripcionlargade().contains(mayusBusqueda)
                || anun.getDescripcioncortafr().contains(mayusBusqueda) || anun.getDescripcionlargafr().contains(mayusBusqueda)) {
            //ENLAZAR DATOS FIREBASE
            lista_anunciosBA.add(dataSnapshot.getValue(ZAnuncio.class));
            //ADAPTADOR
            ZAdaptadorAnuncio adaptador = new ZAdaptadorAnuncio(this, lista_anunciosBA);
            listBA.setAdapter(adaptador);
            //CREAR EL CLICK CORTO EN LA LISTVIEW (QUE VA A IR A OTRA MAINACTIVITY)
            listBA.setOnItemClickListener
                    (new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //REFERENCIA A LA CLASE
                            ZAnuncio a = ((ZAnuncio) parent.getItemAtPosition(position));
                            ZAnuncio anuncioenviado = new ZAnuncio(a.getImagen(), a.getNombre(), a.getDescripcionlargaes(),
                                    a.getDescripcionlargade(), a.getDescripcionlargaen(), a.getDescripcionlargafr(), a.getDescuentoes(),
                                    a.getDescuentode(), a.getDescuentoen(), a.getDescuentofr(), a.getFacebook(), a.getTwitter(),
                                    a.getTelefono(), a.getMail(), a.getMaps(), a.getExtra(), a.getDescripcioncortaes(), a.getDescripcioncortade(),
                                    a.getDescripcioncortaen(), a.getDescripcioncortafr(), a.getHorarioes(), a.getHorariode(), a.getHorarioen(),
                                    a.getHorariofr(), a.getDireccion(), a.getCategoria(), a.getSubcategoria(), a.getLatitud(), a.getLongitud());
                            String idioma = tvidiomaba.getText().toString();
                            Intent i = new Intent(getApplicationContext(), DAEmpresaActivity.class);
                            i.putExtra("EXTRA_ANUNCIO", anuncioenviado);
                            i.putExtra("EXTRA_IDIOMACAT", idioma);
                            i.putExtra("EXTRA_CATEGORIA", a.getCategoria());
                            startActivity(i);
                        }
                    });
        }
    }
    private void cargardatosservicio(){
        dbAnuncio = FirebaseDatabase.getInstance().getReference().child("servicio");
        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot anuncioDataSnapShot : dataSnapshot.getChildren()) {
                    cargarListViewservicio(anuncioDataSnapShot);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("CACategoriaActivity", "DATABASE ERROR");
            }
        };
        dbAnuncio.addListenerForSingleValueEvent(eventListener);
    }
    private void cargarListViewservicio(DataSnapshot dataSnapshot) {
        ZAnuncio anun =dataSnapshot.getValue(ZAnuncio.class);
        String busqueda=etfootbuscarBA.getText().toString();
        String mayusBusqueda = busqueda.substring(0,1).toUpperCase()+busqueda.substring(1);
        if(anun.getCategoria().contains(etfootbuscarBA.getText().toString()) || anun.getNombre().contains(etfootbuscarBA.getText().toString()) || anun.getSubcategoria().contains(etfootbuscarBA.getText().toString()) || anun.getDireccion().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortaes().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargaes().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortaen().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargaen().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortade().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargade().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortafr().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargafr().contains(etfootbuscarBA.getText().toString())
                || anun.getCategoria().contains(mayusBusqueda) || anun.getNombre().contains(mayusBusqueda) || anun.getSubcategoria().contains(mayusBusqueda) || anun.getDireccion().contains(mayusBusqueda)
                || anun.getDescripcioncortaes().contains(mayusBusqueda) || anun.getDescripcionlargaes().contains(mayusBusqueda)
                || anun.getDescripcioncortaen().contains(mayusBusqueda) || anun.getDescripcionlargaen().contains(mayusBusqueda)
                || anun.getDescripcioncortade().contains(mayusBusqueda) || anun.getDescripcionlargade().contains(mayusBusqueda)
                || anun.getDescripcioncortafr().contains(mayusBusqueda) || anun.getDescripcionlargafr().contains(mayusBusqueda)) {
            //ENLAZAR DATOS FIREBASE
            lista_anunciosBA.add(dataSnapshot.getValue(ZAnuncio.class));
            //ADAPTADOR
            ZAdaptadorAnuncio adaptador = new ZAdaptadorAnuncio(this, lista_anunciosBA);
            listBA.setAdapter(adaptador);
            //CREAR EL CLICK CORTO EN LA LISTVIEW (QUE VA A IR A OTRA MAINACTIVITY)
            listBA.setOnItemClickListener
                    (new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //REFERENCIA A LA CLASE
                            ZAnuncio a = ((ZAnuncio) parent.getItemAtPosition(position));
                            ZAnuncio anuncioenviado = new ZAnuncio(a.getImagen(), a.getNombre(), a.getDescripcionlargaes(),
                                    a.getDescripcionlargade(), a.getDescripcionlargaen(), a.getDescripcionlargafr(), a.getDescuentoes(),
                                    a.getDescuentode(), a.getDescuentoen(), a.getDescuentofr(), a.getFacebook(), a.getTwitter(),
                                    a.getTelefono(), a.getMail(), a.getMaps(), a.getExtra(), a.getDescripcioncortaes(), a.getDescripcioncortade(),
                                    a.getDescripcioncortaen(), a.getDescripcioncortafr(), a.getHorarioes(), a.getHorariode(), a.getHorarioen(),
                                    a.getHorariofr(), a.getDireccion(), a.getCategoria(), a.getSubcategoria(), a.getLatitud(), a.getLongitud());
                            String idioma = tvidiomaba.getText().toString();
                            Intent i = new Intent(getApplicationContext(), DAEmpresaActivity.class);
                            i.putExtra("EXTRA_ANUNCIO", anuncioenviado);
                            i.putExtra("EXTRA_IDIOMACAT", idioma);
                            i.putExtra("EXTRA_CATEGORIA", a.getCategoria());
                            startActivity(i);
                        }
                    });
        }
    }
    private void cargardatostransporte(){
        dbAnuncio = FirebaseDatabase.getInstance().getReference().child("transporte");
        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot anuncioDataSnapShot : dataSnapshot.getChildren()) {
                    cargarListViewtransporte(anuncioDataSnapShot);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("CACategoriaActivity", "DATABASE ERROR");
            }
        };
        dbAnuncio.addListenerForSingleValueEvent(eventListener);
    }
    private void cargarListViewtransporte(DataSnapshot dataSnapshot) {
        ZAnuncio anun =dataSnapshot.getValue(ZAnuncio.class);
        String busqueda=etfootbuscarBA.getText().toString();
        String mayusBusqueda = busqueda.substring(0,1).toUpperCase()+busqueda.substring(1);
        if(anun.getCategoria().contains(etfootbuscarBA.getText().toString()) || anun.getNombre().contains(etfootbuscarBA.getText().toString()) || anun.getSubcategoria().contains(etfootbuscarBA.getText().toString()) || anun.getDireccion().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortaes().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargaes().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortaen().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargaen().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortade().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargade().contains(etfootbuscarBA.getText().toString())
                || anun.getDescripcioncortafr().contains(etfootbuscarBA.getText().toString()) || anun.getDescripcionlargafr().contains(etfootbuscarBA.getText().toString())
                || anun.getCategoria().contains(mayusBusqueda) || anun.getNombre().contains(mayusBusqueda) || anun.getSubcategoria().contains(mayusBusqueda) || anun.getDireccion().contains(mayusBusqueda)
                || anun.getDescripcioncortaes().contains(mayusBusqueda) || anun.getDescripcionlargaes().contains(mayusBusqueda)
                || anun.getDescripcioncortaen().contains(mayusBusqueda) || anun.getDescripcionlargaen().contains(mayusBusqueda)
                || anun.getDescripcioncortade().contains(mayusBusqueda) || anun.getDescripcionlargade().contains(mayusBusqueda)
                || anun.getDescripcioncortafr().contains(mayusBusqueda) || anun.getDescripcionlargafr().contains(mayusBusqueda)) {
            //ENLAZAR DATOS FIREBASE
            lista_anunciosBA.add(dataSnapshot.getValue(ZAnuncio.class));
            //ADAPTADOR
            ZAdaptadorAnuncio adaptador = new ZAdaptadorAnuncio(this, lista_anunciosBA);
            listBA.setAdapter(adaptador);
            //CREAR EL CLICK CORTO EN LA LISTVIEW (QUE VA A IR A OTRA MAINACTIVITY)
            listBA.setOnItemClickListener
                    (new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //REFERENCIA A LA CLASE
                            ZAnuncio a = ((ZAnuncio) parent.getItemAtPosition(position));
                            ZAnuncio anuncioenviado = new ZAnuncio(a.getImagen(), a.getNombre(), a.getDescripcionlargaes(),
                                    a.getDescripcionlargade(), a.getDescripcionlargaen(), a.getDescripcionlargafr(), a.getDescuentoes(),
                                    a.getDescuentode(), a.getDescuentoen(), a.getDescuentofr(), a.getFacebook(), a.getTwitter(),
                                    a.getTelefono(), a.getMail(), a.getMaps(), a.getExtra(), a.getDescripcioncortaes(), a.getDescripcioncortade(),
                                    a.getDescripcioncortaen(), a.getDescripcioncortafr(), a.getHorarioes(), a.getHorariode(), a.getHorarioen(),
                                    a.getHorariofr(), a.getDireccion(), a.getCategoria(), a.getSubcategoria(), a.getLatitud(), a.getLongitud());
                            String idioma = tvidiomaba.getText().toString();
                            Intent i = new Intent(getApplicationContext(), DAEmpresaActivity.class);
                            i.putExtra("EXTRA_ANUNCIO", anuncioenviado);
                            i.putExtra("EXTRA_IDIOMACAT", idioma);
                            i.putExtra("EXTRA_CATEGORIA", a.getCategoria());
                            startActivity(i);
                        }
                    });
        }
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
        String anunciosplash = "no";

        Intent mainIntent = new Intent().setClass(
                BAMenuActivity.this, BAMenuActivity.class);
        mainIntent.putExtra("EXTRA_ANUNCIOSPLASH", anunciosplash);
        startActivity(mainIntent);
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

        String anunciosplash = "no";

        Intent mainIntent = new Intent().setClass(
                BAMenuActivity.this, BAMenuActivity.class);
        mainIntent.putExtra("EXTRA_ANUNCIOSPLASH", anunciosplash);
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
        String anunciosplash = "no";

        Intent mainIntent = new Intent().setClass(
                BAMenuActivity.this, BAMenuActivity.class);
        mainIntent.putExtra("EXTRA_ANUNCIOSPLASH", anunciosplash);
        startActivity(mainIntent);
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
        String anunciosplash = "no";

        Intent mainIntent = new Intent().setClass(
                BAMenuActivity.this, BAMenuActivity.class);
        mainIntent.putExtra("EXTRA_ANUNCIOSPLASH", anunciosplash);

        startActivity(mainIntent);
    }

    public void clickMapaBA(View v) {
        Intent mainIntent = new Intent().setClass(
                BAMenuActivity.this, BBMapaActivity.class);
        mainIntent.putExtra("EXTRA_PERMANENTE","todo");
        mainIntent.putExtra("EXTRA_FILTRO","todo");
        startActivity(mainIntent);
        //TRANSICIÓN LATERAL PARA ADELANTE
        overridePendingTransition(R.anim.arrastrar_derecha_in, R.anim.arrastrar_derecha_out);

        //PARA QUE SE CIERRE AL PULSAR

        menu_fabBA.collapse();
    }

    public void clickInfoBA(View v) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_info);

        //ELEMENTOS DECLARADOS DE DIALOG
        TextView tvInfoApp = (TextView) dialog.findViewById(R.id.tvInfoApp);
        TextView tvDialogCorreo = (TextView) dialog.findViewById(R.id.tvDialogCorreo);
        Button volvermenu = (Button) dialog.findViewById(R.id.volverBotonDialog);

        //TEXTVIEW PARA QUE FUNCIONE EN HTML
        tvInfoApp.setText(Html.fromHtml(getString(R.string.infomensaje)));

        //BOTON PARA CERRRAR
        volvermenu.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

        //BOTON PARA ENVIAR EMAIL
        tvDialogCorreo.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String[] TO = {"fulgenll@hotmail.com"};
                        String[] CC = {""};
                        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                        emailIntent.setData(Uri.parse("mailto:"));
                        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                        emailIntent.putExtra(Intent.EXTRA_CC, CC);
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                        emailIntent.putExtra(Intent.EXTRA_TEXT, "");

                        try {
                            startActivity(Intent.createChooser(emailIntent, "Enviar email..."));
                            //finish();
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(BAMenuActivity.this,
                                    R.string.NoEncuentraEmail, Toast.LENGTH_LONG).show();
                        }
                    }
                });
        dialog.show();

        //PARA QUE SE CIERRE AL PULSAR
        menu_fabBA.collapse();
    }

    public void clickFiltrarBA(View v) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_filtrar_ciudad);

        Button filtrar = (Button) dialog.findViewById(R.id.btnfooterDialogFiltroBA);

        //CHECKBOX DECLARADOS
        CheckBox checkCHIPIONA = (CheckBox) dialog.findViewById(R.id.checkCHIPIONA);
        CheckBox checkCADIZ = (CheckBox) dialog.findViewById(R.id.checkCADIZ);
        CheckBox checkELPUERTODESTAMARIA = (CheckBox) dialog.findViewById(R.id.checkELPUERTODESTAMARIA);
        CheckBox checkJEREZ = (CheckBox) dialog.findViewById(R.id.checkJEREZ);
        CheckBox checkPUERTOREAL = (CheckBox) dialog.findViewById(R.id.checkPUERTOREAL);
        CheckBox checkROTA = (CheckBox) dialog.findViewById(R.id.checkROTA);
        CheckBox checkSANFERNANDO = (CheckBox) dialog.findViewById(R.id.checkSANFERNANDO);
        CheckBox checkSANLUCAR = (CheckBox) dialog.findViewById(R.id.checkSANLUCAR);


        dialog.show();

        //PARA QUE SE CIERRE AL PULSAR
        menu_fabBA.collapse();
    }

    //BOTONE BUSCAR
    private void buscartodo(){
        if(etfootbuscarBA.getText().toString().equals("")){
            listBA.setVisibility(View.GONE);
            scrollBA.setVisibility(View.VISIBLE);
            Toast.makeText(context, R.string.EscribeBuscar, Toast.LENGTH_SHORT).show();
        }else{
            scrollBA.setVisibility(View.GONE);
            listBA.setVisibility(View.VISIBLE);
            lista_anunciosBA.clear();
            cargardatosalimentacion();
            cargardatosasociacion();
            cargardatoscompras();
            cargardatosdeporte();
            cargardatoseducacion();
            cargardatoshoteles();
            cargardatosinmobiliaria();
            cargardatosinstituciones();
            cargardatosmonumentos();
            cargardatosocio();
            cargardatosparque();
            cargardatosplaya();
            cargardatosrestauracion();
            cargardatossalud();
            cargardatosseguridad();
            cargardatosservicio();
            cargardatostransporte();
        }
    }
    public void clickBuscarBA(View v) {
        buscartodo();
    }
    public void onBackPressed() {
        //super.onBackPressed();


        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_salirapp);

        //ELEMENTOS DECLARADOS DE DIALOG
        Button cancelarBotonDialogSALIR = (Button) dialog.findViewById(R.id.cancelarBotonDialogSALIR);
        Button siBotonDialogSALIR = (Button) dialog.findViewById(R.id.siBotonDialogSALIR);


        //BOTON PARA CERRRAR
        cancelarBotonDialogSALIR.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

        //BOTON PARA CERRRAR
        siBotonDialogSALIR.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //PARA QUE CIERRE LA APP
                        finishAffinity();
                    }
                });

        dialog.show();


    }


    //COMPRUEBA QUE HAY INTERNET
    public static boolean compruebaConexion(Context context) {

        boolean connected = false;
        ConnectivityManager connec = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // Recupera todas las redes (tanto móviles como wifi)
        NetworkInfo[] redes = connec.getAllNetworkInfo();
        for (int i = 0; i < redes.length; i++) {
            // Si alguna red tiene conexión, se devuelve true
            if (redes[i].getState() == NetworkInfo.State.CONNECTED) {
                connected = true;
            }
        }
        return connected;
    }

}
