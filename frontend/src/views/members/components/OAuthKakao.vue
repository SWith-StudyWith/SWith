<script>
import { mapActions } from 'vuex';

export default {
  name: 'OAuthKakao',
  components: {},
  setup() {
  },
  created() {
    this.LOGIN_KAKAO(this.$route.query.code)
  },
  mounted() {},
  unmounted() {},
  methods: {
    ...mapActions(['LOGIN_KAKAO'])
  }
}
</script>
<style scoped>

</style>
