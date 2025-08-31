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
                                sh 'mvn clean package'
                                      }
                               }
                 stage('Test'){
                              steps {
                                sh 'mvn test'
                                }
                             }
                 stage('Build Docker Image'){
                                steps{
                                 sh "docker build -t $IMAGE_NAME:$IMAGE_TAG ."
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
                                         docker run -d --name webserver -p $SERVER_PORT:80  $IMAGE_NAME:$IMAGE_TAG
                                            """
                                          }
                                     }
                           }
                     }
 
                                               
