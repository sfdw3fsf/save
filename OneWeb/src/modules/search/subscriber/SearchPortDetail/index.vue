<template>
  <div class="main-wrapper">
    <div class="breadcrumb-top">
      <div class="main-title">Tra cứu chi tiết port</div>
      <ul class="breadcrumb">
        <li class="breadcrumb-item">
          <a href="#"><span class="nc-icon-outline ui-1_home-simple"></span> Trang chủ </a>
        </li>
        <li class="breadcrumb-item"><a href="#">Tìm kiếm</a></li>
        <li class="breadcrumb-item active">Tra cứu chi tiết Port</li>
      </ul>
    </div>
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="#" @click.prevent="tsbtnNhapMoi_Click"> <span class="icon one-file-attach1"></span>Lấy DS(F5) </a>
        </li>
        <li>
          <a href="#" @click.prevent="tsbtnTimKiem_Click"> <span class="icon one-search"></span>Tìm kiếm(F3) </a>
        </li>
        <li>
          <a href="#" @click.prevent="exportExcel"> <span class="icon one-xlsx-import"></span>Xuất Excel </a>
        </li>
      </ul>
    </div>
    <div class="page-content">
      <div class="box-form">
        <div class="legend-title">Thông tin tra cứu</div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w100">Đơn vị QL</div>
              <div class="value">
                <Select2 v-model="DonViQuanLyId" :options="DonViQuanLy" />
              </div>
            </div>
            <div class="info-row">
              <div class="key w100">Trạm thi công</div>
              <div class="value">
                <!--
                                <div class="select-custom">
                                    <select name="TramThiCongId" id="TramThiCongId" class="form-control" v-model="TramThiCongId">
                                        <option v-for="item in TramThiCong" v-bind:key="item.donvi_id" v-bind:value="item.donvi_id">{{
                                                item.ten_dv}}
                                        </option>
                                    </select>
                                </div>
                                -->
                <Select2 v-model="TramThiCongId" :options="TramThiCong" />
              </div>
            </div>
            <div class="info-row">
              <div class="key w100">Trạm thiết bị</div>
              <div class="value">
                <!--
                                <div class="select-custom">
                                    <select name="TramThietBiId" id="TramThietBiId" class="form-control" v-model="TramThietBiId">
                                        <option v-for="item in TramThietBi" v-bind:key="item.donvi_id" v-bind:value="item.donvi_id">{{
                                                item.ten_dv}}
                                        </option>
                                    </select>
                                </div>
                                -->
                <Select2 v-model="TramThietBiId" :options="TramThietBi" />
              </div>
            </div>
            <div class="info-row">
              <div class="key w100">Dslam</div>
              <div class="value">
                <!--
                                <div class="select-custom">
                                    <select name="DsLamId" id="DsLamId" class="form-control" v-model="DsLamId">
                                         <option v-for="item in DSLam" v-bind:key="item.dslam_id" v-bind:value="item.dslam_id">{{
                                                item.tendslam}}
                                        </option>
                                    </select>
                                </div>
                                -->
                <Select2 v-model="DsLamId" :options="DSLam" />
              </div>
            </div>
            <div class="info-row">
              <div class="key w100">Trạng thái</div>
              <div class="value">
                <!--
                                <div class="select-custom">
                                    <select name="TrangThaiPortId" id="TrangThaiPortId" class="form-control" v-model="TrangThaiPortId">
                                         <option v-for="item in TrangThaiPort" v-bind:key="item.tt_port_id" v-bind:value="item.tt_port_id">{{
                                                item.trangthai_port}}
                                        </option>
                                    </select>
                                </div>
                                -->
                <Select2 v-model="TrangThaiPortId" :options="TrangThaiPort" />
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="row">
              <div class="col-sm-8 col-12">
                <div class="info-row">
                  <div class="key w70">System</div>
                  <div class="value">
                    <input type="text" ref="txtSystem" class="form-control highlight bold" v-model="SystemName" v-on:keyup.enter="TimKiem('SystemName')" />
                  </div>
                </div>
              </div>
              <div class="col-sm-4 col-12">
                <div class="info-row">
                  <div class="key w70">Port MDF</div>
                  <div class="value">
                    <input type="text" class="form-control highlight bold" v-model="PortMdf" v-on:keyup.enter="TimKiem('PortMdf')" />
                  </div>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-8 col-12">
                <div class="info-row">
                  <div class="key w70">Slot</div>
                  <div class="value">
                    <input type="text" class="form-control highlight bold" v-model="Slot" v-on:keyup.enter="TimKiem('Slot')" />
                  </div>
                </div>
              </div>
              <div class="col-sm-4 col-12">
                <div class="info-row">
                  <div class="key w70">Port</div>
                  <div class="value">
                    <input type="text" class="form-control highlight bold" v-model="Port" v-on:keyup.enter="TimKiem('Port')" />
                  </div>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-8 col-12">
                <div class="info-row">
                  <div class="key w70">Rack</div>
                  <div class="value">
                    <input type="text" class="form-control highlight bold" v-model="Rack" v-on:keyup.enter="TimKiem('Rack')" />
                  </div>
                </div>
              </div>
              <div class="col-sm-4 col-12">
                <div class="info-row">
                  <div class="key w70">Shelf</div>
                  <div class="value">
                    <input type="text" class="form-control highlight bold" v-model="Shelf" v-on:keyup.enter="TimKiem('Shelf')" />
                  </div>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-8 col-12">
                <div class="info-row">
                  <div class="key w70">Vpi/Svlan</div>
                  <div class="value">
                    <input type="text" class="form-control highlight bold" v-model="VpiSvLan" v-on:keyup.enter="TimKiem('VpiSvLan')" />
                  </div>
                </div>
              </div>
              <div class="col-sm-4 col-12">
                <div class="info-row">
                  <div class="key w70">Vci/CVlan</div>
                  <div class="value">
                    <input type="text" class="form-control highlight bold" v-model="VciCvLan" v-on:keyup.enter="TimKiem('VciCvLan')" />
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="box-form">
        <div class="legend-title">
          <div class="pull-left">Danh sách Port</div>
          <div class="pull-right red">
            <div class="list-checks">
              <div class="item">Tổng số: {{ Count_TotalItem }}</div>
              <div class="item">Chưa SD: {{ Count_CHUA_SD }}</div>
              <div class="item">Đã SD: {{ Count_DA_SD }}</div>
              <div class="item">Tạm cấp: {{ Count_TAM_CAP }}</div>
              <div class="item">Dự phòng: {{ Count_DU_PHONG }}</div>
              <div class="item">Hỏng: {{ Count_HONG }}</div>
            </div>
          </div>
          <div class="clearfix"></div>
        </div>
        <!--
                <gridView :headers="DsPort.headers" :datalist="DsPort.data" :flcol="DsPort.flcol"
                                :showCheck='false' :showHeader='true' @recordChange='SelectRow'>
                            </gridView>
                            -->
<!--        <DataGridC v-bind:columns="DsPort.headers2" :enabledSelectFirstRow="true" v-bind:dataSource="DsPort.data" :showColumnCheckbox="false" :enable-paging-server="false" :allowFiltering="true" :filterSettings="filterOptions" ref="refDsPort" @rowClicked="SelectRow"> </DataGridC>-->

        <DataGrid :columns="DsPort.headers2" :enabledSelectFirstRow="true" :dataSource="DsPort.data" :showColumnCheckbox="false" :allowPaging='true' :enable-paging-server="false" :allowFiltering="true" :allowSorting="false" :filterSettings="filterOptions" ref="refDsPort" @rowClicked="SelectRow"> </DataGrid>

      </div>
    </div>
  </div>
</template>
<script>
import Vue from "vue";
import gridView from '@/modules/complaint/ResovleComplaintAfterSales/components/gridview.vue'
import treegridView from '@/modules/complaint/ResovleComplaintAfterSales/components/treegridview.vue'
import breadcrumb from '@/components/breadcrumb'
import 'flatpickr/dist/plugins/monthSelect/style.css'
import bssFlatPicker from '@/components/BssDatePicker'
import DataGridC from '@/components/Shared/DataGrid/index.vue'
import LOAI_DV from '@/const/enums/LOAI_DV'
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
    DataGridC
  },
  name: 'SearchPortDetail',
  created() {
    this.Loading = true
    try {
      Promise.all([this.getDsDonViQuanLy(), this.getTramThiCong(), this.getDsTramThietBi(), this.getDsDSLamTheoDonVi(), this.getDmTrangThaiPort()])
    } catch (ex) {
      console.log(ex)
      this.Loading = false
    }

    this.Loading = false
  },
  watch: {
    DonViQuanLyId: async function (val) {
      this.Loading = true
      try {
        this.TramThiCong = []
        this.TramThietBi = []
        this.DSLam = []
        await this.getTramThiCong()
        this.Loading = false
      } catch (ex) {
        this.Loading = false
      }
    },
    TramThiCongId: async function (newval, oldval) {
      this.Loading = true
      this.TramThietBi = []
      this.DSLam = []
      await this.getDsTramThietBi()
      this.Loading = false
    },
    TramThietBiId: async function (val) {
      this.Loading = true
      try {
        this.DSLam = []
        await this.getDsDSLamTheoDonVi()
        this.Loading = false
      } catch (ex) {
        this.Loading = false
      }
    },
    Loading: function (val) {
      this.$root.showLoading(val)
    }
  },

  data() {
    return {
      filterOptions: { type: "Excel" , showFilterBarOperator: true},
      header: {
        title: 'Tra cứu',
        list: [
          { name: ' TRA CỨU KHÁCH HÀNG QUAN TÂM OA', link: { name: 'Ui.cards' } },
          {
            name: 'TRA CỨU KHÁCH HÀNG QUAN TÂM OA',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      DsPort: {
        headers: [
          { name: 'RACK', text: 'RACK', value: '' },
          { name: 'SHELF', text: 'SHELF', value: '' },
          { name: 'SLOT', text: 'SLOT', value: '' },
          { name: 'VPI', text: 'VPI', value: '' },
          { name: 'VCI', text: 'VCI', value: '' },
          { name: 'SELFID01', text: 'FRAME', value: '' },
          { name: 'SELFID02', text: 'OLT_SLOT', value: '' },
          { name: 'SELFID03', text: 'OLT_PORT', value: '' },
          { name: 'SELFID04', text: 'ONUID', value: '' },
          { name: 'PORTMDF', text: 'PORT MDF', value: '' },
          { name: 'ADSL_PORT', text: 'ADSL PORT', value: '' },
          { name: 'TRANGTHAI_PORT', text: 'TRẠNG THÁI', value: '' },
          { name: 'DS_MA_TB', text: 'DS THUÊ BAO', value: '' },
          { name: 'PORT', text: 'PORT', value: '' },
          { name: 'ONU_PASSWORD', text: 'Onu Pass', value: '' },
          { name: 'VITRI', text: 'Vị trí', value: '' },
          { name: 'TEN_CARD', text: 'CARD', value: '' },
          { name: 'PORT_ID', text: 'PORT_ID', value: '' },
          { name: 'VCI_VPI_ID', text: 'VCI_VPI_ID', value: '' }
        ],
        headers2: [
          { fieldName: 'rack', headerText: 'RACK'},
          { fieldName: 'shelf', headerText: 'SHELF'},
          { fieldName: 'slot', headerText: 'SLOT'},
          { fieldName: 'vpi', headerText: 'VPI'},
          { fieldName: 'vci', headerText: 'VCI'},
          { fieldName: 'selfid01', headerText: 'FRAME'},
          { fieldName: 'selfid02', headerText: 'OLT_SLOT'},
          { fieldName: 'selfid03', headerText: 'OLT_PORT'},
          { fieldName: 'selfid04', headerText: 'ONUID'},
          { fieldName: 'port_mdf', headerText: 'PORT MDF'},
          { fieldName: 'adsl_port', headerText: 'ADSL PORT'},
          { fieldName: 'trangthai_port', headerText: 'TRẠNG THÁI'},
          { fieldName: 'ds_ma_tb', headerText: 'DS THUÊ BAO'},
          { fieldName: 'port', headerText: 'PORT'},
          { fieldName: 'onu_password', headerText: 'Onu Pass'},
          { fieldName: 'vitri', headerText: 'Vị trí'},
          { fieldName: 'ten_card', headerText: 'CARD'},
          { fieldName: 'port_id', headerText: 'PORT_ID'},
          { fieldName: 'vci_vpi_id', headerText: 'VCI_VPI_ID'}
        ],
        data: []
      },
      Loading: false,
      DonViQuanLyId: 0,
      DonViQuanLy: [],
      TramThiCongId: '',
      TramThiCong: [],
      TramThietBiId: '',
      TramThietBi: [],
      DSLam: [],
      TrangThaiPortId: '',
      TrangThaiPort: [],
      DsLamId: '',
      SystemName: '',
      Slot: '',
      Rack: '',
      VpiSvLan: '',
      PortMdf: '',
      Port: '',
      Shelf: '',
      VciCvLan: '',
      TRANGTHAI_PORT: {
        CHUA_SD: 1,
        DA_SD: 3,
        DU_PHONG: 5,
        HONG: 4,
        TAM_CAP: 2
      },
      Count_TotalItem: 0,
      Count_CHUA_SD: 0,
      Count_DA_SD: 0,
      Count_DU_PHONG: 0,
      Count_HONG: 0,
      Count_TAM_CAP: 0
    }
  },
  methods: {
    actionBegin(args) {
      if (args.requestType === "filterchoicerequest" || args.requestType ==="filtersearchbegin" ) {
        args.filterChoiceCount = 3000;
      }
    },
    getDsDonViQuanLy: async function () {
      var input = { donViId: 0, loaiDVId: LOAI_DV.DONVIQL_LD }
      let data = this.GetData(await api.getDsDonViQuanLy(this.axios, input))
      this.DonViQuanLy = data.map((x) => ({ id: x.donvi_id, text: x.ten_dv == null ? '' : x.ten_dv }))
      if (this.DonViQuanLy != null && this.DonViQuanLy.length > 0) this.DonViQuanLyId = this.DonViQuanLy[0].id
    },
    getTramThiCong: async function () {
      var input = { donViId: this.DonViQuanLyId, loaiDVId: LOAI_DV.TRAM_VT }
      let data = this.GetData(await api.getDsDonViThiCong(this.axios, input))
      this.TramThiCong = data.map((x) => ({ id: x.donvi_id, text: x.ten_dv == null ? '' : x.ten_dv })) // this.GetData(await api.getDsDonViThiCong(this.axios, input))
      if (this.TramThiCong != null && this.TramThiCong.length > 0) this.TramThiCongId = this.TramThiCong[0].id
    },
    getDsTramThietBi: async function () {
      var input = { donViId: this.TramThiCongId, loaiDVId: LOAI_DV.TRAM_PORT }
      let data = this.GetData(await api.getDsTramThietBi(this.axios, input))
      this.TramThietBi = data.map((x) => ({ id: x.donvi_id, text: x.ten_dv == null ? '' : x.ten_dv })) // this.GetData(await api.getDsTramThietBi(this.axios, input))
      if (this.TramThietBi != null && this.TramThietBi.length > 0) {
        this.TramThietBiId = this.TramThietBi[0].id
      } else this.TramThietBiId = ''
    },
    getDsDSLamTheoDonVi: async function () {
      var input = { donViId: this.TramThietBiId }
      let data = this.GetData(await api.getDsDSLamTheoDonVi(this.axios, input))
      this.DSLam = data.map((x) => ({ id: x.dslam_id, text: x.tendslam == null ? '' : x.tendslam })) // this.GetData(await api.getDsDSLamTheoDonVi(this.axios, input))
      if (this.DSLam != null && this.DSLam.length > 0) {
        this.DsLamId = this.DSLam[0].id
      } else {
        this.DsLamId = ''
      }
    },
    getDmTrangThaiPort: async function () {
      var input = {}
      let data = this.GetData(await api.getDmTrangThaiPort(this.axios, input))
      this.TrangThaiPort = data.map((x) => ({ id: x.tt_port_id, text: x.trangthai_port == null ? '' : x.trangthai_port })) // this.GetData(await api.getDmTrangThaiPort(this.axios, input))
      if (this.TrangThaiPort.length > 0) this.TrangThaiPortId = this.TrangThaiPort[0].id
    },
    toDefault1: function (a) {
      if (a == '') return -1
      return a
    },
    getDanhSach: async function () {
      // let test = {
      //   dslamId: 0,
      //   slot: -1,
      //   port: -1,
      //   system: '0',
      //   rack: -1,
      //   shelf: -1,
      //   vci: -1,
      //   vpi: -1,
      //   thamSo: '0',
      //   trangThai: 0,
      //   port_mdf: -1,
      //   loaiTBId: '0'
      // }
      var input = {
        dslamId: this.DsLamId == null || this.DsLamId == '' ? 0 : this.DsLamId,
        slot: this.toDefault1(this.Slot),
        port: this.toDefault1(this.Port),
        system: this.SystemName == '' ? '0' : this.SystemName,
        rack: this.toDefault1(this.Rack),
        shelf: this.toDefault1(this.Shelf),
        vci: this.toDefault1(this.VciCvLan),
        vpi: this.toDefault1(this.VpiSvLan),
        thamSo: '0',
        trangThai: parseInt(this.TrangThaiPortId),
        port_mdf: this.toDefault1(this.PortMdf),
        loaiTBId: '0'
      }
      try {
        let response = await api.LayDanhSach(this.axios, input)
        if (response.data.error == 200 || response.data.error == '200') {
          if (response.data.error_code == 'BSS-00000000') {
            this.DsPort.data = response.data.data
          } else {
            this.DsPort.data = []
            this.ShowError(`Không tìm thấy dữ liệu theo điều kiện tìm kiếm !`)
            //this.ShowError(`Có lỗi : error_code:${response.data.error_code}:${response.data.message}`)
            return
          }
        } else {
          this.ShowError(`Không tìm thấy dữ liệu theo điều kiện tìm kiếm !`)
          //this.ShowError(`Có lỗi : ${response.data.error_code}:${response.data.message}`)
          this.DsPort.data = []
          console.log(response.data.error_code)
        }
        // this.DsPort.data = this.GetData(await api.LayDanhSach(this.axios, input))
        if (this.DsPort.data && this.DsPort.data.length >= 0) {
          this.Count_TotalItem = this.DsPort.data.length
          this.Count_CHUA_SD = this.DsPort.data.filter((x) => x.trangthai == this.TRANGTHAI_PORT.CHUA_SD).length
          this.Count_DA_SD = this.DsPort.data.filter((x) => x.trangthai == this.TRANGTHAI_PORT.DA_SD).length
          this.Count_DU_PHONG = this.DsPort.data.filter((x) => x.trangthai == this.TRANGTHAI_PORT.DU_PHONG).length
          this.Count_HONG = this.DsPort.data.filter((x) => x.trangthai == this.TRANGTHAI_PORT.HONG).length
          this.Count_TAM_CAP = this.DsPort.data.filter((x) => x.trangthai == this.TRANGTHAI_PORT.TAM_CAP).length
        }
        if (this.DsPort.data.length > 0) {
          this.SelectRow(0, this.DsPort.data[0])
        }
      } catch (ex) {}
    },
    exportExcel: function () {
      if (this.DsPort.data != null && this.DsPort.data.length > 0) {
        let newData = []
        let headers = this.DsPort.headers2
        for (const item of this.DsPort.data) {
          let newItem = {}
          for (const header of headers) {
            newItem[header.headerText] = item[header.fieldName]
          }
          newData.push(newItem)
        }
        // let data = XLSX.utils.json_to_sheet(this.DsPort.data)
        let data = XLSX.utils.json_to_sheet(newData)
        let wb = XLSX.utils.book_new() // make Workbook of Excel
        XLSX.utils.book_append_sheet(wb, data, 'DsPorts') // sheetAName is name of Worksheet
        // export Excel file
        XLSX.writeFile(wb, 'DsPorts.xlsx')
      } else this.$toast.error('Không có dữ liệu!')
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },
    ShowWarning: function (message) {
      this.$toast.error(message)
    },
    ShowMessage: function (message) {
      alert(message)
    },
    SelectRow: function (i, row) {
      this.Port = row.port
      this.VciCvLan = row.vci
      this.VpiSvLan = row.vpi
      this.SystemName = row.system
      this.Slot = row.slot
      this.Rack = row.rack
      this.Shelf = row.shelf
      this.PortMdf = row.port_mdf
    },
    Clear: function () {
      this.Port = ''
      this.VciCvLan = ''
      this.VpiSvLan = ''
      this.SystemName = ''
      this.Slot = ''
      this.Rack = ''
      this.Shelf = ''
      this.PortMdf = ''
      this.$refs.txtSystem.focus()
    },
    tsbtnNhapMoi_Click: async function () {
      try {
        this.Clear()
        this.Loading = true
        await this.getDanhSach()
      } catch (err) {
        this.ShowError(`${err.message}`)
        this.Loading = false
      } finally {
        this.Loading = false
      }
    },
    tsbtnTimKiem_Click: async function () {
      try {
        this.Loading = true
        await this.getDanhSach()
      } catch (err) {
        this.ShowError(`${err.message}`)
        this.Loading = false
      } finally {
        this.Loading = false
      }
    },
    async TimKiem(modelName) {
      try {
        if (modelName == 'SystemName' && this.SystemName == '') {
          return
        }
        if (modelName == 'PortMdf' && this.PortMdf == '') {
          return
        }
        if (modelName == 'Slot' && this.Slot == '') {
          return
        }
        if (modelName == 'Port' && this.Port == '') {
          return
        }
        if (modelName == 'Rack' && this.Rack == '') {
          return
        }
        if (modelName == 'Shelf' && this.Shelf == '') {
          return
        }
        if (modelName == 'VpiSvLan' && this.VpiSvLan == '') {
          return
        }
        if (modelName == 'VciCvLan' && this.VciCvLan == '') {
          return
        }
        this.Loading = true
        await this.getDanhSach()
      } catch (err) {
      } finally {
        this.Loading = false
      }
    },
    ShowAlert: function (msg) {
      this.$toast.error(msg)
      // this.$toast.warning(msg)
    },
    ShowSuccess: function (msg) {
      this.$toast.success(msg)
    },
    ShowError: function (msg) {
      this.$toast.error(msg)
    }
  }
}
</script>
<style scoped>
.breadcrumb-top .breadcrumb {
  position: absolute;
  right: 15px;
}
@import "https://cdn.syncfusion.com/ej2/material.css";
</style>
