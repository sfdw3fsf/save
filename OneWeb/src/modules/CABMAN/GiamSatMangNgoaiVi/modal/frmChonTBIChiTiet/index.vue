<template src="./index.html">
</template>
<script>
export default {
  name: 'ChonTBIChiTiet',
  props: {
    modalId: {
      type: String
    },
    maDuAn: {
      type: String,
      default: ''
    },
    idDuAn: {
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
      cboMaHD: '',
      dsMaHD: [],
      chkLocTheoDA: false,
      visibleFilter: true,
      enabledTxtMaDuAn: true,
      enabledCboMaHD: true
    }
  },
  computed: {
    MaDuAn () {
      return this.chkLocTheoDA ? this.txtMaDuAn : ''
    },
    MaHD () {
      return this.chkLocTheoDA ? this.cboMaHD : ''
    }
  },
  methods: {
    async focusMyElement () {
      this.frmChonTBIChiTiet(this.maDuAn)
      if (this.chkLocTheoDA) {
        this.LAY_DS_MA_HD()
      }
      this.btnTraCuu_Click()
    },
    hiddenModal () {
      this.$bvModal.hide(this.modalId)
      this.grcDanhSach = []
      this.txtMaDuAn = ''
    },
    frmChonTBIChiTiet (MaDuAn) {
      this.txtMaDuAn = MaDuAn ? MaDuAn.trim() : ''
      this.chkLocTheoDA = !(MaDuAn == null || MaDuAn == '')
      this.visibleFilter = this.chkLocTheoDA
    },
    btnTraCuu_Click () {
      this.LAY_DS_TBI_CHITIET()
    },

    async LAY_DS_MA_HD () {
      try {
        this.$root.loading(true)
        let rs = await this.Get_ContractInfo_byProjectCode_Station(this.MaDuAn, '')
        console.log('🚀 ~ file: index.vue:62 ~ LAY_DS_MA_HD ~ rs:', rs)
        if (rs && rs.message) {
          this.dsMaHD = []
          this.$root.toastError(rs.message)
          return
        }
        if (rs && rs.data && rs.data.length > 0) {
          this.dsMaHD = rs.data
        }
      } catch (e) {
        console.log('🚀 ~ file: index.vue:71 ~ LAY_DS_MA_HD ~ e:', e)
      } finally {
        this.$root.loading(false)
      }
    },
    async LAY_DS_TBI_CHITIET () {
      this.enabledChon = false
      this.grcDanhSach = []
      try {
        this.$root.loading(true)
        if (this.MaHD) {
          return
        }
        let res = await this.$root.context.get('web-cabman/mang-truyen-dan/lay-ds-tbi-chitiet', {
          maHD: this.MaHD
        })
        if (res && res.data) {
          this.grcDanhSach = res.data
        }
      } catch (e) {

      } finally {
        this.$root.loading(false)
        this.enabledChon = this.grcDanhSach != null && this.grcDanhSach.length > 0
      }
    },
    chkLocTheoDA_CheckedChanged () {
      this.enabledTxtMaDuAn = this.chkLocTheoDA
      this.enabledCboMaHD = this.chkLocTheoDA
      this.btnTraCuu_Click()
    },

    async Get_ContractInfo_byProjectCode_Station (sProjectCode, sStation) {
      try {
        let rs = await this.$root.context.get('tichhop/qldt/get_ContractInfo_byProjectCode_Station', {
          sProjectCode: sProjectCode,
          sStation: sStation
        })
        console.log('🚀 ~ file: index.vue:109 ~ Get_ContractInfo_byProjectCode_Station ~ rs:', rs)
        return rs
      } catch (error) {
        console.log('🚀 ~ file: index.vue:111 ~ Get_ContractInfo_byProjectCode_Station ~ error:', error)
      }
    },
    grcDanhSach_selectedRowChanged (args) {
      console.log('🚀 ~ file: index.vue:110 ~ grcDanhSach_selectedRowChanged ~ args:', args)
      this.selected = args
    },
    chonMa () {
      if (this.selected) {
        console.log('🚀 ~ file: index.vue:116 ~ chonMa ~ this.selected.ID_CHITIET:', this.selected.ID_CHITIET)
        this.$emit('xacnhan', this.selected.ID_CHITIET)
        this.hiddenModal()
      }
    }
  }
}
</script>
<style scoped>
</style>
