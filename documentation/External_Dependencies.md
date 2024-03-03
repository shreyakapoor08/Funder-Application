## Installation of Dependencies

In order to build and deploy the application, you will need to install the following dependencies on your machine: 

### **Java**  
To install Java, run the following commands:  

```bash
sudo apt-get update  
sudo apt install openjdk-17-jdk
```

To confirm if the installation is successful, run the following command:

```bash
java -version
```

### **Maven**  
Maven is used for building and managing the project.
To install maven, run the following command:

```bash
sudo apt install maven
```

To confirm if the installation is successful, run the following command:

```bash
maven --v
```

### **Springboot**  
Spring Boot is the framework used for building the application.
To install Springboot, run the following commands:  

```bash
sudo apt-get update
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk install springboot
```

To confirm if the installation is successful, run the following command:

```bash
spring --version 
``` 

### **MySQL Connector**  

The application uses MySQL as a database. MySQL Connector Dependency Enables your Spring Boot application to connect to a MySQL database. It provides the necessary classes and drivers for MySQL database interaction. 
To install MySQL in your machine follow :
https://dev.mysql.com/downloads/installer/ 


### **Firebase Admin SDK**

It Allows the application to interact with Firebase services. In our application we have used it to store the images.

```
<dependency>
	<groupId>com.google.firebase</groupId>
	<artifactId>firebase-admin</artifactId>
	<version>9.0.0</version>
</dependency>
```
## Other Dependencies

The project includes additional dependencies such as Spring Security, JJWT, Spring Data JPA, Lombok, JAX-B, Mockito, JUnit Jupiter, and more. These dependencies are managed by Maven and will be downloaded automatically during the build process. 
Following are the additional dependencies and their usages in our application:

### **JJWT (Java JWT: JSON Web Token)**

This dependency allows the application to work with JSON Web Tokens (JWTs), which are commonly used for authentication and authorization in web applications.

```
<dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt</artifactId>
        <version>0.9.1</version>
</dependency>
```


### **Spring Boot Starter Data JPA**

This simplifies the development of a data access layer using Spring Data JPA. It includes Hibernate as the default JPA implementation.

```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

### **Spring Boot Starter Mail**

It helps with the configuration and usage of email-related features in the application, allowing to send emails using Spring's JavaMailSender.

```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-mail</artifactId>
</dependency>
```

### **Project Lombok**

This dependency Reduces boilerplate code in the Java classes by automatically generating methods like getters, setters, and constructors. It enhances code readability and maintainability.

```
<dependency>
		<groupId>org.projectlombok</groupId>
		<artifactId>lombok</artifactId>
		<optional>true</optional>
</dependency>
```


### **JAXB API**

It provides a convenient way to bind XML schemas and Java representations, making it easier to work with XML.

```
<dependency>
	<groupId>javax.xml.bind</groupId>
	<artifactId>jaxb-api</artifactId>
	<version>2.3.1</version>
</dependency>
```

### **Mockito Core:**

A mocking framework for unit testing in Java. It allows to create mock objects to simulate the behavior of real objects, facilitating the testing of individual components.

```
<dependency>
	<groupId>org.mockito</groupId>
	<artifactId>mockito-core</artifactId>
</dependency>
```

### **JUnit Jupiter API**

This is a part of the JUnit 5 testing framework, this API provides annotations and classes for writing and executing tests.

```
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-api</artifactId>
</dependency>
```

### **Razorpay Java**

Integrates the Razorpay payment gateway into your application, allowing you to handle online payments.

```
<dependency>
	<groupId>com.razorpay</groupId>
	<artifactId>razorpay-java</artifactId>
	<version>1.4.4</version>
</dependency>
```

### **Spring Transaction Management**

It Provides support for programmatic and declarative transaction management in Spring applications.

```
<dependency>
	<groupId>org.springframework</groupId>
	<artifactId>spring-tx</artifactId>
</dependency>
```



# Building and Running the Application

Use the following Maven command to build the application:

```bash
mvn clean install
```

To run the application, use:

```bash
mvn spring-boot:run
```

The application will be accessible at http://localhost:8080.

## Dependencies used in Frontend


- [**@emotion/react**](https://emotion.sh/docs/introduction): Used for styling components with Emotion.
- [**@emotion/styled**](https://emotion.sh/docs/introduction): Styling library for Emotion.
- [**@fluentui/react**](https://developer.microsoft.com/en-us/fluentui#/): Fluent UI for React components.
- [**@fluentui/react-components**](https://developer.microsoft.com/en-us/fluentui#/): Fluent UI React components.
- [**@fluentui/react-datepicker-compat**](https://developer.microsoft.com/en-us/fluentui#/): Fluent UI-compatible datepicker.
- [**@mui/icons-material**](https://mui.com/components/material-icons/): Material-UI for icons.
- [**@mui/joy**](https://mui.com/joy-ui/getting-started/): Material-UI component for joy.
- [**@mui/material**](https://mui.com/material-ui/getting-started/): Material-UI core components.
- [**@mui/styled-engine-sc**](https://mui.com/material-ui/guides/styled-components/): Material-UI styled engine for Styled Components.
- [**@testing-library/jest-dom**](https://testing-library.com/docs/ecosystem-jest-dom/): Testing utility for Jest and DOM.
- [**@testing-library/react**](https://testing-library.com/docs/react-testing-library/intro/): Testing utility for React.
- [**@testing-library/user-event**](https://testing-library.com/docs/ecosystem-user-event/): Testing utility for user events.
- [**antd**](https://ant.design/docs/react/introduce): Ant Design for UI components.
- [**axios**](https://axios-http.com/docs/intro): HTTP client for making requests.
- [**jquery**](https://jquery.com/): JavaScript library for DOM manipulation (consider using native APIs).
- [**moment**](https://momentjs.com/): Library for date and time manipulation.
- [**pdfmake**](https://pdfmake.github.io/docs/): Library for PDF generation in the browser.
- [**react**](https://reactjs.org/docs/getting-started.html): Core library for building user interfaces in React.
- [**react-dom**](https://reactjs.org/docs/react-dom.html): Entry point to the DOM and server renderers for React.
- [**react-responsive-carousel**](https://www.npmjs.com/package/react-responsive-carousel): Carousel component for React.
- [**react-router-dom**](https://reactrouter.com/web/guides/quick-start): DOM bindings for React Router.
- [**react-scripts**](https://create-react-app.dev/docs/getting-started): Configuration and scripts for Create React App.
- [**react-swipeable**](https://github.com/dogfessional/react-swipeable): Swipeable component for React.
- [**sass**](https://sass-lang.com/guide): Syntactically Awesome Stylesheets for styling.
- [**styled-components**](https://styled-components.com/docs): Library for styling React components.
- [**web-vitals**](https://web.dev/vitals/): Library for measuring web vitals.

## Installation


To install Node.js run the following commands:  

```bash
sudo apt-get update  
sudo apt-get install nodejs  
```

After installation, verify Node.js and are installed by running:

```bash
node -v  
npm -v  
``` 

To install the external dependencies, run the following command. *Please see before running this command that you are located in the **funder-frontend folder** and not in any other folder of the project*:

```bash
npm install
```

## Running the application and Build(If Required).

Once this is done to run the application, use:

```bash
npm run start
```

The application will be accessible at http://localhost:3000 .

If you want the build of the frontend you can do the following:

```bash
npm run build
```

This will create an optimized build of the application in the build directory.

[**Go back to README.md**](../README.md)

