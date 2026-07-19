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
  <ejs-dialog :enableResize="true" :isModal="true" :allowDragging="true" :visible="false" :animationSettings="animationSettings" ref="dialogHDTB_SUB_V2" :position="position" :header="'Thông tin thuê bao bổ sung'" showCloseIcon="true" width="50%">
    <div class=""> 
        <div class="list-actions-top">
          <ul class="list">
            <li>
              <a href="javascript:;" @click="btnGhiLai_Click">
                <span class="icon one-save"></span> Ghi lại
              </a>
            </li>  
          </ul>
        </div>
        <div class="popup-body">
          <div class="box-form"> 
            <div class="info-row">
                  <div class="key w110">Loại giấy tờ</div>
                  <div class="value">
                      <div class="select-custom">
                        <select2  :options="controls.cbLoaiGT.list" v-model="controls.cbLoaiGT.value" :disabled="!controls.cbLoaiGT.enabled"> 
                        </select2>   
                      </div>
                  </div>
            </div> 
            <div class="info-row">
                    <div class="key w110">Số giấy tờ (*)</div>
                  <div class="value">
                      <input type="text" class="form-control"  v-model="controls.tbSoGT.text"  :disabled="!controls.tbSoGT.enabled">
                  </div>
              </div>
              <div class="info-row">
                    <div class="key w110">Ngày hết hạn (*)</div>
                    <div class="value">
                        <div class="select-custom">
                            <date-picker type="date"  input-class="form-control" :format="dateFormat" :value-type="'format'"  :disabled="!controls.dtpNgayHetHan.enabled" v-model="controls.dtpNgayHetHan.value" ></date-picker> 
                        </div>
                    </div>
                </div>
              <div class="info-row">
                  <div class="key w110">Chức danh (*)</div>
                  <div class="value">
                      <input type="text"  class="form-control" :disabled="!controls.tbChucDanh.enabled" v-model="controls.tbChucDanh.text">
                  </div>
              </div>
              <div class="info-row">
                  <div class="key w110">CN (Tên TB) (*)</div>
                  <div class="value">
                      <input type="text"  class="form-control" :disabled="!controls.txtTenTB.enabled"  v-model="controls.txtTenTB.text">
                  </div>
              </div>
              <div class="info-row">
                  <div class="key w110">O/OU(Tên KH)(*)</div>
                  <div class="value">
                      <input type="text"  class="form-control" :disabled="!controls.txtTenKH.enabled" v-model="controls.txtTenKH.text">
                  </div>
              </div>
              <div class="info-row">
                  <div class="key w110">Người đại diện</div>
                  <div class="value">
                      <input type="text"  class="form-control"  :disabled="!controls.txtNguoiDaiDien.enabled" v-model="controls.txtNguoiDaiDien.text">
                  </div>
              </div>
              <div class="info-row">
                  <div class="key w110">Địa chỉ thuê bao</div>
                  <div class="value">
                      <textarea name="" id="" cols="30" rows="10" class="form-control" style="height: 100px;resize: vertical;"  :disabled="!controls.txtDiaChiThueBao.enabled" v-model="controls.txtDiaChiThueBao.text"></textarea>
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
import { LOAIHINH_THUEBAO  } from '../../../../../../constants'
  
export default {
  components: { 
    moment,
    DatePicker, 
    api, 
  },
  name: 'HDTB_SUB_V2',
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
      state: {}, 
      controls: {
          
        tbChucDanh: {
          enabled: true,
          readOnly: false,
          text: '',
        },
        tbSoGT: {
          enabled: true,
          readOnly: false,
          text: '',
        },
        cbLoaiGT: {
          enabled: true,
          readOnly: false,
          list: [],
          value: 0
        },
        txtTenKH: {
          enabled: true,
          readOnly: false,
          text: '',
        },
        txtTenTB: {
          enabled: true,
          readOnly: false,
          text: '',
        },
        txtNguoiDaiDien: {
          enabled: true,
          readOnly: false,
          text: '',
        },
        dtpNgayHetHan: {
          enabled: true,
          readOnly: false,
          value: new Date()
        },
        txtDiaChiThueBao: {
          enabled: true,
          readOnly: false,
          text: '',
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
      this.clearForm()
      if(!data){
        this.$toast.error("Chưa có dữ liệu khởi tạo!");
        return;
      }
      console.log('openDialog', data);
      if(data.type == 1){ //frmHDTB_SUB_V2(HDTB_SUB_V2 hdtb_sub, int kieuld_id) : this()
        this.state.hdtb_id = data.hdtb_id;
        this.state.isRAUpdate = true; 
        this.state.isReadonly = data.isReadonly;

        this.controls.txtTenKH.enabled = true;
        this.controls.txtTenTB.enabled = true;
        this.controls.txtNguoiDaiDien.enabled = true; 
      }
      else if(data.type == 2){ //frmHDTB_SUB_V2(HDTB_SUB_V2 hdtb_sub, int kieuld_id, int loaitb_id)
        this.state.hdtb_sub = data.hdtb_sub;
        this.state.kieuld_id = data.kieuld_id;
        this.state.isUpdated = false; 
        this.state.loaitb_id = data.loaitb_id;

        this.controls.txtTenKH.enabled = false;
        this.controls.txtTenTB.enabled = false;
        this.controls.txtNguoiDaiDien.enabled = false;
      }
      else if(data.type == 3){ //frmHDTB_SUB_V2(long hdtb_id, bool readonly_init)
        this.state.hdtb_id = data.hdtb_id;
        this.state.isRAUpdate = true;
        this.state.isReadonly = data.isReadonly;

        this.controls.txtTenKH.enabled = true;
        this.controls.txtTenTB.enabled = true;        
        this.controls.txtNguoiDaiDien.enabled = true;
      }
        
      this.$refs.dialogHDTB_SUB_V2.show()
      this.pageLoad();
    },
    async pageLoad(){
      this.loading(true);
      try{ 
        if (this.state.hdtb_id && this.state.hdtb_id > 0){
          this.state.loaitb_id = this.GetData(await api.fn_lay_loaitb_theo_hdtb_id(this.axios, {"vhdtb_id": this.state.hdtb_id}));
        }

        if (this.state.isRAUpdate && this.state.loaitb_id != LOAIHINH_THUEBAO.IVAN)
        {
          if (this.state.isReadonly)
          {
              console.log('isReadonly', this.state.isReadonly);
              this.controls.tbChucDanh.enabled = this.state.isReadonly == false;
              this.controls.tbSoGT.enabled = this.state.isReadonly == false;
              this.controls.cbLoaiGT.enabled = this.state.isReadonly == false;
              this.controls.txtTenKH.enabled = this.state.isReadonly == false;
              this.controls.txtTenTB.enabled = this.state.isReadonly == false;
              this.controls.txtDiaChiThueBao.enabled = this.state.isReadonly == false; 
              
              this.controls.txtNguoiDaiDien.enabled = this.state.isReadonly == false;
              this.controls.dtpNgayHetHan.enabled = this.state.isReadonly == false;
          }
          await this.LoadThongTinLoaiGT();
          
          let dt_load = this.GetData(await api.lay_tt_thuebao_bosung_ca(this.axios, {"vhdtb_id": this.state.hdtb_id}));
          if(dt_load && dt_load.length > 0){
            this.controls.tbChucDanh.text = dt_load[0]["chucdanh"];
            this.controls.tbSoGT.text = dt_load[0]["so_gt"];
            this.controls.cbLoaiGT.value = dt_load[0]["loaigt_id"];
            this.controls.txtTenKH.text = dt_load[0]["ten_kh"];
            this.controls.txtTenTB.text = dt_load[0]["ten_tb"];
            this.controls.txtNguoiDaiDien.text = dt_load[0]["nguoi_dd"];
            this.controls.txtDiaChiThueBao.text = dt_load[0]["diachi_tb"];
            if(dt_load[0]["ngayhethan_gt"] && dt_load[0]["ngayhethan_gt"].length > 0)
              this.controls.dtpNgayHetHan.value = moment(dt_load[0]["ngayhethan_gt"], "YYYY-MM-DD HH:mm:ss").format(this.dateFormat); 
          } 
        }
        else{
            await this.LoadThongTinLoaiGT(); 
            let khoaform = "";

            if (this.state.loaitb_id == LOAIHINH_THUEBAO.IVAN)
            {
                khoaform =  this.GetData(await api.ivan_khoa_form_thongtin_bosung(this.axios, { "vkieuld_id": this.state.kieuld_id}));
            }
            else
            {
                khoaform =  this.GetData(await api.ca_khoa_form_thongtin_bosung(this.axios, { "vkieuld_id": this.state.kieuld_id}));
            }
            console.log('khoaform', khoaform);
            if(khoaform){
              let arrControls = khoaform.split(',');
              for(const c of arrControls){
                if(this.controls[c]) 
                  this.controls[c].enabled = false;
              }
            } 

            if(this.state.hdtb_sub){
              this.controls.tbChucDanh.text = this.state.hdtb_sub.CHUCDANH;
              this.controls.tbSoGT.text = this.state.hdtb_sub.SO_GT;
              this.controls.dtpNgayHetHan.value = this.state.hdtb_sub.NGAYHETHAN_GT; 
              this.controls.cbLoaiGT.value = this.state.hdtb_sub.LOAIGT_ID;
            } 
        }

      }catch(ex){
        console.error(ex);
        this.$toast.error(ex.message);
      }
      this.loading(false);
    }, 
    async LoadThongTinLoaiGT()
    {
      let data = this.GetData(await api.sp_loadthongtinloaigt(this.axios, {})); 
      this.controls.cbLoaiGT.list = data.map((x) => {
        return {id: x.loaigt_id, text: x.loai_gt};
      }); 
      console.log('this.controls.cbLoaiGT.list', this.controls.cbLoaiGT.list);
      if(this.controls.cbLoaiGT.list && this.controls.cbLoaiGT.list.length > 0){
        this.controls.cbLoaiGT.value = this.controls.cbLoaiGT.list[0].id;
      }
    },
    
    async  btnGhiLai_Click(){
      
        if (this.state.isRAUpdate)
        {
            await this.GhiLaiRA();
        }
        else
        {
            await this.GhiLaiGH();
        }
    },
    async GhiLaiGH()
    {
        console.log('GhiLaiGH');
        if (!this.controls.tbChucDanh.text || !this.controls.tbSoGT.text || !this.controls.cbLoaiGT.value || !this.controls.dtpNgayHetHan.value)
        {
            this.$toast.warning("Bạn chưa nhập đầy đủ các trường thông tin!");
            return;
        }
        this.state.hdtb_sub.CHUCDANH = this.controls.tbChucDanh.text;
        this.state.hdtb_sub.NGUOI_DD = this.controls.txtNguoiDaiDien.text;
        this.state.hdtb_sub.LOAIGT_ID = this.controls.cbLoaiGT.value*1;
        this.state.hdtb_sub.SO_GT = this.controls.tbSoGT.text;
        this.state.hdtb_sub.NGAYHETHAN_GT = this.controls.dtpNgayHetHan.value;
        this.state.isUpdated = true;
        this.$toast.warning("Cập nhật thành công thông tin bổ sung"); 
        this.$refs.dialogHDTB_SUB_V2.hide()
    },
    async GhiLaiRA(){
        console.log('GhiLaiRA');
        
        if (!this.controls.tbChucDanh.text || this.controls.tbChucDanh.text == "" 
          || !this.controls.tbSoGT.text || this.controls.tbSoGT.text == "" 
          || !this.controls.cbLoaiGT.value || this.controls.cbLoaiGT.value == ""
          || !this.controls.dtpNgayHetHan.value || this.controls.dtpNgayHetHan.value == "")
        {
            this.$toast.warning("Bạn chưa nhập đầy đủ các trường thông tin!");
            return;
        }

        console.log('vds_dulieu');
        let vds_dulieu = {
          NGUOI_DD: this.controls.txtNguoiDaiDien.text,
          LOAIGT_ID: this.controls.cbLoaiGT.value,
          SO_GT: this.controls.tbSoGT.text,
          TEN_KH: this.controls.txtTenKH.text,
          TEN_TB: this.controls.txtTenTB.text,
          CHUCDANH: this.controls.tbChucDanh.text,
          NGAYHETHAN_GT: this.controls.dtpNgayHetHan.value,
        }
        let req = {
            "vhdtb_id": this.state.hdtb_id,
            "vds_dulieu": JSON.stringify(vds_dulieu)
        }
        this.loading(true)
        let kq = this.GetData(await api.capnhat_hdtb_sub_ra_ca(this.axios, req)); 
        if (kq == "ok")
        {
            this.$toast.success("Cập nhật thông tin thành công");
        }
        else
        {
            this.$toast.error(kq);
        }
        this.loading(false)
    },
    clearForm(){
      this.state.hdtb_id = 0;
      this.state.hdtb_sub = 0;
      this.state.kieuld_id = 0;
      this.state.loaitb_id = 0;
      this.controls.tbChucDanh.text = "";
      this.controls.tbSoGT.text = "";
      this.controls.cbLoaiGT.text = "";
      this.controls.txtTenKH.text = "";
      this.controls.txtTenTB.text = "";
      this.controls.txtNguoiDaiDien.text = "";
      this.controls.txtDiaChiThueBao.text = ""; 
      this.controls.dtpNgayHetHan.value = moment().format(this.dateFormat); 
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