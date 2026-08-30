# E-commerce Microservices

Repository này gồm ba Spring Boot service độc lập:

- `customer-service`: quản lý dữ liệu khách hàng.
- `product-service`: quản lý dữ liệu sản phẩm.
- `order-service`: quản lý đơn hàng.

Mỗi service có ứng dụng Spring Boot và database riêng, không import entity, repository
hoặc service Java của nhau. `Order` chỉ lưu `customerId` và `productId` vì Customer
và Product thuộc database/microservice khác. Do đó hệ thống không dùng quan hệ JPA
như `@ManyToOne` hay foreign key xuyên service; việc trao đổi dữ liệu giữa các service
sẽ thực hiện qua API.

