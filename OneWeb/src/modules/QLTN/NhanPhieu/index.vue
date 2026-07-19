<template src="./index.html"></template>
<style src="./style.scss" scoped></style>
<style>
.overlay-common .loading {
  display: flex;
  justify-content: center;
  flex-direction: column;
}

.overlay-common.show {
  display: flex;
  justify-content: center;
}

.overlay-common {
  background-color: rgba(0, 0, 0, 0.7);
}

.overlay-common .loading-panel {
  display: flex;
  text-align: center;
  background-color: #fff;
  padding: 10px 15px;
  align-items: center;
  color: #035fcc;
  border: 1px solid #035fcc;
  border-radius: 7px;
}

.overlay-common .loading-panel .loading-content {
  flex-grow: 1;
  padding-left: 10px;
}
</style>

<script>
// import MultiSelectDemo from './components/multi-select'
import moment from 'moment'
import GRID_COLUMS from './constant'
import ModalExport from './components/modal-export'
import NhatKy from './components/nhat-ky'
import DSLoi from '../components/PopupDSLoi/PopupDSLoi.vue'
import Vue from 'vue'
import { Query } from '@syncfusion/ej2-data'
import { MultiSelectPlugin, CheckBoxSelection } from '@syncfusion/ej2-vue-dropdowns'
import { CheckBoxPlugin } from '@syncfusion/ej2-vue-buttons'
import { DropDownList } from '@syncfusion/ej2-dropdowns'
import { currency } from '@/filters/currency'
import XLSX from 'xlsx'
import { cloneArray } from '@/utils/util'

Vue.use(MultiSelectPlugin)
Vue.use(CheckBoxPlugin)

let lydoElem, lydoObj

export default {
  components: {
    // 'multi-select-demo': MultiSelectDemo,
    ModalExport,
    NhatKy,
    DSLoi
  },
  data: function () {
    return {
      editSettings: { allowEditing: true, allowAdding: false, allowDeleting: false, mode: 'Normal' },
      lyDoParams: {
        create: () => {
          lydoElem = document.createElement('input')
          return lydoElem
        },
        read: () => {
          // console.log(lydoObj)
          return lydoObj.value
        },
        destroy: () => {
          lydoObj.destroy()
        },
        write: () => {
          lydoObj = new DropDownList({
            dataSource: this.dsLyDoTraChung,
            fields: { value: 'LYDOTRA_ID', text: 'NOIDUNG' },
            enabled: true,
            placeholder: 'Chọn lý do',
            floatLabelType: 'Never',
            popupWidth: 500
          })
          lydoObj.appendTo(lydoElem)
        }
      },
      GRID_COLUMS: GRID_COLUMS,
      dsLoi: [],
      dsKiemTra: [],
      passParam: {
        maTT: '',
        kyHoaDon: '',
        trangThaiHDId: ''
      },
      header: {
        title: 'XÁC NHẬN NỢ, HOÀN TRẢ GIẤY BÁO, HẸN TRẢ',
        list: [
          { name: 'Quản lý đại lý', link: { name: 'Ui.cards' } },
          {
            name: 'Nhận phiếu',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      configs: {
        nvtc: {
          fields: { text: 'TEN_HIENTHI', value: 'NHANVIEN_ID' },
          mode: 'CheckBox',
          showSelectAll: true,
          showDropDownIcon: true,
          filterBarPlaceholder: 'Tìm kiếm nhanh'
        }
      },
      tabData: {
        1: [],
        2: [],
        3: [],
        4: [],
        5: [],
        6: [],
        7: []
      },
      tabKey: {
        1: 0,
        2: 0,
        3: 0,
        4: 0,
        5: 0,
        6: 0,
        7: 0
      },
      keyDSLuotGiao: 0,
      dataExport: [],
      columnsExport: [],
      dsLoaiPhieu: [],
      dsLyDoTra1: [{ LYDOTRA_ID: 19, NOIDUNG: 'test' }],
      dsNhomLyDoTra: [],
      dsLyDoTraChung:[],
      dsLyDoTra: [],
      dsLuotGiao: [],
      dsNVTC: [
        // {
        //   NHANVIEN_ID: "3789",
        //   MA_NV: "ABI3",
        //   TEN_NV: "Vũ Thị Sơn",
        //   TEN_HIENTHI: "ABI3 - Vũ Thị Sơn",
        //   TIEN_DC: "0",
        //   DONVI_ID: "1004",
        // },
      ],
      dsPhieuChuaThu: [],
      dsTraHoaDon: [],
      dsTraTronBo: [],
      dsNVTCId: [],
      searchForm: {
        searchType: '3',
        ckbNgayHenTra: false,
        ckbNgayNhanGiayBao: false,
        ngayHenTra: new Date(),
        ngayNhanGiayBao: new Date(),
        idLoaiPhieu: '',
        idLuotGiao: '',
        idNVTC: '',
        txtTimKiem: ''
      },
      updateForm: {
        ngayDen: new Date(),
        idNhomLyDo: '',
        idLyDo: '',
        ghiChu: ''
      },
      indexFocus: 0,
      waterMark: 'Chọn ngày',
      dateFormat: 'dd/MM/yyyy',
      showTable: true,
      hideNVTC: true,
      DS_NHANPHIEU_THEO_AM: -1,
      currentTab: 1,
      maTTChosen: [],
      files: [],
      dsLoiExcel: [],
      rowChosen: null,
      kyHoaDon: `${moment(new Date()).subtract(1, 'months').endOf('month').format('YYYYMM')}01`, //20210301//,
      showSearch: true,
      updatedRows: [],
      customTemplate: function () {
        return {
          template: Vue.component('footerTemplate', {
            template: `<input type="text" class="form-control tright" v-model="data.Custom" :disabled="true" style="margin: 7px 0px 7px 0px;" />`,
            data() {
              return { data: { data: {} } }
            }
          })
        }
      },
      firstColumnTemplate: function () {
        return {
          template: Vue.component('footerTemplate', {
            template: `<input type="text" class="form-control tright sum-record" v-model="data.Custom" :disabled="true" style="margin: 7px 0px 7px 0px; margin-left: -50px" />`,
            data() {
              return { data: { data: {} } }
            }
          })
        }
      }
    }
  },
  computed: {
    PTAggregatesColumns() {
      return [
        {
          field: 'MA_TT',
          type: 'Custom',
          footerTemplate: this.firstColumnTemplate,
          customAggregateFn: this.sumMaTT
        },
        {
          field: 'TIEN_HD',
          type: 'Custom',
          footerTemplate: this.customTemplate,
          customAggregateFn: this.sumTienHd
        },
        {
          field: 'TIEN',
          type: 'Custom',
          footerTemplate: this.customTemplate,
          customAggregateFn: this.sumTienNo
        }
        // {
        //   field: "phieuThu",
        //   type: "Custom",
        //   footerTemplate: this.customTemplate,
        //   customAggregateFn: this.sumPhieuThu,
        // },
        // {
        //   field: "tienThu",
        //   type: "Custom",
        //   footerTemplate: this.customTemplate,
        //   customAggregateFn: this.sumTienThu,
        // },
      ]
    }
  },
  validations: {},
  watch: {
    async 'updateForm.idNhomLyDo'(val) {
      try {
        this.loading(true)
        let kq = await this.axios.post('/web-thicong/capnhatthongtin/sp_cbx_lay_ds_lydotra', { p_lydotra_id: val })
        if (kq.data.data) {
          // this.dsLyDoTra = kq.data.data
          let id = 0;          
          this.dsLyDoTra = [];
          kq.data.data.forEach(item => {
            if (id != item.lydotra_id)
              this.dsLyDoTra.push({
                LYDOTRA_ID: item.lydotra_id,
                NOIDUNG: item.noidung
              });
            id = item.lydotra_id;
          });         
        } else {
          this.dsLyDoTra = []
        }
      } catch (e) {
      } finally {
        this.loading(false)
      }
    }
  },
  created: async function () {
    this.loading(true)
    await Promise.all([
      this.getKyCuocHH(),
      this.dsLoaiPhieuDropDown(),
      this.dsLyDoDropDown(),
      this.getTSMD(),
      this.getDsNVTC()
    ])
    this.loading(false)
    // console.log(this.$auth.getUserName())
  },
  async mounted() {
    try {
      this.loading(true)
      let kq = await this.axios.get('/web-thicong/capnhatthongtin/sp_cbx_lay_ds_nhom_lydotra')
      if (kq.data.data) {
        this.dsNhomLyDoTra = kq.data.data
        this.updateForm.idNhomLyDo = 0
      } else {
        this.dsNhomLyDoTra = []
      }
    } catch (e) {
    } finally {
      this.loading(false)
    }
  },
  async updated() {},
  methods: {
    sumMaTT() {
      let selected = this.tabData[this.currentTab].filter((x) => x._CHECK === true).length
      return `${selected}/${this.tabData[this.currentTab].length}`
    },
    sumTienHd() {
      const selected = this.tabData[this.currentTab].filter((x) => x._CHECK === true).reduce((a, b) => +a + +b.TIEN_HD, 0)
      const total = this.tabData[this.currentTab].reduce((a, b) => +a + +b.TIEN_HD, 0)
      return currency(selected) + '/' + currency(total)
    },
    sumTienNo() {
      // const tong = this.tabData[this.currentTab].reduce((a, b) => +a + +b.TIEN, 0)
      // return currency(tong)
      const selected = this.tabData[this.currentTab].filter((x) => x._CHECK === true).reduce((a, b) => +a + +b.TIEN, 0)
      const total = this.tabData[this.currentTab].reduce((a, b) => +a + +b.TIEN, 0)
      return currency(selected) + '/' + currency(total)
    },
    onSelectRowCheckbox(value) {
      if (value.length > 0) {
        this.searchForm.idLuotGiao = value.join()
      } else {
        this.searchForm.idLuotGiao = ''
      }
      // this.$refs.table.rowClick(1, null);
    },
    onSelectRowGrid(value) {
      // console.log(value)

      // var selectLyDoID = document.getElementById('combo' + value.MA_TT)
      // var selectGhiChu = document.getElementById('ghichu' + value.MA_TT)
      // value.LYDOTRA_ID = selectLyDoID.options[selectLyDoID.selectedIndex].value
      // value.GHICHU = selectGhiChu.value
      this.rowChosen = value
    },
    onChangeLuotGiao() {
      this.showTable = !this.showTable
    },
    onChangeNVTC() {
      this.hideNVTC = !this.hideNVTC
    },
    onOk(value) {
      // this.hideNVTC = true
      // const arrName = []
      // this.dsNVTC.forEach((element) => {
      //   if (value.includes(element.NHANVIEN_ID)) {
      //     arrName.push(element.TEN_HIENTHI)
      //   }
      // })
      // this.searchForm.idNVTC = arrName.join()
      // this.dsNVTCId = value.length > 0 ? value.join() : ''
      this.dsLuotGiaoDropDown()
    },
    onCancel(value) {
      this.hideNVTC = true
    },
    onClickDanhSach: async function (event, keepCheck = false) {
      // this.maTTChosen = [];
      // this.updateForm.ghiChu="";
      // console.log("this.DS_NHANPHIEU_THEO_AM", this.DS_NHANPHIEU_THEO_AM);
      this.updateForm.idLyDo = ''
      this.updateForm.ghiChu = ''
      if (this.DS_NHANPHIEU_THEO_AM === 1) {
        this.loading(true)
        await this.getDataV1()
        this.loading(false)
      } else {
        if (this.searchForm.idLuotGiao) {
          this.loading(true)
          await this.getDataV3()
          this.loading(false)
        } else {
          this.loading(true)
          await this.getDataV2()
          this.loading(false)
        }
      }
      // if (keepCheck && this.updatedRows.length > 0) {
      //   let ids = []
      //   for (let index = 0; index < this.updatedRows.length; index++) {
      //     const element = this.updatedRows[index]
      //     const idSelect = this.tabData[this.currentTab].findIndex(item => item.MA_TT == element.MA_TT)
      //     ids.push(idSelect)
      //   }
      //   setTimeout(() => this.$refs[`table0${this.currentTab}`].grid.selectRows(ids), 500)
      // } else {
      //   this.updatedRows = []
      // }
    },
    switchTab(tab) {
      this.currentTab = tab
      this.maTTChosen = []
      this.onClickDanhSach()
    },
    onChangeLoaiPhieu() {
      // this.dsLyDoDropDown(); v2
      this.onClickDanhSach()
    },
    checkedGrid01(value) {
      if (JSON.stringify(this.maTTChosen) != JSON.stringify(value)) {
        this.maTTChosen = value
      } else {
        this.maTTChosen = []
      }
      $('.sum-record').val(`${this.maTTChosen.length}/${this.tabData[this.currentTab].length}`)
    },
    onClickTraGiayBao() {
      if (this.validateFormIgnore()) {
        const dsMaTT = this.tabData[this.currentTab]
          .filter((x) => x._CHECK === true)
          .map(function (x) {
            return x.MA_TT
          })
        const { body, haveHoaDon } = this.makeTraGiayBaoBody(dsMaTT, 3, this.tabData[this.currentTab])
        if (body === null) return
        if (haveHoaDon) {
          this.$toast.error(`Trả giấy báo không áp dụng cho hóa đơn !`)
        } else {
          this.traGiayBao(body)
        }
      }
    },
    onClickTraHoaDon: async function () {
      if (this.validateFormIgnore()) {
        const dsMaTT = this.tabData[this.currentTab]
          .filter((x) => x._CHECK === true)
          .map(function (x) {
            return x.MA_TT
          })
        const { body } = this.makeTraGiayBaoBody(dsMaTT, 1, this.tabData[this.currentTab])
        if (body === null) return
        const bodySeri = this.makeHuySeriBody(
          // this.maTTChosen,
          dsMaTT,
          this.tabData[this.currentTab]
        )
        await this.traHoaDon(body)
        await this.huySeri(bodySeri)
      }
    },
    onClickHuyTraHoaDon() {
      const dsMaTT = this.tabData[this.currentTab]
        .filter((x) => x._CHECK === true)
        .map(function (x) {
          return x.MA_TT
        })
      const body = this.makeHuyTraHDBody(dsMaTT, 3, this.tabData[this.currentTab])
      this.huyTraHoaDon(body)
    },
    onClickHuyTraTronBo() {
      const dsMaTT = this.tabData[this.currentTab]
        .filter((x) => x._CHECK === true)
        .map(function (x) {
          return x.MA_TT
        })
      const body = this.makeHuyTraHDBody(
        // this.maTTChosen,
        dsMaTT,
        5,
        this.tabData[this.currentTab]
      )
      this.huyTraTronBo(body)
    },
    onClickCapNhat() {
      if (this.validateCapNhat()) {
        const dsMaTT = this.tabData[this.currentTab]
          .filter((x) => x._CHECK === true)
          .map(function (x) {
            return x.MA_TT
          })
        const body = this.makeCapNhatBody(
          // this.maTTChosen,
          dsMaTT,
          this.tabData[this.currentTab]
        )
        const urlObject = {
          1: '/web-thuno/api/thu-no/nhan-phieu/cap-nhat-phieu-chua-thu',
          2: '/web-thuno/api/thu-no/nhan-phieu/cap-nhat-phieu-tra-hoa-don',
          3: '/web-thuno/api/thu-no/nhan-phieu/cap-nhat-phieu-tra-tron-bo',
          4: '/web-thuno/api/thu-no/nhan-phieu/cap-nhat-phieu-tra-phieu-hen',
          5: '/web-thuno/api/thu-no/nhan-phieu/cap-nhat-phieu-tra-giay-bao',
          7: '/web-thuno/api/thu-no/nhan-phieu/cap-nhat-phieu-hoa-don-loai'
        }
        if (body === null) return
        this.capNhatData(body, urlObject[this.currentTab])
      }
    },
    onClickTraTronBo: async function () {
      if (this.validateFormIgnore()) {
        const dsMaTT = this.tabData[this.currentTab]
          .filter((x) => x._CHECK === true)
          .map(function (x) {
            return x.MA_TT
          })
        const body = this.makeTraTronBoBody(
          // this.maTTChosen,
          dsMaTT,
          this.tabData[this.currentTab]
        )
        await this.traTronBo(body)
        const bodySeri = this.makeHuySeriBody(
          // this.maTTChosen,
          dsMaTT,
          this.tabData[this.currentTab]
        )
        await this.huySeri(bodySeri)
      }
    },
    // onClickNhapExcel () {
    //   this.$refs.fileInput.value = ''
    //   if (this.tabData[this.currentTab].length == 0) {
    //     this.$toast.error('Danh sách phiếu không được để trống!')
    //   } else {
    //     this.$refs.fileInput.click()
    //   }
    // },

    onClickNhapExcel() {
      if (this.currentTab !== 1 && this.currentTab !== 7) return

      if (this.tabData[this.currentTab].length <= 0) {
        this.$toast.error('Danh sách phiếu không được để trống !')
        return
      }
      document.getElementById('upload_nhap_excel').click()
    },
    onClickExport() {
      this.columnsExport = this.GRID_COLUMS[this.currentTab]
      const dataSelected = this.tabData[this.currentTab].filter((x) => x._CHECK === true)
      this.dataExport = []
      this.dataExport = [...dataSelected]
      if (this.dataExport !== undefined && this.dataExport !== null && this.dataExport.length > 0) {
        this.$bvModal.show('modal-export')
      } else {
        this.$toast.error('Không có dữ liệu để xuất excel')
      }
    },
    onClickNhatKy() {
      if (this.tabData[this.currentTab].length > 0 && this.rowChosen) {
        this.passParam.maTT = this.rowChosen.MA_TT
        this.passParam.kyHoaDon = this.kyHoaDon
        this.passParam.trangThaiHDId = this.rowChosen.TRANGTHAI_HD_ID
        this.dsKiemTra = [...this.tabData[this.currentTab]]
        // console.log(this.passParam)
        this.$bvModal.show('modal-nhatky')
      } else {
        this.$toast.error('Chưa có dữ liệu để cập nhật nhật ký')
      }
    },
    onCloseModal() {
      this.$bvModal.hide('modal-nhatky')
    },
    onToggleSearchForm() {
      this.showSearch = !this.showSearch
    },
    CheckMaTT_NVQLXL(ds) {
      let errors = []
      let data = [...this.tabData[this.currentTab]]

      if (this.tabData[this.currentTab].length <= 0) return
      for (let i = 0; i < this.tabData[this.currentTab]; i++) {
        this.tabData[this.currentTab][i]._CHECK = false
      }
      for (let i = 0; i < ds.length; i++) {
        let fIndex = data.findIndex((x) => x.MA_TT + '' === ds[i].MA_TT + '')
        if (fIndex < 0) {
          errors.push({ STT: i + 1, MA_TT: ds[i].MA_TT, LOI: 'Mã TT không có trong danh sách' })
        } else {
          data[fIndex]._CHECK = true
          data[fIndex].ND_THUCHIEN = ds[i].NVQL_XL === null ? null : ds[i].NVQL_XL.trim()
        }
      } // end for
      console.log(errors)
      if (errors.length > 0) {
        this.dsLoi = [...errors]
        this.$toast.error('Đã phát sinh lỗi khi upload file.')
        this.$refs.refDSLoi.openDialog()
      }
      this.tabData[this.currentTab] = [...data]
    },
    checkMaTT(ds) {
      let errors = []
      let data = [...this.tabData[this.currentTab]]
      let dataOK=[]
      if (this.tabData[this.currentTab].length <= 0) return
      for (let i = 0; i < this.tabData[this.currentTab]; i++) {
        this.tabData[this.currentTab][i]._CHECK = false
      }

      for (let i = 0; i < ds.length; i++) {
        let fIndex = data.findIndex((x) => x.MA_TT + '' === ds[i].MA_TT + '')
        if (fIndex < 0) {
          errors.push({ STT: i + 1, MA_TT: ds[i].MA_TT, LOI: 'Không có trong danh sách' })
        } else {
          try {
            let lydoId = Number(ds[i].LYDO_ID)
            let fLyDoId = this.dsLyDoTraChung.find((x) => x.LYDOTRA_ID === lydoId)
            if (fLyDoId === undefined) {
              errors.push({ STT: i + 1, MA_TT: ds[i].MA_TT, LOI: 'Lý do ID phải là kiểu số' })
            } else {
              data[fIndex]._CHECK = true
              data[fIndex].LYDOTRA_ID = lydoId
              data[fIndex].GHICHU = ds[i].GHICHU === null ? null : ds[i].GHICHU.trim()
              dataOK.push(data[fIndex])
            }
          } catch (err) {
            errors.push({ STT: i + 1, MA_TT: ds[i].MA_TT, LOI: 'Không tồn tại lý do ID trong dữ liệu' })
          }
        }
      } // end for
      //console.log(errors)
      if (errors.length > 0) {
        this.dsLoi = [...errors]
        this.$toast.error('Đã phát sinh lỗi khi upload file.')
        this.$refs.refDSLoi.openDialog()
      }
      else
      this.tabData[this.currentTab] = [...dataOK]
    },
    async onChangeDocFile(e) {
      let files = e.target.files

      if (!files.length) {
        return
      } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
        document.getElementById('upload_doc_excel').value = null
        return this.$toast.error('File excel không đúng định dạng!')
      }
      let f = files[0]

      var reader = new FileReader()
      const vm = this

      reader.onload = function (e) {
        var data = new Uint8Array(e.target.result)
        var workbook = XLSX.read(data, { type: 'array' })
        let sheetName = workbook.SheetNames[0]
        /* DO SOMETHING WITH workbook HERE */
        // console.log(workbook);
        let worksheet = workbook.Sheets[sheetName]
        let exceldata = XLSX.utils.sheet_to_json(worksheet)

        if (exceldata.length <= 0) {
          vm.$toast.error('Không tìm thấy dữ liệu trong file đã upload.')
          document.getElementById('upload_doc_excel').value = null
          return false
        }
        let exceldata1 = XLSX.utils.sheet_to_json(worksheet, { header: 1 })
        let columns = []
        exceldata1[0].forEach((item) => {
          columns.push(item.toUpperCase())
        })
        if (!columns.includes('MA_TT') || !columns.includes('NVQL_XL')) {
          vm.$toast.error('File gồm có các cột: MA_TT, NVQL_XL')
          document.getElementById('upload_doc_excel').value = null
          return false
        }
        vm.CheckMaTT_NVQLXL(exceldata)
      } // end of onload file
      reader.readAsArrayBuffer(f)
      document.getElementById('upload_doc_excel').value = null
    },
    async onChangeNhapFile(e) {
      let files = e.target.files

      if (!files.length) {
        return
      } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
        document.getElementById('upload_nhap_excel').value = null
        return this.$toast.error('File excel không đúng định dạng!')
      }
      let f = files[0]

      var reader = new FileReader()
      const vm = this

      reader.onload = function (e) {
        var data = new Uint8Array(e.target.result)
        var workbook = XLSX.read(data, { type: 'array' })
        let sheetName = workbook.SheetNames[0]
        /* DO SOMETHING WITH workbook HERE */
        // console.log(workbook);
        let worksheet = workbook.Sheets[sheetName]
        let exceldata = XLSX.utils.sheet_to_json(worksheet)

        if (exceldata.length <= 0) {
          vm.$toast.error('Không tìm thấy dữ liệu trong file đã upload.')
          document.getElementById('upload_nhap_excel').value = null
          return false
        }
        let exceldata1 = XLSX.utils.sheet_to_json(worksheet, { header: 1 })
        let columns = []
        exceldata1[0].forEach((item) => {
          columns.push(item.toUpperCase())
        })
        if (!columns.includes('MA_TT') || !columns.includes('LYDO_ID') || !columns.includes('GHICHU')) {
          vm.$toast.error('File gồm có các cột: MA_TT, LYDO_ID, GHICHU')
          document.getElementById('upload_nhap_excel').value = null
          return false
        }
        if (vm.currentTab === 1 || vm.currentTab === 7) {
          vm.checkMaTT(exceldata)
        }
      } // end of onload file
      reader.readAsArrayBuffer(f)
      document.getElementById('upload_nhap_excel').value = null
    },
    onClickDocFileExcel() {
      if (this.currentTab !== 2 && this.currentTab !== 3 && this.currentTab !== 4 && this.currentTab !== 5 && this.currentTab !== 7) return
      if (this.tabData[this.currentTab].length <= 0) {
        this.$toast.error('Danh sách phiếu không được để trống !')
        return
      }
      document.getElementById('upload_doc_excel').click()
    },
    changeDataGrid(data) {
      let temp = []
      let row = 0
      let selectArray = []
      this.tabData[this.currentTab].forEach((el) => {
        data.forEach((item) => {
          if (el.MA_TT + '' === item.MA_TT + '') {
            // el.GHICHU_HTML = `<input type="text" id="ghichu${item.MA_TT}" class="form-control" value="${item.GHICHU}">`
            el.GHICHU = item.GHICHU
            // let opt = `<select id="combo${item.MA_TT}" class="form-control">`
            // this.dsLyDoTra.forEach((el1) => {
            //   if (el1.LYDOTRA_ID == item.LYDO_ID) {
            //     opt += `<option selected="selected" value="${el1.LYDOTRA_ID}">${el1.NOIDUNG}</option>`
            //   } else {
            //     opt += `<option value="${el1.LYDOTRA_ID}">${el1.NOIDUNG}</option>`
            //   }
            // })
            // opt += `</select>`
            // el.LYDOTRA_ID_HTML = opt
            el.LYDOTRA_ID = item.LYDO_ID
            selectArray.push(row)
            temp.push(el)
          }
        })
        row = row + 1
      })
      //   this.tabData[this.currentTab] = [... this.tabData[this.currentTab]];
      //   setTimeout(() => this.$refs[`table0${this.currentTab}`].grid.selectRows(selectArray), 1000);
      this.tabData[this.currentTab] = [...temp]
    },
    checkFormatData(data) {
      const dsLoi = []
      if (data && data.length > 0) {
        let keys = Object.keys(data[0])
        data.forEach((el) => {
          let key = Object.keys(el)
          keys = [...new Set(keys.concat(key))]
        })
        if (keys.length < 3 || keys[0] != 'MA_TT' || keys[1] != 'LYDO_ID' || keys[2] != 'GHICHU') {
          this.$toast.error('File Excel nhập vào phải đúng định dạng!\n - Chỉ có các trường theo thứ tự MA_TT, LYDO_ID, GHICHU')
          return false
        }
        data.forEach((el) => {
          if (this.isExistMaTT(el.MA_TT)) {
            if (!el.LYDO_ID || (typeof el.LYDO_ID == 'string' && el.LYDO_ID.trim() == '')) {
              dsLoi.push({
                MA_TT: el.MA_TT,
                LOI: 'Lý do ID bị null'
              })
            } else {
              if (!this.isNumeric(el.LYDO_ID)) {
                dsLoi.push({
                  MA_TT: el.MA_TT,
                  LOI: 'Lý do ID phải là kiểu số'
                })
              } else if (!this.isExistLyDoId(el.LYDO_ID)) {
                dsLoi.push({
                  MA_TT: el.MA_TT,
                  LOI: 'Không tồn tại lý do ID trong dữ liệu'
                })
              }
            }
          } else {
            dsLoi.push({
              MA_TT: el.MA_TT,
              LOI: 'Không có trong danh sách'
            })
          }
        })
      }

      if (dsLoi.length > 0) {
        this.dsLoiExcel = dsLoi
        this.$bvModal.show('modal-error')
        return false
      }
      return true
    },
    isExistLyDoId(lyDoId) {
      return this.dsLyDoTra.some(function (el) {
        return el.LYDOTRA_ID === lyDoId
      })
    },
    isExistMaTT(maTT) {
      return this.tabData[this.currentTab].some(function (el) {
        return el.MA_TT === maTT
      })
    },
    isNumeric(str) {
      if (!isNaN(str)) return true
      if (typeof str != 'string') return false
      return !isNaN(str) && !isNaN(parseFloat(str))
    },
    checkReadFile(data) {
      const dsLoi = []
      if (data && data.length > 0) {
        let keys = Object.keys(data[0])
        data.forEach((el) => {
          let key = Object.keys(el)
          keys = [...new Set(keys.concat(key))]
        })
        if (keys.length < 2 || keys[0] != 'MA_TT' || keys[1] != 'NVQL_XL') {
          this.$toast.error('File Excel nhập vào phải đúng định dạng!\n - Chỉ có các trường theo thứ tự MA_TT, NVQL_XL')
          return false
        }
        data.forEach((el) => {
          if (!this.isExistMaTT(el.MA_TT)) {
            dsLoi.push({
              MA_TT: el.MA_TT,
              LOI: 'Không có trong danh sách'
            })
          }
        })
      } else {
        this.$toast.error('Không tìm thấy dữ liệu từ file Excel!')
      }
      if (dsLoi.length > 0) {
        this.dsLoiExcel = dsLoi
        this.$bvModal.show('modal-error')
        return false
      }
      return true
    },
    changeDataReadFile(data) {
      let row = 0
      let selectArray = []
      let temp = []
      this.tabData[this.currentTab].forEach((el) => {
        data.forEach((item) => {
          if (el.MA_TT == item.MA_TT) {
            el.ND_THUCHIEN = `<input type="text" id="noidung${item.MA_TT}" class="form-control" value="${item.NVQL_XL}">`
            selectArray.push(row)
            temp.push(el)
          }
        })
        row = row + 1
      })
      // this.tabData[this.currentTab] = [... this.tabData[this.currentTab]];
      // setTimeout(() => this.$refs[`table0${this.currentTab}`].grid.selectRows(selectArray), 1000);
      this.tabData[this.currentTab] = [...temp]
    },
    kiemtra_nhaplydo() {
      // let kiemtra = false
      // this.updatedRows = this.tabData[this.currentTab].filter(item => this.maTTChosen.includes(item.MA_TT))
      // this.updatedRows.forEach((item) => {
      //   if (item.LYDOTRA_ID === '')
      //     kiemtra = true
      // })
      // return kiemtra
      const updatedRows = this.tabData[this.currentTab].filter((x) => x._CHECK === true && (x.LYDOTRA_ID === null || x.LYDOTRA_ID === ''))
      return updatedRows.length > 0
    },

    validateForm() {
      // const dsSelected = this.$refs[`table0${this.currentTab}`].getSelectedRecords() || []
      const dsSelected = this.tabData[this.currentTab].filter((x) => x._CHECK === true)
      if (dsSelected.length === 0) {
        this.$toast.error(`Bạn chưa chọn phiếu !`)
        return false
      }
      if (!this.updateForm.ghiChu.trim()) {
        this.$toast.error(`Mã ${dsSelected[0].MA_TT} chưa nhập ghi chú`)
        return false
      }
      if (!this.updateForm.idLyDo && this.currentTab !== 4) {
        this.$toast.error(`Mã ${dsSelected[0].MA_TT} chưa nhập lý do trả`)
        return false
      }
      if (this.kiemtra_nhaplydo()) {
        this.$toast.error(`Mã ${dsSelected[0].MA_TT} test `)
        return false
      }

      return true
    },
    validateFormIgnore() {
      // const dsSelected = this.$refs[`table0${this.currentTab}`].getSelectedRecords() || []
      const dsSelected = this.tabData[this.currentTab].filter((x) => x._CHECK === true)
      if (dsSelected.length === 0) {
        this.$toast.error(`Bạn chưa chọn phiếu !`)
        return false
      }

      // const lyDoInvalid = this.tabData[this.currentTab].filter(item => this.maTTChosen.includes(item.MA_TT)
      //   && item.LYDOTRA_ID == null || item.LYDOTRA_ID == '')
      const lyDoInvalid = this.tabData[this.currentTab].filter((x) => x._CHECK === true && (x.LYDOTRA_ID === null || x.LYDOTRA_ID === ''))
      if (lyDoInvalid.length > 0) {
        this.$toast.error(
          'Mã ' +
            lyDoInvalid
              .map(function (x) {
                return x.MA_TT
              })
              .join(',') +
            ' chưa nhập lý do trả'
        )
        return false
      }

      // const ghiChuInvalid = this.tabData[this.currentTab].filter(item => this.maTTChosen.includes(item.MA_TT) && item.GHICHU == null || item.GHICHU == '')
      const ghiChuInvalid = this.tabData[this.currentTab].filter((x) => x._CHECK === true && (x.GHICHU === null || x.GHICHU === ''))
      if (ghiChuInvalid.length > 0) {
        this.$toast.error(
          'Mã ' +
            ghiChuInvalid
              .map(function (x) {
                return x.MA_TT
              })
              .join(',') +
            ' chưa nhập ghi chú'
        )
        return false
      }

      return true
    },
    validateCapNhat() {
      // const dsSelected = this.$refs[`table0${this.currentTab}`].getSelectedRecords() || []
      const dsSelected = this.tabData[this.currentTab].filter((x) => x._CHECK === true)
      if (dsSelected.length === 0) {
        this.$toast.error(`Bạn chưa chọn phiếu !`)
        return false
      }
      if ((this.currentTab === 2 || this.currentTab === 3 || this.currentTab === 5) && this.searchForm.ckbNgayHenTra && this.searchForm.ngayHenTra) {
        this.$toast.error(`Bạn chỉ được quyền xóa ngày hẹn chứ không được quyền sửa ! `)
        return false
      }

      if (this.searchForm.ckbNgayHenTra && this.searchForm.ngayHenTra && this.searchForm.ngayHenTra < new Date().setHours(0, 0, 0, 0)) {
        this.$toast.error(`Ngày hẹn trả không được nhỏ hơn ngày hiện tại`)
        return false
      }
      // if (!this.updateForm.idLyDo && this.currentTab !== 4) {
      //   this.$toast.error(`Bạn chưa chọn lý do trả thông tin cập nhật`)
      //   return false
      // }

      //   if (this.kiemtra_nhaplydo()) {
      //     this.$toast.error(`Bạn chưa nhập lý do cho hóa đơn đã chọn!`);
      //     return false;
      //   }
      return true
    },
    makeHuyTraHDBody(dsMaTT, pkieu, tabData) {
      const body = {
        pPhanVungId: this.$root.token.getPhanVungID(),
        pKyHoaDon: this.kyHoaDon,
        pTrangThaiHDId: pkieu,
        pData: []
      }
      tabData.forEach((item) => {
        if (dsMaTT.includes(item.MA_TT)) {
          body.pData.push({
            giaophieu_id: item.GIAOPHIEU_ID,
            thanhtoan_id: item.THANHTOAN_ID // item.HOADON_ID,
          })
        }
      })
      return body
    },
    makeHuySeriBody(dsMaTT, tabData) {
      const body = {
        pPhanVungId: this.$root.token.getPhanVungID(),
        pMaND: this.$auth.getMaNhanVien(),
        pKyHoaDon: this.kyHoaDon,
        pMayCN: '-',
        pIPCN: '-',
        pDSCNSeri: []
      }
      tabData.forEach((item) => {
        if (dsMaTT.includes(item.MA_TT)) {
          body.pDSCNSeri.push({
            ma_tt: item.MA_TT,
            seri: item.SERI,
            so_seri: item.SO_SERI,
            so_thung: item.SO_THUNG,
            tien_hd: item.TIEN_HD
          })
        }
      })
      return body
    },
    makeTraGiayBaoBody(dsMaTT, pkieu, tabData) {
      const body = {
        pPhanVungId: this.$root.token.getPhanVungID(),
        pKyHoaDon: this.kyHoaDon,
        pKieu: pkieu,
        pDSPhieu: []
      }
      let haveHoaDon = false
      let errors = []
      const selectedRows = tabData.filter((x) => x._CHECK === true)

      selectedRows.forEach((item) => {
        // let lyDoTra = this.updateForm.idLyDo !== null ? this.updateForm.idLyDo : item.LYDOTRA_ID !== null ? item.LYDOTRA_ID : null
        let lyDoTra = item.LYDOTRA_ID === null || item.LYDOTRA_ID === 19 ? null : item.LYDOTRA_ID
        // let ghiChu = this.updateForm.ghiChu !== null ? this.updateForm.ghiChu.trim() ? item.GHICHU !== null ? item.GHICHU.trim() : null
        let ghiChu = item.GHICHU !== null ? item.GHICHU.trim() : null
        if (lyDoTra === null) {
          errors.push(item.HOADON_ID)
        } else {
          body.pDSPhieu.push({
            giaophieu_id: item.GIAOPHIEU_ID,
            hoadon_id: item.HOADON_ID,
            lydo_id: lyDoTra,
            ghichu: ghiChu
          })
          if (item.LOAIPHIEU_ID === 1) {
            haveHoaDon = true
          }
        }
      }) // end forEach
      if (errors.length > 0) {
        this.$toast.error('Đã phát sinh lỗi. Hoá đơn ' + errors.join(',') + ' chưa nhập lý do trả.')
        return null
      }
      return { body, haveHoaDon }
    },
    makeCapNhatBody(dsMaTT, tabData) {
      const ngayDen = this.updateForm.ngayDen ? moment(this.updateForm.ngayDen).format('DD/MM/YYYY') : null
      const pNgayNhanGB = this.searchForm.ngayNhanGiayBao ? moment(this.searchForm.ngayNhanGiayBao).format('DD/MM/YYYY') : null
      const pNgayHen = this.searchForm.ngayHenTra ? moment(this.searchForm.ngayHenTra).format('DD/MM/YYYY') : null

      if (this.currentTab === 1999) {
        let body = {
          pPhanVungId: this.$root.token.getPhanVungID(),
          pKyHoaDon: this.kyHoaDon,
          pNgayNhanGB: this.searchForm.ckbNgayNhanGiayBao ? pNgayNhanGB : null,
          pNgayHen: this.searchForm.ckbNgayHenTra ? pNgayHen : null,
          pNgayDen: ngayDen,
          pLyDoId: null, // this.updateForm.idLyDo,
          pGhiChu: null, //this.updateForm.ghiChu.trim(),
          pDSCapNhat: [],
          pNguoiCN: this.$auth.getUserName(),
          pMayCN: '-',
          pIPCN: '-'
        }
        tabData.forEach((item) => {
          if (dsMaTT.includes(item.MA_TT)) {
            const ghiChuTb = document.getElementById(`ghichu${item.MA_TT}`).value
            const liDoTb = item.LYDOTRA_ID //document.getElementById(`combo${item.MA_TT}`).value;
            body.pDSCapNhat.push({
              hoadon_id: item.HOADON_ID,
              nd_thuchien: item.ND_THUCHIEN,
              lydo_id: liDoTb ? liDoTb : this.updateForm.idLyDo,
              ghichu: ghiChuTb ? ghiChuTb : this.updateForm.ghiChu.trim()
            })
          }
        })
        return body
      } else if (this.currentTab === 1) {
        let errors = []
        let body = {
          pPhanVungId: this.$root.token.getPhanVungID(),
          pKyHoaDon: this.kyHoaDon,
          pNgayNhanGB: this.searchForm.ckbNgayNhanGiayBao ? pNgayNhanGB : null,
          pNgayHen: this.searchForm.ckbNgayHenTra ? pNgayHen : null,
          pNgayDen: ngayDen,
          pLyDoId: this.updateForm.idLyDo,
          pGhiChu: this.updateForm.ghiChu !== null ? this.updateForm.ghiChu.trim() : null,
          pDSHoaDon: [],
          pNguoiCN: this.$auth.getUserName(),
          pMayCN: '-',
          pIPCN: '-'
        }
        tabData.forEach((item) => {
          if (dsMaTT.includes(item.MA_TT)) {
            const liDoTb = this.updateForm.idLyDo !== null && this.updateForm.idLyDo !== '' ? this.updateForm.idLyDo : item.LYDOTRA_ID !== null ? item.LYDOTRA_ID : null
            const ghiChu = this.updateForm.ghiChu !== null && this.updateForm.ghiChu !== '' ? this.updateForm.ghiChu.trim() : item.GHICHU !== null ? item.GHICHU : null

            if (liDoTb === null) {
              errors.push(item.MA_TT)
            } else {
              body.pDSHoaDon.push({
                hoadon_id: item.HOADON_ID,
                // nd_thuchien: noiDung ? noiDung.trim() : '',
                lydo_id: liDoTb,
                ghichu: ghiChu
              })
            }
          }
        })
        if (errors.length > 0) {
          this.$toast.error('Đã phát sinh lỗi. Các MA_TT ' + errors.join(',') + ' thiếu lý do trả.')
          return null
        }
        return body
      } else {
        let errors = []
        let body = {
          pPhanVungId: this.$root.token.getPhanVungID(),
          pKyHoaDon: this.kyHoaDon,
          pNgayNhanGB: this.searchForm.ckbNgayNhanGiayBao ? pNgayNhanGB : null,
          pNgayHen: this.searchForm.ckbNgayHenTra ? pNgayHen : null,
          pNgayDen: ngayDen,
          pLyDoId: this.updateForm.idLyDo ? this.updateForm.idLyDo : 1,
          pGhiChu: this.updateForm.ghiChu.trim(),
          pDSCapNhat: [],
          pNguoiCN: this.$auth.getUserName(),
          pMayCN: '-',
          pIPCN: '-'
        }
        tabData.forEach((item) => {
          if (dsMaTT.includes(item.MA_TT)) {
            // const liDoTb = item.LYDOTRA_ID !== null ? item.LYDOTRA_ID : this.updateForm.idLyDo
            // const ghiChu = item.GHICHU !== null ? item.GHICHU : this.updateForm.ghiChu !== null ? this.updateForm.ghiChu.trim() : null
            const liDoTb = this.updateForm.idLyDo !== null && this.updateForm.idLyDo !== '' ? this.updateForm.idLyDo : item.LYDOTRA_ID !== null ? item.LYDOTRA_ID : null
            const ghiChu = this.updateForm.ghiChu !== null && this.updateForm.ghiChu !== '' ? this.updateForm.ghiChu.trim() : item.GHICHU !== null ? item.GHICHU : null
            const noiDung = item.ND_THUCHIEN !== null ? item.ND_THUCHIEN.trim() : null
            if (liDoTb === null) {
              errors.push(item.MA_TT)
            } else {
              body.pDSCapNhat.push({
                hoadon_id: item.HOADON_ID,
                lydo_id: liDoTb,
                ghichu: ghiChu,
                // pLyDoId: liDoTb,
                // pGhiChu: ghiChu,
                nd_thuchien: noiDung
              })
            }
          }
        })
        if (errors.length > 0) {
          this.$toast.error('Đã phát sinh lỗi. Các MA_TT ' + errors.join(',') + ' thiếu lý do trả.')
          return null
        }
        return body
      }
    },
    makeTraTronBoBody(dsMaTT, tabData) {
      let body = {
        pPhanVungId: this.$root.token.getPhanVungID(),
        pKyHoaDon: this.kyHoaDon,
        pKieu: 2,
        pDSPhieu: []
      }
      tabData.forEach((item) => {
        if (dsMaTT.includes(item.MA_TT)) {
          // const noiDungDOM = document.getElementById(`noidung${item.MA_TT}`)
          // const liDoTbDOM = document.getElementById(`combo${item.MA_TT}`)
          // const ghiChuDOM = document.getElementById(`ghichu${item.MA_TT}`)
          // const noiDung = noiDungDOM ? noiDungDOM.value : ''
          // const liDoTb = liDoTbDOM ? liDoTbDOM.value : null
          // const ghiChu = ghiChuDOM ? ghiChuDOM.value : null
          const ghiChu = item.GHICHU !== null ? item.GHICHU.trim() : null
          body.pDSPhieu.push({
            hoadon_id: item.HOADON_ID,
            giaophieu_id: item.GIAOPHIEU_ID,
            lydo_id: item.LYDOTRA_ID, // liDoTb ? liDoTb : this.updateForm.idLyDo,
            ghichu: ghiChu ? ghiChu.trim() : this.updateForm.ghiChu.trim()
          })
        }
      })
      return body
    },
    dsLoaiPhieuDropDown: async function () {
      try {
        var rs = await this.$root.context.get('/web-thuno/api/thu-no/nguyen-nhan-no/lay-danh-sach-loai-phieu')
        this.dsLoaiPhieu = rs.data.filter((item) => item.LOAIPHIEU_ID != 7)
        if (this.dsLoaiPhieu.length > 0) {
          this.searchForm.idLoaiPhieu = this.dsLoaiPhieu[0].LOAIPHIEU_ID
        }
      } catch (error) {}
    },
    dsLyDoDropDown: async function () {
      try {
        var rs = await this.$root.context.get('/web-thuno/api/thu-no/nhan-phieu/lay-danh-sach-ly-do-tra')
        this.dsLyDoTraChung = rs.data
        // if (rs.data) {
        //   this.updateForm.idLyDo = rs.data[0].LYDOTRA_ID;
        // }
      } catch (error) {
      } finally {
      }
    },
    dsLuotGiaoDropDown: async function () {
      const body = {
        //  pPhanVungId: 26,//this.$root.token.getPhanVungID(),
        pChuKyNo: this.kyHoaDon,
        //  `${moment(new Date())
        //   .subtract(1, "months")
        //   .endOf("month")
        //   .format("YYYYMM")}01`, //20210301,
        // pNVTCId: this.dsNVTCId, //"3789,12901",
        pNVTCId: this.dsNVTCId.join(','),
        pKieu: 5, //this.$root.token.getPhanVungID() == 26 ? 5 : 1, // HP 5 else 1
        pLanTaoId: null
      }
      // console.log(body)
      try {
        this.loading(true)
        var rs = await this.$root.context.post('/web-thuno/api/thu-no/nhan-phieu/lay-danh-sach-luot-giao', body)
        this.dsLuotGiao = rs.data
        this.keyDSLuotGiao++
      } catch (error) {
      } finally {
        this.loading(false)
      }
    },
    getTSMD: async function () {
      try {
        var rs = await this.$root.context.get('/web-thuno/api/thu-no/thong-tin-khach-hang/lay-danh-sach-tham-so-mac-dinh', {
          pPhanVungid: this.$root.token.getPhanVungID(),
          pMaThamSo: 'DS_NHANPHIEU_THEO_AM'
        })
        if (rs.data && rs.data.length > 0) {
          this.DS_NHANPHIEU_THEO_AM = rs.data[0].TEN_TS
        }
      } catch (error) {
      } finally {
      }
    },
    getDataV1: async function () {
      // Lay_ds_hoadon_giao_nvtc_v3
      // const param = {
      //   pPhanVungId: this.$root.token.getPhanVungID(),
      //   pKyHoaDon: "20210301",
      //   pLoaiPhieuId: this.searchForm.idLoaiPhieu,
      //   pKieu: this.currentTab, //1,//tabNo
      //   pNhanVienId: 38853,
      //   pGiaoPhieuId: this.searchForm.idLuotGiao
      // };
      const body = {
        // "pPhanVungId":28,
        pKyHoaDon: this.kyHoaDon,
        pLoaiPhieuId: this.searchForm.idLoaiPhieu,
        pKieu: this.currentTab, // 2,
        pNhanVienId: this.$auth.getNhanVienID(), // 993,
        pGiaoPhieuId: this.searchForm.idLuotGiao // "1362436,1368732"
      }

      try {
        var rs = await this.$root.context.post('/web-thuno/api/thu-no/nhan-phieu/lay-danh-sach-hoa-don-giao-nhan-vien-thu-cuoc-v2', body)

        // this.dataExport = rs.data.data
        this.handleData(rs.data.data)
      } catch (error) {
      } finally {
      }
    },
    handleData(srcData) {
      // console.log('srcData', srcData)
      let dataParse = []
      if (srcData && srcData.length > 0) {
        dataParse = srcData.map((item) => {
          // let opt = `<select id="combo${item.MA_TT}" class="form-control">`
          // opt += `<option value=""></option>`
          // this.dsLyDoTra.forEach((el) => {
          //   if (el.LYDOTRA_ID == item.LYDOTRA_ID) {
          //     opt += `<option selected="selected" value="${el.LYDOTRA_ID}">${el.NOIDUNG}</option>`
          //   } else {
          //     opt += `<option value="${el.LYDOTRA_ID}">${el.NOIDUNG}</option>`
          //   }
          // })
          // opt += `</select>`
          return {
            ...item,
            _CHECK: false
          }
        })
      }
      // console.log('dataParse', dataParse)
      if (!this.searchForm.txtTimKiem) {
        this.tabData[this.currentTab] = dataParse
        this.tabKey[this.currentTab]++
      } else {
        this.tabData[this.currentTab] = dataParse.filter((ele) => ele.MA_TT.indexOf(this.searchForm.txtTimKiem.trim()) > -1)
      }
      // setTimeout(() => {
      //   $('table tfoot tr td:nth-child(2)').html(`<input type='text' class='form-control tright' width="20px" disabled value='123'>`);
      // }, 1000);
    },
    getLyDo(id) {
      let nd = id
      this.dsLyDoTra.forEach((item) => {
        if (item.LYDOTRA_ID == id) {
          nd = item.NOIDUNG
        }
      })
      return nd
    },
    getDataV2: async function () {
      //LAY_DS_HOADON_GIAO_NVTC_NV
      const param = {
        pPhanVungId: this.$root.token.getPhanVungID(),
        pKyHoaDon: this.kyHoaDon,
        pLoaiPhieuId: this.searchForm.idLoaiPhieu,
        pKieu: this.currentTab, //1,//tabNo
        pNhanVienId: this.$auth.getNhanVienID() //38853
      }
      // console.log(param);
      try {
        var rs = await this.$root.context.get('/web-thuno/api/thu-no/nhan-phieu/lay-danh-sach-hoa-don-giao-nhan-vien-thu-cuoc-nv', param)
        this.dataExport = rs.data.data
        this.handleData(rs.data.data)
      } catch (error) {}
    },
    getDataV3: async function () {
      //LAY_DS_HOADON_GIAO_NVTC_2
      // const param = {
      //   pPhanVungId: this.$root.token.getPhanVungID(),
      //   pKyHoaDon: this.kyHoaDon,
      //   pLoaiPhieuId: this.searchForm.idLoaiPhieu,
      //   pKieu: this.currentTab, // 1,//tabNo
      //   pGiaoPhieuId: this.searchForm.idLuotGiao
      // };
      // console.log(param);
      const body = {
        pKyHoaDon: this.kyHoaDon,
        pLoaiPhieuId: this.searchForm.idLoaiPhieu,
        pKieu: this.currentTab,
        pGiaoPhieuId: this.searchForm.idLuotGiao //"675239,675231,673020,672995,672567,671999,671561,671134,670883,670697,670215,669946,669693"
      }
      this.$root.showLoading(true)
      try {
        // var rs = await this.$root.context.post(
        //   "/web-thuno/api/thu-no/nhan-phieu/lay-danh-sach-hoa-don-giao-nhan-vien-thu-cuoc",
        //   body,
        // );
        const { data } = await this.axios.post('/web-thuno/api/thu-no/nhan-phieu/lay-danh-sach-hoa-don-giao-nhan-vien-thu-cuoc', body, {
          params: {
            pageNo: 0,
            pageSize: 10000,
            totalRow: 1
          }
        })
        this.dataExport = data.data.data
        this.handleData(data.data.data)
        this.$root.showLoading(false)
      } catch (error) {
        this.$root.showLoading(false)
      }
    },
    traGiayBao: async function (body) {
      try {
        const { data } = await this.axios.put('/web-thuno/api/thu-no/nhan-phieu/tra-giay-bao', body)
        if (data && data.error == '200') {
          this.onClickDanhSach()
        }
      } catch (error) {
        if (error.data.message_detail !== undefined) {
          this.$toast.error(error.data.message_detail)
        } else console.log(error)
      }
    },
    traHoaDon: async function (body) {
      // console.log(body);
      try {
        const { data } = await this.axios.put('/web-thuno/api/thu-no/nhan-phieu/tra-hoa-don', body)
        // console.log(data);
        if (data && data.error == '200') {
          this.onClickDanhSach()
        }
      } catch (error) {
        if (error.data.message_detail !== undefined) {
          this.$toast.error(error.data.message_detail)
        } else console.log(error)
      }
    },
    huySeri: async function (body) {
      // console.log(body);
      try {
        const { data } = await this.axios.put('/web-thuno/api/thu-no/nhan-phieu/cap-nhat-huy-seri', body)
        // console.log(data);
      } catch (error) {
        if (error.data.message_detail !== undefined) {
          this.$toast.error(error.data.message_detail)
        } else console.log(error)
      }
    },
    huyTraHoaDon: async function (body) {
      // console.log(body);
      try {
        const { data } = await this.axios.put('/web-thuno/api/thu-no/nhan-phieu/huy-tra-phieu-hoa-don', body)
        // console.log(data);
        if (data && data.error === '200') {
          this.onClickDanhSach()
        }
      } catch (error) {
        if (error.data.message_detail !== undefined) {
          this.$toast.error(error.data.message_detail)
        } else console.log(error)
      }
    },
    capNhatData: async function (body, url) {
      // console.log(body);
      try {
        this.loading(true)
        const { data } = await this.axios.put(url, body)
        console.log(data)
        if (data && data.error === '200') {
          this.$toast.success(`Cập nhật dữ liệu thành công`)
          // this.updatedRows = this.tabData[this.currentTab].filter(item => this.maTTChosen.includes(item.MA_TT))
          this.onClickDanhSach(true, true)
        }
      } catch (error) {
        if (error.data.message_detail !== undefined) {
          this.$toast.error(error.data.message_detail)
        } else console.log(error)
      } finally {
        this.loading(false)
      }
    },
    traTronBo: async function (body) {
      // console.log(body);
      try {
        const { data } = await this.axios.put('/web-thuno/api/thu-no/nhan-phieu/tra-tron-bo', body)
        if (data && data.error === '200') {
          this.$toast.success(`Cập nhật dữ liệu thành công`)
          this.onClickDanhSach()
        }
      } catch (error) {
        if (error.data.message_detail !== undefined) {
          this.$toast.error(error.data.message_detail)
        } else console.log(error)
      }
    },
    huyTraTronBo: async function (body) {
      // console.log(body);
      try {
        const { data } = await this.axios.put('/web-thuno/api/thu-no/nhan-phieu/huy-tra-phieu-tron-bo', body)
        if (data && data.error === '200') {
          this.onClickDanhSach()
        }
      } catch (error) {
        if (error.data.message_detail !== undefined) {
          this.$toast.error(error.data.message_detail)
        } else console.log(error)
      }
    },
    getDsNVTC: async function () {
      try {
        const { data } = await this.$root.context.get('/web-thuno/api/thu-no/sp-lay-danh-sach-nhan-vien-tinh-cuoc')
        if (data !== undefined && data.data !== undefined && data.data.length > 0) this.dsNVTC = data.data
        else this.dsNVTC = []
      } catch (error) {
        if (error.data.message_detail !== undefined) {
          this.$toast.error(error.data.message_detail)
        } else console.log(error)
      }
    },
    getKyCuocHH: async function () {
      try {
        const { data } = await this.$root.context.get('/web-thuno/api/thu-no/common/ky-cuoc-hien-hanh')
        if (data.length > 0) {
          this.kyHoaDon = data[0].KYCUOC
        }
      } catch (error) {
        if (error.data.message_detail !== undefined) {
          this.$toast.error(error.data.message_detail)
        } else console.log(error)
      }
    },
    onFilteringNVTC(e) {
      var query = new Query()
      query = e.text !== '' ? query.where('TEN_HIENTHI', 'contains', e.text, true) : query
      e.updateData(this.dsNVTC, query)
    },
    checkBoxChange(args) {
      if (args.target.classList.contains('e-checkselectall')) {
        // click check/uncheck all
        for (let i = 0; i < this.tabData[this.currentTab].length; i++) {
          this.tabData[this.currentTab][i]._CHECK = args.checked
        }
        this.refreshAggPhieu()
      }
    },
    dsPhieuRowSelected(args) {
      // console.log('sel', args)
      if (!args.isHeaderCheckboxClicked && args.isInteracted) {
        // không phải call by click checkbox header
        let f = this.tabData[this.currentTab].find((x) => x.MA_TT + '' === args.data.MA_TT + '')
        if (f !== undefined) f._CHECK = true
        this.refreshAggPhieu()
      }
    },
    dsPhieuRowDeselected(args) {
      console.log('desel', args)
      if (!args.isHeaderCheckboxClicked && args.isInteracted) {
        // không phải call by click checkbox header & paging
        let f = this.tabData[this.currentTab].find((x) => x.MA_TT + '' === args.data.MA_TT + '')
        if (f !== undefined) f._CHECK = false
        this.refreshAggPhieu()
      }
    },
    refreshAggPhieu() {
      this.$refs['table0' + this.currentTab].$refs.grid.ej2Instances.aggregateModule.refresh()
    },
    dsPhieuDatabound(args) {
      // console.log('db')
      this.refreshGrid()
    },
    refreshGrid() {
      let obj = this.$refs['table0' + this.currentTab].$refs.grid.ej2Instances
      if (obj.currentViewData.length > 0) {
        let data = obj.currentViewData
        let selectedCurrentPage = []
        data.forEach((value, index) => {
          if (value._CHECK) {
            selectedCurrentPage.push(index)
          }
        })
        obj.selectRows(selectedCurrentPage)
      }
    },
    recordDoubleClick(args) {
      console.log(args)
      return false
    },
    queryCellInfo(args) {
      // if (args.column.field === 'LYDOTRA_ID' || args.column.field === 'GHICHU') {
      //   args.cell.classList.add('grid-green')
      // }
    },
    actionComplete(args) {
      if (args.action === 'edit' && args.requestType === 'save') {
        let data = cloneArray(this.tabData[this.currentTab])
        // console.log(data.filter(x => x._CHECK === true))
        for (let i = 0; i < data.length; i++) {
          if (data[i].MA_TT + '' === args.data.MA_TT + '') {
            data[i].GHICHU = args.data.GHICHU
            data[i].LYDOTRA_ID = args.data.LYDOTRA_ID
            data[i].ND_THUCHIEN = args.data.ND_THUCHIEN
            data[i]._CHECK = true
            break
          }
        }
        this.tabData[this.currentTab] = cloneArray(data)
      }
    },
    keyPressedHandler(args) {
      if (args.key === 'Insert') {
        this.checkAll(true)
      } else if (args.key === 'Delete') {
        this.checkAll(false)
      }
    },

    checkAll(f) {
      let temp = [...this.tabData[this.currentTab]]
      if (f) {
        // true
        for (let i = 0; i < temp.length; i++) temp[i]._CHECK = true
      } else {
        for (let i = 0; i < temp.length; i++) temp[i]._CHECK = false
      }
      this.tabData[this.currentTab] = [...temp]
    },
    getColumnTemplateDataLink(parent) {
      return function () {
        return {
          template: {
            template: `     <div class="info-row" v-show="data.DATA_LINK">
                            <input type="text" v-model=data.DATA_LINK class="bg-none bor0 fw6" />                            
                              <button class="btn btn-sm   btn-primary  lh14 pad3" 
                              @click="parent.copyURL(data.DATA_LINK)" title="Copy Link">
                                <span class="fa fa-copy"></span>
                            </button>                          
                            </div>                            
                        `,
            data() {
              return {
                parent: parent,
                data: {}
              };
            }
          }
        };
      };
    },
 
  async copyURL(mytext) {
    try {
      await navigator.clipboard.writeText(mytext);      
    } catch(ex) {
    //
    }
  }
  },
  provide: {
    multiselect: [CheckBoxSelection]
  }
}
</script>
