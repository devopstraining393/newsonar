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
                    sh "${scannerHome}/bin/sonar-scanner -e -Dsonar.projectKey=spring-boot-base -Dsonar.host.url=http://10.16.8.88:9000 -Dsonar.login=5f00f799dfc75cd693ee109089f73eb9a0878265 -Dsonar.java.binaries=target -Dsonar.coverage.exclusions=**/.mvn/**,**/src/test/**,**/domain/**,**SpringBootApplication.java"
                }
            }
        }
        stage('Quality Gate') {
            steps {
              sh 'sleep 300'
              timeout(time: 1, unit: 'MINUTES') {
                waitForQualityGate abortPipeline: true
              }
            }
        }
    }
}