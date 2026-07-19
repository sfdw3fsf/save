<template>
  <div>
    <div class="legend-title">Danh sách loại chương trình</div>
    <DataGrid
        ref="gridLoaiCT"
        :columns="columns"
        :dataSource="dataSources"
        :showColumnCheckbox="false"
        :enable-paging-server="false"
        :allowPaging="true"
        :showFilter="false"
        :editSettings="editSettings"
        :enabledSelectFirstRow="true"/>
  </div>
</template>
<script>
import DefaultParameterAPI from './DefaultParameterAPI'
export default {
    name:'LoaiChuongTrinh',
    data(){
        return {
            columns:[
                {
                    fieldName: "tenloai_ct",
                    headerText: "Loại chương trình",
                    allowFiltering: true,
                    allowSorting:true,
                    textAlign:'left'

                },
                {
                    fieldName: "ma_nd",
                    headerText: "Tên đăng nhập",
                    allowFiltering: true,
                    allowSorting:true,
                    allowEditing:true,
                    textAlign:'left'
                },
                {
                    fieldName: "matkhau",
                    headerText: "Mật khẩu",
                    allowFiltering: true,
                    allowSorting:true,
                    allowEditing:true,
                    textAlign:'left',
                    customAttributes:{class: 'customcss'}
                }
            ],
            dataSources:[],
            editSettings:{ allowEditing: true, mode:'Batch'},
        }
    },
    methods:{
        async initDuLieu(){
            await this.sp_tsmd_ht_loaichuongtrinh()
        },
        async clearDuLieu(){
            this.dataSources=[]
        },
        async capnhat_thamso(){
            let data=this.layDulieuCapNhat()
            console.log(data)
            this.sp_nguoidung_loaict_update(JSON.stringify(data))
        },
        onCellSaved(args){
            console.log('onCellSaved', args)
        },
        onQueryCellInfo(args){
            console.log('onQueryCellInfo', args)
        },
        cellSelected(args){
            console.log('cellSelected', args)
        },
        layDulieuCapNhat(){
            let changes = this.$refs.gridLoaiCT.getBatchChanges().changedRecords
            if(changes.length>0){
                for(let i=0;i<changes.length;i++){
                    let change=changes[i]
                    const index=this.dataSources.findIndex(x=>x.loai_ct_id==change.loai_ct_id)
                    if(index>-1){
                        this.dataSources[index].ma_nd=change.ma_nd
                        this.dataSources[index].matkhau=change.matkhau
                    }
                }
            }
            //"[{'NGUOIDUNG_ID':3211,'LOAICT_ID': 4, 'MA_ND':'035-HPG000888', 'MATKHAU': 'hungle@12' }]"
            let nguoidung_id=this.$root.token.getNguoiDungID()
            return this.dataSources.map(x=>{
                return {
                    NGUOIDUNG_ID:nguoidung_id,
                    LOAICT_ID:x.loai_ct_id,
                    MA_ND:x.ma_nd?x.ma_nd.toString().trim():null,
                    MATKHAU:x.matkhau?x.matkhau:null
                }
            })
            // let thamso="["
            // this.dataSources.forEach((item, index)=>{
            //     var row="{'NGUOIDUNG_ID': #1,'LOAICT_ID': #2, 'MA_ND':'#3', 'MATKHAU': '#4'}"
            //     row=row.replace("#1", nguoidung_id)
            //     row=row.replace("#2", item.loai_ct_id)
            //     if(item.ma_nd){
            //         row=row.replace("#3", item.ma_nd.toString().trim())
            //     }else{
            //         row=row.replace("'#3'", null)
            //     }
            //     if(item.matkhau){
            //         row=row.replace("#4", item.matkhau)
            //     }else{
            //         row=row.replace("'#4'", null)
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
        async sp_tsmd_ht_loaichuongtrinh(){
          try{
            this.loading(true)
            this.dataSources=[]
            let response=await DefaultParameterAPI.sp_tsmd_ht_loaichuongtrinh(this.axios)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
               this.dataSources=response.data.data
            }else{
               this.dataSources=[]
            }
          }catch(e){
            this.$toast.error('Không load được danh sách loại chương trình')
          }finally{
            this.loading(false)
          }
        },
        async sp_nguoidung_loaict_update(data){
            try{
                this.loading(true)
                let response=await DefaultParameterAPI.sp_nguoidung_loaict_update(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    this.$toast.success('Cập nhật tham số loại chương trình thành công!')
                    await this.sp_tsmd_ht_loaichuongtrinh()
                }else {
                    if(response && response.data &&response.data.message){
                        this.$toast.error(response.data.message)
                    }else{
                        this.$toast.error('Đã xảy ra lỗi, cập nhật tham số loại chương trình không thành công!')
                    }
                }
            }catch(error){
                this.loading(false)
                if(error.data&&error.data.message){
                    this.$toast.error(error.data.message)
                }else if(error.response&&error.response.data&&error.response.data.message){
                    this.$toast.error(error.response.data.message)
                }else{
                    this.$toast.error('Đã xảy ra lỗi, cập nhật tham số loại chương trình không thành công!')
                }
            }
        }
    }

}
</script>
<style>
.e-grid .e-rowcell.customcss{
    -webkit-text-security: disc;
    font: small-caption;
    font-size: 16px;
}
</style>