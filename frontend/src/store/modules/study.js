import { getStudyInfo, getStudyList, getMemberList, getChatList, updateStudy, getFileList } from "../../api/study";
import router from '@/router';
import notifications from '@/composables/notifications'

const { notifyDanger } = notifications();

const state = () => ({
  studyInfo: {},
  studyList: [],
  memberList: [],
  fileList: [],
  chatList: [],
});

const getters = {
};

const actions = {
  GET_STUDY_INFO({ commit }, payload) {
    getStudyInfo(
      payload,
      (res) => {
        if (res.data.code === 200) {
          commit('SET_STUDY_INFO', res.data.data)
        } else if (res.data.code === 401) {
          notifyDanger('접근 권한이 없습니다.⛔')
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
  GET_MEMBER_LIST({ commit }, studyId) {
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
  GET_CHAT_LIST({ commit }, { studyId, index }) {
    getChatList(
      studyId,
      index,
      (res) => {
        if (res.data.code === 200) {
          commit('SET_CHAT_LIST', res.data.data)
        }
      },
      (err) => {
        console.log('채팅 불러오기 실패')
        console.log(err)
      }
    )
  },
  updateStudyInfo({ commit }, {studyId, payload}) {
    updateStudy(
      studyId,
      payload,
      (res) => {
        commit('UPDATE_STUDY_INFO', res.data.data);
        router.push({ name: 'Main' })
      },
      () => {
      }
    )
  },
  GET_FILE_LIST({ commit }, studyId) {
    getFileList(
      studyId,
      (res) => {
        if (res.data.code === 200) {
          commit('SET_FILE_LIST', res.data.data)
        }
      },
      (err) => {
        console.log(err)
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
  SET_CHAT_LIST(state, payload) {
    state.chatList = payload;
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
  },
  SET_FILE_LIST(state, payload) {
    state.fileList = payload;
  },
};

export default {
  state,
  getters,
  actions,
  mutations,
};
