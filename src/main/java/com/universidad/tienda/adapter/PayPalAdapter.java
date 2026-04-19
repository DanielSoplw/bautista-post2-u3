package com.universidad.tienda.adapter;

import org.springframework.stereotype.Component;

@Component("paypal")
public class PayPalAdapter implements PasarelaPago {
    private final PayPalAPI api = new PayPalAPI();

    @Override
    public boolean procesarPago(String moneda, double monto, String token) {
        String resultado = api.executePayment(monto, moneda);
        return resultado.startsWith("PP_");
    }

    @Override
    public String obtenerNombre() { return "PayPal"; }
}
