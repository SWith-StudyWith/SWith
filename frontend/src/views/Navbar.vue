<template>
  <nav id="nav">
    <section id='logo'>
      <img
        id='logo-icon'
        src="@/assets/SWith_logo.png"
        alt="로고"
        @click="$router.push({name: 'Main'})"
      >
      <div class="slogan-box">
        <!-- <router-link
          id="logo-text square"
          class='text-hover'
          :to="{ name: 'Main' }">
          Square 4 Us
        </router-link>
        <a class="slogan" @click="$router.push({name: 'Main'})">we link the world</a> -->
      </div>
    </section>
    <section v-if='isLogin' id='nav-list'>
      <router-link :to="{ name: 'Landing' }">랜딩으로가</router-link>
      <router-link :to="{ name: 'MyPage' }">마이페이지</router-link>
<!--
      <router-link :to="{ name: 'Tutorial' }">Tutorial</router-link>
      <router-link :to="{ name: 'StudyList' }">Study</router-link>
      <MyStudy v-if='myStudies.length' :myStudies='myStudies'/>
      <router-link :to="{ name: 'User' }">User</router-link>
      <router-link :to="{ name: 'StudyReport' }">Report</router-link>
      <Logout/>
-->
    </section>
    <section v-else id='nav-list'>
      <!-- <Login/> -->
      <router-link :to="{ name: 'SignUp' }">회원가입</router-link>
      <router-link :to="{ name: 'Login' }">로그인</router-link>
    </section>
  </nav>
</template>

<script>
export default {
  name: 'Navbar',
};
</script>

<style>
#nav{
  top: 0;
  position: sticky;
  /* background-color: var(--headerBackground); */
  /* color: var(--textColor); */
  display: flex;
  justify-content: space-between;
  padding: 25px;
  height: 100px;
  min-width: 900px;
  box-sizing: border-box;
  /* vertical-align: middle; */
  user-select:none;
  z-index: 1;
}
#logo, #nav-list {
  display: flex;
  justify-content: flex-start;
  align-items: center;
}
</style>
