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
  <ejs-dialog :enableResize="true" :isModal="true" :allowDragging="true" :visible="false" :animationSettings="animationSettings" ref="dialogHDTB_SUB_V3" :position="position" :header="'Thông tin thuê bao bổ sung'" showCloseIcon="true" width="50%" target="#app .main-wrapper">
    <div class=""> 
        <div class="list-actions-top">
          <ul class="list">
            <li>
              <a href="javascript:;" v-on:click="btnGhiLai_Click">
                <span class="icon one-save"></span> Ghi lại
              </a>
            </li>  
          </ul>
        </div>
        <div class="popup-body">
          <div class="box-form"> 
             
              <div class="info-row">
                    <div class="key w110">Người ký tờ khai</div>
                  <div class="value">
                      <input type="text" class="form-control" v-model="txtNguoiKyHoSo" :disabled="!controls.txtNguoiDaiDien.enabled"> 
                  </div>
              </div> 
              <div class="info-row">
                  <div class="key w110">Chức danh</div>
                  <div class="value">
                      <input type="text"  class="form-control" v-model="txtChucDanh" :disabled="!controls.txtChucDanh.enabled">
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
  name: 'HDTB_SUB_V3',
  data() {
    return {
      loading: false,
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
      txtNguoiKyHoSo: "",
      txtChucDanh: "", 
      controls: {
        txtNguoiDaiDien: {
          enabled: true,
          readOnly: false,
        },
        txtChucDanh: {
          enabled: true,
          readOnly: false,
        },
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
        this.$toast.error("Chưa có dữ liệu khởi tạo!");
        return;
      }
 
      this.state =  {
        hdtb_id: data.hdtb_id,
        isReadonly: data.isReadonly,
        isUpdate: data.isUpdate,
        loaitb_id: data.loaitb_id,
        kieuld_id: data.kieuld_id,
        ds_hdtb_sub: data.ds_hdtb_sub, 
      };

       console.log('openDialog', this.state);
      
      this.$refs.dialogHDTB_SUB_V3.show();
      this.pageLoad();
    },
    async pageLoad(){
      let data = this.GetData(await api.lay_tt_thuebao_bosung(this.axios, {"vhdtb_id": this.state.hdtb_id}));
     
      if(data && data.length > 0){
        this.txtNguoiKyHoSo = data[0]["nguoi_ky_hoso"];
        this.txtChucDanh = data[0]["chucdanh"];
        console.log(this.txtNguoiKyHoSo);
      }

      if (this.state.isReadonly == false)
      {
          this.controls.txtNguoiDaiDien.enabled = true;
          this.controls.txtChucDanh.enabled = true;
      }
      else
      {
          let khoaform = this.GetData(await api.ivan_khoa_form_thongtin_bosung(this.axios, {vkieuld_id: this.state.kieuld_id})); 
          if(khoaform){
            let arrControls = khoaform.split(',');
            for(const c of arrControls){
              if(this.controls[c]) 
                this.controls[c].enabled = false;
            }
          } 
      }

      if (!this.state.ds_hdtb_sub) return;
            
       console.log("ds_hdtb_sub", this.state.ds_hdtb_sub);
      if(this.state.ds_hdtb_sub.length > 0)
      {
         
          this.txtNguoiKyHoSo = this.state.ds_hdtb_sub[0]["nguoi_ky_hoso"];
          this.txtChucDanh = this.state.ds_hdtb_sub[0]["chucdanh"];
      }
    }, 
    async  btnGhiLai_Click(){
       if (!this.txtNguoiKyHoSo || this.txtNguoiKyHoSo == "" || !this.txtChucDanh || this.txtChucDanh == "")
        {
            this.$toast.error("Bạn chưa nhập đủ thông tin");
            return;
        }
        if (this.state.isUpdate || this.state.hdtb_id != 0){
          let req = {
              "vhdtb_id": this.state.hdtb_id,
              "vnguoi_ky_hoso": this.txtNguoiKyHoSo,
              "vchucdanh": this.txtChucDanh,
              "vis_update": 1,
          }
          let res = this.GetData(await api.fn_an_capnhat_hdtb_sub_v3(this.axios, req)); 
          if(res)
            this.$toast.success(res);
          else
            this.$toast.error("Cập nhật lỗi");
        } 
    },
    clearForm(){
      this.txtChucDanh = "";
      this.txtNguoiKyHoSo = ""; 
      this.state.hdtb_id = 0
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
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
    }
  }, 
}
</script>

<style>
.chuyenTo .page-content {
  top: 130px !important;
}
</style>