package com.fulgen.paginasroteas;

public class ZCategoria {

    private String name;
    private String nombreinterno;
    private int icon;

    public ZCategoria(String nombre, String nombreinterno, int icono)
    {
        super();
        this.name = nombre;
        this.nombreinterno = nombreinterno;
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

    public String getNombreinterno()
    {
        return nombreinterno;
    }

    public void setNombreinterno(String nombreinterno)
    {
        this.nombreinterno = nombreinterno;
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