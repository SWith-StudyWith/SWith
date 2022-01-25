<template>
  <div>
    <Navbar />
    <div class="container">
      <header>
        <h1>스터디룸 만들기</h1>
      </header>
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
                <input @change="changeFile" type="file" class="form-control" id="inputFileUploadInsert" accept="image/*"/>
              </div>
              <button @click="onClickUpdateUserInfo" class="btn btn-primary col-12 btn-create">스터디 만들기</button>
            </form>
          </section>
        </div>
      </div>
    </div>
    <SignOut />
    <Footer />
  </div>
</template>

<script>
import { toRefs, reactive } from 'vue';
import { useStore } from 'vuex';
import Navbar from '@/views/common/Navbar.vue';
import Footer from '@/views/common/Footer.vue';
import SignOut from '@/views/members/components/SignOut.vue';

export default {
  name: '',
  components: { Navbar, Footer, SignOut },
  setup() {
    const store = useStore();
    const state = reactive({
      userInfo : store.getters.getUserInfo,
      file: store.getters.getUserInfo.profileImgUrl
    });

    const changeFile = (event) => {
      console.log(event)
      if(event.target.files && event.target.files.length > 0){
        state.file = URL.createObjectURL(event.target.files[0]);
      }
    };


    return {
      ...toRefs(state), changeFile
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
h1{
  font-size: 24px;
  color: #737B7D;
  text-align: center;
  margin-top: 70px;
  margin-bottom: 70px;
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
  margin-bottom: 50px;
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
</style>
