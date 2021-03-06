import createPersistedState from "vuex-persistedstate";
import { createStore } from 'vuex';

// 모듈 import
import user from './modules/user';
import study from './modules/study';
import memo from './modules/memo';

const store = createStore({
  modules: {
    user,
    study,
    memo,
  },
  plugins: [
    createPersistedState(
      {
        paths: ['user'],
      }
    )
  ]
});

export default store;
