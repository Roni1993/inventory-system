job("Warmup data for Fleet") {
    warmup(ide = Ide.Fleet) {
        // path to the warm-up script
        scriptLocation = "./dev-env-warmup.sh"
        // use image specified in the devfile
        // devfile = ".space/devfile.yaml"
    }

    // optional
    git {
        // fetch the entire commit history
        depth = UNLIMITED_DEPTH
        // fetch all branches
        refSpec = "refs/*:refs/*"
    }
}

job("Warmup data for IDEA") {
    warmup(ide = Ide.Idea) {
        // path to the warm-up script
        scriptLocation = "./dev-env-warmup.sh"
        // use image specified in the devfile
        // devfile = ".space/devfile.yaml"
    }

    // optional
    git {
        // fetch the entire commit history
        depth = UNLIMITED_DEPTH
        // fetch all branches
        refSpec = "refs/*:refs/*"
    }
}

job("Build and push dev container") {
    host("Build artifacts and a Docker image") {
        dockerBuildPush {
            // Docker context, by default, project root
            context = "docker"
            // path to Dockerfile relative to project root
            // if 'file' is not specified, Docker will look for it in 'context'/Dockerfile
            file = "Dockerfile"
            labels["vendor"] = "roni1993"
            args["HTTP_PROXY"] = "http://10.20.30.1:123"

            val spaceRepo = "roni1993.registry.jetbrains.space/p/TrustyServa/roni1993/dev-container"
            // image tags for 'docker push'
            tags {
                +"$spaceRepo:1.0.${"$"}JB_SPACE_EXECUTION_NUMBER"
                +"$spaceRepo:latest"
            }
        }
    }
}