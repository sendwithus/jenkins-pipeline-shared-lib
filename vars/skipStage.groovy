def call() {
    if (args.action == 'checkLint') {
        return checkLint()
    }
    error 'skipStage has been called without valid arguments'
}

def checkLint() {
    env.LINT_SKIP = "false"
    result = sh (script: "git log -1 | grep '.*\\[lint skip\\].*'", returnStatus: true)
    if (result == 0) {
        env.CI_SKIP = "true"
        error "'[lint skip]' found in git commit message. Aborting."
    }
}
