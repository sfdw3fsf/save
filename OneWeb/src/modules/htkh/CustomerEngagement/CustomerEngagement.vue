<template src="./CustomerEngagement.html"></template>
<style scoped src="./CustomerEngagement.scss"></style>
<script>
import VueElement from "./components/VueElement.vue";
import api from "./api/index.js";
import collums from "./define/collums.js";
import DataGridCustom from "./components/DataGrid";
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
import moment from 'moment'
import { BFormSelect } from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import DataGrid2 from '@/components/Controls/DataGrid2'
import {
    required,
    maxLength,
    minLength,
    numeric
} from "vuelidate/lib/validators"
import {
    DichVuVienThong,
    LoaiHopDong,
    LoaiHinhTB,
    KHOANMUC_TT,
    Loai_NV,
    LOAI_DV,
    LOAI_KV,
    TrangThaiHD,
    TRANGTHAI_DONGBO,
    KieuLapDat,
} from "./define/index.js";
export default {
    components: {
        VueElement,
        BFormSelect,
        DataGridCustom,
        DataGrid2,
        KDatePicker
    },
    name: 'Order',
    async created() {
        this.SetButton(-1);
        this.listGioitinh = [{
                id: 0,
                ten: "Nam",
            },
            {
                id: 1,
                ten: "Nữ",
            },
            {
                id: -1,
                ten: "Khác",
            }
        ];
        this.cbbGioitinh = this.listGioitinh[0].id;
    },
    data() {
        return {
            cbo_NganHang: {
                items: [],
                selected: null
            },
            dateFormat: 'dd/MM/yyyy',
            formData: {
                thuebao_id: 0,
                dichvuvt_id: 0,
                ma_tb: ''
            },
            ...collums,
            header: {
                title: "Giao nhiệm vụ tiếp cận khách hàng",
                list: [{
                        name: "HTKH",
                        link: {
                            name: "Ui.cards"
                        }
                    },
                    {
                        name: "Giao nhiệm vụ tiếp cận khách hàng",
                        link: {
                            name: "Ui.buttons"
                        },
                    },
                ],
            },
            btn_Enabled: {
                nhapMoi: true,
                ghiLai: false,
                xoa: false,
                huy: false,
                gankh: false,
                giaonhiemvu: false
            },
            searchConditions: {
                ngaylap_hd: 'equal', // Giá trị mặc định
                // Thêm các điều kiện tìm kiếm cho các cột khác nếu cần
            },
            txtNgayGiao: moment().format('DD/MM/YYYY'),
            txtHanthuchien: moment().format('DD/MM/YYYY'),
          
            items: {
                dichvuvt_id: 0,
                loaitb_id: 0,
                so_dt: "",
                ten_dv:"",
                ma_nv:"",
                ten_nv:"",
                email:"",
                nhanvien_tc: 0,
                ngay_giao: moment().format('DD/MM/YYYY'),
                han_thuchien: moment().format('DD/MM/YYYY'),
            },
            tsbtnHoanThanh: {
                Enabled: true
            },
            tsbtnNhapMoi: {
                Enabled: true
            },
            tsbtnXoa: {
                Enabled: true
            },
            tsbtnHuyBo: {
                Enabled: true
            },
            tsbtnGhiLai: {
                Enabled: true
            },
            tsbtnXoaTB: {
                Enabled: true
            },
            tsbtnThemTB: {
                Enabled: true
            },
             tsbtnHenLD: {
                Enabled: true
            },
             rdoGTNam: {
                Enabled: true
            },
             rdoGTNu: {
                Enabled: true
            },
            rdoGTNone: {
                Enabled: true
            },
            cboDaiVT:{
                Enabled: true
            },
            tt_nd: {
                ngay_cn: this.$auth.getNgayCapNhat(),
                tentat: "",
                phanvung_id: this.$root.token.getPhanVungID(),
                donvi_id: this.$auth.getDonViID(),
                nhanvien_id: this.$auth.getNhanVienID(),
                may_cn: this.$auth.getUserName(),
                ma_nd: this.$auth.getUserName(),
                ip: this.$auth.getUserName(),
                ma_tinh:  this.$root.token.getMaTinh(),
                //ten_dv:this.$auth.getTenDonVi()
            },
            grid_Kenhban: {
                items: [],
                selected: {}
            },
        }
    },
    async mounted() {
    }, 
    methods: {
    }   
}
</script>
<style>
.info-row .value{
    padding-left: 0px !important;
}
.greyColor {
    background-color: #e1e1e1 !important;
}
.pd-l0{
    padding-left: 0px !important;
}
.bd-cl0{
    border-color: gray !important
}
.bd-0{
    border-color: gray !important
}
</style>