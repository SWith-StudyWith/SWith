<template>
  <a :href="KAKAO_AUTH_CODE_URL">
    <img src="@/assets/img/kakao_login_medium_narrow.png" alt="kakao login button">
  </a>
</template>
<script>
import { KAKAO_GET_AUTH_CODE_URL } from '@/api/kauth';

export default {
  name: 'KakaoLoginBtn',
  components: {},
  setup() {
    const KAKAO_AUTH_CODE_URL = KAKAO_GET_AUTH_CODE_URL;
    return { KAKAO_AUTH_CODE_URL };
  },
}
</script>
<style scoped>

</style>
