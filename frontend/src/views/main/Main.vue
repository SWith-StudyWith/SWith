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
