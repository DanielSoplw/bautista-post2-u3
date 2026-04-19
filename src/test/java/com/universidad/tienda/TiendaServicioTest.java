package com.universidad.tienda;

import com.universidad.tienda.decorator.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DecoratorTest {

    private OrdenServicio buildCompleto() {
        OrdenServicio base = new OrdenServicioBase();
        return new AuditoriaDecorator(
                new ValidacionDecorator(
                        new LoggingDecorator(base)));
    }

    @Test
    void testOrdenValida() {
        OrdenServicio svc = buildCompleto();
        String result = svc.procesarOrden("ORD-001", 50000.0);
        assertTrue(result.startsWith("PROCESADA:"));
    }

    @Test
    void testOrdenMontoInvalido() {
        OrdenServicio svc = buildCompleto();
        assertThrows(IllegalArgumentException.class,
                () -> svc.procesarOrden("ORD-002", 0.0));
    }

    @Test
    void testOrdenIdVacio() {
        OrdenServicio svc = buildCompleto();
        assertThrows(IllegalArgumentException.class,
                () -> svc.procesarOrden("", 10000.0));
    }

    @Test
    void testDecoradorIndividualLogging() {
        OrdenServicio base = new OrdenServicioBase();
        OrdenServicio conLog = new LoggingDecorator(base);
        assertDoesNotThrow(() -> conLog.procesarOrden("ORD-003", 0.0));
    }
}