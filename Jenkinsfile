pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/CodiFi-Finserv-Pvt-Ltd/Scrip-Module.git'
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
