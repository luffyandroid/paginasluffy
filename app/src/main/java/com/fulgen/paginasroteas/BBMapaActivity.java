package com.fulgen.paginasroteas;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.VectorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;

public class BBMapaActivity extends AppCompatActivity implements OnMapReadyCallback {

    //ETIQUETA EXTRA PARA PASAR INFO A TODOS LOS ACTIVITYS
    //002
    static final String EXTRA_ANUNCIOSPLASH = "ANUNCIOSPLASH";

    //VARIANTES DE DECLARADAS
    private FloatingActionsMenu menu_fabBB;
    TextView tvlatitudocultaBB, tvlongitudocultaBB, tvidmarcadorBB, tvpermanenteocultaBB, tvfiltroocultaBB;

    ZAnuncio anuncio;

    //GOOGLE MAPS
    private GoogleMap mMap;

    final Context context = this;
    Geocoder geocoder = null;

    DatabaseReference dbRef;
    ValueEventListener valueEventListener;
    ArrayList<Marker> tmpRealTimeMarkers = new ArrayList<>();
    ArrayList<Marker> RealTimeMarkers = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbmapa);
        //FRAGMENT
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.bbmap);
        mapFragment.getMapAsync(this);

        //ENLAZO VARIANTES DECLARADAS
        menu_fabBB  = (FloatingActionsMenu) findViewById(R.id.menu_fabBB);
        tvlatitudocultaBB = (TextView)findViewById(R.id.tvlatitudocultaBB);
        tvlongitudocultaBB = (TextView)findViewById(R.id.tvlongitudocultaBB);
        tvidmarcadorBB = (TextView)findViewById(R.id.tvidmarcadorBB);
        tvpermanenteocultaBB = (TextView)findViewById(R.id.tvpermanenteocultaBB);
        tvfiltroocultaBB = (TextView)findViewById(R.id.tvfiltroocultaBB);
        dbRef = FirebaseDatabase.getInstance().getReference();

        String permanente = getIntent().getStringExtra("EXTRA_PERMANENTE");
        String filtrado = getIntent().getStringExtra("EXTRA_FILTRO");
        tvpermanenteocultaBB.setText(permanente);
        tvfiltroocultaBB.setText(filtrado);

        //ESTO ES PARA QUE QUEDE ARCHIVOS FIREBASE EN LOCAL
        dbRef.keepSynced(true);



    }//FIN ONCREATE



    //BOTONES FLOATMENU

    public void clickSpainBB(View v) {

        Locale locale = new Locale("es");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        Toast.makeText(this, getResources().getString(R.string.spaintoast), Toast.LENGTH_SHORT).show();

        //PARA QUE SE CIERRE AL PULSAR
        menu_fabBB.collapse();

        //PARA CONVERTIR NO EL ANUNCIO
        String anunciosplash = "no";
        Intent mainIntent = new Intent().setClass(
                BBMapaActivity.this, BBMapaActivity.class);
        //PARA CONVERTIR NO EL ANUNCIO
        mainIntent.putExtra("EXTRA_ANUNCIOSPLASH", anunciosplash);
        String pasarpermanente = tvpermanenteocultaBB.getText().toString();
        String pasarfiltro = tvfiltroocultaBB.getText().toString();
        mainIntent.putExtra("EXTRA_PERMANENTE", pasarpermanente);
        mainIntent.putExtra("EXTRA_FILTRO", pasarfiltro);
        startActivity(mainIntent);

        //ANIMACIÓN
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        startActivity(mainIntent);
    }

    public void clickUkBB(View v) {

        Locale locale = new Locale("en");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        Toast.makeText(this, getResources().getString(R.string.englishtoast), Toast.LENGTH_SHORT).show();

        //PARA QUE SE CIERRE AL PULSAR
        menu_fabBB.collapse();

        //PARA CONVERTIR NO EL ANUNCIO
        String anunciosplash = "no";
        Intent mainIntent = new Intent().setClass(
                BBMapaActivity.this, BBMapaActivity.class);
        //PARA CONVERTIR NO EL ANUNCIO
        mainIntent.putExtra("EXTRA_ANUNCIOSPLASH", anunciosplash);
        String pasarpermanente = tvpermanenteocultaBB.getText().toString();
        String pasarfiltro = tvfiltroocultaBB.getText().toString();
        mainIntent.putExtra("EXTRA_PERMANENTE", pasarpermanente);
        mainIntent.putExtra("EXTRA_FILTRO", pasarfiltro);
        startActivity(mainIntent);

        //ANIMACIÓN
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        startActivity(mainIntent);
    }

    public void clickFranceBB(View v) {

        Locale locale = new Locale("fr");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        Toast.makeText(this, getResources().getString(R.string.frenchtoast), Toast.LENGTH_SHORT).show();

        //PARA QUE SE CIERRE AL PULSAR
        menu_fabBB.collapse();

        //PARA CONVERTIR NO EL ANUNCIO
        String anunciosplash = "no";
        Intent mainIntent = new Intent().setClass(
                BBMapaActivity.this, BBMapaActivity.class);
        //PARA CONVERTIR NO EL ANUNCIO
        mainIntent.putExtra("EXTRA_ANUNCIOSPLASH", anunciosplash);
        String pasarpermanente = tvpermanenteocultaBB.getText().toString();
        String pasarfiltro = tvfiltroocultaBB.getText().toString();
        mainIntent.putExtra("EXTRA_PERMANENTE", pasarpermanente);
        mainIntent.putExtra("EXTRA_FILTRO", pasarfiltro);
        startActivity(mainIntent);

        //ANIMACIÓN
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        startActivity(mainIntent);
    }

    public void clickGermanBB(View v) {

        Locale locale = new Locale("de");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        Toast.makeText(this, getResources().getString(R.string.germantoast), Toast.LENGTH_SHORT).show();

        //PARA QUE SE CIERRE AL PULSAR
        menu_fabBB.collapse();

        //PARA CONVERTIR NO EL ANUNCIO
        String anunciosplash = "no";
        Intent mainIntent = new Intent().setClass(
                BBMapaActivity.this, BBMapaActivity.class);
        //PARA CONVERTIR NO EL ANUNCIO
        mainIntent.putExtra("EXTRA_ANUNCIOSPLASH", anunciosplash);
        String pasarpermanente = tvpermanenteocultaBB.getText().toString();
        String pasarfiltro = tvfiltroocultaBB.getText().toString();
        mainIntent.putExtra("EXTRA_PERMANENTE", pasarpermanente);
        mainIntent.putExtra("EXTRA_FILTRO", pasarfiltro);
        startActivity(mainIntent);

        //ANIMACIÓN
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        startActivity(mainIntent);
    }

    public void clickLocalizarmeBB(View v) {

        locationStart();
        //PARA QUE SE CIERRE AL PULSAR
        menu_fabBB.collapse();
    }

    public void clickFiltrarBB(View v) {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_filtrar);
        TextView tvalimentacionfiltroBA = (TextView)dialog.findViewById(R.id.tvsubbcabeceraAlimentacionFiltroBA);
        LinearLayout bloquealimentacion01 = (LinearLayout)dialog.findViewById(R.id.checkbloque01);
        LinearLayout bloquealimentacion02 = (LinearLayout)dialog.findViewById(R.id.checkbloque02);
        LinearLayout bloquealimentacion03 = (LinearLayout)dialog.findViewById(R.id.checkbloque03);
        if(tvpermanenteocultaBB.getText().toString().equals("alimentacion")){

            tvalimentacionfiltroBA.setVisibility(View.VISIBLE);
            bloquealimentacion01.setVisibility(View.VISIBLE);
            bloquealimentacion02.setVisibility(View.VISIBLE);
            bloquealimentacion03.setVisibility(View.VISIBLE);

        }
        final CheckBox check_carniceria = (CheckBox)dialog.findViewById(R.id.check_carniceria);
        final CheckBox check_fruteria = (CheckBox)dialog.findViewById(R.id.check_fruteria);
        final CheckBox check_panaderia = (CheckBox)dialog.findViewById(R.id.check_panaderia);
        final CheckBox check_pasteleria = (CheckBox)dialog.findViewById(R.id.check_pasteleria);
        final CheckBox check_pescaderia = (CheckBox)dialog.findViewById(R.id.check_pescaderia);
        final CheckBox check_supermercado = (CheckBox)dialog.findViewById(R.id.check_supermercado);
        final CheckBox check_aotros = (CheckBox)dialog.findViewById(R.id.check_aotros);
        TextView tvasociacionesfiltroBA = (TextView)dialog.findViewById(R.id.tvsubbcabeceraAsociacionesFiltroBA);
        LinearLayout bloqueasociaciones01 = (LinearLayout)dialog.findViewById(R.id.checkbloque03B);
        if(tvpermanenteocultaBB.getText().toString().equals("asociaciones")){
            tvasociacionesfiltroBA.setVisibility(View.VISIBLE);
            bloqueasociaciones01.setVisibility(View.VISIBLE);
        }
        final CheckBox check_asociaciones = (CheckBox) dialog.findViewById(R.id.check_asociaciones);
        TextView tvcomprasfiltroBA = (TextView)dialog.findViewById(R.id.tvsubbcabeceraComprasFiltroBA);
        LinearLayout bloquecompras01 = (LinearLayout)dialog.findViewById(R.id.checkbloque04);
        LinearLayout bloquecompras02 = (LinearLayout)dialog.findViewById(R.id.checkbloque05);
        LinearLayout bloquecompras03 = (LinearLayout)dialog.findViewById(R.id.checkbloque06);
        LinearLayout bloquecompras04 = (LinearLayout)dialog.findViewById(R.id.checkbloque07);
        LinearLayout bloquecompras05 = (LinearLayout)dialog.findViewById(R.id.checkbloque08);
        LinearLayout bloquecompras06 = (LinearLayout)dialog.findViewById(R.id.checkbloque09);
        LinearLayout bloquecompras07 = (LinearLayout)dialog.findViewById(R.id.checkbloque10);
        if(tvpermanenteocultaBB.getText().toString().equals("compras")){
            tvcomprasfiltroBA.setVisibility(View.VISIBLE);
            bloquecompras01.setVisibility(View.VISIBLE);
            bloquecompras02.setVisibility(View.VISIBLE);
            bloquecompras03.setVisibility(View.VISIBLE);
            bloquecompras04.setVisibility(View.VISIBLE);
            bloquecompras05.setVisibility(View.VISIBLE);
            bloquecompras06.setVisibility(View.VISIBLE);
            bloquecompras07.setVisibility(View.VISIBLE);

        }
        final CheckBox check_bazar = (CheckBox)dialog.findViewById(R.id.check_bazar);
        final CheckBox check_bicicleta = (CheckBox)dialog.findViewById(R.id.check_bicicleta);
        final CheckBox check_bodegas = (CheckBox)dialog.findViewById(R.id.check_bodegas);
        final CheckBox check_calzado = (CheckBox)dialog.findViewById(R.id.check_calzado);
        final CheckBox check_concesionario = (CheckBox)dialog.findViewById(R.id.check_concesionario);
        final CheckBox check_drogueria = (CheckBox)dialog.findViewById(R.id.check_drogueria);
        final CheckBox check_electronica = (CheckBox)dialog.findViewById(R.id.check_electronica);
        final CheckBox check_ferreteria = (CheckBox)dialog.findViewById(R.id.check_ferreteria);
        final CheckBox check_floristeria = (CheckBox)dialog.findViewById(R.id.check_floristeria);
        final CheckBox check_informatica = (CheckBox)dialog.findViewById(R.id.check_informatica);
        final CheckBox check_joyeria = (CheckBox)dialog.findViewById(R.id.check_joyeria);
        final CheckBox check_jugueteria = (CheckBox)dialog.findViewById(R.id.check_jugueteria);
        final CheckBox check_kiosko = (CheckBox)dialog.findViewById(R.id.check_kiosko);
        final CheckBox check_mascotas = (CheckBox)dialog.findViewById(R.id.check_mascotas);
        final CheckBox check_mercadillo = (CheckBox)dialog.findViewById(R.id.check_mercadillo);
        final CheckBox check_mobiliario = (CheckBox)dialog.findViewById(R.id.check_mobilario);
        final CheckBox check_motos = (CheckBox)dialog.findViewById(R.id.check_motos);
        final CheckBox check_moviles = (CheckBox)dialog.findViewById(R.id.check_moviles);
        final CheckBox check_ropa = (CheckBox)dialog.findViewById(R.id.check_ropa);
        final CheckBox check_cotros = (CheckBox)dialog.findViewById(R.id.check_cotros);
        TextView tvdeportefiltroBA = (TextView)dialog.findViewById(R.id.tvsubbcabeceraDeporteFiltroBA);
        LinearLayout bloquedeporte01 = (LinearLayout)dialog.findViewById(R.id.checkbloque11);
        LinearLayout bloquedeporte02 = (LinearLayout)dialog.findViewById(R.id.checkbloque12);
        LinearLayout bloquedeporte03 = (LinearLayout)dialog.findViewById(R.id.checkbloque13);
        if(tvpermanenteocultaBB.getText().toString().equals("deporte")){
            tvdeportefiltroBA.setVisibility(View.VISIBLE);
            bloquedeporte01.setVisibility(View.VISIBLE);
            bloquedeporte02.setVisibility(View.VISIBLE);
            bloquedeporte03.setVisibility(View.VISIBLE);
        }
        final CheckBox check_estadio = (CheckBox)dialog.findViewById(R.id.check_estadio);
        final CheckBox check_gimnasio = (CheckBox)dialog.findViewById(R.id.check_gimnasio);
        final CheckBox check_golf = (CheckBox)dialog.findViewById(R.id.check_golf);
        final CheckBox check_piscina = (CheckBox)dialog.findViewById(R.id.check_piscina);
        final CheckBox check_polideportivo = (CheckBox)dialog.findViewById(R.id.check_polideportivo);
        final CheckBox check_skatepark = (CheckBox)dialog.findViewById(R.id.check_skatepark);
        final CheckBox check_tenis = (CheckBox)dialog.findViewById(R.id.check_tenis);
        final CheckBox check_dotros = (CheckBox)dialog.findViewById(R.id.check_dotros);
        TextView tveducacionfiltroBA = (TextView)dialog.findViewById(R.id.tvsubbcabeceraEducacionFiltroBA);
        LinearLayout bloqueeducacion01 = (LinearLayout) dialog.findViewById(R.id.checkbloque14);
        LinearLayout bloqueeducacion02 = (LinearLayout) dialog.findViewById(R.id.checkbloque15);
        if(tvpermanenteocultaBB.getText().toString().equals("educacion")){
            tveducacionfiltroBA.setVisibility(View.VISIBLE);
            bloqueeducacion01.setVisibility(View.VISIBLE);
            bloqueeducacion02.setVisibility(View.VISIBLE);
        }
        final CheckBox check_academia = (CheckBox)dialog.findViewById(R.id.check_academia);
        final CheckBox check_autoescuela = (CheckBox)dialog.findViewById(R.id.check_autoescuela);
        final CheckBox check_biblioteca = (CheckBox)dialog.findViewById(R.id.check_biblioteca);
        final CheckBox check_colegio = (CheckBox)dialog.findViewById(R.id.check_colegio);
        final CheckBox check_guarderia = (CheckBox)dialog.findViewById(R.id.check_guarderia);
        final CheckBox check_instituto = (CheckBox)dialog.findViewById(R.id.check_instituto);
        TextView tvhotelesfiltroBA = (TextView)dialog.findViewById(R.id.tvsubbcabeceraHotelesFiltroBA);
        LinearLayout bloquehoteles01 = (LinearLayout)dialog.findViewById(R.id.checkbloque16);
        LinearLayout bloquehoteles02 = (LinearLayout)dialog.findViewById(R.id.checkbloque16B);
        if(tvpermanenteocultaBB.getText().toString().equals("hoteles")){
            tvhotelesfiltroBA.setVisibility(View.VISIBLE);
            bloquehoteles01.setVisibility(View.VISIBLE);
            bloquehoteles02.setVisibility(View.VISIBLE);
        }
        final CheckBox check_albergue = (CheckBox)dialog.findViewById(R.id.check_albergue);
        final CheckBox check_apartahotel = (CheckBox)dialog.findViewById(R.id.check_apartahotal);
        final CheckBox check_camping = (CheckBox)dialog.findViewById(R.id.check_camping);
        final CheckBox check_hostal = (CheckBox)dialog.findViewById(R.id.check_hostal);
        final CheckBox check_hotel = (CheckBox)dialog.findViewById(R.id.check_hotel);
        final CheckBox check_pension = (CheckBox)dialog.findViewById(R.id.check_pension);
        TextView tvinmobiliariafiltroBA = (TextView)dialog.findViewById(R.id.tvsubbcabeceraInmobilariaFiltroBA);
        LinearLayout bloqueinmobiliaria01 = (LinearLayout)dialog.findViewById(R.id.checkbloque16C);
        if(tvpermanenteocultaBB.getText().toString().equals("inmobiliaria")){
            tvinmobiliariafiltroBA.setVisibility(View.VISIBLE);
            bloqueinmobiliaria01.setVisibility(View.VISIBLE);
        }
        final CheckBox check_inmobiliaria = (CheckBox) dialog.findViewById(R.id.check_inmobiliaria);
        TextView tvinstitucionesfiltroBA = (TextView)dialog.findViewById(R.id.tvsubbcabeceraInstitucionesFiltroBA);
        LinearLayout bloqueinstituciones01 = (LinearLayout)dialog.findViewById(R.id.checkbloque17);
        LinearLayout bloqueinstituciones02 = (LinearLayout)dialog.findViewById(R.id.checkbloque18);
        if(tvpermanenteocultaBB.getText().toString().equals("instituciones")){
            tvinstitucionesfiltroBA.setVisibility(View.VISIBLE);
            bloqueinstituciones01.setVisibility(View.VISIBLE);
            bloqueinstituciones02.setVisibility(View.VISIBLE);
        }
        final CheckBox check_ayuntamiento = (CheckBox)dialog.findViewById(R.id.check_ayuntamiento);
        final CheckBox check_correos = (CheckBox)dialog.findViewById(R.id.check_correos);
        final CheckBox check_juzgados = (CheckBox)dialog.findViewById(R.id.check_juzgados);
        final CheckBox check_omic = (CheckBox)dialog.findViewById(R.id.check_omic);
        final CheckBox check_turismo = (CheckBox)dialog.findViewById(R.id.check_turismo);
        final CheckBox check_iotros = (CheckBox)dialog.findViewById(R.id.check_iotros);
        TextView tvmonumentosfiltroBA = (TextView)dialog.findViewById(R.id.tvsubbcabeceraMonumentosFiltroBA);
        LinearLayout bloquemonumentos01 = (LinearLayout)dialog.findViewById(R.id.checkbloque19);
        LinearLayout bloquemonumentos02 = (LinearLayout)dialog.findViewById(R.id.checkbloque20);
        LinearLayout bloquemonumentos03 = (LinearLayout)dialog.findViewById(R.id.checkbloque21);
        if(tvpermanenteocultaBB.getText().toString().equals("monumentos")){
            tvmonumentosfiltroBA.setVisibility(View.VISIBLE);
            bloquemonumentos01.setVisibility(View.VISIBLE);
            bloquemonumentos02.setVisibility(View.VISIBLE);
            bloquemonumentos03.setVisibility(View.VISIBLE);
        }
        final CheckBox check_castillo = (CheckBox)dialog.findViewById(R.id.check_castillo);
        final CheckBox check_estatua = (CheckBox)dialog.findViewById(R.id.check_estatua);
        final CheckBox check_iglesia = (CheckBox)dialog.findViewById(R.id.check_iglesia);
        final CheckBox check_museo = (CheckBox)dialog.findViewById(R.id.check_museo);
        final CheckBox check_plaza_de_toros = (CheckBox)dialog.findViewById(R.id.check_plaza_de_toros);
        final CheckBox check_puente = (CheckBox)dialog.findViewById(R.id.check_puente);
        final CheckBox check_ruinas = (CheckBox)dialog.findViewById(R.id.check_ruinas);
        final CheckBox check_torre = (CheckBox)dialog.findViewById(R.id.check_torre);
        final CheckBox check_motros = (CheckBox)dialog.findViewById(R.id.check_motros);
        TextView tvociofiltroBA = (TextView)dialog.findViewById(R.id.tvsubbcabeceraOcioFiltroBA);
        LinearLayout bloqueocio01 = (LinearLayout)dialog.findViewById(R.id.checkbloque22);
        LinearLayout bloqueocio02 = (LinearLayout)dialog.findViewById(R.id.checkbloque22b);
        LinearLayout bloqueocio03 = (LinearLayout)dialog.findViewById(R.id.checkbloque23);
        if(tvpermanenteocultaBB.getText().toString().equals("ocio")){
            tvociofiltroBA.setVisibility(View.VISIBLE);
            bloqueocio01.setVisibility(View.VISIBLE);
            bloqueocio02.setVisibility(View.VISIBLE);
            bloqueocio03.setVisibility(View.VISIBLE);
        }
        final CheckBox check_bingo = (CheckBox)dialog.findViewById(R.id.check_bingo);
        final CheckBox check_cine = (CheckBox)dialog.findViewById(R.id.check_cine);
        final CheckBox check_discoteca = (CheckBox)dialog.findViewById(R.id.check_discoteca);
        final CheckBox check_karaoke = (CheckBox)dialog.findViewById(R.id.check_karaoke);
        final CheckBox check_paintball = (CheckBox)dialog.findViewById(R.id.check_paintball);
        final CheckBox check_scaperoom = (CheckBox)dialog.findViewById(R.id.check_scaperoom);
        final CheckBox check_parque_infantil = (CheckBox)dialog.findViewById(R.id.check_parque_infantil);
        final CheckBox check_teatro = (CheckBox)dialog.findViewById(R.id.check_teatro);
        final CheckBox check_ootros = (CheckBox)dialog.findViewById(R.id.check_ootros);
        TextView tvparquefiltroBA = (TextView)dialog.findViewById(R.id.tvsubbcabeceraParquesFiltroBA);
        LinearLayout bloqueparque01 = (LinearLayout)dialog.findViewById(R.id.checkbloque24);
        if(tvpermanenteocultaBB.getText().toString().equals("parque")){
            tvparquefiltroBA.setVisibility(View.VISIBLE);
            bloqueparque01.setVisibility(View.VISIBLE);
        }
        final CheckBox check_jardines = (CheckBox)dialog.findViewById(R.id.check_jardines);
        final CheckBox check_parque = (CheckBox)dialog.findViewById(R.id.check_parque);
        TextView tvplayafiltroBA = (TextView)dialog.findViewById(R.id.tvsubbcabeceraPlayaFiltroBA);
        LinearLayout bloqueplaya01 = (LinearLayout)dialog.findViewById(R.id.checkbloque24B);
        if(tvpermanenteocultaBB.getText().toString().equals("playa")){
            tvplayafiltroBA.setVisibility(View.VISIBLE);
            bloqueplaya01.setVisibility(View.VISIBLE);
        }
        final CheckBox check_playa = (CheckBox) dialog.findViewById(R.id.check_playa);
        TextView tvrestauracionfiltroBA = (TextView)dialog.findViewById(R.id.tvsubbcabeceraRestauracionFiltroBA);
        LinearLayout bloquerestauracion01 = (LinearLayout)dialog.findViewById(R.id.checkbloque25);
        LinearLayout bloquerestauracion02 = (LinearLayout)dialog.findViewById(R.id.checkbloque26);
        LinearLayout bloquerestauracion03 = (LinearLayout)dialog.findViewById(R.id.checkbloque27);
        LinearLayout bloquerestauracion04 = (LinearLayout)dialog.findViewById(R.id.checkbloque28);
        if(tvpermanenteocultaBB.getText().toString().equals("restauracion")){
            tvrestauracionfiltroBA.setVisibility(View.VISIBLE);
            bloquerestauracion01.setVisibility(View.VISIBLE);
            bloquerestauracion02.setVisibility(View.VISIBLE);
            bloquerestauracion03.setVisibility(View.VISIBLE);
            bloquerestauracion04.setVisibility(View.VISIBLE);
        }
        final CheckBox check_asador = (CheckBox)dialog.findViewById(R.id.check_asador);
        final CheckBox check_bar = (CheckBox)dialog.findViewById(R.id.check_bar);
        final CheckBox check_cafeteria = (CheckBox)dialog.findViewById(R.id.check_cafeteria);
        final CheckBox check_cerveceria = (CheckBox)dialog.findViewById(R.id.check_cerveceria);
        final CheckBox check_chiringuito = (CheckBox)dialog.findViewById(R.id.check_chiringuito);
        final CheckBox check_hamburguesa = (CheckBox)dialog.findViewById(R.id.check_hamburguesa);
        final CheckBox check_heladeria = (CheckBox)dialog.findViewById(R.id.check_heladeria);
        final CheckBox check_pizzeria = (CheckBox)dialog.findViewById(R.id.check_pizzeria);
        final CheckBox check_pub = (CheckBox)dialog.findViewById(R.id.check_pub);
        final CheckBox check_restaurante = (CheckBox)dialog.findViewById(R.id.check_restaurante);
        final CheckBox check_rotros = (CheckBox)dialog.findViewById(R.id.check_rotros);
        TextView tvsaludfiltroBA = (TextView)dialog.findViewById(R.id.tvsubbcabeceraSaludFiltroBA);
        LinearLayout bloquesalud01 = (LinearLayout)dialog.findViewById(R.id.checkbloque29);
        LinearLayout bloquesalud02 = (LinearLayout)dialog.findViewById(R.id.checkbloque30);
        LinearLayout bloquesalud03 = (LinearLayout)dialog.findViewById(R.id.checkbloque31);
        if(tvpermanenteocultaBB.getText().toString().equals("salud")){
            tvsaludfiltroBA.setVisibility(View.VISIBLE);
            bloquesalud01.setVisibility(View.VISIBLE);
            bloquesalud02.setVisibility(View.VISIBLE);
            bloquesalud03.setVisibility(View.VISIBLE);
        }
        final CheckBox check_ambulatorio = (CheckBox)dialog.findViewById(R.id.check_ambulatorio);
        final CheckBox check_dentista = (CheckBox)dialog.findViewById(R.id.check_dentista);
        final CheckBox check_dietista = (CheckBox)dialog.findViewById(R.id.check_dietista);
        final CheckBox check_farmacia = (CheckBox)dialog.findViewById(R.id.check_farmacia);
        final CheckBox check_fisioterapia = (CheckBox)dialog.findViewById(R.id.check_fisioterapia);
        final CheckBox check_optica = (CheckBox)dialog.findViewById(R.id.check_optica);
        final CheckBox check_podologo = (CheckBox)dialog.findViewById(R.id.check_podologo);
        final CheckBox check_psicologia = (CheckBox)dialog.findViewById(R.id.check_psicologia);
        final CheckBox check_veterinario = (CheckBox)dialog.findViewById(R.id.check_veterinario);
        TextView tvserviciosfiltroBA = (TextView)dialog.findViewById(R.id.tvsubbcabeceraServiciosFiltroBA);
        LinearLayout bloqueservicios01 = (LinearLayout)dialog.findViewById(R.id.checkbloque32);
        LinearLayout bloqueservicios02 = (LinearLayout)dialog.findViewById(R.id.checkbloque33);
        LinearLayout bloqueservicios03 = (LinearLayout)dialog.findViewById(R.id.checkbloque34);
        LinearLayout bloqueservicios04 = (LinearLayout)dialog.findViewById(R.id.checkbloque35);
        LinearLayout bloqueservicios05 = (LinearLayout)dialog.findViewById(R.id.checkbloque36);
        LinearLayout bloqueservicios06 = (LinearLayout)dialog.findViewById(R.id.checkbloque37);
        if(tvpermanenteocultaBB.getText().toString().equals("servicios")){
            tvserviciosfiltroBA.setVisibility(View.VISIBLE);
            bloqueservicios01.setVisibility(View.VISIBLE);
            bloqueservicios02.setVisibility(View.VISIBLE);
            bloqueservicios03.setVisibility(View.VISIBLE);
            bloqueservicios04.setVisibility(View.VISIBLE);
            bloqueservicios05.setVisibility(View.VISIBLE);
            bloqueservicios06.setVisibility(View.VISIBLE);
        }
        final CheckBox check_autolavado_coche = (CheckBox)dialog.findViewById(R.id.check_autolavado_coche);
        final CheckBox check_banca = (CheckBox)dialog.findViewById(R.id.check_banca);
        final CheckBox check_fotografo = (CheckBox)dialog.findViewById(R.id.check_fotografo);
        final CheckBox check_gasolinera = (CheckBox)dialog.findViewById(R.id.check_gasolinera);
        final CheckBox check_imprenta = (CheckBox)dialog.findViewById(R.id.check_imprenta);
        final CheckBox check_juridico = (CheckBox)dialog.findViewById(R.id.check_juridico);
        final CheckBox check_limpieza = (CheckBox)dialog.findViewById(R.id.check_limpieza);
        final CheckBox check_papeleria = (CheckBox)dialog.findViewById(R.id.check_papeleria);
        final CheckBox check_peluqueria = (CheckBox)dialog.findViewById(R.id.check_peluqueria);
        final CheckBox check_pintor = (CheckBox)dialog.findViewById(R.id.check_pintor);
        final CheckBox check_plagas = (CheckBox)dialog.findViewById(R.id.check_plagas);
        final CheckBox check_reformas = (CheckBox)dialog.findViewById(R.id.check_reformas);
        final CheckBox check_taller = (CheckBox)dialog.findViewById(R.id.check_taller);
        final CheckBox check_tatuaje = (CheckBox)dialog.findViewById(R.id.check_tatuaje);
        final CheckBox check_tintoreria = (CheckBox)dialog.findViewById(R.id.check_tintoreria);
        final CheckBox check_transportes = (CheckBox)dialog.findViewById(R.id.check_transportes);
        final CheckBox check_viajes = (CheckBox)dialog.findViewById(R.id.check_viajes);
        final CheckBox check_seotros = (CheckBox)dialog.findViewById(R.id.check_seotros);
        TextView tvseguridadfiltroBA = (TextView)dialog.findViewById(R.id.tvsubbcabeceraSeguridadFiltroBA);
        LinearLayout bloqueseguridad01 = (LinearLayout)dialog.findViewById(R.id.checkbloque38);
        LinearLayout bloqueseguridad02 = (LinearLayout)dialog.findViewById(R.id.checkbloque39);
        if(tvpermanenteocultaBB.getText().toString().equals("seguridad")){
            tvseguridadfiltroBA.setVisibility(View.VISIBLE);
            bloqueseguridad01.setVisibility(View.VISIBLE);
            bloqueseguridad02.setVisibility(View.VISIBLE);
        }
        final CheckBox check_guardia_civil = (CheckBox)dialog.findViewById(R.id.check_guardia_civil);
        final CheckBox check_policia_local = (CheckBox)dialog.findViewById(R.id.check_policia_local);
        final CheckBox check_policia_nacional = (CheckBox)dialog.findViewById(R.id.check_policia_nacional);
        final CheckBox check_proteccion_civil = (CheckBox)dialog.findViewById(R.id.check_proteccion);
        final CheckBox check_seguridad = (CheckBox)dialog.findViewById(R.id.check_seguridad);
        TextView tvtransportefiltroBA = (TextView)dialog.findViewById(R.id.tvsubbcabeceraTransporteFiltroBA);
        LinearLayout bloquetransporte01 = (LinearLayout)dialog.findViewById(R.id.checkbloque40);
        LinearLayout bloquetransporte02 = (LinearLayout)dialog.findViewById(R.id.checkbloque41);
        if(tvpermanenteocultaBB.getText().toString().equals("transporte")){
            tvtransportefiltroBA.setVisibility(View.VISIBLE);
            bloquetransporte01.setVisibility(View.VISIBLE);
            bloquetransporte02.setVisibility(View.VISIBLE);
        }
        final CheckBox check_aeropuerto = (CheckBox)dialog.findViewById(R.id.check_aeropuerto);
        final CheckBox check_estacion_bus = (CheckBox)dialog.findViewById(R.id.check_estacion);
        final CheckBox check_estacion_tren = (CheckBox)dialog.findViewById(R.id.check_estacion_tren);
        final CheckBox check_parada_bus = (CheckBox)dialog.findViewById(R.id.check_parada_bus);
        final CheckBox check_puerto = (CheckBox)dialog.findViewById(R.id.check_puerto);
        final CheckBox check_taxi = (CheckBox)dialog.findViewById(R.id.check_taxi);
        if(tvpermanenteocultaBB.getText().toString().equals("todo")){
            tvalimentacionfiltroBA.setVisibility(View.VISIBLE);
            bloquealimentacion01.setVisibility(View.VISIBLE);
            bloquealimentacion02.setVisibility(View.VISIBLE);
            bloquealimentacion03.setVisibility(View.VISIBLE);
            tvasociacionesfiltroBA.setVisibility(View.VISIBLE);
            bloqueasociaciones01.setVisibility(View.VISIBLE);
            tvcomprasfiltroBA.setVisibility(View.VISIBLE);
            bloquecompras01.setVisibility(View.VISIBLE);
            bloquecompras02.setVisibility(View.VISIBLE);
            bloquecompras03.setVisibility(View.VISIBLE);
            bloquecompras04.setVisibility(View.VISIBLE);
            bloquecompras05.setVisibility(View.VISIBLE);
            bloquecompras06.setVisibility(View.VISIBLE);
            bloquecompras07.setVisibility(View.VISIBLE);
            tvdeportefiltroBA.setVisibility(View.VISIBLE);
            bloquedeporte01.setVisibility(View.VISIBLE);
            bloquedeporte02.setVisibility(View.VISIBLE);
            bloquedeporte03.setVisibility(View.VISIBLE);
            tveducacionfiltroBA.setVisibility(View.VISIBLE);
            bloqueeducacion01.setVisibility(View.VISIBLE);
            bloqueeducacion02.setVisibility(View.VISIBLE);
            tvhotelesfiltroBA.setVisibility(View.VISIBLE);
            bloquehoteles01.setVisibility(View.VISIBLE);
            bloquehoteles02.setVisibility(View.VISIBLE);
            tvinmobiliariafiltroBA.setVisibility(View.VISIBLE);
            bloqueinmobiliaria01.setVisibility(View.VISIBLE);
            tvinstitucionesfiltroBA.setVisibility(View.VISIBLE);
            bloqueinstituciones01.setVisibility(View.VISIBLE);
            bloqueinstituciones02.setVisibility(View.VISIBLE);
            tvmonumentosfiltroBA.setVisibility(View.VISIBLE);
            bloquemonumentos01.setVisibility(View.VISIBLE);
            bloquemonumentos02.setVisibility(View.VISIBLE);
            bloquemonumentos03.setVisibility(View.VISIBLE);
            tvociofiltroBA.setVisibility(View.VISIBLE);
            bloqueocio01.setVisibility(View.VISIBLE);
            bloqueocio02.setVisibility(View.VISIBLE);
            bloqueocio03.setVisibility(View.VISIBLE);
            tvparquefiltroBA.setVisibility(View.VISIBLE);
            bloqueparque01.setVisibility(View.VISIBLE);
            tvplayafiltroBA.setVisibility(View.VISIBLE);
            bloqueplaya01.setVisibility(View.VISIBLE);
            tvrestauracionfiltroBA.setVisibility(View.VISIBLE);
            bloquerestauracion01.setVisibility(View.VISIBLE);
            bloquerestauracion02.setVisibility(View.VISIBLE);
            bloquerestauracion03.setVisibility(View.VISIBLE);
            bloquerestauracion04.setVisibility(View.VISIBLE);
            tvsaludfiltroBA.setVisibility(View.VISIBLE);
            bloquesalud01.setVisibility(View.VISIBLE);
            bloquesalud02.setVisibility(View.VISIBLE);
            bloquesalud03.setVisibility(View.VISIBLE);
            tvserviciosfiltroBA.setVisibility(View.VISIBLE);
            bloqueservicios01.setVisibility(View.VISIBLE);
            bloqueservicios02.setVisibility(View.VISIBLE);
            bloqueservicios03.setVisibility(View.VISIBLE);
            bloqueservicios04.setVisibility(View.VISIBLE);
            bloqueservicios05.setVisibility(View.VISIBLE);
            bloqueservicios06.setVisibility(View.VISIBLE);
            tvseguridadfiltroBA.setVisibility(View.VISIBLE);
            bloqueseguridad01.setVisibility(View.VISIBLE);
            bloqueseguridad02.setVisibility(View.VISIBLE);
            tvtransportefiltroBA.setVisibility(View.VISIBLE);
            bloquetransporte01.setVisibility(View.VISIBLE);
            bloquetransporte02.setVisibility(View.VISIBLE);

        }





        Button btnfooterDialogFiltroBA = (Button)dialog.findViewById(R.id.btnfooterDialogFiltroBA);

        btnfooterDialogFiltroBA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvfiltroocultaBB.setText("");
                if(check_carniceria.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_a_carniceria");
                }
                if(check_fruteria.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_a_fruteria");
                }
                if(check_panaderia.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_a_panaderia");
                }
                if(check_pasteleria.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_a_pasteleria");
                }
                if(check_pescaderia.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_a_pescaderia");
                }
                if(check_supermercado.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_a_supermercado");
                }
                if(check_aotros.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_a_aotros");
                }
                if(check_asociaciones.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_cat_asociaciones");
                }
                if(check_bazar.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_c_bazar");
                }
                if(check_bicicleta.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_c_bicicleta");
                }
                if(check_bodegas.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_c_bodega");
                }
                if(check_calzado.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_c_calzado");
                }
                if(check_concesionario.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_c_concesionario");
                }
                if(check_drogueria.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_c_drogueria");
                }
                if(check_electronica.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_c_electronica");
                }
                if(check_ferreteria.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_c_ferreteria");
                }
                if(check_floristeria.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_c_floristeria");
                }
                if(check_informatica.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_c_informatica");
                }
                if(check_joyeria.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_c_joyeria");
                }
                if(check_jugueteria.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_c_jugueteria");
                }
                if(check_kiosko.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_c_kiosko");
                }
                if(check_mascotas.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_c_mascotas");
                }
                if(check_mercadillo.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_c_mercadillo");
                }
                if(check_mobiliario.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_c_mobiliario");
                }
                if(check_motos.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_c_motos");
                }
                if(check_moviles.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_c_moviles");
                }
                if(check_ropa.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_c_ropa");
                }
                if(check_cotros.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_c_cotros");
                }
                if(check_estadio.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_d_estadio");
                }
                if(check_gimnasio.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_d_gimnasio");
                }
                if(check_golf.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_d_golf");
                }
                if(check_piscina.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_d_piscina");
                }
                if(check_polideportivo.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_d_polideportivo");
                }
                if(check_skatepark.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_d_skatepark");
                }
                if(check_tenis.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_d_tenis");
                }
                if(check_dotros.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_d_dotros");
                }
                if(check_academia.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_e_academia");
                }
                if(check_autoescuela.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_e_autoescuela");
                }
                if(check_biblioteca.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_e_biblioteca");
                }
                if(check_colegio.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_e_colegio");
                }
                if(check_guarderia.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_e_guarderia");
                }
                if(check_instituto.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_e_instituto");
                }
                if(check_albergue.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_h_albergue");
                }
                if(check_apartahotel.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_h_apartahotel");
                }
                if(check_camping.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_h_camping");
                }
                if(check_hostal.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_h_hostal");
                }
                if(check_hotel.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_h_hotel");
                }
                if(check_pension.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_h_pension");
                }
                if(check_inmobiliaria.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_cat_inmobiliaria");
                }
                if(check_ayuntamiento.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_i_ayuntamiento");
                }
                if(check_correos.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_i_correos");
                }
                if(check_juzgados.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_i_juzgados");
                }
                if(check_omic.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_i_omic");
                }
                if(check_turismo.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_i_turismo");
                }
                if(check_iotros.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_i_iotros");
                }
                if(check_castillo.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_m_castillo");
                }
                if(check_estatua.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_m_estatua");
                }
                if(check_iglesia.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_m_iglesia");
                }
                if(check_museo.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_m_museo");
                }
                if(check_plaza_de_toros.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_m_plaza_de_toros");
                }
                if(check_puente.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_m_puente");
                }
                if(check_ruinas.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_m_ruinas");
                }
                if(check_torre.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_m_torre");
                }
                if(check_motros.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_m_motros");
                }
                if(check_bingo.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_o_bingo");
                }
                if(check_cine.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_o_cine");
                }
                if(check_discoteca.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_o_discoteca");
                }
                if(check_karaoke.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_o_karaoke");
                }
                if(check_paintball.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_o_paintball");
                }
                if(check_scaperoom.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_o_scaperoom");
                }
                if(check_parque_infantil.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_o_parque_infantil");
                }
                if(check_teatro.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_o_teatro");
                }
                if(check_ootros.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_o_ootros");
                }
                if(check_jardines.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_pa_jardines");
                }
                if(check_parque.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_pa_parque");
                }
                if(check_playa.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_cat_pl_playa");
                }
                if(check_asador.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_r_asador");
                }
                if(check_bar.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_r_bar");
                }
                if(check_cafeteria.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_r_cafeteria");
                }
                if(check_cerveceria.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_r_cerveceria");
                }
                if(check_chiringuito.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_r_chiringuito");
                }
                if(check_hamburguesa.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_r_hamburguesa");
                }
                if(check_heladeria.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_r_heladeria");
                }
                if(check_pizzeria.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_r_pizzeria");
                }
                if(check_pub.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_r_pub");
                }
                if(check_restaurante.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_r_restaurante");
                }
                if(check_rotros.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_r_rotros");
                }
                if(check_ambulatorio.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_sa_ambulatorio");
                }
                if(check_dentista.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_sa_dentista");
                }
                if(check_dietista.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_sa_dietista");
                }
                if(check_farmacia.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_sa_farmacia");
                }
                if(check_fisioterapia.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_sa_fisioterapia");
                }
                if(check_optica.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_sa_optica");
                }
                if(check_podologo.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_sa_podologo");
                }
                if(check_psicologia.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_sa_psicologia");
                }
                if(check_veterinario.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_sa_veterinario");
                }
                if(check_autolavado_coche.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_se_autolavado_coche");
                }
                if(check_banca.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_se_banca");
                }
                if(check_fotografo.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_se_fotografo");
                }
                if(check_gasolinera.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_se_gasolinera");
                }
                if(check_imprenta.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_se_imprenta");
                }
                if(check_juridico.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_se_juridico");
                }
                if(check_limpieza.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_se_limpieza");
                }
                if(check_papeleria.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_se_papeleria");
                }
                if(check_peluqueria.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_se_peluqueria");
                }
                if(check_pintor.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_se_pintor");
                }
                if(check_plagas.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_se_plagas");
                }
                if(check_reformas.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_se_reformas");
                }
                if(check_taller.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_se_taller");
                }
                if(check_tatuaje.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_se_tatuaje");
                }
                if(check_tintoreria.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_se_tintoreria");
                }
                if(check_transportes.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_se_transportes");
                }
                if(check_viajes.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_se_viajes");
                }
                if(check_seotros.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_se_seotros");
                }
                if(check_guardia_civil.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_seg_guardia_civil");
                }
                if(check_policia_local.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_seg_policia_local");
                }
                if(check_policia_nacional.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_seg_policia_nacional");
                }
                if(check_proteccion_civil.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_seg_proteccion");
                }
                if(check_seguridad.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_seg_seguridad");
                }
                if(check_aeropuerto.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_t_aeropuerto");
                }
                if(check_estacion_bus.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_t_estacion");
                }
                if(check_estacion_tren.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_t_estacion_tren");
                }
                if(check_parada_bus.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_t_parada_bus");
                }
                if(check_puerto.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_t_puerto");
                }
                if(check_taxi.isChecked()){
                    tvfiltroocultaBB.setText(tvfiltroocultaBB.getText().toString()+"ic_sub_t_taxi");
                }

                Intent mainIntent = new Intent().setClass(
                        BBMapaActivity.this, BBMapaActivity.class);
                mainIntent.putExtra("EXTRA_PERMANENTE",tvpermanenteocultaBB.getText().toString());
                mainIntent.putExtra("EXTRA_FILTRO",tvfiltroocultaBB.getText().toString());
                startActivity(mainIntent);

            }
        });




        //MOSTAR DIALOGO
        dialog.show();

        //PARA QUE SE CIERRE AL PULSAR
        menu_fabBB.collapse();
    }

    public void clickMenuBB(View v) {

        //002
        //PARA CONVERTIR NO EL ANUNCIO
        String anunciosplash = "no";
        Intent mainIntent = new Intent().setClass(
                BBMapaActivity.this, BAMenuActivity.class);

                //PARA CONVERTIR NO EL ANUNCIO
                mainIntent.putExtra("EXTRA_ANUNCIOSPLASH", anunciosplash);
        startActivity(mainIntent);

        //ANIMACIÓN
        overridePendingTransition(R.anim.arrastrar_izquierda_in, R.anim.arrastrar_izquierda_out);
        //PARA QUE SE CIERRE AL PULSAR
        menu_fabBB.collapse();
    }

    public void onBackPressed() {
        super.onBackPressed();

        //002
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

    //PROTOCOLO GOOGLE MAPS
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        CameraUpdate inicioSatelite =
                CameraUpdateFactory.newLatLngZoom(new LatLng(36.6418169,-6.1849189),10);
        mMap.moveCamera(inicioSatelite);
        //MARCADORES ALIMENTACION

        if(tvpermanenteocultaBB.getText().toString().equals("todo")||(tvpermanenteocultaBB.getText().toString().equals("alimentacion"))) {



            dbRef.child("alimentacion").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ZAnuncio anun = snapshot.getValue(ZAnuncio.class);

                        if(tvfiltroocultaBB.getText().toString().contains(anun.getSubcategoria()) || tvfiltroocultaBB.getText().toString().equals("todo")) {

                            mMap.setInfoWindowAdapter
                                    (new ZTitleInvisible(BBMapaActivity.this) {
                                        @Override
                                        public boolean onTouch(View view, MotionEvent motionEvent) {
                                            return false;
                                        }
                                    });

                            MarkerOptions markeroptions = new MarkerOptions();
                            if (anun.getSubcategoria().equals("ic_sub_a_carniceria")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_a_carniceria));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_a_fruteria")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_a_fruteria));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_a_panaderia")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_a_panaderia));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_a_pasteleria")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_a_pasteleria));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_a_pescaderia")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_a_pescaderia));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_a_supermercado")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_a_supermercado));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_z_otros")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_a_zotros));
                            }
                            markeroptions.title("alimentacion/" + anun.getNombre());
                            markeroptions.position(new LatLng(anun.getLatitud(), anun.getLongitud()));
                            tmpRealTimeMarkers.add(mMap.addMarker(markeroptions));

                        }

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        //MARCADORES ASOCIACIONES

        if(tvpermanenteocultaBB.getText().toString().equals("todo")||(tvpermanenteocultaBB.getText().toString().equals("asociaciones"))) {


            dbRef.child("asociaciones").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ZAnuncio anun = snapshot.getValue(ZAnuncio.class);

                        if(tvfiltroocultaBB.getText().toString().contains(anun.getSubcategoria()) || tvfiltroocultaBB.getText().toString().equals("todo")) {
                            //COSA PARA HACER INVISIBLE EL TITLE
                            mMap.setInfoWindowAdapter
                                    (new ZTitleInvisible(BBMapaActivity.this) {
                                        @Override
                                        public boolean onTouch(View view, MotionEvent motionEvent) {
                                            return false;
                                        }
                                    });

                            MarkerOptions markeroptions = new MarkerOptions();
                            markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_asociaciones));

                            markeroptions.title("asociaciones/" + anun.getNombre());

                            markeroptions.position(new LatLng(anun.getLatitud(), anun.getLongitud()));
                            tmpRealTimeMarkers.add(mMap.addMarker(markeroptions));

                        }

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        //MARCADORES COMPRAS

        if(tvpermanenteocultaBB.getText().toString().equals("todo")||(tvpermanenteocultaBB.getText().toString().equals("compras"))) {

            dbRef.child("compras").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ZAnuncio anun = snapshot.getValue(ZAnuncio.class);

                        if(tvfiltroocultaBB.getText().toString().contains(anun.getSubcategoria()) || tvfiltroocultaBB.getText().toString().equals("todo")) {

                            mMap.setInfoWindowAdapter
                                    (new ZTitleInvisible(BBMapaActivity.this) {
                                        @Override
                                        public boolean onTouch(View view, MotionEvent motionEvent) {
                                            return false;
                                        }
                                    });

                            MarkerOptions markeroptions = new MarkerOptions();
                            if (anun.getSubcategoria().equals("ic_sub_c_bazar")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_c_bazar));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_c_bicicleta")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_c_bicicleta));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_c_bodegas")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_c_bodegas));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_c_calzado")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_c_calzado));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_c_concesionario")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_c_concesionario));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_c_drogueria")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_c_drogueria));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_c_electronica")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_c_electronica));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_c_ferreteria")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_c_ferreteria));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_c_floristeria")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_c_floristeria));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_c_informatica")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_c_informatica));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_c_joyeria")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_c_joyeria));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_c_jugueteria")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_c_jugueteria));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_c_kiosko")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_c_kiosko));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_c_mascotas")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_c_mascotas));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_c_mercadillo")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_c_mercadillo));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_c_mobiliario")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_c_mobilario));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_c_motos")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_c_motos));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_c_moviles")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_c_moviles));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_c_ropa")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_c_ropa));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_z_otros")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_c_zotros));
                            }

                            markeroptions.title("compras/" + anun.getNombre());
                            markeroptions.position(new LatLng(anun.getLatitud(), anun.getLongitud()));
                            tmpRealTimeMarkers.add(mMap.addMarker(markeroptions));

                        }

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

        //MARCADORES DEPORTES

        if(tvpermanenteocultaBB.getText().toString().equals("todo")||(tvpermanenteocultaBB.getText().toString().equals("deportes"))) {

            dbRef.child("deportes").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ZAnuncio anun = snapshot.getValue(ZAnuncio.class);

                        if(tvfiltroocultaBB.getText().toString().contains(anun.getSubcategoria()) || tvfiltroocultaBB.getText().toString().equals("todo")) {

                            mMap.setInfoWindowAdapter
                                    (new ZTitleInvisible(BBMapaActivity.this) {
                                        @Override
                                        public boolean onTouch(View view, MotionEvent motionEvent) {
                                            return false;
                                        }
                                    });

                            MarkerOptions markeroptions = new MarkerOptions();
                            if (anun.getSubcategoria().equals("ic_sub_d_estadio")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_d_estadio));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_d_gimnasio")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_d_gimnasio));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_d_golf")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_d_golf));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_d_piscina")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_d_piscina));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_d_polideportivo")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_d_polideportivo));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_d_skatepark")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_d_skatepark));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_d_tenis")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_d_tenis));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_z_otros")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_d_zotros));
                            }

                            markeroptions.title("deportes/" + anun.getNombre());
                            markeroptions.position(new LatLng(anun.getLatitud(), anun.getLongitud()));
                            tmpRealTimeMarkers.add(mMap.addMarker(markeroptions));

                        }

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        //MARCADORES EDUCACION

        if(tvpermanenteocultaBB.getText().toString().equals("todo")||(tvpermanenteocultaBB.getText().toString().equals("educacion"))) {

            dbRef.child("educacion").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ZAnuncio anun = snapshot.getValue(ZAnuncio.class);

                        if(tvfiltroocultaBB.getText().toString().contains(anun.getSubcategoria()) || tvfiltroocultaBB.getText().toString().equals("todo")) {

                            mMap.setInfoWindowAdapter
                                    (new ZTitleInvisible(BBMapaActivity.this) {
                                        @Override
                                        public boolean onTouch(View view, MotionEvent motionEvent) {
                                            return false;
                                        }
                                    });

                            MarkerOptions markeroptions = new MarkerOptions();
                            if (anun.getSubcategoria().equals("ic_sub_e_academia")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_e_academia));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_e_autoescuela")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_e_autoescuela));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_e_biblioteca")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_e_biblioteca));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_e_colegio")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_e_colegio));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_e_guarderia")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_e_guarderia));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_e_instituto")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_e_instituto));
                            }


                            markeroptions.title("educacion/" + anun.getNombre());
                            markeroptions.position(new LatLng(anun.getLatitud(), anun.getLongitud()));
                            tmpRealTimeMarkers.add(mMap.addMarker(markeroptions));

                        }

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        //MARCADORES HOTELES

        if(tvpermanenteocultaBB.getText().toString().equals("todo")||(tvpermanenteocultaBB.getText().toString().equals("hoteles"))) {

            dbRef.child("hoteles").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ZAnuncio anun = snapshot.getValue(ZAnuncio.class);

                        if(tvfiltroocultaBB.getText().toString().contains(anun.getSubcategoria()) || tvfiltroocultaBB.getText().toString().equals("todo")) {

                            mMap.setInfoWindowAdapter
                                    (new ZTitleInvisible(BBMapaActivity.this) {
                                        @Override
                                        public boolean onTouch(View view, MotionEvent motionEvent) {
                                            return false;
                                        }
                                    });

                            MarkerOptions markeroptions = new MarkerOptions();
                            if (anun.getSubcategoria().equals("ic_sub_h_hostal")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_h_hostal));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_h_hotel")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_h_hotel));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_h_pension")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_h_pension));
                            }

                            markeroptions.title("hoteles/" + anun.getNombre());
                            markeroptions.position(new LatLng(anun.getLatitud(), anun.getLongitud()));
                            tmpRealTimeMarkers.add(mMap.addMarker(markeroptions));

                        }

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        //MARCADORES INMOBILIARIA

        if(tvpermanenteocultaBB.getText().toString().equals("todo")||(tvpermanenteocultaBB.getText().toString().equals("inmobiliaria"))) {

            dbRef.child("inmobiliaria").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ZAnuncio anun = snapshot.getValue(ZAnuncio.class);

                        if(tvfiltroocultaBB.getText().toString().contains(anun.getSubcategoria()) || tvfiltroocultaBB.getText().toString().equals("todo")) {

                            mMap.setInfoWindowAdapter
                                    (new ZTitleInvisible(BBMapaActivity.this) {
                                        @Override
                                        public boolean onTouch(View view, MotionEvent motionEvent) {
                                            return false;
                                        }
                                    });

                            MarkerOptions markeroptions = new MarkerOptions();
                            markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_inmobiliaria));

                            markeroptions.title("inmobiliaria/" + anun.getNombre());
                            markeroptions.position(new LatLng(anun.getLatitud(), anun.getLongitud()));
                            tmpRealTimeMarkers.add(mMap.addMarker(markeroptions));

                        }

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        //MARCADORES INSTITUCIONES

        if(tvpermanenteocultaBB.getText().toString().equals("todo")||(tvpermanenteocultaBB.getText().toString().equals("instituciones"))) {

            dbRef.child("instituciones").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ZAnuncio anun = snapshot.getValue(ZAnuncio.class);

                        if(tvfiltroocultaBB.getText().toString().contains(anun.getSubcategoria()) || tvfiltroocultaBB.getText().toString().equals("todo")) {

                            mMap.setInfoWindowAdapter
                                    (new ZTitleInvisible(BBMapaActivity.this) {
                                        @Override
                                        public boolean onTouch(View view, MotionEvent motionEvent) {
                                            return false;
                                        }
                                    });

                            MarkerOptions markeroptions = new MarkerOptions();
                            if (anun.getSubcategoria().equals("ic_sub_i_ayuntamiento")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_i_ayuntamiento));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_i_correos")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_i_correos));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_i_juzgados")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_i_juzgados));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_i_omic")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_i_omic));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_i_turismo")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_i_turismo));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_z_otros")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_i_zotros));
                            }

                            markeroptions.title("instituciones/" + anun.getNombre());
                            markeroptions.position(new LatLng(anun.getLatitud(), anun.getLongitud()));
                            tmpRealTimeMarkers.add(mMap.addMarker(markeroptions));

                        }

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        //MARCADORES MONUMENTOS

        if(tvpermanenteocultaBB.getText().toString().equals("todo")||(tvpermanenteocultaBB.getText().toString().equals("monumentos"))) {

            dbRef.child("monumentos").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ZAnuncio anun = snapshot.getValue(ZAnuncio.class);

                        if(tvfiltroocultaBB.getText().toString().contains(anun.getSubcategoria()) || tvfiltroocultaBB.getText().toString().equals("todo")) {

                            mMap.setInfoWindowAdapter
                                    (new ZTitleInvisible(BBMapaActivity.this) {
                                        @Override
                                        public boolean onTouch(View view, MotionEvent motionEvent) {
                                            return false;
                                        }
                                    });

                            MarkerOptions markeroptions = new MarkerOptions();
                            if (anun.getSubcategoria().equals("ic_sub_m_castillo")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_m_castillo));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_m_estatua")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_m_estatua));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_m_iglesia")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_m_iglesia));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_m_museo")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_m_museo));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_m_plaza_de_toros")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_m_plaza_de_toros));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_m_puente")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_m_puente));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_m_ruinas")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_m_ruinas));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_m_torre")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_m_torre));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_z_otros")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_m_zotros));
                            }

                            markeroptions.title("monumentos/" + anun.getNombre());
                            markeroptions.position(new LatLng(anun.getLatitud(), anun.getLongitud()));
                            tmpRealTimeMarkers.add(mMap.addMarker(markeroptions));

                        }

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        //MARCADORES OCIO

        if(tvpermanenteocultaBB.getText().toString().equals("todo")||(tvpermanenteocultaBB.getText().toString().equals("ocio"))) {

            dbRef.child("ocio").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ZAnuncio anun = snapshot.getValue(ZAnuncio.class);

                        if(tvfiltroocultaBB.getText().toString().contains(anun.getSubcategoria()) || tvfiltroocultaBB.getText().toString().equals("todo")) {

                            mMap.setInfoWindowAdapter
                                    (new ZTitleInvisible(BBMapaActivity.this) {
                                        @Override
                                        public boolean onTouch(View view, MotionEvent motionEvent) {
                                            return false;
                                        }
                                    });

                            MarkerOptions markeroptions = new MarkerOptions();
                            if (anun.getSubcategoria().equals("ic_sub_o_bingo")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_o_bingo));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_o_cine")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_o_cine));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_o_discoteca")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_o_discoteca));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_o_karaoke")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_o_karaoke));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_o_paintball")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_o_paintball));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_o_parque_infantil")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_o_parque_infantil));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_o_scaperoom")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_o_scaperoom));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_o_teatro")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_o_teatro));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_z_otros")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_o_zotros));
                            }

                            markeroptions.title("ocio/" + anun.getNombre());
                            markeroptions.position(new LatLng(anun.getLatitud(), anun.getLongitud()));
                            tmpRealTimeMarkers.add(mMap.addMarker(markeroptions));

                        }

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        //MARCADORES PARQUE

        if(tvpermanenteocultaBB.getText().toString().equals("todo")||(tvpermanenteocultaBB.getText().toString().equals("parque"))) {

            dbRef.child("parque").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ZAnuncio anun = snapshot.getValue(ZAnuncio.class);

                        if(tvfiltroocultaBB.getText().toString().contains(anun.getSubcategoria()) || tvfiltroocultaBB.getText().toString().equals("todo")) {

                            mMap.setInfoWindowAdapter
                                    (new ZTitleInvisible(BBMapaActivity.this) {
                                        @Override
                                        public boolean onTouch(View view, MotionEvent motionEvent) {
                                            return false;
                                        }
                                    });

                            MarkerOptions markeroptions = new MarkerOptions();
                            if (anun.getSubcategoria().equals("ic_sub_pa_jardines")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_pa_jardines));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_pa_parque")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_pa_parque));
                            }


                            markeroptions.title("parque/" + anun.getNombre());
                            markeroptions.position(new LatLng(anun.getLatitud(), anun.getLongitud()));
                            tmpRealTimeMarkers.add(mMap.addMarker(markeroptions));

                        }

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        //MARCADORES PLAYA

        if(tvpermanenteocultaBB.getText().toString().equals("todo")||(tvpermanenteocultaBB.getText().toString().equals("playa"))) {

            dbRef.child("playa").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ZAnuncio anun = snapshot.getValue(ZAnuncio.class);

                        if(tvfiltroocultaBB.getText().toString().contains(anun.getSubcategoria()) || tvfiltroocultaBB.getText().toString().equals("todo")) {

                            mMap.setInfoWindowAdapter
                                    (new ZTitleInvisible(BBMapaActivity.this) {
                                        @Override
                                        public boolean onTouch(View view, MotionEvent motionEvent) {
                                            return false;
                                        }
                                    });

                            MarkerOptions markeroptions = new MarkerOptions();
                            markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_playa));

                            markeroptions.title("playa/" + anun.getNombre());
                            markeroptions.position(new LatLng(anun.getLatitud(), anun.getLongitud()));
                            tmpRealTimeMarkers.add(mMap.addMarker(markeroptions));

                        }

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        //MARCADORES RESTAURACION

        if(tvpermanenteocultaBB.getText().toString().equals("todo")||(tvpermanenteocultaBB.getText().toString().equals("restauracion"))) {

            dbRef.child("restauracion").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ZAnuncio anun = snapshot.getValue(ZAnuncio.class);

                        if(tvfiltroocultaBB.getText().toString().contains(anun.getSubcategoria()) || tvfiltroocultaBB.getText().toString().equals("todo")) {

                            mMap.setInfoWindowAdapter
                                    (new ZTitleInvisible(BBMapaActivity.this) {
                                        @Override
                                        public boolean onTouch(View view, MotionEvent motionEvent) {
                                            return false;
                                        }
                                    });

                            MarkerOptions markeroptions = new MarkerOptions();
                            if (anun.getSubcategoria().equals("ic_sub_r_asador")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_r_asador));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_r_bar")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_r_bar));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_r_cafeteria")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_r_cafeteria));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_r_cerveceria")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_r_cerveceria));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_r_chiringuito")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_r_chiringuito));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_r_hamburguesa")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_r_hamburguesa));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_r_heladeria")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_r_heladeria));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_r_pizzeria")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_r_pizzeria));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_r_pub")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_r_pub));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_r_restaurante")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_r_restaurante));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_z_otros")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_r_zotros));
                            }

                            markeroptions.title("restauracion/" + anun.getNombre());
                            markeroptions.position(new LatLng(anun.getLatitud(), anun.getLongitud()));
                            tmpRealTimeMarkers.add(mMap.addMarker(markeroptions));

                        }

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        //MARCADORES SALUD

        if(tvpermanenteocultaBB.getText().toString().equals("todo")||(tvpermanenteocultaBB.getText().toString().equals("salud"))) {

            dbRef.child("salud").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ZAnuncio anun = snapshot.getValue(ZAnuncio.class);

                        if(tvfiltroocultaBB.getText().toString().contains(anun.getSubcategoria()) || tvfiltroocultaBB.getText().toString().equals("todo")) {

                            mMap.setInfoWindowAdapter
                                    (new ZTitleInvisible(BBMapaActivity.this) {
                                        @Override
                                        public boolean onTouch(View view, MotionEvent motionEvent) {
                                            return false;
                                        }
                                    });

                            MarkerOptions markeroptions = new MarkerOptions();
                            if (anun.getSubcategoria().equals("ic_sub_sa_ambulatorio")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_sa_ambulatorio));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_sa_dentista")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_sa_dentista));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_sa_dietista")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_sa_dietista));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_sa_farmacia")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_sa_farmacia));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_sa_fisioterapia")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_sa_fisioterapia));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_sa_optica")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_sa_optica));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_sa_podologo")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_sa_podologo));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_sa_psicologia")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_sa_psicologia));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_sa_veterinario")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_sa_veterinario));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_z_otros")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_sa_zotros));
                            }

                            markeroptions.title("salud/" + anun.getNombre());
                            markeroptions.position(new LatLng(anun.getLatitud(), anun.getLongitud()));
                            tmpRealTimeMarkers.add(mMap.addMarker(markeroptions));

                        }

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        //MARCADORES SEGURIDAD

        if(tvpermanenteocultaBB.getText().toString().equals("todo")||(tvpermanenteocultaBB.getText().toString().equals("seguridad"))) {

            dbRef.child("seguridad").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ZAnuncio anun = snapshot.getValue(ZAnuncio.class);

                        if(tvfiltroocultaBB.getText().toString().contains(anun.getSubcategoria()) || tvfiltroocultaBB.getText().toString().equals("todo")) {

                            mMap.setInfoWindowAdapter
                                    (new ZTitleInvisible(BBMapaActivity.this) {
                                        @Override
                                        public boolean onTouch(View view, MotionEvent motionEvent) {
                                            return false;
                                        }
                                    });

                            MarkerOptions markeroptions = new MarkerOptions();
                            if (anun.getSubcategoria().equals("ic_sub_seg_guardia_civil")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_seg_guardia_civil));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_seg_policia_local")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_seg_policia_local));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_seg_policia_nacional")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_seg_policia_nacional));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_seg_proteccion")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_seg_proteccion));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_seg_seguridad")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_seg_seguridad));
                            }


                            markeroptions.title("seguridad/" + anun.getNombre());
                            markeroptions.position(new LatLng(anun.getLatitud(), anun.getLongitud()));
                            tmpRealTimeMarkers.add(mMap.addMarker(markeroptions));

                        }

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        //MARCADORES SERVICIOS

        if(tvpermanenteocultaBB.getText().toString().equals("todo")||(tvpermanenteocultaBB.getText().toString().equals("servicios"))) {

            dbRef.child("servicios").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ZAnuncio anun = snapshot.getValue(ZAnuncio.class);

                        if(tvfiltroocultaBB.getText().toString().contains(anun.getSubcategoria()) || tvfiltroocultaBB.getText().toString().equals("todo")) {

                            mMap.setInfoWindowAdapter
                                    (new ZTitleInvisible(BBMapaActivity.this) {
                                        @Override
                                        public boolean onTouch(View view, MotionEvent motionEvent) {
                                            return false;
                                        }
                                    });

                            MarkerOptions markeroptions = new MarkerOptions();
                            if (anun.getSubcategoria().equals("ic_sub_se_autolavado_coche")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_se_autolavado_coche));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_se_banca")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_se_banca));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_se_fotografo")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_se_fotografo));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_se_gasolinera")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_se_gasolinera));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_se_imprenta")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_se_imprenta));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_se_juridico")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_se_juridico));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_se_limpieza")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_se_limpieza));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_se_papeleria")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_se_papeleria));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_se_peluqueria")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_se_peluqueria));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_se_pintor")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_se_pintor));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_se_plagas")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_se_plagas));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_se_reformas")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_se_reformas));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_se_taller")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_se_taller));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_se_tatuaje")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_se_tatuaje));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_se_tintoreria")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_se_tintoreria));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_se_transportes")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_se_transportes));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_se_viajes")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_se_viajes));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_z_otros")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_se_zotros));
                            }

                            markeroptions.title("servicios/" + anun.getNombre());
                            markeroptions.position(new LatLng(anun.getLatitud(), anun.getLongitud()));
                            tmpRealTimeMarkers.add(mMap.addMarker(markeroptions));

                        }

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        //MARCADORES TRANSPORTE

        if(tvpermanenteocultaBB.getText().toString().equals("todo")||(tvpermanenteocultaBB.getText().toString().equals("transporte"))) {

            dbRef.child("transporte").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ZAnuncio anun = snapshot.getValue(ZAnuncio.class);

                        if(tvfiltroocultaBB.getText().toString().contains(anun.getSubcategoria()) || tvfiltroocultaBB.getText().toString().equals("todo")) {

                            mMap.setInfoWindowAdapter
                                    (new ZTitleInvisible(BBMapaActivity.this) {
                                        @Override
                                        public boolean onTouch(View view, MotionEvent motionEvent) {
                                            return false;
                                        }
                                    });

                            MarkerOptions markeroptions = new MarkerOptions();
                            if (anun.getSubcategoria().equals("ic_sub_t_aeropuerto")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_t_aeropuerto));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_t_estacion")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_t_estacion));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_t_estacion_tren")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_t_estacion_tren));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_t_parada_bus")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_t_parada_bus));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_t_puerto")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_t_puerto));
                            }
                            if (anun.getSubcategoria().equals("ic_sub_t_taxi")) {
                                markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_t_taxi));
                            }


                            markeroptions.title("transporte/" + anun.getNombre());
                            markeroptions.position(new LatLng(anun.getLatitud(), anun.getLongitud()));
                            tmpRealTimeMarkers.add(mMap.addMarker(markeroptions));

                        }

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }





        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String idmarcador = marker.getTitle().toString();
                tvidmarcadorBB.setText(idmarcador);
                pasarinfo();


                return false;
            }
        });

        //ESTILO DEL MAPA
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.mapstyle));

            if (!success) {
                Log.e("BBMapaActivity", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("BBMapaActivity", "Can't find style. Error: ", e);
        }


    }



    private void pasarinfo() {

        //DIALOGO INFO ANUNCIO
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.activity_adaptadormapa);

        //CAMBIAR TAMAÑO DEL DIALOGO
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);

        //dialog.getWindow().setLayout(650, 1250);
        //dialog.getWindow().setLayout(R.dimen.dialogAncho, R.dimen.dialogAlto);


        dbRef = FirebaseDatabase.getInstance().getReference().child(tvidmarcadorBB.getText().toString());

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //VINCULAR ELEMENTOS CON DIALOG
                anuncio = dataSnapshot.getValue(ZAnuncio.class);
                final TextView nombre = (TextView) dialog.findViewById(R.id.tvempresaADAPTADOR);
                final TextView descripcionlarga = (TextView) dialog.findViewById(R.id.tvdescripempresaADAPTADOR);
                final TextView descuento = (TextView) dialog.findViewById(R.id.tvinformacionadicionalADAPTADOR);
                final TextView direccion = (TextView) dialog.findViewById(R.id.tvdireccionADAPTADOR);
                final TextView extra = (TextView) dialog.findViewById(R.id.tvimagextraADAPTADOR);
                final TextView facebook = (TextView) dialog.findViewById(R.id.tvimagfacebookADAPTADOR);
                final TextView horario = (TextView) dialog.findViewById(R.id.tvhorarioADAPTADOR);
                final TextView mail = (TextView) dialog.findViewById(R.id.tvimagmailADAPTADOR);
                final TextView maps = (TextView) dialog.findViewById(R.id.tvimagmapADAPTADOR);
                final TextView telefono = (TextView) dialog.findViewById(R.id.tvimagtlfADAPTADOR);
                final TextView twitter = (TextView) dialog.findViewById(R.id.tvimagtwitterADAPTADOR);
                final TextView idioma = (TextView) dialog.findViewById(R.id.tvidiomaADAPTADOR);

                final ImageView imagenADAPTADOR = (ImageView) dialog.findViewById(R.id.imagempresaADAPTADOR);
                final ImageView btnimagfacebookADAPTADOR = (ImageView) dialog.findViewById(R.id.btnimagfacebookADAPTADOR);
                final ImageView btnimagtwitterADAPTADOR = (ImageView) dialog.findViewById(R.id.btnimagtwitterADAPTADOR);
                final ImageView btnimagtlfADAPTADOR = (ImageView) dialog.findViewById(R.id.btnimagtlfADAPTADOR);
                final ImageView btnimagmailADAPTADOR = (ImageView) dialog.findViewById(R.id.btnimagmailADAPTADOR);
                final ImageView btnimagmapADAPTADOR = (ImageView) dialog.findViewById(R.id.btnimagmapADAPTADOR);
                final ImageView btnimagextraADAPTADOR = (ImageView) dialog.findViewById(R.id.btnimagextraADAPTADOR);

                final LinearLayout btnlinearlayoutcerrarADAPTADOR = (LinearLayout) dialog.findViewById(R.id.btnlinearlayoutcerrarADAPTADOR);

                nombre.setText(anuncio.getNombre());
                descripcionlarga.setText(anuncio.getDescripcionlargaes());
                descuento.setText(anuncio.getDescuentoes());
                direccion.setText(anuncio.getDireccion());
                extra.setText(anuncio.getExtra());
                facebook.setText(anuncio.getFacebook());
                telefono.setText(anuncio.getTelefono());
                horario.setText(anuncio.getHorarioes());
                mail.setText(anuncio.getMail());
                maps.setText(anuncio.getMaps());
                telefono.setText(anuncio.getTelefono());
                twitter.setText(anuncio.getTwitter());

                if(idioma.getText().toString().equals("es")){
                    descuento.setText(anuncio.getDescuentoes());
                    descripcionlarga.setText(anuncio.getDescripcionlargaes());
                    horario.setText(anuncio.getHorarioes());
                }
                if(idioma.getText().toString().equals("en")){
                    descuento.setText(anuncio.getDescuentoen());
                    descripcionlarga.setText(anuncio.getDescripcionlargaen());
                    horario.setText(anuncio.getHorarioen());
                }
                if(idioma.getText().toString().equals("de")){
                    descuento.setText(anuncio.getDescuentode());
                    descripcionlarga.setText(anuncio.getDescripcionlargade());
                    horario.setText(anuncio.getHorariode());
                }
                if(idioma.getText().toString().equals("fr")){
                    descuento.setText(anuncio.getDescuentofr());
                    descripcionlarga.setText(anuncio.getDescripcionlargafr());
                    horario.setText(anuncio.getHorariofr());
                }
                //DESCUENTO GONE SI ES NO
                if(descuento.getText().toString().equals("no")){

                    descuento.setVisibility(View.GONE);

                }
                //BOTONES REDES
                //COLORES DESHABILITADOS

                if(anuncio.getFacebook().equals("no")){

                    btnimagfacebookADAPTADOR.setBackgroundColor(getResources().getColor(R.color.deshabilitado));
                    btnimagfacebookADAPTADOR.setImageResource(R.drawable.redesc_facebook_disable);

                }
                if(anuncio.getTwitter().equals("no")){

                    btnimagtwitterADAPTADOR.setBackgroundColor(getResources().getColor(R.color.deshabilitado));
                    btnimagtwitterADAPTADOR.setImageResource(R.drawable.redesc_twitter_disable);

                }
                if(anuncio.getTelefono().equals("no")){

                    btnimagtlfADAPTADOR.setBackgroundColor(getResources().getColor(R.color.deshabilitado));
                    btnimagtlfADAPTADOR.setImageResource(R.drawable.redesc_telefono_disable);

                }
                if(anuncio.getMail().equals("no")){

                    btnimagmailADAPTADOR.setBackgroundColor(getResources().getColor(R.color.deshabilitado));
                    btnimagmailADAPTADOR.setImageResource(R.drawable.redesc_correo_disable);

                }
                if(anuncio.getExtra().equals("no")){

                    btnimagextraADAPTADOR.setBackgroundColor(getResources().getColor(R.color.deshabilitado));
                    btnimagextraADAPTADOR.setImageResource(R.drawable.redesc_extra_disable);

                }

                //FACEBOOK
                btnimagfacebookADAPTADOR.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                String AFACE = getString(R.string.NoTieneFacebook);
                                String Facebook = facebook.getText().toString();
                                String Nombre = nombre.getText().toString();
                                if (Facebook.equals("no")) {
                                    Toast.makeText(getApplicationContext(),
                                            Nombre + AFACE,
                                            Toast.LENGTH_LONG).show();

                                } else {
                                    Uri uri = Uri.parse(Facebook);
                                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                    startActivity(intent);
                                }
                            }
                        });

                //TWITTER
                btnimagtwitterADAPTADOR.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                String ATW = getString(R.string.NoTieneTwitter);
                                String Twitter = twitter.getText().toString();
                                String Nombre = nombre.getText().toString();
                                if (Twitter.equals("no")) {
                                    Toast.makeText(getApplicationContext(),
                                            Nombre + ATW,
                                            Toast.LENGTH_LONG).show();

                                } else {
                                    Uri uri = Uri.parse(Twitter);
                                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                    startActivity(intent);
                                }
                            }
                        });

                //TELÉFONO
                btnimagtlfADAPTADOR.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                String ATELF = getString(R.string.NoTieneContacto);
                                String Telefono = telefono.getText().toString();
                                String Nombre = nombre.getText().toString();
                                if (Telefono.equals("no")) {
                                    Toast.makeText(getApplicationContext(),
                                            Nombre + ATELF,
                                            Toast.LENGTH_LONG).show();

                                } else {
                                    try {
                                        Uri number = Uri.parse("tel:" + Telefono);
                                        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                                        startActivity(callIntent);
                                    } catch (android.content.ActivityNotFoundException ex) {
                                        Toast.makeText(BBMapaActivity.this,
                                                R.string.NoEncuentraLlamar, Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        });

                //MAIL
                btnimagmailADAPTADOR.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                String AMAIL = getString(R.string.NoTieneMail);
                                String Mail = mail.getText().toString();
                                String Nombre = nombre.getText().toString();
                                if (Mail.equals("no")) {
                                    Toast.makeText(getApplicationContext(),
                                            Nombre + AMAIL,
                                            Toast.LENGTH_LONG).show();

                                } else {
                                    String[] TO = {Mail};
                                    String[] CC = {""};
                                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                                    emailIntent.setData(Uri.parse("mailto:"));
                                    emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                                    emailIntent.putExtra(Intent.EXTRA_CC, CC);
                                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                                    emailIntent.putExtra(Intent.EXTRA_TEXT, "");

                                    try {
                                        startActivity(Intent.createChooser(emailIntent, "Enviar email..."));
                                    } catch (android.content.ActivityNotFoundException ex) {
                                        Toast.makeText(BBMapaActivity.this,
                                                R.string.NoEncuentraEmail, Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        });

                //MAPS
                btnimagmapADAPTADOR.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String AMAPA = getString(R.string.NoEncuentraLocalizacion);
                                String Maps = maps.getText().toString();
                                String Nombre = nombre.getText().toString();
                                if (Maps.equals("no")) {
                                    Toast.makeText(getApplicationContext(),
                                            Nombre + AMAPA,
                                            Toast.LENGTH_LONG).show();

                                } else {
                                    Uri uri = Uri.parse(Maps);
                                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                    startActivity(intent);
                                }
                            }
                        });

                //EXTRA
                btnimagextraADAPTADOR.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                String AEXTRA = getString(R.string.NoTieneInformacion);
                                String Extra = extra.getText().toString();
                                String Nombre = nombre.getText().toString();
                                if (Extra.equals("no")) {
                                    Toast.makeText(getApplicationContext(),
                                            Nombre + AEXTRA,
                                            Toast.LENGTH_LONG).show();

                                } else {
                                    Uri uri = Uri.parse(Extra);
                                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                    startActivity(intent);
                                }
                            }
                        });

                //VINCULAR GLIDE CON IMAGEN
                String imagentienda = anuncio.getImagen();
                Glide.with(getApplicationContext()).load(imagentienda).into(imagenADAPTADOR);

                btnlinearlayoutcerrarADAPTADOR.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("LoginActivity", "DATABASE ERROR");
            }
        };
        dbRef.addValueEventListener(valueEventListener);
        dialog.show();

    }

    //PROTOCOLO PARA CONVERTIR SVG A MAPA DE BIT
    private BitmapDescriptor getBitmapDescriptor(int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            VectorDrawable vectorDrawable = (VectorDrawable) getDrawable(id);

            int h = vectorDrawable.getIntrinsicHeight();
            int w = vectorDrawable.getIntrinsicWidth();

            vectorDrawable.setBounds(0, 0, w, h);

            Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bm);
            vectorDrawable.draw(canvas);

            return BitmapDescriptorFactory.fromBitmap(bm);

        } else {
            return BitmapDescriptorFactory.fromResource(id);
        }
    }

    //PROTOCOLO LOCALIZACIÓN GPS
    private void locationStart() {
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
        Local.setMainActivity(this);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) Local);

    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationStart();
                return;
            }
        }
    }

    public void setLocation(Location loc) {
        //Obtener la direccion de la calle a partir de la latitud y la longitud
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);

                if (!list.isEmpty()) {
                    CameraUpdate locactual =
                            CameraUpdateFactory.newLatLngZoom(new LatLng(loc.getLatitude(), loc.getLongitude()), 17);
                    mMap.animateCamera(locactual);



                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /* Aqui empieza la Clase Localizacion */
    public class Localizacion implements LocationListener {
        BBMapaActivity baMapaFinalActivity;

        public BBMapaActivity getBaMapaFinalActivity() {
            return baMapaFinalActivity;
        }

        public void setMainActivity(BBMapaActivity baMapaFinalActivity) {
            this.baMapaFinalActivity = baMapaFinalActivity;
        }

        @Override
        public void onLocationChanged(Location loc) {
            // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
            // debido a la deteccion de un cambio de ubicacion

            loc.getLatitude();
            loc.getLongitude();


            this.baMapaFinalActivity.setLocation(loc);


        }

        @Override
        public void onProviderDisabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es desactivado
            //mensaje1.setText("GPS Desactivado");

        }

        @Override
        public void onProviderEnabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es activado
            //mensaje1.setText("GPS Activado");

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case LocationProvider.AVAILABLE:
                    Log.d("debug", "LocationProvider.AVAILABLE");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                    break;
            }
        }
    }

    //TODO cosa de poner marcadores sin lat y long
    /*
    try {
            String direccionnegocioCA = etdireccionnegocioAutoCA.getText().toString();

            List<Address> addressList = geocoder.getFromLocationName(
                    direccionnegocioCA, 5);
            if (addressList != null && addressList.size() > 0) {
                Double lat = (Double) (addressList.get(0).getLatitude() );
                Double lng = (Double) (addressList.get(0).getLongitude() );
                String latitudint = Double.toString(lat);
                tvocultolatitudCA.setText(latitudint);
                String longitudint = Double.toString(lng);
                tvocultolongitudCA.setText(longitudint);

            }
        }catch (Exception e) {
            e.printStackTrace();
        }
     */
    /*String latfin = tvlatitudocultaBB.getText().toString();
                    String lngfin = tvlongitudocultaBB.getText().toString();
                    Double latdob = Double.parseDouble(latfin);
                    Double lngdob = Double.parseDouble(lngfin);*/


}
