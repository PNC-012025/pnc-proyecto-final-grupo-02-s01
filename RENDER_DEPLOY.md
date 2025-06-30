# 🚀 Deploy en Render - BudgetBuddy Backend

## 📋 Pasos para Deploy en Render

### **1. Preparar el Repositorio**

Asegúrate de que tu código esté en GitHub y que incluya:
- ✅ `render.yaml` (configuración automática)
- ✅ `backend/pom.xml` (dependencias Maven)
- ✅ `backend/src/main/resources/application-prod.properties` (configuración de producción)

### **2. Crear Cuenta en Render**

1. Ve a [render.com](https://render.com)
2. Crea una cuenta o inicia sesión
3. Conecta tu repositorio de GitHub

### **3. Configuración Automática (Recomendado)**

Si usas el archivo `render.yaml`:

1. **Crear nuevo Web Service**
2. **Conectar repositorio de GitHub**
3. **Render detectará automáticamente la configuración**
4. **Se creará automáticamente la base de datos PostgreSQL**

### **4. Configuración Manual**

Si prefieres configurar manualmente:

#### **A. Crear Base de Datos PostgreSQL:**
1. Dashboard → **New** → **PostgreSQL**
2. **Name:** `budgetbuddy-db`
3. **Database:** `budgetbuddy`
4. **User:** `budgetbuddy_user`
5. Anota las credenciales

#### **B. Crear Web Service:**
1. Dashboard → **New** → **Web Service**
2. **Connect Repository** → Selecciona tu repo
3. **Name:** `budgetbuddy-backend`
4. **Environment:** `Java`
5. **Build Command:** `./mvnw clean package -DskipTests`
6. **Start Command:** `java -jar target/backend-0.0.1-SNAPSHOT.jar`

#### **C. Configurar Variables de Entorno:**
En **Environment Variables** agrega:

```bash
SPRING_PROFILES_ACTIVE=prod
SERVER_PORT=$PORT
SPRING_DATASOURCE_URL=jdbc:postgresql://tu-host-render:5432/budgetbuddy
SPRING_DATASOURCE_USERNAME=budgetbuddy_user
SPRING_DATASOURCE_PASSWORD=tu-password-de-render
SECURITY_USER_NAME=admin
SECURITY_USER_PASSWORD=tu-password-segura
```

### **5. Variables de Entorno Detalladas**

| Variable | Valor | Descripción |
|----------|-------|-------------|
| `SPRING_PROFILES_ACTIVE` | `prod` | Activa el perfil de producción |
| `SERVER_PORT` | `$PORT` | Puerto asignado por Render |
| `SPRING_DATASOURCE_URL` | `jdbc:postgresql://host:5432/db` | URL de conexión a PostgreSQL |
| `SPRING_DATASOURCE_USERNAME` | `username` | Usuario de la base de datos |
| `SPRING_DATASOURCE_PASSWORD` | `password` | Contraseña de la base de datos |
| `SECURITY_USER_NAME` | `admin` | Usuario para autenticación básica |
| `SECURITY_USER_PASSWORD` | `password` | Contraseña para autenticación básica |

### **6. Verificar el Deploy**

1. **Esperar a que termine el build** (puede tomar 5-10 minutos)
2. **Verificar logs** en la pestaña **Logs**
3. **Probar la API:**
   ```bash
   curl https://tu-app.onrender.com/actuator/health
   ```

### **7. URLs Importantes**

- **API Base:** `https://tu-app.onrender.com`
- **Health Check:** `https://tu-app.onrender.com/actuator/health`
- **Swagger UI:** `https://tu-app.onrender.com/swagger-ui.html` (si está configurado)

### **8. Troubleshooting**

#### **Error de Build:**
- Verificar que `pom.xml` esté en la raíz del backend
- Revisar logs de build en Render

#### **Error de Conexión a Base de Datos:**
- Verificar variables de entorno
- Asegurar que la base de datos esté creada
- Verificar que el servicio esté en la misma región

#### **Error de Puerto:**
- Asegurar que `SERVER_PORT=$PORT` esté configurado
- Verificar que la app use el puerto correcto

### **9. Monitoreo**

- **Logs:** Disponibles en tiempo real en Render
- **Health Check:** `/actuator/health`
- **Métricas:** Render proporciona métricas básicas

### **10. Actualizaciones**

Para actualizar:
1. **Push a GitHub** (rama main)
2. **Render detectará automáticamente** los cambios
3. **Deploy automático** se iniciará

### **11. Costos**

- **Web Service:** Gratis (con limitaciones)
- **PostgreSQL:** Gratis (hasta 1GB)
- **Verificar límites** en la documentación de Render

## 🎉 ¡Listo!

Tu backend estará disponible en `https://tu-app.onrender.com` 