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
div.editable {    
    border: 1px solid #ccc;
    padding: 5px;
}

strong {
  font-weight: bold;
}
input.form-control {
  color: black;
  font-weight: bold;
}
</style>

<template>
<b-modal
  ref="dialogTruyenThongSuCo"
  size="xl"
  hide-header
  hide-header-close
  hide-footer
  body-class="p-0"
  centered
  no-close-on-backdrop
  @show="handleShowModal"
>
  <div class="modal-content popup-box">
    <div class="popup-header">
      <div class="title">
        <span class="icon one-notepad"></span> Cập nhật nội dung truyền thông
      </div>
      <div class="close -ap icon-close" @click="hideDialog"></div>
    </div>
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="javascript:;" v-on:click="tsbtnUpdate_Click" >
            <span class="icon one-save"></span> Cập nhật
          </a>
        </li>        
      </ul>
    </div>
    <div class="popup-body">
      <div class="box-form">
        <div class="legend-title">Chi tiết sự cố</div> 
        <div class="row">
          <div class="col-12">             
            <div id="chitiet" contenteditable="true" class="">
              <b>Nội dung TĐML:</b>{{suco.noidung_sc}}<br>
              <b>Thời gian:</b>{{suco.ngay_sc}}<br>
              <b>Dự kiến thời gian xử lý:</b> {{suco.dukien_tg_xuly}}<br>
              <b>Đơn vị nhập:</b> {{suco.donvi_nhap }}<br>
            </div>                
          </div>
        </div>
      </div>
      <div class="box-form">
        <div class="legend-title">Thông tin truyền thông</div> 
        <div class="row">
          <div class="info-row">
              <div class="key">Tiêu đề</div>
              <div class="value">
                  <input type="text" class="form-control" v-model="tieude">
              </div>
          </div> 
        </div>
        <div class="row">
          <div class="info-row">
              <div class="key">Nội dung</div>
              <div class="value">
                <textarea class="form-control" v-model="noidung" style="height: 120px;resize: none;"></textarea>
              </div>
          </div> 
        </div>
        <div class="row">
          <div class="info-row">
              <div class="key">Văn bản</div>
              <div class="value">
                <div class="input-more-button -right">
                    <label class="btn" for="upload-file">
                        <span class="-ap icon-more_horiz"></span>
                        <input type="file" ref="files" class="hidden" id="upload-file"
                            :accept="formAllowFileType" @change="UploadFileHoSo">
                    </label>
                    <input type="text" class="form-control" v-model="vanban">
                </div>
                  
              </div>              
          </div> 
        </div>
      </div>
    </div>
    
  </div>
</b-modal>
  
</template>
<script> 
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import moment from 'moment' 
import api from './PopUpApi'  
import { DICHVU_VIENTHONG, TRANGTHAI_PH } from '@/constants'
import commonFn from "@/modules/contract/setup/ChangeLandlineIMSType/CommonFuntions"; 

export default {
  components: { 
    moment,
    DatePicker, 
    api,  
  },
  name: 'frmTruyenThongSuCo',
  data() {
    return {      
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
      chitiet:'',
      noidung:'',
      vanban:'',
      tieude:'',   
      vdb_id:0,    
      vphanloai:0,
      suco: {
        donvi_nhap:'',
        dukien_tg_xuly:'',
        ngay_sc:'',
        noidung_sc:'',
      },
    }
  },
  computed: {    
    formAllowFileType() {
      return ".doc,.docx,.xls,.xlsx,.pdf,.jpg,.jpeg,.png";
    },
  },
  created: async function () {
     
  },
  mounted: function () { 
    
  },
  watch: {   
    
    
  },
  methods: {
    handleShowModal(){},
    dialogClose() {},
    openDialog(data) { 
      if(!data){
        return;
      } 
      
      this.vdb_id=data.db_id
      this.vphanloai=data.phanloai
      this.suco = data.item
      this.chitiet = '<b>Nội dung TĐML:</b>'+ this.suco.noidung_sc +'<br>' +
                      '<b>Thời gian:</b>'+ this.suco.ngay_sc + '<br>' +
                      '<b>Dự kiến thời gian xử lý:</b>' + this.suco.dukien_tg_xuly +'<br>' +
                      '<b>Đơn vị nhập:</b>' + this.suco.donvi_nhap + '<br>'  
         
      this.$refs.dialogTruyenThongSuCo.show();
      this.pageLoad();
    },
    hideDialog () {
      this.$refs.dialogTruyenThongSuCo.hide()
    },
    
    async pageLoad(){ //frm_BRN_KichHoat_Brandname_Load
      try{ 
        this.loading(true)
        this.tt_nd.may_cn = await this.$root.token.getMachine();
        this.tt_nd.ip_cn = await this.$root.token.getIP();
        await this.LoadChitietSuCo();
        
         
      }catch(ex){
        console.error(ex);
        this.$toast.error(ex.message);
        console.error(ex.message);
      }
      this.loading(false) 
    },  
    async LoadChitietSuCo(){
      this.loading(true);
      try {
        let ds = await api.sp_lay_chitiet_suco_tdml(this.axios, {vphanloai:this.vphanloai,vdb_id:this.vdb_id});
        console.log(ds)
        if(ds && ds.data && ds.data.data && ds.data.data.length > 0){          
          this.tieude = ds.data.data[0].tieude_tt
          this.noidung = ds.data.data[0].noidung_tt
          this.vanban =  ds.data.data[0].path_file          
        }
        console.log('log 1')
        
        this.loading(false);
      }
      catch(ex){
        this.loading(false);
      }
    },
    
    async tsbtnUpdate_Click(){      
      console.log('tsbtnUpdate_Click')
      if (!this.tieude || this.tieude == "" || !this.noidung || this.noidung == ""){
        this.$toast.error('Vui lòng nhập đủ tiêu đề và nội dung!');
        return
      }
      const data = { 
        "vphanloai": this.vphanloai, 
        "vdb_id": this.vdb_id, 
        "vtieude_tt": this.tieude, 
        "vnoidung_tt": this.noidung, 
        "vpath_file": this.vanban 
        }

      let ds = await api.sp_update_thongtin_truyenthong_tdml(this.axios, data);
      console.log('sp_update_thongtin_truyenthong_tdml')
      console.log(ds)
      if(ds && ds.data && ds.data.data){
        
        this.$toast.success('Cập nhật thông tin truyền thông thành công!');
        
      }
      console.log('log 1')
      
    },
    async Clear(){
      
    },
    async UploadFileHoSo() {
      this.loading(true);
       let formData = new FormData();
      const lastDot = this.$refs.files.files[0].name.lastIndexOf(".");
      const vfile_ext = this.$refs.files.files[0].name.substring(lastDot + 1);
      for (var file of this.$refs.files.files) {
        formData.append("files", file);
      }      
      await api.upload_file_hoso(this.axios, formData, {
        headers: { "Content-Type": "multipart/form-data" },
      })
        .then((response) => {
          this.loading(false);
          if (response.data.error_code && response.data.error_code === 'BSS-00000000' ) {
            this.vanban = response.data.data[0]             
          }
        })
        
        ;
      this.loading(false);
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
    Popup(modalId) {
      this.$bvModal.show(modalId);
    },
    async popupClosed(val) {
      console.log("popupClosed", val);
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