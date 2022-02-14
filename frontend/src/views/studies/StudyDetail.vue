<template>
<div>
  <Navbar/>
  <div class="container mt-5">
    <div class="row">
      <div class="col-md-12 col-lg-4 px-5">
        <StudyDetailHeader :studyInfo="state.studyInfo"/>
        <StudyDetailCamera @setDevice="setDevice($event)"/>
        <button class="btn btn-primary my-3 text-white" @click="onClickEnterBtn">입장하기</button>
      </div>
      <div class="col-md-12 col-lg-8 pt-5">
        <StudyDetailKanbanBoard :studyInfo="state.studyInfo"/>
      </div>
      <div class="text-end">
        <button type="button" class="btn btn-secondary" data-bs-toggle="modal" data-bs-target="#exitModal">
          탈퇴하기
        </button>
      </div>
    </div>
    <StudyDetailExitModal/>
  </div>
</div>
</template>
<script>
import Navbar from '@/views/common/Navbar.vue';
import StudyDetailHeader from '@/views/studies/components/detail/StudyDetailHeader';
import StudyDetailCamera from '@/views/studies/components/detail/StudyDetailCamera';
import StudyDetailExitModal from '@/views/studies/components/detail/StudyDetailExitModal';
import StudyDetailKanbanBoard from '@/views/studies/components/detail/StudyDetailKanbanBoard';
import { reactive, computed, onBeforeUnmount } from 'vue'
import { useRoute, useRouter } from 'vue-router';
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
    const router = useRouter();
    const route = useRoute();
    const store = useStore()
    store.dispatch('GET_STUDY_INFO', route.params.studyId)

    const state = reactive({
      studyInfo: computed(() => {
        return store.state.study.studyInfo;
      }),
      videoId: '',
      audioId: '',
      videoOn: false,
      audioOn: false,
    });
    const setDevice = function (deviceSettings) {
      state.videoId = deviceSettings.videoId
      state.audioId = deviceSettings.audioId
      state.videoOn = deviceSettings.videoOn
      state.audioOn = deviceSettings.audioOn
    }
    const onClickEnterBtn = function () {
      router.push(
        {
          name: 'Studies',
          params: {
            studyId: route.params.studyId,
            studyCode: route.params.studyCode,
            initVideoId: state.videoId,
            initAudioId: state.audioId,
            initVideoOn: state.videoOn,
            initAudioOn: state.audioOn,
          }
        }
      )
    }
    onBeforeUnmount(() => {
      store.commit('SET_STUDY_INFO', {})
    })
    return { state, route, setDevice, onClickEnterBtn };
  },
}
</script>
<style scoped>
.btn-secondary {
  color: white;
  margin-top: 1rem;
}
</style>
