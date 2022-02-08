import { createToast } from "mosha-vue-toastify";
import "mosha-vue-toastify/dist/style.css";


// success
//'스터디룸 생성 완료!🔨'
// '비밀번호 수정 성공!🤗'
const notifySuccess = (title) => {
  createToast(title,
    {
      showIcon: 'true',
      position: 'bottom-left',
      type: 'success',
      transition: 'bounce',
      // toastBackgroundColor: "#334666",
    })
  };
const notifySuccessRight = (title) => {
  createToast(title,
    {
      showIcon: 'true',
      position: 'bottom-left',
      type: 'success',
      transition: 'bounce',
      // toastBackgroundColor: "#334666",
    })
  };
const notifySuccessDescription = (title, description) => {
  createToast({
    title,
    description,
  },
  {
    showIcon: 'true',
    position: 'bottom-left',
    type: 'success',
    transition: 'bounce',
    // toastBackgroundColor: "#334666",
  })
};

// warning : 노란색
// '서버가 아파요 😰'
// '접근 권한이 없습니다⛔'
// '회원가입 실패.😰'
// '회원 인증 실패 😰'
const notifications = () => {
  const notifyWarning = (title) => {
    createToast(title,
      {
      showIcon: 'true',
      position: 'bottom-left',
      type: 'warning',
      transition: 'bounce',
      timeout: 2000,
      hideProgressBar: 'true',
      })
  };

// danger 빨간색
// 400
// '이미 가입된 이메일입니다.😳'
// '이미 존재하는 회원입니다.😳'
// '인증번호가 맞지 않습니다.🙄'
  const notifyDanger = (title) => {
    createToast(title,
      {
      position: 'bottom-left',
      type: 'danger',
      transition: 'bounce',
      timeout: 3000,
      hideProgressBar: 'true',
      showIcon: 'true',
      })
  };

// danger with descriptsion
// '파일 사이즈가 너무 큽니다.😯', '최대 2MB'
  const notifyDangerDescription = (title, description) => {
    createToast({
      title,
      description,
      },
      {
      position: 'bottom-left',
      type: 'danger',
      transition: 'bounce',
      timeout: 3000,
      hideProgressBar: 'true',
      showIcon: 'true',
      })
  };

  return {
    notifySuccess,
    notifySuccessRight,
    notifySuccessDescription,
    notifyWarning,
    notifyDanger,
    notifyDangerDescription,
  };
};

export default notifications;

