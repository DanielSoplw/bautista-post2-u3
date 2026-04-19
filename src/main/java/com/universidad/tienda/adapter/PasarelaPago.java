package com.universidad.tienda.adapter;

public interface PasarelaPago {
    boolean procesarPago(String moneda, double monto, String tokenCliente);
    String obtenerNombre();
}
