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
        if (mem.user_id == idData) {
          this.color = true;
          return this.color;
        }
      });
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
