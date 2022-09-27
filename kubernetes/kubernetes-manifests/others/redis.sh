helm repo add bitnami https://charts.bitnami.com/bitnami
helm delete redis
helm install redis bitnami/redis