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
  ref="dialogTuvanMytv"
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
        <span class="icon one-notepad"></span> Tư vấn tiếp thị dịch vụ
      </div>
      <div class="close -ap icon-close" @click="hideDialog"></div>
    </div>
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="javascript:;" v-on:click="tsbtnUpdate_Click" >
            <span class="icon one-save"></span> Tư vấn
          </a>
        </li>        
      </ul>
    </div>
    <div class="popup-body">      
      <div class="box-form">
        <div class="legend-title">Thông tin tư vấn</div>                
        <div class="row">
          <div class="col-sm-12 col-12">
            <div class="info-row">
              <div class="key">Loại hình</div>
              <div class="value select-custom">
                  
                      <ejs-multiselect v-model="options.cbo_loaihinh.value" 
                        id="loaihinh" 
                        :dataSource="options.cbo_loaihinh.list" 
                        placeholder="Chọn loại hình" 
                        selectAllText="Chọn tất cả" 
                        :showSelectAll="true"
                        locale='vi-VN'
                        :showDropDownIcon="true"
                        unSelectAllText="Bỏ chọn tất cả" 
                        mode="multiMode" 
                                                
                        :fields="{ text: 'loaihinh_tb', value: 'loaitb_id' }">
                      </ejs-multiselect>
                      
                  
              </div>
            </div>                            
          </div>          
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key">Hình thức BH</div>
              <div class="value">
                  <div class="select-custom">                                        
                      <SelectExt id="hinhthuc" 
                          v-model="options.cbo_hinhthuc.value" 
                          :allowFiltering="true" 
                          dataTextField="hinhthuc_tv"
                          dataValueField="hinhthuctv_id" 
                          :placeholder="'Chọn hình thức'"
                          :dataSource="options.cbo_hinhthuc.list"
                      >
                      </SelectExt>
                  </div>
              </div>
            </div>                            
          </div>  
          <div class="col-sm-6 col-12">
            <div class="info-row">
                <div class="value">
                    <input v-if="options.cbo_hinhthuc.value==5" type="text" class="form-control" v-model="hinhthuc">
                </div>
            </div>              
          </div>                          
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key">Đại diện KH</div>
              <div class="value">
                  <div class="select-custom">                                        
                      <SelectExt id="daidien" 
                          v-model="options.cbo_daidien.value" 
                          :allowFiltering="true" 
                          dataTextField="quanhe_tv"
                          dataValueField="quanhetv_id" 
                          :placeholder="'Chọn đại diện'"
                          :dataSource="options.cbo_daidien.list"
                      >
                      </SelectExt>
                  </div>
              </div>
            </div>                            
          </div>  
          <div class="col-sm-6 col-12">
            <div class="info-row">
                <div class="value">
                    <input v-if="options.cbo_daidien.value==100" type="text" class="form-control" v-model="daidien">
                </div>
            </div>              
          </div>   
        </div>
        <div class="row">
          <div class="col-sm-3 col-6">
            <div class="info-row">
              <div class="key">Ngày tư vấn</div>              
              <div class="value">                                       
                <vue-date  v-model="ngaytuvan" :format="dateFormat" :checkbox="false"></vue-date>
              </div>
            </div>                            
          </div>  
          <div class="col-sm-3 col-6">
            <div class="info-row">
              <div class="key">Số ĐT</div>
                  <div class="value">
                      <input type="text" class="form-control" v-model="so_dt">
                  </div>
              </div>              
          </div> 
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key">Nhu cầu</div>
              <div class="value">
                  <div class="select-custom">                                        
                      <SelectExt id="nhucau" 
                          v-model="options.cbo_nhucau.value" 
                          :allowFiltering="true" 
                          dataTextField="nhucau"
                          dataValueField="nhucau_id" 
                          :placeholder="'Chọn nhu cầu'"
                          :dataSource="options.cbo_nhucau.list"
                      >
                      </SelectExt>
                  </div>
              </div>
            </div>                            
          </div>  
          <div class="col-sm-6 col-12">
            <div class="info-row">
                <div class="value">
                    <input v-if="options.cbo_nhucau.value==10" type="text" class="form-control" v-model="nhucau">
                </div>
            </div>              
          </div>   
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key">Kết quả</div>
              <div class="value">
                  <div class="select-custom">                                        
                      <SelectExt id="ketqua" 
                          v-model="options.cbo_ketqua.value" 
                          :allowFiltering="true" 
                          dataTextField="ketqua"
                          dataValueField="ketqua_id" 
                          :placeholder="'Chọn kết quả'"
                          :dataSource="options.cbo_ketqua.list"
                      >
                      </SelectExt>
                  </div>
              </div>
            </div>                            
          </div>  
          <div class="col-sm-6 col-12">
            <div class="info-row">              
                <div class="value">
                    <input v-if="options.cbo_ketqua.value==6" type="text" class="form-control" v-model="ketqua">
                </div>
            </div>              
          </div>        
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">            
            <div class="info-row">
                <div class="key w90">Hẹn gặp</div>
                <div class="value w20 padt7">
                    <div class="check-action">
                        <input id="chkhen" type="checkbox" class="check" v-model="check_hen">
                        <span class="name"></span>
                    </div>
                </div>
                <div class="value">
                    
                    <vue-date :class="{disabled: !check_hen}" v-model="ngayhengap" :format="dateFormat" :checkbox="false"></vue-date>
                    
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
  name: 'frmTuvanMytv',
  data() {
    return {            
        
      dateFormat:'DD/MM/YYYY',
      
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
      options: {
        cbo_loaihinh: {
          value:[],
          list:[]
        },
        cbo_nhucau: {
          value:-1,
          list:[]
        },
        cbo_daidien: {
          value:-1,
          list:[]
        },
        cbo_ketqua: {
          value:-1,
          list:[]
        },
        cbo_hinhthuc: {
          value:-1,
          list:[]
        }
      },
      ngayhengap:moment(new Date()).format("DD/MM/YYYY"), 
      ketqua:'',
      nhucau:'',
      so_dt:'',
      daidien:'',
      hinhthuc:'',
      ngaytuvan: moment(new Date()).format("DD/MM/YYYY"),   
      check_hen:false, 
      hdkh_id:0,
      khachhang_id:0,
      loai:1,

    }
  },
  computed: {   
    
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
      if (data){
        this.hdkh_id = data.hdkh_id?data.hdkh_id:0;
        this.khachhang_id = data.khachhang_id?data.khachhang_id:0;
        this.loai = data.loai?data.loai:1;

      }
      this.$refs.dialogTuvanMytv.show();
      this.pageLoad();
    },
    hideDialog () {
      this.$refs.dialogTuvanMytv.hide()
    },
    
    async pageLoad(){ 
      try{ 
        this.loading(true)
        this.tt_nd.may_cn = await this.$root.token.getMachine();
        this.tt_nd.ip_cn = await this.$root.token.getIP();
        await this.LoadDanhmuc();
        
         
      }catch(ex){
        console.error(ex);
        this.$toast.error(ex.message);
        console.error(ex.message);
      }
      this.loading(false) 
    },  
    async LoadDanhmuc(){
      this.loading(true);
      try {
        let ds = await api.lay_danhmuc_tuvan_mytv(this.axios);
        console.log(ds.data.data)
        if(ds && ds.data && ds.data.data){          
          this.options.cbo_hinhthuc.list = ds.data.data.hinhthuc_bh                  
          this.options.cbo_daidien.list = ds.data.data.daidien_kh
          this.options.cbo_nhucau.list = ds.data.data.nhucau_kh
          this.options.cbo_ketqua.list = ds.data.data.ketqua_bh
        }
        let ds2 = await api.sp_tt_loaihinh_tb(this.axios,{ type : 3});
        
        if(ds2 && ds2.data && ds2.data.data && ds2.data.data.length > 0){          
          this.options.cbo_loaihinh.list = ds2.data.data            
        }
        if (this.loai == 1){
          let ds1 = await api.fn_tt_db_khachhang(this.axios,{ type : 3, param : this.khachhang_id });
          console.log(ds1)
          if(ds1 && ds1.data && ds1.data.data ){          
            this.so_dt =  ds1.data.data      
          }          
        }
        else {
          let ds1 = await api.fn_tt_db_khachhang(this.axios,{ type : 9, param : this.hdkh_id });
          console.log(ds1)
          if(ds1 && ds1.data && ds1.data.data ){          
            this.so_dt =  ds1.data.data      
          }  
        }
        if (this.so_dt=="-1") this.so_dt = ''
        // console.log('log 1')
        
        this.loading(false);
      }
      catch(ex){
        this.loading(false);
      }
    },
    
    async tsbtnUpdate_Click(){      
      console.log('tsbtnUpdate_Click')
      console.log('select loại hình:'+this.options.cbo_loaihinh.value.join(','))
      try
      {
          if (this.options.cbo_loaihinh.value.length == 0)
          {
            this.$toast.error("Vui lòng chọn loại hình!");
            //txtSoDT.Focus();
            return;
          }
          if (this.options.cbo_hinhthuc.value == -1)
          {
            this.$toast.error("Vui lòng chọn hình thức!");
            //txtSoDT.Focus();
            return;
          }
          if (this.options.cbo_daidien.value == -1)
          {
            this.$toast.error("Vui lòng chọn đại diện!");
            //txtSoDT.Focus();
            return;
          }
          if (this.options.cbo_nhucau.value == -1)
          {
            this.$toast.error("Vui lòng chọn nhu cầu!");
            //txtSoDT.Focus();
            return;
          }
          if (this.options.cbo_ketqua.value == -1)
          {
            this.$toast.error("Vui lòng chọn kết quả!");
            //txtSoDT.Focus();
            return;
          }
          if (this.so_dt == null || this.so_dt == "")
          {
            this.$toast.error("Bạn chưa nhập số điện thoại khách hàng !");
            //txtSoDT.Focus();
            return;
          }
          if (this.options.cbo_nhucau.value == 10){
            if (this.nhucau == '')
            {
              this.$toast.error("Bạn chưa nhập nhu cầu khách hàng !");
              //  txtNhuCauKH.Focus();
                return;
            }
          }
          if (this.options.cbo_ketqua.value ==6 ){
            if (this.ketqua == '')
            {
              this.$toast.error("Bạn chưa nhập kết quả bán hàng !");
              //  txtKetQua.Focus();
              return;
            }
          }
          const input = {
            vkhachhang_id:this.khachhang_id,
            vhdkh_id:this.hdkh_id,
            vloai:this.loai,
            vhinhthuctv_id:this.options.cbo_hinhthuc.value,
            vhinhthuc_tv:this.hinhthuc,
            vngaytv: this.ngaytuvan,
            vquanhetv_id:this.options.cbo_daidien.value,
            vquanhe_tv:this.daidien,
            vso_dt:this.so_dt,
            vnhucau_id:this.options.cbo_nhucau.value,
            vnhucau:this.nhucau,
            vketqua_id: this.options.cbo_ketqua.value,
            vketqua:this.ketqua,
            vhentv:this.ngayhengap,
            vdsloaitb_id:this.options.cbo_loaihinh.value.join(',')
          }
          let kq = await api.tuvan_mytv_fiber_hienhuu(this.axios,input);
          
          if (kq && kq.data && kq.data.data && kq.data.data == "ok")
          {
            this.$toast.success("Cập nhật kết quả tư vấn thành công !");  
            this.$emit('onUpdate', "OK")            
          }
          else
            this.$toast.error("Có lỗi khi cập nhật kết quả tư vấn.");

      }
      catch (ex)
      {
        this.$toast.error("Lỗi ghi lại thông tin tư vấn.");
      }
    },
    async Clear(){
      
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
  }, 
}
</script>

<style>
.chuyenTo .page-content {
  top: 130px !important;
}
</style>