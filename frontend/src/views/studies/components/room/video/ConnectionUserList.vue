<template>
  <div>{{ clientData }},{{ idData }},{{ checkUser }}</div>
</template>

<script>
export default {
  name: "ConnetionUserList",
  props: {
    publisher: undefined,
    subscribers: []
  },
  data() {
    return {
      search: ""
    };
  },

  methods: {
    getConnectionData() {
      const { connection } = this.subscriber.stream;
      return JSON.parse(connection.data);
    }
  }
};
</script>
