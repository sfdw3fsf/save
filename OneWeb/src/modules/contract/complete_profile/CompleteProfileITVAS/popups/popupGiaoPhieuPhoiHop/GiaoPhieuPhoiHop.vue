<template>
  <ejs-dialog :enableResize="true" :allowDragging="true" :isModal="true" :visible="false" :animationSettings="animationSettings" ref="dialogGiaoPhieuPhoiHop" :position="position" :header="'Giao phiếu phối hợp'" showCloseIcon="true" width="80%" target="#app .main-wrapper">
    <div class="">
      
      <div class="list-actions-top">
          <ul class="list">
              <li>
                  <a href="#" @click.prevent="tsbtnNapLai_Click()">
                      <span  class="icon nc-icon-glyph files_refresh"></span> Nạp lại
                  </a>
              </li>
              
          </ul>
      </div>
      <div class="popup-body">
                    <div class="box-form">
                        <div class="legend-title">Giao phiếu</div>
                        <div class="info-row">
                            <div class="key w150">
                                Đơn vị phối hợp
                            </div>
                            <div class="value">
                                <div class="select-custom"> 
                                    <select2 :options="controls.cboTOKT.list" v-model="controls.cboTOKT.value" :disabled='false'>
                                    </select2>
                                </div>
                            </div>
                            <div class="value w80 nowrap">
                                <button class="btn btn-second" style="border-color: rgb(1, 118, 255);" v-on:click="btnGiaoPhieuPH_Click" :class="{'disabled':(!controls.btnGiaoPhieuPH.enabled)}">Giao phiếu</button>
                            </div>
                        </div>
                    </div>
                    <div class="box-form">
                        <div class="legend-title">Danh sách phiếu đã giao</div>
                        <div class="table-content">
                             <DataGrid v-bind:columns="DsPhieu.headers" v-bind:dataSource="DsPhieu.data" :showColumnCheckbox="false" :enable-paging-server="false" :allowPaging="true" :showFilter="true" 
                                :enabledSelectFirstRow="false"
                                @queryCellInfo="(x) => { x.data.nhiemvu == 'Thi công và nghiệm thu' ?  x.cell.setAttribute('style', x.cell.getAttribute('style') + ';font-weight: bold') : '' }"
                              > 
                             </DataGrid>
                        </div> 
                    </div>
                    
                </div> 
    </div>
  </ejs-dialog>
</template>
<script>
import XLSX from 'xlsx'
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import moment from 'moment' 
import api2 from '@/modules/contract/complete_profile/CompleteProfileITVAS/popups/popupGiaoPhieuPhoiHop/PopUpApi'
import commonFn from "@/modules/contract/setup/ChangeLandlineIMSType/CommonFuntions";
import Vue from 'vue'
import { 
  LOAIHINH_THUEBAO, 
} from "@/constants";
import api from '../../../../../CABMAN/BanDoMangCap/api'

export default {
  components: {
    XLSX,
    moment,
    DatePicker, 
    api2
  },
  name: 'GiaoPhieuPhoiHop',
  data() {
    return {
      Loading: false,
      dateFormat: 'DD/MM/YYYY HH:mm:ss',
      dateFormatMonth: 'MM/YYYY',
      typeFormat: 'datetime',
      typeFormatMonth: 'month',
      dateConfig: {
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y'
      },
      target: '#app', //  .main-wrapper-payment
      targetTop: '#app', // document.querySelector('body'),.main-wrapper
      animationSettings: { effect: 'Zoom' },
      position: { X: 'center', Y: 'top' },
      showTimePanel: false,
      tabIndex: 0,
      tt_nd: {
        nhanvien_id: this.$root.token.getNhanVienID() * 1,
        nguoidung_id: this.$root.token.getNguoiDungID() * 1,
        phanvung_id: this.$root.token.getPhanVungID() * 1,
        donvi_id: this.$root.token.getDonViID() * 1,
        may_cn: this.$root.token.getMachine(),
        ip_cn: this.$root.token.getIP(),
        ngay_cn: moment(new Date()).format("DD/MM/YYYY"),
        ngay_bg: moment(new Date().setSeconds(new Date().getSeconds() + 30)).format(
          "DD/MM/YYYY"
        ),
      },
      state: {},
      controls: {
        btnGiaoPhieuPH:{ enabled: false,  type: 'button'},

        cboTOKT: {
          list: [],
          value: 0,
          type: 'droplist'
        },
      }, 
      
      DsPhieu: {
        headers: [
          {
            fieldName: 'phieu_id',
            headerText: 'ID',
            allowFiltering: true,
            allowSorting: true,
            textAlign: 'left',
            width: '148'
          },
          {
            fieldName: 'ngaygiao',
            headerText: 'Ngày giao',
            allowFiltering: true,
            allowSorting: false,
            type: 'datetime',
            format: 'dd/MM/yyyy',
            textAlign: 'right',
            width: '120',
          },
          {
            fieldName: 'nguoigiao',
            headerText: 'Người giao',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'nhiemvu',
            headerText: 'Nhiệm vụ',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ten_dv',
            headerText: 'Đơn vị nhận',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'trangthai_ph',
            headerText: 'Trạng thái phiếu',
            allowFiltering: true,
            allowSorting: false
          },
          { 
            fieldName: '', headerText: 'Hủy giao',
            allowFiltering: false,
            allowSorting: false,
            textAlign: 'center',
            width: 90,
            template: function () {
              return {
                template: Vue.component('btnHuyGiaoTemplate', {
                  template: `<button class="btn btn-second" style="border-color: red" @click="onHuyGiao"><span class="icon nc-icon-glyph ui-1_simple-remove"></span></button>`,
                  data () {
                    return {data: {}}
                  },
                  computed: {
                    parent () {
                      return this.$parent.$parent.$parent.$parent
                    }
                  },
                  methods: {
                    onHuyGiao (e) { 
                      this.parent.ribeHuyGiao_ButtonClick(this.data)
                    }
                  }
                })
              }
            }
          },
        ],
        data: []
      }, 
      Tag: ''
    }
  },
  created: async function () {
     
  },
  mounted: function () { 
    
  },
  watch: {
     
  },
  methods: {
    
    dialogClose() {},
    openDialog(phieu_id, kieu_id) {
      this.state.phieu_id = phieu_id
      this.state.kieu_id = kieu_id
       
      console.log('this.$refs.dialogGiaoPhieuPhoiHop.show()', this.state.phieu_id);
 
      this.$refs.dialogGiaoPhieuPhoiHop.show();
      this.pageLoad();
    },
    async pageLoad(){
      try{ 
        this.loading(true)
        await this.NAP_THONGTIN_PHIEU_HIENTAI();
        if (this.state.CURRENT_PHIEU == null) {
          this.loading(false);
          return;
        }
        await this.NAP_DS_PHIEU();
        await this.NAP_DS_TOKT();
      }catch(ex){
        commonFn.showException(this, ex);
      }
      this.loading(false)    
    }, 
    async NAP_THONGTIN_PHIEU_HIENTAI(){
      var input = {  "phieu_id": this.state.phieu_id }
      let rs = this.GetData(await api2.sp_nap_thongtin_phieu_hientai(this.axios, input));
      if(rs && rs.length > 0){
        this.state.CURRENT_PHIEU = rs[0]; 
        this.state.IS_PHIEUCHINH = this.state.CURRENT_PHIEU["is_phieuchinh"] == "1"; 
        this.state.hdtb_id =  this.state.CURRENT_PHIEU["hdtb_id"]*1;
        this.controls.btnGiaoPhieuPH.enabled = this.state.IS_PHIEUCHINH;  
      }
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') { 
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return null
    },
    async NAP_DS_PHIEU(){
      var input = {  "phieu_id": this.state.phieu_id }
      let data = this.GetData(await api2.sp_nap_ds_phieu_giaophieuphoihop(this.axios, input));
      this.DsPhieu.data = data; 
    },
    async NAP_DS_TOKT(){ 
      if (this.state.kieu_id == 0)
      {
          var rs = this.GetData(await api2.sp_lay_ds_tokt_theo_nv(this.axios, {"vnhanvien_id": this.tt_nd.nhanvien_id, "vdonvi_id": this.tt_nd.donvi_id}));
          
          if(rs && rs.length > 0){
            this.controls.cboTOKT.list = rs.map((x) => {
              return {id: x.donvi_id, text: x.ten_dv};
            });
            this.controls.cboTOKT.value = rs[0].donvi_id;  
          } 
      }
      else{ 
        //let vloaitb_id = await commonFn.MAP_ID(this.axios, "loaitb_id", "css.hd_thuebao", "where hdtb_id = " + this.state.hdtb_id);        
        //let vloaitb_id = commonFn.GetData(await api.fn_tt_hd_thuebao(this.axios, { param: this.state.hdtb_id, type: 4 }))
        
        let vloaitb_id = await api2.lay_loaitb_id_theo_hopdong(this.axios, { "vhdtb_id": this.state.hdtb_id});
        if (vloaitb_id == LOAIHINH_THUEBAO.IT_CARE){
          var rs = this.GetData(await api2.sp_lay_ds_dvtc_theo_phieu_id(this.axios, {"vphieu_id": this.state.phieu_id, "vloaidv_id": 27 /*LOAI_DV.THICONG_CNTT*/}));
          if(rs && rs.length > 0){
            this.controls.cboTOKT.list = rs.map((x) => {
              return {id: x.donvi_id, text: x.ten_dv};
            });
            this.controls.cboTOKT.value = rs[0].donvi_id;  
          } 
        }
        else{
          var rs = this.GetData(await api2.sp_lay_ds_dvtc_theo_phieu_id(this.axios, {"vphieu_id": this.state.phieu_id, "vloaidv_id": 27}));
          if(rs && rs.length > 0){
            this.controls.cboTOKT.list = rs.map((x) => {
              return {id: x.donvi_id, text: x.ten_dv};
            });
            this.controls.cboTOKT.value = rs[0].donvi_id;  
          } 
        }
      }
    },
    async KIEMTRA_GIAOPHIEU(){
      if (!this.state.donvi_id || this.state.donvi_id < 0) {
          this.ShowError("Chưa chọn đơn vị nhận phiếu.");
          return false;
      }
      //Ko dc chọn đơn vị của phiếu hiện tại
      if (this.state.donvi_id.toString() == this.state.CURRENT_PHIEU["donvi_nhan_id"].toString())
      {
          this.ShowError("Đơn vị nhân phiếu phải khác đơn vị hiện tại.");
          return false;
      }
     
      var input = {  
                      "phieu_id": this.state.phieu_id,
                      "hdtb_id": this.state.CURRENT_PHIEU["hdtb_id"],
                      "huonggiao_id": this.state.CURRENT_PHIEU["huonggiao_id"],
                      "donvi_id": this.state.donvi_id,
                      "ttph_id": 1,
                  }
      let data = this.GetData(await api2.fn_kiemtra_giaophieuphoihop(this.axios, input));
 
      let toKT = this.controls.cboTOKT.list.filter(x=>x.id == this.controls.cboTOKT.value)[0].text; //cboTOKT.Text
      if (!data || data != "OK")
      {
          this.ShowError("Đơn vị "+ toKT +" đang có phiếu chưa thực hiện, không thể giao phiếu thêm.");
          return false;
      }
      return true;
    },
    async GIAOPHIEU_PHOIHOP(){
      //TODO: Bán chéo
      let reqData = {
          "vphieu_id": this.state.phieu_id,
          "vnhanvien_id": this.tt_nd.nhanvien_id,
          "vnoidung_giao": "Giao phiếu phối hợp thi công",
          "vdonvi_nhan_id": this.state.donvi_id
      }
      try{ 
        let data = this.GetData(await api2.giaophieu_phoihop_thicong(this.axios, reqData)); 
        return true;
      }
      catch(ex){
        commonFn.showException(this, ex);
        return false;
      }
    },
    async XOA_PHIEU_PHOIHOP(phieu_id){
      try{ 
        this.loading(true)
        let res = this.GetData(await api2.fn_huy_giao_giaophieuphoihop(this.axios, {phieu_id: phieu_id})) 
        if(res != 1 && res != "ok" && res != "OK")
          this.$toast.error(res);
        else{
          this.$toast.success("Hủy phiếu thành công!");
        }
        await this.NAP_DS_PHIEU();
      }catch(ex){
         commonFn.showException(this, ex);
      }
      this.loading(false) 
    },
    async btnGiaoPhieuPH_Click(){
      this.state.donvi_id = this.controls.cboTOKT.value*1;
      try{ 
        this.loading(true)
        if (await this.KIEMTRA_GIAOPHIEU() == false) {
          this.loading(false)
          return;
        }
        this.loading(false)
        let toKT = this.controls.cboTOKT.list.filter(x=>x.id == this.controls.cboTOKT.value)[0].text; //cboTOKT.Text
        this.$bvModal.msgBoxConfirm("Giao phiếu phối hợp cùng "+toKT+" ?", {
            title: "",
            size: "sm",
            okTitle: "Đồng ý",
            cancelTitle: "Hủy",
            centered: true,
          })
          .then(async (v) => {
            if (!v) {
              return;
            }

            this.loading(true);
            if (await this.GIAOPHIEU_PHOIHOP() == true) {
                this.ShowSuccess("Giao phiếu thành công.");
                await this.NAP_DS_PHIEU();
            } 

            this.loading(false);
          }); 
      }catch(ex){
         commonFn.showException(this, ex);
      }
      this.loading(false)      
      
    },
    async ribeHuyGiao_ButtonClick(e){
      let phieuhuy_id = e.phieu_id;
      console.log('ribeHuyGiao_ButtonClick', phieuhuy_id);

      const isConfirm = await this.$bvModal.msgBoxConfirm(
        `Bạn thật sự muốn hủy phiếu đã giao ?`,
        {
          title: "Thông báo",
          size: "sm",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
          centered: true,
        }
      );
 
      if(isConfirm){  
        await this.XOA_PHIEU_PHOIHOP(phieuhuy_id); 
      }
    },
    async tsbtnNapLai_Click(){
      try{ 
        this.loading(true)
        await this.NAP_THONGTIN_PHIEU_HIENTAI();
        await this.NAP_DS_PHIEU();
      }catch(ex){
         commonFn.showException(this, ex);
      }
      this.loading(false) 
    },
    ShowAlert: function (msg) {
      this.$toast.warning(msg)
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

<style>
.chuyenTo .page-content {
  top: 130px !important;
}
</style>