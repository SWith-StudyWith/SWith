<template>
  <Navbar/>
  <div class="container">
    <div class="row col-5">
      <StudyMainHeader/>
      <StudyMainCamera/>
      <button class="btn btn-success my-3 text-white">입장하기</button>
    </div>
    <div class="text-end">
      <button type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#exitModal">
        탈퇴하기
      </button>
    </div>
    <StudyMainExitModal/>
  </div>
</template>
<script>
import Navbar from '@/views/common/Navbar.vue';
import StudyMainHeader from '@/views/studies/components/StudyMainHeader';
import StudyMainCamera from '@/views/studies/components/StudyMainCamera';
import StudyMainExitModal from '@/views/studies/components/StudyMainExitModal';
import { onMounted, reactive } from 'vue'
import { useStore } from 'vuex';

export default {
  name: 'StudyMain',
  components: {
    Navbar,
    StudyMainHeader,
    StudyMainCamera,
    StudyMainExitModal
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
