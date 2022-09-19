# Inventory-System

This repository contains an example project on how to build an event-driven application.  Within this repository you'll find following components:

- in `infra` you'll find the Infrastructure setup with kubernetes and [Tilt](https://docs.tilt.dev/)
    - a Solace helm chart sets up an Event Queue
    - a PostgreSQL Helm chart sets up the database as persistence layer
    - 3 Service & 3 Deployments execute the below mentioned services
    - an Traefik Ingress provides simple API GW functionality to route traffic to the according service
- in `inventory-frontend` you'll find the Frontend built with Next.js, Tailwind-css, daisyUI and URQL as GraphQL client
- in `delivery-service` you'll find the event generating backend that connects to an solace queue to send events
- in `store-service` you'll find the backend that processes these events
    - this service receives all events and stores them into an internal event store
    - the service also saves a calculated view into a view Table that can be requested executing any additional logic
    - the view table is exposed via a GraphQL schema that provides simple filtering, Pagination, Sorting
    - Core components are tested with TestContainers & JUnit 5

# Overview
![Architecture Overview of the Inventory System](https://raw.githubusercontent.com/Roni1993/inventory-system/master/overview.drawio.png)

## How to run

### Requirements:

- rancher-desktop
  - contains local kubernetes cluster
  - traefik preinstalled
  - allows docker builds
- Tilt CLI tool

      # Tilt needs to be installed
      brew install tilt-dev/tap/tilt

### How to start

    git clone https://github.com/Roni1993/inventory-system.git
    cd inventory-system
    # Tilt will set-up all k8s resources & compile all resources
    tilt up

### How to reach
You'll be able to reach the applications once Tilt has started up.
Make sure that your local kubernetes cluster is forwarding its ingress to localhost
it might not be publishing it's endpoint on port `80`. In this case the Links below need to be acces with the port  
- tilt UI: `http://localhost:10350/r/(all)/overview`
- frontend: `http://localhost/frontend/`
- store Graphiql UI: `http://localhost/api/graphiql`
- delivery "Management" API: `http://localhost/api/delivery`
