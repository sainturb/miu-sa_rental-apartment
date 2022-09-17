kubectl delete -f payment-service.yml
kubectl delete -f payment-deployment.yml
kubectl delete -f payment-config.yml

kubectl apply -f payment-config.yml
kubectl apply -f payment-deployment.yml
kubectl apply -f payment-service.yml