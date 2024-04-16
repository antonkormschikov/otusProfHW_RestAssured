import groovy.json.JsonSlurperClassic

timestamp {
    node("maven") {
        wrap([$class: 'BuildUser']) {
            currentBuild.description  ="""
build user: $BUILD_USER
branch: $REFSPEC
"""
        def slurped = new JsonSlurperClassic().parseText($JSON_CONFIG)
            slurped.forEach(k, v -> {
              env.setProperty(k, v)
            })
        }
        stage("Checkout") {
            checkout scm
        }
        stage("Create configuration"){

        }
        stage("Run API TESTS"){
            sh "mkdir ./reports"
            sh "docker run --rm --env-file -v ./reports:/root/api_tests/allure-results ./.env -t API_tests:1.0.0"
        }
        stage("Publish allure report"){
            allure([
                    results: ["*./reports"],
                    reportBuildPolicy: ALWAYS
                    ])
        }
    }
}

0-40