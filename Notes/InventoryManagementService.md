## Inventory Management Service 

## List of rest api 
In an inventory management service, various REST APIs can be used to manage inventory-related operations. Here are some common REST APIs that you might find in an inventory management service:

1. Get Product Inventory:
   - Endpoint: GET /inventory/{productId}
   - Description: Retrieves the current inventory status for a specific product identified by its productId.

2. Update Product Inventory:
   - Endpoint: PATCH /inventory/{productId}
   - Description: Updates the inventory quantity or other attributes of a specific product.

3. Get Low Stock Products:
   - Endpoint: GET /inventory/low-stock
   - Description: Retrieves a list of products that are running low in stock, allowing proactive inventory management.

4. Add Product to Inventory:
   - Endpoint: POST /inventory
   - Description: Adds a new product to the inventory with initial quantity and other relevant details.

5. Remove Product from Inventory:
   - Endpoint: DELETE /inventory/{productId}
   - Description: Removes a product from the inventory system, typically when it is no longer available for sale.

6. Reserve Inventory for Order:
   - Endpoint: POST /inventory/{productId}/reserve
   - Description: Reserves a specific quantity of a product in the inventory for a pending order to prevent overselling.

7. Release Reserved Inventory:
   - Endpoint: POST /inventory/{productId}/release
   - Description: Releases the reserved inventory for a canceled or failed order, making it available for other orders.

8. Adjust Inventory Quantity:
   - Endpoint: POST /inventory/{productId}/adjust
   - Description: Adjusts the inventory quantity of a product by adding or subtracting a specified quantity.

9. Get Inventory History:
   - Endpoint: GET /inventory/history/{productId}
   - Description: Retrieves the history of inventory changes for a specific product, including adjustments, stock updates, and other relevant events.

10. Get Inventory Report:
    - Endpoint: GET /inventory/report
    - Description: Generates a comprehensive inventory report, including stock levels, low stock products, and other relevant metrics.


### Get Product Inventory
- Url: /inventory/{productId}
- Method: GET
- responseSample: 
```
{
  "productId": "12345",
  "productName": "Example Product",
  "quantity": 50,
  "availability": true,
  "location": "Warehouse A",
  "lastUpdated": "2023-07-12T10:30:00Z"
}
```
**HTTP Status Codes**:
- 200 OK: The request was successful, and the inventory information is included in the response.
- 404 Not Found: The specified productId does not exist in the inventory system.
- 500 Internal Server Error: An error occurred on the server while processing the request.
  
### Adjust Inventory Quantity
- URL:  /inventory/{productId}/adjust
- method: POST 
- payload 
```
  {
  "quantity": 10,
  "adjustmentType": "ADD",
  "reason": "New stock received"
}
```
- response sample 
```
{
  "productId": "12345",
  "message": "Inventory quantity adjusted successfully"
}
```
- **HTTP Status Codes:**
  - 200 OK: The inventory quantity adjustment was successful.
  - 400 Bad Request: The request was invalid or missing required fields.
  - 404 Not Found: The specified productId does not exist in the inventory system.
  - 500 Internal Server Error: An error occurred on the server while processing the request.
- If we want to reduce quantity, we can give negative value. Sample payload structure 
```
{
  "quantity": -5,
  "adjustmentType": "ADD",
  "reason": "Product returned by customer"
}
```
















