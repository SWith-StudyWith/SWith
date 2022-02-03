import { getStudyInfo, getStudyList, getMemberList } from "../../api/study";

const state = () => ({
  studyInfo: {},
  studyList: [],
  memberList: [],
});

const getters = {
  getStudyInfo: function (state) {
    return state.studyInfo
  }
};

const actions = {
  GET_STUDY_INFO({ commit }, payload) {
    getStudyInfo(
      payload,
      (res) => {
        console.log(res.data)
        if (res.data.code === 200) {
          commit('SET_STUDY_INFO', res.data.data)
        } else if (res.data.code === 401) {
          alert('접근 권한이 없습니다.')
          history.back()
        }
      },
      (err) => {
        console.log(err)
      }
    )
  },
  GET_STUDY_LIST({ commit }) {
    getStudyList(
      (res) => {
        if (res.data.code === 200) {
          commit('SET_STUDY_LIST', res.data.data)
        }
      },
      (err) => {
        console.log(err)
      }
    )
  },
  GET_MEMBER_LIST({ commit } ,studyId) {
    getMemberList(
      studyId,
      (res) => {
        console.log(res.data)
        if (res.data.code === 200) {
          commit('SET_MEMBER_LIST', res.data.data)
        }
      },
      (err) => {
        console.log(err)
      }
    )
  }
};

const mutations = {
  SET_STUDY_INFO(state, payload) {
    state.studyInfo = payload;
  },
  SET_STUDY_LIST(state, payload) {
    state.studyList = payload;
  },
  SET_MEMBER_LIST(state, payload) {
    state.memberList = payload;
  }
};

export default {
  state,
  getters,
  actions,
  mutations,
};
