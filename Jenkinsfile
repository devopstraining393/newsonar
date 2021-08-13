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
                        sh "${scannerHome}/bin/sonar-scanner -e -Dsonar.projectKey=spring-boot-base -Dsonar.host.url=http://10.16.8.88:9000 -Dsonar.login=${sonar_token} -Dsonar.java.binaries=target -Dsonar.coverage.exclusions=**/.mvn/**,**/src/test/**,**/domain/**,**SpringBootApplication.java"
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