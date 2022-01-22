<template>
<navbar/>
  <div class="container">
    <h1 class="form-title">회원가입</h1>
    <div class="row">
      <div class="offset-4 col-4">
        <input/>
        <form
          class="email-form row"
          :class="{
          valid: state.isValidEmail, invalid: !state.email=='' && !state.isValidEmail }"
          @submit.prevent
        >
          <label for="email" class="form-label">이메일</label>
          <div class="col-9">
            <input class="form-control" id="email" type="email"
              v-model="state.email"
              :disabled="state.isValidAuthNum"
              required
            >
          </div>
          <div class="col-3">
            <button class="btn btn-primary"
              style="width:100%"
              :disabled="!state.isValidEmail || state.isValidAuthNum "
              @click="onClickSendCode"
            >
              이메일 인증
            </button>
          </div>
          <div v-if="!state.isValidEmail && !state.email == '' "
          class="invalid-feedback">유효하지 않은 이메일입니다.</div>
        </form>
        <form
          class="authnum-form row"
          :class="{ valid: state.isValidAuthNum,
          invalid: !state.authNum=='' && !state.isValidAuthNum }"
          @submit.prevent
          v-if="!state.isValidAuthNum"
        >
          <label for="authNum" class="form-label">인증 번호</label>
          <div class="col-9">
            <input class="form-control" id="authNum" type="text" v-model="state.authNum" required>
          </div>
          <div class="col-3">
            <button
              class="btn btn-primary"
              style="width:100%"
              :disabled="!state.authNumBtnAble"
              @click="onClickConfirmAuthNum"
            >인증 확인</button>
          </div>
          <div class="invalid-feedback">인증 확인이 필요합니다.</div>
        </form>
        <form
          class="password-form"
          :class="{ valid: state.isValidPassword,
          invalid: !state.password=='' && !state.isValidPassword }"
          @submit.prevent
        >
          <label for="password" class="form-label">비밀번호</label>
          <input
            class="form-control"
            type="password"
            id="password"
            v-model="state.password"
            required
          >
          <div class="invalid-feedback">유효하지 않은 비밀번호입니다.</div>
        </form>
        <form
          class="passwordConfirm-form"
          :class="{ valid: state.isValidPasswordConfirm,
          invalid: !state.passwordConfirm =='' && !state.isValidPasswordConfirm }"
          @submit.prevent
        >
          <label for="passwordConfirm" class="form-label">비밀번호 확인</label>
          <input
            class="form-control"
            type="password"
            id="passwordConfirm"
            v-model="state.passwordConfirm"
            required
          >
          <div class="invalid-feedback">비밀번호와 일치하지 않습니다.</div>
          <div class="valid-feedback">비밀번호와 일치합니다.</div>
        </form>
        <form
          class="nickname-form"
          :class="{ valid: state.isValidNickname,
          invalid: !state.nickname =='' && !state.isValidNickname }"
          @submit.prevent
        >
          <label for="nickname" class="form-label">닉네임</label>
          <input class="form-control" type="text" id="nickname"
            v-model="state.nickname" required>
          <div class="invalid-feedback">유효하지 않은 닉네임입니다.</div>
        </form>
        <form
          class="terms-form"
          :class="{ valid: state.isChecked, invalid: !state.isChecked }"
          @submit.prevent
        >
          <label for="">이용 약관 <p style="color:green;">(필수)</p></label>
          <input type="checkbox" v-model="state.isChecked">
          <div class="invalid-feedback">SWith 이용약관과 개인정보 수집 및 이용에 대한 안내 모두 동의해주세요.</div>
        </form>
        <button class="btn btn-primary btn-lg col-12" @click="onClickSignup">회원가입</button>
        <div class="socialLogin mt-2">
          <button class="btn btn-primary col-6">카카오</button>
          <button class="btn btn-primary col-6">구글</button>
        </div>
        <div class="text-center">
          <span>이미 회원이신가요?</span>
          <router-link :to="{ name: 'Login' }">로그인</router-link>
        </div>
      </div>
    </div>
  </div>
  <sign-up-term></sign-up-term>
  <Footer/>
</template>
<script>
/* eslint-disable */
import axios from 'axios';
import { reactive, computed } from 'vue';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';
import SignUpTerm from './SignUpTerm.vue';
import Navbar from '../../common/Navbar.vue';
import Footer from '../../common/Footer.vue';

export default {
  name: '',
  components: {
    SignUpTerm,
    Navbar,
    Footer,
  },
  setup() {
    const store = useStore()
    const state = reactive({
      emailForm: null,
      authNumForm: null,
      passwordForm: null,
      passwordConfirmForm: null,
      nicknameForm: null,
      email: '',
      authNum: '',
      password: '',
      passwordConfirm: '',
      nickname: '',
      authNumBtnAble: false,
      isValidAuthNum: false,
      isValidEmail: computed(() => {
        if ( state.email && checkEmail(state.email)) {
          return true;
        } return false;
      }),
      isValidPassword: computed(() => {
        if (state.password && checkPassword(state.password)) {
          return true;
        } return false;
      }),
      isValidPasswordConfirm: computed(() => {
        if (state.passwordConfirm && state.password === state.passwordConfirm) {
          return true;
        } return false;
      }),
      isValidNickname: computed(() => {
        if (state.nickname && checkNickname(state.nickname)) {
          return true;
        } return false;
      }),
      isChecked: false,
    });

    async function api (url, method, data) {
      const options = {
        method,
        url,
        data,
        headers: {
          'content-type': 'application/json',
        }
      }
      return axios(options)
    }

    const onClickSendCode = function (e) {
      e.preventDefault();
      if (!state.isValidEmail) {
        return;
      }
      state.authNumBtnAble = true;
      this.api(`${process.env.VUE_APP_LOCAL_URI}/members/auth/email`, 'post', { email: state.email })
        .then((res) => console.log(res.data))
        .catch((err) => console.log(err));
    };

    const onClickConfirmAuthNum = function (e) {
      e.preventDefault();
      const payload = {
        email: state.email,
        authNum: state.authNum,
      };
      this.api(`${process.env.VUE_APP_LOCAL_URI}/members/auth/email/check`, 'post', payload)
        .then((res) => {
          console.log(res.data)
          if(res.data.isSuccess) {
            state.isValidAuthNum = true
          }
          console.log(res)
        })
        .catch((err) => console.log(err));
    };

    const router = useRouter();
    const onClickSignup = function (e) {
      e.preventDefault();
      if (!state.isValidAuthNum || !state.isValidEmail || !state.isValidPassword || !state.isValidPasswordConfirm || !state.isValidNickname || !state.isChecked){
        return;
      }
      const payload = {
        email: state.email,
        password: state.password,
      }

      this.api(`${process.env.VUE_APP_LOCAL_URI}/members`, 'post', payload)
        .then((res) => console.log(res))
        .catch((err) => console.log(err));
      // router.push({ name: 'Main' })
    };

    const checkEmail = function (email) {
      const re = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
      return re.test(email);
    };

    const checkPassword = function (password) {
      const numberChar = /[0-9]/;
      const specialChar = /[`~!@#$%^&*\\\'\";:\/?]/;
      const alphabetChar = /[a-zA-Z]/;
      if (password.length >= 8 && password.length <= 16) {
        if (numberChar.test(password) && specialChar.test(password) && alphabetChar.test(password)) {
          return true;
        }
      } return false;
    };
    const checkNickname = function (nickname) {
      if (nickname.length >= 2 && nickname.length <= 16) {
        return true;
      } return false;
    }
    return {
      state, api, onClickSendCode, onClickSignup, checkEmail, onClickConfirmAuthNum,
    };
  },
  created() {},
  mounted() {
    this.state.emailForm = document.querySelector('.email-form');
    this.state.authNumForm = document.querySelector('.authnum-form');
    this.state.passwordForm = document.querySelector('.password-form');
    this.state.passwordConfirmForm = document.querySelector('.passwordConfirm-form');
    this.state.nicknameForm = document.querySelector('.nickname-form');
  },
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
.btn {
  text-align: center;
  font-size: 0.7rem;
}
.invalid input {
  border: red solid 1px;
}
.valid input:focus {
  border: green solid 1px;
}
.invalid .invalid-feedback {
  display: block;
}
.valid .invalid-feedback {
  display: none;
}
.invalid .valid-feedback {
  display: none;
}
.valid .valid-feedback {
  display: block;
}
.terms:hover {
  cursor: pointer;
  font-weight: bold;
}
</style>
