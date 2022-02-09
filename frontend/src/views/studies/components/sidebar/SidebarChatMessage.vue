<template>
  <div class="chat">

    <!-- 내가 보낸 메세지 -->
    <div class="chat-my-message" v-if="chat?.memberId==this.getUserInfo.memberId">
      <p class="chat-my-message-time" >{{ hhmm }}</p>
      <p class="my-content">{{ chat?.content }}</p>
    </div>

    <!-- 상대가 보낸 메세지  -->
    <div class="chat-other-message" v-else>
      <div class="chat-other-img">
        <!--  v-if="!isSame" 수정해야.. -->
        <img :src="chat?.imgUrl ? chat?.imgUrl : require(`@/assets/img/navbar/profile.png`)"
          alt="" aria-expanded="false" v-if="!isSame">
      </div>
      <div class="chat-other-content">
        <div class="chat-other-content1">
          <!--  v-if="!isSame" -->
          <p class="chat-other-nickname" v-if="!isSame">{{ chat?.nickname }}</p>
          <p class="chat-other-message-time">{{ hhmm }}</p>
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
      todayDate: dayjs().format('YY/MM/DD'),
      todayTime: dayjs().format('hh:mm A'),
    };
  },
  computed: {
    ...mapGetters([
      'getUserInfo'
    ]),
    hhmm(){
      var value = this.chat?.createdAt
      // 22/02/08/ 06:24 PM
      if(value == '') return '';

      var data = (value||'').split(" ")

      var setTime = ""

      // 오늘 날짜이면 시간만
      if(data[0] == this.todayDate){
        setTime += data[1] + " "
        setTime += data[2]
      }else{
        setTime += data[0]
      }

      return setTime
    }
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

<style scoped>
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
