package com.universidad.tienda.facade;

import org.springframework.stereotype.Component;

@Component
public class SMSService {
    public void enviar(String telefono, String mensaje) {
        System.out.println("SMS → " + telefono + ": " + mensaje);
    }
}