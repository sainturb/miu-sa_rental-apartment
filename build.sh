mvn package -DskipTests=true

cd account-service
docker build -t sainturb/account-service .
docker push sainturb/account-service:latest


cd ../bank-service
docker build -t sainturb/bank-service .
docker push sainturb/bank-service:latest

cd ../credit-service
docker build -t sainturb/credit-service .
docker push sainturb/credit-service:latest

cd ../order-service
docker build -t sainturb/order-service .
docker push sainturb/order-service:latest

cd ../payment-service
docker build -t sainturb/payment-service .
docker push sainturb/payment-service:latest

cd ../paypal-service
docker build -t sainturb/paypal-service .
docker push sainturb/paypal-service:latest

cd ../product-service
docker build -t sainturb/product-service .
docker push sainturb/product-service:latest

cd ../notification-service
docker build -t sainturb/notification-service .
docker push sainturb/notification-service:latest

docker rmi $(docker images --filter "dangling=true" -q --no-trunc)