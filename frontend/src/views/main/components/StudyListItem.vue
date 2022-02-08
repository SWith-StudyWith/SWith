<template>
  <router-link class="text-decoration-none" :to="{ name: 'StudyDetail', params: { studyId: study.studyId, studyCode: study.studyCode }}">
    <div class="card scale">
      <img :src="state.studyImgSrc" class="card-img-top" alt="study image">
      <div class="card-body">
        <p class="card-title text-truncate">{{ study.studyName }}</p>
        <p class="card-text text-truncate">{{ study.studyGoal }}</p>
      </div>
    </div>
  </router-link>
</template>

<script>
import { reactive, computed } from 'vue';
export default {
  props: ['study'],
  setup(props) {
    const state = reactive({
      studyImgSrc: computed(()=> {
        if ( props.study.studyImgURL) {
          return props.study.studyImgURL
        } else {
          return require(`@/assets/img/study_room/studyroom.png`)
        }
      }),
    })
    return { state }
  }
};
</script>

<style scoped>

.card {
  width: 100%;
  border-radius: 3%;
}

.card-body{
  height: 115px;
  /* padding: 25px 20px 20px; */
  border-top: 1px solid rgba(0, 0, 0, 0.125);
  display: flex;
  flex-direction: column;
  display: inline-block
}

.card-img-top{
  height: 205px;
  object-fit: cover;
  background-color: white;
  border-top-left-radius: 3%;
  border-top-right-radius: 3%;
}

.card-title {
  font-size: 21px;
  font-weight: 500;
  align-self: center;
  margin-bottom: 0;
}
.card-text{
  font-size: 15px;
  align-self: center;
}
.scale {
  transform: scale(1);
  -webkit-transform: scale(1);
  -moz-transform: scale(1);
  -ms-transform: scale(1);
  -o-transform: scale(1);
  transition: all 0.3s ease-in-out;   /* 부드러운 모션을 위해 추가*/
}
.scale:hover {
  transform: scale(1.05);
  -webkit-transform: scale(1.05);
  -moz-transform: scale(1.05);
  -ms-transform: scale(1.05);
  -o-transform: scale(1.05);
  box-shadow : 2px 3px 5px 2px rgb(190, 190, 190);
}
</style>
