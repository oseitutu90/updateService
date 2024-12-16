pipeline {
    agent any


    stages {
        stage('Clone Repository') {
            steps {
                script {
                    // Print debugging info
                    echo "Cloning repository..."
                    sh 'git --version' // Check Git version in the pipeline environment
                    sh 'git ls-remote https://github.com/oseitutu90/updateService.git' // Verify access to the repo
                }
                // Perform Git clone
                git branch: 'main', url: 'https://github.com/oseitutu90/updateService.git'
            }
        }
    }

        stage('Trigger Kaniko Build') {
            steps {
                script {
                    // Use withCredentials to bind the kubeconfig file
                    withCredentials([file(credentialsId: 'kubeconfig', variable: 'KUBECONFIG')]) {
                        // Apply the Kaniko Job YAML
                        sh """
                            kubectl --kubeconfig=$KUBECONFIG apply -f kaniko-build-job.yaml
                        """

                        // Wait for the Kaniko Job to complete
                        sh """
                            kubectl --kubeconfig=$KUBECONFIG wait --for=condition=complete --timeout=300s job/kaniko-build -n dev
                        """

                        // Check logs of Kaniko Job (Optional)
                        sh """
                            kubectl --kubeconfig=$KUBECONFIG logs job/kaniko-build -n dev
                        """
                    }
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