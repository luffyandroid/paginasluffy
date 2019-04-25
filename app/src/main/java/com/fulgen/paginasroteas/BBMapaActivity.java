package com.fulgen.paginasroteas;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Locale;

public class BBMapaActivity extends AppCompatActivity implements OnMapReadyCallback {

    //VARIANTES DE DECLARADAS
    private FloatingActionsMenu menu_fabBB;

    //GOOGLE MAPS
    private GoogleMap mMap;

    final Context context = this;



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


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

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

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions()
                .position(sydney)
                //.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marcador_001))
                .icon(getBitmapDescriptor(R.drawable.ic_marcador_a_carniceria))
                .title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }

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


}
