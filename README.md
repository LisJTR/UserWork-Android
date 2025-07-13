
#🖥 UserWork

Este proyecto es el Trabajo de Fin de Grado (TFG) del ciclo de **Desarrollo de Aplicaciones Multiplataforma (DAM)**.  
La aplicación está orientada a facilitar el contacto entre estudiantes y empresas, permitiendo a los alumnos aplicar a ofertas de empleo y a las empresas mostrar interés en candidatos.

## 📱 Vista previa de la app

<p align="center">
  <img src="https://github.com/LisJTR/UserWork-Android/blob/main/1.%20Pantalla%20Inicio.png?raw=true" width="45%" />
  <img src="https://github.com/LisJTR/UserWork-Android/blob/main/1.2.%20Pantalla%20para%20logearse.png?raw=true" width="45%" />
</p>
<p align="center">
  <img src="https://github.com/LisJTR/UserWork-Android/blob/main/2.%20Pantalla%20oferta%20-%20EMPRESA.png?raw=true" width="45%" />
  <img src="https://github.com/LisJTR/UserWork-Android/blob/main/6.%20Pantalla%20notoficacion%20ALUMNO.png?raw=true" width="45%" />
</p>
<p align="center">
  <img src="https://github.com/LisJTR/UserWork-Android/blob/main/6.1.%20Pantalla%20notoficacion%20card%20EMPRESA%20RESPONDIDO.png?raw=true"
    width="45%" />
  <img src="https://github.com/LisJTR/UserWork-Android/blob/main/5.%20Pantalla%20Ajuste.png?raw=true"
    width="45%" />
</p>

## 🚀 Tecnologías utilizadas

### 📱 Frontend Android

- 🛠️ Jetpack Compose (Android Studio)
- 🧠 Kotlin + Clean Architecture
- 🌐 Retrofit (peticiones HTTP)
- 🗂️ Subida de imágenes y PDF
- 💾 SharedPreferences (persistencia local)

### ⚙️ Backend

- ☕ Java 17 + Spring Boot
- 🐘 PostgreSQL (Dockerizado)
- 🔐 Spring Crypto + REST API
- 📁 Subida de archivos a servidor (`/uploads`)

### 🧰 Infraestructura y herramientas

- 🐳 Docker (contenedorización de backend y base de datos)
- 🧪 Supabase (gestión inicial de la BD)
- 🌐 Git + GitHub (control de versiones)

## Descripción funcional

### 👥 Roles de usuario

#### 👩‍🎓 Alumno
- Registro de perfil (formación, CV, foto, habilidades)
- Aplicar a ofertas de empleo
- Ver ofertas aplicadas
- Recibir y responder invitaciones
- Gestión de notificaciones

#### 🏢 Empresa
- Registro de empresa (sector, descripción, etc.)
- Publicar ofertas de empleo
- Ver candidatos
- Enviar invitaciones a alumnos
- Gestionar notificaciones y candidaturas

### ⚙️ Funcionalidades principales

- 🔐 Autenticación y registro de usuarios
- 🧑‍💻 Gestión de perfiles (alumno / empresa)
- 📢 Publicación y aplicación a ofertas
- ✉️ Sistema de notificaciones e invitaciones
- 📤 Subida de archivos (CV, imágenes)
- 📰 Lectura de noticias vía feeds RSS
- ✅ CRUD completo de ofertas y perfiles

## 🏗️ Arquitectura del proyecto

### Backend (API REST)

- Paquetes organizados por dominio:
  - `user`, `oferta`, `aplicacion`, `invitacion`, `notificacion`
- Servicios + controladores bien separados
- Almacenamiento de archivos local (`/uploads`)
- Relaciones en base de datos por claves foráneas (`Long`)

### Frontend Android

- Arquitectura en capas (Clean Architecture)
  - `Data`: Repositorios, modelos, Retrofit
  - `Domain`: Casos de uso
  - `Presentation`: ViewModel + UI (Compose)
- Estado con `StateFlow`
- Navegación con Jetpack Compose Navigation


## 🗄️ Base de datos (PostgreSQL)

Inicialmente diseñada con Supabase y luego dockerizada.  
Tablas principales:

- `alumno`, `empresa`, `oferta`
- `aplicacion_oferta`
- `invitacion`
- `notificacion`

## 🔄 Flujo de trabajo (ejemplo)

1. 👩‍🎓 El alumno se registra y sube su CV
2. 🏢 La empresa publica ofertas
3. 📬 El alumno aplica a las ofertas
4. 👀 La empresa revisa perfiles y envía invitaciones
5. 🔁 Ambos responden a notificaciones
6. 📱 Todo se visualiza y gestiona desde la app


##🚀 Despliegue

## 🚀 Despliegue del backend

```bash
docker-compose up --build

