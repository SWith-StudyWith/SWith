// study 관련 api 요청
import { basicInstance } from './index.js';

function getStudyInfo(payload, success, fail) {
  basicInstance
    .get(`/api/studies/${payload}`)
    .then(success)
    .catch(fail)
}

export {
  getStudyInfo,
}
