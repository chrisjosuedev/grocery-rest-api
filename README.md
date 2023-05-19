# Grocery Store RestAPI

<p style="justify-content: center">
   <img src="https://img.shields.io/badge/versión-v1.0-blue.svg" alt="version">
   <img src="https://img.shields.io/badge/status-completed-green" alt="status">
</p>

✨ Rest API developed to Small Grocery Stores.

## ⚡️ Description
The Grocery Rest API project is a professional-grade, technically advanced REST API for a small store. It is developed using Spring Boot 3 and utilizes MySQL as its database management system. The API incorporates a robust security module powered by Spring Security, which implements JSON Web Tokens (JWT) for secure authentication and authorization. Additionally, the project includes features such as email notifications for account credentials and password reset functionality. With a focus on scalability and security, this API provides a reliable foundation for managing various aspects of a small store's operations. Additionally, the Twilio API is being used for phone number verification purposes.

## 👨‍💻 Documentation OpenAPI

- [Postman Collection](https://postman).
- [OpenAPI Swagger Spec](http://url).

## 🤖 General Database Diagram

![Database Relational Model](https://github.com/chrisjosuedev/my-assets/blob/main/db-diagrams/grocery-diagram-v2.png?raw=true)
The database design for the project includes two main entities: `Articles` and `Invoice`, which are related through a one-to-many relationship forming the `Invoice Detail` entity.
Another entity, `Person` represents both customers and employees, distinguished by a type property. Each employee has a username and password, with a corresponding role defining their privileges. The available roles are "ADMIN" and "USER," with only the "ADMIN" role being able to create new users or request a password reset. Other tasks can be performed by all roles.
This database design ensures a structured representation of items, invoices, and personnel, enabling efficient management of store operations and access control based on user roles.


## 🚀 Setup
> This is the local version for execution. For running it with a Docker image, please refer to the "docker-version" branch for detailed instructions on how to configure the image correctly.

1. Clone this project: `git clone https://github.com/chrisjosuedev/grocery-rest-api.git`
2. Go to the project folder:
   `cd grocery-rest-api`
3. Run `init.sql` script from init folder via mysql.
4. Do some configuration:
   > Configure a [Twilio Account](https://documentation.onesignal.com/docs/twilio-setup) and get your SID and TOKEN
   > and create or use an Outlook account that will serve as the email server sender for the generated emails (If you want
   > use other email service, check NOTES below.

   Once your Twilio Account and Email is ready, create the follow environment variables:
    - OUTLOOK_EMAIL: YOUR_EMAIL
    - OUTLOOK_PASSWORD: YOUR_EMAIL_PASSWORD
    - TWILIO_ACCOUNT_SID: YOUR_TWILIO_SID
    - TWILIO_AUTH_TOKEN: YOUR_TWILIO_TOKEN

## 🛠 Run

1. Once the dependencies are installed, you can run via IDE or Maven.

## 🧪 Unit Tests & Integration Tests
The unit tests and integration tests can be found in the directory `src/test/java`

## 🔗 Notes
> If you want to use other Mail Service, please check [Spring Email](https://www.baeldung.com/spring-email) and change
> `host: your_service`, `username` and `password` from `application.yml` with new data.

Remember change env local variable `env: MAIL: ${YOUR_ENV_NAME}` in `application.yml`.

## 🦀 Technologies

![SpringBoot badge](https://img.shields.io/badge/springboot-java-brightgreen)
![MySQL badge](https://img.shields.io/badge/mysql-db-red)

## 🧾 License

The MIT License (MIT)
