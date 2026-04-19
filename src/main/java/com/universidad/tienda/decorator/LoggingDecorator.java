package com.universidad.tienda.decorator;

public class LoggingDecorator extends OrdenServicioDecorator {
    public LoggingDecorator(OrdenServicio wrapped) { super(wrapped); }

    @Override
    public String procesarOrden(String ordenId, double monto) {
        System.out.println("[LOG] Iniciando procesamiento: " + ordenId);
        long inicio = System.currentTimeMillis();
        String resultado = wrapped.procesarOrden(ordenId, monto);
        long tiempo = System.currentTimeMillis() - inicio;
        System.out.println("[LOG] Completado en " + tiempo + "ms. Resultado: " + resultado);
        return resultado;
    }
}
