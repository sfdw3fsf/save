<template src="./index.html">

</template>

<script>
import API from '../API';
import EventBus from "@/utils/eventBus";
import {DichVuVienThong} from "@/const/enums";

export default {
  name: "ThongTinKhachHang",
  data() {
    return {
      cbHopDong: [],
      cbDichVu: [],
    }
  },
  mounted() {
    this.loadData();
  },
  props: {
    value: {
      type: Object,
      required: true
    }
  },
  computed: {
    customerInfo: {
      get() {
        return this.value;
      },
      set(customerInfo) {
        this.$emit('input', customerInfo);
      }
    }
  },
  methods: {
    async loadData() {
      try {
        this.loading(true)

        let cbHopDong = this.getCbHopDong()
        let cbDichVu = this.getCbDichVu()
        await Promise.all([cbHopDong, cbDichVu]);

        EventBus.$emit('loadedCombobox');

        this.loading(false)
      } catch (err) {
        this.$toast.err(err.message);
      }
    },

    async getCbHopDong() {
      try {
        await API.danhmuc_loai_hd(this.axios, {})
          .then(rs => {
            this.cbHopDong = rs.data.data;
            if (this.cbHopDong.length > 0) {
              let {loai_hd_id} = this.customerInfo;
              this.customerInfo.loai_hd_id = loai_hd_id === 0 ? 3 : loai_hd_id;
            }
          });
      } catch (err) {
        this.$toast.error(err.message);
      }
    },

    async getCbDichVu() {
      try {
        await API.danhmuc_dichvu(this.axios, {})
          .then(rs => {
            this.cbDichVu = rs.data.data;
          });
      } catch (err) {
        this.$toast.error(err.message);
      }
    },

    onEnterSoMayAcc() {
      EventBus.$emit('onEnterSoMayAcc');
    }
  }
}
</script>

<style scoped>

</style>
