<template>
  <navbar/>
  <div class="container">
    <h1 class="form-title">회원가입</h1>
    <div class="row">
      <div class="offset-4 col-4">

        <!-- Email -->
        <form class="row" @submit.prevent>
          <label for="email" class="form-label">이메일</label>
          <div class="col-8">
            <input class="form-control" id="email" type="email"
              v-model="state.email"
              :disabled="state.isAuthNumChecked"
              required
              placeholder="가입하실 이메일을 입력해주세요."
            >
          </div>
          <div class="col-4">
            <button class="btn btn-primary"
              style="width:100%"
              :disabled="!state.isValidEmail || state.isAuthNumChecked"
              @click="onClickSendCode"
            >이메일 인증
            </button>
          </div>
          <div :style="{ visibility: (state.isValidEmail || !state.wasInputed.email)? 'hidden' : 'visible' }"
          class="invalid-feedback">유효하지 않은 이메일입니다.</div>
        </form>

        <!-- Certification Number -->
        <form class="row"
          :style="{ visibility: state.isAuthNumChecked ? 'hidden': 'visible' }"
          @submit.prevent
          v-if="!state.isAuthNumChecked"
        >
          <label for="authNum" class="form-label">인증 번호</label>
          <div class="col-8">
            <input class="form-control" id="authNum" type="text"
              v-model="state.authNum" :disabled="state.isDisableAuthNum" required
              placeholder=""
            >
          </div>
          <div class="col-4">
            <button class="btn btn-primary" style="width:100%"
              :disabled="state.isDisableAuthNum"
              @click="onClickConfirmAuthNum"
            >인증 확인</button>
          </div>
          <div :style="{ visibility: (state.isAuthNumChecked || !state.wasInputed.authNum)? 'hidden' : 'visible' }"
            class="invalid-feedback"
          >
            인증 확인이 필요합니다.
          </div>
        </form>

        <!-- Password -->
        <form @submit.prevent>
          <label for="password" class="form-label">비밀번호</label>
          <input
            class="form-control"
            type="password"
            id="password"
            v-model="state.password"
            required
            placeholder="비밀번호를 입력해주세요."
          >
          <div
            :style="{ visibility: (state.isValidPassword || !state.wasInputed.password)? 'hidden' : 'visible' }"
            class="invalid-feedback"
          >
            유효하지 않은 비밀번호입니다.
          </div>
        </form>

        <!-- Password Confirm -->
        <form @submit.prevent>
          <label for="passwordConfirm" class="form-label">비밀번호 확인</label>
          <input
            class="form-control"
            type="password"
            id="passwordConfirm"
            v-model="state.passwordConfirm"
            required
            placeholder="비밀번호를 확인해주세요."
          >
          <div
            v-if="!state.isValidPasswordConfirm && state.wasInputed.passwordConfirm"
            class="invalid-feedback"
          >
            비밀번호와 일치하지 않습니다.
          </div>
          <div v-else-if="(state.passwordConfirm !== '') && state.isValidPasswordConfirm" class="valid-feedback">비밀번호와 일치합니다.</div>
          <div v-else style="height: 1.7rem;"></div>
        </form>

        <!-- Nickname -->
        <form @submit.prevent>
          <label for="nickname" class="form-label">닉네임</label>
          <input class="form-control" type="text" id="nickname" placeholder="사용할 닉네임을 입력해주세요."
            v-model="state.nickname" required>
          <div
            :style="{ visibility: (state.isValidNickname || !state.wasInputed.nickname)? 'hidden' : 'visible' }"
            class="invalid-feedback"
          >
            닉네임을 입력해주세요.
          </div>
        </form>

        <!-- Terms of Use //  !state.ischecked=='' && -->
        <form
          :class="{ valid: state.isChecked, invalid:
          !state.isChecked }"
          @submit.prevent
        >
          <input class="form-check-input mb-2 me-1" type="checkbox" v-model="state.isChecked" id="checkTerm">
          <label style="color:green;" for="checkTerm"> (필수)</label>
          <span> SWith
          <span class="text-decoration-underline terms" data-bs-toggle="modal"
                data-bs-target="#signupTermModal">
                이용 약관 및 개인정보 취급방침
          </span>
              에 대한 내용을 모두 확인하였으며, 이에 동의합니다.</span>
          <div :style="{visibility: (!state.isChecked && state.wasInputed.check) ? 'visible' : 'hidden' }" class="invalid-feedback">SWith 이용약관과 개인정보 수집 및 이용에 모두 동의해주세요.</div>
        </form>

        <!-- SignUp Btn -->
        <button class="btn btn-primary btn-lg col-12" @click="onClickSignup" style="margin-bottom:15px;">회원가입</button>
        <div class="socialLogin mt-2">
            <KakaoLoginBtn/>
            <!-- <a :href="GOOGLE_GET_AUTH_CODE_URL"><img src="@/assets/img/icon_oauth_google/btn_google_signin_dark_normal_web.png" alt="google login button"></a> -->
            <GoogleLoginBtn />
        </div>
        <div class="text-center">
          <span class="text-muted">이미 회원이신가요? </span>
          <router-link class="ps-3" :to="{ name: 'Login' }">로그인</router-link>
        </div>
      </div>
    </div>
  </div>
  <SignUpTerm/>

  <Footer/>
</template>
<script>
/* eslint-disable */
import { sendEmail, checkEmail, signup } from '@/api/user';
import { reactive, computed } from 'vue';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';
import SignUpTerm from './components/SignUpTerm.vue';
import Navbar from '../common/Navbar.vue';
import Footer from '../common/Footer.vue';
import KakaoLoginBtn from '@/views/members/components/KakaoLoginBtn.vue';
// import { GOOGLE_GET_AUTH_CODE_URL } from '@/api/gauth.js';
import notifications from '@/composables/notifications'
import GoogleLoginBtn from './components/GoogleLoginBtn.vue';

export default {

  components: {
    SignUpTerm,
    Navbar,
    Footer,
    // Test,
    KakaoLoginBtn,
    GoogleLoginBtn,
  },
  methods: {
    GoogleLoginBtn() {
      const self = this;

      window.gapi.signin2.render('my-signin2', {
        scope: 'profile email',
        width: 240,
        height: 50,
        longtitle: true,
        theme: 'dark',
        onsuccess: this.GoogleLoginSuccess,
        onfailure: this.GoogleLoginFailure,
      });

      setTimeout(() => {
        if (!self.googleLoginCheck) {
          const auth = window.gapi.auth2.getAuthInstance();
          auth.isSignedIn.get();
          document.querySelector('.abcRioButton').click();
        }
      }, 1500);
    },
    async GoogleLoginSuccess(googleUser) {
      const googleEmail = googleUser.getBasicProfile().getEmail();
      if (googleEmail !== 'undefined') {
        console.log(googleEmail);
      }
    },
    // 구글 로그인 콜백함수 (실패)
    GoogleLoginFailure(error) {
      console.log(error);
    },
  },
  setup() {
    const store = useStore()
    const state = reactive({
      email: '',
      authNum: '',
      password: '',
      passwordConfirm: '',
      nickname: '',
      isDisableAuthNum: true,
      isAuthNumChecked: false,
      isChecked: false,
      wasInputed: {
        email: false,
        authNum: false,
        password: false,
        passwordConfirm: false,
        nickname: false,
        check: false,
      },
      isValidEmail: computed(() => {
        if (state.email !== '') {
          state.wasInputed.email = true;
        }
        if (state.email && validateEmail(state.email)) {
          return true;
        }
        return false;
      }),
      isValidPassword: computed(() => {
        if (state.password !== '') {
          state.wasInputed.password = true;
        }
        if (state.password && validatePassword(state.password)) {
          return true;
        }
        return false;
      }),
      isValidPasswordConfirm: computed(() => {
        if (state.passwordConfirm !== '') {
          state.wasInputed.passwordConfirm = true;
        }
        if (state.passwordConfirm && (state.password === state.passwordConfirm)) {
          return true;
        }
        return false;
      }),
      isValidNickname: computed(() => {
        if (state.nickname !== '') {
          state.wasInputed.nickname = true;
        }
        if (state.nickname && validateNickname(state.nickname)) {
          return true;
        }
        return false;
      }),
    });
    const { notifyWarning, notifySuccess, notifyDanger } = notifications();

    const onClickSendCode = function (e) {
      e.preventDefault();
      if (!state.isValidEmail) {
        return;
      }
      sendEmail(
        { email: state.email },
        (res) => {
          console.log(res.data)
          switch (res.data.code) {
            case 400:
              notifyDanger('이미 가입된 이메일입니다.😳')
              break;
            case 200:
              notifySuccess('인증번호가 담긴 이메일을 전송했습니다.💌')
              state.isDisableAuthNum = false;
              break;
          }
        },
        (err) => {
          console.log(err)
          notifyWarning('서버가 아파요.😥');
        }
      )
    }
    const onClickConfirmAuthNum = function (e) {
      e.preventDefault();
      checkEmail(
        { email: state.email, authNum: state.authNum },
        (res) => {
          console.log(res.data)
          switch (res.data.code) {
            case 200:
              state.isAuthNumChecked = true;
              notifySuccess('이메일 인증 성공!🎉');
              break;
            case 409:
              state.isAuthNumChecked = false;
              notifyDanger('인증번호가 맞지 않습니다.🙄');
              break;
          }
        },
        (err) => {
          console.log(err)
          notifyWarning('서버가 아파요.😥');
        }
      )
    };

    const router = useRouter();
    const onClickSignup = function (e) {
      e.preventDefault();
      if (state.email === '' || state.password === '' || state.passwordConfirm === '' || state.nickname === '' || state.authNum === '') {
        state.wasInputed.email = true;
        state.wasInputed.password = true;
        state.wasInputed.passwordConfirm = true;
        state.wasInputed.authNum = true;
        state.wasInputed.nickname = true;
        state.wasInputed.check = true;
        return;
      }
      if (!state.isAuthNumChecked || !state.isValidEmail || !state.isValidPassword || !state.isValidPasswordConfirm || !state.isValidNickname || !state.isChecked){
        return;
      }
      signup(
        { email: state.email, password: state.password, nickname: state.nickname },
        (res) => {
          console.log(res.data)
          switch (res.data.code) {
            case 200:
              notifySuccess('회원가입 성공!🎉');
              break;
            case 400:
              notifyDanger('이미 존재하는 회원입니다.😳')
              break;
            case 404:
              notifyWarning('회원가입 실패.😰')
              break;
          }
        },
        (err) => {
          console.log(err)
          notifyWarning('서버가 아파요.😥');
        }
      )
      router.push({ name: 'Login' })
    };

    const validateEmail = function (email) {
      const re = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
      return re.test(email);
    };

    const validatePassword = function (password) {
      const numberChar = /[0-9]/;
      const specialChar = /[`~!@#$%^&*\\\'\";:\/?]/;
      const alphabetChar = /[a-zA-Z]/;
      if (password.length >= 8 && password.length <= 16) {
        if (numberChar.test(password) && specialChar.test(password) && alphabetChar.test(password)) {
          return true;
        }
      } return false;
    };
    const validateNickname = function (nickname) {
      if (nickname.length >= 2 && nickname.length <= 16) {
        return true;
      } return false;
    };
    return {
      state, onClickSendCode, onClickSignup, onClickConfirmAuthNum, //GOOGLE_GET_AUTH_CODE_URL,
      notifyWarning, notifyDanger, notifySuccess
    };
  },
  created() {},
  mounted() {},
  unmounted() {},
  methods: {
  },
};
</script>
<style scoped>
* {
  text-align: left;
}
h1 {
  text-align: center;
}
label {
  margin-bottom: 0;
}

/* 레이아웃 기준 */

button{
  font-size: 14px;
  text-align: center;
}
.form-label{
  margin-bottom: 3px;
}
.form-title {
  font-size: 24px;
  text-align: center;
  margin-bottom: 30px;
}
.container {
  margin-top: 100px;
  /* margin-bottom: 180px; */
}
input::placeholder {
  font-size: 12px;
  padding: auto;
  vertical-align: middle;

}
input{
  background-color: #F4F5F4;
  vertical-align: middle;
}

/* ! */

.invalid-feedback {
  display: block;
  font-size: 0.75rem;
  margin-top: 0;
  margin-bottom: 0.5rem;
}
.valid-feedback {
  display: block;
  font-size: 0.75rem;
  margin-top: 0;
  margin-bottom: 0.5rem;
  color: green;
}

.terms:hover {
  cursor: pointer;
  font-weight: bold;
}
.term-msg {
  font-size: 0.8rem;
  color: red;
}
.socialLogin {
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
  margin-bottom: 20px;
}
a {
  font-weight: 500;
  text-decoration: none;
}
</style>
