import { createRouter, createWebHistory } from 'vue-router'
import HomeVue from '../components/HomeVue.vue'
import IncidentTicketForm from '../components/IncidentTicketForm.vue'

const routes = [
  { path: '/', redirect: '/splash' },
  { path: '/splash', component: HomeVue },
  { path: '/incident-form', name: 'IncidentTicketForm', component: IncidentTicketForm }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
