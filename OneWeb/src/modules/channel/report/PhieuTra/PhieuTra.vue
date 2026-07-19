<template>
    <div class="main-wrapper">
        <breadcrumb :header="header" />
        <ActionTop :actions="actions" @onActionClick="onActionClick"/>
         <div class="page-content">
            <div class="box-form">
                <div class="legend-title">Thông tin tìm kiếm</div>
                <div class="row">
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key">Tháng</div>
                            <div class="value">
                                <div class="input-icon-right">
                                    <ejs-datepicker class="form-control bg-none bor0 fw6 text-red"
                                        placeholder="Chọn tháng" :format="'MM/y'" :start="'Year'"
                                        :depth="'Year'" :showClearButton="false" v-model="thang_hn"
                                        @change="changeThang"
                                        :strictMode="true"
                                        :allowEdit="true"/>
                                </div>
                                <!-- <input type="text" :value="input_thang" @change="e=>input_thang=e.target.value" class="form-control"> -->
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                    </div>
                    <div class="col-sm-4 col-12">
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key">Kênh bán</div>
                            <div class="value">
                                <div class="select-custom" ref="ds_kenhban">
                                    <select2 :settings="{ dropdownParent: $refs['ds_kenhban'] }"
                                        v-model="kenhban_selected" class="select2"
                                        :options="ds_kenhban.map(e=> ({id: e.nhanvien_id, text: e.ten_kb}))"
                                        >
                                    </select2>
                                </div>  
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key">Hình thức TT</div>
                            <div class="value">
                                <div class="select-custom" ref="ds_hinhthuctt">
                                    <select2 :settings="{ dropdownParent: $refs['ds_hinhthuctt'] }"
                                        v-model="hinhthuc_tt_selected" class="select2"
                                        :options="ds_hinhthuctt.map(e=> ({id: e.httt_id, text: e.hinhthuc}))"
                                        >
                                    </select2>
                                </div>  
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key">User gạch</div>
                            <div class="value">
                                <div class="select-custom" ref="ds_usergach">
                                    <select2 :settings="{ dropdownParent: $refs['ds_usergach'] }"
                                        v-model="user_gach_selected" class="select2"
                                        :options="ds_usergach.map(e=> ({id: e.nhanvien_id, text: e.usergach}))"
                                        >
                                    </select2>
                                </div>  
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key">Ngày TT</div>
                            <div class="value">
                                <div class="input-icon-right">
                                    <k-date-picker v-model="ngay_tt"/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key">Tiền trả</div>
                            <div class="value">
                                <vue-numeric class="form-control bold tright" separator=","  :value="input_tientra" @change="e=>input_tientra=e.target.value" placeholder="0" output-type="Number"></vue-numeric>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key">Ghi chú</div>
                            <div class="value">
                                <input type="text" :value="input_ghichu" @change="e=>input_ghichu=e.target.value" class="form-control">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="box-form">
                <div class="legend-title">Danh sách chi trả</div>
                <KDataGrid
                    ref="gridDs"
                    :columns="columns"
                    :dataSource="dataSources"
                    :showColumnCheckbox="false"
                    :enable-paging-server="false"
                    :allowPaging="true"
                    :showFilter="true"
                    :enabledSelectFirstRow="true"
                    :selectionSettings="{ checkboxMode: 'ResetOnRowClick' }"
                    @onRowSelected="onSelectedRow"
                    @rowSelected="rowGridSelected"/>
            </div>
         </div>
    </div>
</template>
<script>
import breadcrumb from '@/components/kylq_components/breadcrumb.vue'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import KDataGrid from '@/components/kylq_components/KDataGrid'
import API from './API'
import moment from 'moment'
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
export default {
    name:'PhieuTra',
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
        }
    },
    components:{
        breadcrumb,
        ActionTop,KDataGrid,
        KDatePicker
    },
    data(){
        return {
            header: {
                title: "Danh sách phiếu thanh toán cho kênh",
                list: [
                    {
                        name: "Trang chủ",
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
                    icon_class:'one-search'
                },
                {
                    id:'xoaphieu',
                    name:'Xóa phiếu',
                    active: true,
                    icon_class:'nc-icon-glyph ui-1_circle-remove'
                },
                {
                    id:'huy',
                    name:'Hủy',
                    active: true,
                    icon_class:'one-cancel'
                },
            ],
            kenhban_selected:null,
            ds_kenhban:[],
            ds_hinhthuctt:[],
            hinhthuc_tt_selected:null,
            user_gach_selected:null,
            ds_usergach:[],
            ngay_tt:moment(new Date()).format('DD/MM/YYYY'),
            input_tientra:'',
            input_ghichu:'',
            selectIndex:-1,
            columns:[
                {
                    fieldName:'ngay_tt',
                    headerText:'Ngày chi trả',
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
            input_thang:'',
            dataSources:[],
            rowSelected:null,
            thang_hn:null

        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='timkiem'){
                this.TimKiem()
            }else if(action.id=='xoaphieu'){
                this.XoaPhieu()
            }else if(action.id=='huy'){
                this.Huy()
            }
        },
        async onSelectedRow(data){
            if(!data){
                return
            }
            this.rowSelected=Array.isArray(data)?data[0]:data
        },
        rowGridSelected(args){
            this.selectIndex=args.rowIndex
        },
        async TimKiem(){
            console.log('xóa phiếu thang hn', this.thang_hn)
            this.dataSources=await this.dsPhieuTraKenhBan({
                kenhban_id:this.kenhban_selected,
                hinhthucTT_id:this.hinhthuc_tt_selected,
                usergach_id:this.user_gach_selected,
                ngay_TT:this.ngay_tt,
                thang_cn:this.thang_hn?moment(this.thang_hn, 'MM/YYYY').format('YYYYMM'):null,
                tientra:this.input_tientra?this.input_tientra.trim():null,
                ghichu:this.input_ghichu?this.input_ghichu.trim():null
            })
        },
        Huy(){
            this.ngay_tt=null
            this.input_ghichu=null
            this.input_tientra=null
            this.input_thang=null
            this.hinhthuc_tt_selected=null
            this.kenhban_selected=null
            this.user_gach_selected=null
            this.dataSources=[]
            this.thang_hn=null
        },
        async XoaPhieu(){  
            if(this.dataSources.length==0||!this.rowSelected){
                this.$toast.error('Hãy chọn phiếu !')
                return
            }

            let resultConfirm = await this.confirm(`Bạn có muốn xoá phiếu với số tiền trả ${this.numberWithCommas(this.rowSelected.tientra)}?`,'Thông báo')
            if(resultConfirm==0){
                return
            }

            let rs=await this.xoaPhieuTraKenhBan({
                phieuhuy_id:this.rowSelected.phieu_id,
                kenhban_id:this.rowSelected.kenhban_id
            })
            if(rs.code==1){
                this.$toast.success(rs.data)
                this.TimKiem()
            }else{
                this.$toast.error(rs.data)
            }


        },
        async confirm(message, title){
            try{
                let result = await this.$confirm(message,title,{
                    confirmButtonText: 'Đồng ý',
                    cancelButtonText: 'Hủy'
                })
                return 1
            }catch(e){
                return 0
            }
        },
        numberWithCommas(n) {
            var parts = n.toString().split(".");
            return (
            parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",") +
            (parts[1] ? "." + parts[1] : "")
            );
        },
        async initDuLieu(){
            this.ds_hinhthuctt=await this.dsHinhThucTT()
            this.ds_kenhban=await this.danhSachKenhBan()
            this.ds_usergach=await this.dsUserGach()
            //query
            this.kenhban_selected=this.$route.query.kenhban_id?this.$route.query.kenhban_id:null
            this.user_gach_selected=this.$route.query.usergach_id?this.$route.query.usergach_id:null
            this.ngay_tt=this.$route.query.ngay_tt?this.$route.query.ngay_tt:null
            this.thang_hn=this.$route.query.thang_cn?moment(this.$route.query.thang_cn, 'YYYYMM').format('MM/YYYY'):null
            //this.input_thang=this.$route.query.thang_cn?this.$route.query.thang_cn:null

            this.TimKiem()
        },
        changeThang(event){
            console.log(event)
            if(!event.value){
                this.thang_hn=null
                return
            }
            this.thang_hn=moment(event.value).format('MM/YYYY')
        },
         async dsHinhThucTT(){
            try{
                this.loading(true)
                let response = await API.dsHinhThucTT(this.axios)
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
        async dsPhieuTraKenhBan(data){
            try{
                this.loading(true)
                let response = await API.dsPhieuTraKenhBan(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data.map(x=>{
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
        async xoaPhieuTraKenhBan(data){
            try{
                this.loading(true)
                let response = await API.xoaPhieuTraKenhBan(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return {
                        code:1,
                        data:response.data.data
                    }
                }else{
                    if(response&&response.data&&response.data.message){
                        return {
                            code:0,
                            data:response.data.message
                        }
                    }else{
                        return {
                            code:0,
                            data:'Đã xảy ra lỗi, xóa không thành công!'
                        }
                    }
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                    return {
                        code:0,
                        data:e.data.message
                    }
                }else if(e.response&&e.response.data&&e.response.data.message){
                    return {
                        code:0,
                        data:e.response.data.message
                    }
                }else{
                    return {
                        code:0,
                        data:'Đã xảy ra lỗi, xóa không thành công!'
                    }
                }
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