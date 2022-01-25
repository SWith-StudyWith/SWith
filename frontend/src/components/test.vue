<template>
  <section class="test">
    <button id="my-signin2"
    class="btn btn-primary btn-lg col-12" @click="GoogleLoginBtn">구글 OAuth2 연동</button>
    <!-- <div v-on:click="GoogleLoginBtn">구글 OAuth2 연동</div> -->
    <!-- <div id="my-signin2" style="display: none"></div> -->
  </section>
</template>

<script>

export default {
  name: 'test',
  methods: {
    GoogleLoginBtn() {
      const self = this;

      window.gapi.signin2.render('my-signin2', {
        scope: 'profile email openid',
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
};
</script>

<style scoped>
  .test{ display:flex; justify-content: center; align-items: center; height:100vh; }
  div{ width: 200px; height:40px;
  background-color:#ffffff;
  border:1px #a8a8a8 solid; color:black;
  display:flex; align-items: center; justify-content: center; cursor:pointer; }
</style>
