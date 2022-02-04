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
  width: 90%;
  text-align: left;

  height: 100vh;

  display: flex;
  /* scroll */
  flex-direction: column;
}
.member-body{
  flex-grow: 1;
  /* scroll */
  overflow: scroll;
  scroll-behavior: smooth;
  /* overflow: auto; */
}
/* scroll 안보이게 */
.member-body::-webkit-scrollbar {
  display: none;
}
.row{
  margin-bottom: 20px;
}
.title{
  font-size: 25px;
  font-weight:500;
  margin-top: 20px;
  margin-bottom: 30px;
}
.profile-img {
  width: 55px;
  height: 55px;
  border-radius: 70%;
}
.nickname{
  font-size: 17px;
  margin-bottom: 2px;
}
.email{
  font-size: 13px;
  color: rgb(199, 199, 199);
}
</style>
