<template src="./index.template.html"></template>
<script>
import XLSX from 'xlsx'
import DanDoCapThueBao from '../../tracuu-thuebao-theo-cap/tracuu-dandocap-theothuebao'
export default {
  components: {DanDoCapThueBao},
  name: 'Modal',
  props: {
    modalShow: Boolean,
    doiTuong: Object,
    id: String
  },

  computed: {
  },
  data () {
    return {
      key: 1,
      isShown: false,
      tabThueBao: true,
      show: false,
      checkrdo: 'rdoChinhXac',
      activetab: 1,
      grcDanhSach: [],
      grcLichSu: [],
      isDisableBtn: true,
      dataExcelEx: [],
      kyhieuKC: '',
      CurrentKetCuoi: {},
      modelData: {
        cboMat: [],
        cboViTri: [],
        mMat: 0,
        mVitri: 0
      },
      CurrentThueBao: [],
      ma_thuebao: ''
    }
  },
  watch: {

  },
  methods: {
    onHiddenModal () {
      this.activetab = 1
      this.isShown = false
      this.show = false
      this.key++
      this.dataSearch = []
      this.tabThueBao = false
    },
    async onShownModal () {
      if (this.doiTuong.ketcuoi_id === null || this.doiTuong.ketcuoi_id === '') return
      this.activetab = 1
      this.isShown = true
      this.show = true
      this.tabThueBao = true
      try {
        this.$root.showLoading(true)
        var rs = await this.$root.context.post('/web-cabman/thueBaoCapKetCuoi/layKetCuoiTheoID', {id: this.doiTuong.ketcuoi_id})
        if (rs.error_code !== 'BSS-00000000') {
          this.$root.toastError(rs.message_detail)
          return
        } else if (rs.data == null || rs.data.length == 0) {
          this.$root.toastError('Không tìm thấy thông tin kết cuối')
          return
        }
        this.CurrentKetCuoi = rs.data
        this.kyhieuKC = this.CurrentKetCuoi.KYHIEU
        await this.NAP_DS_MAT()
        this.modelData.mMat = this.doiTuong.mat
        await this.NAP_DS_VITRI()
        this.modelData.mVitri = this.doiTuong.vitri
        await this.TRACUU_DS_THUEBAO()
        this.setDisableBtn(this.grcDanhSach.length > 0)
      } catch (error) {
        this.$root.toastError(error)
      } finally {
        this.$root.showLoading(false)
      }
    },
    onclickTab: function (currentActivetab) {
      this.activetab = currentActivetab
      if (currentActivetab == 1) {
        this.tabThueBao = true
      } else if (currentActivetab == 2) {
        this.tabThueBao = false
      }
    },
    selectedGrcDanhSach: async function (item) {
      this.CurrentThueBao = item
    },
    selectedGrcLichSu: async function () {},
    setDisableBtn: function (isHasData) {
      this.isDisableBtn = !isHasData
    },
    onClickItem: async function () {
    },
    traCuu: async function () {
      await this.TRACUU_DS_THUEBAO()
    },
    changeVitri: async function () {
      await this.TRACUU_DS_THUEBAO()
    },
    changeMat: async function () {
      await this.NAP_DS_VITRI()
      await this.TRACUU_DS_THUEBAO()
    },
    xuatExcel: async function () {
      try {
        this.$root.showLoading(true)
        try {
          var ma_thuebao = XLSX.utils.json_to_sheet(Object.values(this.grcDanhSach))
          var wb = XLSX.utils.book_new() // make Workbook of Excel
          XLSX.utils.book_append_sheet(wb, ma_thuebao, 'DS') // sheetAName is name of Worksheet
          // export Excel file
          XLSX.writeFile(wb, 'export.xlsx')
        } catch (ex) {
          this.$toast.error('Có lỗi trong quá trình xuất file Excel !' + ex)
        }
      } catch (error) {
        this.$root.toastError(error)
      } finally {
        this.$root.showLoading(false)
      }
    },
    KIEMTRA_TRUOCKHI_LUU: function () {
      if (this.CurrentThueBao == null) {
        this.$root.toastWarning('Chưa có thông tin thuê bao')
        return false
      }
      return true
    },
    giaiPhong: async function () {
      try {
        this.$root.showLoading(true)
        if (!this.KIEMTRA_TRUOCKHI_LUU()) return
        this.$bvModal.msgBoxConfirm('Bạn có chắc muốn giải phóng thuê bao khỏi tuyến cáp?',
          {
            title: 'Xác nhận hành động',
            centered: true,
            okTitle: 'Đồng ý',
            cancelTitle: 'Hủy',
            size: 'sm'
          }).then(async (value) => {
          if (value) {
            var vis_thuebao = this.CurrentThueBao.IS_THUEBAO
            var vdb_id = this.CurrentThueBao.DB_ID
            var vdaucuoi_id = this.CurrentThueBao.DAUCUOI_ID
            let data = {
              kieu: vis_thuebao == 1 ? 0 : 1,
              db_id: vdb_id,
              daucuoi_id: vdaucuoi_id,
              checkquyen: 0

            }
            var rs = await this.$root.context.post('/web-cabman/dau-noi-thue-bao-vao-tuyen-cap/giai-phong-thue-bao', data)
            if (!rs.data.result) {
              this.$root.toastError(rs.message_detail)
            }
            await this.TRACUU_DS_THUEBAO()
            this.$root.toastSuccess('Giải phóng thuê bao khỏi tuyến cáp thành công')
          } else {

          }
        })
      } catch (error) {
        this.$root.toastError(error)
      } finally {
        this.$root.showLoading(false)
      }
    },
    TRACUU_DS_THUEBAO: async function () {
      try {
        this.grcDanhSach = []
        this.$root.showLoading(true)
        if (this.CurrentKetCuoi == null || this.CurrentKetCuoi == {}) return

        let data = {
          ketcuoi_id: this.CurrentKetCuoi.KETCUOI_ID,
          mat: this.modelData.mMat,
          vitri: this.modelData.mVitri
        }
        var rs = await this.$root.context.post('/web-cabman/thueBaoCapKetCuoi/dsthuebaoketcuoi', data)
        this.grcDanhSach = rs.data

        if (rs.error_code !== 'BSS-00000000') {
          this.$root.toastError(rs.message_detail)
        }

        await this.TRACUU_LICHSU_TACDONG()
        this.setDisableBtn(this.grcDanhSach.length > 0)
      } catch (error) {
        this.$root.toastError(error)
      } finally {
        this.$root.showLoading(false)
      }
    },
    TRACUU_LICHSU_TACDONG: async function () {
      try {
        this.$root.showLoading(true)
        if (this.CurrentKetCuoi == null || this.CurrentKetCuoi == {}) return
        let data = {
          ketcuoi_id: this.CurrentKetCuoi.KETCUOI_ID,
          mat: this.modelData.mMat,
          vitri: this.modelData.mVitri
        }
        var rs = await this.$root.context.post('/web-cabman/thueBaoCapKetCuoi/dslichsutacdong', data)
        this.grcLichSu = rs.data

        if (rs.error_code !== 'BSS-00000000') {
          this.$root.toastError(rs.message_detail)
        }
      } catch (error) {
        this.$root.toastError(error)
      } finally {
        this.$root.showLoading(false)
      }
    },
    NAP_DS_VITRI: async function () {
      try {
        this.modelData.cboViTri = []
        this.$root.showLoading(true)
        if (this.CurrentKetCuoi == null || this.CurrentKetCuoi == {}) return
        var ketcuoi_id = this.CurrentKetCuoi.KETCUOI_ID
        var mat = this.modelData.mMat
        var rs = await this.$root.context.post(`/web-cabman/thueBaoCapKetCuoi/dsvitriketcuoi?ketcuoiId=${ketcuoi_id}&mat=${mat}`)
        if (rs.error_code !== 'BSS-00000000') {
          this.$root.toastError(rs.message_detail)
        }
        rs.data.unshift({
          VITRI: -1,
          VITRI_STR: '[Tất cả]'
        })
        this.modelData.cboViTri = rs.data
        if (rs.data.length > 0) {
          this.modelData.mVitri = this.modelData.cboViTri[0].VITRI
        } else {
          this.$root.toastError('Không tìm thấy thông tin kết cuối')
        }
      } catch (error) {
        this.$root.toastError(error)
      } finally {
        this.$root.showLoading(false)
      }
    },
    NAP_DS_MAT: async function () {
      try {
        this.$root.showLoading(true)
        this.modelData.cboMat = [
          { ID: 1, DESC: 'Mặt trước' },
          { ID: 2, DESC: 'Mặt sau' }
        ]
        this.modelData.mMat = this.modelData.cboMat[0].ID
      } catch (error) {
        this.$root.toastError(error)
      } finally {
        this.$root.showLoading(false)
      }
    },
    btnDanDo: function (name, args) {
      if (name == 'xoa') {
        this.ma_thuebao = args.MA_TB
        this.$bvModal.show('dandocap')
      }
    }
  },
  async created () {

  }
}
</script>
<style>
a.disabled {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
</style>
