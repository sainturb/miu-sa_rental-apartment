kubectl delete -f product-service.yml
kubectl delete -f product-deployment.yml
kubectl delete -f product-config.yml

kubectl apply -f product-config.yml
kubectl apply -f product-deployment.yml
kubectl apply -f product-service.yml