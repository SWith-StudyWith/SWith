<template>
  <loading v-model:active="state.loading"
      :can-cancel="false"
      :is-full-page="false"
      :height="height"
      :width="width"
      :color="color"
      :loader="loader"
      :background-color="bgColor"
      :opacity="opacity"
      :lock-scroll="false"
      class="vld-overlay"
      :style="state.loading ? '-webkit-backdrop-filter: blur(2px); backdrop-filter: blur(2px);' : ''"
    ></loading>
  <div class= "memberDiv">
    <p class="title">👥 회원 목록</p>
    <div class="member-body">
      <div v-if="members.length" >
        <div class="row" v-for="member in state.list" :key="member.memberId">
          <div class="col-3">
            <img :src="member.imgUrl?member.imgUrl:require(`@/assets/img/navbar/profile.png`)" :fit="fit" class="profile-img">
          </div>
          <div class="col-7">
            <p class="nickname" :style="state.userInfo.memberId == member.memberId ? 'color:#ffe8e8' : ''">
              <span>{{ member.nickname }}&nbsp</span>
              <span v-if="state.userInfo.memberId == member.memberId">(나)</span>
            </p>
            <p class="goal" :style="state.userInfo.memberId == member.memberId ? 'color:#ffe8e8' : ''">{{ member.goal }}</p>
          </div>
        </div>
      </div>
      <div v-if="members.length === 0">
          스터디 회원 목록을 불러오지 못했습니다.
      </div>
    </div>
  </div>
</template>

<script>
import { reactive } from '@vue/reactivity'
import { useStore } from 'vuex';
import Loading from 'vue-loading-overlay';
import 'vue-loading-overlay/dist/vue-loading.css';

export default {
  data(){
    return{
      loader: 'dots',
      color: '#F5CEC7',
      bgColor: '#1E304F',
      height: 80,
      width: 80,
      opacity: 0.2,
      lockScroll: true,
    }
  },
  components:{
    Loading
  },
  props:{
    members: Array,
  },
  setup(props) {
    const store = useStore();
    const state = reactive({
      loading: false,
      userInfo : store.getters.getUserInfo,
      list: Array,
    })

    function loadingCall(){
      state.loading = true
      setTimeout(() => {
        state.loading = false
      }, 1500)
    }

    function memberRange(){
      state.list = props.members;
      for(var i = 0; i < state.list.length ;i++){
        // 본인 아이디면 맨 앞으로
        if(state.list[i].memberId == state.userInfo.memberId){
          [state.list[0], state.list[i]] = [state.list[i], state.list[0]]
        }else{
          state.list[i] = props.members[i]
        }

      }
    }

    return { loadingCall, state, memberRange }
  },
  created() {
    this.loadingCall()
    this.memberRange()
  },
  mounted() {},
  unmounted() {},
  methods: {}
}
</script>

<style scoped>
.memberDiv{
  text-align: left;
  height: 100vh;
  display: flex;
  flex-direction: column;
}
.member-body{
  flex-grow: 1;
  overflow-x: hidden;
  overflow-y: scroll;
  scroll-behavior: smooth;
  width: 100%;
  margin-left: 1vw;
}
/* scroll 안보이게 */
.member-body::-webkit-scrollbar {
  display: none;
}
::-webkit-scrollbar{
    width: 12px;
}
::-webkit-scrollbar-thumb{
    background-color: #999;
    border-radius: 10px;
}
::-webkit-scrollbar-track{
    background-color: #1E304F;
}

.title{
  font-size: 3vh;
  font-weight:500;
  margin: 5.5vh 0 2vh 1vw;
}
.row{
  margin-top: 1vh;
  margin-bottom: 20px;
}
.profile-img {
  width: 5.5vh;
  height: 5.5vh;
  border-radius: 50%;
  background-color: #5a5a5a;
}
.nickname{
  font-size: 1.6vh;
  margin-bottom: 0.2vh;
  color : #aebed4;
}
.goal{
  font-size: 1.2vh;
  color: #aebed4;
  margin-bottom: 0;
}
.col-3 {
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
  padding-inline: 0;
}
.col-7 {
  padding-left: 0;
  display: flex;
  flex-direction: column;
  justify-content: center;
}
.vld-overlay{
  margin-left: 4vw;
}
</style>
