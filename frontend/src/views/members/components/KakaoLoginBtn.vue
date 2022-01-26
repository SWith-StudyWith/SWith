<template>
  <a :href="kakaoGetAuthCodeURL">
    <img src="@/assets/img/kakao_login_medium_narrow.png" alt="kakao login button">
  </a>
</template>
<script>
export default {
  name: 'KakaoLoginBtn',
  components: {},
  setup() {
    const kakaoGetAuthCodeURL = `https://kauth.kakao.com/oauth/authorize?client_id=${process.env.VUE_APP_KAKAO_CLIENT_ID}&redirect_uri=${process.env.VUE_APP_KAKAO_REDIRECT_URI}&response_type=code`
    return { kakaoGetAuthCodeURL };
  },
}
</script>
<style scoped>

</style>
