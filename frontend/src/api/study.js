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
  console.log(payload)
  basicInstance
    .post(`api/studies/join`, payload)
    .then(success)
    .catch(fail)
}

export {
  getStudyInfo,
  getStudyList,
  joinStudy,
}
