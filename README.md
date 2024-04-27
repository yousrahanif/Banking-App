**Important: Don't forget to update the [Candidate README](#candidate-readme) section**

Real-time Transaction Challenge
===============================
## Overview
Welcome to Current's take-home technical assessment for backend engineers! We appreciate you taking the time to complete this, and we're excited to see what you come up with.

Today, you will be building a small but critical component of Current's core banking enging: real-time balance calculation through [event-sourcing](https://martinfowler.com/eaaDev/EventSourcing.html).

## Schema
The [included service.yml](service.yml) is the OpenAPI 3.0 schema to a service we would like you to create and host. 

## Details
The service accepts two types of transactions:
1) Loads: Add money to a user (credit)

2) Authorizations: Conditionally remove money from a user (debit)

Every load or authorization PUT should return the updated balance following the transaction. Authorization declines should be saved, even if they do not impact balance calculation.

You may use any technologies to support the service. We do not expect you to use a persistent store (you can you in-memory object), but you can if you want. We should be able to bootstrap your project locally to test.

## Expectations
We are looking for attention in the following areas:
1) Do you accept all requests supported by the schema, in the format described?

2) Do your responses conform to the prescribed schema?

3) Does the authorizations endpoint work as documented in the schema?

4) Do you have unit and integrations test on the functionality?

# Candidate README
## Bootstrap instructions
* Clone the repository containing the codebase onto your local machine.[https://github.com/codescreen/CodeScreen_amq1btcj.git]
* Open a terminal window and navigate to the directory where you cloned the repository.
* Build the Spring Boot application by running the following command: `mvn clean install`.
* Once the build is successful, start the Spring Boot application using the command: `mvn spring-boot:run`.
* Access the application in your web browser at [http://localhost:8080](http://localhost:8080).
* Interact with the application using the provided buttons on the web interface to load funds, authorize transactions, view transaction history, and check total balance.
* Enter the User ID and amount as required, then click the corresponding button to perform the action.
* To run tests, use Maven. For unit tests, run `mvn test`, and for integration tests, run `mvn integration-test`.


## Design considerations


- **Modularization:** Breaking down the application into smaller, manageable components such as controllers, services, and models allows for easier maintenance and scalability.

- **RESTful API:** Implementing a RESTful API architecture enables seamless communication between the frontend and backend, promoting flexibility and decoupling.

- **Spring Boot:** Leveraging the Spring Boot framework provides numerous benefits, including rapid development, built-in features for web development, and easy integration with other Spring modules.

- **Event sourcing:** By using event sourcing, I can capture every change to the application's state as a sequence of events, ensuring data integrity and enabling easy auditing and debugging.

- **Error handling:** Implementing robust error handling mechanisms ensures that the application gracefully handles exceptions, providing meaningful feedback to users and maintaining system stability.

- **Security:** Incorporating security measures such as input validation, authentication, and authorization safeguards sensitive user data and prevents unauthorized access or malicious activities.

- **Testing:** Writing comprehensive unit tests and integration tests ensures the reliability and correctness of the application, identifying and fixing issues early in the development lifecycle.

- **UI/UX design:** Designing an intuitive and user-friendly interface enhances the overall user experience, making it easier for users to navigate, interact with, and understand the application's functionality.

- **Performance optimization:** Optimizing database queries, minimizing network latency, and implementing caching mechanisms improve the application's performance, resulting in faster response times and better scalability.

- **Documentation:** Providing clear and comprehensive documentation facilitates collaboration among team members, assists with onboarding new developers, and ensures that the application remains maintainable in the long term.


## Bonus: Deployment considerations

# If I Deploy This Application

- **Containerization with Docker:** Package the application and its dependencies into a Docker container.
  
- **Cloud Platform Deployment:** Deploy the Docker container to a cloud platform like AWS or Azure.
  
- **CI/CD Pipeline:** Set up a basic CI/CD pipeline using a service like GitHub Actions or GitLab CI/CD to automate the build and deployment process.
  
- **Monitoring and Logging:** Use built-in monitoring and logging services provided by the cloud platform for basic monitoring and logging of the application.
  
- **Security Considerations:** Follow basic security best practices such as using HTTPS, managing access credentials securely, and regularly updating dependencies.


## ASCII art
*Optional but suggested, replace this:*
```
                                                                                
                   @@@@@@@@@@@@@@                                               
               @@@@@@@@@@@@@@@@@@@@@                                            
             @@@@@@@@@@@@@@@@@@@@@@@@@@                                         
          @@@@@@@@@@@@@@@@@@@@@@@@                                  @@@@        
        @@@@@@@@@@@@@@@@@@@@@      @@@@@@                        @@@@@@@@@      
     @@@@@@@@@@@@@@@@@@@@@    @@@@@@@@@@@@@@@                 .@@@@@@@@@@@@@@   
   @@@@@@@@@@@@@@@@@@@@   @@@@@@@@@@@@@@@@@@@@@           @@@@@@@@@@@@@@@@@@@@@ 
 @@@@@@@@@@@@@@@@@@@    @@@@@@@@@@@@@@@@@@@@@@@@@@   @@@@@@@@@@@@@@@@@@@@@@@@@@ 
    @@@@@@@@@@@@@@               @@@@@@@@@@@@@@@@@@@    @@@@@@@@@@@@@@@@@@@@    
      @@@@@@@@@@                     @@@@@@@@@@@@@@@@@@    @@@@@@@@@@@@@@       
         @@@@                          @@@@@@@@@@@@@@@@@@@@                     
                                          @@@@@@@@@@@@@@@@@@@@@@@@@@@@@         
                                            @@@@@@@@@@@@@@@@@@@@@@@@            
                                               @@@@@@@@@@@@@@@@@@               
                                                    @@@@@@@@                    
```
## License

At CodeScreen, we strongly value the integrity and privacy of our assessments. As a result, this repository is under exclusive copyright, which means you **do not** have permission to share your solution to this test publicly (i.e., inside a public GitHub/GitLab repo, on Reddit, etc.). <br>

## Submitting your solution

Please push your changes to the `main branch` of this repository. You can push one or more commits. <br>

Once you are finished with the task, please click the `Submit Solution` link on <a href="https://app.codescreen.com/candidate/7dc2dd58-9cd0-445a-afea-acc083f2b4a1" target="_blank">this screen</a>.