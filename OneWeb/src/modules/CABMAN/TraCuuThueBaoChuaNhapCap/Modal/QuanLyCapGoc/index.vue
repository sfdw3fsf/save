<template src="./index.template.html"></template>
<script>

import { required, minValue, maxValue, maxLength, between } from 'vuelidate/lib/validators'
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
export default {
  components: {BssErrorMarker },
  name: 'ModalQuanLyCapGoc',
  props: {
    idModal: null
  },
  validations: {
    selectedCapGocQL:
    {
      tudoi: {
        required,
        maxValue: maxValue(10000),
        minValue: minValue(1)
      },
      dendoi: {
        required,
        maxValue: maxValue(10000),
        minValue: minValue(1)
      }
    }

  },
  computed: {
    message () {
      return 'Lịch sử thay đổi cáp của thuê bao: ' + this.thuebao.TEN_TB
    },
    getDenDoi () {
      return this.selectedCapGocQL.dendoi ? this.selectedCapGocQL.dendoi : 0
    }
  },
  async mounted () {
    try {
      // console.log('this.$root.token.getNhanVienID()', this.$root.token.getNhanVienID())
      // var rs = await this.$root.context.get(
      //   '/web-cabman/ban-do-mang-cap/danh-muc', {loai: 'DONVI_DL_ID', option: this.$root.token.getNhanVienID()}
      // )
      // this.DONVI_DL_ID = rs.data[0].DONVI_DL_ID
    } catch (error) {
      console.log('error', error)
    }
  },
  data () {
    return {
      DONVI_DL_ID: null,
      resolvePromise: null,
      rejectPromise: null,
      dsTTVT: [],
      dsTOQL: [],
      dsTRAM: [],
      dsNV: [],
      dsCapGocQL: [],
      dsCapGoc: [],
      selected: {
        DONVI_ID: null,
        TOQL_ID: null,
        TRAM_ID: null,
        NHANVIEN_ID: null
      },
      loading: null,
      indexFocus: 0,
      cap_id: null,
      selectedCapGocQL: {
        tudoi: null,
        dendoi: null,
        nhanvien_id: null
      },
      option: {
        them: false,
        ghilai: false,
        huy: false,
        xoa: false,
        txtTuDoi: false,
        txtDenDoi: false
      }
    }
  },
  methods: {
    setButton (kieu) {
      this.option.them = false
      this.option.ghilai = false
      this.option.huy = false
      this.option.xoa = false
      this.option.txtTuDoi = false
      this.option.txtDenDoi = false
      if (kieu === -1) {
        this.option.ghilai = false
        this.option.huy = false
      }
      if (kieu === 0) {
        this.option.them = false
        this.option.ghilai = true
        this.option.huy = true
        this.option.txtTuDoi = true
        this.option.txtDenDoi = true
      }
      if (kieu === 1) {
        this.option.them = true
        this.option.txtTuDoi = false
        this.option.txtDenDoi = false
        this.option.ghilai = false
        this.option.huy = false
      }
    },
    async KiemTra_DuLieu () {
      this.$v.$touch()
      if (this.$v.$invalid && !this.$v.selectedCapGocQL.tudoi.required) {
        this.$root.$toast.error('Bạn phải nhập đầy đủ dữ liệu !')
        return false
      }
      if (this.$v.$invalid && !this.$v.selectedCapGocQL.dendoi.required) {
        this.$root.$toast.error('Bạn phải nhập đầy đủ dữ liệu !')
        return false
      }
      if (this.selectedCapGocQL.tudoi > this.selectedCapGocQL.dendoi) {
        console.log(this.selectedCapGocQL.tudoi, this.selectedCapGocQL.dendoi)
        this.$root.$toast.error('Khoảng đôi cáp bạn nhập không hợp lệ !')
        return false
      }
      console.log('KiemTra_DuLieu', this.cap_id, this.selectedCapGocQL.tudoi, this.selectedCapGocQL.dendoi)
      var rs = await this.$root.context.post(
        '/web-cabman/ban-do-mang-cap/execute1', {P_PHANVUNG_ID: this.$root.token.getPhanVungID(), P_CAPGOC_ID: this.cap_id, P_TUDOI: this.selectedCapGocQL.tudoi, P_DENDOI: this.selectedCapGocQL.dendoi, PROCEDURE_NAME: 'CABMAN.PKG_GIANDO_CAP.SP_KT_TUDOI_DENDOI'}
      )
      if (rs.data !== null) {
        console.log('KiemTra_DuLieu', rs.data)
        if (rs.data.length > 0) {
          if (rs.data[0].RESULT === 0) {
            this.$root.$toast.error('Khoảng đôi cáp bạn chọn chứa đôi cáp đã được phân, đề nghị chọn lại khoảng cáp !')
            return false
          }
        }
      }
      return true
    },
    async CapNhat_QL_Capgoc () {
      var data = {P_PHANVUNG_ID: this.$root.token.getPhanVungID(), P_CAP_ID: this.cap_id, P_TUDOI: this.selectedCapGocQL.tudoi, P_DENDOI: this.selectedCapGocQL.dendoi, P_NHANVIEN_ID: this.selected.NHANVIEN_ID, PROCEDURE_NAME: 'CABMAN.PKG_GIANDO_CAP.SP_CN_QL_CAPGOC'}
      console.log('ThemMoi_QL_Capgoc', data)
      var rs = await this.$root.context.post(
        '/web-cabman/ban-do-mang-cap/execute1', data
      )
      if (rs.data !== null && rs.data.length > 0 && rs.data[0].RESULT > 0) {
        return true
      } else {
        return false
      }
    },
    async ThemMoi_QL_Capgoc () {
      var data = {P_PHANVUNG_ID: this.$root.token.getPhanVungID(), P_CAP_ID: this.cap_id, P_TUDOI: this.selectedCapGocQL.tudoi, P_DENDOI: this.selectedCapGocQL.dendoi, P_NHANVIEN_ID: this.selected.NHANVIEN_ID, PROCEDURE_NAME: 'CABMAN.PKG_GIANDO_CAP.SP_CN_QL_CAPGOC'}
      console.log('ThemMoi_QL_Capgoc', data)
      var rs = await this.$root.context.post(
        '/web-cabman/ban-do-mang-cap/execute1', data
      )
      if (rs.data !== null && rs.data.length > 0 && rs.data[0].result === 1) {
        return true
      } else {
        return false
      }
    },
    async Xoa_QL_Capgoc () {
      var data = {P_PHANVUNG_ID: this.$root.token.getPhanVungID(), P_CAP_ID: this.cap_id, P_TUDOI: this.selectedCapGocQL.tudoi, P_DENDOI: this.selectedCapGocQL.dendoi, PROCEDURE_NAME: 'CABMAN.PKG_GIANDO_CAP.SP_XOA_QL_CAPGOC'}
      console.log('ThemMoi_QL_Capgoc', data)
      var rs = await this.$root.context.post(
        '/web-cabman/ban-do-mang-cap/execute1', data
      )
      if (rs.data !== null && rs.data.length > 0 && rs.data[0].RESULT === 1) {
        return true
      } else {
        return false
      }
    },
    async ghi () {
      if (!this.option.ghilai) return false
      if (this.option.them === false) {
        if (await this.KiemTra_DuLieu() === false) return false
        var result1 = await this.CapNhat_QL_Capgoc()
        if (result1) {
          this.$root.$toast.success('Thêm phân quản lý cáp gốc cho nhân viên thành công.')
        }
      } else {
        var result2 = await this.CapNhat_QL_Capgoc()
        if (result2) {
          this.$root.$toast.success('Cập nhật phân quản lý cáp gốc cho nhân viên thành công.')
        }
      }
      this.setButton(1)
      this.LAY_DS_DOICAPGOC_DAGIAO_QL()
    },
    nhapmoi () {
      if (!this.option.them) return false
      this.setButton(0)
      this.selectedCapGocQL.tudoi = null
      this.selectedCapGocQL.dendoi = null
      this.$v.$reset()
    },
    huy () {
      if (!this.option.huy) return false
      this.$v.$reset()
      this.setButton(1)
    },
    async xoa () {
      if (!this.option.xoa) return false
      this.$confirm(`Bạn thật sự muốn xóa dữ liệu không ?`,
        'Thông báo',
        {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không',
          type: 'warning'
        }).then(() => {
        if (this.Xoa_QL_Capgoc()) {
          this.$root.$toast.success('Đã hủy phân đôi cáp cho nhân viên thành công !')
          this.setButton(1)
          this.LAY_DS_DOICAPGOC_DAGIAO_QL()
        } else {

        }
      }).catch((e) => {})
    },
    gridCapGocQL_selectedRowChanged (data) {
      console.log('gridCapGocQL_selectedRowChanged', data)

      if (data) {
        this.selectedCapGocQL.tudoi = data.TUDOI
        this.selectedCapGocQL.dendoi = data.DENDOI
        this.selectedCapGocQL.nhanvien_id = data.NHANVIEN_ID
        this.option.them = true
        this.option.ghilai = true
        this.option.huy = true
        this.option.xoa = true
      } else {

      }
    },
    async LAY_DS_DOICAPGOC_DAGIAO_QL () {
      if (this.cap_id === null) return false
      console.log('LAY_DS_DOICAPGOC_DAGIAO_QL', this.cap_id)
      var rs = await this.$root.context.post(
        '/web-cabman/ban-do-mang-cap/execute1', {P_PHANVUNG_ID: this.$root.token.getPhanVungID(), P_CAPGOC_ID: this.cap_id, PROCEDURE_NAME: 'CABMAN.PKG_GIANDO_CAP.SP_LAY_DS_DOICAPGOC_DAGIAO_QL'}
      )
      if (rs.data !== null) {
        console.log('LAY_DS_DOICAPGOC_DAGIAO_QL', rs.data)
        this.dsCapGocQL = rs.data
      }
    },
    gridCapGoc_selectedRowChanged (data) {
      console.log('gridCapGoc_selectedRowChanged', data)
      if (data !== null) {
        this.cap_id = data.CAP_ID
        this.LAY_DS_DOICAPGOC_DAGIAO_QL()
        this.setButton(1)
      } else {
        this.cap_id = null
        this.dsCapGocQL = []
        this.setButton(-1)
      }
    },
    async shownModal () {
      try {
        this.$v.$reset()
        this.loading = true

        var rs = await this.$root.context.get(
          '/web-cabman/ban-do-mang-cap/danh-muc', {loai: 'DONVI_DL_ID', option: this.$root.token.getNhanVienID()}
        )
        this.DONVI_DL_ID = rs.data[0].DONVI_DL_ID

        rs = await this.$root.context.post(
          '/web-cabman/ban-do-mang-cap/execute1', {P_PHANVUNG_ID: this.$root.token.getPhanVungID(), P_LOAIDV_ID: 3, PROCEDURE_NAME: 'CABMAN.PKG_GIANDO_CAP.SP_LAY_DS_TTVT_THEO_LOAI'}
        )
        if (rs.data !== null) {
          this.dsTTVT = rs.data
          if (this.dsTTVT.length > 0) {
            if (this.DONVI_DL_ID === 0) {
              this.selected.DONVI_ID = this.dsTTVT[0].DONVI_ID
            } else {
              this.selected.DONVI_ID = this.DONVI_DL_ID
            }
          }
        }
        this.loading = false
      } catch (error) {
        console.log('shownModal', error)
        this.loading = false
      }
    },
    async layDsToQL (DONVI_ID) {
      try {
        this.loading = true
        this.dsTOQL = []
        this.selected.TOQL_ID = null
        if (DONVI_ID === null) return false
        console.log('layDsToQL', DONVI_ID)
        var rs = await this.$root.context.post(
          '/web-cabman/ban-do-mang-cap/execute1', {P_PHANVUNG_ID: this.$root.token.getPhanVungID(), P_DONVI_ID: DONVI_ID, P_LOAIDV_ID: 5, PROCEDURE_NAME: 'CABMAN.PKG_DAUNOI_TB.LAY_DS_DONVICON_LOAIDV'}
        )
        if (rs.data !== null) {
          this.dsTOQL = rs.data
          if (this.dsTOQL.length > 0) {
            this.selected.TOQL_ID = this.dsTOQL[0].DONVI_ID
          }
        }
        this.loading = false
      } catch (error) {
        this.loading = false
        console.log('layDsToQL', error)
      }
    },
    async layDsTram (TOQL_ID) {
      try {
        this.loading = true
        this.dsTRAM = []
        this.selected.TRAM_ID = null
        if (TOQL_ID === null) return false
        console.log('layDsToQL', TOQL_ID)
        var rs = await this.$root.context.post(
          '/web-cabman/ban-do-mang-cap/execute1', {P_PHANVUNG_ID: this.$root.token.getPhanVungID(), P_DONVI_ID: TOQL_ID, P_LOAIDV_ID: '39,25', PROCEDURE_NAME: 'CABMAN.PKG_DAUNOI_TB.LAY_DS_DONVICON_LOAIDV'}
        )
        if (rs.data !== null) {
          this.dsTRAM = rs.data
          if (this.dsTRAM.length > 0) {
            this.selected.TRAM_ID = this.dsTRAM[0].DONVI_ID
          }
        }
        this.loading = false
      } catch (error) {
        console.log('layDsTram', error)
        this.loading = false
      }
    },
    async layDsNhanVienQL (TOQL_ID) {
      try {
        this.loading = true
        this.dsNV = []
        this.selected.NHANVIEN_ID = null
        if (TOQL_ID === null) return false
        console.log('layDsNhanVienQL', TOQL_ID)
        var rs = await this.$root.context.post(
          '/web-cabman/ban-do-mang-cap/execute1', {P_PHANVUNG_ID: this.$root.token.getPhanVungID(), P_DONVI_ID: TOQL_ID, P_LOAINV_ID: 6, PROCEDURE_NAME: 'CABMAN.PKG_DAUNOI_TB.LAY_DS_NHANVIEN_QUANLY'}
        )
        if (rs.data !== null) {
          this.dsNV = rs.data
          if (this.dsNV.length > 0) {
            this.selected.NHANVIEN_ID = this.dsNV[0].NHANVIEN_ID
          }
        }
        this.loading = false
      } catch (error) {
        this.loading = false
        console.log('layDsNhanVienQL', error)
      }
    },

    async layDsCapGoc (TRAM_ID) {
      this.dsCapGoc = []
      this.dsCapGocQL = []
      if (TRAM_ID === null) return false
      console.log('layDsCapGoc', TRAM_ID)
      var rs = await this.$root.context.post(
        '/web-cabman/ban-do-mang-cap/execute1', {P_PHANVUNG_ID: this.$root.token.getPhanVungID(), P_TONGDAI_ID: TRAM_ID, PROCEDURE_NAME: 'CABMAN.PKG_DAUNOI_TB.LAY_DS_CAPGOC_THEO_TONGDAI'}
      )
      if (rs.data !== null) {
        this.dsCapGoc = rs.data
        if (this.dsCapGoc.length > 0) {
          this.setButton(1)
        } else { this.setButton(-1) }
        console.log('this.dsCapGoc', this.dsCapGoc)
      }
    },

    hiddenModal () {
      this.setButton(-1)
      this.dsTTVT = []
      this.dsTOQL = []
      this.dsTRAM = []
      this.dsNV = []
      this.dsCapGoc = []
      this.dsCapGocQL = []
      this.option = {
        them: false,
        ghilai: false,
        huy: false,
        xoa: false,
        txtTuDoi: false,
        txtDenDoi: false
      }
      this.selected.DONVI_ID = null
      this.$v.$reset()
    },
    show () {
      return new Promise((resolve, reject) => {
        this.$bvModal.show(this.idModal)
        this.resolvePromise = resolve
        this.rejectPromise = reject
      })
    },
    closeModal: function () {
      this.resolvePromise({ok: false, data: null})
      this.$bvModal.hide(this.idModal)
    },
    getLichSuCap: async function () {
      try {
        this.$root.showLoading(true)
        var rs = await this.$root.context.post(
          '/web-cabman/ban-do-mang-cap/execute1', {P_PHANVUNG_ID: this.$root.token.getPhanVungID(), P_THUEBAO_ID: this.thuebao.THUEBAO_ID, PROCEDURE_NAME: 'CABMAN.PKG_DAUNOI_TB.SP_LISHSU_CAP'}
        )
        console.log('rs.data', rs.data)
        this.lichsucap = rs.data
      } catch (error) {
        console.log('getLichSuCap', error)
        this.$root.showLoading(false)
      } finally {
        this.$root.showLoading(false)
      }
    }
  },
  watch: {
    'selected.DONVI_ID' (val) {
      if (val === null) return
      this.layDsToQL(val)
    },
    'selected.TOQL_ID' (val) {
      if (val === null) return
      this.layDsTram(val)
      this.layDsNhanVienQL(val)
    },
    'selected.TRAM_ID' (val) {
      if (val === null) return
      this.setButton(-1)
      this.layDsCapGoc(val)
    },
    loading (val) {
      this.$root.showLoading(val)
    }
  },
  created () {

  },
  destroyed () {
  }
}
</script>
<style scoped src="./index.scss">
</style>
