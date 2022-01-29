<template>
  <div>
    <div class="text-center">
      <span class="fs-2 me-2 fw-bold">{{ this.studyInfo.studyName }}</span>
      <router-link :to="{ name: 'StudyUpdate', params: this.$route.params }"><i class="fas fa-pen"></i></router-link>
    </div>
    <div class="align-middle">
      <span class="text-muted pe-3">목표 </span>
      <span class="">{{ this.studyInfo.studyGoal }}</span>
    </div>
    <p>
      {{ this.studyInfo.studyCode }}
      <span @click="copyCode">
        <i class="fas fa-copy"></i>
      </span>
    </p>
  </div>
</template>
<script>
export default {
  name: 'StudyDetailHeader',
  components: {},
  props: {
    studyInfo: Object,
  },
  setup(props) {
    // 스터디 url 복사
    const copyCode = function () {
      navigator.clipboard.writeText(props.studyInfo.studyCode)
    }
    return { copyCode };
  },
  created() {},
  mounted() {},
  unmounted() {},
  methods: {}
}
</script>
<style scoped>

</style>
