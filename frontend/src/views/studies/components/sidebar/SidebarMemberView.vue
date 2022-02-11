<template>
  <div class= "memberDiv">
    <p class="title">👥 스터디 회원 목록</p>
    <div class="member-body">
      <div v-if="members.length" >
        <div class="row" v-for="member in members" :key="member.memberId">
          <div class="col-4">
            <img :src="member.imgUrl?member.imgUrl:require(`@/assets/img/navbar/profile.png`)" :fit="fit" class="profile-img">
          </div>
          <div class="col-8">
            <p class="nickname">{{ member.nickname }}</p>
            <p class="email">{{ member.email }}</p>
          </div>
        </div>
      </div>
      <div v-if="members.length === 0">
        없는데용 ?
      </div>
    </div>

  </div>
</template>

<script>
// import { reactive } from '@vue/reactivity'

export default {
  props:{
    members: Array,
  },
  setup() {

  },
  created() {},
  mounted() {},
  unmounted() {},
  methods: {}
}
</script>

<style scoped>
.memberDiv{
  /* float: right; */
  /* width: 90%; */
  text-align: left;

  height: 100vh;

  display: flex;
  /* scroll */
  flex-direction: column;
}
.member-body{
  flex-grow: 1;
  /* scroll */
  overflow-x: hidden;
  overflow-y: scroll;
  scroll-behavior: smooth;
  width: 100%;
  /* overflow: auto; */
}
/* scroll 안보이게 */
.member-body::-webkit-scrollbar {
  /* display: none; */
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

.row{
  margin-bottom: 20px;
}
.title{
  font-size: 3vh;
  font-weight:500;
  margin: 5.5vh 0 1vh 1vw;
}
.profile-img {
  width: 5.5vh;
  height: 5.5vh;
  border-radius: 50%;
  background-color: white;
}
.nickname{
  font-size: 1.7vh;
  margin-bottom: 0.2vh;
}
.email{
  font-size: 1.5vh;
  color: rgb(199, 199, 199);
  margin-bottom: 0;
}
.row{
  margin-top: 1vh;
}
.col-4 {
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
}
.col-8 {
  padding-left: 0;
  display: flex;
  flex-direction: column;
  justify-content: center;
}
</style>
