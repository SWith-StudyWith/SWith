// study 관련 api 요청
import { studyInstance } from './index.js';

function getStudyInfo(payload, success, fail) {
  studyInstance
    .get(`/api/studies/${payload}`)
    .then(success)
    .catch(fail)
}

export {
  getStudyInfo,
}
