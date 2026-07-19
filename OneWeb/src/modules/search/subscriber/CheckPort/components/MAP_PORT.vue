<script>
import api from "../api/index.js";
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
  data() {
    return {
      target: '.main-wrapper',      
      vma_tb:'',      
      txtUserONT:'',
      txtPasswordONT:'',
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
  // computed: {
  //   phanvungId: () => {
  //     const OneBSSClient =  localStorage.getItem("OneBSS-Client") ? localStorage.getItem("OneBSS-Client") : null;   
  //     let phanvung_id = 28;
  //     if (OneBSSClient) {
  //       phanvung_id = JSON.parse(OneBSSClient)['phanvung_id'];
  //     }
  //     return phanvung_id
  //   }
  // },
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
      <!-- <b-row>
          <b-col sm="2" cols="12"> 
            <div class="key">User ONT</div>
            </b-col> 
          <b-col sm="3" cols="12">         
            <div class="value">
                  <input type="text" v-model= "txtUserONT" labelWidth="0" ref="textFocus" class="form-control">                                            
            </div>                         
          </b-col> 
      </b-row>  
      <b-row>
          <b-col sm="2" cols="12"> 
            <div class="key">Password ONT</div>
          </b-col> 
          <b-col sm="3" cols="12">                               
              <div class="value">
                <input type="text" v-model= "txtPasswordONT" labelWidth="0" ref="textFocuspwd" class="form-control">              
              </div>                         
          </b-col> 
      </b-row>        -->
  </div>   
</template>