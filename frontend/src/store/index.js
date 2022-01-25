import { createStore } from 'vuex';
import createPersistedState from "vuex-persistedstate";

// 모듈 import
import user from './modules/user';

const store = createStore({
  modules: {
    user,
  },
  plugins: [createPersistedState()]
});

export default store;
