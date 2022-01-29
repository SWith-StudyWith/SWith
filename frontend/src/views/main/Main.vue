<template>
  <div class="home">
    <Navbar />
    <header class="header">
      <MainHeader />
    </header>
    <section>
      <StudyList :studies="state.studyList" />
    </section>
    <Footer />
  </div>
</template>

<script>
import { reactive, computed } from 'vue';
import { useStore } from 'vuex';
import Navbar from '../common/Navbar.vue';
import MainHeader from './components/MainHeader.vue';
import StudyList from './components/StudyList.vue';
import Footer from '../common/Footer.vue';

export default {
  name: 'Main',
  components: { Navbar, MainHeader, StudyList, Footer },
  setup() {
    const store = useStore();
    store.dispatch('GET_STUDY_LIST')
    const state = reactive({
      studyList: computed(() => {
        return store.state.study.studyList
      }),
      studyInfo: computed(() => {
        return store.state.study.studyInfo
      })
    });
    // const error = ref(null);

    // const load = () => {
    //   axios.get('https://d82e66db-a86a-4041-9f92-ecedba015b47.mock.pstmn.io/studies')
    //   // axios.get(`VUE_APP_LOCAL_URI/studies`)
    //     .then((res) => studies.value = res.data.data.studies)
    //     .catch((err) => console.log(err))
    // };

    // load();
    // onCreated(
    //   async () => {
    //     await
    //     console.log(store.state.study)
    //     console.log(store.state.study.studyList)
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
