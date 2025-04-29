pipeline {
    agent any  // Tells Jenkins to run on any available agent

    stages {
        stage('Checkout') {
            steps {
                // Checkout the code from GitHub
                git 'https://github.com/CodiFi-Finserv-Pvt-Ltd/Scrip-Module.git'
            }
        }
        
        stage('Build') {
            steps {
                // Example of a build step, for instance a Maven build
                sh 'mvn clean install'  // Replace with the actual build command for your project
            }
        }

        stage('Test') {
            steps {
                // Example of a testing step
                sh 'mvn test'  // Replace with the actual test command for your project
            }
        }

        stage('Deploy') {
            steps {
                // Example of a deploy step
                sh 'mvn deploy'  // Replace with the actual deploy command for your project
            }
        }
    }
}
