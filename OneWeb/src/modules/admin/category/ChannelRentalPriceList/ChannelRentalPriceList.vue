<template>
    <div class="main-wrapper">
        
        <breadcrumb :header="header"/>

        <ActionTop :actions="actions" @onActionClick="onActionClick"/>

        <div class="page-content">
            <div class="box-form">
                <div class="row">
                    <div class="col-sm-6 col-12">
                        <div class="legend-title">Thông tin bảng giá cước thuê kênh - cước thuê cổng</div>
                        <div class="row">
                            <div class="col-sm-7 col-12">
                                <div class="info-row">
                                    <div class="key w90">Dịch vụ <k-required-marker/></div>
                                    <div class="value">
                                        <div class="select-custom" ref="ds_dichvu">
                                            <select2 :z-index="2000" :settings="{ dropdownParent: $refs['ds_dichvu'] }" ref="cboDichVu"
                                                v-model="thongtin_banggia.dichvu_selected" class="select2"
                                                :options="thongtin_banggia.dichvu.map(e=> ({id: e.DICHVUVT_ID, text: e.TEN_DVVT}))"
                                                @change="changeDichVuBangGia">
                                            </select2>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-5 col-12">
                                <div class="info-row">
                                    <div class="key w90">Loại hình <k-required-marker/></div>
                                    <div class="value">
                                        <div class="select-custom" ref="ds_loaihinh">
                                            <select2 :z-index="2000" :settings="{ dropdownParent: $refs['ds_loaihinh'] }" ref="cboLoaiHinh"
                                                v-model="thongtin_banggia.loaihinh_selected" class="select2"
                                                :options="thongtin_banggia.loaihinh_filter.map(e=> ({id: e.LOAITB_ID, text: e.LOAIHINH_TB}))"
                                                @change="changeLoaiHinh">
                                            </select2>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-7 col-12">
                                <div class="info-row">
                                    <div class="key w90">Tên bảng giá <k-required-marker/></div>
                                    <div class="value">
                                        <input type="text" id="inputTenBangGia" ref="inputTenBangGia" @blur="outFocusInput('inputTenBangGia')" :value="thongtin_banggia.input_ten_banggia" @change="e=>thongtin_banggia.input_ten_banggia=e.target.value" class="form-control highlight">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-5 col-12">
                                <div class="info-row">
                                    <div class="key w90">Loại cước <k-required-marker/></div>
                                    <div class="value">
                                        <div class="select-custom" ref="ds_loaicuoc">
                                            <select2 :z-index="2000" :settings="{ dropdownParent: $refs['ds_loaicuoc'] }" ref="cboLoaiCuoc"
                                                v-model="thongtin_banggia.loaicuoc_selected" class="select2"
                                                :options="thongtin_banggia.loaicuoc.map(e=> ({id: e.kieu_id, text: e.ten_loaicuoc}))">
                                            </select2>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-7 col-12">
                                <div class="info-row">
                                    <div class="key w90">Cước TK <k-required-marker/></div>
                                    <div class="value">
                                        <vue-numeric class="form-control highlight tright" separator="," :value="thongtin_banggia.input_cuoc_tk" @change="e=>thongtin_banggia.input_cuoc_tk=e.target.value" placeholder="0" output-type="Number"></vue-numeric>
                                        <!-- <input type="text" :value="thongtin_banggia.input_cuoc_tk" @change="e=>thongtin_banggia.input_cuoc_tk=e.target.value" @blur="handleBlur($event, 1)" @keypress="isNumber($event)" class="form-control highlight tright"> -->
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-5 col-12">
                                <div class="info-row">
                                    <div class="key w90">Vat TK <k-required-marker/></div>
                                    <div class="value">
                                        <vue-numeric class="form-control highlight tright" separator="," :value="thongtin_banggia.input_vat_tk" @change="e=>thongtin_banggia.input_vat_tk=e.target.value" placeholder="0" output-type="Number"></vue-numeric>
                                        <!-- <input type="text" :value="thongtin_banggia.input_vat_tk" @change="e=>thongtin_banggia.input_vat_tk=e.target.value" @blur="handleBlur($event, 2)" @keypress="isNumber($event)" class="form-control highlight tright" > -->
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-7 col-12">
                                <div class="info-row">
                                    <div class="key w90">Cước TC <k-required-marker/></div>
                                    <div class="value">
                                        <vue-numeric class="form-control highlight tright" separator="," :value="thongtin_banggia.input_cuoc_tc" @change="e=>thongtin_banggia.input_cuoc_tc=e.target.value" placeholder="0" output-type="Number"></vue-numeric>
                                        <!-- <input type="text" :value="thongtin_banggia.input_cuoc_tc" @change="e=>thongtin_banggia.input_cuoc_tc=e.target.value" @blur="handleBlur($event, 3)" @keypress="isNumber($event)" class="form-control highlight tright" > -->
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-5 col-12">
                                <div class="info-row">
                                    <div class="key w90">Vat TC <k-required-marker/></div>
                                    <div class="value">
                                        <vue-numeric class="form-control highlight tright" separator="," :value="thongtin_banggia.input_vat_tc" @change="e=>thongtin_banggia.input_vat_tc=e.target.value" placeholder="0" output-type="Number"></vue-numeric>
                                        <!-- <input type="text" :value="thongtin_banggia.input_vat_tc" @change="e=>thongtin_banggia.input_vat_tc=e.target.value" @blur="handleBlur($event, 4)" @keypress="isNumber($event)" class="form-control highlight tright" > -->
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-7 col-12">
                                <div class="info-row">
                                    <div class="key w90">Từ ngày <k-required-marker/></div>
                                    <div class="value">
                                        <div class="input-icon-right">
                                            <k-date-picker v-model="thongtin_banggia.tu_ngay"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-5 col-12">
                                <div class="info-row">
                                    <div class="key w90">Đến ngày <k-required-marker/></div>
                                    <div class="value">
                                        <div class="input-icon-right">
                                            <k-date-picker v-model="thongtin_banggia.den_ngay"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="legend-title mart20">Thông tin tìm kiếm bảng giá</div>
                        <div class="row">
                            <div class="col-sm-7 col-12">
                                <div class="info-row">
                                    <div class="key w90">Dịch vụ</div>
                                    <div class="value">
                                        <div class="select-custom" ref="ds_dichvu_tk">
                                            <select2 :z-index="2000" :settings="{ dropdownParent: $refs['ds_dichvu_tk'] }" ref="cboDichVuTK"
                                                v-model="thongtin_timkiem.dichvu_selected" class="select2"
                                                :options="thongtin_timkiem.dichvu.map(e=> ({id: e.DICHVUVT_ID, text: e.TEN_DVVT}))"
                                                @change="changeDichVuTimKiem">
                                            </select2>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-5 col-12">
                                <div class="info-row">
                                    <div class="key w90">Loại hình</div>
                                    <div class="value">
                                        <div class="select-custom" ref="ds_loaihinh_tk">
                                            <select2 :z-index="2000" :settings="{ dropdownParent: $refs['ds_loaihinh_tk'] }" ref="cboLoaiHinhTK"
                                                v-model="thongtin_timkiem.loaihinh_selected" class="select2"
                                                :options="thongtin_timkiem.loaihinh_filter.map(e=> ({id: e.LOAITB_ID, text: e.LOAIHINH_TB}))">
                                            </select2>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="legend-title mart20">Danh sách bảng giá</div>
                        <KDataGrid
                            :columns="columnsBangGia"
                            :dataSource="dataSourcesBangGia"
                            :showColumnCheckbox="false"
                            :enable-paging-server="false"
                            :allowPaging="true"
                            :showFilter="true"
                            :enabledSelectFirstRow="true"
                            @onRowSelected="onSelectedRow"/>
                            <!-- @selectedItemsChanged="gridDsChanged"/> -->
                       
                    </div>
                    <div class="col-sm-6 col-12">
                        <div class="box-move-select-table">
                            <div class="box-col">
                                <div class="legend-title mart20">Đối tượng TB đã gán</div>
                                <KDataGrid
                                    ref="gridDoiTuongGan"
                                    :columns="columnsDoiTuong"
                                    :dataSource="dataSourcesDoiTuongGan"
                                    :showColumnCheckbox="true"
                                    :enable-paging-server="false"
                                    :allowPaging="true"
                                    :enabledSelectFirstRow="false"
                                    panelDataHeight="200px"
                                    :showFilter="true"/>
                            </div>
                            <div class="actions">
                                <button class="btn" @click="boGan(TYPE.DOITUONG)">
                                    <span class="fa fa-angle-right"></span>
                                </button>
                                <button class="btn" @click="boGanAll(TYPE.DOITUONG)">
                                    <span class="fa fa-angle-double-right"></span>
                                </button>
                                <button class="btn" @click="gan(TYPE.DOITUONG)">
                                    <span class="fa fa-angle-left"></span>
                                </button>
                                <button class="btn" @click="ganAll(TYPE.DOITUONG)">
                                    <span class="fa fa-angle-double-left"></span>
                                </button>
                            </div>
                            <div class="box-col">
                                <div class="legend-title mart20">Đối tượng TB chưa gán</div>
                                <KDataGrid
                                    ref="gridDoiTuongChuaGan"
                                    :columns="columnsDoiTuong"
                                    :dataSource="dataSourcesDoiTuongChuaGan"
                                    :showColumnCheckbox="true"
                                    :enable-paging-server="false"
                                    :allowPaging="true"
                                    :enabledSelectFirstRow="false"
                                    panelDataHeight="200px"
                                    :showFilter="true"/>
                            </div>
                        </div>
                        <div class="box-move-select-table">
                            <div class="box-col">
                                <div class="legend-title mart20">Loại kênh đã gán</div>
                                <KDataGrid
                                    ref="gridLoaiKenhGan"
                                    :columns="columnsLoaiKenh"
                                    :dataSource="dataSourcesLoaiKenhGan"
                                    :showColumnCheckbox="true"
                                    :enable-paging-server="false"
                                    :allowPaging="true"
                                    :enabledSelectFirstRow="false"
                                    panelDataHeight="200px"
                                    :showFilter="true"/>
                            </div>
                            <div class="actions">
                                <button class="btn" @click="boGan(TYPE.LOAIHINH)">
                                    <span class="fa fa-angle-right"></span>
                                </button>
                                <button class="btn" @click="boGanAll(TYPE.LOAIHINH)">
                                    <span class="fa fa-angle-double-right"></span>
                                </button>
                                <button class="btn" @click="gan(TYPE.LOAIHINH)">
                                    <span class="fa fa-angle-left"></span>
                                </button>
                                <button class="btn" @click="ganAll(TYPE.LOAIHINH)">
                                    <span class="fa fa-angle-double-left"></span>
                                </button>
                            </div>
                            <div class="box-col">
                                <div class="legend-title mart20">Loại kênh chưa gán</div>
                                <KDataGrid
                                    ref="gridLoaiKenhChuaGan"
                                    :columns="columnsLoaiKenh"
                                    :dataSource="dataSourcesLoaiKenhChuaGan"
                                    :showColumnCheckbox="true"
                                    :enable-paging-server="false"
                                    :allowPaging="true"
                                    :enabledSelectFirstRow="false"
                                    panelDataHeight="200px"
                                    :showFilter="true"/>
                            </div>
                        </div>
                        <div class="box-move-select-table">
                            <div class="box-col">
                                <div class="legend-title mart20">Loại hình, tốc độ đã gán</div>
                                <KDataGrid
                                    ref="gridTocDoGan"
                                    :columns="columnsTocDo"
                                    :dataSource="dataSourcesTocDoGan"
                                    :showColumnCheckbox="true"
                                    :enable-paging-server="false"
                                    :allowPaging="true"
                                    :enabledSelectFirstRow="false"
                                    panelDataHeight="200px"
                                    :showFilter="true"/>
                            </div>
                            <div class="actions">
                                <button class="btn" @click="boGan(TYPE.TOCDO)">
                                    <span class="fa fa-angle-right"></span>
                                </button>
                                <button class="btn" @click="boGanAll(TYPE.TOCDO)">
                                    <span class="fa fa-angle-double-right"></span>
                                </button>
                                <button class="btn" @click="gan(TYPE.TOCDO)">
                                    <span class="fa fa-angle-left"></span>
                                </button>
                                <button class="btn" @click="ganAll(TYPE.TOCDO)">
                                    <span class="fa fa-angle-double-left"></span>
                                </button>
                            </div>
                            <div class="box-col">
                                <div class="legend-title mart20">Loại hinh, tốc độ chưa gán</div>
                                <KDataGrid
                                    ref="gridTocDoChuaGan"
                                    :columns="columnsTocDo"
                                    :dataSource="dataSourcesTocDoChuaGan"
                                    :showColumnCheckbox="true"
                                    :enable-paging-server="false"
                                    :allowPaging="true"
                                    :enabledSelectFirstRow="false"
                                    panelDataHeight="200px"
                                    :showFilter="true"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <Footer/> 

    </div>
</template>
<script>
import Footer from '@/components/FooterStyle.vue'
import breadcrumb from '@/components/kylq_components/breadcrumb.vue'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import moment from 'moment'
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
import KTableV3 from '@/components/kylq_components/mirana/KTableV3.vue'
import KDataGrid from '@/components/kylq_components/KDataGrid'
import ChannelRentalPriceListAPI from './ChannelRentalPriceListAPI'
import { currency } from '@/filters/currency'
import KRequiredMarker from '@/components/kylq_components/KRequiredMarker.vue'
export default {
    name:'ChannelRentalPriceList',
    components:{
        Footer,
        breadcrumb,
        ActionTop,
        KDatePicker,
        KTableV3,
        KDataGrid,
        KRequiredMarker
    },
    data(){
        return{
            header: {
                title: "Bảng giá cước thuê kênh - cước thuê cổng",
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
                        name: "Bảng giá - Khuyến mãi - Gói cước",
                        link: { name: "Ui.buttons" },
                        active: false
                    },
                    {
                        name: "Bảng giá cước thuê kênh - cước thuê cổng",
                        link: { name: "Ui.buttons" },
                        active: false
                    }
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
                    id:'ghilai',
                    name:'Ghi lại',
                    active: true,
                    icon_class:'one-save'
                },
                {
                    id:'huy',
                    name:'Huỷ',
                    active: true,
                    icon_class:'nc-icon-glyph ui-1_circle-remove'
                },
                {
                    id:'xoa',
                    name:'Xoá',
                    active: true,
                    icon_class:'one-trash'
                },
                {
                    id:'timkiem',
                    name:'Tìm kiếm',
                    active: true,
                    icon_class:'one-search'
                },
            ],

            thongtin_banggia:{
                dichvu:[],
                dichvu_selected:null,
                loaihinh:[],
                loaihinh_selected:null,
                loaihinh_filter:[],
                loaicuoc:[],
                loaicuoc_selected:null,

                input_ten_banggia:'',
                input_cuoc_tk:'0',
                input_cuoc_tc:'0',
                input_vat_tk:'0',
                input_vat_tc:'0',
                
                tu_ngay:moment(new Date()).format('DD/MM/YYYY'),
                den_ngay:moment(new Date()).format('DD/MM/YYYY'),
            },
            thongtin_timkiem:{
                dichvu:[],
                dichvu_selected:null,
                loaihinh:[],
                loaihinh_filter:[],
                loaihinh_selected:null,
            },
            columnsBangGia:[
                {
                    fieldName: "stt",
                    headerText: "STT",
                    allowFiltering: true,
                    width:80
                },
                {
                    fieldName: "ten_bg",
                    headerText: "Tên bảng giá",
                    allowFiltering: true,
                    width:120
                },
                {
                    fieldName: "cuoc_tk",
                    headerText: "Cước TK",
                    allowFiltering: true,
                    type: "Number",
                    format: "N0",
                    width:100
                },
                {
                    fieldName: "vat_tk",
                    headerText: "Vat TK",
                    allowFiltering: true,
                    type: "Number",
                    format: "N0",
                    width:100
                },
                {
                    fieldName: "cuoc_tc",
                    headerText: "Cước TC",
                    allowFiltering: true,
                    type: "Number",
                    format: "N0",
                    width:100
                },
                {
                    fieldName: "vat_tc",
                    headerText: "Vat TC",
                    allowFiltering: true,
                    type: "Number",
                    format: "N0",
                    width:100
                },
                {
                    fieldName: "loai_cuoc",
                    headerText: "Loại cước",
                    allowFiltering: true,
                    width:100
                }
            ],
            dataSourcesBangGia:[],
            TYPE:{
                DOITUONG:1,
                LOAIHINH:2,
                TOCDO:3
            },
            columnsDoiTuong:[
                {
                    fieldName: "ten_dt",
                    headerText: "Đối tượng",
                    allowFiltering: true
                }
            ],
            dataSourcesDoiTuongGan:[],
            dataSourcesDoiTuongChuaGan:[],
            columnsLoaiKenh:[
                {
                    fieldName: "loai_kenh",
                    headerText: "Loại kênh",
                    allowFiltering: true
                }
            ],
            dataSourcesLoaiKenhGan:[],
            dataSourcesLoaiKenhChuaGan:[],
            columnsTocDo:[
                {
                    fieldName: "thuonghieu",
                    headerText: "Loại hình, tốc độ",
                    allowFiltering: true
                }
            ],
            dataSourcesTocDoGan:[],
            dataSourcesTocDoChuaGan:[],
            banggia_selected:null,
            cuoctk_id:0,
            dataCheckeds:{
                doituong_gan:[],
                doituong_chuagan:[],
                loaihinh_gan:[],
                loaihinh_chuagan:[],
                tocdo_gan:[],
                tocdo_chuagan:[]
            }
        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='nhapmoi'){
                this.setActiveActions(1)
                //hard
                this.hienthi_lhtb_tocdo_doituong_loaikenh(0,this.thongtin_banggia.loaihinh_selected)
                //this.hienthi_lhtb_tocdo_doituong_loaikenh(0,134)
                
            }else if(action.id=='ghilai'){
                this.ghiDulieu()
            }else if(action.id=='huy'){
                if(this.dataSourcesBangGia.length>0&&this.banggia_selected!=null){
                    this.onSelectedRowBangGia(this.banggia_selected)
                }else{
                    this.setActiveActions(0)
                }
                
            }else if(action.id=='xoa'){
                if(this.banggia_selected==null){
                    this.$toast.error('Hãy chọn bảng giá để thực hiện thao tác xoá!')
                    return
                }

                this.$confirm('Bạn thật sự muốn xóa dữ liệu không ?','Thông báo',{
                    confirmButtonText: 'Có',
                    cancelButtonText: 'Không'
                }).then(() => {
                    this.sp_banggia_delete_kc(this.banggia_selected.cuoctk_id)
                })
            }else if(action.id=='timkiem'){
                this.hienthi_ds_banggia_cuoc()
            }
        },
        setActiveActions(kieu){
            this.actions.forEach((item)=>{
                if(item.id!='timkiem'){
                    item.active=false
                }
            })
            if(kieu==-1){//bắt đầu
                this.actions[this.actions.findIndex(x=>x.id=='ghilai')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='huy')].active=true
                this.$refs.inputTenBangGia.focus()
            }else if(kieu==0){//bắt đầu
                this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active=true
                this.clear()
            }else if(kieu==1){//thêm mới
                this.actions[this.actions.findIndex(x=>x.id=='ghilai')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='huy')].active=true
                this.clear()
                this.$refs.inputTenBangGia.focus()
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
            this.thongtin_banggia.input_vat_tc='0'
            this.thongtin_banggia.input_vat_tk='0'
            this.thongtin_banggia.input_cuoc_tc='0'
            this.thongtin_banggia.input_cuoc_tk='0'
            this.thongtin_banggia.input_ten_banggia=''
            this.thongtin_banggia.tu_ngay=moment(new Date()).format('DD/MM/YYYY')
            this.thongtin_banggia.den_ngay=moment(new Date()).format('DD/MM/YYYY')
            this.banggia_selected=null
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
        //kieu 1 cuoctk,2 vattk, 3 cuoctc, 4 vattc
        //format currency out input
        handleBlur(event, kieu){
            //remove all ,
            let value=event.target.value.replace(/\,/g,'')
            if(kieu==1){
                this.thongtin_banggia.input_cuoc_tk=currency(value)
            }else if(kieu==2){
                this.thongtin_banggia.input_vat_tk=currency(value)
            }else if(kieu==3){
                this.thongtin_banggia.input_cuoc_tc=currency(value)
            }else if(kieu==4){
                this.thongtin_banggia.input_vat_tc=currency(value)
            }   
        },
        changeDichVuBangGia(){
            if(this.thongtin_banggia.dichvu_selected!=null){
                this.thongtin_banggia.loaihinh_filter=this.thongtin_banggia.loaihinh.filter(x=>x.DICHVUVT_ID==this.thongtin_banggia.dichvu_selected)
                if(this.thongtin_banggia.loaihinh_filter.length>0){
                    this.thongtin_banggia.loaihinh_selected=this.thongtin_banggia.loaihinh_filter[0].LOAITB_ID
                    this.changeLoaiHinh()
                    //this.hienthi_lhtb_tocdo_doituong_loaikenh(0,this.thongtin_banggia.loaihinh_selected)
                }else{
                    this.thongtin_banggia.loaihinh_selected=null
                }
            }else{
                this.thongtin_banggia.loaihinh_filter=[]
                this.thongtin_banggia.loaihinh_selected=null
            }   
        },
        changeLoaiHinh(){
            this.hienthi_lhtb_tocdo_doituong_loaikenh(0,this.thongtin_banggia.loaihinh_selected)
        },
        changeDichVuTimKiem(){
            if(this.thongtin_timkiem.dichvu_selected!=null){
                this.thongtin_timkiem.loaihinh_filter=this.thongtin_timkiem.loaihinh.filter(x=>x.DICHVUVT_ID==this.thongtin_timkiem.dichvu_selected)
                if(this.thongtin_timkiem.loaihinh_filter.length>0){
                    this.thongtin_timkiem.loaihinh_selected=this.thongtin_timkiem.loaihinh_filter[0].LOAITB_ID
                }else{
                    this.thongtin_timkiem.loaihinh_selected=null
                }
            }else{
                this.thongtin_timkiem.loaihinh_filter=[]
                this.thongtin_timkiem.loaihinh_selected=null
            }
        },
        outFocusInput(inputId){
            let element=document.getElementById(inputId)
            if(element){
                element.classList.remove('input_batbuoc')
            }
        },
        // gridDsChanged(data){
        //     if(this.dataSourcesBangGia.length<=0||data.length<=0){
        //         this.banggia_selected=null
        //         this.cuoctk_id=0
        //         return
        //     }
        //     this.onSelectedRow(data[0])
        // },

        async onSelectedRow(item){
            if(!item){
                this.banggia_selected=null
                this.cuoctk_id=0
                return
            }
            console.log('onSelectedRowBangGia', item)
            this.banggia_selected=item
            this.cuoctk_id=item.cuoctk_id?item.cuoctk_id:0
            //hiển thị thông tin lên
            this.thongtin_banggia.input_ten_banggia=item.ten_bg?item.ten_bg:''
            this.thongtin_banggia.input_cuoc_tc = item.cuoc_tc?item.cuoc_tc.toString():'0'
            this.thongtin_banggia.input_vat_tc = item.vat_tc?item.vat_tc.toString():'0'
            this.thongtin_banggia.input_cuoc_tk = item.cuoc_tk?item.cuoc_tk.toString():'0'
            this.thongtin_banggia.input_vat_tk = item.vat_tk?item.vat_tk.toString():'0'

            if(item.ngay_bd!=null&&item.ngay_bd.toString().trim()!=''){
                this.thongtin_banggia.tu_ngay=item.ngay_bd?moment(item.ngay_bd,'DD/MM/YYYY hh:mm:ss').format('DD/MM/YYYY'):''
                
                //this.thongtin_banggia.tu_ngay=item.ngay_bd?moment(item.ngay_bd, 'ddd MMM DD').format('DD/MM/YYYY'):''
            }
            if(item.ngay_kt!=null&&item.ngay_kt.toString().trim()!=''){
                this.thongtin_banggia.den_ngay=item.ngay_kt?moment(item.ngay_kt,'DD/MM/YYYY hh:mm:ss').format('DD/MM/YYYY'):''
                //this.thongtin_banggia.den_ngay=item.ngay_kt?moment(item.ngay_kt).format('DD/MM/YYYY'):''
            }
            if(item.kieu_id!=null&&item.kieu_id.toString().trim()!=''){
                this.thongtin_banggia.loaicuoc_selected=parseInt(item.kieu_id)
            }
            
            this.setActiveActions(3)

            await this.hienthi_lhtb_tocdo_doituong_loaikenh(item.cuoctk_id, this.thongtin_timkiem.loaihinh_selected)
        },
        dataCheckedsGan(data, type){
            if(type==this.TYPE.DOITUONG){
                this.dataCheckeds.doituong_gan=data
            }else if(type==this.TYPE.LOAIHINH){
                this.dataCheckeds.loaihinh_gan=data
            }else if(type==this.TYPE.TOCDO){
                this.dataCheckeds.tocdo_gan=data
            }
        },
        dataCheckedsChuaGan(data, type){
            if(type==this.TYPE.DOITUONG){
                this.dataCheckeds.doituong_chuagan=data
            }else if(type==this.TYPE.LOAIHINH){
                this.dataCheckeds.loaihinh_chuagan=data
            }else if(type==this.TYPE.TOCDO){
                this.dataCheckeds.tocdo_chuagan=data
            }
        },
        gan(type){
            if(type==this.TYPE.DOITUONG){
                let dataCheckedsDoiTuong=this.$refs.gridDoiTuongChuaGan.getSelectedRecords()
                if(dataCheckedsDoiTuong.length==0){
                    return
                }
                for(let i=0;i<dataCheckedsDoiTuong.length;i++){
                    this.dataSourcesDoiTuongGan.push(dataCheckedsDoiTuong[i])
                    const index=this.dataSourcesDoiTuongChuaGan.findIndex(x=>x.doituong_id==dataCheckedsDoiTuong[i].doituong_id)
                    if(index>-1){
                        this.dataSourcesDoiTuongChuaGan.splice(index,1)
                    }
                }
                this.dataSourcesDoiTuongGan=[].concat(this.dataSourcesDoiTuongGan)
                this.dataSourcesDoiTuongChuaGan=[].concat(this.dataSourcesDoiTuongChuaGan)

            }else if(type==this.TYPE.LOAIHINH){
                let dataCheckedsLoaiKenh=this.$refs.gridLoaiKenhChuaGan.getSelectedRecords()
                if(dataCheckedsLoaiKenh.length==0){
                    return
                }
                for(let i=0;i<dataCheckedsLoaiKenh.length;i++){
                    this.dataSourcesLoaiKenhGan.push(dataCheckedsLoaiKenh[i])
                    const index=this.dataSourcesLoaiKenhChuaGan.findIndex(x=>x.loaikenh_id==dataCheckedsLoaiKenh[i].loaikenh_id)
                    if(index>-1){
                        this.dataSourcesLoaiKenhChuaGan.splice(index,1)
                    }
                }
                this.dataSourcesLoaiKenhGan=[].concat(this.dataSourcesLoaiKenhGan)
                this.dataSourcesLoaiKenhChuaGan=[].concat(this.dataSourcesLoaiKenhChuaGan)

            }else if(type==this.TYPE.TOCDO){
                //gridTocDoChuaGan
                let dataCheckedsTocDo=this.$refs.gridTocDoChuaGan.getSelectedRecords()
                if(dataCheckedsTocDo.length==0){
                    return
                }
                for(let i=0;i<dataCheckedsTocDo.length;i++){
                    this.dataSourcesTocDoGan.push(dataCheckedsTocDo[i])
                    const index=this.dataSourcesTocDoChuaGan.findIndex(x=>x.tocdo_id==dataCheckedsTocDo[i].tocdo_id)
                    if(index>-1){
                        this.dataSourcesTocDoChuaGan.splice(index,1)
                    }
                }
                this.dataSourcesTocDoGan=[].concat(this.dataSourcesTocDoGan)
                this.dataSourcesTocDoChuaGan=[].concat(this.dataSourcesTocDoChuaGan)
                
            }
        },
        ganAll(type){
            if(type==this.TYPE.DOITUONG){
                if(this.dataSourcesDoiTuongChuaGan.length==0){
                    return
                }
                this.dataSourcesDoiTuongGan=this.dataSourcesDoiTuongGan.concat(this.dataSourcesDoiTuongChuaGan)
                this.dataSourcesDoiTuongChuaGan=[]
            }else if(type==this.TYPE.LOAIHINH){
                if(this.dataSourcesLoaiKenhChuaGan.length==0){
                    return
                }
                this.dataSourcesLoaiKenhGan=this.dataSourcesLoaiKenhGan.concat(this.dataSourcesLoaiKenhChuaGan)
                this.dataSourcesLoaiKenhChuaGan=[]
            }else if(type==this.TYPE.TOCDO){
                if(this.dataSourcesTocDoChuaGan.length==0){
                    return
                }
                this.dataSourcesTocDoGan=this.dataSourcesTocDoGan.concat(this.dataSourcesTocDoChuaGan)
                this.dataSourcesTocDoChuaGan=[]
            }
        },
        boGan(type){
            if(type==this.TYPE.DOITUONG){
                let dataCheckedsDoiTuong=this.$refs.gridDoiTuongGan.getSelectedRecords()
                if(dataCheckedsDoiTuong.length==0){
                    return
                }
                for(let i=0;i<dataCheckedsDoiTuong.length;i++){
                    this.dataSourcesDoiTuongChuaGan.push(dataCheckedsDoiTuong[i])
                    const index=this.dataSourcesDoiTuongGan.findIndex(x=>x.doituong_id==dataCheckedsDoiTuong[i].doituong_id)
                    if(index>-1){
                        this.dataSourcesDoiTuongGan.splice(index,1)
                    }
                }
                this.dataSourcesDoiTuongGan=[].concat(this.dataSourcesDoiTuongGan)
                this.dataSourcesDoiTuongChuaGan=[].concat(this.dataSourcesDoiTuongChuaGan)

            }else if(type==this.TYPE.LOAIHINH){
                let dataCheckedsLoaiKenh=this.$refs.gridLoaiKenhGan.getSelectedRecords()
                if(dataCheckedsLoaiKenh.length==0){
                    return
                }
                for(let i=0;i<dataCheckedsLoaiKenh.length;i++){
                    this.dataSourcesLoaiKenhChuaGan.push(dataCheckedsLoaiKenh[i])
                    const index=this.dataSourcesLoaiKenhGan.findIndex(x=>x.loaikenh_id==dataCheckedsLoaiKenh[i].loaikenh_id)
                    if(index>-1){
                        this.dataSourcesLoaiKenhGan.splice(index,1)
                    }
                }
                this.dataSourcesLoaiKenhGan=[].concat(this.dataSourcesLoaiKenhGan)
                this.dataSourcesLoaiKenhChuaGan=[].concat(this.dataSourcesLoaiKenhChuaGan)
            }else if(type==this.TYPE.TOCDO){
                let dataCheckedsTocDo=this.$refs.gridTocDoGan.getSelectedRecords()
                if(dataCheckedsTocDo.length==0){
                    return
                }
                for(let i=0;i<dataCheckedsTocDo.length;i++){
                    this.dataSourcesTocDoChuaGan.push(dataCheckedsTocDo[i])
                    const index=this.dataSourcesTocDoGan.findIndex(x=>x.tocdo_id==dataCheckedsTocDo[i].tocdo_id)
                    if(index>-1){
                        this.dataSourcesTocDoGan.splice(index,1)
                    }
                }
                this.dataSourcesTocDoGan=[].concat(this.dataSourcesTocDoGan)
                this.dataSourcesTocDoChuaGan=[].concat(this.dataSourcesTocDoChuaGan)
            }
        },
        boGanAll(type){
            if(type==this.TYPE.DOITUONG){
                if(this.dataSourcesDoiTuongGan.length==0){
                    return
                }
                this.dataSourcesDoiTuongChuaGan=this.dataSourcesDoiTuongChuaGan.concat(this.dataSourcesDoiTuongGan)
                this.dataSourcesDoiTuongGan=[]
            }else if(type==this.TYPE.LOAIHINH){
                if(this.dataSourcesLoaiKenhGan.length==0){
                    return
                }
                this.dataSourcesLoaiKenhChuaGan=this.dataSourcesLoaiKenhChuaGan.concat(this.dataSourcesLoaiKenhGan)
                this.dataSourcesLoaiKenhGan=[]
                
            }else if(type==this.TYPE.TOCDO){
                if(this.dataSourcesTocDoGan.length==0){
                    return
                }
                this.dataSourcesTocDoChuaGan=this.dataSourcesTocDoChuaGan.concat(this.dataSourcesTocDoGan)
                this.dataSourcesTocDoGan=[]
            }
        },
        ghiDulieu(){
            //kiểm tra dữ liệu
            if(!this.kiemtra_dulieu()){
                return
            }

            var is_insert=this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active==true?0:1
            if(is_insert==0&&this.banggia_selected==null){
                this.$toast.error('Hãy chọn bảng giá để cập nhật!')
                return
            }
            if(!this.thongtin_banggia.tu_ngay){
                this.$toast.error('Hãy nhập dữ liệu từ ngày!')
                return
            }
            if(!this.thongtin_banggia.den_ngay){
                this.$toast.error('Hãy nhập dữ liệu đến ngày!')
                return
            }
            
            let banggia_cuoc=this.get_banggia_cuoc(is_insert)
            
            let lhtb_tocdo=this.get_lhtb_tocdo()
            let doituong=this.get_doituong()
            let loaikenh=this.get_loaikenh()

            let data={
                IS_INSERT: is_insert, 
                BANGGIA_ID: is_insert==1?null:this.banggia_selected.cuoctk_id,
                BGCUOC_DATAS: banggia_cuoc,
                LHTB_DATAS: lhtb_tocdo,
                DOITUONG_DATAS: doituong,
                LOAIKENH_DATAS: loaikenh
            }
            console.log('data', data)
            this.sp_banggia_insert_or_update(data, is_insert)
        },
        get_banggia_cuoc(is_insert){

            let ngay_bd=moment(this.thongtin_banggia.tu_ngay,'DD/MM/YYYY').format('DD/MM/YYYY HH:mm:ss')
            let ngay_kt=moment(this.thongtin_banggia.den_ngay,'DD/MM/YYYY').format('DD/MM/YYYY HH:mm:ss')

            let result=[
                {
                    CUOCTK_ID: is_insert==1?null:this.banggia_selected.cuoctk_id,
                    TEN_BG: this.thongtin_banggia.input_ten_banggia,
                    VAT_TC: Number(this.thongtin_banggia.input_vat_tc.trim()),
                    CUOC_TC: Number(this.thongtin_banggia.input_cuoc_tc.trim()),
                    CUOC_TK: Number(this.thongtin_banggia.input_cuoc_tk.trim()),
                    VAT_TK: Number(this.thongtin_banggia.input_vat_tk.trim()),
                    NGAY_BD: ngay_bd,
                    NGAY_KT: ngay_kt,
                    KIEU_ID: this.thongtin_banggia.loaicuoc_selected,
                    NGAY_KT_VNP: null,
                    NGAY_BD_VNP: null,
                    TEN_BG_VNP: null
                }
            ]
            return result

        },
        get_lhtb_tocdo(){
            var result=[]
            this.dataSourcesTocDoGan.forEach((item)=>{
                result.push({
                    TOCDO_ID:item.tocdo_id,
                    LOAITB_ID:item.loaitb_id
                })
            })
            return result
        },
        get_doituong(){
            var result=[]
            this.dataSourcesDoiTuongGan.forEach((item)=>{
                result.push({
                    DOITUONG_ID:item.doituong_id
                })
            })
            return result
        },
        get_loaikenh(){
            var result=[]
            this.dataSourcesLoaiKenhGan.forEach((item)=>{
                result.push({
                    LOAIKENH_ID:item.loaikenh_id
                })
            })
            return result
        },
        kiemtra_dulieu(){
            if(this.thongtin_banggia.input_ten_banggia.trim()==''){
                this.$toast.error('Bạn chưa nhập tên bảng giá!')
                document.getElementById('inputTenBangGia').classList.add('input_batbuoc')
                this.$refs.inputTenBangGia.focus()
                return false
            }
            if(this.thongtin_banggia.input_cuoc_tc.trim()==''){
                this.$toast.error('Bạn chưa nhập cước thuê cổng!')
                return false
            }
            if(this.thongtin_banggia.input_vat_tc.trim()==''){
                this.$toast.error('Bạn chưa nhập Vat của cước thuê cổng!')
                return false
            }
            if(this.thongtin_banggia.input_cuoc_tk.trim()==''){
                this.$toast.error('Bạn chưa nhập cước thuê kênh !')
                return false
            }
            if(this.thongtin_banggia.input_vat_tk.trim()==''){
                this.$toast.error('Bạn chưa nhập Vat của cước thuê kênh !')
                return false
            }

            if(this.dataSourcesTocDoGan.length==0){
                this.$toast.error('Bạn phải phải chọn loại hình tốc độ cho bảng giá')
                return false
            }

            return true
        },
        async initDuLieu(){
            //lấy ds dịch vụ với id 7, 8, 9
            let ds_dichvu=await this.lay_danhsach_dichvu()
            if(ds_dichvu.length>0){
                this.thongtin_banggia.dichvu=ds_dichvu.filter((item)=>item.DICHVUVT_ID==7||item.DICHVUVT_ID==8||item.DICHVUVT_ID==9)
                this.thongtin_timkiem.dichvu=ds_dichvu.filter((item)=>item.DICHVUVT_ID==7||item.DICHVUVT_ID==8||item.DICHVUVT_ID==9)
                if(this.thongtin_banggia.dichvu.length>0&&this.thongtin_timkiem.dichvu.length>0){
                    this.thongtin_banggia.dichvu_selected=this.thongtin_banggia.dichvu[0].DICHVUVT_ID
                    this.thongtin_timkiem.dichvu_selected=this.thongtin_timkiem.dichvu[0].DICHVUVT_ID
                }
            }else{
                this.thongtin_banggia.dichvu=[]
                this.thongtin_timkiem.dichvu=[]
            }
            //lấy danh sách loại hình thuê bao
            let ds_loaihinh=await this.lay_danhsach_loaihinh_tb()
            if(ds_loaihinh.length>0){
                this.thongtin_banggia.loaihinh=ds_loaihinh.filter((item)=>item.DICHVUVT_ID==7||item.DICHVUVT_ID==8||item.DICHVUVT_ID==9)
                this.thongtin_timkiem.loaihinh=ds_loaihinh.filter((item)=>item.DICHVUVT_ID==7||item.DICHVUVT_ID==8||item.DICHVUVT_ID==9)
            }else{
                this.thongtin_banggia.loaihinh=[]
                this.thongtin_timkiem.loaihinh=[]
            }
            //filter loại hình thuê bao theo dịch vụ
            //bảng giá
            if(this.thongtin_banggia.dichvu_selected!=null){
                this.thongtin_banggia.loaihinh_filter=this.thongtin_banggia.loaihinh.filter(x=>x.DICHVUVT_ID==this.thongtin_banggia.dichvu_selected)
                if(this.thongtin_banggia.loaihinh_filter.length>0){
                    this.thongtin_banggia.loaihinh_selected=this.thongtin_banggia.loaihinh_filter[0].LOAITB_ID
                    await this.hienthi_lhtb_tocdo_doituong_loaikenh(0,this.thongtin_banggia.loaihinh_selected)
                }else{
                    this.thongtin_banggia.loaihinh_selected=null
                }
            }else{
                this.thongtin_banggia.loaihinh_filter=[]
                this.thongtin_banggia.loaihinh_selected=null
            }
            //tìm kiếm
            if(this.thongtin_timkiem.dichvu_selected!=null){
                this.thongtin_timkiem.loaihinh_filter=this.thongtin_timkiem.loaihinh.filter(x=>x.DICHVUVT_ID==this.thongtin_timkiem.dichvu_selected)
                if(this.thongtin_timkiem.loaihinh_filter.length>0){
                    this.thongtin_timkiem.loaihinh_selected=this.thongtin_timkiem.loaihinh_filter[0].LOAITB_ID
                }else{
                    this.thongtin_timkiem.loaihinh_selected=null
                }
            }else{
                this.thongtin_timkiem.loaihinh_filter=[]
                this.thongtin_timkiem.loaihinh_selected=null
            }
            //lấy danh sách loại cước
            this.thongtin_banggia.loaicuoc=await this.lay_danhsach_loai_cuoc()
            if(this.thongtin_banggia.loaicuoc.length>0){
                this.thongtin_banggia.loaicuoc_selected=this.thongtin_banggia.loaicuoc[0].kieu_id
            }
        },
        async lay_danhsach_dichvu(){
            try{
                this.loading(true)
                let response = await ChannelRentalPriceListAPI.lay_danhsach_dichvu(this.axios)
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
        async lay_danhsach_loaihinh_tb(){
            try{
                this.loading(true)
                let response = await ChannelRentalPriceListAPI.lay_danhsach_loaihinh_tb(this.axios)
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
        async lay_danhsach_loai_cuoc(){
            try{
                this.loading(true)
                let response = await ChannelRentalPriceListAPI.lay_danhsach_loai_cuoc(this.axios)
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
        async hienthi_ds_banggia_cuoc(){
            //this.thongtin_timkiem.loaihinh_selected
            let loaihinhtb_id=this.thongtin_timkiem.loaihinh_selected!=null?this.thongtin_timkiem.loaihinh_selected:0
            this.banggia_selected=null
            //clear data check
            this.dataCheckeds={
                doituong_gan:[],
                doituong_chuagan:[],
                loaihinh_gan:[],
                loaihinh_chuagan:[],
                tocdo_gan:[],
                tocdo_chuagan:[]
            }

            this.dataSourcesBangGia=await this.lay_danhsach_banggia_cuoc(loaihinhtb_id)
            if(this.dataSourcesBangGia.length<=0){
                await this.hienthi_lhtb_tocdo_doituong_loaikenh(0, loaihinhtb_id)
                this.setActiveActions(1)
            }

        },
        async lay_danhsach_banggia_cuoc(loaihinh_id){
            try{
                this.loading(true)
                let response = await ChannelRentalPriceListAPI.lay_danhsach_banggia_cuoc(this.axios, loaihinh_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data.map(x=>Object.assign(x, {
                        cuoc_tk:x.cuoc_tk?Number(x.cuoc_tk):0,
                        vat_tk:x.vat_tk?Number(x.vat_tk):0,
                        cuoc_tc:x.cuoc_tc?Number(x.cuoc_tc):0,
                        vat_tc:x.vat_tc?Number(x.vat_tc):0
                    }))
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async hienthi_lhtb_tocdo_doituong_loaikenh(banggia_id, loaihinh_tb){
            //Tốc độ
           // this.dataSourcesTocDoChuaGan=[]
            this.dataSourcesTocDoChuaGan = await this.lay_danhsach_loaihinh_tb_tocdo_cuoctk_tc(banggia_id, loaihinh_tb,0)
           // this.dataSourcesTocDoGan=[]
            this.dataSourcesTocDoGan = await this.lay_danhsach_loaihinh_tb_tocdo_cuoctk_tc(banggia_id, loaihinh_tb,1)

            //Loại hình
           // this.dataSourcesLoaiKenhChuaGan=[]
            this.dataSourcesLoaiKenhChuaGan = await this.lay_danhsach_loaikenh_cuoctk_tc(banggia_id, 0)
            //this.dataSourcesLoaiKenhGan=[]
            this.dataSourcesLoaiKenhGan = await this.lay_danhsach_loaikenh_cuoctk_tc(banggia_id, 1)

            //Đối tượng
            //this.dataSourcesDoiTuongChuaGan=[]
            this.dataSourcesDoiTuongChuaGan = await this.lay_danhsach_doituongkh_cuoctk_tc(banggia_id, 0)
           // this.dataSourcesDoiTuongGan=[]
            this.dataSourcesDoiTuongGan = await this.lay_danhsach_doituongkh_cuoctk_tc(banggia_id, 1)
            
        },
        async lay_danhsach_loaihinh_tb_tocdo_cuoctk_tc(banggia_id, loaitb_id, kieu){
            try{
                this.loading(true)
                let response = await ChannelRentalPriceListAPI.lay_danhsach_loaihinh_tb_tocdo_cuoctk_tc(this.axios, banggia_id, loaitb_id, kieu)
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
        async lay_danhsach_loaikenh_cuoctk_tc(banggia_id, kieu){
            try{
                this.loading(true)
                let response = await ChannelRentalPriceListAPI.lay_danhsach_loaikenh_cuoctk_tc(this.axios, banggia_id, kieu)
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
        async lay_danhsach_doituongkh_cuoctk_tc(banggia_id, kieu){
            try{
                this.loading(true)
                let response = await ChannelRentalPriceListAPI.lay_danhsach_doituongkh_cuoctk_tc(this.axios, banggia_id, kieu)
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
        async sp_banggia_delete_kc(banggia_id){
            try{
                this.loading(true)
                let response = await ChannelRentalPriceListAPI.sp_banggia_delete_kc(this.axios, banggia_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    this.$toast.success('Xoá bảng giá thành công')
                    await this.hienthi_ds_banggia_cuoc()
                }else{
                    if(response&&response.data&&response.data.message){
                        this.$toast.error(response.data.message)
                    }else{
                        this.$toast.error('Đã xảy ra lỗi, xoá không thành công!')
                    }
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                     this.$toast.error('Đã xảy ra lỗi, xoá không thành công!')
                }
            }
        },
        //kieu=1: thêm mới, 0 update
        async sp_banggia_insert_or_update(data, kieu){
            try{
                this.loading(true)
                let response = await ChannelRentalPriceListAPI.sp_banggia_insert_or_update(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    this.$toast.success(kieu==1?'Thêm mới dữ liệu thành công !':'Cập nhật dữ liệu thành công !')
                    await this.hienthi_ds_banggia_cuoc()
                }else{
                    if(response&&response.data&&response.data.message){
                        this.$toast.error(response.data.message)
                    }else{
                        this.$toast.error(kieu==1?'Đã xảy ra lỗi, thêm dữ liệu không thành công!':'Đã xảy ra lỗi cập nhật dữ liệu không thành công')
                    }
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error(kieu==1?'Đã xảy ra lỗi, thêm dữ liệu không thành công!':'Đã xảy ra lỗi cập nhật dữ liệu không thành công')
                }
            }
        }
    },
    mounted(){
        this.setActiveActions(-1)
        setTimeout(()=>{
            this.initDuLieu()
        },500)
    }
}
</script>
<style>
.select2-selection { overflow: hidden !important; }
.select2-container .select2-selection--single .select2-selection__rendered {
  white-space: normal !important;
  word-break: break-all !important;
}
.input_batbuoc:focus {
    border-color:red;
    background-color: lightcoral;
    box-shadow: none;
}
</style>