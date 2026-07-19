<template src="./UpdateProcessIncident.html"></template>
<script>
import breadcrumb from '@/components/breadcrumb'
import api from "./UpdateProcessIncident.js";
import moment from "moment";
import DataGridCustom from "@/modules/admin/category/EmployeeAssignmentCriteria/components/DataGrid";
import columns from "./columns.js";
import { Query } from '@syncfusion/ej2-data'
import Vue from 'vue';
import { DropDownListPlugin } from "@/components/duybn_components/Dropdowns/ej2-vue-dropdowns";
Vue.use(DropDownListPlugin);
import enums from '@/modules/incident/subscriber/HandleAcceptanceIncidentReports/enums.js'
import LayDuLieuBC from "@/modules/contract/complete_profile/CompleteProfileITVAS/popups/popupLayDuLieuBC/LayDuLieuBC.vue";
import xlsx from "xlsx";
import NoiDungTT from "./popups/NoiDungTT"
import UploadEProfileAPI from '@/modules/contract/profile/UploadEProfile/UploadEProfileAPI'
import NoiDung from "./popups/NoiDung"
import PopupGuiSms from '@/modules/install/UpdateInstallProgress/popups/PopupGuiSms'
import daunoithuebao from '../HandleAcceptanceIncidentReports/Popups/DauNoi.vue'
import frmPhanAnhBH from "../SearchIncidentUpdateAppointment/components/frmPhanAnhBH";
import frmTraCuuPhieuBH from "../SearchIncidentUpdateAppointment"
import CheckPort from '@/modules/search/subscriber/CheckPort';

export default {
    name:'UpdateProcessIncident',
    components:{
        breadcrumb,
        DataGridCustom,
        LayDuLieuBC,
        NoiDungTT,
        NoiDung,
        PopupGuiSms,
        daunoithuebao,
        CheckPort,
        frmPhanAnhBH,
        frmTraCuuPhieuBH
    },
    data() {
        return {
            header: {
                title: 'Cập nhật tiến trình báo hỏng',
                list: [
                    { name: 'Báo hỏng', link: { name: 'Ui.cards' } },
                    { name: 'Cập nhật tiến trình báo hỏng', link: { name: 'Ui.cards' } },
                ]
            },
            ...columns,
            ngay_ht: moment(new Date()).format("DD/MM/yyyy"),
            visible: {
                tsbtnCapNhatMucDo: true,
                tsbtnFileTT: true,
                tsmMoTamNgung: true,
            },
            enable: {
                btnNhapMoi: true,
                tsbtnCapNhatMucDo: true,
            },
            VNPT_NET_UPLOAD_FILE_TIEN_TRINH: -1,
            ds_tientrinhxuly: [],
            ds_phieuyeucau: [],
            ds_phieuyeucau_selected: [],
            pycSelected: {},
            ds_donvi: [],
            fields: '',
            donvi_id: null,
            ds_loainhom: [],
            loainhom_id: null,
            ds_dichvuvt: [],
            dichvuvt_id: null,
            ds_trangthai: [],
            trangthai_id: null,
            ds_loaihd: [],
            loaihd_id: null,
            dtpNgayBH1: null,
            dtpNgayBH2: null,
            dtpNgayNT1: null,
            dtpNgayNT2: null,
            txtMaGD: "",
            txtMaTB: "",
            txtKieuLD: "",
            txtTenTB: "",
            txtDiaChiLD: "",
            txtSoAo: "",
            txtGhiChu: "",
            cbxNgayBH: false,
            cbxNgayNT: false,
            ndtt_vtinh: null,
            ndtt_tag: "",
            ndtt_vtientrinhbh_id: null,
            ndtt_vnhom_id: null,
            ndtt_vbaohong_id: null,
            ndtt_vnoidung_goc: "",
            ndtt_vnd_baohong: "",
            nd_tientrinhbh_id: null,
            smsProp: {
                nhomId: null,
                baoHongId: 0,
                maTB: '',
                noiDung: '',
                tienTrinhBHId: null,
            },
            position: { X: 'center', Y: 'top' },
            frmPhanAnhBHInput: {},

            columnsTTXL: [
                { fieldName: "nguoi_cn", headerText: "Người CN", textAlign: "left", width: "200px" },
                { fieldName: "ngay_cn", headerText: "Ngày CN", textAlign: "left", autoFit: "true" },
                { fieldName: "noidung", headerText: "Nội dung", textAlign: "left", autoFit: "true" },
                { fieldName: "donvi_cn", headerText: "Đơn vị", textAlign: "left", autoFit: "true" },
                { fieldName: "", headerText: "SMS", allowHtml: true, textAlign: 'center', width: "111px",
                    template: function () {
                        return {
                            template: Vue.component('smsTemplate', {
                                template: `
                                    <button class="btn btn-main" @click="smsClick">SMS</button>
                                `,
                                data () {
                                    return {data: {}}
                                },
                                computed: {
                                    parent () {
                                        return this.$parent.$parent.$parent
                                    }
                                },
                                methods: {
                                    smsClick() {
                                        this.parent.gridTTXL_onSMS(this.data)
                                    },
                                }
                            })
                        }
                    }
                },
                { fieldName: "", headerText: "Sửa", allowHtml: true, textAlign: 'center', width: "111px",
                    template: function () {
                        return {
                            template: Vue.component('suaTemplate', {
                                template: `
                                    <button class="btn btn-main" @click="suaClick">Sửa</button>
                                `,
                                data () {
                                    return {data: {}}
                                },
                                computed: {
                                    parent () {
                                        return this.$parent.$parent.$parent
                                    }
                                },
                                methods: {
                                    suaClick() {
                                        this.parent.gridTTXL_onSua(this.data)
                                    },
                                }
                            })
                        }
                    }
                },
                { fieldName: "", headerText: "Xóa", allowHtml: true, textAlign: 'center', width: "111px",
                    template: function () {
                        return {
                            template: Vue.component('xoaTemplate', {
                                template: `
                                    <button class="btn btn-main" @click="xoaClick">Xóa</button>
                                `,
                                data () {
                                    return {data: {}}
                                },
                                computed: {
                                    parent () {
                                        return this.$parent.$parent.$parent
                                    }
                                },
                                methods: {
                                    xoaClick() {
                                        this.parent.gridTTXL_onXoa(this.data)
                                    },
                                }
                            })
                        }
                    }
                },
            ],
            checkPortDialog: {
                isVisiable: false,
                input: {
                    vma_tb: null,
                    vthuebao_id: null,
                    vkieu: null,
                }
            },
            animationSettings: { effect: 'None' },
        }
    },
    watch: {
        cbxNgayBH(val) {
            if(val == false) {
                this.dtpNgayBH1 = null
                this.dtpNgayBH2 = null
                this.cbxNgayNT = true
            } else {
                let today = new Date()
                this.dtpNgayBH1 = moment(today).format("DD/MM/yyyy")
                this.dtpNgayBH2 = moment(new Date(today.getFullYear(), today.getMonth() + 1, 0)).format("DD/MM/yyyy")
            }
        },
        cbxNgayNT(val) {
             if(val == false) {
                this.dtpNgayNT1 = null
                this.dtpNgayNT2 = null
                this.cbxNgayBH = true
            } else {
                let today = new Date()
                this.dtpNgayNT1 = moment(today).format("DD/MM/yyyy")
                this.dtpNgayNT2 = moment(new Date(today.getFullYear(), today.getMonth() + 1, 0)).format("DD/MM/yyyy")
            }
        },
    },
    mounted() {
        //a hieu bao mac dinh chon checkbox
        this.cbxNgayBH = true
        this.init()
    },
    methods: {
        async init() {
            try {
                this.loading(true)
                await this.frmTienTrinhBH_v2_Load()
            } catch (e) {
                console.log(e)
                this.$toast.error("Xảy ra lỗi: " + e)
            } finally {
                this.loading(false)
            }
        },
        async createTree() {
            try {
                await this.NAP_TVW_DONVI();
                let lvtDonVi = this.createTreeDonVi(this.ds_donvi);
                lvtDonVi[0].expanded = true
                this.fields = { dataSource: lvtDonVi, id: 'donvi_id', text: 'ten_dv', parentID: 'donvi_cha_id', hasChildren: 'hasChild' }
            } catch (e){
                console.log(e)
            }
        },
        async selectDonViFocusedNodeChange (node) {
            this.donvi_id = Number(node.nodeData.id)
        },
        createTreeDonVi(lvtDonVi){
            let tree = []
            let lvtChaArray = lvtDonVi.map(item => (item['donvi_cha_id']))
            lvtDonVi.forEach(item => {
                if (lvtChaArray.includes(item['donvi_id'])){
                    tree.push( {...item, hasChild:true, expanded: false})
                } else {
                    tree.push(item)
                }
            })
            return tree;
        },
        async NAP_TVW_DONVI() {
            this.ds_donvi = []
            try {
                let params = {
                    "vnhanvien_id": this.$root.token.getNhanVienID()
                }
                let response = await api.ur_41005_001_nap_tvw_donvi(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    this.ds_donvi = response.data.data
                }
            } catch(e) {
                console.log(e);
                this.$toast.error(e)
            }
        },
        async NAP_CBO_LOAI_NHOM() {
            this.ds_loainhom = []
            this.loainhom_id = null
            try {
                let response = await api.ur_41005_002_sp_ht_loainhom(this.axios);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    this.ds_loainhom = response.data.data
                    if (this.ds_loainhom.length > 0) {
                        this.loainhom_id = this.ds_loainhom[0].nhom_id
                    }
                }
            } catch(e) {
                console.log(e);
                this.$toast.error(e)
            }
        },
        async NAP_CBO_DICHVU_VT() {
            this.ds_dichvuvt = []
            this.dichvuvt_id = null
            try {
                let response = await api.ur_41005_003_CSS_DICHVU_VT(this.axios);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    this.ds_dichvuvt = response.data.data
                    if (this.ds_dichvuvt.length > 0) {
                        this.dichvuvt_id = this.ds_dichvuvt[0].DICHVUVT_ID
                    }
                }
            } catch(e) {
                console.log(e);
                this.$toast.error(e)
            }
        },
        async NAP_CBO_TRANGTHAI() {
            this.ds_trangthai = []
            this.trangthai_id = null
            try {
                let response = await api.ur_41005_004_sp_ht_trangthaibh(this.axios);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    this.ds_trangthai = response.data.data
                    if (this.ds_trangthai.length > 0) {
                        this.trangthai_id = this.ds_trangthai[0].ttbh_id
                    }
                }
            } catch(e) {
                console.log(e);
                this.$toast.error(e)
            }
        },
        async LAY_DS_THAMSO_MD() {
            let result = []
            try {
                let params = {
                    "p_param": "",
                    "p_type": 1
                }
                let response = await api.sp_tt_thamso_md(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }
            } catch(e) {
                console.log(e);
                this.$toast.error(e)
            }
            return result
        },
        async frmTienTrinhBH_v2_Load() {
            this.visible.tsbtnCapNhatMucDo = (this.$root.token.getMaTinh() == "HPG");
            await this.createTree();
            $("#treeview").find('.e-level-1').eq(0).addClass('e-active e-node-focus');
            this.donvi_id = (this.ds_donvi != null && this.ds_donvi.length > 0) ? this.ds_donvi[0].donvi_id : null

            await this.NAP_CBO_LOAI_NHOM();
            await this.NAP_CBO_DICHVU_VT();
            await this.NAP_CBO_TRANGTHAI();

            let ds_ts = await this.LAY_DS_THAMSO_MD();
            for (let i=0; i<ds_ts.length; i++) {
                if (ds_ts[i].ma_ts == "VNPT_NET_UPLOAD_FILE_TIEN_TRINH") {
                    if (ds_ts[i].ten_ts == "1") {
                        this.VNPT_NET_UPLOAD_FILE_TIEN_TRINH = 1
                    }
                    break
                }
            }

            if (this.VNPT_NET_UPLOAD_FILE_TIEN_TRINH == 1) {
                this.visible.tsbtnFileTT = true;
                let objIndex = this.columnsPYC.findIndex((obj => obj.fieldName == "url_file_tt")); this.columnsPYC[objIndex].visible = true
            } else {
                this.visible.tsbtnFileTT = false;
                let objIndex = this.columnsPYC.findIndex((obj => obj.fieldName == "url_file_tt")); this.columnsPYC[objIndex].visible = false
            }

            let objIndex = this.columnsPYC.findIndex((obj => obj.fieldName == "nhomton")); this.columnsPYC[objIndex].visible = false
        },
        onFilterLoaiNhom(e) {
            var query = new Query();
            query = (e.text !== '') ? query.where('ten_nhom', 'contains', e.text, true) : query;
            e.updateData(this.ds_loainhom, query);
        },
        onFilterLoaiHD(e) {
            var query = new Query();
            query = (e.text !== '') ? query.where('ten_loaihd', 'contains', e.text, true) : query;
            e.updateData(this.ds_loaihd, query);
        },
        onFilterDichVuVT(e) {
            var query = new Query();
            query = (e.text !== '') ? query.where('TEN_DVVT', 'contains', e.text, true) : query;
            e.updateData(this.ds_dichvuvt, query);
        },
        onFilterTrangThai(e) {
            var query = new Query();
            query = (e.text !== '') ? query.where('trangthai_bh', 'contains', e.text, true) : query;
            e.updateData(this.ds_trangthai, query);
        },
        async cboLoaiNhom_Changed() {
            await this.NAP_CBO_LOAI_HD_PL()
        },
        async NAP_CBO_LOAI_HD_PL() {
            this.ds_loaihd = []
            this.loaihd_id = null
            try {
                let params = {
                    vnhom_id: this.loainhom_id
                }
                let response = await api.ur_41005_014_lay_tt_loai_hd_pl(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    this.ds_loaihd = response.data.data
                    if (this.ds_loaihd.length > 0) {
                        this.loaihd_id = this.ds_loaihd[0].loaihd_id
                    }
                }
            } catch(e) {
                console.log(e);
                this.$toast.error(e)
            }
        },
        async btnTimKiem_Click() {
            try {
                this.loading(true)
                await this.NAP_GRD_DANHSACH()
            } catch (e) {
                console.log(e)
            } finally {
                this.loading(false)
            }
        },
        async Lay_ds_phieu_cn_tientrinh_bh(vdichvuvt_id, vdonvi_id, vtrangthaibh_id, vngaybh_tu, vngaybh_den, vngaynt_tu, vngaynt_den) {
            let result = []
            try {
                let params = {
                    "vdichvuvt_id": vdichvuvt_id,
                    "vdonvi_id": vdonvi_id,
                    "vngaybh_den": vngaybh_den,
                    "vngaybh_tu": vngaybh_tu,
                    "vngaynt_den": vngaynt_den,
                    "vngaynt_tu": vngaynt_tu,
                    "vtrangthaibh_id": vtrangthaibh_id
                }
                let response = await api.ur_41005_012_001_lay_ds_phieu_cn_tientrinh_bh(this.axios, params);
                console.log(response)
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }
            } catch(e) {
                console.log(e);
                this.$toast.error(e)
            }
            return result
        },
        async Lay_ds_phieu_cn_tientrinh_bd(vdichvuvt_id, vdonvi_id, vtrangthaibh_id, vngaybh_tu, vngaybh_den, vngaynt_tu, vngaynt_den) {
            let result = []
            try {
                let params = {
                    "vdichvuvt_id": vdichvuvt_id,
                    "vdonvi_id": vdonvi_id,
                    "vngaybh_den": vngaybh_den,
                    "vngaybh_tu": vngaybh_tu,
                    "vngaynt_den": vngaynt_den,
                    "vngaynt_tu": vngaynt_tu,
                    "vtrangthaibh_id": vtrangthaibh_id
                }
                let response = await api.ur_41005_012_002_lay_ds_phieu_cn_tientrinh_bd(this.axios, params);
                console.log(response)
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }
            } catch(e) {
                console.log(e);
                this.$toast.error(e)
            }
            return result
        },
        async lay_tt_hs_tb(vbaohong_id, vthuebao_id) {
            let result = []
            try {
                let params = {
                    vbaohong_id: vbaohong_id,
                    vthuebao_id: vthuebao_id
                }
                let response = await api.ur_41005_012_003_lay_tt_hs_tb(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }
            } catch(e) {
                console.log(e);
                this.$toast.error(e)
            }
            return result
        },
        async NAP_GRD_DANHSACH() {
            try {
                this.pycSelected = null
                this.ds_phieuyeucau = []

                let dts = []
                let vngaybh_1 = document.getElementById("cbxNgayBH").checked ? this.dtpNgayBH1 : ""
                let vngaybh_2 = document.getElementById("cbxNgayBH").checked ? this.dtpNgayBH2 : ""
                let vngaynt_1 = document.getElementById("cbxNgayNT").checked ? this.dtpNgayNT1 : ""
                let vngaynt_2 = document.getElementById("cbxNgayNT").checked ? this.dtpNgayNT2 : ""
                if (this.loainhom_id == "2") //Báo hỏng
                    dts = await this.Lay_ds_phieu_cn_tientrinh_bh(this.dichvuvt_id, this.donvi_id, this.trangthai_id, vngaybh_1, vngaybh_2, vngaynt_1, vngaynt_2);
                else // bảo dưỡng
                    dts = await this.Lay_ds_phieu_cn_tientrinh_bd(this.dichvuvt_id, this.donvi_id, this.trangthai_id, vngaybh_1, vngaybh_2, vngaynt_1, vngaynt_2);

                if (dts.length > 0) {
                    try {
                        if (this.VNPT_NET_UPLOAD_FILE_TIEN_TRINH == 1) {
                            for (let i=0; i<dts.length; i++) {
                                let dtTemp = await this.lay_tt_hs_tb(dts[i].baohong_id, dts[i].thuebao_id)
                                if (dtTemp.length > 0)
                                    dts[i].url_file_tt = dtTemp[0].url;
                            }
                        }
                    } catch(e) {
                        console.log(e)
                    }

                    let objIndex = this.columnsPYC.findIndex((obj => obj.fieldName == "nhomton"));
                    if (dts[0].nhomton != undefined) {
                        this.columnsPYC[objIndex].visible = true
                    } else {
                        this.columnsPYC[objIndex].visible = false
                    }

                    this.ds_phieuyeucau = dts.slice()
                }
            } catch (ex) {
                this.$toast.error(ex);
            }
        },
        async gridPYC_rowSelected(row) {
            this.pycSelected = row.data
            try {
                this.loading(true)
                await this.NAP_GRD_TIENTRINH();
                if (this.$root.token.getMaTinh() == "HPG") {
                    if (row.data.loaitb_id == null) return;

                    if (Number(row.data.loaitb_id) == enums.LoaiHinhTB.INTERNET_MYTV) {
                        this.visible.tsmMoTamNgung = true;
                    } else {
                        this.visible.tsmMoTamNgung = false;
                    }
                } else {
                    this.visible.tsmMoTamNgung = false;
                }
            } catch (e) {
                console.log(e)
            } finally {
                this.loading(false)
            }
        },
        async fn_tt_baohong(type, param) {
            let result = ""
            try {
                let params = {
                    "type": type,
                    "param": param
                }
                let response = await api.fn_tt_baohong(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }
            } catch(e) {
                console.log(e);
            }
            return result
        },
        async fn_tt_db_adsl(type, param) {
            let result = ""
            try {
                let params = {
                    "type": type,
                    "param": param
                }
                let response = await api.fn_tt_db_adsl(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }
            } catch(e) {
                console.log(e);
            }
            return result
        },
        async fn_tt_db_tsl(type, param) {
            let result = ""
            try {
                let params = {
                    "type": type,
                    "param": param
                }
                let response = await api.fn_tt_db_tsl(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }
            } catch(e) {
                console.log(e);
            }
            return result
        },
        async fn_tt_db_cd(type, param) {
            let result = ""
            try {
                let params = {
                    "type": type,
                    "param": param
                }
                let response = await api.fn_tt_db_cd(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }
            } catch(e) {
                console.log(e);
            }
            return result
        },
        async fn_tt_tientrinhsms(type, param) {
            let result = ""
            try {
                let params = {
                    "type": type,
                    "param": param
                }
                let response = await api.fn_tt_tientrinhsms(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }
            } catch(e) {
                console.log(e);
            }
            return result
        },
        async fn_tt_tientrinh_bh(type, param) {
            let result = ""
            try {
                let params = {
                    "type": type,
                    "param": param
                }
                let response = await api.fn_tt_tientrinh_bh(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }
            } catch(e) {
                console.log(e);
            }
            return result
        },
        async fn_tt_baohong_bc(type, param) {
            let result = ""
            try {
                let params = {
                    "type": type,
                    "param": param
                }
                let response = await api.fn_tt_baohong_bc(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }
            } catch(e) {
                console.log(e);
            }
            return result
        },
        async fn_tt_tinh(type, param) {
            let result = ""
            try {
                let params = {
                    "type": type,
                    "param": param
                }
                let response = await api.fn_tt_tinh(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }
            } catch(e) {
                console.log(e);
            }
            return result
        },
        async nap_grd_tientrinh(vbaohong_id, vnhom_id) {
            let result = []
            try {
                let params = {
                    "vbaohong_id": vbaohong_id,
                    "vnhom_id": vnhom_id
                }
                let response = await api.ur_41005_009_002_nap_grd_tientrinh(this.axios, params);
                console.log(response)
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }
            } catch(e) {
                console.log(e);
                this.$toast.error(e)
            }
            return result
        },
        async NAP_GRD_TIENTRINH() {
            if (this.pycSelected == undefined || this.pycSelected == null) {
                this.ds_tientrinhxuly = []
                this.txtMaGD = "";
                this.txtMaTB = "";
                this.txtKieuLD = "";
                this.txtTenTB = "";
                this.txtDiaChiLD = "";
                this.txtSoAo = "";
                this.txtGhiChu = "";

                this.enable.btnNhapMoi = false;
            } else {
                var vbaohong_id = Number(this.pycSelected.baohong_id);
                var vthuebao_id = Number(this.pycSelected.thuebao_id);
                var vdichvuvt_id = Number(this.pycSelected.dichvuvt_id);
                var vtrangthai_id = await this.fn_tt_baohong(4, vbaohong_id);

                this.txtMaTB = this.pycSelected.ma_tb;
                this.txtMaGD = this.pycSelected.ma_gd;
                this.txtKieuLD = this.pycSelected.ten_kieuld;
                this.txtTenTB = this.pycSelected.ten_tb;
                this.txtDiaChiLD = this.pycSelected.diachi_ld;
                this.txtGhiChu = this.pycSelected.ghichu_tb;

                var vdanhba_con = "";
                switch (vdichvuvt_id) {
                    case enums.DichVuVienThong.CO_DINH:
                        vdanhba_con = "db_cd";
                        this.txtSoAo = await this.fn_tt_db_cd(1, vthuebao_id);
                        break;
                    case enums.DichVuVienThong.ADSL:
                    case enums.DichVuVienThong.MEGA_EYES:
                        vdanhba_con = "db_adsl";
                        this.txtSoAo = await this.fn_tt_db_adsl(2, vthuebao_id);
                        break;
                    case enums.DichVuVienThong.TSL:
                        vdanhba_con = "db_tsl";
                        this.txtSoAo = await this.fn_tt_db_tsl(3, vthuebao_id);
                        break;
                }

                this.ds_tientrinhxuly = await this.nap_grd_tientrinh(vbaohong_id, this.loainhom_id)
                if (this.loainhom_id == 2 || this.loainhom_id == 3) {
                    if (this.$root.token.getMaTinh() == "HPG") {
                        if (String(this.pycSelected.ngaygiao_td) != "") {
                            this.enable.tsbtnCapNhatMucDo = (new Date() < moment(String(this.pycSelected.ngaygiao_td), 'YYYY-MM-DD hh:mm:ss'))
                        }
                    }
                }

                this.enable.btnNhapMoi = (vtrangthai_id != "6" && vtrangthai_id != "7");
            }
        },
        strDDMMYYYY_toDate(dtStr) {
            let parts_of_date = dtStr.split("/");
            return new Date(+parts_of_date[2], parts_of_date[1] - 1, +parts_of_date[0]);
        },
        async lay_ds_tientrinh_bh_theo_matb(vdichvuvt_id, vdonvi_id, vtrangthaibh_id, vma_tb, vngaybh_den, vngaybh_tu, vngaynt_den, vngaynt_tu) {
            let result = []
            try {
                let params = {
                    "vdichvuvt_id": vdichvuvt_id,
                    "vdonvi_id": vdonvi_id,
                    "vma_tb": vma_tb,
                    "vngaybh_den": vngaybh_den,
                    "vngaybh_tu": vngaybh_tu,
                    "vngaynt_den": vngaynt_den,
                    "vngaynt_tu": vngaynt_tu,
                    "vtrangthaibh_id": vtrangthaibh_id
                }
                let response = await api.ur_41005_015_lay_ds_tientrinh_bh_theo_matb(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }
            } catch(e) {
                console.log(e);
                this.$toast.error(e)
            }
            return result
        },
        async txtMaTB_KeyPress(event) {
            if (event.keyCode == 13) {
                if (this.txtMaTB.trim() != "") {
                    let matb = this.txtMaTB
                    try {
                        this.loading(true)
                        var dts = []
                        if (this.loainhom_id == 2) { // Báo hỏng
                            let vngaybh_1 = document.getElementById("cbxNgayBH").checked ? this.dtpNgayBH1 : ""
                            let vngaybh_2 = document.getElementById("cbxNgayBH").checked ? this.dtpNgayBH2 : ""
                            let vngaynt_1 = document.getElementById("cbxNgayNT").checked ? this.dtpNgayNT1 : ""
                            let vngaynt_2 = document.getElementById("cbxNgayNT").checked ? this.dtpNgayNT2 : ""
                            dts = await this.lay_ds_tientrinh_bh_theo_matb(this.dichvuvt_id, this.donvi_id, this.trangthai_id, this.txtMaTB.trim(),
                              vngaybh_2,vngaybh_1, vngaynt_2, vngaynt_1);
                        }

                        if (dts.length > 0) {
                            let objIndex = this.columnsPYC.findIndex((obj => obj.fieldName == "nhomton"));
                            if (dts[0].nhomton != undefined) {
                                this.columnsPYC[objIndex].visible = true
                            } else {
                                this.columnsPYC[objIndex].visible = false
                            }

                            this.ds_phieuyeucau = dts.slice()
                        } else {
                            this.ds_phieuyeucau = []
                            this.pycSelected = null
                            this.NAP_GRD_TIENTRINH()
                            this.ds_tientrinhxuly = []
                            this.txtMaTB = matb
                        }
                    } catch (ex) {
                        this.$toast.error(ex);
                    } finally {
                        this.loading(false)
                    }
                }
            }
        },
        gridTTXL_onSMS(data) {
            var bhi = this.pycSelected.baohong_id
            var tti = data.tientrinhbh_id

            if (bhi != null && tti != null) {
                this.smsProp.baoHongId = Number(bhi);
                this.smsProp.nhomId = Number(this.loainhom_id);
                this.smsProp.tienTrinhBHId = Number(tti);
                this.smsProp.maTB = this.txtMaTB;
                this.smsProp.noiDung = this.pycSelected.noidung;
                this.$refs.guiSmsRef.onInit()
                this.$refs.popupGuiSmsRef.show()
            }
        },
        onClosePopupGuiSMS () {
            this.$refs.popupGuiSmsRef.hide()
        },
        gridTTXL_onSua(data) {
            this.nd_tientrinhbh_id = String(data.tientrinhbh_id)
            let nd_txtNoiDung = ""
            if (this.nd_tientrinhbh_id != null && this.nd_tientrinhbh_id != "") {
                nd_txtNoiDung = this.ds_tientrinhxuly.find(x => x.tientrinhbh_id == this.nd_tientrinhbh_id).noidung
            } else {
                if (this.ds_phieuyeucau.length > 0) {
                    nd_txtNoiDung = this.pycSelected.ma_tb + " :   " + this.pycSelected.nhanvien_tc
                } else {
                    nd_txtNoiDung = ""
                }
            }
            this.$refs.dlg_noidung.show()
            this.$refs.ppNoiDung.txtNoiDung = nd_txtNoiDung
        },
        async nd_btnGhiLai_Click(val) {
            this.$refs.dlg_noidung.hide()
            try {
                this.loading(true)
                await this.capnhat_tientrinh_bh(val.tientrinhbh_id, val.noidung)
                await this.NAP_GRD_TIENTRINH()
            } catch (e) {
                console.log(e)
            } finally {
                this.loading(false)
            }
        },
        async capnhat_tientrinh_bh(vtientrinhbh_id, vnoidung) {
            try {
                let params = {
                    "vbaohong_id": this.pycSelected.baohong_id,
                    "vdonvi_id": this.$root.token.getDonViID(),
                    "vip_cn": "127.0.0.1",
                    "vloai_id": vtientrinhbh_id == null ? "1" : "2",
                    "vnguoi_cn": this.$root.token.getUserName(),
                    "vnguoidung_id": this.$root.token.getNguoiDungID(),
                    "vnhanvien_id": this.$root.token.getNhanVienID(),
                    "vnoidung": vnoidung,
                    "vtientrinhbh_id": vtientrinhbh_id
                }
                let response = await api.ur_41005_025_capnhat_tientrinh_bh(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000') {
                    this.$toast.success("Cập nhật thành công")
                } else {
                    this.$toast.error(response.data.message)
                }
            } catch(e) {
                console.log(e);
                this.$toast.error(e)
            }
        },
        async fn_xoa_tientrinh_bh(vbaohong_id, vtientrinhbh_id) {
            try {
                let params = {
                    "vbaohong_id": Number(vbaohong_id),
                    "vtientrinhbh_id": Number(vtientrinhbh_id)
                }
                let response = await api.ur_41005_020_fn_xoa_tientrinh_bh(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000') {
                    this.$toast.success("Xóa thành công")
                } else {
                    this.$toast.error(response.data.message)
                }
            } catch(e) {
                console.log(e);
                this.$toast.error(e)
            }
        },
        async gridTTXL_onXoa(data) {
            let vtientrinhbh_id = data.tientrinhbh_id
            this.$confirm('Bạn có thực sự muốn xóa tiến trình được chọn không ?', {
                    confirmButtonText: 'Có', cancelButtonText: 'Không',
                }).then(async v => {
                    if (v) {
                        try {
                            this.loading(true)
                            var sdt = await this.fn_tt_tientrinhsms(1, vtientrinhbh_id);
                            if (sdt != undefined && sdt != null && sdt != "-1") {
                                this.$toast.error("Bạn không thể xóa tiến trình này\r\nTiến trình đã được gửi tin nhắn đến " + sdt);
                            } else {
                                var vbaohong_id = await this.fn_tt_tientrinh_bh(1, vtientrinhbh_id);
                                await this.fn_xoa_tientrinh_bh(vbaohong_id, vtientrinhbh_id)
                                await this.NAP_GRD_TIENTRINH();
                            }
                        } catch(error) {
                            this.$toast.error(error.message ? error.message : 'Đã có lỗi xảy ra')
                        } finally {
                            this.loading(false)
                        }
                    }
                })
        },
        tsbtnXuatFile_Click() {
            if (this.ds_phieuyeucau.length <= 0) {
                this.$toast.error("Không có dữ liệu. Bạn hãy kiểm tra lại điều kiện lấy báo cáo !");
                return;
            }
            var dsbaoCao = this.ds_phieuyeucau;
            this.$refs.dialogLayDuLieuBC.openDialog(dsbaoCao);
        },
        tsbtnXuatExcel_Click() {
            if (this.ds_phieuyeucau.length <= 0) {
                this.$toast.error("Không có dữ liệu để xuất Excel!")
                return
            }

            try {
                this.loading(true)
                let worksheet = xlsx.utils.json_to_sheet(this.ds_phieuyeucau)
                let workbook = xlsx.utils.book_new()
                xlsx.utils.book_append_sheet(workbook, worksheet, "Sheet1")
                xlsx.writeFile(workbook, "export.xlsx")
            } catch (e) {
                console.log(e)
                this.$toast.error(e)
            } finally {
                this.loading(false)
            }
        },
        async sp_lay_tinh_tc_theo_tinh_id(tinh_id) {
            let result
            try {
                let params = {
                    "tinh_id": tinh_id
                }
                let response = await api.ur_41005_009_003_sp_lay_tinh_tc_theo_tinh_id(this.axios, params);
                console.log(response)
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }
            } catch(e) {
                console.log(e);
                this.$toast.error(e)
            }
            return result
        },
        async btnNhapMoi_Click() {
            if (!this.enable.btnNhapMoi) return
            try {
                this.loading(true)
                if (this.ds_phieuyeucau.length <= 0) {
                    this.$toast.error("Không có dữ liệu trên lưới để nhập tiến trình!");
                    return;
                }

                let vbaohong_id = Number(this.pycSelected.baohong_id);
                let tinh_id_tc = Number(await this.fn_tt_baohong_bc(2, vbaohong_id));
                if (tinh_id_tc != -1) {
                    let tinh_tc = await this.sp_lay_tinh_tc_theo_tinh_id(tinh_id_tc);
                    if (tinh_tc && tinh_tc.length > 0) {
                        this.ndtt_vtinh = tinh_tc[0]
                        vbaohong_id = Number(await this.fn_tt_baohong_bc(3, vbaohong_id));
                    } else {
                        this.ndtt_vtinh = {tinh_id: 0}
                    }
                } else {
                    this.ndtt_vtinh = {tinh_id: 0}
                }

                this.ndtt_tag = "1";
                this.ndtt_vtientrinhbh_id = null;
                this.ndtt_vnhom_id = Number(this.loainhom_id);
                this.ndtt_vbaohong_id = Number(vbaohong_id);

                if (this.ds_phieuyeucau.length > 0) {
                    this.ndtt_vnoidung_goc = this.checkUndefined(this.pycSelected.ma_tb) + " :   " + this.checkUndefined(this.pycSelected.nhanvien_tc);
                    try {
                        this.ndtt_vnd_baohong = "VNPT " + this.$root.token.getMaTinh() + ":[" + this.checkUndefined(this.pycSelected.ma_tb) + ";;" +
                            this.checkUndefined(this.pycSelected.ltb) + "]:" + this.checkUndefined(this.pycSelected.ma_loaikh) + ","
                            + this.checkUndefined(this.pycSelected.ngay_yc) + "," + this.checkUndefined(this.pycSelected.ten_kh) + ",LH:"
                            + this.checkUndefined(this.pycSelected.so_dt) + "," + this.checkUndefined(this.pycSelected.diachi_ld) + "";
                    } catch (e) {
                        this.ndtt_vnd_baohong = this.checkUndefined(this.pycSelected.ma_tb) + " :   " + this.checkUndefined(this.pycSelected.nhanvien_tc);
                    }
                } else {
                    this.ndtt_vnd_baohong = "";
                    this.ndtt_vnoidung_goc = "";
                }

                this.$refs.dlg_noidungtt.show()
                this.$refs.ppNoiDungTT.frmNoiDungTT_Load()
            } catch (e) {
                console.log(e)
                this.$toast.error(e)
            } finally {
                this.loading(false)
            }
        },
        checkUndefined(s) {
            if (s == undefined || s == null) return ""
            return s.trim()
        },
        async ndtt_btnGhiLai_Click() {
            this.$refs.dlg_noidungtt.hide()
            await this.NAP_GRD_TIENTRINH();
        },
        async capnhat_ngaygiao_td(vbaohong_id, vngaydieu, vmucdo_id) {
            try {
                let params = {
                    "vbaohong_id": vbaohong_id,
                    "vmucdo_id": vmucdo_id,
                    "vngaydieu": moment(vngaydieu, "DD/MM/YYYY").format("DD/MM/YYYY HH:mm:ss")
                }
                let response = await api.ur_41005_026_capnhat_ngaygiao_td(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000') {
                    this.$toast.success("Cập nhật thành công");
                } else {
                    this.$toast.error(response.data.message)
                }
            } catch(e) {
                console.log(e);
                this.$toast.error(e)
            }
        },
        async tsbtnCapNhatMucDo_Click() {
            if (!this.enable.tsbtnCapNhatMucDo) return
            if (this.ds_phieuyeucau.length <= 0) {
                this.$toast.error("Bạn phải chọn phiếu để xử lý")
                return;
            }

            try {
                this.loading(true)
                var vbaohong_id = Number(this.pycSelected.baohong_id);
                await this.capnhat_ngaygiao_td(vbaohong_id, this.ngay_ht, 2);
            } catch (e) {
                console.log(e)
                this.$toast.error(e)
            } finally {
                this.loading(false)
            }
        },
        async tsbtnFileNT_Click() {
            if (this.ds_phieuyeucau.length > 0 && this.pycSelected != null) {
                document.getElementById("upload-file").click()
            }
        },
        async upFile() {
            let formData = new FormData();
            for (var file of this.$refs.files.files) {
                formData.append('files', file);
            }
            this.loading(true);
            UploadEProfileAPI.upload_file_hoso(this.axios,formData,{ headers: { 'Content-Type': 'multipart/form-data' } }).then((response) => {
                this.loading(false);
                if(response.data.error_code && response.data.error_code === "BSS-00000000") {
                    let txtDuongDan = response.data.data.join('|');
                    this.capnhat_file_tientrinh_bh(this.pycSelected.baohong_id, this.$refs.files.files[0].name, this.txtMaTB,
                                        this.pycSelected.thuebao_id, txtDuongDan)
                }
            }).catch(function(){
                console.log('File upload failed! ');
            });
        },
        async capnhat_file_tientrinh_bh(p_baohong_id, p_file_name, p_ma_tb, p_thuebao_id, p_url) {
            try {
                let params = {
                    "p_baohong_id": p_baohong_id,
                    "p_file_name": p_file_name,
                    "p_ma_tb": p_ma_tb,
                    "p_may_cn": this.$root.token.getUserName(),
                    "p_nguoi_cn": this.$root.token.getUserName(),
                    "p_thuebao_id": p_thuebao_id,
                    "p_url": p_url
                }
                let response = await api.ur_41005_030_capnhat_file_tientrinh_bh(this.axios, params);
                console.log(response)
                if (response && response.data && response.data.error_code === 'BSS-00000000') {
                    this.$toast.success("Cập nhật thành công");
                    await this.NAP_GRD_DANHSACH()
                } else {
                    this.$toast.error(response.data.message)
                }
            } catch(e) {
                console.log(e);
                this.$toast.error(e)
            }
        },
        gridPYC_selectedItemsChanged(val) {
            this.ds_phieuyeucau_selected = [];
            for (let index in val) {
                for (let ix in this.ds_phieuyeucau) {
                    if (val[index].ma_tb == this.ds_phieuyeucau[ix].ma_tb) {
                        this.ds_phieuyeucau_selected.push(val[index]);
                    }
                }
            }
        },
        async getPortStatus(input, linetestPassword, linetestUser, mac) {
            let result = ""
            try {
                let params = {
                    "input": input,
                    "linetestPassword": linetestPassword,
                    "linetestUser": linetestUser,
                    "mac": mac
                }
                let response = await api.ur_41005_000_getPortStatus(this.axios, params);
                if (response && response.data && response.data.errorCode == 0) {
                    result = response.data.data
                }
            } catch(e) {
                console.log(e);
            }
            return result
        },
        async tsbtnTestAllPort_Click() {
            if (this.ds_phieuyeucau_selected.length == 0) {
                this.$toast.error("Bạn chưa chọn thuê bao.");
                return;
            }

            try {
                this.loading(true)
                let dsTemp = this.ds_phieuyeucau.slice()
                let vuser_alt = await this.fn_tt_tinh(2, this.$root.token.getPhanVungID());
                let vpass_alt = await this.fn_tt_tinh(3, this.$root.token.getPhanVungID());
                for (let i = 0; i < this.ds_phieuyeucau_selected.length; i++) {
                    try {
                        let index = dsTemp.findIndex((obj => obj.ma_tb == this.ds_phieuyeucau_selected[i].ma_tb));
                        let vInput = String(this.ds_phieuyeucau_selected[i].ma_tb)
                        let vdichvuvtID = Number(this.ds_phieuyeucau_selected[i].dichvuvt_id);
                        dsTemp[index].tt_port = "..."

                        if (vdichvuvtID != enums.DichVuVienThong.ADSL) {
                            dsTemp[index].tt_port = ""
                        } else {
                            dsTemp[index].tt_port = await this.getPortStatus(vInput, vpass_alt, vuser_alt, 0)
                        }
                    } catch (e) {
                        console.log(e)
                    }
                }

                this.ds_phieuyeucau = dsTemp.slice()
            } catch (e) {
                console.log(e)
            } finally {
                this.loading(false)
            }
        },
        tsbtnGianDo_Click() {
            let thongTinDauNoi = {
                thueBaoId: Number(this.pycSelected.thuebao_id),
                kieu: 0
            }
            this.$refs.daunoithuebao.setData(thongTinDauNoi)
            this.$refs.daunoithuebao.show()
        },
        showPort() {
            if (this.ds_phieuyeucau.length <= 0) return;
            let inthuebao_id = Number(this.pycSelected.thuebao_id);

            if (inthuebao_id == 0)  {
                this.$toast.error("Bạn hãy chọn thuê bao !");
                return;
            }
            this.checkPortDialog.input.vma_tb = this.txtMaTB.trim()
            this.checkPortDialog.input.vthuebao_id = inthuebao_id
            this.checkPortDialog.input.vkieu = 0
            this.checkPortOnClick()
        },
        tsbKHGoi_Click() {
            let vbaohong_id = String(this.pycSelected.baohong_id)
            let vthuebao_id = Number(this.pycSelected.thuebao_id);
            if (vbaohong_id != "") {
                this.frmPhanAnhBHInput.vbaohong_id_t = vbaohong_id
                this.frmPhanAnhBHInput.thuebao_id = vthuebao_id
                this.$refs.dialogfrmPhanAnhBH.openDialog()
            } else {
                this.$toast.error("Chưa có thông tin báo hỏng!");
            }
        },
        async tsbtnChiTiet_Click() {
            try {
                this.loading(true)
                let vma_tb = String(this.pycSelected.ma_tb)
                let thuebao_id = Number(this.pycSelected.thuebao_id);
                this.$refs.dlg_tracuuphieubh.show()
                await this.$refs.frmTraCuuPhieuBH.HienThi_LichSu_BaoHong(vma_tb, thuebao_id, "", 0)
            } finally {
                this.loading(false)
            }
        },
        async checkPortOnClick() {
            this.checkPortDialog.isVisiable = true
            this.$refs.checkPort.show()
        },
        async checkPortOnClose() {
            this.checkPortDialog.isVisiable = false
        },
    },
}
</script>
<style>
    .non-ative a {
        color: #d3d3d3 !important;
    }
    .popup-show-port .page-content {
        position: inherit;
    }

    .popup-show-port .breadcrumb-top {
        display: none;
    }

    .popup-ttpbh .page-content {
        position: inherit;
        top: 20px !important;
    }

    .popup-ttpbh .breadcrumb-top {
        display: none;
    }

    .popup-ttpbh .main-wrapper {
        position: relative !important;
        top: 0px !important;
    }

    .thanhtoan-modal .page-content {
        position: unset !important;
    }

    .thanhtoan-modal .main-wrapper {
        position: unset !important;
    }
</style>
