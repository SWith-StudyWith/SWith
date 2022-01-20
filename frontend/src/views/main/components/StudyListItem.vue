<template>
  <div class="study">
    <!-- <router-link :to="{ name: 'StudyView', params: { id: study.studyCode }}"> -->
      <h3>{{ study.studyName }}</h3>
      <p>{{ study.studyGoal }}</p>
      <img :src="study.studyImage">
    <!-- </router-link> -->
  </div>
</template>

<script>
export default {
  props: ['study'],
};
</script>
