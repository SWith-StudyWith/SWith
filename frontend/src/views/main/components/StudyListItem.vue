<template>
  <a @click="onClickStudyItem(study)">
    <div class="card">
      <img :src="study.studyImage" class="card-img-top" alt="...">
      <div class="card-body">
        <h5 class="card-title">{{ study.studyName }}</h5>
        <p class="card-text">{{ study.studyGoal }}</p>
      </div>
    </div>
  </a>
</template>

<script>
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';

export default {
  props: ['study'],
  setup() {
    const store = useStore()
    const router = useRouter()

    const onClickStudyItem = function (study) {
      store.dispatch('GET_STUDY_INFO', study.studyId)
      router.push({ name: 'StudyDetail', params: { studyCode: study.studyCode }})
    }
    return { onClickStudyItem }
  }
};
</script>

<style scoped>

.card {
  width: 100%;
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
