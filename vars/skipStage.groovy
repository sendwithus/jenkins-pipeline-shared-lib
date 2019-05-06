def call(Map args) {
    if (args.action == 'lint') {
        return lint()
    }
    if (args.action == 'pr') {
        return prEnv()
    }
    error 'skipStage has been called without valid arguments'
}

def lint() {
    env.LINT_SKIP = "false"
    result = sh (script: "git log -1 | grep '.*\\[lint skip\\].*'", returnStatus: true)
    if (result == 0) {
        env.LINT_SKIP = "true"
        echo "'[lint skip]' found in git commit message. Aborting."
    }
}

def prEnv() {
    env.PRENV_CREATE = "false"
    result = sh (script: "git log -1 | grep '.*\\[pr env\\].*'", returnStatus: true)
    if (result == 0) {
        env.PRENV_CREATE = "true"
        echo "'[pr env]' found in git commit message. Creating."
    }
}
