import { login, getUserInfo, loginKakao, updateUserInfoAPI } from '../../api/user';

const state = () => ({
  userInfo: {
    email : "ssafy@ssafy.com",
    nickname : "ssafy",
    goal : "내 꿈은 해적왕",
    profileImgUrl: ''
  },
});

const getters = {
  getUserInfo: function (state) {
    return state.userInfo
  }
};

const actions = {
  GET_USER_INFO({ commit }) {
    getUserInfo(
      (res) => {
        commit('SET_USER_INFO', res.data.data);
      },
      () => {
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
      () => {
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
  updateUserInfo({ commit }, payload) {
    updateUserInfoAPI(
      payload,
      (res) => {
        console.log(res)
        commit('UPDATE_USER_INFO', res.data.data);
      },
      () => {
        alert('서버가 아파유.')
      }
    )
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
    state.userInfo.nickname = payload.nickname
    state.userInfo.goal = payload.goal
    state.userInfo.profileImgUrl = payload.profileImgUrl
  }
};

export default {
  state,
  getters,
  actions,
  mutations,
};
