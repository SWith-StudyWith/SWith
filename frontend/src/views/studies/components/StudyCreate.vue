<template>
  <div>
    <Navbar />
    <div class="container">
      <h1 class="form-title">스터디룸 만들기</h1>
      <div class="row d-flex justify-content-center">
        <div class="col-4">
          <section>

            <form>
              <div class="mb-3">
                <label for="studyName" class="form-label">스터디 이름</label>
                <input type="text" class="form-control" id="studyName" placeholder="스터디 이름">
              </div>
              <div class="mb-3">
                <label for="studyCode" class="form-label">스터디 코드</label>
                <p class="base-url">https://swith.com/</p>
                <input type="text" class="form-control" id="studyCode" placeholder="URL을 입력하세요">
              </div>
              <div class="mb-3">
                <label for="goal" class="form-label">스터디 목표</label>
                <textarea class="form-control form-goal" id="goal" rows="3" v-model="userInfo.goal" placeholder="스터디 목표를 한 줄로 표현해 보세요!"></textarea>
              </div>
              <div class="studyroom-img">
                <p>대표 이미지</p>
                <div class="box d-inline-flex justify-content-center">
                  <img class="profile-img" :src="file" />
                </div>
              </div>
              <div class="form-group mt-3 mb-3">
                <input @change="onClickUploadFile" type="file" class="form-control" id="inputFileUploadInsert" accept="image/*"/>
              </div>
              <button @click="onClickUpdateUserInfo" class="btn btn-primary col-12 btn-create">스터디 만들기</button>
            </form>
          </section>
        </div>
      </div>
    </div>
    <SignOutModal />
    <Footer />
  </div>
</template>

<script>
import { ref } from 'vue';
import { useStore } from 'vuex';
import Navbar from '@/views/common/Navbar.vue';
import Footer from '@/views/common/Footer.vue';
import SignOutModal from '@/views/members/components/SignOutModal.vue';

export default {
  name: '',
  components: { Navbar, Footer, SignOutModal },
  setup() {
    const store = useStore();
    const state = ref({
      userInfo : store.getters.getUserInfo,
      file: ''
    });

    const onClickUploadFile = (e) => {
      console.log(e)
      const files = e.target.files
      if(files && files.length > 0){
        state.value.file = URL.createObjectURL(files[0]);
      }
    };


    return {
      state, onClickUploadFile
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
textarea {
  background-color: #F4F5F4;
  vertical-align: middle;
}
</style>
