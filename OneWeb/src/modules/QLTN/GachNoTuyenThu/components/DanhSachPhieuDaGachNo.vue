<template>
  <div>
    <!-- TOOLBAR -->
    <div class="list-actions-top">
        <div class="pull-left">
            <ul class="list">
                <li>
                    <a href="#" v-on:click.prevent="getDanhSach">
                        <span class="icon one-file-attach1"></span> Lấy DS (F5)
                    </a>
                </li>
                <li>
                    <a :class="{ disabled: resultDSPhieuTT.length === 0 }" href="#" v-on:click.prevent="updateNgayThanhToan">
                        <span class="icon one-reload1"></span> Cập nhật
                    </a>
                    <PopupThayDoiNgayThanhToan ref="dlgThayDoiNgayTT" :kycuoc="f_kycuoc" :phieu="currentPhieuTTRow" @acceptChange="updateDatePhieuThuHandler"></PopupThayDoiNgayThanhToan>
                </li>
                <li>
                    <a :class="{ disabled: resultDSPhieuTT.length === 0 }" href="#"  v-on:click.prevent="deletePhieuThu">
                        <span class="icon one-file-trash"></span> Xoá phiếu
                    </a>
                    <PopupXacNhanXoaPhieu ref="refXoaPhieuTT" :kycuoc="f_kycuoc" :dsPhieu="dsPhieuCanXoa" @acceptChange="deletePhieuThuHandler"></PopupXacNhanXoaPhieu>
                </li>
                <li>
                    <a href="#" v-on:click.prevent="editThanhToan">
                        <span class="icon one-file-edit1"></span> Sửa TT
                    </a>

                </li>
                <li>
                    <a href="#" v-on:click.prevent="editLuot">
                        <span class="icon one-file-list"></span> Sửa lượt
                    </a>
                    <SuaThongTinLuotThanhToan ref="dlgThayDoiThongTinTT" :input="TDTTTTParams" @acceptChange="updatePhieuThuHandler"></SuaThongTinLuotThanhToan>
                </li>
                <li>
                  <a href="#" v-on:click.prevent="printPhieuThu">
                      <span class="icon one-print"></span>In phiếu
                  </a>
                  <PopupInPhieuThu ref="refPopupInPhieuThu" @accept="acceptPrintPhieuThuHandler"></PopupInPhieuThu>
                </li>
                <li>
                    <a href="#"  v-on:click.prevent="exportExcel">
                        <span class="icon one-xlsx"></span> Xuất Excel
                    </a>
                </li>
                <li>
                    <a href="#"  v-on:click.prevent="syncVNP">
                        <span class="icon one-sync1"></span> Đồng bộ VNP
                    </a>
                </li>
            </ul>
        </div>
        <div class="pull-right fw6 mart10">
            <span>Kỳ cước : {{get_kycuoc}}</span>
            <span>Chu kỳ : {{get_chuky}}</span>
        </div>
        <div class="clearfix"></div>
    </div>
    <!-- MAIN CONTENT -->
    <div class="page-content">
      <div class="grid-stack-box">
        <Split style="height: 750px">
          <SplitArea :size="33" :minSize="100">
            <Split style="height: 700px;" direction="vertical">
            <SplitArea sizes="50">
              <div class="box-form marb0" id="boxTop">
                  <div class="legend-title mart5">Danh sách nhân viên thu cước</div>
                  <div class="table-content" style="max-height: calc(100% - 18px);">
                    <DataGrid
                    v-bind:columns="columnsNVTC"
                    v-bind:dataSource="resultDSNVTC"
                    v-bind:aggregates="[
                      {field: 'TEN_NVTC', type: 'Count', format: 'N0', footerTemplate: this.footerCount},
                      {field: 'TONGPHIEU', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
                      {field: 'TONGTIEN', type: 'Sum', format: 'N0', footerTemplate: this.footerSum}
                    ]"
                    :showFilter="true"
                    :allowPaging="true"
                    :allowExcelExport="true"
                    :totalRecords="pageinfoNVTC.totalElement"
                    :pageSize="pageinfoNVTC.maxSize"
                    :pageIndex="pageinfoNVTC.page"
                    :key="keyNVTC"
                    @selectedRowChanged="RowChangedNVTC"
                    ref="gridDSNVTC"
                    >
                  </DataGrid>
                </div>
              </div>
            </SplitArea>
            <SplitArea sizes="50">
              <div class="box-form marb0" id="boxBottom">
                <div class="legend-title mart5">Danh sách lượt thanh toán</div>
                <div class="table-content" style="max-height: calc(100% - 80px);">
                  <DataGrid
                    v-bind:columns="columnsLuotTT"
                    v-bind:dataSource="resultDSLuotTT"
                    v-bind:aggregates="[
                      {field: 'LUOT_TT', type: 'Count', format: 'N0', footerTemplate: this.footerCount},
                      {field: 'TONGPHIEU', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
                      {field: 'TONGTIEN', type: 'Sum', format: 'N0', footerTemplate: this.footerSum}
                    ]"
                    :showFilter="true"
                    :allowPaging="true"
                    :allowExcelExport="true"
                    :totalRecords="pageinfoLuotTT.totalElement"
                    :pageSize="pageinfoLuotTT.maxSize"
                    :pageIndex="pageinfoLuotTT.page"
                    @selectedRowChanged="RowChangedLuotTT"
                    :key="keyLuotTT"
                    ref="gridDSLuotTT"
                    >
                  </DataGrid>
                </div>
              </div>
            </SplitArea>
          </Split>
          </SplitArea>
          <SplitArea :size="67" :minSize="100">
              <div class="box-col box-form marb0" id="boxRight">
                    <div class="legend-title padb3">
                        <div class="pull-left mart7">
                            Danh sách phiếu thanh toán
                        </div>
                        <div class="pull-right">
                          <div class="info-row marb0">
                            <div class="key w60 red">Ngày TT</div>
                            <div class="value">
                              <ejs-datepicker
                                v-model="p_ngay_ttoan"
                                :value="config.dateConfig.value"
                                :format="config.dateConfig.format"
                                :allowEdit=false
                                :showClearButton=false
                                >
                              </ejs-datepicker>
                            </div>
                          </div>
                        </div>
                        <div class="clearfix"></div>
                    </div>
                    <div class="table-content" style="max-height: 600px;">
                        <DataGrid3
                          v-bind:columns="[
                            {fieldName: 'HTTT_ID', visible: false},
                            {fieldName: 'NGANHANG_ID', visible: false},
                            {fieldName: 'STT', headerText: 'STT', allowFiltering: true, width: 50, allowEditing: false, freeze: 'Left'},
                            {fieldName: 'NGAY_TT', headerText: 'Ngày trả', allowFiltering: true, textAlign: 'Right', width: 100, allowEditing: false},
                            {fieldName: 'SOPHIEU', headerText: 'Số phiếu', allowFiltering: true, textAlign: 'Right', width: 100, allowEditing: false},
                            {fieldName: 'MA_TT', headerText: 'Mã TT', allowFiltering: true, textAlign: 'Left', width: 130, allowEditing: false},
                            {fieldName: 'MATB_DD', headerText: 'Mã đại diện', allowFiltering: true, textAlign: 'Left', width: 120, allowEditing: false},
                            {fieldName: 'TIEN_TRA', headerText: 'Tiền trả', allowFiltering: true, format:'N0', type: 'number', textAlign: 'Right', width: 100, allowEditing: false},
                            {fieldName: 'SO_HD', headerText: 'Số HĐ', allowFiltering: true, textAlign: 'Right', width: 100, allowEditing: false},
                            {fieldName: 'TEN_TT', headerText: 'Tên TT', allowFiltering: true, width: 200, allowEditing: false},
                            {fieldName: 'MANV_TC', headerText: 'Mã NVTC', allowFiltering: true, width: 80, allowEditing: false},
                            {fieldName: 'DIACHI_TT', headerText: 'Địa chỉ TT', allowFiltering: true, width: 200, allowEditing: false},
                            {fieldName: 'NGUOI_CN', headerText: 'Người gạch', allowFiltering: true, width: 150, allowEditing: false},
                            {fieldName: 'CHUNGTU', headerText: 'Chứng từ', allowFiltering: true, width: 80, allowEditing: false},
                            {fieldName: 'THUNGAN', headerText: 'Thu ngân', allowFiltering: true, width: 130, allowEditing: false},
                            {fieldName: 'NGAY_CN', headerText: 'Ngày CN', allowFiltering: true, textAlign: 'Right', width: 130, allowEditing: false},
                            {fieldName: 'HDDT', headerText: 'HĐĐT', allowFiltering: true, width: 80, allowEditing: false},
                            {fieldName: 'GHICHU', headerText: 'Ghi chú', allowFiltering: true, width: 100, allowEditing: true},
                            {fieldName: 'TRANGTHAI', headerText: 'Trạng thái', allowFiltering: true, width: 100, allowEditing: false, template: this.statusTemplate, freeze: 'Right' }
                          ]"
                          v-bind:dataSource="resultDSPhieuTT"
                          :showColumnCheckbox="true"
                          :selectionSettings="config.selectionSettings"
                          :showFilter="true"
                          :allowPaging="true"
                          :allowExcelExport="true"
                          :totalRecords="pageinfoPhieuTT.totalElement"
                          :pageSize="pageinfoPhieuTT.maxSize"
                          :pageIndex="pageinfoPhieuTT.page"
                          :editSettings="editSettings"
                          @actionBegin="actionBegin"
                          @actionComplete="actionComplete"
                          @selectedRowChanged="RowChangedPhieuTT"
                          :key="keyPhieuTT"
                          ref="gridDSPhieuTT"
                          >
                        </DataGrid3>
                    </div>
                </div>
          </SplitArea>
        </Split>
      </div>
    </div>
  </div>
</template>

<script>

import Vue from 'vue'
import { mapActions, mapState } from "vuex"
import moment from 'moment'
import GachNoAPI from '../../api/GachNoAPI'
import CommonsAPI from '@/modules/QLTN/api/Commons'
import GachNoTuyenThuAPI from '../../api/GachNoTuyenThu'
import PopupXacNhanXoaPhieu from '../../components/PopupInXoaSuaPhieuThanhToan/PopupXacNhanXoaPhieu'
import PopupThayDoiNgayThanhToan from '../../components/PopupInXoaSuaPhieuThanhToan/PopupThayDoiNgayThanhToan'
//import PopupThayDoiThongTinThanhToan from '../../components/PopupInXoaSuaPhieuThanhToan/PopupThayDoiThongTinThanhToan'
import SuaThongTinLuotThanhToan from './SuaThongTinLuotThanhToan'
import PopupInPhieuThu from '../../components/PopupInXoaSuaPhieuThanhToan/PopupInPhieuThu'
import {previewPrint} from "@/utils/util"
import { DialogUtility } from '@syncfusion/ej2-popups'


Vue.use(DialogUtility)
let DialogObj = undefined


import {
  NHOM_HTTT
} from "@/const/enums"

export default{
  components: {
    PopupXacNhanXoaPhieu,
    PopupThayDoiNgayThanhToan,
    SuaThongTinLuotThanhToan,
    PopupInPhieuThu
  },
  async created () {
    if(this.$route.query.ma_thungan  !== undefined
      && this.$route.query.ma_thungan !== null
      && this.$route.query.ma_thungan !== '')
    {
      this.params.p_ma_thungan = this.$route.query.ma_thungan
    }
    if(this.$route.query.thungan_id  !== undefined
      && this.$route.query.thungan_id !== null
      && this.$route.query.thungan_id !== '')
    {
      this.params.p_thungan_id = this.$route.query.thungan_id
    }
    if(this.$route.query.ten_thungan  !== undefined
      && this.$route.query.ten_thungan !== null
      && this.$route.query.ten_thungan !== '')
    {
      this.params.p_ten_thungan = this.$route.query.ten_thungan
    }
    if(this.$route.query.ngay_tt  !== undefined
      && this.$route.query.ngay_tt !== null
      && this.$route.query.ngay_tt !== '')
    {
      if(moment(this.$route.query.ngay_tt, 'DD/MM/YYYY').isValid())
        this.params.p_ngay_ttoan = moment(this.$route.query.ngay_tt, 'DD/MM/YYYY').toDate()
    }

    // check quyền di động
    if(this.$auth.getMaCCBS() !== null)
      this.params.p_quyen_dd = true

    await this.getKyHoaDonHienTai()
    if (this.kyHoaDonHienTai !== undefined) {
      this.params.p_kycuoc = this.kyHoaDonHienTai.KYCUOC
      this.getDSDaiLy()
    } else {
      this.params.p_kycuoc = moment(new Date()).subtract(1,'months').startOf('month').format('YYYYMMDD')
    }

    let tmp = this.params.p_kycuoc.toString()
    this.params.p_kyhientai_xuoi = tmp.substr(4, 2) + tmp.substr(0, 4)


  },
  computed :{
    ...mapState("qltnCommon", [
      "kyHoaDonHienTai"
    ]),
    get_kycuoc (){
      let v = this.params.p_kycuoc
      if(v !== undefined && v !== null)
      {
        v = '' + v
        return v.substr(4, 2) + '/' + v.substr(0, 4)
      }
      else
        return '-'
    },
    get_chuky (){
      let v = this.params.p_kycuoc
      if(v !== undefined && v !== null)
      {
        v = '' + v
        return v.substr(6, 2)
      }
      else
        return '-'
    },
    f_phieu_id() {
      return this.currentPhieuTTRow !== null ? this.currentPhieuTTRow.PHIEU_ID : null
    },
    f_ngay_tt(){
      return moment(this.params.p_ngay_ttoan).format('DD/MM/YYYY')
    },
    f_kycuoc(){
      if(this.params.p_kycuoc === null)
        return null
      return this.params.p_kycuoc.toString()
    },
    f_lantra(){
      return this.currentPhieuTTRow !== null ? this.currentPhieuTTRow.LANTRA : null
    },
    f_ghichu(){
      return this.currentPhieuTTRow !== null ? this.currentPhieuTTRow.GHICHU : null
    },
    p_ngay_ttoan : {
      get() {
        return this.params.p_ngay_ttoan
      },
      set(value) {
        return this.params.p_ngay_ttoan = value
      }
    },
  },
  data () {
    return {
      editSettings: { allowEditing: true, allowAdding: false, allowDeleting: false, mode: 'Inline' },

      config: {
        dateConfig: {
          value: new Date(),
          format: 'dd/MM/y',
          maxDate: moment().endOf('month').toDate(),
          minDate: moment().startOf('month').toDate()
        },
        selectionSettings: {checkboxOnly: true, type: 'Multiple'},
      },
      params: {
        p_quyen_dd: false,
        p_gachno_chuan: -1,
        p_ngay_ttoan: new Date(),
        p_kycuoc: null, // kỳ hóa đơn hiện tại
        p_kyhientai_xuoi: null,
        p_thungan_id: null,
        p_ma_thungan: null,
        p_ten_thungan: null,
        p_nhom_httt: NHOM_HTTT.GACHNO_DAILY,
        p_ngaygach_lui_thang : 0,
        p_ngaygach_tien : 0,
        p_ngayxoa_lui : 0,
        p_ngaygach_lui_thanght : 0,
        p_kyhoadon: '', // MMyyyy  // Kỳ hóa đơn tìm kiếm
        p_chuky: '01',  // Chu kỳ tính cước của 1 tháng : 01,02
      },
      TDTTTTParams: {
        kyCuoc: 0,
        phieuId: 0,
        lanTra: '',
        HTTTId: '',
        nhomHTTTId: '',
        ngayNganHang: '',
        chungTu: '',
        chiPhi: '',
        nganHangId: '',
        ghiChu: '',
        matKhau: ''
      },
      footerSum: function () {
        return  { template : Vue.component('sumTemplate', {
            template: `<span>{{data.Sum}}</span>`,
            data () {return { data: {}};}
            })
          }
      },
      footerCount: function () {
        return  { template : Vue.component('countTemplate', {
            template: `<div style='text-align:"right"'><span>Tổng : {{data.Count}}</span></div>`,
            data () {return { data: {}};}
            })
          }
      },
      statusTemplate: function () {
        return {
            template: Vue.component('statusTemplate', {
                template: `<span :class="{'e-icons': true, 'grid-status--1': data.TRANGTHAI == -1, 'grid-status-0': data.TRANGTHAI == 0, 'grid-status-0': data.TRANGTHAI == 1}"></span>`,
                data: function () { return { data: {}}; }
            })
        }
      },
      columnsNVTC: [
        {fieldName: 'MANV_TC', visible: false, isPrimaryKey: true},
        {fieldName: 'TEN_NVTC', headerText: 'Nhân viên', allowFiltering: true, width: 200},
        {fieldName: 'TONGPHIEU', headerText: 'Tổng phiếu', allowFiltering: true, format:'N0', type: 'number', width: 130},
        {fieldName: 'TONGTIEN', headerText: 'Tổng tiền', allowFiltering: true, format:'N0', type: 'number', width: 130},
        {fieldName: 'LOAI_TIEN', headerText: 'Loại tiền', allowFiltering: true, width: 100},
      ],
      sumColumnsNVTC:[

        ],
      resultDSNVTC: [],
      pageinfoNVTC: {
        page: 0,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null,
      },
      keyNVTC: 1,

      columnsLuotTT: [
        {fieldName: 'NHOM_HTTT_ID', visible: false},
        {fieldName: 'LANTRA', visible: false, isPrimaryKey: true},
        {fieldName: 'LUOT_TT', headerText: 'Lượt TT', allowFiltering: true},
        {fieldName: 'TONGPHIEU', headerText: 'Tổng phiếu', allowFiltering: true, format:'N0', type: 'number'},
        {fieldName: 'TONGTIEN', headerText: 'Tổng tiền', allowFiltering: true, format:'N0', type: 'number'}
      ],
      sumColumnsLuotTT:[

        ],
      resultDSLuotTT: [],
      pageinfoLuotTT: {
        page: 0,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null,
      },
      keyLuotTT: 1,

      columnsPhieuTT: [

      ],
      resultDSPhieuTT: [],
      pageinfoPhieuTT: {
        page: 0,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null,
      },
      keyPhieuTT: 1,
      currentNVTCRow: null,
      currentLuotTTRow: null,
      currentPhieuTTRow: null,
      phieuSelected: [],
      XPParams: {
        tongTien: 0,
        tongPhieu: 0
      },
      dsPhieuCanXoa: []
    }
  },
  methods : {
    ...mapActions("qltnCommon", [
        "getKyHoaDonHienTai",
    ]),
    getThamSoGachNo: function(){

      try{
          GachNoAPI.getThamSoGachNo(this.axios).then(
            (response) => {
              //console.log(response)
              if(response.data.error_code === 'BSS-00000000'
                && response.data.data !== undefined)
              {
                this.params.p_ngaygach_lui_thang = parseInt(response.data.data[0].NGAY_GL)
                this.params.p_ngaygach_tien = parseInt(response.data.data[0].NGAY_GT)
                this.params.p_ngayxoa_lui = parseInt(response.data.data[0].NGAY_XL)
                this.params.p_ngaygach_lui_thanght = parseInt(response.data.data[0].NGAY_GLT)
              }
              else
                this.$toast.error(response.data.message)
            }
          )
        } catch (error) {
          this.$toast.error(error)
      }
    },
    async getDSDaiLy(){
      let postData = {
       //kyCuoc: moment(this.params.p_ngay_ttoan).subtract(1, 'months').startOf('month').format('YYYYMMDD'),
        kyCuoc: this.params.p_kycuoc,
        ngayTT: moment(this.params.p_ngay_ttoan).format('DD/MM/YYYY')
      }

      this.clearResuls()
      this.loading(true)
      //console.log(postData)
      await GachNoAPI.getDanhSachNVTCTheoTuyen(this.axios, postData)
        .then(
          (res) => {
            //console.log(res)
            if(res.data.error_code === 'BSS-00000000'
              && res.data.data !== undefined
              && res.data.data.length > 0)
              {
                this.keyNVTC++
                this.resultDSNVTC = res.data.data
                this.pageinfoNVTC.totalElement = res.data.data.length
                //this.params.p_nvtc_options = res.data.data
                //this.params.p_nvtc = res.data.data[0].TUYENTHU_ID
              }
          }
        )
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => {this.loading(false)})

    },
    async getDSLuotTra(ma_nvtc){
      let postData = {
        //kyCuoc : moment(this.params.p_ngay_ttoan).subtract(1, 'months').startOf('month').format('YYYYMMDD'),
        kyCuoc : this.params.p_kycuoc,
        ngayTT : moment(this.params.p_ngay_ttoan).format('DD/MM/YYYY'),
        maNVTC: ma_nvtc

      }
      this.resultDSLuotTT = []
      this.pageinfoLuotTT.page = 0
      this.pageinfoLuotTT.totalElement = 0

      try{
        await GachNoTuyenThuAPI.getDSLuotTra(this.axios, postData).then(
          (res) => {
            if(res.data.error_code === 'BSS-00000000'
              && res.data.data !== undefined
              && res.data.data.length > 0)
              {
                this.keyLuotTT++
                this.resultDSLuotTT = res.data.data
                this.pageinfoLuotTT.totalElement = res.data.data.length
              }
          }
        )
      } catch (error)
      {
        this.$toast.error(error.data.message_detail)
      }
    },
    async getDSPhieuTT(lanTra, maNVTC, htttId){
      let postData = {
        kyCuoc : this.params.p_kycuoc,
        lanTra : lanTra,
        ngayTT : moment(this.params.p_ngay_ttoan).format('DD/MM/YYYY'),
        maNVTC : maNVTC,
        htttId : htttId
      }
      this.resultDSPhieuTT = []
      this.pageinfoPhieuTT.page = 0
      this.pageinfoPhieuTT.totalElement = 0

      await GachNoTuyenThuAPI.getDSPhieuTT(this.axios, postData)
        .then(
          (res) => {
            if(res.data.error_code === 'BSS-00000000'
              && res.data.data !== undefined
              && res.data.data.length > 0)
              {
                this.keyPhieuTT++
                this.resultDSPhieuTT = res.data.data
                this.pageinfoPhieuTT.totalElement = res.data.data.length
              }
          }
        )
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
    },

    clearResuls(){
      this.resultDSNVTC = []
      this.resultDSLuotTT = []
      this.resultDSPhieuTT = []
      this.currentNVTCRow = null
      this.currentLuotTTRow = null
      this.pageinfoNVTC.page = 0
      this.pageinfoNVTC.totalElement = 0
      this.pageinfoLuotTT.page = 0
      this.pageinfoLuotTT.totalElement = 0
      this.pageinfoPhieuTT.page = 0
      this.pageinfoPhieuTT.totalElement = 0
    },

    getDanhSach(){
      this.getDSDaiLy()
    },
    updateNgayThanhToan(){
      if(this.currentPhieuTTRow === null)
      {
        this.$toast.error('Chưa chọn phiếu để cập nhật')
        return false
      }
      this.$refs.dlgThayDoiNgayTT.openDialog()
    },
    confirmDelete: function(){
      let selected = this.$refs.gridDSPhieuTT.getSelectedRecords()
      let arrKeys = []
      selected.forEach((item) => {
        arrKeys.push(item.THUEBAO_ID)
      })

      selected.forEach( (item, index) =>{
        /** */
        try{
          let postData = {
            pKyCuoc : this.params.p_kycuoc,
            pPhieuId: item.PHIEU_ID,
            pMayHuy: '-',
            pNguoiHuy: this.$auth.getUserName()
          }
            GachNoAPI.deletePhieuThanhToan(this.axios, postData).then(
              (response) => {
                if(response.data.error_code === 'BSS-00000000')
                {

                  this.$toast.success('Xóa phiếu' + item.PHIEU_ID + ' thành công')
                }
                else
                  this.$toast.error(response.data.message)
              }
            )
          } catch (error) {
            this.$toast.error(error)
        }
        /** */
      })
      DialogObj.hide()
//      this.

      this.getDSPhieuTT(this.currentLuotTTRow.LANTRA, this.currentNVTCRow.MANV_TC, this.currentLuotTTRow.HTTT_ID)

    },
    deletePhieuThu(){
      /*
      let selected = this.$refs.gridDSPhieuTT.getSelectedRecords()

      if(selected === null || selected.length <= 0){
        this.$toast.error('Bạn chưa chọn phiếu cần xóa.')
        return
      }

      DialogObj = DialogUtility.confirm({
          title: 'Thông báo',
          content: "<div style='padding:10px'>Bạn có thật sự muốn xóa " + selected.length + " phiếu thanh toán đang được chọn không ?</div>",
          okButton: {  text: 'Đồng ý', click: this.confirmDelete },
          cancelButton: {  text: 'Hủy'},
          showCloseIcon: true,
          closeOnEscape: true,
          animationSettings: { effect: 'Zoom' }
      });
      */


      let tongTien = 0;
      let tongPhieu = 0;
      this.phieuSelected = this.$refs.gridDSPhieuTT.getSelectedRecords();

      if(this.phieuSelected.length <= 0)
      {
        this.$toast.error('Bạn chưa chọn phiếu để xóa.')
        return false
      }

      let errors = []
      let user = this.$auth.getUserName()


      this.phieuSelected.forEach(element => {
        if(element.NGUOI_CN !== user)
          errors.push('Bạn không thể xóa phiếu thu ID' + element.PHIEU_ID + ' của user ' + element.NGUOI_CN)
        //tongTien += element.TIEN_TRA
        element.SOTIENTRA = element.TIEN_TRA
      });

      if(errors.length > 0)
      {
        this.$toast.error(errors.join('\r\n'))
        return false
      }
      this.dsPhieuCanXoa = this.$refs.gridDSPhieuTT.getSelectedRecords()

      this.$refs.refXoaPhieuTT.openDialog();
    },
    editThanhToan(){
      if(this.currentPhieuTTRow === null)
      {
        this.$toast.error('Chưa chọn phiếu để cập nhật')
        return false
      } else
      {
        let selected = this.$refs.gridDSPhieuTT.getSelectedRecords()
        if(selected.length <= 0)
        {
          this.$toast.error('Chưa chọn phiếu để cập nhật')
          return false
        }
        let ds = this.$refs.gridDSPhieuTT.$refs.grid.ej2Instances.currentViewData
        if(ds.length <= 0)
        {
          this.$toast.error('Chưa chọn phiếu để cập nhật')
          return false
        }
      }
      this.$refs.gridDSPhieuTT.$refs.grid.startEdit()
    },
    editLuot(){
      if(this.currentPhieuTTRow === null)
      {
        this.$toast.error('Chưa chọn phiếu để cập nhật')
        return false
      }
      this.TDTTTTParams = {
        kyCuoc: this.params.p_kycuoc,
        phieuId: this.currentPhieuTTRow.PHIEU_ID,
        lanTra: this.currentLuotTTRow.LANTRA,
        HTTTId: this.currentLuotTTRow.HTTT_ID,
        nhomHTTTId: this.currentLuotTTRow.NHOM_HTTT_ID,
        ngayNganHang: this.currentPhieuTTRow.NGAY_NH,
        chungTu: this.currentPhieuTTRow.CHUNGTU,
        chiPhi: 0,//this.currentLuotTTRow.CHIPHI,
        nganHangId: this.currentPhieuTTRow.NGANHANG_ID,
        ghiChu: this.currentPhieuTTRow.GHICHU
      },
      this.$refs.dlgThayDoiThongTinTT.openDialog()
    },
    printPhieuThu(){
      this.selectedRows = this.$refs.gridDSPhieuTT.getSelectedRecords()


      if(this.selectedRows.length <= 0)
      {
        this.$toast.error('Chưa chọn phiếu để in.')
        return false
      }

      let account = this.$auth.getUserName()

      this.selectedRows.forEach((item) => {
        if(item.NGUOI_CN.toUpperCase() !== account.toUpperCase())
        {
          this.$toast.error('Bạn không in phiếu thanh toán của user ' + item.NGUOI_CN)
          return false
        }
      })

      //this.$toast.error('Chức năng In đang được phát triển. Vui lòng thử lại sau.')
      this.$refs.refPopupInPhieuThu.openDialog()
    },
    exportExcel(){
      if(this.resultDSPhieuTT.length <= 0)
      {
        this.$toast.error('Không có dữ liệu để xuất file')
      }
      let excelExportProperties = {
          dataSource: this.resultDSPhieuTT
      };
      this.$refs.gridDSPhieuTT.excelExport(excelExportProperties)
    },
    syncVNP()
    {

      if(!this.params.p_quyen_dd)
      {
        this.$toast.error('Đồng bộ VNP chưa được thiết lập bởi tỉnh.')
        return
      }


      if(this.resultDSPhieuTT.length <= 0)
      {
        this.$toast.error('Không có dữ liệu phiếu trả đồng bộ.')
        return
      }

      let selectedRows = this.$refs.gridDSPhieuTT.getSelectedRecords()

      if(selectedRows.length <= 0)
      {
        this.$toast.error('Bạn chưa check chọn phiếu trả để đồng bộ VNP')
        return false
      }

      let t = selectedRows.filter(x => (x.PHIEU_ID_NEO === null && (x.TRANGTHAI === -1 || x.TRANGTHAI === 1)))

      if(t.length > 0)
      {
        this.$toast.error("Bạn không được chọn phiếu đã đồng bộ hoặc phiếu không phải dịch vụ di động")
        return
      }

      let user = this.$auth.getUserName()
      let t_user = selectedRows.filter(x => x.PHIEU_ID_NEO !== null && x.PHIEU_ID_NEO !== 0 && x.TRANGTHAI !== -1 && x.TRANGTHAI !== 1 && x.NGUOI_CN !== user)

      if(t_user.length > 0)
      {
        this.$toast.error("Bạn không được đồng bộ phiếu của user vnp khác")
        return
      }

      let postData = {
        "nhomHTTT": NHOM_HTTT.GACHNO_DAILY,
        "data": []
      }

      selectedRows.forEach((item) => {
        postData.data.push(
          {
            "phieuIdNeo": item.PHIEU_ID_NEO,
            "trangThai": item.TRANGTHAI,
            "phieuId": item.PHIEU_ID,
            "nguoiCN": user,
            "httt": item.HTTT_ID,
            "kyCuoc": this.params.p_kycuoc,
            "maTT": item.MA_TT
          }
        )
      })


      this.loading(true)

      GachNoAPI.syncCCBS(this.axios, postData)
        .then((response) => {
          this.$toast.success('Đồng bộ VNP thành công.')
        })
        .catch((error) => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => {this.loading(false)})


    },
    /* */
    RowChangedNVTC(e)
    {
      if(e === null)
      {
        this.resultDSLuotTT = []
        this.pageinfoLuotTT.totalElement = 0
        this.resultDSPhieuTT = []
        this.pageinfoPhieuTT.totalElement = 0
        return
      }
      this.currentNVTCRow = e
      this.getDSLuotTra(e.MANV_TC)
    },
    RowChangedLuotTT(e)
    {
      if(e === null)
      {
        this.resultDSPhieuTT = []
        this.pageinfoPhieuTT.totalElement = 0
        return
      }
      this.currentLuotTTRow = e
      this.getDSPhieuTT(e.LANTRA, this.currentNVTCRow.MANV_TC, e.HTTT_ID)
    },
    RowChangedPhieuTT(e)
    {
      if(e !== null)
        this.currentPhieuTTRow = e
    },
    /*
    updateNgayTToanHandler(e)
    {
      if(e !== undefined && e !== 0)
      {
        this.getDSPhieuTT(this.currentLuotTTRow.LANTRA, this.currentNVTCRow.MANV_TC, this.currentLuotTTRow.HTTT_ID)
      }
    },
    */
    actionComplete(args){
      //console.log(args)
      if(args.action === 'edit' && args.requestType === 'save')
      {
        if(args.data.GHICHU === null || args.data.GHICHU === '' || args.data.GHICHU.trim() === '')
        {
          this.$toast.error('Chưa nhập ghi chú.')
          return false
        }
        let postData = {
          phieuId: args.data.PHIEU_ID,
          kyCuoc: this.params.p_kycuoc,
          ghiChu: args.data.GHICHU
        }
        this.loading(true)
        GachNoAPI.updateGhiChuPhieuTra(this.axios, postData)
          .then(
            (res) => {
              if(res.data.error_code === 'BSS-00000000'
              )
              {
                this.$toast.success('Cập nhật ghi chú thành công')
                this.getDSPhieuTT(this.currentLuotTTRow.LANTRA, this.currentNVTCRow.MANV_TC, this.currentLuotTTRow.HTTT_ID)
              }
            }
          )
          .catch(err => { this.$toast.error(err.data.message_detail)})
          .finally(() => {this.loading(false)})
      }
      else if(args.requestType === 'refresh')
      {
        //console.log(this.$refs.gridDSPhieuTT.$refs.grid.ej2Instances)
      }
      //console.log(this.$refs.gridDSPhieuTT.$refs.grid.ej2Instances)
    },
    actionBegin(e)
    {
      if(e.requestType === 'beginEdit')
      {
        console.log(this.$refs.gridDSPhieuTT.$refs.grid.$el.ej2_instances[0])
        console.log(this.$refs.gridDSPhieuTT.$refs.grid.$el.ej2_instances[0].getColumnByField("STT"))
        this.$refs.gridDSPhieuTT.$refs.grid.$el.ej2_instances[0].columnModel[0].freeze = null
        this.$refs.gridDSPhieuTT.$refs.grid.$el.ej2_instances[0].columnModel[1].freeze = null
        this.$refs.gridDSPhieuTT.$refs.grid.$el.ej2_instances[0].columnModel[2].freeze = null
        this.$refs.gridDSPhieuTT.$refs.grid.$el.ej2_instances[0].columnModel[20].freeze = null
        //this.$refs.gridDSPhieuTT.$refs.grid.$el.ej2_instances[0].getColumnByField("STT").visible = false;
      }

      //let form_id = e.form.id
    },
    async updateDatePhieuThuHandler(flag)
    {
      ///console.log(flag)
      if(flag)  // thành công
      {
        this.getDSPhieuTT(this.currentLuotTTRow.LANTRA, this.currentNVTCRow.MANV_TC, this.currentLuotTTRow.HTTT_ID)
        //await this.doSearch();
      }
    },
    async deletePhieuThuHandler(flag)
    {
      if(flag)  // xóa thành công
      {
        //await this.doSearch();
        await this.getDanhSach()
      }
    },
    async updatePhieuThuHandler(flag)
    {
      if(flag)  // xóa thành công
      {
        await this.getDanhSach()
      }
    },
    async acceptPrintPhieuThuHandler(e){
      //let phieu_id = this.selectedRow.PHIEU_ID
      let selectedRows = this.$refs.gridDSPhieuTT.getSelectedRecords()
      let phieuCount = 0
      let seri = parseInt(e.so_seri)

      if(e.is_print === 1)
      {
        let dsPhieuCanIn = []
        //this.dsPhieuCanIn.push(this.phieuSelected[0].PHIEU_ID)
        selectedRows.forEach((item) => {
          dsPhieuCanIn.push(item.PHIEU_ID)
        })

        //this.$refs.refPrintPreview.openDialog();
        let postData = {
          kyCuoc: this.params.p_kycuoc,
          dsPhieu : dsPhieuCanIn
        }
        this.loading(true)
        try{
          const baseUrl = process.env.API;
          this.axios.post(baseUrl + "/web-thuno/api/thu-no/gach-no/bien-nhan", postData, {responseType: 'blob'})
          .then( (response) => {
            previewPrint(response.data)
          })
        }catch(err)
        {
          this.$toast.error(err.data.message_detail)
        }finally{
          this.loading(false)
        }
      }
      else
      {
        let apiBody = {
          "pKyCuoc": this.params.p_kycuoc,
          "pSeri": e.seri,
          "pQuyen": e.thung,
          "pSoSeri": 0,
          "pPhieuId": 0
        };

        for (let item of selectedRows)
        {
          apiBody.pPhieuId = item.PHIEU_ID
          apiBody.pSoSeri = seri + phieuCount

          let result = await this.updateSeriPhieuThu(apiBody);
          if (result) {
            phieuCount ++;
          }
          else {
            this.$toast.error('Không thể cập nhật phiếu thanh toán có mã TT:' + item.MA_TT);
          }
        };

        if (phieuCount === selectedRows.length) {
          this.$toast.success('Cập nhật phiếu thanh toán thành công!');
        };
        await this.getDanhSach();
      }
    },
    async updateSeriPhieuThu(apiBody) {
      this.loading(true)
      try {
        let response = await GachNoAPI.updateSeriThanhToan(this.axios, apiBody);
        return response.data.error_code === 'BSS-00000000' ? true : false;
      }
      catch (error) {
        this.$toast.error(error.data.message_detail)
        return false;
      }
      finally {
        this.loading(false)
      }
    },
  }
}
</script>
<style scoped>
.grid-status--1:before{
  content:'\e761';
}
.grid-status-0:before{
  content:'\e718';
}
.grid-status--1{
  color: red!important;
  font-weight: bold!important;
}
.grid-status-0{
  color: green!important;
  font-weight: bold!important;
}
</style>
