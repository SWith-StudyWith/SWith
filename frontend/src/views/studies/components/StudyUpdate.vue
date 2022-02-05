<template>
  <div>
    <Navbar />
    <div class="container">
      <h1 class="form-title">스터디룸 수정</h1>
      <div class="row d-flex justify-content-center">
        <div class="col-4">
          <section>
            <form class="studyInfo-wrapper" enctype="multipart/form-data">
              <div>
                <label for="studyName" class="form-label">스터디 이름</label>
                <input type="text" class="form-control" id="studyName" v-model="state.studyInfo.studyName" required>
              </div>
              <div class="mb-3">
                <label for="goal" class="form-label">스터디 목표</label>
                <textarea class="form-control form-goal" id="studyGoal" rows="3" v-model="state.studyInfo.studyGoal"></textarea>
              </div>
              <div class="d-flex justify-content-start mb-4">
                <div class="dropend">
                  <div class="image-wrapper" role="button" id="dropdownMenuLink" data-bs-toggle="dropdown" aria-expanded="false" >
                    <img class="study-img" :src="state.studyImgSrc">
                  </div>
                  <ul class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                    <li>
                      <label for="changeStudyImg" class="dropdown-item img-form-label">스터디 이미지 선택</label>
                    </li>
                    <li>
                      <span @click="onClickDefaultImg" class="dropdown-item img-revert-default">기본 이미지로 변경</span>
                    </li>
                  </ul>
                </div>
                <div class="form-group">
                  <input @change="onClickUploadFile" id="changeStudyImg" type="file" ref="file" class="form-control" accept="image/*" style="display: none;" />
                </div>
              </div>
              <button @click="onClickUpdateStudy" class="btn btn-primary col-12">변경사항 수정</button>
            </form>
          </section>
        </div>
      </div>
    </div>
    <Footer />
  </div>
</template>

<script>
import { reactive, computed } from 'vue';
import { useStore } from 'vuex';
import Navbar from '@/views/common/Navbar.vue';
import Footer from '@/views/common/Footer.vue';

export default {
  name: '',
  components: { Navbar, Footer },
  setup() {
    const store = useStore();
    const state = reactive({
      // studyInfo : store.getters.getStudyInfo,
      studyInfo : computed(() => {
        return store.state.study.studyInfo
      }),
      studyImage: '',
      updated: false,
      studyImgSrc: computed(()=> {
        if (state.studyInfo.studyImgURL) {
          return state.studyInfo.studyImgURL
        } else {
          return require(`@/assets/img/study_room/studyroom.png`)
        }
      }),
    });

    const onClickUploadFile = (e) => {
      console.log(e)
      const file = e.target.files[0]
      state.studyInfo.studyImgURL = URL.createObjectURL(file);
      state.studyImage = file;
      state.updated = true;
    };
    const onClickDefaultImg = () => {
      state.userInfo.studyImgURL = '';
      state.updated = true;
    };
    const onClickUpdateStudy = (e) => {
      e.preventDefault();
      const updateStudyData = new FormData();
      updateStudyData.append("studyName", state.studyInfo.studyName)
      updateStudyData.append("studyGoal", state.studyInfo.studyGoal)
      updateStudyData.append("studyImage", state.studyImage)
      updateStudyData.append("updated", state.updated)
      store.dispatch('updateStudyInfo', { studyId: state.studyInfo.studyId, payload:updateStudyData})
    }
    return {
      state, onClickUploadFile, onClickDefaultImg, onClickUpdateStudy
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
.invalid-feedback {
  display: block;
  font-size: 0.75rem;
  margin-top: 0;
  margin-bottom: 0;
}
.valid-feedback {
  display: block;
  font-size: 0.75rem;
  margin-top: 0;
  margin-bottom: 0.2rem;
  color: green;
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
.img-form-label{
  margin-bottom: 3px;
}
.image-wrapper{
  width: 200px;
  height: 150px;
  border-radius: 4%;
  overflow: hidden;
  justify-content: center;
  cursor: pointer;
  display: block;
}
.study-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  background: #BDBDBD;
}
.dropdown-menu{
  text-align: center;
}
.dropdown-item{
  cursor: pointer;
}
</style>
