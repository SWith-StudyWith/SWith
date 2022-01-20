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
<!--
    <router-link class="navbar-brand" to="/">
      <img src="@/assets/SWith_logo.png" alt="" height="50"
      class="d-inline-block align-text-center"/>
    </router-link>
      <router-link class="nav-link ms-auto" to="/members/signup">
        <button >회원가입</button>
      </router-link>
      <router-link class="nav-link ms-auto" to="/members/login">
        <button >로그인</button>
      </router-link>

  <div id="nav">
    <router-link class="navbar-brand" to="/">
      <img src="@/assets/SWith_logo.png" alt="" height="50"
      />
    </router-link>
    <router-link to="/">Landing</router-link> |
    <router-link to="/main">Main</router-link>
  </div> -->

  <router-view/>
</template>

<script>
</script>
<style lang="scss">
@import 'bootstrap/scss/bootstrap';
@import url('https://fonts.googleapis.com/css2?family=Mulish:ital,wght@0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap');
#app {
  font-family: 'Mulish', 'Noto Sans KR', sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  // text-align: center;
  color: #2c3e50;
}
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
