<template>

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
      <div class="chat-other-img">
        <!--  v-if="!isSame" 수정해야.. -->
        <img :src="chat?.imgUrl ? chat?.imgUrl : require(`@/assets/img/navbar/profile.png`)"
          alt="" aria-expanded="false" v-if="chat?.memberId != prev[0]?.memberId">
      </div>
      <div class="chat-other-content">
        <div class="chat-other-content1">
          <p class="chat-other-nickname" v-if="chat?.memberId != prev[0]?.memberId">
            {{ chat?.nickname }}
          </p>
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
      var value = this.chat?.createdAt

      if(value == '') return '';

      var data = (value||'').split(" ")

      // hh:mm A
      var setTime = ""

      setTime += data[1] + " "
      setTime += data[2]
      return setTime
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

      console.log(preValue + ", " + chatValue)
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
  color: #c0c0c0;
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
  font-weight: 500;
  color: #c0c0c0;
  margin-right: auto;
}

.dateLine{
  display: flex;
  flex-basis: 100%;
  align-items: center;
  color: rgba(255, 255, 255, 0.7);
  font-size: 15px;
  /* font-weight: 500; */
  margin-top: 10px;
  margin-bottom: 22px;
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
