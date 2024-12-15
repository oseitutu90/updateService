pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh 'docker build -t update-service:latest .'
            }
        }
        stage('Push Image') {
            steps {
                sh 'docker tag update-service:latest <your-registry>/update-service:latest'
                sh 'docker push <your-registry>/update-service:latest'
            }
        }
        stage('Deploy') {
            steps {
                sh 'kubectl apply -k k8s/overlays/local'
            }
        }
    }
}
