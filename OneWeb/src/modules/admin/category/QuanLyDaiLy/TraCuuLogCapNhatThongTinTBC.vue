<template>
    <div class="main-wrapper">
        <breadcrumb :header="header" />
        <ActionTop :actions="actions" @onActionClick="onActionClick"/>
        <div class="page-content">
            <div class="box-form">
                <div class="legend-title">
                    Thông tin tra cứu
                </div>
                <div class="row">
                    <div class="col-sm-5 col-12">
                        <div class="info-row">
                            <div class="key w100">Tỉnh</div>
                            <div class="value">
                                <div class="select-custom">
                                    <select v-model="tinh_selected" class="form-control" disabled>
                                        <option v-for="item in ds_tinh" :key="item.tinh_id" :value="item.tinh_id">
                                            {{item.tentinh}}
                                        </option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-7 col-12">

                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-5 col-12">
                        <div class="info-row">
                            <div class="key w100">Tìm kiếm theo</div>
                            <div class="value">
                                <div class="select-custom">
                                    <select name="" id="" class="form-control">
                                        <option value=""></option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-7 col-12">
                        <div class="info-row">
                            <div class="value">
                                <input type="text" class="form-control">
                            </div>
                        </div>
                    </div>
                </div>
                
            </div>
            <div class="box-form">
                <div class="legend-title">Thông tin biến động</div>
                <KDataGrid
                    :columns="columns"
                    :dataSource="dataSources"
                    :showColumnCheckbox="false"
                    :enable-paging-server="false"
                    :allowPaging="true"
                    :showFilter="true"
                    :enabledSelectFirstRow="false"/>
            </div>

        </div>
    </div>
</template>
<script>
import breadcrumb from '@/components/kylq_components/breadcrumb.vue'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import KDataGrid from '@/components/kylq_components/KDataGrid'
import API from './API'
export default {
    name:'TraCuuLogCapNhatThongTinTBC',
    components:{
        breadcrumb,
        ActionTop,
        KDataGrid
    },
    data(){
        return {
            header: {
                title: "Tra cứu log cập nhật thông tin thông báo cước",
                list: [
                    { 
                        name: "Tra cứu", 
                        link: { name: "Ui.cards" },
                        active: false
                    },
                    { 
                        name: "Quản lý thu nợ", 
                        link: { name: "Ui.cards" },
                        active: false
                    },
                    { 
                        name: "Tra cứu thu nợ", 
                        link: { name: "Ui.cards" },
                        active: false
                    },
                    {
                        name: "Tra cứu log cập nhật thông tin thông báo cước",
                        link: { name: "Ui.buttons" },
                        active: true
                    },
                ]
            },
            actions:[
                {
                    id:'timkiem',
                    name:'Tìm kiếm',
                    active: true,
                    visible: true,
                    icon_class:'one-search'
                }
            ],
            columns:[
                {
                    fieldName:'ma_gd',
                    headerText:'Mã GD',
                    allowFiltering:true,
                    freeze:"left"
                },
                {
                    fieldName:'ma_tt',
                    headerText:'Mã TT',
                    allowFiltering:true,
                    freeze:"left"
                },
                {
                    fieldName:'email',
                    headerText:'Email',
                    allowFiltering:true
                },
                {
                    fieldName:'kenhnhan',
                    headerText:'Kênh nhận',
                    allowFiltering:true
                },
                {
                    fieldName:'nguoi_cn',
                    headerText:'Người CN',
                    allowFiltering:true
                },
                {
                    fieldName:'donvi_cn',
                    headerText:'Đơn vị CN',
                    allowFiltering:true
                },
                {
                    fieldName:'kenh_cn',
                    headerText:'Kênh CN',
                    allowFiltering:true
                },
                {
                    fieldName:'sdt',
                    headerText:'SĐT',
                    allowFiltering:true
                },
                {
                    fieldName:'sdt',
                    headerText:'SĐT',
                    allowFiltering:true
                },
                {
                    fieldName:'thoigian_cn',
                    headerText:'Thời gian CN',
                    allowFiltering:true
                },
            ],
            dataSources:[],
            ds_tinh:[],
            tinh_selected:null
        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='timkiem'){

            }
        },

        async initDuLieu(){
            this.ds_tinh=await this.lay_danhsach_tinh()
            if(this.ds_tinh.length>0){
                this.tinh_selected=this.$root.token.getPhanVungID()
            }
        },
        async lay_danhsach_tinh(){
            try{
                this.loading(true)
                let response=await API.lay_danhsach_tinh(this.axios)
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
    },
    mounted(){
        setTimeout(()=>{
            this.initDuLieu()
        }, 500)
    }
}
</script>