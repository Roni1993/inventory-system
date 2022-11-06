job("Warmup data for Fleet") {
    warmup(ide = Ide.Fleet) {
        scriptLocation = "./dev-env-warmup.sh"
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
        scriptLocation = "./dev-env-warmup.sh"
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
            val spaceRepo = "roni1993.registry.jetbrains.space/p/trustyserva/containers/dev-container"
            // image tags for 'docker push'
            tags {
                +"$spaceRepo:1.0.${"$"}JB_SPACE_EXECUTION_NUMBER"
                +"$spaceRepo:latest"
            }
        }
    }
}