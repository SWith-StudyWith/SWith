<template>
  <nav id="nav">
    <section class="align-text-center d-inline-block ">
      <router-link class="navbar-brand" v-if="!state.isLogin" to="/" >
        <img src="@/assets/SWith_logo2.png" alt="" height="75"/>
      </router-link>
      <router-link class="navbar-brand" v-else to="/main">
        <img src="@/assets/SWith_logo2.png" alt="" height="75"/>
      </router-link>
    </section>

    <section id="nav-list" v-if="!state.isLogin">
      <router-link :to="{ name: 'SignUp' }">
        <button class="btn btn-primary">회원가입</button>
      </router-link>
      <router-link :to="{ name: 'Login' }">
        <button class="btn btn-success" style="color:white">
          로그인
        </button>
      </router-link>
    </section>
    <section id="nav-list" v-else>
      <span style="font-weight:500;">{{ state.userInfo.nickname }} 님 환영합니다 !</span>
      <div class="dropdown">
        <img src="../../assets/img/profile1.png" alt="" role="button" id="dropdownMenuLink" data-bs-toggle="dropdown" aria-expanded="false" style="width: 50px;" >
        <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
          <li><router-link :to="{ name: 'MyPage' }">회원정보 수정</router-link></li>
          <li>
            <router-link v-on:click="onClickLogout" to="/Login">로그아웃</router-link>
          </li>
        </ul>
      </div>
    </section>
  </nav>
</template>

<script>
import { toRefs, reactive } from '@vue/reactivity';
import { useStore } from 'vuex';
import { computed } from '@vue/runtime-core';
import { useRouter } from 'vue-router'


export default {
  name: 'Navbar',
  setup(){
    const store = useStore()
    const router = useRouter()

    const state = reactive({
      isLogin : computed(() => {
        if(store.state.user.userInfo.accessToken == '' || store.state.user.userInfo.accessToken == undefined)
          return false
        else return true
      }),
      userInfo : store.getters.getUserInfo,
    })

    const onClickLogout = function (e) {
      e.preventDefault();
      store.commit('LOGOUT')
      router.push({ name: 'Login' })
    };

    return{
      state, onClickLogout,
    }
  },
  created() {},
  mounted() {},
  unmounted() {},
  methods: {},
};
</script>

<style scoped>
#nav {
  top: 0;
  position: sticky;
  background-color: #FFF7F6;
  display: flex;
  justify-content: space-between;
  padding: 0px 200px;
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

.logoutBtn{
  margin: 0px;
}
.dropdown-menu{
  text-align: center;
  margin: 50px;
}
/* .dropdown-menu.show{
  transform: translate3d(-70px, 40px, 0px);
} */
a{
  text-decoration-line : none;
  color: #2c3e50;
}
.dropdown{
  margin-left: 40px;
}

</style>
