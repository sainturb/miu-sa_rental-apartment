helm repo add elastic https://helm.elastic.co
helm delete elasticsearch
helm install elasticsearch elastic/elasticsearch -f ./values.yaml

# kubectl port-forward --namespace default svc/elasticsearch-master 9200:9200