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
                    sh "${scannerHome}/bin/sonar-scanner -e -Dsonar.projectKey=spring-boot-base -Dsonar.host.url=http://10.16.8.88:9000 -Dsonar.login=4d9593e2a0fdaf48cfdabe5b707442e9ee4436f3 -Dsonar.java.binaries=target -Dsonar.coverage.exclusions=**/.mvn/**,**/src/test/**,**/domain/**,**SpringBootApplication.java"
                }
            }
        }
        stage('Quality Gate') {
            sleep(5)
            steps {
              timeout(time: 1, unit: 'MINUTES') {
                waitForQualityGate abortPipeline: true
              }
            }
        }
    }
}