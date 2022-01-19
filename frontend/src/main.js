import { createApp } from 'vue';
import io from 'socket.io-client';
import App from './App.vue';
import router from './router';
import store from './store';

const socket = io('http://localhost:3000');

const app = createApp(App);
app.use(store);
app.use(router);
app.mount('#app');
app.provide('$socket', socket);
