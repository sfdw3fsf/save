<template>
    <div class="main-wrapper">
        <breadcrumb :header="header" />
        <div class="page-content" style="top:50px!important">
            <div class="box-form">
                <div class="legend-title">Thông tin kênh bán hủy chi trả</div>
                <div class="row">
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key">Kênh bán</div>
                            <div class="value">
                                <div class="select-custom" ref="ds_kenhban">
                                    <select2 :settings="{ dropdownParent: $refs['ds_kenhban'] }"
                                        v-model="kenhban_selected" class="select2"
                                        :disabled="true"
                                        :options="ds_kenhban.map(e=> ({id: e.nhanvien_id, text: e.ten_kb}))"
                                        >
                                    </select2>
                                </div>  
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key">Ngày TT</div>
                            <div class="value">
                                <div class="input-icon-right">
                                    <k-date-picker v-model="ngay_tt" :disabled="true"/>
                                </div>
                            </div>
                        </div>

                    </div>
                     <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key">Tháng</div>
                            <div class="value">
                                <input type="text" readonly :value="input_thang" @change="e=>input_thang=e.target.value" class="form-control">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key">User gạch</div>
                            <div class="value">
                                <div class="select-custom" ref="ds_usergach">
                                    <select2 :settings="{ dropdownParent: $refs['ds_usergach'] }"
                                        v-model="user_huy_selected" class="select2"
                                        :disabled="true"
                                        :options="ds_userhuy.map(e=> ({id: e.nhanvien_id, text: e.usergach}))"
                                        >
                                    </select2>
                                </div>  
                            </div>
                        </div>
                    </div>
                     <div class="col-sm-4 col-12">

                    </div>
                </div>
            </div>
            <div class="box-form">
                <div class="legend-title">Danh sách phiếu hủy chi trả</div>
                <KDataGrid
                    ref="gridDs"
                    :columns="columns"
                    :dataSource="dataSources"
                    :showColumnCheckbox="false"
                    :enable-paging-server="false"
                    :allowPaging="true"
                    :showFilter="true"
                    @onRowSelected="onSelectedRow"
                    :enabledSelectFirstRow="true"
                    :selectionSettings="{ checkboxMode: 'ResetOnRowClick' }"/>
            </div>
        </div>
    </div>
</template>
<script>
import breadcrumb from '@/components/kylq_components/breadcrumb.vue'
import KDataGrid from '@/components/kylq_components/KDataGrid'
import moment from 'moment'
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
import API from './API'
export default {
    name:'PhieuHuy',
    props:{
        kenhban_id:{
            type:Number,
            default:0
        },
        usergach_id:{
            type:Number,
            default:0
        },
        p_ngay_tt:{
            type:String,
            default:null
        },
        thang_cn:{
            type:String,
            default:null
        },
        isPopup:{
            type:Boolean,
            default:false
        }
    },
    components:{
        breadcrumb,
        KDataGrid,
        KDatePicker
    },
    data(){
        return {
            header: {
                title: "Danh sách phiếu hủy thanh toán cho kênh bán",
                list: [
                    {
                        name: "Trang chủ",
                        link: { name: "Ui.buttons" },
                        active: true
                    },
                ]
            },
            kenhban_selected:null,
            ds_kenhban:[],
            input_thang:'',
            user_huy_selected:null,
            ds_userhuy:[],
            ngay_tt:moment(new Date()).format('DD/MM/YYYY'),
            columns:[
                {
                    fieldName:'ngayhuy',
                    headerText:'Ngày hủy',
                    allowFiltering:true,
                    allowSorting:true
                },
                {
                    fieldName:'kenhban',
                    headerText:'Kênh bán',
                    allowFiltering:true,
                    allowSorting:true
                },
                {
                    fieldName:'diachi_tt',
                    headerText:'Địa chỉ',
                    allowFiltering:true,
                    allowSorting:true
                }, 
                {
                    fieldName:'tientra',
                    headerText:'Tiền trả',
                    allowFiltering:true,
                    allowSorting:true,
                    type: "Number",
                    format: "N0"
                },
                {
                    fieldName:'hinhthuc',
                    headerText:'Hình thức trả',
                    allowFiltering:true,
                    allowSorting:true
                },
                {
                    fieldName:'usergach',
                    headerText:'User gạch',
                    allowFiltering:true,
                    allowSorting:true
                },
            ],
            dataSources:[],

        }
    },
    methods:{
        async initDuLieu(){
            this.ds_kenhban=await this.danhSachKenhBan()
            this.kenhban_selected=null
            this.ds_userhuy=await this.dsUserGach()
            this.user_huy_selected=null

            //query
            this.kenhban_selected=this.$route.query.kenhban_id?this.$route.query.kenhban_id:null
            this.user_huy_selected=this.$route.query.usergach_id?this.$route.query.usergach_id:null
            this.ngay_tt=this.$route.query.ngay_tt?this.$route.query.ngay_tt:null
            this.input_thang=this.$route.query.thang_cn?this.$route.query.thang_cn:null

            this.dataSources=await this.dsPhieuHuyKenhBan({
                kenhban_id:this.kenhban_selected,
                usergach_id:this.user_huy_selected,
                ngay_TT:this.ngay_tt,
                thang_cn:this.input_thang==''?null:this.input_thang
            })
        },
        terminateForm() {
            this.$emit('form-close');
        },
        async onSelectedRow(data){
            if(!data){
                return
            }
            let item=Array.isArray(data)?data[0]:data
            this.kenhban_selected=item.kenhban_id
            this.ngay_tt=item.ngay_tt
            this.user_huy_selected=item.nguoigach?item.nguoigach.toString():null
            this.input_thang=item.ngayhuy?moment(item.ngayhuy, 'DD/MM/YYYY').format('MM'):null

        },
        async danhSachKenhBan(){
            try{
                this.loading(true)
                let response = await API.danhSachKenhBan(this.axios)
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
        async dsUserGach(){
            try{
                this.loading(true)
                let response = await API.dsUserGach(this.axios)
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
        async dsPhieuHuyKenhBan(data){
            try{
                this.loading(true)
                let response = await API.dsPhieuHuyKenhBan(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data.map(x=>{
                        x.ngayhuy=x.ngayhuy?moment(x.ngayhuy, 'DD/MM/YYYY HH:mm:ss').format('DD/MM/YYYY'):null
                        x.ngay_tt=x.ngay_tt?moment(x.ngay_tt, 'DD/MM/YYYY HH:mm:ss').format('DD/MM/YYYY'):null
                        return x
                    })
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
<style>
.mx-input:disabled, .mx-input.disabled{
    color: #555 !important;
}
</style>