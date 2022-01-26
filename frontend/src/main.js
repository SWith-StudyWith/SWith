import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import 'bootstrap';
import '@fortawesome/fontawesome-free/js/all';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'

const app = createApp(App)
app.use(store)
app.use(router)
app.mount('#app');
app.component('font-awesome-icon', FontAwesomeIcon)
