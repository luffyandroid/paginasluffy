package com.fulgen.paginasroteas;

import android.os.Parcel;
import android.os.Parcelable;

public class ZAnuncio implements Parcelable {

    //ATRIBUTOS (DISTINTAS VARIANTES)
    String imagen;
    String nombre;
    String descripcionlargaes;
    String descripcionlargade;
    String descripcionlargaen;
    String descripcionlargafr;
    String descuentoes;
    String descuentode;
    String descuentoen;
    String descuentofr;
    String facebook;
    String twitter;
    String telefono;
    String mail;
    String maps;
    String extra;
    String descripcioncortaes;
    String descripcioncortade;
    String descripcioncortaen;
    String descripcioncortafr;
    String horarioes;
    String horariode;
    String horarioen;
    String horariofr;
    String direccion;
    String categoria;
    String subcategoria;


    public static final Parcelable.Creator<ZAnuncio> CREATOR = new
            Parcelable.Creator<ZAnuncio>(){
                @Override
                public ZAnuncio createFromParcel(Parcel in){
                    return new ZAnuncio(in);
                }

                @Override
                public ZAnuncio[] newArray(int size){
                    return new ZAnuncio[size];
                }
            };
    

    //CONTRUCTOR PONIENDO A LAS VARIANTES (ALT + INS)


    public ZAnuncio(String imagen, String nombre, String descripcionlargaes, String descripcionlargade, String descripcionlargaen, String descripcionlargafr, String descuentoes, String descuentode, String descuentoen, String descuentofr, String facebook, String twitter, String telefono, String mail, String maps, String extra, String descripcioncortaes, String descripcioncortade, String descripcioncortaen, String descripcioncortafr, String horarioes, String horariode, String horarioen, String horariofr, String direccion, String categoria, String subcategoria) {
        this.imagen = imagen;
        this.nombre = nombre;
        this.descripcionlargaes = descripcionlargaes;
        this.descripcionlargade = descripcionlargade;
        this.descripcionlargaen = descripcionlargaen;
        this.descripcionlargafr = descripcionlargafr;
        this.descuentoes = descuentoes;
        this.descuentode = descuentode;
        this.descuentoen = descuentoen;
        this.descuentofr = descuentofr;
        this.facebook = facebook;
        this.twitter = twitter;
        this.telefono = telefono;
        this.mail = mail;
        this.maps = maps;
        this.extra = extra;
        this.descripcioncortaes = descripcioncortaes;
        this.descripcioncortade = descripcioncortade;
        this.descripcioncortaen = descripcioncortaen;
        this.descripcioncortafr = descripcioncortafr;
        this.horarioes = horarioes;
        this.horariode = horariode;
        this.horarioen = horarioen;
        this.horariofr = horariofr;
        this.direccion = direccion;
        this.categoria = categoria;
        this.subcategoria = subcategoria;
    }

    public ZAnuncio (Parcel p){
        readFromParcel(p);
    }

    public ZAnuncio(){

    }

    //GET & SET PONIENDO A LAS VARIANTES (ALT + INS)


    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcionlargaes() {
        return descripcionlargaes;
    }

    public void setDescripcionlargaes(String descripcionlargaes) {
        this.descripcionlargaes = descripcionlargaes;
    }

    public String getDescripcionlargade() {
        return descripcionlargade;
    }

    public void setDescripcionlargade(String descripcionlargade) {
        this.descripcionlargade = descripcionlargade;
    }

    public String getDescripcionlargaen() {
        return descripcionlargaen;
    }

    public void setDescripcionlargaen(String descripcionlargaen) {
        this.descripcionlargaen = descripcionlargaen;
    }

    public String getDescripcionlargafr() {
        return descripcionlargafr;
    }

    public void setDescripcionlargafr(String descripcionlargafr) {
        this.descripcionlargafr = descripcionlargafr;
    }

    public String getDescuentoes() {
        return descuentoes;
    }

    public void setDescuentoes(String descuentoes) {
        this.descuentoes = descuentoes;
    }

    public String getDescuentode() {
        return descuentode;
    }

    public void setDescuentode(String descuentode) {
        this.descuentode = descuentode;
    }

    public String getDescuentoen() {
        return descuentoen;
    }

    public void setDescuentoen(String descuentoen) {
        this.descuentoen = descuentoen;
    }

    public String getDescuentofr() {
        return descuentofr;
    }

    public void setDescuentofr(String descuentofr) {
        this.descuentofr = descuentofr;
    }


    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMaps() {
        return maps;
    }

    public void setMaps(String maps) {
        this.maps = maps;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getDescripcioncortaes() {
        return descripcioncortaes;
    }

    public void setDescripcioncortaes(String descripcioncortaes) {
        this.descripcioncortaes = descripcioncortaes;
    }

    public String getDescripcioncortade() {
        return descripcioncortade;
    }

    public void setDescripcioncortade(String descripcioncortade) {
        this.descripcioncortade = descripcioncortade;
    }

    public String getDescripcioncortaen() {
        return descripcioncortaen;
    }

    public void setDescripcioncortaen(String descripcioncortaen) {
        this.descripcioncortaen = descripcioncortaen;
    }

    public String getDescripcioncortafr() {
        return descripcioncortafr;
    }

    public void setDescripcioncortafr(String descripcioncortafr) {
        this.descripcioncortafr = descripcioncortafr;
    }

    public String getHorarioes() {
        return horarioes;
    }

    public void setHorarioes(String horarioes) {
        this.horarioes = horarioes;
    }

    public String getHorariode() {
        return horariode;
    }

    public void setHorariode(String horariode) {
        this.horariode = horariode;
    }

    public String getHorarioen() {
        return horarioen;
    }

    public void setHorarioen(String horarioen) {
        this.horarioen = horarioen;
    }


    public String getHorariofr() {
        return horariofr;
    }

    public void setHorariofr(String horariofr) {
        this.horariofr = horariofr;
    }


    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getSubcategoria() {
        return subcategoria;
    }

    public void setSubcategoria(String subcategoria) {
        this.subcategoria = subcategoria;
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.imagen);
        parcel.writeString(this.nombre);
        parcel.writeString(this.descripcionlargaes);
        parcel.writeString(this.descripcionlargade);
        parcel.writeString(this.descripcionlargaen);
        parcel.writeString(this.descripcionlargafr);
        parcel.writeString(this.descuentoes);
        parcel.writeString(this.descuentode);
        parcel.writeString(this.descuentoen);
        parcel.writeString(this.descuentofr);
        parcel.writeString(this.facebook);
        parcel.writeString(this.twitter);
        parcel.writeString(this.telefono);
        parcel.writeString(this.mail);
        parcel.writeString(this.maps);
        parcel.writeString(this.extra);
        parcel.writeString(this.descripcioncortaes);
        parcel.writeString(this.descripcioncortade);
        parcel.writeString(this.descripcioncortaen);
        parcel.writeString(this.descripcioncortafr);
        parcel.writeString(this.horarioes);
        parcel.writeString(this.horariode);
        parcel.writeString(this.horarioen);
        parcel.writeString(this.horariofr);
        parcel.writeString(this.direccion);
        parcel.writeString(this.categoria);
        parcel.writeString(this.subcategoria);
    }

    private void readFromParcel (Parcel p){
        this.imagen = p.readString();
        this.nombre = p.readString();
        this.descripcionlargaes = p.readString();
        this.descripcionlargade = p.readString();
        this.descripcionlargaen = p.readString();
        this.descripcionlargafr = p.readString();
        this.descuentoes = p.readString();
        this.descuentode = p.readString();
        this.descuentoen = p.readString();
        this.descuentofr = p.readString();
        this.facebook = p.readString();
        this.twitter = p.readString();
        this.telefono = p.readString();
        this.mail = p.readString();
        this.maps = p.readString();
        this.extra = p.readString();
        this.descripcioncortaes = p.readString();
        this.descripcioncortade = p.readString();
        this.descripcioncortaen = p.readString();
        this.descripcioncortafr = p.readString();
        this.horarioes = p.readString();
        this.horariode = p.readString();
        this.horarioen = p.readString();
        this.horariofr = p.readString();
        this.direccion = p.readString();
        this.categoria = p.readString();
        this.subcategoria = p.readString();
    }
    
    
}





