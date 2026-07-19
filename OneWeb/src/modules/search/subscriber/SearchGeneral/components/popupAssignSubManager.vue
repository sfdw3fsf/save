<template>
    <div class="modal-content popup-box popupAssignSubManager">
                <div class="list-actions-top">
                    <ul class="list">
                        <li>
                            <a href="#" @click.prevent="btnLayTT">
                                <span class="icon one-file-attach1"></span>Lấy TT
                            </a>
                        </li>
                        <li>
                            <a href="#" @click.prevent="btnCapNhat">
                                <span class="icon one-reload1"></span> Cập nhật
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <span class="icon one-book1"></span> Hướng dẫn
                            </a>
                        </li>
                       
                    </ul>
                </div>
                <div class="popup-body">
                    <div class="box-form padt8 padb8 fw6 upper">
                        GÁN NHÂN VIÊN QUẢN LÝ THUÊ BAO THEO HỢP ĐỒNG
                    </div>
                    <div class="box-form">
                        <div class="legend-title">
                            Danh sách hợp đồng
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w100 nowrap">
                                        <div class="list-checks">
                                            <div class="item">
                                                <div class="check-action">
                                                    <input type="radio" id="one" value= 1 v-model="isHopDong">
                                                    <span class="name">Hợp đồng</span>
                                                </div>
                                            </div>
                                            <div class="item">
                                                <div class="check-action">
                                                    <input type="radio" id="two" value= 2 v-model="isHopDong">
                                                    <span class="name">Danh bạ</span>
                                                </div>
                                            </div>
                                        </div>
                                        
                                    </div>
                                    <div class="value">
                                        <div class="input-more-button -right">
                                            <button class="btn" @click="btnLayTT">
                                                <span class="one-search f20"></span>
                                            </button>
                                            <input type="text" v-model="txtMaGD" ref="txtMaGD" class="form-control highlight" v-on:keyup.enter="btnLayTT">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="key w100 nowrap">
                                    <div class="list-checks mart7">
                                        <div class="item">
                                            <div class="check-action">
                                                <input type="checkbox" class="check" v-model="isGoToanBo">
                                                <span class="name">Toàn bộ thuê bao thuộc đối tượng quản lý</span>
                                            </div>
                                        </div>
                                       
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="table-content">
                            <table class="table-result table-gachle">
                                <DataGrid
                                    v-bind:columns="ds_hopdong.header"                    
                                    v-bind:dataSource="ds_hopdong.list"
                                    :enable-paging-server="false"                                        
                                    :allowPaging="true"
                                    :showFilter="true"
                                    :showColumnCheckbox="false"
                                    @selectedRowChanged="gridDSHDChanged"
                                    >
                                </DataGrid> 
                            </table>
                        </div>
                        
                    </div>
                    <div class="box-move-select-table">
                        <b-tabs class="nav tabs tab-over relative" style="z-index: 2;" v-model="TabIndex">
                            <b-tab title="Danh sách NV PT mới đã gán" >
                                <div class="tab-content">
                                    <div class="table-content" style="height: 200px;">
                                        <table class="table-result table-gachle">
                                            <DataGrid
                                                v-bind:columns="ds_NVPTDaGan.header"                    
                                                v-bind:dataSource="ds_NVPTDaGan.list"
                                                :enable-paging-server="false"                                        
                                                :allowPaging="true"
                                                :showFilter="true"
                                                :showColumnCheckbox="true"
                                                @selectedItemsChanged="selectedItems1_click"
                                                >
                                            </DataGrid>
                                        </table>
                                    </div>
                                </div>
                            </b-tab> 
                            <b-tab title="Danh sách NV PTM hỗ trợ" >
                                <div class="tab-content">
                                    <div class="table-content" style="height: 200px;">
                                        <table class="table-result table-gachle">
                                            <DataGrid
                                                v-bind:columns="ds_NVPTMHoTro.header"                    
                                                v-bind:dataSource="ds_NVPTMHoTro.list"
                                                :enable-paging-server="false"                                        
                                                :allowPaging="true"
                                                :showFilter="true"
                                                :showColumnCheckbox="true"
                                                @selectedItemsChanged="selectedItems2_click"
                                                >
                                            </DataGrid>
                                        </table>
                                    </div>
                                </div>
                            </b-tab> 
                            <b-tab title="Danh sách NV duy trì đã gán" >
                                <div class="tab-content">
                                    <div class="table-content" style="height: 200px;">
                                        <table class="table-result table-gachle">
                                            <DataGrid
                                                v-bind:columns="ds_NVDuyTri.header"                    
                                                v-bind:dataSource="ds_NVDuyTri.list"
                                                :enable-paging-server="false"                                        
                                                :allowPaging="true"
                                                :showFilter="true"
                                                :showColumnCheckbox="true"
                                                @selectedItemsChanged="selectedItems3_click"
                                                >
                                            </DataGrid>
                                        </table>
                                    </div>
                                </div>
                            </b-tab> 
                        </b-tabs>


                        <div class="actions">
                            <button class="btn" @click="btnGoGan">
                                <span class="fa fa-angle-right"></span>
                            </button>
                            
                            <button class="btn" @click="btnGan">
                                <span class="fa fa-angle-left"></span>
                            </button>
                            
                        </div>
                        <div class="box-col box-form" style="width:30%;">
                            <div class="legend-title">Danh sách nhân viên theo:</div>
                            <div class="row">
                                <div class="col-sm-6 col-12">
                                    <div class="info-row">
                                        <div class="key w30 nowrap">Loại NV</div>
                                        <div class="value">
                                            <div class="select-custom">
                                                <select2 :options="LoaiNV.list" v-model="LoaiNV.value.id" @change="LoaiNV_Change"> 
                                                    <option disabled value="0">Select one</option> 
                                                </select2>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6 col-12">
                                    <div class="info-row">
                                        <div class="key w30 nowrap">Đơn vị</div>
                                        <div class="value">
                                            <div class="select-custom">
                                                <select2 :options="DonVi.list" v-model="DonVi.value.id" > 
                                                    <option disabled value="0">Select one</option> 
                                                </select2>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="table-content" style="height: 200px;">
                                    <ejs-grid :dataSource="ds_NV.list" locale="vi-VN" :allowSorting='true' :allowFiltering='true'  
                                        :allowPaging='true' :pageSettings='{pageSize:10,pageSizes: [10,50,100,200]}' :gridLines='1' :editSettings="{ allowEditing: true, mode: 'Normal' }"  :selectionSettings="{ type: 'Multiple' }" @rowSelected="selectedItems_click" @rowDeselected="deselectedItems_click"> 
                                        <e-columns> 
                                            <e-column template="<span class='fa selected-flag'></span>" :allowFiltering="false" filterTemplate="<span></span>"
                                            width="26" maxWidth="26" minWidth="26" textAlign="center" :customAttributes="{class: 'column-selected-flag'}"
                                             :allowResizing="false" clipMode="Ellipsis"></e-column>
                                            <e-column type="checkbox" width="39" maxWidth="39" minWidth="39" headerTextAlign="center" textAlign="center"
                                            :customAttributes="{class: 'column-check-selected'}"
                                             :allowResizing="false"></e-column>
                                            <e-column v-for="col in ds_NV.cols" :key="col.fieldName" :field='col.fieldName' :allowEditing="col.allowEditing" :customAttributes="col.customAttributes" :headerText='col.headerText' headerTextAlign="center" :editType="col.editType"  :format='col.format' :isPrimaryKey="col.isPrimaryKey"></e-column> 
                                        </e-columns> 
                                    </ejs-grid>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
</template>
<style>
    input.disabled, select.disabled, .disabled input, .disabled select, .disabled /deep/ .select2-selection { background-color: #EEE; color:lightgray;}
    .e-grid .e-gridheader .e-sortfilter .e-headercelldiv, .e-grid .e-gridheader .e-stackedheadercelldiv, .e-grid .e-gridheader .e-headercell .e-headercelldiv.e-headerchkcelldiv 
    {
    padding: 0 5px 0 5px !important;
    }
   
    .relative
    {
        width: 70% !important;
    }
     element.style
    {
        width: 30% !important;
    }
</style>
<script>
import { Sort, Filter, Resize, Group, Freeze, CommandColumn,ExcelExport, Edit, Aggregate, Toolbar,} from "@syncfusion/ej2-vue-grids";
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import { required, minLength, integer, maxValue } from 'vuelidate/lib/validators'
import API from './API'
import { Checkbox } from 'element-ui'
export default {
    name: 'popupServiceOthers',
    components: { API,required, minLength, integer, maxValue,DatePicker },
    provide: { 
        grid: [ 
            Sort, 
            Filter, 
            Resize, 
            Group, 
            Freeze, 
            CommandColumn, 
            ExcelExport, 
            Edit, 
            Aggregate, 
            Toolbar, 
        ], 
    },
    props: [ 'dulieu' ],
    watch: {
        
    },
    
   
    data() {
        return {
             editSettings: { 
                    allowEditing: true 
                },
            ds_hopdong: {
                list: [],
                header: [ {fieldName: 'ma_tb', headerText: 'Số máy/Acc', allowFiltering: true, width: 100}, 
                            {fieldName: 'matb_tn', headerText: 'Mã TN', allowFiltering: true, width: 100},
                            {fieldName: 'ten_dvvt', headerText: 'Dịch vụ', allowFiltering: true , allowHtml : true, width: 100},
                            {fieldName: 'loaihinh_tb', headerText: 'Loại hình', allowFiltering: true , allowHtml : true, width: 100},                                
                            {fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: true , allowHtml : true, width: 150},                                     
                            {fieldName: 'ten_kieuld', headerText: 'Kiểu lắp đặt', allowFiltering: true , allowHtml : true, width: 100},  
                            {fieldName: 'diachi_ld', headerText: 'Địa chỉ lắp đặt', allowFiltering: true , allowHtml : true, width: 150},                                     
                            {fieldName: 'trangthai_hd', headerText: 'Trạng thái HĐ', allowFiltering: true , allowHtml : true, width: 100},                                     
                            ],
                value: {},
                isEnabled: true,
                checked: []
            },

            ds_NVPTDaGan: {
                list: [],
                header: [ {fieldName: 'MA_NV', headerText: 'Mã NV', allowFiltering: true, width: 100}, 
                            {fieldName: 'TEN_NV', headerText: 'Tên NV', allowFiltering: true , width: 150},
                            {fieldName: 'TYLE', headerText: 'Tỷ lệ', allowFiltering: true , allowHtml : true, width: 60},
                            {fieldName: 'LOAINV_ID', headerText: 'Loại NV', allowFiltering: true , allowHtml : true, width: 100},                                
                            {fieldName: 'TEN_DV', headerText: 'Đơn vị', allowFiltering: true , allowHtml : true, width: 150},                                     
                            ],
                value: {},
                isEnabled: true,
                checked: []
            },
            ds_NVPTMHoTro: {
                list: [],
                header: [ {fieldName: 'MA_NV', headerText: 'Mã NV', allowFiltering: true, width: 100}, 
                            {fieldName: 'TEN_NV', headerText: 'Tên NV', allowFiltering: true , width: 150},
                            {fieldName: 'TYLE', headerText: 'Tỷ lệ', allowFiltering: true , allowHtml : true, width: 60},
                            {fieldName: 'LOAINV_ID', headerText: 'Loại NV', allowFiltering: true , allowHtml : true, width: 100},                                
                            {fieldName: 'TEN_DV', headerText: 'Đơn vị', allowFiltering: true , allowHtml : true, width: 150},                                     
                            ],
                value: {},
                isEnabled: true,
                checked: []
            },
            ds_NVDuyTri: {
                list: [],
                header: [ {fieldName: 'MA_NV', headerText: 'Mã NV', allowFiltering: true, width: 100}, 
                            {fieldName: 'TEN_NV', headerText: 'Tên NV', allowFiltering: true , width: 150},
                            {fieldName: 'TYLE', headerText: 'Tỷ lệ', allowFiltering: true , allowHtml : true, width: 60},
                            {fieldName: 'LOAINV_ID', headerText: 'Loại NV', allowFiltering: true , allowHtml : true, width: 100},                                
                            {fieldName: 'TEN_DV', headerText: 'Đơn vị', allowFiltering: true , allowHtml : true, width: 150},                                     
                            ],
                value: {},
                isEnabled: true,
                checked: []
            },
            ds_NV: {
                list: [],
                header: [ {fieldName: 'ma_nv', headerText: 'Mã NV', isPrimaryKey:true,  allowEditing:false, allowFiltering: true, width: 100}, 
                            {fieldName: 'ten_nv', headerText: 'Tên NV',  allowEditing:false,  allowFiltering: true , width: 150},
                            {fieldName: 'tyle', headerText: 'Tỷ lệ', allowEditing:true, editType: 'numericedit',allowFiltering: true , allowHtml : true,  width: 60},
                            ],
                value: {},
                isEnabled: true,
                checked: [],
                cols: [],
            },
           
            LoaiNV: {
                list: [],
                value: {},
            },
            DonVi: {
                list: [],
                value: {},
            },
           
        success_code: "BSS-00000000",
        isHopDong: 1,
        isGoToanBo: false,
        TabIndex: 0,
        txtMaGD: "",

        loai_id: 0,
        loaitb_id: 0,
        thanhtoan_id: 0,
        phanvung_id:0,
        ds_Gan: [],
        dt_hd: [],
        donvi_qlid: "",
        hdtb_id:"",
        thuebao_id:"",
        hdkh_id:"",
        khachhang_id: "",
        vtimkiem_hdkh_id: "",
        }
    },
    async mounted() {
        this.ds_NV.cols = [  
            {fieldName: 'ma_nv', headerText: 'Mã NV', allowFiltering: false, allowSorting: false, isPrimaryKey:true},  
            {fieldName: 'ten_nv', allowEditing:false, format:'N', customAttributes:{class: 'tongtien'},  headerText: 'Tên NV', allowFiltering: false, allowSorting: false},  
            {fieldName: 'tyle', allowEditing:true, editType: 'numericedit', format:'N',  headerText: 'Tỷ lệ', allowFiltering: false, allowSorting: false}, 
        ]
        this.LoadCombobox();
        //this.txtMaGD = this.dulieu.vtimkiem_hdkh_id?this.dulieu.vtimkiem_hdkh_id:"";
        if (this.txtMaGD != "")
        {
            this.btnLayTT();
        }
    },
    computed: {
       KenhTrong: {
            get() { return this.isHopDong; },
            set(value) { this.isHopDong=2; }
        },
        GoToanBo: {
            get() { return this.isGoToanBo; },
            set(value) { !this.isGoToanBo; }
        },
    },

    methods: {
        closeForm() {
            this.dichvudangky = this.ds_dichvuKhac.checked;
            console.log(this.dichvudangky);
            this.$emit('form-close',this.dichvudangky);
        },
        formatPrice(value) {
            let val = (value/1).toFixed(0).replace('.', ',')
            return val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".")
        },
        ShowAlert(mesage,val)
        {
            if (val==0)
            {
                this.$toast.success(mesage);
            }
            else if (val==1)
            {
                this.$toast.warning(mesage);
            }
            else if (val==2)
            {
                this.$toast.error(mesage);
            }
        },
        deselectedItems_click:function (dataKeys) {
            this.ds_NV.checked.splice(this.ds_NV.checked.indexOf(dataKeys.data),1);
        },
        selectedItems_click: function (dataKeys) {
            this.ds_NV.checked.push(dataKeys.data)
            this.ds_NV.checked = this.ds_NV.checked.map( function( item ){ 
                    for(var key in item){ 
                        var upper = key.toUpperCase(); 
                        if( upper !== key ){  
                            item[ upper ] = item[key]; 
                            delete item[key]; 
                        } 
                    } 
                    return item; 
            });
        }, 
        selectedItems1_click: function (dataKeys) {
            this.ds_NVPTDaGan.checked = dataKeys
            var self= this
            this.ds_NVPTDaGan.list.forEach(function (item) {
                if (dataKeys.filter(x => x === (self.isHopDong ==1? item.HDTB_ID: item.THUEBAO_ID)).length > 0) {
                item.ischecked = true
                } else {
                item.ischecked = false
                }
            })
        }, 
        selectedItems2_click: function (dataKeys) {
            this.ds_NVPTMHoTro.checked = dataKeys
            var self= this
            this.ds_NVPTMHoTro.list.forEach(function (item) {
                if (dataKeys.filter(x => x === (self.isHopDong ==1? item.HDTB_ID: item.THUEBAO_ID)).length > 0) {
                item.ischecked = true
                } else {
                item.ischecked = false
                }
            })
        }, 
        selectedItems3_click: function (dataKeys) {
            this.ds_NVDuyTri.checked = dataKeys
            var self= this
            this.ds_NVDuyTri.list.forEach(function (item) {
                if (dataKeys.filter(x => x === (self.isHopDong ==1? item.HDTB_ID: item.THUEBAO_ID)).length > 0) {
                item.ischecked = true
                } else {
                item.ischecked = false
                }
            })
        }, 
        LoadCombobox: async function ()
        {
             //Lấy thông tin dịch vụ khác
            var input = {};
            this.loading(true);
            await API.ADMIN_LOAI_NV(this.axios, input).then((response) => {
                this.loading(false);    
                if(response.data.error_code && response.data.error_code === this.success_code) {
                   this.LoaiNV.list = response.data.data.map(x=>({id:x.LOAINV_ID,text:x.TEN}));
                }
                else
                {
                    this.ShowAlert("Lỗi lấy danh sách loại nhân viên!",2);
                }
            }
            ).finally(()=>{  this.loading(false); });
        },
        LoadDSNhanVienChuaGan: async function ()
        {
            var input = {};
            this.loading(true);
            await API.lay_ds_nhanvien_chuagan(this.axios, input).then((response) => {
                this.loading(false);    
                if(response.data.error_code && response.data.error_code === this.success_code) {
                   this.ds_NV.list = response.data.data;
                }
                else
                {
                    this.ShowAlert("Lỗi lấy danh sách nhân viên chưa gán!",2);
                }
            }
            ).finally(()=>{  this.loading(false); });
        },
        gridDSHDChanged(obj)
        {
            if(!obj) { 
                return; 
            }
            this.donvi_qlid = obj.donvi_qlid;
            this.ds_Gan = [];
            if (this.isHopDong == 1) // Hợp đồng
            {
                this.hdtb_id = obj.hdtb_id;
                this.hdkh_id = obj.hdkh_id_ql;
                this.ds_NVPTDaGan.list = this.dt_hd.filter (x => (x.LOAI == 1 && x.HDTB_ID == this.hdtb_id));
                this.ds_NVDuyTri.list = this.dt_hd.filter (x => (x.LOAI == 2 && x.HDTB_ID == this.hdtb_id));
                this.ds_NVPTMHoTro.list = this.dt_hd.filter (x => (x.LOAI == 3 && x.HDTB_ID == this.hdtb_id));
            }
            else if (this.isHopDong ==2) //Thuê bao
            {
                this.thuebao_id = obj.thuebao_id;
                this.khachhang_id = obj.khachhang_id;
                this.ds_NVPTDaGan.list = this.dt_hd.filter (x => (x.LOAI == 1 && x.THUEBAO_ID == this.thuebao_id));
                this.ds_NVDuyTri.list = this.dt_hd.filter (x => (x.LOAI == 2 && x.THUEBAO_ID == this.thuebao_id));
                this.ds_NVPTMHoTro.list = this.dt_hd.filter (x => (x.LOAI == 3 && x.THUEBAO_ID == this.thuebao_id));
            }
        },
        LoaiNV_Change: async function ()
        {
            var input = {};
            input.dvi_qly_id = 1194;// (this.donvi_qlid == ""? -1: this.donvi_qlid);
            input.loainv_id = 28;// this.LoaiNV.value.id;
            this.loading(true);
            console.log(input);
            await API.lay_ds_combobox_loai_nhanvien(this.axios, input).then((response) => {
                this.loading(false);    
                if(response.data.error_code && response.data.error_code === this.success_code) {
                    this.DonVi.list = response.data.data["dsDonVi"].map(x=>({id:x.donvi_id,text:x.ten_dv}));
                    this.ds_NV.list = response.data.data["dsNhanVien"];
                }
                else
                {
                    this.ShowAlert("Lỗi lấy danh sách nhân viên chưa gán!",1);
                }
            }
            ).finally(()=>{  this.loading(false); });
        },
        btnLayTT: async function ()
        {
            var pdata = {};
            pdata.kieu = this.isHopDong; //1: tra cứu hợp đồng; 2: danh bạ
            pdata.maTb= this.txtMaGD;
            await API.timkiem_ds_hopdong(this.axios, {params:pdata}).then((response) => {
                if(response.data.error_code && response.data.error_code === this.success_code) {
                    if (this.isHopDong == 1) //Hợp đồng
                    {
                        if (response.data.data["dt_hd_tb"].length>0)
                        {
                            this.ds_hopdong.list = response.data.data["dt_hd_tb"];
                            this.ds_hopdong.header = [ {fieldName: 'ma_gd', headerText: 'Mã GD', allowFiltering: true, width: 100}, 
                                {fieldName: 'ngay_yc', headerText: 'Ngày yêu cầu', allowFiltering: true, width: 100},
                                {fieldName: 'trangthai_hd', headerText: 'Trạng thái', allowFiltering: true , allowHtml : true, width: 100},
                                {fieldName: 'ten_dv', headerText: 'Đơn vị lập HĐ/PL', allowFiltering: true , allowHtml : true, width: 100},                                
                                ];
                                
                        }
                    }
                    else if (this.isHopDong == 2) //Danh bạ
                    {
                        this.ds_hopdong.list = response.data.data["ds_dbhd"];
                        this.ds_hopdong.header = [ {fieldName: 'ma_tb', headerText: 'Số máy/Acc', allowFiltering: true, width: 100}, 
                            {fieldName: 'matb_tn', headerText: 'Mã TN', allowFiltering: true, width: 100},
                            {fieldName: 'ten_dvvt', headerText: 'Dịch vụ', allowFiltering: true , allowHtml : true, width: 100},
                            {fieldName: 'loaihinh_tb', headerText: 'Loại hình', allowFiltering: true , allowHtml : true, width: 100},                                
                            {fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: true , allowHtml : true, width: 150},                                     
                            {fieldName: 'ten_kieuld', headerText: 'Kiểu lắp đặt', allowFiltering: true , allowHtml : true, width: 100},  
                            {fieldName: 'diachi_ld', headerText: 'Địa chỉ lắp đặt', allowFiltering: true , allowHtml : true, width: 150},                                     
                            {fieldName: 'trangthai_hd', headerText: 'Trạng thái HĐ', allowFiltering: true , allowHtml : true, width: 100},                                     
                            ];
                    }
                    this.dt_hd = response.data.data["dt_hd"];
                }
            }
            );
        },
        
        btnGan()
        {
            if (this.ds_NV.checked.length<=0)
            {
                this.ShowAlert("Bạn cần chọn dữ liệu để gán",1);
                return;
            }
            else
            {
                if (this.isHopDong == 1) //tra cứu hợp đồng
                {
                    if (this.TabIndex == 0) //phát triển mới
                    {
                        for (var temp of this.ds_NV.checked)
                        {
                            temp["LOAI"]=1;
                            temp["HDTB_ID"] = (this.hdtb_id? this.hdtb_id: 0);
                            this.ds_NVPTDaGan.list.push(temp);
                            this.ds_Gan.push(temp);
                        } 
                        console.log(this.ds_Gan);
                         //Chuyển item sang trái
                        var temps = this.ds_NV.list.filter(a => !this.ds_NV.checked.includes(a));
                        this.ds_NV.list = [];
                        this.ds_NV.list = temps;
                        this.ds_NV.checked = [];
                    }
                    else if (this.TabIndex == 1) //hỗ trợ
                    {
                        for (var temp of this.ds_NV.checked)
                        {
                            temp["LOAI"]=3;
                            temp["HDTB_ID"] = (this.hdtb_id? this.hdtb_id: 0);
                            this.ds_NVPTMHoTro.list.push(temp);
                            this.ds_Gan.push(temp);
                        } 
                         //Chuyển item sang trái
                        var temps = this.ds_NV.list.filter(a => !this.ds_NV.checked.includes(a));
                        this.ds_NV.list = [];
                        this.ds_NV.list = temps;
                        this.ds_NV.checked = [];

                    }
                    else if (this.TabIndex == 2) //duy trì
                    {
                        for (var temp of this.ds_NV.checked)
                        {
                            temp["LOAI"]=2;
                            temp["HDTB_ID"] = (this.hdtb_id? this.hdtb_id: 0);
                            this.ds_NVDuyTri.list.push(temp);
                            this.ds_Gan.push(temp);
                        } 
                         //Chuyển item sang trái
                        var temps = this.ds_NV.list.filter(a => !this.ds_NV.checked.includes(a));
                        this.ds_NV.list = [];
                        this.ds_NV.list = temps;
                        this.ds_NV.checked = [];
                    }
                }
                else if (this.isHopDong ==2)//danh bạ
                {
                    if (this.TabIndex == 0) //phát triển mới
                    {
                        for (var temp of this.ds_NV.checked)
                        {
                            temp["LOAI"]=1;
                            temp["THUEBAO_ID"] = (this.thuebao_id? this.thuebao_id:0);
                            this.ds_NVPTDaGan.list.push(temp);
                            this.ds_Gan.push(temp);
                        } 
                         //Chuyển item sang trái
                        var temps = this.ds_NV.list.filter(a => !this.ds_NV.checked.includes(a));
                        this.ds_NV.list = [];
                        this.ds_NV.list = temps;
                        this.ds_NV.checked = [];
                    }
                    else if (this.TabIndex == 1) //hỗ trợ
                    {
                        for (var temp of this.ds_NV.checked)
                        {
                            temp["LOAI"]=3;
                            temp["THUEBAO_ID"] = (this.thuebao_id? this.thuebao_id:0);
                            this.ds_NVPTMHoTro.list.push(temp);
                            this.ds_Gan.push(temp);
                        } 
                         //Chuyển item sang trái
                        var temps = this.ds_NV.list.filter(a => !this.ds_NV.checked.includes(a));
                        this.ds_NV.list = [];
                        this.ds_NV.list = temps;
                        this.ds_NV.checked = [];
                    }
                    else if (this.TabIndex == 2) //duy trì
                    {
                        for (var temp of this.ds_NV.checked)
                        {
                            temp["LOAI"]=2;
                            temp["THUEBAO_ID"] = (this.thuebao_id? this.thuebao_id:0);
                            this.ds_NVDuyTri.list.push(temp);
                            this.ds_Gan.push(temp);
                        } 
                         //Chuyển item sang trái
                        var temps = this.ds_NV.list.filter(a => !this.ds_NV.checked.includes(a));
                        this.ds_NV.list = [];
                        this.ds_NV.list = temps;
                        this.ds_NV.checked = [];
                    }
                }
            }
        },
        btnGoGan()
        {
            if (this.isHopDong == 1) //tra cứu hợp đồng
                {
                    if (this.TabIndex == 0) //phát triển mới
                    {
                        if (this.isGoToanBo)
                        {
                            this.$bvModal.msgBoxConfirm('Bạn đang gỡ nhân viên khỏi toàn bộ thuê bao. Bạn có chắc chắn muốn gỡ tất cả không?', {
                                title: '', size: 'sm', okTitle: 'Đồng ý',cancelTitle: 'Hủy',}).then(async v => {
                                if(v) {
                                    this.ds_NVPTDaGan.list = [];
                            }
                            });
                        }
                        else
                        {
                            var temps = this.ds_NVPTDaGan.list.filter(a => !this.ds_NVPTDaGan.checked.includes(a));
                            console.log(temps);
                            this.ds_NVPTDaGan.list = [];
                            this.ds_NVPTDaGan.list = temps;
                        }   
                    }
                    else if (this.TabIndex == 1) //hỗ trợ
                    {
                        if (this.isGoToanBo)
                        {
                            this.$bvModal.msgBoxConfirm('Bạn đang gỡ nhân viên khỏi toàn bộ thuê bao. Bạn có chắc chắn muốn gỡ tất cả không?', {
                                title: '', size: 'sm', okTitle: 'Đồng ý',cancelTitle: 'Hủy',}).then(async v => {
                                if(v) {
                                    this.ds_NVPTMHoTro.list = [];
                            }
                            });
                        }
                        else
                        {
                            var temps = this.ds_NVPTMHoTro.list.filter(a => !this.ds_NVPTMHoTro.checked.includes(a));
                            this.ds_NVPTMHoTro.list = [];
                            this.ds_NVPTMHoTro.list = temps;
                        }
                    }
                    else if (this.TabIndex == 2) //duy trì
                    {
                        if (this.isGoToanBo)
                        {
                            this.$bvModal.msgBoxConfirm('Bạn đang gỡ nhân viên khỏi toàn bộ thuê bao. Bạn có chắc chắn muốn gỡ tất cả không?', {
                                title: '', size: 'sm', okTitle: 'Đồng ý',cancelTitle: 'Hủy',}).then(async v => {
                                if(v) {
                                    this.ds_NVDuyTri.list = [];
                            }
                            });
                        }
                        else
                        {
                            var temps = this.ds_NVDuyTri.list.filter(a => !this.ds_NVDuyTri.checked.includes(a));
                            this.ds_NVDuyTri.list = [];
                            this.ds_NVDuyTri.list = temps;
                        }
                    }
                }
                else if (this.isHopDong ==2)//danh bạ
                {
                    if (this.TabIndex == 0) //phát triển mới
                    {
                        if (this.isGoToanBo)
                        {
                            this.$bvModal.msgBoxConfirm('Bạn đang gỡ nhân viên khỏi toàn bộ thuê bao. Bạn có chắc chắn muốn gỡ tất cả không?', {
                                title: '', size: 'sm', okTitle: 'Đồng ý',cancelTitle: 'Hủy',}).then(async v => {
                                if(v) {
                                    this.ds_NVPTDaGan.list = [];
                            }
                            });
                        }
                        else
                        {
                            var temps = this.ds_NVPTDaGan.list.filter(a => !this.ds_NVPTDaGan.checked.includes(a));
                            this.ds_NVPTDaGan.list = [];
                            this.ds_NVPTDaGan.list = temps;
                        }
                    }
                    else if (this.TabIndex == 1) //hỗ trợ
                    {
                        if (this.isGoToanBo)
                        {
                            this.$bvModal.msgBoxConfirm('Bạn đang gỡ nhân viên khỏi toàn bộ thuê bao. Bạn có chắc chắn muốn gỡ tất cả không?', {
                                title: '', size: 'sm', okTitle: 'Đồng ý',cancelTitle: 'Hủy',}).then(async v => {
                                if(v) {
                                    this.ds_NVPTMHoTro.list = [];
                            }
                            });
                        }
                        else
                        {
                            var temps = this.ds_NVPTMHoTro.list.filter(a => !this.ds_NVPTMHoTro.checked.includes(a));
                            this.ds_NVPTMHoTro.list = [];
                            this.ds_NVPTMHoTro.list = temps;
                        }
                    }
                    else if (this.TabIndex == 2) //duy trì
                    {
                         if (this.isGoToanBo)
                        {
                            this.$bvModal.msgBoxConfirm('Bạn đang gỡ nhân viên khỏi toàn bộ thuê bao. Bạn có chắc chắn muốn gỡ tất cả không?', {
                                title: '', size: 'sm', okTitle: 'Đồng ý',cancelTitle: 'Hủy',}).then(async v => {
                                if(v) {
                                    this.ds_NVDuyTri.list = [];
                            }
                            });
                        }
                        else
                        {
                            var temps = this.ds_NVDuyTri.list.filter(a => !this.ds_NVDuyTri.checked.includes(a));
                            this.ds_NVDuyTri.list = [];
                            this.ds_NVDuyTri.list = temps;
                        }
                    }
                }
        },
        btnCapNhat: async function ()
        {
            var input = {};
            input.kieu = this.isHopDong;
            if (this.isHopDong == 1)
            {
                input.id = (this.hdkh_id? this.hdkh_id: 0);
                input.khachhang_id = 0;
            }
            else if (this.isHopDong == 2)
            {
                input.id = (this.thuebao_id? this.thuebao_id: 0);
                input.khachhang_id = this.khachhang_id;
            }
            input.nhanvien_id_tt = this.$root.token.getNhanVienID();
            input.danhsach = JSON.stringify(this.ds_Gan);
            console.log(input);
            this.loading(true);
            await API.gan_nhanvienql_dbhd(this.axios, input).then((response) => {
                this.loading(false);    
                if(response.data.error_code && response.data.error_code === this.success_code) {
                    this.ShowAlert(response.data.data["ketqua"],0);
                }
                else
                {
                    this.ShowAlert("Có lỗi cập nhật!",2);
                }
            }
            ).finally(()=>{  this.loading(false); });
        },
        GetDsDVKhac: async function ()
        {
            //Lấy thông tin dịch vụ khác
            var input = {};
            input.vloaiid = this.loai_id;
            input.vloaitb_id = this.loaitb_id;
            input.vthanhtoan_id = this.thanhtoan_id;
            console.log(input);
            this.loading(true);
            await API.lay_ds_dichvu_khac(this.axios, input).then((response) => {
                this.loading(false);    
                if(response.data.error_code && response.data.error_code === this.success_code) {
                   this.ds_dichvuKhac.list = response.data.data;
                }
                else
                {
                    this.ShowAlert("Lỗi lấy danh sách dịch vụ khác!",2);
                }
            }
            ).finally(()=>{  this.loading(false); });
        },
    }
}
</script>