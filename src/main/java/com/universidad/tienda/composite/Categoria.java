package com.universidad.tienda.composite;

import java.util.ArrayList;
import java.util.List;

public class Categoria implements ItemCatalogo {
    private final String nombre;
    private final List<ItemCatalogo> items = new ArrayList<>();

    public Categoria(String nombre) { this.nombre = nombre; }

    public void agregar(ItemCatalogo item) { items.add(item); }
    public void remover(ItemCatalogo item) { items.remove(item); }

    @Override public String getNombre() { return nombre; }

    @Override
    public double getPrecioTotal() {
        return items.stream().mapToDouble(ItemCatalogo::getPrecioTotal).sum();
    }

    @Override
    public void mostrar(int nivel) {
        System.out.println(" ".repeat(nivel) + "[" + nombre + "] Total: $" + getPrecioTotal());
        items.forEach(i -> i.mostrar(nivel + 1));
    }
}

