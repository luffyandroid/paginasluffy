package com.fulgen.paginasroteas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ZAdaptadorCategoria extends ArrayAdapter<ZCategoria>
{
    private Context context;

    List<ZCategoria> datos = null;

    public ZAdaptadorCategoria(Context context, List<ZCategoria> datos)
    {
        //se debe indicar el layout para el item que seleccionado (el que se muestra sobre el botón del botón)
        super(context, R.layout.list_bamenu, datos);
        this.context = context;
        this.datos = datos;
    }

    //este método establece el elemento seleccionado sobre el botón del spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_bamenu,null);
        }
        ((TextView) convertView.findViewById(R.id.tvCategoria)).setText(datos.get(position).getNombre());
        ((ImageView) convertView.findViewById(R.id.imagCategoria)).setBackgroundResource(datos.get(position).getIcono());

        return convertView;
    }

    //gestiona la lista usando el View Holder Pattern. Equivale a la típica implementación del getView
    //de un Adapter de un ListView ordinario
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        if (row == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.list_bamenu, parent, false);
        }

        if (row.getTag() == null)
        {
            SocialNetworkHolder redSocialHolder = new SocialNetworkHolder();
            redSocialHolder.setIcono((ImageView) row.findViewById(R.id.imagCategoria));
            redSocialHolder.setTextView((TextView) row.findViewById(R.id.tvCategoria));
            row.setTag(redSocialHolder);
        }

        //rellenamos el layout con los datos de la fila que se está procesando
        ZCategoria redSocial = datos.get(position);
        ((SocialNetworkHolder) row.getTag()).getIcono().setImageResource(redSocial.getIcono());
        ((SocialNetworkHolder) row.getTag()).getTextView().setText(redSocial.getNombre());

        return row;
    }


    private static class SocialNetworkHolder
    {

        private ImageView icono;

        private TextView textView;

        public ImageView getIcono()
        {
            return icono;
        }

        public void setIcono(ImageView icono)
        {
            this.icono = icono;
        }

        public TextView getTextView()
        {
            return textView;
        }

        public void setTextView(TextView textView)
        {
            this.textView = textView;
        }

    }

}
