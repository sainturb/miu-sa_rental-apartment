helm repo add kafka-ui https://provectus.github.io/kafka-ui
helm delete kafka-ui
helm install kafka-ui kafka-ui/kafka-ui --set envs.config.KAFKA_CLUSTERS_0_NAME=local --set envs.config.KAFKA_CLUSTERS_0_BOOTSTRAPSERVERS=kafka:9092
kubectl port-forward svc/kafka-ui 8080:8080