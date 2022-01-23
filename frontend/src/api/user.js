// user 관련 api 요청
import { userInstance } from './index.js';

// 회원가입
function signup(payload, success, fail) {
  userInstance
    .post(`/api/members`, payload)
    .then(success)
    .catch(fail)
}
// 이메일 인증 번호 전송
function sendEmail(payload, success, fail) {
  userInstance
    .post(`/api/members/auth/email`, payload)
    .then(success)
    .catch(fail)
}
// 이메일 인증 확인
function checkEmail(payload, success, fail) {
  userInstance
    .post(`/api/members/auth/email/check`, payload)
    .then(success, fail)
    .catch(fail)
}
// 로그인
function login(payload, success, fail) {
  userInstance
    .post(`/api/members/login`, payload)
    .then(success)
    .catch(fail)
}
// 사용자 정보 확인
function getUserInfo(success, fail) {
  userInstance
    .get(`/api/members`)
    .then(success)
    .catch(fail)
}
// 마이페이지 수정
function updateUserInfo(payload, success, fail) {
  userInstance
    .put(`/api/members`, payload)
    .then(success)
    .catch(fail)
}
export {
  signup,
  sendEmail,
  checkEmail,
  login,
  getUserInfo,
  updateUserInfo,
}
