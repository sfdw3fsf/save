<template>
    <div class="main-wrapper">
        <breadcrumb :header="header"/>
        <ActionTop :actions="actions" @onActionClick="onActionClick" />
        <div class="page-content">
            <div class="row">
                <div class="col-sm-4 col-12">
                    <div class="box-form">
                        <div class="legend-title">
                            Tiêu chí tìm kiếm
                        </div>
                        <div class="info-row">
                            <div class="key w110">
                                    <div class="check-action">
                                        <input type="radio" class="check"  value="1" :checked="tieuchi==1" @change="changeTieuChi($event)">
                                        <span class="name">Mã hồ sơ</span>
                                    </div>
                            </div>
                            <div class="value">
                                <input type="text" class="form-control highlight" @keyup.enter="onEnterMaHS" :value="thongtin_tk.input_ma_hs" @change="e=>thongtin_tk.input_ma_hs=e.target.value" :disabled="!thongtin_tk.enabled_ma_hs">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w110">
                                <div class="check-action">
                                    <input type="radio" class="check" value="2" :checked="tieuchi==2" @change="changeTieuChi($event)">
                                    <span class="name">Tên ptm</span>
                                </div>
                            </div>
                            <div class="value">
                                <div class="input-more-button -right">
                                    <button class="btn" @click="searchTenPtm" :disabled="!thongtin_tk.enabled_ten_ptm">
                                        <span class="icon one-search" ></span>
                                    </button>
                                    <div class="input-icon-right">
                                        <input type="text" class="form-control highlight" readonly :value="thongtin_tk.input_ten_ptm" @change="e=>thongtin_tk.input_ten_ptm=e.target.value" :disabled="!thongtin_tk.enabled_ten_ptm"/>
                                    </div>
                                </div>
                                
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w110">
                                <div class="check-action">
                                    <input type="radio" class="check" value="3" :checked="tieuchi==3" @change="changeTieuChi($event)">
                                    <span class="name">Ngày YC</span>
                                </div>
                            </div>
                            <div class="value">
                                <div class="input-icon-right">
                                    <k-date-picker :disabled="!thongtin_tk.enabledNgayYC" v-model="thongtin_tk.ngay_yc"/>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-8 col-12">
                    <div class="box-form"> 
                        <div class="legend-title">
                            Thông tin hồ sơ
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w100">Mã hồ sơ</div>
                                    <div class="value">
                                        <input type="text" :value="thongtin_hs.input_ma_hs" readonly @change="e=>thongtin_hs.input_ma_hs=e.target.value" class="form-control highlight">
                                    </div>
                                </div>
                                <div class="info-row">
                                    <div class="key w100">Người đăng ký</div>
                                    <div class="value">
                                        <input type="text" :value="thongtin_hs.input_nguoi_dk" readonly @change="e=>thongtin_hs.input_nguoi_dk=e.target.value" class="form-control">
                                    </div>
                                </div>
                                <div class="info-row">
                                    <div class="key w100">Trung tâm VT</div>
                                    <div class="value">
                                        <div class="select-custom">
                                            <select v-model="thongtin_hs.trungtam_vt_selected" disabled class="form-control">
                                                <option v-for="item in thongtin_hs.ds_trungtam_vt" :key="item.donvi_id" :value="item.donvi_id">
                                                    {{item.ten_dv}}
                                                </option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w70">Tên hồ sơ</div>
                                    <div class="value">
                                        <input type="text" :value="thongtin_hs.input_ten_hs" readonly @change="e=>thongtin_hs.input_ten_hs=e.target.value" class="form-control">
                                    </div>
                                </div>
                                <div class="info-row">
                                    <div class="key w70">Ngày BĐ</div>
                                    <div class="value">
                                        <div class="input-icon-right">
                                            <k-date-picker v-model="thongtin_hs.ngay_bd" :disabled="true" type="datetime" format="DD/MM/YYYY HH:mm:ss"/>
                                        </div>
                                    </div>
                                    <div class="key w70 padl10">Ngày KT</div>
                                    <div class="value">
                                        <div class="input-icon-right">
                                            <k-date-picker v-model="thongtin_hs.ngay_kt" :disabled="true" type="datetime" format="DD/MM/YYYY HH:mm:ss"/>
                                        </div>
                                    </div>
                                    
                                </div>
                                <div class="info-row">
                                    <div class="key w70">Trạng thái</div>
                                    <div class="value">
                                        <div class="select-custom">
                                            <select v-model="thongtin_hs.trangthai_selected" disabled class="form-control">
                                                <option v-for="item in thongtin_hs.ds_trangthai" :key="item.ttcc_id" :value="item.ttcc_id">
                                                    {{item.trangthai_cc}}
                                                </option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="box-form" >
                        <div class="legend-title">
                            <div class="pull-left">Danh sách phần tử mạng</div>
                            <div class="pull-right">
                                <div class="item red">
                                    Click đúp phiếu để xem chi tiết nhân viên thực hiện
                                </div>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <DataGrid 
                            :columns="columnsPT" 
                            :dataSource="dataSourcesPT"
                            :showColumnCheckbox="false"
                            :enable-paging-server="false"
                            :allowPaging="true"
                            :showFilter="true"
                            :enabledSelectFirstRow="true"
                            :panelDataHeight="heightTablePT"
                            @rowDoubleClicked="rowPTDoubleClicked"
                            @selectedItemsChanged="selectedItemsChangedPT"
                        />
                    </div>
                </div>
                <div class="col-sm-6 col-12" >
                    <div class="box-form">
                        <div class="legend-title">Danh sách cải tạo bảo dưỡng</div>
                        <DataGrid 
                            :columns="columnsCTBD" 
                            :dataSource="dataSourcesCTBD"
                            :showColumnCheckbox="false"
                            :enable-paging-server="false"
                            :allowPaging="false"
                            :showFilter="true"
                            :enabledSelectFirstRow="true"
                            panelDataHeight='280px'
                            @selectedItemsChanged="selectedItemsChangedCTBD"
                        />
                    </div>
                </div>
            </div>
            <div class="box-form">
                <div class="legend-title">Chi tiết điều hành thi công</div>
                <DataGrid 
                    :columns="columnsCTDHTC" 
                    :dataSource="dataSourcesCTDHTC"
                    :showColumnCheckbox="false"
                    :enable-paging-server="false"
                    :allowPaging="false"
                    :showFilter="true"
                    :enabledSelectFirstRow="true"
                    @selectedItemsChanged="selectedItemsChangedCTDHTC"
                />
            </div>
        </div>
        <!-- Modal -->
        <NhanVienTHModal ref="nhanVienTH" :chitietcc_id="chitietcc_id"/>
        <TraCuuVatTuPTMModal ref="traCuuVatTuPTM" :chitietcc_id="chitietcc_id"/>
        <TraCuuPTMModal ref="traCuuPTM" @accept="acceptPTM"/>
    </div>
</template>
<script>
import breadcrumb from '@/components/kylq_components/breadcrumb.vue'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
import moment from 'moment'
import API from './API'
import NhanVienTHModal from './PopUps/NhanVienTHModal.vue'
import TraCuuVatTuPTMModal from './PopUps/TraCuuVatTuPTMModal.vue'
import TraCuuPTMModal from './PopUps/TraCuuPTM.vue'
//Kiểu 3 ngày yc: 12/05/2021
//Kiểu 1: HSCC/2021/0304
export default {
    name:'SearchRenovationMaintenanceTicket',
    components:{
        breadcrumb,
        ActionTop,
        KDatePicker,
        NhanVienTHModal,
        TraCuuVatTuPTMModal,
        TraCuuPTMModal
    },
    data(){
        return {
            header: {
                title: "Tra cứu chi tiết phiếu cải tạo, bảo dưỡng",
                list: [
                    {   
                        name: "Lập hợp đồng", 
                        link: { name: "Ui.cards" },
                        active: false
                    },
                    {
                        name: "Lắp đặt mới",
                        link: { name: "Ui.buttons" },
                        active: true
                    },
                ],
            },
            actions:[
                {
                    id:'nhapmoi',
                    name:'Nhập mới',
                    active: true,
                    icon_class:'one-file-plus'
                },
                {
                    id:'timmkiem',
                    name:'Tìm kiếm',
                    active: true,
                    icon_class:'one-search'
                },
                {
                    id:'vattu',
                    name:'Vật tư',
                    active: true,
                    icon_class:'one-box'
                },

            ],
            tieuchi:1,
            thongtin_tk:{
                input_ma_hs:'',
                enabled_ma_hs:true,
                input_ten_ptm:'',
                enabled_ten_ptm:false,
                ngay_yc:moment(new Date()).format('DD/MM/YYYY'),
                enabledNgayYC:false
            },
            thongtin_hs:{
                ngay_bd:moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
                ngay_kt:moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
                input_ten_hs:'',
                input_ma_hs:'',
                input_nguoi_dk:'',
                ds_trungtam_vt:[],
                trungtam_vt_selected:null,
                ds_trangthai:[],
                trangthai_selected:null
            },
            columnsCTDHTC:[
                {
                  fieldName: 'huonggiao',
                  headerText: '',
                  allowFiltering: true,
                  allowSorting: true,
                  isGroupedColumn: true
                },
                {
                  fieldName: 'dvg',
                  headerText: 'Đơn vị giao',
                  allowFiltering: true,
                  allowSorting: true,
                  width:150
                },
                {
                  fieldName: "ngay_giao",
                  headerText: "Ngày giao",
                  allowFiltering: true,
                  allowSorting: true,
                  width:150
                },
                {
                  fieldName: "nv_giao",
                  headerText: "Người giao",
                  allowFiltering: true,
                  allowSorting: true,
                  width:150
                },
                {
                  fieldName: "nd_giao",
                  headerText: "Nội dung giao",
                  allowFiltering: true,
                  allowSorting: true,
                  width:150
                },
                {
                  fieldName: "dvn",
                  headerText: "Đơn vị nhận",
                  allowFiltering: true,
                  allowSorting: true,
                  width:150
                },
                {
                  fieldName: "trangthai_ph",
                  headerText: "Trạng thái phiếu",
                  allowFiltering: true,
                  allowSorting: true,
                  width:150
                },
                {
                  fieldName: "ngay_tra",
                  headerText: "Ngày trả",
                  allowFiltering: true,
                  allowSorting: true,
                  width:150
                },
                {
                  fieldName: "nd_tra",
                  headerText: "Nội dung trả",
                  allowFiltering: true,
                  allowSorting: true,
                  width:150
                },
                {
                  fieldName: "lydotra",
                  headerText: "Lý do trả",
                  allowFiltering: true,
                  allowSorting: true,
                  width:150
                },
                {
                  fieldName: "ngay_th",
                  headerText: "Ngày cập nhật",
                  allowFiltering: true,
                  allowSorting: true,
                  width:150
                },
                {
                  fieldName: "nv_th",
                  headerText: "Người thực hiện",
                  allowFiltering: true,
                  allowSorting: true,
                  width:150
                },
                {
                  fieldName: "so_dt",
                  headerText: "Điện thoại liên hệ",
                  allowFiltering: true,
                  allowSorting: true,
                  width:150
                },
                {
                  fieldName: "ghichu",
                  headerText: "Ghi chú",
                  allowFiltering: true,
                  allowSorting: true,
                  width:150
                },
                {
                  fieldName: "ketqua_xl",
                  headerText: "Kết quả XL",
                  allowFiltering: true,
                  allowSorting: true,
                  width:150
                },
                {
                  fieldName: "chuongtrinh",
                  headerText: "Ứng dụng",
                  allowFiltering: true,
                  allowSorting: true,
                  width:150
                },
                {
                  fieldName: "nd_thuchien",
                  headerText: "ND thực hiện",
                  allowFiltering: true,
                  allowSorting: true,
                  width:150
                },
                {
                  fieldName: "nguoi_xuly",
                  headerText: "Người xử lý",
                  allowFiltering: true,
                  allowSorting: true,
                  width:150
                }
            ],
            dataSourcesCTDHTC:[],
            columnsPT:[
                {
                  fieldName: 'ten_tbi',
                  headerText: 'Tên phần tử mạng',
                  allowFiltering: true,
                  allowSorting: true,
                  width:120
                },
                {
                  fieldName: 'tinhtrang',
                  headerText: 'Tình trạng',
                  allowFiltering: true,
                  allowSorting: true,
                  width:120
                },
                {
                  fieldName: 'trangthai',
                  headerText: 'Trạng thái',
                  allowFiltering: true,
                  allowSorting: true,
                  width:120
                },
                {
                  fieldName: 'ngay_hs',
                  headerText: 'Ngày cập nhật',
                  allowFiltering: true,
                  allowSorting: true,
                  width:120
                },
                {
                  fieldName: 'nguoi_cn',
                  headerText: 'Người cập nhật',
                  allowFiltering: true,
                  allowSorting: true,
                  width:120
                },
            ],
            dataSourcesPT:[],
            columnsCTBD:[
                {
                  fieldName: 'ma_hs_cc',
                  headerText: 'Mã hồ sơ',
                  allowFiltering: true,
                  allowSorting: true,
                  width:120
                },
                {
                  fieldName: 'ten_hs',
                  headerText: 'Tên hồ sơ',
                  allowFiltering: true,
                  allowSorting: true,
                  width:120
                },
                {
                  fieldName: 'trangthai_cc',
                  headerText: 'Trạng thái',
                  allowFiltering: true,
                  allowSorting: true,
                  width:120
                },
                {
                  fieldName: 'nguoi_dk',
                  headerText: 'Người đăng ký',
                  allowFiltering: true,
                  allowSorting: true,
                  width:120
                },
                {
                  fieldName: 'ngay_yc',
                  headerText: 'Ngày đăng ký',
                  allowFiltering: true,
                  allowSorting: true,
                  textAlign:'left',
                  width:120
                },
                {
                  fieldName: 'ngaybd_dk',
                  headerText: 'Ngày bắt đầu',
                  allowFiltering: true,
                  allowSorting: true,
                  textAlign:'left',
                  width:120
                },
                {
                  fieldName: 'ngaykt_dk',
                  headerText: 'Ngày kết thúc',
                  allowFiltering: true,
                  allowSorting: true,
                  textAlign:'left',
                  width:120
                },
                {
                  fieldName: 'ten_kieu',
                  headerText: 'Hình thức',
                  allowFiltering: true,
                  allowSorting: true,
                  width:120
                }
            ],
            dataSourcesCTBD:[],
            vmahscc: "",
            hscc_id: 0,
            phieu_id: 0,
            loaitbi_id: 0,
            ds_ptm_id: "",
            chitietcc_id: 0
        }
    },
    computed:{
        heightTablePT(){
            return this.dataSourcesPT.length>0?'200px':'280px'
        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='nhapmoi'){
                this.clear_all()
            }else if(action.id=='timmkiem'){
                this.Search()
            }else if(action.id=='vattu'){
                this.clickVattu()
            }
        },
        changeTieuChi(event){
            this.tieuchi=event.target.value
            if(this.tieuchi==1){
                this.thongtin_tk.enabled_ma_hs=true
                this.thongtin_tk.enabled_ten_ptm=false
                this.thongtin_tk.enabledNgayYC=false
            }else if(this.tieuchi==2){
                this.thongtin_tk.enabled_ma_hs=false
                this.thongtin_tk.enabled_ten_ptm=true
                this.thongtin_tk.enabledNgayYC=false
            }else if(this.tieuchi==3){
                this.thongtin_tk.enabled_ma_hs=false
                this.thongtin_tk.enabled_ten_ptm=false
                this.thongtin_tk.enabledNgayYC=true
            }
        },
        searchTenPtm(){
            this.$refs.traCuuPTM.showModal()
        },
        clear_all(){
            //clear thông tin 
            this.ClearGUI()
            //
            this.vmahscc = ""
            this.hscc_id = 0
            this.phieu_id = 0
            this.loaitbi_id = 0
            this.ds_ptm_id = ""
            this.chitietcc_id = 0
            this.dataSourcesPT=[]
            this.dataSourcesCTDHTC=[]
            this.dataSourcesCTBD=[]
        },
        ClearGUI(){
            this.thongtin_hs.input_ten_hs=''
            this.thongtin_hs.input_ma_hs=''
            this.thongtin_hs.input_nguoi_dk=''
            this.thongtin_hs.ngay_bd=moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
            this.thongtin_hs.ngay_kt=moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
        },
        async Search(){
            //
            if(this.tieuchi==1){
                this.vmahscc=this.thongtin_tk.input_ma_hs.trim()
                let ds_hs=await this.tracuu_hoso_cc_v2({
                    vma_hscc: this.vmahscc,
                    vptm_id:'',
                    vtungay:'',
                    vloaitbi_id: 0,
                    vloai: 1
                })
                if(ds_hs.length>0){
                    this.dataSourcesCTBD=ds_hs
                }else{
                    this.$toast.error('Không tìm thấy thông tin mã hồ sơ '+this.vmahscc+' !')
                    this.clear_all()
                }
            }else if(this.tieuchi==2){
                if(this.loaitbi_id==0||this.ds_ptm_id==''){
                    return
                }
                let ds_hs=await this.tracuu_hoso_cc_v2({
                    vma_hscc: '',
                    vptm_id:this.ds_ptm_id,
                    vtungay:'',
                    vloaitbi_id: this.loaitbi_id,
                    vloai: 2
                })
                if(ds_hs.length>0){
                    this.dataSourcesCTBD=ds_hs
                }else{
                    this.$toast.error('Không tìm thấy thông tin hồ sơ !')
                    this.clear_all()
                }
            }else if(this.tieuchi==3){
                if(this.thongtin_tk.ngay_yc==null||this.thongtin_tk.ngay_yc==''){
                    return
                }
                let ds_hs=await this.tracuu_hoso_cc_v2({
                    vma_hscc: '',
                    vptm_id:'',
                    vtungay:this.thongtin_tk.ngay_yc,
                    vloaitbi_id: 0,
                    vloai: 3
                })
                if(ds_hs.length>0){
                    this.dataSourcesCTBD=ds_hs
                }else{
                    this.$toast.error('Không tìm thấy thông tin hồ sơ !')
                    this.clear_all()
                }
            }
        },
        async selectedItemsChangedCTBD(data){
            if(this.dataSourcesCTBD.length<=0||data.length<=0){
                return
            }
            //init focus
            this.vmahscc=data[0].ma_hs_cc
            await this.NAP_THONGTIN_HOSO(this.vmahscc)

        },
        async NAP_THONGTIN_HOSO(vma_hs){
            //sp_thongtin_hscc
            let rs=await this.sp_thongtin_hscc(vma_hs)
            if(rs.length<=0){
                this.$toast.error('Không tìm thấy thông tin mã hồ sơ!')
                //
                this.ClearGUI()
                this.dataSourcesPT=[]
                this.dataSourcesCTDHTC=[]
                this.phieu_id = 0
                this.hscc_id = 0
                this.chitietcc_id = 0
                return
            }
            let item=rs[0]
            this.hscc_id=item.hscc_id?item.hscc_id:0
            this.thongtin_hs.input_ten_hs=item.ten_hs?item.ten_hs.toString():''
            this.thongtin_hs.input_ma_hs=item.ma_hs_cc?item.ma_hs_cc.toString():''
            this.thongtin_hs.input_nguoi_dk=item.nguoi_dk?item.nguoi_dk.toString():''
            this.thongtin_hs.ngay_bd=item.ngaybd_dk?moment(item.ngaybd_dk,'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss'):''
            this.thongtin_hs.ngay_kt=item.ngaykt_dk?moment(item.ngaykt_dk,'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss'):''
            this.thongtin_hs.trungtam_vt_selected=item.ttvt_id?item.ttvt_id:null
            this.thongtin_hs.trangthai_selected=item.ttcc_id?item.ttcc_id:null

            await this.HienThiDS_PhieuTH(this.hscc_id)
            // gridChitiet_FocusedRowChanged(0);

        },
        async HienThiDS_PhieuTH(hscc_id){
            //tracuu_phieu_cc
            this.dataSourcesCTDHTC=await this.tracuu_phieu_cc(hscc_id)
        },
        async selectedItemsChangedCTDHTC(data){
            if(this.dataSourcesCTDHTC.length<=0||data.length<=0){
                this.dataSourcesPT=[]
                return
            }
            //handle
            this.phieu_id=data[0].phieucc_id
            await this.NAP_DS_PHANTU_MANG()
        },
        async NAP_DS_PHANTU_MANG(){
            //lay_ds_ptm_caitao_baoduong
            this.dataSourcesPT=await this.lay_ds_ptm_caitao_baoduong(this.hscc_id, this.phieu_id)
            

        },
        rowPTDoubleClicked(index, rowData){
            console.log('rowPTDoubleClicked', rowData)
            this.chitietcc_id=rowData.chitietcc_id?rowData.chitietcc_id:0
            this.$refs.nhanVienTH.showModal()

        },
        selectedItemsChangedPT(data){
            if(this.dataSourcesPT.length<=0||data.length<=0){
                return
            }
            this.chitietcc_id=data[0].chitietcc_id?data[0].chitietcc_id:0
        },
        onEnterMaHS(){
            this.Search()
        },
        acceptPTM(data){
            console.log('acceptPTM', data)
            this.ds_ptm_id=''
            this.thongtin_tk.input_ten_ptm=''
            if(data&&data.length<=0){
                return
            }
            this.loaitbi_id=data[0].loaitbi_id
            this.ds_ptm_id=data.map(x=>x.thietbi_id).join(',')
            this.thongtin_tk.input_ten_ptm=data.map(x=>x.ten_tbi).join(',')
            this.Search()
           
        },
        clickVattu(){
            //show modal vật tư
            if(this.dataSourcesPT.length<=0||this.chitietcc_id==0){
                return
            }
            this.$refs.traCuuVatTuPTM.showModal()
        },
        async sp_tt_trangthai_cc(){
            try{
                this.loading(true)
                let response=await API.sp_tt_trangthai_cc(this.axios)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000'&&response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async sp_nap_ds_ttvt(nhanvien_id){
            try{
                this.loading(true)
                let response=await API.sp_nap_ds_ttvt(this.axios, nhanvien_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000'&&response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async tracuu_hoso_cc_v2(data){
            try{
                this.loading(true)
                let response=await API.tracuu_hoso_cc_v2(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000'&&response.data.data) {
                    return response.data.data.map((item)=>Object.assign(item,{
                        ngay_yc:item.ngay_yc?moment(item.ngay_yc,'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY'):null,
                        ngaybd_dk:item.ngaybd_dk?moment(item.ngaybd_dk,'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY'):null,
                        ngaykt_dk:item.ngaykt_dk?moment(item.ngaykt_dk,'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY'):null
                    }))
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async sp_thongtin_hscc(ma_hs){
            try{
                let response=await API.sp_thongtin_hscc(this.axios, ma_hs)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000'&&response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async tracuu_phieu_cc(hscc_id){
            try{
                this.loading(true)
                let response=await API.tracuu_phieu_cc(this.axios, hscc_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000'&&response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async lay_ds_ptm_caitao_baoduong(hscc_id, phieucc_id){
            try{
                this.loading(true)
                let response=await API.lay_ds_ptm_caitao_baoduong(this.axios, hscc_id, phieucc_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000'&&response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        
        async initDuLieu(){
            this.thongtin_hs.ds_trangthai=await this.sp_tt_trangthai_cc()
            if(this.thongtin_hs.ds_trangthai.length>0){
                this.thongtin_hs.trangthai_selected=this.thongtin_hs.ds_trangthai[0].ttcc_id
            }
            let nhanvien_id=this.$root.token.getNhanVienID()
            this.thongtin_hs.ds_trungtam_vt=await this.sp_nap_ds_ttvt(nhanvien_id)
            if(this.thongtin_hs.ds_trungtam_vt.length>0){
                this.thongtin_hs.trungtam_vt_selected=this.thongtin_hs.ds_trungtam_vt[0].donvi_id
            }
            //


        }
        
    },
    mounted(){
        setTimeout(()=>{
            this.initDuLieu()
        },500)
    }

}
</script>
<style>
.mx-input:disabled, .mx-input.disabled{
    color: #555 !important;
}
</style>