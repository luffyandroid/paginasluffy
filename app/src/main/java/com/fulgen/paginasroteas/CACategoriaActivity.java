package com.fulgen.paginasroteas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionsMenu;

public class CACategoriaActivity extends AppCompatActivity {

    //VARIANTES DE DECLARADAS
    private FloatingActionsMenu menu_fabCA;
    ListView listCA;
    EditText etfootbuscarCA;
    TextView tvcabeceraCA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cacategoria);

        //ENLAZO VARIANTES DECLARADAS
        listCA = (ListView) findViewById(R.id.listCA);
        etfootbuscarCA = (EditText) findViewById(R.id.etfootbuscarCA);
        tvcabeceraCA = (TextView) findViewById(R.id.tvcabeceraCA);
        menu_fabCA  = (FloatingActionsMenu) findViewById(R.id.menu_fabCA);


    }//FIN ONCREATE

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
