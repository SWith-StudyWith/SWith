import { login, getUserInfo, loginKakao } from '../../api/user';

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
  LOGIN({ dispatch, commit }, payload) {
    login(
      payload,
      (res) => {
        localStorage.setItem('accessToken', res.data.data.accessToken)
        commit('SET_USER_ACCESS_TOKEN', res.data.data.accessToken)
        dispatch('GET_USER_INFO')
      },
      (err) => {
        alert('서버가 아파요.')
      }
    )
  },
  LOGIN_KAKAO({ dispatch }, payload) {
    loginKakao(
      payload,
      (res) => {
        console.log(res.data)
        localStorage.setItem('accessToken', res.data.data.accessToken)
        dispatch('GET_USER_INFO')
      },
      (err) => {console.log(err)}
    )
  }
};

const mutations = {
  SET_USER_INFO(state, payload) {
    state.user.email = payload.email;
    state.user.nickname = payload.nickname;
    state.user.goal = payload.goal;
  },
  SET_USER_ACCESS_TOKEN(state, payload) {
    state.user.accessToken = payload
  }
};

export default {
  state,
  getters,
  actions,
  mutations,
};
