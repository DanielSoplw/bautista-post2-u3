# 🏗️ Patrones Estructurales — Decorator & Facade

> **Curso:** Patrones de Diseño de Software  
> **Unidad:** 3 — Patrones Estructurales · Post-Contenido 2  
> **Institución:** Universidad Francisco de Paula Santander · Ingeniería de Sistemas · 2026

---

## 📋 Descripción

Este proyecto implementa los patrones de diseño **Decorator** y **Facade** sobre un sistema de tienda virtual construido con **Spring Boot**. Extiende el proyecto del Post-Contenido 1 (Adapter + Composite) con dos nuevas capacidades:

- **Decorator:** Un servicio de procesamiento de órdenes con capas opcionales e independientes de *logging*, *validación* y *auditoría*, ensambladas mediante composición en lugar de herencia.
- **Facade:** Un subsistema de notificaciones multicanal (email, SMS, push) cuya complejidad interna queda oculta detrás de una única interfaz unificada.

---

## ✅ Prerrequisitos

| Herramienta | Versión mínima |
|---|---|
| Java | 17+ |
| Maven | 3.8+ |
| IDE | VS Code o IntelliJ con soporte Spring Boot |
| Git | Cualquier versión reciente |

---

## 📁 Estructura del Proyecto

```
src/
├── main/java/com/universidad/tienda/
│   ├── DecoratorConfig.java              # @Configuration — ensambla la cadena
│   │
│   ├── decorator/
│   │   ├── OrdenServicio.java            # Component (interfaz)
│   │   ├── OrdenServicioBase.java        # ConcreteComponent
│   │   ├── OrdenServicioDecorator.java   # Decorator abstracto
│   │   ├── LoggingDecorator.java         # ConcreteDecorator 1 — mide tiempo
│   │   ├── ValidacionDecorator.java      # ConcreteDecorator 2 — valida datos
│   │   └── AuditoriaDecorator.java       # ConcreteDecorator 3 — registra resultado
│   │
│   └── facade/
│       ├── EmailService.java             # Subsistema: canal email
│       ├── SMSService.java               # Subsistema: canal SMS
│       ├── PushService.java              # Subsistema: canal push
│       └── NotificacionFacade.java       # Facade — punto de entrada unificado
│
└── test/java/com/universidad/tienda/
    └── DecoratorTest.java                # 4 tests JUnit 5
```

---

## 🎨 Patrón Decorator — Servicio de Órdenes

### ¿Qué problema resuelve?

Agregar comportamiento (logging, validación, auditoría) a un servicio **sin modificar su código fuente** y sin crear una explosión de subclases. Cada capa es opcional e intercambiable.

### Composición de la cadena

La cadena se construye en `DecoratorConfig` usando `@Configuration` de Spring, respetando el **principio Open/Closed**:

```
Cliente → AuditoriaDecorator
             └── ValidacionDecorator
                     └── LoggingDecorator
                               └── OrdenServicioBase
```

**Orden de ejecución al llamar `procesarOrden()`:**

```
[AUDITORIA] (envuelve todo) ──▶ [VALIDACION] (verifica datos) ──▶ [LOG] (mide tiempo) ──▶ BASE (procesa)
```

```java
// DecoratorConfig.java
@Bean("ordenCompleto")
public OrdenServicio ordenServicioCompleto(@Qualifier("ordenBase") OrdenServicio base) {
    return new AuditoriaDecorator(
               new ValidacionDecorator(
                   new LoggingDecorator(base)));
}
```

### Reglas de validación (`ValidacionDecorator`)

| Campo | Regla |
|---|---|
| `ordenId` | No puede ser `null` ni vacío |
| `monto` | Entre **$1.000** y **$50.000.000** |

---

## 🔔 Patrón Facade — Subsistema de Notificaciones

### ¿Qué problema resuelve?

El controlador REST no necesita conocer cómo funcionan `EmailService`, `SMSService` ni `PushService` por separado. `NotificacionFacade` ofrece **operaciones de alto nivel** que coordinan los tres canales internamente.

### Operaciones disponibles

```java
// Notifica por email + SMS + push en una sola llamada
facade.notificarCompraExitosa(correo, telefono, pushToken, ordenId);

// Notifica por email + SMS ante un fallo de pago
facade.notificarErrorPago(correo, telefono, ordenId);
```

### Diagrama de colaboración

```
Controlador REST
      │
      ▼
NotificacionFacade          ← único punto de acceso
   ├── EmailService.enviar()
   ├── SMSService.enviar()
   └── PushService.enviar()
```

---

## 🧪 Pruebas

Las pruebas se encuentran en `DecoratorTest.java` y cubren:

| Test | Descripción |
|---|---|
| `testOrdenValida()` | Cadena completa — retorna `"PROCESADA:ORD-001"` |
| `testOrdenMontoInvalido()` | Monto `0.0` lanza `IllegalArgumentException` |
| `testOrdenIdVacio()` | ID vacío `""` lanza `IllegalArgumentException` |
| `testDecoradorIndividualLogging()` | Solo `LoggingDecorator` — monto `0.0` **no** lanza excepción |

Ejecutar todas las pruebas:

```bash
mvn test
```

Salida esperada:

```
[INFO] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

---

## 🚀 Cómo ejecutar el proyecto

```bash
# 1. Clonar el repositorio
git clone https://github.com/DanielSoplw/bautista-post2-u3.git
cd bautista-post2-u3

# 2. Compilar y empaquetar
mvn clean package

# 3. Ejecutar pruebas
mvn test

# 4. Levantar la aplicación Spring Boot
mvn spring-boot:run
```

---

## 🔍 Checkpoints de verificación

- [x] La cadena de decoradores se construye en `DecoratorConfig` sin tocar `OrdenServicioBase`
- [x] El log de consola muestra el orden: `AUDITORIA → VALIDACION → LOG → BASE`
- [x] Las excepciones de validación se propagan y los tests las capturan correctamente
- [x] `NotificacionFacade` notifica por los tres canales con una sola llamada
- [x] Los 4 tests de `DecoratorTest` pasan (`BUILD SUCCESS`)
- [x] Repositorio con mínimo 3 commits descriptivos

---

## 💡 Decisiones de diseño

**¿Por qué Decorator y no herencia?**  
La herencia fuerza a crear subclases para cada combinación posible de comportamientos (`LoggingValidacionServicio`, `LoggingAuditoriaServicio`, etc.). Con Decorator, cada comportamiento es una clase independiente que se compone en tiempo de ejecución, respetando el principio **SOLID** de composición sobre herencia.

**¿Por qué Facade y no acceso directo a los servicios?**  
Exponer `EmailService`, `SMSService` y `PushService` directamente al controlador acopla el cliente a los detalles de implementación del subsistema. Si cambia algún servicio (p. ej., la firma de `SMSService.enviar()`), habría que modificar todos los clientes. La Facade aísla ese acoplamiento en un único lugar.

---

## 👤 Autor

**Daniel Bautista**  
Ingeniería de Sistemas — Universidad Francisco de Paula Santander  
Repositorio: [DanielSoplw/bautista-post2-u3](https://github.com/DanielSoplw/bautista-post2-u3)
