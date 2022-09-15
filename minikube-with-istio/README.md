```shell
kubectl exec -it postgres-xxx -- psql -U postgres --password -p 5432 account

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