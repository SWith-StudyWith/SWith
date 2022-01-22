import { createStore } from 'vuex';

// 모듈 import
import user from './modules/user';

const store = createStore({
  modules: {
    user,
  },
});

export default store;
