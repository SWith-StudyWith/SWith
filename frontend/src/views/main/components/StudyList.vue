<template>
  <div class="container">
    <div class="row">
      <p class="study-list-header col-sm-12 col-lg-5">스터디 목록</p>
      <div class="col-12 offset-lg-0 col-lg-7 join-study">
        <div class="row navbar">
          <div class="col-12 col-lg-6 mb-3">
            <input type="text" class="form-control" v-model="studyCode" placeholder="스터디 URL">
          </div>
          <div class="col-12 col-md-5 offset-lg-0 col-lg-3 mb-3">
            <button class="btn btn-primary" @click="onClickJoin">참여하기</button>
          </div>
          <div class="col-12 col-md-5 offset-lg-0 col-lg-3 mb-3">
            <router-link :to="{ name: 'StudyCreate'}">
              <button class="btn btn-success">만들기</button>
            </router-link>
          </div>
        </div>
      </div>
      <div v-if="studies.length" class="d-flex flex-wrap">
        <div v-for="study in studies" :key="study.studyId" class="col-sm-12 col-lg-6 col-xl-4 card-box">
          <StudyListItem :study="study" />
        </div>
      </div>
      <div v-if="studies.length == 0" class="none">
        <p>아직 참여중인 스터디가 없습니다🙃</p>
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
import notifications from '@/composables/notifications'

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
    // store.dispatch('GET_STUDY_LIST')
    const router = useRouter();
    const { notifyWarning, notifyDanger } = notifications();

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
            notifyDanger('해당 스터디가 존재하지 않습니다.😯')
          } else if (res.data.code === 409) {
            notifyDanger('이미 참여중인 스터디입니다.😓')
          }
        },
        (err) => {
          console.log(err)
          notifyWarning('서버가 아파요.😥')
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
  padding-inline: 200px;
}
.study-list-header {
  font-weight: 600;
  font-size: 36px;
  text-align: left;
  /* padding-left: 20px;
  margin-bottom: 40px; */
}
.form-control {
  height: 42px;
  font-size: 15px;
  margin-top: 0;
}
.btn{
  width: 94%;
  height: 42px;
  color: #ffffff;
  font-size: 14px;
  margin-top: 0;
}
.flex-wrap{
  padding: 0px;
}
.card-box{
  padding: 20px
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
.navbar .col-3{
  margin-right: 0px;
  margin-left: 0px;
  padding-right: 0px;
}
.navbar .col-6{
  margin-right: 0px;
  padding: 0px;
}
</style>
