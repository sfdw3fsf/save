<template src="./GachNoFileChuyenKhoan.html"></template>
<style scoped src="./style.scss"></style>
<script>
import API from '../api/GachNoFileChuyenKhoanAPI.js'
import CommonAPI from '../api/Commons.js'
import HDAPI from '../api/HoaDonDienTuAPI.js'
import { mapActions, mapGetters } from 'vuex'
import PopupDieuKienLoc from './Components/PopupDieuKienLoc.vue'
import PhieuGachNoTienMat from '../GachNo/components/PhieuGachNoTienMat'
import { export_json_to_excel } from '../TraCuuDichVu/Export2Excel'
import { currency } from '@/filters/currency'
import moment from 'moment'
import xlsx from 'xlsx'
import Vue from 'vue'
import { SplitterPlugin } from '@syncfusion/ej2-vue-layouts'
import {Splitter} from '@syncfusion/ej2-layouts'
import { DialogUtility } from '@syncfusion/ej2-popups'
// import { QueryCellInfoEventArgs } from '@syncfusion/ej2-vue-grids';
Vue.use(SplitterPlugin)
Vue.use(DialogUtility)
let DialogObj

export default {
  components: {
    PopupDieuKienLoc,
    appPhieuGachNoTienMat: PhieuGachNoTienMat
  },
  name: 'GachNoFileChuyenKhoan',

  async created () {
    await Promise.all([
      this.getThongTinChuKyNo(),
      this.getHTTTNganHang(),
      this.getDSTimKiem(),
      this.getDSNganHangCcbs(),
      this.getMaVungCd()
    ])

    if (Object.keys(this.kyHoaDonHienTaiGetter).length <= 0) { await this.getKyHoaDonHienTai() }
    if (Object.keys(this.kyHoaDonHienTaiGetter).length > 0) { this.params.kyHoaDon_Selected = this.kyHoaDonHienTaiGetter.KYCUOC }

    if (this.danhSachNhanVienThuNganGetter == null || this.danhSachNhanVienThuNganGetter.length === 0) { await this.getDanhSachNhanVienThuNgan() }
    if (this.danhSachNhanVienThuNganGetter != null && this.danhSachNhanVienThuNganGetter.length > 0) { this.params.nhanVienTN_Selected = this.danhSachNhanVienThuNganGetter[0].NHANVIEN_ID }

    if (this.danhSachHinhThucThanhToanGetter == null || this.danhSachHinhThucThanhToanGetter.length === 0) { await this.getDanhSachNganHangGachNo() }
    if (this.danhSachNganHangGachNoGetter != null && this.danhSachNganHangGachNoGetter.length > 0) {
      this.params.nganHang_Options = this.danhSachNganHangGachNoGetter
      // this.danhSachNganHangGachNoGetter.forEach(item => {
      //   this.params.nganHang_Options.push({ id: item.NGANHANG_ID, text: item.TEN_NH })
      // })
      this.params.nganHang_Selected = this.params.nganHang_Options[0].NGANHANG_ID
      this.params.nganhang.id = this.params.nganHang_Options[0].NGANHANG_ID
      this.params.text = this.params.nganHang_Options[0].TEN_NH
      this.params.ma_gom = this.params.nganHang_Options[0].MA_GOM
    }

    if (this.$refs.dlgDieuKienLoc.params.tieuChi0_Checked) { this.setFilter(this.$refs.dlgDieuKienLoc.params) }

    // Khởi tạo column của grid tại đây vì statusTemplate reference phải được khởi tạo trước:
    this.chuyenKhoan_grid.columns = [
      {fieldName: 'STT', headerText: 'STT', allowFiltering: true, allowSorting: false, width: 70, freeze: 'left'},
      {fieldName: 'NGAY_NH', headerText: 'Ngày ngân hàng', allowFiltering: true, allowSorting: false, width: 150/*, isGroupedColumn: true*/},
      {fieldName: 'TEN_NHOM', headerText: 'Tên nhóm', allowFiltering: true, allowSorting: false, width: 120, cssClass: 'ten-nhom'},
      {fieldName: 'TIENTRA', headerText: 'Tiền trả', allowFiltering: true, allowSorting: false, width: 110, cssClass: 'tien-tra', format: 'N0', type: 'number'},
      {fieldName: 'STK', headerText: 'Số tài khoản', allowFiltering: true, allowSorting: false, width: 130, cssClass: 'text-left'},
      {fieldName: 'GHICHU', headerText: 'Ghi chú', allowFiltering: true, allowSorting: false, width: 120},
      {fieldName: 'NOIDUNG_CT', headerText: 'Nội dung', allowFiltering: true, allowSorting: false, width: 300},
      {fieldName: 'CHUNGTU', headerText: 'Chứng từ', allowFiltering: true, allowSorting: false, width: 110},
      {fieldName: 'LOI', headerText: 'Lỗi', allowFiltering: true, allowSorting: false, width: 120},
      {fieldName: '', headerText: '', allowFiltering: true, allowSorting: false, width: 60, template: this.statusTemplate, cssClass: 'text-center'}
    ]
  },

  computed: {
    ...mapGetters('qltnCommon', [
      'danhSachNganHangGachNoGetter',
      'danhSachNhanVienThuNganGetter',
      'kyHoaDonHienTaiGetter'
    ]),
    get_thungan () {
      let ret = this.danhSachNhanVienThuNganGetter.filter(x => x.NHANVIEN_ID === this.params.nhanVienTN_Selected)
      if (ret.length > 0) { return ret[0] }
      return null
    },
    DSCKAggColumns () {
      return [
        { field: 'STT', type: 'Custom', footerTemplate: this.customTemplate, customAggregateFn: this.countCKAgg }
      ]
    },
    DSGNAggregatesColumns () {
      return [
        { field: 'STT', type: 'Custom', footerTemplate: this.customTemplate, customAggregateFn: this.countAgg },
        { field: 'TIENTRA', type: 'Custom', footerTemplate: this.customTemplate, customAggregateFn: this.tienTraAggregate }
      ]
    },
    DSDSAggColumns () {
      return [
        { field: 'STT', type: 'Custom', footerTemplate: this.customTemplate, customAggregateFn: this.countDSAgg },
        { field: 'TIENTRA', type: 'Custom', footerTemplate: this.customTemplate, customAggregateFn: this.tienTraDSAgg }
      ]
    }
  },

  data () {
    return {
      params: {
        nganhang: {
          id: '',
          text: '',
          ma_gom: ''
        },
        kyHoaDon_Options: [],
        kyHoaDon_Selected: null,
        DKTimKiem_Options: [],
        nganHang_Options: [],
        nganHang_Selected: null,
        ngayTT: new Date(),
        today: new Date(),
        HTTTNganHang_Options: [],
        HTTTNganHang_Selected: null,
        nhanVienTN_Selected: null,
        checkSTT: true,
        isPopupShow: false,
        tabActive: 0,
        timTheoCT: true
      },
      common_params: {
        ngayXoaLui: -1,
        ngayGachLui_Thang: -1
      },
      nganHangNEO_items: [],
      chuyenKhoan_grid: {
        columns: [],
        items: [],
        checked: [],
        key: 1
      },
      KQDoiSoat_grid: {
        columns: [
          {fieldName: 'STT', headerText: 'STT', allowFiltering: true, allowSorting: false, width: 70},
          {fieldName: 'NGAY_NH', headerText: 'Ngày ngân hàng', allowFiltering: true, allowSorting: false, width: 150/*, isGroupedColumn: true*/},
          {fieldName: 'TEN_NHOM', headerText: 'Tên nhóm', allowFiltering: true, allowSorting: false, width: 120, cssClass: 'ten-nhom'},
          {fieldName: 'MA_TT', headerText: 'Mã TT', allowFiltering: true, allowSorting: false, width: 120, cssClass: 'ma-tt'},
          {fieldName: 'MA_TT_NEO', headerText: 'Mã TT Neo', allowFiltering: true, allowSorting: false, width: 120},
          {fieldName: 'TIENTRA', headerText: 'Tiền trả', allowFiltering: true, allowSorting: false, width: 110, cssClass: 'tien-tra', format: 'N0', type: 'number'},
          {fieldName: 'TIEN_NO', headerText: 'Tiền nợ', allowFiltering: true, allowSorting: false, width: 120, format: 'N0', type: 'number'},
          {fieldName: 'TIEN_GOC', headerText: 'Còn nợ', allowFiltering: true, allowSorting: false, width: 120, format: 'N0', type: 'number'},
          {fieldName: 'CHUNGTU', headerText: 'Chứng từ', allowFiltering: true, allowSorting: false, width: 110},
          {fieldName: 'TEN_TT', headerText: 'Tên TT', allowFiltering: true, allowSorting: false, width: 200},
          {fieldName: 'STK', headerText: 'Số tài khoản', allowFiltering: true, allowSorting: false, width: 130, cssClass: 'text-left'},
          {fieldName: 'GHICHU', headerText: 'Ghi chú', allowFiltering: true, allowSorting: false, width: 120},
          {fieldName: '_CHECK', headerText: 'Chọn', visible: true, allowFiltering: true, allowSorting: false}
        ],
        items: [],
        checked: [],
        key: 1
      },
      KQKhongKhop_grid: {
        columns: [
          {fieldName: 'STT', headerText: 'STT', allowFiltering: true, allowSorting: false, width: 70},
          {fieldName: 'NGAY_NH', headerText: 'Ngày ngân hàng', allowFiltering: true, allowSorting: false, width: 150, isGroupedColumn: true},
          {fieldName: 'TEN_NHOM', headerText: 'Tên nhóm', allowFiltering: true, allowSorting: false, width: 120, cssClass: 'ten-nhom'},
          {fieldName: 'TIENTRA', headerText: 'Tiền trả', allowFiltering: true, allowSorting: false, width: 110, cssClass: 'tien-tra', format: 'N0', type: 'number'},
          {fieldName: 'STK', headerText: 'Số tài khoản', allowFiltering: true, allowSorting: false, width: 130, cssClass: 'text-left'},
          {fieldName: 'GHICHU', headerText: 'Ghi chú', allowFiltering: true, allowSorting: false, width: 120},
          {fieldName: 'CHUNGTU', headerText: 'Chứng từ', allowFiltering: true, allowSorting: false, width: 110},
          {fieldName: 'NOIDUNG_CT', headerText: 'Nội dung', allowFiltering: true, allowSorting: false, width: 300},
          {fieldName: 'LOI', headerText: 'Lỗi', allowFiltering: true, allowSorting: false, width: 120},
          {fieldName: 'STATUS', headerText: '', allowFiltering: true, allowSorting: false, width: 40}
        ],
        items: [],
        checked: [],
        key: 1
      },
      DSGN_grid: {
        items: [],
        checked: [],
        key: 1,
        aggSum: 0
      },
      DSLoi: {
        columns: [
          { headerText: 'TEN_NHOM', fieldName: 'TEN_NHOM' },
          { headerText: 'TIENTRA', fieldName: 'TIENTRA' },
          { headerText: 'GHICHU_LOI', fieldName: 'GHICHU_LOI' }
        ],
        items: []
      },
      filter: {
        selected: {},
        dieuKien: '',
        thuTu: '',
        kieu_GN: ''
      },
      ketQuaTitle: 'Thông tin chi tiết kết quả đối soát',
      filterDisplay: 'Chọn tiêu chí tìm kiếm',
      filePath: '',
      ws_Items: [],
      phieuTra_data: {
        header: 'Danh sách các phiếu thanh toán gạch nợ chuyển khoản',
        maTT: '',
        maTB: '',
        kyCuoc: '',
        nhomHTTT: 3, // GACHNO_NGANHANG
        kieuTT: 1,
        maTinh_ThuHo: 0
      },
      hideTable: true,
      customTemplate: function () {
        return { template: Vue.component('footerTemplate', {
          template: `<input type="text" class="form-control tright" v-model="data.Custom" :disabled="true" style="margin: 7px 0px 7px 0px;" />`,
          data () { return { data: {data: {}}} }
        })
        }
      },
      statusTemplate () {
        return { template: Vue.component('columnTemplate', {
          template: `<div>
                                    <span v-if="data.STATUS === '0'" class="one-alert f20 text-warning"></span>
                                    <span v-else-if="data.STATUS === '1'" class="one-check f20 text-success"></span>
                                </div>`,
          data () { return { data: {} } }})
        }
      },
      dauso_tinh: '',
      sep: '\\ ( . _ - ) / ,'.split(' ')
    }
  },

  methods: {
    ...mapActions('qltnCommon', [
      'getDanhSachNganHangGachNo',
      'getDanhSachNhanVienThuNgan',
      'getKyHoaDonHienTai'
    ]),

    onCreate (e) {
      this.splitterObj = new Splitter({
        paneSettings: [
          { size: '50%', min: '20%' },
          { size: '50%', min: '20%'}
        ],
        orientation: 'Vertical'
      })
      this.splitterObj.appendTo('#vertical_splitter')
    },
    countCKAgg () {
      return currency(this.chuyenKhoan_grid.items.length)
    },
    countAgg () {
      const count = this.DSGN_grid.items.filter(x => x._CHECK === true).length
      return currency(count) + '/' + currency(this.DSGN_grid.items.length)
    },
    tienTraAggregate () {
      const total = this.DSGN_grid.items.reduce((a, b) => +a + +b.TIENTRA, 0)
      const selected = this.DSGN_grid.items.filter(x => x._CHECK === true).reduce((a, b) => +a + +b.TIENTRA, 0)
      return currency(selected) + '/' + currency(total)
      // return '0'
    },
    countDSAgg () {
      const count = this.KQDoiSoat_grid.items.filter(x => x._CHECK === true).length
      return currency(count) + '/' + currency(this.KQDoiSoat_grid.items.length)
    },
    tienTraDSAgg () {
      const total = this.KQDoiSoat_grid.items.reduce((a, b) => +a + +b.TIENTRA, 0)
      const selected = this.KQDoiSoat_grid.items.filter(x => x._CHECK === true).reduce((a, b) => +a + +b.TIENTRA, 0)
      return currency(selected) + '/' + currency(total)
    },

    setFilter (e) {
      if (!e.tieuChi0_Checked && !e.tieuChi1_Checked && !e.tieuChi2_Checked && !e.tieuChi3_Checked) { this.filterDisplay = 'Chọn tiêu chí tìm kiếm' } else {
        this.filterDisplay = 'Tiêu chí: '
        if (e.tieuChi0_Checked) {
          let filter = this.params.DKTimKiem_Options.find(i => i.KIEUTK_ID === e.tieuChi0_Selected)
          this.filterDisplay += filter.KIEU_TK + ','
        }
        if (e.tieuChi1_Checked) {
          let filter = this.params.DKTimKiem_Options.find(i => i.KIEUTK_ID === e.tieuChi1_Selected)
          this.filterDisplay += filter.KIEU_TK + ','
        }
        if (e.tieuChi2_Checked) {
          let filter = this.params.DKTimKiem_Options.find(i => i.KIEUTK_ID === e.tieuChi2_Selected)
          this.filterDisplay += filter.KIEU_TK + ','
        }
        if (e.tieuChi3_Checked) {
          let filter = this.params.DKTimKiem_Options.find(i => i.KIEUTK_ID === e.tieuChi3_Selected)
          this.filterDisplay += filter.KIEU_TK + ','
        }
        this.filterDisplay.slice(0, -1)
      }
      this.filter.selected = e
    },

    clickTimKiem(){
        this.onTimKiem()
        this.params.isPopupShow = false
    },

    async confirmDialog (ds, count) {
      try {
        await this.$bvModal.msgBoxConfirm(`Có ${count} bản ghi sai dữ liệu! Bạn có muốn xuất file excel không?`, {
          'title': 'Thông báo',
          'size': 'lg',
          'buttonSize': 'lg',
          'okVariant': 'primary',
          'okTitle': 'Đồng ý',
          'cancelTitle': 'Hủy bỏ',
          'hideHeaderClose': false,
          'centered': true,
          'modal-class': ['f18', 'text-center']
        })
          .then(async (value) => {
            if (value) {
              this.exportExcel('fileSaiDuLieu', ds)
            }
          })
          .catch(err => {
            // An error occurred
            console.log(err)
          })
      } catch (error) {
        this.$toast.error('Có lỗi xảy ra, vui lòng thử lại!')
      }
    },

    toUpperCaseUploadData (obj) {
      let output = {}
      for (let i in obj) {
        if (Object.prototype.toString.apply(obj[i]) === '[object Object]') {
          output[i.toUpperCase()] = this.toUpperCaseUploadData(obj[i])
        } else if (Object.prototype.toString.apply(obj[i]) === '[object Array]') {
          output[i.toUpperCase()] = []
          output[i.toUpperCase()].push(this.toUpperCaseUploadData(obj[i][0]))
        } else {
          output[i.toUpperCase()] = obj[i]
        }
      }
      return output
    },

    async onChangeFile (e) {
      const files = e.target.files
      let vm = this

      if (!files.length) {
        return
      } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
        return this.$toast.error('File excel không đúng định dạng!')
      }

      const fileReader = new FileReader()
      fileReader.onload = async (ev) => {
        try {
          vm.ws_Items = []
          const data = ev.target.result
          const workbook = xlsx.read(data, { type: 'binary', cellDates: true })
          const wsname = workbook.SheetNames[0]
          let worksheet = workbook.Sheets[wsname]
          const ws = xlsx.utils.sheet_to_json(worksheet)

          let sheet = workbook.Sheets[wsname]

          // Validate input file:
          if (Object.keys(ws[0]).length === 0 || Object.keys(ws[0]).length > 8) {
            vm.loading(false)
            return vm.$toast.error('Dữ liệu file excel chưa đúng số lượng cột. Bạn hãy xuất file mẫu và điền thông tin theo file mẫu!')
          }


          let exceldata1 = xlsx.utils.sheet_to_json(worksheet, { header: 1 })

          let columns = []

          exceldata1[0].forEach(item => {
            columns.push(item.toUpperCase())
          })

          if (!columns.includes('STT') ||
            !columns.includes('TEN_NHOM') ||
            !columns.includes('TIENTRA') ||
            !columns.includes('STK') ||
            !columns.includes('CHUNGTU') ||
            !columns.includes('GHICHU') ||
            !columns.includes('NOIDUNG_CT')) {
              vm.loading(false)
              return vm.$toast.error('File không đúng cấu trúc STT,TEN_NHOM, TIENTRA,CHUNGTU,STK,GHICHU,NGAY_NH,NOIDUNG_CT')
            }

            let successData = []
            ws.forEach(item => {
              successData.push(vm.toUpperCaseUploadData(item))
            })

            // ws = [...successData]

          if (successData.length > 0) {
            vm.DSLoi.items = []
            for (let item of successData) {
              if (item.NGAY_NH === null || item.NGAY_NH === undefined || item.NGAY_NH === '') {
                let error = 'Ngày ngân hàng không được trống'
                vm.DSLoi.items.push({ TEN_NHOM: item.TEN_NHOM, TIENTRA: item.TIENTRA, GHICHU_LOI: error })
                continue
              }

              let dateCheck = moment(item.NGAY_NH, 'DD/MM/YYYY HH:mm:ss')
              if (dateCheck._isValid) { item.NGAY_NH = dateCheck.format('DD/MM/YYYY') } else {
                let error = 'Ngày ngân hàng không đúng định dạng dd/MM/yyyy HH:mm:ss'
                vm.DSLoi.items.push({ TEN_NHOM: item.TEN_NHOM, TIENTRA: item.TIENTRA, GHICHU_LOI: error })
                continue
              }

              if (isNaN(item.TIENTRA)) {
                let error = 'Số tiền không đúng định dạng'
                vm.DSLoi.items.push({ TEN_NHOM: item.TEN_NHOM, TIENTRA: item.TIENTRA, GHICHU_LOI: error })
              }

              if (isNaN(item.STT)) {
                let error = 'STT không đúng định dạng'
                vm.DSLoi.items.push({ TEN_NHOM: item.TEN_NHOM, TIENTRA: item.TIENTRA, GHICHU_LOI: error })
              }

              let l = []
              vm.Tokenizer(l, item.NOIDUNG_CT)
              vm.Tokenizer(l, item.NOIDUNG_CT.replace(vm.$auth.getMaTinh() + ' ', vm.$auth.getMaTinh()))
              // console.log(item.NOIDUNG_CT)
              item.NOIDUNG_CHUANHOA = l
            }
            // console.log(columns)


            if (vm.DSLoi.items.length > 0) {
              vm.loading(false)
              await vm.confirmDialog(vm.DSLoi, vm.DSLoi.items.length)
            } else {
              vm.ws_Items = [...successData] // ws.slice()
              vm.chuyenKhoan_grid.items = []
              vm.KQDoiSoat_grid.items = []
              vm.loading(false)
              vm.insert_temp(successData, 1)
            }
          }
        } catch (e) {
          console.log(e)
          vm.loading(false)
          return vm.$toast.error('Kiểm tra dữ liệu file đầu vào!')
        }
      }
      this.loading(true)
      fileReader.readAsBinaryString(files[0])
      this.filePath = files[0].name

    },

    async onTimKiem () {
      if (this.ws_Items.length <= 0) { return this.$toast.error('Chưa có dữ liệu để tìm kiếm!') }

      if (this.filter.selected !== null && this.filter.selected.tieuChi0_Checked) {
        let tt_tk = this.params.DKTimKiem_Options.find(i => i.KIEUTK_ID === this.filter.selected.tieuChi0_Selected).THUTU
        if (tt_tk === 99) {
          let dtSai = {
            columns: this.DSLoi.columns,
            items: []
          }
          for (let item of this.ws_Items) {
            let arr = item.TEN_NHOM.split('_')
            if (arr.length < 3) {
              dtSai.items.push({
                TEN_NHOM: item.TEN_NHOM,
                TIENTRA: item.TIENTRA,
                GHICHU_LOI: 'Sai cấu trúc tên nhóm (TENKH_MAKH_KYHD)'
              })
            } else {
              let ky_hd = arr[arr.length - 1]
              let ma_kh = arr[arr.length - 2].trim().toUpperCase()
              let ten_kh = ''
              for (let i = 0; i < arr.length - 2; i++) {
                ten_kh += arr[i]
              }
              let check_kh = await this.getId(ma_kh)
              if (check_kh === '-1') {
                dtSai.items.push({
                  TEN_NHOM: item.TEN_NHOM,
                  TIENTRA: item.TIENTRA,
                  GHICHU_LOI: `Mã khách hàng ${ma_kh} không tồn tại`
                })
              } else {
                item.MA_KH = ma_kh
                item.KHACHHANG_ID = check_kh
                item.TEN_KH = ten_kh
              }
            }
          }

          if (dtSai.items.length > 0) { return await this.confirmDialog(dtSai, dtSai.items.length) }
        }
      }

      // this.insert_temp(this.ws_Items, 0);
      this.insert_temp(this.chuyenKhoan_grid.items, 0)
    },

    async confirmGachNo () {
      const dsPhieuGN = this.DSGN_grid.items.filter(x => x._CHECK === true)
      let ds = []
      dsPhieuGN.forEach(item => {
        ds.push({
          maTT: item.MA_TT,
          thanhToanId: item.THANHTOAN_ID,
          tienTra: item.TIENTRA,
          chungTu: item.CHUNGTU
        })
      })
      let apiBody = {
        chungTu: dsPhieuGN[0].CHUNGTU,
        dsChuyenKhoan: ds,
        ghiChu: dsPhieuGN[0].GHICHU,
        htttId: this.params.HTTTNganHang_Selected,
        kyCuoc: this.params.kyHoaDon_Selected,
        nganHangId: this.params.nganHang_Selected,
        ngayNganHang: dsPhieuGN[0].NGAY_NH,
        ngayTT: moment(this.params.ngayTT).format('DD/MM/YYYY'),
        thuNganId: this.params.nhanVienTN_Selected
      }
      this.loading(true)
      let rs = await this.updateGachNoChuyenKhoan(apiBody)
      this.loading(false)
      if (rs) {
        this.$toast.success('Gạch nợ chuyển khoản thành công!')
        // let ds = this.DSGN_grid.items.filter(e => this.DSGN_grid.checked.indexOf(e.THANHTOAN_ID) === -1)
        let ds = this.DSGN_grid.items.filter(x => x._CHECK === false)
        this.DSGN_grid.items = [...ds]
        // this.onDSGNGridChecked([])
      }
    },

    async onGachNo () {

      if (this.DSGN_grid.items.length <= 0 || this.chuyenKhoan_grid.items.length <= 0) { return this.$toast.error('Chưa có danh sách gạch nợ không thể gạch!') }
      const dsPhieuGN = this.DSGN_grid.items.filter(x => x._CHECK === true)
      if (dsPhieuGN.length <= 0) { return this.$toast.error('Bạn chưa chọn thanh toán để gạch!') }
      if (this.params.nganHang_Selected === null) { return this.$toast.error('Bạn chưa chọn ngân hàng!') }
      if (this.params.ngayTT === null) { return this.$toast.error('Bạn chưa chọn ngày gạch hợp lệ!') }
      if (this.params.ngayTT > this.params.today) { return this.$toast.error('Bạn không được gạch lớn hơn ngày hiện tại!') }
      if (this.params.HTTTNganHang_Selected == null) { return this.$toast.error('Bạn chưa chọn hình thức thanh toán!') }

      // let dsPhieuGN = this.DSGN_grid.items.filter(e => this.DSGN_grid.checked.findIndex(i => i === e.THANHTOAN_ID) !== -1)

      let f = dsPhieuGN.filter(x => x.TIENTRA <= 0)
      let vm = this
      if (f.length > 0) {
        let message = 'Trong danh sách chọn đang có Mã thanh toán :' + f.map(x => x.MA_TT).join(',') + ' có số tiền <= 0. Bạn có muốn gạch nợ không ?'
        DialogObj = DialogUtility.confirm({
          title: 'Thông báo',
          content: '<div style="padding:10px">' + message + '</div>',
          okButton: { text: 'Đồng ý', click: function () { DialogObj.hide(); vm.confirmGachNo() } },
          cancelButton: { text: ' Hủy ' },
          showCloseIcon: true,
          closeOnEscape: true,
          animationSettings: { effect: 'Zoom' }
        })
      } else vm.confirmGachNo()

    },

    onPhieuTra () {
      if (this.params.nhanVienTN_Selected === null) { return this.$toast.error('Bạn chưa chọn thu ngân. Hãy kiểm tra lại!') }
      if (this.params.ngayTT === null) { return this.$toast.error('Vui lòng chọn ngày thanh toán!') }

      this.phieuTra_data.kyCuoc = moment(this.params.ngayTT).subtract(1, 'months').startOf('month').format('YYYYMMDD').toString()
      this.$refs.phieuDaTra.openDialog()
      this.$nextTick(function () {
        this.$refs.phieuDaTra.doSearch()
      })
    },

    onHuongDan () {},

    onExcelMau () {
      let dsMau = {
        columns: [
          { headerText: 'STT', fieldName: 'STT' },
          { headerText: 'NGAY_NH', fieldName: 'NGAY_NH' },
          { headerText: 'TEN_NHOM', fieldName: 'TEN_NHOM' },
          { headerText: 'TIENTRA', fieldName: 'TIENTRA' },
          { headerText: 'STK', fieldName: 'STK' },
          { headerText: 'CHUNGTU', fieldName: 'CHUNGTU' },
          { headerText: 'GHICHU', fieldName: 'GHICHU' },
          { headerText: 'NOIDUNG_CT', fieldName: 'NOIDUNG_CT' }
        ],
        items: [{
          STT: 0,
          NGAY_NH: moment('22/02/2019 15:45:59', 'DD/MM/YYYY HH:mm:ss').toDate(),
          TEN_NHOM: 'tên thanh toán',
          TIENTRA: 50000,
          STK: '',
          CHUNGTU: 'CT-0001',
          GHICHU: '',
          NOIDUNG_CT: ''
        }]
      }
      this.exportExcel('fileDSCTNH', dsMau)
    },

    onChuyenKhoanExport () {
      if (this.chuyenKhoan_grid.items.length <= 0) { this.$toast.error('Chưa có dữ liệu để xuất excel!') } else { this.exportExcel('DSChuyenKhoan', this.chuyenKhoan_grid) }
    },

    onKQDSExport () {
      if (this.params.tabActive === 0) {
        if (this.KQDoiSoat_grid.items.length <= 0) { this.$toast.error('Chưa có dữ liệu để xuất excel!') } else { this.exportExcel('KetQuaDoiSoat', this.KQDoiSoat_grid) }
      } else {
        if (this.KQKhongKhop_grid.items.length <= 0) { this.$toast.error('Chưa có dữ liệu để xuất excel!') } else { this.exportExcel('KetQuaKhongKhop', this.KQKhongKhop_grid) }
      }
    },

    exportExcel (fileName, gridName) {
      const tHeader = gridName.columns.map((item) => item.headerText)
      const filterVal = gridName.columns.map((item) => item.fieldName)
      const data = this.formatJson(filterVal, gridName.items)
      export_json_to_excel({
        header: tHeader,
        data,
        filename: fileName,
        sheetname: 'export',
        bookType: 'xlsx'
      })
    },

    formatJson (filterVal, jsonData) {
      return jsonData.map((v) =>
        filterVal.map((j) => {
          return v[j]
        })
      )
    },

    onChangeTab () {
      if (this.params.tabActive === 0) {
        this.params.tabActive = 1
        this.ketQuaTitle = 'Thông tin chi tiết kết quả không tìm thấy'
      } else {
        this.params.tabActive = 0
        this.ketQuaTitle = 'Thông tin chi tiết kết quả đối soát'
      }
    },

    onDSGNGridChecked (items) {
      // console.log(items)
      let selected = []
      for (let i = 0; i < items.length; i++) {
        let f = this.DSGN_grid.items.find(x => x.THANHTOAN_ID === items[i])
        let arr = this.DSGN_grid.items.filter(x => x.TEN_NHOM === f.TEN_NHOM)
        arr.forEach(item => {
          if (!selected.includes(item.THANHTOAN_ID)) {
            selected.push(item.THANHTOAN_ID)
          }
        })
      }
      this.DSGN_grid.checked = selected
      // this.DSGN_grid.checked = items
      this.DSGN_grid.aggSum = 0
      this.DSGN_grid.items.forEach(item => {
        if (this.DSGN_grid.checked.findIndex(e => e === item.THANHTOAN_ID) !== -1) { this.DSGN_grid.aggSum += item.TIENTRA }
      })
      let dataGridObj = this.$refs.DSGNTable.$refs.grid.ej2Instances
      dataGridObj.aggregateModule.refresh()
    },

    onCKGridChecked (items) {
      this.chuyenKhoan_grid.checked = items
    },

    onKQDSGridChecked (items) {
      this.KQDoiSoat_grid.checked = items
    },

    onKQKKGridChecked (items) {
      this.KQKhongKhop_grid.checked = items
    },

    onGo () {
      let selected = this.DSGN_grid.items.filter(x => x._CHECK === true)
      let unselected = this.DSGN_grid.items.filter(x => x._CHECK === false)
      if (selected) {
        this.KQDoiSoat_grid.items = [...this.KQDoiSoat_grid.items, ...selected]
        this.KQDoiSoat_grid.key++
        this.DSGN_grid.items = [...unselected]
        this.DSGN_grid.key++
      }
      // if (this.DSGN_grid.checked.length > 0) {
      //   let arr = []
      //   this.DSGN_grid.checked.map((val) => {
      //     let idx = this.DSGN_grid.items.findIndex((v) => v.THANHTOAN_ID === val)
      //     if (idx >= 0) {
      //       arr.push(this.DSGN_grid.items[idx])
      //       this.DSGN_grid.items = this.DSGN_grid.items.filter((v) => v.THANHTOAN_ID !== val)
      //       this.DSGN_grid.key++
      //     }
      //   })
      //   this.DSGN_grid.checked = []
      //   this.DSGN_grid.aggSum = 0
      //   this.KQDoiSoat_grid.items = this.KQDoiSoat_grid.items.concat(arr)
      //   this.KQDoiSoat_grid.key++
      // }
    },

    onGan () {
      let selected = this.KQDoiSoat_grid.items.filter(x => x._CHECK === true)
      let unselected = this.KQDoiSoat_grid.items.filter(x => x._CHECK === false)
      if (selected.length > 0) {
        this.KQDoiSoat_grid.items = [...unselected]
        this.KQDoiSoat_grid.key++
        this.DSGN_grid.items = [...this.DSGN_grid.items, ...selected]
        this.DSGN_grid.key++
      }
      // if (this.KQDoiSoat_grid.checked.length > 0) {
      //   let arr = []
      //   this.KQDoiSoat_grid.checked.map((val) => {
      //     let idx = this.KQDoiSoat_grid.items.findIndex((v) => v.THANHTOAN_ID === val)
      //     if (idx >= 0) {
      //       arr.push(this.KQDoiSoat_grid.items[idx])
      //       this.KQDoiSoat_grid.items = this.KQDoiSoat_grid.items.filter((v) => v.THANHTOAN_ID !== val)
      //       this.KQDoiSoat_grid.key++
      //     }
      //   })
      //   this.KQDoiSoat_grid.checked = []
      //   this.DSGN_grid.items = this.DSGN_grid.items.concat(arr)
      //   this.DSGN_grid.key++
      // }
    },

    async insert_temp (ws, kieu) {
      if (kieu === 1) { this.chuyenKhoan_grid.items = [] } else { this.KQDoiSoat_grid.items = [] }
      this.DSGN_grid.items = []

      this.configDieuKienLoc()
      if (kieu === 0 && this.filter.dieuKien === '') { return this.$toast.error('Bạn phải chọn ít nhất 1 tiêu chí!') }

      if (kieu === 1) { // excel
        this.filter.dieuKien = ''
      }

      let ds = []
      // const regex = /IN/ig
      ws.forEach(item => {
        item.NOIDUNG_CT = item.NOIDUNG_CT.replaceAll(/\bIN\b/ig, 'JINJ')
        item.NOIDUNG_CT = item.NOIDUNG_CT.replaceAll(/\bAND\b/ig, 'JANDJ')
        item.NOIDUNG_CT = item.NOIDUNG_CT.replaceAll(/\bCALL\b/ig, 'JCALLJ')
        item.NOIDUNG_CT = item.NOIDUNG_CT.replaceAll(/\bANY\b/ig, 'JANYJ')
        item.NOIDUNG_CT = item.NOIDUNG_CT.replaceAll(/\bSOME\b/ig, 'JSOMEJ')
        item.NOIDUNG_CT = item.NOIDUNG_CT.replaceAll(/\bUPDATE\b/ig, 'JUPDATEJ')
        // console.log(item.NOIDUNG_CT)
        ds.push({
          CHUNGTU: item.CHUNGTU,
          GHICHU: item.GHICHU,
          NGAY_NH: item.NGAY_NH,
          STK: item.STK,
          STT: item.STT,
          TEN_NHOM: item.TEN_NHOM,
          TIEN: item.TIENTRA,
          NOIDUNG: item.NOIDUNG_CT,
          TTCT_ID: this.params.timTheoCT && kieu !== 1 ? item.TTCT_ID : null
        })
      })

      let apiBody = {
        kieu: kieu,
        kyCuoc: this.params.kyHoaDon_Selected,
        thuTu: this.filter.thuTu,
        dieuKien: this.filter.selected.tieuChi0_Selected,
        dsChuyenKhoan: ds
      }
      // console.log(apiBody)
      // return
      if (kieu === 0) {
        apiBody.sessionId = this.chuyenKhoan_grid.items[0].SESSION_ID != null ? this.chuyenKhoan_grid.items[0].SESSION_ID : null
      }
      let ketqua = await this.layDSChuyenKhoan2(apiBody)

      if (kieu === 0 && this.params.timTheoCT) {
        let apiBody1 = {
          kieu: kieu,
          kyCuoc: this.params.kyHoaDon_Selected,
          thuTu: this.filter.thuTu,
          dieuKien: this.filter.selected.tieuChi0_Selected,
          dsChuyenKhoan: ds,
          sessionId: this.chuyenKhoan_grid.items[0].SESSION_ID != null ? this.chuyenKhoan_grid.items[0].SESSION_ID : null
        }
        let temp = await this.updateLocDSChuyenKhoanND(apiBody1)
        if (temp.length > 0) {
          if (ketqua.length > 0) { ketqua.concat(temp) } else ketqua = [...temp]
        }
      }

      this.parseResult(kieu, ketqua)
    },

    configDieuKienLoc () {
      this.filter.dieuKien = ''
      this.filter.thuTu = ''
      if (this.filter.selected !== {}) {
        if (this.filter.selected.tieuChi0_Checked) {
          let ft = this.params.DKTimKiem_Options.find(i => i.KIEUTK_ID === this.filter.selected.tieuChi0_Selected)
          this.filter.dieuKien += ft.DIEUKIEN + ' and '
          this.filter.thuTu += ft.THUTU + ','
          let kt = ft.KIEU_TK.split('[')
          this.filter.kieu_GN = kt.length > 1 ? kt[1].replaceAll(']', '') : ''
        }
        if (this.filter.selected.tieuChi1_Checked) {
          let ft = this.params.DKTimKiem_Options.find(i => i.KIEUTK_ID === this.filter.selected.tieuChi1_Selected)
          this.filter.dieuKien += ft.DIEUKIEN + ' and '
          this.filter.thuTu += ft.THUTU + ','
        }
        if (this.filter.selected.tieuChi2_Checked) {
          let ft = this.params.DKTimKiem_Options.find(i => i.KIEUTK_ID === this.filter.selected.tieuChi2_Selected)
          this.filter.dieuKien += ft.DIEUKIEN + ' and '
          this.filter.thuTu += ft.THUTU + ','
        }
        if (this.filter.selected.tieuChi3_Checked) {
          let ft = this.params.DKTimKiem_Options.find(i => i.KIEUTK_ID === this.filter.selected.tieuChi3_Selected)
          this.filter.dieuKien += ft.DIEUKIEN + ' and '
          this.filter.thuTu += ft.THUTU + ','
        }
        this.filter.dieuKien = this.filter.dieuKien.slice(0, -4).replaceAll('tientra', 'tien')
        this.filter.thuTu = this.filter.thuTu.slice(0, -1)
      }
    },

    parseResult (kieu, data) {
      if (data === null || data.length <= 0) { return this.$toast.error('Không tìm thấy dữ liệu đối soát') }

      if (kieu === 1) {
        this.chuyenKhoan_grid.items = data
        this.chuyenKhoan_grid.key++
      } else {
        data = [...this.sortBySTT(data)].filter(item => item.TIENTRA !== 0)
        let dsGach = []
        let cot_khop =  data[0].KHOP !== undefined// data.hasOwnProperty('KHOP')
        let f_idstt = data[0].IDSTT !== undefined
        if (f_idstt) {
          for (let item of data) {
            let kt = data.filter(e => e.STT !== item.STT && e.IDSTT == item.IDSTT)
            if (kt.length > 0 && cot_khop) { item.KHOP = 'NOT_OK' }
          }
        }
        data = [...data]

        let rowToDelete = []
        let dt_temp = [...data]

        data.forEach(item => {
          // let dem = dt_temp.filter(e => e === item)
          let dem = dt_temp.filter(x => x.TEN_NHOM === item.TEN_NHOM &&
            x.TIENTRA === item.TIENTRA &&
            x.CHUNGTU === item.CHUNGTU &&
            x.NGAY_NH === item.NGAY_NH &&
            x.STK === item.STK
            )
            if (dem.length === 1 || (cot_khop && item.KHOP === 'OK')) {
                // console.log(item)
                dsGach.push(item)
                rowToDelete.push(item)
              }

        })

        // console.log('dsGach', dsGach)

        this.KQKhongKhop_grid.items = []
        let dsGoc = this.chuyenKhoan_grid.items.slice()
        if (dsGoc.length > 0) {
          let rowOnlyDt1 = dsGoc.filter((e) => (rowToDelete.findIndex(i => i.STT === e.STT) === -1 && e.STATUS !== '1'))
          if (rowOnlyDt1.length > 0) {
            this.KQKhongKhop_grid.items = rowOnlyDt1
            this.KQKhongKhop_grid.key++
          }
        }

        rowToDelete.forEach(item => {
          data = data.filter((e) => e !== item)
        })

        this.KQDoiSoat_grid.items = data
        this.KQDoiSoat_grid.key++


        let t = dsGach.map(x => ({ ...x, _CHECK: false, count: 0 }))
        t = this.groupBySTT(t)
        // console.log(t)
        this.DSGN_grid.items = [...t]

        this.DSGN_grid.key++
        this.generateMaGom()
      }
    },
    groupBySTT (src) {
      let arr = src.map(x => x.STT + '-' + x.TIENTRA)
      const uniqueElements = [...new Set(arr)]
      const count = uniqueElements.map(element => [
        element,
        arr.filter(el => el === element).length
      ])
      // console.log(count)
      let t = [...src]
      count.forEach(item => {
        let a = item[0].split('-')
        let f = t.find(x => x.STT === Number(a[0]) && x.TIENTRA === Number(a[1]))
        if (f !== undefined) f.count = item[1]
      })
      return t
    },
    sortBySTT (src) {
      let dest = src.sort((a, b) => (a.STT > b.STT) ? 1 : ((b.STT > a.STT) ? -1 : 0))
      return dest
    },
    generateMaGom () {
      // this.onDSGNGridChecked([])
      if (this.DSGN_grid.items.length > 0) {
        if (this.params.nganHang_Selected === null) {
          this.DSGN_grid.items.forEach(item => {
            item.MA_GOM = ''
          })
          return
        } else {
          let nh = ''
          if (this.nganHangNEO_items.length > 0) {
            // let idx = this.nganHangNEO_items.findIndex(e => e.NGANHANG_ID.toString() === this.params.nganHang_Selected.toString())
            // if (idx !== -1 && this.nganHangNEO_items[idx].MA_GOM !== null) { nh = this.nganHangNEO_items[idx].MA_GOM }
            let f = this.nganHangNEO_items.find(x => x.NGANHANG_ID.toString() === this.params.nganHang_Selected.toString())
            if (f !== undefined && f.MA_GOM !== null) {
              nh = f.MA_GOM
            }
          }

          this.DSGN_grid.items.forEach(item => {
            if (item.MA_TT_NEO !== null && item.MA_TT_NEO !== '') { item.MA_GOM = nh !== '' ? nh + item.CHUNGTU : '' } else { item.MA_GOM = '' }
          })
          this.DSGN_grid.items = [...this.DSGN_grid.items]
        }
        // this.DSGN_grid.key++
      }
    },

    async getHTTTNganHang () {
      try {
        this.loading(true)
        this.params.HTTTNganHang_Options = []
        let response = await CommonAPI.getHTTTNganHang(this.axios)
        if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
          response.data.data.forEach(item => {
            this.params.HTTTNganHang_Options.push({ id: item.HTTT_ID, text: item.HINHTHUC })
          })
          this.params.HTTTNganHang_Selected = this.params.HTTTNganHang_Options[0].id
        }
      } catch (error) {
        this.$toast.error(error.response.data.message_detail)
      } finally {
        this.loading(false)
      }
    },

    async getDSNganHangCcbs () {
      try {
        this.loading(true)
        this.nganHangNEO_items = []
        let response = await CommonAPI.getDSNganHangCcbs(this.axios)
        if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
          this.nganHangNEO_items = response.data.data
        }
      } catch (error) {
        this.$toast.error(error.response.data.message_detail)
      } finally {
        this.loading(false)
      }
    },

    async getThongTinChuKyNo () {
      try {
        this.loading(true)
        this.params.kyHoaDon_Options = []
        let response = await CommonAPI.getThongTinChuKyNo(this.axios, '')
        if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
          this.params.kyHoaDon_Options = response.data.data
          this.params.kyHoaDon_Selected = this.params.kyHoaDon_Options[0].CHUKYNO
        }
      } catch (error) {
        this.$toast.error(error.response.data.message_detail)
      } finally {
        this.loading(false)
      }
    },

    async getDSTimKiem () {
      try {
        this.loading(true)
        this.params.DKTimKiem_Options = []
        let response = await CommonAPI.getDSTimKiem(this.axios, 93)
        if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
          this.params.DKTimKiem_Options = response.data.data
        }
      } catch (error) {
        this.$toast.error(error.response.data.message_detail)
      } finally {
        this.loading(false)
      }
    },

    async updateLocDSChuyenKhoan (apiBody) {
      try {
        this.loading(true)
        let response = await API.updateLocDSChuyenKhoan(this.axios, apiBody)
        if (response.data.error_code === 'BSS-00000000' && response.data.data != null && response.data.data.length > 0) { return response.data.data } else { return [] }
      } catch (error) {
        return []
        this.$toast.error(error.response.data.message_detail)
      } finally {
        this.loading(false)
      }
    },

    async updateLocDSChuyenKhoanND (apiBody) {
      try {
        this.loading(true)
        let response = await API.updateLocDSChuyenKhoanND(this.axios, apiBody)
        if (response.data.error_code === 'BSS-00000000' && response.data.data != null && response.data.data.length > 0) { return response.data.data } else { return [] }
      } catch (error) {
        return []
        this.$toast.error(error.response.data.message_detail)
      } finally {
        this.loading(false)
      }
    },

    doExportExcel () {
      if (this.DSGN_grid.items.length <= 0) {
        this.$toast.error('Không có phiếu thanh toán để xuất excel.')
        return
      }

      let excelExportProperties = {
        dataSource: this.DSGN_grid.items,
        fileName: 'dsPhieuThanhToan.xlsx'
      }
      this.$refs.DSGNTable.excelExport(excelExportProperties)
    },

    async updateGachNoChuyenKhoan (apiBody) {
      try {
        // this.loading(true)
        let response = await API.updateGachNoChuyenKhoan(this.axios, apiBody)
        if (response.data.error_code === 'BSS-00000000') { return true } else { return false }
      } catch (error) {
        if (error.data.message_detail !== undefined) this.$toast.error(error.data.message_detail)
        else console.log(error)
        return false
      } finally {
        // this.loading(false)
      }
    },

    async layDSChuyenKhoan2 (apiBody) {
      try {
        this.loading(true)
        let response = await API.layDSChuyenKhoan2(this.axios, apiBody)
        if (response.data.error_code === 'BSS-00000000') { return response.data.data } else { return [] }
      } catch (error) {
        return []
        this.$toast.error(error.response.data.message_detail)
      } finally {
        this.loading(false)
      }
    },

    async getMaVungCd () {
      try {
        this.loading(true)
        this.dauso_tinh = ''
        let response = await API.getMaVungCd(this.axios)
        if (response.data.error_code === 'BSS-00000000' && response.data.data != null) { this.dauso_tinh = response.data.data[0].KETQUA }
      } catch (error) {
        this.$toast.error(error.response.data.message_detail)
      } finally {
        this.loading(false)
      }
    },

    async getId (maKH) {
      try {
        this.loading(true)
        let apiBody = {
          tenId: 'ma_kh',
          ma: maKH
        }
        let response = await HDAPI.getId(this.axios, apiBody)
        if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
          return response.data.data[0].KHACHHANG_ID
        } else return ''
      } catch (error) {
        return ''
        console.log(error)
      } finally {
        this.loading(false)
      }
    },

    AddToken (l, t) {
      if (t !== '' && !t.includes(' ')) {
        l.push(t)
        l.push(t.toUpperCase())
        l.push(t.toLowerCase())
        if (t.startsWith('84') && t.length >= 11) {
          l.push(t.substring(2))
        } else if (t.startsWith(this.dauso_tinh) && this.dauso_tinh !== '') {
          l.push(t.substring(this.dauso_tinh.length))
        } else if (t.startsWith('0') && t.length >= 10) {
          l.push(t.replace(/^0+/, ''))
        }
      }
    },

    SplitToken (l, s) {
      this.sep.forEach(c => {
        [' ', ''].forEach(r => {
          let n = s.replace(c, r)
          // console.log(n, s)
          if (n != s) {
            // console.log(n, s)
            // this.Tokenizer(l, n)
          }
        })
      })
    },

    Tokenizer (l, s) {
      let space = [' ', '\t', '\r', '\n']
      this.AddToken(l, s)
      let arr = s.split(/(\s+)/).filter(e => e.trim().length > 0)
      // console.log(l, s, arr)
      arr.forEach(t => {
        this.AddToken(l, t)
        let ar = this.sep.filter(e => t.includes(e))
        if (ar.length > 0) { this.SplitToken(l, t) }
      })
    },
    // queryCellInfoEvent (args) {
    //   if (args.data.STT === 13 && args.column.field === 'TEN_NHOM') {
    //     args.rowSpan = 4
    //   }
    // },
    checkBoxKQChange (args) {
      if (args.target.classList.contains('e-checkselectall')) { // click check/uncheck all
        for (let i = 0; i < this.KQDoiSoat_grid.items.length; i++) {
          this.KQDoiSoat_grid.items[i]._CHECK = args.checked
        }
        // this.KQDoiSoat_grid.checked = this.KQDoiSoat_grid.items.filter(x => x._CHECK === true).map(x => x.THANHTOAN_ID)
        this.refreshKQAgg()
      }
    },
    dsPhieuKQRowSelected (args) {
      // console.log('sel', args)
      if (!args.isHeaderCheckboxClicked && args.isInteracted) { // không phải call by click checkbox header
        let f = this.KQDoiSoat_grid.items.find(x => x.STT === args.data.STT && x.MA_TT === args.data.MA_TT)
        if (f !== undefined) f._CHECK = true
        this.KQDoiSoat_grid.items.filter(x => x.STT === f.STT).forEach(item => {
          item._CHECK = true
        })
        this.KQDoiSoat_grid.items = [...this.KQDoiSoat_grid.items]
        // this.refreshGrid()
        this.refreshKQAgg()
      }
    },
    dsPhieuKQRowDeselected (args) {
      // console.log('desel', args)
      if (!args.isHeaderCheckboxClicked && args.isInteracted) { // không phải call by click checkbox header & paging
        let f = this.KQDoiSoat_grid.items.find(x => x.STT === args.data.STT && x.MA_TT === args.data.MA_TT)
        if (f !== undefined) f._CHECK = false
        // console.log(f)
        if (this.params.checkSTT) {
          this.KQDoiSoat_grid.items.filter(x => x.STT === f.STT).forEach(item => {
            item._CHECK = false
          })
          // console
          this.KQDoiSoat_grid.items = [...this.KQDoiSoat_grid.items]
        }
        this.refreshKQAgg()
      }
    },
    refreshKQAgg () {
      this.$refs.KQDSTable.$refs.grid.ej2Instances.aggregateModule.refresh()
    },
    dsPhieuKQDatabound (args) {
      // console.log('db')
      this.refreshKQGrid()
    },
    refreshKQGrid () {
      let obj = this.$refs.KQDSTable.$refs.grid.ej2Instances
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
    keyPressedDSKQHandler (args) {
      if (args.key === 'Insert') {
        this.checkAllDSKQ(true)
      } else if (args.key === 'Delete') {
        this.checkAllDSKQ(false)
      }
    },
    checkAllDSKQ (f) {
      let temp = [...this.KQDoiSoat_grid.items]
      if (f) { // true
        let filter = this.$refs.KQDSTable.getCurrentFilter()
        if (filter !== undefined && filter.fieldName !== undefined && filter.value !== '') {
          let checked = this.$refs.KQDSTable.filteredDataSource
          for (let i = 0; i < temp.length; i++) {
            let f = checked.find(x => x.STT === temp[i].STT && x.MA_TT === temp[i].MA_TT)
            if (f !== undefined) {
              temp[i]._CHECK = true
            } else {
              temp[i]._CHECK = false
            }
          }
        } else {
          for (let i = 0; i < temp.length; i++) temp[i]._CHECK = true
        }
      } else {
        for (let i = 0; i < temp.length; i++) temp[i]._CHECK = false
      }
      this.KQDoiSoat_grid.items = [...temp]
    },
    checkBoxGNChange (args) {
      if (args.target.classList.contains('e-checkselectall')) { // click check/uncheck all
        for (let i = 0; i < this.DSGN_grid.items.length; i++) {
          this.DSGN_grid.items[i]._CHECK = args.checked
        }
        // this.DSGN_grid.checked = this.DSGN_grid.items.filter(x => x._CHECK === true).map(x => x.THANHTOAN_ID)
        this.refreshGNAgg()
      }
    },
    dsPhieuGNRowSelected (args) {
      // console.log('sel', args)
      if (!args.isHeaderCheckboxClicked && args.isInteracted) { // không phải call by click checkbox header
        let f = this.DSGN_grid.items.find(x => x.STT === args.data.STT && x.MA_TT === args.data.MA_TT)
        if (f !== undefined) f._CHECK = true
        // console.log(f)
        if (this.params.checkSTT) {
          this.DSGN_grid.items.filter(x => x.STT === f.STT).forEach(item => {
            item._CHECK = true
          })
          this.DSGN_grid.items = [...this.DSGN_grid.items]
        }
        // this.refreshGrid()
        this.refreshGNAgg()
      }
    },
    dsPhieuGNRowDeselected (args) {
      // console.log('desel', args)
      if (!args.isHeaderCheckboxClicked && args.isInteracted) { // không phải call by click checkbox header & paging
        let f = this.DSGN_grid.items.find(x => x.STT === args.data.STT && x.MA_TT === args.data.MA_TT)
        if (f !== undefined) f._CHECK = false
        if (this.params.checkSTT) {
          this.DSGN_grid.items.filter(x => x.STT === f.STT).forEach(item => {
            item._CHECK = false
          })
          this.DSGN_grid.items = [...this.DSGN_grid.items]
        }
        this.refreshGNAgg()
      }
    },
    refreshGNAgg () {
      this.$refs.DSGNTable.$refs.grid.ej2Instances.aggregateModule.refresh()
    },
    dsPhieuGNDatabound (args) {
      // console.log('db')
      this.refreshGNGrid()
    },
    refreshGNGrid () {
      let obj = this.$refs.DSGNTable.$refs.grid.ej2Instances
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
    onChangeCustomDropDown (id) {
      const isHIdden = $(id).is(':hidden')
      if (isHIdden) {
        $(id).show()
      } else {
        $(id).hide()
      }
    },
    onChangeNganHang (e) {
      if (e === null) return

      this.params.p_nganhang = e.NGANHANG_ID
      this.params.nganhang.id = e.NGANHANG_ID
      this.params.nganhang.text = e.TEN_NH
      this.params.nganhang.ma_gom = e.MA_GOM
      this.params.nganHang_Selected = this.params.nganhang.id
      this.generateMaGom()
      $('#nganhang-pd').hide()
    },
    keyPressedDSGNHandler (args) {
      if (args.key === 'Insert') {
        this.checkAllDSGN(true)
      } else if (args.key === 'Delete') {
        this.checkAllDSGN(false)
      }
    },
    checkAllDSGN (f) {
      let temp = [...this.DSGN_grid.items]
      if (f) { // true
        let filter = this.$refs.DSGNTable.getCurrentFilter()
        if (filter !== undefined && filter.fieldName !== undefined && filter.value !== '') {
          let checked = this.$refs.DSGNTable.filteredDataSource
          for (let i = 0; i < temp.length; i++) {
            let f = checked.find(x => x.STT === temp[i].STT && x.MA_TT === temp[i].MA_TT)
            if (f !== undefined) {
              temp[i]._CHECK = true
            } else {
              temp[i]._CHECK = false
            }
          }
        } else {
          for (let i = 0; i < temp.length; i++) temp[i]._CHECK = true
        }
      } else {
        for (let i = 0; i < temp.length; i++) temp[i]._CHECK = false
      }
      this.DSGN_grid.items = [...temp]
    },
    queryCellInfoEvent (args) {
      let data = args.data
      if (data.count > 1) {
        if (args.column.field === 'TIENTRA') {
          args.rowSpan = data.count
        }
      }
    }
  },

  watch: {
    // 'params.nganHang_Selected': async function (newVal, oldVal) {
    //   this.generateMaGom()
    // }
  }
}
</script>
<style scoped>
.vattu_box {
    position: relative;
  }

  .vattu_table {
    position: absolute;
    z-index: 1000;
    max-height: 360px;
    background: aliceblue;
    overflow-y: scroll;
    min-width: 700px;
  }
  .displayNone {
    display: none;
  }

</style>
