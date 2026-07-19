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
      vma_tb: this.value.ma_tb,  
      //vma_tb:'',
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
        this.txtTraCuu = this.value.ma_tb      
        this.response = "";  
      }      
    },
    'value.isValid'(newValue, oldValue) {
      this.UiState.btn_disable = newValue;
     // console.log(newValue)     
    }
  },
  methods: {
    // show() {
    //   this.$refs.CCHTPopop.show();
    //   this.visible = true
    // },
    // hide() {
    //   // this.$emit("hideDialog");
    //   this.visible = false
    // },
    Clear() {
       this.response = "";
    },     
    async tracuuport() {    
        this.loading(true)
        //console.log(this.value.ddlChucNang)
        try {           
          // if(!this.KiemTra_SoMayACC())
          // {          
          //   return false;         
          // }   
          if(this.value.ddlChucNang == 61 || this.value.ddlChucNang == 21) 
          {
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
                   if(this.value.ddlChucNang == 61)
                    {
                      console.log("OOOOO");
                      await this.doKiem(1);
                    }
                   
                  //console.log("OOOOO");                 
            	});
          }            
          // Gọi API xử lý sự kiện 
         // console.log(this.value.ddlChucNang)      
          else if(this.value.ddlChucNang == 0)
            {
              await this.doKiem(0);  
            }                     
        } catch (error) {
          this.$toast.error(JSON.stringify(error));
        } finally {
          this.loading(false);
        }
    }, 
    // KiemTra_SoMayACC() {       
    //   if (this.txtTraCuu==null)
    //   {
    //     this.$toast.error('Bạn chưa nhập "Số máy/ Acc"!');
    //     this.$refs.textFocus.focus();             
    //             return false;        
    //   }
    //   else{
    //     if (this.txtTraCuu.trim() == "")
    //     {
    //       this.$toast.error('Bạn chưa nhập "Số máy/ Acc"!');
    //             this.$refs.textFocus.focus();             
    //             return false;
    //     }
    //     else
    //       {
    //         if (this.txtTraCuu.trim().length > 200)
    //         {
    //           this.$toast.error('Nhập "Số máy/ Acc" không được quá 200 ký tự!');
    //           this.$refs.textFocus.focus();
    //           //this.txtThongBao.focus();
    //           return false;
    //         }          
    //       }
    //      }  
    //   return true
    //  },
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
          } 
          else{this._show_SERVER_DK = 28}            
          //console.log(res.data.data[0]["macdinh"]);
        } catch (err) {this._show_SERVER_DK = 28}       
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
            // console.log(isNaN(this.value.ma_tb))
            // Trường hợp input thay đổi 1 trong 3 pr
            // if (isNaN(this.value.ma_tb))
            // {
            //       this.vma_tb = this.value.ma_tb;
            //       this.vhdtb_id=0;
            //       this.vthuebao_id=0;
            // } 
            // else
            // {
            //   if (this.value.thuebao_id != 0){
            //     this.vthuebao_id = this.value.thuebao_id;
            //     this.vhdtb_id=0;
            //     this.vma_tb ="";
            //   } else if (this.value.hdtb_id != 0) {
            //     this.vhdtb_id = this.value.hdtb_id;
            //     this.vthuebao_id=0;
            //     this.vma_tb ="";
            //   } 
            //   else {
            //     this.vma_tb = this.value.ma_tb;
            //     this.vhdtb_id=0;
            //     this.vthuebao_id=0;
            //   } 
            // }
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
            // console.log(this.value.ma_tb)
            // console.log(this.value.thuebao_id)
            // console.log(this.value.hdtb_id)
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
          <b-col sm="10" cols="12"></b-col>  
          <b-col sm="2" cols="12">
            <b-button v-if="value.ddlChucNang == 61" variant="primary" v-on:click="tracuuport" :disabled="UiState.btn_disable">Thực thi</b-button>
            <b-button v-else-if="value.ddlChucNang == 21" variant="primary" v-on:click="tracuuport" :disabled="UiState.btn_disable">Thực thi</b-button>
            <b-button v-else variant="primary" v-on:click="tracuuport" :disabled="UiState.btn_disable">Tra cứu</b-button>
              <!-- <vue-nav>
                <ul class="list">
                  <li @click="tracuuport">
                    <a v-if="value.ddlChucNang == 61"> <span class="icon one-file-search"></span>Thực thi</a>
                    <a v-else-if="value.ddlChucNang == 21"> <span class="icon one-file-search"></span>Thực thi</a>
                    <a v-else> <span class="icon one-file-search"></span>Tra cứu</a>
                    
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
        </b-row> -->
  </div>   
</template>