import { login, getUserInfo, loginKakao, loginGoogle, updateUserInfoAPI, createStudyAPI } from '../../api/user';
import router from '@/router';

const state = () => ({
  userInfo: {
    // email : "ssafy@ssafy.com",
    // nickname : "ssafy",
    // goal : "내 꿈은 해적왕",
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
        if (res.data.code === 200) {
          sessionStorage.setItem('accessToken', res.data.data.accessToken)
          commit('SET_USER_ACCESS_INFO', res.data.data)
          dispatch('GET_USER_INFO')
          router.push({ name: 'Main' })
        } else {
          console.log('잘못된 요청.')
          console.log(res.data)
        }
      },
      () => {
        alert('로그인 정보가 맞지 않습니다.')
      }
    )
  },
  LOGIN_KAKAO({ dispatch, commit }, payload) {
    console.log(payload)
    loginKakao(
      payload,
      (res) => {
        console.log(res.data)
        sessionStorage.setItem('accessToken', res.data.data.accessToken)
        commit('SET_USER_ACCESS_INFO', res.data.data)
        dispatch('GET_USER_INFO')
        router.push({ name: 'Main' })
      },
      (err) => {
        console.log(err)
        alert('서버가 아파요.')
      }
    )
  },
  LOGIN_GOOGLE({ dispatch, commit }, payload) {
    console.log(payload)
    loginGoogle(
      payload,
      (res) => {
        console.log(res.data)
        sessionStorage.setItem('accessToken', res.data.data.accessToken)
        commit('SET_USER_ACCESS_INFO', res.data.data)
        dispatch('GET_USER_INFO')
        router.push({ name: 'Main' })
      },
      (err) => {
        console.log(err)
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
        router.push({ name: 'Main' })
      },
      () => {
        alert('서버가 아파요.')
      }
    )
  },
  LOGOUT({ commit }) {
    commit('LOGOUT')
  },
  createStudy({ commit }, payload) {
    createStudyAPI(
      payload,
      (res) => {
        console.log(res)
        commit('CREATE_STUDY', res.data.data);
        router.push({ name: 'StudyDetail' })
      }
    )
  },
};

const mutations = {
  SET_USER_INFO(state, payload) {
    console.log(payload)
    state.userInfo = payload
  },
  SET_USER_ACCESS_INFO(state, payload) {
    state.userInfo.path = payload.path
  },
  UPDATE_USER_INFO(state, payload) {
    state.userInfo = payload
  },
  LOGOUT(state) {
    // state.userInfo.email = ''
    // state.userInfo.nickname = ''
    // state.userInfo.goal = ''
    // state.userInfo.accessToken = ''
    state.userInfo = {}
    sessionStorage.removeItem('accessToken')
    sessionStorage.clear()
  },
  CREATE_STUDY(state, payload) {
    console.log(payload)
    state.studyInfo = payload
  }
};

export default {
  state,
  getters,
  actions,
  mutations,
};
