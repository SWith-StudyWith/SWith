<template>
  <div class="container">
    <div class="row d-flex justify-content-between">
      <p class="study-list-header col-6">스터디 목록</p>
      <div class="col-6">
        <div class="row navbar">
          <div class="col col-8">
            <input type="text" class="form-control" v-model="studyCode" placeholder="스터디 URL">
          </div>
          <div class="col col-2">
            <button class="btn btn-primary" @click="onClickJoin">참여하기</button>
          </div>
          <div class="col col-2">
            <router-link :to="{ name: 'StudyCreate'}">
              <button class="btn btn-success">만들기</button>
            </router-link>
          </div>
        </div>
      </div>
      <div v-if="studies.length" class="d-flex flex-wrap">
        <div v-for="study in studies" :key="study.studyId" class="col-4 card-box">
          <StudyListItem :study="study" />
        </div>
      </div>
      <div v-if="studies.length == 0" class="none">
        <p>아직 참여중인 스터디가 없습니다🙃</p>
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
import { ref } from 'vue';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router'
import { joinStudy } from '@/api/study';

export default {
  props: {
    studies: Array,
  },
  components: {
    StudyListItem
  },
  setup(props) {
    console.log(props.studies);
    let studyCode = ref('')
    const store = useStore();
    const router = useRouter();
    const onClickJoin = function () {
      if (!studyCode.value) {
        return
      }
      const payload = { code: studyCode.value }
      joinStudy(
        payload,
        (res) => {
          console.log(res.data)
          if (res.data.code === 200) {
            store.dispatch('GET_STUDY_LIST')
            router.push({ name: 'StudyMain', params: { studyCode: studyCode.value } })
          } else if (res.data.code === 400) {
            alert('해당 스터디가 존재하지 않습니다.')
          } else if (res.data.code === 409) {
            alert('이미 참여중인 스터디입니다.')
          }
        },
        (err) => {
          console.log(err)
        }
      )
    }
    return { studyCode, onClickJoin }
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
  margin-top:10px;
}
.btn{
  width: 90px;
  height: 42px;
  color: #ffffff;
  font-size: 14px;
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
.none p{
  font-size: 30px;
  padding-top: 150px;
  padding-bottom: 150px;
}
.navbar > .col{
  margin-right: 0px;
  margin-left: 0px;
  padding-right: 6px;
  padding-left: 0px;
}
</style>
