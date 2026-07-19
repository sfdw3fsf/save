<template src="./AssignCustomerListForB2A.html"></template>
<style scoped src="./AssignCustomerListForB2A.scss"></style>
<script>
import XLSX from 'xlsx'
import breadcrumb from '@/components/breadcrumb'
import gridview from '@/components/Shared/gridview'
import API from './AssignCustomerListForB2AAPI'

export default {
  components: {
    gridview,
    breadcrumb
  },
  name: 'AssignCustomerListForB2A',
  data: () => ({
    customerSearchType: 'pbh',
    header: {
      title: 'Phân tập khách hàng b2a',
      list: [
        { name: 'Trang chủ', link: { name: 'Ui.cards' } },
        { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
        { name: 'Lắp đặt mới', link: { name: 'Ui.cards' } }
      ]
    },
    showDetails: true,
    phanGiaoChuaGiao: {
      list: [],
      cols: [
        { fieldName: 'ma_tb', headerText: 'Mã TB', allowFiltering: true },
        { fieldName: 'ma_kh', headerText: 'Mã KH', allowFiltering: true },
        { fieldName: 'ten_tb', headerText: 'Tên TB', allowFiltering: true },
        { fieldName: 'so_dt', headerText: 'Số DT', allowFiltering: true },
        { fieldName: 'diachi_ld', headerText: 'Địa chỉ LĐ', allowFiltering: true },
        { fieldName: 'doanhthu', headerText: 'Doanh thu', format: 'N0', type: 'Number', allowFiltering: true },
        { fieldName: 'phong_bh', headerText: 'Phòng BH', allowFiltering: true },
        { fieldName: 'tap', headerText: 'Tập', allowFiltering: true }
      ],
      checked: []
    },
    phanGiaoDaGiao: {
      list: [],
      cols: [
        { fieldName: 'srv', headerText: 'Srv', allowFiltering: true },
        { fieldName: 'ma_tb', headerText: 'Mã TB', allowFiltering: true },
        { fieldName: 'ma_kh', headerText: 'Mã KH', allowFiltering: true },
        { fieldName: 'ten_tb', headerText: 'Tên TB', allowFiltering: true },
        { fieldName: 'so_dt', headerText: 'Số ĐT', allowFiltering: true },
        { fieldName: 'diachi_ld', headerText: 'Địa chỉ lắp đặt', allowFiltering: true },
        { fieldName: 'doanhthu', headerText: 'Doanh thu', format: 'N0', type: 'Number', allowFiltering: true },
        { fieldName: 'phong_bh', headerText: 'Phòng BH', allowFiltering: true },
        { fieldName: 'tap', headerText: 'Tập', allowFiltering: true },
        { fieldName: 'ngaygiao', headerText: 'Ngày giao', allowFiltering: true },
        { fieldName: 'nguoigiao', headerText: 'Người giao', allowFiltering: true },
        { fieldName: 'ip_giao', headerText: 'IP', allowFiltering: true },
        { fieldName: 'status', headerText: 'Trạng thái', allowFiltering: true },
        { fieldName: 'phieucskh_id', headerText: 'Phiếu CSKH', allowFiltering: true },
        { fieldName: 'ma_nv', headerText: 'Mã NV HRM', allowSorting: true },
        { fieldName: 'ten_nv', headerText: 'Tên NV', allowSorting: true }
      ],
      value: null,
      checked: []
    },
    ma_srv_list: [],
    ma_srv: null,
    pbh_donvi_list: [],
    pbh_donvi_id: null,
    pbh_khuvuc_list: [],
    pbh_khuvuc_id: null,
    phb_quan_list: [],
    pbh_quan_id: null,
    phb_phuong_list: [],
    pbh_phuong_id: null,
    ttvt_donvi_id: [],
    ttvt_donvi_list: null,
    ttc_donvi_id: [],
    ttc_donvi_list: null,
    ttvt_khuvuc_list: [],
    ttvt_khuvuc_id: null,
    doiTuongPhanGiao: {
      selected: null,
      list: []
    },
    nhanvien: {
      selected: null,
      list: [],
      cols: [
        { fieldName: 'donvi', headerText: 'Tên ĐV', allowFiltering: true, isGroupedColumn: true },
        { fieldName: 'donvi', headerText: 'Tên ĐV', allowFiltering: true },
        { fieldName: 'nhanvien', headerText: 'Tên nhân viên', allowFiltering: true }
      ],
      checked: []
    },
    chkHanTraTruoc: {
      enable: false,
      value: null
    },
    chkSoLanBH: {
      enable: false,
      value: null
    },
    chkTraTruoc: {
      enable: false
    },
    chkSoThangSD: {
      enable: false,
      value: null
    },
    chkDoanhThu: {
      enable: false,
      value: null
    },
    chkKHLe: {
      enable: false,
      value: null,
      list: []
    },
    staffSearchType: 'gtd'
  }),
  computed: {},
  async mounted() {
    this.initData()
  },
  watch: {
    pbh_donvi_id() {
      this.loadPbhKhuvucList()
      this.loadPbhQuanList()
    },
    pbh_khuvuc_id() {
      this.loadPbhQuanList()
    },
    pbh_quan_id() {
      this.loadPhbPhuongList()
    },
    pbh_phuong_id() {},
    ttvt_donvi_id() {
      this.onTtvtDonviIdChanged()
    },
    ttc_donvi_id() {
      this.onTtcDonviIdChanged()
    }
  },
  methods: {
    async initData() {
      this.loading(true)
      this.doiTuongPhanGiao.list = [
        { name: 'Nhân viên kỹ thuật địa bàn', value: 1 },
        { name: 'Nhân viên kinh doanh địa bàn', value: 2 }
      ]
      this.doiTuongPhanGiao.selected = 1
      await this.loadPbhAndTtvt()
      await this.loadSrv()
      await this.loadNhanVienDuocGiao()
      await this.layDanhSachLoaiHinhThueBao()
      this.loading(false)
    },
    onPhanGiaoChuaGiaoChecked(items) {
      this.phanGiaoChuaGiao.checked = items
    },
    onPhanGiaoDaGiaoCheckedChanged(items) {
      this.phanGiaoDaGiao.checked = items
    },
    async onPbhDonviIdChanged(e) {
      await this.loadPbhKhuvucList()
    },
    async onTtvtDonviIdChanged(e) {
      await this.loadTtcList()
    },
    async loadPbhKhuvucList() {
      this.loading(true)
      let data = await API.b2a_lay_khuvuc_theo_donvi(this.axios, { vdonvi_id: this.pbh_donvi_id })
      if (data.data.data && data.data.data.length > 0) {
        this.pbh_khuvuc_list = data.data.data
        this.pbh_khuvuc_id = this.pbh_khuvuc_list[0].khuvuc_id
      } else {
        this.pbh_khuvuc_list = []
        this.pbh_khuvuc_id = null
      }
      this.loading(false)
    },
    async onPbhKhuvucIdChanged(e) {
      await this.loadPbhQuanList()
    },
    async loadPbhQuanList() {
      this.loading(true)
      let data = await API.b2a_lay_quan_theo_phongbh(this.axios, { vdonvi_id: this.pbh_donvi_id })
      if (data.data.data && data.data.data.length > 0) {
        this.phb_quan_list = data.data.data
        this.pbh_quan_id = this.phb_quan_list[0].quan_id
      } else {
        this.phb_quan_list = []
        this.pbh_quan_id = null
      }
      this.loading(false)
    },
    async onPbhQuanIdChanged(e) {},
    async loadPhbPhuongList() {
      this.loading(true)
      let data = await API.b2a_get_phuong_theo_quan(this.axios, { vquan_id: this.pbh_quan_id })
      if (data.data.data && data.data.data.length > 0) {
        this.phb_phuong_list = data.data.data
        this.pbh_phuong_id = this.phb_phuong_list[0].phuong_id
      } else {
        this.phb_phuong_list = []
        this.pbh_phuong_id = null
      }
      this.loading(false)
    },
    async loadPbhAndTtvt() {
      this.loading(true)
      let pbh_data = await API.b2a_lay_ds_donvi_ql(this.axios, { vdonvi_id: 900, vloaidv_id: 2 })
      if (pbh_data.data.data && pbh_data.data.data.length > 0) {
        this.pbh_donvi_list = pbh_data.data.data
        this.pbh_donvi_id = this.pbh_donvi_list[0].donvi_id
      } else {
        this.pbh_donvi_list = []
        this.pbh_donvi_id = null
      }
      let ttvt_data = await API.b2a_lay_ds_donvi_ql(this.axios, { vdonvi_id: 900, vloaidv_id: 3 })
      if (ttvt_data.data.data && ttvt_data.data.data.length > 0) {
        this.ttvt_donvi_list = ttvt_data.data.data
        this.ttvt_donvi_id = this.ttvt_donvi_list[0].donvi_id
      } else {
        this.ttvt_donvi_list = []
        this.ttvt_donvi_id = null
      }
      this.loading(false)
    },
    async loadSrv() {
      this.loading(true)
      let data = await API.post_getds_onload(this.axios, { loaidv_id: 2 })
      if (data.data.data && data.data.data.cbo && data.data.data.cbo.length > 0) {
        this.ma_srv_list = data.data.data.cbo
        this.ma_srv = this.ma_srv_list[0].ma_srv
      } else {
        this.ma_srv_list = []
        this.ma_srv = null
      }
      this.loading(false)
    },
    async loadTtcList() {
      this.loading(true)
      let data = await API.b2a_lay_ds_donvi_ql(this.axios, { vdonvi_id: this.ttvt_donvi_id, vloaidv_id: 5 })
      if (data.data.data && data.data.data.length > 0) {
        this.ttc_donvi_list = data.data.data
        this.ttc_donvi_id = this.ttc_donvi_list[0].donvi_id
      } else {
        this.ttc_donvi_list = []
        this.ttc_donvi_id = null
      }
      this.loading(false)
    },
    async onTtcDonviIdChanged() {
      await this.loadTtvtKhuvucList()
    },
    async loadTtvtKhuvucList() {
      this.loading(true)
      console.log('this.ttc_donvi_id')
      console.log(this.ttc_donvi_id)
      let data = await API.b2a_lay_khuvuc_theo_donvi(this.axios, { vdonvi_id: this.ttc_donvi_id })
      if (data.data.data && data.data.data.length > 0) {
        this.ttvt_khuvuc_list = data.data.data
        this.ttvt_khuvuc_id = this.ttvt_khuvuc_list[0].khuvuc_id
      } else {
        this.ttvt_khuvuc_list = []
        this.ttvt_khuvuc_id = null
      }
      this.loading(false)
    },
    async loadNhanVienDuocGiao() {
      this.loading(true)
      let data = await API.b2a_lay_ds_nhanvien_duocgiao(this.axios, {})
      if (data.data.data && data.data.data.length > 0) {
        this.nhanvien.list = data.data.data
        this.nhanvien.list = this.nhanvien.list.map((e) => {
          return {
            name: `(${e.donvi}) ${e.nhanvien}`,
            value: e.nhanvien_id
          }
        })
        // this.ttvt_khuvuc_id = this.ttvt_khuvuc_list[0].khuvuc_id
      } else {
        this.ttvt_khuvuc_list = []
        // this.ttvt_khuvuc_id = null
      }
      this.loading(false)
    },
    async layDanhSachChuaGiao() {
      if (this.ma_srv == null) {
        this.$toast.warning('Chưa chọn chính sách phân giao!')
      }
      let vkieu = 0
      let vphongbh_id = 0
      let vttvt_id = 0
      let vtramtc_id = 0
      let vkhuvuc_id = 0
      let vquan_id = 0
      let vphuong_id = 0

      let hantratruoc = -1
      let solanbh = -1
      let kotratruoc = 0
      let sothangsd = -1
      let doanhthu = -1
      let loaihinh = -1

      if (this.customerSearchType == 'pbh') {
        vkieu = 1
        if (this.pbh_donvi_id == '-1') {
          this.$toast.warning('Chưa chọn phòng bán hàng!')
          return
        }
        vphongbh_id = this.pbh_donvi_id

        if (this.pbh_khuvuc_id == '0') {
          vkhuvuc_id = 0
        } else vkhuvuc_id = this.pbh_khuvuc_id
        if (this.pbh_quan_id == null) vquan_id = 0
        else vquan_id = this.pbh_quan_id
        if (this.pbh_phuong_id == null) vphuong_id = 0
        else vphuong_id = this.pbh_phuong_id
      } else {
        vkieu = 2
        if (this.ttvt_donvi_id == '-1') {
          this.$toast.warning('Chưa chọn phòng Trung tâm viễn thông!')
          return
        }
        vttvt_id = this.ttvt_donvi_id

        if (this.ttc_donvi_id == '-1') {
          vtramtc_id = 0
        } else vtramtc_id = this.ttc_donvi_id

        if (this.ttvt_khuvuc_id == '-1') {
          vkhuvuc_id = 0
        } else vkhuvuc_id = this.ttvt_khuvuc_id
      }

      if (this.chkHanTraTruoc.enable) hantratruoc = this.chkHanTraTruoc.value
      if (this.chkSoLanBH.enable) solanbh = this.chkSoLanBH.value
      if (this.chkTraTruoc.enable) kotratruoc = 1
      if (this.chkSoThangSD.enable) sothangsd = this.chkSoThangSD.value
      if (this.chkDoanhThu.enable) doanhthu = this.chkDoanhThu.value
      if (this.chkKHLe.enable) loaihinh = this.chkKHLe.value
      this.loading(true)
      try {
        let data = await API.b2a_lay_ds_chuagiao(this.axios, {
          vkieu,
          vsrv: this.ma_srv,
          vphongbh_id,
          vttvt_id,
          vtramtc_id,
          vkhuvuc_id,
          vquan_id,
          vphuong_id,
          vhantratruoc: hantratruoc,
          vsolanbh: solanbh,
          vkotratruoc: kotratruoc,
          vsothangsd: sothangsd,
          vdoanhthu: doanhthu,
          vloaihinh: loaihinh
        })
        if (data.data.data && data.data.data.length) {
          this.phanGiaoChuaGiao.list = data.data.data
        } else {
          this.phanGiaoChuaGiao.list = []
        }
      } catch (e) {
        this.$toast.error(`${e}`)
      }
      this.loading(false)
    },
    async layDanhSachDaGiao() {
      if (this.staffSearchType == 'gtd') return
      if (this.nhanvien.selected == null) {
        this.$toast.error('Chưa chọn nhân viên được phân giao!')
        return
      }
      this.loading(true)
      let data = await API.b2a_lay_danhsach_dagiao(this.axios, { vnhanvien_id: this.nhanvien.selected })
      if (data.data.data && data.data.data.length > 0) {
        this.phanGiaoDaGiao.list = data.data.data
      } else {
        this.phanGiaoDaGiao.list = []
      }
      this.loading(false)
    },
    async phanGiao() {
      if (this.phanGiaoChuaGiao.checked.length == 0) {
        this.$toast.warning('Bạn chưa chọn khách hàng muốn phân giao !')
        return
      }
      let vkieu = 0
      if (this.staffSearchType == 'gtd') {
        vkieu = this.doiTuongPhanGiao.selected
      } else {
        if (this.nhanvien.selected == null) {
          this.$toast.warning('Chưa chọn nhân viên được phân giao!')
          return
        }
      }
      console.log('vkieu')
      console.log(vkieu)
      const vjson_thuebao = `[${this.phanGiaoChuaGiao.checked.map((e) => e.thuebao_id).join(',')}]`
      const vnhanvien_nhan_id = this.nhanvien.selected
      const vnguoigiao = this.$root.token.getUserName()
      const vip_giao = await this.$root.token.getIP()
      const vnguoi_cn = this.$root.token.getUserName()
      this.loading(true)
      try {
        let data = await API.b2a_phangiao_v2(this.axios, {
          vsrv: this.ma_srv,
          vjson_thuebao,
          vnhanvien_nhan_id,
          vnguoigiao,
          vip_giao,
          vnguoi_cn,
          vkieu
        })
        if (data.data.error_code == 'BSS-00000000') {
          if (data.data.data && data.data.data == 'OK') {
            this.$toast.success('Phân giao thành công!')
            await this.layDanhSachChuaGiao()
            await this.layDanhSachDaGiao()
          } else {
            this.$toast.error(data.data.data)
          }
        } else {
          this.$toast.error('Phân giao không thành công!')
        }
      } catch (e) {
        this.$toast.error(`${e}`)
      }
      this.loading(false)
    },
    async huyGiao() {
      const lstDanhSach = this.phanGiaoDaGiao.checked
      // .filter((e) => e.chon == 's1')
      const listDaB2a = this.phanGiaoDaGiao.checked.filter((e) => e.chon == 's1' && e.trangthai != 0)
      if (listDaB2a.length > 0) {
        this.$toast.warning('Trong danh sách chọn có khách hàng đã được xuất phiếu chăm sóc, không thể hủy!')
        return
      }
      if (lstDanhSach.length > 0) {
        const vjson_thuebao = lstDanhSach.map((e) => {
          return {
            thuebao_id: e.thuebao_id,
            srv: e.srv
          }
        })
        const vnguoigiao = this.$root.token.getUserName()
        const vip_giao = await this.$root.token.getIP()
        const vnguoi_cn = this.$root.token.getUserName()
        this.loading(true)

        try { 
          // let data = await API.b2a_huy_phangiao(this.axios, {
          //   vjson_thuebao,
          //   vnguoigiao,
          //   vip_giao,
          //   vnguoi_cn
          // })
          console.log(JSON.stringify(vjson_thuebao))
          let data = await API.b2a_huy_phangiao(this.axios, {
            vsrv: this.phanGiaoDaGiao.value.srv,
            vjson_thuebao: JSON.stringify(vjson_thuebao),
            vnguoigiao,
            vip_giao,
            vnguoi_cn
          })
          
          if (data.data.error_code == 'BSS-00000000') {
            if (data.data.data && data.data.data == 'OK') {
              this.$toast.success('Hủy phân giao thành công!')
              await this.layDanhSachChuaGiao()
              await this.layDanhSachDaGiao()
            } else {
              this.$toast.error(data.data.data)
            }
          } else {
            this.$toast.error('Hủy phân giao không thành công!')
          }
        } catch (e) {
          this.$toast.error(`${e}`)
        }
        this.loading(false)
      } else {
        this.$toast.warning('Bạn chưa chọn khách hàng muốn hủy phân giao !')
      }
    },
    nhanVienChanged(args) {
      this.nhanvien.selected = args[0]
    },
    onPhanGiaoChuaGiaoExportClicked() {
      this.exportExcelByCols(this.phanGiaoChuaGiao.list, this.phanGiaoChuaGiao.cols)
    },
    onPhanGiaoDaGiaoExportClicked() {
      this.exportExcelByCols(this.phanGiaoDaGiao.list, this.phanGiaoDaGiao.cols)
    },
    exportExcelByCols(inputArray, arrayFields) {
      let exportData = []
      exportData = inputArray.map((e) => {
        let exportEntity = {}
        arrayFields.map((x) => {
          exportEntity[x.fieldName] = e[x.fieldName]
        })
        return exportEntity
      })
      if (exportData.length > 0) {
        let worksheet = XLSX.utils.json_to_sheet(exportData)
        let workbook = XLSX.utils.book_new()
        XLSX.utils.book_append_sheet(workbook, worksheet, 'Dữ liệu')
        XLSX.writeFile(workbook, 'export.xlsx')
      } else {
        this.$toast.warning('Không có dữ liệu để xuất file!')
      }
    },
    async layDanhSachLoaiHinhThueBao() {
      let data = await API.CSS_LOAIHINH_TB(this.axios)
      if (data.data.data && data.data.data.length > 0) {
        this.chkKHLe.list = data.data.data
      } else {
        this.chkKHLe.list = []
      }
    },
    async phanGiaoDaGiaoRowSelected(e) {
      console.log('phanGiaoDaGiaoRowSelected')
      console.log(e)
      if(e.data) {
        this.phanGiaoDaGiao.value = e.data
      }
    }
  }
}
</script>
