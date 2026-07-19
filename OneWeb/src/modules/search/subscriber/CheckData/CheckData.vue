<template src="./CheckData.html"></template>
<style scoped src="./CheckData.scss"></style>
<style>
.e-contextmenu-wrapper.e-grid-menu.e-lib.e-keyboard ul.e-menu-parent.e-ul {
    display: block !important;
    top: 150px !important;
    overflow-y: scroll !important;
    height: 80% !important;
}
</style>
<script>
import breadcrumb from "@/components/breadcrumb";
import API from "./API";
import Vue from "vue";
import XLSX from "xlsx";
import {
    DropDownListPlugin
} from "@syncfusion/ej2-vue-dropdowns";
import KTable from "@/components/KTable.vue";
import ActionTop from "@/components/ActionTop.vue";
import searchAccount from '@/modules/search/subscriber/SearchAccount';
import ChangeMegawanInforModal from '@/modules/search/subscriber/ChangeMegawanInfor/ChangeMegawanInforPopup/ChangeMegawanInforModal';
import Const from './Const';
import SearchAccountModal from './popups/SearchAccountModal.vue'
import FrmThayDoiTsKTRModal from './popups/FrmThayDoiTsKTRModal.vue'
import ChangeCustomerInfoModal from './popups/ChangeCustomerContractModal.vue'
import collums from "./define/collums.js";
Vue.use(DropDownListPlugin);
export default {
    components: {
        breadcrumb,
        KTable,
        ActionTop,
        searchAccount,
        SearchAccountModal,
        ChangeMegawanInforModal,
        FrmThayDoiTsKTRModal,
        ChangeCustomerInfoModal
    },
    name: 'CheckData', 
    data: function () {
        return { 
			data_checked_ds_chua_dongbo: [],
            header: {
                title: 'Đối soát dữ liệu thuê bao',
                list: [{
                        name: 'Quản lý DB',
                        link: {
                            name: 'Ui.cards'
                        }
                    },
                    {
                        name: 'Đối soát dữ liệu thuê bao',
                        link: {
                            name: 'Ui.buttons'
                        }
                    },
                ]
            },
            ...collums,
            dataCheckeds_ds_loaihinh: [],
            trangthai_selectted: "",
            tag: 0,
            isDisplayMenu: false,
            xuatPhieuShow: false,
            Event: null,
            params: {
                p_loaidanhsach_option: [],
                p_loai: null,
                p_thuebao_ct_id: null,
                p_thuebao_id: null,
                p_taget: null,
                p_ghichu: null,
                p_id_cn: null,
                p_row_id: null,
                p_ma_tb_tinh: null,
                p_ma_tb: "",
                p_thuthu: null,
                p_fileid: null,
                p_origini: null,
                p_thuebao_goc_chuagan_id: null,
                p_ghichu_goc_chuagan: null,
                p_id_cn_goc_chuagan: null,
                p_thuebao_ct_chuagan_id: null,
                p_ghichu_ct_chuagan: null,
            },
            ds_donvi_option: [],
            txt_donvi: "",
            editSettings: { allowEditing: true, mode: "Batch" },
            ds_cautraloi_table: [],
            ds_dulieu_thuebao_table: [],
            listTrangthai : [
                {trangthaixl_id: 0,trangthaixl:"Tất cả"},
                {trangthaixl_id: 4,trangthaixl:"Đã xác nhận CQ"},
                {trangthaixl_id: 6,trangthaixl:"Không xác nhận CQ"},
                {trangthaixl_id: 7,trangthaixl:"Đã cập nhật thông số kỹ thuật"},
                {trangthaixl_id: 8,trangthaixl:"Đã cập nhật thông tin khách hàng"},
                {trangthaixl_id: 9,trangthaixl:"Đã hoàn thành tổng rà soát"}
            ],
            trangthaixl_id:0,
            ma_tb:'',
            dichvuvt_id: 0,
            ds_dulieu_dadongbo: [],
            ds_dulieu_chuadongbo: [],
            columns_ds_dulieu_chuadongbo: [],
             columns_ds_dulieu_dadongbo: [],
            dataImport: [],
            dtDS_DOISOAT_TINH_CHUAGAN:[],
            dtDS_DOISOAT_TINH_DAGAN: [],
            dtDS_DOISOAT_XACNHAN_CQ: [],
            cboXacNhanCQ: [],
            cboTinhChuQuan: [],
            bancheo_id: 0,
            file_id: 0,
            loai_hinh_id: 0,
            columns: [],
            data_chon: [],
            trangthai_id: 0,
            loaihinh_id: -1,
            fieldName: '',
            p_loaitb_id: 0,
            p_loaitb_goc_chuagan_id: 0,
            p_loaitb_ct_chuagan_id: 0,
            txtMaTB: "",
            ma_tb:"",
            tinh_tc:{"TNS":"","user":"","pass":"","tentinh":"","matinh":"","nhanvien_id":0,"donvi_id":0,"tinhthicong_id":0,"mavung":"","ip_dll":""},
            position: {
                X: 'center',
                Y: 'top'
            },
            popupComponent: null,
            popupComponentData: null,
            popupComponentName: "",
            popupComponentEvents: {
                "form-close": "popupClosed",
                acceptChangeCTV: "popupClosed",
                acceptChangeNGT: "popupClosed",
            },
            dataLayDuLieu: {
                danhsach: [],
                trangthai_id: 0
            },
            button: {
                btnChuyenChuQuan: {
                    Visible: true,
                    enabled: true
                },
                btnXacNhanCQ: {
                    Visible: true,
                    enabled: true
                },
                btn_XuatExcel: {
                    Visible: false,
                    enabled: true
                },
                btnChuyenTinh: {
                    Visible: true,
                    enabled: true
                },
                btn_ImportExcel: {
                    Visible: true,
                    enabled: true
                },
                btnCapNhat: {
                    Visible: true,
                    enabled: true
                },
                btnChonThueBao: {
                    ReadOnly: false
                }
            },
            dsTBGoc: {
                visible: false
            },
            Panel1: {
                visible: true
            },
            Panel2: {
                visible: true
            },
            tt_nd:{
                ngay_cn:this.$auth.getNgayCapNhat(),
                tentat:this.$root.token.getMaTinh(),
                donvi_id: this.$auth.getDonViID(),
                nhanvien_id: this.$auth.getNhanVienID(),
                may_cn: this.$auth.getUserName(),
                ma_nd: this.$auth.getUserName(),
                ip: this.$auth.getUserName(),
                phanvung_id: this.$auth.getPhanVungID()
            },
            listTrangthai1:[],
            columns_ds_dulieu_tb: [{
                    fieldName: "XAC_NHAN_CQ",
                    headerText: "Xác nhận CQ",
                    allowFiltering: true,
                    allowSorting: true,
                    visible: false,
                }],
            row_id:''
        };
    },
    validations: {},
    computed: {
    },
    created() {
    },
    mounted() {
        if (this.$route.query.tag && this.$route.query.tag !== '') {
            this.tag = this.$route.query.tag.replaceAll(' ', '+')
        }  
        this.frmDoiSoatDuLieu_Load();
        
        console.log(this.listTrangthai)
    },
    // 1 - Mới up dữ liệu
    // 2 - Đã chuyển tỉnh
    // 3 - Đã cập nhật
    // 4 - Đã xác nhận CQ
    // 5 - Chờ xác nhận CQ
    // 6 - Ko xác nhận CQ
    // 7 - Đã cập nhật thông số kỹ thuật 
    // 8 - Đã cập nhật thông tin khách hàng 
    // 9 - Đã hoàn thành tổng rà soát
    methods: {
        
        async frmDoiSoatDuLieu_Load() {
            this.loading(true);
            let dsTrangthai = [
                {trangthaixl_id: 0,trangthaixl:"Tất cả"},
                {trangthaixl_id: 4,trangthaixl:"Đã xác nhận CQ"},
                {trangthaixl_id: 6,trangthaixl:"Không xác nhận CQ"},
                {trangthaixl_id: 7,trangthaixl:"Đã cập nhật thông số kỹ thuật"},
                {trangthaixl_id: 8,trangthaixl:"Đã cập nhật thông tin khách hàng"},
                {trangthaixl_id: 9,trangthaixl:"Đã hoàn thành tổng rà soát"},
                {trangthaixl_id: 10,trangthaixl:"Tỉnh khác đã xác nhận"},
                {trangthaixl_id: 11,trangthaixl:"Tỉnh khác chưa xác nhận"}
            ]

            this.listTrangthai = dsTrangthai
            this.listTrangthai1 = dsTrangthai 
            let dtTRANGTHAI_XL = [{
                    "TRANGTHAI_XL": "1",
                    "TRANGTHAI_XL_TEXT": "Mới"
                },
                {
                    "TRANGTHAI_XL": "2",
                    "TRANGTHAI_XL_TEXT": "Đã chuyển tỉnh"
                },
                {
                    "TRANGTHAI_XL": "3",
                    "TRANGTHAI_XL_TEXT": "Đã cập nhật"
                },
                {
                    "TRANGTHAI_XL": "4",
                    "TRANGTHAI_XL_TEXT": "Đã đồng bộ"
                },
                {
                    "TRANGTHAI_XL": "5",
                    "TRANGTHAI_XL_TEXT": "Xác nhận chủ quản"
                },
            ];
            if (this.tag.toString() != "") {
                switch (this.tag.toString()) {
                    case "0": 
                        this.Panel1.visible = false
                        await this.LoadDuLieu();
                        if(this.ds_dulieu_chuadongbo&&this.ds_dulieu_chuadongbo.length >0 ){
                            this.collums = Object.keys(this.ds_dulieu_chuadongbo[0])
                        }
                        this.button.btnChuyenChuQuan.Visible = false;
                        this.button.btnXacNhanCQ.Visible = false;
                        this.button.btnChonThueBao.ReadOnly = true;
                        this.button.btn_XuatExcel.Visible = true;
                        break;
                    case "1":
                        this.Panel1.visible = false
                        this.dtDS_DOISOAT_TINH_CHUAGAN = []
                        this.dtDS_DOISOAT_TINH_DAGAN = []
                        this.dtDS_DOISOAT_TINH_CHUAGAN =  this.changeKeysToUpper(this.GetData(await API.sp_lay_ds_doisoat_v3(this.axios, {
                            "kieu": 3,
                            "trangthai_xl":this.trangthaixl_id
                        })));   
                        this.dtDS_DOISOAT_TINH_DAGAN =  this.changeKeysToUpper(this.GetData(await API.sp_lay_ds_doisoat_v3(this.axios, {
                            "kieu": 1,
                            "trangthai_xl":this.trangthaixl_id
                        })));   
                        console.log('this.dtDS_DOISOAT_TINH_DAGAN')
                        console.log(this.dtDS_DOISOAT_TINH_DAGAN)
                        if(this.dtDS_DOISOAT_TINH_CHUAGAN){
                           
                            let key_chuadongbo = Object.keys(this.dtDS_DOISOAT_TINH_CHUAGAN[0])
                            key_chuadongbo = key_chuadongbo.filter(item => !['ID_ROW','ID_HOPDONG','TRANGTHAI_XL', 'MA_TB_TINH_DAU', 'MA_TB_TINH_CUOI','MA_TINH_CQ','MATINH_DAU','MATINH_CUOI'].includes(item))                          
                            key_chuadongbo.splice(0, 0, ...['ID_ROW','ID_HOPDONG','TRANGTHAI_XL', 'MA_TB_TINH_DAU', 'MA_TB_TINH_CUOI','MA_TINH_CQ','MATINH_DAU','MATINH_CUOI'])
                            
                            for (let i = 0; i < key_chuadongbo.length; i++){
                                let column_chuadongbo = {
                                    fieldName: key_chuadongbo[i],
                                    headerText: key_chuadongbo[i],
                                    allowFiltering: true,
                                    allowSorting: true,
                                }
                                
                                if(key_chuadongbo[i] == 'MA_TINH_CQ'){
                                    column_chuadongbo.template = function () {
                                        return {
                                            template: Vue.component("ChonMatinhCQTemplate", {
                                                template: `<div class="select-custom" >
                                                                <ejs-dropdownlist :change="cboMatinh_CQ_SelectedValueChanged" id="xacnhan_cq" class="combobox" :dataSource="cboTinhChuQuan" :fields="fields_matinh_cq" v-model="tinh_id"></ejs-dropdownlist>                                                       
                                                            </div>`,
                                                data() {
                                                    return {
                                                        cboTinhChuQuan: [],
                                                        tinh_id: 0,
                                                        fields_matinh_cq: { text: 'tentat', value: 'tinh_id' },
                                                    };
                                                },
                                                computed: {
                                                    parent() {
                                                        return this.$parent.$parent.$parent;
                                                    },
                                                },
                                                async mounted() {
                                                    this.cboTinhChuQuan = this.parent.GetData(await API.sp_lay_ds_tinh_khac(this.axios, {}));
                                                    
                                                },
                                                methods: {
                                                    cboMatinh_CQ_SelectedValueChanged(value) {
                                                         this.parent.cboTinhChuQuan_EditValueChanged(this.data,value,'MA_TINH_CQ')
                                                    },
                                                },
                                            }),
                                        };
                                    }
                                }
                                if(key_chuadongbo[i] == 'TRANGTHAI_XL'){
                                    column_chuadongbo.edit = "false"
                                    column_chuadongbo.template = function () {
                                        return {
                                            template: Vue.component("ChonTTXLTemplate", {
                                                template: `<div class="select-custom" >
                                                                <ejs-dropdownlist id="trangthai_xl" readonly disabled :value="TRANGTHAI_XL" v-model="TRANGTHAI_XL" class="combobox" :dataSource="cboTrangThaiXL" :fields="fields_trangthai" ></ejs-dropdownlist>                                                       
                                                            </div>`,
                                                data() {
                                                    return {
                                                        cboTrangThaiXL: [],
                                                        fields_trangthai: { text: 'TRANGTHAI_XL_TEXT', value: 'TRANGTHAI_XL' },
                                                        TRANGTHAI_XL: "0"
                                                    };
                                                },
                                                computed: {
                                                    parent() {
                                                        return this.$parent.$parent.$parent;
                                                    },
                                                },
                                                async mounted() {  
                                                    this.cboTrangThaiXL =[ 
                                                            {
                                                            "TRANGTHAI_XL": "1",
                                                            "TRANGTHAI_XL_TEXT": "Mới"
                                                            },
                                                            {
                                                                "TRANGTHAI_XL": "2",
                                                                "TRANGTHAI_XL_TEXT": "Đã chuyển tỉnh"
                                                            },
                                                            {
                                                                "TRANGTHAI_XL": "3",
                                                                "TRANGTHAI_XL_TEXT": "Đã cập nhật"
                                                            },
                                                            {
                                                                "TRANGTHAI_XL": "4",
                                                                "TRANGTHAI_XL_TEXT": "Đã đồng bộ"
                                                            },
                                                            {
                                                                "TRANGTHAI_XL": "5",
                                                                "TRANGTHAI_XL_TEXT": "Xác nhận chủ quản"
                                                            }]
                                                    this.TRANGTHAI_XL = this.data.TRANGTHAI_XL.toString();
                                                },
                                                methods: {
                                                },
                                            }),
                                        };
                                    }
                                }
                                if(key_chuadongbo[i] == "MA_TB_TINH_DAU"){
                                    column_chuadongbo.template = function () {
                                        return {
                                            template: Vue.component("ChonTB_TinhdauTemplate", {
                                                template: `<div style="border:none">
                                                        <input v-model="parent.ma_tb" :value="data.ma_tb_tinh_dau" type="text" style="border:none" :readonly="true"/>
                                                        <span class="-ap icon-more_horiz" v-on:click="handleLeftClick($event)"></span>
                                                            </div>
                                                `,
                                                data() {
                                                    return {
                                                        data: {}
                                                    };
                                                },
                                                mounted() {
                                                },
                                                computed: {
                                                    parent() {
                                                        return this.$parent.$parent.$parent;
                                                    },
                                                },
                                                methods: {
                                                    handleLeftClick(event) {
                                                         this.parent.onClickLoadAccount(this.data,event,'MA_TB_TINH_DAU')
                                                    },
                                                },
                                            }),
                                        };
                                    }
                                }
                                if(key_chuadongbo[i] == "MA_TB_TINH_CUOI"){
                                    column_chuadongbo.template = function () {
                                        return {
                                            template: Vue.component("ChonTB_TinhcuoiTemplate", {
                                                template: `<div style="border:none">
                                            <input :value="data.ma_tb_tinh_cuoi" type="text" style="border:none" :readonly="true"/>
                                            <span class="-ap icon-more_horiz" v-on:click="handleLeftClick($event)"></span>
                                                </div>
                                    `,
                                                data() {
                                                    return {
                                                        data: {}
                                                    };
                                                },
                                                computed: {
                                                    parent() {
                                                        return this.$parent.$parent.$parent;
                                                    },
                                                },
                                                methods: {
                                                    handleLeftClick(event) {
                                                        this.parent.onClickLoadAccount(this.data,event,'MA_TB_TINH_CUOI')
                                                    },
                                                },
                                            }),
                                        };
                                    }
                                }
                                this.columns_ds_dulieu_chuadongbo.push(column_chuadongbo)
                                
                            }
                        }
                         console.log('this.dtDS_DOISOAT_TINH_DAGAN')
                        console.log(this.dtDS_DOISOAT_TINH_DAGAN)
                        if(this.dtDS_DOISOAT_TINH_DAGAN){
                            let key_dadongbo = Object.keys(this.dtDS_DOISOAT_TINH_DAGAN[0])
                            for (let i = 0; i < key_dadongbo.length; i++){
                                let column_dadongbo = {
                                    fieldName: key_dadongbo[i],
                                    headerText: key_dadongbo[i],
                                    allowFiltering: true,
                                    allowSorting: true,
                                }
                                this.columns_ds_dulieu_dadongbo.push(column_dadongbo)
                            }
                        }
                        this.ds_dulieu_chuadongbo = this.dtDS_DOISOAT_TINH_CHUAGAN;
                        this.ds_dulieu_dadongbo = this.dtDS_DOISOAT_TINH_DAGAN;
                        this.button.btnChuyenTinh.Visible = false;
                        this.button.btnXacNhanCQ.Visible = false;
                        this.button.btn_ImportExcel.Visible = false;
                        this.button.btn_XuatExcel.Visible = true;
                        break;
                    case "2":
                        this.Panel1.visible = true
                        this.Panel2.visible = false
                        this.dtDS_DOISOAT_XACNHAN_CQ =  this.changeKeysToUpper(this.GetData(await API.sp_lay_ds_doisoat_v3(this.axios, {
                            "kieu": 2,
                            "trangthai_xl":this.trangthaixl_id
                        })));   
                         this.dtDS_DOISOAT_TINH_DAGAN =  this.changeKeysToUpper(this.GetData(await API.sp_lay_ds_doisoat_v3(this.axios, {
                            "kieu": 1,
                            "trangthai_xl":this.trangthaixl_id
                        })));   
                        if(this.dtDS_DOISOAT_XACNHAN_CQ){
                            let key = Object.keys(this.dtDS_DOISOAT_XACNHAN_CQ[0])
                            key = key.filter(item => !['XAC_NHAN_CQ','ID_ROW','MA_TB_CQ','ID_HOPDONG','TEN_KH', 'MA_TB_TINH_DAU', 'MA_TB_TINH_CUOI','MA_TINH_CQ','MATINH_DAU','MATINH_CUOI'].includes(item))                          
                            key.splice(0, 0, ...['XAC_NHAN_CQ','ID_ROW','MA_TB_CQ','ID_HOPDONG','TEN_KH', 'MA_TB_TINH_DAU', 'MA_TB_TINH_CUOI','MA_TINH_CQ','MATINH_DAU','MATINH_CUOI'])
                                
                            for (let i = 0; i < key.length; i++){
                                let column_ds_doisoat = {
                                    fieldName: key[i],
                                    headerText: key[i],
                                    allowFiltering: true,
                                    allowSorting: true,
                                }
                                if(key[i] == 'XAC_NHAN_CQ'){
                                    column_ds_doisoat.visible = true
                                    column_ds_doisoat.template = function () { 
                                        return {
                                            template: Vue.component("ChonTemplate", {
                                                template: `<div class="select-custom" >
                                                                <ejs-dropdownlist id="xacnhan_cq" :change="cboXacnhan_CQ_SelectedValueChanged" class="combobox" :dataSource="model" :fields="fields_xacnhan" v-model="xacnhan_cq"></ejs-dropdownlist>                                                       
                                                            </div>`,
                                                data() {
                                                    return {
                                                        model: [],
                                                        xacnhan_cq: -1,
                                                        fields_xacnhan: { text: 'XAC_NHAN_CQ_TEXT', value: 'XAC_NHAN_CQ' },
                                                    };
                                                },
                                                computed: {
                                                    parent() {
                                                        return this.$parent.$parent.$parent;
                                                    },
                                                },
                                                mounted() {
                                                    this.model = [{
                                                            "XAC_NHAN_CQ": -1,
                                                            "XAC_NHAN_CQ_TEXT": "Chưa xác định"
                                                        },
                                                        {
                                                            "XAC_NHAN_CQ": 0,
                                                            "XAC_NHAN_CQ_TEXT": "Không phải chủ quản"
                                                        },
                                                        {
                                                            "XAC_NHAN_CQ": 2,
                                                            "XAC_NHAN_CQ_TEXT": "Xác nhận chủ quản"
                                                        }
                                                    ]
                                                    this.xacnhan_cq = this.model[0].XAC_NHAN_CQ
                                                },
                                                methods: {
                                                    cboXacnhan_CQ_SelectedValueChanged(value) {
                                                            this.parent.cboXacNhanCQ_EditValueChanged(this.data,value,'XAC_NHAN_CQ')
                                                    },
                                                },
                                            }),
                                        };
                                    }
                                }
                                if(key[i] == "MA_TB_CQ"){
                                        column_ds_doisoat.template = function () {
                                            return {
                                                template: Vue.component("ChonTB_CQTemplate", {
                                                    template: `<div style="border:none">
                                                            <input v-model="parent.ma_tb" type="text" style="border:none" :readonly="true"/>
                                                            <span class="-ap icon-more_horiz" v-on:click="handleLeftClick($event)"></span>
                                                                </div>
                                                    `,
                                                    data() {
                                                        return {
                                                            data: {}
                                                        };
                                                    },
                                                    mounted() {
                                                    },
                                                    computed: {
                                                        parent() {
                                                            return this.$parent.$parent.$parent;
                                                        },
                                                    },
                                                    methods: {
                                                        handleLeftClick(event) {
                                                            this.parent.onClickLoadAccount(this.data,event,'MA_TB_CQ')
                                                        },
                                                    },
                                                }),
                                            };
                                        }
                                    }
                                this.columns_ds_dulieu_tb.push(column_ds_doisoat)
                            }
                            this.ds_dulieu_thuebao_table = this.dtDS_DOISOAT_XACNHAN_CQ;
                           
                            this.button.btnChuyenTinh.Visible = false;
                            this.button.btnChuyenChuQuan.Visible = false;
                            this.button.btn_ImportExcel.Visible = false;
                            this.button.btnChonThueBao.ReadOnly = true;
                            this.button.btnCapNhat.Visible = false; 
                            this.button.btn_XuatExcel.Visible = true;
                        }
                        console.log(this.dtDS_DOISOAT_TINH_DAGAN)
                         if(this.dtDS_DOISOAT_TINH_DAGAN){
                                let key_dadongbo = Object.keys(this.dtDS_DOISOAT_TINH_DAGAN[0])
                                for (let i = 0; i < key_dadongbo.length; i++){
                                    let column_dadongbo = {
                                        fieldName: key_dadongbo[i],
                                        headerText: key_dadongbo[i],
                                        allowFiltering: true,
                                        allowSorting: true,
                                    }
                                    this.columns_ds_dulieu_dadongbo.push(column_dadongbo)
                                }
                            }
                            this.ds_dulieu_dadongbo = this.dtDS_DOISOAT_TINH_DAGAN;
                }
                
            }
            this.loading(false);
        },
        gridCommand_Thaotac: async function (type, dataItem) {
            //this.ma_tb = dataItem.MA_TB_CQ
            if(this.tag.toString() == "2"){
                this.ma_tb = dataItem.MA_TB_CQ
            }else{
                if(dataItem.TRANGTHAI_XL == 4){
                    this.ma_tb = dataItem.MA_TB_TINH_DAU?dataItem.MA_TB_TINH_DAU:dataItem.MA_TB_TINH_CUOI
                }else{
                    if(dataItem.MATINH_DAU == this.tt_nd.tentat){
                        this.ma_tb = dataItem.MA_TB_TINH_DAU
                    }
                    if(dataItem.MATINH_CUOI == this.tt_nd.tentat){
                        this.ma_tb = dataItem.MA_TB_TINH_CUOI
                    }
                }
            }
            this.dichvuvt_id = dataItem.DICHVUVT_ID
            this.row_id=dataItem.ID_ROW
            
            console.log(1111)
            console.log(this.dichvuvt_id)

            if(this.tag.toString() == "0"){
                if(dataItem.DICHVUVT_ID == 7||dataItem.DICHVUVT_ID == 8){
                  this.$refs.popupChangeMegawanInfor.showModal();
                }
                if(dataItem.DICHVUVT_ID == 9){
                    this.$refs.popupFrmThayDoiTsKTR.showModal();
                }
            }
            if(this.tag.toString() == "2"){
                if(dataItem.TRANGTHAI_XL == 6){
                    this.$toast.warning("Thuê bao đã xác nhận không phải chủ quản. Không thể cập nhật thông tin!");
                    return;
                }
                this.$refs.popupChangeCustomerContract.showModal();
            }
        },
        popupChangeCustomerContractClose(){
            this.$refs.popupChangeCustomerContract.hideModal();

        },
        popupChangeMegawanInforClose(){
            this.$refs.popupChangeMegawanInfor.hideModal();
        },
        popupFrmThayDoiTsKTRClose(){
            this.$refs.popupFrmThayDoiTsKTR.hideModal();
        },
        async onChangeTrangthai(item){  
             this.dtDS_DOISOAT_TINH_DAGAN =  this.changeKeysToUpper(this.GetData(await API.sp_lay_ds_doisoat_v3(this.axios, {
                "kieu": 1,
                "trangthai_xl":this.trangthaixl_id
            })));   
            if(this.dtDS_DOISOAT_TINH_DAGAN&&this.dtDS_DOISOAT_TINH_DAGAN.length >0){
                let ds_dulieu_dadongbo = []
                let key = Object.keys(this.dtDS_DOISOAT_TINH_DAGAN[0])
                //key.push('THAOTAC')
                console.log(key)
                for (let i = 0; i < key.length; i++)
                {
                    
                    if(key[i] === 'THAOTAC'){
                        ds_dulieu_dadongbo.push(
                            {fieldName: 'THAOTAC',
                             headerText: 'Xóa',
                              allowFiltering: false,
                            //   width:'6%',
                            //   commands:[{type:'Xóa',buttonOption:{cssClass: 'btn btn-second button-not-bg lh14 pad4',iconCss: '-ap icon-close'}}],
                              cssClass:'text-center'})
                
                    }else{
                         ds_dulieu_dadongbo.push(
                        {
                            fieldName: key[i],
                            headerText: key[i],
                            allowFiltering: true,
                            allowSorting: true,
                        });
                    }
                }
                this.columns_ds_dulieu_dadongbo = ds_dulieu_dadongbo
            }
            this.ds_dulieu_dadongbo = this.dtDS_DOISOAT_TINH_DAGAN;
        },
        async LoadDuLieu() {
            this.dtDS_DOISOAT_TINH_CHUAGAN = this.changeKeysToUpper(this.GetData(await API.sp_lay_ds_doisoat_v3(this.axios, {
                "kieu": 0,
                "trangthai_xl":this.trangthaixl_id
            })));   
            this.dtDS_DOISOAT_TINH_DAGAN =  this.changeKeysToUpper(this.GetData(await API.sp_lay_ds_doisoat_v3(this.axios, {
                "kieu": 1,
                "trangthai_xl":this.trangthaixl_id
            })));   
            if(this.dtDS_DOISOAT_TINH_CHUAGAN && this.dtDS_DOISOAT_TINH_CHUAGAN.length >0){
                let key = Object.keys(this.dtDS_DOISOAT_TINH_CHUAGAN[0])
                let ds_dulieu_chuadongbo = []
                for (let i = 0; i < key.length; i++)
                {  
                    ds_dulieu_chuadongbo.push(
                    {
                        fieldName: key[i],
                        headerText: key[i],
                        allowFiltering: true,
                        allowSorting: true,
                    });
                }
                this.columns_ds_dulieu_chuadongbo = ds_dulieu_chuadongbo
            }
            if(this.dtDS_DOISOAT_TINH_DAGAN&&this.dtDS_DOISOAT_TINH_DAGAN.length >0){
                let ds_dulieu_dadongbo = []
                let key = Object.keys(this.dtDS_DOISOAT_TINH_DAGAN[0])
                //key.push('THAOTAC')
                console.log(key)
                for (let i = 0; i < key.length; i++)
                {
                    
                    if(key[i] === 'THAOTAC'){
                        ds_dulieu_dadongbo.push(
                            {fieldName: 'THAOTAC',
                             headerText: 'Xóa',
                              allowFiltering: false,
                            //   width:'6%',
                            //   commands:[{type:'Xóa',buttonOption:{cssClass: 'btn btn-second button-not-bg lh14 pad4',iconCss: '-ap icon-close'}}],
                              cssClass:'text-center'})
                
                    }else{
                         ds_dulieu_dadongbo.push(
                        {
                            fieldName: key[i],
                            headerText: key[i],
                            allowFiltering: true,
                            allowSorting: true,
                        });
                    }
                     console.log(111111)
                    console.log(ds_dulieu_dadongbo)
                }
                this.columns_ds_dulieu_dadongbo = ds_dulieu_dadongbo
            }
        
            this.ds_dulieu_chuadongbo = this.dtDS_DOISOAT_TINH_CHUAGAN;
            this.ds_dulieu_dadongbo = this.dtDS_DOISOAT_TINH_DAGAN;
        },
        btn_ImportExcel_Click() {
            document.getElementById("uploadFile").click()
        },
        async importExcel(e) {
            const files = e.target.files
            if (!files.length) {
                return
            } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
                this.$toast.error("File excel không đúng định dạng!")
                return
            }
            this.readFile(files);
            this.Panel1.visible = true;
            this.Panel2.visible = false;
        },
        async readFile(file) {
            this.loading(true);
            const fileReader = new FileReader()
            fileReader.onload = (ev) => {
                try {
                    const data = ev.target.result
                    const workbook = XLSX.read(data, {
                        type: "binary",
                    })
                    const wsname = workbook.SheetNames[0]
                    const ws = XLSX.utils.sheet_to_json(workbook.Sheets[wsname],{
                      defval: '', 
                      blankrows: false 
                    })
                    
                    const excellist = [];
                    if (ws.length >= 1) {
                      for (var i = 0; i < ws.length; i++) {
                        delete ws[i]["__EMPTY"];
                        excellist.push(ws[i]);
                        
                      }  
                    } else {
                      this.$root.toastError("Không đọc được dữ liệu từ file!"); 
                    }
                    const keysToCheck = ["ID_ROW", "DICHVUVT_ID", "MATINH_DAU", "MATINH_CUOI", "MA_TB_TINH_DAU", "MA_TB_TINH_CUOI"];
                    let allKeysExist = true;
                    for (let i = 0; i < keysToCheck.length; i++) {
                      const key = keysToCheck[i];
                      if (!excellist[0].hasOwnProperty(key)) {
                        this.$toast.warning("Không tìm thấy cột: " + key);
                        return; 
                      }   
                    }  
                    this.columns = Object.keys(excellist[0])
                    this.columns.push("TRANGTHAI_XL"); 
                    
                    let data_execl = excellist.map(obj => {
                      return {
                        ...obj,
                        SEL: "",
                        TRANGTHAI_XL: "",
                        EXISTS: ""
                      };
                    });
                    this.dataImport = data_execl
                } catch (error) {
                    this.$toast.error("Lỗi khi đọc file! " + error)
                }
            }
            fileReader.readAsBinaryString(file[0])
            let dtDS_DOISOAT = this.changeKeysToUpper(this.GetData(await API.sp_lay_ds_doisoat_v3(this.axios, {
                "kieu": -1,
                "trangthai_xl":this.trangthaixl_id
            })));   
            for (let i = 0; i < this.dataImport.length; i++)
            {
               
                this.dataImport[i].SEL = false;
              
                let dr = dtDS_DOISOAT.filter(x => x.ID_ROW == this.dataImport[i]["ID_ROW"]);
                console.log(this.dataImport[i]["ID_ROW"])
                console.log(dr)
                    if (dr.length > 0)
                    {
                        
                        //this.dtDS_DOISOAT_TINH_CHUAGAN.find(obj => obj.ID_ROW === data.ID_ROW).MA_TINH_CQ
                        this.dataImport[i].EXISTS = 1;
                        this.dataImport[i].TRANGTHAI_XL = dr[0]["TRANGTHAI_XL"];
                    }
                    else
                    {
                        this.dataImport[i].EXISTS = 0;
                        this.dataImport[i].TRANGTHAI_XL = 1;
                    }
            }
            for (let i = 0; i < this.columns.length; i++){
                let column = {
                    fieldName: this.columns[i],
                    headerText: this.columns[i],
                    allowFiltering: true,
                    allowSorting: true,
                 }
                if(this.columns[i] == "MA_TB_CQ"){
                    column.template = function () {
                        return {
                            template: Vue.component("ChonTemplate", {
                                template: `<div style="border:none">
                            <input v-model="data.ma_tb_tinh" type="text" style="border:none" :readonly="true"/>
                            <span class="-ap icon-more_horiz" v-on:click="handleLeftClick"></span>
                                  </div>
                      `,
                                data() {
                                    return {
                                        data: {}
                                    };
                                },
                                computed: {
                                    parent() {
                                        return this.$parent.$parent.$parent;
                                    },
                                },
                                methods: {
                                    handleLeftClick() {
                                        this.parent.onClickLoadAccount()
                                    },
                                },
                            }),
                        };
                    }
                }
                 this.columns_ds_dulieu_tb.push(column)
            }
            this.ds_dulieu_thuebao_table = this.dataImport
            this.Panel1.visible = true
            this.loading(false);
        },
        setDisplayDropDownMenu() {
            this.isDisplayMenu = !this.isDisplayMenu;
        },
        hideDropdownMenu(e) {
            if (e.target !== this.$refs.mainWrap) {
                this.isDisplayMenu = false;
            }
        },
        async btnExportExcel_Click() {
            console.log(this.tag.toString())
            if(this.tag.toString() == "0"){
                if (this.ds_dulieu_chuadongbo != null && this.ds_dulieu_chuadongbo.length > 0) {
                    let data = XLSX.utils.json_to_sheet(this.ds_dulieu_chuadongbo)
                    let wb = XLSX.utils.book_new() 
                    XLSX.utils.book_append_sheet(wb, data, 'ds_dulieu_chuadongbo')  
                    XLSX.writeFile(wb, 'DsChuadongbo.xlsx')
                } 
                if (this.ds_dulieu_dadongbo != null && this.ds_dulieu_dadongbo.length > 0) {
                    let data1 = XLSX.utils.json_to_sheet(this.ds_dulieu_dadongbo)
                    let wb1 = XLSX.utils.book_new()
                    XLSX.utils.book_append_sheet(wb1, data1, 'ds_dulieu_dadongbo') 
                    XLSX.writeFile(wb1, 'DsDadongbo.xlsx') 
                } 
            } else if(this.tag.toString() == "1"){
                if (this.ds_dulieu_chuadongbo != null && this.ds_dulieu_chuadongbo.length > 0) {
                    let data = XLSX.utils.json_to_sheet(this.ds_dulieu_chuadongbo)
                    let wb = XLSX.utils.book_new() 
                    XLSX.utils.book_append_sheet(wb, data, 'ds_dulieu_chuadongbo') 
                    XLSX.writeFile(wb, 'DsChuadongbo.xlsx')
                 }
                if (this.ds_dulieu_dadongbo != null && this.ds_dulieu_dadongbo.length > 0) {
                    let data1 = XLSX.utils.json_to_sheet(this.ds_dulieu_dadongbo)
                    let wb1 = XLSX.utils.book_new()
                    XLSX.utils.book_append_sheet(wb1, data1, 'ds_dulieu_dadongbo') 
                    XLSX.writeFile(wb1, 'DsDadongbo.xlsx') 
                } 
            }else{
                 if (this.ds_dulieu_thuebao_table != null && this.ds_dulieu_thuebao_table.length > 0) {
                    let data = XLSX.utils.json_to_sheet(this.ds_dulieu_thuebao_table)
                    let wb = XLSX.utils.book_new() 
                    XLSX.utils.book_append_sheet(wb, data, 'ds_dulieu_thuebao_table') 
                    XLSX.writeFile(wb, 'DsDulieuDoisoat.xlsx')
                } 
                if (this.ds_dulieu_dadongbo != null && this.ds_dulieu_dadongbo.length > 0) {
                    let data1 = XLSX.utils.json_to_sheet(this.ds_dulieu_dadongbo)
                    let wb1 = XLSX.utils.book_new()
                    XLSX.utils.book_append_sheet(wb1, data1, 'ds_dulieu_dadongbo') 
                    XLSX.writeFile(wb1, 'DsDadongbo.xlsx') 
                } 
            }
        },
        btnGetData_Click() {
            this.frmDoiSoatDuLieu_Load();
        },
        gridSelected_DLDadongbo(value) {
        },
        gridSelected_DLChuadongbo(values) {
          this.data_checked_ds_chua_dongbo = values;
        },
        gridSelected_DL(value) {
            console.log(value)
        },
        async btnChuyenTinh_Click() {
            this.loading(true);
            let gridview = this.ds_dulieu_thuebao_table;
            if(this.Panel2.visible){
                gridview = this.ds_dulieu_chuadongbo
            }
            for (let i = 0; i < gridview.length; i++)
            {
                let trangthai_xl = parseInt(gridview[i]["TRANGTHAI_XL"]);
                
                let values = []; 
                
                for (let j = 0; j < this.columns.length; j++)
                {
                 
                    
                    if (this.columns[j] == "TRANGTHAI_XL")
                        values.push("1");
                    else
                    {
                        if(this.columns[j] in gridview[i]){
                            let value = gridview[i][this.columns[j]]?gridview[i][this.columns[j]].toString():"";
                            value = value.replace(/[\[\]]/g, '');
                            value = value.replace(/'/g, '');
                            values.push("'" + value + "'");
                        }
                    }
                }
                
                let ma_tinh_dau = gridview[i]["MATINH_DAU"]?gridview[i]["MATINH_DAU"].toString():"";
                let ma_tinh_cuoi = gridview[i]["MATINH_CUOI"]?gridview[i]["MATINH_CUOI"].toString():"";
                let id_row = gridview[i]["ID_ROW"].toString();
                let tinh_id = 0;
                
                if (ma_tinh_dau != "")
                {
                    tinh_id = this.GetData(await API.fn_tt_tinh(this.axios, {
                      "type": 9,
                      "param": ma_tinh_dau
                    }));
                    if(tinh_id != -1) {
                        let them_dl_doisoat = await API.sp_them_dl_doisoat(this.axios, {
                            "p_id_row": id_row.toString(),
                            "p_columns": this.columns.toString(),
                            "p_values": values.toString(),
                            "tinhthicong_id": tinh_id.toString(),
                            "p_ttxl_id": 1,
                            "p_thuebao_id": 1,
                            "p_ma_tb_tinh": "",
                            "p_daucuoi": 0,
                        });
                        if (them_dl_doisoat.data.error != 200 && them_dl_doisoat.data.error_code != 'BSS-00000000') {
                            this.$toast.error(them_dl_doisoat.data.message_detail);
                            return;
                        } 
                    }
                     

                }
                if (ma_tinh_cuoi != ma_tinh_dau)
                {
                    tinh_id = this.GetData(await API.fn_tt_tinh(this.axios, {
                        "type": 9,
                        "param": ma_tinh_cuoi
                    }));
                    if(tinh_id != -1) {
                        let them_dl_doisoat = this.GetData(await API.sp_them_dl_doisoat(this.axios, {
                            "p_id_row": id_row.toString(),
                            "p_columns": this.columns.toString(),
                            "p_values": values.toString(),
                            "tinhthicong_id": tinh_id.toString(),
                            "p_ttxl_id": 1,
                            "p_thuebao_id": 1,
                            "p_ma_tb_tinh": "",
                            "p_daucuoi": 0,
                        }));
                    }
                } 
                let CAPNHAT_TT_DL_DOISOAT = this.GetData(await API.sp_capnhat_tt_dl_doisoat(this.axios, {
                    "tinhthicong_id":this.tt_nd.phanvung_id,
                    "p_id_row":id_row,
                    "p_ttxl_id": 2
                }));
                let CAPNHAT_LOG = this.GetData(await API.sp_them_log_dl_doisoat(this.axios, {
                    "p_id_row":id_row,
                    "p_ttxl_id": 2
                }));
            }
            this.loading(false);
            this.$root.$toast.success("Cập nhật thành công");
        },
        changeKeysToUpper(obj) {
            var key, upKey;
            for (key in obj) {
                if (obj.hasOwnProperty(key)) {
                    upKey = key.toUpperCase();
                    if (upKey !== key) {
                        obj[upKey] = obj[key];
                        delete(obj[key]);
                    }
                    // recurse
                    if (typeof obj[upKey] === "object") {
                        this.changeKeysToUpper(obj[upKey]);
                    }
                }
            }
            return obj;
        },
        async btnCapNhat_Click()
        {
            this.loading(true);
            switch (this.tag.toString())
            {
                case "0":
                    let id_rows_del = [];
                     this.columns = this.columns.filter(item => item !== "PHANVUNG_ID");
                    for (let i = 0; i < this.ds_dulieu_thuebao_table.length; i++)
                    {
                        let exists = this.ds_dulieu_thuebao_table[i].EXISTS;
                        let trangthai_xl = this.ds_dulieu_thuebao_table[i].TRANGTHAI_XL;
                        let id_row = this.ds_dulieu_thuebao_table[i].ID_ROW;
                        if (exists == 1){
                             let xoa_ds_doisoat = this.GetData(await API.sp_xoa_ds_doisoat(this.axios, {
                                    "id_row":id_row,
                                }));
                        }
                        const values = [];
                        for (let j = 0; j < this.columns.length; j++)
                        {
                            if(this.columns[j] in this.ds_dulieu_thuebao_table[i]){
                                let value = this.ds_dulieu_thuebao_table[i][this.columns[j]].toString();
                                value = value.replace(/[\[\]]/g, '');
                                value = value.replace(/'/g, '');
                                values.push("'" + value + "'");
                                
                            }
                        }
                        
                        values.push("sysdate");
                        values.push("sysdate");
                        values.push("'" + this.tt_nd.ma_nd + "'");
                        values.push("'" + this.tt_nd.may_cn + "'");
                        values.push("'" + this.tt_nd.ip + "'");
                       
                        const columns_new = this.columns;
                        if (!columns_new.includes("NGAY_INS"))
                        {
                            columns_new.push("NGAY_INS");
                            columns_new.push("NGAY_CN");
                            columns_new.push("NGUOI_CN");
                            columns_new.push("MAY_CN");
                            columns_new.push("IP_CN");
                        }
                         let them_dl_doisoat = await API.sp_them_dl_doisoat(this.axios, {
                            "p_id_row": id_row.toString(),
                            "p_columns": columns_new.toString(),
                            "p_values": values.toString(),
                            "tinhthicong_id": this.tt_nd.phanvung_id,
                            "p_ttxl_id": 1,
                            "p_thuebao_id": 1,
                            "p_ma_tb_tinh": "",
                            "p_daucuoi": 0,
                        });
                        if (them_dl_doisoat.data.error != 200&&them_dl_doisoat.data.error_code != 'BSS-00000000') {
                            this.$toast.error(them_dl_doisoat.data.message_detail);
                            return;
                        } 
                    }
                    this.dtDS_DOISOAT_TINH_CHUAGAN = this.changeKeysToUpper(this.GetData(await API.sp_lay_ds_doisoat_v3(this.axios, {
                        "kieu": 0,
                        "trangthai_xl":this.trangthaixl_id
                    })));   
                    this.dtDS_DOISOAT_TINH_DAGAN =  this.changeKeysToUpper(this.GetData(await API.sp_lay_ds_doisoat_v3(this.axios, {
                        "kieu": 1,
                        "trangthai_xl":this.trangthaixl_id
                    })));   
                    this.Panel1.visible = false;
                    this.Panel2.visible = true;
                    this.ds_dulieu_chuadongbo = this.dtDS_DOISOAT_TINH_CHUAGAN;
                    this.ds_dulieu_dadongbo = this.dtDS_DOISOAT_TINH_DAGAN;
                    break;
                case "1":
                    break;
            }
            this.loading(false);
            this.$root.$toast.success("Cập nhật thành công");
            this.frmDoiSoatDuLieu_Load()
        },
        async cboTinhChuQuan_EditValueChanged(data,value,fieldName)
        {
            if (fieldName == "MA_TINH_CQ")
            {
                let ma_tinh_cq = value.itemData.ma_tinh_cq
                let id_row = data.ID_ROW;
                this.dtDS_DOISOAT_TINH_CHUAGAN.find(obj => obj.ID_ROW === data.ID_ROW).MA_TINH_CQ = value.itemData.ma_tinh_cq;
                
                
                let CAPNHAT_CQ_DOISOAT_TINH = await API.sp_capnhat_cq_doisoat_tinh(this.axios,{
                    "phanvung_id": this.tt_nd.phanvung_id,
                    "p_id_row": id_row,
                    "p_ma_tinh_cq": ma_tinh_cq
                });
                if (CAPNHAT_CQ_DOISOAT_TINH.data.error != 200&&CAPNHAT_CQ_DOISOAT_TINH.data.error_code != 'BSS-00000000') {
                            this.$toast.error(CAPNHAT_CQ_DOISOAT_TINH.data.message_detail);
                            return;
                } 
            } 
        },
        cboXacNhanCQ_EditValueChanged(data,value,fieldName)
        {
            this.dtDS_DOISOAT_XACNHAN_CQ.find(obj => obj.ID_ROW === data.ID_ROW).XAC_NHAN_CQ = value.itemData.XAC_NHAN_CQ;
        },
        async btnXacNhanCQ_Click()
        {
            this.loading(true);
            let ds_dulieu_thuebao_table = this.dtDS_DOISOAT_XACNHAN_CQ;
            this.columns = Object.keys(this.dtDS_DOISOAT_XACNHAN_CQ[0])
            this.columns = this.columns.filter(item => item !== "PHANVUNG_ID"&&item !== "TRANGTHAI_CAPNHAT_TTKH_TEXT"&&item !== "TRANGTHAI_CAPNHAT_TSKT_TEXT");
            let values = [];  
            for (let i = 0; i < ds_dulieu_thuebao_table.length; i++)
            {
                let xac_nhan_cq = ds_dulieu_thuebao_table[i].XAC_NHAN_CQ;
                
                let id_row = ds_dulieu_thuebao_table[i].ID_ROW;
               
                let CAPNHAT_XACNHAN_CQ = this.GetData(await API.sp_capnhat_xacnhan_cq(this.axios, {
                    "id_row": id_row,
                    "xac_nhan_cq": xac_nhan_cq
                }));
                
                let ma_tinh_dau = ds_dulieu_thuebao_table[i].MATINH_DAU?ds_dulieu_thuebao_table[i].MATINH_DAU.toString():"";
                let ma_tinh_cuoi = ds_dulieu_thuebao_table[i].MATINH_CUOI?ds_dulieu_thuebao_table[i].MATINH_CUOI.toString():"";
 
                if (xac_nhan_cq == "2")
                {
                    let ma_tb_cq = ds_dulieu_thuebao_table[i].MA_TB_CQ?ds_dulieu_thuebao_table[i].MA_TB_CQ.toString():"";
                    console.log(ma_tb_cq)
                    let thuebao_cq_id = ds_dulieu_thuebao_table[i].TB_ID_CQ?ds_dulieu_thuebao_table[i].TB_ID_CQ:0;
                    if (thuebao_cq_id != 0)
                    {
                        let tinh_tc_dau = 0
                        let tinh_tc_cuoi = 0
                        let tinh_id = 0;
                        let thuebao_id = 0;
                        if (ma_tinh_dau != "")
                        {
                            let ma_tb_tinh_dau = ds_dulieu_thuebao_table[i].MA_TB_TINH_DAU?ds_dulieu_thuebao_table[i].MA_TB_TINH_DAU.toString():"";
                            tinh_tc_dau = this.GetData(await API.fn_tt_tinh(this.axios, {
                            "type": 9,
                            "param": ma_tinh_dau
                            }));
                            
                            thuebao_id = ds_dulieu_thuebao_table[i].TB_ID_TINH_DAU? ds_dulieu_thuebao_table[i].TB_ID_TINH_DAU.toString():"";
                            
                            let DONGBO_DOISOAT_BC = this.GetData(await API.sp_dongbo_doisoat(this.axios, {
                                "tinhthicong_id": tinh_tc_dau,
                                "p_thuebao_id": thuebao_id,
                                "p_thuebao_kn_id": thuebao_cq_id,
                                "p_tinh_kn_id": this.tt_nd.phanvung_id,
                                "p_loai_bc": 2,
                                "p_ma_tb_tinh": ma_tb_tinh_dau,
                                "p_daucuoi_id": 1
                            }));
                             let DONGBO_DOISOAT = this.GetData(await API.sp_dongbo_doisoat(this.axios, {
                                "tinhthicong_id": this.tt_nd.phanvung_id,
                                "p_thuebao_id": thuebao_cq_id,
                                "p_thuebao_kn_id": thuebao_id,
                                "p_tinh_kn_id": tinh_id,
                                "p_loai_bc": 1,
                                "p_ma_tb_tinh": ma_tinh_dau,
                                "p_daucuoi_id": 1
                            }));
                            
                            let CAPNHAT_TT_DL_DOISOAT = this.GetData(await API.sp_capnhat_tt_dl_doisoat(this.axios, {
                                "tinhthicong_id":tinh_tc_dau,
                                "p_id_row":id_row,
                                "p_ttxl_id": 4
                            }));
                        }
                        if (ma_tinh_cuoi != "")
                        {
                            let ma_tb_tinh_cuoi = ds_dulieu_thuebao_table[i].MA_TB_TINH_CUOI?ds_dulieu_thuebao_table[i].MA_TB_TINH_CUOI.toString():"";
                            tinh_tc_cuoi = this.GetData(await API.fn_tt_tinh(this.axios, {
                            "type": 9,
                            "param": ma_tinh_cuoi
                            }));                    
                            thuebao_id = ds_dulieu_thuebao_table[i].TB_ID_TINH_CUOI?ds_dulieu_thuebao_table[i].TB_ID_TINH_CUOI.toString():"";
                            let DONGBO_DOISOAT_BC = this.GetData(await API.sp_dongbo_doisoat(this.axios, {
                                "tinhthicong_id":tinh_tc_cuoi,
                                "p_thuebao_id": thuebao_id,
                                "p_thuebao_kn_id": thuebao_cq_id,
                                "p_tinh_kn_id": this.tt_nd.phanvung_id,
                                "p_loai_bc": 2,
                                "p_ma_tb_tinh": ma_tb_tinh_cuoi,
                                "p_daucuoi_id": 2
                            }));
                             let DONGBO_DOISOAT = this.GetData(await API.sp_dongbo_doisoat(this.axios, {
                                "tinhthicong_id":this.tt_nd.phanvung_id,
                                "p_thuebao_id": thuebao_cq_id,
                                "p_thuebao_kn_id": thuebao_id,
                                "p_tinh_kn_id": tinh_id,
                                "p_loai_bc": 1,
                                "p_ma_tb_tinh": ma_tinh_dau,
                                "p_daucuoi_id": 2
                            }));
                            let CAPNHAT_TT_DL_DOISOAT_BC = this.GetData(await API.sp_capnhat_tt_dl_doisoat(this.axios, {
                                "tinhthicong_id":tinh_tc_cuoi,
                                "p_id_row":id_row,
                                "p_ttxl_id": 4
                            }));
                            
                        }
                         let CAPNHAT_TT_DL_DOISOAT = this.GetData(await API.sp_capnhat_tt_dl_doisoat(this.axios, {
                                "tinhthicong_id":this.tt_nd.phanvung_id,
                                "p_id_row":id_row,
                                "p_ttxl_id": 4
                            }));
                        let CAPNHAT_CQ_DOISOAT_TINH = this.GetData(await API.sp_capnhat_cq_doisoat_tinh(this.axios,{
                            "phanvung_id": this.tt_nd.phanvung_id,
                            "p_id_row": id_row,
                            "p_ma_tinh_cq": this.tt_nd.tentat
                        }));
                        let CAPNHAT_LOG = this.GetData(await API.sp_them_log_dl_doisoat(this.axios, {
                            "p_id_row":id_row,
                            "p_ttxl_id": 4
                        }));
                    }
                }
                else
                    if (xac_nhan_cq == "0")
                    {
                        let dtTinh = this.GetData(await API.sp_lay_ds_tinh(this.axios, {}));
                        let CAPNHAT_TT_DL_DOISOAT = this.GetData(await API.sp_capnhat_tt_dl_doisoat(this.axios, {
                                "tinhthicong_id":this.tt_nd.phanvung_id,
                                "p_id_row":id_row,
                                "p_ttxl_id": 6
                            }));
                        let CAPNHAT_LOG = this.GetData(await API.sp_them_log_dl_doisoat(this.axios, {
                            "p_id_row":id_row,
                            "p_ttxl_id": 6
                        }));
                        for (let x = 0; x < dtTinh.length; x++)
                        {
                            let tinh_id = parseInt(dtTinh[x]["tinh_id"].toString());
                            values = [];
                            if (tinh_id != this.tt_nd.phanvung_id &&
                                (ma_tinh_dau.trim() == "" || (ma_tinh_dau.trim() != "" && !dtTinh[x]["tentinh"].toString().includes(ma_tinh_dau.trim()))) &&
                                (ma_tinh_cuoi.trim() == "" || (ma_tinh_cuoi.trim() != "" && !dtTinh[x]["tentinh"].toString().includes(ma_tinh_cuoi.trim()))))
                            {
                                for (let j = 0; j < this.columns.length; j++)
                                {
                                    if (this.columns[j] == "TRANGTHAI_XL")
                                        values.push("5");
                                    else
                                    {
                                        if(this.columns[j] in ds_dulieu_thuebao_table[i]){
                                        
                                            let value = ds_dulieu_thuebao_table[i][this.columns[j]]?ds_dulieu_thuebao_table[i][this.columns[j]].toString():'';
                                            
                                             value = value.replace(/[\[\]]/g, '');
                                            value = value.replace(/'/g, '');
                                            values.push("'" + value + "'");
                                        }
                                    }
                                }
                                 let them_dl_doisoat = this.GetData(await API.sp_them_dl_doisoat(this.axios, {
                                    "p_id_row": id_row.toString(),
                                    "p_columns": this.columns.toString(),
                                    "p_values": values.toString(),
                                    "tinhthicong_id": tinh_id?tinh_id.toString():"",
                                    "p_ttxl_id": 1,
                                    "p_thuebao_id": 1,
                                    "p_ma_tb_tinh": "",
                                    "p_daucuoi": 0,
                                }));
                            }
                        }
                    }
            }
            this.loading(false);
            this.$root.$toast.success("Cập nhật thành công");
            //this.frmDoiSoatDuLieu_Load()
        },
        async btnChuyenChuQuan_Click()
        {
            this.ds_dulieu_chuadongbo = []
            this.columns = []
            this.loading(true);
            //this.ds_dulieu_chuadongbo = this.dtDS_DOISOAT_TINH_CHUAGAN
			this.ds_dulieu_chuadongbo = this.data_checked_ds_chua_dongbo
           
            this.columns = Object.keys(this.dtDS_DOISOAT_TINH_CHUAGAN[0])
            this.columns = this.columns.filter(item => item !== "PHANVUNG_ID"&&item !== "TRANGTHAI_CAPNHAT_TTKH_TEXT"&&item !== "TRANGTHAI_CAPNHAT_TSKT_TEXT");
            for (let i = 0; i < this.ds_dulieu_chuadongbo.length; i++)
            {
                let values = [];
                for (let j = 0; j < this.columns.length; j++)
                {
                    
                        if (this.columns[j] == "TRANGTHAI_XL")
                            values.push("5");
                        else
                        {
                            if(this.columns[j] in this.ds_dulieu_chuadongbo[i]){
                               
                                let value = this.ds_dulieu_chuadongbo[i][this.columns[j]]?this.ds_dulieu_chuadongbo[i][this.columns[j]].toString():'';
                                
                                value = value.replace(/[\[\]]/g, '');
                                value = value.replace(/'/g, '');
                                values.push("'" + value + "'");
                            }
                        }
                }
                
                let ma_tinh_cq = this.ds_dulieu_chuadongbo[i].MA_TINH_CQ?this.ds_dulieu_chuadongbo[i].MA_TINH_CQ.toString():"";
                let ttxl_id = this.ds_dulieu_chuadongbo[i].TRANGTHAI_XL?this.ds_dulieu_chuadongbo[i].TRANGTHAI_XL.toString():"";
                if (ma_tinh_cq != "" && ttxl_id != "5")
                {
                    let tinh_id = this.GetData(await API.fn_tt_tinh(this.axios, {
                      "type": 9,
                      "param": ma_tinh_cq
                    }));
                    
                    let id_row = this.ds_dulieu_chuadongbo[i].ID_ROW;
                    let ma_tinh_dau = this.ds_dulieu_chuadongbo[i].MATINH_DAU;
                    let ma_tinh_cuoi = this.ds_dulieu_chuadongbo[i].MATINH_CUOI;
                    let ma_tb_tinh = "";
                    let thuebao_id = 0;
                    let daucuoi = 0;
                    if (ma_tinh_dau != "" && ma_tinh_dau == this.tt_nd.tentat)
                    {
                        tinh_id = this.GetData(await API.fn_tt_tinh(this.axios, {
                        "type": 9,
                        "param": ma_tinh_dau
                        }));
                        ma_tb_tinh =  this.ds_dulieu_chuadongbo[i].MA_TB_TINH_DAU?this.ds_dulieu_chuadongbo[i].MA_TB_TINH_DAU.toString():"";
                        thuebao_id = this.ds_dulieu_chuadongbo[i].TB_ID_TINH_DAU?this.ds_dulieu_chuadongbo[i].TB_ID_TINH_DAU:0;;
                        daucuoi = 1;
                    }
                     let them_dl_doisoat_dau = this.GetData(await API.sp_them_dl_doisoat(this.axios, {
                        "p_id_row": id_row.toString(),
                        "p_columns": this.columns.toString(),
                        "p_values": values.toString(),
                        "tinhthicong_id": tinh_id.toString(),
                        "p_ttxl_id": 5,
                        "p_thuebao_id": thuebao_id,
                        "p_ma_tb_tinh": ma_tb_tinh,
                        "p_daucuoi":daucuoi,
                    }));
                    if (ma_tinh_cuoi != "" && ma_tinh_cuoi == this.tt_nd.tentat)
                    {
                        tinh_id = this.GetData(await API.fn_tt_tinh(this.axios, {
                        "type": 9,
                        "param": ma_tinh_cuoi
                        }));
                        ma_tb_tinh = this.ds_dulieu_chuadongbo[i].MA_TB_TINH_CUOI?this.ds_dulieu_chuadongbo[i].MA_TB_TINH_CUOI.toString():"";
                        thuebao_id = this.ds_dulieu_chuadongbo[i].TB_ID_TINH_CUOI?this.ds_dulieu_chuadongbo[i].TB_ID_TINH_CUOI:0;
                        daucuoi = 2;
                    }
                    let them_dl_doisoat = this.GetData(await API.sp_them_dl_doisoat(this.axios, {
                        "p_id_row": id_row.toString(),
                        "p_columns": this.columns.toString(),
                        "p_values": values.toString(),
                        "tinhthicong_id": tinh_id.toString(),
                        "p_ttxl_id": 5,
                        "p_thuebao_id": thuebao_id,
                        "p_ma_tb_tinh": ma_tb_tinh,
                        "p_daucuoi":daucuoi,
                    }));
                    let CAPNHAT_TT_DL_DOISOAT = this.GetData(await API.sp_capnhat_tt_dl_doisoat(this.axios, {
                        "p_id_row":id_row,
                        "p_ttxl_id": 5,
                        "tinhthicong_id": this.tt_nd.phanvung_id
                    }));
                }
            }
            this.LoadDuLieu();
            this.loading(false);
            this.$root.$toast.success("Chuyển chủ quản thành công");
        },
        gridviewDanhSach_RowCellStyle(arg) {
            const { data, column } = arg
            const rgb = (color) => {
                return `rgb(${color})`
            }
            if (column.field == 'EXISTS') {
                if (data.EXISTS == 1) {
                    arg.cell.style.backgroundColor = '#FFCC99'
                }
            }
        },
        GetData: function (response) {
            if (response.data.error === 200 || response.data.error === '200') {
                return response.data.data
            } else {
                return response.data.message_detail
            }
            return []
        },
        async searchAccountDialogClose(data) {
            if (data != null) {
                const row = this.Event.target.closest("tr");
                const cell = this.Event.target.closest("td");
               
                
                Vue.nextTick(() => {
                    const rowIndex = row.rowIndex -1; 
                    const columnIndex = cell.cellIndex;
                    const input = row.querySelector(`td:nth-child(${columnIndex + 1}) input`);
                    input.value = data.ma_tb;
                })
                    if (data.thuebao.ma_tb == null || data.thuebao.thuebao_id < 0)
                        return;
                    if (this.data_chon.DICHVUVT_ID != data.thuebao.dichvuvt_id)
                    {
                         console.log(this.data_chon.DICHVUVT_ID +':'+ data.thuebao.dichvuvt_id)
                        this.$toast.warning("Dịch vụ viễn thông không đúng!");
                        return;
                    }
                    
                    if(data.ma_tb){
                        let daucuoi = 0;
                        if (this.fieldName == "MA_TB_TINH_DAU")
                        {
                            daucuoi = 1;
                            const newData = JSON.parse(JSON.stringify(this.dtDS_DOISOAT_TINH_CHUAGAN));
                            const index = newData.findIndex(x => x.ID_ROW === this.data_chon.ID_ROW);
                            if (index !== -1) {
                                newData[index].TB_ID_TINH_DAU = data.thuebao.thuebao_id; 
                                newData[index].MA_TB_TINH_DAU = data.thuebao.ma_tb; 
                            }
                            this.dtDS_DOISOAT_TINH_CHUAGAN = newData; ;
                        }
                        if (this.fieldName == "MA_TB_TINH_CUOI")
                        {
                            
                            daucuoi = 2;
                            const newData = JSON.parse(JSON.stringify(this.dtDS_DOISOAT_TINH_CHUAGAN));
                            const index = newData.findIndex(x => x.ID_ROW == this.data_chon.ID_ROW);
                            if (index !== -1) {
                                newData[index].TB_ID_TINH_CUOI = data.thuebao.thuebao_id; 
                                newData[index].MA_TB_TINH_CUOI = data.thuebao.ma_tb; 
                            }
                            this.dtDS_DOISOAT_TINH_CHUAGAN = newData; 
                        }
                        if (this.fieldName == "MA_TB_CQ")
                        {
                            const newData = JSON.parse(JSON.stringify(this.dtDS_DOISOAT_XACNHAN_CQ));
                            const index = newData.findIndex(x => x.ID_ROW == this.data_chon.ID_ROW);
                            if (index !== -1) {
                                newData[index].TB_ID_CQ = data.thuebao.thuebao_id; 
                                newData[index].MA_TB_CQ = data.ma_tb; 
                            }
                            let CAPNHAT_TB_DOISOAT_XACNHAN_CQ = this.GetData(await API.sp_capnhat_tb_doisoat_xacnhan_cq(this.axios,{
                                    "id_row": this.data_chon.ID_ROW,
                                    "ma_tb": data.ma_tb,
                                    "thuebao_id": data.thuebao.thuebao_id
                            }));
                            this.dtDS_DOISOAT_XACNHAN_CQ = newData; 
                        }
                    }
                    
            }
            this.$refs.searchAccountModal.hideModal()
        },
        onClickLoadAccount(data,event,fieldName) {
            this.Event = event;
            
            this.fieldName = fieldName;
            this.data_chon = data
            if (this.fieldName == "MA_TB_TINH_DAU" && (!Boolean(this.data_chon.MATINH_DAU) 
                    || this.data_chon.MATINH_DAU != this.tt_nd.tentat))
            {
                    this.$toast.warning("Tỉnh thi công không phải tỉnh đầu!");
                    return; 
            }
            if (this.fieldName == "MA_TB_TINH_CUOI" && (!Boolean(this.data_chon.MATINH_CUOI)
                    || this.data_chon.MATINH_CUOI != this.tt_nd.tentat))
            {
                    this.$toast.warning("Tỉnh thi công không phải tỉnh cuối!");
                    return;
            }
            this.$refs.searchAccountModal.showModal();
        },
    },
};
</script>
<style>
.modal-xl {
    max-width: 1340px !important;
}
</style>