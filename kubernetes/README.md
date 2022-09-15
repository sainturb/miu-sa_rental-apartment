```shell
kubectl exec -it account-database-6849d8fb78-khdz4 -- psql -U postgres --password -p 5432 postgres
kubectl exec -it product-database-74b4cfb84b-c9679 -- psql -U postgres --password -p 5432 postgres

# kiali
kubectl port-forward svc/kiali -n istio-system 20001

# grafana
kubectl port-forward svc/grafana -n istio-system 3000

# prometheus
kubectl port-forward svc/prometheus -n istio-system 9090

# tracing
kubectl port-forward svc/tracing -n istio-system 9090

# zipkin
kubectl port-forward svc/zipkin -n istio-system 9411
```