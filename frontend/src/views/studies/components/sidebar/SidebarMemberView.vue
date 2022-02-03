<template>
  <div>
    회원 목록
    <div v-if="members.length">
      <div class="row" v-for="member in members" :key="member.memberId">
        <div class="col">
          <img :src="member.imgUrl?member.imgUrl:require(`@/assets/img/navbar/profile.png`)" :fit="fit" class="profile-img">
        </div>
        <div class="col">
          {{ member.nickname }}
        </div>
      </div>
    </div>
    <div v-if="members.length === 0">
      없는데용 ?
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

<style>
/* .img-form-label{
  width: 150px;
  height: 150px;
  border-radius: 70%;
} */
.profile-img {
  width: 50px;
  height: 50px;
  /* object-fit: cover; */
  border-radius: 70%;
}
</style>
