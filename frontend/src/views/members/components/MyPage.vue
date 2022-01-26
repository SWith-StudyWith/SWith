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
              <div class="box d-inline-flex justify-content-center">
                <img class="profile-img" :src="file" />
              </div>
              <div class="form-group row">
                <input @change="changeFile" type="file" class="form-control" id="inputFileUploadInsert" accept="image/*"/>
              </div>
            <form>
              <div class="row justify-content-between">
                <p class="col-8 d-inline-flex" style="">{{ userInfo.email }}</p>
                <button type="button" class="btn btn-change-pw btn-primary col-3 align-self-end"
                  data-bs-toggle="modal" data-bs-target="#changePasswordModal">
                  비밀번호 변경
                </button>
              </div>
              <div class="row">
                <label for="nickname" class="form-label">닉네임</label>
                <input type="text" class="form-control" id="nickname">
              </div>
              <div class="row">
                <label for="goal" class="form-label">나의 목표</label>
                <textarea class="form-control form-goal" id="goal" rows="3" v-model="userInfo.goal"></textarea>
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
import { toRefs, reactive } from 'vue';
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

    const onClickUpdateUserInfo = () => {
      var updateUserData = new FormData()
      updateUserData.append("nickname", state.userInfo.nickname )
      updateUserData.append("goal", state.userInfo.goal )
      store.dispatch('updateUserInfo', updateUserData)
    }

    return {
      ...toRefs(state), onClickUpdateUserInfo, changeFile
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
.defaultImage{
  margin-bottom: 32px;
}
.uploadImage{
  margin-bottom: 20px;
}
.form-goal{
  margin-bottom: 36px;
}
.box {
    width: 150px;
    height: 150px;
    border-radius: 70%;
    overflow: hidden;
    background: #BDBDBD;
    margin: 20px;
}
.profile-img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}
.signout-btn:hover {
  cursor: pointer;
  font-weight: bold;
}
.signout-btn{
  color: #BDBDBD;
}
</style>
