<template>
  <div>
    StudyMainHeader
    <h2>{{ state.studyInfo.studyName }}</h2>
    <h3>{{ state.studyInfo.studyGoal }}</h3>
  </div>
</template>
<script>
import { reactive } from 'vue';
import { useStore } from 'vuex';

export default {
  name: '',
  components: {},
  data() {},
  setup() {
    const store = useStore()
    const state = reactive({
      studyInfo: store.studyInfo,
    });
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
