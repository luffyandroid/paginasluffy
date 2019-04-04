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
        TextView tv_titulo = (TextView) convertView.findViewById(R.id.tvtitulolistCA);
        TextView tv_descripcion = (TextView) convertView.findViewById(R.id.tvdescripcionlistCA);
        TextView tv_descuento = (TextView) convertView.findViewById(R.id.tvdescuentolistCA);
        TextView tv_imagempresa = (TextView) convertView.findViewById(R.id.tvimagempresalistCA);
        tv_categoria.setText(anuncios.get(position).getCategoria());
        tv_titulo.setText(anuncios.get(position).getCategoria());
        tv_descripcion.setText(anuncios.get(position).getCategoria());
        tv_descuento.setText(anuncios.get(position).getCategoria());
        tv_imagempresa.setText(anuncios.get(position).getCategoria());

        //ImageView
       /* ImageView imagempresa = (ImageView) convertView.findViewById(R.id.imagempresalistCA);
        String url = anuncios.get(position).getImagen();
        */

        return convertView;

    }

}
