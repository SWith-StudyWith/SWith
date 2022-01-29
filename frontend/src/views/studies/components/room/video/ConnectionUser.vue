<template>
  <div>{{ clientData }},{{ idData }},{{ checkUser }}</div>
</template>

<script>
export default {
  name: "ConnetionUser",
  props: {
    subscriber: undefined,
    memberlist: []
  },

  computed: {
    checkUser() {
      const { idData } = this.getConnectionData();
      this.memberlist.forEach(mem => {
        console.log("id체크");
        console.log(mem.user_id);
        console.log(idData);
        if (mem.user_id == idData) {
          console.log("id가 똑같네");
          this.color = true;
          return this.color;
        }
      });
      console.log("왜나와..");
      return this.color;
    },
    clientData() {
      const { clientData } = this.getConnectionData();
      return clientData;
    },
    idData() {
      const { idData } = this.getConnectionData();
      return idData;
    }
  },

  methods: {
    getConnectionData() {
      const { connection } = this.subscriber.stream;
      return JSON.parse(connection.data);
    }
  }
};
</script>
