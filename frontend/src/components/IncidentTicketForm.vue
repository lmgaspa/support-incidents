<template>
  <div class="d-flex justify-content-center align-items-center bg-gradient-custom full-height">
    <div class="background-gradient"></div>
    <form @submit.prevent="handleSubmit" class="p-4 border rounded bg-white shadow-sm w-100" style="max-width: 600px;">
      <h2 class=" text-center text-primary">Incident Ticket Form</h2>

      <div class="form-group mb-12">
        <label for="user" class="form-label">User</label>
        <input type="text" class="form-control" id="user" v-model="ticket.user" @input="validateForm" required />
      </div>

      <div class="form-group mb-12">
        <label for="company" class="form-label">Company</label>
        <input type="text" class="form-control" id="company" v-model="ticket.company" @input="validateForm" required />
      </div>

      <div class="form-group mb-12">
        <label for="emailFrom" class="form-label">Email from Company</label>
        <input type="email" class="form-control" id="emailFrom" v-model="ticket.emailFrom" @input="validateForm" required />
        <small v-if="!isEmailFromValid && ticket.emailFrom" class="text-danger">Please enter a valid email address</small>
      </div>

      <div class="form-group mb-12">
        <label for="emailTo" class="form-label">Email to Support Company</label>
        <input type="email" class="form-control" id="emailTo" v-model="ticket.emailTo" @input="validateForm" required />
        <small v-if="!isEmailToValid && ticket.emailTo" class="text-danger">Please enter a valid email address</small>
      </div>

      <div class="form-group mb-12">
        <label for="problem" class="form-label">Problem</label>
        <input type="text" class="form-control" id="problem" v-model="ticket.problem" @input="validateForm" required />
      </div>

      <div class="form-group mb-3">
        <label for="description" class="form-label">Description of the Problem</label>
        <textarea class="form-control" id="description" v-model="ticket.description" @input="validateForm" rows="2" required></textarea>
      </div>

      <div class="form-group mb-4">
        <label for="priority" class="form-label">Priority</label>
        <select class="form-control" id="priority" v-model="ticket.priority" @change="validateForm" required>
          <option value="high">High</option>
          <option value="medium">Medium</option>
          <option value="low">Low</option>
        </select>
      </div>

      <button
        type="submit"
        class="btn btn-primary btn-lg w-100 custom-btn"
        :disabled="!isFormValid"
      >
        Submit Ticket
      </button>
    </form>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, computed } from 'vue';

export default defineComponent({
  name: 'TicketForm',
  setup() {
    const ticket = ref({
      user: '',
      company: '',
      emailFrom: '',
      emailTo: '',
      problem: '',
      description: '',
      priority: 'medium',
    });

    const isEmailFromValid = ref(false);
    const isEmailToValid = ref(false);

    const validateEmail = (email: string): boolean => {
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      return emailRegex.test(email);
    };

    const validateForm = () => {
      isEmailFromValid.value = validateEmail(ticket.value.emailFrom);
      isEmailToValid.value = validateEmail(ticket.value.emailTo);

      isFormValid.value =
        ticket.value.user.trim() !== '' &&
        ticket.value.company.trim() !== '' &&
        isEmailFromValid.value &&
        isEmailToValid.value &&
        ticket.value.problem.trim() !== '' &&
        ticket.value.description.trim() !== '' &&
        ticket.value.priority.trim() !== '';
    };

    const isFormValid = ref(false);

    const handleSubmit = async () => {
      if (isFormValid.value) {
        try {
          const response = await fetch('https://ticketsupport-97c66f2a0810.herokuapp.com/send-ticket', {
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

    return {
      ticket,
      handleSubmit,
      validateForm,
      isEmailFromValid,
      isEmailToValid,
      isFormValid,
    };
  },
});
</script>

<style scoped>
.full-height {
  height: 100vh;
  overflow: hidden;
  position: relative;
  background-color: #0f0f1b;
}

.background-gradient {
  position: absolute;
  top: -30%;
  left: 50%;
  transform: translateX(-50%);
  width: 1200px;
  height: 1200px;
  background: radial-gradient(circle at top, #00dc82 60%, #0f0f1b 50%, #000 100%);
  opacity: 0.3;
  filter: blur(150px);
  z-index: 0;
}

/* Garante que o formulário fique acima */
form {
  z-index: 1;
  position: relative;
}

/* Botão */
.custom-btn {
  background-color: #3b82f6;
  transition: background-color 0.3s ease;
}

.custom-btn:hover {
  background-color: #2563eb;
}
</style>
