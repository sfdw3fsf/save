<template>
  <div class="box-form">
    <div class="legend-title">Lịch sử thay đổi thông tin giới thiệu</div>
    <DataGrid
        :columns="columns"
        :dataSource="dataSources"
        :showColumnCheckbox="false"
        :enable-paging-server="false"
        :allowPaging="true"
        :showFilter="true"
        :enabledSelectFirstRow="true"/>
  </div>
</template>
<script>
import KDataGrid from '@/components/kylq_components/KDataGrid'
import ChangeIntroducePeopleAPI from './ChangeIntroducePeopleAPI'
import EventBus from '@/utils/eventBus'
import moment from 'moment'
export default {
    name:'LichSuThayDoi',
    components:{
        KDataGrid
    },
    data(){
        return {
             columns:[
                {
                    fieldName: "ma_tb_gt",
                    headerText: "Mã TB",
                    allowFiltering: true
                },
                {
                    fieldName: "ma_tt",
                    headerText: "Mã thanh toán",
                    allowFiltering: true
                },
                {
                    fieldName: "tru_tien",
                    headerText: "Tiền",
                    allowFiltering: true
                },
                {
                    fieldName: "nguoi_cn",
                    headerText: "Người CN",
                    allowFiltering: true
                },
                {
                    fieldName: "ngay_cn",
                    headerText: "Ngày CN",
                    allowFiltering: true
                }
            ],
            dataSources:[],
        }
    },
    methods:{
        onSelectedRow(item){
            //EventBus.$emit('change_introduce_people',{action:'receive-item', value:item})
        },
        async hienthithongtin(ma_gd){
            try{
                this.loading(true)
                //lấy thông tin hiển thị
                let response=await ChangeIntroducePeopleAPI.lay_ds_hd_tuvan(this.axios, ma_gd, 0, 0)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data){
                    if(response.data.data.length>0){
                        EventBus.$emit('change_introduce_people',{action:'receive-item', value: response.data.data[0]})
                    }else{
                        EventBus.$emit('change_introduce_people',{action:'receive-item', value: {}})
                        this.loading(false)
                        return
                    }
                }else{
                    EventBus.$emit('change_introduce_people',{action:'receive-item', value: {}})
                    this.loading(false)
                    return
                }

                //lấy danh sách lịch sử
                let responseLichSu=await ChangeIntroducePeopleAPI.lay_ds_hd_tuvan(this.axios, ma_gd, 0, 1)
                if(responseLichSu && responseLichSu.data && responseLichSu.data.error_code && responseLichSu.data.error_code == 'BSS-00000000' && responseLichSu.data.data) {
                    this.dataSources=responseLichSu.data.data.map(item=>{
                        return Object.assign(item,{
                            ngay_cn:item.ngay_cn?moment(item.ngay_cn,'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss'):null
                        })
                    })
                }else{
                    this.dataSources=[]
                }
            }catch(e){
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Đã xảy ra lỗi, lấy lịch sử thay đổi không thành công')
                }
                
            }finally{
                this.loading(false)
            }
        },
        receiveData(data){
            if(data.action=='enter_magd'){
                this.hienthithongtin(data.value)
            }
        }
    },
    created () {
        EventBus.$on('change_introduce_people', this.receiveData)
    },
    destroyed () {
        EventBus.$off('change_introduce_people', this.receiveData)
    },    

}
</script>