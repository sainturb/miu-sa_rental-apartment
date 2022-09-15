```shell
docker swarm init

docker swarm join-token manager

docker swarm leave

docker swarm join-token worker

docker node ls
```

```shell
docker stack deploy --compose-file docker-compose.yml cloud

#docker-compose up -d --scale product-service.yml=3
```