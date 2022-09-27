helm repo add bitnami https://charts.bitnami.com/bitnami
helm delete kafka
helm delete zookeeper
helm install zookeeper bitnami/zookeeper \
  --set replicaCount=2 \
  --set auth.enabled=false \
  --set allowAnonymousLogin=true


helm install kafka bitnami/kafka \
  --set zookeeper.enabled=false \
  --set replicaCount=2 \
  --set externalZookeeper.servers=zookeeper.default.svc.cluster.local


#kubectl run kafka-client --restart='Never' --image docker.io/bitnami/kafka:3.2.3-debian-11-r1 --namespace default --command -- sleep infinity
#    kubectl exec --tty -i kafka-client --namespace default -- bash