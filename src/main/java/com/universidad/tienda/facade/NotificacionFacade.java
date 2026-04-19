package com.universidad.tienda.facade;

import org.springframework.stereotype.Service;

@Service
public class NotificacionFacade {
    private final EmailService email;
    private final SMSService sms;
    private final PushService push;

    public NotificacionFacade(EmailService email, SMSService sms, PushService push) {
        this.email = email;
        this.sms = sms;
        this.push = push;
    }

    public void notificarCompraExitosa(String correo, String telefono,
                                        String pushToken, String ordenId) {
        String asunto = "Orden " + ordenId + " confirmada";
        String msg = "Su orden " + ordenId + " ha sido procesada exitosamente.";
        email.enviar(correo, asunto, msg);
        sms.enviar(telefono, msg);
        push.enviar(pushToken, asunto, msg);
    }

    public void notificarErrorPago(String correo, String telefono, String ordenId) {
        String msg = "Error al procesar la orden " + ordenId + ". Verifique su método de pago.";
        email.enviar(correo, "Error en su orden", msg);
        sms.enviar(telefono, msg);
    }
}