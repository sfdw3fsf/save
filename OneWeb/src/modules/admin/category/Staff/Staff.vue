<template>
  <div>
    <breadcrumb />
    <action-button
      :isEnableSearch="selectedDonVi != null"
      :canOpenModal="modeSave === 0"
      @search-action="searchByMaNhanVien"
      @save-action="saveAction"
      @create-action="changeSaveModeToCreated"
      @cancel-action="cancelAction"
      @delete-action="deleteAction"
      @gan-vi-tri-action="openModalNhanVienViTri"
      @gan-khu-vuc-action="openModalKhuVuc"
      :modeSave="modeSave"
    />
    <div class="page-content">
      <div class="row">
        <Split>
          <SplitArea :size="30">
        <div class="col-sm-12 col-12">
          <div class="box-form tree-unit">
            <div class="legend-title">Danh sách đơn vị</div>
            <div class="tree-plus">
              <span class="search-input e-input-group e-control-wrapper">
                <input class="e-filtertext e-input highlight" placeholder="Nhập tên đơn vị cần tìm kiếm" v-model="keywordSearchUnit" @keypress.enter="searchUnitTreeView" />
                <span class="e-clear-icon e-clear-icon-hide" aria-label="close" role="button"></span>
              </span>
              <ejs-treeview ref="treeViewDonVi" @nodeSelected="changeDonViTreeView" :selectedNodes="selectedDonViArray" :fields="{ dataSource: danhSachDonViTreeView, id: 'donvi_id', text: 'ten_dv', child: 'children' }"></ejs-treeview>
            </div>
          </div>
        </div>
      </SplitArea>
      <SplitArea :size="70">
        <div class="col-sm-12 col-12">
          <div class="row">
            <div class="col-sm-6 col-12">
              <form-thong-tin-nhan-vien
                ref="formThongTinNhanVien"
                :danhSachDonVi="danhSachDonViComboBox"
                :danhSachDonViDL="danhSachDonViDLComboBox"
                :danhSachLoaiDonVi="danhSachLoaiDonViComboBox"
                :danhSachHopDong="danhSachHopDongCombobox"
                :danhSachViTri="danhSachViTriComboBox"
                :formData="formThongTinNhanVienData"
                @onChangeLoaiDonVi="onChangeLoaiDonVi"
                @searchStaffByCode="searchStaffByCode"
                @searchStaffByName="searchStaffByName"
              />
              <table-gan-don-vi ref="tableGanDonVi"></table-gan-don-vi>
            </div>
            <div class="col-sm-6 col-12">
              <table-gan-loai-nhan-vien ref="tableGanLoaiNhanVien" />
              <table-gan-nhan-vien-quan-ly ref="tableGanNhanVienQuanLy" />
            </div>
          </div>
          <table-danh-sach-nhan-vien ref="tableDanhSachNhanVien" :danhSachNhanVien="danhSachNhanVien" @selected-change="selectedNhanVienChange" @tableReady="tableNhanVienReady"></table-danh-sach-nhan-vien>

          <!-- <DataGrid
            ref="gridNhanVien"
            :selectionSettings="{ checkboxMode: 'ResetOnRowClick' }"
            v-bind:columns="gridNhanVien.cols"
            v-bind:dataSource="gridNhanVien.list"
            :showFilter="true"
            @rowSelected="onGridNhanVienRowSelected"
            :allowPaging="true"
            :enablePagingServer="false"
            :totalRecords="gridNhanVien.list.length"
          /> -->
        </div>
      </SplitArea>
      </Split>
      </div>
    </div>
    <modal-nhan-vien-vi-tri ref="modalNhanVienViTri" :nhanVienId="modalNhanVienVitriData.nhanVienId" :tenNhanVien="modalNhanVienVitriData.tenNhanVien" @save-success="ganNhanVienViTriSuccess" />

    <modal-tim-kiem-nhan-vien ref="modalTimKiemNhanVien" @searchStaffByCode="searchStaffByCode" :danh-sach-nhan-vien="danhSachNhanVienTimKiem" />
    <modal-khu-vuc ref="modalKhuVuc" />
  </div>
</template>

<script>
import * as moment from 'moment'
import ActionButton from './part/ActionButton.vue'
import FormThongTinNhanVien from './part/FormThongTinNhanVien.vue'
import TableGanLoaiNhanVien from './part/TableGanLoaiNhanVien.vue'
import TableGanNhanVienQuanLy from './part/TableGanNhanVienQuanLy.vue'
import TableGanDonVi from './part/TableGanDonVi.vue'
import TableDanhSachNhanVien from './part/TableDanhSachNhanVien.vue'
import ModalNhanVienViTri from './modals/ModalNhanVienViTri'
import ModalKhuVuc from './modals/ModalKhuVuc'
import ModalTimKiemNhanVien from './modals/ModalTimKiemNhanVien'
import breadcrumb from '../../../../components/breadcrumb'
import { customTreeItem } from './plugins/customTreeItem'

import {
  getDanhSachDonViComboBox,
  getDanhSachDonViTreeView,
  getDanhSachHopDongComboBox,
  getDanhSachNhanVienTheoDonVi,
  getDanhSachLoaiDonViComboBox,
  getDanhSachViTriTheoNhanVien,
  getDanhSachDonViDLComboBox,
  updateNhanVien,
  deleteNhanVien,
  searchStaffByCondition,
  searchStaffInStaffTable
} from './api'
import api from './api/new-api'
import ccbsApi from './api/ccbs-api'
import { queryData } from '../../../contract/setup/DeclareIMS/api'
import { TRANGTHAI_DONGBO } from './const'

const THEM_MOI = 1
const CAP_NHAT = 0

const SEARCH_BY_STAFF_NAME = 2
const SEARCH_BY_STAFF_CODE = 1

export default {
  name: 'Staff',
  components: {
    ActionButton,
    FormThongTinNhanVien,
    TableGanLoaiNhanVien,
    TableGanNhanVienQuanLy,
    TableGanDonVi,
    TableDanhSachNhanVien,
    ModalNhanVienViTri,
    ModalKhuVuc,
    ModalTimKiemNhanVien,
    breadcrumb
  },
  data: () => ({
    keywordSearchUnit: '',
    treeItemTemplate: customTreeItem,
    // mode save: 1 = Thêm mới   0 = cập nhật thông tin
    modeSave: THEM_MOI,
    // selectedNhanVien trong table danh sách nhân viên
    selectedNhanVien: null,
    // danh sách đơn vị cho tree view
    danhSachDonViTreeView: [],
    originalListUnit: [],
    selectedDonViArray: [],
    selectedDonVi: null,

    // table danh sach nhan vien
    danhSachNhanVien: [],

    // load combobox form thông tin nhân viên
    danhSachDonViComboBox: [],
    danhSachDonViDLComboBox: [],
    danhSachLoaiDonViComboBox: [],
    danhSachViTriComboBox: [],
    danhSachHopDongCombobox: [],

    // modal
    danhSachKhuVucTheoMaNhanVien: [],

    // form thông tin nhân viên
    formThongTinNhanVienData: {
      maNhanVien: '', // require
      tenNhanVien: '', // require
      selectedViTri: null,
      gioiTinh: 0,
      tenThuNgan: '', // require, Tên TN
      ngaySinh: null, // require
      dienThoai: '', // require
      diaChi: '', // require
      tienKyQuy: 0,
      mailVNPT: '',
      checkDT: false,
      selectedDonVi: null,
      selectedDonViDL: null,
      soThe: '',
      maKenh: '',
      selectedHopDong: null,
      userVi: '', // User Ví
      ghiChu: '',
      chuKy: null,
      selectedLoaiDonVi: null, // require
      showError: false,
      showModalMap: false
    },
    staffCodeToSearch: '',
    staffId: '',
    modalNhanVienVitriData: {
      nhanVienId: 0,
      tenNhanVien: ''
    },
    danhSachNhanVienTimKiem: [],
    preventLoadTableStaff: false,
    gridNhanVien: {
      cols: [
        { fieldName: 'ma_nv', headerText: 'Mã nhân viên', allowFiltering: true },
        { fieldName: 'ma_the', headerText: 'Mã thẻ', allowFiltering: true },
        { fieldName: 'ma_dv', headerText: 'Mã đơn vị', allowFiltering: true },
        { fieldName: 'ten_nv', headerText: 'Tên nhân viên', allowFiltering: true },
        { fieldName: 'chucdanh', headerText: 'Chức danh', allowFiltering: true },
        { fieldName: 'vitri', headerText: 'Vị trí', allowFiltering: true },
        { fieldName: 'dien_thoai', headerText: 'Điện thoại', allowFiltering: true },
        { fieldName: 'diachi_nv', headerText: 'Địa chỉ', allowFiltering: true },
        { fieldName: 'hinhthuc_hd', headerText: 'Hình thức HĐ', allowFiltering: true },
        { fieldName: 'vitri_id', headerText: 'Mã vị trí', allowFiltering: true }
      ],
      list: [],
      checked: [],
      value: null
    }
  }),
  mounted() {
    //this.loading(true)
    this.loadDataInitializePage()
  },
  methods: {
    changeDonViTreeView($event) {
      this.selectedDonVi = $event.nodeData.id
      this.loadTableStaff()
    },

    changeSaveModeToCreated() {
      console.log('changeSaveModeToCreated')
      console.log(this.modeSave)
      if (this.modeSave === THEM_MOI) {
        this.modeSave = 0
        return
      }
      this.modeSave = THEM_MOI
      this.resetFormData()
    },
    resetFormData() {
      this.formThongTinNhanVienData.tenNhanVien = ''
      this.formThongTinNhanVienData.tenThuNgan = ''
      this.formThongTinNhanVienData.maNhanVien = ''
      this.formThongTinNhanVienData.selectedViTri = null
      this.formThongTinNhanVienData.gioiTinh = 0
      this.formThongTinNhanVienData.ngaySinh = null
      this.formThongTinNhanVienData.dienThoai = ''
      this.formThongTinNhanVienData.mailVNPT = ''
      this.formThongTinNhanVienData.diaChi = ''
      this.formThongTinNhanVienData.tienKyQuy = 0
      // this.formThongTinNhanVienData.selectedDonVi = null

      if (this.danhSachDonViDLComboBox.length > 0) {
        this.formThongTinNhanVienData.selectedDonViDL = this.danhSachDonViDLComboBox[0].donvi_id
      } else {
        this.formThongTinNhanVienData.selectedDonViDL = null
      }

      this.formThongTinNhanVienData.soThe = ''
      this.formThongTinNhanVienData.maKenh = ''
      this.formThongTinNhanVienData.selectedHopDong = null
      this.formThongTinNhanVienData.chuKy = null
      this.formThongTinNhanVienData.userVi = ''
      this.formThongTinNhanVienData.ghiChu = ''
      this.formThongTinNhanVienData.showError = false
    },
    cancelAction() {
      if (this.selectedNhanVien === null) {
        this.resetFormData()
        this.loadDataInitializePage()
      }
      this.loadDataWhenChangeSelectedNhanVien(this.selectedNhanVien)
      this.$refs.gridNhanVien.selectRecord(this.selectedNhanVien)
    },
    async deleteAction() {
      if (!confirm(`Xác nhận xoá nhân viên: ${this.selectedNhanVien.ten_nv}`)) return

      let deletedLoaiNhanVienDaGan = this.$refs.tableGanLoaiNhanVien.getDanhSachLoaiNhanVienDaGan()
      this.loading(true)
      let deleteResult = await api.sp_delete_nhanvien(this.axios, {
        p_flag: 0,
        p_kyhoadon: moment(new Date()).format('YYYYMM01'),
        p_nhanvien_id: this.selectedNhanVien.nhanvien_id,
        donvi_dl_id: this.selectedNhanVien.donvi_dl_id
      })
      this.loading(false)
      if (deleteResult.data.data == '1') {
        let OPEN_SYNC_VINA_TRASAU = await this.get_app_config('OPEN_SYNC_VINA_TRASAU')
        if (OPEN_SYNC_VINA_TRASAU) {
          for (let i = 0; i < deletedLoaiNhanVienDaGan.length; i++) {
            if (deletedLoaiNhanVienDaGan[i].loainv_id == 25) {
              this.loading(true)
              let kq = await ccbsApi.ts_danhmuc_delete_nhanvien_tcs(this.axios, { manv: `${this.selectedNhanVien.ma_nv}` })
              this.loading(false)
              if (kq.data.data.result == 'Xoa du lieu thanh cong') {
                this.$toast.success('Hủy loại nhân viên thu cước với nhân viên này trên vinaphone thành công!')
              } else {
                this.$toast.error(`Lỗi Web Service: \n${kq.data.data.result}`)
              }
            }
          }
        }
        this.loadTableStaff()
        this.staffId = null
        this.$toast.success('Xoá nhân viên thành công')
      } else {
        this.$toast.error(deleteResult.data.data)
      }
    },
    async loadDataInitializePage() {
      //this.loading(true)
      await Promise.all([getDanhSachDonViComboBox(this.axios), getDanhSachHopDongComboBox(this.axios), getDanhSachLoaiDonViComboBox(this.axios), getDanhSachDonViDLComboBox(this.axios)])
        .then((response) => {
          this.danhSachDonViComboBox = response[0]
          this.danhSachHopDongCombobox = response[1]
          this.danhSachLoaiDonViComboBox = response[2]
          if (response[3]) {
            this.danhSachDonViDLComboBox = response[3].map((donvi) => ({
              donvi_id: donvi.donvi_id,
              ten_dv: donvi.ten_dv || ' '
            }))
          }
        })
        .catch(() => {
          this.$toast.error('Lỗi tải dữ liệu')
          this.loading(false)
        })

      // this.danhSachDonViTreeView = await getDanhSachDonViTreeView(this.axios).catch((err) => {
      //   this.$toast.error(err.data.message)
      //   this.loading(false)
      // })

      var data = await api.sp_load_treeview(this.axios, {})

      this.originalListUnit = JSON.parse(JSON.stringify(this.danhSachDonViTreeView))
      await this.compileDanhSachDonVi(data)

      if (!this.danhSachDonViTreeView || this.danhSachDonViTreeView.length === 0) {
        this.loading(false)
        return
      }

      console.log('this.danhSachDonViTreeView - 1')
      console.log(this.danhSachDonViTreeView)

      // this.$nextTick(() => {
      //   this.selectedDonViArray = [this.danhSachDonViTreeView[0].donvi_id]
      //   this.$refs.treeViewDonVi.expandAll([this.danhSachDonViTreeView[0].donvi_id])
      // })
    },
    async compileDanhSachDonVi(data) {
      console.log('data')
      console.log(data)
      var duLieu = data.data.data
      var listAllDonVi = duLieu.ls
      var donViCap1 = {
        ten_dv: duLieu.tendv_cha,
        unitlevel: 0,
        donvi_id: duLieu.donvi_id_cha,
        donvi_cha_id: duLieu.donvi_id_cha,
        children: []
      }
      var donViCap2 = duLieu.ls.filter((e) => e.donvi_cha_id == duLieu.donvi_id_cha)
      listAllDonVi = listAllDonVi.map(e => {
        e.donvi_id = `${e.donvi_id}`
        // if(e.donvi_id == 0) e.donvi_id = 123321
        // if(e.donvi_cha_id == 0) e.donvi_cha_id = 123321
        return e
      })
      donViCap1.children = this.filterDonViCon(listAllDonVi, donViCap2)
      // donViCap2.map((e) => {
      //   return e
      // })
       this.danhSachDonViTreeView = [donViCap1]

      // setTimeout(() => {
       
      //   console.log('output')
      //   console.log(this.danhSachDonViTreeView)
      // }, 5000)
    },
    filterDonViCon(listAllDonVi, donViCap2) {
      donViCap2 = donViCap2.map((dv) => {
        
        if (dv.unitlevel < 10) {
          var donViCon = this.filterDonViCon(
            listAllDonVi,
            listAllDonVi.filter((e) => e.donvi_cha_id == dv.donvi_id)
          )
          // if (donViCon.length < 2) 
          dv.children = donViCon
          // else dv.children = donViCon.slice(0, 4)
        }

        return dv
      })
      return donViCap2
    },
    async selectedNhanVienChange(data) {
      if (this.selectedNhanVien !== null && this.selectedNhanVien.nhanvien_id === data.nhanvien_id) {
        this.loading(false)
        return
      }
      await this.loadDataWhenChangeSelectedNhanVien(data)
      this.loading(false)
    },

    async loadDataWhenChangeSelectedNhanVien(data) {
      this.selectedNhanVien = JSON.parse(JSON.stringify(data))
      this.modeSave = CAP_NHAT
      // gán thông tin cho form
      this.formThongTinNhanVienData.tenNhanVien = data.ten_nv || ''
      this.formThongTinNhanVienData.maNhanVien = data.ma_nv || ''
      this.formThongTinNhanVienData.selectedViTri = data.vitri_id || null
      this.formThongTinNhanVienData.tenThuNgan = data.ten_tn || ''
      this.formThongTinNhanVienData.gioiTinh = data.gioitinh || 0

      if (data.ngay_sn) {
        this.formThongTinNhanVienData.ngaySinh = moment(data.ngay_sn, 'DD/MM/YYYY HH:mm:ss').toDate()
      } else {
        this.formThongTinNhanVienData.ngaySinh = null
      }

      this.formThongTinNhanVienData.dienThoai = data.so_dt || ''
      this.formThongTinNhanVienData.mailVNPT = data.email || ''
      this.formThongTinNhanVienData.diaChi = data.diachi_nv || ''
      this.formThongTinNhanVienData.tienKyQuy = data.tien_dc || 0
      this.formThongTinNhanVienData.selectedDonVi = data.donvi_id || null
      let tempDonViDl = 0
      if (this.danhSachDonViDLComboBox.length > 0) {
        tempDonViDl = this.danhSachDonViDLComboBox[0].donvi_id
      }

      this.formThongTinNhanVienData.selectedDonViDL = data.donvi_dl_id || tempDonViDl
      this.formThongTinNhanVienData.soThe = data.ma_the || ''
      this.formThongTinNhanVienData.maKenh = data.makenh || ''
      this.formThongTinNhanVienData.selectedHopDong = data.hthd_id || null
      this.formThongTinNhanVienData.userVi = data.user_vi || ''
      this.formThongTinNhanVienData.ghiChu = data.ghichu || ''
      this.formThongTinNhanVienData.chuKy = data.chuky || null
      this.formThongTinNhanVienData.selectedLoaiDonVi = data.loai_dv || null // chưa có loại đơn vị
      this.formThongTinNhanVienData.showError = false
      //this.loading(true)
      await Promise.all([
        getDanhSachViTriTheoNhanVien(this.axios, {
          p_nhanvien_id: data.nhanvien_id
        }),
        this.$refs.tableGanNhanVienQuanLy.loadNhanVienQuanLyDaGan(data.nhanvien_id, data.donvi_id),
        this.$refs.tableGanNhanVienQuanLy.loadNhanVienQuanLyChuaGan(data.nhanvien_id, data.donvi_id),
        this.$refs.tableGanLoaiNhanVien.loadLoaiNhanVienDaGanTheoNhanVienId(data.nhanvien_id),
        this.$refs.tableGanLoaiNhanVien.loadLoaiNhanVienChuaGanTheoNhanVienId(data.nhanvien_id),
        this.$refs.tableGanDonVi.loadDanhSachDonViDaGan(32, data.donvi_id, data.nhanvien_id),
        this.$refs.tableGanDonVi.loadDanhSachDonViChuaGan(32, data.donvi_id, data.nhanvien_id)
      ])
        .finally(() => {
          this.loading(false)
        })
        .then((response) => {
          this.danhSachViTriComboBox = response[0]
        })
        .catch(() => {
          this.$toast.error('Lỗi tải dữ liệu')
        })
    },

    // Tìm kiếm nhân viên theo mã khi bấm Enter
    async searchStaffByCode(staffCode) {
      if (this.keywordSearchUnit) {
        this.clearSearchUnitTreeView()
      }
      //this.loading(true)
      let staffId = await searchStaffInStaffTable(this.axios, {
        param: staffCode,
        type: 1
      }).catch((err) => {
        this.$toast.error(err.response.data.message)
        this.loading(false)
      })

      if (staffId === '-1') {
        if (!confirm(`Không tìm thấy nhân viên theo mã nhân viên ${staffCode}\nBạn muốn tiếp tục tìm theo User đăng nhập không ?`)) {
          return
        }
        staffId = await searchStaffInStaffTable(this.axios, {
          param: staffCode,
          type: 7
        }).catch((err) => {
          this.$toast.error(err.response.data.message)
          this.loading(false)
        })
      }

      if (staffId === '-1') {
        this.$toast.error('Không tìm thấy thông tin về mã nhân viên vừa nhập! ')
        this.loading(false)
        return
      }

      let donviId = await queryData(this.axios, {
        id_neo: 'donvi_id',
        in_table: 'admin.nhanvien',
        in_dk: `where nhanvien_id = ${staffId}`
      }).catch((err) => {
        this.$toast.error(err.response.data.message)
        this.loading(false)
      })

      const pathToExpand = this.getPathToExpandTreeView(this.danhSachDonViTreeView, Number(donviId))
      if (!pathToExpand || pathToExpand.length === 0) {
        this.$toast.error('Không tìm thấy thông tin về mã nhân viên vừa nhập! ')
        this.loading(false)
        return
      }
      this.$refs.treeViewDonVi.expandAll(pathToExpand)
      this.selectedDonViArray = [donviId]
      this.staffCodeToSearch = staffCode
      this.staffId = staffId

      this.loading(false)
    },

    async searchStaffByName() {
      //this.loading(true)
      const lstStaff = await searchStaffByCondition(this.axios, {
        dk_tim: this.formThongTinNhanVienData.tenNhanVien.trim(),
        kieu: SEARCH_BY_STAFF_NAME
      }).catch((err) => {
        this.$toast.error(err.data.message_detail)
        this.loading(false)
      })

      if (!lstStaff || lstStaff.length === 0) {
        this.$toast.error('Tên Nhân viên không tồn tại !')
        this.loading(false)
        return
      }

      if (lstStaff.length === 1) {
        await this.searchStaffByCode(lstStaff[0].ma_nv)
        return
      }
      this.danhSachNhanVienTimKiem = lstStaff
      this.$nextTick(() => {
        this.$refs.modalTimKiemNhanVien.show()
        this.loading(false)
      })
    },

    getPathToExpandTreeView(lstDonVi, id) {
      const a = lstDonVi.find((dv) => Number(dv.donvi_id) === id)
      if (a) {
        return [a.donvi_id.toString()]
      }

      for (let ii = 0; ii < lstDonVi.length; ii++) {
        const b = this.getPathToExpandTreeView(lstDonVi[ii].children, id)
        if (b) {
          b.unshift(lstDonVi[ii].donvi_id.toString())
          return b
        }
      }
    },

    async searchByMaNhanVien() {
      if (!this.formThongTinNhanVienData.maNhanVien.trim()) return

      //this.loading(true)
      const lstStaff = await searchStaffByCondition(this.axios, {
        dk_tim: this.formThongTinNhanVienData.maNhanVien.trim(),
        kieu: SEARCH_BY_STAFF_CODE
      }).catch((err) => {
        this.$toast.error(err.data.message_detail)
        this.loading(false)
      })

      if (!lstStaff || lstStaff.length === 0) {
        this.$toast.error(`Không tìm thấy mã nhân viên ${this.formThongTinNhanVienData.maNhanVien.trim()}`)
        this.loading(false)
        return
      }
      this.preventLoadTableStaff = true
      this.danhSachNhanVien = lstStaff
      this.gridNhanVien.list = lstStaff
      this.searchStaffByCode(lstStaff[0].ma_nv)
      this.loading(false)
    },

    loadTableStaff() {
      if (this.preventLoadTableStaff) {
        this.preventLoadTableStaff = false
        return
      }
      //this.loading(true)
      this.selectedNhanVien = null
      this.resetFormData()
      getDanhSachNhanVienTheoDonVi(this.axios, {
        p_donvi_id: this.selectedDonVi,
        p_kieu: 1
      })
        .catch((err) => {
          this.$toast.error(err.data.message_detail)
          this.loading(false)
        })
        .then((res) => {
          if (!res) {
            return
          }
          this.danhSachNhanVien = res
          this.gridNhanVien.list = res
          if (!this.danhSachNhanVien || this.danhSachNhanVien.length === 0) {
            this.$toast.error('Đơn vị chưa có nhân viên nào!')
          }
          this.danhSachNhanVien = res
          this.gridNhanVien.list = res
          if (!this.danhSachNhanVien || this.danhSachNhanVien.length === 0) {
            this.loading(false)
          }
          // this.loading(true)
          // if (this.staffId) {
          //   setTimeout(() => {
          //     var findIndex = this.gridNhanVien.list.findIndex((e) => e.nhanvien_id == this.staffId)
          //     console.log(`findIndex ${findIndex}`)
          //     this.$refs.gridNhanVien.selectRow(findIndex)
          //     this.loading(false)
          //   }, 1000)
          // }
        })
    },
    tableNhanVienReady() {
      console.log('tableNhanVienReady')
      console.log(this.staffId)
      this.$nextTick(() => {
        if (this.staffCodeToSearch || this.staffId) {
          this.$refs.tableDanhSachNhanVien.focusToRowByNhanVienID(this.staffId)
          this.staffCodeToSearch = ''
          return
        }
        this.$refs.tableDanhSachNhanVien.selectIndex(0)
      })
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
    async saveAction() {
      
      // try {
      if (!this.checkValidData()) {
        this.formThongTinNhanVienData.showError = true
        this.$toast.error('Nhập đầy đủ thông tin nhân viên')
        return
      }
      if(!this.formThongTinNhanVienData.checkDT){
        this.$toast.error('Số điện thoại chưa đúng định dạng!')
        return
      }
      const ngaySinhStr = moment(this.formThongTinNhanVienData.ngaySinh).format('DD/MM/YYYY')
      const param = {
        p_data: [
          {
            DIACHI_NV: this.formThongTinNhanVienData.diaChi,
            DONVI_DL_ID: this.formThongTinNhanVienData.selectedDonViDL,
            DONVI_ID: this.formThongTinNhanVienData.selectedDonVi,
            EMAIL: this.formThongTinNhanVienData.mailVNPT,
            GHICHU: this.formThongTinNhanVienData.ghiChu,
            GIOITINH: this.formThongTinNhanVienData.gioiTinh ? 1 : 0,
            HTHD_ID: this.formThongTinNhanVienData.selectedHopDong,
            MAKENH: this.formThongTinNhanVienData.maKenh,
            MA_NV: this.formThongTinNhanVienData.maNhanVien,
            MA_THE: this.formThongTinNhanVienData.soThe,
            NGAY_SN: ngaySinhStr,
            NHANVIEN_ID: this.modeSave === CAP_NHAT ? this.selectedNhanVien.nhanvien_id : null,
            SO_DT: this.formThongTinNhanVienData.dienThoai,
            TEN_NV: this.formThongTinNhanVienData.tenNhanVien,
            TEN_TN: this.formThongTinNhanVienData.tenThuNgan,
            TIEN_DC: this.formThongTinNhanVienData.tienKyQuy,
            USER_VI: this.formThongTinNhanVienData.userVi,
            CHUKY: this.formThongTinNhanVienData.chuKy || ''
          }
        ],
        p_flag: this.modeSave === THEM_MOI ? 1 : 2,
        p_is_insert: this.modeSave === THEM_MOI ? 1 : 0,
        p_kyhoadon: moment(new Date()).format('YYYYMM01'),
        p_nhanvien_ql_data:
          this.$refs.tableGanNhanVienQuanLy.getDanhSachNhanVienQuanLyDaGan().map((item) => ({
            NHANVIEN_ID: item.nhanvien_id,
            NHANVIEN_QL_ID: this.modeSave === CAP_NHAT ? this.selectedNhanVien.nhanvien_id : null
          })) || [],
        p_loainv_data:
          this.$refs.tableGanLoaiNhanVien.getDanhSachLoaiNhanVienDaGan().map((item) => ({
            NHANVIEN_ID: this.modeSave === CAP_NHAT ? this.selectedNhanVien.nhanvien_id : null,
            LOAINV_ID: item.loainv_id
          })) || [],
        p_donvi_data:
          this.$refs.tableGanDonVi.getDanhSachDonviDaGan().map((item) => ({
            NHANVIEN_ID: this.modeSave === CAP_NHAT ? this.selectedNhanVien.nhanvien_id : null,
            DONVI_ID: item.donvi_id
          })) || []
      }

      let actionSuccess = false
      let nhanVien = this.selectedNhanVien
      let loaiNhanVienDaGan = this.$refs.tableGanLoaiNhanVien.getDanhSachLoaiNhanVienDaGan()

      let updatedResult = null
      try {
        this.loading(true)
        updatedResult = await api.sp_insert_update_nhanvien(this.axios, param)
        this.loading(false)
      } catch (e) {
        this.loading(false)
        if(e.data.data){
          this.$toast.error(`${e.data.data}`)
        }else if(e.data.message){
          this.$toast.error(`${e.data.message}`)
        }
        return
      }
      if (updatedResult.data.data && updatedResult.data.data == 'OK') {
        actionSuccess = true
        if (this.modeSave === CAP_NHAT) {
          this.$toast.success('Cập nhật dữ liệu thành công!')
          let OPEN_SYNC_VINA = await this.get_app_config('OPEN_SYNC_VINA')
          if (OPEN_SYNC_VINA) {
            for (let i = 0; i < loaiNhanVienDaGan.length; i++) {
              if (loaiNhanVienDaGan[i].loainv_id == 25) {
                this.loading(true)
                let donvi_id_neo = await api.map_donvi_id_neo(this.axios, {
                  donvi_id: this.formThongTinNhanVienData.selectedDonVi
                })
                this.loading(false)
                this.loading(true)
                let mabc_id_neo = await api.map_mabc_id_neo(this.axios, {
                  donvi_id: this.formThongTinNhanVienData.selectedDonVi
                })
                this.loading(false)

                this.loading(true)
                let kq = await ccbsApi.ts_dm_nhanvientcs_capnhat(this.axios, {
                  ma_bc: mabc_id_neo,
                  dienthoai_lh: this.formThongTinNhanVienData.dienThoai,
                  sms: '0',
                  donviql_id: donvi_id_neo,
                  ma_nv: nhanVien.ma_nv,
                  ten_nv: this.formThongTinNhanVienData.tenNhanVien,
                  dia_chi: this.formThongTinNhanVienData.diaChi
                })
                this.loading(false)
                if (kq.data.data.result == 'Xoa du lieu thanh cong') {
                  this.$toast.success('Hủy loại nhân viên thu cước với nhân viên này trên vinaphone thành công!')
                } else {
                  this.$toast.error(`Lỗi Web Service: \n${kq.data.data.result}`)
                }
              }
            }
          }
        } else {
          for (let i = 0; i < loaiNhanVienDaGan.length; i++) {
            if (loaiNhanVienDaGan[i].loainv_id == 25) {
              let OPEN_SYNC_VINA = await this.get_app_config('OPEN_SYNC_VINA')
              if (OPEN_SYNC_VINA) {
                await this.CapNhatNV_VNP()
              }
            }
          }
          this.$toast.success('Thêm mới nhân viên vào dữ liệu viễn thông tỉnh thành công !')
          this.formThongTinNhanVienData.checkDT = false;
        }
      } else {
        actionSuccess = false
        this.$toast.error(`${updatedResult.data.message}`)
        return
      }
      console.log('this.formThongTinNhanVienData')
      console.log(nhanVien)
      if (nhanVien) this.staffId = nhanVien.nhanvien_id
      this.loadTableStaff()
      // } catch (e) {
      // this.loading(false)
      // console.log('exception')
      // console.log(e)
      // this.$toast.error(`Có lỗi xảy ra - FE-linecode-748: ${e.message}`) 
      // }
      
    },
    async CapNhatNV_VNP() {
      let checkNV_CCBS = false
      let kq = ''

      this.loading(true)
      let mabc_id_neo = await api.map_mabc_id_neo(this.axios, {
        donvi_id: this.formThongTinNhanVienData.selectedDonVi
      })
      this.loading(false)
      if (mabc_id_neo == 0) this.$toast.error('Đơn vị lựa chọn chưa được map với VNP!\nLiên hệ Admin để giải quyết')

      let dsNV_ThuCuoc = (
        await ccbsApi.tinh_ds_nhanvien_tcs(
          this.axios,
          // tt_nd.tentat_ccbs, "1", tt_nd.USER_NEO
          {
            dienthoai_lh: this.formThongTinNhanVienData.dienThoai,
            ma_nv: this.selectedNhanVien.ma_nv,
            page_rec: 100,
            page_num: 1
          }
        )
      ).data.data
      for (let i = 0; i < dsNV_ThuCuoc.length; i++)
        if (dsNV_ThuCuoc[i].MA_NV == 'LT9') {
          checkNV_CCBS = true
          break
        }

      let donViDaChon = this.danhSachDonViComboBox.find((e) => e.donvi_id == this.formThongTinNhanVienData.selectedDonVi)
      if (!checkNV_CCBS)
        //cần xem lại cái đơn vị
        kq = (
          await ccbsApi.ts_danhmuc_insert_nhanvien_tcs(this.axios, {
            diachi: this.formThongTinNhanVienData.diaChi,
            donvi: donViDaChon.ten_dv,
            manhom: this.formThongTinNhanVienData.maNhanVien,
            mabc: mabc_id_neo,
            tennhom: this.formThongTinNhanVienData.tenNhanVien,
            sms: '0',
            dienthoailh: this.formThongTinNhanVienData.dienThoai
          })
        ).data.data.result
      else
        kq = (
          await ccbsApi.ts_danhmuc_update_nhanvien_tcs(this.axios, {
            diachi: this.formThongTinNhanVienData.diaChi,
            donvi: donViDaChon.ten_dv,
            manhom: this.formThongTinNhanVienData.maNhanVien,
            mabc: mabc_id_neo,
            tennhom: this.formThongTinNhanVienData.tenNhanVien,
            sms: '0',
            dienthoailh: this.formThongTinNhanVienData.dienThoai
          })
        ).data.data.result

      if (kq == 'OK') this.$toast.success('Cập nhật nhân viên thu cước lên vinaphone thành công!')
      else this.$toast.error(`Lỗi Web Service: \n ${kq}`)
    },
    async XoaNV_VNP() {
      let checkNV_CCBS = false
      let kq = ''

      let dsNV_ThuCuoc = await ccbsApi.tinh_ds_nhanvien_tcs(this.axios, {})
      for (let i = 0; i < dsNV_ThuCuoc.length; i++)
        if (dsNV_ThuCuoc[i].MA_NV == 'LT9') {
          checkNV_CCBS = true
          break
        }

      // if (checkNV_CCBS)
      //     kq = await ccbsApi.ts_danhmuc_delete_nhanvien_tcs(this.axios, { manv: `${deletedNhanVien.ma_nv}` })

      // if (kq == "OK")
      //     this.$toast.success("Cập nhật nhân viên thu cước lên vinaphone thành công!")
      // else
      //     this.$toast.error(`Lỗi Web Service: kq`)
    },
    checkValidData() {
      if (!this.formThongTinNhanVienData.maNhanVien.trim()) return false
      if (!this.formThongTinNhanVienData.tenNhanVien.trim()) return false
      if (!this.formThongTinNhanVienData.tenThuNgan.trim()) return false
      if (!this.formThongTinNhanVienData.ngaySinh) return false
      if (!this.formThongTinNhanVienData.diaChi.trim()) return false
      return this.formThongTinNhanVienData.selectedDonVi
    },
    onChangeLoaiDonVi() {
      const nhanVienId = this.selectedNhanVien ? this.selectedNhanVien.nhanvien_id : 0
      this.$refs.tableGanDonVi.loadDanhSachDonViDaGan(this.formThongTinNhanVienData.selectedLoaiDonVi, this.formThongTinNhanVienData.selectedDonViDL, nhanVienId)
      this.$refs.tableGanDonVi.loadDanhSachDonViChuaGan(this.formThongTinNhanVienData.selectedLoaiDonVi, this.formThongTinNhanVienData.selectedDonViDL, nhanVienId)
    },
    openModalNhanVienViTri() {
      this.modalNhanVienVitriData = {
        nhanVienId: this.selectedNhanVien.nhanvien_id,
        tenNhanVien: this.selectedNhanVien.ten_nv
      }
      this.$nextTick(() => {
        this.$refs.modalNhanVienViTri.show()
      })
    },
    ganNhanVienViTriSuccess() {
      this.loadDataWhenChangeSelectedNhanVien(this.selectedNhanVien)
    },
    openModalKhuVuc() {
      this.$refs.modalKhuVuc.setMaNhanVien(this.selectedNhanVien.ma_nv)
      this.$refs.modalKhuVuc.loadDataToTable()
      this.$nextTick(() => {
        this.$refs.modalKhuVuc.show()
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

    clearSearchUnitTreeView() {
      this.keywordSearchUnit = ''
      this.danhSachDonViTreeView = JSON.parse(JSON.stringify(this.originalListUnit))
      this.$nextTick(() => {
        if (this.danhSachDonViTreeView && this.danhSachDonViTreeView.length > 0) {
          this.$refs.treeViewDonVi.expandAll([this.danhSachDonViTreeView[0].donvi_id])
        }
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
    onGridNhanVienRowSelected(e) {
      this.selectedNhanVienChange(e.data)
    },
  }
}
</script>

<style scoped>
/*/deep/ .e-treeview .e-list-item.e-active > .e-text-content > .e-list-text > section > span {*/
/*  color: #fff !important;*/
/*}*/

/*/deep/ .e-treeview .e-list-item.e-active > .e-text-content {*/
/*  background-color: #f9e1a9!important;*/
/*}*/

/*/deep/ .e-treeview .e-list-item.e-active > .e-text-content .e-icon-collapsible {*/
/*  color: #495057 !important;*/
/*}*/

/*/deep/ .e-treeview .e-list-item.e-active > .e-text-content .e-icon-expandable {*/
/*  color: #495057 !important;*/
/*}*/

/*/deep/ .e-treeview .e-list-item.e-active > .e-text-content > .e-list-text {*/
/*  color: #495057 !important;*/
/*}*/

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
.disabledInput {
  pointer-events: none;
  color: #666 !important;
  border-color: rgba(153, 153, 153, 0.6) !important;
}

.disabledInput > a {
  color: #666 !important;
}
</style>
