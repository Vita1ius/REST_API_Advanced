pipeline {
  agent any

  stages {
    stage('Scan') {
      steps {
        withSonarQubeEnv(installationName: 'sonar') { 
          bat './mvn clean org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.0.2155:sonar'
        }
      }
    }
  }
}
