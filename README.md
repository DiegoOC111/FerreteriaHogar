# Ferretería Hogar

## 2. Integrantes
- Sebastián Escobar  
- Diego Ortiz  

---

## 3. Funcionalidades
- Autenticación de usuarios (Login)
- Administración de usuarios (solo administrador)
- Gestión de productos
- Gestión de inventarios
- Consola de administración
- Selección de inventario activo
- Escaneo de productos desde la base de datos mediante código de barras (Barcode 128)
- Toma de inventario con registro y control de cantidades

---

## 4. Endpoints utilizados

### Microservicio Backend en EC2
**Base URL:**  

    http://ec2-50-17-165-201.compute-1.amazonaws.com:8081

#### Autenticación
- `POST /auth/login`
- `GET /auth/status`
- `POST /auth/bootstrap-admin`

#### Usuarios
- `GET /users/me`
- `GET /users/all`
- `POST /users/create-user`
- `PUT /users/{username}`
- `DELETE /users/{username}`

#### Inventarios
- `GET /inventory`
- `GET /inventory/{code}`
- `GET /inventory/{code}/full`
- `POST /inventory`
- `PUT /inventory`
- `DELETE /inventory/{code}`

#### Productos
- `GET /products`
- `GET /products/{code}`
- `POST /products`
- `PUT /products`
- `DELETE /products/{code}`

#### Inventario - Producto
- `GET /inventory-product/inventory/{inventoryCode}`
- `GET /inventory-product/{inventoryCode}/{productCode}`
- `POST /inventory-product`
- `POST /inventory-product/{inventoryCode}/scan/{productCode}/{qty}`
- `PUT /inventory-product`
- `DELETE /inventory-product/{inventoryCode}/{productCode}`

>api externa: https://v2.jokeapi.dev/

---

## 5. Pasos para ejecutar el proyecto

### Requisitos
- Android Studio
- Dispositivo Android físico o emulador
- Llave de firma `.jks`

### Ejecución
1. Clonar el repositorio del proyecto.
2. Abrir el proyecto en Android Studio.
3. Configurar la URL del microservicio backend en el archivo de configuración correspondiente.
4. Seleccionar el **Build Variant** adecuado.
5. Ir a:
Build -> Generate Signed Bundle / APK
6. Seleccionar **APK**.
7. Cargar la llave de firma (`.jks`).
8. Generar el APK firmado.
9. Copiar el APK al dispositivo Android.
10. Instalar la aplicación en el dispositivo.

---

## 6. Capturas

Se adjuntan links a capturas del **APK firmado** y del archivo de **firma `.jks`** como evidencia del proceso de compilación y firma de la aplicación.

[Evidencia](https://drive.google.com/drive/folders/1xbgoVfPchQlBZMUfWt4qKJ1pdAt4cwsM?usp=sharing)
[MicroServicio](https://github.com/DiegoOC111/api)
[Documetnacion MicroServicios](http://ec2-50-17-165-201.compute-1.amazonaws.com:8081/doc/swagger-ui/index.html)


