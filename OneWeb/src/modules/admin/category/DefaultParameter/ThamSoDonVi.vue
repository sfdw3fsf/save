<template>
  <div>
    <div class="legend-title">Danh sách tham số mặc định</div>
    <KDataGrid
        ref="gridThamSoDonVi"
        :columns="columns"
        :dataSource="dataSources"
        :showColumnCheckbox="false"
        :enable-paging-server="false"
        :allowPaging="true"
        :showFilter="false"
        :visibleHeader="false"
        :editSettings="editSettings"
        :enabledSelectFirstRow="true"/>
  </div>
</template>
<script>
import DefaultParameterAPI from './DefaultParameterAPI'
import KDataGrid from '@/components/kylq_components/KDataGrid'
export default {
    name:'ThamSoDonVi',
    components:{
        KDataGrid
    },
    data(){
        return {
            columns:[
                {
                    fieldName: "ten_ts",
                    headerText: "Tên TS",
                    allowFiltering: true,
                    allowSorting:true,
                    textAlign:'left'

                },
                {
                    fieldName: "giatri",
                    headerText: "Giá trị",
                    allowFiltering: true,
                    allowSorting:true,
                    allowEditing:true,
                    textAlign:'left',
                    validationRules: {maxLength: [(args) => {return args['value'].length <= 200;}, 'Giá trị phải nhỏ hơn 200 ký tự!'] }
                }
            ],
            dataSources:[],
            editSettings:{ allowEditing: true, mode:'Batch'},

        }
    },
    methods:{
        async initDuLieu(){
            let donvi_nd=this.$root.token.getDonViID()
            await this.lay_ds_thamso_macdinh_dv(donvi_nd)
        },
        async clearDuLieu(){
            this.dataSources=[]
        },
        async capnhat_thamso(){
            let data=this.layDulieuCapNhat()
            await this.sp_ct_tsmd_dv_update(JSON.stringify(data))
        },
        layDulieuCapNhat(){
            let changes = this.$refs.gridThamSoDonVi.getBatchChanges().changedRecords
            if(changes.length>0){
                for(let i=0;i<changes.length;i++){
                    let change=changes[i]
                    const index=this.dataSources.findIndex(x=>x.ma_ts==change.ma_ts)
                    if(index>-1){
                        this.dataSources[index].giatri=change.giatri
                    }
                }
            }
            let donvi_id=this.$root.token.getDonViID()
            return  this.dataSources.map(x=>{
                return {
                    DONVI_ID:donvi_id,
                    GIATRI:x.giatri?x.giatri.toString().trim():null,
                    MA_TS:x.ma_ts
                }
            })
            // let thamso="["
            // this.dataSources.forEach((item, index)=>{
            //     var row="{'DONVI_ID': #1, 'GIATRI': '#2', 'MA_TS': '#3'}"
            //     row=row.replace("#1", donvi_id)
            //     //row=row.replace("#2", item.giatri)
            //     if(item.giatri){
            //         row=row.replace("#2", item.giatri.toString().trim())
            //     }else{
            //         row=row.replace("'#2'", null)
            //     }
            //     row=row.replace("#3", item.ma_ts)
            //     if(index==this.dataSources.length-1){
            //         thamso=thamso.concat(row)
            //     }else{
            //         thamso=thamso.concat(row).concat(', ')
            //     }
            // })
            // thamso=thamso.concat("]")
            // return thamso
        },
        async lay_ds_thamso_macdinh_dv(donvi_id){
          try{
            this.loading(true)
            this.dataSources=[]
            let response=await DefaultParameterAPI.lay_ds_thamso_macdinh_dv(this.axios, donvi_id)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
               this.dataSources=response.data.data
            }else{
               this.dataSources=[]
            }
          }catch(e){
            this.$toast.error('Không load được danh sách tham số đơn vị')
          }finally{
            this.loading(false)
          }
        },
        async sp_ct_tsmd_dv_update(data){
            try{
                this.loading(true)
                let response=await DefaultParameterAPI.sp_ct_tsmd_dv_update(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    this.$toast.success('Cập nhật tham số đơn vị thành công!')
                    await this.initDuLieu()
                }else {
                    if(response && response.data &&response.data.message){
                        this.$toast.error(response.data.message)
                    }else{
                        this.$toast.error('Đã xảy ra lỗi, cập nhật tham số đơn vị không thành công!')
                    }
                }
            }catch(error){
                this.loading(false)
                if(error.data&&error.data.message){
                    this.$toast.error(error.data.message)
                }else if(error.response&&error.response.data&&error.response.data.message){
                    this.$toast.error(error.response.data.message)
                }else{
                    this.$toast.error('Đã xảy ra lỗi, cập nhật tham số đơn vị không thành công!')
                }
            }
        }
    }

}
</script>