<template>
<body>
  <div class="home">
    <Navbar />
    <header class="header">
      <MainHeader />
    </header>
    <section>
      <StudyList :studies="state.studyList" />
    </section>
  </div>
    <Footer class="footer"/>
</body>
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
html, body {
margin: 0;
height: 100%;
}
.home {
min-height: 100%;
/* position: relative; */
/* footer height
padding-bottom: 200px;  */
margin-bottom: -200px;
}
/* section {
  min-height: 100vh;
} */
.footer {
/* position: absolute;
bottom: 0;
left: 0;
right: 0;
color: white;
background-color: #16806e; */
}
</style>
