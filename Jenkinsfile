pipeline {
  agent any

  triggers {
    pollSCM('* * * * *')  // check every minute
  }

  stages {
    stage('Checkout') {
      steps {
        // Checkout the 'dev' branch from the GitHub repository
        git branch: 'dev', url: 'https://github.com/CodiFi-Finserv-Pvt-Ltd/Scrip-Module.git'
      }
    }

    stage('Build') {
      steps {
        // Build the project using Maven
        sh './mvnw clean package -DskipTests'
      }
    }

    stage('Deploy to UAT') {
      steps {
        // Deployment steps (e.g., copy the jar file to UAT server)
        echo "Deployed to UAT"
      }
    }
  }
}
