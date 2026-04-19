package com.universidad.tienda.adapter;

public class PayPalAPI {
    public String executePayment(double amount, String currency) {
        if (amount <= 0) return "ERROR: monto inválido";
        System.out.println("PayPal: procesando " + amount + " " + currency);
        return "PP_" + System.currentTimeMillis();
    }
}
