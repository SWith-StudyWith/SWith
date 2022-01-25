import { login, updateUserInfoAPI } from '../../api/user';

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
  updateUserInfo({ commit }, payload) {
    updateUserInfoAPI(
      payload,
      (res) => {
        console.log(res)
        commit('UPDATE_USER_INFO', res.data.data);
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
