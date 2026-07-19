<template src="./SearchMaintenanceSlipNotDone.html"></template>
<script>
import Split from '../../../../../static/vendor/jquery/split.js'
import gridView from '@/components/Shared/gridview.vue'
import moment from 'moment'
import { getDanhSachDonViTreeView, spNapCboTrangthaiCC, layDSHoSoCaiTaoBaoDuongTT, layDanhSachPTMCaiTaoBaoDuong, spNapGRDTienTrinhCC, layDSGVCaiTaoBaoDuong, traCuuPhieuCC } from './api/index.js'
import { customTreeItem } from './plugins/customTreeItem'
import { Filter, Page, Resize, Freeze, Group } from '@syncfusion/ej2-vue-grids'
import { pagingAndFilter } from '@/modules/admin/category/Staff/mixins/pagingAndFilter.js'
export default {
  name: 'SearchMaintenanceSlipNotDone',
  mixins: [pagingAndFilter],
  components: {
    gridView,
    ListCayDonVi: () => import('./parts/DSCayDonVi.vue'),
    DatePicker: () => import('vue2-datepicker'),
    LayDuLieu: () => import('./LayDuLieu.vue')
  },
  mounted() {
    this.loadDataInitializePage()
    $('.select2js').select2()
    Split(['#boxLeft', '#boxMid', '#boxRight'], {
      sizes: [30, 40, 30]
    })
  },
  data: () => ({
    danhSachPhanTuMang: {
      headers: [
        {
          fieldName: 'gr',
          headerText: 'Group',
          allowFiltering: true,
          isGroupedColumn: true
        },
        {
          fieldName: 'ten_tbi',
          headerText: 'Tên Phần Tử Mạng',
          allowFiltering: true
        },
        {
          fieldName: 'tinhtrang',
          headerText: 'Tình Trạng',
          allowFiltering: true
        },
        {
          fieldName: 'trangthai',
          headerText: 'Trạng Thái',
          allowFiltering: true
        },
        {
          fieldName: 'ngay_hs',
          headerText: 'Ngày Cập Nhật',
          allowFiltering: true
        },
        {
          fieldName: 'nguoi_cn',
          headerText: 'Người Cập Nhật',
          allowFiltering: true
        }
      ],
      headersNoData: [
        {
          fieldName: 'gr',
          headerText: 'Group',
          allowFiltering: true
        },
        {
          fieldName: 'ten_tbi',
          headerText: 'Tên Phần Tử Mạng',
          allowFiltering: true
        },
        {
          fieldName: 'tinhtrang',
          headerText: 'Tình Trạng',
          allowFiltering: true
        },
        {
          fieldName: 'trangthai',
          headerText: 'Trạng Thái',
          allowFiltering: true
        },
        {
          fieldName: 'ngay_hs',
          headerText: 'Ngày Cập Nhật',
          allowFiltering: true
        },
        {
          fieldName: 'nguoi_cn',
          headerText: 'Người Cập Nhật',
          allowFiltering: true
        }
      ],
      data: []
    },
    danhSachTienTrinh: {
      headers: [
        {
          fieldName: 'nguoi_cn',
          headerText: 'Người Cập Nhật',
          allowFiltering: true
        },
        {
          fieldName: 'ngay_cn',
          headerText: 'Ngày Cập Nhật',
          allowFiltering: true
        },
        {
          fieldName: 'donvi_cn',
          headerText: 'Đơn Vị',
          allowFiltering: true
        },
        {
          fieldName: 'ngay_dukien_bd',
          headerText: 'Ngày Dự Kiến BD',
          allowFiltering: true
        },
        {
          fieldName: 'ngay_dukien_kt',
          headerText: 'Ngày Dự Kiến KT',
          allowFiltering: true
        },
        {
          fieldName: 'noidung',
          headerText: 'Nội Dung',
          allowFiltering: true
        }
      ],
      data: []
    },
    danhSachGiaoViec: {
      headers: [
        {
          fieldName: 'ten_nv',
          headerText: 'Tên Nhân Viên',
          allowFiltering: true
        },
        {
          fieldName: 'so_dt',
          headerText: 'ĐT liên hệ',
          allowFiltering: true
        },
        {
          fieldName: 'ngaygiao',
          headerText: 'Ngày Giao',
          allowFiltering: true
        },
        {
          fieldName: 'nhiemvu',
          headerText: 'Nhiệm Vụ',
          allowFiltering: true
        },
        {
          fieldName: 'ketqua_xl',
          headerText: 'Kết Quả',
          allowFiltering: true
        },
        {
          fieldName: 'nd_tontai',
          headerText: 'ND Tồn Tại',
          allowFiltering: true
        },
        {
          fieldName: 'nd_dexuat',
          headerText: 'ND Đề Xuất',
          allowFiltering: true
        },
        {
          fieldName: 'ghichu_xl',
          headerText: 'Ghi Chú',
          allowFiltering: true
        },
        {
          fieldName: 'trangthai_xl',
          headerText: 'Trạng thái TH',
          allowFiltering: true
        }
      ],
      data: []
    },
    danhSachPhieuYeuCau: {
      headers: [
        {
          fieldName: 'loai_hs_cc',
          headerText: 'Loại HSCC',
          allowFiltering: true
        },
        {
          fieldName: 'ma_hs_cc',
          headerText: 'MÃ HSCC',
          allowFiltering: true
        },
        {
          fieldName: 'trangthai_cc',
          headerText: 'Trạng Thái',
          allowFiltering: true
        },
        {
          fieldName: 'ttvt',
          headerText: 'Đơn Vị',
          allowFiltering: true
        },
        {
          fieldName: 'ngay_yc',
          headerText: 'Ngày YC',
          allowFiltering: true
        },
        {
          fieldName: 'ten_kieu',
          headerText: 'Kiểu CC',
          allowFiltering: true
        },
        {
          fieldName: 'nguoi_cn',
          headerText: 'Người YC',
          allowFiltering: true
        },
        {
          fieldName: 'so_dt',
          headerText: 'Sô ĐT',
          allowFiltering: true
        },
        {
          fieldName: 'ten_yc',
          headerText: 'Đơn Vị YC',
          allowFiltering: true
        },
        {
          fieldName: 'nv_giao',
          headerText: 'Nhân Viên Giao',
          allowFiltering: true
        },
        {
          fieldName: 'dv_giao',
          headerText: 'Đơn Vị Giao',
          allowFiltering: true
        },
        {
          fieldName: 'nd_giao',
          headerText: 'Nội Dung Giao',
          allowFiltering: true
        },
        {
          fieldName: 'ghichu',
          headerText: 'Ghi Chú',
          allowFiltering: true
        }
      ],
      data: []
    },
    danhSachChiTietDieuHanhThiCong: {
      headers: [
        {
          fieldName: 'dvg',
          headerText: 'Group',
          allowFiltering: true,
          isGroupedColumn: true
        },
        {
          fieldName: 'ngay_giao',
          headerText: 'Ngày Giao',
          allowFiltering: true
        },
        {
          fieldName: 'nv_giao',
          headerText: 'Người Giao',
          allowFiltering: true
        },
        {
          fieldName: 'nd_giao',
          headerText: 'Nội Dung Giao',
          allowFiltering: true
        },
        {
          fieldName: 'dvn',
          headerText: 'Đơn Vị Nhận',
          allowFiltering: true
        },
        {
          fieldName: 'trangthai_ph',
          headerText: 'Trạng Thái Phiếu',
          allowFiltering: true
        },
        {
          fieldName: 'ngay_tra',
          headerText: 'Ngày Trả',
          allowFiltering: true
        },
        {
          fieldName: 'nd_tra',
          headerText: 'Nội Dung Trả',
          allowFiltering: true
        },
        {
          fieldName: 'lydotra',
          headerText: 'Lý Do Trả',
          allowFiltering: true
        },
        {
          fieldName: 'dv_giao',
          headerText: 'Ngày CN',
          allowFiltering: true
        },
        {
          fieldName: 'nv_th',
          headerText: 'Người Thực Hiện',
          allowFiltering: true
        },
        {
          fieldName: 'so_dt',
          headerText: 'Điện Thoại Liên Hệ',
          allowFiltering: true
        },
        {
          fieldName: 'ghichu',
          headerText: 'Ghi Chú',
          allowFiltering: true
        },
        {
          fieldName: 'ghichu',
          headerText: 'Kết Quả XL',
          allowFiltering: true
        },
        {
          fieldName: 'ghichu',
          headerText: 'Ứng Dụng',
          allowFiltering: true
        },
        {
          fieldName: 'ghichu',
          headerText: 'Người Xử Lý',
          allowFiltering: true
        }
      ],
      headersNoData: [
        {
          fieldName: 'dvg',
          headerText: 'Group',
          allowFiltering: true
        },
        {
          fieldName: 'ngay_giao',
          headerText: 'Ngày Giao',
          allowFiltering: true
        },
        {
          fieldName: 'nv_giao',
          headerText: 'Người Giao',
          allowFiltering: true
        },
        {
          fieldName: 'nd_giao',
          headerText: 'Nội Dung Giao',
          allowFiltering: true
        },
        {
          fieldName: 'dvn',
          headerText: 'Đơn Vị Nhận',
          allowFiltering: true
        },
        {
          fieldName: 'trangthai_ph',
          headerText: 'Trạng Thái Phiếu',
          allowFiltering: true
        },
        {
          fieldName: 'ngay_tra',
          headerText: 'Ngày Trả',
          allowFiltering: true
        },
        {
          fieldName: 'nd_tra',
          headerText: 'Nội Dung Trả',
          allowFiltering: true
        },
        {
          fieldName: 'lydotra',
          headerText: 'Lý Do Trả',
          allowFiltering: true
        },
        {
          fieldName: 'dv_giao',
          headerText: 'Ngày CN',
          allowFiltering: true
        },
        {
          fieldName: 'nv_th',
          headerText: 'Người Thực Hiện',
          allowFiltering: true
        },
        {
          fieldName: 'so_dt',
          headerText: 'Điện Thoại Liên Hệ',
          allowFiltering: true
        },
        {
          fieldName: 'ghichu',
          headerText: 'Ghi Chú',
          allowFiltering: true
        },
        {
          fieldName: 'ghichu',
          headerText: 'Kết Quả XL',
          allowFiltering: true
        },
        {
          fieldName: 'ghichu',
          headerText: 'Ứng Dụng',
          allowFiltering: true
        },
        {
          fieldName: 'ghichu',
          headerText: 'Người Xử Lý',
          allowFiltering: true
        }
      ],
      data: []
    },
    IDdonvi: 0,
    dsDonVi: [],
    trangThaiCC: [],
    form: {
      maHSCC: '',
      ttcc_id: 0,
      tuNgay: moment(new Date()).format('DD/MM/YYYY'),
      denNgay: moment(new Date()).format('DD/MM/YYYY')
    },
    vhscc_id: 0,
    vphieucc_id: 0,
    chitietcc_id: 0,
    paramsHTDSCTBD: {},
    danhSachDonViTreeView: [],
    selectedDonViArray: [],
    keywordSearchUnit: '',
    columns: 2,
    groupOptions: { showDropArea: false, columns: ['dvg'] },
    groupOptionsDSPTM: { showDropArea: false, columns: ['gr'] },
    treeItemTemplate: customTreeItem,
    momentFormat: {
      stringify: (date) => {
        return date ? moment(date).format('DD/MM/YYYY') : ''
      },
      parse: (value) => {
        return value ? moment(value, 'DD/MM/YYYY').toDate() : null
      }
    },
    checkedDate: false
  }),
  provide: {
    grid: [Freeze, Filter, Page, Resize, Group]
  },
  methods: {
    ShowAlertSuccess(mesage) {
      this.$toast.success(mesage)
    },
    ShowAlertError(mesage) {
      this.$toast.error(mesage)
    },
    async loadDataInitializePage() {
      await Promise.all([spNapCboTrangthaiCC(this.axios), getDanhSachDonViTreeView(this.axios)])
        .then((response) => {
          this.trangThaiCC = response[0].map((x) => ({ id: x.ttcc_id, text: x.trangthai_cc }))
          this.danhSachDonViTreeView = response[1]
        })
        .catch(() => {
          this.$toast.error('Lỗi tải dữ liệu')
          this.loading(false)
        })
      this.originalListUnit = JSON.parse(JSON.stringify(this.danhSachDonViTreeView))

      if (!this.danhSachDonViTreeView || this.danhSachDonViTreeView.length === 0) {
        this.loading(false)
        return
      }
      this.$nextTick(() => {
        this.selectedDonViArray = [this.danhSachDonViTreeView[0].donvi_id]
        this.IDdonvi = this.danhSachDonViTreeView[0].donvi_id
        this.$refs.treeViewDonVi.expandAll([this.danhSachDonViTreeView[0].donvi_id])
      })
    },
    searchUnitTreeView() {
      const lstUnit = this.filterListUnit(this.originalListUnit)
      if (lstUnit.length === 0) {
        this.$toast.error(`Không tìm thấy đơn vị: ${this.keywordSearchUnit}`)
        return
      }
      this.danhSachDonViTreeView = lstUnit
      this.$nextTick(() => {
        this.$refs.treeViewDonVi.expandAll()
      })
    },
    getUnitMatch(unit, keyword) {
      if (unit.ten_dv.toLowerCase().includes(keyword.trim().toLowerCase())) {
        return {
          ten_dv: unit.ten_dv,
          unitlevel: unit.unitlevel,
          donvi_id: unit.donvi_id,
          donvi_cha_id: unit.donvi_cha_id
        }
      }
      return null
    },
    filterListUnit(lstUnit) {
      return lstUnit.map((unit) => {
        if (unit.children && unit.children.length > 0) {
          const listUnitMatch = this.filterListUnit(unit.children).filter((matchUnit) => !!matchUnit)
          if (listUnitMatch && listUnitMatch.length > 0) {
            return {
              ten_dv: unit.ten_dv,
              unitlevel: unit.unitlevel,
              donvi_id: unit.donvi_id,
              donvi_cha_id: unit.donvi_cha_id,
              children: listUnitMatch
            }
          }
        }
        return this.getUnitMatch(unit, this.keywordSearchUnit)
      })
    },
    btnTimKiem_Click() {
      if (this.IDdonvi === -1 || this.IDdonvi === 0) {
        this.ShowAlertError('Bạn chưa chọn đơn vị!')
        return
      }
      this.HienThi_DS_CaiTao_BaoDuong()
    },
    onSelectStore: async function(selectedStore) {
      if (selectedStore) {
        this.IDdonvi = selectedStore.donvi_id
      }
    },
    onEnter() {
      if (this.form.maHSCC === '') return false
      if (this.IDdonvi === -1 || this.IDdonvi === 0) {
        this.ShowAlertError('Bạn chưa chọn đơn vị!')
        return
      }
      this.HienThi_DS_CaiTao_BaoDuong()
    },
    async HienThi_DS_CaiTao_BaoDuong() {
      if (this.checkedDate) {
        if (this.form.tuNgay == null || this.form.denNgay == null) {
          this.ShowAlertError('Bạn hãy chọn lại từ ngày, đến ngày !')
          return
        }
        this.paramsHTDSCTBD = {
          vdonvi_id: this.IDdonvi,
          vtu_ngay: this.form.tuNgay,
          vden_ngay: this.form.denNgay,
          vma_hs_cc: this.form.maHSCC,
          vttcc_id: this.form.ttcc_id
        }
      } else {
        this.paramsHTDSCTBD = {
          vtu_ngay: 0,
          vden_ngay: 0,
          vdonvi_id: this.IDdonvi,
          vma_hs_cc: this.form.maHSCC,
          vttcc_id: this.form.ttcc_id
        }
      }
      const dataDanhSachPhieuYeuCau = await layDSHoSoCaiTaoBaoDuongTT(this.axios, this.paramsHTDSCTBD).catch((err) => {
        this.$toast.error(err.data.message)
        this.loading(false)
      })
      if (dataDanhSachPhieuYeuCau.length < 1) {
        this.clearData()
        this.$toast.error('Không có danh sách để hiển thị. Vui lòng chọn lại!')
      }
      this.formatDate(dataDanhSachPhieuYeuCau, 'ngay_yc')
      this.danhSachPhieuYeuCau.data = dataDanhSachPhieuYeuCau
      this.form.maHSCC = dataDanhSachPhieuYeuCau[0].ma_hs_cc
      this.vhscc_id = dataDanhSachPhieuYeuCau[0].hscc_id
      this.vphieucc_id = dataDanhSachPhieuYeuCau[0].phieucc_id
      const paramLayDanhSachPTMCaiTaoBaoDuong = {
        vhscc_id: this.vhscc_id,
        vphieucc_id: this.vphieucc_id
      }
      const dataDanhSachPhanTuMang = await layDanhSachPTMCaiTaoBaoDuong(this.axios, paramLayDanhSachPTMCaiTaoBaoDuong).catch((err) => {
        this.$toast.error(err.data.message)
        this.loading(false)
      })
      console.log('dataDanhSachPhanTuMang === >', dataDanhSachPhanTuMang)
      this.formatDate(dataDanhSachPhanTuMang, 'ngay_hs')
      this.danhSachPhanTuMang.data = dataDanhSachPhanTuMang
      const dataSpNapGRDTienTrinhCC = await spNapGRDTienTrinhCC(this.axios, dataDanhSachPhanTuMang[0].chitietcc_id).catch((err) => {
        this.$toast.error(err.data.message)
        this.loading(false)
      })
      // this.formatDate(dataSpNapGRDTienTrinhCC, 'ngay_cn')
      dataSpNapGRDTienTrinhCC.map((item) => (item.ngay_cn = moment(item.ngay_cn, 'DD-MM-YYYY hh:mm:ss').format('DD/MM/YYYY')))
      this.danhSachTienTrinh.data = dataSpNapGRDTienTrinhCC
      console.log('dataDanhSachPhanTuMang')
      console.log(dataDanhSachPhanTuMang[0].chitietcc_id)
      const paramDSGVCaiTaoBaoDuong = {
        vchitietcc_id: dataDanhSachPhanTuMang[0].chitietcc_id,
        vphieucc_id: dataDanhSachPhieuYeuCau[0].phieucc_id
      }
      const dataDSGVCaiTaoBaoDuong = await layDSGVCaiTaoBaoDuong(this.axios, paramDSGVCaiTaoBaoDuong).catch((err) => {
        this.$toast.error(err.data.message)
        this.loading(false)
      })
      this.formatDate(dataDSGVCaiTaoBaoDuong, 'ngaygiao')
      this.danhSachGiaoViec.data = dataDSGVCaiTaoBaoDuong
    },
    clearData() {
      this.danhSachPhanTuMang.data = []
      this.danhSachTienTrinh.data = []
      this.danhSachGiaoViec.data = []
      this.danhSachChiTietDieuHanhThiCong.data = []
    },
    handleExportFile() {
      const dataExportFile = this.danhSachPhieuYeuCau.data.map((item) => {
        return {
          loai_hs_cc: item.loai_hs_cc,
          ma_hs_cc: item.ma_hs_cc,
          trangthai_cc: item.trangthai_cc,
          dv: item.ttvt,
          ngay_yc: item.ngay_yc,
          ten_kieu: item.ten_kieu,
          nguoi_cn: item.nguoi_cn,
          so_dt: item.so_dt,
          ten_yc: item.ten_yc,
          nv_giao: item.nv_giao,
          dv_giao: item.dv_giao,
          nd_giao: item.nd_giao,
          ghichu: item.ghichu
        }
      })
      this.$refs.popupLayDuLieuTraCuuPhieuBaoDuong.openDialog(dataExportFile)
    },
    formatDate(array, dateFormat) {
      array.map((item) => (item[`${dateFormat}`] = moment(item[`${dateFormat}`], 'DD/MM/YYYY hh:mm:ss').format('DD/MM/YYYY')))
    },
    changeDonViTreeView($event) {
      this.selectedDonVi = $event.nodeData.id
      this.IDdonvi = $event.nodeData.id
      this.form.maHSCC = ''
      // console.log('this.IDdonvi===>', this.IDdonvi)
    },
    danhSachPhieuYeuCauSelectedChanged: async function(rowData) {
      rowData = rowData[0]
      this.form.maHSCC = rowData.ma_hs_cc
      if (rowData && rowData && rowData.hscc_id === this.vhscc_id && rowData.phieucc_id === this.vphieucc_id) {
        return
      } else {
        this.vhscc_id = rowData.hscc_id
        this.vphieucc_id = rowData.phieucc_id
      }
      const paramLayDanhSachPTMCaiTaoBaoDuong = {
        vhscc_id: this.vhscc_id,
        vphieucc_id: this.vphieucc_id
      }
      const dataDanhSachPhanTuMang = await layDanhSachPTMCaiTaoBaoDuong(this.axios, paramLayDanhSachPTMCaiTaoBaoDuong).catch((err) => {
        this.$toast.error(err.data.message)
        this.loading(false)
      })
      this.danhSachPhanTuMang.data = dataDanhSachPhanTuMang
      const paramDSGVCaiTaoBaoDuong = {
        vchitietcc_id: dataDanhSachPhanTuMang[0].chitietcc_id,
        vphieucc_id: rowData.phieucc_id
      }
      const dataDSGVCaiTaoBaoDuong = await layDSGVCaiTaoBaoDuong(this.axios, paramDSGVCaiTaoBaoDuong).catch((err) => {
        this.$toast.error(err.data.message)
        this.loading(false)
      })
      this.danhSachGiaoViec.data = dataDSGVCaiTaoBaoDuong
      const dataSpNapGRDTienTrinhCC = await spNapGRDTienTrinhCC(this.axios, dataDanhSachPhanTuMang[0].chitietcc_id).catch((err) => {
        this.$toast.error(err.data.message)
        this.loading(false)
      })
      this.danhSachTienTrinh.data = dataSpNapGRDTienTrinhCC
      const dataTraCuuPhieuCC = await traCuuPhieuCC(this.axios, rowData.hscc_id).catch((err) => {
        this.$toast.error(err.data.message)
        this.loading(false)
      })
      this.danhSachChiTietDieuHanhThiCong.data = dataTraCuuPhieuCC
    }
  },
  computed: {
    getTuNgay() {
      return this.form.tuNgay
    },
    getDenNgay() {
      return this.form.denNgay
    }
  },
  watch: {
    getTuNgay(newVal) {
      this.form.tuNgay = newVal
    },
    getDenNgay(newVal) {
      this.form.denNgay = newVal
    },
    checkedDate(newVal) {
      !newVal ? (this.form.tuNgay = null) : (this.form.tuNgay = this.form.tuNgay)
      !newVal ? (this.form.denNgay = null) : (this.form.denNgay = this.form.denNgay)
    }
  }
}
</script>
<style scoped>
.box-form {
  height: 100%;
}
.tree-plus {
  height: 90%;
}
.fullh {
  height: 100% !important;
}
.search-input::after {
  font-family: 'Nucleo Outline';
  content: '\ed39';
  padding: 6px 7px 3px 3px;
  z-index: 1;
  position: absolute;
  right: 0;
  color: #d2d2d2;
}

.search-input::before {
  content: '';
}

.search-input {
  margin-bottom: 10px;
}

.tree-unit {
  max-height: 1358px;
  min-height: 1050px;
  overflow: auto;
}

.e-control.e-treeview {
  overflow: auto;
  /* height: 450px; */
  max-height: calc(100vh - 400px);
}
.main-wrapper .e-grid td.e-active {
  background-color: #f9e1a9 !important;
}

.e-grid td.e-active {
  background-color: #f9e1a9 !important;
}
</style>
