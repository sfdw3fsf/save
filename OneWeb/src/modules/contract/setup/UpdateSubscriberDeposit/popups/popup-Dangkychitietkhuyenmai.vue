<template>
  <div class="modal-content popup-box">
    <div class="popup-header">
      <div class="title"><span class="icon -ap icon-apps text-main"></span> {{ headerText }}</div>
      <!-- <div class="close -ap icon-close" data-dismiss="modal">
        </div> -->
    </div>
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="#" @click.prevent="tsbtnCapNhan_Click"> <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Chấp nhận </a>
        </li>
        <li>
          <a href="#" @click.prevent="terminateForm"> <span class="icon ui-1_circle-remove nc-icon-glyph"></span> Thoát </a>
        </li>
      </ul>
    </div>
    <div class="popup-body">
      <div class="box-form">
        <div class="nav tabs tab-over">
          <a href="#tabcttt" data-toggle="tab" class="active">{{ lineLabel2 }}</a>
          <a href="#tabtbk" data-toggle="tab" v-if="kieu_km == 1 && dgvDSKemTheo.DataSource && dgvDSKemTheo.DataSource.length > 0">Thuê bao kèm</a>
        </div>
        <div class="tab-content nocorner bor0 pad0 bg-none" style="box-shadow: none;">
          <div id="tabcttt" class="tab-pane active">
            <div class="tab-content">
              <div class="legend-title">{{ lineLabel3 }}</div>
              <div class="table-content">
                <ejs-grid
                  ref="grid"
                  :load="load"
                  width="100%"
                  :dataSource="dt"
                  locale="vi-VN"
                  :selectionSettings="selectOptions"
                  :allowSorting="true"
                  :allowFiltering="true"
                  :allowPaging="false"
                  :pageSettings="{ pageSize: 10, pageSizes: [10, 50, 100, 200] }"
                  :gridLines="1"
                  :editSettings="{ allowEditing: true, mode: 'Normal' }"
                  :recordClick="khuyenmaiChanged"
                  :rowSelected="rowSelected"
                  :dataBound="GridBound"
                  :actionComplete="actionComplete"
                  :queryCellInfo="queryCellInfoEvent"
                  :height="300"
                >
                  <e-columns>
                    <e-column
                      v-for="col in cols"
                      :width="col.width ? col.width : 'auto'"
                      :key="col.fieldName"
                      :field="col.fieldName"
                      :type="col.type"
                      :allowEditing="col.allowEditing"
                      :clipMode="'EllipsisWithTooltip'"
                      :customAttributes="col.customAttributes"
                      :headerText="col.headerText"
                      :textAlign="col.textAlign"
                      headerTextAlign="center"
                      :template="col.template"
                      :displayAsCheckBox="col.displayAsCheckBox"
                      :editType="col.editType"
                      :format="col.format"
                      :isPrimaryKey="col.isPrimaryKey"
                      :visible="col.visible"
                      :valueAccessor="col.valueAccessor"
                    ></e-column>
                  </e-columns>
                </ejs-grid>
              </div>
              <div class="legend-title mart20">{{ lineLabel1 }}</div>
              <div class="info-row">
                <div class="value">
                  <textarea name="" class="form-control" style="height: 100px;resize: none;" readonly v-model="noidungkm"></textarea>
                </div>
              </div>
            </div>
          </div>
          <div id="tabtbk" class="tab-pane">
            <div class="tab-content">
              <div class="legend-title">Danh sách thuê bao kèm</div>
              <div class="table-content">
                <DataGrid v-bind:columns="dgvDSKemTheo.cols" v-bind:dataSource="dgvDSKemTheo.DataSource" :showFilter="false" :showColumnCheckbox="false" :dataKeyField="dgvDSKemTheo.keycol" :defaultColumnCheckboxChecked="false" :panelDataHeight="'200'" @rowClicked="dgvDSKemTheo_FocusedRowChanged">
                </DataGrid>
              </div>
              <div class="legend-title mart20">Thông tin chi tiết khuyến mại</div>
              <div class="table-content">
                <DataGrid
                  v-bind:columns="dgvChiTietKM_new.cols"
                  v-bind:dataSource="dgvChiTietKM_new.DataSource"
                  :showFilter="false"
                  :showColumnCheckbox="false"
                  :dataKeyField="dgvChiTietKM_new.keycol"
                  :defaultColumnCheckboxChecked="false"
                  :panelDataHeight="'300'"
                  @rowClicked="dgvChiTietKM_new_FocusedRowChanged"
                >
                </DataGrid>
              </div>
              <div class="legend-title mart20">Nội dung khuyến mại</div>
              <div class="table-content">
                <textarea v-model="txtND" class="form-control" style="height: 100px;resize: none;"></textarea>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<style scoped>
.disabled {
  pointer-events: none;
  color: lightgray;
  border-color: lightgray;
}
::v-deep .required {
  background-color: lightyellow;
}
::v-deep .e-numeric.e-control-wrapper.e-input-group .e-input-group-icon, .e-numeric.e-control-wrapper.e-input-group .e-input-group-icon {
  display: none !important; visibility: hidden;
}
::v-deep .e-numeric.e-control-wrapper.e-input-group .e-input-group-icon {
  display: none !important; visibility: hidden;
}
::v-deep .e-input-group-icon,.e-input-group-icon { display:none !important; visibility: hidden; }
</style>
<script>
import Vue from 'vue'
import moment from 'moment'
import API from "@/modules/contract/setup/InstallNewSubs/InstallNewSubsAPI.js";
Vue.prototype.$eventHub = new Vue()
import { DichVuVienThong } from "@/modules/contract/setup/InstallNewSubs/Enums.js";
import { Sort, Filter, Resize, Group, Freeze, CommandColumn, ExcelExport, Edit, Aggregate, Page, Toolbar, GridPlugin } from '@syncfusion/ej2-vue-grids'
Vue.use(GridPlugin)
export default {
  name: 'popup-Dangkychitietkhuyenmai',
  provide: {
    grid: [Sort, Filter, Resize, Group, Freeze, CommandColumn, ExcelExport, Edit, Aggregate, Toolbar, Page]
  },
  props: ['dulieu'],
  watch: {},
  data() {
    return {
      vat_datcoc_tt: 0,
      ht_kmtt: false,
      m_dsTienCTKM: [],
      khuyenmai_id: 0,
      vchuyenmang: 0,
      _hdtb_id: 0,
      vdichvuvt_id: 0,
      m_dsThueBao_DichVu: 0,
      dsDC_HDTB: [],
      dsKM_HDTB: [],
      vtocdotn_id: 0,
      vmuccuoctn_id: 0,
      loaihinh_tb: 0,
      ts_kiemtra_dinhmuc: 0,
      ts_loc_ct_tratruoc_dvgt: 0,
      thamsomacdinh: { TINHCUOC_NGAY: 0 },
      isGiaHan: false,
      dt: [],
      cols: [
        { fieldName: 'dangky', headerText: 'Đăng ký', allowFiltering: true, allowSorting: true, type: 'boolean', editType: 'booleanedit', displayAsCheckBox: true, allowEditing: true, width: 40 },
        { fieldName: 'chitietkm_id', headerText: 'ID', allowFiltering: true, allowSorting: true, isPrimaryKey: true, visible: false },
        { fieldName: 'ten_ctkm', headerText: 'Chi tiết khuyến mại', allowFiltering: true, allowSorting: true, width: 300 },
        {
          headerText: '',
          allowFiltering: false,
          allowSorting: false,
          allowEditing: true,
          template: function() {
            return {
              template: Vue.component('columnTemplate', {
                template: `<span class="fa fa-plus" style="color:darkgreen;" v-on:click="doSomething()"></span>`,
                data: function() {
                  return {
                    data: {}
                  }
                },
                computed: {
                  image: function() {
                    return './' + this.data.EmployeeID + '.png'
                  },
                  altImage: function() {
                    return this.data.EmployeeID
                  }
                },
                methods: {
                  doSomething: function() {
                    if (this.data.loai_bddc == 3 || this.data.loai_bdkm == 3)
                      this.$bvModal
                        .msgBoxConfirm('Bạn muốn nhập tháng BĐĐC, tháng BĐKM = tháng hiện tại ?', {
                          title: 'Xác nhận hành động',
                          size: 'sm',
                          okTitle: 'Đồng ý',
                          cancelTitle: 'Hủy'
                        })
                        .then((v) => {
                          if (v) {
                            this.$eventHub.$emit('updatethang', this.data.chitietkm_id)
                          }
                        })
                  }
                }
              })
            }
          }
        },
        { fieldName: 'datcoc_csd', headerText: 'Tiền trả trước', format: 'N', editType: 'numericedit', textAlign: 'right', allowFiltering: true, allowSorting: true, allowEditing: true },
        { fieldName: 'tyle_sd', headerText: 'Tỷ lệ SD', format: 'N', editType: 'numericedit', textAlign: 'right', allowFiltering: true, allowSorting: true, allowEditing: true },
        { fieldName: 'tyle_tb', headerText: 'Tỷ lệ TB', format: 'N', editType: 'numericedit', textAlign: 'right', allowFiltering: true, allowSorting: true, allowEditing: true },
        { fieldName: 'tien_sd', headerText: 'Tiền SD', format: 'N', editType: 'numericedit', textAlign: 'right', allowFiltering: true, allowSorting: true, allowEditing: true },
        { fieldName: 'tien_tb', headerText: 'Tiền TB', format: 'N', editType: 'numericedit', textAlign: 'right', allowFiltering: true, allowSorting: true, allowEditing: true },
        { fieldName: 'cuoc_sd', headerText: 'Giảm cước SD', format: 'N', editType: 'numericedit', textAlign: 'right', allowFiltering: true, allowSorting: true, allowEditing: true },
        { fieldName: 'cuoc_tb', headerText: 'Giảm cước TB', format: 'N', editType: 'numericedit', textAlign: 'right', allowFiltering: true, allowSorting: true, allowEditing: true },
        { fieldName: 'tyle_km', headerText: 'Tỷ lệ KMLĐ', format: 'N', editType: 'numericedit', textAlign: 'right', allowFiltering: true, allowSorting: true, allowEditing: true },
        { fieldName: 'ngay_bddc', headerText: 'Ngày BĐĐC', allowFiltering: true, allowSorting: true, allowEditing: true, visible: true },
        { fieldName: 'ngay_ktdc', headerText: 'Ngày KTĐC', allowFiltering: true, allowSorting: true, allowEditing: false, visible: true },
        { fieldName: 'ngay_bd', headerText: 'Ngày BĐ', allowFiltering: true, allowSorting: true, allowEditing: false, visible: true },
        { fieldName: 'ngay_kt', headerText: 'Ngày KT', allowFiltering: true, allowSorting: true, allowEditing: false, visible: true },
        { fieldName: 'thang_bddc', headerText: 'Tháng BDDC', allowFiltering: true, allowSorting: true, allowEditing: true },
        { fieldName: 'thang_bdkm', headerText: 'Tháng BDKM', allowFiltering: true, allowSorting: true, allowEditing: false },
        { fieldName: 'thang_bdtg', headerText: 'Tháng BDTG', allowFiltering: true, allowSorting: true, allowEditing: true },
        { fieldName: 'nhom_km', headerText: 'Nhóm KM', allowFiltering: true, allowSorting: true, allowEditing: false },
        { fieldName: 'huong_dc', headerText: 'Số tháng hưởng ĐC', allowFiltering: true, allowSorting: true, allowEditing: false },
        { fieldName: 'tyle_vat', headerText: 'Tỷ lệ VAT', format: 'N', editType: 'numericedit', textAlign: 'right', allowFiltering: true, allowSorting: true, allowEditing: false }
      ],
      dgvDSKemTheo: {
        DataSource: [],
        cols: [
          { fieldName: 'MA_TB', headerText: 'Mã TB', allowFiltering: false, allowSorting: false },
          { fieldName: 'TEN_TB', headerText: 'Tên TB', allowFiltering: false, allowSorting: false },
          { fieldName: 'DIACHI_LD', headerText: 'Địa chỉ LĐ', allowFiltering: false, allowSorting: false },
          { fieldName: 'IP_CT', headerText: 'Giá tiền', allowFiltering: false, allowSorting: false },
          { fieldName: 'LOAIHINH_TB', headerText: 'Loại hình TB', allowFiltering: false, allowSorting: false }
        ],
        keycol: 'MA_TB'
      },
      dgvChiTietKM_new: {
        DataSource: [],
        cols: [
          { fieldName: 'DANGKY', headerText: 'Đăng ký', allowFiltering: true, allowSorting: true, type: 'checkbox', width: 40 },
          { fieldName: 'TEN_CTKM', headerText: 'Chi tiết khuyến mại', allowFiltering: false, allowSorting: false },
          { fieldName: 'DATCOC_CSD', headerText: 'Tiền trả trước', format: 'N', editType: 'numericedit', textAlign: 'right', allowFiltering: true, allowSorting: true, allowEditing: true },
          { fieldName: 'TYLE_SD', headerText: 'Tỷ lệ SD', format: 'N', editType: 'numericedit', textAlign: 'right', allowFiltering: true, allowSorting: true, allowEditing: true },
          { fieldName: 'TYLE_TB', headerText: 'Tỷ lệ TB', format: 'N', editType: 'numericedit', textAlign: 'right', allowFiltering: true, allowSorting: true, allowEditing: true },
          { fieldName: 'TIEN_SD', headerText: 'Tiền SD', format: 'N', editType: 'numericedit', textAlign: 'right', allowFiltering: true, allowSorting: true, allowEditing: true },
          { fieldName: 'TIEN_TB', headerText: 'Tiền TB', format: 'N', editType: 'numericedit', textAlign: 'right', allowFiltering: true, allowSorting: true, allowEditing: true },
          { fieldName: 'CUOC_SD', headerText: 'Giảm cước SD', format: 'N', editType: 'numericedit', textAlign: 'right', allowFiltering: true, allowSorting: true, allowEditing: true },
          { fieldName: 'CUOC_TB', headerText: 'Giảm cước TB', format: 'N', editType: 'numericedit', textAlign: 'right', allowFiltering: true, allowSorting: true, allowEditing: true },
          { fieldName: 'TYLE_KMLD', headerText: 'Tỷ lệ KMLĐ', format: 'N', editType: 'numericedit', textAlign: 'right', allowFiltering: true, allowSorting: true, allowEditing: true },
          { fieldName: 'NGAY_BDDC', headerText: 'Ngày BĐĐC', allowFiltering: true, allowSorting: true, allowEditing: true, visible: true },
          { fieldName: 'NGAY_KTDC', headerText: 'Ngày KTĐC', allowFiltering: true, allowSorting: true, allowEditing: false, visible: true },
          { fieldName: 'NGAY_BD', headerText: 'Ngày BĐ', allowFiltering: true, allowSorting: true, allowEditing: false, visible: true },
          { fieldName: 'NGAY_KT', headerText: 'Ngày KT', allowFiltering: true, allowSorting: true, allowEditing: false, visible: true },
          { fieldName: 'THANG_BDDC', headerText: 'Tháng BĐĐC', allowFiltering: true, allowSorting: true, allowEditing: true },
          { fieldName: 'THANG_BDKM', headerText: 'Tháng BĐKM', allowFiltering: true, allowSorting: true, allowEditing: true },
          { fieldName: 'THANG_BDTG', headerText: 'Tháng BĐTG', allowFiltering: true, allowSorting: true, allowEditing: true },
          { fieldName: 'NHOM_KM', headerText: 'Nhóm KM', allowFiltering: true, allowSorting: true, allowEditing: true }
        ],
        keycol: 'TEN_CTKM'
      },
      txtND: '',
      noidungkm: '',
      chkChon_Tung_CTKM: {
        ischecked: false,
        isvisible: false
      },
      vkhuyenmai_dc: 0,
      ts_cho_chon_1_ctkm: 0,
      selectOptions: {
        type: 'Single',
        persistSelection: true,
        checkboxMode: 'ResetOnRowClick'
      },
      kieu_km: 0,
      headerText: 'Đăng ký chi tiết khuyến mại',
      lineLabel1: 'Nội dung trả trước',
      lineLabel2: 'Chi tiết trả trước',
      lineLabel3: 'Thông tin chi tiết trả trước',
      i_th_datcoc: 0,
      i_tien_sd: 0,
      i_tien_tb: 0,
      i_cuoc_sd: 0,
      i_cuoc_tb: 0,
      i_tyle_sd: 0,
      i_tyle_tb: 0
    }
  },
  created() {
    this.$eventHub.$on('updatethang', (e) => {
      for (var item of this.dt)
        if (item.chitietkm_id == e) {
          if (item.loai_bddc == 3) item.thang_bddc = moment().format('YYYYMM')
          if (item.loai_bdkm == 3) item.thang_bdkm = moment().format('YYYYMM')
          this.$forceUpdate()
        }
    })
    //THANGDM1 them 2022-12-07 them de xu ly loading
    var numberOfAjaxCAllPending = 0

    // Add a request interceptor
    this.axios.interceptors.request.use(
      (config) => {
        numberOfAjaxCAllPending++
        // show loader
        this.loading(true)
        return config
      },
      (error) => {
        this.loading(false)
        return Promise.reject(error)
      }
    )

    // Add a response interceptor
    this.axios.interceptors.response.use(
      (response) => {
        numberOfAjaxCAllPending--

        this.loading(true)
        if (numberOfAjaxCAllPending == 0) {
          //hide loader
          this.loading(false)
        }
        return response
      },
      (error) => {
        numberOfAjaxCAllPending--
        if (numberOfAjaxCAllPending == 0) {
          //hide loader
          this.loading(false)
        }
        return Promise.reject(error)
      }
    )
  },
  async mounted() {
    // console.log(this.dulieu,this.dulieu.dsKM_HDTB)
    if (this.dulieu.khuyenmai_id) this.khuyenmai_id = this.dulieu.khuyenmai_id
    if (this.dulieu.vchuyenmang) this.vchuyenmang = this.dulieu.vchuyenmang
    if (this.dulieu._hdtb_id) this._hdtb_id = this.dulieu._hdtb_id
    if (this.dulieu.vdichvuvt_id) this.vdichvuvt_id = this.dulieu.vdichvuvt_id
    if (this.dulieu.m_dsThueBao_DichVu) this.m_dsThueBao_DichVu = this.dulieu.m_dsThueBao_DichVu
    if (this.dulieu.dsDC_HDTB) this.dsDC_HDTB = [...this.dulieu.dsDC_HDTB]
    if (this.dulieu.dsKM_HDTB) this.dsKM_HDTB = [...this.dulieu.dsKM_HDTB]
    if (this.dulieu.vtocdotn_id) this.vtocdotn_id = this.dulieu.vtocdotn_id
    if (this.dulieu.vmuccuoctn_id) this.vmuccuoctn_id = this.dulieu.vmuccuoctn_id
    if (this.dulieu.loaihinh_tb) this.loaihinh_tb = this.dulieu.loaihinh_tb
    if (this.dulieu.isGiaHan) this.isGiaHan = this.dulieu.isGiaHan
    if (this.dulieu.kieu_km) this.kieu_km = this.dulieu.kieu_km
    if (this.dulieu.vat_datcoc_tt) this.vat_datcoc_tt = this.dulieu.vat_datcoc_tt
    if (this.dulieu.ht_kmtt) this.ht_kmtt = this.dulieu.ht_kmtt
    if (this.dulieu.m_dsTienCTKM) this.m_dsTienCTKM = this.dulieu.m_dsTienCTKM
    console.log('ppdsDC_HDTB: ' + JSON.stringify(this.dulieu.dsDC_HDTB))
    if (this.khuyenmai_id > 0) {
      this.setup_thamso_macdinh()
      // this.sp_lay_ds_sudung_dvgt()
      await this.lay_chitiet_datcoc()
      
      // if (this.ts_loc_ct_tratruoc_dvgt == 1 && this.m_dsThueBao_DichVu) {
      //   var ds = [...this.m_dsThueBao_DichVu]
      //   var t = {}
      //   if (isGiaHan) t = ds.filter((x) => x.DANGKY == 1 || x.SUA == 1 || (x.SUDUNG == 'Su dung' && x.HUY == 0))
      //   else t = ds.filter((x) => x.DANGKY == 1)
      //   var dt2 = []
      //   if (t.length > 0) dt2 = [...t]
      //   var ds_dvgt = ''
      //   if (dt2.length > 0) ds_dvgt = dt2.map((x) => x['dichvugt_id']).join(',')
      //   else ds_dvgt = '-1'
      //   var pdata = {
      //     chuyenmang: this.vchuyenmang,
      //     ds_dvgt_id: ds_dvgt,
      //     hdtb_id: this._hdtb_id ? this._hdtb_id : 0,
      //     khuyenmai_id: this.khuyenmai_id,
      //     loaitb_id: this.loaihinh_tb,
      //     muccuoc_id: this.vmuccuoctn_id,
      //     tocdo_id: this.vtocdotn_id
      //   }
      //   var rs = await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/lay_chitiet_km_hdtb_dvgt', pdata)
      //   this.dt = rs.data
      // } else {
      //   var rs = null
      //   var pdata = {
      //     chuyenmang: this.vchuyenmang,
      //     hdtb_id: this._hdtb_id ? this._hdtb_id : 0,
      //     khuyenmai_id: this.khuyenmai_id,
      //     loaitb_id: this.loaihinh_tb,
      //     muccuoc_id: this.vmuccuoctn_id,
      //     tocdo_id: this.vtocdotn_id,
      //     loai_km: this.kieu_km
      //   }
      //   if (this.vkhuyenmai_dc == 1) rs = await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/lay_chitiet_km_hdtb_v4', pdata)
      //   else rs = await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/lay_chitiet_km_hdtb', pdata)
      //   //rs = await this.$root.context.post( "/web-hopdong/thaydoithongtinkhuyenmai/lay_chitiet_km_hdtb", pdata);
      //   this.dt = rs.data
      // }
      if (this.vkhuyenmai_dc == 1 && this.ts_cho_chon_1_ctkm == 1) {
        this.chkChon_Tung_CTKM.isvisible = true
      }
      if (this.kieu_km == 0) {
        this.headerText = 'Đăng ký chi tiết khuyến mại'
        //xtraTabPage1.Text = "Chi tiết trả trước";
        this.lineLabel3 = 'Thông tin chi tiết khuyến mại'
        this.lineLabel2 = 'Chi tiết khuyến mại'
        this.lineLabel1 = 'Nội dung khuyến mại'
        this.cols.find((x) => x.fieldName == 'thang_bdkm').allowEditing = true
        if (!this.dsKM_HDTB || this.dsKM_HDTB.length <= 0) {
          for (var item of this.dt) {
            if (item['hienthi_md']) item['dangky'] = 1
            item['ten_ctkm'] = item['chitietkm_id'] + ' - ' + item['ten_ctkm']
            item['ngay_bd'] = item['ngay_bd'] && (item['ngay_bd'] + '').indexOf('T') > 0 ? moment(item['ngay_bd']).format('DD/MM/YYYY') : item['ngay_bd']
            item['ngay_bddc'] = item['ngay_bddc'] && (item['ngay_bddc'] + '').indexOf('T') > 0 ? moment(item['ngay_bddc']).format('DD/MM/YYYY') : item['ngay_bddc'] //item["ngay_bddc"];
            item['ngay_kt'] = item['ngay_kt'] && (item['ngay_kt'] + '').indexOf('T') > 0 ? moment(item['ngay_kt']).format('DD/MM/YYYY') : item['ngay_kt'] //item["ngay_kt"];
            item['ngay_ktdc'] = item['ngay_ktdc'] && (item['ngay_ktdc'] + '').indexOf('T') > 0 ? moment(item['ngay_ktdc']).format('DD/MM/YYYY') : item['ngay_ktdc'] //item["ngay_ktdc"];
          }
        } else {
          for (var item1 of this.dt) {
            var index = this.dsKM_HDTB.findIndex(e => item1['chitietkm_id'] == e['chitietkm_id'])
            if (index > -1) {
              var item2 = this.dsKM_HDTB[index]
              item1['dangky'] = 1
              item1['thang_bdkm'] = item2['thang_bd']
              item1['thang_bddc'] = item2['thang_bddc']
              item1['thang_bdtg'] = item2['thang_bdtg']
              item1['tyle_kmld'] = item2['tyle_kmld']
              item1['tyle_sd'] = item2['tyle_sd']
              item1['tyle_tb'] = item2['tyle_tb']
              item1['tien_sd'] = item2['tien_sd']
              item1['tien_tb'] = item2['tien_tb']
              item1['cuoc_sd'] = item2['cuoc_sd']
              item1['cuoc_tb'] = item2['cuoc_tb']
              item1['datcoc_csd'] = item2['datcoc_csd']
              item1['tien_td'] = item2['tien_td']
            }
            item1['ten_ctkm'] = item1['chitietkm_id'] + ' - ' + item1['ten_ctkm']
            if (item1['ngay_bd']) {
              if (moment(item1['ngay_bd'],'DD/MM/YYYY').isValid()) {
                item1['ngay_bd'] = item1['ngay_bd']
              } else {
                item1['ngay_bd'] = moment(item1['ngay_bd']).format('DD/MM/YYYY')
              } 
            }

            if (item1['ngay_bddc']) {
              if (moment(item1['ngay_bddc'],'DD/MM/YYYY').isValid()) {
                item1['ngay_bddc'] = item1['ngay_bddc']
              } else {
                item1['ngay_bddc'] = moment(item1['ngay_bddc']).format('DD/MM/YYYY')
              } 
            }

            if (item1['ngay_kt']) {
              if (moment(item1['ngay_kt'],'DD/MM/YYYY').isValid()) {
                item1['ngay_kt'] = item1['ngay_kt']
              } else {
                item1['ngay_kt'] = moment(item1['ngay_kt']).format('DD/MM/YYYY')
              }
            }

            if (item1['ngay_ktdc']) {
              if (moment(item1['ngay_ktdc'],'DD/MM/YYYY').isValid()) {
                item1['ngay_ktdc'] = item1['ngay_ktdc']
              } else {
                item1['ngay_ktdc'] = moment(item1['ngay_ktdc']).format('DD/MM/YYYY')
              }
            }
          }
        }          
      } else if (this.kieu_km == 1) {
        this.headerText = 'Đăng ký chi tiết trả trước'
        //xtraTabPage1.Text = "Chi tiết trả trước";
        this.lineLabel3 = 'Thông tin chi tiết trả trước'
        this.lineLabel2 = 'Chi tiết trả trước'
        this.lineLabel1 = 'Nội dung trả trước'
        this.cols.find((x) => x.fieldName == 'thang_bdkm').allowEditing = false
        if (!this.dsDC_HDTB || this.dsDC_HDTB.length <= 0) {
          for (var item of this.dt) {
            if (item['hienthi_md']) item['dangky'] = 1
            item['ten_ctkm'] = item['chitietkm_id'] + ' - ' + item['ten_ctkm']
            item['ngay_bd'] = item['ngay_bd'] && (item['ngay_bd'] + '').indexOf('T') > 0 ? moment(item['ngay_bd']).format('DD/MM/YYYY') : item['ngay_bd']
            item['ngay_bddc'] = item['ngay_bddc'] && (item['ngay_bddc'] + '').indexOf('T') > 0 ? moment(item['ngay_bddc']).format('DD/MM/YYYY') : item['ngay_bddc'] //item["ngay_bddc"];
            item['ngay_kt'] = item['ngay_kt'] && (item['ngay_kt'] + '').indexOf('T') > 0 ? moment(item['ngay_kt']).format('DD/MM/YYYY') : item['ngay_kt'] //item["ngay_kt"];
            item['ngay_ktdc'] = item['ngay_ktdc'] && (item['ngay_ktdc'] + '').indexOf('T') > 0 ? moment(item['ngay_ktdc']).format('DD/MM/YYYY') : item['ngay_ktdc'] //item["ngay_ktdc"];
          }
        } else {
          for (var item1 of this.dt) {
            var index = this.dsDC_HDTB.findIndex(e => item1['chitietkm_id'] == e['chitietkm_id'])
            if (index > -1) {
              var item2 = this.dsDC_HDTB[index]
              item1['dangky'] = 1
              item1['thang_bdkm'] = item2['thang_bd']
              item1['thang_bddc'] = item2['thang_bddc']
              item1['thang_bdtg'] = item2['thang_bdtg']
              item1['tyle_kmld'] = item2['tyle_kmld']
              item1['tyle_sd'] = item2['tyle_sd']
              item1['tyle_tb'] = item2['tyle_tb']
              item1['tien_sd'] = item2['tien_sd']
              item1['tien_tb'] = item2['tien_tb']
              item1['cuoc_sd'] = item2['cuoc_sd']
              item1['cuoc_tb'] = item2['cuoc_tb']
              item1['datcoc_csd'] = item2['datcoc_csd']
              item1['tien_td'] = item2['tien_td']

              item1['ngay_bd'] = item2['ngay_bd'] && (item2['ngay_bd'] + '').indexOf('T') > 0 ? moment(item2['ngay_bd']).format('DD/MM/YYYY') : item2['ngay_bd']
              item1['ngay_bddc'] = item2['ngay_bddc'] && (item2['ngay_bddc'] + '').indexOf('T') > 0 ? moment(item2['ngay_bddc']).format('DD/MM/YYYY') : item2['ngay_bddc'] //item2["ngay_bddc"];
              item1['ngay_kt'] = item2['ngay_kt'] && (item2['ngay_kt'] + '').indexOf('T') > 0 ? moment(item2['ngay_kt']).format('DD/MM/YYYY') : item2['ngay_kt'] //item2["ngay_kt"];
              item1['ngay_ktdc'] = item2['ngay_ktdc'] && (item2['ngay_ktdc'] + '').indexOf('T') > 0 ? moment(item2['ngay_ktdc']).format('DD/MM/YYYY') : item2['ngay_ktdc'] //item2["ngay_ktdc"];
            }
            item1['ten_ctkm'] = item1['chitietkm_id'] + ' - ' + item1['ten_ctkm']
            if (item1['ngay_bd']) {
              if (moment(item1['ngay_bd'],'DD/MM/YYYY').isValid()) {
                item1['ngay_bd'] = item1['ngay_bd']
              } else {
                item1['ngay_bd'] = moment(item1['ngay_bd']).format('DD/MM/YYYY')
              } 
            }

            if (item1['ngay_bddc']) {
              if (moment(item1['ngay_bddc'],'DD/MM/YYYY').isValid()) {
                item1['ngay_bddc'] = item1['ngay_bddc']
              } else {
                item1['ngay_bddc'] = moment(item1['ngay_bddc']).format('DD/MM/YYYY')
              } 
            }

            if (item1['ngay_kt']) {
              if (moment(item1['ngay_kt'],'DD/MM/YYYY').isValid()) {
                item1['ngay_kt'] = item1['ngay_kt']
              } else {
                item1['ngay_kt'] = moment(item1['ngay_kt']).format('DD/MM/YYYY')
              }
            }

            if (item1['ngay_ktdc']) {
              if (moment(item1['ngay_ktdc'],'DD/MM/YYYY').isValid()) {
                item1['ngay_ktdc'] = item1['ngay_ktdc']
              } else {
                item1['ngay_ktdc'] = moment(item1['ngay_ktdc']).format('DD/MM/YYYY')
              }
            }
          }
        }          
      }
      this.HienThi_TBKem()
    } else {
      this.$toast.warning('Hãy chọn đợt khuyến mãi!')
      this.terminateForm()
    }
    // gridView2.FocusedRowHandle = 0;
    // gridView2.SelectRow(0);

    // if (dgrChiTietKM.RowCount > 0)
    // {
    //     for (int i = 0; i < dgrChiTietKM.RowCount; i++)
    //     {
    //         kiemtra(i);
    //     }
    // }
  },
  computed: {},
  methods: {
    setup_thamso_macdinh: async function () {
      await API.post_lay_thamso_md(this.axios, { kieu_id: 0 }).then((response) => {
        if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
          this.thamsomacdinh = response.data.data.reduce(function(result, item, index, array) {
            result[item['ma_ts']] = item['ten_ts'] //a, b, c
            return result
          }, {}) //.map(x => ({ [x['ma_ts']]:x['ten_ts'] }));
          this.TUDONG_SINH_SOAO = this.thamsomacdinh['TUDONG_SINH_SOAO'] ? true : false
          if (this.thamsomacdinh['DINHMUC_KM'] && this.thamsomacdinh['DINHMUC_KM'] == 1) this.ts_kiemtra_dinhmuc = 1
          if (this.thamsomacdinh['LOC_CT_TRATRUOC_DVGT'] && this.thamsomacdinh['LOC_CT_TRATRUOC_DVGT'] == 1) this.ts_loc_ct_tratruoc_dvgt = 1
          if (this.thamsomacdinh['CHON_TUNG_CTKM'] && this.thamsomacdinh['CHON_TUNG_CTKM'].length == 1) this.ts_cho_chon_1_ctkm = 1
          if (!this.thamsomacdinh['TINHCUOC_NGAY']) this.thamsomacdinh['TINHCUOC_NGAY'] = 0
          //debug test
          // this.thamsomacdinh['TINHCUOC_NGAY'] = 1;
          if (this.kieu_km == 0 || this.thamsomacdinh['TINHCUOC_NGAY'] == 0) {
            this.dgvChiTietKM_new.cols.find((x) => x.fieldName == 'NGAY_BD').visible = false
            this.dgvChiTietKM_new.cols.find((x) => x.fieldName == 'NGAY_BDDC').visible = false
            this.dgvChiTietKM_new.cols.find((x) => x.fieldName == 'NGAY_KT').visible = false
            this.dgvChiTietKM_new.cols.find((x) => x.fieldName == 'NGAY_KTDC').visible = false

            this.cols.find((x) => x.fieldName == 'ngay_bd').visible = false
            this.cols.find((x) => x.fieldName == 'ngay_bddc').visible = false
            this.cols.find((x) => x.fieldName == 'ngay_kt').visible = false
            this.cols.find((x) => x.fieldName == 'ngay_ktdc').visible = false
          }
        }
      })
    },
    sp_lay_ds_sudung_dvgt: async function () {
      var input = {
        in_thuebao_id: this.thuebao_id,
      };
      var response = await this.$root.context.post("/web-quantri/thongsone/sp_lay_ds_sudung_dvgt", input);
      if (response && response.error_code && response.error_code == "BSS-00000000") {
        this.m_dsThueBao_DichVu = response.data;
      } else if (response && response.error_code && response.error_code == "BSS-00000204") {
        this.m_dsThueBao_DichVu = [];
      } else {
        this.$toast.error("" + response.message_detail);
        this.m_dsThueBao_DichVu = [];
      }
    },
    lay_chitiet_datcoc: async function () {
      var pdata = {
        khuyenmai_id: this.khuyenmai_id,
        tocdo_id: this.vtocdotn_id,
        muccuoc_id: this.vmuccuoctn_id,
        loaitb_id: this.loaihinh_tb,
        hdtb_id: this._hdtb_id ? this._hdtb_id : 0,
      };
      var response = await this.$root.context.post("/web-hopdong/thuebao_datcoc_thunghiem/lay_chitiet_datcoc", pdata);
      if (response && response.error_code && response.error_code == "BSS-00000000") {
        this.dt = response.data;
      } else if (response && response.error_code && response.error_code == "BSS-00000204") {
        this.dt = [];
      } else {
        this.$toast.error(response.message_detail);
        this.dt = [];
      }
    },
    queryCellInfoEvent(e) {
      // if(this.kieu_km==0) { // bo dieu kien this.kieu_km==0 &&  ngay 2022-12-20 thangdm1
      // console.log('queryCellInfoEvent',this.dulieu.dichvu_vt,e.data.loai_bdkm);
      if (e.column.field == 'thang_bdkm' && e.data.loai_bdkm == 3 && (this.kieu_km == 0 || !this.dulieu.dichvu_vt || (this.dulieu.dichvu_vt >= 13 && this.dulieu.dichvu_vt <= 16))) e.cell.classList.add('required')
      if (e.column.field == 'thang_bdtg' && e.data.kieu_bdtg == 3) e.cell.classList.add('required')
      if (e.column.field == 'thang_bddc' && e.data.loai_bddc == 3) e.cell.classList.add('required')
      if (e.column.field == 'tyle_km' && e.data.kieu_kmld == 1) e.cell.classList.add('required')
      if (e.column.field == 'datcoc_csd' && e.data.kieu_tratruoc == 1) e.cell.classList.add('required')
      if (e.data.kieu_km == 1 && (e.column.field == 'tyle_sd' || e.column.field == 'tyle_tb' || e.column.field == 'tien_sd' || e.column.field == 'tien_tb' || e.column.field == 'cuoc_sd' || e.column.field == 'cuoc_tb')) e.cell.classList.add('required')
      // }
    },
    actionComplete(args) {
      // console.log(args, args.requestType);
      if (args.requestType === 'save') {
        //args.data du lieu moi
        //args.rowData du lieu cu        
        if (args.rowData['nhom_km'] != null)
          for (var item of this.dt)
            if (item['nhom_km'] == args.rowData['nhom_km'] && item['chitietkm_id'] != args.rowData['chitietkm_id']) item['dangky'] = args.data['dangky']
            else if (item['chitietkm_id'] != args.rowData['chitietkm_id'] && args.data['dangky'] == 1) item['dangky'] = 1 - args.data['dangky'] * 1
        this.$forceUpdate()
        if (this.kieu_km == 1 && this.thamsomacdinh['TINHCUOC_NGAY'] != 0 && args.data['ngay_bddc'] && args.data['ngay_bddc'] != args.rowData['ngay_bddc']) {
          if (!(args.data['ngay_bddc'] + '').match(/^\d{2}\/\d{2}\/\d{4}$/g)) {
            this.$toast.error('Ngày BDDC phải nhập định dạng DD/MM/YYYY !')
            return
          }
          if (!args.data['thang_bddc']) args.data['thang_bddc'] = moment(args.data['ngay_bddc'], 'DD/MM/YYYY').format('YYYYMM')
          else if (args.data['thang_bddc'] && moment(args.data['ngay_bddc'], 'DD/MM/YYYY').format('YYYYMM') != args.data['thang_bddc']) {
            this.$toast.error('Ngày bắt đầu đặt cọc phải nằm trong tháng đặt cọc!')
            return
          }
          args.data['ngay_bd'] = args.data['ngay_bddc']

          var ithang_huongdc = args.data['huong_dc']
          args.data['ngay_ktdc'] = moment(args.data['ngay_bddc'], 'DD/MM/YYYY')
            .add(ithang_huongdc ? ithang_huongdc : 1, 'M')
            .subtract(1, 'd')
            .format('DD/MM/YYYY')
          var ithang_huongkm = args.data['huong_dc']
          args.data['ngay_kt'] = moment(args.data['ngay_bddc'], 'DD/MM/YYYY')
            .add(ithang_huongkm ? ithang_huongkm : 1, 'M')
            .subtract(1, 'd')
            .format('DD/MM/YYYY')
          for (var item of this.dt)
            if (item.chitietkm_id == args.data['chitietkm_id']) {
              item['ngay_bddc'] = args.data['ngay_bddc']
              item['ngay_ktdc'] = args.data['ngay_ktdc']
              item['ngay_bd'] = args.data['ngay_bd']
              item['ngay_kt'] = args.data['ngay_kt']
              item['thang_bddc'] = args.data['thang_bddc']
              break
            }
        }
        this.$forceUpdate()
        if (args.data['thang_bddc']) {
          if (!(args.data['thang_bddc'] + '').match(/^\d{6}$/g)) {
            this.$toast.error('Tháng BDDC phải nhập định dạng YYYYMM, và chỉ là số!')
            return
          }
          if (!args.data['thang_bdkm']) args.data['thang_bdkm'] = args.data['thang_bddc']
          for (var item of this.dt)
            if (item.chitietkm_id == args.data['chitietkm_id']) {
              item['thang_bdkm'] = args.data['thang_bdkm']
              item['thang_bddc'] = args.data['thang_bddc']
              break
            }
        }
        this.$forceUpdate()
      }
      if (args.requestType === 'beginEdit') {
        // bo dieu kien this.kieu_km==0 &&  ngay 2022-12-20 thangdm1
        // focus the column

        if (args.rowData['loai_bdkm'] == 3) {
          // args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute("id") + 'thang_bdkm'].readOnly = false;
          args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute('id') + 'thang_bdkm'].onkeypress = (c) => {
            if ('0123456789'.indexOf(c.key) < 0) {
              this.$toast.error('Phải nhập định dạng YYYYMM, và chỉ là số!')
              return false
            }
          }
          args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute('id') + 'thang_bdkm'].onblur = (c) => {
            if (c.target.value && !(c.target.value + '').match(/^\d{6}$/g)) {
              this.$toast.error('Phải nhập định dạng YYYYMM, và chỉ là số!')
              return false
            }
          }
        } else if (args.rowData['loai_bdkm'] == 1) {
          args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute('id') + 'thang_bdkm'].readOnly = false
          let ithanghuongkm = args.rowData['huong_km'] ? args.rowData['huong_km'] : 0 //args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute("id") + 'huong_km']?args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute("id") + 'huong_km'].value:null;
          if ((!args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute('id') + 'thang_bdkm'].value || args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute('id') + 'thang_bdkm'].value == 0) && ithanghuongkm && ithanghuongkm > 0)
            args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute('id') + 'thang_bdkm'].value = moment().format('YYYYMM')
          args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute('id') + 'thang_bdkm'].readOnly = true
        } else args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute('id') + 'thang_bdkm'].readOnly = true

        if (args.rowData['kieu_bdtg'] != 3) args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute('id') + 'thang_bdtg'].readOnly = true
        else args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute('id') + 'thang_bdtg'].readOnly = false

        if (args.rowData['loai_bddc'] == 3) {
          args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute('id') + 'thang_bddc'].readOnly = false
          args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute('id') + 'thang_bddc'].onkeypress = (c) => {
            if ('0123456789'.indexOf(c.key) < 0) {
              this.$toast.error('Phải nhập định dạng YYYYMM, và chỉ là số!')
              return false
            }
          }
          args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute('id') + 'thang_bddc'].onblur = (c) => {
            if (c.target.value && !(c.target.value + '').match(/^\d{6}$/g)) {
              this.$toast.error('Phải nhập định dạng YYYYMM, và chỉ là số!')
              return false
            }
            args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute('id') + 'thang_bdkm'].value = c.target.value

            // if(c.target.value) {
            //     var ithang_huongdc = args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute("id") + 'huong_dc'].value;
            //     args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute("id") + 'thang_ktdc'] = moment(c.target.value,'YYYYMM').add((ithang_huongdc&& ithang_huongdc>0?ithang_huongdc:1) - 1, 'M').format('YYYYMM');
            //     var ithang_huongkm = args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute("id") + 'huong_km'].value;
            //     args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute("id") + 'thang_ktkm'] = moment(c.target.value,'YYYYMM').add((ithang_huongkm&& ithang_huongkm>0?ithang_huongkm:1) - 1, 'M').format('YYYYMM');
            // }
          }
        } else if (args.rowData['loai_bddc'] == 1) {
          args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute('id') + 'thang_bddc'].readOnly = true
          if (!args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute('id') + 'thang_bddc'].value || args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute('id') + 'thang_bddc'].value == 0) {
            args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute('id') + 'thang_bddc'].value = moment().format('YYYYMM')
            args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute('id') + 'thang_bdkm'].value = moment().format('YYYYMM')

            // var ithang_huongdc = args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute("id") + 'huong_dc'].value;
            // args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute("id") + 'thang_ktdc'] = moment().add((ithang_huongdc&& ithang_huongdc>0?ithang_huongdc:1) - 1, 'M').format('YYYYMM');
            // var ithang_huongkm = args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute("id") + 'huong_km'].value;
            // args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute("id") + 'thang_ktkm'] = moment().add((ithang_huongkm&& ithang_huongkm>0?ithang_huongkm:1) - 1, 'M').format('YYYYMM');
          }
        } else args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute('id') + 'thang_bddc'].readOnly = true

        if (args.rowData['kieu_tratruoc'] != 1) this.makeReadOnly(args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute('id') + 'datcoc_csd'], true)
        //args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute('id') + 'datcoc_csd'].readOnly = true
        else this.makeReadOnly(args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute('id') + 'datcoc_csd'], false) //args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute('id') + 'datcoc_csd'].readOnly = false
        if (args.rowData['kieu_kmld'] != 1) {
          this.makeReadOnly(args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute('id') + 'tyle_km'], true)
        } else {
          this.makeReadOnly(args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute('id') + 'tyle_km'], false)
        }
        if (args.rowData['kieu_km'] != 1) {
          this.makeReadOnly(args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute('id') + 'tyle_sd'], true)
          this.makeReadOnly(args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute('id') + 'tyle_tb'], true)
          this.makeReadOnly(args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute('id') + 'tien_sd'], true)
          this.makeReadOnly(args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute('id') + 'tien_tb'], true)
          this.makeReadOnly(args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute('id') + 'cuoc_sd'], true)
          this.makeReadOnly(args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute('id') + 'cuoc_tb'], true)
        } else {
          this.makeReadOnly(args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute('id') + 'tyle_sd'], false)
          this.makeReadOnly(args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute('id') + 'tyle_tb'], false)
          this.makeReadOnly(args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute('id') + 'tien_sd'], false)
          this.makeReadOnly(args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute('id') + 'tien_tb'], false)
          this.makeReadOnly(args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute('id') + 'cuoc_sd'], false)
          this.makeReadOnly(args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute('id') + 'cuoc_tb'], false)
        }
        if (this.kieu_km == 1 && this.thamsomacdinh['TINHCUOC_NGAY'] != 0) {
          args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute('id') + 'ngay_bddc'].onkeypress = (c) => {
            if ('0123456789/'.indexOf(c.key) < 0) {
              this.$toast.error('Phải nhập định dạng DD/MM/YYYY !')
              return false
            }
          }
          args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute('id') + 'ngay_bddc'].onblur = (c) => {
            if (c.target.value && !(c.target.value + '').match(/^\d{2}\/\d{2}\/\d{4}$/g)) {
              this.$toast.error('Phải nhập định dạng DD/MM/YYYY !')
              return false
            }
            if (
              c.target.value &&
              args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute('id') + 'thang_bddc'].value &&
              args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute('id') + 'thang_bddc'].value != 0 &&
              moment(c.target.value, 'DD/MM/YYYY').format('YYYYMM') != args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute('id') + 'thang_bddc'].value
            ) {
              this.$toast.error('Ngày bắt đầu đặt cọc phải nằm trong tháng đặt cọc!')
              return false
            }
            args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute('id') + 'ngay_bd'].value = c.target.value

            if (c.target.value) {
              var ithang_huongdc = args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute('id') + 'huong_dc'].value
              args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute('id') + 'ngay_ktdc'].value = moment(c.target.value, 'DD/MM/YYYY')
                .add(ithang_huongdc ? ithang_huongdc : 1, 'M')
                .subtract(1, 'd')
                .format('DD/MM/YYYY')
              var ithang_huongkm = args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute('id') + 'huong_dc'].value
              args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute('id') + 'ngay_kt'].value = moment(c.target.value, 'DD/MM/YYYY')
                .add(ithang_huongkm ? ithang_huongkm : 1, 'M')
                .subtract(1, 'd')
                .format('DD/MM/YYYY')
            } else {
              args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute('id') + 'ngay_ktdc'].value = ''
              args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute('id') + 'ngay_kt'].value = ''
            }
          }
        }
        // if(item['loai_bdkm'] == 3)
        // e.form.elements[this.$refs.grid.ej2Instances.element.getAttribute("id") + fieldName].focus();
      }
      // if (args.requestType === "save") {
      //     for(var item of this.dt.list)
      //         if(item.chitietkm_id==args.data.chitietkm_id) {
      //             item.dangky=args.data.dangky?1:0;
      //             break;
      //         }
      //     this.$forceUpdate();
      // }
      // if (args.requestType === "refresh") { console.log(this.dt.list); }
    },
    makeReadOnly: function(el, readonly) {
      if (readonly) {
        el.readOnly = true
        el.parentElement.classList.add('disabled')
        el.parentElement.querySelectorAll('.e-input-group-icon').forEach((ele) => {
          ele.classList.add('d-none')
        })
      } else {
        el.readOnly = false
        el.parentElement.classList.remove('disabled')
        el.parentElement.querySelectorAll('.e-input-group-icon').forEach((ele) => {
          ele.classList.remove('d-none')
        })
      }
    },
    load: function() {
      this.$refs.grid.ej2Instances.element.addEventListener('mouseup', function(e) {
        var instance = this.ej2_instances[0]
        if (e.target.classList.contains('e-rowcell')) {
          if (instance.isEdit) {
            instance.endEdit()
            return
          }

          let index = parseInt(e.target.getAttribute('Index'))
          // this.noidungkm = this.dt[index].noidung;
          instance.selectRow(index)
          instance.startEdit()
        }
      })
    },
    GridBound(e) {
      this.$refs.grid.autoFitColumns()
    },
    closeForm() {
      this.$refs.grid.ej2Instances.vueInstance.endEdit()
      this.$emit('form-close', this.ds)
    },
    terminateForm() {
      this.$emit('form-close', null)
    },
    // rowChanged(item) {
    //     this.ds = item;
    // },
    khuyenmaiChanged(item) {
      this.noidungkm = item.rowData.noidung
    },
    async tsbtnCapNhan_Click() {
      //this.$refs.grid,
      this.$refs.grid.ej2Instances.endEdit()
      // this.$refs.grid.ej2Instances.vueInstance.endEdit()
      var tien_kmld = 0,
        tyle_kmld = 0,
        vat_kmld = 0,
        tien_datcoc = 0,
        tien_kmld_tt = 0,
        tyle_kmld_tt = 0,
        vat_kmld_tt = 0,
        tien_datcoc_tt = 0,
        i_tien_td = 0,
        ithang_huongdc = 0,
        ithang_huongkm = 0,
        ithang_tg = 0,
        dtiendatcoc_csd = 0,
        dtien_td = 0
      var tmp = null
      if (this.kieu_km == 0) {
        this.dsKM_HDTB = []
        for (var item of this.dt) {
          var dr = {}
          if (item['dangky'] == 1) {
            dr.KHUYENMAI_ID = this.khuyenmai_id
            dr.CHITIETKM_ID = item['chitietkm_id']
            dr.NHOM_DATCOC_ID = item['nhom_datcoc_id']
            dr.LOAI_BDDC = item['loai_bddc']
            dr.LOAI_BDKM = item['loai_bdkm']
            dr.KIEU_TRATRUOC = item['kieu_tratruoc']
            dr.TEN_CTKM = item['ten_ctkm']
            dr.KIEU_YC = 1
            ithang_huongdc = item['huong_dc'] //item["thang_huongdc"];
            ithang_huongkm = item['huong_km'] //item["thang_huongkm"];
            ithang_tg = item['sothang_tg']
            dtiendatcoc_csd = item['datcoc_csd']
            this.i_tien_sd = item['tien_sd']
            this.i_tien_tb = item['tien_tb']
            this.i_cuoc_sd = item['cuoc_sd']
            this.i_cuoc_tb = item['cuoc_tb']
            this.i_tyle_sd = item['tyle_sd'] * 1
            this.i_tyle_tb = item['tyle_tb'] * 1

            if (this.vkhuyenmai_dc == 1 && dtiendatcoc_csd > 0) {
              this.$toast.error('Thay đổi khuyến mại Đặt cọc. Bạn không được nhập tiền đặt cọc !')
              return
            }

            if (ithang_huongdc > 0) dtien_td = Math.round(dtiendatcoc_csd / ithang_huongdc)
            i_tien_td = dtien_td
            dr.THANG_HUONGDC = ithang_huongdc
            dr.THANG_HUONGKM = ithang_huongkm
            dr.SOTHANG_TG = ithang_tg
            dr.DATCOC_CSD = dtiendatcoc_csd
            dr.TIEN_TD = dtien_td
            dr.TIEN_SD = this.i_tien_sd
            dr.TIEN_TB = this.i_tien_tb
            dr.CUOC_SD = this.i_cuoc_sd
            dr.CUOC_TB = this.i_cuoc_tb
            dr.TYLE_SD = this.i_tyle_sd
            dr.TYLE_TB = this.i_tyle_tb
            //if (item["loai_bdkm"] == 3)
            if (item['loai_bdkm'] == 3) {
              if (item['ngay_bddc'] && !item['ngay_bddc'].match(/^\d{2}\/\d{2}\/\d{4}$/g)) {
                this.$toast.error('Bạn phải nhập Ngày BĐĐC theo định dạng DD/MM/YYYY')
                return
              }
              if (item['ngay_bddc']) {
                item['thang_bdkm'] = moment(item['ngay_bddc'], 'DD/MM/YYYY').format('YYYYMM') //Convert.ToDateTime(dgrChiTietKM.Rows[i].Cells["ngay_bddc"].Value.ToString()).ToString("yyyyMM");
              }
              if (item['ngay_bddc'] && !item['thang_bdkm']) {
                item['thang_bdkm'] = item['ngay_bddc']
              }
              // console.log(item);
              if (!item['thang_bdkm']) {
                this.$toast.error('Bạn phải nhập tháng bắt đầu khuyến mại')
                return
              }
              tmp = item['thang_bdkm'] + ''
              if (!tmp || !tmp.match(/^\d{6}$/g)) {
                this.$toast.error('Bạn phải nhập tháng bắt đầu khuyến mại theo định dạng YYYYMM')
                return
              }
              dr.THANG_BD = item['thang_bdkm']
              dr.THANG_KT = moment(tmp, 'YYYYMM')
                .add((ithang_huongkm && ithang_huongkm > 0 ? ithang_huongkm : 1) - 1, 'M')
                .format('YYYYMM')
            }
            if (item['kieu_bdtg'] == 3) {
              if (!item['thang_bdtg']) {
                this.$toast.error('Bạn phải nhập tháng bắt đầu trả góp')
                return
              }
              tmp = item['thang_bdtg'] + ''
              if (!tmp || !tmp.match(/^\d{6}$/g)) {
                this.$toast.error('Bạn phải nhập tháng bắt đầu trả góp theo định dạng YYYYMM')
                return
              }
              dr.THANG_BDTG = item['thang_bdtg']
              dr.THANG_KTTG = moment(tmp, 'YYYYMM')
                .add((ithang_tg && ithang_tg > 0 ? ithang_tg : 1) - 1, 'M')
                .format('YYYYMM')
            }

            // if (item["loai_bddc"] == 3)
            if (item['loai_bddc'] == 3) {
              if (item['ngay_bddc'] && !item['ngay_bddc'].match(/^\d{2}\/\d{2}\/\d{4}$/g)) {
                this.$toast.error('Bạn phải nhập Ngày BĐĐC theo định dạng DD/MM/YYYY')
                return
              }
              if (item['ngay_bddc']) {
                item['thang_bddc'] = moment(item['ngay_bddc'], 'DD/MM/YYYY').format('YYYYMM') //Convert.ToDateTime(dgrChiTietKM.Rows[i].Cells["ngay_bddc"].Value.ToString()).ToString("yyyyMM");
              }
              if (!item['thang_bddc']) {
                this.$toast.error('Bạn phải nhập tháng bắt đầu trả trước')
                return
              }
              tmp = item['thang_bddc'] + ''
              if (!tmp || !tmp.match(/^\d{6}$/g)) {
                this.$toast.error('Bạn phải nhập tháng bắt đầu đặt cọc theo định dạng YYYYMM')
                return
              }
              dr.THANG_BDDC = item['thang_bddc']
              dr.THANG_KTDC = moment(tmp, 'YYYYMM')
                .add((ithang_huongdc && ithang_huongdc > 0 ? ithang_huongdc : 1) - 1, 'M')
                .format('YYYYMM')
            }
            //longdx: bổ sung phần cập nhật các tỷ lệ
            if (item['tyle_sd'] == 'undefined' || item['tyle_sd'] == null) {
              this.$toast.error('Tỷ lệ sử dụng không được để trống!')
              return
            } else item['tyle_sd'] = item['tyle_sd'] * 1
            if (item['tyle_tb'] == 'undefined' || item['tyle_tb'] == null) {
              this.$toast.error('Tỷ lệ thuê bao không được để trống!')
              return
            } else item['tyle_tb'] = item['tyle_tb'] * 1
            if (item['tyle_kmld'] == 'undefined' || item['tyle_kmld'] == null) {
              this.$toast.error('Tỷ lệ khuyến mại lắp đặt không được để trống!')
              return
            } else item['tyle_kmld'] = item['tyle_kmld'] * 1

            //Với khuyến mại cho sửa khuyến mại cước
            if (item['kieu_km'] == 1) {
              if ((this.vdichvuvt_id == DichVuVienThong.ANTOAN_BAOMAT_TT || this.vdichvuvt_id == DichVuVienThong.DICHVU_CNTT || this.vdichvuvt_id == DichVuVienThong.TRUNGTAM_DULIEU || this.vdichvuvt_id == DichVuVienThong.HOINGHI_TRUYENHINH) && item['nhom_km'] != '') {
                // LongDX - Hoangpkn : 19/11/2019
                //Đối với riêng các dịch vụ CNTT của chị An
                //Và đối thì các khuyến mại tự nhập mà có nhóm
                //Em sửa lại cho phép để =0 hết nhé
              } else {
                if (this.i_tien_sd + this.i_tien_tb + this.i_cuoc_sd + this.i_cuoc_tb + this.i_tyle_sd + this.i_tyle_tb == 0) {
                  this.$toast.error('Bạn chưa nhập thông tin khuyến mại!')
                  return
                }
              }
            }
            if (this.i_tyle_sd > 100 || this.i_tyle_tb > 100) {
              this.$toast.error('Tỷ lệ không được vượt quá 100!')
              return
            }

            if ((this.i_tien_sd + this.i_tien_tb > 0 && this.i_cuoc_sd + this.i_cuoc_tb > 0) || (this.i_tien_sd + this.i_tien_tb > 0 && this.i_tyle_sd + this.i_tyle_tb > 0) || (this.i_cuoc_sd + this.i_cuoc_tb > 0 && this.i_tyle_sd + this.i_tyle_tb > 0)) {
              this.$toast.error('Bạn không được nhập đồng thời nhiều hình thức khuyến mại!')
              return
            }

            //Với khuyến mại cho sửa khuyến mại cước
            if (item['kieu_km'] == 1) {
              if (this.ts_kiemtra_dinhmuc == 1) {
                if (!(await this.Kiemtra_DinhMuc(item['chitietkm_id'], this.i_tien_sd, this.i_tien_tb, this.i_cuoc_sd, this.i_cuoc_tb, this.i_tyle_sd, this.i_tyle_tb))) return
              }
            }

            dr.TYLE_KMLD = item['tyle_kmld'] * 1
            dr.NHOM_KM = item['nhom_km']
            dr.TYLE_VAT = item['tyle_vat']

            //dr.TYLE_SD = Convert.ToDecimal(item["tyle_sd"]);
            //dr.TYLE_TB = Convert.ToDecimal(item["tyle_tb"]);
            //fix riêng cho HCM
            // FIXME doan nay tam tat, kiem tra lai sau
            // if (DatabaseConstants.DB2 == "CSS_HCM")
            // {
            //     if (dr.TYLE_SD > 50 && item["kieu_kmcuoc"] == "1")
            //     {
            //         this.$toast.error("Tỷ lệ sử dụng không được lớn hơn 50%!");
            //         return;
            //     }
            // }
            this.dsKM_HDTB.push(dr)

            //Lấy tiền khuyến mại
            if (tien_kmld < item['tien_kmld']) tien_kmld = item['tien_kmld']
            if (vat_kmld < item['vat_kmld']) vat_kmld = item['vat_kmld']
            if (tyle_kmld < item['tyle_kmld']) tyle_kmld = item['tyle_kmld']

            //longdx: cong don tien dat coc
            tien_datcoc += item['datcoc_csd']
          }
        }
        var retData = {
          dsKM_HDTB: this.dsKM_HDTB,
          tien_kmld: tien_kmld,
          vat_kmld: vat_kmld,
          tyle_kmld: tyle_kmld,
          tien_datcoc: tien_datcoc
        }
        // console.log(retData);
        this.$emit('form-close', retData)
      } else {
        this.dsDC_HDTB = []
        for (var item of this.dt) {
          var dr = {}
          if (item['dangky'] == 1) {
            dr.KHUYENMAI_ID = this.khuyenmai_id
            dr.CHITIETKM_ID = item['chitietkm_id']
            dr.NHOM_DATCOC_ID = item['nhom_datcoc_id']
            dr.LOAI_BDDC = item['loai_bddc']
            dr.LOAI_BDKM = item['loai_bdkm']
            dr.KIEU_TRATRUOC = item['kieu_tratruoc']
            dr.TEN_CTKM = item['ten_ctkm']
            dr.KIEU_YC = 1
            ithang_huongdc = item['huong_dc'] //item["thang_huongdc"];
            ithang_huongkm = item['huong_km'] //item["thang_huongkm"];
            dtiendatcoc_csd = item['datcoc_csd']
            this.i_tien_sd = item['tien_sd']
            this.i_tien_tb = item['tien_tb']
            this.i_cuoc_sd = item['cuoc_sd']
            this.i_cuoc_tb = item['cuoc_tb']
            this.i_tyle_sd = item['tyle_sd'] * 1
            this.i_tyle_tb = item['tyle_tb'] * 1

            if (ithang_huongdc > 0) dtien_td = Math.round(dtiendatcoc_csd / ithang_huongdc)
            i_tien_td = dtien_td
            this.i_th_datcoc = ithang_huongdc
            dr.THANG_HUONGDC = ithang_huongdc
            dr.THANG_HUONGKM = ithang_huongkm
            dr.SOTHANG_TG = ithang_tg
            dr.DATCOC_CSD = dtiendatcoc_csd
            dr.TIEN_TD = dtien_td
            dr.TIEN_SD = this.i_tien_sd
            dr.TIEN_TB = this.i_tien_tb
            dr.CUOC_SD = this.i_cuoc_sd
            dr.CUOC_TB = this.i_cuoc_tb
            dr.TYLE_SD = this.i_tyle_sd
            dr.TYLE_TB = this.i_tyle_tb
            if (item['loai_bdkm'] == 3) {
              if (item['ngay_bddc'] && !item['ngay_bddc'].match(/^\d{2}\/\d{2}\/\d{4}$/g)) {
                this.$toast.error('Bạn phải nhập Ngày BĐĐC theo định dạng DD/MM/YYYY')
                return
              }
              if (item['ngay_bddc']) {
                item['thang_bdkm'] = moment(item['ngay_bddc'], 'DD/MM/YYYY').format('YYYYMM') //Convert.ToDateTime(dgrChiTietKM.Rows[i].Cells["ngay_bddc"].Value.ToString()).ToString("yyyyMM");
              } else {
                this.$toast.error('Bạn phải nhập ngày bắt đầu trả trước')
                return
              }
              if (!item['thang_bdkm']) {
                this.$toast.error('Bạn phải nhập tháng bắt đầu khuyến mại')
                return
              }
              tmp = item['thang_bdkm'] + ''
              if (!tmp || !tmp.match(/^\d{6}$/g)) {
                this.$toast.error('Bạn phải nhập tháng bắt đầu khuyến mại theo định dạng YYYYMM')
                return
              }
              dr.THANG_BD = item['thang_bdkm']
              dr.THANG_KT =
                moment(tmp, 'YYYYMM')
                  .add((ithang_huongkm && ithang_huongkm > 0 ? ithang_huongkm : 1) - 1, 'M')
                  .format('YYYYMM') * 1
            }
            if (item['kieu_bdtg'] == 3) {
              if (!item['thang_bdtg']) {
                this.$toast.error('Bạn phải nhập tháng bắt đầu trả góp')
                return
              }
              tmp = item['thang_bdtg'] + ''
              if (!tmp || !tmp.match(/^\d{6}$/g)) {
                this.$toast.error('Bạn phải nhập tháng bắt đầu trả góp theo định dạng YYYYMM')
                return
              }
              dr.THANG_BDTG = item['thang_bdtg']
              dr.THANG_KTTG =
                moment(tmp, 'YYYYMM')
                  .add((ithang_tg && ithang_tg > 0 ? ithang_tg : 1) - 1, 'M')
                  .format('YYYYMM') * 1
            }
            if (item['loai_bddc'] == 3) {
              if (item['ngay_bddc'] && !item['ngay_bddc'].match(/^\d{2}\/\d{2}\/\d{4}$/g)) {
                this.$toast.error('Bạn phải nhập Ngày BĐĐC theo định dạng DD/MM/YYYY')
                return
              }
              if (item['ngay_bddc']) {
                item['thang_bddc'] = moment(item['ngay_bddc'], 'DD/MM/YYYY').format('YYYYMM') //Convert.ToDateTime(dgrChiTietKM.Rows[i].Cells["ngay_bddc"].Value.ToString()).ToString("yyyyMM");
              }
              else if(this.thamsomacdinh['TINHCUOC_NGAY'] != 0) {
                this.$toast.error('Bạn phải nhập ngày bắt đầu trả trước')
                return
              }
              if (!item['thang_bddc']) {
                this.$toast.error('Bạn phải nhập tháng bắt đầu trả trước')
                return
              }
              tmp = item['thang_bddc'] + ''
              if (!tmp || !tmp.match(/^\d{6}$/g)) {
                this.$toast.error('Bạn phải nhập tháng bắt đầu đặt cọc theo định dạng YYYYMM')
                return
              }
              //nhapt them 26/09/2016
              //Kiem tra tháng đặt cọc >= tháng hiện tại
              if (item['thang_bddc'] < moment(new Date()).format('YYYYMM')) {
                this.$toast.error('Tháng bắt đầu đặt cọc phải lớn hơn hoặc bằng với tháng hiện tại.')
                return
              }

              dr.THANG_BDDC = item['thang_bddc']
              dr.THANG_KTDC =
                moment(tmp, 'YYYYMM')
                  .add((ithang_huongdc && ithang_huongdc > 0 ? ithang_huongdc : 1) - 1, 'M')
                  .format('YYYYMM') * 1
            }
            //longdx: bổ sung phần cập nhật các tỷ lệ
            if (item['tyle_sd'] == 'undefined' || item['tyle_sd'] == null) {
              this.$toast.error('Tỷ lệ sử dụng không được để trống!')
              return
            } else item['tyle_sd'] = item['tyle_sd'] * 1
            if (item['tyle_tb'] == 'undefined' || item['tyle_tb'] == null) {
              this.$toast.error('Tỷ lệ thuê bao không được để trống!')
              return
            } else item['tyle_tb'] = item['tyle_tb'] * 1
            if (this.i_tyle_sd > 100 || this.i_tyle_tb > 100) {
              this.$toast.error('Tỷ lệ không được vượt quá 100!')
              return
            }
            if (item['tyle_kmld'] == 'undefined' || item['tyle_kmld'] == null) {
              this.$toast.error('Tỷ lệ khuyến mại lắp đặt không được để trống!')
              return
            } else item['tyle_kmld'] == item['tyle_kmld'] * 1

            if (item['ngay_bddc']) {
              if (!item['ngay_bd'] || item['ngay_bd'] == 0) item['ngay_bd'] = item['ngay_bddc']
              if (!item['ngay_ktdc'] || item['ngay_ktdc'] == 0) {
                var ithang = item['huong_dc']
                item['ngay_ktdc'] = moment(item['ngay_bddc'], 'DD/MM/YYYY')
                  .add(ithang && ithang > 0 ? ithang : 1, 'M')
                  .subtract(1, 'd')
                  .format('DD/MM/YYYY')
              }
              if (!item['ngay_kt'] || item['ngay_kt'] == 0) {
                var ithang = item['huong_dc']
                item['ngay_kt'] = moment(item['ngay_kt'], 'DD/MM/YYYY')
                  .add(ithang && ithang > 0 ? ithang : 1, 'M')
                  .subtract(1, 'd')
                  .format('DD/MM/YYYY')
              }
            } else {
              item['ngay_bddc'] = null
              item['ngay_ktdc'] = null
              item['ngay_bd'] = null
              item['ngay_kt'] = null
            }

            if (item['ngay_bddc'] && item['ngay_ktdc']) {
              if (item['ngay_ktdc'] && !item['ngay_ktdc'].match(/^\d{2}\/\d{2}\/\d{4}$/g)) {
                this.$toast.error('Bạn phải nhập Ngày KTĐC theo định dạng DD/MM/YYYY')
                return
              }
              if (item['ngay_bddc'] && !item['ngay_bddc'].match(/^\d{2}\/\d{2}\/\d{4}$/g)) {
                this.$toast.error('Bạn phải nhập Ngày BĐĐC theo định dạng DD/MM/YYYY')
                return
              }
              if (moment(item['ngay_ktdc'], 'DD/MM/YYYY') < moment(item['ngay_bddc'], 'DD/MM/YYYY')) {
                this.$toast.error('Ngày KTĐC không thể nhỏ hơn Ngày BĐĐC!')
                return
              }

              if (item['thang_bddc'] && item['thang_bddc'] != 0 && moment(item['ngay_bddc'], 'DD/MM/YYYY').format('YYYYMM') != item['thang_bddc']) {
                this.$toast.error('Ngày bắt đầu đặt cọc phải nằm trong tháng đặt cọc!')
                return false
              }
            }

            if (item['ngay_bd'] && item['ngay_kt']) {
              if (item['ngay_kt'] && !item['ngay_kt'].match(/^\d{2}\/\d{2}\/\d{4}$/g)) {
                this.$toast.error('Bạn phải nhập Ngày KT theo định dạng DD/MM/YYYY')
                return
              }
              if (item['ngay_bd'] && !item['ngay_bd'].match(/^\d{2}\/\d{2}\/\d{4}$/g)) {
                this.$toast.error('Bạn phải nhập Ngày BĐ theo định dạng DD/MM/YYYY')
                return
              }
              if (moment(item['ngay_kt'], 'DD/MM/YYYY') < moment(item['ngay_bd'], 'DD/MM/YYYY')) {
                this.$toast.error('Ngày KT không thể nhỏ hơn Ngày BĐ!')
                return
              }
            }

            //Với khuyến mại cho sửa khuyến mại cước
            if (item['kieu_km'] == 1) {
              if (this.vdichvuvt_id == DichVuVienThong.ANTOAN_BAOMAT_TT || this.vdichvuvt_id == DichVuVienThong.DICHVU_CNTT || this.vdichvuvt_id == DichVuVienThong.TRUNGTAM_DULIEU || this.vdichvuvt_id == DichVuVienThong.HOINGHI_TRUYENHINH) {
              } else {
                if (this.i_tien_sd + this.i_tien_tb + this.i_cuoc_sd + this.i_cuoc_tb + this.i_tyle_sd + this.i_tyle_tb == 0) {
                  this.$toast.error('Bạn chưa nhập thông tin khuyến mại!')
                  return
                }
              }
              if ((this.i_tien_sd + this.i_tien_tb > 0 && this.i_cuoc_sd + this.i_cuoc_tb > 0) || (this.i_tien_sd + this.i_tien_tb > 0 && this.i_tyle_sd + this.i_tyle_tb > 0) || (this._cuoc_sd + this.i_cuoc_tb > 0 && this.i_tyle_sd + this.i_tyle_sd > 0)) {
                this.$toast.error('Bạn không được nhập đồng thời nhiều hình thức khuyến mại!')
                return
              }
            }

            dr.TYLE_KMLD = item['tyle_kmld'] * 1
            if (item['ngay_bd']) dr.NGAY_BD = item['ngay_bd']
            // check lai xem co can convert sang datetime ko?
            else dr.NGAY_BD = null
            if (item['ngay_bddc']) dr.NGAY_BDDC = item['ngay_bddc']
            // check lai xem co can convert sang datetime ko?
            else dr.NGAY_BDDC = null
            if (item['ngay_kt']) dr.NGAY_KT = item['ngay_kt']
            // check lai xem co can convert sang datetime ko?
            else dr.NGAY_KT = null
            if (item['ngay_ktdc']) dr.NGAY_KTDC = item['ngay_ktdc']
            // check lai xem co can convert sang datetime ko?
            else dr.NGAY_KTDC = null
            dr.NHOM_KM = item['nhom_km']
            dr.TYLE_VAT = item['tyle_vat']
            this.dsDC_HDTB.push(dr)
            //Lấy tiền khuyến mại
            // if (tien_kmld_tt < item["tien"])
            //     tien_kmld_tt = item["tien"];
            // if (vat_kmld_tt < item["vat"])
            //     vat_kmld_tt = item["vat"];
            // if (tyle_kmld_tt < item["tyle_kmld"])
            //     tyle_kmld_tt = item["tyle_kmld"];

            if (tien_kmld_tt < item['tien_kmld']) tien_kmld_tt = item['tien_kmld']
            if (vat_kmld_tt < item['vat_kmld']) vat_kmld_tt = item['vat_kmld']
            if (tyle_kmld_tt < item['tyle_kmld']) tyle_kmld_tt = item['tyle_kmld']

            if (this.ht_kmtt) {
              // #region cuonglt.hpg 20/01/2022 Thêm phần lấy khoản mục chitiet km

              var tongtien = item['datcoc_csd'] * 1
              var tyle_vat = item['tyle_vat_tt'] * 1
              var tien = Math.round((tongtien * 100) / (100 + tyle_vat))
              var vat = Math.round((tien * tyle_vat) / 100)
              if (tongtien != tien + vat) {
                tien = tongtien - vat
              }

              tien_datcoc_tt += tien
              this.vat_datcoc_tt += vat
              this.m_dsTienCTKM.push({
                khoanmuctt_id: 11,
                tien: tien,
                vat: vat,
                danhdau: 0,
                id: item['chitietkm_id'] * 1,
                tyle_vat: item['tyle_vat_tt'],
                tyle_vat_id: item['tyle_vat_id'],
                loai_id: 1,
                ten_ctkm: item['ten_ctkm'],
                kieu: 1
              })
              // #endregion
            } else {
              //longdx: Cong don tien tra truoc neu gdv chon nhieu tra truoc cung luc
              tien_datcoc_tt += item['datcoc_csd']
            }
            // }
            //     //longdx: Cong don tien tra truoc neu gdv chon nhieu tra truoc cung luc
            //     tien_datcoc_tt += item["datcoc_csd"];
          }
        }
        var retData = {
          dsDC_HDTB: this.dsDC_HDTB,
          tien_kmld_tt: tien_kmld_tt,
          vat_kmld_tt: vat_kmld_tt,
          tyle_kmld_tt: tyle_kmld_tt,
          tien_datcoc_tt: tien_datcoc_tt,
          vat_datcoc_tt: this.vat_datcoc_tt,
          m_dsTienCTKM: this.m_dsTienCTKM
        }
        this.$emit('form-close', retData)
      }
    },
    formatPrice(value) {
      let val = (value / 1).toFixed(0).replace('.', ',')
      return val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, '.')
    },
    rowSelected(args) {
      if (!args || !args.data || args.data.length <= 0) return
      for (var item of this.dt) {
        var i = 0
        for (; i < args.data.length; i++)
          if (item['chitietkm_id'] == args.data[i]['chitietkm_id']) {
            item['dangky'] = 1
            break
          }
        // if(i==args.data.length) item['dangky']=0;
      }
    },
    async Kiemtra_DinhMuc(kt_ctkm_id, kt_tien_sd, kt_tien_tb, kt_cuoc_sd, kt_cuoc_tb, kt_tyle_sd, kt_tyle_tb) {
      //TODO line 1393
      //ds_kt = tchopdong.LAY_CTKM_DINHMUC(kt_ctkm_id);
      var rs = await this.$root.context.post('/web-hopdong/lapdatmoi/lay_ctkm_dinhmuc', { chitiet_km_id: kt_ctkm_id })
      var ds_kt = rs.data
      if (ds_kt && ds_kt.length > 0) {
        var _tien_sd_min = ds_kt[0]['tien_sd_min']
        var _tien_sd_max = ds_kt[0]['tien_sd_max']
        var _cuoc_sd_min = ds_kt[0]['cuoc_sd_min']
        var _cuoc_sd_max = ds_kt[0]['cuoc_sd_max']
        var _tyle_sd_min = ds_kt[0]['tyle_sd_min']
        var _tyle_sd_max = ds_kt[0]['tyle_sd_max']
        var _tien_tb_min = ds_kt[0]['tien_tb_min']
        var _tien_tb_max = ds_kt[0]['tien_tb_max']
        var _cuoc_tb_min = ds_kt[0]['cuoc_tb_min']
        var _cuoc_tb_max = ds_kt[0]['cuoc_tb_max']
        var _tyle_tb_min = ds_kt[0]['tyle_tb_min']
        var _tyle_tb_max = ds_kt[0]['tyle_tb_max']
        // Kiểm tra tiền sử dụng ( tiền áp )
        if (kt_tien_sd > 0 && (_tien_sd_min >= 0 || _tien_sd_max >= 0)) {
          if (kt_tien_sd < _tien_sd_min || kt_tien_sd > _tien_sd_max) {
            this.$toast.error('Tiền SD không đúng định mức cho phép : ' + this.formatPrice(_tien_sd_min) + ' -> ' + this.formatPrice(_tien_sd_max) + ' !')
            return false
          }
        }

        // Kiểm tra tiền thuê bao ( tiền áp )
        if (kt_tien_tb > 0 && (_tien_tb_min >= 0 || _tien_tb_max >= 0)) {
          if (kt_tien_tb < _tien_tb_min || kt_tien_tb > _tien_tb_max) {
            this.$toast.error('Tiền TB không đúng định mức cho phép : ' + this.formatPrice(_tien_tb_min) + ' -> ' + this.formatPrice(_tien_tb_max) + ' !')
            return false
          }
        }

        // Kiểm tra cước sử dụng (tiền giảm cho khách hàng)
        if (kt_cuoc_sd > 0 && (_cuoc_sd_min >= 0 || _cuoc_sd_max >= 0)) {
          if (kt_cuoc_sd < _cuoc_sd_min || kt_cuoc_sd > _cuoc_sd_max) {
            this.$toast.error('Giảm cước SD không đúng định mức cho phép : ' + this.formatPrice(_cuoc_sd_min) + ' -> ' + this.formatPrice(_cuoc_sd_max) + ' !')
            return false
          }
        }

        // Kiểm tra cước thuê bao ( tiền áp )
        if (kt_cuoc_tb > 0 && (_cuoc_tb_min >= 0 || _cuoc_tb_max >= 0)) {
          if (kt_cuoc_tb < _cuoc_tb_min || kt_cuoc_tb > _cuoc_tb_max) {
            this.$toast.error('Giảm cước TB không đúng định mức cho phép : ' + this.formatPrice(_cuoc_tb_min) + ' -> ' + this.formatPrice(_cuoc_tb_max) + ' !')
            return false
          }
        }

        // Kiểm tra tỷ lệ sử dụng ( tỷ lệ giảm )
        if (kt_tyle_sd > 0 && (_tyle_sd_min >= 0 || _tyle_sd_max >= 0)) {
          if (kt_tyle_sd < _tyle_sd_min || kt_tyle_sd > _tyle_sd_max) {
            this.$toast.error('Tỷ lệ SD không đúng định mức cho phép : ' + _tyle_sd_min + ' -> ' + _tyle_sd_max + ' !')
            return false
          }
        }

        // Kiểm tra tỷ lệ thuê bao ( tỷ lệ áp )
        if (kt_tyle_tb > 0 && (_tyle_tb_min >= 0 || _tyle_tb_max >= 0)) {
          if (kt_tyle_tb < _tyle_tb_min || kt_tyle_tb > _tyle_tb_max) {
            this.$toast.error('Tỷ lệ TB không đúng định mức cho phép : ' + _tyle_sd_min + ' -> ' + _tyle_sd_max + ' !')
            return false
          }
        }
        return true
      } else return true
    },

    async HienThi_TBKem() {
      if (!this._hdtb_id) return
      var vhdtb_id = this._hdtb_id
      var rs = await this.$root.context.post('/web-hopdong/lapdatmoi/lay_chitiet_loaihinh_kemtheo', { vhdtb_id: vhdtb_id })
      this.dgvDSKemTheo.DataSource = rs && rs.data && rs.data.length > 0 ? rs.data : [] //tchopdong.LAY_CHITIET_LOAIHINH_KEMTHEO(vhdtb_id).Tables[0];
    },
    async dgvDSKemTheo_FocusedRowChanged(idx, item) {
      if (!item) {
        this.dgvChiTietKM_new.DataSource = []
        return
      }
      var rs = null
      var pdata = {
        chuyenmang: this.vchuyenmang,
        hdtb_id: this._hdtb_id ? this._hdtb_id : 0,
        khuyenmai_id: this.khuyenmai_id,
        loaitb_id: item.LOAITB_ID,
        muccuoc_id: item.MUCCUOC_ID,
        tocdo_id: item.TOCDO_ID
      }
      rs = await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/lay_chitiet_km_hdtb', pdata)
      this.dgvChiTietKM_new.DataSource = rs.data
      for (var item1 of this.dgvChiTietKM_new.DataSource) {
        item1['ngay_bd'] = item1['ngay_bd'] ? item1['ngay_bd'] : null
        item1['ngay_bddc'] = item1['ngay_bddc'] ? item1['ngay_bddc'] : null
        item1['ngay_kt'] = item1['ngay_kt'] ? item1['ngay_kt'] : null
        item1['ngay_ktdc'] = item1['ngay_ktdc'] ? item1['ngay_ktdc'] : null
      }
    },
    dgvChiTietKM_new_FocusedRowChanged(idx, item) {
      if (item) this.txtND = item.NOIDUNG ? item.NOIDUNG : ''
      else this.txtND = ''
    }
  }
}
</script>
