# ADR-003: Autenticacion Stateless con JWT

**Estado:** Aceptado

**Contexto:** El frontend (Vue.js) y posibles clientes moviles necesitan autenticarse contra la API. Se requiere un mecanismo stateless que no dependa de sesiones en servidor.

**Decision:** Se implementa autenticacion basada en JWT (JSON Web Tokens) con tokens de acceso (1 hora) y refresh tokens (7 dias). El JWT incluye claims `jti`, `iss`, `sub`, `aud`, `iat`, `nbf`, `exp`.

**Consecuencias:**
- **Positivas:** Stateless, escalable horizontalmente sin sticky sessions. Los refresh tokens permiten sesiones largas sin exponer credenciales. Estandar ampliamente adoptado.
- **Negativas:** Los tokens no pueden revocarse activamente (salvo lista negra). El payload JWT no esta encriptado (solo firmado). Los refresh tokens requieren almacenamiento en BD.
- **Riesgos:** Robo de refresh token podria permitir acceso prolongado. Se mitiga con rotacion de refresh tokens.

**Opciones consideradas:**
1. JWT con access + refresh tokens (seleccionado)
2. Sesiones HTTP con cookies (descartado por requerir estado en servidor)
3. OAuth2 con Keycloak (descartado por sobreingenieria para el alcance actual)
