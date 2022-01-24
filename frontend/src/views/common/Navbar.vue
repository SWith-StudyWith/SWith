<template>
  <nav id="nav">
    <section class="align-text-center d-inline-block ">
        <router-link class="navbar-brand" to="/">
        <img src="@/assets/SWith_logo2.png" alt="" height="75"/>
      </router-link>
    </section>
    <section id="nav-list">
      <router-link :to="{ name: 'SignUp' }">
        <button class="btn btn-primary">회원가입</button>
      </router-link>
      <router-link :to="{ name: 'Login' }">
        <button class="btn btn-success" style="color:white">
          로그인
        </button>
      </router-link>
    </section>
  </nav>
</template>

<script>
export default {
  name: 'Navbar',
};
</script>

<style scoped>
#nav {
  top: 0;
  position: sticky;
  background-color: #FFF7F6;
  display: flex;
  justify-content: space-between;
  padding: 0px 80px;
  text-align: center;
  align-content: center;
  height: 75px;
  min-width: 900px;
  box-sizing: border-box;
  user-select: none;
  z-index: 1;
  /* vertical-align: middle; */
  /* background-color: #FFF7F6; */
  /* opacity: 0.5; */
  /* background-color: var(--headerBackground); */
  /* color: var(--textColor); */
}
#nav-list {
  display: flex;
  justify-content: flex-start;
  align-items: center;
}
button {
  margin: 0px 0px 0px 20px;
  width: 102px;
}

</style>
