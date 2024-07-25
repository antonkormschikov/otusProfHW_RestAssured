timeout(60) {
    node("maven"){
        def testContainerName = "api_tests_$BUILD_NUMBER"
        try {
            stage("Run API tests") {
                sh "docker run --rm --network=host --name $testContainerName -v $pwd/allure-rezults:/home/anton/target/allure-rezults -t api-tests"
            }
            stage("Publish Allure report"){
                allure([
                        disabled: true,
                        results: ["$pwd/allure-results"]
                ])
            }
            stage("Telegram notification"){
                sh "curl -s -X POST https://api.telegram.org/bot7460003126:AAGHETwA70UMIQokbKS9_hJaOR7eOeDCWKo/sendMessage -d chat_id=-4272423291 -d text=\"very simple text\""

            }
        } finally {
            sh "docker stop $testContainerName"
        }

    }
}
