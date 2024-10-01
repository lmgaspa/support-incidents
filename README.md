# Support Incidents System

The **Support Incidents** system is designed to manage support tickets efficiently by allowing users to create, track, and resolve issues. The system follows a microservices architecture, with two separate backends working together to handle tickets and email notifications seamlessly.

## Key Features

- **Microservices Architecture**: Two distinct backend services.
  - **Ticket Backend (Microservice 1)**: Receives and processes support tickets from the frontend and sends them via RabbitMQ.
  - **Email Backend (Microservice 2)**: Processes messages from RabbitMQ and sends email notifications.
- **Email Notifications**: Automatically sends an email to the company responsible for system maintenance when a support ticket is created.
- **Messaging Queue**: Uses RabbitMQ to route messages between the services, ensuring reliable communication.
- **Real-time Updates**: Asynchronous messaging ensures that tickets are processed and sent without delays.

## Technologies Used

- **Backend**: Spring Boot (Java)
- **Frontend**: Vue.js with Bootstrap for responsive UI
- **Database**: MongoDB for data storage in the Email Backend
- **Messaging**: RabbitMQ for asynchronous communication between services
- **Email Service**: Java Mail Sender for sending email notifications
- **Deployment**: Vercel for frontend and Heroku for backend

## System Architecture

```mermaid
graph TD;
    User-->Frontend(Vue.js);
    Frontend-->Backend_Ticket_Service(Ticket Backend - Microservice 1);
    Backend_Ticket_Service-->RabbitMQ;
    RabbitMQ-->Backend_Email_Service(Email Backend - Microservice 2);
    Backend_Email_Service-->MongoDB;
    Backend_Email_Service-->Email_Service(Java Mail Sender);
    Email_Service-->Company_Email(Responsible for Maintenance);
