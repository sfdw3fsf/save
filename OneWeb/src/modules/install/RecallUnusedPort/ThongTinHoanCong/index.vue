<template src="./index.html">

</template>

<script>
import API from "../API";
import moment from "moment";
import EventBus from "../../../../utils/eventBus";

export default {
  name: "ThongTinHoanCong",
  data() {
    return {
      dateFormat: 'DD/MM/YYYY hh:mm:ss',
      dateDefault: moment().format(this.dateFormat),
      cbNguoiGiaoViec: [],
      cbKieuLapDat: [],
    };
  },
  props: {
    value: {
      type: Object,
      required: true
    },
    dsNguoiGiaoViec: {
      type: Array
    },
    dsKieuLD: {
      type: Array
    },
  },
  computed: {
    hoanCongInfo: {
      get() {
        return this.value;
      },
      set(hoanCongInfo) {
        this.$emit('input', hoanCongInfo);
      }
    }
  },
  mounted() {
    EventBus.$on('clear', () => this.clear());
  },
  destroyed() {
    EventBus.$off('clear');
  },
  methods: {
    clear() {
      this.hoanCongInfo.chk_ngay_gv = this.hoanCongInfo.chk_ngay_th = false;
      this.hoanCongInfo.ngay_giao_viec = this.hoanCongInfo.ngay_hoan_thanh = this.dateDefault;
    },

    btnNDTH() {
      EventBus.$emit('updateNDTH', this.hoanCongInfo.txtNoiDungTH);
    }
  }
}
</script>

<style scoped>

</style>
