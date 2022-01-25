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

      <span>{{ state.userInfo.nickcname }}님 환영합니다!</span>
      <button class="btn btn-success" @click="onClickLogout" style="color:white">
        로그아웃
      </button>
    </section>
  </nav>
</template>

<script>
import { reactive } from '@vue/reactivity';
import { useStore } from 'vuex';
import { computed } from '@vue/runtime-core';
import { useRouter } from 'vue-router'

export default {
  name: 'Navbar',
  setup(){
    const store = useStore()
    const router = useRouter()

    // store.commit('SET_USER_ACCESS_TOKEN', localStorage.getItem('accessToken'))
    // store.commit('SET_USER_INFO', localStorage.getItem('nickname'))

    const state = reactive({
      isLogin: computed(() => {
        if(localStorage.getItem('isLogin')) return true
        else return false
      }),
      userInfo : store.getters.getUserInfo,
    })

    const onClickLogout = function (e) {
      e.preventDefault();
      // const accessToken = localStorage.getItem('accessToken')
      // 로그아웃으로 localStorage 해제
      localStorage.removeItem('accessToken')
      localStorage.removeItem('email')
      localStorage.removeItem('nickname')
      localStorage.removeItem('goal')
      localStorage.removeItem('isLogin')
      localStorage.clear()
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
