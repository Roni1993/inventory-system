version_settings(constraint='>=0.22.2')
load('ext://dotenv', 'dotenv')
load('ext://namespace', 'namespace_inject')
load('ext://restart_process', 'docker_build_with_restart')
load('ext://helm_resource', 'helm_resource', 'helm_repo')

allow_k8s_contexts('rancher-desktop')
# default_registry("registry:5000")

# install solace in local cluster
helm_repo('solacecharts', 'https://solaceproducts.github.io/pubsubplus-kubernetes-quickstart/helm-charts')
helm_resource('event-broker', 'solacecharts/pubsubplus-dev')

# install postgres in cluster
helm_repo('bitnami', 'https://charts.bitnami.com/bitnami')
helm_resource('inventory-db', 'bitnami/postgresql')

# set-up k8s resources
k8s_yaml([
    'infra/inventory-frontend.yaml',
    'infra/store-service.yaml',
    'infra/transport-service.yaml',
])

# set-up all docker builds
docker_build(
    "inventory-frontend",
    context="./inventory-frontend",
    dockerfile="./inventory-frontend/Dockerfile",
    live_update=[
        sync('./inventory-frontend', '/usr/src/app')
    ]
)

local_resource(
  'transport-java-compile',
  dir='./transport-service',
  cmd='./gradlew bootJar && ' +
    'unzip -o build/libs/transport-service-0.0.1-SNAPSHOT.jar -d build/jar-staging && ' +
    'rsync --inplace --checksum -r build/jar-staging/ build/jar',
  deps=['src', 'build.gradle'],
)

docker_build(
  'transport-service',
  context='./transport-service',
  live_update=[
    sync('./transport-service/build/jar/BOOT-INF/lib', '/app/lib'),
    sync('./transport-service/build/jar/META-INF', '/app/META-INF'),
    sync('./transport-service/build/jar/BOOT-INF/classes', '/app'),
  ],
)

local_resource(
  'store-java-compile',
  dir='./store-service',
  cmd='./gradlew bootJar && ' +
    'unzip -o build/libs/store-service-0.0.1-SNAPSHOT.jar -d build/jar-staging && ' +
    'rsync --inplace --checksum -r build/jar-staging/ build/jar',
  deps=['src', 'build.gradle'],
)

docker_build(
  'store-service',
  context='./store-service',
  live_update=[
    sync('./store-service/build/jar/BOOT-INF/lib', '/app/lib'),
    sync('./store-service/build/jar/META-INF', '/app/META-INF'),
    sync('./store-service/build/jar/BOOT-INF/classes', '/app'),
  ],
)