# Project Assignment: Enterprise Resource Management (ERM) System - Auditing and Performance Monitoring Module

## Scenario:

You are tasked with developing a module for an existing Enterprise Resource Management (ERM) system. This module's primary purpose is to provide robust **auditing capabilities** for critical business operations and **performance monitoring** for key service methods within the ERM system. The system manages various entities like `Employee`, `Project`, `Department`, and `Task`.

## Core Requirements & AOP Concepts to Apply:

Your solution should leverage Spring AOP extensively to implement the following functionalities without tightly coupling them to the core business logic.

1.  **Comprehensive Auditing of Data Modification Operations:**
    * **Requirement:** Whenever an `Employee`, `Project`, `Department`, or `Task` object is **created, updated, or deleted** (i.e., methods like `addEmployee`, `updateProject`, `deleteDepartment`, etc.), the system must log an audit trail.
    * **AOP Application:**
        * Use **`@Around` advice** to capture method execution for these modification operations.
        * Before the method execution, log the user performing the action (you can simulate this with a `ThreadLocal` or a simple placeholder).
        * After the method successfully returns, log the type of operation, the entity involved (e.g., "Employee"), and potentially its ID or a relevant identifier.
        * If an exception occurs during the operation, log the error with the relevant context.
        * **Pointcut Expressions:** Design precise AspectJ pointcut expressions to target only the data modification methods across various service classes (e.g., `EmployeeService`, `ProjectService`, `DepartmentService`, `TaskService`). Consider using `within()` and `execution()` with wildcards.

2.  **Performance Monitoring of Critical Business Logic:**
    * **Requirement:** Measure the execution time of certain critical business logic methods (e.g., methods that involve complex calculations, data fetching, or external API calls).
    * **AOP Application:**
        * Use **`@Around` advice** (or a combination of `@Before` and `@AfterReturning` if you prefer, but `@Around` is more concise for timing).
        * Capture the start and end time of the method execution.
        * Log the method name and its execution duration.
        * **Pointcut Expressions:** Create a separate AspectJ pointcut expression that targets specific "performance-critical" methods. This could be identified by a custom annotation (e.g., `@PerfMonitored`) or by convention (e.g., methods within a specific package or with a specific name pattern).

3.  **Security Authorization Check (Introduction/Interceptor Pattern):**
    * **Requirement:** For highly sensitive operations (e.g., deleting a `Department`), introduce a pre-check to ensure the current user has "ADMIN" privileges. If not, throw an `AccessDeniedException`.
    * **AOP Application:**
        * Explore using **Introductions (via `@DeclareParents`)** to add a new interface (e.g., `Securable`) and its implementation (e.g., `AdminSecurityChecker`) to certain service beans at runtime. The `Securable` interface could have a `checkAdminAccess()` method.
        * Alternatively, and perhaps more commonly in modern Spring AOP for simple checks, you could use a **`@Before` advice** targeting specific methods that require admin access. This advice would then perform the check. While not a direct "Introduction" in the strict sense of adding an interface, it serves a similar purpose of "introducing" security logic. For this assignment, trying the `@DeclareParents` approach would be more aligned with testing your understanding of Introductions.
        * **Pointcut Expressions:** Target specific methods (e.g., `deleteDepartment`) that require this authorization check.

4.  **Error Handling and Notification:**
    * **Requirement:** When any service method throws an exception, log the exception details (message, stack trace) and potentially send a notification (e.g., to a monitoring system, or just print a simulated notification message).
    * **AOP Application:**
        * Use **`@AfterThrowing` advice** to intercept exceptions.
        * Log the exception and its context.
        * Simulate a notification (e.g., `System.out.println("ALERT: An error occurred in " + joinPoint.getSignature().toShortString());`).
        * **Pointcut Expressions:** Create a broad pointcut that targets all service layer methods or a specific set of methods where error handling is critical.

5.  **Configuring AOP:**
    * Use **Declarative AOP** primarily, leveraging Spring's XML configuration or Java-based `@Configuration` with `@EnableAspectJAutoProxy`.
    * Demonstrate the creation of aspects using the `@Aspect` annotation.
    * Consider using `ProxyFactoryBean` for at least one specific proxy creation if you want to show explicit proxy configuration, though `@AspectJAutoProxy` is generally preferred for its simplicity.

## Technical Stack:

* **Spring Framework:** Core, Context, AOP, Beans
* **Java:** Any recent LTS version (e.g., Java 17)
* **Build Tool:** Maven or Gradle
* **Logging:** SLF4J with Logback or Log4j2 (to demonstrate proper logging setup).

## Deliverables:

1.  **Working Spring Boot (or plain Spring) Application:** A runnable application that demonstrates all the AOP functionalities described above.
2.  **Clear Console Output/Log Files:** The logs should clearly show the audit trails, performance metrics, and error notifications generated by your aspects.
3.  **Codebase:** Well-structured, commented code demonstrating your understanding of the AOP concepts.
4.  **Brief README.md:**
    * Explaining how to run the application.
    * Briefly describing how each AOP concept was applied and where to find the relevant code.
    * Highlighting any challenges faced and how they were overcome.

## Tips and Hints:

* Start with the core business logic (models and basic service implementations) first, without any AOP.
* Then, gradually introduce the aspects one by one, verifying each step.
* Pay close attention to your pointcut expressions – they are crucial for precise targeting.
* For the auditing, consider passing relevant arguments to the advice (e.g., the entity object itself) to log more detailed information.
* Simulate user context (e.g., current user ID) using `ThreadLocal` in a simple utility class for the auditing aspect.
* If you choose to experiment with `ProxyFactoryBean`, remember that it creates a proxy for a *single* bean, whereas `@EnableAspectJAutoProxy` handles proxying for all beans advised by `@Aspect` classes.