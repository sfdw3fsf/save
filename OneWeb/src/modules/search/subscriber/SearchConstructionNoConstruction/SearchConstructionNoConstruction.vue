<template src="./SearchConstructionNoConstruction.html"></template>
<style src="./SearchConstructionNoConstruction.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import SearchConstructionNoConstructionAPI from './SearchConstructionNoConstructionAPI'
import treemenu from './treemenu'
import xlsx from 'xlsx'
import moment from 'moment'
import ModalXuatDuLieu from '@/modules/ECMS/TraCuuSplitterTheoPortOLT/ModalXuatDuLieu/index.vue'
import Vue from 'vue'
import { TreeViewPlugin } from '@syncfusion/ej2-vue-navigations'
Vue.use(TreeViewPlugin)
//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
//JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'
import { log } from 'pdfmake/build/pdfmake'

export default {
  components: { breadcrumb, treemenu, ModalXuatDuLieu },
  name: 'SearchConstructionNoConstructionAPI',
  mounted() {
    //load ds đơn vị
    var pdata = {}
    pdata.p_nguoidung_id = this.$auth.getNguoiDungID()
    SearchConstructionNoConstructionAPI.lay_don_vi(this.axios, pdata).then((response) => {
      if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
        response.data.data.ds_donvi = this.createTreeDonVi(response.data.data.ds_donvi)
        console.log(response.data.data.ds_donvi);
        this.listbox.fields = {
          dataSource: response.data.data.ds_donvi,
          id: 'donvi_id',
          text: 'ten_dv',
          parentID: 'donvi_cha_id',
          hasChildren: 'hasChild'
        }
      }
    })
    //load ds loại nhóm
    SearchConstructionNoConstructionAPI.lay_loai_nhom(this.axios, {}).then((response) => {
      if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
        this.listbox.loai_nhom.list = response.data.data
      }
    })
    //load ds hợp đồng
    SearchConstructionNoConstructionAPI.lay_hop_dong(this.axios, this.listbox.loai_nhom.value).then((response) => {
      if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
        this.listbox.hop_dong.list = response.data.data
      }
    })
    // load ds dịch vụ
    SearchConstructionNoConstructionAPI.lay_dich_vu(this.axios, {}).then((response) => {
      if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
        var dv = {}
        dv.dichvuvt_id = 0
        dv.ten_dvvt = 'Tất cả'
        response.data.data.push(dv)
        this.listbox.dich_vu.list = response.data.data
      }
    })
    // this.btnSearchClicked();
  },
  data() {
    return {
      header: {
        title: 'Tra cứu phiếu TC chưa thực hiện',
        list: [
          {
            name: 'Lắp đặt mới',
            link: { name: 'Ui.cards' }
          },
          {
            name: 'Lắp đặt mới',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      txtTraCuu: '',
      TxtSoMay: '',
      TxtMaGD: '',
      TxtDonVi: '',
      TxtKieuLD: '',
      TxtTenTB: '',
      TxtTenKH: '',
      TxtDiaChi: '',
      TxtGhiChu: '',
      TxtKHLon: '',
      TxtNhanVien: '',
      TxtNoiDungGiao: '',
      TxtSoQD: '',
      NgayGiao: moment().format('DD/MM/YYYY'),
      checkNgayGiao: false,
      tiepnhan: true,
      traphieu: true,
      ketquatimkiem: {
        list: [],
        header: [],
        value: {},
        columns: [
          { fieldName: 'ma_gd', headerText: 'Mã GD', allowFiltering: true, freeze: 'left' },
          { fieldName: 'ma_tb', headerText: 'Số máy/Acc', allowFiltering: true, freeze: 'left' },
          { fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: true, freeze: 'left' },
          { fieldName: 'diachi_ld', headerText: 'Địa chỉ lắp đặt', allowFiltering: true },
          { fieldName: 'nv_th', headerText: 'Người xử lý', allowFiltering: true },
          { fieldName: 'loaihinh_tb', headerText: 'Loại hình', allowFiltering: true },
          { fieldName: 'ten_dt', headerText: 'Gói KT', allowFiltering: true },
          { fieldName: 'ngay', headerText: 'Thời gian CK', allowFiltering: true },
          { fieldName: 'ngaygiao', headerText: 'Ngày giao', allowFiltering: true },
          { fieldName: 'donvi_giao', headerText: 'Đơn vị giao', allowFiltering: true },
          { fieldName: 'donvi_nhan', headerText: 'Đơn vị nhận', allowFiltering: true },
          { fieldName: 'diachi_ld', headerText: 'Đơn vị cha', allowFiltering: true },
          { fieldName: 'trangthai_hd', headerText: 'Trạng thái', allowFiltering: true },
          { fieldName: 'nguoi_cn', headerText: 'Người CN', allowFiltering: true },
          { fieldName: 'ngay_cn', headerText: 'Ngày CN', allowFiltering: true },
          { fieldName: 'ten_kieuld', headerText: 'Kiểu yêu cầu', allowFiltering: true },
          { fieldName: 'nv_giao', headerText: 'Người giao', allowFiltering: true }
        ]
      },
      ketquatimkiem1: {
        list: [],
        header: [],
        value: {},
        columns: [
          { fieldName: 'ma_tb', headerText: 'Số máy/Acc', allowFiltering: true, freeze: 'Left' },
          { fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: true, freeze: 'Left' },
          { fieldName: 'diachi_ld', headerText: 'Địa chỉ lắp đặt', allowFiltering: true },
          { fieldName: 'loaihinh_tb', headerText: 'Loại hình', allowFiltering: true },
          { fieldName: 'loaihinh_tb', headerText: 'Gói KT', allowFiltering: true },
          { fieldName: 'chitieu_tg', headerText: 'Chi tiêu TG	', allowFiltering: true },
          { fieldName: 'ngay_giao', headerText: 'Ngày giao', allowFiltering: true },
          { fieldName: 'donvi_giao', headerText: 'Đơn vị giao', allowFiltering: true },
          { fieldName: 'nv_giao', headerText: 'Người giao', allowFiltering: true },
          { fieldName: 'donvi_nhan', headerText: 'Đơn vị nhận', allowFiltering: true },
          { fieldName: 'donvi_cha', headerText: 'Đơn vị cha', allowFiltering: true },
          { fieldName: 'trangthai_hd', headerText: 'Trạng thái', allowFiltering: true },
          { fieldName: 'lydotra_id', headerText: 'Lý do tồn', allowFiltering: true },
          { fieldName: 'so_dt', headerText: 'Điện thoại KH', allowFiltering: true },
          { fieldName: 'sophieu', headerText: 'Số phiếu', allowFiltering: true },
          { fieldName: 'donvi_cha', headerText: 'Khu vực', allowFiltering: true },
          { fieldName: 'ngay_cn', headerText: 'Ngày CN', allowFiltering: true },
          { fieldName: 'ghichu_tb', headerText: 'Ghi chú tồn', allowFiltering: true },
          { fieldName: 'ten_kieuld', headerText: 'Kiểu yêu cầu', allowFiltering: true }
        ]
      },
      dsThuocTinh: [
        { name: 'stt', width: 100, textAlign: 'Right' },
        { name: 'CHON', width: 130, textAlign: 'Right' },
        { name: 'ma_tb', width: 100, textAlign: 'Right' },
        { name: 'ten_tb', width: 130, textAlign: 'Left' },
        { name: 'loaihinh_tb', width: 130, textAlign: 'Left' },
        { name: 'diachi_ld', width: 150, textAlign: 'Left' },
        { name: 'ten_loaihd', width: 130, textAlign: 'Left' },
        { name: 'ten_kieuld', width: 100, textAlign: 'Left' },
        { name: 'ghichu_tb', width: 100, textAlign: 'Left' },
        { name: 'ma_gd', width: 100, textAlign: 'Right' },
        { name: 'ten_dt', width: 150, textAlign: 'Left' },
        { name: 'so_dt', width: 100, textAlign: 'Right' },
        { name: 'tocdo', width: 150, textAlign: 'Left' },
        { name: 'ngay', width: 150, textAlign: 'Left' },
        { name: 'tinhtrang', width: 150, textAlign: 'Left' },
        { name: 'nd_giao', width: 150, textAlign: 'Left' },
        { name: 'nv_giao', width: 150, textAlign: 'Left' },
        { name: 'ngaygiao', width: 150, textAlign: 'Left' },
        { name: 'trangthai_hd', width: 150, textAlign: 'Left' },
        { name: 'donvi_nhan', width: 150, textAlign: 'Left' },
        { name: 'ghichu_hong', width: 150, textAlign: 'Left' },
        { name: 'sophieu', width: 150, textAlign: 'Left' },
        { name: 'khlon_id', width: 150, textAlign: 'Left' },
        { name: 'ngay_cn', width: 150, textAlign: 'Left' },
        { name: 'nguoi_cn', width: 150, textAlign: 'Left' }
      ],
      selectedNodes: [],
      dsThueBaoXuatExcel: [],
      dsSplitterTree: [],
      dsSplitterFlat: [],
      listbox: {
        loai_nhom: {
          list: [],
          value: 1,
          isEnabled: false
        },
        hop_dong: {
          list: [],
          value: 1,
          isEnabled: false
        },
        dich_vu: {
          list: [],
          value: 0,
          isEnabled: false
        },
        loai_giay_to: {
          list: [],
          value: 1,
          isEnabled: false
        },
        fields: {
          dataSource: [],
          id: 'donvi_id',
          text: 'ten_dv',
          parentID: 'donvi_cha_id',
          hasChildren: 'hasChild'
        },
        ketquatimkiem3: {
          list: [],
          value: '',
          isEnabled: false
        }
      },
      config: {
        dateConfig: {
          value: new Date(),
          format: 'dd/MM/yyyy',
          max: new Date()
        }
      },
      chkLoc: false,
      resultTab: 'dsPhieu',
      kieugoi_id: 0
    }
  },
  methods: {
    filterThueBao() {
      SearchConstructionNoConstructionAPI.lay_hop_dong(this.axios, this.listbox.loai_nhom.value).then((response) => {
        if (response && response.data) {
          this.listbox.hop_dong.list = response.data.data
          this.listbox.hop_dong.value = response.data.data[0].loaihd_id
        }
      })
    },
    btnSearchClicked() {
      this.Clear()
      if (this.selectedNodes == 0) {
        this.$toast.error('Chọn đơn vị muốn lấy báo cáo !')
        return
      }

      if (this.kieugoi_id == 0) {
        if (this.$route.query.tag) this.kieugoi_id = this.$route.query.tag
        else this.kieugoi_id = 1
      }

      if (this.resultTab == 'dsPhieu') {
        if (this.listbox.loai_nhom.value == 2) {
          this.loading(true)
          var data = {
            vdichvuvt_id: this.listbox.dich_vu.value,
            vdonvi_id: this.selectedNodes,
            vngay_giao: this.checkNgayGiao == false ? '0' : this.format_date(this.NgayGiao),
            vngaytra: '0',
            vkieu: this.kieugoi_id
          }
          SearchConstructionNoConstructionAPI.lay_ds_phieu_ht_bh(this.axios, data)
            .then((res) => {
              this.ketquatimkiem.list = res.data.data
              this.loading(false)
            })
            .catch((err) => {
              this.loading(false)
            })
        } else {
          this.loading(true)
          var pdata1 = {}
          pdata1.ma_gd = this.TxtSoMay
          pdata1.loaihd_id = this.listbox.hop_dong.value
          pdata1.dichvuvt_id = this.listbox.dich_vu.value
          pdata1.donvi_id = this.selectedNodes
          if (this.checkNgayGiao == false) {
            pdata1.ngay_giao = '0'
          } else pdata1.ngay_giao = this.format_date(this.NgayGiao)
          pdata1.ngaytra = '0'
          pdata1.kieu = this.kieugoi_id
          pdata1.nhanvien_id = this.chkLoc == true ? this.$auth.getNhanVienID() : null
          SearchConstructionNoConstructionAPI.ds_phieu(this.axios, pdata1).then((response) => {
            if (response.data.message == 'No Content') {
              this.$toast.error('Không tìm thấy danh sách phiếu yêu cầu')
            }
            if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
              this.ketquatimkiem.list = response.data.data
            }
            this.loading(false)
          })
        }
      } else {
        if (this.listbox.loai_nhom.value != 2) {
          this.loading(true)
          var pdata1 = {}
          pdata1.ma_gd = this.TxtSoMay
          pdata1.loaihd_id = this.listbox.hop_dong.value
          pdata1.dichvuvt_id = this.listbox.dich_vu.value
          pdata1.donvi_id = this.selectedNodes
          if (this.checkNgayGiao == false) {
            pdata1.ngay_giao = '0'
          } else pdata1.ngay_giao = this.format_date(this.NgayGiao)
          pdata1.ngaytra = '0'
          pdata1.kieu = this.kieugoi_id
          pdata1.nhanvien_id = this.chkLoc == true ? this.$auth.getNhanVienID() : null
          SearchConstructionNoConstructionAPI.lay_ds_phieu_chua_up(this.axios, pdata1).then((response) => {
            if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
              this.ketquatimkiem1.list = response.data.data
            }
            this.loading(false)
          })
        }
      }
    },
    XuatDL() {
      this.$bvModal.show('modal-xuat-dulieu')
    },
    Lay_TT_KHL(khlon_id) {
      // var pdata = {};
      // pdata.id_neo = "TEN_LKHL";
      // pdata.table = "css.loai_khl";
      // pdata.dk = "where KHLON_ID = " + khlon_id;
      // console.log(pdata);
      SearchConstructionNoConstructionAPI.fn_tt_loai_khl(this.axios, { param: khlon_id, type: 1 }).then((response) => {
        if (response && response.data && response.data.data) {
          if (response.data.data == 1) {
            this.TxtKHLon = ''
          } else this.TxtKHLon = response.data.data
        }
      })
    },
    selectedRowChanged(obj) {
      if (obj != null) {
        this.listbox.ketquatimkiem3.list = obj
        this.TxtSoMay = this.listbox.ketquatimkiem3.list.ma_tb
        this.TxtTenTB = this.listbox.ketquatimkiem3.list.ten_tb
        this.TxtDiaChi = this.listbox.ketquatimkiem3.list.diachi_ld
        this.TxtGhiChu = this.listbox.ketquatimkiem3.list.ghichu_tb
        this.TxtMaGD = this.listbox.ketquatimkiem3.list.ma_gd
        this.TxtKieuLD = this.listbox.ketquatimkiem3.list.ten_kieuld
        //this.TxtKHLon = this.listbox.ketquatimkiem3.list.khlon_id;
        this.TxtNhanVien = this.listbox.ketquatimkiem3.list.nv_giao
        this.TxtNoiDungGiao = this.listbox.ketquatimkiem3.list.nd_giao
        this.TxtDonVi = this.listbox.ketquatimkiem3.list.donvi_nhan
        //  this.TxtSoQD = this.listbox.ketquatimkiem3.list.sophieu;
        //  this.Lay_TT_KHL(this.listbox.ketquatimkiem3.list.khlon_id);
        this.Lay_TT_KHL(obj.khlon_id)
      }
    },
    selectedRowChanged1(obj) {
      this.listbox.ketquatimkiem3.list = obj
      // this.TxtSoMay = this.listbox.ketquatimkiem3.list.ma_tb;
      this.TxtTenTB = this.listbox.ketquatimkiem3.list.ten_tb
      this.TxtDiaChi = this.listbox.ketquatimkiem3.list.diachi_ld
      this.TxtGhiChu = this.listbox.ketquatimkiem3.list.ghichu_tb
      this.TxtMaGD = this.listbox.ketquatimkiem3.list.ma_gd
      this.TxtKieuLD = this.listbox.ketquatimkiem3.list.ten_kieuld
      this.TxtNhanVien = this.listbox.ketquatimkiem3.list.nv_giao
      this.TxtNoiDungGiao = this.listbox.ketquatimkiem3.list.nd_giao
      this.TxtDonVi = this.listbox.ketquatimkiem3.list.donvi_nhan
      //  this.TxtSoQD = this.listbox.ketquatimkiem3.list.sophieu;
    },
    XuatExcel() {
      //console.log("File add", this.listFileMau.length);
      if (this.ketquatimkiem.list == null || this.ketquatimkiem.list == [] || this.ketquatimkiem.list == '') {
        this.$toast.error('Chưa có danh sách để xuất Excel!')
        return
      }
      this.loading(true)
      try {
        var dsach = xlsx.utils.json_to_sheet(this.ketquatimkiem.list)
        var wb = xlsx.utils.book_new()
        xlsx.utils.book_append_sheet(wb, dsach, 'DS')
        xlsx.writeFile(wb, 'Danhsachphieuyeucau.xlsx')
      } catch (error) {
      } finally {
        this.loading(false)
      }
    },
    createTreeDonVi(dsDonVi) {
      let tree = []
      let donviChaArray = dsDonVi.map((item) => item['donvi_cha_id'])
      let donviArray = dsDonVi.map((item) => item['donvi_id'])
      dsDonVi.forEach((item) => {
        //check item la parent node
        if (donviArray.includes(item['donvi_id'])) {
          //check donvi_cha_id khong co trong ds donvi_id thi xoa donvi_cha_id
          if (!donviArray.includes(item['donvi_cha_id'])) {
            delete item.donvi_cha_id
          }
          tree.push({ ...item, hasChild: true })
        } else {
          tree.push(item)
        }
      })
      return tree
    },
    Clear() {
      this.ketquatimkiem.list = null
      this.ketquatimkiem1.list = null
      this.TxtSoMay = ''
      this.TxtTenTB = ''
      this.TxtDiaChi = ''
      this.TxtGhiChu = ''
      this.TxtMaGD = ''
      this.TxtKieuLD = ''
      this.TxtKHLon = ''
      this.TxtNhanVien = ''
      this.TxtNoiDungGiao = ''
      this.TxtDonVi = ''
    },
    format_date(value) {
      if (value) {
        return moment(String(value)).format('DD/MM/YYYY')
      }
    },
    tvDonVi_FocusedNodeChanged(node) {
      this.selectedNodes = node.nodeData.id
    },
    created: async function() {
      this.loading(true)
      this.loadData()
      this.loading(false)
    }
  }
}
</script>
