import { getStudyInfo, getStudyList, getMemberList, updateStudy } from "../../api/study";
import router from '@/router';

const state = () => ({
  studyInfo: {},
  studyList: [],
  memberList: [],
});

const getters = {
  // getStudyInfo: function (state) {
  //   return state.studyInfo
  // }
};

const actions = {
  GET_STUDY_INFO({ commit }, payload) {
    // console.log(payload) // ID만 받아옴
    // console.log("getStudyInfo들가기전~~")
    getStudyInfo(
      payload,
      (res) => {
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
          console.log(res)
          console.log('스터디리스트 조회~~')
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
        if (res.data.code === 200) {
          commit('SET_MEMBER_LIST', res.data.data)
        }
      },
      (err) => {
        console.log(err)
      }
    )
  },
  updateStudyInfo({ commit }, {studyId, payload}) {
    updateStudy(
      studyId,
      payload,
      (res) => {
        console.log(res.data.data)
        console.log('스터디 업데이트~~')
        commit('UPDATE_STUDY_INFO', res.data.data);
        router.push({ name: 'Main' })
      },
      () => {
        alert('서버가 아파요.')
      }
    )
  },
};

const mutations = {
  SET_STUDY_INFO(state, payload) {
    state.studyInfo = {
      ...state.studyInfo,
      ...payload
    }
  },
  SET_STUDY_LIST(state, payload) {
    state.studyList = payload;
  },
  SET_MEMBER_LIST(state, payload) {
    state.memberList = payload;
  },
  UPDATE_STUDY_INFO(state, payload) {
    console.log(payload)
    console.log('update_study_info')
    state.studyInfo = {
      ...state.studyInfo,
      ...payload
      };
    console.log(state.studyInfo)
    console.log('state.studyInfo')
  }
};

export default {
  state,
  getters,
  actions,
  mutations,
};
