# Eshop - CQRS DDD Microservices
This project was created after reading Implementing Domain-Driven Design, great book written by Vaughn Vernon and watching Greg Young's webcast (https://www.youtube.com/watch?v=whCk1Q87_ZI).
I tried to use most of my knowledge in software development and architecture. 

## Used languages/technologies/paradigms/patterns
- Java
  - Spring
  - Hibernate
  - JUnit
  - Mockito
- .NET
  - EntityFramework
  - Asp.NET MVC
  - Castle (Windsor)
  - NUnit
  - Rhino Mocks
- MySQL
- MongoDB
- RabbitMQ
- REST
- Microservices
- CQRS
- Event Sourcing
- Domain-Driven Design
- Event-Driven architecture

## Services
Overall architecture consists of 4 microservices (identity, product, order, stock) and one front-end service combining these microservices.
The way it is designed is overkill for this kind of project, but it was done this way for learning purposes.

### Identity
- used for managing users and their roles as well as their authentication and authorization
- used also by other services for authorization
- implemented in Java
- Spring as DI framework
- JPA used for transactions and persistence, hibernate as implementation
- data stored in MySQL database

### Stock
- used for managing suppliers and products they offer and warehouses and the ammount of products on stock
- implemented in Java
- Spring as DI framework
- CQRS and Event sourcing used
  - Service is divided into two parts, each part with separate MySQL database - read and write. Write part is implemented using event sourcing where the state of each aggregate root is remembered as a continuation of events. Loading aggregate means replaying all events on it (or only after snapshot for performance reasons). New change in aggregate is remembered as a new event. Events stored in event store (write DB) are regularly forwarded to read side where event handlers catch them and update read DB.
  - Write DB stores aggregates and events in serialized form, Read DB stores them in traditional normalized form. 
- own transaction management and authorization using the Chain of responsibility pattern. Only required action is the custom annotation on a command handler ([example](https://github.com/mayoturis/eshop-cqrs-ddd-microservices-excercise/blob/master/java/eshop-cqrs-ddd-microservices-excercise/stock/src/main/java/com/marekturis/stock/application/commandhandlers/IncreaseProductAmmountInWarehouseHandler.java)) 
- for access to DB, plain JDBC is used 
- each aggregate is tested using own "mini framework", tests itself are then very clean and simple ([example 1](https://github.com/mayoturis/eshop-cqrs-ddd-microservices-excercise/blob/master/java/eshop-cqrs-ddd-microservices-excercise/stock/src/test/java/com/marekturis/stock/test/domain/warehouse/WhenExistingProductIsAddedToWarehouse.java), [example 2](https://github.com/mayoturis/eshop-cqrs-ddd-microservices-excercise/blob/master/java/eshop-cqrs-ddd-microservices-excercise/stock/src/test/java/com/marekturis/stock/test/domain/warehouse/WhenNewProductIsAddedToWarehouse.java))
- event handlers are tested using JUnit and Mockito ([example](https://github.com/mayoturis/eshop-cqrs-ddd-microservices-excercise/blob/master/java/eshop-cqrs-ddd-microservices-excercise/stock/src/test/java/com/marekturis/stock/test/application/eventhandlers/WarehouseProductCreatedHandlerTest.java))

### Product
- used for managing products and categories
- implemented in C#
- Castle Windsor as DI framework
- own transaction management and authorization using AOP. Only required action is custom annotation on application service ([example](https://github.com/mayoturis/eshop-cqrs-ddd-microservices-excercise/blob/master/dotnet/eshop-cqrs-ddd-microservices-excercise/Com.Marekturis.Product2/Model/Application/Services/CategoryApplicationService.cs))
- EntityFramework for persistence
- event handlers tested using NUnit and Rhino Mocks

### Order
- used for storing products in shopping carts and creating orders
- implemented in C#
- Castle Windsor as DI framework
- own transaction management and authorization using AOP
- own unit of work implementation, with proxies for aggregate roots
- data stored in MongoDB

### FrontEnd
- uses other services and presents data to user
- only service using front-end technologies (HTML, CSS, ...)
- implemented in C#
- Castle Windsor as DI framework
- Asp.NET MVC framework used

## Communication
Every microservice provides REST api. Requests which need to be authorized are accompanied by Authorization header containing authentication token.

Microservices don't communicate among each other directly (except with Identity service), but they publish events which can be handled by other services. RabbitMQ is used as messaging tool.

### Long running process (saga)
When new warehouse is created, new product (gift card) for this warehouse should be created and warehouse should have knowledge of id of this product.

This is implemented as a long running process. When new warehouse is created in warehouse service, WarehouseCreated event is published. This event is caught (inside warehouse service) and WarehouseProductCreationRequested event is fired. Inside product service this event is caught, product is created and event WarehouseProductCreated is fired. This event is caught in warehouse service and id of created product is assigned to warehouse.

For each long running process a process object is created. Process tracker periodically checks processes and if they timed out, an event is fired (e.g WarehouseProductCreationTimedOut) and specific handler can act upon it.
