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
  margin-top: 7vh;
  margin-bottom: 5.5vh;
}
.profile-img {
  width: 8vh;
  height: 8vh;
  border-radius: 70%;
  background-color: white;
}
.nickname{
  font-size: 2vh;
  margin-top: 0.5vh;
  margin-bottom: 0.2vh;
}
.email{
  font-size: 1.75vh;
  color: rgb(199, 199, 199);
}
.col-8 {
  padding-left: 0;
}
</style>
