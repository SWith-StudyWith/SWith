<template>
  <div>
    <span class="fs-2 me-2">스터디 이름</span>
    <!-- <span class="fs-2 me-2">{{ state.studyInfo.studyName }}</span> -->
    <router-link :to="{ name: 'StudyUpdate', params: this.$route.params }"><i class="fas fa-pen"></i></router-link>
    <h3>목표 : {{ state.studyInfo.studyGoal }}</h3>
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
  name: 'StudyMainHeader',
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
