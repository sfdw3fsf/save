<template>
    <div class="box-form" style="min-height: 348px">
          <div class="legend-title">Đổi Kho Giao PSIS</div>
          <div class="info-row">
            <div class="key w70">Kho Giao</div>
            <div class="value">
              <div class="select-custom">
                <ejs-dropdownlist class="form-control" locale="vi" v-model="cboKhoPSIS.value" :dataSource="cboKhoPSIS.dataSources" :fields="{ value: 'KHO_ID', text: 'TEN_KHO' }" :allowFiltering="true"> </ejs-dropdownlist>
              </div>
            </div>
            <div class="value" style="width: 20px">
              <div class="input-more-button right">
                <button class="btn" @click="savePSISKho" >
                  <span class="icon one-save"></span>
                </button>
              </div>
            </div>
          </div>
        </div>
</template>
<script>
import api from './api.js'
import _ from 'lodash'
export default {
  name: 'PSISComponent',
  emits: ['onSavePSISKho'],
  props: {
    vchungtu_id: {
      type: [String, Number],
      default: null,
      required: true
    },
  },
  data() {
    return {
      
      cboKhoPSIS: {
        dataSources: [],
        value: null
      },
      
    }
  },
  computed:{
  },
  watch: {
   
    
  },
  async created() {
   await this.getDSKhoPSIS();
  },

  methods: {
    async getDSKhoPSIS() {
      try {
    
        const response = await api.getDanhSachKhoPSIS(this.axios)
   
        if(response.data.data){
            this.cboKhoPSIS.dataSources = response.data.data;
            if (this.cboKhoPSIS.dataSources) this.cboKhoPSIS.value = this.cboKhoPSIS.dataSources[0]?.KHO_ID;
        }
   
      } catch (error) {
        console.log(error)
      } 
    },
    async savePSISKho(){
        if(!this.cboKhoPSIS.value){
            return this.$toast.warning('Bạn phải chọn kho giao')
        }
        this.$root.loading(true)
        try {
            const response = await api.saveKhoPSIS(this.axios, {
            vchungtu_id: this.vchungtu_id, 
            vkho_id: this.cboKhoPSIS.value
        })
        if(response.data.error_code === "BSS-00000000"){
            this.$root.loading(false)
            this.$toast.success('Ghi lại thành công.')
            this.$emit('onSavePSISKho', response.data.data)
        }
        } catch (error) {
            this.$root.loading(false)
            console.log(error)
            this.$toast.error('Không thể ghi lại.') 
        }
        

    }
    
  }
}
</script>
<style scoped>

</style>
