<script>
import api from "../api/index.js";
import Result from '../components/RESULT';
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
  data() {
    return {
      target: '.main-wrapper',      
      vma_tb:'',      
      txtChannel:0,
      txtBandwidth:0,
      txttransmitPower:100,
      iServer_DK:1,
      _show_SERVER_DK:0,
      vthuebao_id:0,
      vhdtb_id:0,
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
        this.$emit('input', this.form_popup)
      }
    }
  },
  created () {
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
        //this.txtKieuXacThuc = this.value.ma_tb
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
          // Gọi API xử lý sự kiện 
          this.$bvModal.msgBoxConfirm('Bạn có muốn thực hiện chức năng này không ?', {
                	title: 'Thông báo',
                	size: 'sm',
                	okTitle: 'Đồng ý',
                	cancelTitle: 'Hủy',
            	}).then(async v => {
                	if(!v) {
                    // console.log("OOOOO1111");
                  	return;
                	} 
                  //// Gọi API xử lý sự kiện 
                  // if(this.value.ddlChucNang == 0)
                  //   {
                  //     await this.doKiem(0);  
                  //   }               
            	});       
         // await this.doKiem(0);             
        } catch (error) {
          this.$toast.error(JSON.stringify(error));
        } finally {
          this.loading(false);
        }
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
          if(res.data.data && res.data.data.length > 0) {
              this._show_SERVER_DK = res.data.data[0]["macdinh"]; 
          }  else{this._show_SERVER_DK = 28}           
          //console.log(res.data.data[0]["macdinh"]);
        } catch (err) { }       
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
          }else{this.iServer_DK = 1; }
          //console.log(res.data.data[0]["macdinh"]);             
          //console.log(res.data);
        } catch (err) {this.iServer_DK = 1; }       
      },
     async doKiem(vkieu){         
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

</style>
<template>
  <div>     
      <b-row>
          <b-col sm="2" cols="12"> 
            <div class="key">Channel</div>
            </b-col> 
          <b-col sm="3" cols="12">         
            <div class="value">
                  <input type="number" v-model= "txtChannel" labelWidth="0" ref="textFocus" class="form-control">                                            
            </div> 
            <!-- <div class="text-sm mt-2 text-danger ml-2" v-if="$v.txtChannel.$error">
                        <div class="error" v-if="!$v.txtVlanId.txtChannel">VLAN ID [0-4094]</div>
            </div>                          -->
          </b-col> 
      </b-row>  
      <b-row>
          <b-col sm="2" cols="12"> 
            <div class="key">Bandwidth</div>
          </b-col> 
          <b-col sm="3" cols="12">                               
              <div class="value">
                <input type="number" v-model= "txtBandwidth" labelWidth="0" ref="textFocusbandw" class="form-control">              
              </div>                         
          </b-col> 
          <b-col sm="1" cols="12">             
          </b-col> 
          <b-col sm="2" cols="12"> 
            <div class="key">Transmit Power</div>
          </b-col> 
          <b-col sm="3" cols="12">                               
              <div class="value">
                <input type="number" v-model= "txttransmitPower" labelWidth="0" ref="textFocustranp" class="form-control">              
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
                    <a> <span class="icon one-file-search"></span>Thực thi</a>
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
        </b-row>      -->
  </div>   
</template>