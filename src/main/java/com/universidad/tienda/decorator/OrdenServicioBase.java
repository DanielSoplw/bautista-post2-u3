package com.universidad.tienda.decorator;

import org.springframework.stereotype.Component;

@Component("ordenBase")
public class OrdenServicioBase implements OrdenServicio {
    @Override
    public String procesarOrden(String ordenId, double monto) {
        System.out.println("BASE: Procesando orden " + ordenId + " por $" + monto);
        return "PROCESADA:" + ordenId;
    }
}