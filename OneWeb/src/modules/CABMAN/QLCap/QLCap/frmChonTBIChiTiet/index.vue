<template src="./index.html">
</template>
<script>
export default {
  name: "ChonTBIChiTiet",
  props: {
    modalId: {
      type: String,
    },
    maDuAn: {
      type: String,
      default: ''
    }
  },
  data: function () {
    return {
      grcDanhSach: [],
      selected: null,
      txtMaDuAn: '',
      enabledChon: true,
      cboMaHD: null,
      dsMaHD: [],
      chkLocTheoDA: false,
      visibleFilter: true,
      enabledTxtMaDuAn: true,
      enabledCboMaHD: true,
    }
  },
  computed: {
    MaDuAn() {
      return this.chkLocTheoDA ? this.txtMaDuAn : ""
    },
    MaHD() {
      return this.chkLocTheoDA ? this.cboMaHD : ""
    }
  },
  methods: {
    focusMyElement() {
      this.frmChonTBIChiTiet(this.maDuAn)
      if (this.chkLocTheoDA) {
        this.LAY_DS_MA_HD()
      }
      this.btnTraCuu_Click()
    },
    hiddenModal() {
      this.$bvModal.hide(this.modalId)
      this.grcDanhSach = []
      this.txtMaDuAn = ''
    },
    frmChonTBIChiTiet(MaDuAn) {
      this.txtMaDuAn = MaDuAn ? MaDuAn.trim() :''
      this.chkLocTheoDA = !(MaDuAn == null || MaDuAn == '')
      this.visibleFilter = this.chkLocTheoDA
    },
    btnTraCuu_Click() {
      this.LAY_DS_TBI_CHITIET()
    },
    async LAY_DS_MA_HD() {
      try {
        let msg = ""
        this.$root.loading(true)
        let rs = await this.Get_ContractInfo_byProjectCode_Station(this.MaDuAn, "")
        if (rs && rs.message) {
          this.dsMaHD = []
          this.$root.toastError(rs.message)
          return
        }
        if (rs && rs.data && rs.data.length > 0) {
          this.dsMaHD = rs.data
        }
      } catch (e) {

      } finally {
        this.$root.loading(false)
      }
    },
    async LAY_DS_TBI_CHITIET() {
      this.enabledChon = false
      this.grcDanhSach = []
      try {
        this.$root.loading(true)
        if (this.MaHD) {
          return
        }
        let res = await this.$root.context.get("web-cabman/mang-truyen-dan/lay-ds-tbi-chitiet", {
          maHD: this.MaHD
        })
        if (res && res.data) {
          this.grcDanhSach = res.data
        }
      }catch (e) {

      }finally {
        this.$root.loading(false)
        this.enabledChon = this.grcDanhSach != null && this.grcDanhSach.length > 0
      }
    },
    chkLocTheoDA_CheckedChanged() {
      this.enabledTxtMaDuAn = this.chkLocTheoDA
      this.enabledCboMaHD = this.chkLocTheoDA
      this.btnTraCuu_Click()
    },

    async Get_ContractInfo_byProjectCode_Station(sProjectCode, sStation) {
      return await this.$root.context.get("tichhop/qldt/get_ContractInfo_byProjectCode_Station", {
        sProjectCode: sProjectCode,
        sStation: sStation
      })
    },
    grcDanhSach_selectedRowChanged(args) {
      this.selected = args
    },
    chonMa() {
      if (this.selected) {
        this.$emit('xacnhan', this.selected.ma_goi_thau)
        this.hiddenModal()
      }
    }
  }
}
</script>

<style scoped>

</style>
