<template>
  <div>
    <div class="text-center">
      <span class="fs-2 me-2 fw-bold">{{ state.studyInfo.studyName }}</span>
      <router-link :to="{ name: 'StudyUpdate', params: this.$route.params }"><i class="fas fa-pen"></i></router-link>
    </div>
    <div class="align-middle">
      <span class="text-muted pe-3">목표 </span>
      <span class="">{{ state.studyInfo.studyGoal }}</span>
    </div>
    <p>
      {{ state.studyInfo.studyCode }}
      <span @click="copyCode">
        <i class="fas fa-copy"></i>
      </span>
    </p>
  </div>
</template>
<script>
import { reactive } from 'vue';
import { useStore } from 'vuex';


export default {
  name: 'StudyDetailHeader',
  components: {},
  setup() {
    const store = useStore()
    const state = reactive({
      studyInfo: store.state.study.studyInfo,
    });
    // 스터디 url 복사
    const copyCode = function () {
      navigator.clipboard.writeText(state.studyInfo.studyCode)
    }
    return { state, copyCode };
  },
  created() {},
  mounted() {},
  unmounted() {},
  methods: {}
}
</script>
<style scoped>

</style>
