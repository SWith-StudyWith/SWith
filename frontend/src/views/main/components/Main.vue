<template>
  <div class="home">
    <header class="header">
      <div class="container d-flex justify-content-center">
        <div class="row justify-content-between align-items-center">
          <div class="col-3">
            <img src="@/assets/img/Saly-40.png" alt="">
          </div>
          <div class="col-6">
            <p class="fw-bold" style="font-size:44px">내가 참여한 스터디</p>
          </div>
          <div class="col-3">
            <img src="@/assets/img/Saly-25-1.png" alt="">
          </div>
        </div>
      </div>
    </header>
    <section>
      <div class="container">
        <div v-if="error">{{ error }}</div>
        <div v-if="studies.length">
          <StudyList :studies="studies" />
        </div>
        <div v-else>Loading...</div>
      </div>
    </section>
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

<style scoped>

.header{
  background-color: #FFF4CF;
}

.header .container{
  height: 351px;
}

</style>
