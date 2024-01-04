node {

 

 

   
  stage('SCM') {
    checkout scm
  }

 

 

  stage('Build') {
    def scannerHome = tool 'mvnw';
                    sh "${scannerHome}/bin/mvn clean install"
	  }

 

 

  stage('SonarQube Analysis') {
    def scannerHome = tool 'sonar';

 

                withSonarQubeEnv('sonar') {
                    sh "${scannerHome}/bin/sonar-scanner"
                }
	  }

 

 

}