<template>
  <div class="box-form">
    <div class="legend-title">Thông tin tra cứu</div>
    <div class="row">
      <div class="col-sm-5 col-12">
        <div class="info-row marb0">
          <div class="key w30 nowrap">Thông tin KH</div>
          <div class="value">
            <ejs-dropdownlist 
                allowFiltering="true"
                v-model="cusinfo_vdc_selected"
                @change="onCusinfoVdcChange"
                :dataSource="cusinfo_vdc"
                :fields="{ text: 'NAME', value: 'ID' }" />
          </div>
        </div>
      </div>
      <div class="col-sm-4 col-12">
        <div class="info-row marb0">
          <div class="key w30 nowrap">Lựa chọn</div>
          <div class="value">
            <ejs-dropdownlist 
                allowFiltering="true"
                v-model="operation_vdc_selected"
                @change="onOperationVdcChange"
                :dataSource="operation_vdc"
                :fields="{ text: 'NAME', value: 'ID' }" />
          </div>
        </div>
      </div>
      <div class="col-sm-3 col-12">
        <div class="info-row marb0">
          <div class="key w30 nowrap">Giá trị</div>
          <div class="value">
            <input type="text" class="form-control" @input="onChangeValue($event)" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import PrefixOfCountyAPI from './PrefixOfCountyAPI.js'
export default {
    name:'ThongTinTraCuu',
    data(){
        return{
            cusinfo_vdc:[],
            operation_vdc:[],
            cusinfo_vdc_selected: -1,
            operation_vdc_selected: -1
        }
    },
    methods:{
        async get_tieuchi_tracuu_taikhoan_dichvu(){
            try{
              let response=await PrefixOfCountyAPI.get_tieuchi_tracuu_taikhoan_dichvu(this.axios)
              if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                this.cusinfo_vdc=response.data.data
                if(this.cusinfo_vdc.length>0){
                  this.cusinfo_vdc_selected=this.cusinfo_vdc[0].ID
                  this.$emit('onCusinfoVdcChange', this.cusinfo_vdc_selected)
                }
                
              }
            }catch(e){
              this.$toast.error('Không load được dữ liệu')
            }
        },
        async get_tieuchi_tracuu_taikhoan_dichvu_op(){
            try{
              let response=await PrefixOfCountyAPI.get_tieuchi_tracuu_taikhoan_dichvu_op(this.axios)
               if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                  this.operation_vdc=response.data.data
                  if(this.operation_vdc.length>0){
                    this.operation_vdc_selected=this.operation_vdc[0].ID
                    this.$emit('onOperationVdcChange', this.operation_vdc_selected)
                  }
               }       
            }catch(e){
               this.$toast.error('Không load được dữ liệu')
            }
        },
        onCusinfoVdcChange(){
            this.$emit('onCusinfoVdcChange', this.cusinfo_vdc_selected)
        },
        onOperationVdcChange(){

            this.$emit('onOperationVdcChange', this.operation_vdc_selected)
        },
        onChangeValue(event){
            this.$emit('onChangeValue', event.target.value)
        }

    },
    mounted(){
        this.get_tieuchi_tracuu_taikhoan_dichvu();
        this.get_tieuchi_tracuu_taikhoan_dichvu_op();
    }
    
}
</script>