<template>
  <div>Login...</div>
</template>
<script>
import { mapActions } from 'vuex';

export default {
  name: 'OAuthGoogle',
  components: {},
  setup() {
  },
  created() {
    this.LOGIN_GOOGLE(this.$route.query.code)
    // console.log(this.$route.query.code)
  },
  mounted() {},
  unmounted() {},
  methods: {
    ...mapActions(['LOGIN_GOOGLE'])
  }
}
</script>
<style scoped>

</style>
