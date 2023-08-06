# e-commerce-microservices
Sample micro service applications to demonstrate e-commerce working

## Docker 
```shell
## Build catalog service 
docker build -t catalog-image .

# Create network 
docker network create ecommerce-network

# Run service
docker run --name catalog-service --network ecommerce-network -p 8100:8080 catalog-image

# --- Service Dscovery ---
docker build -t service-discovery .
docker run --name service-discovery --network ecommerce-network -p 8761:8761 service-discovery




```
