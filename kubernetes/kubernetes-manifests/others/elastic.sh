helm repo add bitnami https://charts.bitnami.com/bitnami
helm delete elasticsearch
helm install elasticsearch \
  -f values.yml \
  --set sysctlImage.enabled=true \
    --set data.persistence.size=16Gi \
    --set coordinating.service.type=LoadBalancer \
    --set global.kibanaEnabled=true \
  bitnami/elasticsearch

# kubectl port-forward --namespace default svc/elasticsearch 9200:9200