package com.universidad.tienda.adapter;

import org.springframework.stereotype.Component;

@Component("stripe")
public class StripeAdapter implements PasarelaPago {
    private final StripeAPI api = new StripeAPI();

    @Override
    public boolean procesarPago(String moneda, double monto, String token) {
        long centavos = Math.round(monto * 100);
        return api.charge(token, centavos, moneda.toUpperCase());
    }

    @Override
    public String obtenerNombre() { return "Stripe"; }
}
