# catalog-service
A simple Microservice exercise from chapter 1 of Cloud Native Spring in Action by Thomas Vitale


How to start project:

- minikube start or minikube start --driver=docker if you don't have set docker as default container driver
- ./gradlew bootBuildImage (builds the container image that needs to be deployed to the cluster in k8s) 
- docker images catalog-service:0.0.1-SNAPSHOT (get details about the image you just created)
- docker run --rm --name catalog-service -p 8080:8080 catalog-service:0.0.1-SNAPSHOT (To check if the image you just containerized is working correctly)
- minikube image load catalog-service:0.0.1-SNAPSHOT to import your image to the cluster
- kubectl create deployment catalog-service --image=catalog-service:0.0.1-SNAPSHOT
- verify the creation of the deployment object as follows : kubectl get deployment
- verify the creation of the POD: kubectl get pod
- to check logs of the application: kubectl logs deployment/catalog-service
- by default application in k8s are not accessible so you have to: kubectl expose deployment catalog-service --name=catalog-service --port=8080
- by now you will have a service object, service objects are used to expose the application to other components inside the cluster
- kubectl get service catalog-service (to check that the service has been created correctly)
- for port forwarding: kubectl port-forward service/catalog-service 8000:8080
- now, to terminate the port forwarding process do ctrl + c then
- delete the service: kubectl delete service catalog-service
- delete the deployment: kubectl delete deployment catalog-service
- stop k8s with minikube stop


To just test it out:
- ./gradlew test
