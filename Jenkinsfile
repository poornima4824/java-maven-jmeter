pipeline {

    //String parameter in which a user can specify the jmx file to be executed.
    parameters {
        string(name: "jmxFile",
                description: "What JMeter performance test file (jmx) would you like to execute?",
                defaultValue: 'examples-performance-test')
    }

    //Agent will differ per assignment
    agent any
	
	  tools
    {
       maven "maven"
    }

    //Credentials should be added in Jenkins
    environment {
        TEST_ENV = credentials('git')
    }

    stages {
        stage('Performance test...') {
            steps {
                //Run tests
                sh("mvn clean verify -P choose-jmx-file -DjmxFile=${jmxFile} -DtestEnvUsr=${env.TEST_ENV_USR} -DtestEnvPwd=${env.TEST_ENV_PSW}")
            }
        }
    }
    post {
        //Archive artifacts is a plugin that enables you to store for instance the target folder of a build.
        //PerfReport is a plugin that can be added to Jenkins, making nice graphs and showing trends.
        always {
            perfReport sourceDataFiles: 'target/jmeter/results/*.csv'
        }
    }
}
