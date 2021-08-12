pipeline {
    agent any
    tools {
        jdk 'openjdk-11'
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
                    sh "{scannerHome}/bin/sonar-scanner -e -Dsonar.projectKey=spring-boot-base -Dsonar.host.url=http://34.68.76.28:9000 -Dsonar.login=36525ab919ca5c4659b72e5b1047905e9711fb75"
                }
            }
        }
    }
}