pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
               sh 'docker --host=tcp://localhost:2375 build -t update-service:latest .'
            }
        }
        stage('Push Image') {
            steps {
                sh 'docker tag update-service:latest 192.168.1.18:32000/update-service:latest'
                sh 'docker push 192.168.1.18:32000/update-service:latest'
            }
        }
        stage('Deploy') {
            steps {
                sh 'kubectl apply -k k8s/overlays/local'
            }
        }
    }
}