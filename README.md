# E-commerce Microservices

Repository này gồm ba Spring Boot service độc lập:

- `customer-service`: quản lý dữ liệu khách hàng.
- `product-service`: quản lý dữ liệu sản phẩm.
- `order-service`: quản lý đơn hàng.

Mỗi service có ứng dụng Spring Boot và database riêng, không import entity, repository
hoặc service Java của nhau. `Order` chỉ lưu `customerId` và `productId` vì Customer
và Product thuộc database/microservice khác. Do đó hệ thống không dùng quan hệ JPA
như `@ManyToOne` hay foreign key xuyên service. Khi tạo đơn hàng, `order-service`
gọi API của `product-service` để lấy giá hiện tại rồi mới tính `totalAmount`; service
không truy cập trực tiếp `product_db`.

## API chính

- Customer: `POST /api/v1/customers/register`, `PUT /api/v1/customers/login`,
  `GET /api/v1/customers/{id}`.
- Product: `POST /api/v1/products`, `GET /api/v1/products`,
  `GET /api/v1/products/{id}`.
- Order: `POST /api/v1/orders`, `GET /api/v1/orders/{id}`.

