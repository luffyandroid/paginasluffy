package com.fulgen.paginasroteas;

public class ZCategoria {

    private String name;
    private int icon;

    public ZCategoria(String nombre, int icono)
    {
        super();
        this.name = nombre;
        this.icon = icono;
    }

    public String getNombre()
    {
        return name;
    }

    public void setNombre(String nombre)
    {
        this.name = nombre;
    }

    public int getIcono()
    {
        return icon;
    }

    public void setIcono(int icono)
    {
        this.icon = icono;
    }

}