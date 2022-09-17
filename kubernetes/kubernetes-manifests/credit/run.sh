kubectl delete -f credit-service.yml
kubectl delete -f credit-deployment.yml
kubectl delete -f credit-config.yml

kubectl apply -f credit-config.yml
kubectl apply -f credit-deployment.yml
kubectl apply -f credit-service.yml