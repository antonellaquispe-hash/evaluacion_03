# Diagrama de arquitectura — Laboratorio 16

```mermaid
flowchart LR
    P[Postman / Cliente REST] -->|HTTP :8080| GW[API Gateway]
    GW -->|descubrimiento| EU[Eureka Server :8761]
    EU -. registra .-> EV[event-service :8081]
    EU -. registra .-> AS[attendee-service :8082]
    EU -. registra .-> IN[registration-service :8083]
    GW -->|/api/eventos/**| EV
    GW -->|/api/asistentes/**| AS
    GW -->|/api/inscripciones/**| IN
    IN -->|REST: verificar evento/cupo| EV
    IN -->|REST: verificar asistente| AS
    EV --> DBE[(MySQL bd_evento)]
    AS --> DBA[(MySQL bd_asistente)]
    IN --> DBI[(MySQL bd_inscripcion)]
```

Cada servicio es un módulo Maven desplegable por separado. Todos están agrupados en el proyecto raíz para facilitar su entrega; cada uno tiene puerto y base de datos propios.
