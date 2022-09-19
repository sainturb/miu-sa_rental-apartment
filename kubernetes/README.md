```shell
kubectl exec -it account-database-6849d8fb78-khdz4 -- psql -U postgres --password -p 5432 postgres
kubectl exec -it product-database-74b4cfb84b-c9679 -- psql -U postgres --password -p 5432 postgres

kubectl get pod -o wide
kubectl get endpoints
```