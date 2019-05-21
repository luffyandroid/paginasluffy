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

        if (convertView == null){
            convertView = inflater.inflate(R.layout.list_cacategoria, parent, false);
        }

        /*LayoutInflater inflater = LayoutInflater.from(getContext());
        final View item = inflater.inflate(R.layout.list_cacategoria, null);*/

        //TextView Numero de Socio
        TextView tv_titulo = (TextView) convertView.findViewById(R.id.tvtitulolistCA);
        tv_titulo.setText(anuncios.get(position).getNombre());
        TextView tv_descripcion = (TextView) convertView.findViewById(R.id.tvdescripcionlistCA);
        TextView tv_descuento = (TextView) convertView.findViewById(R.id.tvdescuentolistCA);
        TextView tvidiomaadap = (TextView) convertView.findViewById(R.id.tvidiomaadap);

        //TODO hay que hacerlo CAMBIAR DE IDIOMA LO QUE BAJA DE FIREBASE
        if (tvidiomaadap.getText().toString().equals("es")) {
            tv_descripcion.setText(anuncios.get(position).getDescripcioncortaes());
            tv_descuento.setText(anuncios.get(position).getDescuentoes());
        }
        if (tvidiomaadap.getText().toString().equals("en")) {
            tv_descripcion.setText(anuncios.get(position).getDescripcioncortaen());
            tv_descuento.setText(anuncios.get(position).getDescuentoen());
        }
        if (tvidiomaadap.getText().toString().equals("de")) {
            tv_descripcion.setText(anuncios.get(position).getDescripcioncortade());
            tv_descuento.setText(anuncios.get(position).getDescuentode());
        }
        if (tvidiomaadap.getText().toString().equals("fr")) {
            tv_descripcion.setText(anuncios.get(position).getDescripcioncortafr());
            tv_descuento.setText(anuncios.get(position).getDescuentofr());
        }
        if (tv_descuento.getText().toString().equals("no")){
            tv_descuento.setVisibility(View.GONE);
        }

        TextView tv_imagempresa = (TextView) convertView.findViewById(R.id.tvimagempresalistCA);
        tv_imagempresa.setText(anuncios.get(position).getSubcategoria());

        String imagen = anuncios.get(position).getSubcategoria();
        int idImagen = c.getResources().getIdentifier(imagen, "drawable",c.getPackageName());
        ImageView iv_imagen = (ImageView)convertView.findViewById(R.id.imagempresalistCA);
        iv_imagen.setImageResource(idImagen);




        //ImageView
       /* ImageView imagempresa = (ImageView) convertView.findViewById(R.id.imagempresalistCA);
        String url = anuncios.get(position).getImagen();
        */
        return convertView;


    }

}
