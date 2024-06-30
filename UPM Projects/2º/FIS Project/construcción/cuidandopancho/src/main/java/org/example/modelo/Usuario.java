package org.example.modelo;

public abstract class Usuario {
    protected String id;
    protected String nombre;
    protected EnumProveedor proveedor;

    public Usuario(String id, String nombre, EnumProveedor proveedor) {
        this.id = id;
        this.nombre = nombre;
        this.proveedor = proveedor;
    }

    public abstract String toString();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public EnumProveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(EnumProveedor proveedor) {
        this.proveedor = proveedor;
    }
}