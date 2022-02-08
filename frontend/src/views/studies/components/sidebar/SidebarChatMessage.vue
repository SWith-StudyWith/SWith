<template>
  <div class="chat">

    <!-- 내가 보낸 메세지 -->
    <div class="chat-my-message" v-if="chat?.memberId==this.getUserInfo.memberId">
      <!-- <p>{{ isSame }} </p> -->
      <p class="chat-my-message-time" >{{ chat?.createdAt }}</p>
      <p class="my-content">{{ chat?.content }}</p>
    </div>

    <!-- 상대가 보낸 메세지  -->
    <div class="chat-other-message" v-else>
      <div class="chat-other-img">
        <img :src="this.img ? chat?.imgUrl : require(`@/assets/img/navbar/profile.png`)"
          v-if="!isSame"
          alt="" aria-expanded="false">
      </div>
      <div class="chat-other-content">
        <div class="chat-other-content1">
          <p class="chat-other-nickname" v-if="!isSame">{{ chat?.nickname }}</p>
          <!-- DB 날짜 불러올 땐 다시 수정 ! -->
          <p class="chat-other-message-time">{{ chat?.createdAt }}</p>
        </div>
        <div class="chat-other-content2">
          <p class="other-content">{{ chat?.content }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';
// import { reactive, computed } from 'vue'
import dayjs from 'dayjs'

export default {
  nane: '',
  props: {
    chat : Object,
    prev : Array
    // ["chat","prev"]
  }
  ,
  components:{
    // dayjs
  },
  data() {
    return {
      sampleData: '',
      isSame: false,
      img: null,

      // DB : 22/02/06 06:11 PM
      today: dayjs().format('hh:mm A'),
      // today: dayjs().format('오전 hh시 mm분'),

    };
  },
  computed: {
    ...mapGetters([
      'getUserInfo'
    ]),
  },
  methods: {
    isSameUser(chat, prev){
      if(prev === null){
        return false;
      }else if(prev[0]?.memberId == chat?.memberId){
        return true;
      }else{
        return false;
      }
    },
  },
  created() {
    this.isSame = this.isSameUser(this.chat, this.prev);
    if(this.chat?.imgUrl){
      this.img = this.chat?.imgUrl;
    }
  },
}
</script>

<style>
img {
  width: 40px;
  height: 40px;
  border-radius: 70%;
}
.chat-userinfo-box{
  margin: 10px;
}
.chat {
  border-radius: 10px;
  /* padding: 1rem; */
  padding-bottom: 15px;
  /* width: fit-content; */
}
.chat-other-message{
  display: flex;
}
.chat-other-img{
  width: 51px;
  margin-right: 1rem;
}
.chat-other-nickname{
  font-size: 14px;
  font-weight: 700;
  margin-top: 0;
  margin-block-end: 0rem;
}
.chat-other-content{
  width: 100%;
}
/* nickname + date */
.chat-other-content1{
  display: flex;
  align-items: flex-end;
  line-break: anywhere;
  justify-content: flex-end;
  width: 100%;
}
/* message content */
.chat-other-content2{
  display: flex;
  align-items: flex-end;
  line-break: anywhere;
}
.other-content{
  margin: 0.4rem 1rem 0 0;
  border-radius: 0px 20px 20px 20px;
  background-color: #f3f3f3;
  max-width: 180px;
  color: #414141;
  padding: 0.8rem;
  font-size: 14px;
  font-weight: 500;
}
.chat-other-message-time {
  margin: 0;
  font-size: 10px;
  font-weight: 500;
  color: #9c9c9c;
  margin-left: auto;
  margin-right: 16px;
}
.chat-my-message{
  display: flex;
  justify-content: right;
  align-items: flex-end;
  margin: 0;
  min-height: 40px;
  line-break: anywhere;
}
.my-content{
  margin: 0.4rem 0 0 1rem;
  border-radius: 20px 20px 0px 20px;
  max-width: 180px;
  background-color: #acb5e4;
  color: #ffffff;
  /* color: #414141; */
  padding: 0.8rem;
  font-size: 14px;
  font-weight: 500;
  max-width: 170px;
  margin-left: 3px;;
}
.chat-my-message-time{
  margin: 0;
  font-size: 10px;
  color: #9c9c9c;
  margin-right: auto;
}
</style>
