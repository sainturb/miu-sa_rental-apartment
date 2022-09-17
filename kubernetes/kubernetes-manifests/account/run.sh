kubectl delete -f account-service.yml
kubectl delete -f account-deployment.yml
kubectl delete -f account-config.yml

kubectl apply -f account-config.yml
kubectl apply -f account-deployment.yml
kubectl apply -f account-service.yml