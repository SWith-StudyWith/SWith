<template>
  <div class="home">
    <h1>Home</h1>
    <div v-if="error">{{ error }}</div>
    <div v-if="studies.length">
      <StudyList :studies="studies" />
    </div>
    <div v-else>Loading...</div>

  </div>
</template>

<script>
import { ref } from 'vue';
import StudyList from './StudyList.vue';

export default {
  name: 'Home',
  components: { StudyList },
  setup() {
    const studies = ref([]);
    const error = ref(null);

    const load = async () => {
      try {
        const data = await fetch('http://localhost:3000/studies');
        // console.log(data)
        if (!data.ok) { // data.ok가 false인 경우 = error인 경우
          throw Error('no data available');
        }
        studies.value = await data.json();
      } catch (err) {
        error.value = err.message; // const error 값을 throw한 message로 업뎃
        console.log(error.value);
      }
    };
    load();
    return { studies, error };
  },
};
</script>
