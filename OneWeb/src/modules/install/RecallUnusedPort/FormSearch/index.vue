<template src="./index.html"></template>

<script>
import ThongTinKhachHang from "../ThongTinKhachHang";
import ThongTinHoanCong from "../ThongTinHoanCong";
import DSNVThucHien from "../DSNVThucHien";
import DSThietBi from "../DSThietBi";
import HostInfo from "../HostInfo";
export default {
  components: {
    ThongTinKhachHang,
    ThongTinHoanCong,
    DSNVThucHien,
    DSThietBi,
    HostInfo
  },
  name: "FormSearch",
  props: {
    value: {
      type: Object,
      required: true
    },
    dsNhanVien: {
      type: Array,
    },
    dsThietBi: {
      type: Array,
    },
    hostInfo: {
      type: Object,
    },
    dsNguoiGiaoViec: {
      type: Array,
    },
    dsKieuLD: {
      type: Array
    },
  },
  computed: {
    frmData: {
      get() {
        return this.value;
      },
      set(frmData) {
        this.$emit('input', frmData);
      }
    }
  }
}
</script>

<style scoped>

</style>
