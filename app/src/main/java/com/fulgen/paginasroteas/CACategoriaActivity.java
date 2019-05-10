package com.fulgen.paginasroteas;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.util.Locale;

public class CACategoriaActivity extends AppCompatActivity {

    //ETIQUETAS SUBIDA DE BASE DE DATOS
    private DatabaseReference dbAnuncio;
    private ValueEventListener eventListener;
    static final String EXTRA_ANUNCIO="ANUNCIO";
    static final String EXTRA_ANUNCIOSPLASH = "ANUNCIOSPLASH";

    //VARIANTES DE DECLARADAS
    private FloatingActionsMenu menu_fabCA;
    ListView listCA;
    EditText etfootbuscarCA;
    TextView tvcabeceraCA, tvcategorialistCA, tvtitulolistCA, tvdescripcionlistCA, tvdescuentolistCA, tvimagempresalistCA, tvidiomaCA, tvcategoriaicoCA;
    ImageView imagempresalistCA, imgCabeceramenuCA;

    //ADAPTADOR
    ArrayList<ZAnuncio> lista_anuncios = new ArrayList<ZAnuncio>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cacategoria);

        //ENLAZO VARIANTES DECLARADAS
        listCA = (ListView) findViewById(R.id.listCA);
        etfootbuscarCA = (EditText) findViewById(R.id.etfootbuscarCA);
        tvcabeceraCA = (TextView) findViewById(R.id.tvcabeceraCA);
        menu_fabCA  = (FloatingActionsMenu) findViewById(R.id.menu_fabCA);

        tvcategorialistCA = (TextView) findViewById(R.id.tvcategorialistaCA);
        tvtitulolistCA = (TextView) findViewById(R.id.tvtitulolistCA);
        tvdescripcionlistCA = (TextView) findViewById(R.id.tvdescripcionlistCA);
        tvdescuentolistCA = (TextView) findViewById(R.id.tvdescuentolistCA);
        tvidiomaCA = (TextView) findViewById(R.id.tvidiomaCA);
        tvimagempresalistCA = (TextView) findViewById(R.id.tvimagempresalistCA);

        tvcategoriaicoCA = (TextView) findViewById(R.id.tvcategoriaicoCA);

        imagempresalistCA = (ImageView) findViewById(R.id.imagempresalistCA);
        imgCabeceramenuCA = (ImageView) findViewById(R.id.imgCabeceramenuCA);



        String catcatanuncio = getIntent().getStringExtra("EXTRA_CATEGORIA");
        String catcaticoanuncio = getIntent().getStringExtra("EXTRA_ICO");

        //String idioma = getIntent().getStringExtra("EXTRA_IDIOMA");

        tvcategorialistCA.setText(catcatanuncio);
        tvcabeceraCA.setText(catcatanuncio);
        tvcategoriaicoCA.setText(catcaticoanuncio);
        if(catcatanuncio.equals(null)){
            tvcategorialistCA.setText(getIntent().getStringExtra("EXTRA_AUTOCATEGORIA"));

            //PARA QUE CARGUE ICONO DE LA CATEGORIA
            int idImagen = this.getResources().getIdentifier(catcaticoanuncio, "drawable",this.getPackageName());
            imgCabeceramenuCA.setImageResource(idImagen);
        }
        //tvidiomaCA.setText(idioma);

        //Toast.makeText(this, "La categoria es: "+tvcategorialistCA.getText().toString(), Toast.LENGTH_SHORT).show();

        /*//ADAPTADOR
        ZAdaptadorAnuncio adaptador = new ZAdaptadorAnuncio(this, lista_anuncios);
        listCA.setAdapter(adaptador);*/



        cargardatos();
    }//FIN ONCREATE

    //BASE DE LIST VIEW

    private void cargardatos(){

        dbAnuncio = FirebaseDatabase.getInstance().getReference().child(tvcategorialistCA.getText().toString());

        //ESTO ES PARA QUE QUEDE ARCHIVOS FIREBASE EN LOCAL
        dbAnuncio.keepSynced(true);


        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot anuncioDataSnapShot : dataSnapshot.getChildren()){
                    cargarListView(anuncioDataSnapShot);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("CACategoriaActivity", "DATABASE ERROR");
            }
        };

        dbAnuncio.addListenerForSingleValueEvent(eventListener);

    }

    private void cargarListView(DataSnapshot dataSnapshot) {

        //ENLAZAR DATOS FIREBASE
        lista_anuncios.add(dataSnapshot.getValue(ZAnuncio.class));

        //ADAPTADOR
        ZAdaptadorAnuncio adaptador = new ZAdaptadorAnuncio(this, lista_anuncios);
        listCA.setAdapter(adaptador);

        //CREAR EL CLICK CORTO EN LA LISTVIEW (QUE VA A IR A OTRA MAINACTIVITY)

        listCA.setOnItemClickListener
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

                         //001




                         String idioma = tvidiomaCA.getText().toString();

                         Intent i = new Intent(getApplicationContext(), DAEmpresaActivity.class);

                         i.putExtra("EXTRA_ANUNCIO", anuncioenviado);
                         i.putExtra("EXTRA_IDIOMACAT", idioma);
                         i.putExtra("EXTRA_CATEGORIA", tvcabeceraCA.getText().toString());
                        //TRANSICIÓN LATERAL PARA ADELANTE
                         overridePendingTransition(R.anim.arrastrar_derecha_in, R.anim.arrastrar_derecha_out);
                         startActivity(i);

                     }
                 });
    }

    private void cargardatosbuscar(){

        if (etfootbuscarCA.getText().toString().equals("")){
            Toast.makeText(this, R.string.EscribeBuscar, Toast.LENGTH_SHORT).show();
        }else {

            dbAnuncio = FirebaseDatabase.getInstance().getReference().child(tvcategorialistCA.getText().toString());

            eventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    lista_anuncios.clear();
                    for (DataSnapshot anuncioDataSnapShot : dataSnapshot.getChildren()) {
                        cargarListViewbuscar(anuncioDataSnapShot);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("CACategoriaActivity", "DATABASE ERROR");
                }
            };

            dbAnuncio.addListenerForSingleValueEvent(eventListener);

        }

    }

    private void cargarListViewbuscar(DataSnapshot dataSnapshot) {

        ZAnuncio anun =dataSnapshot.getValue(ZAnuncio.class);

        if(anun.getNombre().contains(etfootbuscarCA.getText().toString()) || anun.getSubcategoria().contains(etfootbuscarCA.getText().toString()) || anun.getDireccion().contains(etfootbuscarCA.getText().toString())
                || anun.getDescripcioncortaes().contains(etfootbuscarCA.getText().toString()) || anun.getDescripcionlargaes().contains(etfootbuscarCA.getText().toString())
                || anun.getDescripcioncortaen().contains(etfootbuscarCA.getText().toString()) || anun.getDescripcionlargaen().contains(etfootbuscarCA.getText().toString())
                || anun.getDescripcioncortade().contains(etfootbuscarCA.getText().toString()) || anun.getDescripcionlargade().contains(etfootbuscarCA.getText().toString())
                || anun.getDescripcioncortafr().contains(etfootbuscarCA.getText().toString()) || anun.getDescripcionlargafr().contains(etfootbuscarCA.getText().toString())) {

            //ENLAZAR DATOS FIREBASE
            lista_anuncios.add(dataSnapshot.getValue(ZAnuncio.class));

            //ADAPTADOR
            ZAdaptadorAnuncio adaptador = new ZAdaptadorAnuncio(this, lista_anuncios);
            listCA.setAdapter(adaptador);

            //CREAR EL CLICK CORTO EN LA LISTVIEW (QUE VA A IR A OTRA MAINACTIVITY)

            listCA.setOnItemClickListener
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

                            String idioma = tvidiomaCA.getText().toString();

                            Intent i = new Intent(getApplicationContext(), DAEmpresaActivity.class);

                            //PASAR LOS EXTRAS
                            i.putExtra("EXTRA_ANUNCIO", anuncioenviado);
                            i.putExtra("EXTRA_IDIOMACAT", idioma);

                            //TRANSICIÓN LATERAL PARA ADELANTE
                            overridePendingTransition(R.anim.arrastrar_derecha_in, R.anim.arrastrar_derecha_out);
                            //INCIAR ACTIVITY
                            startActivity(i);

                        }
                    });
        }


    }

    //BOTONES FLOATMENU


    public void clickSpainCA(View v) {

        Locale locale = new Locale("es");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        Toast.makeText(this, getResources().getString(R.string.spaintoast), Toast.LENGTH_SHORT).show();

        //PARA QUE SE CIERRE AL PULSAR
        menu_fabCA.collapse();

        Intent mainIntent = new Intent().setClass(
                CACategoriaActivity.this, CACategoriaActivity.class);
        mainIntent.putExtra("EXTRA_CATEGORIA", tvcategorialistCA.getText().toString());
        startActivity(mainIntent);

    }

    public void clickUkCA(View v) {

        Locale locale = new Locale("en");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        Toast.makeText(this, getResources().getString(R.string.englishtoast), Toast.LENGTH_SHORT).show();

        //PARA QUE SE CIERRE AL PULSAR
        menu_fabCA.collapse();

        Intent mainIntent = new Intent().setClass(
                CACategoriaActivity.this, CACategoriaActivity.class);
        mainIntent.putExtra("EXTRA_CATEGORIA", tvcategorialistCA.getText().toString());
        startActivity(mainIntent);

    }

    public void clickFranceCA(View v) {

        Locale locale = new Locale("fr");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        Toast.makeText(this, getResources().getString(R.string.frenchtoast), Toast.LENGTH_SHORT).show();

        //PARA QUE SE CIERRE AL PULSAR
        menu_fabCA.collapse();

        Intent mainIntent = new Intent().setClass(
                CACategoriaActivity.this, CACategoriaActivity.class);
        mainIntent.putExtra("EXTRA_CATEGORIA", tvcategorialistCA.getText().toString());
        startActivity(mainIntent);

    }

    public void clickGermanCA(View v) {

        Locale locale = new Locale("de");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        Toast.makeText(this, getResources().getString(R.string.germantoast), Toast.LENGTH_SHORT).show();

        //PARA QUE SE CIERRE AL PULSAR
        menu_fabCA.collapse();

        Intent mainIntent = new Intent().setClass(
                CACategoriaActivity.this, CACategoriaActivity.class);
        mainIntent.putExtra("EXTRA_CATEGORIA", tvcategorialistCA.getText().toString());
        startActivity(mainIntent);

    }

    public void clickMapaCA(View v) {
        Intent mainIntent = new Intent().setClass(
                CACategoriaActivity.this, BBMapaActivity.class);
        //TRANSICIÓN LATERAL PARA ADELANTE
        overridePendingTransition(R.anim.arrastrar_derecha_in, R.anim.arrastrar_derecha_out);
        startActivity(mainIntent);
    }

    public void onBackPressed() {
        super.onBackPressed();

        //PARA CONVERTIR NO EL ANUNCIO
        String anunciosplash = "no";

        Intent i = new Intent().setClass(this,BAMenuActivity.class);

        //PARA CONVERTIR NO EL ANUNCIO
        i.putExtra("EXTRA_ANUNCIOSPLASH", anunciosplash);
        startActivity(i);
        //ANIMACIÓN
        overridePendingTransition(R.anim.arrastrar_izquierda_in, R.anim.arrastrar_izquierda_out);
        finish();
    }

    //BOTONE BUSCAR
    public void clickBuscarCA(View v) {

        cargardatosbuscar();
    }
}
