pipeline{
   agent any 
       tools{
              maven  'Maven3'
           }
       environment {
           REGISTRY = "docker.io"
           IMAGE_NAME = "javeserver"
          IMAGE_TAG = "latest"
          DOCKERHUB_CREDENTIALS = "creds"
          SERVER_PORT ="8085"
                   }
         stages{
                   stage('Checkout'){
                            steps{
                                git branch: 'master', url: 'https://github.com/Curiousgoal202/Project1111.git'
                                     }
                              }
                  stage('Build'){
                            steps{
                                sh 'mvn clean install -DskipTests'
                                      }
                               }
                 stage('Security Scan'){
                            steps{
                               sh 'docker run --rm -i hadolint/hadolint <Dockerfile || true'
                            }
                 }
                 stage('Test'){
                                steps{
                                    sh 'mvn test'
                                      }
                              }
                 
                 stage('Build Docker Image'){
                                steps{
                                 sh "docker build -t $IMAGE_NAME:$IMAGE_TAG ."
                                     }
                                   }
            stage('Push Docker Image') {
    steps {
        script {
            withCredentials([usernamePassword(credentialsId: 'creds',
                                              usernameVariable: 'DOCKER_USER',
                                              passwordVariable: 'DOCKER_PASS')]) {
                sh """
                    echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin
                    docker tag $IMAGE_NAME:$IMAGE_TAG $DOCKER_USER/$IMAGE_NAME:$IMAGE_TAG
                    docker push $DOCKER_USER/$IMAGE_NAME:$IMAGE_TAG
                """
            }
        }
    }
}


                                 
                 stage('Stop Old Container'){
                                    steps{
                                    sh """
                                           docker stop webserver || true
                                           docker rm webserver || true
                                       """
                                       }
                                    }
                 stage('Run New Container'){
                                      steps{
                                       sh """
                                         docker run -d --name webserver -p $SERVER_PORT:8080  $IMAGE_NAME:$IMAGE_TAG
                                            """
                                          }
                                     }
                stage('Health Check'){
                                    steps{
                                       script{
                                           sh "sleep 5"
                                           sh "curl -f http://localhost:$SERVER_PORT || exit 1"
                                       }
                                    }
                   
                }
                           }
                     }
 
         post {
        success {
            echo "✅ Deployment successful!"
            mail to: 'santosgoal2024@gmail.com',
                 subject: "SUCCESS: Webserver Pipeline",
                 body: "Your webserver is up on port $SERVER_PORT"
        }
        failure {
            echo "❌ Deployment failed!"
            mail to: 'santosgoal2024@gmail.com',
                 subject: "FAILED: Webserver Pipeline",
                 body: "Please check the Jenkins logs for details."
        }
    }
}


                                      
