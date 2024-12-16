pipeline {
    agent any

    environment {
        KUBE_CONFIG = credentials('kubeconfig') // Reference the kubeconfig credential
    }

    stages {
        stage('Clone Repository') {
            steps {
                git 'https://github.com/oseitutu90/updateService.git'
            }
        }

        stage('Trigger Kaniko Build') {
            steps {
                script {
                    // Apply the Kaniko Job YAML
                    sh """
                        kubectl --kubeconfig=$KUBE_CONFIG apply -f kaniko-build-job.yaml
                    """

                    // Wait for the Kaniko Job to complete
                    sh """
                        kubectl --kubeconfig=$KUBE_CONFIG wait --for=condition=complete --timeout=300s job/kaniko-build -n dev
                    """

                    // Check logs of Kaniko Job (Optional)
                    sh """
                        kubectl --kubeconfig=$KUBE_CONFIG logs job/kaniko-build -n dev
                    """
                }
            }
        }
    }

    post {
        success {
            echo 'Build and push completed successfully!'
        }
        failure {
            echo 'Build or push failed!'
        }
    }
}