<script>
import api from "../api/index.js";
import Result from '../components/RESULT';
import { required, maxLength, minLength} from 'vuelidate/lib/validators';
export default {
  props:{    
   value: {
     thuebao_id: {
       default: '0'
      },
      hdtb_id: {
        default: '0'
      },
      ma_tb: {
        default: ''
      },
      kieu:{
        default: 0
      },
      ddlChucNang:{
        default:0
      },
      isValid: {
        default: false
      }
   }
  },
  components: {
        Result      
    },
  validations: {
    txtTenWifi: {
     required,
     maxLength: maxLength(30),
     validTenWifi: function (TenWf) {
       var Regex = new RegExp("^[a-z](?:_?[a-z0-9._ -]+)*$");
        var res = Regex.test(TenWf);
        return res;
      }
    },
     txtPwd: {
      required, 
      maxLength: maxLength(30),
      minLength: minLength(8),
      validPassword: function (Password) {
        return Password && Password.length >= 8 && Password.length <= 30;
      }
     //^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[#$@!%&*?])[A-Za-z\d#$@!%&*?]{8,30}$/ 
     //Length: maxLength(200)
    },
      cboKieuXacThuc:{required},
      cboKieuMaHoa:{required}
  },
  data() {
    return {
      target: '.main-wrapper',
      //txtKieuXacThuc: this.value.ma_tb,       
      txtKieuXacThuc:'',
      cboKieuXacThuc: {
              DataSource: [
                { KIEU_XT: 'WPA2PSK', GHICHU: 'WPA2-PSK',id: 0},
                { KIEU_XT: 'MWPA2PSK', GHICHU: 'Mixed WPA2/ WPA2-PSK',id: 1}                
              ],
            },
      indKieuXT: 0, 
      cboKieuMaHoa: {
              DataSource: [
                { KIEU_MH: 'aes',id: 0},
                { KIEU_MH: 'tkip+aes',id: 1}                
              ],
            },
      indKieuMH: 0, 
      txtTenWifi:'',
      txtPwd:'',
      iServer_DK:1,
      _show_SERVER_DK:0,
      vthuebao_id:'0',
      vhdtb_id:'0',
      vma_tb:this.value.vma_tb, 
      response:'',
      UiState: {
        btn_disable: this.value.isValid
      }
    };
  },
  computed: {
    vmodel: {
      get () {
        return vma_tb = this.value.ma_tb
      },
      set (key, value) {
        //this.$emit('input', this.form_popup)
      }
    }
  },
  created () {
    //console.log(`isValid: ${this.value.isValid}`)
    this.$nextTick(async () => {
      
    //  if (this.thuebao_id != 0){this.txtTraCuu = this.value.thuebao_id}
    //   else if (this.hdtb_id != 0){this.txtTraCuu = this.value.hdtb_id}
    //   else {
    //     this.txtTraCuu=this.value.ma_tb;
    //   }      
    //   this.indChucNang = this.value.ddlChucNang     
    
    })
  },
  computed: {
    phanvungId: () => {
      const OneBSSClient =  localStorage.getItem("OneBSS-Client") ? localStorage.getItem("OneBSS-Client") : null;   
      let phanvung_id = 28;
      // if (OneBSSClient) {
      //   phanvung_id = JSON.parse(OneBSSClient)['phanvung_id'];
      // }
      return phanvung_id
    }
  },
  watch: {
    'visible'(newValue, oldValue) {
      if(newValue) {
       // this.txtKieuXacThuc = this.value.ma_tb
       this.$v.$reset();
        this.$v.$touch();
      }      
    },
    'value.isValid'(newValue, oldValue) {
      this.UiState.btn_disable = newValue;
      //console.log(newValue)
    }
  },
  methods: {    
    Clear() {      
    },     
    async tracuuport() {    
        this.loading(true)
        try {           
          // if(!this.KiemTra_SoMayACC())
          // {          
          //   return false;         
          // } 
          this.$bvModal.msgBoxConfirm('Bạn có muốn thực hiện chức năng này không ?', {
                	title: 'Thông báo',
                	size: 'sm',
                	okTitle: 'Yes',
                	cancelTitle: 'No',
            	}).then(async v => {
                	if(!v) {
                    // console.log("OOOOO1111");
                  	return;
                	} 
                  //// Gọi API xử lý sự kiện 
                  this.$v.$touch();
                    if (this.$v.$invalid) {
                      this.$toast.error('Đã có lỗi nhập thông tin. Kiểm tra thông báo lỗi tại các trường nhập')
                      return;
                    }             
            	}); 
                     
        } catch (error) {
          this.$toast.error(JSON.stringify(error));
        } finally {
          this.loading(false);
        }
    }, 
    KiemTra_SoMayACC() {       
      if (this.txtTraCuu==null)
      {
        this.$toast.error('Bạn chưa nhập "Số máy/ Acc"!');
        this.$refs.textFocus.focus();             
                return false;        
      }
      else{
        if (this.txtTraCuu.trim() == "")
        {
          this.$toast.error('Bạn chưa nhập "Số máy/ Acc"!');
                this.$refs.textFocus.focus();             
                return false;
        }
        else
          {
            if (this.txtTraCuu.trim().length > 200)
            {
              this.$toast.error('Nhập "Số máy/ Acc" không được quá 200 ký tự!');
              this.$refs.textFocus.focus();
              //this.txtThongBao.focus();
              return false;
            }          
          }
         }  
      return true
     },
     // Lấy danh sách tham số mặc định mã tham số    
     async layDSThamsoMDMaTS_SHOW_SERVER_DK() {      
        try {     
          //  const params = {
          //                   "p_list_of_cols": "*",
          //                   "p_order": "",
          //                   "p_phanvung_id": this.phanvungId,
          //                   "p_schema": "admin",
          //                   "p_table_name": "thamso_md",
          //                   "p_where": "ma_ts = 'SHOW_SERVER_DK'"
          //                 }
          const params = { "phanvung_id": this.phanvungId,                            
                            "ma_ts": "SHOW_SERVER_DK"
                          }
          const res = await api.lay_ds_thamso_md_mats(this.axios,params);
           //console.log(res);
          if(res.data.data && res.data.data.length > 0) {
             this._show_SERVER_DK = res.data.data[0]["macdinh"]; 
            // console.log(res);
          } else{this._show_SERVER_DK = 28}            
          //console.log(res.data.data[0]["macdinh"]);
        } catch (err) { this._show_SERVER_DK = 0; }       
      },
       // Lấy danh sách tham số mặc định mã tham số    
     async layDSThamsoMDMaTS_SERVER_DK() {   
        //console.log(this.phanvungId)
        try { 
          // const params = {
          //                   "p_list_of_cols": "macdinh",
          //                   "p_order": "",
          //                   "p_phanvung_id": this.phanvungId,
          //                   "p_schema": "admin",
          //                   "p_table_name": "thamso_md",
          //                   "p_where": "ma_ts = 'SERVER_DK'"
          //                 }        
          const params = { "phanvung_id": this.phanvungId,                            
                            "ma_ts": "SERVER_DK"
                          }
          const res = await api.lay_ds_thamso_md_mats(this.axios,params);
          if(res.data.data && res.data.data.length > 0) {
              this.iServer_DK = res.data.data[0]["macdinh"]; 
          }
          //console.log(res.data.data[0]["macdinh"]);             
          //console.log(res.data);
        } catch (err) {this.iServer_DK = 1; }       
      },
     async doKiem(vkieu){  
       //console.log(this.value);       
         try {     
            
            await this.layDSThamsoMDMaTS_SHOW_SERVER_DK();
            await this.layDSThamsoMDMaTS_SERVER_DK();  

            const pMacAddress = this.value.isGetMac;      
            const params = {
                            "show_server_dk": this._show_SERVER_DK,
                            "server_id": this.iServer_DK,
                            "vthuebao_id": `${(this.vthuebao_id || "")}`.trim(),
                            "vhdtb_id": `${(this.vhdtb_id || "")}`.trim(),
                            "vma_tb": (this.vma_tb || "" + "").trim(),
                            "vProfile": this.value.vProfile,
                            "user_ont": ((this.value.user_ont || "") + "").trim(),
                            "pass_ont": (this.value.pass_ont || "" + "").trim(),
                            "isGetMac": pMacAddress,
                            "vkieu": vkieu
            }
           //console.log(params)
           const res = await api.doKiem(this.axios, params);
                               
            if(res.data.data && res.data.data.length > 0) {
              this.response = res.data.data  
              //console.log(res); 
            } else {
              const {data:{data, message}} = res;
              this.response = message;
              //console.log(message);
            }           

        } catch (err) {
          this.$toast.error(JSON.stringify(err));
          this.response = "";
          //console.log(err);
          //this.loading(false);
        }
      }, 
  }
}
</script>
<style>
.popup-body .port-info {
  background-color: #ffffff !important; 
}
.changeWifi .ml-2{padding-left: 5px;}
.changeWifi .mb-2{padding-bottom: 3px;}


</style>
<template>
  <div class="changeWifi">   
     <b-row>
        <b-col sm="2" cols="12"> 
           <div class="key">Kiểu xác thực</div>
          </b-col> 
        <b-col sm="10" cols="12">         
          <div class="value mb-2">
              <vue-select labelWidth="0" 
                  ref="chucnang" 
                  v-model="indKieuXT" 
                  :options="cboKieuXacThuc.DataSource" 
                  labelField="GHICHU" 
                  valueField="id">
                </vue-select> 
          </div> 
           <div class="error" v-if="!$v.cboKieuXacThuc.required">Chưa chọn kiểu xác thực</div>               
        </b-col> 
     </b-row>
     <b-row>
        <b-col sm="2" cols="12"> 
           <div class="key">Kiểu mã hóa</div>
        </b-col> 
        <b-col sm="10" cols="12">                 
            <div class="value mb-2">
                <vue-select labelWidth="0" 
                    ref="chucnang" 
                    v-model="indKieuMH" 
                    :options="cboKieuMaHoa.DataSource" 
                    labelField="KIEU_MH" 
                    valueField="id"                      
                    >
                  </vue-select>                               
            </div> 
            <div class="error" v-if="!$v.cboKieuMaHoa.required">Chưa chọn kiểu mã hóa</div>                                 
        </b-col> 
      </b-row>
      <b-row>
          <b-col sm="2" cols="12"> 
            <div class="key">Tên wifi</div>
          </b-col> 
          <b-col sm="3" cols="12">         
              <div class="value pl-2 mb-2">
                    <input type="text" v-model= "txtTenWifi" class="form-control">
              </div>  
              <div class="text-sm mt-2 text-danger ml-2 mb-2" v-if="$v.txtTenWifi.$error">
                        <div class="error" v-if="!$v.txtTenWifi.required">Chưa nhập tên wifi</div>  
                        <div class="error" v-if="!$v.txtTenWifi.maxLength">Tên wifi không được quá 30 ký tự</div>  
                        <div class="error" v-if="!$v.txtTenWifi.validTenWifi">Tên wifi chứa ký tự không hợp lệ</div>                    
              </div>                       
          </b-col> 
      </b-row>  
      <b-row>
          <b-col sm="2" cols="12"> 
            <div class="key">Password</div>
          </b-col> 
          <b-col sm="3" cols="12">                               
              <div class="value pl-2">
                <input type="text" v-model= "txtPwd"  ref="textFocuspwd" class="form-control"> 
                </div> 
                <div class="text-sm mt-2 text-danger ml-2" v-if="$v.txtPwd.$error">
                        <div class="error" v-if="!$v.txtPwd.required">Chưa nhập mật khẩu wifi</div>
                        <!-- <div class="error" v-if="!$v.txtPwd.minLength">Mật khẩu wifi phải có tối thiểu 8 ký tự và tối đa 30 ký tự</div> -->
                        <div class="error" v-if="!$v.txtPwd.validPassword">Mật khẩu wifi phải có tối thiểu 8 ký tự và tối đa 30 ký tự</div>
                </div>                                     
          </b-col> 
      </b-row> 
      <b-row>
          <b-col sm="10" cols="12"></b-col>  
          <b-col sm="2" cols="12">
             <b-button variant="primary" v-on:click="tracuuport" :disabled="UiState.btn_disable">Thực thi</b-button>
              <!-- <vue-nav>
                <ul class="list">
                  <li @click="tracuuport">
                    <a> <span class="icon one-file-save"></span>Thực thi</a>
                  </li>           
                  </ul>
                </vue-nav> -->
          </b-col>           
        </b-row>
        <b-row>
            <Result :result="response"/>
        </b-row>
        <!-- <b-row>
          <b-col sm="12" cols="12">
            <span>Kết quả</span>
          </b-col>           
        </b-row>
        <b-row>
          <b-col sm="12" cols="12">
            <textarea v-model= "txtResult" disabled name="" class="form-control port-info" style="height:250px;resize: none;"></textarea>
          </b-col>           
        </b-row>       -->
  </div>   
</template>