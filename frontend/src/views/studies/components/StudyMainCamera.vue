<template>
  <div class="card" style="height: 300px;">
    StudyMainCamera
  </div>
  <div class="text-center">
    <button class="btn btn-secondary circle-btn mx-3 p-3">
      <i class="fas fa-video"></i>
    </button>
    <button class="btn btn-secondary circle-btn mx-3 p-3">
      <i class="fas fa-microphone"></i>
    </button>
  </div>
</template>
<script>
export default {
  name: '',
  components: {},
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
.circle-btn{
  border-radius: 70%;
}
</style>
