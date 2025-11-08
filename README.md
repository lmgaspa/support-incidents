# 💬 Support Incidents

This project starts from a simple idea: when someone needs help, support must be fast, clear, and human.
Here, the user opens a ticket on the website → the system records it → a message is sent → and the support team receives everything by email, neat, organized, and effortless.

It’s a flow designed to be lightweight, straightforward, and reliable.

---

# 🧩 Architecture

Microservices design: independent services communicate via RabbitMQ.

Back-end services (ticket-backend, email-backend) are built with Java 17.

Frontend is Vue.

## 🧱 How the system works

1. The user opens a ticket on the Frontend (Vue).
2. The ticket-backend receives it and sends the message to RabbitMQ (queue incident_queue).
3. The email-backend listens to this queue, sends the email, and stores the records in MongoDB.


## 👤 Author

### Luiz Gasparetto

🌐 https://andescoresoftware.com.br
📧 andescoresoftware@gmail.com


