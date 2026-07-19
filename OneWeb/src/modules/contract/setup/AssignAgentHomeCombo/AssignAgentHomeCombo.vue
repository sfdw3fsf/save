<template>
    <div class="main-wrapper">
            <breadcrumb :header="header"/>
            <ActionTop :actions="actions" @onActionClick="onActionClick"/>
            <div class="page-content">
                <div class="row">
                    <ThongTinThueBao :hdtb="itemSelected" ref="thongtinthuebao" @onEnterMaGD="onEnterMaGD"/>
                    <ThongTinCongTacVien ref="ctv" :all_nhanvien="all_ds_nhanvien" :hdtb="itemSelected"/>
                    <ThongTinCongTacVienVNP ref="ctv_vnp" :all_nhanvien="all_ds_nhanvien" :hdtb="itemSelected"/>
                </div>
                <div class="box-form">

                    <div class="legend-title">Danh sách thuê bao tham gia gói</div>
                    <div class="title-bg-main padt6 padb0">
                        <div class="pull-left">
                            <span class="title mart7">Thông tin tìm kiếm</span>
                        </div>
                        <div class="pull-right">
                            <div class="inline">
                                <div class="info-row marb0">
                                    <div class="key w80">
                                        <div class="check-action">
                                            <input type="checkbox" @change="onChangeCheckBox" v-model="checkBoxDateSearch" class="check">
                                            <span class="name">Ngày YC</span>
                                        </div>
                                    </div>
                                    <div class="value">
                                        <div class="input-icon-right">
                                            <k-date-picker v-model="ngay_yc" :disabled="!checkBoxDateSearch"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="inline">
                                <div class="info-row marb0">
                                    <div class="key w80">
                                        Đến ngày
                                    </div>
                                    <div class="value">
                                        <div class="input-icon-right">
                                            <k-date-picker v-model="ngay_den" :disabled="!checkBoxDateSearch"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            
                        </div>
                        <div class="clearfix"></div>
                    </div>
                    <DataGrid
                        :columns="columns"
                        :dataSource="dataSources"
                        :showColumnCheckbox="false"
                        :enable-paging-server="false"
                        :allowPaging="true"
                        :showFilter="true"
                        :enabledSelectFirstRow="true"
                        @selectedItemsChanged="gridDsChanged"/>
                </div>
            </div>
            <Footer/>        
    </div>   
</template>
<script>
import Footer from '@/components/FooterStyle.vue'
import breadcrumb from '@/components/kylq_components/breadcrumb.vue'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
import moment from 'moment'
import AssignAgentHomeComboApi from './AssignAgentHomeComboApi'
import ThongTinThueBao from './ThongTinThueBao.vue'
import ThongTinCongTacVien from './ThongTinCongTacVien.vue'
import ThongTinCongTacVienVNP from './ThongTinCongTacVienVNP.vue'
export default {
    name:'AssignAgentHomeCombo',
    components:{
        breadcrumb,
        Footer,
        ActionTop,
        KDatePicker,
        ThongTinThueBao,
        ThongTinCongTacVien,
        ThongTinCongTacVienVNP
    },
    data(){
        return {
            header: {
                title: "Gán cộng tác viên cho thuê bao sử dụng gói",
                list: [
                    {
                        name: "Lập hợp đồng",
                        link: { name: "Ui.buttons" },
                        active: false
                    },
                    {
                        name: "Gán cộng CTV phát triển gói GD/VP",
                        link: { name: "Ui.buttons" },
                        active: true
                    },
                ],
            },
            actions:[
                {
                    id:'lay_tt',
                    name:'Lấy TT',
                    active: true,
                    icon_class:'one-file-attach'
                },
                {
                    id:'capnhat',
                    name:'Cập nhật',
                    active: true,
                    icon_class:'one-reload1'
                },
            ],
            columns:[
                {
                    fieldName: "ma_gd",
                    headerText: "Mã giao dịch",
                    allowFiltering: true,
                    allowSorting:true
                },
                {
                    fieldName: "ma_tb",
                    headerText: "Số máy/Acc",
                    allowFiltering: true,
                    allowSorting:true
                },
                {
                    fieldName: "ten_tb",
                    headerText: "Tên thuê bao",
                    allowFiltering: true,
                    allowSorting:true
                },
                {
                    fieldName: "diachi_ld",
                    headerText: "Địa chỉ lắp đặt",
                    allowFiltering: true,
                    allowSorting:true
                },
                {
                    fieldName: "ngay_yc",
                    headerText: "Ngày YC",
                    allowFiltering: true,
                    allowSorting:true 
                },
                {
                    fieldName: "ma_goi",
                    headerText: "Mã gói",
                    allowFiltering: true,
                    allowSorting:true
                },
                {
                    fieldName: "ten_goi",
                    headerText: "Tên gói",
                    allowFiltering: true,
                    allowSorting:true
                },
                {
                    fieldName: "cbcnv_neo",
                    headerText: "CBCNV_NEO",
                    allowFiltering: true,
                    allowSorting:true
                },
                {
                    fieldName: "nhanvien_lhd",
                    headerText: "Nhân viên LHĐ",
                    allowFiltering: true,
                    allowSorting:true
                },
                {
                    fieldName: "tocdo",
                    headerText: "Tốc độ",
                    allowFiltering: true,
                    allowSorting:true
                },
                {
                    fieldName: "nhanvien",
                    headerText: "Cộng tác viên",
                    allowFiltering: true,
                    allowSorting:true
                },
                {
                    fieldName: "thang_huong",
                    headerText: "Tháng hưởng",
                    allowFiltering: true,
                    allowSorting:true
                },
                {
                    fieldName: "muc_cuoc",
                    headerText: "Mức cước",
                    allowFiltering: true,
                    allowSorting:true
                },
            ],
            dataSources:[],

            ngay_yc: moment(new Date()).subtract(1, 'months').format('DD/MM/YYYY'),
            checkBoxDateSearch:true,
            configDate: {
                wrap: true, // set wrap to true only when using 'input-group'
                altFormat: 'd/m/Y',
                altInput: true,
                dateFormat: 'd/m/Y',
                allowInput: true
            },
            ngay_den: moment(new Date()).format('DD/MM/YYYY'),
            itemSelected:{},
            all_ds_nhanvien:[],
            //rowSelectedId:-1
        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='lay_tt'){
                
                this.LayTT('0')
            }else if(action.id=='capnhat'){
                this.CapNhat()
            }
        },
        async LayTT(ma_gd){
            // Dữ liệu test
            // "vma_tb": 0,
            // "vma_gd": 0,
            // "vdenngay": "08/09/2021",
            // "vtungay": "08/09/2021",
            // "nhanvien_id": 452
            let nhanvien_id=this.$root.token.getNhanVienID()
            if(this.checkBoxDateSearch){
                if(!this.ngay_yc||!this.ngay_den||this.ngay_yc.trim()==''||this.ngay_den.trim()==''){
                    this.$toast.error('Hãy nhập thông tin thời gian')
                    return
                }
                this.dataSources=await this.lay_ds_hdtb_goi(ma_gd,this.ngay_yc, this.ngay_den, nhanvien_id)
            }else{
                //let currentDay=moment(new Date()).format('DD/MM/YYYY')
                this.dataSources=await this.lay_ds_hdtb_goi(ma_gd, null, null, nhanvien_id)
            }  
        },
        onEnterMaGD(){
            let ma_gd=this.$refs.thongtinthuebao.getMaGD()
            this.LayTT(ma_gd)
        },
        onChangeCheckBox(){
            if(this.checkBoxDateSearch){
                this.ngay_yc=moment(new Date()).subtract(1, 'months').format('DD/MM/YYYY')
                this.ngay_den=moment(new Date()).format('DD/MM/YYYY')
            }else{
                this.ngay_yc=''
                this.ngay_den=''
            }
        },
        CapNhat(){
            if(!this.itemSelected.hdkh_id){
                this.$toast.error('Hãy chọn thuê bao cần cập nhật!')
                return
            }
            
            let nhanvien_id=this.$refs.ctv.getNhanVienID()
            var ctv_vnp_id=this.$refs.ctv_vnp.getNhanVienID()

            if(nhanvien_id==null||nhanvien_id==-1||nhanvien_id==''){
                this.$toast.error('Hãy chọn cộng tác viên để cập nhật!')
                return
            }
            if(ctv_vnp_id==null||ctv_vnp_id==-1||ctv_vnp_id==''){
                ctv_vnp_id=0
            }

            let data={
                p_nhanvien_id:nhanvien_id,
                p_nhomtb_id:this.itemSelected.nhomtb_id,
                p_ctv_vnp_id:ctv_vnp_id,
                p_hdkh_id:this.itemSelected.hdkh_id,
                p_ngay_yc: this.itemSelected.ngay_yc
            }

            this.fn_capnhat_thuebao_ctv(data)
        },
        gridDsChanged(data){
            if(this.dataSources.length<=0||data.length<=0){
                this.itemSelected={}
                return
            }
            this.itemSelected= Object.assign({},data[0])
        },
        onSelectedRow(item){
            //clone item
           this.itemSelected= Object.assign({},item)
        },
        async lay_ds_hdtb_goi(ma_gd, tu_ngay, ngay_den, nhanvien_id){
            try{
                this.loading(true)
                let response = await AssignAgentHomeComboApi.lay_ds_hdtb_goi(this.axios, ma_gd, tu_ngay, ngay_den, nhanvien_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Đã xảy ra lỗi')
                }
                return []

            }
        },
        async sp_lay_ds_nhanvien_ctv(donvi_id, loainv_id){
            try{
                this.loading(true)
                let response=await AssignAgentHomeComboApi.sp_lay_ds_nhanvien_ctv(this.axios, donvi_id, loainv_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    this.all_ds_nhanvien=response.data.data
                }else{
                    this.all_ds_nhanvien=[]
                }
            }catch(e){
                this.$toast.error('Không load được danh sách nhân viên')
            }finally{
                this.loading(false)
            }
        },
        async fn_capnhat_thuebao_ctv(data){
            try{
                this.loading(true)
                let response = await AssignAgentHomeComboApi.fn_capnhat_thuebao_ctv(this.axios, data)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    this.$toast.success(response.data.data)
                    this.$nextTick(() => {
                        this.itemSelected={}
                        this.LayTT('0')
                    })
                    
                }else{
                    if(response && response.data &&response.data.message){
                        this.$toast.error(response.data.message)
                    } 
                }
            }catch(e){
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Đã xảy ra lỗi, không thể cập nhật')
                }
            }finally{
                this.loading(false)
            }

        },
        async initDuLieu(){
            await this.sp_lay_ds_nhanvien_ctv(-1,-1)
            await this.LayTT('0')

        }
        
    },
    mounted(){
        setTimeout(()=>{
            this.initDuLieu()
        }, 300)
    }
}
</script>
<style scoped>
</style>