<template>
  <div id="gSignInWrapper">
    <a :href="googleGetAuthCodeURL">
      <div id="customBtn" class="customGPlusSignIn">
        <img src="@/assets/img/icon_logo/google_logo.png" style="size: 5px" alt="">
        <span class="buttonText">구글 로그인</span>
      </div>
    </a>
  </div>
</template>
<script>
export default {
  name: 'GoogleLoginBtn',
  components: {},
  setup() {
    const googleGetAuthCodeURL = `https://accounts.google.com/o/oauth2/auth?client_id=${process.env.VUE_APP_GOOGLE_CLIENT_ID}&redirect_uri=${process.env.VUE_APP_GOOGLE_REDIRECT_URI}&scope=email profile&response_type=code`
    return { googleGetAuthCodeURL };
  },
}
</script>
<style scoped type="text/css">
    #customBtn {
      display: inline-block;
      background: white;
      color: rgb(93, 93, 93);
      width: 183px;
      height: 45px;
      border-radius: 5px;
      border: thin solid rgb(223, 222, 222);
      /* box-shadow: 1px 1px 1px grey; */
      white-space: nowrap;
      padding-inline: 12px;
      padding-top: 8px;
    }
    #customBtn:hover {
      cursor: pointer;
    }
    img {
      display: inline-block;
      width: 28px;
      height: 28px;
      margin-right: 30px;
    }
    span.buttonText {
      display: inline;
      vertical-align: middle;
      padding-left: 0px;
      padding-right: 42px;
      font-size: 14px;
      font-weight: bold;
      font-family: 'Roboto';
      padding-top: 6px;
    }
  </style>
