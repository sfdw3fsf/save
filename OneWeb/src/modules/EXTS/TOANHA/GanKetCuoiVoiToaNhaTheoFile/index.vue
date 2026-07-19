<template src="./index.html"></template>
<script>
import XLSX from 'xlsx'
export default {
  name: 'CapNhatDoanhThuBanCheo',
  data: function () {
    return {
      ok: false,
      fileExcel: null,
      dgvDanhSach: [],
      dsImportExcel: [],
      vds: [],
      vds_upload: [],
      tsbtnXem: true,
      tsbtnChapNhan: true,
      tsbtnHuy: true,
      tsbtnMauEx: true,
      btnXuatExcel: true,
      dsKeyDuocChon: [],
      checkAll: false,
      duongDan: "",
    }
  },
  async created() {
    this.tsbtnChapNhan = false
  },
  methods: {
    duongDan_click: function(args){
      this.$refs.fileInput.click();
    },
    async onChangeFile(e) {
      const files = e.target.files;
      if (!files.length) {
        return;
      } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
        return this.$root.toastError("File excel không đúng định dạng!");
      }
      this.fileExcel = e.target.files[0];
      this.duongDan = this.fileExcel.name
      this.tsbtnChapNhan = false
      try {
        this.$root.showLoading(true);
        await this.txtDuongDan_ButtonClick();
      } finally {
        this.$root.showLoading(false);
      }
    },
    getColumnTemplateTRANGTHAI(parent) {
      return function () {
        return {
          template: {
            template: `
                          <span title="Trạng thái">
                            <img :src="image" />
                          </span>
                        `,
            data() {
              return {
                parent: parent,
                data: {}
              }
            },
            computed: {
              image: function () {
                return `./static/icons/icon-toanha/${this.data.TRANGTHAI}.png`
              }
            }
          }
        }
      }
    },
    getColumnTemplateChonNhieu(parent) {
      return function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input
                                    type="checkbox"
                                    class="check"
                                    v-model="parent.dsKeyDuocChon"
                                    :value="data.KEY"/>
                                <span class="name"></span>
                            </div>
                        `,
            data() {
              return {
                parent: parent,
                data: {}
              }
            }
          }
        }
      }
    },
    getHeaderTemplateChonNhieu(parent) {
      return function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input type="checkbox" v-model="parent.checkAll" class="check"/>
                                <span class="name"></span>
                            </div>
                        `,
            data() {
              return {
                parent: parent
              }
            }
          }
        }
      }
    },
    tsbtnHuy_Click() {
      if (!this.tsbtnHuy) return
      this.tsbtnChapNhan = false
      this.dgvDanhSach = []
      this.fileExcel = null
      this.vds_upload = []
      this.dsKeyDuocChon = []
    },
    tsbtnMauEx_Click() {
      if (!this.tsbtnMauEx) return
      try {
        this.loading(true)
        let dt = [
          {
            KYHIEU: 'A',
            PHANLOAIKC_ID: 1,
            MA_TOANHA: 'TN1'
          },
          {
            KYHIEU: 'B',
            PHANLOAIKC_ID: 2,
            MA_TOANHA: 'TN2'
          }
        ]
        let fileMau = XLSX.utils.json_to_sheet(dt)
        let wb = XLSX.utils.book_new() // make Workbook of Excel
        XLSX.utils.book_append_sheet(wb, fileMau, 'Spliter_ToaNha') // sheetAName is name of Worksheet
        // export Excel file
        XLSX.writeFile(wb, 'Spliter_ToaNha_FileMau.xls')
      } catch (e) {
        this.$root.toastError('Có lỗi khi xuất excel mẫu ' + e.toString())
      } finally {
        this.loading(false)
      }
    },
    btnXuatExcel_Click() {
      if (this.dgvDanhSach.length === 0) { return }
      try {
        let exportProperties = {
          dataSource: this.dgvDanhSach
        }
        this.$refs.dsKetCuoiToaNha.excelExport(exportProperties)
      } catch (e) {
        this.$root.toastError('Có lỗi khi xuất excel ' + e.toString())
      }
    },
    async tsbtnChapNhan_Click() {
      try {
        this.loading(true)
        var kt = true
        var ds = this.dgvDanhSach.filter(e => {
          return this.dsKeyDuocChon.includes(e.KEY)
        })
        if (this.dgvDanhSach.length > 0) {
          for (let index = 0; index < this.dgvDanhSach.length; index++) {
            const element = this.dgvDanhSach[index]
            if (!this.dsKeyDuocChon.includes(element.KEY)) {
              this.dgvDanhSach[index].TRANGTHAI = '-1'
              this.dgvDanhSach[index].IMAGE = './static/icons/icon-toanha/-1.png'
              this.dgvDanhSach[index].GHICHU = ''
              continue
            }
            let vketcuoi_id = parseInt(element.KETCUOI_ID)
            let vphanloaikc_id = parseInt(element.PHANLOAIKC_ID)
            let vtoanha_id = parseInt(element.TOANHA_ID)
            var kq = await this.CN_KETCUOI_TOANHA(vketcuoi_id, vphanloaikc_id, vtoanha_id)
            if (kq.length > 0) {
              if (kq[0].RESULT !== 'OK') {
                kt = false
                this.dgvDanhSach[index].TRANGTHAI = '0'
                this.dgvDanhSach[index].IMAGE = './static/icons/icon-toanha/0.png'
              } else {
                this.dgvDanhSach[index].TRANGTHAI = '1'
                this.dgvDanhSach[index].IMAGE = './static/icons/icon-toanha/1.png'
              }
              this.dgvDanhSach[index].GHICHU = kq[0].RESULT
            }
          }
          this.$refs.dsKetCuoiToaNha.refresh()
        }
        this.loading(false)
        if (kt) { this.$root.toastSuccess('Cập nhật dữ liệu thành công!') } else { this.$root.toastError('Cập nhật dữ liệu không đầy đủ!') }
        this.tsbtnChapNhan = false
      } catch (e) {
        console.log('🚀 ~ file: index.vue ~ line 149 ~ tsbtnChapNhan_Click ~ e', e)
        this.$root.toastError('Lỗi cập nhật dữ liệu!')
      } finally {
        this.loading(false)
      }
    },
    async CN_KETCUOI_TOANHA(KETCUOI_ID, PHANLOAIKC_ID, TOANHA_ID) {
      try {
        let data = { KETCUOI_ID, PHANLOAIKC_ID, TOANHA_ID }
        console.log('🚀 ~ file: index.vue ~ line 129 ~ CN_KETCUOI_TOANHA ~ data', data)
        let rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/capnhat-ketcuoi-toa-nha', data)
        return rs.data
      } catch (error) {
        return []
      }
    },
    async txtDuongDan_ButtonClick() {
      this.tsbtnChapNhan = false
      if (this.fileExcel === '' || this.fileExcel === null) return
      let tencot
      try {
        this.loading(true)
        if (this.checkfile()) {
          let s = this.fileExcel.name.toLowerCase()
          if (s.endsWith('.xlsx') || s.endsWith('.xls')) {
            this.dgvDanhSach = []
            this.dsImportExcel = []
            this.vds_upload = []
            this.$refs.dsKetCuoiToaNha.refresh()
            let reader = new FileReader()
            reader.onload = async (e) => {
              /* Parse data */
              const data = e.target.result
              const workBook = XLSX.read(data, { type: 'binary' })
              if (workBook != null) {
                for (let i = 0; i < workBook.SheetNames.length; i++) {
                  const sheetName = workBook.SheetNames[i]
                  switch (sheetName.toUpperCase()) {
                    case 'SPLITER_TOANHA':
                      this.dsImportExcel = XLSX.utils.sheet_to_json(
                        workBook.Sheets[sheetName],
                        { header: 'A' }
                      )
                      break
                    default:
                      this.$root.toastError('File không đúng cấu trúc, bạn xem lại!')
                      return
                  }
                }
                tencot = Object.values(this.dsImportExcel[0])
                if (this.kiemTraDuLieu(tencot)) {
                  for (let i = 1; i < this.dsImportExcel.length; i++) {
                    let item = this.convertToItem(this.dsImportExcel[i])
                    this.vds_upload.push(item)
                    if (this.vds_upload.length === 200) break
                  }
                  let dtquyen = await this.DS_QUYEN_KETCUOI_TOANHA(this.vds_upload)
                  this.dgvDanhSach = dtquyen
                  this.dgvDanhSach.forEach((item, index) => {
                    item.KEY = index + 1
                  })
                  let dtChuanHoa = dtquyen.filter(i => i.TRANGTHAI.toString() === '0')
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
                  this.checkAll = true
                  this.$refs.dsKetCuoiToaNha.refresh()
                }
              }
            }
            reader.readAsBinaryString(this.fileExcel)
          }
        }
      } catch (e) {
        this.$root.toastError('Lỗi định dạng file excel, bạn xem lại!')
        console.log(e)
      } finally {
        this.loading(false)
      }
    },
    async DS_QUYEN_KETCUOI_TOANHA(vds) {
      try {
        let rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/ds-quyen-ketcuoi-toanha', vds)
        return rs.data
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 172 ~ DS_QUYEN_KETCUOI_TOANHA ~ error', error)
        return []
      }
    },
    convertToItem(data) {
      let item = {}
      if (!this.isNullOrEmpty(data.A)) {
        item.KYHIEU = data.A.toString()
      } else item.KYHIEU = null

      if (!this.isNullOrEmpty(data.B)) {
        item.PHANLOAIKC_ID = parseInt(data.B)
      } else item.PHANLOAIKC_ID = null

      if (!this.isNullOrEmpty(data.C)) {
        item.MA_TOANHA = data.C.toString()
      } else item.MA_TOANHA = null
      return item
    },
    isNullOrEmpty: function (value) {
      return (
        value == undefined || value == null || value.toString().trim() == ''
      )
    },
    kiemTraDuLieu(tencot) {
      console.log('🚀 ~ file: index.vue ~ line 271 ~ kiemTraDuLieu ~ tencot', tencot)

      if (tencot.length === 0 || tencot.length > 3) {
        this.$root.toastError('Dữ liệu file excel chưa đúng số lượng cột')
        return false
      }

      if (!(tencot.includes('KYHIEU') && tencot.includes('PHANLOAIKC_ID') && tencot.includes('MA_TOANHA'))) {
        this.$root.toastError('File không đúng cấu trúc KYHIEU, PHANLOAIKC_ID, MA_TOANHA')
        return false
      }
      return true
    },
    checkfile: function () {
      if (this.fileExcel == null) {
        this.$root.toastError('Chưa chọn file excel')
        return false
      }
      if (
        this.fileExcel.type !== 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' && this.fileExcel.type !== 'application/vnd.ms-excel'
      ) {
        this.$toast.error('File không đúng định dạng cho phép (xls, xlsx) hoặc không đúng biểu mẫu vui lòng thử lại')
        return false
      }
      return true
    },
    getIp: async function () {
      try {
        const response = await fetch('https://jsonip.com/')
        const data = await response.json()
        return data.ip
      } catch (e) {
        return null
      }
    },
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
    }
  },
  async mounted() {
    try {

    } catch (error) {
      console.log('🚀 ~ file: index.vue ~ line 322 ~ mounted ~ error', error)
    }
  },
  watch: {
    // fileExcel: async function (value) {
    //   this.tsbtnChapNhan = false
    //   await this.txtDuongDan_ButtonClick()
    // },
    checkAll(newVal) {
      console.log('🚀 ~ file: index.vue ~ line 298 ~ checkAll ~ newVal', newVal)
      if (newVal) {
        this.dsKeyDuocChon = this.dgvDanhSach.map(item => item.KEY)
        return
      }
      if (this.dsKeyDuocChon.length > 0 &&
        this.dsKeyDuocChon.length === this.dgvDanhSach.length) {
        this.dsKeyDuocChon = []
      }
    },
    dsKeyDuocChon(newVal) {
      this.checkAll = newVal.length > 0 && newVal.length === this.dgvDanhSach.length
    },
    dgvDanhSach() {
    }
  }
}
</script>

<style>
.disabled-btn {
  pointer-events: none;
  opacity: 0.5;
  background: #ccc;
}

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

a.disable-btn {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}

.ketcuoi-toanha .select2-container .select2-selection--single .select2-selection__rendered {
  white-space: initial;
}

.ketcuoi-toanha .e-grid div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="1"],
.ketcuoi-toanha .e-grid div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="1"]>div {
  padding: 0 !important;
}
</style>
