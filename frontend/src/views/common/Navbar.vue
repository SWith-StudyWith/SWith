<template>
  <nav id="nav">
    <section id="logo">
        <router-link class="navbar-brand" to="/">
        <img src="@/assets/SWith_logo.png" alt="" height="50"/>
      </router-link>
    </section>
    <section id="nav-list">
      <router-link :to="{ name: 'SignUp' }">
        <button class="btn btn-primary">회원가입</button>
      </router-link>
      <router-link :to="{ name: 'Login' }">
        <button class="btn btn-primary">
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
  background-color: #FFE4DE;
  display: flex;
  justify-content: space-between;
  padding: 25px;
  height: 88px;
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
  padding-left: 20;
}
button {
  margin: 0px 0px 10px 10px;
}
</style>
