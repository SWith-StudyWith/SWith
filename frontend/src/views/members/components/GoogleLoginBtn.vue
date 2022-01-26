<template>
  <a :href="googleGetAuthCodeURL">
    <img src="@/assets/img/icon_oauth_google/btn_google_signin_dark_normal_web.png" alt="google login button">
  </a>
</template>
<script>

export default {
  name: 'GoogleLoginBtn',
  components: {},
  setup() {
    const googleGetAuthCodeURL = `https://accounts.google.com/o/oauth2/auth?client_id=${process.env.VUE_APP_GOOGLE_CLIENT_ID}&redirect_uri=${process.env.VUE_APP_GOOGLE_REDIRECT_URI}&scope=email profile&response_type=code`;
    return { googleGetAuthCodeURL };
  },
}
</script>
<style scoped>

</style>
