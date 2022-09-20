mvn package -DskipTests=true

cd account-service
docker build -t account-service .

cd ../bank-service
docker build -t bank-service .

cd ../credit-service
docker build -t credit-service .

cd ../order-service
docker build -t order-service .

cd ../payment-service
docker build -t payment-service .

cd ../paypal-service
docker build -t paypal-service .

cd ../product-service
docker build -t product-service .

cd ../shipment-service
docker build -t shipment-service .

docker rmi $(docker images --filter "dangling=true" -q --no-trunc)