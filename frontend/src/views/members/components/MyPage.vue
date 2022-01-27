<template>
  <div>
    <Navbar />
    <div class="container">
      <header>
        <h1>내 정보 수정</h1>
      </header>
      <div class="row d-flex justify-content-center">
        <div class="col-4">
          <section>
            <form class="userInfo-wrapper" :model="formData">
              <div class="image-wrapper d-flex justify-content-center">
                <label for="changeProfile" class="img-form-label">
                  <img :src="state.userInfo.profileImgUrl" :fit="fit" class="profile-img" style="cursor:pointer">
                </label>
                <input
                  id="changeProfile"
                  class="form-control"
                  ref="file"
                  @change="onClickUploadFile"
                  type="file"
                  accept="image/*"
                  style="display: none;"
                />
              </div>
              <p class="col-8 d-inline-flex" style="">{{ state.userInfo.email }}</p>
              <!-- Google이나 Kakao 로그인인 경우 안 보이게 하기 -->
              <div class="col-4 d-inline-flex justify-content-end" v-if="state.userInfo.path === 'O'">
                <router-link to="/members/changepassword">
                  <button type="button" class="btn btn-change-pw btn-primary">비밀번호 변경</button>
                </router-link>
              </div>
              <div class="mb-3">
                <label for="nickname" class="form-label">닉네임</label>
                <input type="text" class="form-control" id="nickname" v-model="state.userInfo.nickname">
              </div>
              <div class="row">
                <label for="goal" class="form-label">나의 목표</label>
                <textarea class="form-control form-goal" id="goal" rows="3" v-model="state.userInfo.goal"></textarea>
              </div>
              <div class="row">
                <button @click="onClickUpdateUserInfo" class="btn btn-primary btn-save">변경 사항 저장</button>
              </div>
            </form>
            <!-- Button trigger modal -->
            <span class="text-decoration-underline signout-btn" data-bs-toggle="modal" data-bs-target="#signOutModal" style="margin-bottom: 100px;">
              회원 탈퇴
            </span>

          </section>
        </div>
      </div>
    </div>
    <ChangePasswordModal />
    <SignOut />
    <Footer />
  </div>
</template>

<script>
import { ref } from 'vue';
import { useStore } from 'vuex';
import Navbar from '@/views/common/Navbar.vue';
import Footer from '@/views/common/Footer.vue';
import SignOut from '@/views/members/components/SignOut.vue';
import ChangePasswordModal from './ChangePasswordModal.vue';

export default {
  name: '',
  components: { Navbar, Footer, SignOut, ChangePasswordModal },
  setup() {
    const store = useStore();
    const state = ref({
      userInfo : store.getters.getUserInfo,
      imgFile: '@/assets/img/profile1.png'
    });

    const onClickUploadFile = function(e) {
      console.log(e.target.value)
      const file = e.target.files[0];
      state.value.userInfo.profileImgUrl = URL.createObjectURL(file);
      state.value.imgFile = file;
      // if(e.target.files && e.target.files.length > 0){
      //   state.value.file = URL.createObjectURL(e.target.files[0]);
      // }
    };

    const onClickUpdateUserInfo = () => {
      const updateUserData = {
        nickname: state.value.userInfo.nickname,
        goal: state.value.userInfo.goal,
        img_url: state.value.userInfo.imgFile
      }
      store.dispatch('updateUserInfo', updateUserData)
    }
    // image는 formData로?
    // var updateUserData = new FormData()
    // updateUserData.append("image", state.userInfo.profileImgUrl )
    // const formData = new FormData(); formData.append("image", this.image)

    return {
      state, onClickUpdateUserInfo, onClickUploadFile
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
  margin-bottom: 24px;
}
p{
  font-size: 18px;
  font-weight: 700;
  display: flex;
  margin-bottom: 0px
}
.btn-change-pw {
  text-align: center;
  font-size: 0.7rem;
  margin-right: 0px;
}
.btn-save{
  margin-bottom: 50px;
  margin-right: 0px;
  margin-left: 0px;
}
.uploadImage{
  margin-bottom: 20px;
}
.form-goal{
  margin-bottom: 36px;
}
/* .box {

    overflow: hidden;
    background: #BDBDBD;
} */
.img-form-label{
  width: 150px;
  height: 150px;
  border-radius: 70%;
}
.image-wrapper{
  margin-bottom: 30px;
}
.profile-img {
    width: 150px;
    height: 150px;
    border-radius: 70%;
    overflow: hidden;
    background: #BDBDBD;
}
.profile-img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    /* object-fit: cover; */
}
.signout-btn:hover {
  cursor: pointer;
  font-weight: bold;
}
.signout-btn{
  color: #BDBDBD;
}
</style>
