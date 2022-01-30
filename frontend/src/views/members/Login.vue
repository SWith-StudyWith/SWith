<template>
  <Navbar />
  <div class="container">
    <h1 class="form-title">로그인</h1>
    <div class="row">
      <div class="offset-4 col-4">
        <form class="row" @submit.prevent>
          <label for="email" class="form-label">이메일</label>
          <div>
            <input class="form-control" id="email" type="email"
              v-model="state.email" required autofocus>
          </div>
          <div
            :style="{ visibility: (state.isValidEmail || !state.wasEmailInputed) ? 'hidden': 'visible' }"
            class="invalid-feedback mb-2 mt-0">
            이메일을 입력해주세요.
          </div>
        </form>
        <form @submit.prevent>
          <label for="password" class="form-label">비밀번호</label>
          <input class="form-control" type="password"
            id="password" v-model="state.password" required>
          <div
            :style="{visibility: (state.isValidPassword || !state.wasPasswordInputed) ? 'hidden': 'visible' }"
            class="invalid-feedback mb-2 mt-0">
            비밀번호를 입력해주세요.
          </div>
        </form>
        <button class="btn btn-primary btn-lg col-12" @click="onClickLogin" style="margin-bottom:15px; margin-top: 10px;">로그인</button>
        <div class="socialLogin mt-2">
          <KakaoLoginBtn/>
          <a :href="GOOGLE_GET_AUTH_CODE_URL"><img src="@/assets/img/icon_oauth_google/btn_google_signin_dark_normal_web.png" alt="google login button"></a>
        </div>
        <div class="text-center mt-5">
          <router-link class="text-muted px-3" :to="{ name: 'FindPassword' }">비밀번호 찾기</router-link>
          <span> </span>
          <router-link class="text-muted px-3" :to="{ name: 'SignUp' }">회원가입</router-link>
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
import Navbar from '../common/Navbar.vue';
import Footer from '../common/Footer.vue';
import KakaoLoginBtn from '@/views/members/components/KakaoLoginBtn.vue'
import { GOOGLE_GET_AUTH_CODE_URL } from '@/api/gauth.js';

export default {
  name: '',
  components: {
    Navbar,
    Footer,
    KakaoLoginBtn,
  },
  setup() {
    const store = useStore();
    const state = reactive({
      googleUser: null,
      email: '',
      password: '',
      wasEmailInputed: false,
      wasPasswordInputed: false,
      isValidEmail: computed(() => {
        if (state.email !== '') {
          state.wasEmailInputed = true;
        }
        if (state.email && validEmail(state.email)) {
          return true;
        }
        return false;
      }),
      isValidPassword: computed(() => {
        if (state.password !== '') {
          state.wasPasswordInputed = true;
        }
        if (state.password) {
          return true;
        }
        return false;
      }),
    });

    const router = useRouter();
    const onClickLogin = function (e) {
      e.preventDefault();
      if (state.email === '' || state.password === '') {
        state.wasEmailInputed = true;
        state.wasPasswordInputed = true;
        return
      }
      if (!state.isValidEmail || !state.isValidPassword ){
        return;
      }
      store.dispatch('LOGIN', { email: state.email, password: state.password })
    };

    const validEmail = function (email) {
      const re = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
      return re.test(email);
    };

  const onSuccess = (googleUser) => {
      // eslint-disable-next-line
      console.log(googleUser);
      state.googleUser = googleUser.getBasicProfile();
  };
  const onFailure = (error) => {
      // eslint-disable-next-line
      console.log(error);
  };
  const signout = () => {
    const authInst = window.gapi.auth2.getAuthInstance();
    authInst.signOut().then(() => {
      // eslint-disable-next-line
      console.log('User Signed Out!!!');
    });
  };

    return {
      state, onClickLogin, onSuccess, onFailure, signout, GOOGLE_GET_AUTH_CODE_URL,
    };
  },
  created() {},
  mounted() {
    window.gapi.signin2.render('my-signin2', {
      scope: 'profile email',
      width: 240,
      height: 50,
      longtitle: true,
      theme: 'dark',
      onsuccess: this.onSuccess,
      onfailure: this.onFailure,
    });
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
label {
  margin-bottom: 0;
}

.invalid-feedback {
  display: block;
  font-size: 0.75rem;
}

.socialLogin {
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
  margin-bottom: 20px;
}
.footer {
    position: fixed;
    bottom: 0;
    right: 0;
    left: 0;
}

/*  */
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
  margin-bottom: 120px;
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
a {
  text-decoration: none;
}

</style>
