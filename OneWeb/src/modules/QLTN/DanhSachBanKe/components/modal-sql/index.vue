<template src="./index.html"></template>
<style scoped src="./style.scss">

</style>
<script>
import moment from "moment";

export default {
  name: "SqlModal",
  components: {},
  created: async function () {
     console.log(12313)
    this.sql = this.data;
  },
  props: ["data", "id"],
  data() {
    return {
      sql: '12312312'
    };
  },
  computed: {},
  methods: {
  },
};
</script>