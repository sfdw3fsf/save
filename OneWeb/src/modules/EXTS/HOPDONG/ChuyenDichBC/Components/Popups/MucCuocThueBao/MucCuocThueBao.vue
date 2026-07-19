<template>
  <div>
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a @click="tsbtnCapNhat_Click">
            <span class="icon one-save"></span>Cập nhật
          </a>
        </li>
      </ul>

    </div>
    <div class="page-content">
      <div class="legend-title">Thông tin mức cước thuê bao</div>
      <div class="row">
        <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w70">
                Từ ngày
              </div>
              <div class="value">
                <div class="input-icon-right">
                  <vue-date :format="'DD/MM/YYYY'" v-model="dtpNgayBD"  />
                </div>
              </div>

          </div>
        </div>
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w70">
              Đến ngày
            </div>
            <div class="value">
              <div class="input-icon-right">
                <vue-date :format="'DD/MM/YYYY'"  v-model="dtpNgayKT" />
              </div>
            </div>

          </div>
        </div>
    </div>
      <div class="row">
        <div class="col-sm-6 col-12">
          <div class="box-form">
            <div class="legend-title" ><span @click="clickShowHideDoiTuongAndTrangBi" class="fa icon" :class="{'fa-angle-up':expanded.row2, 'fa-angle-down':!expanded.row2,}"></span>Đối tượng</div>
            <div class="table-content">
              <DataGrid
                v-bind:dataSource="dgvDoiTuongTB_DaGan.list"
                v-bind:columns="dgvDoiTuongTB_DaGan.cols"
                :allowPaging="true"
                ref="dgvDoiTuongTB_DaGan"
                :showFilter="true"
                :enablePagingServer="false"
                :enabledSelectFirstRow="true"
                :panelDataHeight="'200'"
                @rowSelected="grvDoiTuongTB_DaGan_FocusedRowChanged"
                v-show="expanded.row2"
              />
            </div>
          </div>
        </div>
        <div class="col-sm-6 col-12">
          <div class="box-form">
            <div class="legend-title"><span @click="clickShowHideDoiTuongAndTrangBi" class="fa icon" :class="{'fa-angle-up':expanded.row2, 'fa-angle-down':!expanded.row2,}"></span>Trang bị</div>
            <div class="table-content">
              <DataGrid
                v-bind:dataSource="dgvTrangBi_DaGan.list"
                v-bind:columns="dgvTrangBi_DaGan.cols"
                :allowPaging="true"
                ref="dgvTrangBi_DaGan"
                :showFilter="true"
                :enablePagingServer="false"
                :enabledSelectFirstRow="true"
                :panelDataHeight="'200'"
                @rowSelected="grvTrangBi_DaGan_FocusedRowChanged"
                v-show="expanded.row2"
              />
            </div>
          </div>
        </div>
      </div>
      <div class="box-form">
        <div class="legend-title"><span @click="clickShowHideDanhSach" class="fa icon" :class="{'fa-angle-up':expanded.row3, 'fa-angle-down':!expanded.row3,}"></span>  Danh sách mức cước thuê bao</div>
        <div class="table-content">
          <DataGrid
            v-bind:dataSource="dgvDanhSach.list"
            v-bind:columns="dgvDanhSach.cols"
            :allowPaging="true"
            ref="dgvTrangBi_DaGan"
            :showFilter="true"
            :enablePagingServer="false"
            :enabledSelectFirstRow="true"
            :panelDataHeight="'100'"
            @rowSelected="grvDanhSach_FocusedRowChanged"
            v-show="expanded.row3"
          />
        </div>
      </div>
  </div>
  </div>

</template>

<script>
import api from './api'
import '@/assets/vendor/jquery/split'
import Vue from 'vue'
import moment from 'moment'
import {DialogPlugin} from '@syncfusion/ej2-vue-popups'
import {LoaiHopDong, DichVuVienThong, TrangThaiHD, LOAI_DV, TrangThaiTB, LoaiHinhTB} from '@/const/enums'

Vue.use(DialogPlugin)
export default {
name: "MucCuocThueBao",
  components: {
    LoaiHopDong, DichVuVienThong, TrangThaiHD, LOAI_DV, TrangThaiTB, LoaiHinhTB
  },
  props:{
    tinhtc:{
      type:Number,
      default:0,
    },
    vtocdo_id:{
      type:Number,
      default:0,
    },
  },
  data: function () {
    return {

      muccuoctb_id : 0,
      vdichvuvt_id : 4,
      bangts_tc : [],
      dtMucCuocTB : [],
      dtDoiTuong : [],
      dtTrangBi : [],
      trangbi_id:0,doituong_id:0,
      expanded:{
        "row2":true,
        "row3":true,
      },
      dtpNgayBD:"",dtpNgayKT:"",
      dgvDoiTuongTB_DaGan:{
        list:[],
        cols:[
          {
            fieldName: "doituong_id",
            headerText: "",
            allowFilter: true,
            allowSorting: false,
            visible:false,
          },
          {
            fieldName: "ten_dt",
            headerText: "đối tượng",
            allowFilter: true,
            allowSorting: false,

          },
        ],
        SelectedValue:null,
      },
      dgvTrangBi_DaGan:{
        list:[],
        cols:[
          {
            fieldName: "trangbi_id",
            headerText: "",
            allowFilter: true,
            allowSorting: false,
            visible:false,
          },
          {
            fieldName: "tentrangbi",
            headerText: "trang bị",
            allowFilter: true,
            allowSorting: false,

          },
        ],
        SelectedValue:null,
      },
      dgvDanhSach:{
        list:[],
        cols:[
          {
            fieldName: "id",
            headerText: "muccuoctb_id",
            allowFilter: true,
            allowSorting: false,

          },
          {
            fieldName: "muccuoc",
            headerText: "Tên mức cước",
            allowFilter: true,
            allowSorting: false,

          },
          {
            fieldName: "cuoc_tb",
            headerText: "Cước TB",
            allowFilter: true,
            allowSorting: false,

          },
          {
            fieldName: "cuockhoan",
            headerText: "Cước khoán",
            allowFilter: true,
            allowSorting: false,

          },
          {
            fieldName: "cuoc_ll",
            headerText: "Cước lưu lượng",
            allowFilter: true,
            allowSorting: false,

          },
          {
            fieldName: "cuoc_tg",
            headerText: "Cước trọn gói",
            allowFilter: true,
            allowSorting: false,

          },
        ],
        SelectedValue:null,
      }
    }
  },
  methods:{
    clickShowHideDoiTuongAndTrangBi(){
      this.expanded.row2=!this.expanded.row2
    },
    clickShowHideDanhSach(){
      this.expanded.row3=!this.expanded.row3
    },
    frmMucCuocTB_TC_Load(){
      this.HienThiDanhSach();
    },
    async HienThiDanhSach(){
      try{
        let response = await api.lay_muccuoc_tb(this.axios,{
          "tinhthicong_id": this.tinhtc,
          "dichvuvt_id": this.vdichvuvt_id,
          "loaitb_id": 0,
          "tocdo_id": this.vtocdo_id
        })
        if(response.data.error_code === 'BSS-00000000' && response.data.data){
          this.dgvDanhSach.list = response.data.data;
        }
        else{
          this.dgvDanhSach.list = [];
        }
      }
      catch (e){
        console.log(e);
        this.dgvDanhSach.list = [];
      }

    },
    async HienThi_TrangBi(){
      try{
        let response = await api.layds_trangbi(this.axios,{
          "tinhthicong_id": this.tinhtc,
          "muccuoc_id": this.muccuoctb_id,
          "kieu": 1
        })
        if(response.data.error_code === 'BSS-00000000' && response.data.data){
          this.dgvTrangBi_DaGan = response.data.list;
        }
        else{
          this.dgvTrangBi_DaGan = [];
        }
      }
      catch (e){
        console.log(e);
        this.dgvTrangBi_DaGan = [];
      }

    },
    HienThi_DoiTuongTB(){
      try{
        let response = api.lay_muccuoc_tb_dt(this.axios,{
          "tinhthicong_id": this.tinhtc,
          "muccuoc_id": this.muccuoctb_id,
        })
        if(response.data.error_code === 'BSS-00000000' && response.data.data){
          this.dgvDoiTuongTB_DaGan = response.data.list;
        }
        else{
          this.dgvDoiTuongTB_DaGan = [];
        }
      }
      catch (e){
        console.log(e);
        this.dgvDoiTuongTB_DaGan = [];
      }

    },
    grvDanhSach_FocusedRowChanged(args){
      try
      {
        if(args.muccuoctb_id == null || args.muccuoctb_id == "")
          this.muccuoctb_id = 0;
        else
          this.muccuoctb_id = args.muccuoctb_id;
        if (args.tungay == null || args.tungay == "")
          this.dtpNgayBD = moment(args.tungay).format("DD/MM/YYYY");
        if (args.denngay == null || args.denngay == "")
          this.dtpNgayKT = moment(args.denngay).format("DD/MM/YYYY");
        this.HienThi_DoiTuongTB();
        this.HienThi_TrangBi();
      }
      catch (e)
      {
        console.log(e);
      }
    },
    grvTrangBi_DaGan_FocusedRowChanged(args){
      if(args.trangbi_id == null || args.trangbi_id == "")
        this.trangbi_id = 0;
      else
        this.trangbi_id = args.trangbi_id;
    },
    grvDoiTuongTB_DaGan_FocusedRowChanged(args){
      if(args.doituong_id == null || args.doituong_id == "")
        this.doituong_id = 0;
      else
        this.doituong_id = args.doituong_id;
    },
    tsbtnCapNhat_Click(){
      let result = {
        "trangbi_id":0,
        "doituong_id":0,
      };
      result.trangbi_id = this.trangbi_id;
      result.doituong_id = this.doituong_id;
      this.$emit("accept",result);
    },

  },


}
</script>

<style scoped>

</style>
