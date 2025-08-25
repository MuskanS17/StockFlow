## StockFlow

Inventory and user management REST API built with Spring Boot.

### Tech stack
- **Java**: 17
- **Spring Boot**: 3.5.5
- **Build**: Maven
- **Persistence**: Spring Data JPA, MySQL
- **Security**: Spring Security (HTTP Basic, in-memory users)

### Prerequisites
- JDK 17+
- Maven 3.8+
- MySQL 8.x (running locally)

### API overview

Base paths:
- Users: `/api/auth`
- Products: `/api/product`

Authentication: HTTP Basic. Some endpoints are public as noted below.

#### User endpoints (`/api/auth`)
- POST `/newUser` — Create a new user. Auth required. Body:
```json
{
  "name": "string",
  "email": "string",
  "password": "string",
  "role": "ADMIN|STAFF"
}
```
- DELETE `/deleteUser/{id}` — Delete user by id. Roles: ADMIN or STAFF.

#### Product endpoints (`/api/product`)
- GET `/allProducts` — Public. List products (name and unitPrice only).
- GET `/lowStock` — Roles: ADMIN or STAFF. List products with low stock.
- POST `/newProduct` — Roles: ADMIN or STAFF. Create product. Body matches `com.ms.entity.Product`.
- PUT `/updateProduct/{id}` — Roles: ADMIN or STAFF. Update product by id.
- DELETE `/deleteProduct/{id}` — Role: ADMIN. Delete product by id.
- GET `/all` — Role: ADMIN. List all product details.

Authorization rules are defined in `com.ms.security.SecurityConfig`.

### Example requests

Public endpoint:
```bash
curl http://localhost:8080/api/product/allProducts
```

Authenticated (ADMIN example):
```bash
curl -u admin:adminpass http://localhost:8080/api/product/lowStock
```

Create product (STAFF or ADMIN):
```bash
curl -u staff1:staff1pass -H "Content-Type: application/json" \
  -d '{
    "name": "Notebook",
    "description": "A5 ruled",
    "quantity": 100,
    "unitPrice": 2.5
  }' \
  http://localhost:8080/api/product/newProduct
```

### Project structure
- `com.ms.controller` — REST controllers
- `com.ms.service` — Business logic
- `com.ms.repository` — Spring Data JPA repositories
- `com.ms.entity` — JPA entities
- `com.ms.security` — Security configuration
- `com.ms.exception` — Custom exceptions and global handler

### Development notes
- Devtools enabled for hot reload in dev scope
- Lombok is used; enable annotation processing 
