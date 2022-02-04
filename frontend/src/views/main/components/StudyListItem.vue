<template>
  <router-link :to="{ name: 'StudyDetail', params: { studyId: study.studyId, studyCode: study.studyCode }}">
    <div class="card">
      <img :src="study.studyImage" class="card-img-top" alt="study image">
      <div class="card-body">
        <h5 class="card-title">{{ study.studyName }}</h5>
        <p class="card-text">{{ study.studyGoal }}</p>
      </div>
    </div>
  </router-link>
</template>

<script>
export default {
  props: ['study'],
};
</script>

<style scoped>

.card {
  width: 100%;
  margin-top: 30px;
}

.card-body{
  height: 170px;
}

.card-img-top{
  height: 258px;
  object-fit: cover;
}

.card h1 {
  font-size: 24px;
}
.card p {
  font-size: 13px;
}

</style>
