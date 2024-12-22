pipeline {
    agent any

    environment {
        // Specify environment variables globally if needed
        KANIKO_NAMESPACE = 'dev'
        KANIKO_JOB_NAME = 'kaniko-build'
        KANIKO_TIMEOUT = '300s'
    }

    stages {
        stage('Clean Workspace') {
            steps {
                echo 'Cleaning workspace to ensure a clean slate...'
                deleteDir() // Deletes all files in the current workspace
            }
        }

        stage('Clone Repository') {
            steps {
                echo 'Cloning repository from GitHub...'
                git branch: 'main', url: 'https://github.com/oseitutu90/updateService.git'
            }
        }

        stage('Trigger Kaniko Build') {
            steps {
                script {
                    echo 'Triggering Kaniko Job...'
                    withCredentials([file(credentialsId: 'kubeconfig', variable: 'KUBECONFIG')]) {
                        // Apply the Kaniko Job YAML
                        sh """
                            echo 'Applying Kaniko job configuration...'
                            kubectl --kubeconfig=$KUBECONFIG apply -f kaniko-build-job.yaml
                        """

                        // Wait for Kaniko Job to Complete
                        sh """
                            echo 'Waiting for Kaniko build job to complete...'
                            kubectl --kubeconfig=$KUBECONFIG wait --for=condition=complete --timeout=${KANIKO_TIMEOUT} job/${KANIKO_JOB_NAME} -n ${KANIKO_NAMESPACE}
                        """

                        // Display Kaniko Job Logs
                        sh """
                            echo 'Retrieving Kaniko job logs...'
                            kubectl --kubeconfig=$KUBECONFIG logs job/${KANIKO_JOB_NAME} -n ${KANIKO_NAMESPACE}
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
            script {
                // Print pod descriptions for debugging
                withCredentials([file(credentialsId: 'kubeconfig', variable: 'KUBECONFIG')]) {
                    sh """
                        echo 'Describing Kaniko job pod for debugging...'
                        kubectl --kubeconfig=$KUBECONFIG describe job/${KANIKO_JOB_NAME} -n ${KANIKO_NAMESPACE}
                    """
                }
            }
        }
    }
}