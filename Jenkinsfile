pipeline {
    agent any

    triggers {
        githubPush()
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'dev', url: 'https://github.com/CodiFi-Finserv-Pvt-Ltd/Scrip-Module.git'
            }
        }

        stage('Build') {
            steps {
                echo 'Building code from dev branch...'
                // Example: sh 'mvn clean install' or npm build or quarkus build
            }
        }
    }
}
