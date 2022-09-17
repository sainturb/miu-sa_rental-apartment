kubectl delete -f bank-service.yml
kubectl delete -f bank-deployment.yml
kubectl delete -f bank-config.yml

kubectl apply -f bank-config.yml
kubectl apply -f bank-deployment.yml
kubectl apply -f bank-service.yml