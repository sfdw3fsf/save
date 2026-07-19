<template src="./index.html">

</template>

<script>
export default {
  name: "FormSearch",
  data() {
    return {
      kyHD: []
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
  async mounted() {
    try {
      let rs = await this.axios.post('ccbs/executor/ts_layds_chukyno', {});
      this.kyHD = rs.data.data;
      if (this.kyHD.length > 0) {
        this.form.lueTuKyHD = this.form.lueDenKyHD = this.kyHD[0].CKN_DANGSO;
        this.form.txtTuKy = this.form.txtDenKy = '01/' + this.kyHD[0].CHUKYNO.trim();
      }
    } catch (e) {
      console.log(e);
    }
  },
  methods: {
    setChuKyTu(val) {
      if (this.kyHD.length > 0) {
        let filter = this.kyHD.filter(item => {
          return item.CKN_DANGSO === Number(val);
        });

        if (filter.length > 0) {
          this.form.txtTuKy = '01/' + filter[0].CHUKYNO.trim();
        }
      }
    },

    setChuKyDen(val) {
      if (this.kyHD.length > 0) {
        let filter = this.kyHD.filter(item => {
          return item.CKN_DANGSO == Number(val);
        });

        if (filter.length > 0) {
          this.form.txtDenKy = '01/' + filter[0].CHUKYNO.trim();
        }
      }
    },
  }
}
</script>

<style scoped>

</style>
