# Medisolution API

API REST construida con Spring Boot para la gestión integral de un centro médico. Este proyecto fue desarrollado con un enfoque estricto en buenas prácticas de ingeniería de software, priorizando la arquitectura de capas, la integridad de los datos y el uso de patrones de diseño escalables.

## Arquitectura y Patrones de Diseño

El sistema está estructurado bajo una **Arquitectura Multicapa (N-Tier)**, garantizando una separación clara de responsabilidades:

- **Capa Web (Controllers):** Manejo exclusivo de peticiones HTTP, delegación de lógica y validación de entrada (Jakarta Validation).
- **Capa de Negocio (Services):** Aislamiento de las reglas de negocio, como validaciones de cruce de agendas y verificación de duplicidad de recursos.
- **Capa de Persistencia (Repositories):** Integración con Spring Data JPA.

**Patrones y estrategias implementadas:**
- **DTO (Data Transfer Object) y Mappers estáticos:** Aislamiento total de las entidades de base de datos respecto al cliente final. Ninguna entidad se expone ni se recibe directamente.
- **Specification Pattern:** Implementado para soportar búsquedas dinámicas, permitiendo combinar múltiples filtros (fechas, nombres, estados) de manera eficiente en la base de datos.
- **Inyección por Constructor:** Inyección de dependencias estricta y segura (sin uso de `@Autowired` en atributos) para garantizar inmutabilidad.
- **Global Exception Handling:** Centralización del manejo de errores mediante `@RestControllerAdvice` para devolver estructuras JSON estandarizadas y códigos de estado HTTP precisos.
- **Soft Deletion:** Preservación de la integridad referencial histórica mediante la inactivación lógica de registros en lugar del borrado físico.

## Stack Tecnológico

- **Lenguaje:** Java 21
- **Framework Core:** Spring Boot 3
- **Persistencia:** Spring Data JPA / Hibernate
- **Base de Datos:** MySQL
- **Herramientas Adicionales:** Lombok, Maven, Jakarta Bean Validation

## Módulos Principales

1. **Gestión de Citas Médicas:** 
   - Motor de agendamiento con validación concurrente de disponibilidad. El sistema impide que un médico atienda dos citas al mismo tiempo, o que un paciente superponga horarios.
   - Consultas con filtros dinámicos por rango de fechas, especialidades y actores involucrados.
2. **Gestión de Personal Médico y Pacientes:** 
   - Operaciones transaccionales con validación de datos únicos (tarjeta profesional, documento de identidad, correo).
   - Motor de búsqueda flexible implementado sobre JPA Specifications.
3. **Gestión de Especialidades y EPS:** 
   - Catálogos de normalización asociados mediante relaciones en base de datos.

## Ejecución Local

### Prerrequisitos
- JDK 21 o superior
- Maven
- Motor de base de datos MySQL

### Configuración
1. Crear una base de datos en MySQL llamada `medisolution` (o el nombre preferido).
2. Ajustar las credenciales en el archivo `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/medisolution
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
```

### Arranque
Compilar e iniciar el servidor embebido de Spring Boot:
```bash
mvn clean compile
mvn spring-boot:run
```
La API estará disponible por defecto en el puerto `8080`.
