// study 관련 api 요청
import { basicInstance } from './index.js';

function createStudy(payload, success, fail) {
  basicInstance
    .post(`/api/studies`, payload)
    .then(success)
    .catch(fail)
}
function updateStudy(studyId, payload, success, fail) {
  basicInstance
    .put(`api/studies/${studyId}`, payload)
    .then(success)
    .catch(fail)
}
function getStudyInfo(payload, success, fail) {
  basicInstance
    .get(`/api/studies/${payload}`)
    .then(success)
    .catch(fail)
}
function getStudyList(success, fail) {
  basicInstance
    .get(`api/studies`)
    .then(success)
    .catch(fail)
}
function joinStudy(payload, success, fail) {
  basicInstance
    .post(`api/studies/join`, payload)
    .then(success)
    .catch(fail)
}
function exitStudy(payload, success, fail) {
  basicInstance
    .delete(`api/studies/${payload}`)
    .then(success)
    .catch(fail)
}
function checkKanban(studyId, success, fail) {
  basicInstance
    .get(`api/studies/${studyId}/kanbans`)
    .then(success)
    .catch(fail)
}
function putKanban(payload, studyId, success, fail) {
  basicInstance
    .put(`api/studies/${studyId}/kanbans`, payload)
    .then(success)
    .catch(fail)
}
function getMemberList(studyId, success, fail) {
  basicInstance
    .get(`api/studies/${studyId}/members`)
    .then(success)
    .catch(fail)
}
function uploadFile(studyId, payload, success, fail) {
  basicInstance
    .post(`api/studies/${studyId}/files`, payload)
    .then(success)
    .catch(fail)
}
function getFileList(studyId, success, fail) {
  basicInstance
    .get(`api/studies/${studyId}/files`)
    .then(success)
    .catch(fail)
}
function downloadFile(studyId, fileId, success, fail) {
  basicInstance
    .get(`api/studies/${studyId}/files/${fileId}`, { responseType: 'blob' })
    .then(success)
    .catch(fail)
}

export {
  createStudy,
  updateStudy,
  getStudyInfo,
  getStudyList,
  joinStudy,
  exitStudy,
  putKanban,
  checkKanban,
  getMemberList,
  uploadFile,
  getFileList,
  downloadFile
}
