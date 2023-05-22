# Grocery Store RestAPI

<p style="justify-content: center">
   <img src="https://img.shields.io/badge/versión-v1.0-blue.svg" alt="version">
   <img src="https://img.shields.io/badge/status-completed-green" alt="status">
</p>

✨ Rest API developed to Small Grocery Stores.

## ⚡️ Description
The Grocery Rest API project is a professional-grade, technically advanced CRM (Customer Relationship Management) designed for small businesses as consumers. It is developed using Spring Boot 3 and utilizes MySQL as its database management system. The API incorporates a robust security module powered by Spring Security, which implements JSON Web Tokens (JWT) for secure authentication and authorization. Additionally, the project includes features such as email notifications for account credentials and password reset functionality. With a focus on scalability and security, this CRM API provides a reliable foundation for managing various aspects of businesses' operations as consumers. Furthermore, the Twilio API is being utilized for phone number verification purposes. This CRM API offers a comprehensive solution for managing customer interactions, storing contact data, tracking sales, and facilitating internal collaboration within businesses.

## 👨‍💻 Documentation OpenAPI

- [Postman Collection](https://postman).

## 🤖 General Database Diagram

![Database Relational Model](https://github.com/chrisjosuedev/my-assets/blob/main/db-diagrams/grocery-diagram-v2.png?raw=true)
The database design for the project includes two main entities: `Articles` and `Invoice`, which are related through a one-to-many relationship forming the `Invoice Detail` entity.
Another entity, `Person` represents both customers and employees, distinguished by a type property. Each employee has a username and password, with a corresponding role defining their privileges. The available roles are "ADMIN" and "USER," with only the "ADMIN" role being able to create new users or request a password reset. Other tasks can be performed by all roles.
This database design ensures a structured representation of items, invoices, and personnel, enabling efficient management of store operations and access control based on user roles. 


## 🚀 Setup

If you have Docker installed, you can try with:

1. Clone this project: `git clone https://github.com/chrisjosuedev/grocery-rest-api.git`
2. Go to the project folder:
   `cd grocery-rest-api`
3. Change to Docker Branch: `git checkout docker-version`
4. Change the data in `docker-compose.yml`, but first, you need to do some configuration:
   > Configure a [Twilio Account](https://documentation.onesignal.com/docs/twilio-setup) and get your SID and TOKEN
   > and create or use an Outlook account that will serve as the email server sender for the generated emails (If you want
   > use other email service, check NOTES below.

   Once your Twilio Account and Email is ready, in `docker-compose.yml` change:
   - OUTLOOK_EMAIL: YOUR_EMAIL
   - OUTLOOK_PASSWORD: YOUR_EMAIL_PASSWORD
   - TWILIO_ACCOUNT_SID: YOUR_TWILIO_SID
   - TWILIO_AUTH_TOKEN: YOUR_TWILIO_TOKEN
5. In project root, to generate package jar:
   `mvn clean package`
6. Run Docker commands in project root:
   `docker-compose up --build`

## 🛠 Run

1. Once the dependencies are installed, you can run via IDE or Maven.
2. Test Endpoints via Postman (or your preferred API tester) on port 9090: `http://localhost:9090/api/v1/...`
   > Please check the documentation (Postman/Swagger) above to see the available endpoints and change `Dev Env`

## 🔗 Notes
   > If you want to use other Mail Service, please check [Spring Email](https://www.baeldung.com/spring-email) and change 
   > `host: your_service`, `username` and `password` from `application.yml` with new data.
   
   Remember change env local variable `env: MAIL: ${YOUR_ENV_NAME}` in `application.yml`. 

## 🦀 Technologies

![SpringBoot badge](https://img.shields.io/badge/springboot-java-brightgreen)
![MySQL badge](https://img.shields.io/badge/mysql-db-red)

## 🧾 License

The MIT License (MIT)
