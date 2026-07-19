<template>
    <div class="box-move-select-table">
      <div class="box-col">
        <div class="legend-title">Mục đích đã gán</div>
        <KDataGrid
            :columns="columns"
            :dataSource="dataSourcesGan"
            :showColumnCheckbox="true"
            :enable-paging-server="false"
            :allowPaging="true"
            :showFilter="true"
            ref="gridDaGan"
            :enabledSelectFirstRow="false"
            panelDataHeight="250px"/>
      </div>
      <div class="actions">
        <button class="btn" @click="boGan">
          <span class="fa fa-angle-right"></span>
        </button>
        <button class="btn" @click="gan">
          <span class="fa fa-angle-left"></span>
        </button>
      </div>
      <div class="box-col">
        <div class="legend-title">Mục đích chưa gán</div>
        <KDataGrid
            :columns="columns"
            :dataSource="dataSourcesChuaGan"
            :showColumnCheckbox="true"
            :enable-paging-server="false"
            :allowPaging="true"
            :showFilter="true"
            ref="gridChuaGan"
            :enabledSelectFirstRow="false"
            panelDataHeight="250px"/>
      </div>
    </div>
</template>
<script>
import SuppliesAPI from './SuppliesAPI'
import KDataGrid from '@/components/kylq_components/KDataGrid'
export default {
    name:'MucDichPhanVung',
    components:{
        KDataGrid
    },
    data(){
        return {
            columns:[
                {
                    fieldName:'ma_md',
                    headerText:'Mã MĐ',
                    allowFiltering:true
                },
                {
                    fieldName:'mucdich',
                    headerText:'Tên MĐ',
                    allowFiltering:true
                },
                {
                    fieldName:'nhom_md',
                    headerText:'Nhóm MĐ',
                    allowFiltering:true
                },
            ],
            dataSourcesGan:[],
            dataSourcesChuaGan:[]
        }
    },
    methods:{
        async initDuLieu(){
            this.dataSourcesGan=[]
            this.dataSourcesChuaGan=[]

            this.dataSourcesChuaGan=await this.lay_ds_mucdich_phanvung(0)
            this.dataSourcesGan=await this.lay_ds_mucdich_phanvung(1)
        },
        async clearData(){
            this.dataSourcesGan=[]
            this.dataSourcesChuaGan=[]
        },
        async boGan(){
            let dataCheckedsGan=this.$refs.gridDaGan.getSelectedRecords()
            if(dataCheckedsGan.length==0){
                return
            }
            let vds=dataCheckedsGan.map(x=>{
                return{
                    MUCDICH_ID:x.mucdich_id
                }
            })
            let data={
                vkieu: 1, 
                vds: JSON.stringify(vds), 
                vnguoi_cn:this.$root.token.getUserName()
            }
            let result=await this.capnhat_mucdich_phanvung(data)
            if(result=='1'){
                this.$toast.success('Bỏ gán thành công !')
                await this.initDuLieu()
            }else{
                this.$toast.error(result)
            }
        },
        async gan(){
            let dataCheckedsChuaGan=this.$refs.gridChuaGan.getSelectedRecords()
            if(dataCheckedsChuaGan.length==0){
                return
            }
            let vds=dataCheckedsChuaGan.map(x=>{
                return{
                    MUCDICH_ID:x.mucdich_id
                }
            })
            let data={
                vkieu: 0, 
                vds: JSON.stringify(vds), 
                vnguoi_cn:this.$root.token.getUserName()
            }
            let result=await this.capnhat_mucdich_phanvung(data)
            if(result=='1'){
                this.$toast.success('Gán thành công !')
                await this.initDuLieu()
            }else{
                this.$toast.error(result)
            }
        },
        async lay_ds_mucdich_phanvung(kieu){
            try{
                this.loading(true)
                let response = await SuppliesAPI.lay_ds_mucdich_phanvung(this.axios, kieu)
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
        async capnhat_mucdich_phanvung(data){
            try{
                this.loading(true)
                let response = await SuppliesAPI.capnhat_mucdich_phanvung(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    if(response && response.data && response.data.message){
                        return response.data.message
                    }else{
                        return 'Đã xảy ra lỗi, thao tác không thành công!'
                    }
                }
            }catch(e){
                this.loading(false)
                if(error.data&&error.data.message){
                    return error.data.message
                }else if(error.response&&error.response.data&&error.response.data.message){
                    return error.response.data.message
                }else{
                    return 'Đã xảy ra lỗi, thao tác không thành công!'
                }
            }
        }
    }

}
</script>
