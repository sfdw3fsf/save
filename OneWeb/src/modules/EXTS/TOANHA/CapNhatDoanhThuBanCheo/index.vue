<template src="./index.html"></template>
<script>
import XLSX from 'xlsx'
import frmChonTinhBanCheo from './modal/frmChonTinh.vue'

export default {
  name: 'CapNhatDoanhThuBanCheo',
  components: {
    frmChonTinhBanCheo
  },
  data: function () {
    return {
      isShowChonTinh: false,
      tinh: null,
      listTinh: [],
      ok: false,
      waterMark: 'Chọn ngày',
      dateVal: new Date(),
      dateFormat: 'MM/yyyy',

      chkThangChot: false,
      dtpKyHD: new Date(),

      fileExcel: null,
      duongDan: '',

      dgvDoanhThu: [],
      dgvDanhSach: [],
      dsImportExcel: [],
      vds: [],
      vds_upload: [],

      tsbtnXem: true,
      tsbtnChapNhan: true,
      tsbtnHuy: true,
      tsbtnMauEx: true,
      btnXuatExcel: true,
      TrangThaiTemplate: function () {
        return {
          template: {
            template: `
              <div style="text-align: center;">
                <span class="one-alert text-warning" v-if="data.trangthai != '-1'"></span>
              </div>`,
            data() {
              return {
                data: {}
              }
            }
          }
        }
      }
    }
  },
  async created() {
    this.tsbtnChapNhan = false
    this.dtpKyHD.setMonth(this.dtpKyHD.getMonth() - 1)
    this.chkThangChot = true
    await this.ShowData()
    await this.onLoad()
  },
  methods: {
    async onLoad() {
      this.axios.post('web-hopdong/bss_group1/lay_ds_tinh_thicong_combobox', {}).then((rs) => {
        this.listTinh = rs.data.data
        if (this.listTinh.length > 0) {
          this.tinh = this.listTinh[0]['tinh_id']
        }
      })
    },
    duongDan_click: function (args) {
      this.$refs.fileInput.click()
    },
    async onChangeFile(e) {
      const files = e.target.files
      if (!files.length) {
        return
      } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
        return this.$root.toastError('File excel không đúng định dạng!')
      }
      this.fileExcel = e.target.files[0]
      this.duongDan = this.fileExcel.name
      //this.tsbtnChapNhan = false
      try {
        this.$root.showLoading(true)
        await this.txtDuongDan_ButtonClick()
      } finally {
        this.$root.showLoading(false)
      }
    },
    async tsbtnXem_Click() {
      if (this.tsbtnXem) await this.ShowData()
    },
    tsbtnHuy_Click() {
      if (!this.tsbtnHuy) return
      this.tsbtnChapNhan = false
      this.dgvDanhSach = []
      this.fileExcel = null
      this.vds_upload = []
    },
    tsbtnMauEx_Click() {
      if (!this.tsbtnMauEx) return
      try {
        this.loading(true)
        let dt = [
          {
            CHUKY: '20220101',
            MA_TB: 'LL000394635',
            MA_TOANHA: 'TĐ1901100',
            DOANHTHU: '100000',
            CHIPHI: ''
          },
          {
            CHUKY: '20220101',
            MA_TB: 'MW000394020',
            MA_TOANHA: 'NS1200100',
            DOANHTHU: '',
            CHIPHI: 20000
          }
        ]
        let fileMau = XLSX.utils.json_to_sheet(dt)
        let wb = XLSX.utils.book_new() // make Workbook of Excel
        XLSX.utils.book_append_sheet(wb, fileMau, 'DOANHTHU_BANCHEO_DT_TEMP') // sheetAName is name of Worksheet
        // export Excel file
        XLSX.writeFile(wb, 'DOANHTHU_BANCHEO_DT_TEMP.xls')
      } catch (e) {
        this.$root.toastError('Có lỗi khi xuất excel mẫu ' + e.toString())
      } finally {
        this.loading(false)
      }
    },
    btnXuatExcel_Click() {
      if (!this.btnXuatExcel) return
      let exportProperties = {
        dataSource: this.dgvDoanhThu,
        fileName: 'doanhthu_chiphi_thuebao_bancheo.xlsx'
      }
      this.$refs.gridDgvDoanhThu.excelExport(exportProperties)
    },
    async btnDongBoDL() {
      if (!this.tsbtnChapNhan) return
      let msg = ''
      if (this.isShowChonTinh) {
        msg = `Bạn có chắc chắn muốn đồng bộ dữ liệu cho ${this.listTinh.find((i) => i.tinh_id == this.tinh).tentinh} không?`
      } else {
        msg = 'Bạn có chắc chắn muốn đồng bộ dữ liệu không?'
      }
      this.$confirm(msg, 'Thông báo', {
        confirmButtonText: 'Đồng bộ',
        cancelButtonText: 'Hủy',
        type: 'warning'
      })
        .then(async () => {
          this.tsbtnChapNhan_Click()
        })
        .catch(() => {
          this.$root.toastInfo('Đã hủy đồng bộ dữ liệu!')
        })
    },
    async tsbtnChapNhan_Click() {
      if (!this.tsbtnChapNhan) return
      if (this.isShowChonTinh) {
        await this.$root.context
          .post('web-toanha/capnhat-doanhthu-bc/fn_kiemtra_quyen_capnhat_doanhthu_bc', {
            kieu: 2
          })
          .then(async (rs) => {
            if (rs.data != 1) {
              this.$root.toastError('Bạn không có quyền cập nhật dữ liệu!')
              return
            } else {
              await this.$root.context
                .post('web-toanha/capnhat-doanhthu-bc/fn_kiemtra_quyen_capnhat_doanhthu_bc', {
                  kieu: 3,
                  vds : this.vds
                })
                .then(async (rs) => {
                  if (rs.data != 1) {
                    this.$root.toastError('Bạn không có quyền cập nhật dữ liệu!')
                    return
                  } else {
                    try {
                      this.loading(true)
                      let ip = this.$auth.getUserName()
                      let kq = await this.fn_capnhat_doanhthu_bc_dt(this.vds, ip)
                      if (kq != 'OK') {
                        this.$root.toastError('Có lỗi: ' + kq)
                        return
                      }
                      this.$root.toastSuccess('Cập nhật dữ liệu thành công!')
                      await this.ShowData()
                      this.tsbtnChapNhan = false
                    } catch (e) {
                    } finally {
                      this.loading(false)
                    }
                  }
                })
            }
          })
      } else {
        try {
          this.loading(true)
          let ip = this.$auth.getUserName()
          let kq = await this.fn_capnhat_doanhthu_bc_dt(this.vds, ip)
          if (kq != 'OK') {
            this.$root.toastError('Có lỗi: ' + kq)
            return
          }
          this.$root.toastSuccess('Cập nhật dữ liệu thành công!')
          await this.ShowData()
          this.tsbtnChapNhan = false
        } catch (e) {
        } finally {
          this.loading(false)
        }
      }
    },
    async txtDuongDan_ButtonClick() {
      this.tsbtnChapNhan = false
      let tencot
      try {
        this.loading(true)
        if (this.checkfile()) {
          let s = this.fileExcel.name.toLowerCase()
          if (s.endsWith('.xlsx') || s.endsWith('.xls')) {
            this.dgvDanhSach = []
            let reader = new FileReader()
            reader.onload = async (e) => {
              /* Parse data */
              const data = e.target.result
              const workBook = XLSX.read(data, { type: 'binary' })
              if (workBook != null) {
                for (let i = 0; i < workBook.SheetNames.length; i++) {
                  const sheetName = workBook.SheetNames[i]
                  switch (sheetName.toUpperCase()) {
                    case 'DOANHTHU_BANCHEO_DT_TEMP':
                      this.dsImportExcel = XLSX.utils.sheet_to_json(workBook.Sheets[sheetName], { header: 'A' })
                      break
                    default:
                      break
                  }
                }
                if (!(this.dsImportExcel[0] == null || this.dsImportExcel[0] == undefined)) {
                  tencot = Object.values(this.dsImportExcel[0])
                  if (this.kiemTraDuLieu(tencot)) {
                    for (let i = 1; i < this.dsImportExcel.length; i++) {
                      let item = this.convertToDoanhThuItem(this.dsImportExcel[i])
                      this.vds_upload.push(item)
                      if (this.vds_upload.length === 200) break
                    }
                    // Kiểm tra Quản lý kết cuối
                    let dtquyen = await this.KT_CAPNHAT_DOANHTHU_BC_DT(this.vds_upload)
                    this.dgvDanhSach = dtquyen
                    let dtChuanHoa = dtquyen.filter((i) => i.trangthai == '0')
                    if (dtChuanHoa.length > 0) {
                      this.$root.toastError('Vui lòng kiểm tra lại dữ liệu')
                      return
                    }
                    if (this.dgvDanhSach.length <= 0) {
                      this.$root.toastError('Không có dữ liệu!')
                      return
                    }
                    this.vds = dtquyen
                    this.tsbtnChapNhan = true
                  }
                } else {
                  this.$root.toastError('File excel không có dữ liệu hợp lệ!')
                }
              }
            }
            reader.readAsBinaryString(this.fileExcel)
          }
        }
      } catch (e) {
        console.log(e)
      } finally {
        this.loading(false)
      }
    },
    convertToDoanhThuItem(data) {
      let item = {}
      if (!this.isNullOrEmpty(data.A)) {
        item.CHUKY = data.A
      } else item.CHUKY = null

      if (!this.isNullOrEmpty(data.B)) {
        item.MA_TB = data.B
      } else item.MA_TB = null

      if (!this.isNullOrEmpty(data.C)) {
        item.MA_TOANHA = data.C
      } else item.MA_TOANHA = null

      if (!this.isNullOrEmpty(data.D)) {
        item.DOANHTHU = data.D
      } else item.DOANHTHU = null

      if (!this.isNullOrEmpty(data.E)) {
        item.CHIPHI = data.E
      } else item.CHIPHI = null
      return item
    },
    isNullOrEmpty: function (value) {
      return value == undefined || value == null || value.toString().trim() == ''
    },
    kiemTraDuLieu(tencot) {
      if (!(tencot.includes('CHUKY') && tencot.includes('MA_TB') && tencot.includes('MA_TOANHA') && tencot.includes('DOANHTHU') && tencot.includes('CHIPHI'))) {
        this.$root.toastError('File không đúng cấu trúc CHUKY, MA_TB, MA_TOANHA, DOANHTHU, CHIPHI')
        return false
      }
      return true
    },
    checkfile: function () {
      if (this.fileExcel == null) {
        this.$root.toastError('Chưa chọn file excel')
        return false
      }
      if (this.fileExcel.type !== 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' && this.fileExcel.type !== 'application/vnd.ms-excel') {
        this.$toast.error('File không đúng định dạng cho phép (xls, xlsx) hoặc không đúng biểu mẫu vui lòng thử lại')
        return false
      }
      return true
    },
    async ShowData() {
      try {
        this.loading(true)
        //let month = this.dtpKyHD.getMonth() + 1
        let chuky = this.chkThangChot ? this.yyyymm(this.dtpKyHD) + '01' : '0'
        this.dgvDoanhThu = await this.lay_tt_doanhthu_bc_dt(chuky)
      } catch (e) {
        console.log(e)
      } finally {
        this.loading(false)
      }
    },
    async btnRefresh() {
      await this.ShowData()
    },
    async lay_tt_doanhthu_bc_dt(chuky) {
      let rs = await this.$root.context.post('web-toanha/capnhat-doanhthu-bc/lay_tt_doanhthu_bc_dt_v2', {
        chuKy: chuky,
        phanVungId: this.isShowChonTinh ? Number.parseInt(this.tinh) : 0
      })
      return rs.data
    },
    async fn_capnhat_doanhthu_bc_dt(danhsach, ip_cn) {
      let ds = this.UpperCaseData(danhsach)
      let rs = await this.$root.context.post(`web-toanha/capnhat-doanhthu-bc/fn_capnhat_doanhthu_bc_dt_v2?ip=${ip_cn}&phanVungId=${this.isShowChonTinh ? this.tinh : 0}`, ds)
      return rs.data
    },
    async KT_CAPNHAT_DOANHTHU_BC_DT(danhsach) {
      let rs = await this.$root.context.post(`web-toanha/capnhat-doanhthu-bc/kt_capnhat_doanhthu_bc_dt_v2?phanVungId=${this.isShowChonTinh ? this.tinh : 0}`, danhsach)
      return rs.data
    },
    // getIp:async function(){
    //   try{
    //     const response = await fetch("https://jsonip.com/")
    //     const data = await response.json();
    //     return data.ip;
    //   }catch(e) {
    //     return null
    //   }
    // },
    UpperCaseData(ds) {
      let _ds = []
      for (let item of ds) {
        let row = {}
        for (let key in item) {
          let k = key.toUpperCase()
          row[k] = item[key]
        }
        _ds.push(row)
      }
      return _ds
    },
    getDateElements(date) {
      let day = date.getDate()
      day = day > 9 ? day : '0' + day

      let month = date.getMonth() + 1
      month = month > 9 ? month : '0' + month

      let hour = date.getHours()
      hour = hour > 9 ? hour : '0' + hour

      let min = date.getMinutes()
      min = min > 9 ? min : '0' + min

      let second = date.getSeconds()
      second = second > 9 ? second : '0' + second

      return {
        y: date.getFullYear(),
        M: month,
        d: day,
        h: hour,
        m: min,
        s: second
      }
    },
    yyyymm(date) {
      if (!date) {
        return ''
      }
      date = this.getDateElements(date)
      return `${date.y}${date.M}`
    },
    async closeChonTinh(data) {
      console.log('🚀 ~ file: ChangeMegaWanConfigCrossSell.vue ~ line 1806 ~ closeChonTinh ~ data', data)
      try {
        this.Loading = true
        this.tinh = data
        this.chonTinh = false
      } catch (err) {
        console.error('closeChonTinh-err', err)
      } finally {
        this.Loading = false
      }
    },
    cboTinh_EditValueChanged: async function (args) {
      if (args == null) {
        this.tinh = {}
        return
      }
      this.tinh = args
      //clear data
      this.dgvDoanhThu = []
      this.dgvDanhSach = []
      this.dsImportExcel = []
      this.vds = []
      this.vds_upload = []
      this.tsbtnChapNhan = false
      this.duongDan = ''
    },
    checkShowModal: async function () {
      this.$root.showLoading(true)
      let data = await this.$root.context.post('web-toanha/capnhat-doanhthu-bc/fn_kiemtra_quyen_capnhat_doanhthu_bc', {
        kieu: 1
      })
      if (data.data == 1) {
        this.isShowChonTinh = true
        this.$root.showLoading(false)
        await this.$refs.ref_frmChonTinhBanCheo.showModal()
      } else {
        this.$root.showLoading(false)
      }
    },
    onChapNhanModalChonTinh: async function (data) {
      this.tinh = data
    }
  },
  watch: {
    // fileExcel: async function (value) {
    //   await this.txtDuongDan_ButtonClick();
    // },
  },
  async mounted() {
    this.checkShowModal()
  }
}
</script>

<style>
.disable-btn:hover {
  background-color: white !important;
}

.disable-btn a {
  color: #6c757d !important;
}

.disable-btn a:hover {
  cursor: default;
  color: #6c757d;
}
</style>
