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
              <div class="form-group mt-3 mb-3">
                <input @change="changeFile" type="file" class="form-control" id="inputFileUploadInsert" accept="image/*"/>
              </div>
            <form>
              <p class="col-8 d-inline-flex" style="">{{ userInfo.email }}</p>
              <router-link to="/members/changepassword">
                <button type="button" class="btn btn-change-pw btn-primary col-4">비밀번호 변경</button>
              </router-link>
              <div class="mb-3">
                <label for="nickname" class="form-label">닉네임</label>
                <input type="text" class="form-control" id="nickname" v-model="userInfo.nickname">
              </div>
              <div class="mb-3">
                <label for="goal" class="form-label">나의 목표</label>
                <textarea class="form-control form-goal" id="goal" rows="3" v-model="userInfo.goal"></textarea>
              </div>
              <button @click="onClickUpdateUserInfo" class="btn btn-primary col-12 btn-save">변경 사항 저장</button>
            </form>
          </section>
        </div>
      </div>
    </div>
    <Footer />
  </div>
</template>

<script>
import { toRefs, reactive } from 'vue';
import { useStore } from 'vuex';
import Navbar from '@/views/common/Navbar.vue';
import Footer from '@/views/common/Footer.vue';

export default {
  name: '',
  components: { Navbar, Footer },
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
}
.btn-change-pw {
  text-align: center;
  font-size: 0.7rem;
}
.btn-save{
  margin-bottom: 106px;
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
}
.profile-img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}
</style>
