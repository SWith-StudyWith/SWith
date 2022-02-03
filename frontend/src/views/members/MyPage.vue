<template>
  <div>
    <Navbar />
    <div class="container">
      <!-- <header> -->
        <h1 class="form-title">내 정보 수정</h1>
      <!-- </header> -->
      <div class="row d-flex justify-content-center">
        <div class="col-4" style="">
          <form class="userInfo-wrapper" enctype="multipart/form-data">
            <div class="d-flex justify-content-center">
              <div class="dropend">
                <div class="image-wrapper" role="button" id="dropdownMenuLink" data-bs-toggle="dropdown" aria-expanded="false" >
                  <img class="profile-img" :src="state.profileImgSrc" alt=""  >
                </div>
                <ul class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                  <li>
                    <label for="changeProfile" class="img-form-label">프로필 이미지 선택</label>
                  </li>
                  <li>
                    <span @click="onClickDefaultImg" class="img-revert-default">기본 이미지로 변경</span>
                  </li>
                </ul>
              </div>
              <div class="form-group mt-3 mb-3">
                <input @change="onClickUploadFile" id="changeProfile" type="file" ref="file" class="form-control" accept="image/*" style="display: none;" />
              </div>
            </div>

            <!-- Google이나 Kakao 로그인인 경우 안 보이게 하기 -->
            <div class="row d-flex justify-content-between">
              <p class="col-6" style="padding: 0px;">{{ state.userInfo.email }}</p>
              <div class="col-4 d-flex justify-content-end" v-if="state.userInfo.path === 'O'" style="padding: 0px">
                <button type="button" class="btn btn-change-pw btn-success"
                  data-bs-toggle="modal" data-bs-target="#changePasswordModal">
                  비밀번호 변경
                </button>
              </div>
            </div>
            <div class="row">
              <label for="nickname" class="form-label">닉네임</label>
              <input type="text" class="form-control" id="nickname" v-model="state.nickname">
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
        </div>
      </div>
    </div>
    <ChangePasswordModal />
    <SignOutModal />
    <Footer />
  </div>
</template>

<script>
import { ref, computed } from 'vue';
import { useStore } from 'vuex';
import Navbar from '@/views/common/Navbar.vue';
import Footer from '@/views/common/Footer.vue';
import SignOutModal from '@/views/members/components/SignOutModal.vue';
import ChangePasswordModal from './components/ChangePasswordModal.vue';
// import { getUserInfo } from '../../api/user'
export default {
  name: '',
  components: { Navbar, Footer, SignOutModal, ChangePasswordModal },
  setup() {
    const store = useStore();
    const state = ref({
      userInfo : store.getters.getUserInfo,
      nickname : store.getters.getUserInfo.nickname,
      profileImg: '',
      updated: false,
      profileImgSrc : computed(() => {
        if (state.value.userInfo.profileImg) {
          return state.value.userInfo.profileImg
        } else {
          return require(`@/assets/img/navbar/profile.png`)
        }
    })

    });

    const onClickUploadFile = function(e) {
      console.log(e.target.value)
      const file = e.target.files[0];
      state.value.userInfo.profileImg = URL.createObjectURL(file);
      state.value.profileImg = file;
      state.value.updated = true;
    };

    const onClickDefaultImg = (e) => {
      console.log(e.target.value)
      state.value.userInfo.profileImg = '';
      state.value.updated = true;
    }

    const onClickUpdateUserInfo = (e) => {
      e.preventDefault();
      const updateUserData = new FormData();
      updateUserData.append("nickname", state.value.nickname)
      updateUserData.append("goal", state.value.userInfo.goal)
      updateUserData.append("profileImg", state.value.profileImg)
      updateUserData.append("updated", state.value.update)
      console.log(state.value.userInfo)
      store.dispatch('updateUserInfo', updateUserData)
    }
    return {
      state, onClickUpdateUserInfo, onClickUploadFile, onClickDefaultImg
    }
  },

}
</script>

<style scoped>
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
.img-form-label{
  /* width: 150px;
  height: 150px;
  border-radius: 70%; */
  padding-bottom: 10px;
  cursor: pointer;
}
.img-revert-default{
  padding-bottom: 10px;
  cursor: pointer;
}
.image-wrapper{
  margin-bottom: 30px;
  width: 150px;
  height: 150px;
  border-radius: 70%;
  overflow: hidden;
  justify-content: center;
}
.profile-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  background: #BDBDBD;
}
.signout-btn:hover {
  cursor: pointer;
  font-weight: bold;
}
.signout-btn{
  color: #BDBDBD;
}
.dropdown-menu{
  text-align: center;
}
.dropend{
  margin-left: 40px;
}

/* setting */
.form-label{
  margin-bottom: 3px;
  padding: 1px;
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
button{
  font-size: 14px;
  text-align: center;
}
input::placeholder {
  font-size: 12px;
  padding: auto;
  vertical-align: middle;
}
input {
  background-color: #F4F5F4;
  vertical-align: middle;
}
textarea {
  background-color: #F4F5F4;
  vertical-align: middle;
}
</style>
