```shell
# ISTIO Gateway
kubectl get crd gateways.gateway.networking.k8s.io || \
{ kubectl kustomize "github.com/kubernetes-sigs/gateway-api/config/crd?ref=v0.5.0" | kubectl apply -f -; }
# install istio minimal profile
istioctl install --set profile=minimal -y
kubectl create namespace istio-ingress

# Delete all
kubectl delete gateways.gateway.networking.k8s.io gateway -n istio-ingress
$ istioctl uninstall -y --purge
$ kubectl delete ns istio-system
$ kubectl delete ns istio-ingress
```