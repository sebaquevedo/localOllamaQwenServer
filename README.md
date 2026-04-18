<p align="center">
  <img src="./apiOllamaQwenServer-banner.svg" alt="apiOllamaQwenServer banner" />
</p>

# AI Backend API

---

## 🇪🇸 Español

Backend desarrollado con Spring Boot 3.5 para gestión de usuarios con autenticación JWT e integración con modelos de IA local via Ollama.

### Arquitectura

```
src/main/java/com/sebacirk/aibackend/
├── config/
├── controller/
├── dto/
├── entity/
├── repository/
├── security/
└── service/
```

### Paquetes y Clases

#### entity/

- **Role.java** — Define los 3 roles posibles del sistema: `ADMIN`, `USER` y `PREMIUM`.
- **User.java** — Representa la tabla `users` en la base de datos, contiene los campos id, username, email, password y role.

#### repository/

- **UserRepository.java** — Interfaz JPA que permite consultar y guardar usuarios en la base de datos, con métodos para buscar por username o email.

#### dto/

- **LoginRequest.java** — Objeto que recibe las credenciales del usuario (username y password) al hacer login.
- **RegisterRequest.java** — Objeto que recibe los datos del usuario (username, email, password y role) al registrarse.
- **AuthResponse.java** — Objeto que devuelve el token JWT, username y role al cliente tras autenticarse.

#### security/

- **JwtUtil.java** — Genera, valida y extrae información de los tokens JWT usando la clave secreta definida en `application.properties`.
- **JwtFilter.java** — Intercepta cada request HTTP, extrae el token del header `Authorization` y valida si es correcto antes de permitir el acceso.

#### config/

- **SecurityConfig.java** — Define las reglas de seguridad: qué endpoints son públicos, cuáles requieren rol específico y configura la política de sesión stateless.

### Roles y Permisos

| Rol       | Acceso                               |
| --------- | ------------------------------------ |
| `ADMIN`   | Acceso total a todos los endpoints   |
| `PREMIUM` | Acceso a endpoints de IA sin límites |
| `USER`    | Acceso limitado a endpoints de IA    |

### Endpoints

| Método | Endpoint             | Acceso               |
| ------ | -------------------- | -------------------- |
| POST   | `/api/auth/register` | Público              |
| POST   | `/api/auth/login`    | Público              |
| GET    | `/api/ai/**`         | USER, PREMIUM, ADMIN |
| GET    | `/api/ai/premium`    | PREMIUM, ADMIN       |
| GET    | `/api/admin/**`      | ADMIN                |

### Flujo de Autenticación

```
1. Cliente hace POST /api/auth/login con username y password
2. Backend valida credenciales y genera token JWT
3. Cliente incluye token en header: Authorization: Bearer <token>
4. JwtFilter intercepta y valida el token en cada request
5. SecurityConfig verifica si el rol tiene permiso al endpoint
```

### Tecnologías

- Java 21
- Spring Boot 3.5
- Spring Security
- JWT (jjwt 0.12.6)
- Spring Data JPA
- PostgreSQL
- Ollama (Spring AI)
- Docker

---

## 🇬🇧 English

Backend built with Spring Boot 3.5 for user management with JWT authentication and integration with local AI models via Ollama.

### 🇬🇧 Architecture

```
src/main/java/com/sebacirk/aibackend/
├── config/
├── controller/
├── dto/
├── entity/
├── repository/
├── security/
└── service/
```

### 🇬🇧 Packages and Classes

#### 🇬🇧 entity/

- **Role.java** — Defines the 3 possible system roles: `ADMIN`, `USER`, and `PREMIUM`.
- **User.java** — Represents the `users` table in the database, containing the fields id, username, email, password, and role.

#### repository/ (EN)

- **UserRepository.java** — JPA interface for querying and saving users in the database, with methods to search by username or email.

#### dto/ (EN)

- **LoginRequest.java** — Object that receives user credentials (username and password) on login.
- **RegisterRequest.java** — Object that receives user data (username, email, password, and role) on registration.
- **AuthResponse.java** — Object that returns the JWT token, username, and role to the client after authentication.

#### security/ (EN)

- **JwtUtil.java** — Generates, validates, and extracts information from JWT tokens using the secret key defined in `application.properties`.
- **JwtFilter.java** — Intercepts each HTTP request, extracts the token from the `Authorization` header, and validates it before granting access.

#### config/ (EN)

- **SecurityConfig.java** — Defines security rules: which endpoints are public, which require a specific role, and configures the stateless session policy.

### Roles and Permissions (EN)

| Role      | Access                                  |
| --------- | --------------------------------------- |
| `ADMIN`   | Full access to all endpoints            |
| `PREMIUM` | Unlimited access to AI endpoints        |
| `USER`    | Limited access to AI endpoints          |

### Endpoints (EN)

| Method | Endpoint             | Access               |
| ------ | -------------------- | -------------------- |
| POST   | `/api/auth/register` | Public               |
| POST   | `/api/auth/login`    | Public               |
| GET    | `/api/ai/**`         | USER, PREMIUM, ADMIN |
| GET    | `/api/ai/premium`    | PREMIUM, ADMIN       |
| GET    | `/api/admin/**`      | ADMIN                |

### Authentication Flow

```
1. Client sends POST /api/auth/login with username and password
2. Backend validates credentials and generates JWT token
3. Client includes token in header: Authorization: Bearer <token>
4. JwtFilter intercepts and validates the token on each request
5. SecurityConfig checks if the role has permission to access the endpoint
```

### Technologies (EN)

- Java 21
- Spring Boot 3.5
- Spring Security
- JWT (jjwt 0.12.6)
- Spring Data JPA
- PostgreSQL
- Ollama (Spring AI)
- Docker
