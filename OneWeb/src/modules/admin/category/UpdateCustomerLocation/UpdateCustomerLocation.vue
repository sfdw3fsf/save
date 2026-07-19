<template>
    <div class="main-wrapper">
        <breadcrumb :header="header" />
         <ActionTop :actions="actions" @onActionClick="onActionClick"/>
        <div class="page-content">
            <div class="box-form">
                <div class="legend-title">Thông tin tìm kiếm</div>
                <div class="row">
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key w100">Mã hộ dân</div>
                            <div class="value">
                                <input type="text" @keyup.enter="onEnterMaHoDan($event)" v-model="thongtin_timkiem.input_ma_hodan" class="form-control highlight">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w100">Quận/Huyện</div>
                            <div class="value">
                                <div class="select-custom" ref="ds_quan">
                                    <select2 :settings="{ dropdownParent: $refs['ds_quan'] }" ref="cboQuan"
                                        v-model="thongtin_timkiem.quan_selected" class="select2"
                                        :options="thongtin_timkiem.quan.map(e=> ({id: e.QUAN_ID, text: e.TEN_QUAN}))"
                                        @change="changeQuan"
                                        >
                                    </select2>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key w100">Phường/Xã</div>
                            <div class="value">
                                <div class="select-custom" ref="ds_phuong">
                                    <select2 :settings="{ dropdownParent: $refs['ds_phuong'] }" ref="cboPhuong"
                                        v-model="thongtin_timkiem.phuong_selected" class="select2"
                                        :options="thongtin_timkiem.phuong.map(e=> ({id: e.PHUONG_ID, text: e.TEN_PHUONG}))"
                                        @change="changePhuong"
                                        >
                                    </select2>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w100">
                                <div class="check-action">
                                    <input type="checkbox" v-model="thongtin_timkiem.checkBoxPho" class="check">
                                    <span class="name">Đường/Phố</span>
                                </div>
                            </div>
                            <div class="value">
                                <div class="select-custom" ref="ds_quan">
                                    <select2 :settings="{ dropdownParent: $refs['ds_quan'] }" ref="cboQuan"
                                        v-model="thongtin_timkiem.pho_selected" class="select2"
                                        :options="thongtin_timkiem.pho.map(e=> ({id: e.PHO_ID, text: e.TEN_PHO}))"
                                        :disabled="!thongtin_timkiem.checkBoxPho"
                                        >
                                    </select2>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="legend-title mart20">Thông tin cập nhật</div>
                <div class="row">
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key w100">
                                <div class="check-action">
                                    <input type="checkbox" v-model="thongtin_capnhat.checkBoxDonViQL" class="check">
                                    <span class="name">Đơn vị QL</span>
                                </div>
                            </div>
                            <div class="value">
                                <div class="select-custom" ref="ds_donvi_ql">
                                    <select2 :settings="{ dropdownParent: $refs['ds_donvi_ql'] }" ref="cboDonViQL"
                                        v-model="thongtin_capnhat.donvi_ql_selected" class="select2"
                                        :options="thongtin_capnhat.donvi_ql.map(e=> ({id: e.donvi_id, text: e.ten_dv}))"
                                        :disabled="!thongtin_capnhat.checkBoxDonViQL"
                                        @change="changeDonVi"
                                        >
                                    </select2>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w100">Nhóm</div>
                            <div class="value">
                                <div class="select-custom" ref="ds_nhom">
                                    <select2 :settings="{ dropdownParent: $refs['ds_nhom'] }" ref="cboNhom"
                                        v-model="thongtin_capnhat.nhom_selected" class="select2"
                                        :options="thongtin_capnhat.nhom.map(e=> ({id: e.loainv_id, text: e.ten}))"
                                        :disabled="!thongtin_capnhat.checkBoxDonViQL"
                                        @change="changeNhom"
                                        >
                                    </select2>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key w100">Nhân viên</div>
                            <div class="value">
                                <div class="select-custom" ref="ds_nhanvien">
                                    <select2 :settings="{ dropdownParent: $refs['ds_nhanvien'] }" ref="cboNhanVien"
                                        v-model="thongtin_capnhat.nhanvien_selected" class="select2"
                                        :options="thongtin_capnhat.nhanvien.map(e=> ({id: e.nhanvien_id, text: e.ten_ht}))"
                                        :disabled="!thongtin_capnhat.checkBoxDonViQL"
                                        >
                                    </select2>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w100">
                                Địa chỉ
                            </div>
                            <div class="value">
                                <div class="input-more-button">
                                    <button class="btn" @click="chonDiachi">
                                        <span class="-ap icon-more_horiz"></span>
                                    </button>
                                    <input type="text" ref="input_diachi" v-model="thongtin_capnhat.input_diachi" class="form-control ">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="box-form">
                <div class="legend-title">Danh sách hộ dân</div>
                <KDataGrid
                    :columns="columns"
                    :dataSource="dataSources"
                    :showColumnCheckbox="false"
                    :enable-paging-server="false"
                    :allowPaging="true"
                    :showFilter="true"
                    :enabledSelectFirstRow="true"
                    @onRowSelected="onRowSelected"
                    @selectedItemsChanged="gridDsChanged"/>
            </div>
        </div>
        <Footer/>
        <!-- Modal -->
        <CapNhatThongTinThanhVienModal ref="capnhat_thongtin_thanhvien" :hodan_id="hodan_id" />
        <CapNhatDichVuHoDanModal ref="capnhat_dichvu_hodan" :hodan_id="hodan_id"/>
        <DiaChiKhachHangModal :data="dataChonDiaChi" ref="chonDiaChi" @accept="acceptDiaChi"/>
    </div>
</template>
<script>

import Footer from '@/components/FooterStyle.vue'
import breadcrumb from '@/components/kylq_components/breadcrumb.vue'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import KDataGrid from '@/components/kylq_components/KDataGrid'
//JS
import '@/assets/vendor/jquery/split'

import CapNhatThongTinThanhVienModal from './CapNhatThongTinThanhVienModal.vue'
import CapNhatDichVuHoDanModal from './CapNhatDichVuHoDanModal.vue'
import UpdateCustomerLocationAPI from './UpdateCustomerLocationAPI'
import DiaChiKhachHangModal from '../../../contract/setup/SurveyRequest/popups/ChonDiaChi/DiaChiKhachHangModal.vue'

export default {
    name:'UpdateCustomerLocation',
    components:{
        Footer,
        breadcrumb,
        ActionTop,
        CapNhatThongTinThanhVienModal,
        CapNhatDichVuHoDanModal,
        DiaChiKhachHangModal,
        KDataGrid
    },
    data(){
        return{
            header: {
                title: "Cập nhật địa chỉ, dịch vụ hộ dân",
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
                    id:'capnhat',
                    name:'Cập nhật',
                    active: true,
                    icon_class:'one-reload1'
                },
                {
                    id:'dichvu',
                    name:'Dịch vụ',
                    active: true,
                    icon_class:'nc-icon-glyph media-2_headphones-mic'
                },
                {
                    id:'thanhvien',
                    name:'Thành viên',
                    active: true,
                    icon_class:'one-users'
                },
            ],
            columns:[
                {
                    fieldName:'ma_hd',
                    headerText:'Mã hộ dân',
                    allowFiltering:true
                },
                {
                    fieldName:'ten_hd',
                    headerText:'Tên hộ dân',
                    allowFiltering:true
                },
                 {
                    fieldName:'diachi',
                    headerText:'Địa chỉ',
                    allowFiltering:true
                },
                {
                    fieldName:'quan',
                    headerText:'Quận',
                    allowFiltering:true
                },
                {
                    fieldName:'phuong',
                    headerText:'Phường',
                    allowFiltering:true
                },
                {
                    fieldName:'pho',
                    headerText:'Phố',
                    allowFiltering:true
                },
                {
                    fieldName:'sonha',
                    headerText:'Số nhà',
                    allowFiltering:true
                }
            ],
            dataSources:[],
            thongtin_timkiem:{
                input_ma_hodan:'',
                quan_selected:null,
                quan:[],
                phuong_selected:null,
                phuong:[],
                pho_selected:null,
                pho:[],

                checkBoxPho:false
            },
            thongtin_capnhat:{
                donvi_ql_selected:null,
                donvi_ql:[],
                nhom_selected:null,
                nhom:[],
                nhanvien_selected:null,
                nhanvien:[],
                input_diachi:'',

                checkBoxDonViQL:false
            },
            hodan_selected:null,
            dataChonDiaChi:{},
            hodan_id:0     
        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='timkiem'){
                this.hienthi_ds_hodan_theo_diachi()
            }else if(action.id=='capnhat'){
                this.cap_nhat()
            }else if(action.id=='dichvu'){
                if(this.dataSources.length==0||this.hodan_id==0||this.hodan_selected==null){
                    this.$toast.error('Không có dữ liệu hộ dân để cập nhật dịch vụ')
                    return
                }
                this.$refs.capnhat_dichvu_hodan.showModal()
                
            }else if(action.id=='thanhvien'){
                if(this.dataSources.length==0||this.hodan_id==0||this.hodan_selected==null){
                    this.$toast.error('Không có dữ liệu hộ dân để cập nhật thành viên!')
                    return
                }
                this.$refs.capnhat_thongtin_thanhvien.showModal()
            }
        },
        clear(){
            this.thongtin_timkiem.input_ma_hodan=''
            this.thongtin_capnhat.input_diachi=''
            this.hodan_selected=null
        },
        cap_nhat(){
            if(this.dataSources.length==0){
                this.$toast.error('Danh sách trống, không có dữ liệu để cập nhật!')
                return
            }
            if(this.hodan_selected==null){
                this.$toast.error('Chưa chọn hộ dân để cập nhật!')
                return
            }

            let data={
                vdiachi_id: this.hodan_selected.diachi_id,
                vtinh_id: this.hodan_selected.tinh_id,
                vquan_id: this.hodan_selected.quan_id,
                vphuong_id: this.hodan_selected.phuong_id,
                vpho_id: this.hodan_selected.pho_id,
                vap_id: this.hodan_selected.ap_id,
                vkhu_id: this.hodan_selected.khu_id,
                vdacdiem_id: this.hodan_selected.dacdiem_id,
                vdiachi: this.thongtin_capnhat.input_diachi,
                vsonha: this.hodan_selected.sonha,
                vctv_id: this.thongtin_capnhat.checkBoxDonViQL?(this.thongtin_capnhat.nhanvien_selected!=null?this.thongtin_capnhat.nhanvien_selected:-1):-1,
                vhodan_id: this.hodan_selected.hodan_id
            }

            this.capnhat_diachi_hodan_v2(data)


        },
        gridDsChanged(data){
            // if(this.dataSources.length<=0||data.length<=0){
            //     this.hodan_id=0
            //     this.hodan_selected=null
            //     this.thongtin_capnhat.input_diachi=''
            //     return
            // }
            // this.onSelectedRow(data[0])
        },
        onRowSelected(rowData){
            console.log('onRowSelected')
            this.onSelectedRow(rowData)
        },
        async onSelectedRow(item){
            console.log('onSelectedRow', item)
            this.hodan_id=item.hodan_id
            this.hodan_selected= Object.assign({},item)
            this.thongtin_capnhat.input_diachi=item.diachi?item.diachi:''

            try{
                //load thông tin cập nhật
                this.loading(true)
                if(item.ctv_id!=null&&item.ctv_id.toString().trim()!=''){
                    this.thongtin_capnhat.checkBoxDonViQL=true
                    //đơn vị theo cộng tác viên id
                    this.thongtin_capnhat.donvi_ql_selected=null
                    let responseDV = await UpdateCustomerLocationAPI.lay_danhsach_donvi_theo_ctv(this.axios, item.ctv_id)
                    if(responseDV && responseDV.data && responseDV.data.error_code && responseDV.data.error_code == 'BSS-00000000' && responseDV.data.data) {
                        if(responseDV.data.data.length>0){
                            this.thongtin_capnhat.donvi_ql_selected=responseDV.data.data[0].donvi_id
                        }
                    }
                    // nhóm theo cộng tác viên id
                    this.thongtin_capnhat.nhom_selected=null
                    let responseNhom = await UpdateCustomerLocationAPI.lay_danhsach_nhomlnv_theo_ctv(this.axios, item.ctv_id)
                    if(responseNhom && responseNhom.data && responseNhom.data.error_code && responseNhom.data.error_code == 'BSS-00000000' && responseNhom.data.data) {
                        if(responseNhom.data.data.length>0){
                            this.thongtin_capnhat.nhom_selected=responseNhom.data.data[0].loainv_id
                        }
                    }
                    this.thongtin_capnhat.nhanvien_selected=null
                    if(this.thongtin_capnhat.donvi_ql_selected!=null&&this.thongtin_capnhat.nhom_selected!=null){
                        this.thongtin_capnhat.nhanvien=[]
                        let responseNV = await UpdateCustomerLocationAPI.lay_danhsach_nhanvien_ctv(this.axios, this.thongtin_capnhat.donvi_ql_selected, this.thongtin_capnhat.nhom_selected)
                        if(responseNV && responseNV.data && responseNV.data.error_code && responseNV.data.error_code == 'BSS-00000000' && responseNV.data.data) {
                            this.thongtin_capnhat.nhanvien=responseNV.data.data
                            if(this.thongtin_capnhat.nhanvien.length>0){
                                this.thongtin_capnhat.nhanvien_selected=item.ctv_id
                            }
                        }else{
                            this.thongtin_capnhat.nhanvien=[]
                        }
                    }
                }else{
                    this.thongtin_capnhat.checkBoxDonViQL=false
                    this.thongtin_capnhat.donvi_ql_selected=null
                    this.thongtin_capnhat.nhom_selected=null
                    this.thongtin_capnhat.nhanvien_selected=null

                }
            }catch(e){
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Đã có lỗi xảy ra khi lấy dữ liệu hiển thị')
                }
            }finally{
                this.loading(false)
            }
        },
        onEnterMaHoDan(event){
            this.hienthi_ds_hodan_theo_diachi()
        },
        async hienthi_ds_hodan_theo_diachi(){
            let data={
                vquan_id: this.thongtin_timkiem.quan_selected!=null?this.thongtin_timkiem.quan_selected:-1,
                vphuong_id: this.thongtin_timkiem.phuong_selected!=null?this.thongtin_timkiem.phuong_selected:-1,
                vpho_id: this.thongtin_timkiem.checkBoxPho?(this.thongtin_timkiem.pho_selected!=null?this.thongtin_timkiem.pho_selected:-1) :-1,
                vma_hd:this.thongtin_timkiem.input_ma_hodan.trim()!=''?this.thongtin_timkiem.input_ma_hodan.trim():0
            }
            //lay_ds_hodan_theo_diachiV1
            //this.dataSources=[]
            this.hodan_selected=null
            this.dataSources=await this.lay_ds_hodan_theo_diachiV1(data)
        },
        changeQuan(){
            this.thongtin_timkiem.phuong=[]
            this.thongtin_timkiem.phuong_selected=null
            this.lay_danhsach_phuong_theo_quan(this.thongtin_timkiem.quan_selected)
        },
        changePhuong(){
            this.lay_danhsach_pho_theo_phuong(this.thongtin_timkiem.phuong_selected, 1)
        },
        changeDonVi(){
            let donvi_id=this.thongtin_capnhat.donvi_ql_selected!=null?this.thongtin_capnhat.donvi_ql_selected:-1
            let loainv_id=this.thongtin_capnhat.nhom_selected!=null?this.thongtin_capnhat.nhom_selected:-1
            this.lay_danhsach_nhanvien_ctv(donvi_id, loainv_id)
        },
        changeNhom(){
            let donvi_id=this.thongtin_capnhat.donvi_ql_selected!=null?this.thongtin_capnhat.donvi_ql_selected:-1
            let loainv_id=this.thongtin_capnhat.nhom_selected!=null?this.thongtin_capnhat.nhom_selected:-1
            this.lay_danhsach_nhanvien_ctv(donvi_id, loainv_id)
        },
        chonDiachi(){
            if(this.hodan_selected==null){
                this.dataChonDiaChi={
                    tinh_id:0,
                    quan_id:0,
                    phuong_id:0,
                    pho_id:0,
                    ap_id:0,
                    khu_id:0,
                    dacdiem_id:0,
                    sonha:'',
                    diachi:''
                }
            }else{
                this.dataChonDiaChi={
                    tinh_id:this.hodan_selected.tinh_id!=null?this.hodan_selected.tinh_id:0,
                    quan_id:this.hodan_selected.quan_id!=null?this.hodan_selected.quan_id:0,
                    phuong_id:this.hodan_selected.phuong_id!=null?this.hodan_selected.phuong_id:0,
                    pho_id:this.hodan_selected.pho_id!=null?this.hodan_selected.pho_id:0,
                    ap_id:this.hodan_selected.ap_id!=null?this.hodan_selected.ap_id:0,
                    khu_id:this.hodan_selected.khu_id!=null?this.hodan_selected.khu_id:0,
                    dacdiem_id:this.hodan_selected.dacdiem_id!=null?this.hodan_selected.dacdiem_id:0,
                    sonha:this.hodan_selected.sonha!=null?this.hodan_selected.sonha:'',
                    diachi:this.hodan_selected.diachi!=null?this.hodan_selected.diachi:''
                }
            }
            setTimeout(()=>{
                this.$refs.chonDiaChi.showModal()
            },500)
        },
        acceptDiaChi(data){
            if(this.hodan_selected==null){
                return
            }
            this.thongtin_capnhat.input_diachi=data.diachi
            Object.assign(this.hodan_selected, data)
        },
        async lay_danhsach_quan(){
            try{
                let response = await UpdateCustomerLocationAPI.lay_danhsach_quan(this.axios)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    this.thongtin_timkiem.quan=response.data.data
                    if(this.thongtin_timkiem.quan.length>0){
                        this.thongtin_timkiem.quan_selected=this.thongtin_timkiem.quan[0].QUAN_ID
                        await this.lay_danhsach_phuong_theo_quan(this.thongtin_timkiem.quan_selected)
                    }
                }else{
                    this.thongtin_timkiem.quan=[]
                }
            }catch(e){
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Không load được danh sách quận')
                }
            } 
        },
        async lay_danhsach_phuong_theo_quan(quan_id){
            try{
                this.thongtin_timkiem.phuong=[]
                this.thongtin_timkiem.phuong_selected=null
                let response = await UpdateCustomerLocationAPI.lay_danhsach_phuong_theo_quan(this.axios, quan_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    this.thongtin_timkiem.phuong=response.data.data
                    if(this.thongtin_timkiem.phuong.length>0){
                        this.thongtin_timkiem.phuong_selected=this.thongtin_timkiem.phuong[0].PHUONG_ID
                        this.lay_danhsach_pho_theo_phuong(this.thongtin_timkiem.phuong_selected,1)
                    }
                }else{
                    this.thongtin_timkiem.phuong=[]
                    this.thongtin_timkiem.phuong_selected=null
                }
            }catch(e){
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Không load được danh sách phường')
                }
            } 
        },
        async lay_danhsach_pho_theo_phuong(phuong_id, loai){
            try{
                this.thongtin_timkiem.pho=[]
                this.thongtin_timkiem.pho_selected=null
                let response = await UpdateCustomerLocationAPI.lay_danhsach_pho_theo_phuong(this.axios, phuong_id, loai)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    this.thongtin_timkiem.pho=response.data.data
                    if(this.thongtin_timkiem.pho.length>0){
                        this.thongtin_timkiem.pho_selected=this.thongtin_timkiem.pho[0].PHO_ID
                    }
                }else{
                    this.thongtin_timkiem.pho=[]
                    this.thongtin_timkiem.pho_selected=null
                }
            }catch(e){
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Không load được danh sách đường phố')
                }
            } 
        },
        async lay_ds_donvi_vs_nhom_ctv(){
            try{
                
                let responseDV = await UpdateCustomerLocationAPI.lay_danhsach_donvi_ctv(this.axios)
                if(responseDV && responseDV.data && responseDV.data.error_code && responseDV.data.error_code == 'BSS-00000000' && responseDV.data.data) {
                    this.thongtin_capnhat.donvi_ql=responseDV.data.data
                    if(this.thongtin_capnhat.donvi_ql.length>0){
                        this.thongtin_capnhat.donvi_ql_selected=this.thongtin_capnhat.donvi_ql[0].donvi_id
                    }
                }else{
                    this.thongtin_capnhat.donvi_ql=[]
                }

                let responseNhom = await UpdateCustomerLocationAPI.lay_danhsach_nhom_ctv(this.axios)
                if(responseNhom && responseNhom.data && responseNhom.data.error_code && responseNhom.data.error_code == 'BSS-00000000' && responseNhom.data.data) {
                    this.thongtin_capnhat.nhom=responseNhom.data.data
                    if(this.thongtin_capnhat.nhom.length>0){
                        this.thongtin_capnhat.nhom_selected=this.thongtin_capnhat.nhom[0].loainv_id
                    }
                }else{
                    this.thongtin_capnhat.nhom=[]
                }

                let donvi_id=this.thongtin_capnhat.donvi_ql_selected!=null?this.thongtin_capnhat.donvi_ql_selected:0
                let loainv_id=this.thongtin_capnhat.nhom_selected!=null?this.thongtin_capnhat.nhom_selected:0

                this.lay_danhsach_nhanvien_ctv(donvi_id, loainv_id)


            }catch(e){
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Không load được danh sách đơn vị hoặc nhóm CTV')
                }
            } 
        },
        async lay_danhsach_nhanvien_ctv(donvi_id, loainv_id){
            try{
                this.loading(true)
                this.thongtin_capnhat.nhanvien=[]
                this.thongtin_capnhat.nhanvien_selected=null
                let response = await UpdateCustomerLocationAPI.lay_danhsach_nhanvien_ctv(this.axios, donvi_id, loainv_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    this.thongtin_capnhat.nhanvien=response.data.data
                    if(this.thongtin_capnhat.nhanvien.length>0){
                        this.thongtin_capnhat.nhanvien_selected=this.thongtin_capnhat.nhanvien[0].nhanvien_id
                    }
                }else{
                    this.thongtin_capnhat.nhanvien=[]
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Không load được danh sách nhân viên')
                }
            } 
        },
        async lay_ds_hodan_theo_diachiV1(data){
            try{
                this.loading(true)
                let response = await UpdateCustomerLocationAPI.lay_ds_hodan_theo_diachi(this.axios, data)
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
        async capnhat_diachi_hodan_v2(data){
            try{
                this.loading(true)
                let response = await UpdateCustomerLocationAPI.capnhat_diachi_hodan_v2(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    this.$toast.success('Cập nhật kết quả thành công!')
                    this.clear()
                    this.hienthi_ds_hodan_theo_diachi()
                }else{
                    this.$toast.error('Đã xảy ra lỗi cập nhật thông tin hộ dân không thành công!')
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Đã xảy ra lỗi cập nhật thông tin hộ dân không thành công!')
                }
            }
        },
        async initDuLieu(){
            this.lay_danhsach_quan()
            this.lay_ds_donvi_vs_nhom_ctv()
        }

    
    },
    mounted(){
        $(".select2js").select2()
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
.select2-selection { overflow: hidden !important; }
.select2-container .select2-selection--single .select2-selection__rendered {
    white-space: normal !important;
    word-break: break-all !important;
}
/* asset css select2 form */
.select2-container {
    width: 100% !important;
}

.select2-container--default .select2-selection--single {
    border-color: #E0E0E0;
    outline: none;
}

.select2-dropdown {
    border: 1px solid #E0E0E0;
}

.select2-container--default .select2-selection--single .select2-selection__arrow b {
    font-family: "FontAwesome";
    border: 0px;
    top: 0px;
    height: 1.428rem;
    bottom: 0px;
    line-height: 1.428rem;
    left: 5px;
    margin: auto;
}
.select2-container--default .select2-selection--single .select2-selection__arrow b:before {
    content: "";
}

.select2-container--default .select2-search--dropdown .select2-search__field {
    border: 0px;
    outline: none;
}
.select2-container--default.select2-container--disabled .select2-selection--single{
    background-color: #e9ecef!important
}
</style>