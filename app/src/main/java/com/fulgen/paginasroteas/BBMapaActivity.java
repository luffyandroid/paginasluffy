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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

    //VARIANTES DE DECLARADAS
    private FloatingActionsMenu menu_fabBB;
    TextView tvlatitudocultaBB, tvlongitudocultaBB, tvidmarcadorBB;

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
        dbRef = FirebaseDatabase.getInstance().getReference();


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

        Intent mainIntent = new Intent().setClass(
                BBMapaActivity.this, BBMapaActivity.class);
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

        Intent mainIntent = new Intent().setClass(
                BBMapaActivity.this, BBMapaActivity.class);
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

        Intent mainIntent = new Intent().setClass(
                BBMapaActivity.this, BBMapaActivity.class);
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

        Intent mainIntent = new Intent().setClass(
                BBMapaActivity.this, BBMapaActivity.class);
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

        //MOSTAR DIALOGO
        dialog.show();

        //PARA QUE SE CIERRE AL PULSAR
        menu_fabBB.collapse();
    }

    public void clickMenuBB(View v) {
        Intent mainIntent = new Intent().setClass(
                BBMapaActivity.this, BAMenuActivity.class);
        startActivity(mainIntent);
        //PARA QUE SE CIERRE AL PULSAR
        menu_fabBB.collapse();
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent().setClass(this,BAMenuActivity.class);
        startActivity(i);
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
        dbRef.child("alimentacion").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ZAnuncio anun = snapshot.getValue(ZAnuncio.class);

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
                     markeroptions.title("alimentacion/"+anun.getNombre());
                     markeroptions.position(new LatLng(anun.getLatitud(), anun.getLongitud()));
                     tmpRealTimeMarkers.add(mMap.addMarker(markeroptions));

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //MARCADORES ASOCIACIONES
        dbRef.child("asociaciones").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ZAnuncio anun = snapshot.getValue(ZAnuncio.class);

                    mMap.setInfoWindowAdapter
                            (new ZTitleInvisible(BBMapaActivity.this) {
                                @Override
                                public boolean onTouch(View view, MotionEvent motionEvent) {
                                    return false;
                                }
                            });

                    MarkerOptions markeroptions = new MarkerOptions();
                    markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_asociaciones));

                    markeroptions.title("asociaciones/"+anun.getNombre());
                    markeroptions.position(new LatLng(anun.getLatitud(), anun.getLongitud()));
                    tmpRealTimeMarkers.add(mMap.addMarker(markeroptions));

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //MARCADORES COMPRAS
        dbRef.child("compras").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ZAnuncio anun = snapshot.getValue(ZAnuncio.class);

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

                    markeroptions.title("compras/"+anun.getNombre());
                    markeroptions.position(new LatLng(anun.getLatitud(), anun.getLongitud()));
                    tmpRealTimeMarkers.add(mMap.addMarker(markeroptions));

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //MARCADORES DEPORTES
        dbRef.child("deportes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ZAnuncio anun = snapshot.getValue(ZAnuncio.class);

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

                    markeroptions.title("deportes/"+anun.getNombre());
                    markeroptions.position(new LatLng(anun.getLatitud(), anun.getLongitud()));
                    tmpRealTimeMarkers.add(mMap.addMarker(markeroptions));

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //MARCADORES EDUCACION
        dbRef.child("educacion").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ZAnuncio anun = snapshot.getValue(ZAnuncio.class);

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


                    markeroptions.title("educacion/"+anun.getNombre());
                    markeroptions.position(new LatLng(anun.getLatitud(), anun.getLongitud()));
                    tmpRealTimeMarkers.add(mMap.addMarker(markeroptions));

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //MARCADORES HOTELES
        dbRef.child("hoteles").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ZAnuncio anun = snapshot.getValue(ZAnuncio.class);

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

                    markeroptions.title("hoteles/"+anun.getNombre());
                    markeroptions.position(new LatLng(anun.getLatitud(), anun.getLongitud()));
                    tmpRealTimeMarkers.add(mMap.addMarker(markeroptions));

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //MARCADORES INMOBILIARIA
        dbRef.child("inmobiliaria").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ZAnuncio anun = snapshot.getValue(ZAnuncio.class);

                    mMap.setInfoWindowAdapter
                            (new ZTitleInvisible(BBMapaActivity.this) {
                                @Override
                                public boolean onTouch(View view, MotionEvent motionEvent) {
                                    return false;
                                }
                            });

                    MarkerOptions markeroptions = new MarkerOptions();
                    markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_inmobiliaria));

                    markeroptions.title("inmobiliaria/"+anun.getNombre());
                    markeroptions.position(new LatLng(anun.getLatitud(), anun.getLongitud()));
                    tmpRealTimeMarkers.add(mMap.addMarker(markeroptions));

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //MARCADORES INSTITUCIONES
        dbRef.child("instituciones").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ZAnuncio anun = snapshot.getValue(ZAnuncio.class);

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

                    markeroptions.title("instituciones/"+anun.getNombre());
                    markeroptions.position(new LatLng(anun.getLatitud(), anun.getLongitud()));
                    tmpRealTimeMarkers.add(mMap.addMarker(markeroptions));

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //MARCADORES MONUMENTOS
        dbRef.child("monumentos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ZAnuncio anun = snapshot.getValue(ZAnuncio.class);

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

                    markeroptions.title("monumentos/"+anun.getNombre());
                    markeroptions.position(new LatLng(anun.getLatitud(), anun.getLongitud()));
                    tmpRealTimeMarkers.add(mMap.addMarker(markeroptions));

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //MARCADORES OCIO
        dbRef.child("ocio").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ZAnuncio anun = snapshot.getValue(ZAnuncio.class);

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

                    markeroptions.title("ocio/"+anun.getNombre());
                    markeroptions.position(new LatLng(anun.getLatitud(), anun.getLongitud()));
                    tmpRealTimeMarkers.add(mMap.addMarker(markeroptions));

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //MARCADORES PARQUE
        dbRef.child("parque").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ZAnuncio anun = snapshot.getValue(ZAnuncio.class);

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


                    markeroptions.title("parque/"+anun.getNombre());
                    markeroptions.position(new LatLng(anun.getLatitud(), anun.getLongitud()));
                    tmpRealTimeMarkers.add(mMap.addMarker(markeroptions));

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //MARCADORES PLAYA
        dbRef.child("playa").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ZAnuncio anun = snapshot.getValue(ZAnuncio.class);

                    mMap.setInfoWindowAdapter
                            (new ZTitleInvisible(BBMapaActivity.this) {
                                @Override
                                public boolean onTouch(View view, MotionEvent motionEvent) {
                                    return false;
                                }
                            });

                    MarkerOptions markeroptions = new MarkerOptions();
                    markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_playa));

                    markeroptions.title("playa/"+anun.getNombre());
                    markeroptions.position(new LatLng(anun.getLatitud(), anun.getLongitud()));
                    tmpRealTimeMarkers.add(mMap.addMarker(markeroptions));

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //MARCADORES RESTAURACION
        dbRef.child("restauracion").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ZAnuncio anun = snapshot.getValue(ZAnuncio.class);

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

                    markeroptions.title("restauracion/"+anun.getNombre());
                    markeroptions.position(new LatLng(anun.getLatitud(), anun.getLongitud()));
                    tmpRealTimeMarkers.add(mMap.addMarker(markeroptions));

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //MARCADORES SALUD
        dbRef.child("salud").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ZAnuncio anun = snapshot.getValue(ZAnuncio.class);

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

                    markeroptions.title("salud/"+anun.getNombre());
                    markeroptions.position(new LatLng(anun.getLatitud(), anun.getLongitud()));
                    tmpRealTimeMarkers.add(mMap.addMarker(markeroptions));

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //MARCADORES SEGURIDAD
        dbRef.child("seguridad").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ZAnuncio anun = snapshot.getValue(ZAnuncio.class);

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


                    markeroptions.title("seguridad/"+anun.getNombre());
                    markeroptions.position(new LatLng(anun.getLatitud(), anun.getLongitud()));
                    tmpRealTimeMarkers.add(mMap.addMarker(markeroptions));

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //MARCADORES SERVICIOS
        dbRef.child("servicios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ZAnuncio anun = snapshot.getValue(ZAnuncio.class);

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

                    markeroptions.title("servicios/"+anun.getNombre());
                    markeroptions.position(new LatLng(anun.getLatitud(), anun.getLongitud()));
                    tmpRealTimeMarkers.add(mMap.addMarker(markeroptions));

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //MARCADORES TRANSPORTE
        dbRef.child("transporte").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ZAnuncio anun = snapshot.getValue(ZAnuncio.class);

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


                    markeroptions.title("transporte/"+anun.getNombre());
                    markeroptions.position(new LatLng(anun.getLatitud(), anun.getLongitud()));
                    tmpRealTimeMarkers.add(mMap.addMarker(markeroptions));

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String idmarcador = marker.getTitle().toString();
                tvidmarcadorBB.setText(idmarcador);
                pasarinfo();


                //Toast.makeText(BAMapaFinalActivity.this, idmarcador, Toast.LENGTH_LONG).show();
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

        /*// Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(36.6269284, -6.3531886);
        mMap.addMarker(new MarkerOptions()
                .position(sydney)
                //.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marcador_001))
                .icon(getBitmapDescriptor(R.drawable.ic_marcador_a_carniceria))
                .title("Prueba"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/

    }



    private void pasarinfo() {

        //DIALOGO INFO ANUNCIO
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.activity_adaptadormapa);

        dbRef = FirebaseDatabase.getInstance().getReference().child(tvidmarcadorBB.getText().toString());

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                anuncio = dataSnapshot.getValue(ZAnuncio.class);
                final TextView nombre = (TextView) dialog.findViewById(R.id.tvinfowindows_titulobb);
                final TextView detalles = (TextView) dialog.findViewById(R.id.tvinfowindows_detalles);
                final TextView salario = (TextView) dialog.findViewById(R.id.tvinfowindows_salario);
                final TextView direccion = (TextView) dialog.findViewById(R.id.tvinfowindows_direccion);
                final TextView telefono = (TextView) dialog.findViewById(R.id.tvinfowindows_telefono);
                final TextView correo = (TextView) dialog.findViewById(R.id.tvinfowindows_correo);
                final TextView idioma = (TextView) dialog.findViewById(R.id.tvinfowindows_idioma);
                final LinearLayout llprofesional = (LinearLayout) dialog.findViewById(R.id.LinearLayoutProfesion);

                nombre.setText(anuncio.getNombre());
                detalles.setText(anuncio.getDescuentoes());
                salario.setText(anuncio.getExtra());
                direccion.setText(anuncio.getDireccion());
                telefono.setText(anuncio.getTelefono());
                correo.setText(anuncio.getMail());
                if(idioma.equals("es")){
                    detalles.setText(anuncio.getDescuentoes());
                }
                if(idioma.equals("en")){
                    detalles.setText(anuncio.getDescuentoen());
                }
                if(idioma.equals("de")){
                    detalles.setText(anuncio.getDescuentode());
                }
                if(idioma.equals("fr")){
                    detalles.setText(anuncio.getDescuentofr());
                }


                //CAMBIAR DE COLOR EL BORDE SUPERIOR

                    /*if (anuncio.getTipopuesto().equals("Otros")) {
                        llprofesional.setBackgroundColor(Color.parseColor("#E84E1B"));
                    }
                    if (anuncio.getTipopuesto().equals("Administración")) {
                        llprofesional.setBackgroundColor(Color.parseColor("#009EE2"));
                    }
                    if (anuncio.getTipopuesto().equals("Agricultura")) {
                        llprofesional.setBackgroundColor(Color.parseColor("#DDDB00"));
                    }
                    if (anuncio.getTipopuesto().equals("Animación")) {
                        llprofesional.setBackgroundColor(Color.parseColor("#FF394C"));
                    }
                    if (anuncio.getTipopuesto().equals("Asistentes sociales")) {
                        llprofesional.setBackgroundColor(Color.parseColor("#009540"));
                    }
                    if (anuncio.getTipopuesto().equals("Atención al cliente")) {
                        llprofesional.setBackgroundColor(Color.parseColor("#F29100"));
                    }
                    if (anuncio.getTipopuesto().equals("Comercial")) {
                        llprofesional.setBackgroundColor(Color.parseColor("#312782"));
                    }
                    if (anuncio.getTipopuesto().equals("Construcción")) {
                        llprofesional.setBackgroundColor(Color.parseColor("#E84E1B"));
                    }
                    if (anuncio.getTipopuesto().equals("Cuidador de animales")) {
                        llprofesional.setBackgroundColor(Color.parseColor("#683B11"));
                    }
                    if (anuncio.getTipopuesto().equals("Diseño y Artes gráficas")) {
                        llprofesional.setBackgroundColor(Color.parseColor("#E61B72"));
                    }
                    if (anuncio.getTipopuesto().equals("Educación")) {
                        llprofesional.setBackgroundColor(Color.parseColor("#39A935"));
                    }
                    if (anuncio.getTipopuesto().equals("Hostelería")) {
                        llprofesional.setBackgroundColor(Color.parseColor("#120F51"));
                    }
                    if (anuncio.getTipopuesto().equals("Informática")) {
                        llprofesional.setBackgroundColor(Color.parseColor("#7FA50C"));
                    }
                    if (anuncio.getTipopuesto().equals("Ingeniería y técnico")) {
                        llprofesional.setBackgroundColor(Color.parseColor("#634E42"));
                    }
                    if (anuncio.getTipopuesto().equals("Legal")) {
                        llprofesional.setBackgroundColor(Color.parseColor("#7E4E24"));
                    }
                    if (anuncio.getTipopuesto().equals("Limpieza")) {
                        llprofesional.setBackgroundColor(Color.parseColor("#0685B7"));
                    }
                    if (anuncio.getTipopuesto().equals("Logística y almacén")) {
                        llprofesional.setBackgroundColor(Color.parseColor("#B17F49"));
                    }
                    if (anuncio.getTipopuesto().equals("Marketing y comunicaciones")) {
                        llprofesional.setBackgroundColor(Color.parseColor("#E20613"));
                    }
                    if (anuncio.getTipopuesto().equals("Música")) {
                        llprofesional.setBackgroundColor(Color.parseColor("#941B80"));
                    }
                    if (anuncio.getTipopuesto().equals("Profesiones y oficios")) {
                        llprofesional.setBackgroundColor(Color.parseColor("#868686"));
                    }
                    if (anuncio.getTipopuesto().equals("Sanidad")) {
                        llprofesional.setBackgroundColor(Color.parseColor("#BD1622"));
                    }
                    if (anuncio.getTipopuesto().equals("Transportes")) {
                        llprofesional.setBackgroundColor(Color.parseColor("#6F6F6E"));
                    }
                    if (anuncio.getTipopuesto().equals("Turismo")) {
                        llprofesional.setBackgroundColor(Color.parseColor("#E5332A"));
                    }
                    if (anuncio.getTipopuesto().equals("Venta")) {
                        llprofesional.setBackgroundColor(Color.parseColor("#A2195B"));
                    }*/




                final LinearLayout LayoutDetalles = (LinearLayout) dialog.findViewById(R.id.LayoutDetalless);
                final LinearLayout LayoutSalario = (LinearLayout) dialog.findViewById(R.id.LayoutSalarios);
                final LinearLayout LayoutDireccion = (LinearLayout) dialog.findViewById(R.id.LayoutDireccion);
                final LinearLayout LayoutTelefono = (LinearLayout) dialog.findViewById(R.id.LayoutTelefonos);
                final LinearLayout LayoutCorreo = (LinearLayout) dialog.findViewById(R.id.LayoutCorreos);

                LayoutDireccion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Uri location = Uri.parse("https://www.google.es/maps/@"+anuncio.getLatitud()+","+anuncio.getLongitud()+",20z");
                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
                            startActivity(mapIntent);
                        }catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(BBMapaActivity.this, "No hay aplicación para ver la dirección", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                LayoutTelefono.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {

                            Uri number = Uri.parse("tel:"+ telefono.getText().toString());
                            Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                            startActivity(callIntent);
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(BBMapaActivity.this,
                                    "No hay apliación para llamar", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                LayoutCorreo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String[] TO = {correo.getText().toString()}; //001 AQUI DEBERÍA IR CORREO
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
                            Toast.makeText(BBMapaActivity.this,
                                    "No tienes clientes de email instalados.", Toast.LENGTH_LONG).show();
                        }
                    }
                });



                if (detalles.equals("") ){
                    LayoutDetalles.setVisibility(View.GONE);
                }

                if (salario.equals("") ){
                    LayoutSalario.setVisibility(View.GONE);
                }

                if (telefono.equals("") ){
                    LayoutTelefono.setVisibility(View.GONE);
                }

                if (correo.equals("") ){
                    LayoutCorreo.setVisibility(View.GONE);
                }



                /*Button compartir = (Button) dialog.findViewById(R.id.btninfowindows_compartirBB);
                compartir.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                final String detalles_f, salario_f, telefono_f, correo_f;


                                Intent compartir = new Intent(android.content.Intent.ACTION_SEND);
                                compartir.setType("text/plain");
                                //compartir.setType("text/plain");
                                String detalles_ = detalles.getText().toString();
                                String salario_ = salario.getText().toString();
                                String direccion_ = direccion.getText().toString();
                                String telefono_ = telefono.getText().toString();
                                String correo_ = correo.getText().toString();
                                String nombre_ = nombre.getText().toString();

                                //EN CASO DE QUE NO HAYA DATOS NO MANDE DATOS VACÍOS

                                if (detalles_.equals("") ){
                                    detalles_f = detalles_;}
                                else { detalles_f = "\uD83D\uDD38" + " " + detalles_ + "\n";
                                }

                                if (salario_.equals("")){
                                    salario_f = salario_;}
                                else { salario_f = "\uD83D\uDCB6" + " " + "salario: " + salario_ + "\n";
                                }

                                if (telefono_.equals("") ){
                                    telefono_f = telefono_;}
                                else { telefono_f = "\uD83D\uDCDE" + " " + telefono_ + "\n";
                                }

                                if (correo_.equals("") ){
                                    correo_f = correo_;}
                                else { correo_f = "\uD83D\uDCE7" + " " + correo_ + "\n";
                                }

                                String en = "Enviado desde GeoWork";

                                //TEXTO CON ESTILO

                                compartir.putExtra(android.content.Intent.EXTRA_SUBJECT, "Compartir oferta de" + nombre_);
                                compartir.putExtra(android.content.Intent.EXTRA_TEXT, ("Empleo de " + nombre_ + "\n"
                                        + detalles_f
                                        + salario_f
                                        + "\uD83D\uDCCD" + " " + direccion_ + "\n"
                                        + telefono_f
                                        + correo_f
                                        + en));
                                //compartir.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtm"Oferta de " + nombre_ );
                                startActivity(Intent.createChooser(compartir, "Compartir vía"));

                            }



                        });*/
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


}
