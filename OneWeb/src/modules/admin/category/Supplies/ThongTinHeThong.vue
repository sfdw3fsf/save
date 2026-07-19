<template>
  <div class="box-move-select-table ">
      <KDataGrid
        :columns="columns"
        :dataSource="dataSources"
        :showColumnCheckbox="false"
        :enable-paging-server="false"
        :allowPaging="true"
        :showFilter="true"
        :enabledSelectFirstRow="false"
        panelDataHeight="250px"/>
  </div>
</template>
<script>
import SuppliesAPI from './SuppliesAPI'
import KDataGrid from '@/components/kylq_components/KDataGrid'
export default {
    name:'ThongTinHeThong',
    props:{
        vattu_id:{
            type:Number,
            default:-1
        }
    },
    components:{
        KDataGrid
    },
    data(){
        return {
            columns:[
                {
                    fieldName:'ma_hethong',
                    headerText:'Mã HT',
                    allowFiltering:true
                },
                {
                    fieldName:'ten_hethong',
                    headerText:'Tên hệ thống',
                    allowFiltering:true
                },
                {
                    fieldName:'linhvuc',
                    headerText:'Lĩnh vực',
                    allowFiltering:true
                },
            ],
            dataSources:[],
        }
    },
    methods:{
        async initDuLieu(){
            if(this.vattu_id==-1){
                return
            }
            this.dataSources=[]
            this.dataSources=await this.sp_nap_ds_hethong(this.vattu_id)
            //this.dataSources=await this.sp_nap_ds_hethong(6542)
        },
        async clearData(){
            this.dataSources=[]
        },
        async sp_nap_ds_hethong(vattu_id){
            try{
                this.loading(true)
                let response = await SuppliesAPI.sp_nap_ds_hethong(this.axios, vattu_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
    }
}
</script>