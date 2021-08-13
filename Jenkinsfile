pipeline {
    agent any
    tool {
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
    }
}