<template src="./index.html">

</template>

<script>
export default {
  name: "FormSearch",
  data() {
    return {
      dsKieuNo: [],
      listChuKyNo: [],
      dsDonVi: [],
      dsBuuCuc: [
        {
          NAME: '',
          VALUE: 'Không chọn'
        }
      ],
      dsTuyenThu: [
        {
          NAME: '',
          VALUE: 'Không chọn'
        }
      ],
      dsNhanVienTC: [
        {
          NAME: '',
          VALUE: 'Không chọn'
        }
      ]
    }
  },
  props: {
    value: {
      type: Object,
      required: true
    },
    vma_tinh: {
      type: String,
      required: true,
      default: ''
    }
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
    this.getData();
  },
  methods: {
    async getData() {
      try {
        this.loading(true);
        let rs;
        try {
          rs = await this.axios.post('ccbs/executor/ts_layds_chukyno', {});
          this.listChuKyNo = rs.data.data;
          if (this.listChuKyNo.length > 0) {
            this.form.lueKyHD = this.listChuKyNo[0].CHUKYNO;
          }
        } catch (e) {
          console.log(e);
        }

        try {
          rs = await this.axios.post('ccbs/danhMuc/ts_tracuu_conno_kieuno', {});
          this.dsKieuNo = rs.data.data;
          if (this.dsKieuNo.length > 0) {
            this.form.lueKieuNo = this.dsKieuNo[0].id;
          }
        } catch (e) {
          console.log(e);
        }

        try {
          rs = await this.axios.post('ccbs/executor/tinh_donvi_qls', {ma_tinh: this.vma_tinh});
          this.dsDonVi = rs.data.data;
          if (this.dsDonVi.length > 0) {
            this.form.lueDonVi = this.dsDonVi[0].DONVIQL_ID;
          }
        } catch (e) {
          console.log(e);
        }
      } catch (e) {
        console.log(e);
      } finally {
        this.loading(false);
      }
    }
  }
}
</script>

<style scoped>

</style>
