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
  <ejs-dialog :enableResize="true" :isModal="true" :allowDragging="true" :visible="false" :animationSettings="animationSettings" ref="dialogCapNhatThongTinKHHDDT" :position="position" :header="'Cập nhật thông tin khách hàng HDDT'" showCloseIcon="true" width="75%" target="#app .main-wrapper">
    <div class="dialogCreateIssue"> 
        <div class="list-actions-top">
          <ul class="list">
            <li>
              <a href="javascript:;" v-on:click="btnCapNhat_ItemClick">
                <span class="icon one-save"></span> Cập nhật
              </a>
            </li>           
          </ul>
        </div>
        <div class="popup-body">
          <div class="box-form">
              <div class="legend-title">Cập nhật khách hàng</div> 
              <div class="row">
                  <div class="col-sm-6 col-12">
                      <div class="info-row">
                          <div class="key w110">Tên công ty</span></div>
                          <div class="value">
                              <input type="text" class="form-control" v-model="controls.txtTenCty" >
                          </div>
                      </div>
                  </div>
                  <div class="col-sm-6 col-12">
                      <div class="info-row">
                          <div class="key w110">Tên tắt công ty</span></div>
                          <div class="value">
                              <input type="text" class="form-control" v-model="controls.txtTenTat" >
                          </div>
                      </div>
                  </div>
              </div>
               <div class="row">
                  <div class="col-sm-6 col-12">
                      <div class="info-row">
                          <div class="key w110">Địa chỉ (Tỉnh/TP)</span></div>
                          <div class="value">
                              <input type="text" class="form-control" v-model="controls.txtDiaChi" >
                          </div>
                      </div>
                  </div>
                  <div class="col-sm-6 col-12">
                      <div class="info-row">
                          <div class="key w110">Người đại diện</span></div>
                          <div class="value">
                              <input type="text" class="form-control" v-model="controls.txtNguoiDaiDien" >
                          </div>
                      </div>
                  </div>
              </div>
              <div class="row">
                <div class="col-sm-6 col-12">
                      <div class="info-row">
                          <div class="key w110">Email</span></div>
                          <div class="value">
                              <input type="text" class="form-control" v-model="controls.txtEmail" >
                          </div>
                      </div>
                  </div>
                  <div class="col-sm-6 col-12">
                    
                      <div class="info-row">
                          <div class="key w110">Điện thoại</span></div>
                          <div class="value">
                              <input type="text" class="form-control" v-model="controls.txtSoDT" >
                          </div>
                      </div>
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
  
export default {
  components: { 
    moment,
    DatePicker, 
    api,  
  },
  name: 'CapNhatThongTinKHHDDT',
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
        nguoi_cn: this.$root.token.getUserName()
      },
       
      controls: {
         
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
        hdtb_id: data.hdtb_id 
      };
      console.log(this.state);
      this.$refs.dialogCapNhatThongTinKHHDDT.show();
      this.pageLoad();
    },
    hideDialog () {
      this.$refs.dialogCapNhatThongTinKHHDDT.hide()
    },
    
    async pageLoad(){
      try{ 
        this.loading(true)
        this.tt_nd.may_cn = await this.$root.token.getMachine();
        this.tt_nd.ip_cn = await this.$root.token.getIP();
      
        let lst = this.GetData(await api.lay_thongtin_tao_hddt(this.axios, {params: {vhdtb_id: this.state.hdtb_id}})); 
        lst = this.LowerCasePropertyList(lst);
        if(lst && lst.length > 0){
          this.controls.txtDiaChi = lst[0].diachi_tb;
          this.controls.txtEmail = lst[0].email;
          this.controls.txtNguoiDaiDien = lst[0].represent_person;
          this.controls.txtSoDT = lst[0].tel;
          this.controls.txtTenCty = lst[0].company_name;
          this.controls.txtTenTat = lst[0].short_name; 
        } 
      }catch(ex){
        console.error(ex);
        this.$toast.error(ex);
      }
      this.loading(false) 
    },  
             
    async btnCapNhat_ItemClick(e){
      try{ 
        this.loading(true)
        let lst = this.GetData(await api.lay_thongtin_tao_hddt(this.axios, {params: {vhdtb_id: this.state.hdtb_id}})); 
        lst = this.LowerCasePropertyList(lst);

        let kh_hddt = {}
        if(lst && lst.length > 0){
          kh_hddt.address = lst[0].diachi_tb;
          kh_hddt.company_name = lst[0].company_name;
          kh_hddt.email = lst[0].email;
          kh_hddt.is_sme = false;
          kh_hddt.org = lst[0].org;
          kh_hddt.represent_person = lst[0].represent_person;
          kh_hddt.short_name = lst[0].short_name;
          kh_hddt.system_type = lst[0].system_type;
          kh_hddt.taxcode = lst[0].taxcode;
          kh_hddt.tel = lst[0].tel;
          kh_hddt.cus_id = lst[0].cus_id;
          kh_hddt.address = txtdiachi.text;
          kh_hddt.company_name = txttencty.text;
          kh_hddt.email = txtemail.text;
          kh_hddt.tel = txtsodt.text;
        }


        if (!kh_hddt.cus_id || kh_hddt.cus_id == "")
        {
            this.$toast.error("Khách hàng chưa được tạo trên ĐHSX");
            this.loading(false)
            return;
        }

        let kq = this.GetData(await api.CapNhatKhachhang(this.axios, kh_hddt)); 
        if(kq && kq == "ok"){
          this.$toast.success("Cập nhật khách hàng thành công");
        }
        else{
          this.$toast.error(kq);
        }

      }catch(ex){
        console.error(ex);
        this.$toast.error(ex);
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