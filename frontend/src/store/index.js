import { createStore } from 'vuex';

// 모듈 import
import user from './modules/user';
import study from './modules/study';

const store = createStore({
  modules: {
    user,
    study,
  },
});

export default store;
