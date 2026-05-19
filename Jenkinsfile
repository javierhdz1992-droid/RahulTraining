pipeline {
    agent any

    tools {
        maven 'Maven3'
        jdk 'JDK25'
    }

    stages {
        stage('Clean') {
            steps {
                bat 'mvn clean'
            }
        }

        stage('Run Tests') {
            steps {
                bat 'mvn test -Dheadless=false'
            }
        }
    }

    post {
        always {
            junit 'target/surefire-reports/*.xml'
            allure commandline: 'Allure', results: [[path: 'target/allure-results']]
            archiveArtifacts artifacts: 'target/**/*', fingerprint: true
        }
        success {
            echo 'Pruebas ejecutadas correctamente'
        }
        failure {
            echo 'Algunas pruebas fallaron'
        }
    }
}
