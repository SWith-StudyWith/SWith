<template>
  <div class="chat">
    <!-- 내가 보낸 메세지 -->
    <div class="chat-my-message" v-if="msg.memberId==this.getUserInfo.memberId">
      <p class="chat-my-message-time">{{ msg.createdAt }}</p>
      <p class="my-content">{{ msg.content }}</p>
    </div>

    <!-- 상대가 보낸 메세지  -->
    <div class="chat-other-message" v-else>
      <div class="chat-other-img">
        <img :src="msg.imgUrl?msg.imgUrl:require(`@/assets/img/navbar/profile.png`)" alt="" aria-expanded="false" v-if="!isSame">
      </div>
      <div class="chat-other-content">
        <div class="chat-other-content1">
          <p class="chat-other-nickname" v-if="!isSame">{{ msg.nickname }}</p>
          <p class="chat-other-message-time">{{ msg.createdAt }}</p>
        </div>
        <div class="chat-other-content2">
          <p class="other-content">{{ msg.content }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';
import { reactive, computed } from 'vue'
export default {
  nane: '',
  props: ["msg","prev"]
  ,
  data() {
    return {
      sampleData: '',
      isSame: false,
      img: null,
      imgUrl: this.msg.imgUrl,
      memberId: this.msg.memberId,
      // prevList: Array,
    };
  },
  setup(props){

    const state = reactive({
      prevList: computed(() => {
        return props.prev[0]
      }),
      memId: computed(() => {
        return props.prev[0]?.memberId
      }),
    })

    return { state }
  },
  computed: {
    ...mapGetters([
      'getUserInfo'
    ]),
  },
  methods: {
    isSameUser(msg, prev){
      if(prev === null){
        return false;
      }else if(this.prev[0]?.memberId == this.msg?.memberId){
        return true;
      }else{
        return false;
      }
    }
  },
  created() {
    this.isSame = this.isSameUser(this.msg, this.prev);
    if(this.msg?.imgUrl){
      this.img = this.msg?.imgUrl;
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
