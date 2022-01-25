import { login, getUserInfo, loginKakao, loginGoogle, updateUserInfo } from '../../api/user';
import router from '@/router';

const state = () => ({
  userInfo: {},
});

const getters = {};

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
          localStorage.setItem('accessToken', res.data.data.accessToken)
          commit('SET_USER_ACCESS_TOKEN', res.data.data.accessToken)
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
    loginKakao(
      payload,
      (res) => {
        localStorage.setItem('accessToken', res.data.data.accessToken)
        commit('SET_USER_ACCESS_TOKEN', res.data.data.accessToken)
        dispatch('GET_USER_INFO')
      },
      (err) => {
        console.log(err)
        alert('서버가 아파요.')
      }
    )
  },
  LOGIN_GOOGLE({ dispatch }, payload) {
    loginGoogle(
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
