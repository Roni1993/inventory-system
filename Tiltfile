version_settings(constraint='>=0.22.2')
load('ext://helm_resource', 'helm_resource', 'helm_repo')
load('ext://helm_remote', 'helm_remote')

# install solace in local cluster
helm_remote(
    chart='pubsubplus-dev',
    release_name='event-broker',
    repo_url='https://solaceproducts.github.io/pubsubplus-kubernetes-quickstart/helm-charts',
    set=["solace.usernameAdminPassword=password"]
)

# install postgres in cluster
helm_remote(
    chart='postgresql',
    release_name='store-db',
    repo_url='https://charts.bitnami.com/bitnami',
    set=[
        "auth.username=store",
        "auth.password=password",
        "auth.database=store"
    ]
)

# set-up k8s resources
k8s_yaml([
    'infra/inventory-frontend.yaml',
    'infra/store-service.yaml',
    'infra/delivery-service.yaml',
    'infra/ingress.yaml',
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
  'delivery-java-compile',
  dir='./delivery-service',
  cmd='./gradlew bootJar && ' +
    'unzip -o build/libs/delivery-service-0.0.1-SNAPSHOT.jar -d build/jar-staging && ' +
    'rsync --inplace --checksum -r build/jar-staging/ build/jar',
  deps=['src', 'build.gradle'],
)

docker_build(
  'delivery-service',
  context='./delivery-service',
  live_update=[
    sync('./delivery-service/build/jar/BOOT-INF/lib', '/app/lib'),
    sync('./delivery-service/build/jar/META-INF', '/app/META-INF'),
    sync('./delivery-service/build/jar/BOOT-INF/classes', '/app'),
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