<template>
  <div class="container">
    <div class="row d-flex justify-content-between">
      <p class="study-list-header col-6">스터디 목록</p>
        <input type="text" class="form-control" placeholder="스터디 URL">
        <button class="btn btn-primary">스터디 참여하기</button>

      <div v-if="error">{{ error }}</div>
      <div v-if="studies.length" class="d-flex flex-wrap">
        <div v-for="study in studies" :key="study.id" class="col-4 card-box">
            <StudyListItem :study="study" />
        </div>
      </div>
      <div v-else>
        <div class="d-flex justify-content-center">
          <div class="spinner-border" role="status">
            <span class="visually-hidden">Loading...</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import StudyListItem from './StudyListItem.vue';

export default {
  props: ['studies'],
  components: { StudyListItem },
  setup(props) {
    console.log(props.studies);
  },
};
</script>

<style scoped>
.container {
  margin-top: 90px;
  margin-bottom: 90px;
  padding-inline: 100px;
}
.study-list-header {
  font-weight: 600;
  font-size: 36px;
  text-align: left;
  margin-bottom: 40px;
}
.form-control {
  height: 42px;
  font-size: 15px;
  width:337px;
  margin-top:10px;
}
.btn{
  width: 133px;
  height: 42px;
  color: #ffffff;
  font-size: 14px;
  margin-right: 10px;
  margin-left: 10px;
  margin-top: 10px;
}
.flex-wrap{
  padding: 0px;
}
.card-box{
  padding: 10px;
}
.searchbar{
  padding-top: 10px;
}
.spinner-border{
  margin-top: 100px;
}
</style>
