job("Warmup data for IDEA") {
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