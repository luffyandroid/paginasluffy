package com.fulgen.paginasroteas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.getbase.floatingactionbutton.FloatingActionsMenu;

public class BBMapaActivity extends AppCompatActivity {

    //VARIANTES DE DECLARADAS
    private FloatingActionsMenu menu_fabBB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbmapa);
        //ENLAZO VARIANTES DECLARADAS
        menu_fabBB  = (FloatingActionsMenu) findViewById(R.id.menu_fabBB);


    }//FIN ONCREATE

    //BOTONES FLOATMENU

    public void clickSpainBB(View v) {

        //PARA QUE SE CIERRE AL PULSAR
        menu_fabBB.collapse();
    }

    public void clickUkBB(View v) {

        //PARA QUE SE CIERRE AL PULSAR
        menu_fabBB.collapse();
    }

    public void clickFranceBB(View v) {

        //PARA QUE SE CIERRE AL PULSAR
        menu_fabBB.collapse();
    }

    public void clickGermanBB(View v) {

        //PARA QUE SE CIERRE AL PULSAR
        menu_fabBB.collapse();
    }

    public void clickLocalizarmeBB(View v) {

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


}