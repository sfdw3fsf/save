<template>
    <div class="main-wrapper">
        <breadcrumb :header="header" />

        <ActionTop :actions="actions" @onActionClick="onActionClick"/>

        <div class="page-content">
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="box-form">
                        <div class="legend-title">Thông tin dịch vụ gia tăng</div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key">Mã DVGT <k-required-marker/></div>
                                    <div class="value">
                                        <input type="text" id="inputMaDVGT" ref="inputMaDVGT" @blur="outFocusInput('inputMaDVGT')" :value="thongtin_dichvugt.input_ma_dvgt" @change="e=>thongtin_dichvugt.input_ma_dvgt=e.target.value" class="form-control">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key">Nhóm <k-required-marker/></div>
                                    <div class="value">
                                        <input type="text" id="inputNhom" ref="inputNhom" @blur="outFocusInput('inputNhom')" :value="thongtin_dichvugt.input_nhom" @change="e=>thongtin_dichvugt.input_nhom=e.target.value" class="form-control">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key">Tên DVGT <k-required-marker/></div>
                                    <div class="value">
                                        <input type="text" id="inputTenDVGT" ref="inputTenDVGT" @blur="outFocusInput('inputTenDVGT')" :value="thongtin_dichvugt.input_ten_dvgt" @change="e=>thongtin_dichvugt.input_ten_dvgt=e.target.value"  class="form-control">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key">Mức <k-required-marker/></div>
                                    <div class="value">
                                        <input type="text" id="inputMuc" ref="inputMuc" @blur="outFocusInput('inputMuc')" :value="thongtin_dichvugt.input_muc" @change="e=>thongtin_dichvugt.input_muc=e.target.value" class="form-control">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key">Trạng thái TB <k-required-marker/></div>
                                    <div class="value">
                                        <div class="select-custom">
                                            <select v-model="thongtin_dichvugt.trangthai_tb_selected"  class="form-control">
                                                <option v-for="item in thongtin_dichvugt.trangthai_tb" :key="item.TRANGTHAITB_ID" :value="item.TRANGTHAITB_ID">{{item.TRANGTHAI_TB}}</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key">Kiểu <k-required-marker/></div>
                                    <div class="value">
                                        <div class="select-custom">
                                            <select v-model="thongtin_dichvugt.kieu_dvgt_selected"  class="form-control">
                                                <option v-for="item in thongtin_dichvugt.kieu_dvgt" :key="item.kieu_id" :value="item.kieu_id">{{item.kieu}}</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key">Cước lắp đặt <k-required-marker/></div>
                                    <div class="value">
                                        <vue-numeric class="form-control" ref="inputCuocLD" @blur="outFocusInput('inputCuocLD')" separator="," :value="thongtin_dichvugt.input_cuoc_ld" @change="e=>thongtin_dichvugt.input_cuoc_ld=e.target.value" placeholder="0" output-type="Number"></vue-numeric>
                                        <!-- <input type="text" id="inputCuocLD" ref="inputCuocLD" @blur="outFocusInput('inputCuocLD')"  @keypress="isNumber($event)" :value="thongtin_dichvugt.input_cuoc_ld" @change="e=>thongtin_dichvugt.input_cuoc_ld=e.target.value" class="form-control"> -->
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key">VAT lắp đặt <k-required-marker/></div>
                                    <div class="value">
                                        <vue-numeric class="form-control" ref="inputVatLD" separator="," :value="thongtin_dichvugt.input_vat_lapdat" @change="e=>thongtin_dichvugt.input_vat_lapdat=e.target.value" placeholder="0" output-type="Number"></vue-numeric>
                                        <!-- <input type="text" id="inputVatLD" ref="inputVatLD" @blur="outFocusInput('inputVatLD')"  @keypress="isNumber($event)" :value="thongtin_dichvugt.input_vat_lapdat" @change="e=>thongtin_dichvugt.input_vat_lapdat=e.target.value" class="form-control"> -->
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key">Cước sử dụng <k-required-marker/></div>
                                    <div class="value">
                                        <vue-numeric class="form-control" ref="inputCuocSD" separator="," :value="thongtin_dichvugt.input_cuoc_sd" @change="e=>thongtin_dichvugt.input_cuoc_sd=e.target.value" placeholder="0" output-type="Number"></vue-numeric>
                                        <!-- <input type="text" id="inputCuocSD" ref="inputCuocSD" @blur="outFocusInput('inputCuocSD')" @keypress="isNumber($event)" :value="thongtin_dichvugt.input_cuoc_sd" @change="e=>thongtin_dichvugt.input_cuoc_sd=e.target.value" class="form-control"> -->
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key">VAT sử dụng <k-required-marker/></div>
                                    <div class="value">
                                        <vue-numeric class="form-control" ref="inputVatSD" separator="," :value="thongtin_dichvugt.input_vat_sudung" @change="e=>thongtin_dichvugt.input_vat_sudung=e.target.value" placeholder="0" output-type="Number"></vue-numeric>
                                        <!-- <input type="text" id="inputVatSD" ref="inputVatSD" @blur="outFocusInput('inputVatSD')"  @keypress="isNumber($event)" :value="thongtin_dichvugt.input_vat_sudung" @change="e=>thongtin_dichvugt.input_vat_sudung=e.target.value" class="form-control"> -->
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key">Mức cước <k-required-marker/></div>
                                    <div class="value">
                                        <input type="text" id="inputMucCuoc" ref="inputMucCuoc" @blur="outFocusInput('inputMucCuoc')" @keypress="isNumber($event)" :value="thongtin_dichvugt.input_muccuoc" @change="e=>thongtin_dichvugt.input_muccuoc=e.target.value" class="form-control">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key">Cài đặt mặc định <k-required-marker/></div>
                                    <div class="value">
                                        <input type="text" id="inputCaiDatMD" ref="inputCaiDatMD" @blur="outFocusInput('inputCaiDatMD')" :value="thongtin_dichvugt.input_caidat_macdinh" @change="e=>thongtin_dichvugt.input_caidat_macdinh=e.target.value" @keypress="isNumber($event)" class="form-control">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key">Ghi chú</div>
                            <div class="value">
                                <textarea ref="inputGhiChu" :value="thongtin_dichvugt.input_ghichu" @change="e=>thongtin_dichvugt.input_ghichu=e.target.value" class="form-control" style="height: 100px;resize: none;"></textarea>
                            </div>
                            <div class="value w30 nowrap">
                                <div class="check-action mart20">
                                    <input type="checkbox" v-model="thongtin_dichvugt.checkbox_soluong"  class="check">
                                    <span class="name">Số lượng</span>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key">Hình thức TC</div>
                                    <div class="value">
                                        <div class="select-custom">
                                            <select v-model="thongtin_dichvugt.hinhthuc_tc_selected"  class="form-control">
                                                <option v-for="item in thongtin_dichvugt.hinhthuc_tc" :key="item.HTTC_ID" :value="item.HTTC_ID">{{item.HINHTHUC_TC}}</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key">Hệ số Block <k-required-marker/></div>
                                    <div class="value">
                                        <input type="text" id="inputHeSoBlock" ref="inputHeSoBlock" @blur="outFocusInput('inputHeSoBlock')" :value="thongtin_dichvugt.input_heso_block" @change="e=>thongtin_dichvugt.input_heso_block=e.target.value" class="form-control">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key">Block đầu <k-required-marker/></div>
                                    <div class="value">
                                        <input type="text" id="inputBlockDau" ref="inputBlockDau" @blur="outFocusInput('inputBlockDau')" @keypress="isNumber($event)" :value="thongtin_dichvugt.input_block_dau" @change="e=>thongtin_dichvugt.input_block_dau=e.target.value" class="form-control">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key">Giá block tiếp <k-required-marker/></div>
                                    <div class="value">
                                        <input type="text" id="inputGiaBlockTiep" ref="inputGiaBlockTiep" @blur="outFocusInput('inputGiaBlockTiep')"  @keypress="isNumber($event)" :value="thongtin_dichvugt.input_gia_block_tiep" @change="e=>thongtin_dichvugt.input_gia_block_tiep=e.target.value" class="form-control">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key">Block tiếp <k-required-marker/></div>
                                    <div class="value">
                                        <input type="text" id="inputBlockTiep" ref="inputBlockTiep" @blur="outFocusInput('inputBlockTiep')" @keypress="isNumber($event)" :value="thongtin_dichvugt.input_block_tiep" @change="e=>thongtin_dichvugt.input_block_tiep=e.target.value" class="form-control">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key">VAT block tiếp <k-required-marker/></div>
                                    <div class="value">
                                        <input type="text" id="inputVatBlockTiep" ref="inputVatBlockTiep" @blur="outFocusInput('inputVatBlockTiep')" @keypress="isNumber($event)" :value="thongtin_dichvugt.input_vat_block_tiep" @change="e=>thongtin_dichvugt.input_vat_block_tiep=e.target.value" class="form-control">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key">
                                <div class="key">Khoản mục TT<k-required-marker/></div>
                            </div>
                            <div class="value">
                                <div class="select-custom" ref="ds_khoanmuc_tt">
                                    <select2 id="cboKhoanMucTT" :settings="{ dropdownParent: $refs['ds_khoanmuc_tt'] }" ref="cboKhoanMucTT"
                                        v-model="thongtin_dichvugt.khoanmuctt_selected" class="select2"
                                        :options="thongtin_dichvugt.ds_khoanmuc_tt.map(e=> ({id: e.khoanmuctt_id, text: e.ten_kmtt}))"
                                        >
                                    </select2>
                                </div>  
                            </div>
                        </div>
                        <div class="legend-title mart20">Thông tin tìm kiếm</div>
                        <div class="info-row">
                            <div class="key">
                                <div class="check-action">
                                    <input type="checkbox" @change="changeCheckDichVuVT"  v-model="thongtin_timkiem.checkbox_dichvu" class="check">
                                    <span class="name">Dịch vụ</span>
                                </div>
                            </div>
                            <div class="value">
                                <div class="select-custom" ref="ds_dichvu_tk">
                                    <select2 :settings="{ dropdownParent: $refs['ds_dichvu_tk'] }" ref="cboDichVuTK"
                                        v-model="thongtin_timkiem.dichvu_vt_selected" class="select2"
                                        :options="thongtin_timkiem.dichvu_vt.map(e=> ({id: e.DICHVUVT_ID, text: e.TEN_DVVT}))"
                                        :disabled="!thongtin_timkiem.checkbox_dichvu"
                                        @change="changeDichVuVT_TK"
                                        >
                                    </select2>
                                </div>  
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="box-form">
                        <div class="legend-title">Thông tin loại hình</div>
                        <div class="info-row">
                            <div class="key w70">
                                Dịch vụ
                            </div>
                            <div class="value">
                                <div class="select-custom" ref="dichvu_vt">
                                    <select2 :settings="{ dropdownParent: $refs['dichvu_vt'] }" ref="cboDichVu"
                                        v-model="thongtin_loaihinh.dichvu_vt_selected" class="select2"
                                        :options="thongtin_loaihinh.dichvu_vt.map(e=> ({id: e.DICHVUVT_ID, text: e.TEN_DVVT}))"
                                        @change="changeDichVu"
                                        >
                                    </select2>
                                </div>  
                            </div>
                        </div>
                        <div class="box-move-select-table">
                            <div class="box-col">
                                <div class="legend-title mart20">Loại hình đã gán</div>
                                <KDataGrid
                                    :columns="columnsGan"
                                    :dataSource="dataSourcesGan"
                                    :showColumnCheckbox="true"
                                    :enable-paging-server="false"
                                    :allowPaging="false"
                                    :showFilter="true"
                                    ref="gridDaGan"
                                    :enabledSelectFirstRow="false"
                                    panelDataHeight="360px"/>
                            </div>
                            <div class="actions">
                                <button class="btn" @click="boGan">
                                    <span class="fa fa-angle-right"></span>
                                </button>
                                <button class="btn" @click="boGanAll">
                                    <span class="fa fa-angle-double-right"></span>
                                </button>
                                
                                <button class="btn" @click="gan">
                                    <span class="fa fa-angle-left"></span>
                                </button>
                                <button class="btn" @click="ganAll">
                                    <span class="fa fa-angle-double-left"></span>
                                </button>
                                
                            </div>
                            <div class="box-col">
                                <div class="legend-title mart20">Loại hình chưa gán</div>
                                <KDataGrid
                                    :columns="columnsChuaGan"
                                    :dataSource="dataSourcesChuaGan"
                                    :showColumnCheckbox="true"
                                    :enable-paging-server="false"
                                    :allowPaging="false"
                                    :showFilter="true"
                                    ref="gridChuaGan"
                                    :enabledSelectFirstRow="false"
                                    panelDataHeight="360px"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="box-form">
                <div class="legend-title">Danh sách dịch vụ gia tăng</div>
                <KDataGrid
                    ref="gridDs"
                    :columns="columnsDVGT"
                    :dataSource="dataSourcesDVGT"
                    :showColumnCheckbox="false"
                    :enable-paging-server="false"
                    :allowPaging="true"
                    :showFilter="true"
                    :enabledSelectFirstRow="true"
                    @onRowSelected="onSelectedRow"
                    @rowSelected="rowGridSelected"/>
            </div>
        </div>

        <Footer/>
        
    </div>
</template>
<script>
import Footer from '@/components/FooterStyle.vue'
import breadcrumb from '@/components/kylq_components/breadcrumb.vue'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
//JS
import '@/assets/vendor/jquery/split'
import VASInfoAPI from './VASInfoAPI'
import KRequiredMarker from '@/components/kylq_components/KRequiredMarker.vue'
import KDataGrid from '@/components/kylq_components/KDataGrid'
//WinUI.WinUIBangTuDien.frmDichVuGT.cs
export default {
    name:'VASInfo',
    components:{
        Footer,
        breadcrumb,
        ActionTop,
        KRequiredMarker,
        KDataGrid
    },
    data(){
        return {
            header: {
                title: "Dịch vụ gia tăng",
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
                        name: "Bảng giá - Khuyến mại - Gói cước",
                        link: { name: "Ui.buttons" },
                        active: false
                    },
                    {
                        name: "Dịch vụ gia tăng",
                        link: { name: "Ui.buttons" },
                        active: true
                    },
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
            ],
            columnsDVGT:[
                {
                    fieldName:'ma_dvgt',
                    headerText:'Mã DVGT',
                    allowFiltering:true,
                    allowSorting:true
                },
                {
                    fieldName:'ten_dvgt',
                    headerText:'Dịch vụ gia tăng',
                    allowFiltering:true,
                    allowSorting:true
                },
                 {
                    fieldName:'cuoc_ld',
                    headerText:'Cước LĐ',
                    allowFiltering:true,
                    allowSorting:true,
                    type: "Number",
                    format: "N0"

                },
                {
                    fieldName:'vat_ld',
                    headerText:'Vat LĐ',
                    allowFiltering:true,
                    allowSorting:true,
                    type: "Number",
                    format: "N0"
                },
                {
                    fieldName:'cuoc_sd',
                    headerText:'Cước SD',
                    allowFiltering:true,
                    allowSorting:true,
                    type: "Number",
                    format: "N0"
                },
                {
                    fieldName:'vat_sd',
                    headerText:'Vat SD',
                    allowFiltering:true,
                    allowSorting:true,
                    type: "Number",
                    format: "N0"
                }
            ],
            dataSourcesDVGT:[],
            columnsGan:[
                {
                    fieldName: "loaihinh_tb",
                    headerText: "Loại hình",
                    allowFiltering: true,
                    allowSorting:true
                },
            ],
            dataSourcesGan:[],
            columnsChuaGan:[
                {
                    fieldName: "loaihinh_tb",
                    headerText: "Loại hình",
                    allowFiltering: true,
                    allowSorting:true
                },
            ],
            dataSourcesChuaGan:[],
            thongtin_dichvugt:{
                //max size:10
                input_ma_dvgt:'',
                input_ten_dvgt:'',
                trangthai_tb:[],
                trangthai_tb_selected:null,
                input_cuoc_ld:'0',
                input_cuoc_sd:'0',
                input_muccuoc:'0',
                input_ghichu:'',
                hinhthuc_tc:[],
                hinhthuc_tc_selected:null,
                input_block_dau:'',
                input_block_tiep:'',
                input_nhom:'0',
                input_muc:'',
                input_kieu:'',
                kieu_dvgt:[],
                kieu_dvgt_selected:null,
                input_vat_lapdat:'0',
                input_vat_sudung:'0',
                input_caidat_macdinh:'0',
                checkbox_soluong:false,
                input_heso_block:'',
                input_gia_block_tiep:'',
                input_vat_block_tiep:'',
                khoanmuctt_selected:-1,
                ds_khoanmuc_tt:[]
            },
            thongtin_timkiem:{
                checkbox_dichvu:false,
                dichvu_vt:[],
                dichvu_vt_selected:null,
            },
            thongtin_loaihinh:{
                dichvu_vt:[],
                dichvu_vt_selected:null,
            },
            dichvugt_id:0,
            selectIndex:0
        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='nhapmoi'){
                this.setActiveActions(1)
            }else if(action.id=='ghilai'){
                this.ghiDuLieu()
            }else if(action.id=='huy'){
                if(this.dichvugt_id!=0&&this.dataSourcesDVGT.length>0){
                    const index=this.dataSourcesDVGT.findIndex(x=>x.dichvugt_id==this.dichvugt_id)
                    if(index>-1){
                        this.onSelectedRow(this.dataSourcesDVGT[index])
                    }
                }else{
                    this.setActiveActions(0)
                }
                
            }else if(action.id=='xoa'){
                if(this.dichvugt_id==0){
                    this.$toast.error('Bạn chưa chọn dịch vụ gia tăng!')
                    return
                }
                if(this.dataSourcesDVGT.length==0){
                    this.$toast.error('Không có dữ liệu để xoá')
                    return
                }
                this.$confirm('Bạn thật sự muốn xóa dữ liệu không ?','Thông báo',{
                    confirmButtonText: 'Đồng ý',
                    cancelButtonText: 'Huỷ'
                }).then(() => {
                    this.dvgt_delete(this.dichvugt_id)
                })
            }
        },
        setActiveActions(kieu){
            this.actions.forEach((item)=>{
                item.active=false
            })
            if(kieu==-1){//bắt đầu
                this.actions[this.actions.findIndex(x=>x.id=='ghilai')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='huy')].active=true
            }else if(kieu==0){//bắt đầu
                this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active=true
                this.clear()
            }else if(kieu==1){//thêm mới
                this.actions[this.actions.findIndex(x=>x.id=='ghilai')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='huy')].active=true
                this.$refs.inputMaDVGT.focus()
                this.clear()

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
            this.thongtin_dichvugt.input_ma_dvgt=''
            this.thongtin_dichvugt.input_ten_dvgt=''
            this.thongtin_dichvugt.input_nhom='0'
            this.thongtin_dichvugt.input_muccuoc='0'
            this.thongtin_dichvugt.input_cuoc_ld='0'
            this.thongtin_dichvugt.input_vat_lapdat='0'
            this.thongtin_dichvugt.input_cuoc_sd='0'
            this.thongtin_dichvugt.input_vat_sudung='0'
            this.thongtin_dichvugt.input_caidat_macdinh='0'
            this.thongtin_dichvugt.input_ghichu=''
            this.selectIndex=0
            this.thongtin_dichvugt.khoanmuctt_selected=-1
        },
        async onSelectedRow(data){
            if(!data){
                this.clear()
                return
            }
            let item=Array.isArray(data)?data[0]:data

            this.dichvugt_id=item.dichvugt_id

            this.thongtin_dichvugt.input_ma_dvgt=item.ma_dvgt?item.ma_dvgt.toString():''
            this.thongtin_dichvugt.input_ten_dvgt=item.ten_dvgt?item.ten_dvgt.toString():''
            this.thongtin_dichvugt.input_nhom=item.nhom?item.nhom.toString():'0'
            this.thongtin_dichvugt.input_muccuoc=item.muccuoc?item.muccuoc.toString():'0'
            this.thongtin_dichvugt.input_cuoc_ld=item.cuoc_ld?item.cuoc_ld.toString():'0'
            this.thongtin_dichvugt.input_vat_lapdat=item.vat_ld?item.vat_ld.toString():'0'
            this.thongtin_dichvugt.input_cuoc_sd=item.cuoc_sd?item.cuoc_sd.toString():'0'
            this.thongtin_dichvugt.input_vat_sudung=item.vat_sd?item.vat_sd.toString():'0'
            this.thongtin_dichvugt.input_caidat_macdinh=item.caidat_md?item.caidat_md.toString():'0'
            this.thongtin_dichvugt.input_muc=item.muc_id!=null?item.muc_id.toString():''

            this.thongtin_dichvugt.kieu_dvgt_selected=item.kieu_id
            this.thongtin_dichvugt.trangthai_tb_selected=item.trangthaitb_id

            if(item.httc_id){
                this.thongtin_dichvugt.hinhthuc_tc_selected=item.httc_id
            }else{
                this.thongtin_dichvugt.hinhthuc_tc_selected=0
            }
            this.thongtin_dichvugt.input_ghichu=item.ghichu!=null?item.ghichu.toString():''

            if(item.soluong&&item.soluong==1){
                this.thongtin_dichvugt.checkbox_soluong=true
            }else{
                this.thongtin_dichvugt.checkbox_soluong=false
            }

            this.thongtin_dichvugt.input_block_dau=item.block_dau?item.block_dau.toString():'0'
            this.thongtin_dichvugt.input_block_tiep=item.block_tiep?item.block_tiep.toString():'0'
            this.thongtin_dichvugt.input_gia_block_tiep=item.gia_block_tiep?item.gia_block_tiep.toString():'0'
            this.thongtin_dichvugt.input_vat_block_tiep=item.vat_block_tiep?item.vat_block_tiep.toString():'0'
            this.thongtin_dichvugt.input_heso_block=item.he_so?item.he_so.toString():'0'

            
            //hiển thị loại hình gán/ chưa gán
            await this.hienthi_loaihinh_tb()
            //https://cntt.vnpt.vn/browse/BSS-87026
            if(this.dataSourcesGan.length>0){
                if(this.dataSourcesGan[0].khoanmuctt_id){
                    this.thongtin_dichvugt.khoanmuctt_selected=this.dataSourcesGan[0].khoanmuctt_id
                }else{
                    this.thongtin_dichvugt.khoanmuctt_selected=-1
                }
            }

            this.setActiveActions(3)
        },
        rowGridSelected(args){
            this.selectIndex=args.rowIndex
            console.log('selectIndex', this.selectIndex)
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
        changeDichVuVT_TK(){
            this.hienthi_danhsach_dvgt(this.thongtin_timkiem.dichvu_vt_selected!=null?this.thongtin_timkiem.dichvu_vt_selected:0)
        },
        changeDichVu(){
            this.hienthi_loaihinh_tb()
        },
        changeCheckDichVuVT(){
            if(this.thongtin_timkiem.checkbox_dichvu){
                this.hienthi_danhsach_dvgt(this.thongtin_timkiem.dichvu_vt_selected!=null?this.thongtin_timkiem.dichvu_vt_selected:0)
            }else{
                this.hienthi_danhsach_dvgt(0)
            }
        },
        gan(){
            let dataCheckedsLHChuaGan=this.$refs.gridChuaGan.getSelectedRecords()
            if(dataCheckedsLHChuaGan.length==0){
                return
            }
            for(let i=0;i<dataCheckedsLHChuaGan.length;i++){
                this.dataSourcesGan.push(dataCheckedsLHChuaGan[i])
                const index=this.dataSourcesChuaGan.findIndex(x=>x.loaitb_id==dataCheckedsLHChuaGan[i].loaitb_id)
                if(index>-1){
                    this.dataSourcesChuaGan.splice(index,1)
                }
            }
            this.dataSourcesGan=[].concat(this.dataSourcesGan)
            this.dataSourcesChuaGan=[].concat(this.dataSourcesChuaGan)
        },
        ganAll(){
            if(this.dataSourcesChuaGan.length==0){
                return
            }
            this.dataSourcesGan=this.dataSourcesGan.concat(this.dataSourcesChuaGan)
            this.dataSourcesChuaGan=[]
        },
        boGan(){
            let dataCheckedsLHGan=this.$refs.gridDaGan.getSelectedRecords()
            if(dataCheckedsLHGan.length==0){
                return
            }
            for(let i=0;i<dataCheckedsLHGan.length;i++){
                this.dataSourcesChuaGan.push(dataCheckedsLHGan[i])
                const index=this.dataSourcesGan.findIndex(x=>x.loaitb_id==dataCheckedsLHGan[i].loaitb_id)
                if(index>-1){
                    this.dataSourcesGan.splice(index,1)
                }
            }
            this.dataSourcesGan=[].concat(this.dataSourcesGan)
            this.dataSourcesChuaGan=[].concat(this.dataSourcesChuaGan)
        },
        boGanAll(){
            if(this.dataSourcesGan.length==0){
                return
            }
            this.dataSourcesChuaGan=this.dataSourcesChuaGan.concat(this.dataSourcesGan)
            this.dataSourcesGan=[]
        },
        outFocusInput(inputId){
            // let element=document.getElementById(inputId)
            // if(element){
            //     element.classList.remove('input_batbuoc')
            // }
        },
        async ghiDuLieu(){
            var is_insert=this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active==true?1:0
            //check dữ liệu
            if(!this.kiemtra_dulieu_dauvao()){
                return
            }
            //check tạo key
            if(is_insert==0){
                this.dichvugt_id=await this.get_keys('DICHVU_GT')
            }

            //tạo dữ liệu
            let dvgt=await this.getDuLieuDVGT()
            let lhtb_data= await this.getLHTB()

            let result = await this.dvgt_update(this.dichvugt_id, is_insert, JSON.stringify(dvgt), JSON.stringify(lhtb_data))
            if(result=='1'){
                this.$toast.success(is_insert==0?'Thêm mới dữ liệu thành công !':'Cập nhật dữ liệu thành công !')
                this.$refs.gridDs.flagSelectedRowIndexes=[this.selectIndex]
                await this.hienthi_danhsach_dvgt()
            }else{
                this.$toast.error(result)
            }
        },
        kiemtra_dulieu_dauvao(){
            if(this.thongtin_dichvugt.input_ma_dvgt.toString().trim()==''){
                this.$toast.error('Bạn chưa nhập mã dịch vụ gia tăng!')
                //document.getElementById('inputMaDVGT').classList.add('input_batbuoc')
                this.$refs.inputMaDVGT.focus()
                return false
            }
            if(this.thongtin_dichvugt.input_ma_dvgt.toString().trim().length>30){
                this.$toast.error('Mã dịch vụ gia tăng không được lớn hơn 30 ký tự!')
                //document.getElementById('inputMaDVGT').classList.add('input_batbuoc')
                this.$refs.inputMaDVGT.focus()
                return false
            }
            if(this.thongtin_dichvugt.input_ten_dvgt.toString().trim()==''){
                this.$toast.error('Bạn chưa nhập tên dịch vụ gia tăng!')
                //document.getElementById('inputTenDVGT').classList.add('input_batbuoc')
                this.$refs.inputTenDVGT.focus()
                return false
            }
            if(this.thongtin_dichvugt.input_ten_dvgt.toString().trim().length>300){
                this.$toast.error('Tên dịch vụ gia tăng không được lớn hơn 300 ký tự!')
                //document.getElementById('inputTenDVGT').classList.add('input_batbuoc')
                this.$refs.inputTenDVGT.focus()
                return false
            }
            if(this.thongtin_dichvugt.input_nhom.toString().trim().length>3){
                this.$toast.error('Độ dài nhóm không lớn quá 3!')
                //document.getElementById('inputNhom').classList.add('input_batbuoc')
                this.$refs.inputNhom.focus()
                return false
            }
            if(this.thongtin_dichvugt.input_muccuoc.toString().trim()==''){
                this.$toast.error('Bạn chưa nhập mức cước!')
                //document.getElementById('inputMucCuoc').classList.add('input_batbuoc')
                this.$refs.inputMucCuoc.focus()
                return false
            }
            if(!this.isNumeric(this.thongtin_dichvugt.input_muccuoc.toString().trim())){
                this.$toast.error('Mức cước phải là kiểu số!')
                //document.getElementById('inputMucCuoc').classList.add('input_batbuoc')
                this.$refs.inputMucCuoc.focus()
                return false
            }
            if(this.thongtin_dichvugt.input_muccuoc.toString().trim().length>2){
                this.$toast.error('Mức cước không được lớn hơn 2 ký tự!')
                //document.getElementById('inputMucCuoc').classList.add('input_batbuoc')
                this.$refs.inputMucCuoc.focus()
                return false
            }
            if(this.thongtin_dichvugt.input_cuoc_ld.toString().trim()==''){
                this.$toast.error('Bạn chưa nhập cước lắp đặt!')
                this.$refs.inputCuocLD.$refs.numeric.focus()
                return false
            }
            if(this.thongtin_dichvugt.input_cuoc_ld.toString().trim().length>12){
                this.$toast.error('Cước lắp đặt không được lớn hơn 12 ký tự!')
                this.$refs.inputCuocLD.$refs.numeric.focus()
                return false
            }
            if(!this.isNumeric(this.thongtin_dichvugt.input_cuoc_ld.toString().trim())){
                this.$toast.error('Cước lắp đặt phải là kiểu số!')
                this.$refs.inputCuocLD.$refs.numeric.focus()
                return false
            }

            if(this.thongtin_dichvugt.input_vat_lapdat.toString().trim()==''){
                this.$toast.error('Bạn chưa nhập thuế lắp đặt!')
                this.$refs.inputVatLD.$refs.numeric.focus()
                // document.getElementById('inputVatLD').classList.add('input_batbuoc')
                // this.$refs.inputVatLD.focus()
                return false
            }
            if(this.thongtin_dichvugt.input_vat_lapdat.toString().trim().length>12){
                this.$toast.error('Thuế lắp đặt không được lớn hơn 12 ký tự!')
                // document.getElementById('inputVatLD').classList.add('input_batbuoc')
                // this.$refs.inputVatLD.focus()
                this.$refs.inputVatLD.$refs.numeric.focus()
                return false
            }
            if(!this.isNumeric(this.thongtin_dichvugt.input_vat_lapdat.toString().trim())){
                this.$toast.error('Thuế lắp đặt phải là kiểu số!')
                // document.getElementById('inputVatLD').classList.add('input_batbuoc')
                // this.$refs.inputVatLD.focus()
                 this.$refs.inputVatLD.$refs.numeric.focus()
                return false
            }

            if(this.thongtin_dichvugt.input_cuoc_sd.toString().trim()==''){
                this.$toast.error('Bạn chưa nhập cước sử dụng!')
                // document.getElementById('inputCuocSD').classList.add('input_batbuoc')
                // this.$refs.inputCuocSD.focus()
                this.$refs.inputCuocSD.$refs.numeric.focus()
                return false
            }
            if(this.thongtin_dichvugt.input_cuoc_sd.toString().trim().length>12){
                this.$toast.error('Cước sử dụng không được lớn hơn 12 ký tự!')
                // document.getElementById('inputCuocSD').classList.add('input_batbuoc')
                // this.$refs.inputCuocSD.focus()
                 this.$refs.inputCuocSD.$refs.numeric.focus()
                return false
            }
            if(!this.isNumeric(this.thongtin_dichvugt.input_cuoc_sd.toString().trim())){
                this.$toast.error('Cước sử dụng phải là kiểu số!')
                // document.getElementById('inputCuocSD').classList.add('input_batbuoc')
                // this.$refs.inputCuocSD.focus()
                this.$refs.inputCuocSD.$refs.numeric.focus()
                return false
            }

            if(this.thongtin_dichvugt.input_vat_sudung.toString().trim()==''){
                this.$toast.error('Bạn chưa nhập thuế cước sử dụng!')
                // document.getElementById('inputVatSD').classList.add('input_batbuoc')
                // this.$refs.inputVatSD.focus()
                this.$refs.inputVatSD.$refs.numeric.focus()
                return false    
            }
            if(this.thongtin_dichvugt.input_vat_sudung.toString().trim().length>12){
                this.$toast.error('Thuế cước sử dụng không được lớn hơn 12 ký tự!')
                // document.getElementById('inputVatSD').classList.add('input_batbuoc')
                // this.$refs.inputVatSD.focus()
                this.$refs.inputVatSD.$refs.numeric.focus()
                return false
            }
            if(!this.isNumeric(this.thongtin_dichvugt.input_vat_sudung.toString().trim())){
                this.$toast.error('Thuế cước sử dụng phải là kiểu số!')
                // document.getElementById('inputVatSD').classList.add('input_batbuoc')
                // this.$refs.inputVatSD.focus()
                this.$refs.inputVatSD.$refs.numeric.focus()
                return false
            }

            if(this.thongtin_dichvugt.input_caidat_macdinh.toString().trim()==''){
                this.$toast.error('Bạn chưa nhập cài đặt mặc định!')
                //document.getElementById('inputCaiDatMD').classList.add('input_batbuoc')
                this.$refs.inputCaiDatMD.focus()
                return false
            }
            if(this.thongtin_dichvugt.input_caidat_macdinh.toString().trim().length>1){
                this.$toast.error('Cài đặt mặc định không được lớn hơn 1 ký tự!')
                //document.getElementById('inputCaiDatMD').classList.add('input_batbuoc')
                this.$refs.inputCaiDatMD.focus()
                return false
            }
            if(!this.isNumeric(this.thongtin_dichvugt.input_caidat_macdinh.toString().trim())){
                this.$toast.error('Cài đặt mặc định phải là kiểu số!')
                //document.getElementById('inputCaiDatMD').classList.add('input_batbuoc')
                this.$refs.inputCaiDatMD.focus()
                return false
            }

            if(this.thongtin_dichvugt.input_muc.toString().trim()==''){
                this.$toast.error('Bạn chưa nhập mức')
                //document.getElementById('inputMuc').classList.add('input_batbuoc')
                this.$refs.inputMuc.focus()
                return false
            }
            if(this.thongtin_dichvugt.input_muc.toString().trim().length>1){
                this.$toast.error('Mức không được lớn hơn 1 ký tự!')
                //document.getElementById('inputMuc').classList.add('input_batbuoc')
                this.$refs.inputMuc.focus()
                return false
            }
            if(!this.isNumeric(this.thongtin_dichvugt.input_muc.toString().trim())){
                this.$toast.error('Mức phải là kiểu số!')
                //document.getElementById('inputMuc').classList.add('input_batbuoc')
                this.$refs.inputMuc.focus()
                return false
            }

            if(this.thongtin_dichvugt.input_ghichu.toString().trim().length>100){
                this.$toast.error('Ghi chú không được quá 100 ký tự!')
                //document.getElementById('inputGhiChu').classList.add('input_batbuoc')
                this.$refs.inputGhiChu.focus()
                return false
            }

            if(this.thongtin_dichvugt.input_block_dau.toString().trim().length>5){
                this.$toast.error('Block đầu không được quá 5 ký tự!')
                //document.getElementById('inputBlockDau').classList.add('input_batbuoc')
                this.$refs.inputBlockDau.focus()
                return false
            }

            if(!this.isNumeric(this.thongtin_dichvugt.input_block_dau.toString().trim())){
                this.$toast.error('Block đầu phải là kiểu số!')
                //document.getElementById('inputBlockDau').classList.add('input_batbuoc')
                this.$refs.inputBlockDau.focus()
                return false
            }

            if(this.thongtin_dichvugt.input_block_tiep.toString().trim().length>5){
                this.$toast.error('Block tiếp không được quá 5 ký tự!')
                //document.getElementById('inputBlockTiep').classList.add('input_batbuoc')
                this.$refs.inputBlockTiep.focus()
                return false
            }

            if(!this.isNumeric(this.thongtin_dichvugt.input_block_tiep.toString().trim())){
                this.$toast.error('Block tiếp phải là kiểu số!')
                //document.getElementById('inputBlockTiep').classList.add('input_batbuoc')
                this.$refs.inputBlockTiep.focus()
                return false
            }
            if(this.thongtin_dichvugt.input_gia_block_tiep.toString().trim()==''){
                this.$toast.error('Bạn chưa nhập giá block tiếp')
                //document.getElementById('inputGiaBlockTiep').classList.add('input_batbuoc')
                this.$refs.inputGiaBlockTiep.focus()
                return false
            }
            if(this.thongtin_dichvugt.input_gia_block_tiep.toString().trim().length>12){
                this.$toast.error('Giá block tiếp không được vượt quá 12 ký tự!')
                //document.getElementById('inputGiaBlockTiep').classList.add('input_batbuoc')
                this.$refs.inputGiaBlockTiep.focus()
                return false
            }

            if(!this.isNumeric(this.thongtin_dichvugt.input_gia_block_tiep.toString().trim())){
                this.$toast.error('Giá block tiếp phải là kiểu số!')
                //document.getElementById('inputGiaBlockTiep').classList.add('input_batbuoc')
                this.$refs.inputGiaBlockTiep.focus()
                return false
            }
            if(this.thongtin_dichvugt.input_vat_block_tiep.toString().trim()==''){
                this.$toast.error('Bạn chưa nhập giá Vat block tiếp')
                //document.getElementById('inputVatBlockTiep').classList.add('input_batbuoc')
                this.$refs.inputVatBlockTiep.focus()
                return false
            }
            if(this.thongtin_dichvugt.input_vat_block_tiep.toString().trim().length>12){
                this.$toast.error('Giá Vat block tiếp không được vượt quá 12 ký tự!')
                //document.getElementById('inputVatBlockTiep').classList.add('input_batbuoc')
                this.$refs.inputVatBlockTiep.focus()
                return false
            }
            if(!this.isNumeric(this.thongtin_dichvugt.input_vat_block_tiep.toString().trim())){
                this.$toast.error('VAT Block tiếp phải là kiểu số!')
                //document.getElementById('inputVatBlockTiep').classList.add('input_batbuoc')
                this.$refs.inputVatBlockTiep.focus()
                //this.$toast.error('Giá Block tiếp phải là kiểu số!')
                return false
            }
            if(this.thongtin_dichvugt.input_heso_block.toString().trim()==''){
                this.$toast.error('Bạn hãy nhập Hệ số Block !')
                //document.getElementById('inputHeSoBlock').classList.add('input_batbuoc')
                this.$refs.inputHeSoBlock.focus()
                return false
            }
            if(this.thongtin_dichvugt.input_heso_block.toString().trim().length>3){
                this.$toast.error('Hệ số block không được quá 3 ký tự!')
                //document.getElementById('inputHeSoBlock').classList.add('input_batbuoc')
                this.$refs.inputHeSoBlock.focus()
                return false
            }
            
            if(!this.isNumeric(this.thongtin_dichvugt.input_heso_block.toString().trim())){
                this.$toast.error('Hệ số block phải là kiểu số!')
                //document.getElementById('inputHeSoBlock').classList.add('input_batbuoc')
                this.$refs.inputHeSoBlock.focus()
                return false
            }
            if(this.thongtin_dichvugt.khoanmuctt_selected==-1){
                this.$toast.error('Bạn chưa nhập khoản mục thanh toán!')
                //this.$refs.cboKhoanMucTT.focus()
                $('#cboKhoanMucTT').select2('open')
                return false
            }
            return true
        },
        isNumeric(str) {
            if (typeof str != "string") return false // we only process strings!  
            return !isNaN(str) && // use type coercion to parse the _entirety_ of the string (`parseFloat` alone does not do this)...
                    !isNaN(parseFloat(str)) // ...and ensure strings of whitespace fail
        },
        async getDuLieuDVGT(){
            let result=[]
            let object={
                DICHVUGT_ID: this.dichvugt_id,
                MA_DVGT: this.thongtin_dichvugt.input_ma_dvgt.trim(),
                TEN_DVGT: this.thongtin_dichvugt.input_ten_dvgt.trim(),
                NHOM: this.thongtin_dichvugt.input_nhom.trim(),
                MUCCUOC: this.thongtin_dichvugt.input_muccuoc.trim()!=''?Number(this.thongtin_dichvugt.input_muccuoc.trim()):0,

                CUOC_LD: this.thongtin_dichvugt.input_cuoc_ld.trim()!=''?Number(this.thongtin_dichvugt.input_cuoc_ld.trim()):0,
                VAT_LD: this.thongtin_dichvugt.input_vat_lapdat.trim()!=''?Number(this.thongtin_dichvugt.input_vat_lapdat.trim()):0,

                CUOC_SD: this.thongtin_dichvugt.input_cuoc_sd.trim()!=''?Number(this.thongtin_dichvugt.input_cuoc_sd.trim()):0,
                VAT_SD: this.thongtin_dichvugt.input_vat_sudung.trim()!=''?Number(this.thongtin_dichvugt.input_vat_sudung.trim()):0,

                CAIDAT_MD: this.thongtin_dichvugt.input_caidat_macdinh.trim()!=''?Number(this.thongtin_dichvugt.input_caidat_macdinh.trim()):0,
                MUC_ID: this.thongtin_dichvugt.input_muc.trim()?Number(this.thongtin_dichvugt.input_muc.trim()):0,
                KIEU_ID: this.thongtin_dichvugt.kieu_dvgt_selected,
                TRANGTHAITB_ID: this.thongtin_dichvugt.trangthai_tb_selected,
                HE_SO: this.thongtin_dichvugt.input_heso_block.trim()!=''?Number(this.thongtin_dichvugt.input_heso_block):0,
                HTTC_ID: this.thongtin_dichvugt.hinhthuc_tc_selected,
                GHICHU: this.thongtin_dichvugt.input_ghichu.trim(),
                BLOCK_DAU: this.thongtin_dichvugt.input_block_dau.trim()!=''?Number(this.thongtin_dichvugt.input_block_dau.trim()):0,
                BLOCK_TIEP: this.thongtin_dichvugt.input_block_tiep.trim()!=''?Number(this.thongtin_dichvugt.input_block_tiep.trim()):0,
                GIA_BLOCK_TIEP: this.thongtin_dichvugt.input_gia_block_tiep.trim()!=''?Number(this.thongtin_dichvugt.input_gia_block_tiep.trim()):0,
                VAT_BLOCK_TIEP: this.thongtin_dichvugt.input_vat_block_tiep.trim()!=''?Number(this.thongtin_dichvugt.input_vat_block_tiep.trim()):0,
                SOLUONG: this.thongtin_dichvugt.checkbox_soluong?1:0
            }
            result.push(object)
            return result
        },
        async getLHTB(){
            let result=[]
            this.dataSourcesGan.forEach((item)=>{
                result.push({
                    LOAITB_ID:item.loaitb_id,
                    DICHVUGT_ID:this.dichvugt_id,
                    KHOANMUCTT_ID:this.thongtin_dichvugt.khoanmuctt_selected
                })
            })
            return result
        },
        async get_keys(keyname){
            try{
                let response = await VASInfoAPI.get_keys(this.axios, keyname)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return Number(response.data.data)
                }else{
                    return 0
                }
            }catch(e){
                return 0
            }
        },
        async lay_danhsach_dichvuvt(){
            try{
                this.loading(true)
                let response = await VASInfoAPI.lay_danhsach_dichvuvt(this.axios)
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
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Không load được danh sách dịch vụ viễn thông')
                }
                return []
            }
        },
        async lay_danhsach_hinhthuc_tc(){
            try{
                this.loading(true)
                let response = await VASInfoAPI.lay_danhsach_hinhthuc_tc(this.axios)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data.sort(function(a, b) {
                        return a.HTTC_ID - b.HTTC_ID
                    })
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
                    this.$toast.error('Không load được danh sách hình thức')
                }
                return []
            }
        },
        async lay_danhsach_trangthai_tb(){
            try{
                this.loading(true)
                let response = await VASInfoAPI.lay_danhsach_trangthai_tb(this.axios)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data.sort(function(a, b) {
                        return a.TRANGTHAITB_ID - b.TRANGTHAITB_ID
                    })
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
                    this.$toast.error('Không load được danh sách trạng thái TB')
                }
                return []
            }
        },
        async lay_danhsach_kieu_dvgt(){
            try{
                this.loading(true)
                let response = await VASInfoAPI.lay_danhsach_kieu_dvgt(this.axios)
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
                    this.$toast.error('Không load được danh sách kiểu dịch vụ GT')
                }
                return []
            }
        },
        async hienthi_danhsach_dvgt(){
            //this.dataSourcesDVGT=[]
            if(this.thongtin_timkiem.checkbox_dichvu){
                this.dataSourcesDVGT=await this.lay_danhsach_dvgt(this.thongtin_timkiem.dichvu_vt_selected!=null?this.thongtin_timkiem.dichvu_vt_selected:0)
            }else{
                this.dataSourcesDVGT=await this.lay_danhsach_dvgt(0)
            }
        },
        async lay_danhsach_dvgt(dichvuvt_id){
            try{
                this.loading(true)
                let response = await VASInfoAPI.lay_danhsach_dvgt_theo_dichvu_vt(this.axios, dichvuvt_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data.sort((a, b)=>b.dichvugt_id-a.dichvugt_id)
                    // let phanvung_id=this.$root.token.getPhanVungID()
                    // return response.data.data.filter(x=>(x.phanvung_id==undefined||x.phanvung_id==phanvung_id))
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
                    this.$toast.error('Không load được danh sách dịch vụ gia tăng')
                }
                return []
            }
        },
        async lay_danhsach_loaihinh_dvgt_dagan_chuagan(dichvugt_id, dichvuvt_id, kieu){
            try{
                this.loading(true)
                let response = await VASInfoAPI.lay_danhsach_loaihinh_dvgt_dagan_chuagan(this.axios, dichvugt_id, dichvuvt_id, kieu)
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
                    this.$toast.error('Đã có lỗi xảy ra không load được loại hình dịch vụ gia tăng')
                }
                return []
            }
        },
        async dvgt_update(dichvugt_id, is_insert, data, lhtb_data){
            try{
                this.loading(true)
                let response = await VASInfoAPI.dvgt_update(this.axios, dichvugt_id, is_insert, data, lhtb_data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    if(response&&response.data&&response.data.message){
                        return response.data.message
                    }else{
                        return is_insert==1?'Đã xảy ra lỗi không thể thêm mới dữ liệu':'Đã xảy ra lỗi không thể cập nhật dữ liệu'
                    }
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                    return e.data.message
                }else if(e.response&&e.response.data&&e.response.data.message){
                    return e.response.data.message
                }else{
                    return is_insert==1?'Đã xảy ra lỗi không thể thêm mới dữ liệu':'Đã xảy ra lỗi không thể cập nhật dữ liệu'
                }
            }
        },
        async dvgt_delete(dichvugt_id){
            try{
                this.loading(true)
                let response = await VASInfoAPI.dvgt_delete(this.axios, dichvugt_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    if(response.data.data=='1'){
                        this.$toast.success('Xóa dữ liệu thành công !')
                        this.clear()
                        //hiển thị resfresh danh sách
                        await this.hienthi_danhsach_dvgt()
                    }else{
                        this.$toast.error(response.data.data)
                    }
                }else{
                    if(response&&response.data&&response.data.message){
                        this.$toast.error(response.data.message)
                    }else{
                        this.$toast.error('Đã xảy ra lỗi, xoá dữ liệu không thành công')
                    }
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Đã xảy ra lỗi, xoá dữ liệu không thành công')
                }
            }
        },
        async get_ds_kmtt(){
            try{
                this.loading(true)
                let response = await VASInfoAPI.get_ds_kmtt(this.axios)
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
        async hienthi_loaihinh_tb(){
            console.log('hienthi_loaihinh_tb', this.dichvugt_id)
            this.dataSourcesChuaGan=[]
            this.dataSourcesChuaGan=await this.lay_danhsach_loaihinh_dvgt_dagan_chuagan(this.dichvugt_id,this.thongtin_loaihinh.dichvu_vt_selected!=null?this.thongtin_loaihinh.dichvu_vt_selected:0,0)
            this.dataSourcesGan=[]
            this.dataSourcesGan=await this.lay_danhsach_loaihinh_dvgt_dagan_chuagan(this.dichvugt_id,this.thongtin_loaihinh.dichvu_vt_selected!=null?this.thongtin_loaihinh.dichvu_vt_selected:0,1)
        },
        async initDuLieu(){
            let ds_dichvu=await this.lay_danhsach_dichvuvt()
            this.thongtin_timkiem.dichvu_vt=ds_dichvu
            this.thongtin_loaihinh.dichvu_vt=ds_dichvu
            if(ds_dichvu.length>0){
                //mặc định 1 cố định
                this.thongtin_timkiem.dichvu_vt_selected=1
                this.thongtin_loaihinh.dichvu_vt_selected=1
            }

            this.thongtin_dichvugt.hinhthuc_tc = await this.lay_danhsach_hinhthuc_tc()
            this.thongtin_dichvugt.hinhthuc_tc.unshift({
                HTTC_ID:0,
                HINHTHUC_TC:''
            })
            if(this.thongtin_dichvugt.hinhthuc_tc.length>0){
                this.thongtin_dichvugt.hinhthuc_tc_selected=this.thongtin_dichvugt.hinhthuc_tc[0].HTTC_ID
            }

            this.thongtin_dichvugt.trangthai_tb=await this.lay_danhsach_trangthai_tb()
            if(this.thongtin_dichvugt.trangthai_tb.length>0){
                this.thongtin_dichvugt.trangthai_tb_selected=this.thongtin_dichvugt.trangthai_tb[0].TRANGTHAITB_ID
            }

            //
            this.thongtin_dichvugt.ds_khoanmuc_tt=await this.get_ds_kmtt()
            this.thongtin_dichvugt.khoanmuctt_selected=-1

            this.thongtin_dichvugt.kieu_dvgt=await this.lay_danhsach_kieu_dvgt()
            if(this.thongtin_dichvugt.kieu_dvgt.length>0){
                this.thongtin_dichvugt.kieu_dvgt_selected=this.thongtin_dichvugt.kieu_dvgt[0].kieu_id
            }

            await this.hienthi_danhsach_dvgt()
        }
    },
    mounted(){
        this.setActiveActions(-1)
        setTimeout(()=>{
            this.initDuLieu()
        }, 500)

    }
}
</script>
<style>
.input_batbuoc:focus {
    border-color:red;
    background-color: lightcoral;
    box-shadow: none;
}
.select2-selection { overflow: hidden !important; }
.select2-container .select2-selection--single .select2-selection__rendered {
  white-space: normal !important;
  word-break: break-all !important;
}
</style>