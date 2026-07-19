<style scoped>
.list-actions-top .list > li > div.button {
    display: block;
    padding: 2px 15px;
    color: #0176FF;
    border-left: 1px solid #BAE7FF;
    text-decoration: none;
    font-weight: 600;
    cursor: pointer;
}
</style>

<template>
  <ejs-dialog :enableResize="true" :isModal="true" :allowDragging="true" :visible="false" :animationSettings="animationSettings" ref="dialogKiemTraSoGiayToCA" :position="position" :header="'Kiểm tra thông tin giấy tờ CA, SmartCA'" showCloseIcon="true" width="75%" target="#app .main-wrapper">
    <div class="dialogCreateIssue"> 
        <div class="list-actions-top">
          <ul class="list">
            <li>
              <a href="javascript:;" v-on:click="tsbtnGhiLai_ItemClick">
                <span class="icon one-save"></span> Ghi lại
              </a>
            </li>           
          </ul>
        </div>
        <div class="popup-body">
          <div class="box-form">
          <div class="row">
              <div class="col-sm-6 col-12"> 
                <div class="info-row">
                    <div class="key">
                        Mã GD
                    </div>
                    <div class="value">
                        <input ref="txtMaGD" type="text" class="form-control" v-model="controls.txtMaGD" readonly="true">
                    </div> 
                </div> 
              </div> 
            </div>
            </div>
            <div class="row"> 
              <div class="box-form">
              <div class="table-content">
                  <DataGrid class="table-result table-gachle"
                      v-bind:columns="controls.gdvDSGiayTo.headers"
                      v-bind:dataSource="controls.gdvDSGiayTo.list"
                      :enable-paging-server="false"
                      :allowPaging="true"
                      :showFilter="true">
                  </DataGrid>
              </div> 
            </div>
          </div> 
      </div> 
    </div>
  </ejs-dialog>
</template>
<script> 
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import moment from 'moment' 
import api from './PopUpApi'  
import commonFn from "@/modules/contract/setup/ChangeLandlineIMSType/CommonFuntions"; 
  
export default {
  components: { 
    moment,
    DatePicker, 
    api,  
  },
  name: 'KiemTraSoGiayToCA',
  data() {
    return {
      Loading: false,
      dateFormat: 'DD/MM/YYYY',
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
      state: { 
      }, 
      tt_nd: {
        nhanvien_id:  this.$root.token.getNhanVienID() * 1,
        nguoidung_id: this.$root.token.getNguoiDungID() * 1,
        phanvung_id: this.$root.token.getPhanVungID() * 1,
        donvi_id: this.$root.token.getDonViID() * 1,
        may_cn: this.$root.token.getMachine(),
        ip_cn: this.$root.token.getIP(),
        ngay_cn: moment(new Date()).format("DD/MM/YYYY hh:mm A"),
        ngay_bg: moment(new Date().setSeconds(new Date().getSeconds() + 30)).format(
          "DD/MM/YYYY hh:mm A"
        ),
        ten_dv: "",
        nguoi_cn: this.$root.token.getUserName(),
        ma_nd: this.$root.token.getUserName(),
      },
       
      controls: {
        txtMaGD: "",
        rpsCboLoaiGT: {
          list: [],
          value: 0
        },
        gdvDSGiayTo: {
          headers: [
            {fieldName: 'so_gt', headerText: 'Số giấy tờ', allowFiltering: true},
            {fieldName: 'loaigt_id', headerText: 'Loại giấy tờ', allowFiltering: true},
            {fieldName: 'ngayhethan_gt', headerText: 'Ngày hết hạn GT', allowFiltering: true, type: "datetime", format: "dd/MM/yyyy HH:mm:ss", textAlign: 'right', width: '148'},
            {fieldName: 'ma_tb', headerText: 'Mã TB', allowFiltering: true},
            {fieldName: 'ten_tb', headerText: 'Tên TB (CN)', allowFiltering: true},
            {fieldName: 'username', headerText: 'Username', allowFiltering: true},
            {fieldName: 'chucdanh', headerText: 'Chức danh', allowFiltering: true},
            {fieldName: 'email', headerText: 'Email', allowFiltering: true},
          ],
          list: [],
        }
      }
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
    openDialog(data) { 
      if(!data){
        return;
      }
    
      this.state =  { 
        ma_gd: data.ma_gd ? data.ma_gd: -1, 
      };
      console.log(this.state);
      this.$refs.dialogKiemTraSoGiayToCA.show();
      this.pageLoad();
    },
    hideDialog () {
      this.$refs.dialogKiemTraSoGiayToCA.hide()
    },
    
    async pageLoad(){
      try{ 
        this.loading(true)
        this.tt_nd.may_cn = await this.$root.token.getMachine();
        this.tt_nd.ip_cn = await this.$root.token.getIP();
      
        await this.LoadDulieu();
      }catch(ex){
        commonFn.showException(this, ex);
      }
      this.loading(false) 
    },  
    async LoadDulieu(){
      this.controls.txtMaGD = this.state.ma_gd;

      let dt2 = this.GetData(await api.sp_loadthongtinloaigt(this.axios, {params: {ma_gd: this.state.ma_gd}}));
      if(dt2 && dt2.length > 0){
        this.controls.rpsCboLoaiGT.list = dt2.map((x) => {return { id: x.loaigt_id, text: x.loai_gt }});
        this.controls.rpsCboLoaiGT.value = this.controls.rpsCboLoaiGT.list[0].id;
      }
       
      let lst = this.GetData(await api.xem_ds_giayto_theo_magd_ca(this.axios, {ma_gd: this.state.ma_gd}));
      this.controls.gdvDSGiayTo.list = lst;
    },      
    async tsbtnGhiLai_ItemClick(e){
      try{ 
        let data = this.UpperCasePropertyList(this.controls.gdvDSGiayTo.list);
        let req = {
            "data": JSON.stringify(data),
            "ma_gd": this.state.ma_gd
        }
        let kq = this.GetData(await api.capnhat_giayto_ca_hangloat(this.axios, req));
        if(kq == "ok")
          this.$toast.success("Cập nhật thành công");
        else
          this.$toast.error("Cập nhật thất bại: " + kq);
         
      }catch(ex){
         commonFn.showException(this, ex);
      }
      this.loading(false)      
       
    },
    GetData: function (response) { 
      if (response.data.error === 200 || response.data.error === '200' || response.data.error === '204' || response.data.errorCode === 0) {
        return response.data.data
      } else {
        console.log(response.data.error_code)
        console.log(response.data.faultString)
        if(response.data && response.data.faultString){
          throw response.data.faultString;
        }
      }
      return null
    },
    
    ShowAlert: function (msg) {
      this.$toast.warning(msg)
    },
    ShowSuccess: function (msg) {
      this.$toast.success(msg)
    },
    ShowError: function (msg) {
      this.$toast.error(msg)
    },
    LowerCasePropertyList(obj) {
      if(!obj) return [];
      return obj.map( function( item ){
          for(var key in item){
              var upper = key.toLowerCase();
              // check if it already wasn't uppercase
              if( upper !== key ){
                  item[ upper ] = item[key];
                  delete item[key];
              }
          }
          return item;
      });
    },
    UpperCasePropertyList(obj) {
      if(!obj) return [];
      return obj.map( function( item ){
          for(var key in item){
              var upper = key.toUpperCase();
              // check if it already wasn't uppercase
              if( upper !== key ){
                  item[ upper ] = item[key];
                  delete item[key];
              }
          }
          return item;
      });
    },
  }, 
}
</script>

<style>
.chuyenTo .page-content {
  top: 130px !important;
}
</style>
