<template>
  <div class="landing-page d-flex align-items-center justify-content-center text-center text-white vh-100">
    <div class="gradient-overlay" :class="gradientClass"></div>
    <div class="content position-relative z-1">
      <h1 class="display-4 fw-bold mb-3">Incident Ticket Form</h1>
      <p class="lead mb-4">Loading form in... {{ countdown }}...</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const gradientClass = ref("bg-step-3")
const countdown = ref(3)

onMounted(() => {
  const interval = setInterval(() => {
    countdown.value--

    if (countdown.value === 2) {
      gradientClass.value = "bg-step-2"
    } else if (countdown.value === 1) {
      gradientClass.value = "bg-step-1" 
    } else if (countdown.value <= 0) {
      clearInterval(interval)
      router.push({ name: "IncidentTicketForm" })
    }
  }, 1000)
})
</script>

<style scoped>
.landing-page {
  position: relative;
  background-color: #0f0f1b;
  overflow: hidden;
}

.gradient-overlay {
  position: absolute;
  top: -30%;
  left: 50%;
  transform: translateX(-50%);
  width: 1200px;
  height: 1200px;
  opacity: 0.5;
  filter: blur(150px);
  z-index: 0;
  transition: background 1s ease; /* anima a mudança */
}

.bg-step-3 {
  background: radial-gradient(circle at top, #3b82f6 60%, #0f0f1b 50%, #000 100%);
}

.bg-step-2 {
  background: radial-gradient(circle at top, #06b6d4 60%, #0f0f1b 50%, #000 100%);
}

.bg-step-1 {
  background: radial-gradient(circle at top, #00dc82 60%, #0f0f1b 50%, #000 100%);
}

.content {
  z-index: 1;
  max-width: 800px;
}
</style>
