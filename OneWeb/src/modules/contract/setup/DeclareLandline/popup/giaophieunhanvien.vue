<template>
    <frnGiaoPhieuNV @giaoviec_success="giaoviec_success" ref="frnGiaoPhieuNV" />
</template>
<script>

import frnGiaoPhieuNV from "../../DeclareLandline/popups/frnGiaoPhieuNV.vue";
export default {
  components: {
    frnGiaoPhieuNV
  },
  props: [
    "phieu_id",
    "nguoigv_id",
    "ngaygiao",
    "ma_tb",
    "hdtb_id",
    "huonggiao_id",
    "dsphieuGV",
  ],
  methods: {
    init() {
      this.$nextTick(() => {
        this.$refs.frnGiaoPhieuNV.frnGiaoPhieuNV(this.dsphieuGV ? this.dsphieuGV : [this.phieu_id], this.$root.token.getNhanVienID(), this.nguoigv_id, this.ngaygiao);
        if(this.ma_tb)
          this.$refs.frnGiaoPhieuNV.vma_tb = this.ma_tb;
        if(this.hdtb_id)
          this.$refs.frnGiaoPhieuNV.vhdtb_id = this.hdtb_id;
        if(this.huonggiao_id)
          this.$refs.frnGiaoPhieuNV.huonggiao_id = this.huonggiao_id;
        this.$refs.frnGiaoPhieuNV.load()
      })
    },
    giaoviec_success() {
      this.$emit("giaoviec_success", true)
    }
  }
};
</script>
<style>
.e-edit-dialog .e-gridform .e-table {
  border-spacing: 0px;
}

</style>
