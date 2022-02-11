<template>
<div>
  <!-- 날짜 구분선 -->
  <div class="dateLine" v-if="!isDateConfirm()">
    {{ yymmdd }}
  </div>

  <div class="chat">
    <!-- 내가 보낸 메세지 -->
    <div class="chat-my-message" v-if="chat?.memberId==this.getUserInfo.memberId">
      <p class="chat-my-message-time" >{{ hhmm }}</p>
      <p class="my-content">{{ chat?.content }}</p>
    </div>

    <!-- 상대가 보낸 메세지  -->
    <div class="chat-other-message" v-else>
      <!-- <div class="chat-other-content1" v-if="chat?.memberId != prev[0]?.memberId"> -->
      <div class="chat-other-content1" v-if="chat?.memberId != prev[0]?.memberId">
        <!--  v-if="!isSame" 수정해야.. -->
        <img :src="chat?.imgUrl ? chat?.imgUrl : require(`@/assets/img/navbar/profile.png`)"
          alt="" aria-expanded="false" >
        <p class="chat-other-nickname" >
          {{ chat?.nickname }}
        </p>
      </div>
      <div class="chat-other-content2">
        <!-- <div class="chat-other-content1"> -->
          <p class="other-content">{{ chat?.content }}</p>
          <p class="chat-other-message-time">{{ hhmm }}</p>
        <!-- </div> -->
        <!-- <div class="chat-other-content2">
        </div> -->
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
  }
  ,
  components:{
    // dayjs
  },
  data() {
    return {
      sampleData: '',
      isSame: false,
      isFirst: true,

      // DB : 22/02/06 06:11 PM
      todayDate: dayjs().format('YY/MM/DD'),
      todayTime: dayjs().format('hh:mm A'),
    };
  },
  computed: {
    ...mapGetters([
      'getUserInfo'
    ]),
    yymmdd(){
      var value = this.chat?.createdAt

      if(value == '') return '';

      var data = (value||'').split(" ")

      // YY/MM/DD
      var setTime = ""

      setTime += data[0]
      return setTime
    },
    hhmm(){
      // 22/02/08/ 06:24 PM

      // 이전 메세지 시간 format
      var prevTime = (this.prev[0]?.createdAt||'').split(" ")

      var preValue = ""
      preValue += prevTime[1] + " "
      preValue += prevTime[2]

      // 현재 메세지 시간 format
      var chatTime = (this.chat?.createdAt||'').split(" ")

      var chatValue = ""
      chatValue += chatTime[1] + " "
      chatValue += chatTime[2]

      // console.log(preValue + ", " + chatValue)

      // 나는 하고싶었다 .... 시간 없애는 걸 ...
      if(preValue == chatValue){
        return chatValue
      }
      return chatValue
    }
  },
  methods: {
    // 오늘 날짜인지 확인하기 위한 메소드
    isDateConfirm(){

      // 이전 메세지 날짜 format
      var prevDate = (this.prev[0]?.createdAt||'').split(" ")

      var preValue = ""
      preValue += prevDate[0]

      // 현재 메세지 날짜
      var chatDate = (this.chat?.createdAt||'').split(" ")

      var chatValue = ""
      chatValue += chatDate[0]

      // 이전 값이 널이면 채팅 처음 시작한 것 -> 날짜 표시 true
      if(preValue == null){
        return false
      }
      // 이전 메세지 날짜와 현재 메세지 날짜가 다르면,
      else if(preValue != chatValue){
        return false
      }else return true
    }
  },
  created() {
    if(this.prev == null){
      this.isFirst = false
    }
  },
}
</script>

<style scoped>
img {
  width: 40px;
  height: 40px;
  border-radius: 70%;
  background-color: white;
}
.chat-userinfo-box{
  margin: 10px;
}
.chat {
  border-radius: 10px;
  /* padding: 1rem; */
  padding-bottom: 1vh;
  /* width: fit-content; */
}
.chat-other-message{
  display: flex;
  flex-direction: column;
}
.chat-other-content1{
  width: 100%;
  margin: 1vh 0;
  display: flex;
  flex-direction: row;
}
.chat-other-nickname{
  font-size: 1.7vh;
  font-weight: 700;
  margin-top: 0;
  margin-block-end: 0rem;
  display: block;
  padding: 1vh 0 0 0.7vw;
}

/* message + time */
.chat-other-content2{
  display: flex;
  align-items: flex-end;
  line-break: anywhere;
  justify-content: flex-start;
  width: 100%;
}
/* message content */
/* .chat-other-content2{
  display: flex;
  align-items: flex-end;
  line-break: anywhere;
} */
.other-content{
  margin: 0.8vh 0.3vw 0 2vw;
  border-radius: 0px 20px 20px 20px;
  background-color: #f3f3f3;
  max-width: 10vw;
  color: #414141;
  padding: 1vh 1vw 1vh;
  font-size: 1.6vh;
  font-weight: 500;
}

.chat-other-message-time {
  margin: 0;
  font-size: 1.2vh;
  color: #9c9c9c;
  margin-left: auto;
}
.chat-my-message{
  display: flex;
  justify-content: right;
  align-items: flex-end;
  margin: 0;
  min-height: 6vh;
  line-break: anywhere;
}
.my-content{
  margin: 0.8vh 0 0 1vw;
  border-radius: 20px 20px 0px 20px;
  max-width: 12vw;
  background-color: #ffe3de;
  color: #ffffff;
  color: #414141;
  padding: 1vh 1vw 1vh;
  font-size: 1.6vh;
  font-weight: 500;
  margin-left: 0.3vw;
}
.chat-my-message-time{
  margin: 0;
  font-size: 1.2vh;
  color: #9c9c9c;
  margin-right: auto;
}

.dateLine{
  display: flex;
  flex-basis: 100%;
  align-items: center;
  color: rgba(255, 255, 255, 0.7);
  font-size: 1.5vh;
  /* font-weight: 500; */
  margin-top: 1vh;
  /* margin-bottom: 0.7vh; */
}
.dateLine::before{
  content: "";
  flex-grow: 1;
  margin-right: 15px;
  background: rgba(255, 255, 255, 0.7);
  height: 1px;
  font-size: 0;
  line-height: 0;
}
.dateLine::after{
  content: "";
  flex-grow: 1;
  margin-left: 15px;
  background: rgba(255, 255, 255, 0.7);
  height: 1px;
  font-size: 0;
  line-height: 0;
}
</style>
