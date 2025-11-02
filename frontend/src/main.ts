import { createApp } from 'vue';
import App from './App.vue';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap';

import router from './router'

const app = createApp(App)
app.use(router)
app.mount('#app')

if (window.location.pathname !== '/splash') {
  window.location.replace('/splash')
} else {
  createApp(App).use(router).mount('#app')
}