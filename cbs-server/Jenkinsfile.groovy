pipeline {
    agent any
    stages {
        stage('Prepare') {
            steps {
                parallel Checkout: {
                    git url: "https://github.com/theFaustus/cinema-booking-system.git"
                }, 'Verify': {
                    echo 'Verifying...'
                }
            }
        }

        stage('Build') {
            steps {
                sh 'curl  --request GET http://c9b6b6f1.ngrok.io/start/'
                sh 'mvn clean package'
                archiveArtifacts 'cbs-server/target/*.war'
            }
        }


        stage('Tests') {
            steps {
                sh 'mvn clean test -Dmaven.test.failure.ignore=true'
                junit '**/target/surefire-reports/TEST-*.xml'
            }
        }

        stage('Deploy') {
            steps {
                input 'Are you sure?'
                echo 'Deploying...'
                build job: 'cbs-deployment'
            }
        }

        stage('Release') {
            steps {
                echo 'Releasing...'
            }
        }

        stage('Deploy @ Prod') {
            steps {
                echo 'Deploying @ Prod...'
            }
        }
    }

    post {
        always {
            echo 'I have finished.'
            sh 'curl  --request GET http://c9b6b6f1.ngrok.io/xmas/'
        }
        success {
            echo 'Job succeeeded!'
            sh 'curl  --request GET http://c9b6b6f1.ngrok.io/ok/'
        }
        unstable {
            echo 'I am unstable :/'
        }
        failure {
            echo 'I failed :('
            sh 'curl --request GET http://c9b6b6f1.ngrok.io/nok/'
        }
        changed {
            echo 'Things were different before...'
        }
    }
}