<template src="./template.html"></template>
<script>
import frmTraPhieuTC from "../frmTraPhieuTC.vue"
export default {
  components: {
    frmTraPhieuTC
  },
  mounted() {
    this.load()
  },
  props: [
    "ma_gd",
    "ma_tb",
    "loaihd_id",
    "dichvuvt_id",
    "ngaygiao",
    "ngaytra",
    "kieu",
    "nhanvien_id",
  ],
  methods : {
    load() {
      this.$refs.frmTraPhieuTC.frmTraPhieuTC(this.ma_gd, this.loaihd_id, this.dichvuvt_id, this.nhanvien_id,
                                                        this.ma_tb, "0", "0", this.kieu);
    }
  }
};
</script>
<style>
.ghichu-traphieu .form-control {
  height: 62px !important;
}

.traphieu-body .page-content {
  position: unset;
}

.traphieu-body {
  padding: 0px !important;
}

.noidung-traphieu .form-control {
  height: 140px !important;
}
</style>
