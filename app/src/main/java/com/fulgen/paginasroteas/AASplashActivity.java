package com.fulgen.paginasroteas;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class AASplashActivity extends AppCompatActivity {

    //TIEMPO DE SPLASH
    private static final long SPLASH_SCREEN_DELAY = 3000;

    static final String EXTRA_ANUNCIOSPLASH = "ANUNCIOSPLASH";

    //ELEMENTOS DECLARADOS ▼

    TextView tvhoraAA;
    LinearLayout fondo1, fondo2, fondo3, fondo4;
    LottieAnimationView animationLogo1, animationLogo2, animationLogo3, animationLogo4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aasplash);

        //ELEMENTOS ENLAZADOS ▼

        animationLogo1 = (LottieAnimationView) findViewById(R.id.animationLogo1);
        animationLogo2 = (LottieAnimationView) findViewById(R.id.animationLogo2);
        animationLogo3 = (LottieAnimationView) findViewById(R.id.animationLogo3);
        animationLogo4 = (LottieAnimationView) findViewById(R.id.animationLogo4);

        fondo1 = (LinearLayout) findViewById(R.id.fondo1);
        fondo2 = (LinearLayout) findViewById(R.id.fondo2);
        fondo3 = (LinearLayout) findViewById(R.id.fondo3);
        fondo4 = (LinearLayout) findViewById(R.id.fondo4);

        //ELEMENTOS ENLAZADOS ▲

        //SACAR HORA ▼

        Date d=new Date();

        tvhoraAA= (TextView) findViewById(R.id.tvhoraAA);
        SimpleDateFormat ho=new SimpleDateFormat("H");
        String horaString = ho.format(d);
        tvhoraAA.setText(horaString);

        //CONVERTIR HORA EN INT
        int hora = Integer.parseInt(horaString);

        //SACAR HORA ▲

        //PROTOCOLO CAMBIAR FONDO SEGÚN HORA ▼

        if (hora >=6 && hora <= 10){
            fondo1.setVisibility(View.VISIBLE);


        }

        else if (hora >=11 && hora <= 17){
            fondo2.setVisibility(View.VISIBLE);

        }

        else if (hora >=18 && hora <= 21){
            fondo3.setVisibility(View.VISIBLE);


        }

        else if (hora >=22 || hora <= 5){
            fondo4.setVisibility(View.VISIBLE);

        }

        //PROTOCOLO CAMBIAR FONDO SEGÚN HORA ▲


        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                //PARA CONVERTIR NO EL ANUNCIO
                String anunciosplash = "si";

                //PARA CONVERTIR NO EL ANUNCIO
                Intent mainIntent = new Intent().setClass(
                        AASplashActivity.this, com.fulgen.paginasroteas.BAMenuActivity.class);

                mainIntent.putExtra("EXTRA_ANUNCIOSPLASH", anunciosplash);

                startActivity(mainIntent);

                finish();

            }
        };
        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);

    }
}
