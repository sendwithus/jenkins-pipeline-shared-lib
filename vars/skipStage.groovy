def call(Map args) {
    if (args.action == 'lint') {
        return lint()
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
