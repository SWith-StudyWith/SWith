import { createStore } from 'vuex';

// Vuex 조립
import state from './state';
import * as getters from './getters';
import * as mutations from './mutations';
import * as actions from './actions';

const store = createStore({
  namespaced: true,
  state,
  getters,
  mutations,
  actions,
});

export default store;
