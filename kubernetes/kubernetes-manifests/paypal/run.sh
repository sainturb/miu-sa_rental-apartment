kubectl delete -f paypal-service.yml
kubectl delete -f paypal-deployment.yml
kubectl delete -f paypal-config.yml

kubectl apply -f paypal-config.yml
kubectl apply -f paypal-deployment.yml
kubectl apply -f paypal-service.yml