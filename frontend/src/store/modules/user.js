import { login, getUserInfo, loginKakao, updateUserInfo } from '../../api/user';

const state = () => ({
  userInfo: {},
});

const getters = {
  getUserInfo: function (state) { return state.userInfo },
};

const actions = {
  GET_USER_INFO({ commit }) {
    getUserInfo(
      (res) => {
        commit('SET_USER_INFO', res.data.data);
      },
      (err) => {
        alert('서버가 아파요!')
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
        localStorage.setItem('accessToken', res.data.data.accessToken)
        dispatch('GET_USER_INFO')
      },
      (err) => {console.log(err)}
    )
  },
  UPDATE_USER_INFO({ dispatch, commit }, payload) {
    updateUserInfo(
      payload,
      (res) => {
        dispatch('GET_USER_INFO')
        commit('UPDATE_MYPAGE', res.data.data);
      },
      (err) => {
        alert('서버가 아파유.')
      }
    )
  },
  LOGOUT({ commit }) {
    commit('LOGOUT')
  }
};

const mutations = {
  SET_USER_INFO(state, payload) {
    state.userInfo.email = payload.email;
    state.userInfo.nickname = payload.nickname;
    state.userInfo.goal = payload.goal;
  },
  SET_USER_ACCESS_TOKEN(state, payload) {
    state.userInfo.accessToken = payload
  },
  UPDATE_USER_INFO(state, payload) {
    state.userInfo.nickname = payload.user.nickname
    state.userInfo.goal = payload.user.goal
  },
  LOGOUT(state) {
    state.userInfo.email = ''
    state.userInfo.nickname = ''
    state.userInfo.goal = ''
    state.userInfo.accessToken = ''
    localStorage.removeItem('accessToken')
  }
};

export default {
  state,
  getters,
  actions,
  mutations,
};
