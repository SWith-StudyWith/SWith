import { basicInstance } from './index.js';

// 회원가입
function setMemoList(studyId, memoList, success, fail) {
  basicInstance
    .put(`/api/studies/${studyId}/memos`, memoList)
    .then(success)
    .catch(fail)
}

function getMemoList(studyId, success, fail) {
  basicInstance
    .get(`/api/studies/${studyId}/memos`)
    .then(success)
    .catch(fail)
}

export {
  setMemoList,
  getMemoList
}
