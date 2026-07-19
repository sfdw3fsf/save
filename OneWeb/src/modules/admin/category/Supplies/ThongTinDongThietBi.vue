<template>
    <div class="box-move-select-table">
      <div class="box-col">
        <div class="legend-title">Danh sách dòng thiết bị đã gán</div>
        <KDataGrid
            :columns="columnsGan"
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
        <div class="legend-title">Danh sách dòng thiết bị chưa gán</div>
        <KDataGrid
            :columns="columnsChuaGan"
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
    name:'ThongTinDongThietBi',
    props:{
        vattu_id:{
            type:Number,
            default:-1
        },
        hangsx_id:{
            type:Number,
            default:0
        }

    },
    components:{
        KDataGrid
    },
    data(){
        return{
            columnsGan:[
                {
                    fieldName:'MA_DONGTBI',
                    headerText:'Mã thiết bị',
                    allowFiltering:true
                },
                {
                    fieldName:'TEN_DONGTBI',
                    headerText:'Dòng thiết bị',
                    allowFiltering:true
                },
            ],
            dataSourcesGan:[],
            columnsChuaGan:[
                {
                    fieldName:'MA_DONGTBI',
                    headerText:'Mã thiết bị',
                    allowFiltering:true
                },
                {
                    fieldName:'TEN_DONGTBI',
                    headerText:'Dòng thiết bị',
                    allowFiltering:true
                },
                {
                    fieldName:'HANG_SX',
                    headerText:'Hãng SX',
                    allowFiltering:true
                },
                {
                    fieldName:'GHICHU',
                    headerText:'Ghi chú',
                    allowFiltering:true
                }
            ],
            dataSourcesChuaGan:[]
        }
    },
    methods:{
        boGan(){
            let dataCheckedsGan=this.$refs.gridDaGan.getSelectedRecords()
            if(dataCheckedsGan.length==0){
                return
            }
            let ds_para={
                HANGSX_ID: this.hangsx_id,
                THEMMOI: 0,
                VATTU_ID: this.vattu_id
            }
            let js_dong_tbi=dataCheckedsGan.map(item=>{
                return {
                    DONGTBI_ID: item.DONGTBI_ID
                }
            })
            this.gan_go_dong_tbi(ds_para, js_dong_tbi, 2)
        },
        gan(){
            let dataCheckedsChuaGan=this.$refs.gridChuaGan.getSelectedRecords()
            if(dataCheckedsChuaGan.length==0){
                return
            }
            let ds_para={
                HANGSX_ID: this.hangsx_id,
                THEMMOI: 0,
                VATTU_ID: this.vattu_id
            }
            let js_dong_tbi=dataCheckedsChuaGan.map(item=>{
                return {
                    DONGTBI_ID: item.DONGTBI_ID
                }
            })
            this.gan_go_dong_tbi(ds_para, js_dong_tbi, 1)
        },
        async initDuLieu(){
            if(this.vattu_id==-1){
                return
            }
            //await this.fn_nap_ds_dong_tbi(6542, 277)
            await this.fn_nap_ds_dong_tbi(this.vattu_id, this.hangsx_id)
        },
        async clearData(){
            this.dataSourcesGan=[]
            this.dataSourcesChuaGan=[]
        },
        async fn_nap_ds_dong_tbi(vattu_id, hangsx_id){
            try{
                this.loading(true)
                this.dataSourcesGan=[]
                this.dataSourcesChuaGan=[]
                let response = await SuppliesAPI.fn_nap_ds_dong_tbi(this.axios, vattu_id, hangsx_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    //this.dataSources=response.data.data
                    let result=JSON.parse(response.data.data)
                    if(result&&result.ERROR_CODE==1&&result.RESULT){
                        if(result.RESULT.DS_DAGAN){
                            this.dataSourcesGan=result.RESULT.DS_DAGAN
                        }
                        if(result.RESULT.DS_CHUAGAN){
                            this.dataSourcesChuaGan=result.RESULT.DS_CHUAGAN
                        }
                    }else{
                        this.dataSourcesGan=[]
                        this.dataSourcesChuaGan=[]
                        if(result&&result.MESSAGE){
                            this.$toast.error(result.MESSAGE)
                        }else{
                            this.$toast.error('Không load được danh sách dòng thiết bị')
                        }
                    }
                }else{
                    this.dataSourcesGan=[]
                    this.dataSourcesChuaGan=[]
                }
            }catch(e){
                this.loading(false)
                this.$toast.error('Không load được danh sách dòng thiết bị')
            }
        },
        //kieu=1:Gán, kieu==2: Bỏ gán
        async gan_go_dong_tbi(ds_para, js_dong_tbi, kieu){
          try{
            this.loading(true)
            let response = await SuppliesAPI.gan_go_dong_tbi(this.axios, ds_para, js_dong_tbi, kieu)
            this.loading(false)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                let result=JSON.parse(response.data.data)
                if(result&&result.ERROR_CODE==1){
                    this.$toast.success(kieu==1?'Gán thành công':'Bỏ gán thành công')
                    this.initDuLieu()
                }else{
                  if(result&&result.MESSAGE){
                    this.$toast.error(result.MESSAGE)
                  }else{
                    this.$toast.error(kieu==1?'Gán dòng thiết bị không thành công!':'Bỏ gán dòng thiết bị không thành công')
                  }
                }
            }else{
                if(response && response.data && response.data.message){
                    this.$toast.error(response.data.message)
                }else{
                    this.$toast.error(kieu==1?'Gán dòng thiết bị không thành công!':'Bỏ gán dòng thiết bị không thành công')
                }   
            }
          }catch(error){
            this.loading(false)
            if(error.data&&error.data.message){
                this.$toast.error(error.data.message)
            }else if(error.response&&error.response.data&&error.response.data.message){
                this.$toast.error(error.response.data.message)
            }else{
                this.$toast.error(kieu==1?'Gán dòng thiết bị không thành công!':'Bỏ gán dòng thiết bị không thành công')
            }
          }
        },
    }

}
</script>