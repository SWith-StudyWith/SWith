// study 관련 api 요청
import { basicInstance } from './index.js';

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
    .get(`/studies/${studyId}/kanbans`)
    .then(success)
    .catch(fail)
}
function putKanban(payload, studyId, success, fail) {
  basicInstance
    .put(`api/studies/${studyId}/kanbans`, payload)
    .then(success)
    .catch(fail)
}

export {
  getStudyInfo,
  getStudyList,
  joinStudy,
  exitStudy,
  putKanban,
  checkKanban,
}
