kubectl delete -f notification-service.yml
kubectl delete -f notification-deployment.yml
kubectl delete -f notification-config.yml

kubectl apply -f notification-config.yml
kubectl apply -f notification-deployment.yml
kubectl apply -f notification-service.yml