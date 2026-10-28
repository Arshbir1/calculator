
pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'snowden69/calculator'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh './mvnw -B clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh './mvnw -B test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Docker Build') {
            steps {
                sh '''
                    docker build \
                        -t ${DOCKER_IMAGE}:${BUILD_NUMBER} \
                        -t ${DOCKER_IMAGE}:latest .
                '''
            }
        }

        stage('Docker Push') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub-credentials',
                    usernameVariable: 'DOCKERHUB_USER',
                    passwordVariable: 'DOCKERHUB_TOKEN'
                )]) {
                    sh '''
                        set +x
                        echo "$DOCKERHUB_TOKEN" | docker login \
                            -u "$DOCKERHUB_USER" --password-stdin
                        docker push ${DOCKER_IMAGE}:${BUILD_NUMBER}
                        docker push ${DOCKER_IMAGE}:latest
                        docker logout
                    '''
                }
            }
        }

        stage('Deploy') {
            steps {
                sh '''
                    set -eu

                    export CALCULATOR_IMAGE="${DOCKER_IMAGE}:${BUILD_NUMBER}"

                    docker pull "$CALCULATOR_IMAGE"

                    docker-compose -p calculator up -d \
                        --no-build --force-recreate

                    for attempt in $(seq 1 30); do
                        if curl -fsS \
                            http://localhost:8083/actuator/health; then
                            echo "Deployment health check passed."
                            exit 0
                        fi
                        echo "Waiting for the application (attempt $attempt/30)..."
                        sleep 5
                    done

                    echo "Deployment health check failed."
                    docker-compose -p calculator ps
                    exit 1
                '''
            }
        }
    }
}

