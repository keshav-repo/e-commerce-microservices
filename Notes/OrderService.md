## Order Service

1. Create Order:
- Method: POST
- Url: /orders
- payload info
```
{
  "customer": {
    "name": "John Doe",
    "email": "johndoe@example.com",
    "address": "123 Main Street",
    "contactNumber": "+1-123-456-7890"
  },
  "products": [
    {
      "productId": "12345",
      "quantity": 2
    },
    {
      "productId": "67890",
      "quantity": 1
    }
  ],
  "payment": {
    "method": "Credit Card",
    "billingAddress": "456 Elm Street",
    "cardNumber": "************1234",
    "expiryDate": "12/25",
    "cvv": "123"
  }
}
```
- payload response
```
{
  "orderId": "ABC123",
  "orderStatus": "PROCESSING",
  "orderTotal": 125.99,
  "message": "Order created successfully."
}
```
- orderId: A unique identifier for the created order.
- orderStatus: The current status of the order, indicating that it is being processed.
- orderTotal: The total amount of the order.
- message: A message indicating the successful creation of the order.

2. Get Order Details:








