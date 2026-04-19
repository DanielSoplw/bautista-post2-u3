package com.universidad.tienda;

import com.universidad.tienda.decorator.OrdenServicio;
import com.universidad.tienda.facade.NotificacionFacade;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TiendaApp implements CommandLineRunner {

    private final OrdenServicio ordenCompleto;
    private final NotificacionFacade notificaciones;

    public TiendaApp(@Qualifier("ordenCompleto") OrdenServicio ordenCompleto,
                     NotificacionFacade notificaciones) {
        this.ordenCompleto = ordenCompleto;
        this.notificaciones = notificaciones;
    }

    public static void main(String[] args) {
        SpringApplication.run(TiendaApp.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println("=== Patrón Decorator ===");
        ordenCompleto.procesarOrden("ORD-001", 50000.0);

        System.out.println("\n=== Patrón Facade ===");
        notificaciones.notificarCompraExitosa(
            "cliente@email.com", "3001234567", "token_push_abc", "ORD-001");
    }
}