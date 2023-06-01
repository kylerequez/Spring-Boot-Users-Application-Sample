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
![image](https://github.com/kylerequez/Spring-Boot-Users-Application-Sample/assets/82488140/5fc9193f-9e24-4ef5-88b8-66fa96f08b6d)
5. Open your email and click the link provided to verify your registration.
![image](https://github.com/kylerequez/Spring-Boot-Users-Application-Sample/assets/82488140/b65041f9-6c3a-4b4c-ac4b-9eae58914179)
![image](https://github.com/kylerequez/Spring-Boot-Users-Application-Sample/assets/82488140/a3900d07-60b3-460c-b243-f85fbe873bb2)
6. Once you have successfully registered, log in through the URL "/api/auth/login" with the following request body format:
![image](https://github.com/kylerequez/Spring-Boot-Users-Application-Sample/assets/82488140/19e3cc8b-1cb4-4614-b780-75229fa19ae0)
7. Once you have successfully logged in, use the access token to access the different functionalities of the system through the url "/api/v1/users". An example is creating a GET request in the said URL.
![image](https://github.com/kylerequez/Spring-Boot-Users-Application-Sample/assets/82488140/fecbb9e1-8504-480b-b0ce-f3819df4c7d6)

# Soon to be updated features:
1. Front-end design and functionality
2. Automated Tests using Mockito
