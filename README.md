# Support Incidents System

The **Support Incidents system** is designed to manage support tickets efficiently by allowing users to create, track, and resolve issues. The system follows a microservices architecture, with two separate backends working together to handle tickets and email notifications seamlessly.

## Key Features

- **Microservices Architecture**: Two distinct backend services.
  - One backend for receiving and processing support tickets.
  - Another backend for sending email notifications via RabbitMQ.
- **Email Notifications**: Automatically sends an email to the company responsible for system maintenance when a support ticket is created.
- **Messaging Queue**: Uses RabbitMQ to route messages between the services, ensuring reliable communication.
- **Real-time Updates**: Asynchronous messaging ensures that tickets are processed and sent without delays.

## Technologies Used

- **Backend**: Spring Boot (Java)
- **Frontend**: Vue.js with Bootstrap for responsive UI
- **Database**: MongoDB for data storage
- **Messaging**: RabbitMQ for asynchronous communication between services
- **Email Service**: Java Mail Sender for sending email notifications
- **Deployment**: Vercel for frontend and Heroku for backend

## System Architecture

```mermaid
graph TD;
    User-->Frontend(Vue.js);
    Frontend-->Backend_Ticket_Service(Spring Boot);
    Backend_Ticket_Service-->MongoDB;
    Backend_Ticket_Service-->RabbitMQ;
    RabbitMQ-->Backend_Email_Service(Java_Spring);
    Backend_Email_Service-->Email_Service(Java_Mail_Sender);
    Email_Service-->Company_Email(Responsible_for_Maintenance);
