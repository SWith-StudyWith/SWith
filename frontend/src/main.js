import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import { dragscrollNext } from "vue-dragscroll";
import 'bootstrap';
import '@fortawesome/fontawesome-free/js/all';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';

// createApp(App).use(store).use(router).mount('#app').component('font-awesome-icon', FontAwesomeIcon);
// createApp(App).use(store).use(router).mount('#app');

const app = createApp(App)
app.use(store)
app.use(router)
app.mount('#app');
app.directive('dragscroll', dragscrollNext);
app.component('font-awesome-icon', FontAwesomeIcon);
