import { getStudyInfo } from "../../api/study";

const state = () => ({
  studyInfo: {},
});

const getters = {};

const actions = {
  GET_STUDY_INFO({ commit }, payload) {
    getStudyInfo(
      payload,
      (res) => {
        console.log(res.data)
        commit('SET_STUDY_INFO', res.data.data)
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
  }
};

export default {
  state,
  getters,
  actions,
  mutations,
};
