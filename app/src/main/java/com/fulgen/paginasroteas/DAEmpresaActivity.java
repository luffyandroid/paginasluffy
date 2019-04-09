package com.fulgen.paginasroteas;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class DAEmpresaActivity extends AppCompatActivity {

    //VARIANTES DE DECLARADAS
    ZAnuncio anuncio=null;
    DatabaseReference dbRef;
    ValueEventListener valueEventListener;

    private FloatingActionsMenu menu_fabDA;
    ImageView imagempresaDA;
    TextView tvcabeceraDA, tvempresaDA, tvdescripempresaDA, tvinformacionadicionalDA, tvhorarioDA, tvdireccionDA,
            tvimagfacebookDA, tvimagtwitterDA, tvimagtlfDA, tvimagmailDA, tvimagmapDA, tvimagextraDA;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daempresa);

        //ENLAZO VARIANTES DECLARADAS
        menu_fabDA  = (FloatingActionsMenu) findViewById(R.id.menu_fabDA);

        tvcabeceraDA = (TextView) findViewById(R.id.tvcabeceraDA);
        tvempresaDA = (TextView) findViewById(R.id.tvempresaDA);
        tvdescripempresaDA = (TextView) findViewById(R.id.tvdescripempresaDA);
        tvinformacionadicionalDA = (TextView) findViewById(R.id.tvinformacionadicionalDA);
        tvhorarioDA = (TextView) findViewById(R.id.tvhorarioDA);
        tvdireccionDA = (TextView) findViewById(R.id.tvdireccionDA);
        imagempresaDA = (ImageView) findViewById(R.id.imagempresaDA);


        //001 HAY QUE ENLAZAR REDES CON SETTEXT Y CONSTRUCTOR


        Bundle c = getIntent().getExtras();

        if(c!=null){

            anuncio = c.getParcelable(CACategoriaActivity.EXTRA_ANUNCIO);

            tvempresaDA.setText(anuncio.getNombre());
            tvdescripempresaDA.setText(anuncio.getDescripcionlargaes());
            tvinformacionadicionalDA.setText(anuncio.getDescuentoes());
            tvhorarioDA.setText(anuncio.getHorarioes());
            tvdireccionDA.setText(anuncio.getDireccion());

            String imagentienda = anuncio.getImagen();
            Glide.with(getApplicationContext()).load(imagentienda).into(imagempresaDA);

        }

    }//FIN ONCREATE
    
//BOTONES FLOATMENU

    public void clickSpainCA(View v) {

        //PARA QUE SE CIERRE AL PULSAR
        menu_fabDA.collapse();
    }

    public void clickUkCA(View v) {

        //PARA QUE SE CIERRE AL PULSAR
        menu_fabDA.collapse();
    }

    public void clickFranceCA(View v) {

        //PARA QUE SE CIERRE AL PULSAR
        menu_fabDA.collapse();
    }

    public void clickGermanCA(View v) {

        //PARA QUE SE CIERRE AL PULSAR
        menu_fabDA.collapse();
    }

    public void clickMapaCA(View v) {
        Intent mainIntent = new Intent().setClass(
                DAEmpresaActivity.this, BBMapaActivity.class);
        startActivity(mainIntent);
        //PARA QUE SE CIERRE AL PULSAR
        menu_fabDA.collapse();
    }

    public void clickMenuDA(View v) {
        Intent mainIntent = new Intent().setClass(
                DAEmpresaActivity.this, BAMenuActivity.class);
        startActivity(mainIntent);
        //PARA QUE SE CIERRE AL PULSAR
        menu_fabDA.collapse();
    }

    public void clickAtrasCA(View v) {
        Intent mainIntent = new Intent().setClass(
                DAEmpresaActivity.this, CACategoriaActivity.class);
        startActivity(mainIntent);
        //PARA QUE SE CIERRE AL PULSAR
        menu_fabDA.collapse();
    }

    //BOTONES REDES

    //FACEBOOK
    public void clickEmpresaFaceboookDA(View v) {
        String Facebook = tvimagfacebookDA.getText().toString();
        String Nombre = tvempresaDA.getText().toString();
        if (Facebook.equals("no")) {
            Toast.makeText(getApplicationContext(),
                    Nombre + " no tiene perfil en Facebook",
                    Toast.LENGTH_LONG).show();

        } else {
            Uri uri = Uri.parse(Facebook);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    //TWITTER
    public void clickEmpresaTwitterDA(View v) {
        String Twitter = tvimagtwitterDA.getText().toString();
        String Nombre = tvempresaDA.getText().toString();
        if (Twitter.equals("no")) {
            Toast.makeText(getApplicationContext(),
                    Nombre + " no tiene perfil en Twitter",
                    Toast.LENGTH_LONG).show();

        } else {
            Uri uri = Uri.parse(Twitter);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    //TELEFONO
    public void clickEmpresaTelefonoDA(View v) {
        String Telefono = tvimagtlfDA.getText().toString();
        String Nombre = tvempresaDA.getText().toString();
        if  (Telefono.equals("no")) {
            Toast.makeText(getApplicationContext(),
                    Nombre + " no tiene teléfono de contacto",
                    Toast.LENGTH_LONG).show();
        } else {
            try {
                Uri number = Uri.parse("tel:" + Telefono);
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                startActivity(callIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(DAEmpresaActivity.this,
                        "No hay apliación para llamar", Toast.LENGTH_LONG).show();
            }
        }
    }

    //MAIL
    public void clickEmpresaMailDA(View v) {
        String Mail = tvimagmailDA.getText().toString();
        String Nombre = tvempresaDA.getText().toString();
        if (Mail.equals("no")) {
            Toast.makeText(getApplicationContext(),
                    Nombre + " no correo electrónico",
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
                Toast.makeText(DAEmpresaActivity.this,
                        "No tienes clientes de email instalados.", Toast.LENGTH_LONG).show();
            }
        }
    }

    //MAPA
    public void clickEmpresaMapsDA(View v) {
        String Mapa = tvimagmapDA.getText().toString();
        String Nombre = tvempresaDA.getText().toString();
        if (Mapa.equals("no")) {
            Toast.makeText(getApplicationContext(),
                    Nombre + " no tiene localización",
                    Toast.LENGTH_LONG).show();
        } else {
            try {
                Uri location = Uri.parse(Mapa);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
                startActivity(mapIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(DAEmpresaActivity.this,
                        "No hay servidor de mapas", Toast.LENGTH_LONG).show();
            }
        }
    }

    //EXTRA
    public void clickEmpresaExtraDA(View v) {
        String Web = tvimagextraDA.getText().toString();
        String Nombre = tvempresaDA.getText().toString();
        if (Web.equals("no")) {
            Toast.makeText(getApplicationContext(),
                    Nombre + " no tiene página Web",
                    Toast.LENGTH_LONG).show();

        } else {
            Uri uri = Uri.parse(Web);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }
    
}