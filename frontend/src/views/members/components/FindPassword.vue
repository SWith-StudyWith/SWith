<template>
  <Navbar />
  <div class="container">
    <p class="form-title">비밀번호 찾기</p>
    <div class="row">
      <form
        class="findpassword-form"
      >
        <label for="email" class="form-label">이메일</label>
        <input class="form-control" id="email" type="email"
          placeholder="가입하신 이메일을 입력해주세요"
          style="background-color: #F4F5F4; vertical-align: middle;"
        >
      </form>
    </div>
    <br>
    <div class="row">
      <form class="findpassword-btn">
        <button class="btn col-12 button_navy"
          style="font-size: 14px;"
        >이메일 보내기</button>
      </form>
    </div>
  </div>
  <Footer />
</template>

<script>
import Navbar from '@/views/common/Navbar.vue';
import Footer from '@/views/common/Footer.vue';

export default {
  name: 'FindPassword',
  components: {
    Navbar,
    Footer
  }
};
</script>

<style scoped>
* {
  text-align: left;
}
.container {
  width: 377px;
  margin-top: 180px;
  margin-bottom: 200px;
}
input::placeholder {
  font-size: 12px;
  padding: auto;
  vertical-align: middle;
}
form{
margin-top: 20px;
}
.form-title {
  font-size: 24px;
  text-align: center;
}
.form-label {
  font-size: 12px;
}
.button_navy {
  background-color: #334666;
  color: white;
  height: 42px;
}
input[type="email"] {
  height: 42px;
  padding: 10px 15px;
}
button{
  text-align: center;
}
</style>
