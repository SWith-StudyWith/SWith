// user 관련 api 요청
import { basicInstance } from './index.js';

// 회원가입
function signup(payload, success, fail) {
  basicInstance
    .post(`/api/members`, payload)
    .then(success)
    .catch(fail)
}
// 이메일 인증 번호 전송
function sendEmail(payload, success, fail) {
  basicInstance
    .post(`/api/members/auth/email`, payload)
    .then(success)
    .catch(fail)
}
// 이메일 인증 확인
function checkEmail(payload, success, fail) {
  basicInstance
    .post(`/api/members/auth/email/check`, payload)
    .then(success, fail)
    .catch(fail)
}
// 로그인
function login(payload, success, fail) {
  basicInstance
    .post(`/api/members/login`, payload)
    .then(success)
    .catch(fail)
}
// 임시 비밀번호 전송
function sendTemporaryPassword(payload, success, fail) {
  basicInstance
    .post(`/api/members/auth/email/password`, payload)
    .then(success)
    .catch(fail)
}
// 사용자 정보 확인
function getUserInfo(success, fail) {
  basicInstance
    .get(`/api/members`)
    .then(success)
    .catch(fail)
}
function loginKakao(payload, success, fail) {
  basicInstance
    .post(
      `/api/members/login/kakao`,
      {},
      {
        params: { code: payload }
      }
    )
    .then(success)
    .catch(fail)
}
function loginGoogle(payload, success, fail) {
  basicInstance
    .post(
      `/api/members/login/google`,
      {},
      {
        params: { code: payload }
      }
    )
    .then(success)
    .catch(fail)
}

// 마이페이지 수정
function updateUserInfoAPI(payload, success, fail) {
  basicInstance
    .put(`/api/members`, payload)
    .then(success)
    .catch(fail)
}

// 비밀번호 수정
function updatePassword(payload, success, fail) {
  basicInstance
    .patch(`/api/members/password`, payload)
    .then(success)
    .catch(fail)
}
// 비밀번호 확인
function confirmpassword(payload, success, fail) {
  basicInstance
    .post(`/api/members/password`, payload)
    .then(success)
    .catch(fail)
}

export {
  signup,
  sendEmail,
  checkEmail,
  login,
  getUserInfo,
  loginKakao,
  updateUserInfoAPI,
  loginGoogle,
  updatePassword,
  confirmpassword,
  sendTemporaryPassword,
}
