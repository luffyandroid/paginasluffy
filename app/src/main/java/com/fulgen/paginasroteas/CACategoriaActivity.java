package com.fulgen.paginasroteas;

import android.content.Intent;
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

    //VARIANTES DE DECLARADAS
    private FloatingActionsMenu menu_fabCA;
    ListView listCA;
    EditText etfootbuscarCA;
    TextView tvcabeceraCA, tvtitulolistCA, tvdescripcionlistCA, tvdescuentolistCA, tvimagempresalistCA;
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

        tvtitulolistCA = (TextView) findViewById(R.id.tvtitulolistCA);
        tvdescripcionlistCA = (TextView) findViewById(R.id.tvdescripcionlistCA);
        tvdescuentolistCA = (TextView) findViewById(R.id.tvdescuentolistCA);
        tvimagempresalistCA = (TextView) findViewById(R.id.tvimagempresalistCA);
        imagempresalistCA = (ImageView) findViewById(R.id.imagempresalistCA);



        //ADAPTADOR
        ZAdaptadorAnuncio adaptador = new ZAdaptadorAnuncio(this, lista_anuncios);
        listCA.setAdapter(adaptador);


    }//FIN ONCREATE

    //BASE DE LIST VIEW
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

                         //DESCARGAR ELEMENTOS QUE NECESITO DE FIREBASE

                         dbAnuncio = FirebaseDatabase.getInstance().getReference()
                                 .child("alimentacion").child("aquiserianombre");

                         eventListener = new ValueEventListener() {
                             @Override
                             public void onDataChange(DataSnapshot dataSnapshot) {

                                 tvimagempresalistCA.setText(dataSnapshot.child("categoria").getValue().toString());
                                 tvdescripcionlistCA.setText(dataSnapshot.child("descripcioncortaes").getValue().toString());
                                 tvdescuentolistCA.setText(dataSnapshot.child("descuentoes").getValue().toString());
                                 tvtitulolistCA.setText(dataSnapshot.child("nombre").getValue().toString());
                             }

                             @Override
                             public void onCancelled(DatabaseError databaseError) {
                                 Log.e("CACategoriaActivity", "Error!", databaseError.toException());
                             }
                         };
                         dbAnuncio.addValueEventListener(eventListener);


                         //ETIQUETA + INDICAR A QUE MAINACTIVITY VA A IR
                         Intent i = new Intent(getApplicationContext(), DAEmpresaActivity.class);

                         //INICIAR ACTIVITY
                         startActivity(i);
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
