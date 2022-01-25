import { login, getUserInfo, loginKakao, loginGoogle, updateUserInfoAPI } from '../../api/user';
import router from '@/router';

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
    console.log(payload)
    loginKakao(
      payload,
      (res) => {
        console.log(res.data)
        localStorage.setItem('accessToken', res.data.data.accessToken)
        commit('SET_USER_ACCESS_TOKEN', res.data.data.accessToken)
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
        localStorage.setItem('accessToken', res.data.data.accessToken)
        commit('SET_USER_ACCESS_TOKEN', res.data.data.accessToken)
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
