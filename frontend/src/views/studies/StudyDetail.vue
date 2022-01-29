<template>
  <Navbar/>
  <div class="container mt-5">
    <div class="row">
      <div class="col-4">
        <StudyDetailHeader/>
        <StudyDetailCamera/>
        <button class="btn btn-success my-3 text-white">입장하기</button>
      </div>
      <div class="col-8">
        <StudyDetailKanbanBoard />
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
    return { };
  },
  created() {},
  mounted() {},
  unmounted() {},
  methods: {}
}
</script>
<style scoped>

</style>
