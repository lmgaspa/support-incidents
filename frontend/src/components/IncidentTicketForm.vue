<template>
  <div class="container-fluid vh-100 d-flex justify-content-center align-items-center" style="background-color: #048ee1;">
    <form @submit.prevent="handleSubmit" class="p-4 border rounded" style="background-color: #aaebd2; width: 100%; max-width: 600px;">
      <h2 class="mb-4 text-center" style="color: #48ee;">Incident Ticket Form</h2>

      <div class="form-group mb-3">
        <label for="user" class="form-label">User</label>
        <input type="text" class="form-control" id="user" v-model="ticket.user" required />
      </div>

      <div class="form-group mb-3">
        <label for="company" class="form-label">Company</label>
        <input type="text" class="form-control" id="company" v-model="ticket.company" required />
      </div>

      <div class="form-group mb-3">
        <label for="email" class="form-label">Email</label>
        <input type="email" class="form-control" id="email" v-model="ticket.email" required />
      </div>

      <div class="form-group mb-3">
        <label for="problem" class="form-label">Problem</label>
        <input type="text" class="form-control" id="problem" v-model="ticket.problem" required />
      </div>

      <div class="form-group mb-3">
        <label for="description" class="form-label">Description of the Problem</label>
        <textarea class="form-control" id="description" v-model="ticket.description" rows="3" required></textarea>
      </div>

      <div class="form-group mb-3">
        <label for="priority" class="form-label">Priority</label>
        <select class="form-control" id="priority" v-model="ticket.priority" required>
          <option value="high">High</option>
          <option value="medium">Medium</option>
          <option value="low">Low</option>
        </select>
      </div>

      <input type="hidden" v-model="ticket.status" />

      <button type="submit" class="btn btn-lg w-100" style="background-color: #48ee; color: white;">Submit Ticket</button>
    </form>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';

export default defineComponent({
  name: 'TicketForm',
  setup() {
    const ticket = ref({
      user: '',
      company: '',
      email: '',
      problem: '',
      description: '',
      priority: 'medium',
      status: 'open',
    });

    const handleSubmit = async () => {
      if (validateForm()) {
        try {
          const response = await fetch('/api/send-ticket', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
            },
            body: JSON.stringify(ticket.value),
          });

          if (response.ok) {
            alert('Ticket sent successfully!');
          } else {
            alert('Error sending ticket!');
          }
        } catch (error) {
          console.error('Error:', error);
        }
      }
    };

    const validateEmail = (email: string): boolean => {
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      return emailRegex.test(email);
    };

    const validateForm = () => {
      // Verifica se todos os campos obrigatórios estão preenchidos
      for (const key in ticket.value) {
        if (ticket.value[key as keyof typeof ticket.value] === '') {
          alert(`${key} is required`);
          return false;
        }
      }

      // Verifica se o email é válido
      if (!validateEmail(ticket.value.email)) {
        alert('Please enter a valid email address');
        return false;
      }

      return true;
    };

    return {
      ticket,
      handleSubmit,
    };
  },
});
</script>

<style scoped>
h2 {
  font-family: 'Arial', sans-serif;
}

.form-control {
  background-color: #fffaf3;
  border: 1px solid #ff8c00;
}

.btn {
  font-weight: bold;
}

.container-fluid {
  background-color: #ffebd2;
}
</style>
