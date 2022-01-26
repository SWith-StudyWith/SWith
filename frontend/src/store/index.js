import { createStore } from 'vuex';
import createPersistedState from "vuex-persistedstate";

// 모듈 import
import user from './modules/user';
import study from './modules/study';

const store = createStore({
  modules: {
    user,
    study,
  },
  plugins: [createPersistedState()]
});

export default store;
