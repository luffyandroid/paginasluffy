package com.fulgen.paginasroteas;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

public class DAEmpresaActivity extends AppCompatActivity {

    //002
    static final String EXTRA_ANUNCIOSPLASH = "ANUNCIOSPLASH";

    //VARIANTES DE DECLARADAS
    ZAnuncio anuncio = null;
    DatabaseReference dbRef;
    ValueEventListener valueEventListener;

    final Context context = this;

    private FloatingActionsMenu menu_fabDA;
    ImageView imagempresaDA, imgCabeceramenuDA;
    TextView tvcabeceraDA, tvempresaDA, tvdescripempresaDA, tvinformacionadicionalDA, tvhorarioDA, tvdireccionDA,
            tvimagfacebookDA, tvimagtwitterDA, tvimagtlfDA, tvimagmailDA, tvimagmapDA, tvimagextraDA, tvidiomaDA, tvdescripempresacortaDA;

    //005
    //LinearLayout idForSaveView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daempresa);

        //ENLAZO VARIANTES DECLARADAS
        menu_fabDA = (FloatingActionsMenu) findViewById(R.id.menu_fabDA);

        tvcabeceraDA = (TextView) findViewById(R.id.tvcabeceraDA);
        tvempresaDA = (TextView) findViewById(R.id.tvempresaDA);
        tvdescripempresaDA = (TextView) findViewById(R.id.tvdescripempresaDA);
        tvinformacionadicionalDA = (TextView) findViewById(R.id.tvinformacionadicionalDA);
        tvhorarioDA = (TextView) findViewById(R.id.tvhorarioDA);
        tvdireccionDA = (TextView) findViewById(R.id.tvdireccionDA);
        tvimagfacebookDA = (TextView) findViewById(R.id.tvimagfacebookDA);
        tvimagtwitterDA = (TextView) findViewById(R.id.tvimagtwitterDA);
        tvimagtlfDA = (TextView) findViewById(R.id.tvimagtlfDA);
        tvimagmailDA = (TextView) findViewById(R.id.tvimagmailDA);
        tvimagmapDA = (TextView) findViewById(R.id.tvimagmapDA);
        tvimagextraDA = (TextView) findViewById(R.id.tvimagextraDA);
        tvidiomaDA = (TextView) findViewById(R.id.tvidiomaDA);
        tvdescripempresacortaDA = (TextView) findViewById(R.id.tvdescripempresacortaDA);
        imagempresaDA = (ImageView) findViewById(R.id.imagempresaDA);
        imgCabeceramenuDA = (ImageView) findViewById(R.id.imgCabeceramenuDA);

        //005
        //idForSaveView=(LinearLayout)findViewById(R.id.idForSaveView);

        //001 HAY QUE ENLAZAR REDES CON SETTEXT Y CONSTRUCTOR


        Bundle c = getIntent().getExtras();


        if (c != null) {

            anuncio = c.getParcelable("EXTRA_ANUNCIO");
            String catcatanuncio = getIntent().getStringExtra("EXTRA_CATEGORIA");


            tvempresaDA.setText(anuncio.getNombre());

            tvdireccionDA.setText(anuncio.getDireccion());
            tvimagfacebookDA.setText(anuncio.getFacebook());
            tvimagtwitterDA.setText(anuncio.getTwitter());
            tvimagtlfDA.setText(anuncio.getTelefono());
            tvimagmailDA.setText(anuncio.getMail());
            tvimagmapDA.setText(anuncio.getMaps());
            tvimagextraDA.setText(anuncio.getExtra());

            tvcabeceraDA.setText(catcatanuncio);


            //PONER ICONO CABECERA
            String imagen = anuncio.getSubcategoria();
            int idImagen = this.getResources().getIdentifier(imagen, "drawable", this.getPackageName());
            imgCabeceramenuDA.setImageResource(idImagen);

            //COMPROBAR SI LOS CAMPOS NO ESTAN VACIOS
            if (!tvinformacionadicionalDA.getText().toString().equals("")) {

                tvinformacionadicionalDA.setVisibility(View.VISIBLE);
            }

            //CARGAR IMAGEN
            String imagentienda = anuncio.getImagen();
            Glide.with(getApplicationContext()).load(imagentienda).into(imagempresaDA);

            if (tvidiomaDA.getText().toString().equals("es")) {
                tvdescripempresaDA.setText(anuncio.getDescripcionlargaes());
                tvinformacionadicionalDA.setText(anuncio.getDescuentoes());
                tvdescripempresacortaDA.setText(anuncio.getDescripcioncortaes());
                tvhorarioDA.setText(anuncio.getHorarioes());
            }
            if (tvidiomaDA.getText().toString().equals("en")) {
                tvdescripempresaDA.setText(anuncio.getDescripcionlargaen());
                tvinformacionadicionalDA.setText(anuncio.getDescuentoen());
                tvdescripempresacortaDA.setText(anuncio.getDescripcioncortaen());
                tvhorarioDA.setText(anuncio.getHorarioen());
            }
            if (tvidiomaDA.getText().toString().equals("de")) {
                tvdescripempresaDA.setText(anuncio.getDescripcionlargade());
                tvinformacionadicionalDA.setText(anuncio.getDescuentode());
                tvdescripempresacortaDA.setText(anuncio.getDescripcioncortade());
                tvhorarioDA.setText(anuncio.getHorariode());
            }
            if (tvidiomaDA.getText().toString().equals("fr")) {
                tvdescripempresaDA.setText(anuncio.getDescripcionlargafr());
                tvinformacionadicionalDA.setText(anuncio.getDescuentofr());
                tvdescripempresacortaDA.setText(anuncio.getDescripcioncortafr());
                tvhorarioDA.setText(anuncio.getHorariofr());
            }


        }


    }//FIN ONCREATE

//BOTONES FLOATMENU

    public void clickSpainDA(View v) {

        Locale locale = new Locale("es");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        Toast.makeText(this, getResources().getString(R.string.spaintoast), Toast.LENGTH_SHORT).show();

        //PARA QUE SE CIERRE AL PULSAR
        menu_fabDA.collapse();

        Intent mainIntent = new Intent().setClass(
                DAEmpresaActivity.this, DAEmpresaActivity.class);
        mainIntent.putExtra("EXTRA_ANUNCIO", anuncio);
        startActivity(mainIntent);

    }

    public void clickUkDA(View v) {

        Locale locale = new Locale("en");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        Toast.makeText(this, getResources().getString(R.string.englishtoast), Toast.LENGTH_SHORT).show();

        //PARA QUE SE CIERRE AL PULSAR
        menu_fabDA.collapse();

        Intent mainIntent = new Intent().setClass(
                DAEmpresaActivity.this, DAEmpresaActivity.class);
        mainIntent.putExtra("EXTRA_ANUNCIO", anuncio);
        startActivity(mainIntent);

    }

    public void clickFranceDA(View v) {

        Locale locale = new Locale("fr");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        Toast.makeText(this, getResources().getString(R.string.frenchtoast), Toast.LENGTH_SHORT).show();

        //PARA QUE SE CIERRE AL PULSAR
        menu_fabDA.collapse();

        Intent mainIntent = new Intent().setClass(
                DAEmpresaActivity.this, DAEmpresaActivity.class);
        mainIntent.putExtra("EXTRA_ANUNCIO", anuncio);
        startActivity(mainIntent);

    }

    public void clickGermanDA(View v) {

        Locale locale = new Locale("de");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        Toast.makeText(this, getResources().getString(R.string.germantoast), Toast.LENGTH_SHORT).show();

        //PARA QUE SE CIERRE AL PULSAR
        menu_fabDA.collapse();

        Intent mainIntent = new Intent().setClass(
                DAEmpresaActivity.this, DAEmpresaActivity.class);
        mainIntent.putExtra("EXTRA_ANUNCIO", anuncio);
        startActivity(mainIntent);

    }

    public void clickMapaDA(View v) {
        Intent mainIntent = new Intent().setClass(
                DAEmpresaActivity.this, BBMapaActivity.class);
        startActivity(mainIntent);
        //PARA QUE SE CIERRE AL PULSAR
        menu_fabDA.collapse();
    }

    public void clickMenuDA(View v) {

        //PARA CONVERTIR NO EL ANUNCIO
        String anunciosplash = "no";

        Intent mainIntent = new Intent().setClass(
                DAEmpresaActivity.this, BAMenuActivity.class);

        //PARA CONVERTIR NO EL ANUNCIO
        mainIntent.putExtra("EXTRA_ANUNCIOSPLASH", anunciosplash);
        startActivity(mainIntent);
        //ANIMACIÓN
        overridePendingTransition(R.anim.arrastrar_izquierda_in, R.anim.arrastrar_izquierda_out);
        //PARA QUE SE CIERRE AL PULSAR
        menu_fabDA.collapse();
    }

    public void onBackPressed() {
        //PARA CONVERTIR NO EL ANUNCIO
        String anunciosplash = "no";

        Intent mainIntent = new Intent().setClass(
                DAEmpresaActivity.this, CACategoriaActivity.class);

        //PARA CONVERTIR NO EL ANUNCIO
        mainIntent.putExtra("EXTRA_ANUNCIOSPLASH", anunciosplash);
        startActivity(mainIntent);
        //ANIMACIÓN
        overridePendingTransition(R.anim.arrastrar_izquierda_in, R.anim.arrastrar_izquierda_out);
        finish();
    }

    //BOTONES REDES

    //FACEBOOK
    public void clickEmpresaFaceboookDA(View v) {
        String Facebook = tvimagfacebookDA.getText().toString();
        String Nombre = tvempresaDA.getText().toString();
        if (Facebook.equals("no")) {
            Toast.makeText(getApplicationContext(),
                    Nombre + R.string.NoTieneFacebook,
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
                    Nombre + R.string.NoTieneTwitter,
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
        if (Telefono.equals("no")) {
            Toast.makeText(getApplicationContext(),
                    Nombre + R.string.NoTieneContacto,
                    Toast.LENGTH_LONG).show();
        } else {
            try {
                Uri number = Uri.parse("tel:" + Telefono);
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                startActivity(callIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(DAEmpresaActivity.this,
                        R.string.NoEncuentraLlamar, Toast.LENGTH_LONG).show();
            }
        }
    }

    //MAIL
    public void clickEmpresaMailDA(View v) {
        String Mail = tvimagmailDA.getText().toString();
        String Nombre = tvempresaDA.getText().toString();
        if (Mail.equals("no")) {
            Toast.makeText(getApplicationContext(),
                    Nombre + R.string.NoTieneContacto,
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
                        R.string.NoEncuentraEmail, Toast.LENGTH_LONG).show();
            }
        }
    }

    //MAPA
    public void clickEmpresaMapsDA(View v) {
        String Mapa = tvimagmapDA.getText().toString();
        String Nombre = tvempresaDA.getText().toString();
        if (Mapa.equals("no")) {
            Toast.makeText(getApplicationContext(),
                    Nombre + R.string.NoEncuentraLocalizacion,
                    Toast.LENGTH_LONG).show();
        } else {
            try {
                Uri location = Uri.parse(Mapa);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
                startActivity(mapIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(DAEmpresaActivity.this,
                        R.string.NoHayMapas, Toast.LENGTH_LONG).show();
            }
        }
    }

    //EXTRA
    public void clickEmpresaExtraDA(View v) {
        String Web = tvimagextraDA.getText().toString();
        String Nombre = tvempresaDA.getText().toString();
        if (Web.equals("no")) {
            Toast.makeText(getApplicationContext(),
                    Nombre + R.string.NoTieneInformacion,
                    Toast.LENGTH_LONG).show();

        } else {
            Uri uri = Uri.parse(Web);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    //COMPARTIR
    public void clickCompartirDA(View v) {

        /*


        // Get access to bitmap image from view
        imagempresaDA = (ImageView) findViewById(R.id.imagempresaDA);
        // Get access to the URI for the bitmap
        Uri bmpUri = getLocalBitmapUri(imagempresaDA);
        if (bmpUri != null) {
            // Construct a ShareIntent with link to image
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
            shareIntent.setType("image/*");
            // Launch sharing dialog for image
            startActivity(Intent.createChooser(shareIntent, "Share Image"));
        } else {
            // ...sharing failed, handle error
        }
    }

    // Returns the URI path to the Bitmap displayed in specified ImageView
    public Uri getLocalBitmapUri(ImageView imageView) {
        // Extract Bitmap from ImageView drawable
        Drawable drawable = imageView.getDrawable();
        Bitmap bmp = null;
        if (drawable instanceof BitmapDrawable){
            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            return null;
        }
        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            // Use methods on Context to access package-specific directories on external storage.
            // This way, you don't need to request external read/write permission.
            // See https://youtu.be/5xVh-7ywKpE?t=25m25s
            File file =  new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            // **Warning:** This will fail for API >= 24, use a FileProvider as shown below instead.
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

    // Method when launching drawable within Glide
    public Uri getBitmapFromDrawable(Bitmap bmp){

        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            // Use methods on Context to access package-specific directories on external storage.
            // This way, you don't need to request external read/write permission.
            // See https://youtu.be/5xVh-7ywKpE?t=25m25s
            File file =  new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();

            // wrap File object into a content provider. NOTE: authority here should match authority in manifest declaration
            bmpUri = FileProvider.getUriForFile(this, "com.codepath.fileprovider", file);  // use this version for API >= 24

            // **Note:** For API < 24, you may use bmpUri = Uri.fromFile(file);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

*/





















    /*
        final Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/jpg");
        final File photoFile = new File(getFilesDir(), "anuncio.jpg");
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(photoFile));
        startActivity(Intent.createChooser(shareIntent, "Share image using"));


*/












/*
        Bitmap bitmap =getBitmapFromView(idForSaveView);
        try {
            File file = new File(this.getExternalCacheDir(),"col_merced.png");
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            file.setReadable(true, false);
            final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            intent.setType("image/png");
            startActivity(Intent.createChooser(intent, "Share image via"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Bitmap getBitmapFromView(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null) {
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        }   else{
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return returnedBitmap;
    }
    */





        /*
        Uri imageUri = Uri.parse("android.resource://your.package/drawable/col_merced");
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/png");

        intent.putExtra(Intent.EXTRA_STREAM, imageUri);
        startActivity(Intent.createChooser(intent , "Share"));
        */



        /*
        imagempresaDA.buildDrawingCache();
        Bitmap bitmap = imagempresaDA.getDrawingCache();


        try {
            File file = new File(imagempresaDA.getContext().getCacheDir(), bitmap + ".png");
            FileOutputStream fOut = null;
            fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            file.setReadable(true, false);
            final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            intent.setType("image/png");
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        */




/*
            //Se carga la imagen que se quiere compartir
            Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.anuncio);
            //Se guarda la imagen en la SDCARD
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            icon.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            File f = new File( Environment.getExternalStorageDirectory() + File.separator +
                    "tmp" + File.separator + "peter.jpg");
            try {
                f.createNewFile();
                FileOutputStream fo = new FileOutputStream(f);
                fo.write(bytes.toByteArray());
            } catch (IOException e) {
                Log.e("ERROR", e.getMessage() );
            }
            //compartir imagen
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("image/jpeg");
            share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(f));
            share.putExtra(Intent.EXTRA_TEXT, "Mi imagen");
            startActivity(Intent.createChooser(share, "Compartir imagen"));
        }

        */






/*
//Se carga la imagen que se quiere compartir
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.anuncio);
        //Se guarda la imagen en la SDCARD
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        icon.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        File f = new File(String.valueOf(imagempresaDA));
        try {
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
        } catch (IOException e) {
            Log.e("ERROR", e.getMessage() );
        }
        //compartir imagen
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(f));
        share.putExtra(Intent.EXTRA_TEXT, "Mi imagen");
        startActivity(Intent.createChooser(share, "Compartir imagen"));
    }

*/
    /*

        String imagentienda_ = anuncio.getImagen();
        Glide.with(getApplicationContext()).load(imagentienda_).into(imagempresaDA);


        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imagentienda_);
        shareIntent.setType("image/jpeg");
        startActivity(Intent.createChooser(shareIntent, "Compartir vía"));

*/




        /*

        Intent compartir = new Intent(android.content.Intent.ACTION_SEND);
        compartir.setType("text/plain");
        //compartir.setType("text/plain");
        String nombre_ = tvempresaDA.getText().toString();
        String descripcioncorta_ = tvdescripempresacortaDA.getText().toString();
        String horario_ = tvhorarioDA.getText().toString();
        String telefono_ = tvimagtlfDA.getText().toString();
        String direccion_ = tvdireccionDA.getText().toString();
        String urldireccion_ = tvimagmapDA.getText().toString();


        //ESTILO DE FUENTE

        SpannableString en = new SpannableString("Enviado desde Guiadir");
        //en.setSpan(new StyleSpan(Typeface.BOLD), 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        en.setSpan(new StyleSpan(Typeface.ITALIC), 0, 21, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        StyleSpan italicSpan = new StyleSpan(Typeface.ITALIC);
        StyleSpan bolditalicSpan = new StyleSpan(Typeface.BOLD_ITALIC);

        //ESTILO EJECUTADO
       // en.setSpan(bolditalicSpan, 0, 21, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        //TEXTO CON ESTILO

        Intent clipboardIntent = new Intent(context, DAEmpresaActivity.class);
        clipboardIntent.setData(Uri.parse(urldireccion_));


        compartir.putExtra(android.content.Intent.EXTRA_SUBJECT, "Compartir" + nombre_);
        compartir.putExtra(android.content.Intent.EXTRA_TEXT, (nombre_ + "\n"
                + "\uD83D\uDD38" + " " + descripcioncorta_ + "\n"
                + "\uD83D\uDD52" + " " + horario_ + "\n"
                + "\uD83D\uDCCD" + " " + direccion_ + "\n"
                + "\uD83D\uDCDE" + " " + telefono_ + "\n"
                + en));
        //compartir.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtm"Oferta de " + nombre_ );
        startActivity(Intent.createChooser(compartir, "Compartir vía"));

    }
    */



    /*
            Intent compartir = new Intent(android.content.Intent.ACTION_SEND);
        compartir.setType("text/plain");
        //compartir.setType("text/plain");
        String nombre_ = tvempresaDA.getText().toString();
        String descripcioncorta_ = tvdescripempresacortaDA.getText().toString();
        String horario_ = tvhorarioDA.getText().toString();
        String telefono_ = tvimagtlfDA.getText().toString();
        String direccion_ = tvdireccionDA.getText().toString();
        String urldireccion_ = tvimagmapDA.getText().toString();


        //ESTILO DE FUENTE

        SpannableString en = new SpannableString("Enviado desde Guiadir");
        //en.setSpan(new StyleSpan(Typeface.BOLD), 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        en.setSpan(new StyleSpan(Typeface.ITALIC), 0, 21, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        StyleSpan italicSpan = new StyleSpan(Typeface.ITALIC);
        StyleSpan bolditalicSpan = new StyleSpan(Typeface.BOLD_ITALIC);

        //ESTILO EJECUTADO
       // en.setSpan(bolditalicSpan, 0, 21, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        //TEXTO CON ESTILO

        Intent clipboardIntent = new Intent(context, DAEmpresaActivity.class);
        clipboardIntent.setData(Uri.parse(urldireccion_));


        compartir.putExtra(android.content.Intent.EXTRA_SUBJECT, "Compartir" + nombre_);
        compartir.putExtra(android.content.Intent.EXTRA_TEXT, (nombre_ + "\n"
                + "\uD83D\uDD38" + " " + descripcioncorta_ + "\n"
                + "\uD83D\uDD52" + " " + horario_ + "\n"
                + "\uD83D\uDCCD" + " " + direccion_ + "\n"
                + "\uD83D\uDCDE" + " " + telefono_ + "\n"
                + en));
        //compartir.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtm"Oferta de " + nombre_ );
        startActivity(Intent.createChooser(compartir, "Compartir vía"));

    }
     */


    }
}