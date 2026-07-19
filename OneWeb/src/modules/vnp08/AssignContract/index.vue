<template src="./index.html"></template>
<script>
import api from "./api/index.js";
import * as moment from 'moment';
import Vue from "vue";
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue";
import VueFlatPickr from "vue-flatpickr-component";
import gridview from "@/components/Shared/gridview";
import DataGridNV from "./components/DataGrid";
// CSS
import "@/assets/vendor/bootstrap-table/bootstrap-table.min.css";
import "@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css";
// JS
import "@/assets/vendor/jquery/split";
import "@/assets/vendor/bootstrap-table/bootstrap-table.min";
import "@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min";
export default {
    provide: {
    },
    components: {
        api,
        SearchContractModal,
        VueFlatPickr, gridview, DataGridNV
    },
    props: ['Tag', "gan_hd"],
    computed: {
    },
    watch:{
        donvi_id(newvalue, oldvalue){
            this.getDsNvDaGan();
            this.getDsNvChuaGan();
            this.getDSCongDoan();
            //
            
        },
        hdtbID(newvalue, oldvalue){
            this.getDSCongDoan();
        },
        check_vk(newvalue, oldvalue){
            if(!newvalue){
                this.items.file_upload = ''
            }
        }
    },
    mounted() {
        // this.Load_start(); // Load khi khởi tạo
        // this.init(); // Load các api khởi tạo.
        // this.Load_form();
        // this.$nextTick(() => this.$refs.focus.focus());
        //Xử lý popup
        // if (this.gan_hd) {
        //     this.kieugoi_id = 1;
        //     this.txtMaGD = vtimkiem_hdkh_id;
        //     this.TimThongTinKH();
        // }
    },
    created() {
        this.initData();
        // console.log('xxx', store.dispatch('setStoredsDonVi'))
    },
    data() {
        return {
            header: {
                title: "Gán nhân viên quản lý thuê bao theo hợp đồng",
                list: [{
                    name: "Quản lý kênh",
                    link: {
                        name: "Ui.cards"
                    }
                },
                {
                    name: "Quản lý thông tin kênh bán",
                    link: {
                        name: "Ui.buttons"
                    },
                },
                ],
            },
            // Khai báo thuộc tính:
            Tab1: true,
            Tab2: false,
            Tab3: false,
            Tab4: false,
            Tab5: false,
            TabGIANTIEP: false,
            selectedOption: 2,
            hideTab: false,
            check_toanbo: false,
            check_all: false,
            check_vk: false,
            Tag: "",
            txtMaGD: "",
            label_trangthai: "",
            check_load: 0,
            dvi_tt: -1,
            flag_gan: true,
            sotb_tam: "",
            kieugoi_id: 1,
            loainv_id: 0,
            donvi_id: 0,
            dateTimeString: "",
            hdkhId: 0,
            thuebaoID: 0,
            khachhangID: 0,
            hdtbID: 0,
            donviql_id: 0,
            data_cskh: [],
            dt_Nhanvien_All: [],
            dt_HopDong_All: [],
            options: {
                cbx_dsloainv: [],
                cbx_dsdonvi: [],
            },
            ds_danhba_timkiem: {
                header: [
                    {
                        headerText: "Số máy ACC",
                        fieldName: "ma_tb",
                        allowSorting: false,
                        width: "30",
                        textAlign: "left",
                        autoFit: "true",
                        allowFiltering: true,
                    },
                    {
                        headerText: "Dịch vụ",
                        fieldName: "ten_dvvt",
                        allowSorting: false,
                        width: "30",
                        textAlign: "center",
                        autoFit: "true",
                        allowFiltering: true,
                    },
                    {
                        headerText: "Loại hình",
                        fieldName: "loaihinh_tb",
                        allowSorting: false,
                        width: "30",
                        textAlign: "center",
                        autoFit: "true",
                        allowFiltering: true,
                    },
                    {
                        headerText: "Tên thuê bao",
                        fieldName: "ten_tb",
                        allowSorting: false,
                        width: "40",
                        textAlign: "center",
                        autoFit: "true",
                        allowFiltering: true,
                    },
                    {
                        headerText: "Trạng thái TB",
                        fieldName: "trangthai_tb",
                        allowSorting: false,
                        width: "50",
                        textAlign: "center",
                        autoFit: "true",
                        allowFiltering: true,
                    },
                    {
                        headerText: "Địa chỉ TB",
                        fieldName: "diachi_tb",
                        allowSorting: false,
                        width: "100",
                        textAlign: "left",
                        autoFit: "true",
                        allowFiltering: true,
                    },
                ],
                list: [],
            },
            ds_hopdong_thuebao_timkiem: {
                header: [
                    {
                        headerText: "Mã GD",
                        fieldName: "maGd",
                        allowSorting: false,
                        width: "50",
                        textAlign: "center",
                        autoFit: "true",
                        allowFiltering: true,
                    },
                    {
                        headerText: "Tên loại HĐ",
                        fieldName: "tenLoaiHd",
                        allowSorting: false,
                        width: "50",
                        textAlign: "center",
                        autoFit: "true",
                        allowFiltering: true,
                    },
                    {
                        headerText: "Ngày yêu cầu",
                        fieldName: "ngayYc",
                        allowSorting: false,
                        width: "50",
                        textAlign: "center",
                        autoFit: "true",
                        allowFiltering: true,
                    },
                    {
                        headerText: "Trạng thái",
                        fieldName: "trangThaiHd",
                        allowSorting: false,
                        textAlign: "center",
                        width: "40",
                        autoFit: "true",
                        allowFiltering: true,
                    },
                    {
                        headerText: "Đơn vị lập HĐ/PL",
                        fieldName: "tenDv",
                        allowSorting: false,
                        textAlign: "center",
                        width: "60",
                        autoFit: "true",
                        allowFiltering: true,
                    },
                ],
                list: [],
            },
            ds_nhavien_chuagan: {
                header: [
                    {
                        headerText: "Mã NV",
                        fieldName: "ma_nv",
                        allowSorting: false,
                        // width: "45",
                        textAlign: "center",
                        autoFit: "true",
                        allowFiltering: true,
                        allowEditing: false,
                    },
                    {
                        headerText: "Tên NV",
                        fieldName: "ten_nv",
                        allowSorting: false,
                        // width: "45",
                        textAlign: "center",
                        autoFit: "true",
                        allowFiltering: true,
                        allowEditing: false,
                    },
                    {
                        fieldName: 'tyle', headerText: 'Tỷ lệ', allowFiltering: true, allowSorting: false, 
                        // width: "40",
                        template: () => {
                            return {
                                template: {
                                    template: `<div>
                                                <input type="text" class="form-control text" :value="data.tyle" @change="onChangeNoiDung">
                                            </div>`,
                                    data: function () {
                                        return {
                                            data: {},
                                        };
                                    },
                                    computed: {
                                        parent() {
                                            return this.$parent.$parent.$parent;
                                        },
                                    },
                                    methods: {
                                        onChangeNoiDung(e) {
                                            var index = this.$parent.$parent.$parent.ds_nhavien_chuagan.list.findIndex(e => e.tyle == this.data.tyle)
                                            if (index > -1) this.$parent.$parent.$parent.ds_nhavien_chuagan.list[index]['tyle'] = e.target.value
                                        },
                                    },
                                },
                            };
                        },
                    },
                    {
                        fieldName: 'tyle_hh', headerText: 'TL % thù lao', allowFiltering: true, allowSorting: false, 
                        // width: "60",
                        template: () => {
                            return {
                                template: {
                                    template: `<div>
                                                <input type="text" class="form-control text" :value="data.tyle_hh" @change="onChangeNoiDung">
                                            </div>`,
                                    data: function () {
                                        return {
                                            data: {},
                                        };
                                    },
                                    computed: {
                                        parent() {
                                            return this.$parent.$parent.$parent;
                                        },
                                    },
                                    methods: {
                                        onChangeNoiDung(e) {
                                            var index = this.$parent.$parent.$parent.ds_nhavien_chuagan.list.findIndex(e => e.tyle_hh == this.data.tyle_hh)
                                            if (index > -1) this.$parent.$parent.$parent.ds_nhavien_chuagan.list[index]['tyle_hh'] = e.target.value
                                        },
                                    },
                                },
                            };
                        },
                    },
                    {
                        fieldName: 'tien_hh', headerText: 'Số tiền', allowFiltering: true, allowSorting: false, 
                        // width: "45",
                        template: () => {
                            return {
                                template: {
                                    template: `<div>
                                                <input type="text" class="form-control text" :value="data.tien_hh" @change="onChangeNoiDung">
                                            </div>`,
                                    data: function () {
                                        return {
                                            data: {},
                                        };
                                    },
                                    computed: {
                                        parent() {
                                            return this.$parent.$parent.$parent;
                                        },
                                    },
                                    methods: {
                                        onChangeNoiDung(e) {
                                            var index = this.$parent.$parent.$parent.ds_nhavien_chuagan.list.findIndex(e => e.tien_hh == this.data.tien_hh)
                                            if (index > -1) this.$parent.$parent.$parent.ds_nhavien_chuagan.list[index]['tien_hh'] = e.target.value
                                        },
                                    },
                                },
                            };
                        },
                    },
                    {
                        // width: "60", 
                        fieldName: 'congdoan_id', headerText: 'Công đoạn', allowFiltering: true, allowSorting: false,
                        template: () => {
                            return {
                                template: {
                                    template: `<div>
                                    <select2 :z-index="2000" class="select2" @change="onChangeCongDoan" v-model="data.congdoan_id"
                                        :options="cp_congdoan.map(e=>({id:e.congdoan_id,text:'['+e.congdoan_id+'] - ' + e.ten_congdoan}))">
                                    </select2>
                                    </div>`,
                                    data: function () {
                                        return {
                                            data: {
                                            },
                                            ls_congdoan:[]
                                        };
                                    },
                                    computed: {
                                        parent() {
                                            return this.$parent.$parent.$parent;
                                        },
                                        cp_congdoan() {
                                            console.log('cp_congdoan',)
                                            // console.log('z1', this.$parent.$parent.$parent, this.data.condoan_id);
                                            // console.log('z2', this.$parent.$parent);
                                            // console.log('z3', this.$parent);
                                            // this.ls_congdoan = [...this.$parent.$parent.$parent.cbb_CongDoan]
                                            // this.$parent.$parent.$parent.ds_nhavien_chuagan.list.findIndex(e => e.ma_nv == this.data.ma_nv)
                                            // this.data.congdoan_id = 
                                            return this.$parent.$parent.$parent.cbb_CongDoan.Items.filter(
                                                (item) => item.nhanvien_id == this.data.nhanvien_id
                                            );
                                        },
                                    },
                                    mounted:{
                                    },
                                    methods: {
                                        onChangeCongDoan(value) {
                                            console.log('onChangeCongDoan')
                                            var index = this.$parent.$parent.$parent.ds_nhavien_chuagan.list.findIndex(e => e.ma_nv == this.data.ma_nv)
                                            if (index > -1) this.$parent.$parent.$parent.ds_nhavien_chuagan.list[index]['congdoan_id'] = Number(value)
                                        },
                                    },
                                },
                            };
                        },
                    }
                ],
                list: [],
                checked: [],
                select_chuagan: [],
                display_list: [],
            },
            ds_thuebao_main: {
                header: [
                    {
                        headerText: "Số máy/ACC",
                        fieldName: "ma_tb",
                        allowSorting: false,
                        width: "50",
                        textAlign: "left",
                        autoFit: "true",
                        allowFiltering: true,
                    },
                    {
                        headerText: "Mã TN",
                        fieldName: "matb_tn",
                        allowSorting: false,
                        width: "50",
                        textAlign: "center",
                        autoFit: "true",
                        allowFiltering: true,
                    },
                    {
                        headerText: "Dịch vụ",
                        fieldName: "ten_dvvt",
                        allowSorting: false,
                        width: "70",
                        textAlign: "center",
                        autoFit: "true",
                        allowFiltering: true,
                    },
                    {
                        headerText: "Loại hình",
                        fieldName: "loaihinh_tb",
                        allowSorting: false,
                        width: "70",
                        textAlign: "center",
                        autoFit: "true",
                        allowFiltering: true,
                    },
                    {
                        headerText: "Tên thuê bao",
                        fieldName: "ten_tb",
                        allowSorting: false,
                        width: "70",
                        textAlign: "center",
                        autoFit: "true",
                        allowFiltering: true,
                    },
                    {
                        headerText: "Kiểu lắp đặt",
                        fieldName: "ten_kieuld",
                        allowSorting: false,
                        width: "70",
                        textAlign: "center",
                        autoFit: "true",
                        allowFiltering: true,
                    },
                    {
                        headerText: "Địa chỉ lắp đặt",
                        fieldName: "diachi_ld",
                        allowSorting: false,
                        width: "120",
                        textAlign: "left",
                        autoFit: "true",
                        allowFiltering: true,
                    },
                    {
                        headerText: "Trạng thái",
                        fieldName: "trangthai_hd",
                        allowSorting: false,
                        width: "70",
                        textAlign: "center",
                        autoFit: "true",
                        allowFiltering: true,
                    },
                ],
                list: [],
                active: false,
            },
            ds_nhanvien_ptm: {
                header: [
                    {
                        headerText: "Mã NV",
                        fieldName: "ma_nv",
                        allowSorting: false,
                        width: "50",
                        textAlign: "center",
                        autoFit: "true",
                        allowFiltering: true,
                        allowEditing: false,
                    },
                    {
                        headerText: "Tên NV",
                        fieldName: "ten_nv",
                        allowSorting: false,
                        width: "100",
                        textAlign: "center",
                        autoFit: "true",
                        allowFiltering: true,
                        allowEditing: false,
                    },
                    {
                        headerText: "Tỷ lệ",
                        fieldName: "tyle",
                        allowSorting: false,
                        width: "50",
                        textAlign: "center",
                        autoFit: "true",
                        allowFiltering: true,
                        allowEditing: true,
                        type: "Number",
                    },
                    {
                        headerText: "Đơn vị",
                        fieldName: "ten_dv",
                        allowSorting: false,
                        width: "120",
                        textAlign: "center",
                        autoFit: "true",
                        allowFiltering: true,
                        allowEditing: false,
                    },
                ],
                list: [],
                select_danggo: [],
                active: false,
            },
            ds_nhanvien_hotro: {
                header: [
                    {
                        headerText: "Mã NV",
                        fieldName: "ma_nv",
                        allowSorting: false,
                        width: "50",
                        textAlign: "center",
                        autoFit: "true",
                        allowFiltering: true,
                    },
                    {
                        headerText: "Tên NV",
                        fieldName: "ten_nv",
                        allowSorting: false,
                        width: "100",
                        textAlign: "center",
                        autoFit: "true",
                        allowFiltering: true,
                    },
                    {
                        headerText: "Tỷ lệ",
                        fieldName: "tyle",
                        allowSorting: false,
                        width: "50",
                        textAlign: "center",
                        autoFit: "true",
                        allowFiltering: true,
                        allowEditing: true,
                        type: "Number",
                    },
                    {
                        headerText: "Đơn vị",
                        fieldName: "ten_dv",
                        allowSorting: false,
                        width: "120",
                        textAlign: "center",
                        autoFit: "true",
                        allowFiltering: true,
                    },
                ],
                list: [],
                select_danggo: [],
                active: false,
            },
            ds_nhanvien_hotro_kythuat: {
                header: [
                    {
                        headerText: "Mã NV",
                        fieldName: "ma_nv",
                        allowSorting: false,
                        width: "50",
                        textAlign: "center",
                        autoFit: "true",
                        allowFiltering: true,
                    },
                    {
                        headerText: "Tên NV",
                        fieldName: "ten_nv",
                        allowSorting: false,
                        width: "100",
                        textAlign: "center",
                        autoFit: "true",
                        allowFiltering: true,
                    },
                    {
                        headerText: "Tỷ lệ",
                        fieldName: "tyle",
                        allowSorting: false,
                        width: "50",
                        textAlign: "center",
                        autoFit: "true",
                        allowFiltering: true,
                        allowEditing: true,
                        type: "Number",
                    },
                    {
                        headerText: "Đơn vị",
                        fieldName: "ten_dv",
                        allowSorting: false,
                        width: "120",
                        textAlign: "center",
                        autoFit: "true",
                        allowFiltering: true,
                    },
                ],
                list: [],
                select_danggo: [],
                active: false,
            },
            ds_nhanvien_duytri: {
                header: [
                    {
                        headerText: "Mã NV",
                        fieldName: "ma_nv",
                        allowSorting: false,
                        width: "50",
                        textAlign: "center",
                        autoFit: "true",
                        allowFiltering: true,
                    },
                    {
                        headerText: "Tên NV",
                        fieldName: "ten_nv",
                        allowSorting: false,
                        width: "100",
                        textAlign: "center",
                        autoFit: "true",
                        allowFiltering: true,
                    },
                    {
                        headerText: "Tỷ lệ",
                        fieldName: "tyle",
                        allowSorting: false,
                        width: "50",
                        textAlign: "center",
                        autoFit: "true",
                        allowFiltering: true,
                        allowEditing: true,
                        type: "Number",
                    },
                    {
                        headerText: "Đơn vị",
                        fieldName: "ten_dv",
                        allowSorting: false,
                        width: "120",
                        textAlign: "center",
                        autoFit: "true",
                        allowFiltering: true,
                    },
                ],
                select_danggo: [],
                list: [],
            },
            ds_nhanvien_cskh: {
                header: [
                    {
                        headerText: "Mã NV",
                        fieldName: "ma_nv",
                        allowSorting: false,
                        width: "50",
                        textAlign: "center",
                        autoFit: "true",
                        allowFiltering: true,
                        allowEditing: false,
                    },
                    {
                        headerText: "Tên NV",
                        fieldName: "ten_nv",
                        allowSorting: false,
                        width: "100",
                        textAlign: "center",
                        autoFit: "true",
                        allowFiltering: true,
                        allowEditing: false,
                    },
                    {
                        headerText: "Tỷ lệ",
                        fieldName: "tyle",
                        allowSorting: false,
                        width: "50",
                        textAlign: "center",
                        autoFit: "true",
                        allowFiltering: true,
                        allowEditing: true,
                        type: "Number",
                    },
                    {
                        headerText: "Đơn vị",
                        fieldName: "ten_dv",
                        allowSorting: false,
                        width: "120",
                        textAlign: "center",
                        autoFit: "true",
                        allowFiltering: true,
                        allowEditing: false,
                    },
                ],
                list: [],
                select_danggo: [],
                active: false,
            },
            ds_nhanvien_giantiep: {
                header: [
                    {
                        headerText: "Mã NV",
                        fieldName: "ma_nv",
                        allowSorting: false,
                        // width: "40",
                        textAlign: "center",
                        autoFit: "true",
                        allowFiltering: true,
                        allowEditing: false,
                    },
                    {
                        headerText: "Tên NV",
                        fieldName: "ten_nv",
                        allowSorting: false,
                        // width: "43",
                        textAlign: "center",
                        autoFit: "true",
                        allowFiltering: true,
                        allowEditing: false,
                    },
                    {
                        fieldName: 'tyle', headerText: 'Tỷ lệ', allowFiltering: true, allowSorting: false, 
                        // width: "40",
                        template: () => {
                            return {
                                template: {
                                    template: `<div>
                                                <input type="text" class="form-control text" :value="data.tyle" @change="onChangeNoiDung">
                                            </div>`,
                                    data: function () {
                                        return {
                                            data: {},
                                        };
                                    },
                                    computed: {
                                        parent() {
                                            return this.$parent.$parent.$parent;
                                        },
                                    },
                                    methods: {
                                        onChangeNoiDung(e) {
                                            var index = this.$parent.$parent.$parent.ds_nhanvien_giantiep.list.findIndex(e => e.httb_nhanvien_cs_id == this.data.httb_nhanvien_cs_id)
                                            if (index > -1) this.$set(this.$parent.$parent.$parent.ds_nhanvien_giantiep.list[index], 'tyle', e.target.value);
                                            //this.$parent.$parent.$parent.ds_nhanvien_giantiep.list[index]['tyle'] = e.target.value
                                        },
                                    },
                                },
                            };
                        },
                    },
                    {
                        fieldName: 'tyle_hh', headerText: 'TL % thù lao', allowFiltering: true, allowSorting: false, 
                        // width: "58",
                        template: () => {
                            return {
                                template: {
                                    template: `<div>
                                                <input type="text" class="form-control text" :value="data.tyle_hh" @change="onChangeNoiDung">
                                            </div>`,
                                    data: function () {
                                        return {
                                            data: {},
                                        };
                                    },
                                    computed: {
                                        parent() {
                                            return this.$parent.$parent.$parent;
                                        },
                                    },
                                    methods: {
                                        onChangeNoiDung(e) {
                                            console.log('input: ', e.target.value);
                                            var index = this.$parent.$parent.$parent.ds_nhanvien_giantiep.list.findIndex(e => e.httb_nhanvien_cs_id == this.data.httb_nhanvien_cs_id)
                                            //httb_nhanvien_cs_id
                                            console.log(11111111111)
                                            console.log(index)
                                            if (index > -1) this.$set(this.$parent.$parent.$parent.ds_nhanvien_giantiep.list[index], 'tyle_hh', e.target.value);
                                            //this.$parent.$parent.$parent.ds_nhanvien_giantiep.list[index]['tyle_hh'] = e.target.value
                                        },
                                    },
                                },
                            };
                        },
                    },
                    {
                        fieldName: 'tien_hh', headerText: 'Số tiền', allowFiltering: true, allowSorting: false,
                        // width: "45",
                        template: () => {
                            return {
                                template: {
                                    template: `<div>
                                                <input type="text" class="form-control text" :value="data.tien_hh" @change="onChangeNoiDung">
                                            </div>`,
                                    data: function () {
                                        return {
                                            data: {},
                                        };
                                    },
                                    computed: {
                                        parent() {
                                            return this.$parent.$parent.$parent;
                                        },
                                    },
                                    methods: {
                                        onChangeNoiDung(e) {
                                            var index = this.$parent.$parent.$parent.ds_nhanvien_giantiep.list.findIndex(e => e.httb_nhanvien_cs_id == this.data.httb_nhanvien_cs_id)
                                            if (index > -1) this.$set(this.$parent.$parent.$parent.ds_nhanvien_giantiep.list[index], 'tien_hh', e.target.value);
                                            //this.$parent.$parent.$parent.ds_nhanvien_giantiep.list[index]['tien_hh'] = e.target.value
                                        },
                                    },
                                },
                            };
                        },
                    },
                    {
                        headerText: "Đơn vị",
                        fieldName: "ten_dv",
                        allowSorting: false,
                        // width: "40",
                        textAlign: "center",
                        autoFit: "true",
                        allowFiltering: true,
                        allowEditing: false,
                    },
                    {
                        // width: "55", 
                        fieldName: 'congdoan_id', headerText: 'Công đoạn', allowFiltering: true, allowSorting: false,
                        template: () => {
                            return {
                                template: {
                                    template: `<div>
                                    <select2 :z-index="2000" class="select2" @change="onChangeCongDoan" v-model="data.congdoan_id"
                                        :options="cp_congdoan.map(e=>({id:e.congdoan_id,text:'['+e.congdoan_id+'] - ' + e.ten_congdoan}))">
                                    </select2>
                                    </div>`,
                                    data: function () {
                                        return {
                                            data: {
                                                congdoan_id: 10
                                            },
                                        };
                                    },
                                    computed: {
                                        parent() {
                                            return this.$parent.$parent.$parent;
                                        },
                                        cp_congdoan() {
                                            return this.$parent.$parent.$parent.cbb_CongDoan.Items.filter(
                                                (item) => item.nhanvien_id == this.data.nhanvien_id
                                            );
                                        }
                                    },
                                    watch:{
                                        '$parent.$parent.$parent.cbb_CongDoan': function(newValue, oldValue) {
                                            console.log('xxxxx',newValue);
                                        }
                                    },
                                    methods: {
                                        // onChangeCongDoan(value) {
                                        //     // console.log('select: ', value, this.$parent.$parent.$parent.ds_nhanvien_giantiep.list.findIndex(e => e.congdoan_id == this.data.congdoan_id));
                                        //     var index = this.$parent.$parent.$parent.ds_nhanvien_giantiep.list.findIndex(e => e.ma_nv == this.data.ma_nv);
                                        //     if (index > -1) this.$parent.$parent.$parent.ds_nhanvien_giantiep.list[index]['congdoan_id'] = Number(value)
                                        // },
                                        onChangeCongDoan(value) {
                                            const ma_nv = this.data.ma_nv; // Assuming ma_nv is available in your data structure
                                            const index = this.$parent.$parent.$parent.ds_nhanvien_giantiep.list.findIndex(e => e.httb_nhanvien_cs_id === this.data.httb_nhanvien_cs_id);
                                            if (index > -1) {
                                                //this.$parent.$parent.$parent.ds_nhanvien_giantiep.list[index]['congdoan_id'] = Number(value);
                                                this.$set(this.$parent.$parent.$parent.ds_nhanvien_giantiep.list[index], 'congdoan_id', Number(value));
                                            }
                                        },
                                    },
                                },
                            };
                        },
                    }
                ],
                list: [],
                select_danggo: [],
                active: false,
            },
            items:{
                file_upload: '',
                ngay_gan: ''
            },
            cbb_CongDoan:{
            },
            dichvuvt_id: "",
            loaitb_id: ''
        };
    },
    methods: {
        rowDataBound(args){
            try {
                // console.log('xxxxx', args);
                if(args.data.chot == 1){
                    args.row.classList.add('e-disabled');
                    // args.row.classList.add('e-disabled');
                    // this.$refs.giantiepGridRef.getRowByIndex(rowIndex).classList.contains('e-disabled').style.cursor = 'pointer'
                }
            } catch (error) {
                console.log(error);
            }
        },
        changeFile() {
            let formData = new FormData();
            for (let file of this.$refs.files_upload.files) {
                formData.append('files', file);
            }
            api.upload_file(this.axios, formData, { headers: { 'Content-Type': 'multipart/form-data' } }).then((response) => {
                if (response.data.error_code === 'BSS-00000000') {
                    this.items.file_upload = response.data.data.join('|');
                    this.$refs.files_upload.value = null;
                }
            }).catch(function () {
                console.log('File upload failed!');
            });
            console.log(this.items.file_upload)
        },
        Load_start() {
            if (!this.gan_hd) {
                if (
                    this.$route.query.tag != null &&
                    this.$route.params.tag != "" &&
                    this.$route.query.tag.length > 0
                ) {
                    this.Tag = this.$route.query.tag;
                    if (this.Tag == "1" || this.Tag == "2") {
                        this.kieugoi_id = parseInt(this.Tag);
                    } else if (this.Tag != "") {
                        this.$toast.warning(
                            "Tag này chưa được phân quyền! Liên hệ admin để xử lý."
                        );
                    }
                } else {
                    this.$toast.warning(
                        "Không tìm thấy thông tin tag! Liên hệ admin để xử lý."
                    );
                }
            } else {
                this.kieugoi_id = 1;
                this.txtMaGD = vtimkiem_hdkh_id;
            }
        },
        Load_form() {
            this.SelectTab1();
            if (this.kieugoi_id == 1) {
                this.label_trangthai = "GÁN NHÂN VIÊN QUẢN LÝ THUÊ BAO HỢP ĐỒNG";
                this.check_toanbo = false;
            } else if (this.kieugoi_id == 2) {
                this.label_trangthai = "GÁN NHÂN VIÊN QUẢN LÝ THUÊ BAO DANH BẠ";
                this.check_toanbo = true;
            } else this.check_toanbo = false;
            this.clickable();
        },
        onSelectedRowChange_PTM(items) {
            this.ds_nhanvien_ptm.select_danggo = [];
            if (this.kieugoi_id == 1) {
                if (items.rowIndexes) {
                    items.rowIndexes.forEach((index) => {
                        var item = this.ds_nhanvien_ptm.list[index];
                        if (
                            item &&
                            !this.ds_nhanvien_ptm.select_danggo.some(
                                (nv) => nv.ma_nv == item.ma_nv && nv.loai == "1"
                            )
                        ) {
                            this.ds_nhanvien_ptm.select_danggo.push(item);
                        }
                    });
                } else if (items.rowIndex >= 0) {
                    this.ds_nhanvien_ptm.list[items.rowIndex] = items.data;
                    var item = this.ds_nhanvien_ptm.list[items.rowIndex];
                    this.dt_HopDong_All.forEach((nv) => {
                        if (
                            nv.ma_nv == item.ma_nv &&
                            nv.hdtb_id == this.hdtbId &&
                            nv.loai == "1"
                        ) {
                            nv.tyle = item.tyle;
                        }
                    });
                    if (
                        !this.ds_nhanvien_ptm.select_danggo.some(
                            (nv) => nv.ma_nv == item.ma_nv
                        )
                    ) {
                        this.ds_nhanvien_ptm.select_danggo.push(item);
                    }
                }
            }
            if (this.kieugoi_id == 2) {
                if (items.rowIndexes) {
                    items.rowIndexes.forEach((index) => {
                        var item = this.ds_nhanvien_ptm.list[index];
                        if (
                            item &&
                            !this.ds_nhanvien_ptm.select_danggo.some(
                                (nv) => nv.ma_nv == item.ma_nv && nv.loai == "1"
                            )
                        ) {
                            this.ds_nhanvien_ptm.select_danggo.push(item);
                        }
                    });
                } else if (items.rowIndex >= 0) {
                    this.ds_nhanvien_ptm.list[items.rowIndex] = items.data;
                    var item = this.ds_nhanvien_ptm.list[items.rowIndex];
                    this.dt_HopDong_All.forEach((nv) => {
                        if (
                            nv.ma_nv == item.ma_nv &&
                            nv.thuebao_id == this.thuebaoID &&
                            nv.loai == "1"
                        ) {
                            nv.tyle = item.tyle;
                        }
                    });
                    if (
                        !this.ds_nhanvien_ptm.select_danggo.some(
                            (nv) => nv.ma_nv == item.ma_nv
                        )
                    ) {
                        this.ds_nhanvien_ptm.select_danggo.push(item);
                    }
                }
            }
            console.log(this.ds_nhavien_chuagan.select_chuagan);
        },
        onSelectedRowChange_HOTRO(items) {
            this.ds_nhanvien_hotro.select_danggo = [];
            if (this.kieugoi_id == 1) {
                if (items.rowIndexes) {
                    items.rowIndexes.forEach((index) => {
                        var item = this.ds_nhanvien_hotro.list[index];
                        if (
                            item &&
                            !this.ds_nhanvien_hotro.select_danggo.some(
                                (nv) => nv.ma_nv == item.ma_nv && nv.loai == "3"
                            )
                        ) {
                            this.ds_nhanvien_hotro.select_danggo.push(item);
                        }
                    });
                } else if (items.rowIndex >= 0) {
                    this.ds_nhanvien_hotro.list[items.rowIndex] = items.data;
                    var item = this.ds_nhanvien_hotro.list[items.rowIndex];
                    this.dt_HopDong_All.forEach((nv) => {
                        if (
                            nv.ma_nv == item.ma_nv &&
                            nv.hdtb_id == this.hdtbId &&
                            nv.loai == "3"
                        ) {
                            nv.tyle = item.tyle;
                        }
                    });
                    if (
                        !this.ds_nhanvien_hotro.select_danggo.some(
                            (nv) => nv.ma_nv == item.ma_nv
                        )
                    ) {
                        this.ds_nhanvien_hotro.select_danggo.push(item);
                    }
                }
            }
            if (this.kieugoi_id == 2) {
                if (items.rowIndexes) {
                    items.rowIndexes.forEach((index) => {
                        var item = this.ds_nhanvien_hotro.list[index];
                        if (
                            item &&
                            !this.ds_nhanvien_hotro.select_danggo.some(
                                (nv) => nv.ma_nv == item.ma_nv && nv.loai == "3"
                            )
                        ) {
                            this.ds_nhanvien_hotro.select_danggo.push(item);
                        }
                    });
                } else if (items.rowIndex >= 0) {
                    this.ds_nhanvien_hotro.list[items.rowIndex] = items.data;
                    var item = this.ds_nhanvien_hotro.list[items.rowIndex];
                    this.dt_HopDong_All.forEach((nv) => {
                        if (
                            nv.ma_nv == item.ma_nv &&
                            nv.thuebao_id == this.thuebaoID &&
                            nv.loai == "3"
                        ) {
                            nv.tyle = item.tyle;
                        }
                    });
                    if (
                        !this.ds_nhanvien_hotro.select_danggo.some(
                            (nv) => nv.ma_nv == item.ma_nv
                        )
                    ) {
                        this.ds_nhanvien_hotro.select_danggo.push(item);
                    }
                }
            }
        },
        onSelectedRowChange_DUYTRI(items) {
            this.ds_nhanvien_duytri.select_danggo = [];
            if (this.kieugoi_id == 1) {
                if (items.rowIndexes) {
                    items.rowIndexes.forEach((index) => {
                        var item = this.ds_nhanvien_duytri.list[index];
                        if (
                            item &&
                            !this.ds_nhanvien_duytri.select_danggo.some(
                                (nv) => nv.ma_nv == item.ma_nv && nv.loai == "2"
                            )
                        ) {
                            this.ds_nhanvien_duytri.select_danggo.push(item);
                        }
                    });
                } else if (items.rowIndex >= 0) {
                    this.ds_nhanvien_duytri.list[items.rowIndex] = items.data;
                    var item = this.ds_nhanvien_duytri.list[items.rowIndex];
                    this.dt_HopDong_All.forEach((nv) => {
                        if (
                            nv.ma_nv == item.ma_nv &&
                            nv.hdtb_id == this.hdtbId &&
                            nv.loai == "2"
                        ) {
                            nv.tyle = item.tyle;
                        }
                    });
                    if (
                        !this.ds_nhanvien_duytri.select_danggo.some(
                            (nv) => nv.ma_nv == item.ma_nv
                        )
                    ) {
                        this.ds_nhanvien_duytri.select_danggo.push(item);
                    }
                }
            }
            if (this.kieugoi_id == 2) {
                if (items.rowIndexes) {
                    items.rowIndexes.forEach((index) => {
                        var item = this.ds_nhanvien_duytri.list[index];
                        if (
                            item &&
                            !this.ds_nhanvien_duytri.select_danggo.some(
                                (nv) => nv.ma_nv == item.ma_nv && nv.loai == "2"
                            )
                        ) {
                            this.ds_nhanvien_duytri.select_danggo.push(item);
                        }
                    });
                } else if (items.rowIndex >= 0) {
                    this.ds_nhanvien_duytri.list[items.rowIndex] = items.data;
                    var item = this.ds_nhanvien_duytri.list[items.rowIndex];
                    this.dt_HopDong_All.forEach((nv) => {
                        if (
                            nv.ma_nv == item.ma_nv &&
                            nv.thuebao_id == this.thuebaoID &&
                            nv.loai == "2"
                        ) {
                            nv.tyle = item.tyle;
                        }
                    });
                    if (
                        !this.ds_nhanvien_duytri.select_danggo.some(
                            (nv) => nv.ma_nv == item.ma_nv
                        )
                    ) {
                        this.ds_nhanvien_duytri.select_danggo.push(item);
                    }
                }
            }
        },
        // New
        onSelectedRowChange_CSKH(items) {
            this.ds_nhanvien_cskh.select_danggo = [];
            if (this.kieugoi_id == 1) {
                if (items.rowIndexes) {
                    items.rowIndexes.forEach((index) => {
                        var item = this.ds_nhanvien_cskh.list[index];
                        if (
                            item &&
                            !this.ds_nhanvien_cskh.select_danggo.some(
                                (nv) => nv.ma_nv == item.ma_nv && nv.loai == "5"
                            )
                        ) {
                            this.ds_nhanvien_cskh.select_danggo.push(item);
                        }
                    });
                } else if (items.rowIndex >= 0) {
                    this.ds_nhanvien_cskh.list[items.rowIndex] = items.data;
                    var item = this.ds_nhanvien_cskh.list[items.rowIndex];
                    this.dt_HopDong_All.forEach((nv) => {
                        if (
                            nv.ma_nv == item.ma_nv &&
                            nv.hdtb_id == this.hdtbId &&
                            nv.loai == "5"
                        ) {
                            nv.tyle = item.tyle;
                        }
                    });
                    if (
                        !this.ds_nhanvien_cskh.select_danggo.some(
                            (nv) => nv.ma_nv == item.ma_nv
                        )
                    ) {
                        this.ds_nhanvien_cskh.select_danggo.push(item);
                    }
                }
            }
            if (this.kieugoi_id == 2) {
                if (items.rowIndexes) {
                    items.rowIndexes.forEach((index) => {
                        var item = this.ds_nhanvien_cskh.list[index];
                        if (
                            item &&
                            !this.ds_nhanvien_cskh.select_danggo.some(
                                (nv) => nv.ma_nv == item.ma_nv && nv.loai == "5"
                            )
                        ) {
                            this.ds_nhanvien_cskh.select_danggo.push(item);
                        }
                    });
                } else if (items.rowIndex >= 0) {
                    this.ds_nhanvien_cskh.list[items.rowIndex] = items.data;
                    var item = this.ds_nhanvien_cskh.list[items.rowIndex];
                    this.dt_HopDong_All.forEach((nv) => {
                        if (
                            nv.ma_nv == item.ma_nv &&
                            nv.thuebao_id == this.thuebaoID &&
                            nv.loai == "5"
                        ) {
                            nv.tyle = item.tyle;
                        }
                    });
                    if (
                        !this.ds_nhanvien_cskh.select_danggo.some(
                            (nv) => nv.ma_nv == item.ma_nv
                        )
                    ) {
                        this.ds_nhanvien_cskh.select_danggo.push(item);
                    }
                }
            }
        },
        // New
        onSelectedRowChange_HOTRO_KYTHUAT(items) {
            this.ds_nhanvien_hotro_kythuat.select_danggo = [];
            if (this.kieugoi_id == 1) {
                if (items.rowIndexes) {
                    items.rowIndexes.forEach((index) => {
                        var item = this.ds_nhanvien_hotro_kythuat.list[index];
                        if (
                            item &&
                            !this.ds_nhanvien_hotro_kythuat.select_danggo.some(
                                (nv) => nv.ma_nv == item.ma_nv && nv.loai == "5"
                            )
                        ) {
                            this.ds_nhanvien_hotro_kythuat.select_danggo.push(item);
                        }
                    });
                } else if (items.rowIndex >= 0) {
                    this.ds_nhanvien_hotro_kythuat.list[items.rowIndex] = items.data;
                    var item = this.ds_nhanvien_hotro_kythuat.list[items.rowIndex];
                    this.dt_HopDong_All.forEach((nv) => {
                        if (
                            nv.ma_nv == item.ma_nv &&
                            nv.hdtb_id == this.hdtbId &&
                            nv.loai == "5"
                        ) {
                            nv.tyle = item.tyle;
                        }
                    });
                    if (
                        !this.ds_nhanvien_hotro_kythuat.select_danggo.some(
                            (nv) => nv.ma_nv == item.ma_nv
                        )
                    ) {
                        this.ds_nhanvien_hotro_kythuat.select_danggo.push(item);
                    }
                }
            }
            if (this.kieugoi_id == 2) {
                if (items.rowIndexes) {
                    items.rowIndexes.forEach((index) => {
                        var item = this.ds_nhanvien_hotro_kythuat.list[index];
                        if (
                            item &&
                            !this.ds_nhanvien_hotro_kythuat.select_danggo.some(
                                (nv) => nv.ma_nv == item.ma_nv && nv.loai == "5"
                            )
                        ) {
                            this.ds_nhanvien_hotro_kythuat.select_danggo.push(item);
                        }
                    });
                } else if (items.rowIndex >= 0) {
                    this.ds_nhanvien_hotro_kythuat.list[items.rowIndex] = items.data;
                    var item = this.ds_nhanvien_hotro_kythuat.list[items.rowIndex];
                    this.dt_HopDong_All.forEach((nv) => {
                        if (
                            nv.ma_nv == item.ma_nv &&
                            nv.thuebao_id == this.thuebaoID &&
                            nv.loai == "5"
                        ) {
                            nv.tyle = item.tyle;
                        }
                    });
                    if (
                        !this.ds_nhanvien_hotro_kythuat.select_danggo.some(
                            (nv) => nv.ma_nv == item.ma_nv
                        )
                    ) {
                        this.ds_nhanvien_hotro_kythuat.select_danggo.push(item);
                    }
                }
            }
        },
        GTRowSelecting(args){
            try {
                if (args.data['chot'] === 1) {
                    args.cancel = true;
                }
                if (args.rowIndexes.length > 1) {
                    // debugger;
                    var index = [];
                    args.rowIndexes.filter((rowIndex) => {
                        if (
                            this.$refs.giantiepGridRef.getRowByIndex(rowIndex).classList.contains('e-disabled')
                        ) {
                            index.push(rowIndex);
                        }
                    });
                    console.log();
                    index.forEach((p) =>
                        args.rowIndexes.splice(args.rowIndexes.indexOf(p), 1)
                    );
                }
            } catch (error) {
                console.log(error);
            }
        },
        onSelectedRowChange_GIANTIEP(items) {
            this.ds_nhanvien_giantiep.select_danggo = [];
                if (items.rowIndexes) {
                    items.rowIndexes.forEach((index) => {
                        var item = this.ds_nhanvien_giantiep.list[index];
                        if (
                            item &&
                            !this.ds_nhanvien_giantiep.select_danggo.some(
                                (nv) => nv.ma_nv == item.ma_nv && nv.loai == "6"
                            )
                        ) {
                            this.ds_nhanvien_giantiep.select_danggo.push(item);
                        }
                    });
                } else if (items.rowIndex >= 0) {
                    this.ds_nhanvien_giantiep.list[items.rowIndex] = items.data;
                    var item = this.ds_nhanvien_giantiep.list[items.rowIndex];
                    this.dt_HopDong_All.forEach((nv) => {
                        if (
                            nv.ma_nv == item.ma_nv &&
                            nv.hdtb_id == this.hdtbId &&
                            nv.loai == "6"
                        ) {
                            nv.tyle = item.tyle;
                        }
                    });
                    if (
                        !this.ds_nhanvien_giantiep.select_danggo.some(
                            (nv) => nv.ma_nv == item.ma_nv
                        )
                    ) {
                        this.ds_nhanvien_giantiep.select_danggo.push(item);
                    }
                }
        },
        onDeselectedRowChange_PTM(items) {
            if (items.rowIndexes) {
                items.rowIndexes.forEach((index) => {
                    var item = this.ds_nhanvien_ptm.list[index];
                    for (var i = 0; i < this.ds_nhanvien_ptm.list.length; i++) {
                        if (this.ds_nhanvien_ptm.select_danggo[i].ma_nv == item.ma_nv) {
                            this.ds_nhanvien_ptm.select_danggo.splice(i, 1);
                            break;
                        }
                    }
                });
            }
        },
        onDeselectedRowChange_HOTRO(items) {
            if (items.rowIndexes) {
                items.rowIndexes.forEach((index) => {
                    var item = this.ds_nhanvien_hotro.list[index];
                    for (var i = 0; i < this.ds_nhanvien_hotro.list.length; i++) {
                        if (this.ds_nhanvien_hotro.select_danggo[i].ma_nv == item.ma_nv) {
                            this.ds_nhanvien_hotro.select_danggo.splice(i, 1);
                            break;
                        }
                    }
                });
            }
        },
        onDeselectedRowChange_DUYTRI(items) {
            if (items.rowIndexes) {
                items.rowIndexes.forEach((index) => {
                    var item = this.ds_nhanvien_duytri.list[index];
                    for (var i = 0; i < this.ds_nhanvien_duytri.list.length; i++) {
                        if (this.ds_nhanvien_duytri.select_danggo[i].ma_nv == item.ma_nv) {
                            this.ds_nhanvien_duytri.select_danggo.splice(i, 1);
                            break;
                        }
                    }
                });
            }
        },
        onDeselectedRowChange_GIANTIEP(items) {
            console.log('onDeselectedRowChange_GIANTIEP',items);
            if (items.rowindexes) {
                 items.rowindexes.foreach((index) => {
                     var item = this.ds_nhanvien_giantiep.list[index];
                     for (var i = 0; i < this.ds_nhanvien_giantiep.list.length; i++) {
                         if (
                             this.ds_nhanvien_giantiep.select_danggo[i].httb_nhanvien_cs_id == item.httb_nhanvien_cs_id
                         ) {
                             this.ds_nhanvien_giantiep.select_danggo.splice(i, 1);
                             break;
                         }
                     }
                 });
             }
        },
        onDeselectedRowChange_CSKH(items) {
            if (items.rowIndexes) {
                items.rowIndexes.forEach((index) => {
                    var item = this.ds_nhanvien_cskh.list[index];
                    for (var i = 0; i < this.ds_nhanvien_cskh.list.length; i++) {
                        if (this.ds_nhanvien_cskh.select_danggo[i].ma_nv == item.ma_nv) {
                            this.ds_nhanvien_cskh.select_danggo.splice(i, 1);
                            break;
                        }
                    }
                });
            }
        },
        // NEW 
        onDeselectedRowChange_HOTRO_KYTHUAT(items) {
            if (items.rowIndexes) {
                items.rowIndexes.forEach((index) => {
                    var item = this.ds_nhanvien_hotro_kythuat.list[index];
                    for (var i = 0; i < this.ds_nhanvien_hotro_kythuat.list.length; i++) {
                        if (this.ds_nhanvien_hotro_kythuat.select_danggo[i].ma_nv == item.ma_nv) {
                            this.ds_nhanvien_hotro_kythuat.select_danggo.splice(i, 1);
                            break;
                        }
                    }
                });
            }
        },
        onSelectedRowChange_NVCG(items) {
            this.ds_nhavien_chuagan.select_chuagan = [];
            if (items.rowIndexes) {
                items.rowIndexes.forEach((index) => {
                    var item = this.ds_nhavien_chuagan.display_list[index];
                    if (
                        !this.ds_nhavien_chuagan.select_chuagan.some(
                            (nv) => nv.ma_nv == item.ma_nv
                        )
                    ) {
                        this.ds_nhavien_chuagan.select_chuagan.push(item);
                    }
                });
            } else if (items.rowIndex >= 0) {
                this.ds_nhavien_chuagan.display_list[items.rowIndex] = items.data;
                var item = this.ds_nhavien_chuagan.display_list[items.rowIndex];
                if (
                    !this.ds_nhavien_chuagan.select_chuagan.some(
                        (nv) => nv.ma_nv == item.ma_nv
                    )
                ) {
                    this.ds_nhavien_chuagan.select_chuagan.push(item);
                }
            }
            // Xử lý khi chọn row trên lưới nhân viên chưa gán
        },
        onDeselectedRowChange_NVCG(items) {
            if (items.rowIndexes) {
                items.rowIndexes.forEach((index) => {
                    var item = this.ds_nhavien_chuagan.display_list[index];
                    for (var i = 0; i < this.ds_nhavien_chuagan.list.length; i++) {
                        if (this.ds_nhavien_chuagan.select_chuagan[i].ma_nv == item.ma_nv) {
                            this.ds_nhavien_chuagan.select_chuagan.splice(i, 1);
                            break;
                        }
                    }
                });
            }
        },
        async ThongBao(msg, title) {
            if (!title) title = "Lỗi dữ liệu";
            await this.$bvModal.msgBoxOk(msg, {
                title: title,
                size: "sm",
                okTitle: "Đồng ý",
            });
        },
        SelectTab1() {
            this.Tab1 = true;
            this.Tab2 = false;
            this.Tab3 = false;
            this.Tab4 = false;
            this.Tab5 = false;
            this.TabGIANTIEP = false;
            this.ds_nhanvien_ptm.active = true;
            this.ds_nhanvien_hotro.active = false;
            this.ds_nhanvien_hotro_kythuat.active = false;
            this.ds_nhanvien_duytri.active = false;
            this.ds_nhanvien_cskh.active = false;
            this.ds_nhanvien_giantiep.active = false;
            this.ds_nhavien_chuagan.list = [];
            this.init();
        },
        SelectTab2() {
            this.Tab1 = false;
            this.Tab2 = true;
            this.Tab4 = false;
            this.Tab3 = false;
            this.Tab5 = false;
            this.TabGIANTIEP = false;
            this.ds_nhanvien_ptm.active = false;
            this.ds_nhanvien_hotro.active = false;
            this.ds_nhanvien_hotro_kythuat.active = false;
            this.ds_nhanvien_duytri.active = true;
            this.ds_nhanvien_cskh.active = false;
            this.ds_nhanvien_giantiep.active = false;
            this.ds_nhavien_chuagan.list = [];
            this.init();
        },
        SelectTab3() {
            this.Tab1 = false;
            this.Tab2 = false;
            this.Tab3 = true;
            this.Tab4 = false;
            this.Tab5 = false;
            this.TabGIANTIEP = false;
            this.ds_nhanvien_ptm.active = false;
            this.ds_nhanvien_hotro.active = true;
            this.ds_nhanvien_hotro_kythuat.active = false;
            this.ds_nhanvien_duytri.active = false;
            this.ds_nhanvien_cskh.active = false;
            this.ds_nhanvien_giantiep.active = false;
            this.ds_nhavien_chuagan.list = [];
            this.init();
        },
        async SelectTab4() {
            this.Tab1 = false;
            this.Tab2 = false;
            this.Tab3 = false;
            this.Tab4 = true;
            this.Tab5 = false;
            this.TabGIANTIEP = false;
            this.ds_nhanvien_ptm.active = false;
            this.ds_nhanvien_hotro.active = false;
            this.ds_nhanvien_hotro_kythuat.active = false;
            this.ds_nhanvien_duytri.active = false;
            this.ds_nhanvien_cskh.active = true;
            this.ds_nhanvien_giantiep.active = false;
            this.ds_nhavien_chuagan.list = [];
            if (this.kieugoi_id != 0) {
                var response = await api.ADMIN_LOAI_NV(this.axios);
                if (
                    response &&
                    response.data &&
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data.length > 0
                ) {
                    var data_lnv = response.data.data;
                    this.options.cbx_dsloainv = data_lnv;
                    var data_default;
                    if (response.data.data.length) {
                        data_default = data_lnv[0];
                    }
                    if (data_default && data_default.LOAINV_ID > 0) {
                        this.loainv_id = data_default.LOAINV_ID;
                        this.NAP_DS_DVI_LINE(-2, this.loainv_id);
                    }
                } else {
                    this.$toast.warning(
                        "Có lỗi khi quá trình nạp dữ liệu từ DB. Liên hệ Admin để xử lý!"
                    );
                }
            }
        },
        async SelectTab5() {
            this.Tab1 = false;
            this.Tab2 = false;
            this.Tab3 = false;
            this.Tab4 = false;
            this.Tab5 = true;
            this.TabGIANTIEP = false;
            this.ds_nhanvien_ptm.active = false;
            this.ds_nhanvien_hotro.active = false;
            this.ds_nhanvien_hotro_kythuat.active = true;
            this.ds_nhanvien_duytri.active = false;
            this.ds_nhanvien_cskh.active = false;
            this.ds_nhanvien_giantiep.active = false;
            this.ds_nhavien_chuagan.list = [];
            if (this.kieugoi_id != 0) {
            }
        },
        async SelectTabGIANTIEP() {
            this.Tab1 = false;
            this.Tab2 = false;
            this.Tab3 = false;
            this.Tab4 = false;
            this.TabGIANTIEP = true;
            this.ds_nhanvien_ptm.active = false;
            this.ds_nhanvien_hotro.active = false;
            this.ds_nhanvien_hotro_kythuat.active = false;
            this.ds_nhanvien_duytri.active = false;
            this.ds_nhanvien_cskh.active = false;
            this.ds_nhanvien_giantiep.active = true;
            this.options.cbx_dsloainv = [];
            this.options.cbx_dsdonvi = [];
            this.ds_nhavien_chuagan.list = [];
            if (this.kieugoi_id != 0) {
                this.getDsLoaiNV();
                // var response = await api.ADMIN_LOAI_NV(this.axios);
                // if (
                //     response &&
                //     response.data &&
                //     response.data.error_code === "BSS-00000000" &&
                //     response.data.data.length > 0
                // ) {
                //     var data_lnv = response.data.data.filter(
                //         (item) => item.LOAINV_ID === 584 || item.LOAINV_ID === 587
                //     );
                //     this.options.cbx_dsloainv = data_lnv;
                //     if (
                //         response.data.data.filter((item) => item.LOAINV_ID === 584)
                //             .length === 0
                //     ) {
                //         data_lnv.push({ LOAINV_ID: 584, TEN: "Đại lý" });
                //     }
                //     if (
                //         response.data.data.filter((item) => item.LOAINV_ID === 584)
                //             .length === 0
                //     ) {
                //         data_lnv.push({
                //             LOAINV_ID: 587,
                //             TEN: "NV/Phòng BH quản lý Đại lý",
                //         });
                //     }
                //     this.options.cbx_dsloainv = data_lnv;
                //     var data_default;
                //     if (data_lnv.some((item) => item.LOAINV_ID === 584)) {
                //         data_default = data_lnv.find((item) => item.LOAINV_ID === 584);
                //     } else {
                //         data_default = data_lnv[0];
                //     }
                //     if (data_default && data_default.LOAINV_ID > 0) {
                //         this.loainv_id = data_default.LOAINV_ID;
                //         this.NAP_DS_DVI_LINE(-1, this.loainv_id);
                //     }
                // } else {
                //     this.$toast.warning(
                //         "Có lỗi khi quá trình nạp dữ liệu từ DB. Liên hệ Admin để xử lý!"
                //     );
                // }
            }
        },
        // Hiển thị thông tin khi tìm kiếm ra nhiều HĐ
        Hienthi_hdkh_popup(data) {
            var $ = document.querySelector.bind(document);
            $("#popupTCTTTB").style.display = "block";
            this.ds_hopdong_thuebao_timkiem.list = data;
        },
        // Close form tìm kiếm HD
        close_timkiem_hdtb() {
            var $ = document.querySelector.bind(document);
            $("#popupTCTTTB").style.display = "none";
        },
        clickable() {
            if (this.kieugoi_id == 1 || this.kieugoi_id == 2) {
                return false;
            } else return true;
        },
        TimThongTinKH() {
            if (this.txtMaGD == null || this.txtMaGD == "") {
                this.check_load = 0;
            } else {
                this.CLEAR();
                this.ds_hopdong_thuebao_timkiem.list = [];
                this.sotb_tam = "";
                if (this.kieugoi_id == 1) {
                    // Load Hợp đồng
                    this.loading(true);
                    var api_hd = api.lay_ds_hopdong_theo_mathuebao(
                        this.axios,
                        this.txtMaGD
                    ).then((response) => {
                        if (
                            response &&
                            response.data &&
                            response.data.error_code === "BSS-00000000" &&
                            response.data.data.length > 0
                        ) {
                            this.sotb_tam = this.txtMaGD;
                            // Nếu nhiều HĐ thì cho hiển thị lưới tìm kiếm
                            if (response.data.data.length > 1) {
                                this.Hienthi_hdkh_popup(response.data.data);
                            }
                            // Nếu 1 HĐ
                            else if (response.data.data.length === 1) {
                                this.txtMaGD = response.data.data[0].maGd;
                                this.hdkhId = response.data.data[0].hdkhId;
                                this.khachHangId = response.data.data[0].khachHangId;
                                this.LoadDS_HopDongTB_NhanVien(this.txtMaGD, this.hdkhId);
                            }
                            // end
                            this.loading(false);
                        } else if (response.data.error_code === "BSS-00000500") {
                            this.check_load = 0;
                            this.loading(false);
                            this.$toast.error(
                                "Request timed out after 30000ms. Liên hệ admin để xử lý!"
                            );
                        } else {
                            // bố sung hàm lấy file vượt khung
                            this.setFileUploadVK(null);
                            this.check_load = 0;
                            this.loading(false);
                            this.$toast.warning("Không tìm thấy thông tin hợp đồng!");
                        }
                    });
                } else {
                    // Load Danh bạ
                    this.loading(true);
                    var api_db = api.timkiem_ds_hopdong(this.axios, 2, this.txtMaGD).then(
                        (response) => {
                            if (
                                response &&
                                response.data &&
                                response.data.error_code === "BSS-00000000" &&
                                response.data.data.dt_hd_tb.length > 0
                            ) {
                                this.sotb_tam = this.txtMaGD;
                                this.ds_danhba_timkiem.list = response.data.data.dt_hd_tb;
                                this.flag_gan = false;
                                this.check_load = 1;
                                this.loading(false);
                                if (this.ds_danhba_timkiem.list.length >= 1) {
                                    if (
                                        response.data.data.dt_hd &&
                                        response.data.data.dt_hd.length > 0
                                    ) {
                                        this.dt_HopDong_All = response.data.data.dt_hd;
                                    }
                                    var index = 0;
                                    for (var i = 0; i < this.ds_danhba_timkiem.list.length; i++) {
                                        if (
                                            this.ds_danhba_timkiem.list[i].ma_tb === this.sotb_tam
                                        ) {
                                            index = i;
                                            break;
                                        }
                                    }
                                    this.$refs.dbGridRef.setCurrentSelectedRow(index);
                                }
                            } else {
                                this.loading(false);
                                this.check_load = 0;
                                this.$toast.warning("Không tìm thấy thông tin danh bạ!");
                            }
                        }
                    );
                }
            }
        },
        // Lấy data khi chọn row trên lưới
        Select_ds_hopdong_thuebao_timkiem: function (items) {
            this.CLEAR();
            this.txtMaGD = items.maGd;
            this.hdkhId = items.hdkhId;
            this.khachHangId = items.khachHangId;
            this.LoadDS_HopDongTB_NhanVien(items.maGd, items.hdkhId);
        },
        Select_thuebao_danhba: function (items) {
            this.donviql_id = items.donvi_qlid;
            if (
                this.donviql_id !== 0 &&
                this.loainv_id !== 0 &&
                this.donviql_id !== null
            ) {
                if (this.Tab4 === true) {
                    this.NAP_DS_DVI_LINE(-2, this.loainv_id);
                } else {
                    this.NAP_DS_DVI_LINE(this.donviql_id, this.loainv_id);
                }
                this.thuebaoID = items.thuebao_id;
                this.khachHangId = items.khachhang_id;
                this.ds_nhanvien_ptm.list = [];
                this.ds_nhanvien_hotro.list = [];
                this.ds_nhanvien_duytri.list = [];
                this.ds_nhanvien_cskh.list = [];
                this.ds_nhanvien_giantiep.list = [];
                this.dt_HopDong_All.forEach((element) => {
                    if (
                        element.loai == "1" &&
                        element.thuebao_id == this.thuebaoID &&
                        parseInt(element.ischeck) >= 0 &&
                        !this.ds_nhanvien_ptm.list.some(
                            (item) =>
                                item.ma_nv == element.ma_nv && item.thuebao_id == this.thuebaoID
                        )
                    ) {
                        this.ds_nhanvien_ptm.list.push(element);
                    } else if (
                        element.loai == "3" &&
                        element.thuebao_id == this.thuebaoID &&
                        parseInt(element.ischeck) >= 0 &&
                        !this.ds_nhanvien_hotro.list.some(
                            (item) =>
                                item.ma_nv == element.ma_nv && item.hdtb_id == this.thuebaoID
                        )
                    ) {
                        this.ds_nhanvien_hotro.list.push(element);
                    } else if (
                        element.loai == "2" &&
                        element.thuebao_id == this.thuebaoID &&
                        parseInt(element.ischeck) >= 0 &&
                        !this.ds_nhanvien_duytri.list.some(
                            (item) =>
                                item.ma_nv == element.ma_nv && item.thuebao_id == this.thuebaoID
                        )
                    ) {
                        this.ds_nhanvien_duytri.list.push(element);
                    } else if (
                        element.loai == "6" &&
                        element.thuebao_id == this.thuebaoID &&
                        parseInt(element.ischeck) >= 0 &&
                        !this.ds_nhanvien_giantiep.list.some(
                            (item) =>
                                item.ma_nv == element.ma_nv && item.thuebao_id == this.thuebaoID
                        )
                    ) {
                        this.ds_nhanvien_giantiep.list.push(element);
                    } else if (
                        element.loai == "5" &&
                        element.thuebao_id == this.thuebaoID &&
                        parseInt(element.ischeck) >= 0 &&
                        !this.ds_nhanvien_cskh.list.some(
                            (item) =>
                                item.ma_nv == element.ma_nv && item.thuebao_id == this.thuebaoID
                        )
                    ) {
                        this.ds_nhanvien_cskh.list.push(element);
                    }
                });
            } else {
                this.ds_nhavien_chuagan.list = [];
                this.options.cbx_dsdonvi = [];
            }
        },
        Select_thuebao_main: function (items) {
            // console.log('xxxxxxxx');
            
            if(this.selectedOption == 2){
                this.hdtbID = items.hdtb_id;
                this.getDSCongDoan();
                this.getDsNvDaGan();
                this.getDsNvChuaGan();
                this.getFileVK();
                this.getDonVi()
            }else{
                this.donviql_id = items.donvi_qlid;
                if (
                    this.donviql_id !== 0 &&
                    this.loainv_id !== 0 &&
                    this.donviql_id !== null
                ) {
                    this.NAP_DS_DVI_LINE(this.donviql_id, this.loainv_id);
                } else {
                    this.NAP_DS_DVI_LINE(-1, this.loainv_id);
                }
    
                this.hdtbId = items.hdtb_id;
                this.ds_nhanvien_ptm.list = [];
                this.ds_nhanvien_hotro.list = [];
                this.ds_nhanvien_duytri.list = [];
                this.ds_nhanvien_cskh.list = [];
                this.ds_nhanvien_giantiep.list = [];
    
                this.dt_HopDong_All.forEach((element) => {
                    if (
                        element.loai == "1" &&
                        element.hdtb_id == this.hdtbId &&
                        parseInt(element.ischeck) >= 0 &&
                        !this.ds_nhanvien_ptm.list.some(
                            (item) => item.ma_nv == element.ma_nv && item.hdtb_id == this.hdtbId
                        )
                    ) {
                        this.ds_nhanvien_ptm.list.push(element);
                    } else if (
                        element.loai == "3" &&
                        element.hdtb_id == this.hdtbId &&
                        parseInt(element.ischeck) >= 0 &&
                        !this.ds_nhanvien_hotro.list.some(
                            (item) => item.ma_nv == element.ma_nv && item.hdtb_id == this.hdtbId
                        )
                    ) {
                        this.ds_nhanvien_hotro.list.push(element);
                    } else if (
                        element.loai == "2" &&
                        element.hdtb_id == this.hdtbId &&
                        parseInt(element.ischeck) >= 0 &&
                        !this.ds_nhanvien_duytri.list.some(
                            (item) => item.ma_nv == element.ma_nv && item.hdtb_id == this.hdtbId
                        )
                    ) {
                        this.ds_nhanvien_duytri.list.push(element);
                    } else if (
                        element.loai == "6" &&
                        element.hdtb_id == this.hdtbId &&
                        parseInt(element.ischeck) >= 0 &&
                        !this.ds_nhanvien_giantiep.list.some(
                            (item) => item.ma_nv == element.ma_nv && item.hdtb_id == this.hdtbId
                        )
                    ) {
                        this.ds_nhanvien_giantiep.list.push(element);
                    } else if (
                        element.loai == "5" &&
                        element.hdtb_id == this.hdtbId &&
                        parseInt(element.ischeck) >= 0 &&
                        !this.ds_nhanvien_cskh.list.some(
                            (item) => item.ma_nv == element.ma_nv && item.hdtb_id == this.hdtbId
                        )
                    ) {
                        this.ds_nhanvien_cskh.list.push(element);
                    }
                });
            }
        },
        select_LoaiNV() {
            this.loading(true);
            if(this.selectedOption == 2){
                this.getDonVi();
                // set donvi_id
                // this.donv_id = options.cbx_dsdonvi[0]
                this.getDsNvDaGan();
                this.getDsNvChuaGan();
            }else{
                if (this.check_load == 0) {
                    if (this.Tab4 === true) {
                        this.NAP_DS_DVI_LINE("-2", this.loainv_id);
                    } else {
                        this.NAP_DS_DVI_LINE(-1, this.loainv_id);
                    }
                } else {
                    if (this.Tab4 === true) {
                        this.NAP_DS_DVI_LINE("-2", this.loainv_id);
                    } else {
                        this.NAP_DS_DVI_LINE(this.donviql_id, this.loainv_id);
                    }
                }
            }
            this.loading(false);
        },
        select_Donvi() {
            if (this.selectedOption == 2) {
                // this.getDsNvDaGan();
                // this.getDsNvChuaGan();
                // this.getDsLoaiNV();
            }else{
                if (this.loainv_id == 449) {
                    this.ds_nhavien_chuagan.list = this.dt_Nhanvien_All.filter(
                        (item) => this.donvi_id == 0 || item.donvi_id == this.donvi_id
                    );
                } else {
                    if (this.Tab4 === true) {
                        this.NAP_DS_NHANVIEN_CHUAGAN_V2(this.loainv_id, this.donvi_id);
                    } else {
                        var api6 = api.DS_NHANVIEN_CHUAGAN(
                            this.axios,
                            this.donvi_id,
                            this.loainv_id
                        ).then((response) => {
                            if (
                                response &&
                                response.data &&
                                response.data.error_code === "BSS-00000000" &&
                                response.data.data.length > 0
                            ) {
                                this.ds_nhavien_chuagan.list = response.data.data;
                            }
                        });
                    }
                }
            }
        },
        NAP_DS_DVI_LINE(loaidv, loainv) {
            this.CLEAR_COMBO();
            if (loaidv == null) {
                loaidv = -1;
            }
            var api2 = api.DS_LOAI_DV(this.axios, loaidv, loainv).then((response) => {
                if (
                    response &&
                    response.data &&
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data.length > 0
                ) {
                    var data_dv = response.data.data;
                    this.options.cbx_dsdonvi = data_dv;
                    this.donvi_id = data_dv[0].donvi_id;
                    if (this.Tab4 === true && this.loainv_id != 449) {
                        this.NAP_DS_NHANVIEN_CHUAGAN_V2(this.loainv_id, this.donvi_id);
                    } else {
                        if (this.loainv_id != 0 && this.donvi_id != 0) {
                            this.NAP_DS_NHANVIEN_CHUAGAN(this.donvi_id, this.loainv_id);
                        }
                    }
                }
            });
        },
        async NAP_DS_NHANVIEN_CHUAGAN_V2(loainv_id, donvi_line_id) {
            this.ds_nhavien_chuagan.list = [];
            var danhsach_gt = await api.sp_lay_nhanvien_chuagan_cskh(this.axios, {
                loainv_id,
                donvi_line_id,
            }).then((response) => {
                if (
                    response &&
                    response.data &&
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data.length > 0
                ) {
                    this.ds_nhavien_chuagan.list = response.data.data;
                }
            });
        },
        async NAP_DS_NHANVIEN_CHUAGAN(donvi, nhanvien) {
            // Load danh sách nhân viên chưa gán theo loại nhân viên, đơn vị
            try {
                this.loading(true);
                this.ds_nhavien_chuagan.list = [];
                if (this.loainv_id == 449) {
                    var api_1 = await api.lay_ds_nhanvien_chuagan(this.axios).then(
                        (response) => {
                            if (
                                response &&
                                response.data &&
                                response.data.error_code === "BSS-00000000" &&
                                response.data.data.length > 0
                            ) {
                                this.ds_nhavien_chuagan.list = response.data.data;
                                this.dt_Nhanvien_All = structuredClone(
                                    this.ds_nhavien_chuagan.list
                                );
                                this.options.cbx_dsdonvi = [];
                                this.options.cbx_dsdonvi.push({
                                    donvi_id: 0,
                                    ten_dv: "Tất cả",
                                });
                                response.data.data.forEach((item) => {
                                    if (
                                        !this.options.cbx_dsdonvi.some(
                                            (dv) => dv.donvi_id == item.donvi_id
                                        )
                                    ) {
                                        this.options.cbx_dsdonvi.push({
                                            donvi_id: item.donvi_id,
                                            ten_dv: item.ten_dv,
                                        });
                                    }
                                });
                                var data_default1 = response.data.data.filter(
                                    (item) => item.donvi_id == 0
                                );
                                this.donvi_id = data_default1[0].donvi_id;
                            }
                        }
                    );
                } else {
                    this.loading(true);
                    var api3 = await api.DS_NHANVIEN_CHUAGAN(
                        this.axios,
                        donvi,
                        nhanvien
                    ).then((response) => {
                        if (
                            response &&
                            response.data &&
                            response.data.error_code === "BSS-00000000" &&
                            response.data.data.length > 0
                        ) {
                            this.ds_nhavien_chuagan.list = response.data.data;
                        }
                    });
                }
            } catch (error) {
            } finally {
                this.loading(false);
            }
        },
        LoadDS_HopDongTB_NhanVien(timkiem, hdkh_id) {
            this.check_load = 0;
            var api_loadhd = api.load_ds_hopdong_thuebao(this.axios, hdkh_id).then(
                (response) => {
                    if (
                        response &&
                        response.data &&
                        response.data.error_code === "BSS-00000000"
                    ) {
                        if (
                            response.data.data.ds_dbhd != null &&
                            response.data.data.ds_dbhd !== []
                        ) {
                            this.check_load = 1;
                            this.ds_thuebao_main.list = response.data.data.ds_dbhd;
                            // gán dichvu_id, loaitb_id
                            this.dichvuvt_id = response.data.data.ds_dbhd[0]['dichvuvt_id'];
                            this.loaitb_id = response.data.data.ds_dbhd[0]['loaitb_id'];
                        }
                        if (this.ds_thuebao_main.list.length >= 1) {
                            var index = 0;
                            for (var i = 0; i < this.ds_thuebao_main.list.length; i++) {
                                if (this.ds_thuebao_main.list[i].ma_tb === this.sotb_tam) {
                                    index = i;
                                    break;
                                }
                            }
                            this.$refs.mainGridRef.setCurrentSelectedRow(index);
                        } else {
                            this.$toast.warning(
                                "Không tìm thấy dữ liệu của số thuê bao bạn đã nhập"
                            );
                        }
                        if (
                            response.data.data.dt_hd &&
                            response.data.data.dt_hd.length > 0
                        ) {
                            this.dt_HopDong_All = response.data.data.dt_hd;
                        }
                        this.flag_gan = false;
                    }
                }
            );
        },
        async CapnhatDB() {
            var NHANVIEN_TH = this.$root.token.getNhanVienID();
            // Xử lý với loại truc tiep, gian tiep: Nếu chọn trực tiếp thì xóa hết các hđ ở tab gian tiếp và ngược lại:
            if (this.selectedOption === "1") {
                if (this.dt_HopDong_All.length > 0) {
                    var items = [];
                    this.dt_HopDong_All.forEach((item) => {
                        if (item.loai != "6") {
                            items.push(item);
                        }
                    });
                    this.dt_HopDong_All = items;
                }
            }
            if (this.selectedOption === "2") {
                if (this.dt_HopDong_All.length > 0) {
                    var items = [];
                    this.dt_HopDong_All.forEach((item) => {
                        if (item.loai === "6") {
                            items.push(item);
                        }
                    });
                    this.dt_HopDong_All = items;
                }
            }
            if (!this.dt_HopDong_All.length > 0) {
                this.$toast.warning("Không có dữ liệu gán nhân viên quản lý!");
                return;
            }
            this.$bvModal
                .msgBoxConfirm(
                    "Bạn có muốn cập nhật thông tin nhân viên quản lý không?",
                    {
                        title: "",
                        size: "sm",
                        okTitle: "Đồng ý",
                        cancelTitle: "Hủy",
                    }
                )
                .then(async (v) => {
                    if (!v) {
                        return;
                    }
                    if (this.kieugoi_id == 1) {
                        if (this.dt_HopDong_All.length > 0) {
                            var danhSach = "";
                            this.dt_HopDong_All.forEach((item) => {
                                if (this.data_cskh.length > 0) {
                                    if (
                                        this.data_cskh.filter(
                                            (items) => items.LOAINV_ID == item.loainv_id
                                        ).length > 0
                                    ) {
                                        var tenloai_nv1 = this.data_cskh.filter(
                                            (items) => items.LOAINV_ID == item.loainv_id
                                        )[0].TEN;
                                    } else tenloai_nv1 = "Không xác định";
                                } else {
                                    if (
                                        this.options.cbx_dsloainv.filter(
                                            (items) => items.LOAINV_ID == item.loainv_id
                                        ).length > 0
                                    ) {
                                        var tenloai_nv1 = this.options.cbx_dsloainv.filter(
                                            (items) => items.LOAINV_ID == item.loainv_id
                                        )[0].TEN;
                                    } else tenloai_nv1 = "Không xác định";
                                }
                                var jsonStr = `{\"HDTB_ID\":\"${item.hdtb_id}\",\"NHANVIEN_ID\":\"${item.nhanvien_id}\",\"LOAINV_ID\":\"${item.loainv_id}\",\"TYLE\":\"${item.tyle}\",\"LOAI\":\"${item.loai}\",\"MA_NV\":\"${item.ma_nv}\",\"TEN_NV\":\"${item.ten_nv}\",\"DONVI_ID\":\"${item.donvi_id}\",\"TEN_DV\":\"${item.ten_dv}\",\"LOAI_NV\":\"${tenloai_nv1}\",\"ISCHECK\":\"0\",\"KIEU\":\"${this.selectedOption}\"},`;
                                danhSach = danhSach + jsonStr;
                            });
                            // bo ky tu , o cuoi
                            danhSach = danhSach.substring(0, danhSach.length - 1);
                            var body = {
                                vkieu: 1,
                                vid: this.hdkhId,
                                vnhanvien_id_tt: parseInt(NHANVIEN_TH),
                                vdanhsach: `[${danhSach}]`,
                            };
                            var result = await api.gan_nhanvienql_dbhd(this.axios, body);
                            if (result.data.error_code === "BSS-00000500") {
                                this.$toast.error(result.data.message_detail);
                                return;
                            }
                            var thucthi = "";
                            var thongbao_ = "";
                            if (result.data.data) {
                                thucthi = result.data.data;
                            }
                            if (thucthi) {
                                thongbao_ = thucthi.split("|")[1];
                            }
                            if (thucthi.charAt(0) != "1") {
                                this.$toast.warning(thucthi);
                            } else {
                                if (thongbao_ === "0") {
                                    this.$toast.success("Cập nhật thông tin thành công");
                                } else {
                                    this.$toast.warning(
                                        "Cập nhật thông tin: " +
                                        thongbao_ +
                                        " đã hoàn thành không bị tác động"
                                    );
                                }
                                this.TimThongTinKH();
                            }
                        }
                    }
                    if (this.kieugoi_id == 2) {
                        if (this.dt_HopDong_All.length > 0) {
                            var danhSach = "";
                            this.dt_HopDong_All.forEach((item) => {
                                if (this.data_cskh.length > 0) {
                                    if (
                                        this.data_cskh.filter(
                                            (items) => items.LOAINV_ID == item.loainv_id
                                        ).length > 0
                                    ) {
                                        var tenloai_nv = this.data_cskh.filter(
                                            (items) => items.LOAINV_ID == item.loainv_id
                                        )[0].TEN;
                                    } else tenloai_nv = "Không xác định";
                                } else {
                                    if (
                                        this.options.cbx_dsloainv.filter(
                                            (items) => items.LOAINV_ID == item.loainv_id
                                        ).length > 0
                                    ) {
                                        var tenloai_nv = this.options.cbx_dsloainv.filter(
                                            (items) => items.LOAINV_ID == item.loainv_id
                                        )[0].TEN;
                                    } else tenloai_nv = "Không xác định";
                                }
                                var jsonStr = `{\"THUEBAO_ID\":\"${item.thuebao_id}\",\"NHANVIEN_ID\":\"${item.nhanvien_id}\",\"LOAINV_ID\":\"${item.loainv_id}\",\"TYLE\":\"${item.tyle}\",\"LOAI\":\"${item.loai}\",\"MA_NV\":\"${item.ma_nv}\",\"TEN_NV\":\"${item.ten_nv}\",\"DONVI_ID\":\"${item.donvi_id}\",\"TEN_DV\":\"${item.ten_dv}\",\"LOAI_NV\":\"${tenloai_nv}\",\"ISCHECK\":\"0\",\"KIEU\":\"${this.selectedOption}\"},`;
                                danhSach = danhSach + jsonStr;
                            });
                            // bo ky tu , o cuoi
                            danhSach = danhSach.substring(0, danhSach.length - 1);
                            var body = {
                                vkieu: 2,
                                vid: this.thuebaoID,
                                vnhanvien_id_tt: parseInt(NHANVIEN_TH),
                                vdanhsach: `[${danhSach}]`,
                            };
                            var result = await api.gan_nhanvienql_dbhd(this.axios, body);
                            if (result.data.error_code === "BSS-00000500") {
                                this.$toast.error(result.data.message_detail);
                                return;
                            }
                            var thucthi = "";
                            var thongbao_ = "";
                            if (result.data.data) {
                                thucthi = result.data.data;
                            }
                            if (thucthi) {
                                thongbao_ = thucthi.split("|")[1];
                            }
                            if (thucthi.charAt(0) != "1") {
                                this.$toast.warning(thucthi);
                            } else {
                                if (thongbao_ === "0") {
                                    this.$toast.success("Cập nhật thông tin thành công");
                                } else {
                                    this.$toast.warning(
                                        "Cập nhật thông tin: " + thongbao_ + "  "
                                    );
                                }
                                this.TimThongTinKH();
                            }
                        }
                    }
                });
        },
        async getFormattedDateTime() {
            const now = new Date();
            const year = now.getFullYear();
            const month = String(now.getMonth() + 1).padStart(2, '0');
            const day = String(now.getDate()).padStart(2, '0');
            const hours = String(now.getHours()).padStart(2, '0');
            const minutes = String(now.getMinutes()).padStart(2, '0');
            const seconds = String(now.getSeconds()).padStart(2, '0');

            this.dateTimeString = `${year}${month}${day}${hours}${minutes}${seconds}`;
        },
        async Gan_click() {
            if(this.selectedOption == 2){
                 console.log(33333333)
                // if (this.ds_nhavien_chuagan.select_chuagan.length) {
                //     for(i = 0;i<this.ds_nhavien_chuagan.select_chuagan.length;i++){
                //         await this.getFormattedDateTime();
                //         this.ds_nhavien_chuagan.select_chuagan[i].httb_nhanvien_cs_id = parseInt(this.dateTimeString);
                //         this.dateTimeString = "";
                //     }
                //     this.ds_nhanvien_giantiep.list = [...this.ds_nhanvien_giantiep.list, ...this.ds_nhavien_chuagan.select_chuagan];
                //     console.log(this.ds_nhavien_chuagan.select_chuagan)
                //     console.log(this.ds_nhanvien_giantiep.list) 
                // } else {
                //     this.$toast.warning("Chưa chọn nhân viên để gán")
                // }
                if (this.ds_nhavien_chuagan.select_chuagan.length) {
                    for (let i = 0; i < this.ds_nhavien_chuagan.select_chuagan.length; i++) {
                        const newItem = { ...this.ds_nhavien_chuagan.select_chuagan[i] };
                        await this.getFormattedDateTime();
                        newItem.httb_nhanvien_cs_id = parseInt(this.dateTimeString) + i;
                        this.ds_nhanvien_giantiep.list.push(newItem);
                        this.dateTimeString = "";
                    }
                    console.log(this.ds_nhavien_chuagan.select_chuagan);
                    console.log(this.ds_nhanvien_giantiep.list);
                } else {
                    this.$toast.warning("Chưa chọn nhân viên để gán");
                }
            }else{
                console.log(44444444)
                var tmp_ds_nhanvien = null;
                var vLoaiNV = "0";
                if (this.ds_nhanvien_ptm.active) {
                    tmp_ds_nhanvien = this.ds_nhanvien_ptm.list;
                    vLoaiNV = "1";
                } else if (this.ds_nhanvien_hotro.active) {
                    tmp_ds_nhanvien = this.ds_nhanvien_hotro.list;
                    vLoaiNV = "3";
                } else if (this.ds_nhanvien_duytri.active) {
                    tmp_ds_nhanvien = this.ds_nhanvien_duytri.list;
                    vLoaiNV = "2";
                } else if (this.ds_nhanvien_giantiep.active) {
                    tmp_ds_nhanvien = this.ds_nhanvien_giantiep.list;
                    vLoaiNV = "6";
                } else if (this.ds_nhanvien_cskh.active) {
                    tmp_ds_nhanvien = this.ds_nhanvien_cskh.list;
                    vLoaiNV = "5";
                } else if (this.ds_nhanvien_hotro_kythuat.active) {
                    tmp_ds_nhanvien = this.ds_nhanvien_hotro_kythuat.list;
                    vLoaiNV = "7";
                }
                // Kiểm tra HRM
                var kiemtra_hrm = true;
                if (this.ds_nhavien_chuagan.select_chuagan) {
                     console.log(5555555)
                    for (
                        var i = 0;
                        i < this.ds_nhavien_chuagan.select_chuagan.length;
                        i++
                    ) {
                        var item = this.ds_nhavien_chuagan.select_chuagan[i];
                        if (this.loainv_id == "449") {
                            try {
                                var response = await api.xacThucNhanVien(this.axios, item.ma_nv);
                                if (
                                    response.data &&
                                    response.data.error_code === "BSS-00000000" &&
                                    response.data.data.ctHoSoNhanSu &&
                                    response.data.data.ctHoSoNhanSu.length > 0
                                ) {
                                    var data_dv = response.data.data.ctHoSoNhanSu;
                                } else {
                                    kiemtra_hrm = false;
                                    this.$toast.warning("Lỗi kiểm tra mã nhân viên: " + item.ma_nv);
                                    return;
                                }
                            } catch (error) {
                                kiemtra_hrm = false;
                                this.$toast.warning("Có lỗi xảy ra khi kiểm tra mã nhân viên: " + error);
                                return;
                            }
                        }
                    }
                }
    
                if (!kiemtra_hrm) return; //
    
                // if (vLoaiNV == "3" && this.loainv_id == 451) {
                //   this.$toast.warning(
                //     "Nhân viên phát triển hỗ trợ không thể chọn loại nhân viên (AM)"
                //   );
                //   return;
                // }
    
                if (this.kieugoi_id == 1) {
                     console.log(77777777)
                    if (this.ds_nhavien_chuagan.select_chuagan.length > 0) {
                        this.ds_nhavien_chuagan.select_chuagan.forEach((item) => {
                            if (
                                !tmp_ds_nhanvien.some(
                                    (nv) =>
                                        nv.ma_nv == item.ma_nv &&
                                        nv.hdtb_id == this.hdtbId &&
                                        nv.loai == vLoaiNV
                                )
                            ) {
                                var new_item = structuredClone(item);
                                new_item.hdtb_id = this.hdtbId;
                                new_item.loai = vLoaiNV;
                                tmp_ds_nhanvien.push(new_item);
                            }
                            if (
                                !this.dt_HopDong_All ||
                                !this.dt_HopDong_All.some(
                                    (nv) =>
                                        nv.ma_nv == item.ma_nv &&
                                        nv.hdtb_id == this.hdtbId &&
                                        nv.loai == vLoaiNV
                                )
                            ) {
                                var new_item = structuredClone(item);
                                new_item.hdtb_id = this.hdtbId;
                                new_item.loai = vLoaiNV;
                                this.dt_HopDong_All.push(new_item);
                            } else {
                                for (var i = 0; i < this.dt_HopDong_All.length; i++) {
                                    if (
                                        this.dt_HopDong_All[i].ma_nv == item.ma_nv &&
                                        this.dt_HopDong_All[i].hdtb_id == this.hdtbId
                                    ) {
                                        this.dt_HopDong_All[i].ischeck = 1;
                                    }
                                }
                            }
                        });
                        this.ds_nhanvien_hotro_kythuat.select_danggo = [];
                        this.ds_nhanvien_hotro.select_danggo = [];
                        this.ds_nhanvien_duytri.select_danggo = [];
                        this.ds_nhanvien_ptm.select_danggo = [];
                        this.ds_nhanvien_giantiep.select_danggo = [];
                        this.ds_nhanvien_cskh.select_danggo = [];
                    } else {
                        this.$toast.warning("Chưa chọn danh sách nhân viên quản lý!");
                    }
                }
    
                if (this.kieugoi_id == 2) {
                     console.log(888888888)
                    if (this.ds_nhavien_chuagan.select_chuagan.length > 0) {
                        this.ds_nhavien_chuagan.select_chuagan.forEach((item) => {
                            if (
                                !tmp_ds_nhanvien.some(
                                    (nv) =>
                                        nv.ma_nv == item.ma_nv &&
                                        nv.thuebao_id == this.thuebaoID &&
                                        nv.loai == vLoaiNV
                                )
                            ) {
                                var new_item = structuredClone(item);
                                new_item.thuebao_id = this.thuebaoID;
                                new_item.loai = vLoaiNV;
                                tmp_ds_nhanvien.push(new_item);
                            }
                            if (
                                !this.dt_HopDong_All ||
                                !this.dt_HopDong_All.some(
                                    (nv) =>
                                        nv.ma_nv == item.ma_nv &&
                                        nv.thuebao_id == this.thuebaoID &&
                                        nv.loai == vLoaiNV
                                )
                            ) {
                                var new_item = structuredClone(item);
                                new_item.thuebao_id = this.thuebaoID;
                                new_item.loai = vLoaiNV;
                                this.dt_HopDong_All.push(new_item);
                            } else {
                                for (var i = 0; i < this.dt_HopDong_All.length; i++) {
                                    if (
                                        this.dt_HopDong_All[i].ma_nv == item.ma_nv &&
                                        this.dt_HopDong_All[i].thuebao_id == this.thuebaoID
                                    ) {
                                        this.dt_HopDong_All[i].ischeck = 1;
                                    }
                                }
                            }
                        });
                        this.ds_nhanvien_hotro_kythuat.select_danggo = [];
                        this.ds_nhanvien_hotro.select_danggo = [];
                        this.ds_nhanvien_duytri.select_danggo = [];
                        this.ds_nhanvien_ptm.select_danggo = [];
                        this.ds_nhanvien_giantiep.select_danggo = [];
                        this.ds_nhanvien_cskh.select_danggo = [];
                    } else {
                        this.$toast.warning("Chưa chọn danh sách nhân viên quản lý!");
                    }
                }
            }
        },
        Gogan_click() {
            if(this.selectedOption == 2){
                if (this.ds_nhanvien_giantiep.select_danggo.length) {
                    let arrId = this.ds_nhanvien_giantiep.select_danggo.map(item => item.httb_nhanvien_cs_id)
                    this.ds_nhanvien_giantiep.list = this.ds_nhanvien_giantiep.list.filter(i => {
                        if (!arrId.includes(i.httb_nhanvien_cs_id)) {
                            return i;
                        }
                    });
                    this.ds_nhanvien_giantiep.select_danggo = [];
                } else {
                    this.$toast.warning("Chưa chọn nhân viên để gỡ")
                }
            }else{
                console.log('x2');
                var tmp_ds_nhanvien;
                var vLoaiNV = "0";
                if (this.ds_nhanvien_ptm.active) {
                    tmp_ds_nhanvien = this.ds_nhanvien_ptm;
                    vLoaiNV = "1";
                } else if (this.ds_nhanvien_hotro.active) {
                    tmp_ds_nhanvien = this.ds_nhanvien_hotro;
                    vLoaiNV = "3";
                } else if (this.ds_nhanvien_duytri.active) {
                    tmp_ds_nhanvien = this.ds_nhanvien_duytri;
                    vLoaiNV = "2";
                } else if (this.ds_nhanvien_giantiep.active) {
                    tmp_ds_nhanvien = this.ds_nhanvien_giantiep;
                    vLoaiNV = "6";
                } else if (this.ds_nhanvien_cskh.active) {
                    tmp_ds_nhanvien = this.ds_nhanvien_cskh;
                    vLoaiNV = "5";
                } else if (this.ds_nhanvien_hotro_kythuat.active) {
                    tmp_ds_nhanvien = this.ds_nhanvien_hotro_kythuat;
                    vLoaiNV = "7";
                }
    
                if (tmp_ds_nhanvien.select_danggo.length > 0) {
                    if (this.check_all == false) {
                        tmp_ds_nhanvien.select_danggo.forEach((item) => {
                            if (this.kieugoi_id == 2) {
                                for (var i = 0; i < this.dt_HopDong_All.length; i++) {
                                    if (
                                        this.dt_HopDong_All[i].ma_nv == item.ma_nv &&
                                        this.dt_HopDong_All[i].loai == vLoaiNV &&
                                        this.dt_HopDong_All[i].thuebao_id == item.thuebao_id
                                    ) {
                                        this.dt_HopDong_All.splice(i, 1);
                                        break;
                                    }
                                }
                            }
                            if (this.kieugoi_id == 1) {
                                for (var i = 0; i < this.dt_HopDong_All.length; i++) {
                                    if (
                                        this.dt_HopDong_All[i].ma_nv == item.ma_nv &&
                                        this.dt_HopDong_All[i].loai == vLoaiNV &&
                                        this.dt_HopDong_All[i].hdtb_id == item.hdtb_id
                                    ) {
                                        this.dt_HopDong_All.splice(i, 1);
                                        break;
                                    }
                                }
                            }
    
                            for (var i = 0; i < tmp_ds_nhanvien.list.length; i++) {
                                if (
                                    tmp_ds_nhanvien.list[i].ma_nv == item.ma_nv &&
                                    tmp_ds_nhanvien.list[i].loai == vLoaiNV
                                ) {
                                    tmp_ds_nhanvien.list.splice(i, 1);
                                    break;
                                }
                            }
                        });
                    } else {
                        // Gỡ toàn bộ thuê bao thuộc HD + loại
                        this.$bvModal
                            .msgBoxConfirm(
                                "Bạn đang gỡ nhân viên khỏi toàn bộ thuê bao. Bạn có chắc chắn muốn gỡ tất cả không?",
                                {
                                    title: "",
                                    size: "sm",
                                    okTitle: "Đồng ý",
                                    cancelTitle: "Hủy",
                                }
                            )
                            .then(async (v) => {
                                if (!v) {
                                    return;
                                }
    
                                tmp_ds_nhanvien.select_danggo.forEach((item) => {
                                    for (var i = 0; i < this.dt_HopDong_All.length; i++) {
                                        if (
                                            this.dt_HopDong_All[i].ma_nv == item.ma_nv &&
                                            this.dt_HopDong_All[i].loai == vLoaiNV
                                        ) {
                                            this.dt_HopDong_All.splice(i, 1);
                                            i = i - 1;
                                        }
                                    }
    
                                    for (var i = 0; i < tmp_ds_nhanvien.list.length; i++) {
                                        if (
                                            tmp_ds_nhanvien.list[i].ma_nv == item.ma_nv &&
                                            tmp_ds_nhanvien.list[i].loai == vLoaiNV
                                        ) {
                                            tmp_ds_nhanvien.list.splice(i, 1);
                                            break;
                                        }
                                    }
                                });
                            });
                    }
                } else {
                    this.$toast.warning("Bạn chưa check chọn dữ liệu để gỡ!");
                }
            }
        },
        actionCompleteHandle(args) {
            // Xu ly danh sach filter
            if (args.rows && args.rows.length > 0) {
                this.ds_nhavien_chuagan.display_list = [];
                args.rows.forEach((row) => {
                    this.ds_nhavien_chuagan.display_list.push(row.data);
                });
            }
        },
        CLEAR() {
            // this.dt_HopDong_All = [];
            // (this.ds_thuebao_main.list = []),
            //     (this.ds_nhanvien_ptm.list = []),
            //     (this.ds_nhanvien_hotro.list = []),
            //     (this.ds_nhanvien_hotro_kythuat.list = []),
            //     (this.ds_nhanvien_duytri.list = []),
            //     (this.ds_danhba_timkiem.list = []),
            //     (this.ds_nhanvien_giantiep.list = []),
            //     (this.ds_nhanvien_cskh.list = []),
            //     (this.flag_gan = true),
            //     (this.dvi_tt = -1);
        },
        // Set cho đơn vị:
        CLEAR_COMBO() {
            this.dvi_tt = -1;
            this.options.cbx_dsdonvi = [];
            this.ds_nhavien_chuagan.list = [];
        },
        // Nếu tích checkbox toàn bộ nhân viên
        check(e) {
            this.check_all = e.target.checked;
        },
        // Nếu tích checkbox toàn bộ nhân viên
        checkVK(e) {
            this.check_vk = e.target.checked;
        },
        //Nếu tích chọn radio
        check_radio(e) {
           
            if (this.selectedOption == "1") {
                this.hideTab = false;
                this.SelectTab1();
            }
            if (this.selectedOption == "2") {
                this.hideTab = true;
                this.SelectTabGIANTIEP();
            }
        },
        async init() {
            // if (this.kieugoi_id != 0) {
                var response = await api.ADMIN_LOAI_NV(this.axios);
                if (
                    response &&
                    response.data &&
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data.length > 0
                ) {
                    // Xử lý loại  nhân viên all:
                    this.data_cskh = response.data.data;
                    var data_lnv = this.data_cskh.filter(
                        (item) => item.LOAINV_ID >= 450 && item.LOAINV_ID <= 454
                    );
                    data_lnv.push({ LOAINV_ID: 449, TEN: "Khác" });
                    this.options.cbx_dsloainv = data_lnv;
                    // Xử lý nếu không có LOAINV_ID = 450
                    var data_default;
                    if (data_lnv.some((item) => item.LOAINV_ID === 450)) {
                        data_default = data_lnv.find((item) => item.LOAINV_ID === 450);
                    } else {
                        data_default = data_lnv[0];
                    }
                    if (data_default && data_default.LOAINV_ID > 0) {
                        this.loainv_id = data_default.LOAINV_ID;
                        this.NAP_DS_DVI_LINE(-1, this.loainv_id);
                    }
                } else {
                    this.$toast.warning(
                        "Có lỗi khi quá trình nạp dữ liệu từ DB. Liên hệ Admin để xử lý!"
                    );
                }
            // }
        },
        async sp_lay_ds_nhanvien_theo_manv(ls_manv) {
            try {
                let response = await this.axios.post('/web-hopdong/tochuc_hoanthien_kenh_khdn/sp_lay_ds_nhanvien_theo_manv', {
                    ls_manv: ls_manv
                });
                if (
                    response &&
                    response.data &&
                    response.data.error_code === "BSS-00000000"
                ) {
                }
            } catch (error) {
                console.log(error);
            }
        },
        async getDsNvDaGan() {
            try {
                let body = {
                    hdtb_id: this.hdtbID,
                    dichvuvt_id: this.dichvuvt_id,
                    loaitb_id: this.loaitb_id
                }
                let data = this.getData(await api.dsNhanVienDaGan(this.axios, body));
                this.ds_nhanvien_giantiep.list = data;
            } catch (error) {
                console.log(error);
            }
        },
        async getDsNvChuaGan() {
            try {
                let body = {
                    hdtb_id: this.hdtbID,
                    loainv_id: this.loainv_id ? this.loainv_id : 0,
                    donvi_id: this.donvi_id ? this.donvi_id : 0,
                    ma_nv: "",
                    dichvuvt_id: this.dichvuvt_id,
                    loaitb_id: this.loaitb_id
                }
                let data = this.getData(await api.dsNhanVienChuaGan(this.axios, body));
                this.ds_nhavien_chuagan.list = data;
                //
                //this.handleDataList();
            } catch (error) {
                console.log(error);
            }
        },
        initData() {
            try {
                // get nguoidung_id
                // let res_tt_nd = await this.axios.post('/quantri/user/thongtin_nguoidung2', {});
                // let nguoidung_id = 999999;
                // if (res_tt_nd.data && res_tt_nd.data.error_code == 'BSS-00000000')
                //     nguoidung_id = res_tt_nd.data.data['nguoidung_id'];
                this.getDSCongDoan();
                // this.getDsLoaiNV();
                if (this.selectedOption == "2") {
                    this.hideTab = true;
                    this.SelectTabGIANTIEP();
                }
            } catch (error) {
                console.log(error);
            }
        },
        getData(res) {
            if (res.data.error == '200' && res.data.error_code == 'BSS-00000000') {
                // console.log(res.data.data);
                return res.data.data;
            } else {
                return []
            }
            // else{
            //     this.$toast.error("Lỗi: " + res.data.message_detail);
            // }
        },
        async getDonVi() {
            try {
                let body = { 
                    "loainv_id": this.loainv_id,
                    "dichvuvt_id": this.dichvuvt_id, 
                    "loaitb_id": this.loaitb_id
                
                }
                let data = this.getData(await api.dsDonVi(this.axios, body));
                this.options.cbx_dsdonvi = data;
                this.donvi_id = data[0]['donvi_id'];
            } catch (error) {
                console.log(error);
            }
        },
        async getDsLoaiNV() {
            try {
                let data = this.getData(await api.dsLoaiNhanVien(this.axios));
                // console.log(data);
                this.options.cbx_dsloainv = data;
                this.loainv_id = data[1]['loainv_id']
                this.getDonVi();
            } catch (error) {
                console.log(error);
            }
        },
        async getDSCongDoan() {
            try {
                let body = {
                    hdtb_id: this.hdtbID ? this.hdtbID : 0,
                    donvi_id: this.donvi_id ? this.donvi_id : 0,
                }
                let data = this.getData(await api.ds_congdoan(this.axios, body));
                this.cbb_CongDoan.Items = data;
                
                // console.log(this.cbb_CongDoan);
            } catch (error) {
                console.log(error);
            }
        },
        handleDataList(){
            try {
                console.log('dmm: ',this.ds_nhavien_chuagan.list, this.ds_nhanvien_giantiep.list);
                if(this.ds_nhavien_chuagan.list.length){
                    this.ds_nhavien_chuagan.list.forEach(i => {
                        if(this.cbb_CongDoan.Items.length){
                            let congdoan = this.cbb_CongDoan.Items.find(e => {
                                if(e.nhanvien_id == i['nhanvien_id'])
                                    return e
                            });
                            // console.log('x1', congdoan);
                            if(congdoan){
                                i.congdoan_id = congdoan.congdoan_id;
                            }
                        }
                    })
                    // console.log('x1: ',tem1);
                    // this.ds_nhavien_chuagan.list = [...tem1];
                }
                if(this.ds_nhanvien_giantiep.list.length){
                    this.ds_nhanvien_giantiep.list.forEach(i => {
                        if (this.cbb_CongDoan.Items.length) {
                            let congdoan = this.cbb_CongDoan.Items.find(e => {
                                if(e.nhanvien_id == i['nhanvien_id'])
                                    return e
                            });
                            // console.log('x2', congdoan);
                            if(congdoan){
                                i.congdoan_id = congdoan.congdoan_id;
                            }
                        }
                    });
                    // console.log('x2',tem2);
                    // this.ds_nhanvien_giantiep.list = [...tem2];
                }
                console.log('dmm1: ',this.ds_nhavien_chuagan.list, this.ds_nhanvien_giantiep.list);
            } catch (error) {
                console.log('line 2906', error);
            }
        },
        setFileUploadVK(data) {
            try {
                if(data){
                    this.items.file_upload = data.url;
                    this.check_vk = true;
                    this.items.ngay_gan = data.ngay_gan? data.ngay_gan.slice(0, 10) : '';
                }else{
                    this.items.file_upload = '';
                    this.check_vk = false;
                    this.items.ngay_gan = '';
                }
            } catch (error) {
                console.log(error);
            }
        },
        async getFileVK() {
            try {
                let body = {
                    hdtb_id: this.hdtbID
                }
                let res = await api.getFileVK(this.axios, body);
                if(res.data.error == '200' && res.data.error_code == 'BSS-00000000'){
                    this.setFileUploadVK(res.data.data[0]);
                }else{
                    this.setFileUploadVK(null);
                }
            } catch (error) {
                console.log(error);
            }
        },
        async acceptSearchContract(args) {
            // console.log('xxx',args);
            this.tt_hd.so_hd = args.ma_hd;
            this.tt_hd.ngay_ky = moment(args.ngaylap_hd.substring(0, 10)).format("DD/MM/YYYY");
            this.tt_hd.gt_hd = args.so_gt;
            this.tt_hd.ten_kh = args.ten_kh;
            this.tt_hd.mst = args.mst;
            this.tt_hd.diachi_kh = args.diachi_kh;
            this.hdtb_id = args.hdtb_id;
            this.hdkh_id = args.hdkh_id;
            // call api lấy ds thuê bao
            let dstb = this.getData(await api.dsThueBao(this.axios, args.hdkh_id))
            this.config.thuebao.list = dstb;
            // select cbb loai nhaan vien
            this.select_LoaiNV(this.options.cbx_dsloainv[0]['loainv_id']);
            // get file vk 
            await this.getFileVK();
        },
        rowSelectedCD() {
        },
        rowSelectedHD() {
        },
        btnTraCuuTB_Click() {
            this.$refs.popupSearchContract.showModal();
        },
        txtSoHD_KeyPress() {
        },
        clickButton(key) {
            if (key == "NHAPLAI") {
                this.CLEAR();
            }
            if (key == "GHILAI") {
                this.tsbtnGhiLai_Click();
            }
            if (key == "CHOT") {
                this.tsbtnChot_Click();
            }
        },
        async tsbtnGhiLai_Click() {
            try {
                if(this.check_vk && !this.items.file_upload){
                    return this.$toast.warning("Bạn chưa nhập văn bản  vượt khung")
                }
                this.loading(true);
                let body = {
                    vhdtb_id: this.hdtbID,
                    vkieu: 1,
                    vid: this.hdkhId ? this.hdkhId : 0,
                    vurl: this.items.file_upload,
                    vdanhsach: JSON.stringify(this.ds_nhanvien_giantiep.list).toUpperCase()
                }
                let res = await api.ganNhanVienHopDong(this.axios, body);
                if (res.data.error == '200' && res.data.error_code == 'BSS-00000000') {
                    this.$toast.success('Thành công')
                } else {
                    this.$toast.error(res.data.message)
                }
                this.loading(false);
            } catch (error) {
                this.loading(false);
                console.log(error);
            }
        },
        async tsbtnChot_Click() {
            try {
                this.loading(true)
                let body = {
                    vhdtb_id: this.hdtbID,
                    vkieu: 1,
                    vid: this.hdkhId ? this.hdkhId : 0,
                    vurl: this.items.file_upload,
                    vdanhsach: JSON.stringify(this.ds_nhanvien_giantiep.list).toUpperCase()
                }
                let res = await api.chotGanCongDoan(this.axios, body);
                if (res.data.error == '200' && res.data.error_code == 'BSS-00000000') {
                    this.$toast.success('Thành công');
                    this.getDsNvDaGan();
                } else {
                    this.$toast.error(res.data.message)
                }
                this.loading(false)
            } catch (error) {
                this.loading(false)
                console.log(error);
            }
        },
        CLEAR() {
            // this.hdtb_id = ''
            // this.tt_hd.so_hd = ''
            // this.tt_hd.ngay_ky = ''
            // this.tt_hd.gt_hd = ''
            // this.tt_hd.ten_kh = ''
            // this.tt_hd.mst = '',
            //     this.tt_hd.diachi_kh = ''
            // this.donvi_id = ''
            // this.loainv_id = ''
            // this.gan_congdoan.chk_toanbo = true,
            //     this.gan_congdoan.chk_vuotkhung = false
            // this.gan_congdoan.vb_vuotkhung = ''
            // this.gan_congdoan.ngay_gan = ''
            // this.config.thuebao.list = []
            // this.ds_nhanvien_giantiep.list = []
            // this.ds_nhavien_chuagan.list = []
            // this.ds_nhavien_chuagan.select_chuagan = [];
            this.dt_HopDong_All = [];
            (this.ds_thuebao_main.list = []),
                (this.ds_nhanvien_ptm.list = []),
                (this.ds_nhanvien_hotro.list = []),
                (this.ds_nhanvien_hotro_kythuat.list = []),
                (this.ds_nhanvien_duytri.list = []),
                (this.ds_danhba_timkiem.list = []),
                (this.ds_nhanvien_giantiep.list = []),
                (this.ds_nhanvien_cskh.list = []),
                (this.flag_gan = true),
                (this.dvi_tt = -1);
        },
        currencyFormater(c) {
            try {
                // Create our number formatter.
                var formatter = new Intl.NumberFormat('en-US', {
                    style: 'currency',
                    currency: 'USD',
                    // These options are needed to round to whole numbers if that's what you want.
                    //minimumFractionDigits: 0, // (this suffices for whole numbers, but will print 2500.10 as $2,500.1)
                    //maximumFractionDigits: 0, // (causes 2500.99 to be printed as $2,501)
                });
                return formatter.format(c);
            } catch (error) {
                // this.$toast.error(error);
                console.log(error);
            }
        },
    },
}
</script>
<style>
.list a {
    cursor: pointer;
}
.e-grid td.e-selectionbackground {
    background-color: #F9E1A9 !important;
}
.e-grid tr:hover {
    background-color: #FFF9EB !important;
}
.e-checkbox-wrapper .e-check::before,
.e-css.e-checkbox-wrapper .e-check::before {
    content: "\e718";
    background-color: transparent;
    line-height: 16px;
}
.e-checkbox-wrapper .e-stop::before,
.e-css.e-checkbox-wrapper .e-stop::before {
    content: "\e7a5" !important
}
.e-tab .e-tab-header .e-toolbar-item.e-active .e-tab-text {
    color: #000000 !important;
    font-weight: bold !important;
}
/* .e-tab .e-content{
    border: 1px solid rgb(218, 218, 218);
    border-radius: 5px;
} */
.disabled {
    pointer-events: none;
    opacity: 0.5;
    background: #ccc;
}
.disable-btn:hover {
    background-color: white !important;
}
.disable-btn a {
    color: #6c757d !important;
}
.disable-btn a:hover {
    cursor: default;
    color: #6c757d;
}
#treeview .e-list-item .e-level-3>.e-text-content .e-list-text {
    font-weight: bold;
    font-style: italic;
    color: darkmagenta;
}
</style>