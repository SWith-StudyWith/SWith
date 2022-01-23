<<<<<<< HEAD
import { login, getUserInfo, loginKakao } from '../../api/user';
=======
import { login, getUserInfo, putMypage, updateUserInfo } from '../../api/user';
>>>>>>> develop

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
<<<<<<< HEAD
    state.user.email = payload.email;
    state.user.nickname = payload.nickname;
    state.user.goal = payload.goal;
  },
  SET_USER_ACCESS_TOKEN(state, payload) {
    state.user.accessToken = payload
=======
    state.userInfo = payload;
  },
  UPDATE_USER_INFO(state, payload) {
    state.userInfo.nickname = payload.user.nickname
    state.userInfo.goal = payload.user.goal
>>>>>>> develop
  }
};

export default {
  state,
  getters,
  actions,
  mutations,
};
