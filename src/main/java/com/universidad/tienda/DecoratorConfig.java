package com.universidad.tienda;

import com.universidad.tienda.decorator.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DecoratorConfig {

    @Bean("ordenCompleto")
    public OrdenServicio ordenServicioCompleto(
            @Qualifier("ordenBase") OrdenServicio base) {
        return new AuditoriaDecorator(
                new ValidacionDecorator(
                        new LoggingDecorator(base)));
    }
}
