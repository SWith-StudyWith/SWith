<template>
  <div>
    <Navbar />
    <div class="container">
      <h1 class="form-title">스터디룸 만들기</h1>
      <div class="row d-flex justify-content-center">
        <div class="col-4">
          <section>
            <form class="studyInfo-wrapper">
              <div class="mb-3">
                <label for="studyName" class="form-label">스터디 이름</label>
                <input type="text" class="form-control" id="studyName" v-model="state.studyName" required placeholder="스터디 이름">
              </div>
              <div class="mb-3">
                <label for="studyCode" class="form-label">스터디 코드</label>
                <p class="base-url">https://swith.com/</p>
                <input type="text" class="form-control" id="studyCode" v-model="state.studyCode" required placeholder="URL을 입력하세요">
              </div>
              <div class="mb-3">
                <label for="goal" class="form-label">스터디 목표</label>
                <textarea class="form-control form-goal" id="studyGoal" rows="3" v-model="state.studyGoal" placeholder="스터디 목표를 한 줄로 표현해 보세요!"></textarea>
              </div>
              <div class="d-flex justify-content-start">
                <div class="image-wrapper">
                  <label for="changeStudyImg" class="img-form-label">
                    <img :src="state.studyImgSrc" class="study-img" style="cursor:pointer">
                  </label>
                  <input
                    id="changeStudyImg"
                    class="form-control"
                    ref="file"
                    @change="onClickUploadFile"
                    type="file"
                    accept="image/*"
                    style="display: none;"
                  />
                </div>
              </div>
              <button @click="onClickCreateStudy" class="btn btn-primary col-12 btn-create">스터디 만들기</button>
            </form>
          </section>
        </div>
      </div>
    </div>
    <Footer />
  </div>
</template>

<script>
import { ref, computed } from 'vue';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';
import { createStudy } from '@/api/study';
import Navbar from '@/views/common/Navbar.vue';
import Footer from '@/views/common/Footer.vue';

export default {
  name: '',
  components: { Navbar, Footer },
  setup() {
    const store = useStore();
    const router = useRouter();
    const state = ref({
      studyInfo: store.getters.getStudyInfo,
      studyName: '',
      studyCode: '',
      studyGoal: '',
      studyImgUrl: '',
      studyImgSrc: computed(()=> {
        if (state.value.studyImgUrl) {
          return state.value.studyImgUrl
        } else {
          return require(`@/assets/img/study_room/studyroom.png`)
        }
      }),
      wasInputed: {
        studyName: false,
        studyCode: false
      }
    });

    const onClickUploadFile = (e) => {
      console.log(e)
      const file = e.target.files[0]
      state.value.studyImgUrl = URL.createObjectURL(file);
    };

    const onClickCreateStudy = (e) => {
      e.preventDefault();
      if (state.value.studyName === '' || state.value.studyCode === '') {
        state.value.wasInputed.studyName = true;
        state.value.wasInputed.studyCode = true;
        return;
      }
      createStudy(
        {
          studyName: state.value.studyName,
          studyCode: state.value.studyCode,
          studyGoal: state.value.studyGoal,
          studyImgUrl: state.value.studyImgUrl
        },
        (res) => {
          console.log(res)
          switch (res.data.code) {
            case 200: alert('스터디룸 생성 완료!🔨')
            break;
          }
        },
        (err) => {
          console.log(err)
          alert('서버가 아파유~')
        },
        router.push({ name: 'studyDetail', params: { studyId: state.value.studyInfo.studyId, studyCode: state.value.studyInfo.studyCode }})
      )
    }
    return {
      state, onClickUploadFile, onClickCreateStudy
    }
  },

}
</script>

<style scoped>
.container{
  margin-top: 50px;
  justify-content: center;
}
form{
  text-align: left;
}
section{
  margin-bottom: 100px;
}
p{
  font-size: 18px;
  font-weight: 700;
  display: flex;
}
.btn-change-pw {
  text-align: center;
  font-size: 0.7rem;
}
.btn-create{
  margin: 10px 0px 0px 0px;
}
.uploadImage{
  margin-bottom: 20px;
}
.box{
    width: 240px;
    height: 160px;
    overflow: hidden;
    background: #BDBDBD;
}
.profile-img{
    width: 100%;
    height: 100%;
    object-fit: cover;
}
.base-url{
  font-size: 14px;
  font-weight: 400;
  color: #737B7D;
  margin-bottom: 5px;
}
.studyroom-img p{
  font-size: 16px;
  font-weight: 400;
  margin-bottom: 5px;
}

/* basic setting */
button{
  font-size: 14px;
  text-align: center;
}
.form-label{
  margin-bottom: 3px;
}
.form-title {
  font-size: 24px;
  text-align: center;
  margin-bottom: 30px;
}
.container {
  margin-top: 100px;
  margin-bottom: 120px;
}
input::placeholder {
  font-size: 12px;
  padding: auto;
  vertical-align: middle;
}
input{
  background-color: #F4F5F4;
  vertical-align: middle;
}
textarea{
  background-color: #F4F5F4;
  vertical-align: middle;
}
.image-wrapper{
  margin-bottom: 6px;
  width: 200px;
  height: 150px;
  border-radius: 4%;
  overflow: hidden;
  justify-content: center;
}
.study-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
</style>
