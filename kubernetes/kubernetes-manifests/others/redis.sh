helm repo add bitnami https://charts.bitnami.com/bitnami
helm delete redis
helm install redis --set auth.password=redis bitnami/redis