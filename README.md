# **Transaction Management API**

A Spring Boot project that provides RESTful APIs for managing customers, accounts, transactions, and funds transfers.
The system includes secure endpoints with spring security, message queuing with RabbitMQ, logging to files, and support
for Docker containerization.
---

## **Features**

1. **Customer Management:**
    - Register a new customer.
2. **Account Management:**
    - Create accounts for customers.
    - Retrieve account balances.
    - Perform funds transfers between accounts.
3. **Transaction Management:**
    - Persist transaction details in a MySQL database.
    - Publish transaction messages to RabbitMQ.
4. **Security:**
    - Endpoints secured using Spring Security basic authentication.
5. **Logging:**
    - Errors and activities logged to a file and ready for integration with log aggregation systems.
6. **Containerized Setup:**
    - Dockerized application with Docker Compose for MySQL and RabbitMQ services.

---

## **API Endpoints**

### **Customer Endpoints**

| Method | Endpoint                  | Description               |
|--------|---------------------------|---------------------------|
| POST   | `/api/customers/register` | Registers a new customer. |

### **Account Endpoints**

| Method | Endpoint                                | Description                                                 |
|--------|-----------------------------------------|-------------------------------------------------------------|
| POST   | `/api/accounts/create`                  | Creates a new account for a customer.                       |
| GET    | `/api/accounts/{accountNumber}/balance` | Retrieves the account balance for the given account number. |
| POST   | `/api/accounts/fundstransfer`           | Transfers funds between accounts.                           |

### **Architecture Overview**

1. **Customer Workflow:**
    - Create a customer using `/api/customers/register`.
2. **Account Workflow:**
    - Create an account for the customer with `/api/accounts/create`.
    - Check account balance using `/api/accounts/{accountNumber}/balance`.
    - Transfer funds with `/api/accounts/fundstransfer`.
3. **RabbitMQ Integration:**
    - Successful transactions publish a message to RabbitMQ for downstream processing.