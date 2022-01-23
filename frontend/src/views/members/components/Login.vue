<template>
  <Navbar />
  <div class="container">
    <h1 class="form-title">로그인</h1>
    <div class="row">
      <div class="offset-4 col-4">
        <form
          class="email-form row"
          :class="{ valid: state.isValidEmail, invalid: !state.isValidEmail }"
          @submit.prevent
        >
          <label for="email" class="form-label">이메일</label>
          <div>
            <input class="form-control" id="email" type="email"
              v-model="state.email" required autofocus>
          </div>
          <div class="invalid-feedback">이메일을 입력해주세요.</div>
        </form>
        <form
          class="password-form"
          :class="{ valid: state.isValidPassword, invalid: !state.isValidPassword }"
          @submit.prevent
        >
          <label for="password" class="form-label">비밀번호</label>
          <input class="form-control" type="password"
            id="password" v-model="state.password" required>
          <div class="invalid-feedback">유효하지 않은 비밀번호입니다.</div>
        </form>
        <button class="btn btn-primary btn-lg col-12" @click="onClickLogin">로그인</button>
        <div class="socialLogin mt-2">
          <button class="btn btn-primary col-6">카카오</button>
          <button class="btn btn-primary col-6">구글</button>
        </div>
        <div class="container2 text-center">
          <router-link :to="{ name: 'FindPassword' }">비밀번호 찾기</router-link>
          <span> </span>
          <router-link :to="{ name: 'SignUp' }">회원가입</router-link>
        </div>
      </div>
    </div>
  </div>
  <Footer />
</template>
<script>
/* eslint-disable */
import { reactive, computed } from 'vue';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';
import Navbar from '../../common/Navbar.vue';
import Footer from '../../common/Footer.vue';

export default {
  name: '',
  components: {
    Navbar,
    Footer,
  },
  setup() {
    const store = useStore();
    const state = reactive({
      emailForm: null,
      passwordForm: null,
      email: '',
      password: '',
      isValidEmail: computed(() => {
        if (state.email && validEmail(state.email)) {
          return true;
        } return false;
      }),
      isValidPassword: computed(() => {
        if (state.password && validPassword(state.password)) {
          return true;
        } return false;
      }),
    });

    const router = useRouter();
    const onClickLogin = function (e) {
      e.preventDefault();
      if (!state.isValidEmail || !state.isValidPassword ){
        return;
      }
      store.dispatch('LOGIN', { email: state.email, password: state.password })
      // router.push({ name: 'Main' })
    };

    const validEmail = function (email) {
      const re = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
      return re.test(email);
    };

    const validPassword = function (password) {
      const numberChar = /[0-9]/;
      const specialChar = /[`~!@#$%^&*\\\'\";:\/?]/;
      const alphabetChar = /[a-zA-Z]/;
      if (password.length >= 8 && password.length <= 16) {
        if (numberChar.test(password) && specialChar.test(password) && alphabetChar.test(password)) {
          return true;
        }
      } return false;
    };

    return {
      state, onClickLogin,
    };
  },
  created() {},
  mounted() {
    this.state.emailForm = document.querySelector('.email-form');
    this.state.passwordForm = document.querySelector('.password-form');
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
.container2 {
  display: flex;
  justify-content: space-evenly;
}
</style>
