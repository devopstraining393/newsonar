pipeline {
    agent any
    tools {
        maven '3.8.1'
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests=true'
            }
        }
        stage('Unit Tests') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Sonar Analysis') {
            environment {
                scannerHome = tool 'SONAR_SCANNER'
            }
            steps {
                withSonarQubeEnv('SONAR_GCP') {
                    sh "{scannerHome}/bin/sonar-scanner -e -Dsonar.projectKey=spring-boot-base -Dsonar.host.url=http://34.68.76.28:9000 -Dsonar.login=0b2aa115a74d5ed1eeb8d82abe542618416e7306"
                }
            }
        }
    }
}