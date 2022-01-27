<template>
  <Navbar/>
  <div class="container">
    <div class="row col-5">
      <StudyMainHeader/>
      <StudyMainCamera/>
      <button class="btn btn-secondary my-3">입장하기</button>
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

export default {
  name: '',
  components: {
    Navbar,
    StudyMainHeader,
    StudyMainCamera,
    StudyMainExitModal
  },
  setup() {
  },
  created() {},
  mounted() {},
  unmounted() {},
  methods: {}
}
</script>
<style scoped>

</style>
