import { login, getUserInfo, putMypage, updateUserInfo } from '../../api/user';

const state = () => ({
  userInfo: {
    // email : "ssafy@ssafy.com",
    // nickname : "ssafy",
    // goal : "내 꿈은 해적왕"
  },
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
  }
};

const mutations = {
  SET_USER_INFO(state, payload) {
    state.userInfo = payload;
  },
  UPDATE_USER_INFO(state, payload) {
    state.userInfo.nickname = payload.user.nickname
    state.userInfo.goal = payload.user.goal
  }
};

export default {
  state,
  getters,
  actions,
  mutations,
};
