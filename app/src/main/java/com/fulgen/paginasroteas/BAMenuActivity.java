package com.fulgen.paginasroteas;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

public class BAMenuActivity extends AppCompatActivity {

    //ETIQUETA EXTRA PARA PASAR INFO A TODOS LOS ACTIVITYS
    static final String EXTRA_ANUNCIO = "ANUNCIO";
    static final String EXTRA_CATEGORIA = "CATEGORIA";

    //ETIQUETAS SUBIDA DE BASE DE DATOS
    private DatabaseReference dbAnuncio;
    private ValueEventListener eventListener;


    //VARIANTES DE DECLARADAS
    GridView listBA;
    TextView tvCategoria;

    //ADAPTADOR
    ArrayList<ZCategoria> lista_anuncios = new ArrayList<ZCategoria>();

    //VARIANTES DE DECLARADAS
    private FloatingActionsMenu menu_fabBA;
    EditText etfootbuscarBA;
    final Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bamenu);

        //ENLAZO VARIANTES DECLARADAS

        etfootbuscarBA = (EditText) findViewById(R.id.etfootbuscarBA);
        menu_fabBA = (FloatingActionsMenu) findViewById(R.id.menu_fabBA);

        //CATEGORIA MENU
        ArrayList<ZCategoria> menu = new ArrayList<ZCategoria>();

        menu.add(new ZCategoria(getString(R.string.alimentacion), R.drawable.ic_cat_a_alimentacion));
        menu.add(new ZCategoria(getString(R.string.asociaciones), R.drawable.ic_cat_asociaciones));
        menu.add(new ZCategoria(getString(R.string.compras), R.drawable.ic_cat_c_compras));
        menu.add(new ZCategoria(getString(R.string.deporte), R.drawable.ic_cat_d_deporte));
        menu.add(new ZCategoria(getString(R.string.educacion), R.drawable.ic_cat_e_educacion));
        menu.add(new ZCategoria(getString(R.string.hoteles), R.drawable.ic_cat_h_hoteles));
        menu.add(new ZCategoria(getString(R.string.instituciones), R.drawable.ic_cat_i_instituciones));
        menu.add(new ZCategoria(getString(R.string.inmobiliaria), R.drawable.ic_cat_inmobiliaria));
        menu.add(new ZCategoria(getString(R.string.monumentos), R.drawable.ic_cat_m_monumentos));
        menu.add(new ZCategoria(getString(R.string.ocio), R.drawable.ic_cat_o_ocio));
        menu.add(new ZCategoria(getString(R.string.parque), R.drawable.ic_cat_pa_parque));
        menu.add(new ZCategoria(getString(R.string.playa), R.drawable.ic_cat_pl_playa));
        menu.add(new ZCategoria(getString(R.string.restauracion), R.drawable.ic_cat_r_restauracion));
        menu.add(new ZCategoria(getString(R.string.salud), R.drawable.ic_cat_sa_salud));
        menu.add(new ZCategoria(getString(R.string.servicios), R.drawable.ic_cat_se_servicios));
        menu.add(new ZCategoria(getString(R.string.seguridad), R.drawable.ic_cat_seg_seguridad));
        menu.add(new ZCategoria(getString(R.string.trasnporte), R.drawable.ic_cat_t_transporte));

        listBA = (GridView) findViewById(R.id.listBA);
        tvCategoria = (TextView) findViewById(R.id.tvCategoria);

        listBA.setAdapter(new ZAdaptadorCategoria(this, menu));

        //AL HACER CLICK
        listBA.setOnItemClickListener
                (new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        //DESCARGAR ELEMENTOS QUE NECESITO DE FIREBASE

                dbAnuncio = FirebaseDatabase.getInstance().getReference();

                eventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //RECOGER NOMBRE DE LA BASE
                        tvCategoria.setText(dataSnapshot.getValue().toString());

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("BAMenuActivity", "Error!", databaseError.toException());
                    }
                };
                dbAnuncio.addValueEventListener(eventListener);


                //ETIQUETA + INDICAR A QUE MAINACTIVITY VA A IR
                Intent i = new Intent(getApplicationContext(), CACategoriaActivity.class);

                //INICIAR ACTIVITY
                startActivity(i);
            }
        });

    }//FIN ONCREATE

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
