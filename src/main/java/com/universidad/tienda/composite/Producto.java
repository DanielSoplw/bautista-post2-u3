package com.universidad.tienda.composite;

public class Producto implements ItemCatalogo {
    private final String nombre;
    private final double precio;

    public Producto(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    @Override public String getNombre() { return nombre; }
    @Override public double getPrecioTotal() { return precio; }

    @Override
    public void mostrar(int nivel) {
        System.out.println(" ".repeat(nivel) + "• " + nombre + " — $" + precio);
    }
}
