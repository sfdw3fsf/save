/* eslint-disable one-var */
<template>
  <div class="main-wrapper">
    <breadcrumb :header="header" v-show="!ispopup"/>
    <!-- <div class="breadcrumb-top">
      <div class="main-title">Gán địa địa danh-nhân viên</div>
      <ul class="breadcrumb">
        <li class="breadcrumb-item">
          <a href="#"><span class="nc-icon-outline ui-1_home-simple"></span> Trang chủ </a>
        </li>
        <li class="breadcrumb-item"><a href="#">Địa bàn</a></li>
        <li class="breadcrumb-item active">Gán địa địa danh-nhân viên</li>
      </ul>
    </div> -->
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="#" @click.prevent="tsbtnLoa_Click"> <span class="icon one-file-attach1"></span>Lấy DS </a>
        </li>
        <li>
          <a href="#" @click.prevent="tsbtnGhiLai_Click"> <span class="icon one-save"></span>Ghi lại </a>
        </li>
        <li>
          <a href="#" @click.prevent="tsbtnXoaDL_Nap_Click"> <span class="icon one-trash"></span>Xóa nạp </a>
        </li>
      </ul>
    </div>
    <div class="page-content">
      <div class="row">
        <div class="col-sm-4 col-12">
          <div class="box-form">
            <div class="legend-title">Khu vực</div>
            <div class="info-row">
              <div class="key w90">Mã khu vực</div>
              <div class="value">
                <input type="text" class="form-control" v-model="MaKhuVuc" readonly />
              </div>
            </div>
            <div class="info-row">
              <div class="key w90">KV cha</div>
              <div class="value">
                <div class="select-custom">
                  <select name="" id="" class="form-control" disabled v-model="KhuVucChaId" readonly>
                    <option value=""></option>
                    <option v-for="item in DsKhuVuc" v-bind:value="item.khuvuc_id">
                      {{ item.ten_kv }}
                    </option>
                  </select>
                </div>
              </div>
            </div>
            <div class="info-row">
              <div class="key w90">Khu vực</div>
              <div class="value">
                <input type="text" class="form-control" v-model="KhuVucText" readonly />
              </div>
            </div>
            <div class="info-row">
              <div class="key w90">Ghi chú</div>
              <div class="value">
                <textarea name="GhiChu" v-model="GhiChu" class="form-control" style="height: 50px; resize: none" readonly></textarea>
              </div>
            </div>
          </div>
          <div class="box-form">
            <div class="legend-title">Danh sách khu vực</div>
            <div class="input-icon-right">
              <input type="text" class="form-control" v-on:blur="searchNodesBlur" v-model="tenKhuVucSearch" @keydown="onTenKhuVucSearch" @change="onTenKhuVucChange" />
              <span class="icon nc-icon-outline ui-1_zoom"></span>
            </div>
            <div class="tree-plus" style="height: 313px; overflow: auto">
              <ejs-treeview id="treeview" ref="treeObj" :fields="fields" @nodeSelected="onKhuVucSelect"> </ejs-treeview>
            </div>
          </div>
        </div>
        <div class="col-sm-8 col-12">
          <div class="box-move-select-table">
            <div class="box-col box-form">
              <div class="legend-title">Khu vực</div>

              <DataGrid v-bind:columns="DsKhuVuc2.headers2" v-bind:dataSource="DsKhuVuc2.data" :showColumnCheckbox="true" :enable-paging-server="false" :allowPaging="true" :showFilter="true" ref="dsKhuVuc2" :enabledSelectFirstRow="false" @selectedItemsChanged="select_selectedItemsChangedKV2">
              </DataGrid>
            </div>
            <div class="actions">
              <button class="btn" @click="MoveRight">
                <span class="-ap icon-chevron-thin-right"></span>
              </button>
              <button class="btn" @click="MoveLeft">
                <span class="-ap icon-chevron-thin-left"></span>
              </button>
            </div>
            <div class="box-col box-form">
              <div class="legend-title">Thông tin nhân viên</div>
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w30 nowrap">Nhân viên</div>
                    <div class="value">
                      <Select2 :disabled="true" v-model="NhanVienId" :options="DsNhanVien" v-on:change="ChangeNhanVienId" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w30 nowrap">Loại NV</div>
                    <div class="value">
                      <Select2 :disabled="true" v-model="LoaiNhanVienId" :options="DsLoaiNhanVien" />
                    </div>
                  </div>
                </div>
              </div>
              <div class="legend-title mart10">Khu vực đã nạp</div>
              <div>
                <DataGrid v-bind:columns="DsKhuVuc3.headers2" v-bind:dataSource="DsKhuVuc3.data" :showColumnCheckbox="true" :enable-paging-server="false" :allowPaging="true" :showFilter="true" ref="dsKhuVuc3" :enabledSelectFirstRow="false" @selectedItemsChanged="select_selectedItemsChangedKV3">
                </DataGrid>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import gridView from '@/modules/complaint/ResovleComplaintAfterSales/components/gridview.vue'
import treegridView from '@/modules/complaint/ResovleComplaintAfterSales/components/treegridview.vue'
import breadcrumb from '@/components/breadcrumb'
import 'flatpickr/dist/plugins/monthSelect/style.css'
import bssFlatPicker from '@/components/BssDatePicker'
import { DataManager, Query, ODataAdaptor, Predicate } from '@syncfusion/ej2-data'
import XLSX from 'xlsx'
import moment from 'moment'
import api from './Api'
export default {
  components: {
    breadcrumb,
    gridView,
    treegridView,
    bssFlatPicker,
    XLSX,
    moment,
    DataManager,
    Query,
    ODataAdaptor,
    Predicate
  },
  name: 'StaffPlace',
  props: {
    tag: {
      type: Number,
      default: 1
    },
    ispopup: {
      type: Boolean,
      default: false
    }
  },
  async created() {
    this.Loading = true
    try {
      Promise.all([this.getDsNhanVien(), this.getDsKhuVuc()])
      this.Loading = false
    } catch (ex) {
      this.Loading = false
    }
  },
  watch: {
    tenKhuVucSearch: function (val) {
      this.currentFindIndex = 0
    },
    Loading: function (val, oldval) {
      this.$root.showLoading(val)
    }
  },
  data() {
    return {
      header: {
        title: 'Tra cứu',
        list: [
          {
            name: ' TRA CỨU KHÁCH HÀNG QUAN TÂM OA',
            link: { name: 'Ui.cards' }
          },
          {
            name: 'TRA CỨU KHÁCH HÀNG QUAN TÂM OA',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      Loading: false,
      fields: {
        dataSource: null,
        id: 'khuvuc_id',
        parentID: 'khuvuc_cha_id',
        text: 'ten_kv',
        hasChildren: 'hasChild'
      },
      DsNhanVien: [],
      DsLoaiNhanVien: [],
      DsKhachHang: {
        headers: [
          { name: 'ma_kh', text: 'Mã khách hàng', value: '' },
          { name: 'ten_kh', text: 'Tên khách hàng', value: '' },
          { name: 'ma_tt', text: 'Mã thanh toán', value: '' },
          { name: 'ten_tt', text: 'Tên thanh toán', value: '' },
          { name: 'diachi_tt', text: 'Địa chỉ thanh toán', value: '' },
          { name: 'manv_tc', text: 'Mã Đường thu', value: '' },
          {
            name: 'da_quantam_oa',
            text: 'Quan tâm OA',
            value: '',
            type: 'bool'
          },
          { name: 'mucdich_sd', text: 'Mục đích liên hệ', value: '' }
        ],
        data: [],
        ma_thuebao: ''
      },
      KieuGoiId: 1,
      MaKhuVuc: '',
      KhuVucId: 0,
      KhuVucChaId: 0,
      KhuVucText: '',
      GhiChu: '',
      NhanVienId: 0,
      LoaiNhanVienId: 0,
      tenKhuVucSearch: '',
      currentFindIndex: 0,
      currentFindCount: 0,
      tenKhuVucSearchChanged: true,
      TreeData: {},
      DsKhuVuc: [],
      DsNap: [],
      DsKhuVuc2: {
        headers: [
          { name: 'ten_quan', text: 'Quận' },
          { name: 'ten_phuong', text: 'Phường' },
          { name: 'ten_pho', text: 'Phố' },
          { name: 'dacdiem', text: 'Đặc điểm' },
          { name: 'ten_ap', text: 'Ấp' },
          { name: 'ten_khu', text: 'Khu' }
        ],
        headers2: [
          {
            fieldName: 'ten_quan',
            headerText: 'Quận',
            allowFiltering: true,
            width: 90
          },
          {
            fieldName: 'ten_phuong',
            headerText: 'Phường',
            allowFiltering: true
          },
          {
            fieldName: 'ten_pho',
            headerText: 'Phố',
            allowFiltering: true,
            allowHtml: true,
            width: 90
          },
          {
            fieldName: 'dacdiem',
            headerText: 'Đặc điểm',
            allowFiltering: true,
            allowHtml: true
          },
          {
            fieldName: 'ten_ap',
            headerText: 'Ấp',
            allowFiltering: true,
            width: 90
          },
          { fieldName: 'ten_khu', headerText: 'Khu', allowFiltering: true }
        ],
        data: [],
        selectedItems: [],
        ma_thuebao: ''
      },
      DsKhuVuc3: {
        headers: [
          { name: 'ten_quan', text: 'Quận', value: '' },
          { name: 'ten_phuong', text: 'Phường', value: '' },
          { name: 'ten_pho', text: 'Phố', value: '' },
          { name: 'dacdiem', text: 'Đặc điểm', value: '' },
          { name: 'ten_ap', text: 'Ấp', value: '' },
          { name: 'ten_khu', text: 'Khu', value: '' }
        ],
        headers2: [
          {
            fieldName: 'ten_quan',
            headerText: 'Quận',
            allowFiltering: true,
            width: 90
          },
          {
            fieldName: 'ten_phuong',
            headerText: 'Phường',
            allowFiltering: true
          },
          {
            fieldName: 'ten_pho',
            headerText: 'Phố',
            allowFiltering: true,
            allowHtml: true,
            width: 90
          },
          {
            fieldName: 'dacdiem',
            headerText: 'Đặc điểm',
            allowFiltering: true,
            allowHtml: true
          },
          {
            fieldName: 'ten_ap',
            headerText: 'Ấp',
            allowFiltering: true,
            width: 90
          },
          { fieldName: 'ten_khu', headerText: 'Khu', allowFiltering: true }
        ],
        data: [],
        selectedItems: [],
        ma_thuebao: ''
      }
    }
  },
  methods: {
    getDsKhuVuc: async function () {
      var input = { kieugoiId: this.KieuGoiId }
      var self = this
      this.Loading = true
      try {
        this.DsKhuVuc = this.GetData(await api.getDsKhuVuc(this.axios, input))
        this.Loading = false
      } catch (ex) {
        this.Loading = false
      }
      var data = []
      self.DsKhuVuc.forEach(function (item) {
        var newItem = {
          khuvuc_cha_id: item.khuvuc_cha_id == '0' ? null : item.khuvuc_cha_id,
          khuvuc_id: item.khuvuc_id,
          ten_kv: item.ten_kv,
          unitlevel: item.unitlevel,
          ma_kv: item.ma_kv,
          ghichu: item.ghichu
        }

        var child = self.DsKhuVuc.filter((x) => x.khuvuc_cha_id === item.khuvuc_id)
        let count = child.length
        if (count > 1) {
          newItem.hasChild = true
          newItem.expanded = true
          newItem.count = count
          // newItem.child = child
          if (newItem.khuvuc_cha_id === 0 || newItem.unitlevel <= 2) {
            newItem.expanded = true
          } else {
            newItem.expanded = false
          }
        }

        if (data.filter((x) => x.khuvuc_id === newItem.khuvuc_id).length === 0) {
          data.push(newItem)
        }
      })
      // data[0].ten_kv = `${data[0].ten_kv} ( ${data.length} )`
      this.TreeData = data.sort((a, b) => a.ten_kv.localeCompare(b.ten_kv))
      this.fields = {
        dataSource: this.TreeData,
        id: 'khuvuc_id',
        parentID: 'khuvuc_cha_id',
        text: 'ten_kv',
        hasChildren: 'hasChild'
      }
    },
    getDsKhuVucPx: async function () {
      var input = { khuvucId: this.KhuVucId, loainvId: this.LoaiNhanVienId }
      let data = this.GetData(await api.getDsKhuVucPx(this.axios, input))
      if (data.length > 0) {
        let i = 1
        data.forEach(function (item) {
          i = i + 1
          item.keyId = `key2_${i}`
        })
        this.DsKhuVuc2.data = data
      }
      this.DsKhuVuc2.data = []
    },
    getDsKhuVucById: async function (kvId) {
      var input = { khuvucId: kvId }
      var data = this.GetData(await api.getDsKhuVucById(this.axios, input))
      return data
    },
    getDsLoaiNhanVien: async function () {
      this.DsLoaiNhanVien = []
      var input = { kieu: 1, nhanvienId: this.NhanVienId }
      let data = this.GetData(await api.getDsLoaiNhanVien(this.axios, input))
      this.DsLoaiNhanVien = data.map((x) => ({
        id: x.loainv_id,
        text: x.ten == null ? '' : x.ten
      }))
      this.LoaiNhanVienId = this.DsLoaiNhanVien[0].id
    },
    getDsNhanVien: async function () {
      var input = {}
      let data = this.GetData(await api.getDsNhanVien(this.axios, input))
      this.DsNhanVien = data.map((x) => ({
        id: x.nhanvien_id,
        text: x.ten_nv == null ? '' : x.ten_nv
      }))
    },
    getDsNhanVienPx: async function () {
      var input = {
        loainvId: this.LoaiNhanVienId,
        nhanvienId: this.NhanVienId
      }
      var nhanviens = this.GetData(await api.getDsNhanVienPx(this.axios, input))
      let i = 1
      nhanviens.forEach(function (item) {
        i = i + 1
        item.keyId = `key3_${i}`
      })
      this.DsKhuVuc3.data = nhanviens
    },
    getDsNhanVienByKieu: async function () {
      var input = { kieu: 1 }
      this.DsNhanVien = this.GetData(await api.getDsNhanVienByKieu(this.axios, input))
    },
    select_selectedItemsChangedKV2: function (dataKeys) {
      this.DsKhuVuc2.checked = dataKeys
      this.DsKhuVuc2.selectedItems = dataKeys
    },
    select_selectedItemsChangedKV3: function (dataKeys) {
      this.DsKhuVuc3.selectedItems = dataKeys
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },
    async tsbtnLoa_Click() {
      try {
        await this.LayDanhSach()
      } catch (err) {
        this.ShowError(`${err.message}`)
      } finally {
        this.Loading = false
      }
    },
    LayDanhSach: async function () {
      try {
        this.Loading = true
        await this.LoadDS()
        await this.HienThi_DuLieu_Nap()
        this.Loading = false
      } catch (ex) {
        this.Loading = false
      }
    },
    getKey: async function (keyName) {
      var input = {
        keyname: keyName,
        numblocksize: 1,
        numretry: 10
      }
      let response = await api.getKey(this.axios, input)
      if (response.data.error === 200 || response.data.error === '200') {
        let data = response.data.data
        return data
      } else {
        return 0
      }
    },
    async tsbtnGhiLai_Click() {
      try {
        this.Loading = true
        await this.GhiLai()
      } catch (err) {
        this.ShowError(`${err.message}`)
      } finally {
        this.Loading = false
      }
    },
    GhiLai: async function () {
      var self = this
      if (this.NhanVienId.length <= 0) {
        this.ShowError('Chưa chọn thông tin nhân viên !')
        return
      }
      if (this.LoaiNhanVienId.length <= 0) {
        this.ShowError('Chưa chọn thông tin loại nhân viên !')
        return
      }
      var input = {
        loainvId: 55,
        nhanvienId: this.NhanVienId,
        danhsach: [
          // {'nhanvienpx_id': '526957', 'phuong_id': '465', 'pho_id': '2842', 'ap_id': '0', 'khu_id': '0', 'dacdiem_id': '0', 'quan_id': '11', 'khuvuc_id': '676'}
        ]
      }
      var c = function (x, item) {
        if (x.khuvuc_id === item.khuvuc_id && x.quan_id === item.quan_id && x.pho_id === item.pho_id && x.phuong_id === item.phuong_id && x.dacdiem_id === item.dacdiem_id && x.ap_id === item.ap_id) {
          return true
        }
        return false
      }
      var selectedKVs = this.DsKhuVuc3.data
      if (selectedKVs.length >= 0) {
        input.danhsach = []
        input.loainvId = this.LoaiNhanVienId
        let nhanvienId = this.NhanVienId
        let khuvucId = this.KhuVucId

        /*
        selectedKVs.forEach(function (item) {
          let newItem = {
            nhanvienpx_id: api.getKey('NHANVIEN_PX'),
            phuong_id: item.phuong_id,
            pho_id: item.pho_id,
            ap_id: item.ap_id,
            khu_id: item.khu_id,
            dacdiem_id: item.dacdiem_id,
            quan_id: item.quan_id,
            khuvuc_id: item.khuvuc_id
          }
          if (input.danhsach.filter((x) => c(x, newItem)).length === 0) {
            input.danhsach.push(newItem)
          }
        })
        */
        for (const item of selectedKVs) {
          let nhanvienpx_id = await self.getKey('NHANVIEN_PX')
          let newItem = {
            nhanvienpx_id: nhanvienpx_id,
            phuong_id: item.phuong_id,
            pho_id: item.pho_id,
            ap_id: item.ap_id,
            khu_id: item.khu_id,
            dacdiem_id: item.dacdiem_id,
            quan_id: item.quan_id,
            khuvuc_id: item.khuvuc_id
          }
          if (input.danhsach.filter((x) => c(x, newItem)).length === 0) {
            input.danhsach.push(newItem)
          }
        }

        // capNhatNhanVien
        // this.Loading = true
        try {
          var response = await api.capNhatNhanVien(this.axios, input)
          this.Loading = false
          if (response.data.error === 200 || response.data.error === '200') {
            if (response.data.error_code === 'BSS-00000000') {
              this.ShowSuccess('Cập nhật thành công !')
            } else {
              this.ShowError(`Cập nhật không thành công, chi tiết lỗi  ${response.data.message} !`)
            }

            await this.LoadDS()
          }
        } catch (ex) {
          // this.Loading = false
          this.ShowError('Cập nhật lỗi  !')
        }
      } else {
        this.ShowError('Chưa chọn khu vực ')
      }
    },
    async tsbtnXoaDL_Nap_Click() {
      try {
        await this.Xoa()
      } catch (err) {
        this.ShowError(`${err.message}`)
      } finally {
        this.Loading = false
      }
    },
    Xoa: async function () {
      // this.DsKhuVuc3.data = []
      // Refer trong code
      this.DsKhuVuc3.data = this.DsKhuVuc3.data.filter((x) => !x.ischecked)
      var c = function (x, item) {
        if (x.khuvuc_id === item.khuvuc_id && x.quan_id === item.quan_id && x.pho_id === item.pho_id && x.phuong_id === item.phuong_id && x.dacdiem_id === item.dacdiem_id && x.ap_id === item.ap_id) {
          return true
        }
        return false
      }
      this.DsKhuVuc3.data = this.DsKhuVuc3.data.filter((x) => this.DsNap.filter((y) => c(x, y)).length === 0)
      this.DsNap = []

      try {
        this.Loading = true
        await this.LoadDS()
        this.Loading = false
      } catch (ex) {
        this.Loading = false
      }
    },
    onDonViSelect: function (item) {
      console.log(item)
    },
    onTenKhuVucSearch: async function (event) {
      if (event.keyCode === 13) {
        this.tenKhuVucSearchChanged = false
        this.searchNodes()
        /*
        return
        if (this.tenKhuVucSearch.length > 0) {
          let filterKhuVuc = this.fields.dataSource
            .filter(x => x.ten_kv.toLowerCase().includes(this.tenKhuVucSearch.trim().toLowerCase()))
            .sort((a, b) => a.ten_kv.localeCompare(b.ten_kv))
          this.currentFindCount = filterKhuVuc.length
          if (filterKhuVuc.length === 0) return
          let treeObj = document.getElementById('treeview').ej2_instances[0]
          if (this.currentFindIndex >= filterKhuVuc.lengh - 1) { this.currentFindIndex = 0 }
          treeObj.selectedNodes = []
          let i = this.currentFindIndex
          if (filterKhuVuc[i] != null && filterKhuVuc[i].khuvuc_id) {
            treeObj.selectedNodes.push(filterKhuVuc[i])
            treeObj.ensureVisible(treeObj.selectedNodes[0].khuvuc_id)
            this.currentFindIndex++
          } else {
            console.log(`filterKhuVuc[i].khuvuc_id is null`)
            console.log(filterKhuVuc[i])
          }
        }
        */
      } else {
        this.tenKhuVucSearchChanged = true
      }
    },
    onTenKhuVucChange() {
      this.currentFindIndex = 0
    },
    async onKhuVucSelect(data) {
      let treeObj = document.getElementById('treeview').ej2_instances[0]
      let kvid = treeObj.selectedNodes[0]
      console.log('kvid:' + kvid)
      this.KhuVucId = kvid
      try {
        var khuvucs = this.fields.dataSource.filter((x) => x.khuvuc_id === kvid)
        console.log(khuvucs)
        this.Loading = true
        if (khuvucs[0]) {
          let khuvuc = khuvucs[0]
          this.MaKhuVuc = khuvuc.ma_kv
          this.KhuVucChaId = khuvuc.khuvuc_cha_id
          this.KhuVucText = khuvuc.ten_kv
          this.GhiChu = khuvuc.ghichu
          if (khuvuc.unitlevel === 4) {
            this.KhuVucId = khuvuc.khuvuc_cha_id
            this.NhanVienId = kvid.split('-')[0]
            this.KhuVucId = kvid.split('-')[1]
            console.log('kvid.split()[0]')
            console.log(kvid.split('-')[0])
            await this.getDsLoaiNhanVien()
            let kv = await this.getDsKhuVucById(this.KhuVucId)
            if (kv[0]) {
              this.GhiChu = kv[0].ghichu
              this.MaKhuVuc = kv[0].ma_kv
              this.MaKhuVuc = kv[0].ma_kv
              this.KhuVucText = kv[0].ten_kv
              this.KhuVucChaId = kv[0].khuvuc_cha_id
            }
          }
          await this.LoadDS()
          await this.HienThi_DuLieu_Nap()
        }

        this.Loading = false
      } catch (ex) {
        this.Loading = false
      }
    },
    HienThi_DuLieu_Nap: async function () {
      var input = {
        loainvId: this.LoaiNhanVienId,
        nhanvienId: this.NhanVienId
      }
      var nhanviens = this.GetData(await api.getDsNhanVienPx(this.axios, input))
      let i = 1
      nhanviens.forEach(function (item) {
        item.keyId = `key3_${i}`
        i = i + 1
      })
      this.DsKhuVuc3.data = nhanviens
    },
    ChangeNhanVienId: async function () {
      await this.LoadDS()
    },
    LoadDS: async function () {
      var input = { khuvucId: this.KhuVucId, loainvId: this.LoaiNhanVienId }
      var nhanviens = this.GetData(await api.getDsKhuVucPx(this.axios, input))
      let i = 1
      nhanviens.forEach(function (item) {
        item.keyId = `key2_${i}`
        i = i + 1
      })
      this.DsKhuVuc2.data = nhanviens
      await this.HienThi_DuLieu_Nap()
    },
    MoveLeft: function () {
      let selectedRights = this.DsKhuVuc3.selectedItems
      if (selectedRights.length == 0) {
        this.ShowError(`Bạn chưa chọn dữ liệu để thực hiện !`)
        return
      }
      var self = this
      var c = function (x, item) {
        if (x.khuvuc_id === item.khuvuc_id && x.quan_id === item.quan_id && x.pho_id === item.pho_id && x.phuong_id === item.phuong_id && x.dacdiem_id === item.dacdiem_id && x.ap_id === item.ap_id) {
          return true
        }
        return false
      }
      if (selectedRights.length > 0) {
        selectedRights.forEach(function (item) {
          console.log(item.khuvuc_id)
          if (self.DsKhuVuc2.data.filter((x) => c(x, item)).length === 0) {
            self.DsKhuVuc2.data.push(item)
          }
        })
        this.DsKhuVuc3.data = this.DsKhuVuc3.data.filter((x) => self.DsKhuVuc2.data.filter((y) => c(x, y)).length === 0)
      }
    },
    MoveRight: function () {
      var self = this
      let selectedLefts = this.DsKhuVuc2.selectedItems
      if (selectedLefts.length == 0) {
        this.ShowError(`Bạn chưa chọn dữ liệu để thực hiện !`)
        return
      }
      var c = function (x, item) {
        if (x.khuvuc_id === item.khuvuc_id && x.quan_id === item.quan_id && x.pho_id === item.pho_id && x.phuong_id === item.phuong_id && x.dacdiem_id === item.dacdiem_id && x.ap_id === item.ap_id) {
          return true
        }
        return false
      }
      if (selectedLefts.length > 0) {
        selectedLefts.forEach(function (item) {
          if (self.DsKhuVuc3.data.filter((x) => c(x, item)).length === 0) {
            self.DsKhuVuc3.data.push(item)
            self.DsNap.push(item)
          }
        })
        this.DsKhuVuc2.data = this.DsKhuVuc2.data.filter((x) => self.DsKhuVuc3.data.filter((y) => c(x, y)).length === 0)
      }
    },
    ShowAlert: function (msg) {
      this.$toast.success(msg)
    },
    ShowError: function (msg) {
      this.$toast.error(msg)
    },
    ShowSuccess: function (msg) {
      this.$toast.success(msg)
    },
    searchNodesBlur() {
      if (this.tenKhuVucSearchChanged) {
        this.searchNodes()
      }
    },
    searchNodes: function (args) {
      var _text = this.tenKhuVucSearch
      var seft = this
      // eslint-disable-next-line one-var
      var predicats = [],
        _array = [],
        _filter = []
      let treeObj = document.getElementById('treeview').ej2_instances[0]
      if (_text == '') {
        this.changeDataSource(seft.TreeData)
      } else {
        var predicate = new Predicate('ten_kv', 'contains', _text, true)
        var filteredList = new DataManager(seft.TreeData).executeLocal(new Query().where(predicate))
        console.log(`filteredList:${filteredList.length}`)
        console.log(filteredList)
        for (var j = 0; j < filteredList.length; j++) {
          _filter.push(filteredList[j]['khuvuc_id'])
          var filters = this.getFilterItems(filteredList[j], seft.TreeData)

          for (var i = 0; i < filters.length; i++) {
            if (_array.indexOf(filters[i]) == -1 && filters[i] != null) {
              _array.push(filters[i])
              predicats.push(new Predicate('khuvuc_id', 'equal', filters[i], false))
            }
          }
        }
        if (predicats.length == 0) {
          this.changeDataSource([])
        } else {
          var query = new Query().where(new Predicate.or(predicats))
          var newList = new DataManager(this.TreeData).executeLocal(query)
          seft.changeDataSource(newList)
          setTimeout(function () {
            treeObj.expandAll()
          }, 100)
        }
      }
    },
    getFilterItems: function (fList, list) {
      var nodes = []
      nodes.push(fList['khuvuc_id'])
      var query2 = new Query().where('khuvuc_id', 'equal', fList['khuvuc_cha_id'], false)
      var fList1 = new DataManager(list).executeLocal(query2)
      if (fList1.length != 0) {
        var pNode = this.getFilterItems(fList1[0], list)
        for (var i = 0; i < pNode.length; i++) {
          if (nodes.indexOf(pNode[i]) == -1 && pNode[i] != null) {
            nodes.push(pNode[i])
          }
        }
        return nodes
      }
      return nodes
    },
    changeDataSource: function (data) {
      let treeObj = document.getElementById('treeview').ej2_instances[0]
      treeObj.fields = {
        dataSource: data,
        id: 'khuvuc_id',
        text: 'ten_kv',
        parentID: 'khuvuc_cha_id',
        hasChildren: 'hasChild'
      }
      /*
      this.fields = {
        dataSource: this.TreeData,
        id: 'khuvuc_id',
        parentID: 'khuvuc_cha_id',
        text: 'ten_kv',
        hasChildren: 'hasChild'
      }
      */
    }
  }
}
</script>
<style>
.e-parentmsgbar,
.e-pagecountmsg {
  width: 100%;
  float: left;
}
.e-grid .e-gridheader {
  position: -webkit-sticky;
  position: sticky;
  top: 0px; /* The height of top nav menu. */
  z-index: 1;
}
.breadcrumb-top .breadcrumb {
  position: absolute;
  right: 15px;
}
</style>
