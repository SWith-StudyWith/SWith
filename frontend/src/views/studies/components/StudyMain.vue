<template>
  <Navbar/>
  <div class="container">
    <div class="row col-5">
      <StudyMainHeader/>
      <StudyMainCamera/>
      <button class="btn btn-secondary">입장하기</button>
    </div>
  </div>
</template>
<script>
import Navbar from '@/views/common/Navbar.vue';
import StudyMainHeader from '@/views/studies/components/StudyMainHeader';
import StudyMainCamera from '@/views/studies/components/StudyMainCamera';

export default {
  name: '',
  components: {
    Navbar,
    StudyMainHeader,
    StudyMainCamera,
  },
  data() {
    return {
      sampleData: ''
    }
  },
  setup() {},
  created() {},
  mounted() {},
  unmounted() {},
  methods: {}
}
</script>
<style scoped>

</style>
