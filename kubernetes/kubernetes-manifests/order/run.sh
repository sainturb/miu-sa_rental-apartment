kubectl delete -f order-service.yml
kubectl delete -f order-deployment.yml
kubectl delete -f order-config.yml

kubectl apply -f order-config.yml
kubectl apply -f order-deployment.yml
kubectl apply -f order-service.yml