# Spring-Security-User-Login-and-Registration
This is a sample Spring Boot application that provides the following:
1. User login and registration
2. Database connectivity which is configurable through the application.yaml file
3. JWT authentication in urls that are filtered in the web security config class

# How to run the application?
1. Download/Clone the repository
2. Using an IDE (preferably Intellij), change the configuration in the application.yaml file found under the resources folder
3. Run the application
4. Run Postman and create a POST request through the URL "/api/v1/auth/register" with the following request body format:
5. Once you have successfully registered, log in through the URL "/api/auth/login" with the following request body format:
6. Once you have successfully logged in, use the access token to access the different functionalities of the system through the url "/api/v1/users". An example is creating a GET request in the said URL.

# Soon to be updated features:
1. Front-end design and functionality
2. Automated Tests using Mockito
