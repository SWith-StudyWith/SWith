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
            <img :src="state.defaultImage" class="defaultImage">
            <form>
              <div>
                <input @change='onInputImage()' class="form-control" ref="serveyImage" type="file" accept="image/*">
              </div>
              <p class="col-8 d-inline-flex" style="">{{ state.email }}</p>
              <router-link to="/members/changepassword">
                <button type="button" class="btn btn-primary col-4">비밀번호 변경</button>
              </router-link>
              <div class="mb-3">
                <label for="nickname" class="form-label">닉네임</label>
                <input type="text" class="form-control" id="nickname" v-model="state.nickname">
              </div>
              <div class="mb-3">
                <label for="goal" class="form-label">나의 목표</label>
                <textarea class="form-control form-goal" id="goal" rows="3" v-model="state.goal"></textarea>
              </div>
              <button @click="onClickUpdateMypage" class="btn btn-primary col-12 btn-save">변경 사항 저장</button>
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
// import { updateMypage } from '@/api/user'
import Navbar from '@/views/common/Navbar.vue';
import Footer from '@/views/common/Footer.vue';

export default {
  name: '',
  components: { Navbar, Footer },
  setup() {
    const store = useStore();
    const state = reactive({
      email: computed(() => store.state),
      nickname: computed(() => store.state.user.userinfo.nickname),
      goal: computed(() => store.state.user.userinfo.goal),
      defaultImage: require('@/assets/img/profile1.png'),
      image: '',
    });

    const onInputImage = function () {
      this.state.image = this.$refs.serveyImage.files;
      console.log('this.state.image');
    };
    const onClickUpdateMypage = function (e) {
      e.preventDefault();
      store.dispatch('updateMypage', { nickname: this.state.user.nickname, goal: this.state.user.goal });
    };
    return {
      state, onInputImage, onClickUpdateMypage,
    };
  },
  created() {},
  mounted() {},
  unmounted() {},
  methods: {},
};
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
button {
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
</style>
