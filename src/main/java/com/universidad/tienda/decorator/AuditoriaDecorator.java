package com.universidad.tienda.decorator;

import java.time.LocalDateTime;

public class AuditoriaDecorator extends OrdenServicioDecorator {
    public AuditoriaDecorator(OrdenServicio wrapped) { super(wrapped); }

    @Override
    public String procesarOrden(String ordenId, double monto) {
        String resultado = wrapped.procesarOrden(ordenId, monto);
        System.out.println("[AUDITORIA] " + LocalDateTime.now() +
            " — Orden: " + ordenId + " — Resultado: " + resultado);
        return resultado;
    }
}