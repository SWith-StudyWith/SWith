<template>
  <div class="home">
    <header class="header">
      <MainHeader />
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
import StudyList from './components/StudyList.vue';
import MainHeader from './components/MainHeader.vue';
import axios from 'axios';

export default {
  name: 'Home',
  components: { StudyList, MainHeader },
  setup() {
    const studies = ref([]);
    const error = ref(null);

    const load = () => {
      axios.get('https://d82e66db-a86a-4041-9f92-ecedba015b47.mock.pstmn.io/studies')
      // axios.get(`VUE_APP_LOCAL_URI/studies`)
        .then((res) => studies.value = res.data.data.studies )
        .catch((err) => console.log(err))
    }

    load()

    return { studies, error };
  },
};
</script>
