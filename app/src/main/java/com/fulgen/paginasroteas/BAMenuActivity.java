package com.fulgen.paginasroteas;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
            tvMonumentosocultoBA, tvOcioocultoBA, tvParqueocultoBA, tvPlayaocultoBA, tvRestauracionocultoBA, tvSaludocultoBA, tvServiciosocultoBA, tvSeguridadocultoBA, tvTransporteocultoBA, tvVacioBA;

    //ADAPTADOR
    //ArrayList<ZCategoria> lista_anuncios = new ArrayList<ZCategoria>();

    //VARIANTES DE DECLARADAS
    LinearLayout lyAlimentacionBA, lyAsociacionesBA, lyComprasBA, lyDeporteBA, lyEducacionBA, lyHotelesBA, lyInmobiliariaBA, lyInstitucionesBA, lyMonumentosBA, lyOcioBA,
    lyParqueBA, lyPlayaBA, lyRestauracionBA, lySaludBA, lySeguridadBA, lyServiciosBA, lyTransportesBA;
    private FloatingActionsMenu menu_fabBA;
    EditText etfootbuscarBA;
    final Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bamenu);

        //ENLAZO VARIANTES DECLARADAS

        lyAlimentacionBA = (LinearLayout)findViewById(R.id.lyAlimentacionBA);
        lyAsociacionesBA = (LinearLayout)findViewById(R.id.lyAsociacionesBA);
        lyComprasBA = (LinearLayout)findViewById(R.id.lyComprasBA);
        lyDeporteBA = (LinearLayout)findViewById(R.id.lyDeporteBA);
        lyEducacionBA = (LinearLayout)findViewById(R.id.lyEducacionBA);
        lyHotelesBA = (LinearLayout)findViewById(R.id.lyHotelesBA);
        lyInstitucionesBA = (LinearLayout)findViewById(R.id.lyInstitucionesBA);
        lyInmobiliariaBA = (LinearLayout)findViewById(R.id.lyInmobiliariaBA);
        lyMonumentosBA = (LinearLayout)findViewById(R.id.lyMonumentosBA);
        lyOcioBA = (LinearLayout)findViewById(R.id.lyOcioBA);
        lyParqueBA = (LinearLayout)findViewById(R.id.lyParqueBA);
        lyPlayaBA = (LinearLayout)findViewById(R.id.lyPlayaBA);
        lyRestauracionBA = (LinearLayout)findViewById(R.id.lyRestauracionBA);
        lySaludBA = (LinearLayout)findViewById(R.id.lySaludBA);
        lySeguridadBA = (LinearLayout)findViewById(R.id.lySeguridadBA);
        lyServiciosBA = (LinearLayout)findViewById(R.id.lyServiciosBA);
        lyTransportesBA = (LinearLayout)findViewById(R.id.lyTransporteBA);
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
        tvVacioBA = (TextView)findViewById(R.id.tvVacioBA);
        etfootbuscarBA = (EditText) findViewById(R.id.etfootbuscarBA);
        menu_fabBA = (FloatingActionsMenu) findViewById(R.id.menu_fabBA);

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

        //TextView volvermenu = (TextView) dialog.findViewById(R.id.tvFooterDialogBA);
        Button volvermenu = (Button) dialog.findViewById(R.id.volverBotonDialog);

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


}
