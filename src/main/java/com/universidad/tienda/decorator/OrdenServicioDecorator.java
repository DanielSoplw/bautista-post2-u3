package com.universidad.tienda.decorator;

public abstract class OrdenServicioDecorator implements OrdenServicio {
    protected final OrdenServicio wrapped;

    public OrdenServicioDecorator(OrdenServicio wrapped) {
        this.wrapped = wrapped;
    }
}