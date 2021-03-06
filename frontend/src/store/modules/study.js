import { getStudyInfo, getStudyList, getMemberList, getChatList, updateStudy, getFileList } from "../../api/study";
import router from '@/router';
import notifications from '@/composables/notifications'
import { basicInstance } from '@/api/index.js';

const { notifyDanger } = notifications();

const state = () => ({
  studyInfo: {},
  studyList: [],
  memberList: [],
  fileList: [],
  chatList: [],
  fileUploadPercent: 100,
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
      },
      () => {
      }
    )
  },
  GET_FILE_LIST({ commit }, studyId) {
    // return new Promise(async (resolve, reject) => {
    //   try {

    //   }
    // })
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
  UPLOAD_FILE({ commit }, { studyId, uploadFileData, success, fail }) {
    basicInstance
    .post(`api/studies/${studyId}/files`, uploadFileData, {
      onUploadProgress: (progressEvent) => {
        let percentage = (progressEvent.loaded * 100) / progressEvent.total;
        let percentCompleted = Math.round(percentage);
        commit('SET_FILE_UPLOAD_PERCENT', percentCompleted);
      }
    })
    .then(success)
    .catch(fail)
  },
  DOWNLOAD_FILE({ commit }, { studyId, fileId, success, fail }) {
    basicInstance
    .get(`api/studies/${studyId}/files/${fileId}`, {
      onDownloadProgress: (progressEvent) => {
        let percentage = (progressEvent.loaded * 100) / progressEvent.total;
        let percentCompleted = Math.round(percentage);
        commit('SET_FILE_UPLOAD_PERCENT', percentCompleted);
      },
      responseType: 'blob',
    })
    .then(success)
    .catch(fail)
  }
  // uploadMultiFile({ commit }, params) {
  //   return new Promise( async (resolve, reject) => {
  //     try {
  //       const rs = await this.$axios.post('/api/v1/file/file_multi_upload', params);
  //       resolve(true);
  //     } catch (err) {
  //       console.error(err);
  //       reject(err);
  //     }
  //   })
  // },
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
  },
  SET_CHAT_LIST(state, payload) {
    state.chatList = payload;
  },
  UPDATE_STUDY_INFO(state, payload) {
    console.log('update_study_info')
    state.studyInfo = {
      ...state.studyInfo,
      ...payload
    };
  },
  SET_FILE_LIST(state, payload) {
    state.fileList = payload;
  },
  SET_FILE_UPLOAD_PERCENT(state, payload) {
    state.fileUploadPercent = payload;
  },
  SET_FILE_UPLOAD_PERCENT_INIT(state) {
    state.fileUploadPercent = 0;
  }
};

export default {
  state,
  getters,
  actions,
  mutations,
};
