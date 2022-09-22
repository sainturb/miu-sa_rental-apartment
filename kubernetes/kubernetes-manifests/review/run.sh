kubectl delete -f shipment-service.yml
kubectl delete -f shipment-deployment.yml
kubectl delete -f shipment-config.yml

kubectl apply -f shipment-config.yml
kubectl apply -f shipment-deployment.yml
kubectl apply -f shipment-service.yml