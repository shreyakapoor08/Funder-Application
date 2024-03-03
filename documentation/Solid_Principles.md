# Design Principles

### SOLID Principles

#### Single Responsibility Principle (SRP)
The application follows the Single Responsibility Principle by ensuring that each class or module has only one reason to change. For example, the `UserService` is responsible for managing user-related operations, and the `JwtAuthenticationFilter` is responsible for handling JWT authentication.

#### Open/Closed Principle (OCP)
The Open/Closed Principle is applied by designing classes to be open for extension but closed for modification. For instance, the `SecurityConfig` class is configured using Spring Security and can be extended for custom security requirements.

#### Liskov Substitution Principle (LSP)
The Liskov Substitution Principle is adhered to by ensuring that derived classes can substitute their base classes without affecting the correctness of the program. In the `PaymentServiceImplTest` class, mocking and testing demonstrate adherence to this principle.

#### Interface Segregation Principle (ISP)
The Interface Segregation Principle is followed by designing interfaces that are specific to the needs of the classes that implement them. For example, the `InvestedProductService` interface defines methods relevant to product management.

#### Dependency Inversion Principle (DIP)
Dependency Inversion Principle is achieved by relying on abstractions (interfaces) rather than concrete implementations. Dependency injection is used to provide dependencies to classes. For instance, the `ProductServiceImpl` class takes the `ProductRepository` and `FirebaseFileStorageService` as dependencies.

### Cohesion
The application exhibits high cohesion by organizing classes and methods according to their responsibilities. For example, the `ContactUsController` class is focused on handling product-related HTTP requests, and the `ContactUsServiceImpl` class is responsible for implementing product-related business logic.

### Coupling
Low coupling is maintained between classes by minimizing their interdependence. Dependency injection, interfaces, and proper encapsulation contribute to loose coupling. For instance, the `ProductService` interface allows for flexibility in implementing different services.

### Don't Repeat Yourself (DRY):
Code duplication is minimized by encapsulating common functionality in methods or services, such as the `updateProductFields` method in `ProductServiceImpl`.

[**Go back to README.md**](../README.md)