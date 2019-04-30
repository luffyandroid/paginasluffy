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
import android.graphics.drawable.VectorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
    TextView tvlatitudocultaBB, tvlongitudocultaBB;

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
        Toast.makeText(this, getResources().getString(R.string.spaintoast), Toast.LENGTH_SHORT).show();

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
        Toast.makeText(this, getResources().getString(R.string.spaintoast), Toast.LENGTH_SHORT).show();

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
        Toast.makeText(this, getResources().getString(R.string.spaintoast), Toast.LENGTH_SHORT).show();

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
                    if (anun.getSubcategoria().equals("ic_sub_a_zotros")) {
                        markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_a_zotros));
                    }
                     markeroptions.title("ole ole los caracole");
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

                    MarkerOptions markeroptions = new MarkerOptions();


                    markeroptions.icon(getBitmapDescriptor(R.drawable.ic_marcador_asociaciones));


                    markeroptions.title("ole ole los caracole");
                    markeroptions.position(new LatLng(anun.getLatitud(), anun.getLongitud()));
                    tmpRealTimeMarkers.add(mMap.addMarker(markeroptions));

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
