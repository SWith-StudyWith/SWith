import { getStudyInfo, getStudyList } from "../../api/study";

const state = () => ({
  studyInfo: {},
  studyList: [],
});

const getters = {};

const actions = {
  GET_STUDY_INFO({ commit }, payload) {
    getStudyInfo(
      payload,
      (res) => {
        console.log(res.data)
        if (res.data.code === 200) {
          commit('SET_STUDY_INFO', res.data.data)
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
  }
};

const mutations = {
  SET_STUDY_INFO(state, payload) {
    state.studyInfo = {
      ...state.studyInfo,
      ...payload
    };
  },
  SET_STUDY_LIST(state, payload) {
    state.studyList = payload;
  }
};

export default {
  state,
  getters,
  actions,
  mutations,
};
