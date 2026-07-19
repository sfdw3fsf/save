<template>
  <div>
    <div class="legend-title">Danh sách tham số mặc định</div>
    <KDataGrid
        ref="gridThamSoNguoiDung"
        :columns="columns"
        :dataSource="dataSources"
        :showColumnCheckbox="false"
        :enable-paging-server="false"
        :allowPaging="true"
        :showFilter="false"
        :visibleHeader="false"
        :editSettings="editSettings"
        @cellSaved="editGridThamSoNguoiDung"
        :enabledSelectFirstRow="true"/>
  </div>
</template>
<script>
import DefaultParameterAPI from './DefaultParameterAPI'
import KDataGrid from '@/components/kylq_components/KDataGrid'
export default {
    name:'ThamSoNguoiDung',
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
            await this.lay_ds_thamso_macdinh(1)
        },
        async clearDuLieu(){
            this.dataSources=[]
        },
        async capnhat_thamso(){
            let data=this.layDulieuCapNhat()
            await this.sp_ct_tsmd_update(JSON.stringify(data))
        },
        editGridThamSoNguoiDung(arg){
            let changes = this.$refs.gridThamSoNguoiDung.getBatchChanges()
            console.log('arg',arg)
            console.log('changes', changes)

        },
        layDulieuCapNhat(){
            let changes = this.$refs.gridThamSoNguoiDung.getBatchChanges().changedRecords
            if(changes.length>0){
                for(let i=0;i<changes.length;i++){
                    let change=changes[i]
                    const index=this.dataSources.findIndex(x=>x.ma_ts==change.ma_ts)
                    if(index>-1){
                        this.dataSources[index].giatri=change.giatri
                    }
                }
            }
            console.log('layDulieuCapNhat', this.dataSources)
            //"[{'MA_TS':'CHUCDANH_TTR_TEST','NGUOIDUNG_ID': 1, 'GIATRI': 2434}]"
            let nguoidung_id=this.$root.token.getNguoiDungID()
            return this.dataSources.map(x=>{
                return {
                    MA_TS:x.ma_ts,
                    NGUOIDUNG_ID:nguoidung_id,
                    GIATRI:x.giatri?x.giatri.toString().trim():null
                }
            })
            // let thamso="["
            // this.dataSources.forEach((item, index)=>{
            //     var row="{'MA_TS':'#1','NGUOIDUNG_ID': #2, 'GIATRI': '#3'}"
            //     row=row.replace("#1", item.ma_ts)
            //     row=row.replace("#2", nguoidung_id)
            //     if(item.giatri){
            //         row=row.replace("#3", item.giatri.toString().trim())
            //     }else{
            //         row=row.replace("'#3'", null)
            //     }
                
            //     if(index==this.dataSources.length-1){
            //         thamso=thamso.concat(row)
            //     }else{
            //         thamso=thamso.concat(row).concat(', ')
            //     }
            // })
            // thamso=thamso.concat("]")
            // return thamso

        },
        async lay_ds_thamso_macdinh(kieu){
          try{
            this.loading(true)
            this.dataSources=[]
            let response=await DefaultParameterAPI.lay_ds_thamso_macdinh(this.axios, kieu)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
               this.dataSources=response.data.data
            }else{
               this.dataSources=[]
            }
          }catch(e){
            this.$toast.error('Không load được danh sách tham số người dùng')
          }finally{
            this.loading(false)
          }
        },
        async sp_ct_tsmd_update(data){
            try{
                this.loading(true)
                let response=await DefaultParameterAPI.sp_ct_tsmd_update(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    this.$toast.success('Cập nhật tham số người dùng thành công!')
                    await this.lay_ds_thamso_macdinh(1)
                }else {
                    if(response && response.data &&response.data.message){
                        this.$toast.error(response.data.message)
                    }else{
                        this.$toast.error('Đã xảy ra lỗi, cập nhật tham số người dùng không thành công!')
                    }
                }
            }catch(error){
                this.loading(false)
                if(error.data&&error.data.message){
                    this.$toast.error(error.data.message)
                }else if(error.response&&error.response.data&&error.response.data.message){
                    this.$toast.error(error.response.data.message)
                }else{
                    this.$toast.error('Đã xảy ra lỗi, cập nhật tham số người dùng không thành công!')
                }
            }
        }
    }

}
</script>