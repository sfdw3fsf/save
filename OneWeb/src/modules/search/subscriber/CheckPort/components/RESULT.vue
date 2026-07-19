<script>
export default {
  props:{    
   result: {}
  },
  data() {
    return {
      target: '.main-wrapper',     
      txtResult:'', 
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
      //console.log(this.result.response)
         this.txtResult=this.result;
    //   }      
    //   this.indChucNang = this.value.ddlChucNang      
     
    })
  },
    
  methods: {    
    Clear() {
      this.txtResult = null;     
    },   
  }
}
</script>
<style>
.result .port-info {
  background-color: #ffffff !important;
}

</style>
<template>
  <div class="result">     
      <b-row>
          <b-col sm="12" cols="12">
            <span>Kết quả</span>
          </b-col>           
        </b-row>
        <b-row>
          <b-col sm="12" cols="12">
            <textarea v-model="result" disabled name="" class="form-control port-info" style="height:250px;resize: none;"></textarea>
          </b-col>           
        </b-row>
  </div>   
</template>