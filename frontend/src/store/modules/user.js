import { login, getUserInfo, loginKakao, loginGoogle, updateUserInfoAPI, signOut } from '../../api/user';
import router from '@/router';
import notifications from '@/composables/notifications'

const { notifyWarning, notifyDanger } = notifications();

const state = () => ({
  userInfo: {
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
        commit('UPDATE_USER_INFO', res.data.data);
      },
      () => {
        notifyWarning('서버가 아파요!😰')
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
        } else if (res.data.code === 404) {
          notifyDanger('존재하지 않는 이메일입니다.😦')
          console.log(res.data)
        }
      },
      () => {
        notifyDanger('로그인 정보가 맞지 않습니다.😅')
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
        notifyWarning('서버가 아파요!😰')
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
        notifyWarning('서버가 아파요!😰')
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
        notifyWarning('서버가 아파요!😰')
      }
    )
  },
  LOGOUT({ commit }) {
    commit('LOGOUT')
  },
  SIGNOUT({ dispatch }) {
    signOut(
      (res) => {
        if (res.data.code === 200) {
          console.log('탈퇴성공~')
          dispatch('LOGOUT')
          router.push({ name: 'Login' })
        }  else if ( res.data.code === 404) {
        notifyWarning('회원 탈퇴 실패.😰')
        }
      },
    )
  }
};

const mutations = {
  SET_USER_ACCESS_INFO(state, payload) {
    state.userInfo.path = payload.path
  },
  UPDATE_USER_INFO(state, payload) {
    state.userInfo = {
      ...state.userInfo,
      ...payload
    };
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
};

export default {
  state,
  getters,
  actions,
  mutations,
};
