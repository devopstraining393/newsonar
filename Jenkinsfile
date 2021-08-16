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
        stage('SonarQube Analysis') {
            environment {
                scannerHome = tool 'SONAR_SCANNER'
            }
            steps {
                withCredentials([string(credentialsId: 'sonar_token', variable: 'sonar_token')]) {
                    withSonarQubeEnv('SONAR_GCP') {
                        sh "${scannerHome}/bin/sonar-scanner -e -Dsonar.login=${sonar_token}"
                    }
                }
            }
        }
        stage('Quality Gate') {
            steps {
              sleep(5)
              timeout(time: 1, unit: 'MINUTES') {
                waitForQualityGate abortPipeline: true
              }
            }
        }
    }
}