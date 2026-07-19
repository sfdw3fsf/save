<template src="./index.html"></template>
<style scoped src="./style.scss"></style>
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
import Vue from 'vue'
import $ from 'jquery'
import KyCuoc from '../components/KyCuoc.vue'
import moment from 'moment'
import DropDownCustom from './components/dropdown-custom'
// import LuotDaGiao from './components/luot-da-giao'
import LuotDaGiao from '../GiaoPhieuTon/components/PopupLuotGiao.vue'
// import DanhSachTon from './components/danh-sach-ton'
import { export_json_to_excel } from '../TraCuuDichVu/Export2Excel'
import { currency } from '@/filters/currency'
import { cloneArray } from '@/utils/util'

let KHLCache = []
export default {
  components: {
    AppKyCuoc: KyCuoc,
    'multi-select-demo': DropDownCustom,
    PopupLuotGiao: LuotDaGiao
    // 'danh-sach-ton': DanhSachTon
  },
  // filters: {
  //   currency: function (value) {
  //     let valNum = parseInt(value.toString().replaceAll(',', ''))
  //     if (valNum >= 0) return valNum.toLocaleString('en-En')
  //     return value
  //   }
  // },
  data: function () {
    return {
      columns: [
        {
          fieldName: 'MA_TUYEN',
          headerText: 'Tuyến thu',
          allowFiltering: true,
          allowSorting: false,
          isGroupedColumn: false
        },
        {
          fieldName: 'MA_TT',
          headerText: 'Mã Thanh toán',
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: 'MATB_DD',
          headerText: 'Mã đại diện',
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: 'MA_LT',
          headerText: 'Số ảo',
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: 'TEN_TT',
          headerText: 'Tên thanh toán',
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: 'TIEN',
          headerText: 'Tiền nợ',
          allowFiltering: true,
          allowSorting: false, format: 'N0', type: 'number', textAlign: 'Right'
        },
        {
          fieldName: 'TIEN_HD',
          headerText: 'Tiền hóa đơn',
          allowFiltering: true,
          allowSorting: false, format: 'N0', type: 'number', textAlign: 'Right'
        },
        {
          fieldName: 'TIENHD_PS',
          headerText: 'Tiền phát sinh',
          allowFiltering: true,
          allowSorting: false, format: 'N0', type: 'number', textAlign: 'Right'
        },
        {
          fieldName: 'TIENHD_DK',
          headerText: 'Tiền đầu kỳ',
          allowFiltering: true,
          allowSorting: false, format: 'N0', type: 'number', textAlign: 'Right'
        },
        { fieldName: 'SDT_LH', headerText: 'Số DT liên hệ', allowFiltering: true, allowSorting: false },
        {
          fieldName: 'SO_SERI',
          headerText: 'Số seri',
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: 'SO_THUNG',
          headerText: 'Số thùng',
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: 'HINHTHUC_TT',
          headerText: 'HTTT',
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: 'SERI',
          headerText: 'Seri',
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: 'DIACHI_TT',
          headerText: 'Địa chỉ thanh toán',
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: 'DIACHI_BC',
          headerText: 'Địa chỉ phát',
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: 'LUOTGIAO',
          headerText: 'Lượt giao',
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: 'MAIN_GHEP',
          headerText: 'Mã in ghép',
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: 'THUNO',
          headerText: 'Thu nợ',
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: 'STK',
          headerText: 'Số tài khoản',
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: 'TEN_LOAIKH',
          headerText: 'Đối tượng',
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: 'TEN_LKHL',
          headerText: 'Loại kh lớn',
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: 'DS_DVKHAC',
          headerText: 'Dịch vụ khác',
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: 'THUTU_IN',
          headerText: 'Thứ tự in',
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: 'NGAYHEN',
          headerText: 'Ngày hẹn',
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: 'NHANVIEN_CSKH',
          headerText: 'CSKH',
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: 'DOTGIAO_ID',
          headerText: 'Đợt giao',
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: 'MANV_TC',
          headerText: 'Mã NV/HTTT',
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: 'TEN_PLKH',
          headerText: 'Phân loại KH',
          allowFiltering: true,
          allowSorting: false
        }
        ,
        {
          fieldName: 'CNRR',
          headerText: 'CNRR',
          allowFiltering: true,
          allowSorting: false
        }
      ],
      luotGiao_Input: null,
      form: {
        nvtc: {
          id: '',
          text: ''
        },
        loaiKHId: '0',
        ngayGiao: new Date(),
        cbkKhLon: false,
        khhl: {
          id: [],
          text: ''
        },
        cbkHtg: false,
        htg: {
          id: [],
          text: ''
        },
        idLoaiPhieu: '',
        loaiPhieuPlus: '>0',
        loaiTien: 2,
        cbkTongPhieu: false,
        tongPhieuTu: 0,
        tongPhieuDen: 0,
        ckbDenHen: true,
        ckbTienHd: false,
        ckbTienDc: true,
        tienDc: '0',
        ckbTongTien: false,
        tongTien: '0',
        ckbIn: false
      },
      dsNVTC: [],
      dsLoaiKhachHang: [
        {
          loaikh_id: '0',
          loaikh: 'Tất cả'
        },
        {
          loaikh_id: '3',
          loaikh: 'Nhờ thu'
        },
        {
          loaikh_id: '2',
          loaikh: 'Bình thường'
        }
      ],
      dsKHLon: [],
      dsHtGiao: [],
      dsLoaiPhieu: [],
      dsDotGiao: [],
      dsDotGiaoIdSelected: [],
      dsTuyenThu: [],
      dsTuyenThuIdSelected: [],
      dsHoaDon: [],
      dsHoaDonDaGiao: [],
      phieuChuaGiaoChosen: [],
      phieuDaGiaoChosen: [],
      phieuCGSelected: [],
      phieuDGSelected: [],
      kyCuoc: '',
      gridTab: 1,
      sideBarTab: 0,
      hideTable: true,
      hideDropCustom: true,
      hideHtGiao: true,
      numberHide: 0,
      numberHideTT: 0,
      daGiao: {
        tienNo: {
          sum: 0,
          chose: 0
        },
        tienHoaDon: {
          sum: 0,
          chose: 0
        },
        tienPhatSinh: {
          sum: 0,
          chose: 0
        },
        tienDauKy: {
          sum: 0,
          chose: 0
        }
      },
      chuaGiao: {
        tienNo: {
          sum: 0,
          chose: 0
        },
        tienHoaDon: {
          sum: 0,
          chose: 0
        },
        tienPhatSinh: {
          sum: 0,
          chose: 0
        },
        tienDauKy: {
          sum: 0,
          chose: 0
        }
      },
      pageinfoDSHDChuaGiao: {
        page: 0,
        maxSize: 200,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null
      },
      keyChuaGiao: 0,
      pageinfoDSHDDaGiao: {
        page: 0,
        maxSize: 200,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null
      },
      keyDaGiao: 0,
      selectionSettings: { checkboxOnly: true, type: 'Multiple' },
      customTemplate: function () {
        return {
          template: Vue.component('footerTemplate', {
            template: `<input type="text" class="form-control tright" v-model="data.Custom" :disabled="true" style="font-weight:bold;margin: 7px 0px 7px 0px;" />`,
            data() { return { data: { data: {} } } }
          })
        }
      }
    }
  },
  validations: {},
  watch: {},
  created: async function () {
    this.initPullDown('#nvtc-pd', 'root')
    this.initPullDown('#khl-pd', 'root2')
    this.initPullDown('#htg-pd', 'root3')
    this.getDsKHLon()
    this.getDsHtGiao()
    this.getDsLoaiPhieu()
  },
  async mounted() {
    this.initTienDc()
    this.initTongTien()
  },
  async updated() { },
  methods: {
    aggCountPhieuCG() {
      return currency(this.phieuCGSelected.length) + '/' + currency(this.dsHoaDon.length)
    },
    aggCountPhieuDG() {
      return currency(this.phieuDGSelected.length) + '/' + currency(this.dsHoaDonDaGiao.length)
    },
    aggPhieuCGTIEN() {
      return currency(this.chuaGiao.tienNo.chose) + '/' + currency(this.chuaGiao.tienNo.sum)
    },
    aggPhieuCGTIEN_HD() {
      return currency(this.chuaGiao.tienHoaDon.chose) + '/' + currency(this.chuaGiao.tienHoaDon.sum)
    },
    aggPhieuCGTIENHD_PS() {
      return currency(this.chuaGiao.tienPhatSinh.chose) + '/' + currency(this.chuaGiao.tienPhatSinh.sum)
    },
    aggPhieuCGTIENHD_DK() {
      return currency(this.chuaGiao.tienDauKy.chose) + '/' + currency(this.chuaGiao.tienDauKy.sum)
    },
    aggPhieuDGTIEN() {
      return currency(this.daGiao.tienNo.chose) + '/' + currency(this.daGiao.tienNo.sum)
    },
    aggPhieuDGTIEN_HD() {
      return currency(this.daGiao.tienHoaDon.chose) + '/' + currency(this.daGiao.tienHoaDon.sum)
    },
    aggPhieuDGTIENHD_PS() {
      return currency(this.daGiao.tienPhatSinh.chose) + '/' + currency(this.daGiao.tienPhatSinh.sum)
    },
    aggPhieuDGTIENHD_DK() {
      return currency(this.daGiao.tienDauKy.chose) + '/' + currency(this.daGiao.tienDauKy.sum)
    },
    initTienDc() {
      $('#input-1').focus(function () {
        $('#input-contain').addClass('contain-focus')
        $('#input-2').addClass('input2-focus')
      })
      $('#input-1').blur(function () {
        $('#input-contain').removeClass('contain-focus')
        $('#input-2').removeClass('input2-focus')
      })
    },
    initTongTien() {
      $('#input-1-tt').focus(function () {
        $('#input-contain-tt').addClass('contain-focus')
        $('#input-2-tt').addClass('input2-focus')
      })
      $('#input-1-tt').blur(function () {
        $('#input-contain-tt').removeClass('contain-focus')
        $('#input-2-tt').removeClass('input2-focus')
      })
    },
    initPullDown(selector, ignore) {
      $(document).on('mouseup', (e) => {
        var container = $(selector)
        const id = $(e.target).attr('id')
        if (!container.is(e.target) && container.has(e.target).length === 0 && id !== ignore) {
          if (selector === '#khl-pd') {
            // console.log(KHLCache)
          }
          container.hide()
        }
      })
    },
    handleKyHoaDon(e) {
      this.kyCuoc = moment(e.p_kyhoadon).format('YYYYMM') + e.p_chuky_no // "20210101"; //
      this.getDsNvDatCoc()
      this.getTien()
    },
    onSelectNVTC: async function (value) {
      this.dsHoaDon = []
      this.phieuCGSelected = []
      this.dsHoaDonDaGiao = []
      this.phieuDGSelected = []
      if (value) {
        this.form.nvtc.id = value.NHANVIEN_ID
        this.form.nvtc.text = value.TEN_HIENTHI
        $('#nvtc-pd').hide()
        await this.getDsDotGiao()
        await this.getDsTuyenThu()
        $('.tree-plus a').click(function (e) {
          if (e.target !== this) return
          $(this).parent().toggleClass('open')
        })
      } else {
        this.form.nvtc.id = ''
        this.form.nvtc.text = ''
      }
    },
    onOk(value) {
      if (value.length > 0) {
        const ids = value.map((item) => item.ID)
        const texts = value.map((item) => item.TEN_HIENTHI)
        this.form.khhl.id = ids
        this.form.khhl.text = texts.join()
      } else {
        this.form.khhl.id = []
        this.form.khhl.text = ''
      }
      $('#khl-pd').hide()
    },
    onChoseItem(value) {
      if (value.length > 0) {
        KHLCache = value
        // const ids = value.map((item) => item.ID);
        // const texts = value.map((item) => item.TEN_HIENTHI);
        // this.form.khhl.id = ids;
        // this.form.khhl.text = texts.join();
      } else {
        KHLCache = []
        // this.form.khhl.id = [];
        // this.form.khhl.text = "";
      }
    },
    onCancel(id) {
      $(id).hide()
    },
    onChangeCustomDropDown(id) {
      const isHIdden = $(id).is(':hidden')
      if (isHIdden) {
        $(id).show()
      } else {
        $(id).hide()
      }
    },
    onOkHtg(value) {
      if (value.length > 0) {
        const ids = value.map((item) => item.ID)
        const texts = value.map((item) => item.TEN_HIENTHI)
        this.form.htg.id = ids
        this.form.htg.text = texts.join()
      } else {
        this.form.htg.id = []
        this.form.htg.text = ''
      }
      $('#htg-pd').hide()
    },
    onChangeLoaiPhieu() { },
    changeNumber(evt) {
      const value = evt.target.value
      const tienDc = Number(this.form.tienDc.replaceAll(',', ''))
      if (Number(value) > Number(this.numberHide)) {
        this.form.tienDc = Number(tienDc + 1).toLocaleString('en-En')
      } else {
        this.form.tienDc = Number(tienDc - 1).toLocaleString('en-En')
      }
      this.numberHide = value
    },
    changeNumberTT(evt) {
      const value = evt.target.value
      const tienDc = Number(this.form.tongTien.replaceAll(',', ''))
      if (Number(value) > Number(this.numberHideTT)) {
        this.form.tongTien = Number(tienDc + 1).toLocaleString('en-En')
      } else {
        this.form.tongTien = Number(tienDc - 1).toLocaleString('en-En')
      }
      this.numberHideTT = value
    },
    onChangeTreeCb(ev) {
      const isChecked = ev.target.checked
      if (!isChecked) {
        this.dsDotGiaoIdSelected = []
      } else {
        this.dsDotGiaoIdSelected = this.dsDotGiao.map(
          (item) => item.DOTGIAO_ID
        )
      }
    },
    onChangeTreeCbTT(ev) {
      const isChecked = ev.target.checked
      if (!isChecked) {
        this.dsTuyenThuIdSelected = []
      } else {
        this.dsTuyenThuIdSelected = this.dsTuyenThu.map(
          (item) => item.TUYENTHU_ID
        )
      }
    },
    onChangeTreeItem(ev, value) {
      const isChecked = ev.target.checked
      if (!isChecked) {
        const index = this.dsDotGiaoIdSelected.indexOf(value)
        if (index > -1) {
          this.dsDotGiaoIdSelected.splice(index, 1)
        }
      } else {
        this.dsDotGiaoIdSelected.push(value)
      }
    },
    onChangeTreeItemTT(ev, value) {
      const isChecked = ev.target.checked
      if (!isChecked) {
        const index = this.dsTuyenThuIdSelected.indexOf(value)
        if (index > -1) {
          this.dsTuyenThuIdSelected.splice(index, 1)
        }
      } else {
        this.dsTuyenThuIdSelected.push(value)
      }
    },
    onClickDanhSach() {
      if (this.gridTab === 1) {
        if (this.dsDotGiaoIdSelected.length === 0) {
          this.$toast.error('Bạn chưa chọn đợt giao!')
          return false
        }
      } else {
        if (this.dsTuyenThuIdSelected.length === 0) {
          this.$toast.error('Bạn chưa chọn tuyến thu!')
          return false
        }
      }
      if (this.dsNVTC.length === 0) {
        this.$toast.error(
          'User đăng nhập của bạn chưa được gán NVTC. Bạn không thể giao phiếu !'
        )
        return false
      }
      this.getDsHoaDon()
    },
    onClickExport() {
      this.exportExcel()
    },
    exportExcel() {
      let displayedData =
        this.gridTab === 1 ? this.dsHoaDon : this.dsHoaDonDaGiao
      if (displayedData.length === 0) {
        this.$toast.error('Không có dữ liệu để xuất Excel')
        return false
      }
      const tHeader = this.columns.map((item) => item.headerText)
      const filterVal = this.columns.map((item) => item.fieldName)
      const data = this.formatJson(filterVal, displayedData)
      export_json_to_excel({
        header: tHeader,
        data,
        filename: `export`,
        sheetname: 'export',
        bookType: 'xlsx'
      })
      // let excelExportProperties = {
      //   dataSource: this.gridTab === 1 ? this.dsHoaDon : this.dsHoaDonDaGiao,
      //   fileName: this.gridTab === 1 ? 'dsPhieuChuaGiao.xlsx' : 'dsPhieuDaGiao.xlsx'
      // }
      // if (excelExportProperties.dataSource.length <= 0) {
      //   this.$toast.error('Không có dữ liệu để xuất Excel')
      //   return false
      // }
      // if (this.gridTab === 1) {
      //   this.$refs.table01.$refs.grid.excelExport(excelExportProperties)
      // } else {
      //   this.$refs.table02.$refs.grid.excelExport(excelExportProperties)
      // }
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map((v) =>
        filterVal.map((j) => {
          return v[j]
        })
      )
    },
    selectItemChuaGiao(value) {
      this.phieuChuaGiaoChosen = value
      this.chuaGiao.tienNo.chose = 0
      this.chuaGiao.tienHoaDon.chose = 0
      this.chuaGiao.tienPhatSinh.chose = 0
      this.chuaGiao.tienDauKy.chose = 0
      if (value && value.length > 0) {
        const dsPhieuChon = this.dsHoaDon.filter(item => this.phieuChuaGiaoChosen.includes(item.HOADON_ID))
        dsPhieuChon.forEach(item => {
          // this.chuaGiao.tienNo.chose += Number(item.TIEN.replaceAll(',', ''))
          // this.chuaGiao.tienHoaDon.chose += Number(item.TIEN_HD.replaceAll(',', ''))
          // this.chuaGiao.tienPhatSinh.chose += Number(item.TIENHD_PS.replaceAll(',', ''))
          // this.chuaGiao.tienDauKy.chose += Number(item.TIENHD_DK.replaceAll(',', ''))
        })
      }
      this.appendFooter()
    },
    selectItemDaGiao(value) {
      this.phieuDaGiaoChosen = value
      this.daGiao.tienNo.chose = 0
      this.daGiao.tienHoaDon.chose = 0
      this.daGiao.tienPhatSinh.chose = 0
      this.daGiao.tienDauKy.chose = 0
      if (value && value.length > 0) {
        const dsPhieuChon = this.dsHoaDonDaGiao.filter(item => this.phieuDaGiaoChosen.includes(item.MA_TT))
        dsPhieuChon.forEach(item => {
          // this.daGiao.tienNo.chose += Number(item.TIEN.replaceAll(',', ''))
          // this.daGiao.tienHoaDon.chose += Number(item.TIEN_HD.replaceAll(',', ''))
          // this.daGiao.tienPhatSinh.chose += Number(item.TIENHD_PS.replaceAll(',', ''))
          // this.daGiao.tienDauKy.chose += Number(item.TIENHD_DK.replaceAll(',', ''))
        })
      }
      this.appendFooter()
    },
    onClickGiaoPhieu() {
      if (this.checkDataGiaoPhieu()) {
        // const dsHd = this.dsHoaDon.filter((item) =>
        //   this.phieuChuaGiaoChosen.includes(item.HOADON_ID)
        // )
        const dsHd = this.phieuCGSelected
        let tongTien = 0
        const dsPhieuGiao = dsHd.map((item) => {
          // tongTien += Number(item.TIEN_HD.replaceAll(',', ''))
          tongTien += item.TIEN_HD
          return {
            id: item.HOADON_ID,
            nhanVienId: this.$auth.getNhanVienID(),
            thanhToanId: item.THANHTOAN_ID
          }
        })
        const body = {
          dsPhieuGiao: dsPhieuGiao,
          giaoVet: 0,
          kieuGiao: 1,
          kyCuoc: this.kyCuoc,
          ngayGiao: moment(this.form.ngayGiao).format('DD/MM/YYYY'),
          nguoiGiaoId: this.$auth.getNhanVienID(),
          nhanVienTCId: this.form.nvtc.id,
          soLuongHD: this.phieuCGSelected.length,
          tongTienHD: tongTien
        }
        this.giaoPhieu(body)
      }
    },
    checkDataGiaoPhieu() {
      if (this.dsNVTC.length === 0) {
        this.$toast.error(
          'User đăng nhập của bạn chưa được gán NVTC. Bạn không thể giao phiếu !'
        )
        return false
      }
      // if (this.phieuChuaGiaoChosen.length === 0) {
      //   this.$toast.error('Chưa chọn phiếu cần giao!')
      //   return false
      // }
      if (this.phieuCGSelected.length <= 0) {
        this.$toast.error('Chưa chọn phiếu cần giao!')
        return false
      }
      const tongTien = Number(this.form.tienDc.replaceAll(',', ''))
      if (this.form.ckbTienDc && tongTien > 0) {
        // const tongTien = Number(this.form.tienDc.replaceAll(',', ''))
        // const dsHd = this.dsHoaDon.filter((item) =>
        //   this.phieuChuaGiaoChosen.includes(item.HOADON_ID)
        // )
        const dsHd = this.phieuCGSelected
        let sum = 0
        dsHd.forEach((el) => {
          if (el.SERI != '0') {
            sum += Number(el.TIEN)
          }
        })
        // console.log(tongTien, sum)
        if (sum > (tongTien * 11) / 10) {
          this.$toast.error(
            'Số tiền hóa đơn đã vượt 110% số tiền ký quỹ. Bạn không thể giao phiếu !'
          )
          return false
        }
      }
      return true
    },
    onCickHuyGiao() {
      if (this.checkHuyGiao()) {
        this.$bvModal
          .msgBoxConfirm(`Bạn có muốn hủy giao phiếu không ?`, {
            title: 'Thông báo',
            size: 'sm',
            buttonSize: 'md',
            okVariant: 'primary',
            headerClass: 'p-2 pb-0 border-bottom-0',
            footerClass: 'p-2 pt-0 border-top-0 justify-content-center',
            centered: true,
            noCloseOnBackdrop: true,
            okTitle: 'Đồng ý',
            cancelTitle: 'Không đồng ý'
          })
          .then((value) => {
            value && this.huyGiao()
          })
      }
    },
    checkHuyGiao() {
      // if (this.phieuDaGiaoChosen.length == 0) {
      //   this.$toast.error('Chưa chọn phiếu!')
      //   return false
      // }
      if (this.phieuDGSelected.length <= 0) {
        this.$toast.error('Chưa chọn phiếu!')
        return false
      }
      return true
      // const dsHd = this.dsHoaDon.filter((item) =>
      //   this.phieuDaGiaoChosen.includes(item.HOADON_ID)
      // );
      // for (let index = 0; index < dsHd.length; index++) {
      //   const element = dsHd[index];
      // }
    },
    onClickLuotDaGiao() {
      if (this.dsNVTC.length === 0) {
        this.$toast.error(
          'User đăng nhập của bạn chưa được gán NVTC. Bạn không thể xem dữ liệu !'
        )
        return false
      }
      if (this.form.nvtc.id === null || this.form.nvtc.id === '') {
        this.$toast.error('Bạn chưa chọn nhân viên thu cước.')
        return false
      }
      // this.$bvModal.show('modal-luot-da-giao')
      this.luotGiao_Input = {
        kyCuoc: this.kyCuoc,
        kieu: 1,
        NVTCId: this.form.nvtc.id,
        NVTC: this.dsNVTC.find(e => e.NHANVIEN_ID === this.form.nvtc.id)
      }
      this.$refs.dlgLuotGiao.openDialog()
    },
    onCloseModal() {
      // this.$bvModal.hide('modal-luot-da-giao')
    },
    onChonLuotGiao(value) {
      // console.log(value);
      if (this.cbkHtg && this.form.htg.id.length == 0) {
        this.$toast.error('Chưa chọn hình thức giao!')
        return false
      }
      if (this.cbkKhLon && this.form.khhl.id.length == 0) {
        this.$toast.error('Chưa chọn khách hàng lớn!')
        return false
      }
      this.getDsPhieuDaGiao(value)
    },
    getDsNvDatCoc: async function () {
      try {
        const rs = await this.$root.context.get(
          `/web-thuno/api/thu-no/common/ds-nhan-vien-dat-coc/${this.kyCuoc}`
        )
        this.dsNVTC = rs.data
      } catch (error) { }
    },
    getDsDotGiao: async function () {
      try {
        const rs = await this.$root.context.get(
          `/web-thuno/api/thu-no/tuyenthu/lay-danh-sach-dot-giao`,
          {
            pNhanVienId: this.form.nvtc.id,
            pageNo: 0,
            pageSize: 10000,
            totalRow: 1
          }
        )
        this.dsDotGiao = rs.data
        this.dsDotGiaoIdSelected = this.dsDotGiao.map((item) => item.DOTGIAO_ID)
      } catch (error) { }
    },
    getDsTuyenThu: async function () {
      try {
        const rs = await this.$root.context.get(
          `/web-thuno/api/thu-no/tuyenthu/lay-danh-sach-tuyen-thu-theo-nhan-vien`,
          { pNhanVienId: this.form.nvtc.id }
        )
        this.dsTuyenThu = rs.data
        this.dsTuyenThuIdSelected = this.dsTuyenThu.map(
          (item) => item.TUYENTHU_ID
        )
      } catch (error) { }
    },
    getDsKHLon: async function () {
      try {
        const rs = await this.$root.context.get(
          `/web-thuno/api/thu-no/common/khach-hang-lon`
        )
        this.dsKHLon = rs.data.map((item) => ({
          ID: item.KHLON_ID,
          TEN_HIENTHI: item.TEN_LKHL
        }))
      } catch (error) { }
    },
    getDsHtGiao: async function () {
      try {
        const rs = await this.$root.context.get(
          `/web-thuno/api/thu-no/common/hinh-thuc-giao`
        )
        this.dsHtGiao = rs.data.map((item) => ({
          ID: item.HTGIAO_ID,
          TEN_HIENTHI: item.TENHT_GIAO
        }))
      } catch (error) { }
    },
    getDsLoaiPhieu: async function () {
      try {
        const rs = await this.$root.context.get(
          `/web-thuno/api/thu-no/common/loai-phieu-thu-cuoc`
        )
        this.dsLoaiPhieu = rs.data
        if (rs.data.length > 0) {
          this.form.idLoaiPhieu = rs.data[0].LOAIPHIEU_ID
        }
      } catch (error) { }
    },
    getTien: async function () {
      try {
        const rs = await this.$root.context.get(
          `/web-thuno/api/thu-no/common/tien-ky-quy-theo-nhan-vien/${this.kyCuoc}` // this.kyCuoc
        )
        // console.log("tien", rs);
        if (rs.data.length > 0) {
          this.form.tienDc = Number(rs.data[0].TIEN_DC).toLocaleString('en-En')
        } else {
          this.form.tienDc = '0'
        }
      } catch (error) { }
    },
    getDsHoaDon: async function () {
      this.dsHoaDon = []
      this.phieuCGSelected = []
      this.dsHoaDonDaGiao = []
      this.phieuDGSelected = []
      this.$root.showLoading(true)
      const param = {
        kyCuoc: this.kyCuoc, // "20210301",
        nvtcId: this.form.nvtc.id, // "",
        dsDot: this.dsDotGiaoIdSelected.join() + ',0', // "",
        dsTuyenThu: this.dsTuyenThuIdSelected.join(), // "",
        loaiKHId: this.form.loaiKHId, // 3,
        loaiPhieuId: this.form.idLoaiPhieu, // "",
        soTien: this.form.loaiPhieuPlus !== '<=>0' ? this.form.loaiPhieuPlus : '0', // "",
        dsHTGiao: this.form.cbkHtg ? this.form.htg.id.join() : '', // "",
        denHen: this.form.ckbDenHen ? 1 : 0, // "",
        trangThaiHD: this.gridTab,
        loaiBC: 1, // "",
        ngayGiao: moment(this.form.ngayGiao).format('DD/MM/YYYY'), // "",
        giaoPhieuId: '', // 0
        dsKHLon: this.form.cbkKhLon ? this.form.khhl.id.join() : '', // "",
        kieu: this.form.ckbTienHd ? 1 : 0, // 1,
        kieuNo: this.form.loaiTien
      }
      console.log(param)
      try {
        const rs = await this.$root.context.post(
          `/web-thuno/api/thu-no/quan-ly-dai-ly/hoa-don`,
          param
        )
        // console.log(rs)
        let dataGrid = []
        if (rs.data.length === 0) {
          // this.$toast.error('Không có dữ liệu, vui lòng thử lại.')
        } else {
          if (this.gridTab === 1) {
            this.chuaGiao.tienNo.sum = 0
            this.chuaGiao.tienHoaDon.sum = 0
            this.chuaGiao.tienPhatSinh.sum = 0
            this.chuaGiao.tienDauKy.sum = 0
          } else {
            this.daGiao.tienNo.sum = 0
            this.daGiao.tienHoaDon.sum = 0
            this.daGiao.tienPhatSinh.sum = 0
            this.daGiao.tienDauKy.sum = 0
          }

          dataGrid = rs.data.map((item) => {
            if (this.gridTab === 1) {
              this.chuaGiao.tienNo.sum += Number(item.TIEN)
              this.chuaGiao.tienHoaDon.sum += Number(item.TIEN_HD)
              this.chuaGiao.tienPhatSinh.sum += Number(item.TIENHD_PS)
              this.chuaGiao.tienDauKy.sum += Number(item.TIENHD_DK)
            } else {
              this.daGiao.tienNo.sum += Number(item.TIEN)
              this.daGiao.tienHoaDon.sum += Number(item.TIEN_HD)
              this.daGiao.tienPhatSinh.sum += Number(item.TIENHD_PS)
              this.daGiao.tienDauKy.sum += Number(item.TIENHD_DK)
            }
            return {
              ...item,
              // TIEN: Number(item.TIEN).toLocaleString('en-En'),
              // TIEN_HD: Number(item.TIEN_HD).toLocaleString('en-En'),
              // TIENHD_PS: Number(item.TIENHD_PS).toLocaleString('en-En'),
              // TIENHD_DK: Number(item.TIENHD_DK).toLocaleString('en-En')
            }
          })
          if (this.form.tongPhieuTu > 0 && this.form.tongPhieuDen >= this.form.tongPhieuTu) {
            const tu = Number(this.form.tongPhieuTu) - 1
            const den = Number(this.form.tongPhieuDen)
            dataGrid = dataGrid.slice(tu, den)
          }
        }
        if (this.gridTab === 1) {
          this.dsHoaDon = dataGrid
          this.keyChuaGiao++
        } else {
          // this.dsHoaDonDaGiao = dataGrid
          this.dsHoaDonDaGiao = cloneArray(dataGrid)
          this.keyDaGiao++
        }
        this.appendFooter()
      } catch (error) {
        // this.$toast.error('Không có dữ liệu, vui lòng thử lại.')
      } finally {
        this.$root.showLoading(false)
      }
    },
    appendFooter() {
      if (this.gridTab === 1) {
        document.querySelector('.table-chua-giao .table-result').deleteTFoot()
        const footer = `<tfoot>
            <tr>
              <th></th>
              <th></th>
              <th></th>
              <th></th>
              <th></th>
              <th></th>
              <th></th>
              <th><input type="text" class="w-auto form-control" disabled value="${Number(this.chuaGiao.tienNo.chose).toLocaleString('en-En')}/${Number(this.chuaGiao.tienNo.sum).toLocaleString('en-En')}" class="form-control"></th>
              <th><input type="text" class="w-auto form-control" disabled value="${Number(this.chuaGiao.tienHoaDon.chose).toLocaleString('en-En')}/${Number(this.chuaGiao.tienHoaDon.sum).toLocaleString('en-En')}" class="form-control"></th>
              <th><input type="text" class="w-auto form-control" disabled value="${Number(this.chuaGiao.tienPhatSinh.chose).toLocaleString('en-En')}/${Number(this.chuaGiao.tienPhatSinh.sum).toLocaleString('en-En')}" class="form-control"></th>
              <th><input type="text" class="w-auto form-control" disabled value="${Number(this.chuaGiao.tienDauKy.chose).toLocaleString('en-En')}/${Number(this.chuaGiao.tienDauKy.sum).toLocaleString('en-En')}" class="form-control"></th>
              <th></th>
              <th></th>
              <th></th>
              <th></th>
              <th></th>
              <th></th>
              <th></th>
              <th></th>
              <th></th>
              <th></th>
              <th></th>
              <th></th>
              <th></th>
              <th></th>
              <th></th>
              <th></th>
              <th></th>
              <th></th>
              <th></th>
              <th></th>
            </tr>
          </tfoot>`
        $('.table-chua-giao .table-result').append(footer)
      } else {
        document.querySelector('.table-da-giao .table-result').deleteTFoot()
        const footer = `<tfoot>
            <tr>
              <th></th>
              <th></th>
              <th></th>
              <th></th>
              <th></th>
              <th></th>
              <th><input type="text" class="w-auto form-control" disabled value="${Number(this.daGiao.tienNo.chose).toLocaleString('en-En')}/${Number(this.daGiao.tienNo.sum).toLocaleString('en-En')}" class="form-control"></th>
              <th><input type="text" class="w-auto form-control" disabled value="${Number(this.daGiao.tienHoaDon.chose).toLocaleString('en-En')}/${Number(this.daGiao.tienHoaDon.sum).toLocaleString('en-En')}" class="form-control"></th>
              <th><input type="text" class="w-auto form-control" disabled value="${Number(this.daGiao.tienPhatSinh.chose).toLocaleString('en-En')}/${Number(this.daGiao.tienPhatSinh.sum).toLocaleString('en-En')}" class="form-control"></th>
              <th><input type="text" class="w-auto form-control" disabled value="${Number(this.daGiao.tienDauKy.chose).toLocaleString('en-En')}/${Number(this.daGiao.tienDauKy.sum).toLocaleString('en-En')}" class="form-control"></th>
              <th></th>
              <th></th>
              <th></th>
              <th></th>
              <th></th>
              <th></th>
              <th></th>
              <th></th>
              <th></th>
              <th></th>
              <th></th>
              <th></th>
              <th></th>
              <th></th>
              <th></th>
              <th></th>
              <th></th>
              <th></th>
              <th></th>
              <th></th>
              <th></th>
            </tr>
          </tfoot>`
        $('.table-da-giao .table-result').append(footer)
      }
    },
    getDsPhieuDaGiao: async function (ts) {
      this.this.dsHoaDonDaGiao = []
      this.phieuDGSelected = []
      const param = {
        kyCuoc: this.kyCuoc, // "20210301",
        nvtcId: this.form.nvtc.id, // "",
        dsDot: ts.dsDot.join(),
        dsTuyenThu: this.dsTuyenThuIdSelected.join(), // "",
        loaiKHId: this.form.loaiKHId, // 3,
        loaiPhieuId: this.form.idLoaiPhieu, // "",
        soTien: this.loaiPhieuPlus !== '<=>0' ? this.loaiPhieuPlus : '0', // "",
        dsHTGiao: this.form.htg.id.join(), // "",
        denHen: this.form.ckbDenHen ? 1 : 0, // "",
        trangThaiHD: this.gridTab,
        loaiBC: 1, // "",
        ngayGiao: moment(this.form.ngayGiao).format('DD/MM/YYYY'), // "",
        giaoPhieuId: ts.giaoPhieuIds.join(),
        dsKHLon: this.form.khhl.id.join(), // "",
        kieu: this.form.ckbTienHd ? 1 : 0 // 1,
      }
      // console.log(param)
      this.$root.showLoading(true)
      try {
        const rs = await this.$root.context.post(
          `/web-thuno/api/thu-no/quan-ly-dai-ly/hoa-don`,
          param
        )
        this.daGiao.tienNo.sum = 0
        this.daGiao.tienHoaDon.sum = 0
        this.daGiao.tienPhatSinh.sum = 0
        this.daGiao.tienDauKy.sum = 0
        let dataGrid = rs.data.map((item) => {
          this.daGiao.tienNo.sum += Number(item.TIEN)
          this.daGiao.tienHoaDon.sum += Number(item.TIEN_HD)
          this.daGiao.tienPhatSinh.sum += Number(item.TIENHD_PS)
          this.daGiao.tienDauKy.sum += Number(item.TIENHD_DK)
          return {
            ...item,
            // TIEN: Number(item.TIEN).toLocaleString('en-En'),
            // TIEN_HD: Number(item.TIEN_HD).toLocaleString('en-En'),
            // TIENHD_PS: Number(item.TIENHD_PS).toLocaleString('en-En'),
            // TIENHD_DK: Number(item.TIENHD_DK).toLocaleString('en-En')
          }
        })
        if (this.form.tongPhieuTu > 0 && this.form.tongPhieuDen >= this.form.tongPhieuTu) {
          const den = Number(this.form.tongPhieuDen)
          dataGrid = dataGrid.slice(0, den)
        }
        this.dsHoaDonDaGiao = dataGrid
        // this.appendFooter()
      } catch (error) { } finally {
        this.$root.showLoading(false)
        this.onCloseModal()
      }
    },
    giaoPhieu: async function (body) {
      console.log(body)
      try {
        const { data } = await this.axios.put(
          '/web-thuno/api/thu-no/quan-ly-dai-ly/giao-phieu-nvtc',
          body
        )
        if (data && data.error == '200') {
          this.$toast.success('Giao phiếu thành công!')
          this.getDsHoaDon()
        } else {
          this.$toast.error('Giao phiếu thất bại!')
        }
      } catch (error) {
        this.$toast.error('Giao phiếu thất bại!')
      }
    },
    huyGiao: async function () {
      // console.log(this.phieuDaGiaoChosen)
      // console.log(this.dsHoaDonDaGiao)
      // const dsHd = this.dsHoaDonDaGiao.filter((item) =>
      //   this.phieuDaGiaoChosen.includes(item.MA_TT)
      // )
      // const dsPhieu = dsHd.map((item) => item.LUOTGIAO)
      const dsPhieu = this.phieuDGSelected.map(x => x.HOADON_ID)
      const body = {
        dsGiaoPhieu: dsPhieu,
        nguoiHuy: this.$auth.getMaNhanVien(),
        mayHuy: 'test',
        chuKyNo: this.kyCuoc
      }
      console.log(body)
      try {
        const { data } = await this.axios.put(
          '/web-thuno/api/thu-no/quan-ly-dai-ly/huy-ds-giao-phieu-nvct',
          body
        )
        if (data && data.error === '200') {
          this.$toast.success('Hủy giao thành công!')
          this.getDsHoaDon()
        } else {
          this.$toast.error('Hủy giao thất bại!')
        }
      } catch (error) {
        this.$toast.error('Hủy giao thất bại!')
      }
    },
    checkBoxCGChange(args) {
      if (args.target.classList.contains('e-checkselectall')) { // click check/uncheck all
        if (args.checked) this.phieuCGSelected = cloneArray(this.dsHoaDon)
        else this.phieuCGSelected = []
        this.refreshAggPhieuCG()
      }
    },
    dsPhieuCGRowSelected(args) {
      if (!args.isHeaderCheckboxClicked && args.isInteracted) { // không phải call by click checkbox header
        this.updatePhieuCGSelected(args.data)
        this.refreshAggPhieuCG()
      }
    },
    dsPhieuCGRowDeselected(args) {
      // console.log(args)
      if (!args.isHeaderCheckboxClicked && args.isInteracted) { // không phải call by click checkbox header & paging
        this.updatePhieuCGSelected(args.data)
        this.refreshAggPhieuCG()
      }
    },
    updatePhieuCGSelected(data) {
      if (Object.prototype.toString.apply(data) === '[object Array]') {
        data.forEach((item) => {
          const index = this.phieuCGSelected.findIndex(x => x.HOADON_ID === item.HOADON_ID)
          if (index > -1) { // found
            this.phieuCGSelected.splice(index, 1)
          } else {
            this.phieuCGSelected.push(item)
          }
        })
      } else {
        const index = this.phieuCGSelected.length > 0 ? this.phieuCGSelected.findIndex(x => x.HOADON_ID === data.HOADON_ID) : -1
        if (index > -1) {
          this.phieuCGSelected.splice(index, 1)
        } else {
          this.phieuCGSelected.push(data)
        }
      }
    },
    refreshAggPhieuCG() {
      if (this.phieuCGSelected.length > 0) {
        this.chuaGiao.tienNo.chose = this.phieuCGSelected.reduce((acc, curr) => {
          return acc = Number(curr.TIEN) + acc
        }, 0)
        this.chuaGiao.tienHoaDon.chose = this.phieuCGSelected.reduce((acc, curr) => {
          return acc = Number(curr.TIEN_HD) + acc
        }, 0)
        this.chuaGiao.tienPhatSinh.chose = this.phieuCGSelected.reduce((acc, curr) => {
          return acc = Number(curr.TIENHD_PS) + acc
        }, 0)
        this.chuaGiao.tienDauKy.chose = this.phieuCGSelected.reduce((acc, curr) => {
          return acc = Number(curr.TIENHD_DK) + acc
        }, 0)
      } else {
        this.chuaGiao.tienNo.chose = 0
        this.chuaGiao.tienHoaDon.chose = 0
        this.chuaGiao.tienPhatSinh.chose = 0
        this.chuaGiao.tienDauKy.chose = 0
      }
      this.$refs.table01.$refs.grid.ej2Instances.aggregateModule.refresh()
    },
    dsPhieuCGDatabound(args) {
      let obj = this.$refs.table01.$refs.grid.ej2Instances
      if (obj.currentViewData.length > 0 && this.phieuCGSelected.length > 0) {
        let data = obj.currentViewData
        let selectedCurrentPage = []
        data.forEach((value, index) => {
          if (this.phieuCGSelected.find(x => x.HOADON_ID === value.HOADON_ID) !== undefined) {
            selectedCurrentPage.push(index)
          }
        })
        obj.selectRows(selectedCurrentPage)
      }
    },
    checkBoxDGChange(args) {
      // console.log(args)
      if (args.target.classList.contains('e-checkselectall')) { // click check/uncheck all
        // let obj = this.$refs.table02.$refs.grid.ej2Instances
        // this.checkedPhieuDG(obj.currentViewData, args.checked)
        // console.log(this.dsHoaDonDaGiao)
        if (args.checked) this.phieuDGSelected = cloneArray(this.dsHoaDonDaGiao)
        else this.phieuDGSelected = []
        // console.log(this.dsHoaDonDaGiao, this.checkedPhieuDG)
        this.refreshAggPhieuDG()
      }
    },
    dsPhieuDGRowSelected(args) {
      // console.log(args)
      if (!args.isHeaderCheckboxClicked && args.isInteracted) { // không phải call by click checkbox header
        this.updatePhieuDGSelected(args.data)
        this.refreshAggPhieuDG()
      }
    },
    dsPhieuDGRowDeselected(args) {
      // console.log(args)
      if (!args.isHeaderCheckboxClicked && args.isInteracted) { // không phải call by click checkbox header & paging
        this.updatePhieuDGSelected(args.data)
        this.refreshAggPhieuDG()
      }
    },
    updatePhieuDGSelected(data) {
      if (Object.prototype.toString.apply(data) === '[object Array]') {
        data.forEach((item) => {
          const index = this.phieuDGSelected.findIndex(x => x.HOADON_ID === item.HOADON_ID)
          if (index > -1) { // found
            this.phieuDGSelected.splice(index, 1)
          } else {
            this.phieuDGSelected.push(item)
          }
        })
      } else {
        const index = this.phieuDGSelected.length > 0 ? this.phieuDGSelected.findIndex(x => x.HOADON_ID === data.HOADON_ID) : -1
        if (index > -1) {
          this.phieuDGSelected.splice(index, 1)
        } else {
          this.phieuDGSelected.push(data)
        }
      }
    },
    refreshAggPhieuDG() {
      // console.log(this.phieuDGSelected)
      if (this.phieuDGSelected.length > 0) {
        this.daGiao.tienNo.chose = this.phieuDGSelected.reduce((acc, curr) => {
          return acc = Number(curr.TIEN) + acc
        }, 0)
        this.daGiao.tienHoaDon.chose = this.phieuDGSelected.reduce((acc, curr) => {
          return acc = Number(curr.TIEN_HD) + acc
        }, 0)
        this.daGiao.tienPhatSinh.chose = this.phieuDGSelected.reduce((acc, curr) => {
          return acc = Number(curr.TIENHD_PS) + acc
        }, 0)
        this.daGiao.tienDauKy.chose = this.phieuDGSelected.reduce((acc, curr) => {
          return acc = Number(curr.TIENHD_DK) + acc
        }, 0)
      } else {
        this.daGiao.tienNo.chose = 0
        this.daGiao.tienHoaDon.chose = 0
        this.daGiao.tienPhatSinh.chose = 0
        this.daGiao.tienDauKy.chose = 0
      }
      this.$refs.table02.$refs.grid.ej2Instances.aggregateModule.refresh()
    },
    dsPhieuDGDatabound(args) {
      // console.log('databound')
      let obj = this.$refs.table02.$refs.grid.ej2Instances
      if (obj.currentViewData.length > 0 && this.phieuDGSelected.length > 0) {
        // let selected = this.phieuDGSelected.map(x => x.HOADON_ID)
        let data = obj.currentViewData
        let selectedCurrentPage = []
        data.forEach((value, index) => {
          if (this.phieuDGSelected.find(x => x.HOADON_ID === value.HOADON_ID) !== undefined) {
            selectedCurrentPage.push(index)
          }
        })
        // console.log(this.phieuDGSelected, selectedCurrentPage)
        obj.selectRows(selectedCurrentPage)
      }
    },
    async onSelectLuotGiao(value) {
      // this.params.kieu = 2
      // this.params.giaoPhieuId = value
      // await this.getDSPhieuTon(this.params.giaoPhieuId, this.params.maTT.trim(), this.params.kieu)
      // if (this.phieuTon_grid.items.length <= 0) this.$toast.error('Không có dữ liệu, vui lòng thử lại!')
      if (this.cbkHtg && this.form.htg.id.length === 0) {
        this.$toast.error('Chưa chọn hình thức giao!')
        return false
      }
      if (this.cbkKhLon && this.form.khhl.id.length === 0) {
        this.$toast.error('Chưa chọn khách hàng lớn!')
        return false
      }
      this.getDsPhieuDaGiao(value)
    },
    keyPressedHandlerPhieuCG(args) {
      if (args.key === 'Insert') {
        this.phieuCGSelected = cloneArray(this.dsHoaDon)
      } else if (args.key === 'Delete') {
        this.phieuCGSelected = []
      }
      this.dsHoaDon = [...this.dsHoaDon]
      this.refreshAggPhieuCG()
    },
    keyPressedHandlerPhieuDG(args) {
      if (args.key === 'Insert') {
        this.phieuDGSelected = cloneArray(this.dsHoaDonDaGiao)
      } else if (args.key === 'Delete') {
        this.phieuDGSelected = []
      }
      this.dsHoaDonDaGiao = [...this.dsHoaDonDaGiao]
      this.refreshAggPhieuDG()
    }
  }
}
</script>
