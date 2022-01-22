import { login, getUserInfo } from '../../api/user';

const state = () => ({
  user: {},
});

const getters = {};

const actions = {
  GET_USER_INFO({ commit }) {
    getUserInfo(
      (res) => {
        commit('SET_USER_INFO', res.data.data);
      },
      (err) => {
        alert('서버가 아파요.')
      }
    )
  },
  LOGIN({ dispatch }, payload) {
    login(
      payload,
      (res) => {
        dispatch('GET_USER_INFO')
        localStorage.setItem('accessToken', res.data.accessToken)
      },
      (err) => {
        alert('서버가 아파요.')
      }
    )
  }
};

const mutations = {
  SET_USER_INFO(state, payload) {
    state.user = payload;
  }
};

export default {
  state,
  getters,
  actions,
  mutations,
};
