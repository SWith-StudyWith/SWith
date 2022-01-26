<template>
  <Navbar />
  <div class="container">
    <p class="form-title">비밀번호 찾기</p>
    <div class="row">
      <form
        class="findpassword-form"
      >
        <label for="email" class="form-label">이메일</label>
        <input class="form-control" id="email" type="email" v-model="state.email"
          placeholder="가입하신 이메일을 입력해주세요"
          style="background-color: #F4F5F4; vertical-align: middle;" required
        >
        <div :style="{ visibility: (state.isValidEmail || !state.wasInputed.email)? 'hidden' : 'visible' }"
          class="invalid-feedback">유효하지 않은 이메일입니다.</div>
      </form>
    </div>
    <br>
    <div class="row">
      <form class="findpassword-btn">
        <button class="btn col-12 button_navy"
          style="font-size: 14px;"
        >이메일 보내기</button>
      </form>
    </div>
  </div>
  <Footer />
</template>

<script>
/* eslint-disable */
import Navbar from '@/views/common/Navbar.vue';
import Footer from '@/views/common/Footer.vue';

import { reactive, computed } from 'vue';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';

export default {
  name: 'FindPassword',
  components: {
    Navbar,
    Footer
  },
  setup(){
    const state = reactive({
      email: '',

      wasInputed: {
        email: false,
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
    })

    const validateEmail = function (email) {
      const re = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
      return re.test(email);
    };

    return{
      state,
    }

  },

};
</script>

<style scoped>
* {
  text-align: left;
}
.container {
  width: 377px;
  margin-top: 180px;
  margin-bottom: 200px;
}
input::placeholder {
  font-size: 12px;
  padding: auto;
  vertical-align: middle;
}
form{
margin-top: 20px;
}
.form-title {
  font-size: 24px;
  text-align: center;
}
.form-label {
  font-size: 12px;
}
.button_navy {
  background-color: #334666;
  color: white;
  height: 42px;
}
input[type="email"] {
  height: 42px;
  padding: 10px 15px;
  margin-bottom: 3px;
}
button{
  text-align: center;
}

.invalid-feedback {
  display: block;
  font-size: 0.75rem;
  margin-top: 0;
  margin-bottom: 0.5rem;
}
</style>
