<template src="./index.html">

</template>

<script>
import axios from "axios";

export default {
  name: "FormSearch",
  data() {
    return {
      kyHD: [],
    }
  },
  props: {
    value: {
      type: Object,
      required: true
    },
  },
  computed: {
    form: {
      get() {
        return this.value;
      },
      set(form) {
        this.$emit('input', form);
      }
    }
  },
  mounted() {
    this.loadKyHoaDon();
  },

  methods: {
    async loadKyHoaDon() {
      try {
        let rs = await this.axios.post('web-ccdv/capnhat_loai_thietbi/lay_ds_chukyno', {});
        this.kyHD = rs.data.data;
        if (this.kyHD.length > 0) {
          this.form.kyHD = this.kyHD[0].chukyno;
        }
      } catch (e) {
        console.log(e);
      }
    },
  },
}
</script>

<style scoped>

</style>
