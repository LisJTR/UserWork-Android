
# TFG - Aplicación de Intermediación Laboral

Este proyecto es el Trabajo de Fin de Grado (TFG) del ciclo de **Desarrollo de Aplicaciones Multiplataforma (DAM)**.  
La aplicación está orientada a facilitar el contacto entre estudiantes y empresas, permitiendo a los alumnos aplicar a ofertas de empleo y a las empresas mostrar interés en candidatos.

## Tecnologías utilizadas

### Frontend Android

- Android Studio (Jetpack Compose)
- Kotlin
- Clean Architecture
- Retrofit (peticiones HTTP)
- Gestión de archivos (subida de imágenes y PDF)
- Persistencia local con SharedPreferences

### Backend

- Java 17
- Spring Boot
- PostgreSQL (Dockerizado)
- REST API
- Spring Crypto
- Subida y almacenamiento de archivos en servidor local (`/uploads`)

### Infraestructura y herramientas

- Docker (contenedorización de base de datos y backend)
- Supabase (gestión inicial de la base de datos)
- Git y GitHub (control de versiones)

## Descripción funcional

### Roles de usuario

#### Alumno
- Registro de perfil (datos personales, formación, CV, habilidades, foto de perfil)
- Aplicación a ofertas de empleo
- Visualización de ofertas aplicadas
- Gestión de notificaciones
- Recepción de invitaciones de empresas
- Respuesta a invitaciones (interesado / no interesado)

#### Empresa
- Registro de perfil (datos de empresa, descripción, sector, etc.)
- Publicación de ofertas de empleo
- Visualización de candidatos
- Envío de invitaciones a alumnos
- Respuesta a candidaturas (seleccionado / descartado)
- Gestión de notificaciones

### Funcionalidades principales

- Sistema de autenticación y registro
- Gestión de perfiles de usuario (alumno y empresa)
- CRUD de ofertas de empleo
- Aplicación y seguimiento de ofertas
- Gestión de invitaciones y notificaciones entre usuarios
- Subida y gestión de archivos (imágenes, currículum en PDF)
- Sistema de notificaciones visuales (badge)
- Visualización de noticias externas mediante feeds RSS

## Arquitectura

El proyecto sigue una arquitectura **Full Stack separada**:

### Backend (API REST)

- Organizado en paquetes por dominio:
  - `user` (usuarios)
  - `oferta` (ofertas)
  - `aplicacion` (aplicaciones a ofertas)
  - `invitacion` (invitaciones)
  - `notificacion` (notificaciones)
- Manejo de lógica de negocio en los controladores y servicios.
- Almacenamiento de archivos en el servidor (almacenamiento persistente).
- Implementación de relaciones en base de datos mediante claves foráneas (Long IDs).

### Frontend Android

- Patrón Clean Architecture y separación de capas:
  - Data Layer (repositories, models, Retrofit)
  - Domain Layer (use cases)
  - Presentation Layer (ViewModels y Compose UI)
- Gestión de estado mediante `StateFlow` y `ViewModel`.
- Navegación con Jetpack Compose Navigation.

## Base de datos

La base de datos utilizada es **PostgreSQL**. Inicialmente gestionada en Supabase y posteriormente migrada a un contenedor Docker local.  
Tablas principales:

- `alumno`
- `empresa`
- `oferta`
- `aplicacion_oferta`
- `invitacion`
- `notificacion`

## Flujo de trabajo (ejemplos)

- Un alumno se registra, completa su perfil y sube su CV.
- Las empresas publican sus ofertas.
- El alumno aplica a ofertas de interés.
- Las empresas pueden ver los perfiles de los alumnos y enviar invitaciones.
- Ambos roles reciben notificaciones sobre aplicaciones e invitaciones.
- Las respuestas a notificaciones se almacenan y visualizan en la app.

## Despliegue

### Backend

```bash
docker-compose up
