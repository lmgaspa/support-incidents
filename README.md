# 💬 Support Incidents

Esse projeto nasce de uma ideia simples: **quando alguém precisa de ajuda, o suporte tem que ser rápido, claro e humano.**  
Aqui, o usuário abre um ticket pelo site → o sistema registra → uma mensagem é enviada → e o suporte recebe tudo por e-mail, bonitinho, organizado, sem esforço.

É um fluxo pensado para ser **leve, direto e confiável**.

---

## 🧱 Como o sistema funciona

1. O **usuário** abre um chamado no **Frontend** (Vue).
2. O **ticket-backend** recebe e **envia a mensagem para o RabbitMQ** (fila `incident_queue`).
3. O **email-backend** escuta essa fila, **envia o e-mail** e guarda os registros no **MongoDB**.

## 👤 Autor

### Luiz Gasparetto

🌐 https://andescoresoftware.com.br  
📧 andescoresoftware@gmail.com


