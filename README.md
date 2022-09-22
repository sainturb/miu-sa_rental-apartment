# Team members
1. Saintur Batkhuu
2. Samreth Kem
3. Sumayya Jahan
4. Yumjirdulam Chinbat
5. Anthonyo Michael
6. Matthew Stephens
7. Tsinat Mehari Kidane

🌐 Public API

🛡 Protected API (with access token)

🔒 Internal API (service to service protection)

🗑️ No longer an API

# Structure

![alt text](assignment.drawio.png)

- Account &larr; Payment and Shipment
- Product &larr; Order
- Order &larr; Kafka
- Payment &larr; Order
- Transaction &larr; Payment
- Shipment &larr; Order
- Notification &larr; Kafka
- Kafka &larr; Payment, Order, Shipment and Transaction

### Account service : 8081

    Responsible for authenticating user. 
    Store following information:
        Full name, email, username, password, roles
        Set payment method: create payment method
        Update payment method: update payment method
        Set address: create shipping address
        Update address: update shipping address
```
🌐 /api/uaa/authenticate [POST] {username, password}
🌐 /api/uaa/register [POST] {username, email, firstname, lastname, password}
🛡 /api/users [CRUD] (only user with role ADMIN can access)
🛡 /api/payment-method [POST] create or update payment mehtod
🛡 /api/shipping-address [POST] create or update shipping address
🛡 /api/payment-method [GET] get user's payment mehtod
🛡 /api/shipping-address [GET] get user's shipping address
```

### Shipment service : 8082

    Responsible for finding user's shipping address and ship the products in the order
#### APIs
```
🔒 /api/ship/{orderNumber} [POST] body: address
```
### Order service : 8083
    
    Order service responsible for storing a cart items,
    Order service will make sure stock exceeding issue,
    This service also connects to shipment service to ship items to user's home
#### APIs
```
🛡 /api/orders/my [GET] user see their own orders
🛡 /api/orders/my/{orderNumber} [GET] user see their own order by order number
🛡 /api/orders/activities/{orderNumber} [GET] order activities by order number
🛡 /api/orders/place-order [POST] to place order
🗑️ /api/orders/update-status/{orderNumber}/{status} [PUT] change status (deprecated) 
```
### Product service : 8084
    
    Product service stores all the product information.
    We used batch processing to insert 1000 fake data to DB.
#### APIs
```
🛡 /api/products [CRUD] query parameters will filter products
🌐 /api/search [GET] query parameters {name, description, category, price.lessThan, price.greaterThan}
🔒 /api/products/{id}/reduce-stocks/{count} [PUT] reduce stock when user orders products (only internal service will access to this)
🔒 /api/products/{id}/availablility/{count} [GET] reduce stock when user orders products (only internal service will access to this)
```
### Payment service : 8085

    Decides which payment service to call according to 
    user's preferred payment method
#### APIs
```
🔒 /api/checkout [POST]
```
### Credit service : 8086
```
🔒 /api/pay [POST] cardNumber, cardExpires, cardSecurityCode required
```
### Bank service : 8087
```
🔒 /api/pay [POST] bankName, bankAccount, routingNumber required
```
### Paypal service : 8088
```
🔒 /api/pay [POST] accountNumber, accountToken required
```


# How to run

## Prerequisites

- Java 11+
- Maven 3.3+
- Docker
- Kubernetes

## Commands

```shell
sh build.sh
cd kubernetes
sh deploy.sh # it may take up to 5 minutes to run depending on running machine specification
```

## Extra
If you are using Intellij IDEA, try to call rest API using api.http file. 