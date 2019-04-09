package com.fulgen.paginasroteas;

import android.content.Intent;
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

public class CACategoriaActivity extends AppCompatActivity {

    //ETIQUETAS SUBIDA DE BASE DE DATOS
    private DatabaseReference dbAnuncio;
    private ValueEventListener eventListener;
    static final String EXTRA_ANUNCIO="ANUNCIO";

    //VARIANTES DE DECLARADAS
    private FloatingActionsMenu menu_fabCA;
    ListView listCA;
    EditText etfootbuscarCA;
    TextView tvcabeceraCA, tvcategorialistCA, tvtitulolistCA, tvdescripcionlistCA, tvdescuentolistCA, tvimagempresalistCA;
    ImageView imagempresalistCA;

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
        tvimagempresalistCA = (TextView) findViewById(R.id.tvimagempresalistCA);
        imagempresalistCA = (ImageView) findViewById(R.id.imagempresalistCA);


        String catcatanuncio = getIntent().getStringExtra("EXTRA_CATEGORIA");

        tvcategorialistCA.setText(catcatanuncio);

        Toast.makeText(this, "La categoria es: "+catcatanuncio, Toast.LENGTH_SHORT).show();

        /*//ADAPTADOR
        ZAdaptadorAnuncio adaptador = new ZAdaptadorAnuncio(this, lista_anuncios);
        listCA.setAdapter(adaptador);*/



        cargardatos();
    }//FIN ONCREATE

    //BASE DE LIST VIEW

    private void cargardatos(){

        dbAnuncio = FirebaseDatabase.getInstance().getReference().child(tvcategorialistCA.getText().toString());

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

        //TOAST DE CARGA
        Toast.makeText(CACategoriaActivity.this, "• Cargando \uD83C\uDFC3\u200D •", Toast.LENGTH_SHORT).show();

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

                         Toast.makeText(CACategoriaActivity.this, "ole ole los caracole", Toast.LENGTH_SHORT).show();

                         //REFERENCIA A LA CLASE
                         ZAnuncio a = ((ZAnuncio) parent.getItemAtPosition(position));
                         ZAnuncio anuncioenviado = new ZAnuncio(a.getImagen(), a.getNombre(), a.getDescripcionlargaes(),
                                 a.getDescripcionlargade(), a.getDescripcionlargaen(), a.getDescripcionlargafr(), a.getDescuentoes(),
                                 a.getDescuentode(), a.getDescuentoen(), a.getDescuentofr(), a.getFacebook(), a.getTwitter(),
                                 a.getTelefono(), a.getMail(), a.getMaps(), a.getExtra(), a.getDescripcioncortaes(), a.getDescripcioncortade(),
                                 a.getDescripcioncortaen(), a.getDescripcioncortafr(), a.getHorarioes(), a.getHorariode(), a.getHorarioen(),
                                 a.getHorariofr(), a.getDireccion(), a.getCategoria(), a.getSubcategoria());

                         Intent i = new Intent(getApplicationContext(), DAEmpresaActivity.class);

                         i.putExtra(EXTRA_ANUNCIO, anuncioenviado);
                         startActivity(i);

                         /*//ETIQUETA + INDICAR A QUE MAINACTIVITY VA A IR
                         Intent i = new Intent(getApplicationContext(), DAEmpresaActivity.class);

                         //INICIAR ACTIVITY
                         startActivity(i);*/
                     }
                 });
    }

    //BOTONES FLOATMENU


    public void clickSpainCA(View v) {

        //PARA QUE SE CIERRE AL PULSAR
        menu_fabCA.collapse();
    }

    public void clickUkCA(View v) {

        //PARA QUE SE CIERRE AL PULSAR
        menu_fabCA.collapse();
    }

    public void clickFranceCA(View v) {

        //PARA QUE SE CIERRE AL PULSAR
        menu_fabCA.collapse();
    }

    public void clickGermanCA(View v) {

        //PARA QUE SE CIERRE AL PULSAR
        menu_fabCA.collapse();
    }

    public void clickMapaCA(View v) {
        Intent mainIntent = new Intent().setClass(
                CACategoriaActivity.this, BBMapaActivity.class);
        startActivity(mainIntent);
        //PARA QUE SE CIERRE AL PULSAR
        menu_fabCA.collapse();
    }

    public void clickAtrasCA(View v) {
        Intent mainIntent = new Intent().setClass(
                CACategoriaActivity.this, BAMenuActivity.class);
        startActivity(mainIntent);
        //PARA QUE SE CIERRE AL PULSAR
        menu_fabCA.collapse();
    }

    //BOTONE BUSCAR
    public void clickBuscarCA(View v) {

        //PARA QUE SE CIERRE AL PULSAR
        menu_fabCA.collapse();
    }
}
