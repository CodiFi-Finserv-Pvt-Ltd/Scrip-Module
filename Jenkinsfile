pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/your-org/your-repo.git'
            }
        }

        stage('Build') {
            steps {
                echo 'Building the app...'
            }
        }

        stage('Test') {
            steps {
                echo 'Running tests...'
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploying the app...'
            }
        }
    }
}
