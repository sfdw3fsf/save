<template>
<div class="modal-content popup-box">
    <div class="popup-header">
        <div class="title"><span class="icon one-notepad"></span>Chọn số cho khách hàng</div>
        <div class="close -ap icon-close" data-dismiss="modal" @click.prevent="closeForm">
        </div>
    </div>
    <div class="list-actions-top">
        <ul class="list">
            <li>
                <a href="#" @click.prevent="btnLayTT_Click">
                    <span class="icon one-search"></span>Tìm kiếm
                </a>
            </li>
            <li>
                <a href="#" @click.prevent="btnRefresh_Click">
                    <span class="icon one-reload1"></span> Lấy TT
                </a>
            </li>
            <li>
                <a href="#" @click.prevent="tsbtnChonSo_Click">
                    <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Chọn số
                </a>
            </li>
            <li>
                <a href="#" @click.prevent="btnNhapMoi_Click">
                    <span class="icon one-file-edit2"></span>Nhập số
                </a>
            </li>
            <li>
                <a href="#" @click.prevent="btnXoaTB_Click">
                    <span class="icon one-trash"></span>Xoá DS
                </a>
            </li>
            <li>
                <a href="#" @click.prevent="btnCapNhat_Click">
                    <span class="icon one-luu-so"></span> Đăng ký                </a>
            </li>
            <!-- <li>
                <a href="#">
                    <span class="icon one-xlsx-import"></span> Xuất Excel
                </a>
            </li> -->
        </ul>
        <ul class="list right">
            <li>
                <a href="#">
                    <span class="icon one-search"></span>Hướng dẫn
                </a>
            </li>
            <li>
                <a href="#" @click.prevent="closeForm">
                    <span class="icon ui-1_circle-remove nc-icon-glyph"></span> Thoát
                </a>
            </li>
        </ul>
    </div>
    <div class="popup-body">
        <div class="row">
            <div class="legend-title mart20">Thông tin tìm kiếm kho số</div>
            <div class="col-sm-6 col-12">
                <div class="box-form">
                    <div class="info-row">
                        <div class="key w90">
                            <!-- <div class="check-action"> -->
                                <span class="name">Tìm kiếm</span>
                            <!-- </div> -->
                        </div>
                        <div class="value w90">
                            <select2 v-model="cboPrefix.value" class="select-custom" :options="cboPrefix.list.map(ls=>({id:ls.NHOMSO_ID,text:ls.NHOMSO}))" @change="cboPrefix_EditValueChanged"></select2>
                        </div>
                        <div class="value">
                            <input type="text" class="form-control highlight" v-model="txtPrefix" @keypress.enter="btnLayTT_Click" />
                        </div>
                    </div>

                    <div class="info-row">
                        <div class="key w90">
                            <!-- <div class="check-action"> -->
                                <span class="name">Kho số</span>
                            <!-- </div> -->
                        </div>
                        <div class="value">
                            <select2 v-model="cboKhoSo.value" class="select-custom" :options="cboKhoSo.list.map(ls=>({id:ls.KHO_ID,text:ls.TEN_KHO}))"></select2>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-6 col-12">
                <div class="box-form">
                    <div class="info-row">
                        <div class="key w70">Trạng thái</div>
                        <div class="value">
                            <select2 v-model="cboTrangThai.value" class="select-custom" :options="cboTrangThai.list.map(ls=>({id:ls.ID,text:ls.NAME}))"></select2>
                        </div>
                    </div>
                    <div class="info-row">
                        <div class="key w70">Mã xác thực</div>
                        <div class="value">
                            <input type="text" class="form-control highlight" v-model="txtMaXacThuc">
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-12">
                <div class="info-row">
                    <div class="key w90">
                            <span class="name">Kiểu số</span>
                    </div>
                    <div class="value">
                        <select2 v-model="CboKieuSo.value" class="select-custom" :options="CboKieuSo.list.map(ls=>({id:ls.KIEUSO_ID,text:ls.TEN_KIEUSO}))"></select2>
                    </div>
                </div>
            </div>
            <div class="col-12">
                <div class="box-form">
                    <div class="nav tabs tab-over" id="xTabKhoSo">
                        <a href="#tabdskstq" @click.prevent="khoso_dangchon=0; cboKhoSo.value = dulieu.vkieuform==0?4:14;" data-toggle="tab" class="active">{{xTabKhoTQ}}</a>
                        <a href="#tabdsks" @click.prevent="khoso_dangchon=1; cboKhoSo.value = 1;" data-toggle="tab">{{xTabKhoTinh}}</a>
                        <a href="#tabgdvcstq" @click.prevent="khoso_dangchon=0; cboKhoSo.value = 1" data-toggle="tab">GDV chọn số toàn quốc</a>
                    </div>
                    <div class="tab-content nocorner bor0 pad0 bg-none" style="box-shadow: none;">
                        <div id="tabdskstq" class="tab-pane active">
                            <div class="tab-content">
                                <DataGrid ref="grid_tabdskstq" @commandClick="LAPHD_CHONSO"
                                        v-bind:columns="[ 
                                        {fieldName: '', headerText: 'Chọn', width:100,textAlign:'center', commands: [{ buttonOption: { content: 'Chọn', cssClass: 'e-flat' } }]}, 
                                        {fieldName: 'ROW_STT', headerText: 'STT', allowFiltering: true, allowSorting: true}, 
                                        {fieldName: 'SO_TB', headerText: 'Số máy', allowFiltering: true, allowSorting: true}, 
                                        {fieldName: 'STATUS', headerText: 'Tình trạng', allowFiltering: true, allowSorting: true}, 
                                        {fieldName: 'TEN_KIEUSO', headerText: 'Kiểu số', allowFiltering: true, allowSorting: false}, 
                                        {fieldName: 'CUOCTHANG_TOITHIEU', headerText: 'Cước tháng cam kết', allowFiltering: true, allowSorting: false}, 
                                        {fieldName: 'THOIGIAN_SUDUNG', headerText: 'Thời gian cam kết', allowFiltering: true, allowSorting: true}, 
                                        {fieldName: 'DATCOC', headerText: 'Tiền hoàn trả HĐ tháng', allowFiltering: true, allowSorting: true}, 
                                        {fieldName: 'TRANGTHAI', headerText: 'Trạng Thái', allowFiltering: true, allowSorting: true}, 
                                        {fieldName: 'STATUS_TT', headerText: 'Trạng Thái TT', allowFiltering: true, allowSorting: true}, 
                                        ]" 
                                    v-bind:dataSource="grcDSKhoSo.DataSource" :showFilter="true" :showColumnCheckbox="false" :allowPaging="true" :enable-paging-server="false"
                                    dataKeyField="ROW_STT" :defaultColumnCheckboxChecked="false" panelDataHeight="200" @rowClicked="(i,v)=>grcDSKhoSo.value=v"> 
                                </DataGrid>
                                <div class="input-group mb-3">
                                    <div class="input-group-prepend" style="float:left">
                                        <button class="btn btn-second w40" title="Đi đến trang trước" @click="goToPrevCustomPage('grid_tabdskstq',pageNumberTQ)"><</button>
                                    </div>
                                    <div class="value w90"  style="float:left">
                                        <input type="number" class="form-control highlight text-center" min=1 v-model="pageNumberTQ" @keypress.enter="goToCustomPage('grid_tabdskstq',pageNumberTQ )"/>
                                    </div>
                                    <div class="input-group-append"  style="float:left">
                                        <button class="btn btn-second w40" title="Đi đến trang sau" @click="goToNextCustomPage('grid_tabdskstq',pageNumberTQ)">></button>
                                    </div>
                                </div>  
                            </div>
                        </div>
                        <div id="tabdsks" class="tab-pane">
                            <div class="tab-content">
                                <DataGrid  @commandClick="btnKhoiTao_TS_ButtonClick"
                                        v-bind:columns="[ 
                                        // {fieldName: 'ISCHECK', headerText: 'CB', allowFiltering: true, allowSorting: true}, 
                                        {fieldName: 'ROW_STT', headerText: 'STT', allowFiltering: true, allowSorting: true}, 
                                        {fieldName: 'MSISDN', headerText: 'Số máy', allowFiltering: true, allowSorting: true}, 
                                        {fieldName: 'STATUS', headerText: 'Tình trạng', allowFiltering: true, allowSorting: true}, 
                                        {fieldName: 'TEN_KIEUSO', headerText: 'Kiểu số', allowFiltering: true, allowSorting: false}, 
                                        {fieldName: 'CUOC_CK', headerText: 'Cước CK', allowFiltering: true, allowSorting: false}, 
                                        {fieldName: 'THOIGIAN_SUDUNG', headerText: 'Thời gian CK', allowFiltering: true, allowSorting: false}, 
                                        {fieldName: 'MSI_KHO', headerText: 'Kho', allowFiltering: true, allowSorting: true}, 
                                        {fieldName: 'TRANGTHAI', headerText: 'Trạng Thái', allowFiltering: true, allowSorting: true}, 
                                        {fieldName: 'STATUS_TT', headerText: 'Trạng Thái TT', allowFiltering: true, allowSorting: true}, 
                                        {fieldName: '', headerText: 'K.Tạo', width:55, commands: [{ buttonOption: { content: '', cssClass: 'icon one-luu-so' } }]}, 
                                        ]" 
                                    v-bind:dataSource="grcDSAllKho.DataSource" :showFilter="true" :showColumnCheckbox="true" :allowPaging="true" :enable-paging-server="false"
                                    dataKeyField="ROW_STT" :defaultColumnCheckboxChecked="false" panelDataHeight="200" @rowClicked="(i,v)=>grcDSAllKho.value=v"> 
                                </DataGrid>
                            </div>
                        </div>
                        <div id="tabgdvcstq" class="tab-pane">
                            <div class="tab-content">
                                <DataGrid @commandClick='KHOTQ_CHONSO_TS' ref="grid_tabgdvcstq"
                                        v-bind:columns="[ 
                                        // {fieldName: 'ISCHECK', headerText: 'CB', allowFiltering: true, allowSorting: true}, 
                                        {fieldName: 'ROW_STT', headerText: 'STT', allowFiltering: true, allowSorting: true, width:80, textAlign:'center'}, 
                                        {fieldName: '', headerText: 'Chọn', width:100,textAlign:'center', commands: [{ buttonOption: { content: 'Chọn', cssClass: 'e-flat' } }]}, 
                                        {fieldName: 'SO_TB', headerText: 'Số máy', allowFiltering: true, allowSorting: true}, 
                                        {fieldName: 'STATUS', headerText: 'Tình trạng', allowFiltering: true, allowSorting: true}, 
                                        {fieldName: 'TEN_KIEUSO', headerText: 'Kiểu số', allowFiltering: true, allowSorting: false}, 
                                        {fieldName: 'CUOCTHANG_TOITHIEU', headerText: 'Cước tháng cam kết', allowFiltering: true, allowSorting: false}, 
                                        {fieldName: 'THOIGIAN_SUDUNG', headerText: 'Thời gian cam kết', allowFiltering: true, allowSorting: true},
                                        {fieldName: 'DATCOC', headerText: 'Tiền hoàn trả HĐ tháng', allowFiltering: true, allowSorting: true}, 
                                        {fieldName: 'TRANGTHAI', headerText: 'Trạng Thái', allowFiltering: true, allowSorting: true}, 
                                        {fieldName: 'STATUS_TT', headerText: 'Trạng Thái TT', allowFiltering: true, allowSorting: true}, 
                                        ]" 
                                    v-bind:dataSource="gcKHOTQTraSau.DataSource" :showFilter="true" :showColumnCheckbox="true" :allowPaging="true" :enable-paging-server="false"
                                    dataKeyField="ROW_STT" :defaultColumnCheckboxChecked="false" panelDataHeight="200" @rowClicked="(i,v)=>gcKHOTQTraSau.value=v"> 
                                </DataGrid>
                                <div class="input-group mb-3">
                                    <div class="input-group-prepend"  style="float:left">
                                        <button class="btn btn-second w40" title="Đi đến trang trước" @click="goToPrevCustomPage('grid_tabgdvcstq',pageNumberTQTraSau)"><</button>
                                    </div>
                                    <div class="value w90"  style="float:left">
                                        <input type="number" class="form-control highlight text-center" min=1 v-model="pageNumberTQTraSau" @keypress.enter="goToCustomPage('grid_tabgdvcstq',pageNumberTQTraSau)"/>
                                    </div>
                                    <div class="input-group-append"  style="float:left">
                                        <button class="btn btn-second w40" title="Đi đến trang sau" @click="goToNextCustomPage('grid_tabgdvcstq',pageNumberTQTraSau)">></button>
                                    </div>
                                </div>               
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-6 col-12" v-if="dulieu.vkieuform==0">
                <div class="box-form">
                    <div class="legend-title mart20">Danh sách các số chờ đăng ký</div>
                    <DataGrid2 
                            v-bind:columns="[ 
                            // {fieldName: 'ISCHECK', headerText: 'CB', allowFiltering: true, allowSorting: true}, 
                            {fieldName: 'SO_TB', headerText: 'Số máy', allowFiltering: true, allowSorting: true}, 
                            {fieldName: 'TEN_KIEUSO', headerText: 'Kiểu số', allowFiltering: true, allowSorting: false}, 
                            {fieldName: 'CUOCTHANG_TOITHIEU', headerText: 'Cước tháng cam kết', allowFiltering: true, allowSorting: false}, 
                            ]" 
                        v-bind:dataSource="grcDS_ChuaDK.DataSource" :showFilter="true" :showColumnCheckbox="true" :allowPaging="true" :enable-paging-server="false"
                        dataKeyField="SO_TB" :defaultColumnCheckboxChecked="false" :panelDataHeight="200" @rowClicked="(i,v)=>grcDS_ChuaDK.value=v"> 
                    </DataGrid2>
                </div>
            </div>
            <div class="col-12" :class="{'col-sm-6':dulieu.vkieuform==0,'col-sm-12':dulieu.vkieuform==1}">
                <div class="box-form">
                    <div class="nav tabs tab-over">
                        <a href="#tabsmddk" data-toggle="tab" class="active">Danh sách các số máy đã đăng ký</a>
                        <a href="#tabsmdkt" data-toggle="tab">Danh sách các số máy đã khởi tạo</a>
                    </div>
                    <div class="tab-content nocorner bor0 pad0 bg-none" style="box-shadow: none;">
                        <div id="tabsmddk" class="tab-pane active">
                            <div class="tab-content">
                                <DataGrid @commandClick="btnChon_ButtonClick"
                                        v-bind:columns="[ 
                                        {fieldName: 'STT', headerText: 'STT', allowFiltering: true, allowSorting: true}, 
                                        {fieldName: 'SO_TB', headerText: 'Số máy', allowFiltering: true, allowSorting: true}, 
                                        {fieldName: 'NGAYTH', headerText: 'Thời gian ĐK', allowFiltering: true, allowSorting: false}, 
                                        {fieldName: 'THOIGIAN_CON', headerText: 'Hiệu lực (phút)', allowFiltering: true, allowSorting: false}, 
                                        {fieldName: 'GHI_CHU', headerText: 'Ghi chú', allowFiltering: true, allowSorting: true}, 
                                        {fieldName: 'KHO_ID', headerText: 'Kho Id', allowFiltering: true, allowSorting: false}, 
                                        {fieldName: '', headerText: 'Chọn', width:50, commands: [{ buttonOption: { content: 'Chọn', cssClass: 'e-flat' } }]}, 
                                        ]" 
                                    v-bind:dataSource="grcDS_DaDK.DataSource" :showFilter="true" :showColumnCheckbox="false" :allowPaging="true" :enable-paging-server="false"
                                    dataKeyField="STT" :defaultColumnCheckboxChecked="false" panelDataHeight="200" @rowClicked="(i,v)=>grcDS_DaDK.value=v"> 
                                </DataGrid>
                            </div>
                        </div>
                        <div id="tabsmdkt" class="tab-pane">
                            <div class="tab-content">
                                <DataGrid @commandClick="btnChonSo_KT_ButtonClick"
                                        v-bind:columns="[ 
                                        {fieldName: 'STT', headerText: 'STT', allowFiltering: true, allowSorting: true}, 
                                        {fieldName: 'SOMAY', headerText: 'Số máy', allowFiltering: true, allowSorting: true}, 
                                        {fieldName: 'SIMCARD', headerText: 'Số Sim', allowFiltering: true, allowSorting: true}, 
                                        {fieldName: 'NGAY_CN', headerText: 'Ngày khởi tạo', allowFiltering: true, allowSorting: false}, 
                                        {fieldName: 'GHICHU', headerText: 'Ghi chú', allowFiltering: true, allowSorting: false}, 
                                        {fieldName: '', headerText: 'Chọn', width:50, commands: [{ buttonOption: { content: 'Chọn', cssClass: 'e-flat' } }]}, 
                                        ]" 
                                    v-bind:dataSource="grcDSKhoiTao.DataSource" :showFilter="true" :showColumnCheckbox="false" :allowPaging="true" :enable-paging-server="false"
                                    dataKeyField="STT" :defaultColumnCheckboxChecked="false" panelDataHeight="200" @rowClicked="(i,v)=>grcDSKhoiTao.value=v"> 
                                </DataGrid>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <b-modal id="popupComponentsKhoiTaoTB" size="l"
        hide-footer
        hide-header
        hide-header-close
        body-class="modal-body p-0">
        <component :is="popupComponent" ref="generalPopup" :dulieu="popupComponentData" v-bind="popupComponentAttr" isPopup="true" v-on="popupComponentEvts"></component> <!--v-dynamic-events="popupComponentEvents"-->
    </b-modal>
</div>
</template>
<style scoped>
    input.disabled, select.disabled, .disabled input, .disabled select, .disabled /deep/ .select2-selection { background-color: #EEE; color:lightgray;} .btn-second { border-color: #0d6efd; }
</style>
<script>
import {
  LoaiHinhTB,
  DatabaseConstants,
} from "../Enums";
import moment from 'moment'
export default {
    name: 'popupChonSoKH',
    props: [ 'dulieu' ],
    watch: {
    },
    data() {
        return {
            customPage: 1,
            KHO_TINH_ID: 14,
            KHO_TQ_ID: 1,
            khoso_dangchon: 0,
            xTabKhoTQ: 'Danh sách kho số toàn quốc',
            xTabKhoTinh: 'Danh sách kho số',
            pageNumberAllKho: 1,
            pageNumberTQ: 1,
            pageNumberTQTraSau: 1,
            Buttons: {
                btnCapNhat: true,
                btnRefresh: true,
                tsbtnChonSo: true,
                btnXoaTB: true,
                btnNhapMoi: true,
            },
            Columns: {
                colChKB: true,
                colBtnChonSo: true,
            },
            cboPrefix: {
                list: [],
                value: -1,
            },
            cboTrangThai: {
                list: [],
                value: 0,
            },
            cboKhoSo: {
                list: [],
                value: 0,
            },
            CboKieuSo: {
                list: [],
                value: 0,
            },
            grcDS_ChuaDK: {
                DataSource: [],
            },
            grcDS_DaDK: {
                DataSource: [],
                value: {},
            },
            grcDSKhoiTao: {
                DataSource: [],
                value: {},
            },
            grcDSAllKho: {
                DataSource: [],
                value: {},
            },
            grcDSKhoSo: {
                DataSource: [],
                value: {},
            },
            gcKHOTQTraSau: {
                DataSource: [],
                value: {},
            },
            txtPrefix: '',
            txtMaXacThuc: '',
            ttnd: {},
            frmDangTai_Output: {},
            popupRetVal: null,
            popupComponent: null,
            popupComponentData: null,
            popupComponentAttr: {},
            popupComponentEvts: {
                'form-close':this.popupClosed,
            },
        }
    },
    async mounted() {
        this.ttnd = await this.$root.token.getThongTinNguoiDung();
        if(!this.ttnd.ma_nv_ccbs) this.$toast.error('Không lấy được thông tin ["ma_nv_ccbs"] của người dùng hiện tại, chức năng này có thể không hoạt động được bình thường. Vui lòng liên hệ Admin!');
        this.THIETLAP();
        // this.loading(true);
        // //Load default data
        // {
        //     this.diachi.quan_id = this.dulieu._quan_id?this.dulieu._quan_id:0;
        //     this.diachi.phuong_id = this.dulieu._phuong_id?this.dulieu._phuong_id:0;
        //     this.diachi.pho_id = this.dulieu._pho_id?this.dulieu._pho_id:0;
        //     this.diachi.ap_id = this.dulieu._ap_id?this.dulieu._ap_id:0;
        //     this.diachi.khu_id = this.dulieu._khu_id?this.dulieu._khu_id:0;
        //     this.loaiso.value = this.dulieu._loaiso_id?this.dulieu._loaiso_id:0;
        //     this.loaitb_id = this.dulieu.loaitb_id;
        //     this.donvi_ql_id = this.dulieu.donvi_ql_id;
        //     this.dichvuvt_id = this.dulieu.dichvuvt_id;
        //     this.hdkh_id = this.dulieu.hdkh_id;
        // }

        // // var load3 = this.$root.context.get("/web-quantri/danhmuc-chung/CSS_LOAI_SO");
        // var load4 = this.$root.context.get("/web-quantri/danhmuc-chung/CSS_NHOM_SO");
        // var load5 = this.$root.context.get("/web-quantri/danhmuc-chung/CSS_KIEU_SO");
        // //if(loaiso && loaiso.data) this.loaiso.list = loaiso.data;
        // Promise.all([load4,load5]).then(([data4,data5])=>{
        //     // if(data3 && data3.data) this.loaiso.list = data3.data;
        //     if(data4 && data4.data) this.nhomso.list = data4.data;
        //     if(data5 && data5.data) this.kieuso.list = data5.data;
        // }).finally(()=>{
        //     this.loading(false);
        // });
    },
    computed: {
        filteredSoMay: function() {
            var newlist = this.somay.list.filter(x=>x.somay.indexOf(this.timkiem)>=0);
            if(newlist && newlist.length>0) this.dsSoMayChanged(0,newlist[0]);
            return newlist?newlist:[];
        },
        vprefix: function() {
            return !this.cboPrefix.value ? -1 : this.cboPrefix.value;
        },
        vprefix_all: function() {
            return !this.cboPrefix.value ? -1 : '84'+(this.cboPrefix.value.indexOf('0')==0?this.cboPrefix.value.substring(1):this.cboPrefix.value);
        },
        vma_tb: function() {
            return this.txtPrefix;
        },
        vstatus: function() {
            return !this.cboTrangThai.value?0:this.cboTrangThai.value;
        },
        vma_xacthuc: function() {
            return this.txtMaXacThuc;
        },
        vkieusso_id: function() {
            return !this.CboKieuSo.value || this.CboKieuSo.value==0?"":this.CboKieuSo.value;
        },
        vkho_id: function() {
            return !this.cboKhoSo.value?-1:this.cboKhoSo.value;
        },
        vthuebao_chon: function() {
            return this.grcDS_DaDK.value && this.grcDS_DaDK.value.SO_TB?this.grcDS_DaDK.value.SO_TB:'-1';
        },
        vthuebao_dakhoitao: function() {
            return this.grcDSKhoiTao.value && this.grcDSKhoiTao.value.SOMAY?this.grcDSKhoiTao.value.SOMAY:'-1';
        },
        vsomay_khotinh: function() {
            return this.grcDSAllKho.value && this.grcDSAllKho.value.MSISDN?this.grcDSAllKho.value.MSISDN:'-1';
        },
        vkhoid_dadangky: function() {
            return this.grcDS_DaDK.value && this.grcDS_DaDK.value.KHO_ID?this.grcDS_DaDK.value.KHO_ID:'-1';
        },
        vsomay_khochung_tinh: function() {
            return this.grcDSKhoSo.value && this.grcDSKhoSo.value.SO_TB?this.grcDSKhoSo.value.SO_TB:'-1';
        },
        vsomay_toanquoc_trasau: function() {
            var stb = this.gcKHOTQTraSau.value && this.gcKHOTQTraSau.value.SO_TB?this.gcKHOTQTraSau.value.SO_TB:'-1';
            return stb.indexOf('0')==0?'84'+stb.substring(1):stb;
        },
        vthuebao_kt: function() {
            return this.grcDS_DaDK.value && this.grcDS_DaDK.value.SOMAY?this.grcDS_DaDK.value.SOMAY:'-1';
        },
        vgoicuoc_id: function() {
            return this.grcDSKhoiTao.value && this.grcDSKhoiTao.value.GOICUOC_ID?this.grcDSKhoiTao.value.GOICUOC_ID:-1;
        },
        vsimcard: function() {
            return this.grcDSKhoiTao.value && this.grcDSKhoiTao.value.SIMCARD?this.grcDSKhoiTao.value.SIMCARD:'-1';
        },
        matinh: function() {
            return this.$root.token.getMaTinh();
        }
    },
    methods: {
        async THIETLAP() {
            if(this.dulieu.vkieuform == undefined || this.dulieu.vkieuform == null || this.dulieu.vkieuform*1<0) {
                this.$toast.error("Bạn phải truyền kiểu form vào");
                this.closeForm();
                return;
            }

            if (this.dulieu.vkieuform == 1)// trả sau
            {
                this.xTabKhoTQ = "Danh sách kho số chung viễn thông tỉnh";
                this.Columns.colChKB = false;
                this.Buttons.btnCapNhat = false;
                this.Buttons.btnRefresh = false;
                this.Buttons.tsbtnChonSo = false;
                this.Buttons.btnXoaTB = false;
                this.Buttons.btnNhapMoi = false;
                this.xTabKhoTinh = "Danh sách kho số khác";//vuth update ts 0210
                // splitContainerControl1.PanelVisibility = SplitPanelVisibility.Both; // vuth update ts 0710
                // cboTrangThai.Properties.ReadOnly = false;
                this.NAP_COMBO();
                this.NAP_DS_SODANGKY();
                this.NAP_DS_KHOITAO(); // vuth update 04/12

                // xTabKhoSo.SelectedTabPageIndex = 2; // vuth update 0710 mac dinh kho so toan quoc
                // var triggerFirstTabEl = document.querySelector('#xTabKhoSo a[href="#tabgdvcstq"]')
                // bootstrap.Tab.getInstance(triggerFirstTabEl).show()

                // tableLayoutPanel2.ColumnStyles[0].SizeType = SizeType.Percent;
                // tableLayoutPanel2.ColumnStyles[1].SizeType = SizeType.Percent;
                // tableLayoutPanel2.ColumnStyles[0].Width = 0;                
                // tableLayoutPanel2.ColumnStyles[1].Width = 100;
                // xtraTabControl1.Dock = DockStyle.Fill;
            }
            if (this.dulieu.vkieuform == 0)//Trả trước
            {
                this.Columns.colBtnChonSo = false;
                this.xTabKhoTinh = "Danh sách kho số trả trước viễn thông tỉnh";
                // xTabKhoTinh.PageVisible = true; // vuth update fix 1409
                // emptyEditor = new RepositoryItem();
                // grcDSKhoSo.RepositoryItems.Add(emptyEditor);
                this.NAP_COMBO();
                this.NAP_DS_SODANGKY();
                this.NAP_DS_KHOITAO();
                // #region Thiết lập lưới
                // grcDSKhoSo.setAllGridView_SpaceToggleCheck(false, true, false);
                // grcDS_ChuaDK.setAllGridView_SpaceToggleCheck(false, true, false);
                // #endregion
            }
        },
        async NAP_COMBO()
        {
            try
            {
                // #region COMBO prefix
                var loaihinh = -1;
                if (this.dulieu.vkieuform == 1)
                    loaihinh = LoaiHinhTB.DIDONGTRASAU;
                if (this.dulieu.vkieuform == 0)
                    loaihinh = LoaiHinhTB.DIDONGTRATRUOC;
                
                // var rs = await this.$root.context.post("/web-danhba/huonggiao/sp_huonggiao_runsql",{
                //     "command":`Select prefix NHOMSO_ID,prefix NHOMSO from ${DatabaseConstants.DB2}.prefix_lhtb where loaitb_id= ${loaihinh}`
                // });
                var rs = await this.$root.context.get("/web-hopdong/hopdong/lay_thongtin_nhomso_theo_loaitb_id?loaitb_id="+loaihinh);
                this.cboPrefix.list = this.UpperCasePropertyList(rs.data);
                if(this.cboPrefix.list && this.cboPrefix.list.length>0 && (!this.cboPrefix.value || this.cboPrefix.value<=0)) {
                    this.cboPrefix.value = this.cboPrefix.list[0].NHOMSO_ID;
                    this.cboPrefix_EditValueChanged();
                }
                // #endregion

                // #region COMBO trạng thái
                try
                {
                    rs = await this.$root.context.post("/ccbs/chonSo/ts_trangthai_dk_chonso",{});
                    var dt = this.UpperCasePropertyList(rs.data);
                    if (!dt || dt.length==0)
                    {
                        dt = [
                            { "NAME": "Không chọn", "ID": 0 },
                            { "NAME": "Đã đăng ký", "ID": 1 },
                            { "NAME": "Chưa đăng ký", "ID": 2 }
                        ];
                    } else {
                        dt.push({ "NAME": "Không chọn", "ID": 0 });
                        dt.sort((a,b)=>{a.ID-b.ID});
                    }
                }
                catch(ex)
                {
                    dt = [
                        { "NAME": "Không chọn", "ID": 0 },
                        { "NAME": "Đã đăng ký", "ID": 1 },
                        { "NAME": "Chưa đăng ký", "ID": 2 }
                    ];
                }
                finally {
                    this.loading(false);
                }
                this.cboTrangThai.list = dt;
                this.cboTrangThai.value = 0;
                // #endregion

                if (this.dulieu.vkieuform == 0)//trả truoc
                {
                    // columns = "STATUS";
                    // #region Nạp dữ liệu bảng chưa đăng ký
                    var dt_cdk = [{'ISCHECK':'0','SO_TB':'','TEN_KIEUSO':'','CUOCTHANG_TOITHIEU':'',}]
                    //     tdan.GET_DATA_SQL("select '0' ISCHECK ,'' SO_TB,'' TEN_KIEUSO,'' CUOCTHANG_TOITHIEU from dual");
                    // dt_cdk.Clear();
                    this.grcDS_ChuaDK.DataSource = dt_cdk;
                    // #endregion
                    // #region COMBO kho số tỉnh vuth update 1709  
                    var dt = [
                        {"TEN_KHO":"Kho số toàn quốc","KHO_ID":1},
                        {"TEN_KHO":"Kho số trả trước viễn thông tỉnh","KHO_ID":4},
                    ];
                    this.cboKhoSo.list = dt;
                    this.cboKhoSo.value = 1;
                    // #endregion
                }

                if (this.dulieu.vkieuform == 1) //trả sau
                {
                    // columns = "TRANGTHAI";
                    // #region COMBO kho số
                    rs = await this.$root.context.post("/ccbs/executor/ts_thongtin_khoso_khoitao",{
                        "ma_tinh": this.matinh,
                        "ip": await this.$root.token.getIP(),
                        "userid": this.ttnd['ma_nv_ccbs']
                    });
                    var dt_kho = this.UpperCasePropertyList(rs.data);//ccbs.ts_thongtin_khoso_khoitao(ttnd.USER_NEO, matinh);
                    var lingq_result = dt_kho.filter(x=>x.KHO_ID!=4);
                    if (lingq_result && lingq_result.length>0) {
                        this.cboKhoSo.list = lingq_result;
                        if(!this.cboKhoSo.value) this.cboKhoSo.value = lingq_result[0].KHO_ID
                    }
                    else
                        this.$toast.error("Không lấy được ds kho số");
                    // #endregion
                }
                if(this.cboKhoSo.list && this.cboKhoSo.list.length>0 && (!this.cboKhoSo.value|| this.cboKhoSo.value<=0))
                    this.cboKhoSo.value = this.cboKhoSo.list[0].KHO_ID;
            }
            catch (ex)
            {
                this.$toast.error("Có lôi khi nạp danh sách combo " + ex);
            }
            finally {
                this.loading(false);
            }
        },
        async NAP_DS_SODANGKY() {
            try
            {
                var rs = await this.$root.context.post("/ccbs/chonSo/ts_ds_chonso",{ //chonSo/ts_ds_chonso <- hàm Ngọc cung cấp, ra thiếu dữ liệu, đã đổi về hàm gốc. Hàm gốc: executor/tt_1_chonso_ds
                    "userid": this.ttnd['ma_nv_ccbs']
                });
                var ds = this.UpperCasePropertyList(rs.data);
                var dtDangkyChuaKhoiTao = [];
                // #region vuth update fix 1609
                // vuth update 1709 : loc theo user_neo vi ccbs tra ve ds dang ky theo user_neo
                if (ds && ds.length>0)
                {
                    // rs = await this.$root.context.post("/web-danhba/huonggiao/sp_huonggiao_runsql",{
                    //     "command":`SELECT somay, simcard, donvi_id, trangthaiso_id, ghichu,goicuoc_id,
                    //         ngay_cn, nguoidung_id, nguoi_cn, may_cn, ip_cn
                    //     FROM ${DatabaseConstants.DB2}.khoso_dd where user_neo=${this.$root.token.getProperty('ma_nhanvien_ccbs')} and  trangthaiso_id not in (4,5)`
                    // });
                    rs = await this.$root.context.get('/web-hopdong/hopdong/lay_thongtin_khoso_dd', {});
                    var dt_dakhoitao = this.UpperCasePropertyList(rs.data);//tdan.GET_DATA_SQL(@"SELECT somay, simcard, donvi_id, trangthaiso_id, ghichu,goicuoc_id, ngay_cn, nguoidung_id, nguoi_cn, may_cn, ip_cn FROM {?DB2}.khoso_dd where user_neo=:vuser_neo and  trangthaiso_id not in (4,5)", "vuser_neo", ttnd.USER_NEO);
                    if (dt_dakhoitao)
                    {
                        dtDangkyChuaKhoiTao = [];
                        for(var item of ds) if(!dt_dakhoitao.find(x=>(x.SOMAY==item.SO_TB) || ('84'+x.SOMAY==item.SO_TB) || (x.SOMAY=='84'+item.SO_TB))) dtDangkyChuaKhoiTao.push(item);
                        //dtDangkyChuaKhoiTao = ds.filter(x=>dt_dakhoitao.some(y=>y.SOMAY==x.SO_TB));
                        if(!dtDangkyChuaKhoiTao) dtDangkyChuaKhoiTao = [];
                    }
                    else
                    {
                        dtDangkyChuaKhoiTao = ds;
                    }
                }
                // #endregion 
                this.grcDS_DaDK.DataSource = dtDangkyChuaKhoiTao;
                //Doan phia tren o 2 kieuform giong nhau, chi khac o doan nay!
                if (this.dulieu.vkieuform == 1)
                {
                    rs = await this.$root.context.post("/ccbs/executor/ts_laphd_chonso_khotinh_phieudk",{
                        "userid": this.ttnd['ma_nv_ccbs']
                    });
                    var ds_chonso_khotinh_ts = this.UpperCasePropertyList(rs.data);//ccbs.ts_laphd_chonso_khotinh_phieudk(ttnd.USER_NEO);
                    var dtDaDK = [];
                    for (var dr of dtDangkyChuaKhoiTao)
                    {
                        var tmp = {};
                        tmp["SO_TB"] = dr["SO_TB"];
                        tmp["NGAYTH"] = dr["NGAYTH"];
                        tmp["THOIGIAN_CON"] = dr["THOIGIAN_CON"];
                        tmp["KHO_ID"] = this.KHO_TQ_ID;
                        dtDaDK.push(tmp);
                    }
                    if (ds_chonso_khotinh_ts && ds_chonso_khotinh_ts.length>0) // vuth update 0710
                    {
                        for (var dr of ds_chonso_khotinh_ts)
                        {
                            var tmp = {};
                            tmp["SO_TB"] = dr["SO_TB"];
                            tmp["NGAYTH"] = dr["NGAY_TH"];
                            var NGAY_HH = moment(dr['NGAY_HH']);
                            var thoigian_con = moment.duration(moment(new Date()).diff(NGAY_HH.isValid?NGAY_HH:dr['NGAY_HH']));
                            tmp["THOIGIAN_CON"] = thoigian_con.asMinutes();
                            tmp["KHO_ID"] = this.KHO_TINH_ID;
                            dtDaDK.push(tmp);
                        }
                    }

                    this.grcDS_DaDK.DataSource = dtDaDK;
                }


            }
            catch (ex)
            {
                this.$toast.error("Có lỗi khi nạp danh sách số đã đăng ký " + ex);
            }
            finally {
                this.loading(false);
            }
        },
        async NAP_DS_KHOITAO() {
            try
            {
                var loaitb_id = this.dulieu.vkieuform == 0 ? "21" : "20";
                this.grcDSKhoiTao.DataSource = [];
                // rs = await this.$root.context.post("/web-danhba/huonggiao/sp_huonggiao_runsql",{
                //     "command":`SELECT somay, simcard, donvi_id, trangthaiso_id, ghichu, a.goicuoc_id,
                //         ngay_cn, nguoidung_id, nguoi_cn, may_cn, ip_cn
                //         FROM ${DatabaseConstants.DB2}.khoso_dd a, ${DatabaseConstants.DB2}.goicuoc_dd b
                //         where nguoidung_id =${this.$root.token.getNguoiDungID()} and trangthaiso_id=1
                //     and a.goicuoc_id = b.goicuoc_id and b.loaitb_id=${loaitb_id}`
                // });
                var rs = await this.$root.context.get('/web-hopdong/hopdong/lay_thongtin_khoso_dd_theo_loaitb_id?loaitb_id='+loaitb_id,{});
                this.grcDSKhoiTao.DataSource = this.UpperCasePropertyList(rs.data);
            }
            catch (ex)
            {
                this.$toast.error("Có lỗi khi nạp danh sách số đã đăng ký " + ex);
            }
            finally {
                this.loading(false);
            }
        },
        btnLayTT_Click() {
            this.TRACUU_KHOSO_TQ();
        },
        async TRACUU_KHOSO_TQ2(...params) {
            var parameters = params&&params.length==1?params[0]:params;
            // #region xu ly tra truoc 
            if (this.dulieu.vkieuform == 0)
            {
                if (this.khoso_dangchon == 0) // nếu trả trước và ở tab kho số toàn quốc -- vuth update fix 1609
                {
                    // #region  Nạp dữ liệu  kho số toàn quốc
                    var rs = await this.$root.context.post("/ccbs/chonSo/tim_kiem_so",{
                        "kieuso_id": parameters[1]+'',
                        "page_index": parameters[5]+'',
                        "prefix": parameters[2]+'',
                        "so_tb": parameters[0]+'',
                        "status": parameters[3]+''
                    });
                    var dt = rs.data;//ccbs.tt_1_tim_kiem_so(parameters[0].ToString(), parameters[1].ToString(),
                    //                                parameters[2].ToString(),
                    //                                parameters[3].ToString(), parameters[4].ToString(), parameters[5].ToString(), ttnd.ip, ttnd.tentat_ccbs);
                    //Điền thêm page_index = 0 để build không lỗi
                    //0 ma tb
                    //1 kieuso id
                    //2 prefix
                    //3 trangthai
                    //4 user id
                    //5 so trang
                    if(!dt && rs.message) throw rs.message;
                    else dt = this.UpperCasePropertyList(dt);
                    if (dt.length > 0)
                    if(dt.length==1 && dt[0].TEN_KIEUSO && dt[0].TEN_KIEUSO.indexOf('Loi thuc hien:')==0)
                        throw dt[0].TEN_KIEUSO;
                    else
                    for(var item of dt)
                    {
                        item.ISCHECK = 0;
                        item.STATUS_TT = 0;
                    }
                    return dt;// sender.SetOutput(dt);
                    // #endregion
                }
                else // vuth update fix 1609
                {
                    if (parameters[1] != this.KHO_TINH_ID )
                    {
                        // #region  Nạp dữ liệu  kho all trừ kho chung tỉnh
                        var rs = await this.$root.context.post("/ccbs/executor/ts_danhsach_khoso_khoitao_tb",{
                            "kho": parameters[1],
                            "ma_tinh": this.matinh,
                            "prefix": parameters[4],
                            "so_tb": parameters[0],
                            "xac_thuc": parameters[3],
                            "userid": parameters[2],
                            "seq": parameters[5]
                        });
                        var dt = this.UpperCasePropertyList(rs.data);//ccbs.ts_danhsach_khoso_khoitao_tb(parameters[0].ToString(), parameters[3].ToString(),
                                                                //    parameters[4].ToString(), matinh.ToUpper(),
                                                                //    parameters[1].ToString(), parameters[2].ToString(), parameters[5].ToString());
                        //Điền thêm page_index = 0 để build không lỗi
                        //0 ma tb
                        //1 kh_id id
                        //2 prefix
                        //3 xac thuc
                        //4 user id
                        if (dt)
                        for(var i=0; i<dt.length;i++)
                        {
                            dt[i].STT = i+1;
                        }
                        return dt;//sender.SetOutput(dt);
                        // #endregion
                    }
                    else
                    {
                        // #region  Nạp dữ liệu  kho chung viễn thông tỉnh
                        var rs = await this.$root.context.post("/ccbs/executor/ts_laphd_chonso_khotinh_layds", {
                            "prefix": parameters[2],
                            "so_trang": "0",
                            "ip": "", // khong co
                            "so_tb": parameters[0],
                            "kieuso_id": parameters[3], // truoc la [1] là kho số, kiểm tra sai đổi về kiểu  số
                            "userid": parameters[4]
                        });
                        var dt = this.UpperCasePropertyList(rs.data);//ccbs.ts_laphd_chonso_khotinh_layds(parameters[0].ToString(), parameters[3].ToString(),
                                                                    //    parameters[2].ToString(), parameters[4].ToString(),
                                                                    //    parameters[5].ToString());
                        //Điền thêm page_index = 0 để build không lỗi
                        //0 ma tb
                        //1 kho_id id
                        //2 prefix
                        //3 kieuso
                        //4 user id
                        if (dt && dt.length>0)
                        for(var item of dt)
                        {
                            item.STATUS_TT = 0;
                        }
                        return dt; //sender.SetOutput(dt);
                        // #endregion
                    }
                }
            }
            // #endregion xong xu ly tra truoc
            
            if (this.dulieu.vkieuform == 1)
            {
                if (this.cboKhoSo.value == this.KHO_TQ_ID)
                {
                    // #region  Nạp dữ liệu  kho số toàn quốc
                    var rs = await this.$root.context.post("/ccbs/chonSo/tim_kiem_so",{
                        "kieuso_id": parameters[1]+'',
                        "page_index": parameters[5]+'',
                        "prefix": parameters[2]+'',
                        "so_tb": parameters[0]+'',
                        "status": parameters[3]+''
                    });
                    var dt = rs.data;//ccbs.tt_1_tim_kiem_so(parameters[0].ToString(), parameters[1].ToString(),
                    //                                parameters[2].ToString(),
                    //                                parameters[3].ToString(), parameters[4].ToString(), parameters[5].ToString(), ttnd.ip, ttnd.tentat_ccbs);
                    //Điền thêm page_index = 0 để build không lỗi
                    //0 ma tb
                    //1 kieuso id
                    //2 prefix
                    //3 trangthai
                    //4 user id
                    //5 so trang
                    if(!dt && rs.message) throw rs.message;
                    else dt = this.UpperCasePropertyList(dt);
                    //var dt = this.UpperCasePropertyList(rs.data); //ccbs.tt_1_tim_kiem_so(parameters[0].ToString(), parameters[1].ToString(),
                                                //    parameters[2].ToString(),
                                                //    parameters[3].ToString(), parameters[4].ToString(), parameters[5].ToString(), ttnd.ip, ttnd.tentat_ccbs);
                    //Điền thêm page_index = 0 để build không lỗi
                    //0 ma tb
                    //1 kieuso id
                    //2 prefix
                    //3 trangthai
                    //4 user id
                    //5 so trang
                    if (dt.length > 0)
                    for(var item of dt)
                    {
                        item.ISCHECK = 0;
                        item.STATUS_TT = 0;
                    }
                    return dt; //sender.SetOutput(dt);
                    // #endregion
                }

                else if (this.cboKhoSo.value != this.KHO_TINH_ID)//(parameters[1] != this.KHO_TINH_ID)
                {
                    // #region  Nạp dữ liệu  kho all trừ kho chung tỉnh
                    var rs = await this.$root.context.post("/ccbs/executor/ts_danhsach_khoso_khoitao_tb",{
                        "kho": parameters[1],
                        "ma_tinh": this.matinh,
                        "prefix": parameters[2],
                        "so_tb": parameters[0],
                        "xac_thuc": parameters[3],
                        "userid": parameters[4],
                        "seq": parameters[5]
                    });
                    var dt = this.UpperCasePropertyList(rs.data);//ccbs.ts_danhsach_khoso_khoitao_tb(parameters[0].ToString(), parameters[3].ToString(),
                                                            //    parameters[4].ToString(), matinh.ToUpper(),
                                                            //    parameters[1].ToString(), parameters[2].ToString(), parameters[5].ToString());
                    //Điền thêm page_index = 0 để build không lỗi
                    //0 ma tb
                    //1 kh_id id
                    //2 prefix
                    //3 xac thuc
                    //4 user id
                    if (dt)
                    for(var i=0; i<dt.length;i++)
                    {
                        dt[i].STT = i+1;
                    }
                    return dt;//sender.SetOutput(dt);
                    // #endregion
                }
                else
                {
                    // #region  Nạp dữ liệu  kho chung viễn thông tỉnh
                    var rs = await this.$root.context.post("/ccbs/executor/ts_laphd_chonso_khotinh_layds", {
                        "prefix": parameters[2],
                        "so_trang": "0",
                        "ip": "",
                        "so_tb": parameters[0],
                        "kieuso_id": parameters[3], // sua [1] -> [3] do sai dieu kien
                        "userid": parameters[4]
                    });
                    var dt = this.UpperCasePropertyList(rs.data);//ccbs.ts_laphd_chonso_khotinh_layds(parameters[0].ToString(), parameters[3].ToString(),
                                                                //    parameters[2].ToString(), parameters[4].ToString(),
                                                                //    parameters[5].ToString());
                    //Điền thêm page_index = 0 để build không lỗi
                    //0 ma tb
                    //1 kho_id id
                    //2 prefix
                    //3 kieuso
                    //4 user id
                    if (dt && dt.length>0)
                    for(var item of dt)
                    {
                        item.STATUS_TT = 0;
                    }
                    return dt; //sender.SetOutput(dt);
                    // #endregion
                }
            }
            // #endregion
        },
        async TRACUU_KHOSO_TQ() {
            try
            {
                // #region Nạp danh sách
                // #region Kiểm tra đầu vào
                if (this.vprefix == "-1")
                {
                    this.$toast.error("Không tìm thấy dữ liệu đầu số");
                    return;
                }
                if (this.vkieusso_id == "-1")
                {
                    this.$toast.error("Không tìm thấy dữ liệu kiểu số");
                    return;
                }
                if (this.vstatus == "-1")
                {
                    this.$toast.error("Không tìm thấy dữ liệu trạng thái");
                    return;
                }
                // #endregion

                var v2 = "";
                var v3 = "";
                var pageNumber = 1;
                switch (this.dulieu.vkieuform)//neu la tra truoc thi lay kieu so va trang thai ,tra sau lay kho va ma xac thuc
                {
                    case 0:
                        if (this.khoso_dangchon == 0) // tab kho toan quoc
                        {
                            v2 = this.vkieusso_id;
                            v3 = this.vstatus;
                            pageNumber = this.pageNumberTQ; //thangdm1: bien nay chua hieu lay o dau! Phải check app cu.
                            this.grcDSKhoSo.DataSource = [];
                            break;
                        }
                        else // tab kho tinh
                        {
                            if (this.vkho_id == this.KHO_TINH_ID)
                            {
                                if (this.vma_tb == "")
                                {
                                    this.$toast.error("Bạn phải nhập số thuê bao");
                                    // txtPrefix.Focus();
                                    return;
                                }
                                pageNumber = this.pageNumberTQ;
                                v2 = this.vkho_id;
                                v3 = this.vkieusso_id;
                                this.grcDSKhoSo.DataSource = [];
                            }
                            else
                            {
                                this.grcDSAllKho.DataSource = [];
                                pageNumber = this.pageNumberAllKho;
                                v2 = this.vkho_id;
                                v3 = this.vma_xacthuc;
                            }
                            break;
                        }
                    case 1:
                        if (this.vkho_id == this.KHO_TINH_ID) 
                        {
                            if (this.vma_tb == "")
                            {
                                this.$toast.error("Bạn phải nhập số thuê bao");
                                // txtPrefix.Focus();
                                return;
                            }
                            pageNumber = this.pageNumberTQ;
                            v2 = this.vkho_id;
                            v3 = this.vkieusso_id;
                            this.grcDSKhoSo.DataSource = [];
                        }
                        else if (this.vkho_id == this.KHO_TQ_ID) // vuth fix 0410
                        {
                            this.gcKHOTQTraSau.DataSource = [];
                            pageNumber = this.pageNumberTQTraSau;
                            v2 = this.vkieusso_id;
                            v3 = this.vstatus;
                        }
                        else
                        {
                            this.grcDSAllKho.DataSource = [];
                            pageNumber = this.pageNumberAllKho;
                            v2 = this.vkho_id;
                            v3 = this.vma_xacthuc;
                        }
                        break;
                }
                var oriText = $(".overlay-common .loading-content").text();
                this.loading(true);
                $(".overlay-common .loading-content").text('Đang lấy dữ liệu nạp...');
                var output = await this.TRACUU_KHOSO_TQ2([this.vma_tb, v2, this.vprefix_all, v3, this.ttnd.user_neo, pageNumber?pageNumber:1]);
                this.loading(false);
                $(".overlay-common .loading-content").text(oriText);
                //var frm = new frmDangTai(this.TRACUU_KHOSO_TQ, this.vma_tb, v2, this.vprefix_all, v3, this.ttnd.user_neo, pageNumber);
                // frm.SetText("Đang lấy dữ liệu nạp...", "Đang lấy dữ liệu ...");
                // frm.ShowCounter = true;
                if (this.dulieu.vkieuform == 0) 
                {
                    if (this.khoso_dangchon == 0) //vuth update fixed 1609
                    {
                        // #region Trả trước tab kho toàn quốc
                        this.grcDSKhoSo.DataSource = [];
                        if (output)
                            this.grcDSKhoSo.DataSource = output;
                        else {
                            this.$toast.error("Dữ liệu tìm kiếm bị lỗi: " + output);
                            return;
                        }
                        this.CAP_NHAT_TRANGTHAI();
                        // #endregion
                    }
                    else // nếu kho tỉnh
                    {
                        if (this.cboKhoSo.value > 0)
                        {
                            this.xTabKhoTinh = "" + this.cboKhoSo.list.filter(x=>x.KHO_ID==this.cboKhoSo.value)[0].TEN_KHO;
                        }
                        // colTrangThai.FieldName = columns; //FIXME chua biet lam gi, tam comment lai
                        if (this.vkho_id == this.KHO_TINH_ID)//neu la kho chung
                        {
                            // colSTATUS_TT.Visible = false; //FIXME tam comment lai, code sau.
                            this.xTabKhoTQ = "Danh sách kho số chung viễn thông tỉnh";
                            this.grcDSKhoSo.DataSource = [];
                            if (output)
                            {
                                if (output && output.length==1 && !output[0]["SO_TB"])
                                    return;
                                this.grcDSKhoSo.DataSource = output;
                            }
                            // CAP_NHAT_TRANGTHAI();
                        }
                        else
                        {
                            this.grcDSAllKho.DataSource = [];
                            if (output)
                            {
                                var dt_khosoconlai = [];  // vuth update fix 1709 : lọc bỏ những số đã khởi tạo/đã dùng
                                if (output && output.length == 1 && !output[0]["ID"])
                                {
                                    this.$toast.error("" + output[0]["MSI_KHO"]);
                                    return;
                                }

                                // var rs1 = await this.$root.context.post("/web-danhba/huonggiao/sp_huonggiao_runsql",{
                                //     "command":`SELECT somay, simcard, donvi_id, trangthaiso_id, ghichu,goicuoc_id,
                                //         ngay_cn, nguoidung_id, nguoi_cn, may_cn, ip_cn
                                //         FROM ${DatabaseConstants.DB2}.khoso_dd where user_neo=${this.ttnd.user_neo} and  trangthaiso_id not in (4,5)
                                //     `
                                // });
                                var rs1 = await this.$root.context.get('/web-hopdong/hopdong/lay_thongtin_khoso_dd',{});
                                var dt_khosodadung = this.UpperCasePropertyList(rs1.data);
                                var linqResult = output.filter(x=>dt_khosodadung.some(y=>x.MSISDN.substring(2)==y.SOMAY));
                                if (linqResult && linqResult.length>0)
                                    dt_khosoconlai = linqResult;
                                this.grcDSAllKho.DataSource = dt_khosoconlai;
                            }
                        }
                    } 
                }
                if (this.dulieu.vkieuform == 1)
                {
                    // #region Trả sau
                    if (this.cboKhoSo.value > 0 && this.vkho_id != this.KHO_TINH_ID && this.vkho_id != this.KHO_TQ_ID)
                        this.xTabKhoTinh = "" + this.cboKhoSo.list.filter(x=>x.KHO_ID==this.cboKhoSo.value)[0].TEN_KHO;
                    // colTrangThai.FieldName = columns; //FIXME chua lam dong nay
                    if (this.vkho_id == this.KHO_TINH_ID)//neu la kho chung
                    {
                        //colSTATUS_TT.Visible = false; //FIXME chua lam dong nay
                        this.xTabKhoTQ = " Danh sách kho số chung viễn thông tỉnh";
                        this.grcDSKhoSo.DataSource = [];
                        if (output)
                        {
                            if (output && output.length == 1)
                            {
                                if (output[0]["TEN_KIEUSO"] == "Yeu cau dang nhap he thong truoc khi tim kiem")
                                {
                                    this.$toast.error("Kiểm tra lại phân quyền của tài khoản người dùng!");
                                    return;
                                }
                                else
                                    if (output[0]["TEN_KIEUSO"] == "Ban phai nhap gia tri can tim ket hop voi ky tu * de tim gan chinh xac")
                                    {
                                        this.$toast.error("Nhập thêm giá trị kết hợp với ký tự * để tìm kiếm!");
                                        return;
                                    }
                                    else
                                        if (!output[0]["SO_TB"])
                                            return;
                            }
                            this.grcDSKhoSo.DataSource = output;
                        }
                        var triggerFirstTabEl = document.querySelector('#xTabKhoSo a[href="#tabdskstq"]')
                        triggerFirstTabEl.click();
                        // console.info('TRACUU_KHOSO_TQ',bootstrap,triggerFirstTabEl);
                        // bootstrap.Tab.getInstance(triggerFirstTabEl).show()
                        // xTabKhoSo.SelectedTabPageIndex = 0; // vuth update fix ts 0210
                        // CAP_NHAT_TRANGTHAI();
                    }
                    else if (this.vkho_id == this.KHO_TQ_ID)
                    {
                        // #region Trả sau tab kho gv chon so toàn quốc
                        this.gcKHOTQTraSau.DataSource = [];
                        if (output)
                        {
                            //Bo vi cho nay la column count -> dem so properties
                            // if (output && output.length == 1)
                            // {

                            //     this.$toast.error("Dữ liệu tìm kiếm bị lỗi: " + output[0][0]);
                            //     return;
                            // }
                            this.gcKHOTQTraSau.DataSource = output;
                        }
                    }
                    else
                    {
                        this.grcDSAllKho.DataSource = [];
                        if (output)
                        {
                            if (output && output.length == 1)
                            {
                                if (!output[0]["ID"])
                                {
                                    this.$toast.error("" + output[0]["MSI_KHO"]);
                                    return;
                                }
                            }
                            this.grcDSAllKho.DataSource = output;
                        }
                    }
                    // #endregion
                }
                // #endregion
            }
            catch (ex)
            {
                this.$toast.error("Có lỗi khi tìm kiếm số" + ex);
            }
            finally {
                this.loading(false);
            }
        },
        CAP_NHAT_TRANGTHAI()
        {
            try
            {
                var dt = this.grcDSKhoSo.DataSource;
                if (!dt || dt.length==0)
                    return;
                var dt_cdk = this.grcDS_ChuaDK.DataSource;
                if (!dt_cdk)
                    for (var dr of dt)
                        dr["STATUS_TT"] = dr["TRANGTHAI"];
                else
                {
                    if (dt_cdk.length == 0)
                        for (var dr of dt)
                            dr["STATUS_TT"] = dr["TRANGTHAI"];
                    else
                    {
                        for (var dr of dt)
                        {
                            var banghi = dt_cdk.filter(x=>x.SO_TB==dr["SO_TB"]).length;
                            if (banghi > 0)
                                dr["STATUS_TT"] = "2";
                            else
                                dr["STATUS_TT"] = dr["TRANGTHAI"];
                        }
                    }
                }
            }
            catch (ex)
            {
                this.$toast.error("Có lỗi khi cập nhật trạng thái " + ex);
            }
            finally {
                this.loading(false);
            }
        },
        btnRefresh_Click()
        {
            this.NAP_DS_SODANGKY();
            this.NAP_DS_KHOITAO();
        },
        closeForm() {
            this.$emit('form-close',null);
        },
        tsbtnChonSo_Click()
        {
            this.CHONSO_DK();
        },
        CHONSO_DK()
        {
            try
            {
                // #region Kiểm tra
                var dt = this.grcDSKhoSo.DataSource;
                if (!dt)
                {
                    this.$toast.error("Không lấy được dữ liệu danh sách kho số");
                    return;
                }
                if (dt.length == 0)
                {
                    this.$toast.error("Chưa có dữ liệu kho số để chọn");
                    return;
                }
                var banghi = dt.filter(x=>x.ISCHECK==1).length;
                if (banghi == 0)
                {
                    this.$toast.error("Chưa có số máy nào được chọn");
                    return;
                }

                var ds_cdk = this.grcDS_ChuaDK.DataSource;
                if (!ds_cdk)
                {
                    this.$toast.error("Không lấy được dữ liệu danh sách chưa đăng ký");
                    return;
                }
                // #endregion

                // #region Gán dữ liệu
                for (var dr of dt.filter(x=>x.ISCHECK==1))
                {
                    if (this.dulieu.vkieuform == 0 && ((dr["THOIGIAN_SUDUNG"]+'').replace("Tháng","").trim())*1 > 0 )
                    { // vuth update fix 1409
                        this.$toast.error("Bạn đã chọn nhầm số cam kết trả sau , xin vui lòng chọn lại !!!");
                        return;
                    }
                    dr["ISCHECK"] = "0";
                    if (dr["TRANGTHAI"] == 1)
                        continue;
                    var row = {};
                    row["SO_TB"] = dr["SO_TB"];
                    row["ISCHECK"] = "0";
                    row["TEN_KIEUSO"] = dr["TEN_KIEUSO"];
                    row["CUOCTHANG_TOITHIEU"] = dr["CUOCTHANG_TOITHIEU"];
                    ds_cdk.push(row);
                }
                // #endregion
                // #region Distinct lại dữ liệu
                const unique = (arr, props = []) => [...new Map(arr.map(entry => [props.map(k => entry[k]).join('|'), entry])).values()];
                // var view = new DataView(ds_cdk);
                var distinctValues = unique(ds_cdk,["SO_TB", "TEN_KIEUSO", "CUOCTHANG_TOITHIEU"]);//view.ToTable(true, "SO_TB", "TEN_KIEUSO", "CUOCTHANG_TOITHIEU");
                for(var item of distinctValues)
                    item.ISCHECK = 0;
                this.grcDS_ChuaDK.DataSource = distinctValues;
                // #endregion

                this.CAP_NHAT_TRANGTHAI();
            }
            catch (ex)
            {
                this.$toast.error("Có lỗi khi chọn số " + ex);
            }
            finally {
                this.loading(false);
            }
        },
        btnNhapMoi_Click()
        {
            NHAP_SO();
        },
        NHAP_SO()
        {
            try
            {
                var ds_cdk = this.grcDS_ChuaDK.DataSource;
                if (!ds_cdk)
                {
                    this.$toast.error("Không lấy được dữ liệu danh sách chưa đăng ký");
                    return;
                }
                var f = prompt("Nhập danh sách số máy");//new frmNhapText("Nhập danh sách số máy ");
                // f.Text = "Nhập danh sách số máy";
                if (f) // nếu thay đổi
                {
                    var s = f.trim();//f.Output.TrimEnd(", ".ToCharArray()); ;
                    var ds_tb = s.split(',');
                    for (var r of ds_tb)
                    {
                        var banghi = ds_cdk.filter(x=>x.SO_TB=='84' + r.trim() || x.SO_TB==r.trim()).length;
                        if (banghi > 0)
                        {
                            continue;
                        }
                        var row = {};
                        row["ISCHECK"] = "0";
                        row["TEN_KIEUSO"] = "Chọn số thủ công";
                        row["CUOCTHANG_TOITHIEU"] = "Chọn số thủ công";
                        if (r.trim().indexOf("84")==0)
                        {
                            row["SO_TB"] = "0" + r.trim().substring(2);
                        }
                        else
                        {
                            row["SO_TB"] = r.trim();
                        }
                        ds_cdk.push(row);
                    }
                    // #region Distinct lại dữ liệu
                    const unique = (arr, props = []) => [...new Map(arr.map(entry => [props.map(k => entry[k]).join('|'), entry])).values()];
                    // var view = new DataView(ds_cdk);
                    var distinctValues = unique(ds_cdk,["SO_TB", "TEN_KIEUSO", "CUOCTHANG_TOITHIEU"]);//view.ToTable(true, "SO_TB", "TEN_KIEUSO", "CUOCTHANG_TOITHIEU");
                    for(var item of distinctValues)
                        item.ISCHECK = 0;
                    this.grcDS_ChuaDK.DataSource = distinctValues;
                    // #endregion
                }
            }
            catch (ex)
            {
                this.$toast.error("Có lỗi khi nhập số máy " + ex);
            } finally {
                this.loading(false);
            }
        },
        btnXoaTB_Click()
        {
            this.XOA_DS_CHUA_DK();
        },
        XOA_DS_CHUA_DK()
        {
            try
            {
                // #region  Kiểm tra
                var dt = this.grcDS_ChuaDK.DataSource;
                if (!dt)
                {
                    this.$toast.error("Không lấy được dữ liệu danh sách chưa đăng ký");
                    return;
                }
                if (dt.length == 0)
                {
                    this.$toast.error("Không có dữ liệu số máy để xóa");
                    return;
                }
                var banghi = dt.filter(x=>x.ISCHECK==1).length;
                if (banghi == 0)
                {
                    this.$toast.error("Chưa có số máy nào được chọn");
                    return;
                }
                // #endregion
                // #region Xóa dữ liệu
                this.grcDS_ChuaDK.DataSource = dt.filter(x=>x.ISCHECK!=1);
                // #endregion

                this.CAP_NHAT_TRANGTHAI();
            }
            catch (ex)
            {
                this.$toast.error("Có lỗi khi xóa danh sách chưa đăng ký " + ex);
            }
            finally {
                this.loading(false);
            }
        },
        btnCapNhat_Click() {
            this.DANGKY();
        },
        async DANGKY()
        {
            try
            {
                var dt = this.grcDS_ChuaDK.DataSource;
                if (!dt)
                {
                    this.$toast.error("Không lấy được dữ liệu danh sách số đang chờ đăng ký");
                    return;
                }
                if (dt.length == 0)
                {
                    this.$toast.error("Chưa có danh sách số máy để đăng ký");
                    return;
                }
                var dachon = dt.filter(x=>x.ISCHECK==1).length;
                if (dachon == 0) //vuth update fix 0210
                {
                    this.$toast.error("Chưa có số máy nào được chọn");
                    return;
                }
                var cfr = await this.$bvModal.msgBoxConfirm("Bạn có chắc chắn muốn đăng ký danh sách các số này không?", {
                    title: 'Xác nhận hành động',
                    size: 'sm',
                    okTitle: 'Đồng ý',
                    cancelTitle: 'Hủy',
                }).then(async v => {
                    return v;
                });
                if (!cfr)
                    return;
                // vuth update fix 2409 them capcha
                this.popupComponentData = {"kieu":this.dulieu.vkieuform,"chonso":this.dulieu.vkieuform==0,"sotb_goc":so_tb_kt,"khoid":this.dulieu.vkieuform==1?e.rowData.KHO_ID:null};
                this.popupComponent = ()=>import('./popupCaptcha');
                this.popupComponentName = "popupCaptcha";
                this.Popup('popupComponentsKhoiTaoTB');
                // frmCaptcha frm = new frmCaptcha();
                // frm.ShowDialog();
                // if (frm.Result != "OK")
                // {
                //     Message_Box.ShowTB("Chưa xác nhận được capcha, không thể đăng ký giữ số !!!");
                //     return;
                // }
                

            }
            catch (ex)
            {
                this.$toast.error("Có lỗi khi đăng ký số máy " + ex);
            }
            finally {
                this.loading(false);
            }

        },
        // HienThiDanhSach: async function() {
        //     this.loading(true);
        //     var rs = await this.$root.context.post("/web-hopdong/lapdatmoi/lay_ds_khoso_new", {
        //         "vdichvuvt_id": this.dichvuvt_id,
        //         "vdonvi_ql_id": this.donvi_ql_id,
        //         "vkieuso_id": this.kieuso.value,
        //         "vloaiso_id": this.loaiso.value,
        //         "vloaitb_id": this.loaitb_id,
        //         "vnguoidung_id": this.$root.token.getNguoiDungID(),
        //         "vnhomso_id": this.nhomso.value,
        //         "vphuong_id": this.diachi.phuong_id,
        //         "vquan_id": this.diachi.quan_id,
        //     });
        //     this.loading(false);
        //     if(rs.data && rs.data.length>0) {
        //         this.somay.list = rs.data;
        //         if(this.somay.list && this.somay.list.length>0) this.dsSoMayChanged(0,this.somay.list[0]);
        //     }
        //     else 
        //     {
        //         this.somay.list = [];
        //     }
        // },
        // selectedItemsChanged(items) {
        //     this.somay.valuearr = items;
        //     this.tiensodep = items.map(x=>x.tien_hm).reduce((a, b) => a + b, 0)
        // },
        chapnhan: function () {
            var retData = {
                so_tb_chon:this.vthuebao_chon.indexOf("84")==0?this.vthuebao_chon.substring(2):this.vthuebao_chon,
                so_sim_chon: this.vsimcard,
                goicuoc_id_chon: this.vgoicuoc_id,
                kieuform: this.dulieu.vkieuform,
            };//{somay:this.somay.value,loaiso:this.loaiso.value,arraySoMay:this.somay.valuearr,nhomso:this.nhomso.value,tiensodep:this.tiensodep};
            if(retData.so_tb_chon!=-1) {
                this.$emit("form-close", retData);
                this.$bvModal.hide(this.modalId);
            } else {
                if (retData.so_tb_chon != -1)
                {
                    this.$toast.error("Không lấy được số máy đã chọn");
                    return;

                }
                if (retData.so_sim_chon != -1)
                {
                    this.$toast.error("Không lấy được sim card của số máy");

                }
            }
        },
        async cboPrefix_EditValueChanged() {
            
            if (this.vprefix == "-1")
                return;

            // #region COMBO kiểu số

            if (this.dulieu.vkieuform == 0 || this.dulieu.vkieuform == 1)
            {
                var rs = await this.$root.context.post("/ccbs/executor/tt_1_kieuso",{"prefix":this.vprefix_all});
                var dt = this.UpperCasePropertyList(rs.data);//ccbs.tt_1_kieuso(this.vprefix_all);
                if (!dt || dt.length == 0)
                {
                    this.$toast.error("Không lấy được dữ liệu kiểu số từ VNP");
                    return;
                }
                dt.push({"TEN_KIEUSO":"Không chọn", "KIEUSO_ID":0});
                this.CboKieuSo.list = dt;
                if(!this.CboKieuSo.value) this.CboKieuSo.value = 0;
                // dt.AcceptChanges();
                // DataView dv = dt.DefaultView;
                // dv.Sort = "KIEUSO_ID";
                // this.SetColumnsOrder(dv.Table, "KIEUSO_ID", "TEN_KIEUSO");
                // bts.HT_DS_GRIDLOOKUP(CboKieuSo, dv.Table, "TEN_KIEUSO", "KIEUSO_ID", true, 2, 400);
            }
            // if (vkieuform == 1) // bo do code giong phia tren!!!
            // {
            //     var rs = await this.$root.context.post("/ccbs/executor/tt_1_kieuso",{"prefix":this.vprefix_all});
            //     var dt = rs.data;//ccbs.tt_1_kieuso(this.vprefix_all);
            //     if (!dt || dt.length == 0)
            //     {
            //         this.$toast.error("Không lấy được dữ liệu kiểu số từ VNP");
            //         return;
            //     }
            //     if (!dt || dt.length == 0)
            //     {
            //         this.$toast.error("Không lấy được dữ liệu kiểu số từ VNP");
            //         return;
            //     }
            //     dt.push({"TEN_KIEUSO":"Không chọn", "KIEUSO_ID":0});
            //     this.CboKieuSo.list = dt;
            //     if(!this.CboKieuSo.value) this.CboKieuSo.value = 0;
            // }

            // #endregion

        },

        UpperCasePropertyList(obj) {
            if(!obj || !obj.length || obj.length<=0) return obj;
            return obj.map( function( item ){
                for(var key in item){
                    var upper = key.toUpperCase();
                    // check if it already wasn't uppercase
                    if( upper !== key ){
                        item[ upper ] = item[key];
                        delete item[key];
                    }
                }
                return item;
            });
        },
        // async dsSoMayChanged(idx,item) {
        //     if(item) this.somay.value = item.somay;
        // },
        // async btnGiuSoClicked() {
        //     this.loading(true);
        //     var ketqua = await this.$root.context.post("/web-hopdong/lapdatmoi/sp_capnhat_giuso",{
        //         "dichvuvt_id": this.dichvuvt_id,
        //         "js_somay": JSON.stringify(this.somay.valuearr),
        //         "nguoidung_id": this.$root.token.getNguoiDungID()
        //     });
        //     if(ketqua && ketqua.message.toLowerCase() == "success")
        //         this.$toast.success(ketqua.message);
        //     else this.$toast.error(ketqua.message);
        //     this.loading(false);
        //     if(rs.data && rs.data.length>0) {
        //         this.popupComponent = ()=>import('./popupGiuSo');
        //         this.popupComponentName = "popupGiuSo";
        //         this.popupComponentData = {"dichvuvt_id": this.dichvuvt_id, "nguoidung_id": this.$root.token.getNguoiDungID()}
        //         this.Popup('popupComponents');
        //     } else this.$toast.error(rs.message+ (rs.message_detail?': '+rs.message_detail:''));
        // },
        // async TimKiemEnter() {
        //     if(this.timkiem.length <= 5) {
        //         this.$toast.error("Số máy phải lớn hơn 5 ký tự !");
        //         return;
        //     }
        //     var ktra = false;
        //     var kw = this.timkiem;
        //     var dauso = this.ds_dauso.filter(x=>kw.substring(0,6).indexOf(x.prefix)==0);
        //     if(dauso && dauso.length>0) {
        //         this.dauso = dauso[0].prefix;
        //         //TODO Chon row cua dau so
        //         var listsm = this.somay.list.filter(x=>x.somay==kw.trim());
        //         if(listsm && listsm.length>0) {
        //             //TODO chon row cua somay
        //             this.somay.value = this.timkiem.trim();
        //             ktra = true;
        //         }
        //     }
        //     if(!ktra) {
        //         this.$toast.error("Số máy : '" + this.timkiem.trim() + "' không có trong kho số hoặc đã được cấp !");
        //         return;
        //     }
        //     this.tsbtnIn = true;
        // },

        async KHOTQ_CHONSO_TS()
        {
            try
            {

                var cfr = await this.$bvModal.msgBoxConfirm("Bạn có chắc chắn muốn chọn số " + this.vsomay_toanquoc_trasau + " không ?", {
                    title: 'Xác nhận hành động',
                    size: 'sm',
                    okTitle: 'Đồng ý',
                    cancelTitle: 'Hủy',
                }).then(async v => {
                    return v;
                });
                if (!cfr)
                    return;
                // frmCaptcha frm = new frmCaptcha();
                // frm.ShowDialog();
                // if(frm.Result != "OK"){ // vuth update fix ts 0210
                //     Message_Box.ShowTB("Chưa xác nhận được Captcha, vui lòng thử lại");
                //     return;
                // }

                this.popupComponent = ()=>import('./popupCaptcha');
                this.popupComponentName = "popupCaptcha1";
                this.Popup('popupComponentsKhoiTaoTB');

                // var rs = await this.$root.context.post("/ccbs/chonSo/ts_chonso",{
                //     "ghi_chu": "DHSX",
                //     "so_tb": this.vsomay_toanquoc_trasau
                // });
                // var kq = this.UpperCasePropertyList(rs.data);//ccbs.tt_1_chonso(ttnd.USER_NEO, ds_thuebao, ttnd.tentat_ccbs, "DHSX");
                // if ( !(rs.error==200 && rs.error_code=="BSS-00000000")) // vuth update 14/12
                // {
                //     // this.$toast.error("Không có dữ liệu trả về"+(rs.message?": "+rs.message:""));
                //     this.$toast.error("Chọn số thất bại: "+(kq?kq:(rs.message?rs.message:"")));
                //     return;
                // }
                // else {//if(kq[0][0]==1) {
                //     this.$toast.success("Chọn số thành công: "+(kq?kq:rs.message));
                //     this.TRACUU_KHOSO_TQ();
                //     this.NAP_DS_SODANGKY();
                // }
            }
            catch (ex)
            { this.$toast.error("Có lỗi khi chọn số kho tỉnh " + ex); }
            finally {
                this.loading(false);
            }
        },
        btnChonSo_KT_ButtonClick() {
            var so_tb_chon = "";
            if (this.vthuebao_dakhoitao && (this.vthuebao_dakhoitao+'').length == 11) // vuth update fix 1609
            {
                so_tb_chon = (this.vthuebao_dakhoitao+'').replace(/^84/,"");//Regex.Replace(vthuebao_dakhoitao, "^84", "");
            }
            else
            {
                so_tb_chon = this.vthuebao_dakhoitao;
            }
            var so_sim_chon = this.vsimcard;
            var goicuoc_id_chon = this.vgoicuoc_id;
            if (so_tb_chon != "-1" && so_sim_chon != "-1")
            {
                var retData = {
                    so_tb_chon:so_tb_chon,
                    so_sim_chon: so_sim_chon,
                    goicuoc_id_chon: goicuoc_id_chon,
                    kieuform: this.dulieu.vkieuform,
                };
                this.$emit("form-close", retData);
                this.$bvModal.hide(this.modalId);
            }
            else
            {
                if (so_tb_chon != "-1")
                {
                    this.$toast.error("Không lấy được số máy đã chọn");
                    return;

                }
                if (so_sim_chon != "-1")
                {
                    this.$toast.error("Không lấy được sim card của số máy");
                    return;
                }
            }
        },
        async btnKhoiTao_TS_ButtonClick(e) {
            console.info(e);
            var so_tb_kt = this.vsomay_khotinh;
            if (so_tb_kt == "-1")
            {
                xacnhan = false;
                this.$toast.error("Không lấy được số máy đã chọn để khởi tạo");
            }
            else
            {

                this.popupComponentData = {"kieu":this.dulieu.vkieuform,"chonso":this.dulieu.vkieuform==0,"sotb_goc":so_tb_kt,"khoid":this.cboKhoSo.value};//this.dulieu.vkieuform==1?e.rowData.KHO_ID:null};
                console.info('khoitao_ts',this.popupComponentData);
                this.popupComponent = ()=>import('./popupKhoiTaoTB');
                this.popupComponentName = "popupKhoiTaoTB";
                this.Popup('popupComponentsKhoiTaoTB');
                // var frm = new frmKhoiTaoTB();
                // frm.kieu = vkieuform; // vuth update fix 1609
                // if (vkieuform == 0)
                //     frm.chonso = false;
                // else
                //     frm.khoid = vkho_id; // vuth update 03/12/2019
                
                // frm.sotb_goc = so_tb_kt;
                // frm.ShowDialog();
                // if (frm.xacnhan)
                // {
                //     NAP_DS_KHOITAO();
                // }
            }
        },
        async btnChon_ButtonClick(e) {
            console.info(e);
            if (1==1)//(e.Button.Tag.ToString() == "Dow")
            {

                var so_tb_kt = this.vthuebao_chon;
                if (so_tb_kt == "-1")
                {
                    this.$toast.error("Không lấy được số máy đã chọn để khởi tạo");
                    return;
                }
                else
                {
                    
                    this.popupComponentData = {"kieu":this.dulieu.vkieuform,"chonso":this.dulieu.vkieuform==0,"sotb_goc":so_tb_kt,"khoid":this.dulieu.vkieuform==1?e.rowData.KHO_ID:null};
                    this.popupComponent = ()=>import('./popupKhoiTaoTB');
                    this.popupComponentName = "popupKhoiTaoTB";
                    this.Popup('popupComponentsKhoiTaoTB');
                    // var frm = new frmKhoiTaoTB();
                    // frm.kieu = vkieuform;
                    // if (vkieuform == 0)
                    //     frm.chonso = true; // vuth update fix 0210
                    
                    // frm.sotb_goc = so_tb_kt;
                    // if (vkieuform ==1)
                    //     frm.khoid = vkho_id; // vuth update 03/12/2019
                    // frm.ShowDialog();
                    // if (frm.xacnhan)
                    // {
                    //     NAP_DS_KHOITAO();
                    //     NAP_DS_SODANGKY();
                    // }
                }
            }
            else
            {
                var so_tb_chon = this.vthuebao_chon.indexOf("84")==0?this.vthuebao_chon.substring(2):this.vthuebao_chon
                // so_tb_chon = Regex.Replace(vthuebao_chon, "^84", ""); // vuth update before 1509
                var so_sim_chon = this.vsimcard;
                var goicuoc_id_chon = this.vgoicuoc_id;
                if (so_tb_chon != "-1")
                {
                    var retData = {
                        so_tb_chon:so_tb_chon,
                        so_sim_chon: so_sim_chon,
                        goicuoc_id_chon: goicuoc_id_chon,
                        kieuform: this.dulieu.vkieuform,
                    };
                    this.$emit("form-close", retData);
                    this.$bvModal.hide(this.modalId);
                    // xacnhan = true;
                    // Close();
                }
                else
                {
                    if (so_tb_chon != "-1")
                    {
                        this.$toast.error("Không lấy được số máy đã chọn");
                        return;

                    }
                    if (so_sim_chon != "-1")
                    {
                        this.$toast.error("Không lấy được sim card của số máy");
                        return;
                    }
                }
            }
        },

        async LAPHD_CHONSO()
        {
            try
            {

                var cfr = await this.$bvModal.msgBoxConfirm("Bạn có chắc chắn muốn chọn số " + this.vsomay_khochung_tinh + " không ?", {
                    title: 'Xác nhận hành động',
                    size: 'sm',
                    okTitle: 'Đồng ý',
                    cancelTitle: 'Hủy',
                }).then(async v => {
                    return v;
                });
                if (!cfr)
                    return;

                this.popupComponent = ()=>import('./popupCaptcha');
                this.popupComponentName = "popupCaptchaLAPHD_CHONSO";
                this.Popup('popupComponentsKhoiTaoTB');

            }
            catch (ex)
            { this.$toast.error("Có lỗi khi chọn số kho tỉnh " + ex); }
        },
        
        Popup(modalId) {
        this.$bvModal.show(modalId);
        },
        ClosePopup(modalId) {
            this.$bvModal.hide(modalId);
            this.popupComponent = null;
        },
        async popupClosed(val) {
            this.loading(true);
            try {
                switch(this.popupComponentName) {
                    case "popupKhoiTaoTB":
                        // if(!val) {
                        //     return;
                        // }
                        this.loading(true);
                        this.NAP_DS_KHOITAO();
                        await this.NAP_DS_SODANGKY();
                        this.loading(false);
                        break;
                    case "popupCaptcha1":
                        if(val) this.$toast.success("Xác thực captcha thành công!");
                        else {
                            this.$toast.error("Chưa xác nhận được capcha, không thể thực hiện tính năng !!!");
                            return;
                        }
                        var rs = await this.$root.context.post("/ccbs/chonSo/ts_chonso",{
                            "ghi_chu": "DHSX",
                            "so_tb": this.vsomay_toanquoc_trasau
                        });
                        var kq = this.UpperCasePropertyList(rs.data);//ccbs.tt_1_chonso(ttnd.USER_NEO, ds_thuebao, ttnd.tentat_ccbs, "DHSX");
                        if ( !(rs.error==200 && rs.error_code=="BSS-00000000")) // vuth update 14/12
                        {
                            // this.$toast.error("Không có dữ liệu trả về"+(rs.message?": "+rs.message:""));
                            this.$toast.error("Chọn số thất bại: "+(kq?kq:(rs.message?rs.message:"")));
                            return;
                        }
                        else {//if(kq[0][0]==1) {
                            this.$toast.success("Chọn số thành công: "+(kq?kq:rs.message));
                            this.TRACUU_KHOSO_TQ();
                            this.NAP_DS_SODANGKY();
                        }
                        break;
                    case "popupCaptcha":
                        if(val) this.$toast.success("Xác thực captcha thành công!");
                        else {
                            this.$toast.error("Chưa xác nhận được capcha, không thể thực hiện tính năng !!!");
                            return;
                        }

                        var dt = this.grcDS_ChuaDK.DataSource;
                        if (!dt)
                        {
                            this.$toast.error("Không lấy được dữ liệu danh sách số đang chờ đăng ký");
                            return;
                        }
                        if (dt.length == 0)
                        {
                            this.$toast.error("Chưa có danh sách số máy để đăng ký");
                            return;
                        }
                        var dachon = dt.filter(x=>x.ISCHECK==1).length;
                        if (dachon == 0) //vuth update fix 0210
                        {
                            this.$toast.error("Chưa có số máy nào được chọn");
                            return;
                        }
                        var ds_thuebao = "";
                        // #region Lây dữ liệu
                        for (var dr of dt.filter(x=>x.ISCHECK==1))
                        {
                            ds_thuebao += "84" + dr["SO_TB"].indexOf("0")==0?dr["SO_TB"].substring(1):dr["SO_TB"] + ",";
                        }
                        ds_thuebao = ds_thuebao[ds_thuebao.length-1]==','?ds_thuebao.substring(0,ds_thuebao.length-1):ds_thuebao;
                        // #endregion
                        var rs = await this.$root.context.post("/ccbs/chonSo/ts_chonso",{
                            "ghi_chu": "DHSX",
                            "so_tb": ds_thuebao
                        });
                        var kq = this.UpperCasePropertyList(rs.data);//ccbs.tt_1_chonso(ttnd.USER_NEO, ds_thuebao, ttnd.tentat_ccbs, "DHSX");
                        // if ( !kq || kq.length == 0 || !kq[0].length==0 || !kq[0][0]) // vuth update 14/12
                        // {
                        //     this.$toast.error("Không có dữ liệu trả về"+(rs.message?": "+rs.message:""));
                        //     return;
                        // }
                        if ( !(rs.error==200 && rs.error_code=="BSS-00000000")) // vuth update 14/12
                        {
                            // this.$toast.error("Không có dữ liệu trả về"+(rs.message?": "+rs.message:""));
                            this.$toast.error("Chọn số thất bại: "+(kq?kq:(rs.message?rs.message:"")));
                            return;
                        }
                        this.$toast.success("Đăng ký thành công: "+(kq?kq:rs.message));// : (kq?kq:rs.message));
                        this.NAP_DS_SODANGKY();

                        var ds_dagan = this.grcDS_DaDK.DataSource;
                        if (!ds_dagan)
                            return;
                        if (ds_dagan.length == 0)
                            return;
                        dt = dt.filter(x=>x.ISCHECK==1).filter(x=>{ 
                            stb = x.SO_TB[0]=='0'?x.SO_TB.substring(1):x.SO_TB;
                            return ds_dagan.some(y=>y.SO_TB=='84'+stb || y.SO_TB==stb);
                        });
                        // for (var dr of dt.filter(x=>x.ISCHECK==1)) // vuth update fix 0210
                        // {
                        //     var stb = dr["SO_TB"];
                        //     if(stb[0]=='0') stb = stb.substring(1);
                        //     var banghi = ds_dagan.filter(x=>x.SO_TB=='84' + stb || x.SO_TB==stb).length;
                        //     if (banghi > 0)
                        //     {
                        //         dt.Rows.Remove(dr);
                        //     }
                        // }
                        this.TRACUU_KHOSO_TQ();
                        break;
                    case "popupCaptchaLAPHD_CHONSO":
                        if(val) this.$toast.success("Xác thực captcha thành công!");
                        else {
                            this.$toast.error("Chưa xác nhận được capcha, không thể thực hiện tính năng !!!");
                            return;
                        }
                        var kq = await this.$root.context.post("/ccbs/executor/ts_laphd_chonso_khotinh", 
                            {
                                so_tb:this.vsomay_khochung_tinh.match(/0\d+/g)?`84${this.vsomay_khochung_tinh.substring(1)}`:this.vsomay_khochung_tinh, 
                                ma_tinh_nguoi_dung:this.matinh
                            }
                        ); // var kq = ccbs.ts_laphd_chonso_khotinh(vsomay_khochung_tinh, ttnd.USER_NEO, matinh);
                        //if (kq == "1")
                        console.log(kq.data);
                        if(kq.error==200 && kq.error_code=="BSS-00000000" && kq.data==1 || kq.data.result==1)
                        {
                            this.$toast.success("Chọn số thành công");
                            this.TRACUU_KHOSO_TQ();
                            this.NAP_DS_SODANGKY();
                        }
                        else
                        {
                            this.$toast.error("Chọn số thất bại : " + (kq&&kq.data&&kq.data.result?kq.data.result:JSON.stringify(kq)));
                        }
                        break;
                }
            } catch(ex) { }
            finally {
                this.loading(false);
                this.ClosePopup('popupComponentsKhoiTaoTB');
            }
        },
        goToCustomPage(grid, page) {      
            if (Number(page)<=0) page = 1
            switch(grid) {
                case "grid_tabgdvcstq":
                    this.pageNumberTQTraSau = Number(page)
                    break
                case "grid_tabdskstq":
                    this.pageNumberTQ = Number(page)
                    break
            }            
            this.TRACUU_KHOSO_TQ();
            //this.$refs[grid].$refs.pagination.gotoPage(Number(page))
        },
        goToPrevCustomPage(grid, page) {
            if (page < 2) {
                switch(grid) {
                    case "grid_tabgdvcstq":
                        this.pageNumberTQTraSau = 1
                        break
                    case "grid_tabdskstq":
                        this.pageNumberTQ = 1
                        break
                }
            } else {
                switch(grid) {
                    case "grid_tabgdvcstq":
                        this.pageNumberTQTraSau = Number(page) - 1    
                        break
                    case "grid_tabdskstq":
                        this.pageNumberTQ = Number(page) - 1                
                        break
                }
            }
            //this.$refs[grid].$refs.pagination.gotoPage(this.customPage - 1)
            this.TRACUU_KHOSO_TQ();
        },
        goToNextCustomPage(grid, page) {            
            switch(grid) {
                case "grid_tabgdvcstq":
                    this.pageNumberTQTraSau = Number(page) + 1     
                    break
                case "grid_tabdskstq":
                    this.pageNumberTQ = Number(page) + 1             
                    break
            }
            //this.$refs[grid].$refs.pagination.gotoPage(this.pageNumberTQ - 1)
            this.TRACUU_KHOSO_TQ();
        }
    },

}
</script>