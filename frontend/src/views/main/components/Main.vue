<template>
  <div class="home">
    <Navbar />
    <header class="header">
      <MainHeader />
    </header>
    <section>
      <StudyList :studies="state.studies" />
    </section>
    <Footer />
  </div>
</template>

<script>
import { reactive } from 'vue';
import { useStore } from 'vuex';
import Navbar from '../../common/Navbar.vue';
import MainHeader from './MainHeader.vue';
import StudyList from './StudyList.vue';
import Footer from '../../common/Footer.vue';

export default {
  name: 'Main',
  components: { Navbar, MainHeader, StudyList, Footer },
  setup() {
    const store = useStore();
    const state = reactive({
      studies: store.state.study.studyList
    });
    // const error = ref(null);

    // const load = () => {
    //   axios.get('https://d82e66db-a86a-4041-9f92-ecedba015b47.mock.pstmn.io/studies')
    //   // axios.get(`VUE_APP_LOCAL_URI/studies`)
    //     .then((res) => studies.value = res.data.data.studies)
    //     .catch((err) => console.log(err))
    // };

    // load();
    // onMounted(
    //   () => {
    //     // store.dispatch('GET_STUDY_LIST')
    //   }
    // )
    return {
      state
    };
  },
};
</script>

<style scoped>
.spinner-border{
  margin-top: 200px;
  margin-bottom: 200px;
}
</style>
