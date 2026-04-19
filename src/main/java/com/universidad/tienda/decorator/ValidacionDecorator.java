package com.universidad.tienda.decorator;

public class ValidacionDecorator extends OrdenServicioDecorator {
    private static final double MONTO_MINIMO = 1000.0;
    private static final double MONTO_MAXIMO = 50_000_000.0;

    public ValidacionDecorator(OrdenServicio wrapped) { super(wrapped); }

    @Override
    public String procesarOrden(String ordenId, double monto) {
        if (ordenId == null || ordenId.isBlank())
            throw new IllegalArgumentException("ID de orden inválido");
        if (monto < MONTO_MINIMO || monto > MONTO_MAXIMO)
            throw new IllegalArgumentException("Monto fuera de rango: " + monto);
        System.out.println("[VALIDACION] Orden " + ordenId + " validada correctamente.");
        return wrapped.procesarOrden(ordenId, monto);
    }
}
