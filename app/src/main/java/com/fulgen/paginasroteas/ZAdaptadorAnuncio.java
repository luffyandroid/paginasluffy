package com.fulgen.paginasroteas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ZAdaptadorAnuncio extends ArrayAdapter<ZAnuncio> {

    //EQUIPO REFIERE AL JAVA YA CREADO, EQUIPOS NOMBRE QUE LE HE GENERADO

    ArrayList<ZAnuncio> anuncios;
    Context c;
    LayoutInflater inflater;

    public ZAdaptadorAnuncio(Context c, ArrayList<ZAnuncio> anuncios) {
        super(c, R.layout.list_bamenu, anuncios);
        this.anuncios = anuncios;
        this.c = c;
        inflater = LayoutInflater.from(c);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (null == convertView) {

            convertView = inflater.inflate(R.layout.list_bamenu, parent, false);
        }

        //TextView Numero de Socio
        TextView tv_categoria = (TextView) convertView.findViewById(R.id.tvCategoria);
        tv_categoria.setText(anuncios.get(position).getCategoria());


        //ImageView
        ImageView imagen_categoria = (ImageView)convertView.findViewById(R.id.imagCategoria);
        String url = anuncios.get(position).getImagen();

        /*
        GlideApp
                .with(c)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imagen_socio);
                */



        return convertView;

    }

}
