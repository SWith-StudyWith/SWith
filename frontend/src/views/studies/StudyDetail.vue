<template>
  <Navbar/>
  <div class="container">
    <div class="row col-5">
      <StudyDetailHeader/>
      <StudyDetailCamera/>
      <button class="btn btn-success my-3 text-white">입장하기</button>
    </div>
    <div class="text-end">
      <button type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#exitModal">
        탈퇴하기
      </button>
    </div>
    <StudyDetailExitModal/>
  </div>
</template>
<script>
import Navbar from '@/views/common/Navbar.vue';
import StudyDetailHeader from '@/views/studies/components/StudyDetailHeader';
import StudyDetailCamera from '@/views/studies/components/StudyDetailCamera';
import StudyDetailExitModal from '@/views/studies/components/StudyDetailExitModal';
import { onMounted, reactive } from 'vue'
import { useStore } from 'vuex';

export default {
  name: 'StudyMain',
  components: {
    Navbar,
    StudyDetailHeader,
    StudyDetailCamera,
    StudyDetailExitModal
  },
  setup() {
    const store = useStore();
    const state = reactive({
      studyId: store.state.study.studyInfo.studyId
    })
    onMounted(() => {
      store.dispatch('GET_STUDY_INFO', state.studyId)
    })

    return { state };
  },
  created() {},
  mounted() {},
  unmounted() {},
  methods: {}
}
</script>
<style scoped>

</style>
