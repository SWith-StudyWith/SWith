<template>
    <div class="study-list">
      <div class="row row-cols-3 d-flex justify-content-between">
        <p class="study-list-header col-5">스터디 목록</p>
        <div class="col-7 d-flex flex-row">
            <input type="text" class="form-control" placeholder="스터디 URL">
          <button class="btn btn-primary">스터디 참여하기</button>
        </div>
          <div v-for="study in studies" :key="study.id">
            <StudyListItem :study="study" />
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
.study-list {
  margin-top: 90px;
  margin-bottom: 90px;
  margin-inline: 187px;
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
}

.form-control{
  width:337px;
  height: 42px;
  margin-top: 10px;
}
.btn{
  width: 133px;
  height: 42px;
  color: #ffffff;
  font-size: 14px;
  margin-top: 10px;
}
</style>
