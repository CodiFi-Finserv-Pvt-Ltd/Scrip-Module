pipeline {
    agent any  // This tells Jenkins to run on any available agent

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out the code from GitHub...'
                git 'https://github.com/CodiFi-Finserv-Pvt-Ltd/Scrip-Module.git'
            }
        }
        
        stage('Build') {
            steps {
                echo 'Building the project...'
                sh 'mvn clean install'  // Replace with your actual build command
            }
        }

        stage('Test') {
            steps {
                echo 'Running tests...'
                sh 'mvn test'  // Replace with your actual test command
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploying the project...'
                sh 'mvn deploy'  // Replace with your actual deploy command
            }
        }
    }

    post {
        success {
            echo 'Build completed successfully!'
        }

        failure {
            echo 'Build failed. Please check the logs.'
        }
    }
}
