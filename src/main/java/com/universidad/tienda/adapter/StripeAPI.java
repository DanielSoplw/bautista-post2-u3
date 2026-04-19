package com.universidad.tienda.adapter;

public class StripeAPI {
    public boolean charge(String token, long amountCents, String currencyCode) {
        System.out.println("Stripe: cobro de " + amountCents + " centavos [" + currencyCode + "]");
        return token != null && !token.isBlank();
    }
}
