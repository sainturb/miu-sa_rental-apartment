### Back of envelope estimates :

- Your system should be able to support a workload of 100M+ users, we’ll start with one continent but we should be able to web scale eventually.

- Expected storage requirement about 50TB, it increases by a rate of 5TB every 12 month

- Uniform request load (1K/sec) through the year, but it peaks 150% at major holidays, you should be ready to scale up/down accordingly


### Functional & non-functional requirements:

- ✅ Your system should have a low latency/near real time notification service (sending emails).

- ✅ We should be able to add new services in the future. For now, we think of analytical services that needs to read all prior system state (preferably as events), with the ability to index your content into Elastic search to do text search later. In all cases, Your existing services shouldn’t be modified to support any new future services, they should just publish suitable events.

- ✅ Your system should show resiliency. Ideally, it shouldn’t have any single point of failure. We might try to kill a node randomly, your system should be able to perform.

- ✅ Your system should be easily deployable to the cloud , we don’t have on premise data centers

- ⚠️ That includes the ability to run over different availability zones for disaster recovery reasons if we need to

- ✅ It must provide some sort of logging, at least for major errors

- ⚠️ It’s nice to have the ability to show distributed tracing or monitoring dashboard. It helps with latency analysis, and assists the operation team during troubleshooting efforts.

- ✅ At Least one service, should provide its queries behind a caching layer to improve response time

- ✅ Provide security for your services both client facing and service-to-service communication

- ✅ Well documented, specially its major components and rational behind your architecture


### Tasks:

- ✅ (Apartment rental) Pick a domain of interest with your team 

- ✅ As a team, provide a diagram for your architecture where you show the major services and their communication patterns.

- ✅ Show how your system will be deployed/scaled later in the cloud

- ✅ Each team member should be responsible for at least one microservice (e.g. team of five must have 5+ services)

- ✅ Unless it’s a complete backend utility service, each service must provide an API. Show the style you used and your reasons for your decision .

- ✅ if it’s client facing , you must use JWT token for authentication/authorization.

- ✅ Dockerize your solution and deploy to Kubernetes or an equivalent container orchestration platform.

- ✅ You must deploy to a multi-node cluster, preferably on a cloud provider ( Minikube is fine for dev only but won't be accepted as final delivery )

- ✅ You must handle failure scenarios (e.g. a service call failure or a service being slow). Explain your approach and the tools you used.

- ✅ Feel free to implement using any language or framework you’re familiar with. Java/Spring cloud is a good place to start.

- ⚠️ UI is completely optional .. bonus points will be awarded if you have one.

- ⚠️ Be prepared to present your work to other teams and answer their questions if any.


### Hints:

- ✅ Kafka is a good place to publish your events to. Services don’t have to talk directly with each other, specially if we’re planning to have too many of them.

- ✅ Kubernetes provides services like services discovery, configmap, secrets. Use them instead of DYI approach, unless you have strong reasons not to.

- ⚠️ With 5TB+ of data , Mysql may not be the best persistence DB to go forward in the long term. Thinking NoSQL might help.

- ✅ For Caching, assume simplest case, not too many modifications to back-end. Redis is a good place to start.

- ⚠️ Given the available time sprint, prioritize your tasks. Instead of implementing a complete set of complex businesses scenarios/rules, simplify your process. Try to avoid Sagas. Show how the rest of the system components/services might fit in your architecture if needed.

- ✅ Use HELM charts to install components to K8s cluster. Don’t install them manually.


### Going the Extra mile!: <<optional with bonus points>>

- Providing User Interface (Web or mobile)

- Using Service Mesh like Istio to establish dashboard and distributed tracing with Zipkin

- Creating CI/CD pipeline to deploy directly to the cloud when developers merge code to the release branch.


### Deliverable:

- All your source code and deployment scripts (no github links or the like please)

- PDF report showing your architecture with a brief discussion of your choices.

- A working solution running on some cloud, or a multi-node cluster