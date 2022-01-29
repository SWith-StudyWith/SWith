<template>
  <Navbar/>
  <div class="container mt-5">
    <div class="row">
      <div class="col-4">
        <StudyDetailHeader :studyInfo="state.studyInfo"/>
        <StudyDetailCamera/>
        <router-link :to="{ name: 'StudyPage', params: { studyId: state.studyInfo.studyId, studyCode: state.studyInfo.studyCode } }">
          <button class="btn btn-success my-3 text-white">입장하기</button>
        </router-link>
      </div>
      <div class="col-8">
        <StudyDetailKanbanBoard :studyInfo="state.studyInfo"/>
      </div>
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
import StudyDetailHeader from '@/views/studies/components/detail/StudyDetailHeader';
import StudyDetailCamera from '@/views/studies/components/detail/StudyDetailCamera';
import StudyDetailExitModal from '@/views/studies/components/detail/StudyDetailExitModal';
import StudyDetailKanbanBoard from '@/views/studies/components/detail/StudyDetailKanbanBoard';
import { reactive, computed } from 'vue'
import { useRoute } from 'vue-router';
import { useStore } from 'vuex'

export default {
  name: 'StudyDetail',
  components: {
    Navbar,
    StudyDetailHeader,
    StudyDetailCamera,
    StudyDetailExitModal,
    StudyDetailKanbanBoard,
  },
  setup() {
    const route = useRoute();
    const store = useStore()
    store.dispatch('GET_STUDY_LIST', route.params.studyId)
    const state = reactive({
      studyInfo: computed(() => {
        return store.state.study.studyInfo
      })
    });
    return { state };
  },
}
</script>
<style scoped>

</style>
