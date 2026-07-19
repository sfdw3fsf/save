<template>
    <div class="main-wrapper">

        <breadcrumb :header="header" v-if="!isPopup" />

        <ActionTop :actions="actions" @onActionClick="onActionClick"/>

        <div class="page-content">
            <div class="box-form">
                <div class="legend-title">Thông tin hướng giao</div>
                <div class="row">

                    <div class="col-sm-7 col-12">
                        <div class="info-row">
                            <div class="value padl0">
                                <div class="row">
                                    <div class="col-sm-7 col-12">
                                        <div class="info-row">
                                            <div class="key w100">Hướng giao ID <k-required-marker/></div>
                                            <div class="value">
                                                <input type="text" ref="inputHuongGiaoID"  @keypress="isNumber($event)" :value="inputTTHG.huonggiao_id" @change="e=>inputTTHG.huonggiao_id=e.target.value" :readonly="readOnlyHuongGiaoID" class="form-control highlight red" >
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-sm-5 col-12">
                                        <div class="info-row">
                                            <div class="key w60">Mã HG <k-required-marker/></div>
                                            <div class="value k-mahg">
                                                <input type="text"  :value="inputTTHG.ma_huonggiao" @change="e=>inputTTHG.ma_huonggiao=e.target.value" ref="inputMaHG" class="form-control highlight red">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="info-row">
                                    <div class="key w100">Tên hướng <k-required-marker/></div>
                                    <div class="value">
                                        <input type="text" ref="inputTenHG" :value="inputTTHG.ten_huong" @change="e=>inputTTHG.ten_huong=e.target.value" class="form-control">
                                    </div>
                                </div>
                                <div class="info-row">
                                    <div class="key w100">Ghi chú</div>
                                    <div class="value">
                                        <input type="text" :value="inputTTHG.ghi_chu" @change="e=>inputTTHG.ghi_chu=e.target.value" class="form-control">
                                    </div>
                                </div>
                                <div class="info-row">
                                    <div class="key w100"></div>
                                    <div class="value">
                                        <div class="list-checks">
                                            <div class="item">
                                                <div class="check-action">
                                                    <input type="checkbox" v-model="checkBoxsTTHG.sms_traphieu" class="check">
                                                    <span class="name">SMS Trả phiếu</span>
                                                </div>
                                            </div>
                                            <div class="item">
                                                <div class="check-action">
                                                    <input type="checkbox" v-model="checkBoxsTTHG.giao_viec_nvkt" class="check">
                                                    <span class="name">Giao việc NVKT</span>
                                                </div>
                                            </div>
                                            <div class="item">
                                                <div class="check-action">
                                                    <input type="checkbox" v-model="checkBoxsTTHG.giao_viec_nvkd" class="check">
                                                    <span class="name">Giao việc NVKD</span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="value w60 nowrap">
                                <div class="list-checks-ver">
                                    <div class="item marb15 mart7">
                                        <div class="check-action">
                                            <input type="checkbox" v-model="checkBoxsTTHG.loc_theo_nv" class="check">
                                            <span class="name">Lọc theo NV</span>
                                        </div>
                                    </div>
                                    <div class="item marb15 mart7">
                                        <div class="check-action">
                                            <input type="checkbox" v-model="checkBoxsTTHG.sms" class="check">
                                            <span class="name">SMS</span>
                                        </div>
                                    </div>
                                    <div class="item marb15 mart7">
                                        <div class="check-action">
                                            <input type="checkbox" v-model="checkBoxsTTHG.kiem_tra_kh" class="check">
                                            <span class="name">Kiểm tra KH</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                    </div>

                    <div class="col-sm-5 col-12">
                        <div class="info-row">
                            <div class="key w100">Nhóm quy trình</div>
                            <div class="value">
                                <div class="select-custom" ref="ds_nhom_quytrinh">
                                    <select2 :settings="{ dropdownParent: $refs['ds_nhom_quytrinh'] }" ref="cboNhomQuyTrinh"
                                        v-model="selectTTHG.nhom_quytrinh_selected" class="select2"
                                        :options="selectTTHG.nhom_quytrinh.map(e=> ({id: e.NHOM_QT_ID, text: e.TEN_NHOM}))"
                                        @change="changeNhomQT">
                                    </select2>
                                </div>  
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w100">Quy trình</div>
                            <div class="value">
                                <div class="select-custom" ref="ds_quytrinh">
                                    <!-- <SelectExt v-model="selectTTHG.quytrinh_selected" :allowFiltering="true" data-text-field="QUYTRINH" data-value-field="QUYTRINH_ID" :dataSource="selectTTHG.quytrinh"/> -->
                                    <select2 :settings="{ dropdownParent: $refs['ds_quytrinh'] }" ref="cboQuyTrinh"
                                        v-model="selectTTHG.quytrinh_selected" class="select2"
                                        :options="selectTTHG.quytrinh.map(e=> ({id: e.QUYTRINH_ID, text: e.QUYTRINH}))">
                                    </select2>
                                </div>  
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-8 col-12">
                                <div class="info-row">
                                    <div class="key w100">Trạng thái HĐ</div>
                                    <div class="value">
                                        <div class="select-custom" ref="ds_trangthai_hd">
                                            <select2 :settings="{ dropdownParent: $refs['ds_trangthai_hd'] }" ref="cboTrangThaiHD"
                                                v-model="selectTTHG.trangthai_hd_selected" class="select2"
                                                :options="selectTTHG.trangthai_hd.map(e=> ({id: e[selectTTHG.columns_trangthai_hd[0].column_name], text: e[selectTTHG.columns_trangthai_hd[1].column_name]}))"
                                                >
                                            </select2>
                                        </div>  
                                    </div>
                                    <!-- <div class="value">
                                        <div class="select-custom">
                                            <select v-model="selectTTHG.trangthai_hd_selected" class="form-control">
                                                <option v-for="item in selectTTHG.trangthai_hd" :key="item[selectTTHG.columns_trangthai_hd[0].column_name]" :value="item[selectTTHG.columns_trangthai_hd[0].column_name]">
                                                    {{ item[selectTTHG.columns_trangthai_hd[1].column_name] }}
                                                </option>
                                            </select>
                                        </div>
                                    </div> -->
                                </div>
                            </div>
                            <div class="col-sm-4 col-12">
                                <div class="info-row">
                                    <div class="key w80">Thứ tự</div>
                                    <div class="value">
                                        <input type="number" ref="inputThuTu"  :value="inputTTHG.thutu" @change="e=>inputTTHG.thutu=e.target.value" class="form-control">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-4 col-12">
                                <div class="info-row">
                                    <div class="key w100">Giao Vip</div>
                                    <div class="value">
                                        <div class="select-custom">
                                            <select v-model="selectTTHG.giao_vip_selected" class="form-control">
                                                <option v-for="item in selectTTHG.giao_vip_net_media" :key="item.id"  :value="item.id">{{item.name}}</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-4 col-12">
                                <div class="info-row">
                                    <div class="key w100">Giao Net</div>
                                    <div class="value">
                                        <div class="select-custom">
                                            <select v-model="selectTTHG.giao_net_selected" class="form-control">
                                                <option v-for="item in selectTTHG.giao_vip_net_media" :key="item.id"  :value="item.id">{{item.name}}</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-4 col-12">
                                <div class="info-row">
                                    <div class="key w80">Giao Media</div>
                                    <div class="value">
                                        <div class="select-custom">
                                            <select v-model="selectTTHG.giao_media_selected" class="form-control">
                                                <option v-for="item in selectTTHG.giao_vip_net_media" :key="item.id"  :value="item.id">{{item.name}}</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                    </div>
                </div>
            </div>

            <div class="box-form">
                <div class="legend-title">Thông tin tìm kiếm</div>
                <div class="row">
                    <div class="col-sm-3 col-12">
                        <div class="info-row">
                            <div class="key w80">
                                <div class="check-action">
                                    <input type="checkbox" v-model="thongtin_timkiem.check_loai_hd" @change="changeCheckLoaiHD($event)" class="check">
                                    <span class="name">Loại HĐ</span>
                                </div>
                            </div>
                            <div class="value">
                                <div class="select-custom" ref="ds_loaihd_tk">
                                    <select2 :settings="{ dropdownParent: $refs['ds_loaihd_tk'] }" ref="cboLoaiHD"
                                        v-model="thongtin_timkiem.loai_hd_selected" class="select2"
                                        :options="thongtin_timkiem.loai_hd.map(e=> ({id: e.LOAIHD_ID, text: e.TEN_LOAIHD}))"
                                        :disabled="!thongtin_timkiem.check_loai_hd"
                                        @change="changeLoaiHD"
                                        >
                                    </select2>
                                </div>  
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-3 col-12">
                        <div class="info-row">
                            <div class="key w80">
                                <div class="check-action">
                                    <input type="checkbox" v-model="thongtin_timkiem.check_dichvu" @change="changeCheckDichVuVT($event)" class="check">
                                    <span class="name">Dịch vụ</span>
                                </div>
                            </div>
                            <div class="value">
                                <div class="select-custom" ref="ds_dichvu_tk">
                                    <select2 :settings="{ dropdownParent: $refs['ds_dichvu_tk'] }" ref="cboDichVuTK"
                                        v-model="thongtin_timkiem.dichvu_selected" class="select2"
                                        :options="thongtin_timkiem.dichvu.map(e=> ({id: e.DICHVUVT_ID, text: e.TEN_DVVT}))"
                                        :disabled="!thongtin_timkiem.check_dichvu"
                                        @change="changeDichVuVT"
                                        >
                                    </select2>
                                </div>  
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key w100">
                                <div class="check-action">
                                    <input type="checkbox" v-model="thongtin_timkiem.check_quytrinh" @change="changeCheckQuyTrinh($event)" class="check">
                                    <span class="name">Quy trình</span>
                                </div>
                            </div>
                            <div class="value">
                                <div class="select-custom" ref="ds_quytrinh_tk">
                                    <!-- <SelectExt v-model="thongtin_timkiem.quytrinh_selected" 
                                    :allowFiltering="true" 
                                    data-text-field="QUYTRINH" 
                                    data-value-field="QUYTRINH_ID"
                                    :disabled="!thongtin_timkiem.check_quytrinh"
                                    :dataSource="thongtin_timkiem.quytrinh"
                                    @change="changeQuyTrinh"/> -->
                                    <select2 :settings="{ dropdownParent: $refs['ds_quytrinh_tk'] }" ref="cboQuyTrinhTK"
                                        v-model="thongtin_timkiem.quytrinh_selected" class="select2"
                                        :options="thongtin_timkiem.quytrinh.map(e=> ({id: e.QUYTRINH_ID, text: e.QUYTRINH}))"
                                        :disabled="!thongtin_timkiem.check_quytrinh"
                                        @change="changeQuyTrinh"
                                        >
                                    </select2>
                                </div>  
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="box-form">
                <div class="legend-title">Danh sách hướng giao</div>
                <!-- <KTableV3 :columns="columns"
                    :dataSources="dataSources"
                    @onSelectedRow="onSelectedRow"
                    @doubleClickRow="doubleClickRow"
                    id="huonggiao_id"
                    :rowSelectedId="rowSelectedId"
                    :allowFilter="true"
                /> -->
                <KDataGrid
                    ref="gridDsHuongGiao"
                    :columns="columns"
                    :dataSource="dataSources"
                    :enable-paging-server="false"
                    :allowPaging="true"
                    :showFilter="true"
                    :showColumnCheckbox="false"
                    :enabledSelectFirstRow="true"
                    @rowDoubleClicked="rowDoubleClicked"
                    @selectedItemsChanged="gridHuongGiaoChanged"
                />
            </div>

        </div>
        <Footer/>
        <!-- Modal -->
        <TaoLenhHuongGiaoModal ref="taoLenhHuongGiaoModal"
            :huonggiao_id="huonggiao_id"
            :nhom_qt_id="nhom_quytrinh_id"
            :tenhuong="tenhuong"
            :quytrinh="quytrinh"
            :phanvung_id="phanvung_id"
        />
    </div>
</template>
<script>
import Footer from '@/components/FooterStyle.vue'
import breadcrumb from '@/components/kylq_components/breadcrumb.vue'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import KDataGrid from '@/components/kylq_components/KDataGrid'
import KRequiredMarker from '@/components/kylq_components/KRequiredMarker.vue'
//JS
import '@/assets/vendor/jquery/split'
import IntersectionDirectionAPI from './IntersectionDirectionAPI'
import TaoLenhHuongGiaoModal from './TaoLenhHuongGiaoModal.vue'
import SelectExt from "@/components/Controls/SelectExt"
export default {
    name:'IntersectionDirection',
    props:{
        tk_dichvu_id:{
            type:Number,
            default:0
        },
        tk_loaihd_id:{
            type:Number,
            default:0
        },
        tk_quytrinh_id:{
            type:Number,
            default:0
        },
        isPopup:{
            type:Boolean,
            default:false
        }

    },
    components:{
        Footer,
        breadcrumb,
        ActionTop,
        KDataGrid,
        TaoLenhHuongGiaoModal,
        KRequiredMarker,
        SelectExt
    },
    computed:{
        nhom_quytrinh_id(){
            return this.selectTTHG.nhom_quytrinh_selected!=null?this.selectTTHG.nhom_quytrinh_selected:0
        },
        tenhuong(){
            return this.inputTTHG.huonggiao_id + '.('+this.inputTTHG.ma_huonggiao+') '+ this.inputTTHG.ten_huong
        },
        quytrinh(){
            const quytrinh=this.selectTTHG.quytrinh.find(x=>x.QUYTRINH_ID==this.selectTTHG.quytrinh_selected)
            return quytrinh!=undefined?quytrinh.QUYTRINH:''
        },
        phanvung_id(){
            return this.huonggiao_selected!=null?this.huonggiao_selected.phanvung_id:0
        },
        loai_hd_o_selected(){
            const loaihd=this.thongtin_timkiem.loai_hd.find(x.LOAIHD_ID==this.thongtin_timkiem.loai_hd_selected)
            return loaihd!=undefined?loaihd:null
        }
    },
    data(){
        return {
            header: {
                title: "Hướng giao",
                list: [
                    { 
                        name: "Dữ liệu", 
                        link: { name: "Ui.cards" },
                        active: false
                    },
                    {
                        name: "Phát triển thuê bao",
                        link: { name: "Ui.buttons" },
                        active: false
                    },
                    {
                        name: "Quy trình -  Nghiệp vụ",
                        link: { name: "Ui.buttons" },
                        active: false
                    },
                    {
                        name: "Hướng giao",
                        link: { name: "Ui.buttons" },
                        active: true
                    }
                ]
            },
            actions:[
                {
                    id:'nhapmoi',
                    name:'Nhập mới',
                    active: true,
                    icon_class:'one-file-plus'
                },
                {
                    id:'ghilai',
                    name:'Ghi lại',
                    active: true,
                    icon_class:'one-save'
                },
                {
                    id:'huy',
                    name:'Hủy',
                    active: true,
                    icon_class:'nc-icon-glyph ui-1_circle-remove'
                },
                {
                    id:'xoa',
                    name:'Xóa',
                    active: true,
                    icon_class:'one-trash'
                },
            ],
            columns:[
                {
                    fieldName:'huonggiao_id',
                    headerText:'ID',
                    allowFiltering:true,
                    width:120
                },
                {
                    fieldName:'ma_hg',
                    headerText:'Mã hướng giao',
                    allowFiltering:true
                },
                 {
                    fieldName:'huonggiao',
                    headerText:'Tên hướng giao',
                    allowFiltering:true
                },
                {
                    fieldName:'quytrinh_id',
                    headerText:'Quy trình ID',
                    allowFiltering:true,
                    width:120
                },
                {
                    fieldName:'quytrinh',
                    headerText:'Quy trình',
                    allowFiltering:true
                },
                {
                    fieldName:'ten_loaihd',
                    headerText:'Loại hợp đồng',
                    allowFiltering:true
                }
            ],
            dataSources:[],
            checkBoxsTTHG:{
                loc_theo_nv:false,
                sms:false,
                kiem_tra_kh:false,
                sms_traphieu:false,
                giao_viec_nvkt:false,
                giao_viec_nvkd:false
            },
            inputTTHG:{
                huonggiao_id: 0,
                ma_huonggiao: '',
                ten_huong:'',
                ghi_chu:'',
                thutu: 0
            },
            selectTTHG:{
                nhom_quytrinh_selected:null,
                nhom_quytrinh:[],
                quytrinh_selected:null,
                quytrinh:[],
                trangthai_hd_selected:null,
                trangthai_hd:[],
                columns_trangthai_hd:[],

                giao_vip_selected:0,
                giao_net_selected:0,
                giao_media_selected:0,
                giao_vip_net_media:[
                    {
                        id:0,
                        name:''
                    },
                    {
                        id:1,
                        name:'Giao phối hợp'
                    },
                    {
                        id:2,
                        name:'Giao xử lý'
                    },
                ],
            },
            thongtin_timkiem:{
                loai_hd_selected:null,
                loai_hd:[],
                dichvu_selected:null,
                dichvu:[],
                quytrinh_selected:null,
                quytrinh:[],

                check_loai_hd:true,
                check_dichvu:true,
                check_quytrinh:false
            },
            huonggiao_selected:null,
            NHOM_QT:{
                DIEUHANH_THICONG: 1,
                BAOHONG: 2,
                GIAIQUYET_KN: 3,
                BAOHANH_TBI: 4,
                SLSC_CNTT: 5,
                LUANCHUYEN_HOSO: 6,
                QUANLY_VATTU: 7,
                CAITAO_BAODUONG: 8
            },
            rowSelectedId:-1,
            huonggiao_id:0,
            readOnlyHuongGiaoID:false

        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='nhapmoi'){
                this.setActiveActions(1)
            }else if(action.id=='ghilai'){
                this.ghiDuLieu()
            }else if(action.id=='huy'){
                if(this.huonggiao_selected!=null){
                    this.onSelectedRow(this.huonggiao_selected)
                }else{
                    this.setActiveActions(0)
                }
                
            }else if(action.id=='xoa'){
                if(this.huonggiao_selected==null){
                    this.$toast.error('Hãy chọn hướng giao để xoá!')
                    return
                }
                //truyền nhom_qt_id, quytrinh_id, loaihd_id, dichvuvt_id: để xoá xong trả về danh sách dữ liệu luôn, ngược lại để 0 chỉ xoá
                let data={
                    nhom_qt_id: 0,
                    quytrinh_id: 0,
                    loaihd_id: 0,
                    dichvuvt_id: 0,
                    huonggiao_id: this.huonggiao_selected.huonggiao_id
                }
                this.$confirm('Bạn thật sự muốn xóa dữ liệu không ?','Thông báo',{
                    confirmButtonText: 'Có',
                    cancelButtonText: 'Không'
                }).then(() => {
                    this.sp_del_huonggiao_by_id(data)
                })
            }
        },
        setActiveActions(kieu){
            this.actions.forEach((item)=>{
                item.active=false
            })
            this.readOnlyHuongGiaoID=true
            if(kieu==-1){//bắt đầu
                this.actions[this.actions.findIndex(x=>x.id=='ghilai')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='huy')].active=true
                this.readOnlyHuongGiaoID=false
                this.$refs.inputHuongGiaoID.focus()
            }else if(kieu==0){//bắt đầu
                this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active=true
                this.clear()
            }else if(kieu==1){//thêm mới
                this.actions[this.actions.findIndex(x=>x.id=='ghilai')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='huy')].active=true
                this.readOnlyHuongGiaoID=false
                this.clear()
                this.$refs.inputHuongGiaoID.focus()

            }else if(kieu==2){//Huỷ
                this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='xoa')].active=true
                this.clear()
            }else if(kieu==3){//edit
                this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='ghilai')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='huy')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='xoa')].active=true
            }
        },
        clear(){
            this.inputTTHG.ten_huong=''
            this.inputTTHG.ma_huonggiao=''
            this.inputTTHG.huonggiao_id=''
            this.inputTTHG.thutu=0
            this.inputTTHG.ghi_chu=''
        },
        isNumber(evt) {
            evt = (evt) ? evt : window.event;
            var charCode = (evt.which) ? evt.which : evt.keyCode;
            if ((charCode > 31 && (charCode < 48 || charCode > 57))) {
                evt.preventDefault()
            } else {
                return true
            }
        },
        changeCheckLoaiHD(event){
            this.huonggiao_id=0
            this.load_quytrinh_timkiem_refresh_data()
        },
        changeLoaiHD(){
            this.huonggiao_id=0
            this.load_quytrinh_timkiem_refresh_data()
        },
        changeCheckDichVuVT(event){
            this.huonggiao_id=0
            this.load_quytrinh_timkiem_refresh_data()
        },
        changeDichVuVT(){
            this.huonggiao_id=0
            this.load_quytrinh_timkiem_refresh_data()
        },
        changeCheckQuyTrinh(){
            this.huonggiao_id=0
            this.hienthi_danhsach()
        },
        changeQuyTrinh(){ 
            this.huonggiao_id=0
            this.hienthi_danhsach()
        },
        doubleClickRow(item){
            this.$refs.taoLenhHuongGiaoModal.showModal()
        },
        rowDoubleClicked(rowIndex, rowData){
            this.$refs.taoLenhHuongGiaoModal.showModal()
        },
        gridHuongGiaoChanged(data){
            if(this.dataSources.length<=0||data.length<=0){
                this.huonggiao_id=0
                this.huonggiao_selected=null
                return
            }
            this.onSelectedRow(data[0])
        },
        onSelectedRow(item){
            this.huonggiao_selected=item
            this.huonggiao_id=item.huonggiao_id?item.huonggiao_id:0

            // init data lên form thông tin
            this.inputTTHG.huonggiao_id=item.huonggiao_id?item.huonggiao_id.toString():''
            this.inputTTHG.ten_huong=item.huonggiao?item.huonggiao.toString():''
            this.inputTTHG.thutu=item.thutu?item.thutu.toString():''
            this.inputTTHG.ghi_chu=item.ghichu?item.ghichu.toString():''

            this.inputTTHG.ma_huonggiao=item.ma_hg?item.ma_hg.toString():''

            if(item.quytrinh_id!=null&&item.quytrinh_id!=''){
                this.selectTTHG.quytrinh_selected=parseInt(item.quytrinh_id)
            }else {
                this.selectTTHG.quytrinh_selected=null
            }
            //
            if(this.selectTTHG.nhom_quytrinh_selected==this.NHOM_QT.GIAIQUYET_KN){
                if(item.ttkn_id!=null&&item.ttkn_id!=''){
                    this.selectTTHG.trangthai_hd_selected=parseInt(item.ttkn_id)
                }else{
                    this.selectTTHG.trangthai_hd_selected=null
                }
            }else if(this.selectTTHG.nhom_quytrinh_selected==this.NHOM_QT.DIEUHANH_THICONG){
                if(item.tthd_id!=null&&item.tthd_id!=''){
                    this.selectTTHG.trangthai_hd_selected=parseInt(item.tthd_id)
                }else{
                    this.selectTTHG.trangthai_hd_selected=null
                }
            }else if(this.selectTTHG.nhom_quytrinh_selected==this.NHOM_QT.BAOHONG){
                if(item.ttbh_id!=null&&item.ttbh_id!=''){
                    this.selectTTHG.trangthai_hd_selected=parseInt(item.ttbh_id)
                }else{
                    this.selectTTHG.trangthai_hd_selected=null
                }
            }else if(this.selectTTHG.nhom_quytrinh_selected==this.NHOM_QT.BAOHANH_TBI){
                if(item.ttbh_id!=null&&item.ttbh_id!=''){
                    this.selectTTHG.trangthai_hd_selected=parseInt(item.ttbh_id)
                }else{
                    this.selectTTHG.trangthai_hd_selected=null
                }
            }else{
                this.selectTTHG.trangthai_hd_selected=null
            }
            //
            if(item.loctheo_nv!=null&&item.loctheo_nv=='1'){
                this.checkBoxsTTHG.loc_theo_nv=true
            }else{
                this.checkBoxsTTHG.loc_theo_nv=false
            }

            if(item.sms!=null&&item.sms=='1'){
                this.checkBoxsTTHG.sms=true
            }else{
                this.checkBoxsTTHG.sms=false
            }

            if(item.status!=null&&item.status=='1'){
                this.checkBoxsTTHG.kiem_tra_kh=true
            }else{
                this.checkBoxsTTHG.kiem_tra_kh=false
            }
            if(item.sms_traphieu!=null&&item.sms_traphieu=='1'){
                this.checkBoxsTTHG.sms_traphieu=true
            }else{
                this.checkBoxsTTHG.sms_traphieu=false
            }
            if(item.giaoviec!=null&&item.giaoviec=='1'){
                this.checkBoxsTTHG.giao_viec_nvkt=true
            }else{
                this.checkBoxsTTHG.giao_viec_nvkt=false
            }

            if(item.giaoviec_nvkd!=null&&item.giaoviec_nvkd=='1'){
                this.checkBoxsTTHG.giao_viec_nvkd=true
            }else{
                this.checkBoxsTTHG.giao_viec_nvkd=false
            }

            this.selectTTHG.giao_vip_selected=parseInt(item.giao_vip)
            this.selectTTHG.giao_net_selected=parseInt(item.giao_net)
            this.selectTTHG.giao_media_selected=parseInt(item.giao_media)


            this.setActiveActions(3)
        },
        async changeNhomQT(){
            this.huonggiao_id=0
            //lấy danh sách quy trình và danh sách trạng thái hợp đồng
            this.selectTTHG.quytrinh=[]
            this.selectTTHG.trangthai_hd=[]
            this.selectTTHG.columns_trangthai_hd=[]
            this.selectTTHG.trangthai_hd_selected=null
            this.huonggiao_selected=null
            this.clear()
            let result = await this.sp_lay_ds_quytrinh_combobox(this.selectTTHG.nhom_quytrinh_selected, 0, 0, 1)
            if(result){
                //data quy trình
                if(result.bang&&result.bang.data_json){
                    this.selectTTHG.quytrinh=result.bang.data_json
                    if(this.selectTTHG.quytrinh.length>0){
                        this.selectTTHG.quytrinh_selected=this.selectTTHG.quytrinh[0].QUYTRINH_ID
                    }
                }else{
                    this.selectTTHG.quytrinh=[]
                }
                //data trạng thái hd
                if(result.bang1&&result.bang1.data_json&&result.bang1.columns){
                    this.selectTTHG.trangthai_hd=result.bang1.data_json
                    this.selectTTHG.columns_trangthai_hd=result.bang1.columns
                    if(this.selectTTHG.trangthai_hd.length>0){
                        this.selectTTHG.trangthai_hd_selected=this.selectTTHG.trangthai_hd[0][this.selectTTHG.columns_trangthai_hd[0].column_name]
                    }
                }else {
                    this.selectTTHG.trangthai_hd=[]
                    this.selectTTHG.columns_trangthai_hd=[]
                    this.selectTTHG.trangthai_hd_selected=null
                }
            }else{
                this.selectTTHG.quytrinh=[]
                this.selectTTHG.trangthai_hd=[]
                this.selectTTHG.columns_trangthai_hd=[]
                this.selectTTHG.trangthai_hd_selected=null
            }
            //lấy danh sách quy trình tim kiếm
            //this.load_quytrinh_timkiem_refresh_data()
            this.thongtin_timkiem.quytrinh_selected=null
            let loaihd_id=this.thongtin_timkiem.check_loai_hd?(this.thongtin_timkiem.loai_hd_selected!=null?this.thongtin_timkiem.loai_hd_selected:0):0
            let dichvuvt_id=this.thongtin_timkiem.check_dichvu?(this.thongtin_timkiem.dichvu_selected!=null?this.thongtin_timkiem.dichvu_selected:0):0
            this.thongtin_timkiem.quytrinh=[]
            let resultQTTK=await this.sp_lay_ds_quytrinh_timkiem_quytrinh(this.selectTTHG.nhom_quytrinh_selected, loaihd_id, dichvuvt_id, 1)
            if(resultQTTK){
                if(resultQTTK.bang&&resultQTTK.bang.data_json){
                    this.thongtin_timkiem.quytrinh=resultQTTK.bang.data_json
                    if(this.thongtin_timkiem.quytrinh.length>0){
                        this.thongtin_timkiem.quytrinh_selected=this.thongtin_timkiem.quytrinh[0].QUYTRINH_ID
                    }
                }else{
                    this.thongtin_timkiem.quytrinh=[]
                }
            }else{
                this.thongtin_timkiem.quytrinh=[]
            }

            await this.hienthi_danhsach()
        },
        async load_quytrinh_timkiem_refresh_data(){
            this.thongtin_timkiem.quytrinh_selected=null
            let loaihd_id=this.thongtin_timkiem.check_loai_hd?(this.thongtin_timkiem.loai_hd_selected!=null?this.thongtin_timkiem.loai_hd_selected:0):0
            let dichvuvt_id=this.thongtin_timkiem.check_dichvu?(this.thongtin_timkiem.dichvu_selected!=null?this.thongtin_timkiem.dichvu_selected:0):0
            let result = await this.sp_lay_ds_quytrinh_timkiem_quytrinh(this.selectTTHG.nhom_quytrinh_selected, loaihd_id, dichvuvt_id, 1)
            if(result){
                if(result.bang&&result.bang.data_json){
                    this.thongtin_timkiem.quytrinh=result.bang.data_json
                    if(this.thongtin_timkiem.quytrinh.length>0){
                        this.thongtin_timkiem.quytrinh_selected=this.thongtin_timkiem.quytrinh[0].QUYTRINH_ID
                    }
                }else{
                    this.thongtin_timkiem.quytrinh=[]
                }
            }else{
                this.thongtin_timkiem.quytrinh=[]
            }
            await this.hienthi_danhsach()
        },
        //form load
        async initDuLieu(){
            //nhóm quy trình
            this.selectTTHG.nhom_quytrinh=await this.lay_danhsach_nhom_quytrinh()
            if(this.selectTTHG.nhom_quytrinh.length>0){
                this.selectTTHG.nhom_quytrinh_selected=this.selectTTHG.nhom_quytrinh[0].NHOM_QT_ID
            }
            //load danh sách loại hợp đồng
            this.thongtin_timkiem.loai_hd=await this.lay_danhsach_loai_hd()
            if(this.thongtin_timkiem.loai_hd.length>0){
                //Loại hợp đồng đặt mới selected với id=1
                this.thongtin_timkiem.loai_hd_selected=this.tk_loaihd_id!=0?this.tk_loaihd_id:1
            }
            //load danh sách dịch vụ
            this.thongtin_timkiem.dichvu=await this.lay_danhsach_dichvu()
            if(this.thongtin_timkiem.dichvu.length>0){
                //Dịch vụ cố định với id=1
                this.thongtin_timkiem.dichvu_selected=this.tk_dichvu_id!=0?this.tk_dichvu_id:1
            }
            if(this.tk_quytrinh_id!=0){
                this.thongtin_timkiem.check_quytrinh=true
                this.thongtin_timkiem.quytrinh_selected=this.tk_quytrinh_id
            }
            //lấy danh sách quy trình và danh sách trạng thái hợp đồng
            this.selectTTHG.quytrinh=[]
            this.selectTTHG.trangthai_hd=[]
            this.selectTTHG.columns_trangthai_hd=[]
            this.selectTTHG.trangthai_hd_selected=null

            let result = await this.sp_lay_ds_quytrinh_combobox(this.selectTTHG.nhom_quytrinh_selected, 0, 0, 1)
           
            if(result){
                //data quy trình
                if(result.bang&&result.bang.data_json){
                    this.selectTTHG.quytrinh=result.bang.data_json.sort(function(a, b) {
                        return a.QUYTRINH_ID - b.QUYTRINH_ID
                    })
                     console.log('size quytrinh', this.selectTTHG.quytrinh.length)
                    if(this.selectTTHG.quytrinh.length>0){
                        this.selectTTHG.quytrinh_selected=this.selectTTHG.quytrinh[0].QUYTRINH_ID
                    }
                }else{
                    this.selectTTHG.quytrinh=[]
                }
                //data trạng thái hd
                if(result.bang1&&result.bang1.data_json&&result.bang1.columns&&result.bang1.columns.length>0){
                    this.selectTTHG.columns_trangthai_hd=result.bang1.columns
                    let self=this
                    this.selectTTHG.trangthai_hd=result.bang1.data_json.sort(function(a, b) {
                        return a[self.selectTTHG.columns_trangthai_hd[0].column_name] - b[self.selectTTHG.columns_trangthai_hd[0].column_name]
                    })
                    if(this.selectTTHG.trangthai_hd.length>0){
                        this.selectTTHG.trangthai_hd_selected=this.selectTTHG.trangthai_hd[0][this.selectTTHG.columns_trangthai_hd[0].column_name]
                    }
                }else {
                    this.selectTTHG.trangthai_hd=[]
                    this.selectTTHG.columns_trangthai_hd=[]
                    this.selectTTHG.trangthai_hd_selected=null
                }
            }else{
                this.selectTTHG.quytrinh=[]
                this.selectTTHG.trangthai_hd=[]
                this.selectTTHG.columns_trangthai_hd=[]
                this.selectTTHG.trangthai_hd_selected=null
            }
            //load quy trình tìm kiếm
            let loaihd_id=this.thongtin_timkiem.check_loai_hd?(this.thongtin_timkiem.loai_hd_selected!=null?this.thongtin_timkiem.loai_hd_selected:0):0
            let dichvuvt_id=this.thongtin_timkiem.check_dichvu?(this.thongtin_timkiem.dichvu_selected!=null?this.thongtin_timkiem.dichvu_selected:0):0
            let resultQT = await this.sp_lay_ds_quytrinh_timkiem_quytrinh(this.selectTTHG.nhom_quytrinh_selected, loaihd_id, dichvuvt_id, 1)
            if(resultQT){
                if(resultQT.bang&&resultQT.bang.data_json){
                    this.thongtin_timkiem.quytrinh=resultQT.bang.data_json
                    if(this.thongtin_timkiem.quytrinh.length>0){
                        this.thongtin_timkiem.quytrinh_selected=this.thongtin_timkiem.quytrinh[0].QUYTRINH_ID
                    }
                }else{
                    this.thongtin_timkiem.quytrinh=[]
                }
            }else{
                this.thongtin_timkiem.quytrinh=[]
            }
            //Hiển thị danh sách hướng giao
            await this.hienthi_danhsach()
        },
        async lay_danhsach_nhom_quytrinh(){
            try{
                this.loading(true)
                let response=await IntersectionDirectionAPI.lay_danhsach_nhom_quytrinh(this.axios)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data.sort(function(a, b) {
                        return a.NHOM_QT_ID - b.NHOM_QT_ID
                    })
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async lay_danhsach_loai_hd(){
            try{
                this.loading(true)
                let response=await IntersectionDirectionAPI.lay_danhsach_loai_hd(this.axios)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data.sort(function(a, b) {
                        return a.LOAIHD_ID - b.LOAIHD_ID
                    })
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async lay_danhsach_dichvu(){
            try{
                this.loading(true)
                let response=await IntersectionDirectionAPI.lay_danhsach_dichvu(this.axios)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data.sort(function(a, b) {
                        return a.DICHVUVT_ID - b.DICHVUVT_ID
                    })
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async sp_lay_ds_quytrinh_combobox(nhom_qt_id, loaihd_id, dichvuvt_id, kt_load){
            try{
                this.loading(true)
                let response=await IntersectionDirectionAPI.sp_lay_ds_quytrinh_combobox(this.axios, nhom_qt_id, loaihd_id, dichvuvt_id, kt_load)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return null
                }
            }catch(e){
                this.loading(false)
                return null
            }
        },
        async hienthi_danhsach(){
            let nhom_qt_id=this.selectTTHG.nhom_quytrinh_selected?this.selectTTHG.nhom_quytrinh_selected:0
            var quytrinh_id=0
            var loaihd_id=0
            var dichvuvt_id=0

            if(this.thongtin_timkiem.check_quytrinh){
                quytrinh_id=this.thongtin_timkiem.quytrinh_selected?this.thongtin_timkiem.quytrinh_selected:0
            }
            if(this.thongtin_timkiem.check_loai_hd){
                loaihd_id=this.thongtin_timkiem.loai_hd_selected?this.thongtin_timkiem.loai_hd_selected:0
            }
            if(this.thongtin_timkiem.check_dichvu){
                dichvuvt_id=this.thongtin_timkiem.dichvu_selected?this.thongtin_timkiem.dichvu_selected:0
            }
            //this.dataSources=[]
            this.dataSources=await this.lay_danhsach_huonggiao(nhom_qt_id, quytrinh_id, loaihd_id, dichvuvt_id)
            console.log('dataSources', this.dataSources)
            if(this.dataSources.length<=0){
                this.setActiveActions(1)
            }
            if(this.dataSources.length>0){
                // if(this.huonggiao_id!=0){
                //     const index=this.dataSources.findIndex(x=>x.huonggiao_id==this.huonggiao_id)
                //     if(index>-1){
                //         this.rowSelectedId=this.dataSources[index].huonggiao_id
                //         this.onSelectedRow(this.dataSources[index])
                //     }else{
                //         this.rowSelectedId=this.dataSources[0].huonggiao_id
                //         this.onSelectedRow(this.dataSources[0])
                //     }
                // }else{
                //     this.rowSelectedId=this.dataSources[0].huonggiao_id
                //     this.onSelectedRow(this.dataSources[0])
                // }
            }else{
                this.setActiveActions(1)
            }
            
        },
        async lay_danhsach_huonggiao(nhom_qt_id, quytrinh_id, loaihd_id, dichvuvt_id){
            try{
                this.loading(true)
                let response=await IntersectionDirectionAPI.lay_danhsach_huonggiao(this.axios, nhom_qt_id, quytrinh_id, loaihd_id, dichvuvt_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data.sort((a,b)=>{
                        return a.huonggiao_id-b.huonggiao_id
                    })
                }else {
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async sp_lay_ds_quytrinh_timkiem_quytrinh(nhom_qt_id, loaihd_id, dichvuvt_id, kt_load){
            try{
                this.loading(true)
                let response=await IntersectionDirectionAPI.sp_lay_ds_quytrinh_timkiem_quytrinh(this.axios, nhom_qt_id, loaihd_id, dichvuvt_id, kt_load)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else {
                    return null
                }
            }catch(e){
                this.loading(false)
                return null
            }
        },
        //dsf
        async sp_del_huonggiao_by_id(data){
            try{
                this.loading(true)
                let response=await IntersectionDirectionAPI.sp_del_huonggiao_by_id(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000'&&response.data.data) {
                   if(response.data.data.ketqua=='0'){
                        this.huonggiao_selected=null
                        this.huonggiao_id=0
                        this.clear()
                        this.$toast.success('Xoá dữ liệu thành công!')
                        //hiển thị lại danh sách
                        await this.hienthi_danhsach()
                   }else{
                       this.$toast.error('Xoá dữ liệu không thành công!')
                   }
                }else {
                    if(response && response.data &&response.data.message){
                        this.$toast.error(response.data.message)
                    }else{
                        this.$toast.error('Xoá dữ liệu không thành công!')
                    }
                }
            }catch(error){
                this.loading(false)
                if(error.data&&error.data.message){
                    this.$toast.error(error.data.message)
                }else if(error.response&&error.response.data&&error.response.data.message){
                    this.$toast.error(error.response.data.message)
                }else{
                    this.$toast.error('Xoá dữ liệu không thành công!')
                }
            }
        },
        async ghiDuLieu(){
            if(this.inputTTHG.huonggiao_id.toString().trim()==''){
                this.$toast.error('Hãy nhập ID hướng giao!')
                this.$refs.inputHuongGiaoID.focus()
                return 
            }
            if(this.inputTTHG.ma_huonggiao.toString().trim()==''){
                this.$toast.error('Hãy nhập mã hướng giao!')
                this.$refs.inputMaHG.focus()
                return 
            }
            if(this.inputTTHG.ten_huong.toString().trim()==''){
                this.$toast.error('Hãy nhập tên hướng giao!')
                this.$refs.inputTenHG.focus()
                return 
            }
            if(this.inputTTHG.thutu.toString().trim()==''){
                this.$toast.error('Hãy nhập thứ tự hướng giao!')
                this.$refs.inputThuTu.focus()
                return 
            }

            var is_insert=this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active==true?0:1
            let data=await this.taoDuLieu(is_insert)
            await this.sp_ghilai_huonggiao(data, is_insert)
        },
        async taoDuLieu(is_insert){
            if(is_insert==0&&this.huonggiao_selected==null){
                return
            }
            //this.huonggiao_id=is_insert==1?(await this.get_keys('HUONGGIAO')):this.huonggiao_selected.huonggiao_id
            this.huonggiao_id=Number(this.inputTTHG.huonggiao_id.trim())
            //nhom_qt_id,loaihd_id, dichvuvt_id=0 chỉ insert trả về kết quả, ngược lại trả về thêm cả danh sách
            let data={
                nhom_qt_id: 0,
                loaihd_id: 0,
                dichvuvt_id: 0,
                huonggiao_id: this.huonggiao_id,
                ma_hg: this.inputTTHG.ma_huonggiao,
                huonggiao: this.inputTTHG.ten_huong,
                thutu: this.inputTTHG.thutu,
                ghichu: this.inputTTHG.ghi_chu,
                quytrinh_id: this.selectTTHG.quytrinh_selected,
                tthd_id: this.selectTTHG.nhom_quytrinh_selected==this.NHOM_QT.DIEUHANH_THICONG?this.selectTTHG.trangthai_hd_selected:null,
                ttcc_id: 0,
                ttkn_id: this.selectTTHG.nhom_quytrinh_selected==this.NHOM_QT.GIAIQUYET_KN?this.selectTTHG.trangthai_hd_selected:null,
                ttbh_id: (this.selectTTHG.nhom_quytrinh_selected==this.NHOM_QT.BAOHONG)||(this.selectTTHG.nhom_quytrinh_selected==this.NHOM_QT.BAOHANH_TBI)?this.selectTTHG.trangthai_hd_selected:null,
                loctheo_nv: this.checkBoxsTTHG.loc_theo_nv?1:0,
                sms: this.checkBoxsTTHG.sms?1:0,
                status: this.checkBoxsTTHG.kiem_tra_kh?1:0,
                sms_traphieu: this.checkBoxsTTHG.sms_traphieu?1:0,
                giaoviec: this.checkBoxsTTHG.giao_viec_nvkt?1:0,
                giaoviec_nvkd: this.checkBoxsTTHG.giao_viec_nvkd?1:0,
                giao_vip: this.selectTTHG.giao_vip_selected,
                giao_net: this.selectTTHG.giao_net_selected,
                giao_media: this.selectTTHG.giao_media_selected,
                chk_new: is_insert
            }

            return data

        },
        //kieu=1: thêm mới, ngược lại cập nhật
        async sp_ghilai_huonggiao(data, kieu){
            try{
                this.loading(true)
                let response=await IntersectionDirectionAPI.sp_ghilai_huonggiao(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000'&&response.data.data) {
                   if(response.data.data.ketqua=='0'){
                        this.huonggiao_selected=null
                        this.clear()
                        this.$toast.success(kieu==1?'Thêm mới dữ liệu hướng giao thành công!':'Cập nhật dữ liệu hướng giao thành công!')
                        await this.hienthi_danhsach()
                   }else{
                       this.$toast.error(response.data.data.ketqua)
                   }
                }else {
                    if(response && response.data &&response.data.message){
                        this.$toast.error(response.data.message)
                    }else{
                        this.$toast.error(kieu==1?'Thêm mói dữ liệu không thành công!':'Cập nhật dữ liệu không thành công!')
                    }
                }
            }catch(error){
                this.loading(false)
                if(error.data&&error.data.message){
                    this.$toast.error(error.data.message)
                }else if(error.response&&error.response.data&&error.response.data.message){
                    this.$toast.error(error.response.data.message)
                }else{
                    this.$toast.error(kieu==1?'Thêm mói dữ liệu không thành công!':'Cập nhật dữ liệu không thành công!')
                }
            }
        },
        async get_keys(keyname){
            try{
                let response=await IntersectionDirectionAPI.get_keys(this.axios, keyname)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else {
                    return '0'
                }
            }catch(e){
                this.loading(false)
                return '0'
            }
        },
    },
    mounted(){
        $(".select2js").select2()
        this.setActiveActions(-1)
        setTimeout(()=>{
            this.initDuLieu()
        }, 500)
    }
}
</script>
<style>
.k-mahg{
    padding-right: 0px !important;
}
.cc_cc {
    padding-left: 5px !important;
    padding-right: 7px !important;
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
</style>