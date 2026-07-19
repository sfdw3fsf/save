<template src="./index.html"></template>
<script>
import Vue from 'vue'
import lodash from 'lodash'
import { DashboardLayoutPlugin } from '@syncfusion/ej2-vue-layouts'
import * as moment from 'moment'
import api from './api/new-api'
import ccbsApi from './api/ccbs-api'

Vue.use(DashboardLayoutPlugin)

export default {
  components: {},
  props: {
    type: {
      type: String,
      default: 'view'
    },
    vhuonggiao_id: {
      type: [String, Number],
      required: false,
      default: null
    }
  },
  async mounted() {
    await this.init()

  },
  data() {
    return {
      count: 10,
      spacing: [10, 10],
      resizableHandles: ['e-south-east', 'e-east', 'e-west', 'e-north', 'e-south'],
      header: {
        title: 'Nhân viên',
        list: [{ name: '<<<', link: { name: 'Ui.cards' } }]
      },
      menus: [
        { id: 'mnuLayDS', name: 'Lấy DS', icon: 'icon nc-icon-glyph location_crosshair', enabled: true },
        { id: 'mnuGan', name: 'Gán', icon: 'icon one-save', enabled: true },
        { id: 'mnuHuyGan', name: 'Hủy gán', icon: 'icon nc-icon-glyph ui-1_circle-remove', enabled: true },
      ],
      donviIDSelected: 0,
      txtTreeDonViSearch: { enabled: true, value: null },
      treeDonVi: { flatList: [], list: [], selected: [] },
      txtMaNV: { enabled: true, value: '' },
      txtViTri: { enabled: true, value: null },
      cboGioiTinh: { enabled: true, list: [], value: null },
      cboDichvuVT: { enabled: true, list: [], value: null },
      txtTenNV: { enabled: true, value: '' },
      acTxtTenNV: { enabled: true, value: '' },
      txtTenTN: { enabled: true, list: [], value: '' },
      dpNgaySinh: { enabled: true, value: null },
      txtSdt: { enabled: true, value: '' },
      txtDiaChi: { enabled: true, value: '' },
      txtTienKyQuy: { enabled: true, value: 0 },
      txtMailVNPT: { enabled: true, value: null },
      cboDonVi: { enabled: true, list: [], value: null },
      cboNhomChuDe: { enabled: true, list: [], value: null },
      cboLoaiNV: { enabled: true, list: [], value: null },
      cboDonViDL: { enabled: true, list: [], value: null },
      txtSoThe: { enabled: true, value: null },
      txtMaKenh: { enabled: true, value: null },
      cboHopDong: { enabled: true, list: [], value: null },
      txtUserVi: { enabled: true, value: null },
      txtGhiChu: { enabled: true, value: null },
      cboLoaiDV: { enabled: true, list: [], value: null },
      fpChuKy: { enabled: true, value: null },
      gridColumn: {
        header: [
        { fieldName: 'STT', headerText: 'STT', allowFiltering: true, width: 150 },
            { fieldName: 'MA_NV', headerText: 'Mã nhân viên', allowFiltering: true, width: 200 },
            { fieldName: 'TEN_NV', headerText: 'Tên nhân viên', allowFiltering: true, allowHtml: true, width: 200 },
            { fieldName: 'LOAI_NV', headerText: 'loại nhân viên', allowFiltering: true, allowHtml: true, width: 200 },
            { fieldName: 'NHIEM_VU', headerText: 'Loại nhiệm vụ', allowFiltering: true, allowHtml: true, width: 200 },
            { fieldName: 'MA_DV', headerText: 'Mã đơn vị', allowFiltering: true, allowHtml: true, width: 200 },
            { fieldName: 'TEN_DV', headerText: 'Tên đươn vị', allowFiltering: true, allowHtml: true, width: 200 },
            { fieldName: 'TEN_DVVT', headerText: 'Dịch vụ', allowFiltering: true, allowHtml: true, width: 200 },
            { fieldName: 'LOAIHINH_TB', headerText: 'Loại hình', allowFiltering: true, allowHtml: true, width: 200 },
            { fieldName: 'NHOM_CHUDE', headerText: 'Nhóm chủ đề', allowFiltering: true, allowHtml: true, width: 200 },
            { fieldName: 'CHUDE_CAP1', headerText: 'Chủ đè cấp 1', allowFiltering: true, allowHtml: true, width: 200 },
            { fieldName: 'CHUDE_CAP2', headerText: 'Chủ đè cấp 2', allowFiltering: true, allowHtml: true, width: 200 },            
   
        ],
        list: [],
        value: null
      },

      gridChuDeDaGan: {
        header: [
          { fieldName: 'NHOM_CHUDE', headerText: 'Nhóm chủ đề', allowFiltering: true, width: 150 },
          { fieldName: 'CHUDE_CAP1', headerText: 'Chủ đè cấp 1', allowFiltering: true, width: 200 },
          { fieldName: 'CHUDE_CAP2', headerText: 'Chủ đề cấp 2', allowFiltering: true, width: 200 }
        ],
        list: [],
        selected: [],
        value: null
      },

      gridChuDeChuaGan: {
        header: [
          { fieldName: 'NHOM_CHUDE', headerText: 'Nhóm chủ đề', allowFiltering: true, width: 150 },
          { fieldName: 'CHUDE_CAP1', headerText: 'Chủ đè cấp 1', allowFiltering: true, width: 200 },
          { fieldName: 'CHUDE_CAP2', headerText: 'Chủ đề cấp 2', allowFiltering: true, width: 200 }
        ],
        list: [],
        selected: [],
        value: null
      },

      gridLoaiHinhDaGan: {
        header: [
          { fieldName: 'LOAIHINH_TB', headerText: 'Loại hình', allowFiltering: true, width: 150 },
          { fieldName: 'MA_LHTB', headerText: 'mã loại hình', allowFiltering: true, width: 150 },
          { fieldName: 'TEN_DVVT', headerText: 'dịch vụ', allowFiltering: true, width: 150 },
        ],
        list: [],
        selected: [],
        value: null
      },
      gridLoaiHinhChuaGan: {
        header: [
          { fieldName: 'LOAIHINH_TB', headerText: 'Loại hình', allowFiltering: true, width: 150 },
          { fieldName: 'MA_LHTB', headerText: 'mã loại hình', allowFiltering: true, width: 150 }
        ],
        list: [],
        selected: [],
        value: null
      },

      gridNhanVienDaGan: {
        header: [
          { fieldName: 'MA_NV', headerText: 'Mã nhân viên', allowFiltering: true, width: 150 },
          { fieldName: 'TEN_NV', headerText: 'Tên nhân viên', allowFiltering: true, width: 150 },
          { fieldName: 'LOAI_NV', headerText: 'Loại nhân viên', allowFiltering: true, width: 150 },
          { fieldName: 'NHIEM_VU', headerText: 'Nhiệm vụ', allowFiltering: true, width: 150 },
          { fieldName: 'TEN_DV', headerText: 'Tên đơn vị', allowFiltering: true, width: 150 },
          { fieldName: 'SO_DT', headerText: 'Số điện thoại', allowFiltering: true, width: 150 },
          { fieldName: 'EMAIL', headerText: 'Email', allowFiltering: true, width: 150 }
        ],
        list: [],
        selected: [],
        value: null
      },
      gridNhanVienChuaGan: {
        header: [
          { fieldName: 'MA_NV', headerText: 'Mã nhân viên', allowFiltering: true, width: 150 },
          { fieldName: 'TEN_NV', headerText: 'Tên nhân viên', allowFiltering: true, width: 150 },
          { fieldName: 'LOAI_NV', headerText: 'Loại nhân viên', allowFiltering: true, width: 150 },
          { fieldName: 'NHIEM_VU', headerText: 'Nhiệm vụ', allowFiltering: true, width: 150 },
          { fieldName: 'TEN_DV', headerText: 'Tên đơn vị', allowFiltering: true, width: 150 },
          { fieldName: 'SO_DT', headerText: 'Số điện thoại', allowFiltering: true, width: 150 },
          { fieldName: 'EMAIL', headerText: 'Email', allowFiltering: true, width: 150 }
        ],
        list: [],
        selected: [],
        value: null
      },
      gridNhanVien: {
        header: [
          { fieldName: 'ma_nv', headerText: 'Mã nhân viên', allowFiltering: true, width: 150 },
          { fieldName: 'ma_the', headerText: 'Mã thẻ', allowFiltering: true, width: 150 },
          { fieldName: 'ma_dv', headerText: 'Mã đơn vị', allowFiltering: true, width: 150 },
          { fieldName: 'ten_nv', headerText: 'Tên nhân viên', allowFiltering: true, width: 150 },
          { fieldName: 'chucdanh', headerText: 'Chức danh', allowFiltering: true, width: 150 },
          { fieldName: 'vitri', headerText: 'Vị trí', allowFiltering: true, width: 150 },
          { fieldName: 'dien_thoai', headerText: 'Điện thoại', allowFiltering: true, width: 150 },
          { fieldName: 'diachi_nv', headerText: 'Địa chỉ', allowFiltering: true, width: 150 },
          { fieldName: 'hinhthuc_hd', headerText: 'Hình thức HĐ', allowFiltering: true, width: 150 },
          { fieldName: 'vitri_id', headerText: 'Mã vị trí', allowFiltering: true, width: 150 }
        ],
        list: [],
        selected: [],
        value: null
      },
      searchResult: {
        nhanvien_id: null
      },
      nhanVienViTriDialog: {
        isVisible: false
      },
      khuVucDialog: {
        isVisible: false
      }
    }
  },
  async created() {

  },
  watch: {
    async 'gridNhanVien.value'(val) {
      // if (val) {
      //   console.log(this.gridNhanVien.value)
      //   this.txtMaNV.value = this.gridNhanVien.value.ma_nv
      //   this.txtViTri.value = this.gridNhanVien.value.vitri
      //   this.cboGioiTinh.value = this.gridNhanVien.value.gioitinh
      //   this.txtTenNV.value = this.gridNhanVien.value.ten_nv
      //   this.txtTenTN.value = this.gridNhanVien.value.ten_tn
      //   this.dpNgaySinh.value = moment(this.gridNhanVien.value.ngay_sn).format('DD/MM/YYYY')
      //   this.txtSdt.value = this.gridNhanVien.value.so_dt
      //   this.txtDiaChi.value = this.gridNhanVien.value.diachi_nv
      //   this.txtTienKyQuy.value = this.gridNhanVien.value.tien_dc
      //   this.txtMailVNPT.value = this.gridNhanVien.value.email
      //   this.cboDonVi.value = this.gridNhanVien.value.donvi_id
      //   this.cboDonViDL.value = this.gridNhanVien.value.donvi_dl_id
      //   this.txtSoThe.value = this.gridNhanVien.value.ma_the
      //   this.txtMaKenh.value = this.gridNhanVien.value.makenh
      //   this.cboHopDong.value = this.gridNhanVien.value.hthd_id
      //   this.txtUserVi.value = this.gridNhanVien.value.user_vi
      //   this.txtGhiChu.value = this.gridNhanVien.value.ghichu

      //   if (this.gridNhanVien.value.nhanvien_id != -1) {
      //     this.loading(true)
      //     let dataLoaiNhanVienDaGan = await api.sp_ds_loainhanvien(this.axios, {
      //       p_kieu: 1,
      //       p_nhanvien_id: val.nhanvien_id
      //     })
      //     this.gridLoaiNhanVienDaGan.list = dataLoaiNhanVienDaGan.data.data
      //     let dataLoaiNhanVienChuaGan = await api.sp_ds_loainhanvien(this.axios, {
      //       p_kieu: 0,
      //       p_nhanvien_id: val.nhanvien_id
      //     })
      //     this.gridLoaiNhanVienChuaGan.list = dataLoaiNhanVienChuaGan.data.data
      //     this.loadDonVi(val, 32)           
      //     let dataNhanVienQuanLyDaGan = await api.sp_ds_nhanvien_quanly(this.axios, {
      //       p_nhanvien_id: val.nhanvien_id,
      //       p_donvi_id: val.donvi_id,
      //       p_kieu: 1
      //     })
      //     this.gridNhanVienQLDaGan.list = dataNhanVienQuanLyDaGan.data.data
      //     let dataNhanVienQuanLyChuaGan = await api.sp_ds_nhanvien_quanly(this.axios, {
      //       p_nhanvien_id: val.nhanvien_id,
      //       p_donvi_id: val.donvi_id,
      //       p_kieu: 0
      //     })
      //     this.gridNhanVienQLChuaGan.list = dataNhanVienQuanLyChuaGan.data.data
      //     this.loading(false)
      //   } else {
      //     this.gridLoaiNhanVienDaGan.list = []
      //     this.gridLoaiNhanVienChuaGan.list = []
      //     this.gridNhanVienQLChuaGan.list = []
      //     this.gridNhanVienQLChuaGan.list = []
      //     this.gridDonViChuaGan.list = []
      //     this.gridDonViDaGan.list = []
      //     this.gridNhanVien.list = []
      //   }
      // }
    },
    async 'cboLoaiDV.value'(val) {
      console.log('cboLoaiDV.value')
      console.log(val)
      if (this.gridNhanVien.value) {
        this.loadDonVi(this.gridNhanVien.value, this.cboLoaiDV.value)
      }
    }
  },
  methods: {
    async init() {
      await this.loadData()
      if (this.type == 'view') {
        this.initView()
      }
      if (this.type == 'modal') {
        this.initModal()
      }
    },
    async initView() { },
    async initModal() { },
    async loadData() {
      this.loading(true)
      //this.gridDonViChuaGan.list=await this.getDanhMuc('CAY_DONVI_2CAP',e.nodeData.id);

      let dataTreeDonVi = await api.sp_load_treeview(this.axios, {})
      await this.compileDanhSachDonVi(dataTreeDonVi)
      this.cboLoaiNV.list = await this.getDanhMuc('LOAINV_CSKH', 0, 0);
      if (this.cboLoaiNV.list.length > 0)
        this.cboLoaiNV.value = this.cboLoaiNV.list[0].LOAINV_ID

      this.cboDichvuVT.list = await this.getDanhMuc('DICHVU_VT', 0, 0);
      if (this.cboDichvuVT.list.length > 0)
        this.cboDichvuVT.value = this.cboDichvuVT.list[0].DICHVUVT_ID
      this.cboNhomChuDe.list = await this.getDanhMuc('NHOM_CHUDE', 0, 0);
      if (this.cboNhomChuDe.list.length > 0)
        this.cboNhomChuDe.value = this.cboNhomChuDe.list[1].NHOM_ID
      this.gridColumn.list = await this.danhSachCauHinh();
      this.loading(false)
      // this.$nextTick(() => {
      //   this.selectedDonViArray = [this.danhSachDonViTreeView[0].donvi_id]
      //   this.$refs.treeViewDonVi.expandAll([this.danhSachDonViTreeView[0].donvi_id])
      // })
    },
    async compileDanhSachDonVi(data) {
      let duLieu = data.data.data
      let listAllDonVi = duLieu.ls
      let donViCap1 = {
        ten_dv: duLieu.tendv_cha,
        unitlevel: 0,
        donvi_id: duLieu.donvi_id_cha,
        donvi_cha_id: duLieu.donvi_id_cha,
        children: []
      }
      let donViCap2 = duLieu.ls.filter((e) => e.donvi_cha_id == duLieu.donvi_id_cha)
      listAllDonVi = listAllDonVi.map((e) => {
        e.donvi_id = `${e.donvi_id}`
        return e
      })
      donViCap1.children = this.filterDonViCon(listAllDonVi, donViCap2)
      this.treeDonVi.flatList = duLieu.ls
      this.treeDonVi.list = [donViCap1]
    },
    filterDonViCon(listAllDonVi, donViCap2) {
      donViCap2 = donViCap2.map((dv) => {
        if (dv.unitlevel < 10) {
          let donViCon = this.filterDonViCon(
            listAllDonVi,
            listAllDonVi.filter((e) => e.donvi_cha_id == dv.donvi_id)
          )
          dv.children = donViCon
        }

        return dv
      })
      return donViCap2
    },
    async loadDonVi(nhanVienData, loaidv_id) {
      this.loading(true)
      let dataDonViDaGan = await api.sp_ds_donvi_theo_donvidl_loaidv(this.axios, {
        p_donvidl_id: 0,
        p_loaidv_id: loaidv_id,
        p_nhanvien_id: nhanVienData.nhanvien_id || 0,
        p_kieu: 1
      })
      this.gridNhanVienDaGan.list = dataDonViDaGan.data.data
      let dataDonViChuaGan = await api.sp_ds_donvi_theo_donvidl_loaidv(this.axios, {
        p_donvidl_id: 0,
        p_loaidv_id: loaidv_id,
        p_nhanvien_id: nhanVienData.nhanvien_id || 0,
        p_kieu: 0
      })
      this.gridNhanVienChuaGan.list = dataDonViChuaGan.data.data
      this.loading(false)
    },
    async get_app_config(key) {
      this.loading(true)
      let data = await api.get_app_config(this.axios, { keyname: key })
      this.loading(false)
      if (data.data) {
        if (data.data.keyname == key && data.data.dongbo == 1 && data.data.keyvalue == 'TRUE') {
          return true
        }
      }
      return false
    },
    onMenuClicked(action) {
      switch (action.id) {
        case 'mnuLayDS':
          this.onBtnLayDSClicked()
          break
        case 'mnuGan':
          this.onBtnGanClicked()
          break
        case 'mnuHuyGan':
          this.onBtnHuyGanClicked()
          break
        case 'xoa':
          this.onBtnXoaClicked()
          break
        case 'tim_kiem':
          this.timKiemNhanVienBy('ma_nv')
          break
        case 'gan_vi_tri':
          this.onBtnGanViTriClicked()
          break
        case 'khu_vuc':
          this.onBtnKhuVucOnClicked()
          break
      }
    },
    onBtnNhapMoiClicked() {
      this.resetForm()
    },
    resetForm() {
      this.gridNhanVien.value = {
        nhanvien_id: -1
        // txtMaNV: {value: ''},
        // txtTenNV: {value: ''},
        // txtTenTN: {value: ''},
        // dpNgaySinh: {value: null},
        // txtSdt: {value: ''},
        // txtDiaChi: {value: ''},
      }
    },
    async onBtnGhiLaiOnClicked() {
      if (this.gridNhanVien.value.nhanvien_id == -1) {
        this.actionNhanVien('them_moi')
      } else {
        this.actionNhanVien('cap_nhat')
      }
    },
    onBtnGhiLaiClicked() { },
    onBtnGanViTriClicked() {
      if (this.gridNhanVien.value && this.gridNhanVien.value.nhanvien_id != -1) {
        this.nhanVienViTriDialogOnClick()
      }
      // this.modalNhanVienVitriData = {
      //   nhanVienId: this.gridNhanVien.value.nhanvien_id,
      //   tenNhanVien: this.gridNhanVien.value.ten_nv
      // }
    },
    onBtnHuyClicked() {
      if (this.gridNhanVien.value == null || this.gridNhanVien.value.nhanvien_id == -1) {
        this.resetForm()
        return
      }
      this.timKiemNhanVienBy('nhanvien_id')
    },

    async onBtnLayDSClicked() {
      this.gridColumn.list = await this.danhSachCauHinh()
    },
    async danhSachCauHinh() {
      try {
        let rs = await this.$root.context.get('/web-gqkn/khieunai/lay-danhsach-nhanvien-theo-chude')
        return rs.data
      }
      catch (ex) {
        this.$toast.error('Có lối :' + ex.response.data.messages_detail)
      }
    },

    async capNhatCauHinh() {
      try {
        var ds_chude = []
        this.gridChuDeDaGan.list.forEach(item => {
          ds_chude.push(item.CHUDE_CAP2_ID)
        });
        var ds_loaihinh = []
        this.gridLoaiHinhDaGan.list.forEach(item => {
          ds_loaihinh.push(item.LOAITB_ID)
        });
        var ds_nhanvien = []
        this.gridNhanVienDaGan.list.forEach(item => {
          ds_nhanvien.push({DONVI_ID:item.DONVI_ID,NHANVIEN_ID:item.NHANVIEN_ID})
        });
        let param = {
          dsChuDe: ds_chude.join(","),
          dsLoaiTB: ds_loaihinh.join(","),
          dsDonVi: '',
          dsNhanVien: JSON.stringify(ds_nhanvien)
        }
        let rs = await this.$root.context.post('/web-gqkn/khieunai/capnhat-danhsach-cauhinh-chude-lhtb-donvi-nhanvien', param)
        return rs.data
      }
      catch (ex) {
        this.$toast.error('Có lối :' + ex.response.data.messages_detail)
      }
    },
    async onBtnGanClicked() {
      this.gridColumn.list = await this.capNhatCauHinh();
      this.gridColumn.list = await this.danhSachCauHinh();
    },
    async xoaCauHinhDonVi() {
      try {
        this.loading(true)
        let param = {
          pChuDeKNId: this.gridColumn.value.ID_CAP2,
          pLoaiTBId: this.gridColumn.value.LOAI_ID,
          pDonViId: this.gridColumn.value.DONVI_ID,
          pNhanVienId: this.gridColumn.value.NHANVIEN_ID
        }
        let rs = await this.$root.context.post('/web-gqkn/khieunai/xoa-nhanvien-donvi-theo-chude-loaitb', param)
        await this.danhSachCauHinh();
        this.loading(false)
        this.$toast.success('Xóa phiếu thành công')

      }
      catch (ex) {
        this.loading(false)
        this.$toast.error('Có lối :' + ex.response.data.message_detail)
      }
    },
    async onBtnHuyGanClicked() {
      if (!confirm(`Xác nhận xoá nhân viên: ${this.gridColumn.value.TEN_DV}`)) return
      await this.xoaCauHinhDonVi()
    },

    async onBtnXoaClicked() {
      if (!confirm(`Xác nhận xoá nhân viên: ${this.gridNhanVien.value.ten_nv}`)) return

      let deletedLoaiNhanVienDaGan = this.gridLoaiNhanVienDaGan.list
      this.loading(true)
      let deleteResult = await api.sp_delete_nhanvien(this.axios, {
        p_flag: 0,
        p_kyhoadon: moment(new Date()).format('YYYYMM01'),
        p_nhanvien_id: this.gridNhanVien.value.nhanvien_id,
        donvi_dl_id: this.gridNhanVien.value.donvi_dl_id ? this.gridNhanVien.value.donvi_dl_id : 0
      })
      this.loading(false)
      if (deleteResult.data.data == '1') {
        let OPEN_SYNC_VINA_TRASAU = await this.get_app_config('OPEN_SYNC_VINA_TRASAU')
        OPEN_SYNC_VINA_TRASAU = true
        if (OPEN_SYNC_VINA_TRASAU) {
          for (let i = 0; i < deletedLoaiNhanVienDaGan.length; i++) {
            if (deletedLoaiNhanVienDaGan[i].loainv_id == 25) {
              this.loading(true)
              let kq = await ccbsApi.ts_danhmuc_delete_nhanvien_tcs(this.axios, { manv: `${this.gridNhanVien.value.ma_nv}` })
              this.loading(false)
              if (kq.data.data.result == 'Xoa du lieu thanh cong') {
                this.$toast.success('Hủy loại nhân viên thu cước với nhân viên này trên vinaphone thành công!')
              } else {
                this.$toast.error(`Lỗi Web Service: \n${kq.data.data.result}`)
              }
            }
          }
        }
        this.$toast.success('Xoá nhân viên thành công')
        this.resetForm()
      } else {
        this.$toast.error(deleteResult.data.data)
      }
    },
    async onBtnKhuVucOnClicked() {
      this.khuVucDialogOnClick()
    },
    async onTreeDonviNodeSelected(e) {

      this.gridNhanVienChuaGan.list = await this.getDanhMuc('NHANVIEN_CSKH', e.nodeData.id, this.cboLoaiNV.value);

      this.donviIDSelected = e.nodeData.id
      //this.gridLoaihinhChuaGan.list=await this.getDanhMuc('LOAIHINH_TB',e.nodeData.id);
      // this.loading(true)
      // let data = await api.sp_ds_nhanvien_donvi(this.axios, {
      //   p_donvi_id: e.nodeData.id,
      //   p_kieu: 1
      // })
      // this.loading(false)
      // this.gridNhanVien.list = data.data.data

      // ///
      // if (this.searchResult.nhanvien_id) {
      //   try {
      //     const index = this.gridNhanVien.list.findIndex((item) => item.nhanvien_id == this.searchResult.nhanvien_id)
      //     if (index === -1) {
      //       this.$toast.warning('Không tìm thấy nhân viên')
      //     } else {
      //       const toPage = parseInt(index / this.$refs.gridNhanVien.pageSize)
      //       const toRow = index % this.$refs.gridNhanVien.pageSize
      //       setTimeout(() => {
      //         this.$refs.gridNhanVien.$refs.pagination.gotoPage(toPage)
      //         setTimeout(() => {
      //           this.$refs.gridNhanVien.selectRow(toRow, false)
      //         }, 200)
      //       }, 100)
      //       this.searchResult.nhanvien_id = null
      //     }
      //   } catch (e) {
      //     console.log(e)
      //   }
      // }
    },
    onTxtTreeDonViSearchEntered(e) { },
    btnUploadChuKyFileClicked() { },
    
    nhanVienMTL() {
      const data = this.$refs.gridNhanVienDaGan.getSelectedRecords()
      if (data.length > 0) {
        this.gridNhanVienDaGan.list = this.gridNhanVienDaGan.list.filter((e) => data.find((x) => x.NHANVIEN_ID == e.NHANVIEN_ID) == null)
        this.gridNhanVienChuaGan.list.unshift(...data)
      }
    },
    nhanVienMTR() {
      const data = this.$refs.gridNhanVienChuaGan.getSelectedRecords()
      if (data.length > 0) {
        this.gridNhanVienChuaGan.list = this.gridNhanVienChuaGan.list.filter((e) => data.find((x) => x.NHANVIEN_ID == e.NHANVIEN_ID) == null)
        this.gridNhanVienDaGan.list.unshift(...data)
      }
    },

    chuDeMTL() {
      const data = this.$refs.gridChuDeDaGan.getSelectedRecords()
      if (data.length > 0) {
        this.gridChuDeDaGan.list = this.gridChuDeDaGan.list.filter((e) => data.find((x) => x.CHUDE_CAP2_ID == e.CHUDE_CAP2_ID) == null)
        this.gridChuDeChuaGan.list.unshift(...data)
      }
    },
    chuDeMTR() {
      const data = this.$refs.gridChuDeChuaGan.getSelectedRecords()
      if (data.length > 0) {
        this.gridChuDeChuaGan.list = this.gridChuDeChuaGan.list.filter((e) => data.find((x) => x.CHUDE_CAP2_ID == e.CHUDE_CAP2_ID) == null)
        this.gridChuDeDaGan.list.unshift(...data)
      }
    },

    loaiTBMTL() {
      const data = this.$refs.gridLoaiHinhDaGan.getSelectedRecords()
      console.log('data1', data)
      if (data.length > 0) {
        this.gridLoaiHinhDaGan.list = this.gridLoaiHinhDaGan.list.filter((e) => data.find((x) => x.LOAITB_ID == e.LOAITB_ID) == null)
        this.gridLoaiHinhChuaGan.list.unshift(...data)
      }
    },
    loaiTBMTR() {
      const data = this.$refs.gridLoaiHinhChuaGan.getSelectedRecords()
      console.log('data2', data)
      if (data.length > 0) {
        this.gridLoaiHinhChuaGan.list = this.gridLoaiHinhChuaGan.list.filter((e) => data.find((x) => x.LOAITB_ID == e.LOAITB_ID) == null)
        this.gridLoaiHinhDaGan.list.unshift(...data)
      }
    },

    //   onDataBound() {
    //   let tree = this.$refs.treeDonVi.ej2Instances;
    //   let parents = this.findParentNodes(tree, "3");
    //   if (parents.length) {
    //     tree.expandNodes(parents);
    //   }
    // },
    async gridColumnOnSelectedRowChanged(e) {
      //this.gridNhanVien.value = e
      //console.log(e)
      //this.cboNhomChuDe.list=await this.getDanhMuc('NHOM_CHUDE',0);
      //this.cboDichvuVT.list=await this.getDanhMuc('DICHVU_VT',0);  
      if (e)
    {
      this.gridColumn.value = e
      this.cboDichvuVT.value = e?.DICHVUVT_ID;
      this.cboNhomChuDe.value = e.ID_NHOM;
      this.gridLoaiHinhChuaGan.list = await this.getDanhMuc('LOAIHINH_TB', e.DICHVUVT_ID, 0);
      this.gridNhanVienChuaGan.list = await this.getDanhMuc('NHANVIEN_KN', e.DONVI_ID, 0);
      this.gridChuDeChuaGan.list = await this.getDanhMuc('CAY_CHUDE_NGANG', e.ID_NHOM, 0);

      // let tree = this.$refs.treeDonVi.ej2Instances;
      // let parents = this.getParentNodes(tree, e.DONVI_ID);
      //   tree.expandNodes(parents)
      // xu ly loai hinh
      this.gridNhanVienDaGan.list = this.gridNhanVienChuaGan.list.filter(item => item.NHANVIEN_ID == e.NHANVIEN_ID)
      let loaitb_temp = this.gridNhanVienChuaGan.list.filter(item => item.NHANVIEN_ID != e.NHANVIEN_ID)
      this.gridNhanVienChuaGan.list = loaitb_temp

      // xu ly loai hinh
      this.gridLoaiHinhDaGan.list = this.gridLoaiHinhChuaGan.list.filter(item => item.LOAITB_ID == e.LOAITB_ID)
      loaitb_temp = this.gridLoaiHinhChuaGan.list.filter(item => item.LOAITB_ID != e.LOAITB_ID)
      this.gridLoaiHinhChuaGan.list = loaitb_temp
      // xu ly nhom chu de        
      this.gridChuDeDaGan.list = this.gridChuDeChuaGan.list.filter(item => item.CHUDE_CAP2_ID == e.ID_CAP2)
      loaitb_temp = this.gridChuDeChuaGan.list.filter(item => item.CHUDE_CAP2_ID != e.ID_CAP2)
      this.gridChuDeChuaGan.list = loaitb_temp
      //this.cboDichvuVT.list=await this.getDanhMuc('DICHVU_VT',0);
    }
    },
    async timKiemNhanVienBy(method) {
      let nhanvien_id = '-1'
      switch (method) {
        case 'ma_nv':
          if (this.txtMaNV.value.length == 0) {
            this.$toast.warning('Vui lòng nhập mã nhân viên cần tìm!')
            return
          }
          this.loading(true)
          nhanvien_id = (
            await api.fn_tt_nhanvien(this.axios, {
              param: this.txtMaNV.value,
              type: 1
            })
          ).data.data
          this.loading(false)
          if (nhanvien_id == '-1') {
            this.loading(true)
            nhanvien_id = (
              await api.fn_tt_nhanvien(this.axios, {
                param: this.txtMaNV.value,
                type: 7
              })
            ).data.data
            this.loading(false)
          }
          break
        case 'ten_nv':
          if (this.txtTenNV.value.length == 0) {
            this.$toast.warning('Vui lòng nhập tên nhân viên cần tìm!')
            return
          }
          this.loading(true)
          let data = await api.sp_get_nhanvien_manv_tennv(this.axios, {
            dk_tim: this.txtTenNV.value,
            kieu: 2
          })
          this.loading(false)
          if (data.data.data && data.data.data.length > 0) {
            nhanvien_id = data.data.data[0].nhanvien_id
          }
          break
        case 'nhanvien_id':
          nhanvien_id = this.gridNhanVien.value.nhanvien_id
          break
      }
      if (nhanvien_id == '-1') {
        this.$toast.warning('Không tìm thấy nhân viên bằng mã nhân viên hoặc user đăng nhập!')
        this.loading(false)
        return
      }
      this.searchResult.nhanvien_id = nhanvien_id
      this.loading(true)
      let donvi_id = (
        await api.thanh_ly_map_id(this.axios, {
          id_neo: 'donvi_id',
          in_table: 'admin.nhanvien',
          in_dk: `where nhanvien_id = ${nhanvien_id}`
        })
      ).data.data
      this.loading(false)
      if (donvi_id == '-1') {
      } else {
        let donViNode = this.treeDonViFindNode(this.treeDonVi.list, donvi_id)
        this.$refs.treeDonVi.expandAll(donViNode)
        this.treeDonVi.selected = [donvi_id]
      }
    },
    async timKiemNhanVienByTenNV(input) {
      this.loading(true)
      let data = await api.sp_get_nhanvien_manv_tennv(this.axios, {
        // dk_tim: this.txtTenNV.value,
        dk_tim: input,
        kieu: 2
      })
      this.loading(false)
      if (data.data.data && data.data.data.length > 0) {
        this.acTxtTenNV.list = data.data.data
      }
    },
    treeDonViFindNode(lstDonVi, id) {
      const a = lstDonVi.find((dv) => dv.donvi_id == id)
      if (a) {
        return [a.donvi_id.toString()]
      }

      for (let ii = 0; ii < lstDonVi.length; ii++) {
        const b = this.treeDonViFindNode(lstDonVi[ii].children, id)
        if (b) {
          b.unshift(lstDonVi[ii].donvi_id.toString())
          return b
        }
      }
    },


    onModalNhanVienViTriSaveSuccess(e) {
      console.log('onModalNhanVienViTriSaveSuccess')
      console.log(e)
      this.timKiemNhanVienBy('nhanvien_id')
    },
    onModalChonDiaChiSuccess(e) {
      this.txtDiaChi.value = e.diachimoi
    },
    async nhanVienViTriDialogOnClick() {
      this.nhanVienViTriDialog.isVisible = true
      console.log('dieu kien nay')
      console.log(this.nhanVienViTriDialog.isVisible && this.gridNhanVien.value && this.gridNhanVien.value.nhanvien_id != -1)
      this.$refs.nhanVienViTriDialog.show()
    },
    async nhanVienViTriDialogOnClose() {
      this.nhanVienViTriDialog.isVisible = false
    },
    async khuVucDialogOnClick() {
      this.khuVucDialog.isVisible = true
      this.$refs.khuVucDialog.show()
    },
    async khuVucDialogOnClose() {
      this.khuVucDialog.isVisible = false
    },
    getDanhMuc: async function (ten, id, id2) {
      try {
        let param = {
          tenDanhMuc: ten,
          id: Number(id),
          id2: id2
        }
        const rs = await this.$root.context.post('/web-gqkn//khieunai/danhmuc_khieunai', param);
        return rs.data;
      } catch (error) { return null }
    },
  },
  watch: {

    async 'cboLoaiNV.value'(val) {
      this.gridNhanVienChuaGan.list = await this.getDanhMuc('NHANVIEN_CSKH', this.donviIDSelected, val);
    },

    async 'cboDichvuVT.value'(val) {
      this.gridLoaiHinhChuaGan.list = await this.getDanhMuc('LOAIHINH_TB', val, 0);
    },
    async 'cboNhomChuDe.value'(val) {
      this.gridChuDeChuaGan.list = await this.getDanhMuc('CAY_CHUDE_NGANG', val, 0);
    }
  }
}
</script>
<style scoped></style>