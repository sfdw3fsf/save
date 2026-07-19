<template src="./index.html">

</template>

<script>
import API from '../API';

export default {
  name: "HostInfo",
  props: {
    hostInfo: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      cbLoaiCap: []
    };
  },
  mounted() {
    this.loadData();
  },
  methods: {
    async loadData() {
      try {
        this.loading(true)

        let cbLoaiCap = this.getCbLoaiCap()
        await Promise.all([cbLoaiCap]);

        this.loading(false)
      } catch (err) {
        this.$toast.error(err.message);
      }
    },

    async getCbLoaiCap() {
      try {
        await API.danhmuc_loai_cap(this.axios, {})
          .then(rs => {
            this.cbLoaiCap = rs.data.data;
          });
      } catch (err) {
        this.$toast.error(err.message);
      }
    },
  }
}
</script>

<style scoped>

</style>
