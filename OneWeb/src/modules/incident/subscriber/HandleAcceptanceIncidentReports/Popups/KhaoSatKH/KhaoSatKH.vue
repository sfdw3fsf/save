<template src="./KhaoSatKH.html"></template>
<script>
import breadcrumb from '@/components/breadcrumb'
import api from "./KhaoSatKH.js";
import Split from '@/modules/contract/setup/HandoverCompleteProfile/components/split.js'
import { Query } from '@syncfusion/ej2-data'
import Vue from 'vue';
import { DropDownListPlugin } from "@/components/duybn_components/Dropdowns/ej2-vue-dropdowns";
Vue.use(DropDownListPlugin);
import DataGridCustom from "@/modules/admin/category/EmployeeAssignmentCriteria/components/DataGrid";
import columns from "./columns.js";
import moment from "moment";
import LichSu_HenBH from '../../Popups/LichSu_HenBH/LichSu_HenBH.vue'
import LichSu_BaoHong from '../../Popups/LichSu_BaoHong/LichSu_BaoHong.vue'
import TraCuuTBKS from '../../Popups/TraCuuTBKS/TraCuuTBKS.vue'

export default {
    name:'KhaoSatKH',
    props:{
        _ma_tb: {
            type: String,
            default: ""
        },
        vmophieu: {
            type: Number,
            default: 0
        },
        vkieu_ks: {
            type: Number,
            default: -1
        },
        giaophieu_ks_id: {
            type: Number,
            default: 0
        },
        sua_phieu: {
            type: Boolean,
            default: false
        }
    },
    components:{
        breadcrumb,
        DataGridCustom,
        LichSu_HenBH,
        LichSu_BaoHong,
        TraCuuTBKS
    },
    data() {
        return {
            header: {
                title: 'Khảo sát khách hàng báo hỏng',
                list: [
                    { name: 'Báo hỏng', link: { name: 'Ui.cards' } },
                    { name: 'Khảo sát khách hàng báo hỏng', link: { name: 'Ui.cards' } },
                ]
            },
            ...columns,
            ngay_cn: moment(new Date()).format("DD/MM/yyyy"),
            enable: {
                tsbtnDieuLai: true,
                tsbtnGhiLai: true,
                cboLyDoKTL: false,
                chkHenKS_Tu: false,
                chkHenKS_Den: false,
                dtpHenKS_Tu: false,
                dtpHenKS_Den: false,
            },
            visible: {
                tsbtnDieuLai: true,
                tsbtnBatDau: false,
                btnHuyBo: false,
                tsbtnDieuXN: false,
                tsbtnLogEditKS: false,
                tsbtnSMS: false,
                lblTimeBD: false,
                lblCKCL: false,
                lblMangVTLT: false,
                lblThongBaoCoHen: false,
            },
            lan_ks_toida: 4,
            TS_LAN_KS_TOIDA: -1,
            BATBUOC_TRALOI_ALL_CAUHOI: -1,
            KIEMTRA_CAUHOI_KHL: -1,
            FIMS_DIEU_XN_DONVI: -1,
            OUTBOUND_KHONG_DIEULAI: -1,
            OB_KHONGTL_KTC1: -1,
            KHONGTL_CHUYEN_OB_TUDONG: -1,
            OB_CHON_NHIEU_LYDO_KTL: -1,
            CHOPHEP_SUA_OB_KHONG_TL: -1,
            kt_dambao_ckcl_kh: false,
            baohong_ckcl: false,
            lblMangVTLT: "Phiếu có chuyển lên NET/MEDIA",
            isNew: false,
            vdacapnhat: false,
            Loai_nv_ks: 6,   // Loai_NV.THICONG_DAYMAY = 6
            ds_lydoktl: [],
            lydoktl_id: null,
            dtThuebao: [],
            dt: [],
            dtTBTL: [], 
            dtCTTBTL: [],
            tbtl_id: null, 
            thuebao_id: null, 
            baohong_id: null, 
            hdkh_id: null, 
            txtMaTB: "",
            txtTenTB: "",
            txtDiaChiLD: "",
            txtDienThoaiLH: "",
            txtDienThoaiBH : "",
            txtSoLH: "",
            txtTenNguoiTL: "",
            txtLoaiHinh: "",
            txtTBCungCap: "",
            txtNgayBH: "",
            txtNguoiBH: "",
            txtSoDTBH: "",
            txtNgayNT: "",
            txtTinhTrang: "",
            txtNguyenNhan: "",
            txtGhiChuNT: "",
            txtVPXL: "",
            txtGhiChu: "",
            dtpHenKS_Tu: null,
            dtpHenKS_Den: null,
            lbLanHong: 0,
            ds_lanhoi: [],
            ngay_ks: null,
            ngay_bd_ks: null,
            vNGUOI_CN: "",
            vMAY_CN: null,
            vNHANVIEN_ID: null,
            vNGUOIDUNG_ID: null,
            vIP_CN: "",
            giuphieu: false,
            dsTb: [],
            dsCT: [],
            isDieuLai: false,
            isDieuLai_XN: false,
            dsCauHoiDXL: [],
            dsTraLoi: [],
            DieuLH_CD: false,
            ds_hen_khaosat: [],
        }
    },
    methods: {
        async init(baohong_id, thuebao_id, tbtl_id, hdkh_id) {
            try {
                Split(['boxLeftKSKH','boxRightKSKH'], {
                    sizes: [50, 50],
                    gutterSize: 16,
                    onDragEnd: function (sizes) {

                    },
                })
                this.loading(true)
                this.baohong_id = baohong_id
                this.thuebao_id = thuebao_id
                this.tbtl_id = tbtl_id
                this.hdkh_id = hdkh_id
                this.refresh()
                await this.frmKhaoSatKH_Load()
            } catch (e) {
                console.log(e)
                this.$toast.error(e)
            } finally {                
                this.loading(false)
            }
        },
        refresh() {
            this.txtMaTB = ""
            this.txtTenTB = ""
            this.txtDiaChiLD = ""
            this.txtDienThoaiLH = ""
            this.txtDienThoaiBH = ""
            this.txtSoLH = ""
            this.txtTenNguoiTL = ""
            this.txtLoaiHinh = ""
            this.txtNgayBH = ""
            this.txtNguoiBH = ""
            this.txtSoDTBH = ""
            this.txtNgayNT = ""     
            this.txtTinhTrang = ""
            this.txtNguyenNhan = ""
            this.txtGhiChuNT = ""
            this.txtVPXL = ""
            this.txtGhiChu = ""
            this.lbLanHong = 0
            this.ds_lanhoi = []
        },
        async LAY_DS_THAMSO_MD() {
            let result = []
            try {
                let params = {
                    "ma_ts": ""
                }
                let response = await api.ur_41003_5_001_lay_ds_thamso_md_mats(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async HT_LYDO_KTL_Combobox() {
            try {
                let response = await api.ur_41003_5_002_sp_ht_lydo_ktl(this.axios);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    this.ds_lydoktl = response.data.data
                    if (this.ds_lydoktl.length > 0) {
                        this.lydoktl_id = this.ds_lydoktl[0].lydoktl_id
                    }
                }            
            } catch (e) {
                console.log(e)
            }
        },
        async LAY_TT_BAOHONG(baohong_id) {
            let result = []
            try {
                let params = {
                    "baohong_id": baohong_id
                }
                let response = await api.ur_41003_5_003_01_lay_tt_baohong_theo_baohong_id(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async Lay_tocdo_loaihinh(p_dichvuvt_id, p_ma_tb) {
            let result = ""
            try {
                let params = {
                    "p_dichvuvt_id": p_dichvuvt_id,
                    "p_ma_tb": p_ma_tb
                }
                let response = await api.ur_41003_5_003_02_fn_lay_tocdo_loaihinh(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async lanhong_trong30ngay(vthuebao_id, vngay) {
            let result = ""
            try {
                let params = {
                    "vthuebao_id": vthuebao_id,
                    "vngay": "01/01/2022"
                }
                let response = await api.ur_41003_5_003_03_solan_bh_trong_thirtyngay(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async Lay_lichsu_hen_baohong(baohong_id) {
            let result = []
            try {
                let params = {
                    "baohong_id": baohong_id
                }
                let response = await api.ur_41003_5_003_04_lay_lichsu_hen_baohong(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async LanGoi_ChuaTraLoi(p_hdkh_id, p_baohong_id) {
            let result = []
            try {
                let params = {
                    "p_hdkh_id": p_hdkh_id,
                    "p_baohong_id": p_baohong_id
                }
                let response = await api.ur_41003_5_003_05_langoi_chuatraloi(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async TB_TRALOI_THEO_TBTL_ID(tbtl_id) {
            let result = []
            try {
                let params = {
                    "tbtl_id": tbtl_id
                }
                let response = await api.ur_41003_5_003_06_sp_tb_traloi_theo_tbtl_id(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async TB_TRALOI_THEO_BAOHONG_ID(baohong_id) {
            let result = []
            try {
                let params = {
                    "baohong_id": baohong_id
                }
                let response = await api.ur_41003_5_003_07_sp_tb_traloi_theo_baohong_id(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async LAY_DS_CAUHOI_THEOLOAINV(vnhanvien_id, vhinhthucks_id) {
            let result = []
            try {
                let params = {
                    "vnhanvien_id": vnhanvien_id,
                    "vhinhthucks_id": vhinhthucks_id
                }
                let response = await api.ur_41003_5_004_01_lay_ds_cauhoi_theoloainv(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async CT_TBTRALOI(tbtl_id) {
            let result = []
            try {
                let params = {
                    "tbtl_id": tbtl_id
                }
                let response = await api.ur_41003_5_004_02_sp_ct_tbtraloi(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async sp_capnhat_giaophieu_ks_tmp(giaophieu_ks_id) {
            try {
                let params = {
                    "giaophieu_ks_id": giaophieu_ks_id
                }
                let response = await api.ur_41003_5_000_sp_capnhat_giaophieu_ks_tmp(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000') {
                    
                } else {
                    this.$toast.error(response.data.message)
                }     
            } catch (e) {
                console.log(e)
            }
        },    
        async HienThiThongTinTB() {
            this.dtThuebao = await this.LAY_TT_BAOHONG(this.baohong_id);
            if (this.dtThuebao.length > 0) {
                // Thông tin thuê bao
                this.baohong_id = Number(this.dtThuebao[0].baohong_id);
                this.thuebao_id = Number(this.dtThuebao[0].thuebao_id);
                this.txtMaTB = this.dtThuebao[0].ma_tb;
                this.txtTenTB = this.dtThuebao[0].ten_tb;
                this.txtDiaChiLD = this.dtThuebao[0].diachi_ld;
                this.txtDienThoaiLH = this.dtThuebao[0].so_dt_kh;
                this.txtDienThoaiBH = this.dtThuebao[0].dienthoai_bh;

                if (this.dtThuebao[0].lblMangVT != undefined) {
                    if (this.dtThuebao[0].lblMangVT != null && this.dtThuebao[0].lblMangVT != "") {
                        this.lblMangVTLT = this.dtThuebao[0].lblMangVT;
                        this.visible.lblMangVTLT = true;
                    } else {
                        this.visible.lblMangVTLT = false;
                        this.lblMangVTLT = "";
                    }
                }

                if (this.$root.token.getMaTinh() != "HPG") {
                    this.txtSoLH = this.dtThuebao[0].dienthoai_lh;
                    this.txtTenNguoiTL = this.dtThuebao[0].ten_tb;
                }
                this.txtLoaiHinh = await this.Lay_tocdo_loaihinh(this.dtThuebao[0].dichvuvt_id, this.dtThuebao[0].ma_tb);
                // End thông tin thuê bao

                // Thông tin báo hỏng
                this.txtNgayBH = this.dtThuebao[0].ngay_bh;
                this.txtNguoiBH = this.dtThuebao[0].nguoi_bh;
                this.txtSoDTBH = this.dtThuebao[0].dienthoai_lh;
                this.txtNgayNT = this.dtThuebao[0].ngay_ht       
                this.txtTinhTrang = this.dtThuebao[0].tinhtrang;
                this.txtNguyenNhan = this.dtThuebao[0].nguyennhan;
                this.txtGhiChuNT = this.dtThuebao[0].ghichu_xl;
                this.txtVPXL = this.dtThuebao[0].vpxl;
                this.lbLanHong = await this.lanhong_trong30ngay(this.thuebao_id);

                let dsHen = await this.Lay_lichsu_hen_baohong(this.baohong_id);
                if (dsHen != null && dsHen.length > 0) {
                    this.visible.lblThongBaoCoHen = true;
                } else {
                    this.visible.lblThongBaoCoHen = false;
                }

                this.ds_lanhoi = await this.LanGoi_ChuaTraLoi(this.hdkh_id, this.baohong_id);
                //

                if (this.sua_phieu)
                    this.dtTBTL = await this.TB_TRALOI_THEO_TBTL_ID(this.tbtl_id);
                else
                    this.dtTBTL = await this.TB_TRALOI_THEO_BAOHONG_ID(this.baohong_id);

                let dtTBTL_OB = []
                this.dtTBTL.forEach((item) => {
                    if (item.may_cn == "Auto OB") {
                        dtTBTL_OB.push(item)
                    }
                })

                if (dtTBTL_OB.length > 0) {
                    let dr = dtTBTL_OB[0];
                    this.isNew = false;

                    if (this.tbtl_id == 0) {
                        this.tbtl_id = Number(dr.tbtl_id);
                    }
                        
                    this.txtTenNguoiTL = dr.nguoi_tl;
                    this.txtSoLH = dr.dienthoai_lh;
                    this.txtGhiChu = dr.ghichu;

                    $("#chkKTL").prop("checked", (dr.trangthai == "0"))
                    this.ngay_ks = dr.ngay_hoi

                    if (document.getElementById("chkKTL").checked) {
                        this.isNew = true;

                        if (this.CHOPHEP_SUA_OB_KHONG_TL == -1)
                            this.tbtl_id = 0;

                        if (String(dr.lydoktl_id) != "") {
                            this.lydoktl_id = String(dr.lydoktl_id)
                        }
                    }

                    this.ngay_bd_ks = dr.ngay_bd
                    if (dr.nguoi_cn != null && dr.nguoi_cn != "")
                        this.vNGUOI_CN = dr.nguoi_cn;

                    if (dr.may_cn != null && dr.may_cn != "")
                        this.vMAY_CN = dr.may_cn;

                    if (dr.nhanvien_id != null && dr.nhanvien_id != "")
                        this.vNHANVIEN_ID = Number(dr.nhanvien_id);

                    if (dr.nguoidung_id != null && dr.nguoidung_id != "")
                        this.vNGUOIDUNG_ID = Number(dr.nguoidung_id);

                    if (dr.ip_cn != null && dr.ip_cn != "")
                        this.vIP_CN = dr.ip_cn;
                } else {
                    this.isNew = true;
                    this.tbtl_id = 0;
                }
            }
        },
        async HienThiCH() {
            this.dt = await this.LAY_DS_CAUHOI_THEOLOAINV(this.Loai_nv_ks, 2);
            $("#htmlDSCauHoi").html(this.KhoiTaoCauHoi())

            var radios = document.querySelectorAll('input[type=radio]');
            Array.prototype.forEach.call(radios, function(radio) {
                radio.addEventListener('change', checkRadioTL);
            });

            if (!this.isNew) {
                this.dtCTTBTL = await this.CT_TBTRALOI(this.tbtl_id);
                this.dtCTTBTL.forEach((item) => {
                    let idTL = "#checktl_" + item.cauhoi_id + "_" + item.traloi_id
                    $(idTL).prop("checked", true)
                })
            }
                
        },
        async frmKhaoSatKH_Load() {
            try {
                let ds_kt1 = await this.LAY_DS_THAMSO_MD();
                this.lblMangVTLT = "";
                if (ds_kt1.length > 0) {
                    ds_kt1.forEach((item) => {
                        if (item.ma_ts == "BATBUOC_TRALOI_ALL_CAUHOI" && item.ten_ts == "1") {
                            this.BATBUOC_TRALOI_ALL_CAUHOI = 1;
                        }
                        if (item.ma_ts == "KIEMTRA_CAUHOI_KHL" && item.ten_ts == "1") {
                            this.KIEMTRA_CAUHOI_KHL = 1;
                        }
                        if (item.ma_ts == "FIMS_DIEU_XN_DONVI" && item.ten_ts == "1") {
                            this.FIMS_DIEU_XN_DONVI = 1;
                        }
                        if (item.ma_ts == "OUTBOUND_KHONG_DIEULAI" && item.ten_ts == "1") {
                            this.OUTBOUND_KHONG_DIEULAI = 1;
                            this.visible.tsbtnDieuLai = false;
                        }
                        if (item.ma_ts == "OB_KHONGTL_KTC1" && item.ten_ts == "1") {
                            this.OB_KHONGTL_KTC1 = 1;
                        }
                        if (item.ma_ts == "KHONGTL_CHUYEN_OB_TUDONG" && item.ten_ts == "1") {
                            this.KHONGTL_CHUYEN_OB_TUDONG = 1;
                        }
                        if (item.ma_ts == "OB_CHON_NHIEU_LYDO_KTL" && item.ten_ts == "1") {
                            this.OB_CHON_NHIEU_LYDO_KTL = 1;
                        }
                        if (item.ma_ts == "CHOPHEP_SUA_OB_KHONG_TL" && item.ten_ts == "1") {
                            this.CHOPHEP_SUA_OB_KHONG_TL = 1;
                        }
                        if (item.ma_ts == "LAN_KS_TOIDA" && item.ten_ts != "0" && item.ten_ts != "-1") {
                            this.TS_LAN_KS_TOIDA = 1;
                            this.lan_ks_toida = Number(item.ten_ts);
                        }
                        if (item.ma_ts == "KT_DAMBAO_CKCL_KH" && item.ten_ts == "1") {
                            this.kt_dambao_ckcl_kh = true;
                        }
                    })
                }

                if (this.FIMS_DIEU_XN_DONVI == 1)
                    this.visible.tsbtnDieuXN = true;

                await this.HT_LYDO_KTL_Combobox();
                $("#txtTenNguoiTL").focus();
                await this.HienThiThongTinTB();
                await this.HienThiCH();
                this.enable.tsbtnDieuLai = this.isNew;

                if (this.vkieu_ks == 2) {
                    await this.sp_capnhat_giaophieu_ks_tmp(this.giaophieu_ks_id)
                }

                if (this.$root.token.getMaTinh() == "HCM") {
                    this.visible.tsbtnLogEditKS = true;
                    this.visible.tsbtnSMS = true;
                    if (this.isNew || (!this.isNew && document.getElementById("chkKTL").checked)) {
                        this.visible.tsbtnBatDau = true;
                        this.enable.tsbtnGhiLai = false;
                        this.visible.lblTimeBD = true;

                        await this.tsbtnBatDau_Click()
                    }
                }
                if (this.kt_dambao_ckcl_kh) {
                    let kq = this.kiemtra_phieu_baohong_ckcl(this.baohong_id);
                    if (kq == "1") {
                        this.baohong_ckcl = true;
                        this.visible.lblCKCL = true;
                    } else {
                        this.baohong_ckcl = false;
                        this.visible.lblCKCL = false;
                    }
                }
            } catch (ex) {
                this.$toast.error(ex);
            }
        },
        kiemtra_phieu_baohong_ckcl(baohong_id) {
            // DB khong co ham css.camket_chatluong.kiemtra_phieu_baohong_ckcl => Khong con su dung
            return "-1"
        },
        cboLyDoKTL_EditValueChanged() {
            if (Number(this.lydoktl_id) == 20) {  // FIMS_LYDO_KTL.KHACHHANG_HEN = 20
                this.enable.chkHenKS_Tu = true;
                this.enable.chkHenKS_Den = true;
            } else {
                this.enable.chkHenKS_Tu = false;
                this.enable.chkHenKS_Den = false;
                $("#chkHenKS_Tu").prop("checked", false);
                $("#chkHenKS_Den").prop("checked", false);
                this.enable.dtpHenKS_Tu = false;
                this.enable.dtpHenKS_Den = false;
            }
        },
        cboLyDoKTL_onFilter(e) {
            var query = new Query();
            query = (e.text !== '') ? query.where('lydo_ktl', 'contains', e.text, true) : query;
            e.updateData(this.ds_lydoktl, query);
        },
        checkBox1_CheckedChanged() {
            this.enable.cboLyDoKTL = document.getElementById("chkKTL").checked;
            if (!document.getElementById("chkKTL").checked) {
                this.enable.chkHenKS_Tu = false;
                this.enable.chkHenKS_Den = false;
                $("#chkHenKS_Tu").prop("checked", false);
                $("#chkHenKS_Den").prop("checked", false);
                this.enable.dtpHenKS_Tu = false;
                this.enable.dtpHenKS_Den = false;
            } else {
                if (Number(this.lydoktl_id) == 20) {  // FIMS_LYDO_KTL.KHACHHANG_HEN = 20
                    this.enable.chkHenKS_Tu = true;
                    this.enable.chkHenKS_Den = true;
                }
            }
        },
        chkHenKS_Tu_CheckedChanged() {
            this.enable.dtpHenKS_Tu = document.getElementById("chkHenKS_Tu").checked;
        },
        chkHenKS_Den_CheckedChanged() {
            this.enable.dtpHenKS_Den = document.getElementById("chkHenKS_Den").checked;
        },
        KhoiTaoCauHoi() {
            let result = ""

            // Group lai thanh cac object cau hoi
            let dsGroup = []        

            let dsCl = this.dt.slice()
            let dsCauhoiID = []
            let cauhoi_id, noidung, thutu, kieu, loaich_id, rch_id
            while (true) {
                let check = false
                if (dsCauhoiID.length == 0) {  // cau hoi dau tien
                    check = true
                    cauhoi_id = dsCl[0].cauhoi_id
                    noidung = dsCl[0].noidung
                    thutu = dsCl[0].thutu
                    kieu = dsCl[0].kieu
                    loaich_id = dsCl[0].loaich_id
                    rch_id = dsCl[0].rch_id
                    dsCauhoiID.push(cauhoi_id)
                } else {
                    // tim xem con cauhoi_id nao chua duoc xu ly
                    for (let i=0; i<dsCl.length; i++) {
                        if (dsCauhoiID.includes(dsCl[i].cauhoi_id)) continue

                        check = true
                        cauhoi_id = dsCl[i].cauhoi_id
                        noidung = dsCl[i].noidung
                        thutu = dsCl[i].thutu
                        kieu = dsCl[i].kieu
                        loaich_id = dsCl[i].loaich_id
                        rch_id = dsCl[i].rch_id
                        dsCauhoiID.push(cauhoi_id)
                        break
                    }
                }

                if (check) {
                    // them vao mang tra ve
                    let cauhoi = {}
                    cauhoi.id = cauhoi_id
                    cauhoi.ds = []
                    
                    for(let i=0; i<dsCl.length; i++) {
                        let g = dsCl[i]
                        if (g.cauhoi_id == cauhoi_id && g.noidung == noidung && g.thutu == thutu && 
                            g.kieu == kieu && g.loaich_id == loaich_id && g.rch_id == rch_id) {
                                cauhoi.ds.push(g)
                            }
                    }
                        
                    dsGroup.push(cauhoi)
                } else {
                    break
                }
            }

            dsGroup.sort((a, b) => a.id > b.id ? 1 : -1);
            console.log(dsGroup)
            this.dsCauHoiDXL = dsGroup.slice()

            for (let i=0; i<dsGroup.length; i++) {
                let ttch = i+1
                result += "<div class='question-item marb20'>"
                result +=     "<div class='title-bg-main'>"
                result +=         "<span class='title underline'>Câu hỏi " + ttch + ":</span>"
                result +=     "</div>"
                result +=     "<p class='title'>" + dsGroup[i].ds[0].noidung + "</p>"
                result +=     "<div class='list-check-ver'>"
                dsGroup[i].ds.sort((a, b) => a.thutu_tl > b.thutu_tl ? 1 : -1);
                for (let j=0; j<dsGroup[i].ds.length; j++) {
                    result +=     "<div class='item marb6'>"
                    result +=        "<div class='check-action'>"

                    if (dsGroup[i].ds[j].loaich_id == 1)
                    result +=            "<input type='radio' class='check' id='checktl_" + dsGroup[i].id + "_" + dsGroup[i].ds[j].traloi_id + "'>"
                    else
                    result +=            "<input type='checkbox' class='check' id='checktl_" + dsGroup[i].id + "_" + dsGroup[i].ds[j].traloi_id + "'>"

                    result +=            "<span class='name'>" + dsGroup[i].ds[j].noidung_tl + "</span>"
                    result +=        "</div>"
                    result +=     "</div>"
                }
                result +=     "</div>"
                result += "</div>"
            }

            return result
        },
        async KIEMTRA_GIUPHIEU() {
            try {
                let kt = await this.kiemtra_xacnhan_ks(this.giaophieu_ks_id, 0, this.baohong_id, "BH")
                if (kt != "1") {
                    this.$toast.error(kt);
                    return false;
                }

                this.giuphieu = true;
                return true;
            } catch (ex) {
                this.$toast.error("Có lôi khi bắt đâu khảo sát " + ex);
                return false;
            }
        },
        async tsbtnBatDau_Click() {
            if (await this.KIEMTRA_GIUPHIEU()) {
                this.enable.tsbtnGhiLai = true;
                this.visible.btnHuyBo = true;
                this.visible.tsbtnBatDau = false;
                this.ngay_bd_ks = this.ngay_cn
                this.lblTimeBD = "TG Bắt đầu KS: " + this.ngay_bd_ks;
            }
        },
        async kiemtra_xacnhan_ks(vgiaophieu_ks_id, vkieu, vid, vkieuphieu) {
            let result = ""
            try {
                let params = {
                    "vgiaophieu_ks_id": vgiaophieu_ks_id,
                    "vkieu": vkieu,
                    "vid": vid,
                    "vkieuphieu": vkieuphieu
                }
                let response = await api.ur_41003_5_006_kiemtra_xacnhan_ks(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }   
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async HUYPHIEU(thongbao) {
            let loi = false
            try {
                let kt = await this.kiemtra_xacnhan_ks(this.giaophieu_ks_id, 1, this.baohong_id, "BH")
                if (kt == "1") {
                    if (thongbao == 1) {
                        this.$toast.success("Huỷ giữ phiếu khảo sát thành công");
                    }
                } else {
                    if (thongbao == 1) {
                        this.$toast.error(kt);
                    }
                    loi = true;
                }
            } catch (ex) {
                if (thongbao == 1) {
                    this.$toast.error("Có lỗi khi huỷ giữ phiếu khảo sát " + ex);
                }
                loi = true;
            }
            return loi
        },
        async btnHuyBo_Click() {
            let loi = await this.HUYPHIEU(1);
            if (!loi) {
                this.visible.tsbtnBatDau = true;
                this.visible.btnHuyBo = false;
                this.ngay_bd_ks = null;
                this.lblTimeBD = "TG Bắt đầu KS: ";
            }
        },
        tsbtnDieuXN_Click() {

        },
        tsbtnLogEditKS_Click() {

        },
        tsbtnSMS_Click() {

        },
        async fn_tt_tb_traloi(type, param) {
            let result = ""
            try {
                let params = {
                    "type": type,
                    "param": param
                }
                let response = await api.fn_tt_tb_traloi(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }   
            } catch (e) {
                console.log(e)
            }
            return result
        },
        Log_EditKhaoSatBH() {
            // Khong su dung voi HPG
        },
        AddLog_EditKS() {
            // Khong su dung voi HPG
        },
        async Kiemtra_ct_traloi_hl_khl(vtbtl_id, vkieu) {
            let result = ""
            try {
                let params = {
                    "vtbtl_id": vtbtl_id,
                    "vkieu": vkieu,
                }
                let response = await api.ur_41003_5_009_08_kiemtra_ct_traloi_hl_khl(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }   
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async Chapnhan_phieu_khaosat(vgiaophieu_ks_id, vnhanvien_id) {
            try {
                let params = {
                    "vgiaophieu_ks_id": vgiaophieu_ks_id,
                    "vnhanvien_id": vnhanvien_id,
                }
                let response = await api.ur_41003_5_010_09_chapnhan_phieu_khaosat(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000') {
                    
                }   
            } catch (e) {
                console.log(e)
            }
        },
        async TaoDuLieu_Hen_KS() {
            this.ds_hen_khaosat = []
            if (document.getElementById("chkHenKS_Tu").checked || document.getElementById("chkHenKS_Den").checked) {
                let rowHen = {}

                rowHen.HENKS_ID = await this.GetKey("HENKS_ID");
                rowHen.GIAOPHIEU_KS_ID = this.giaophieu_ks_id;

                rowHen.HDKH_ID = 0;
                rowHen.HDTB_ID = 0;
                rowHen.BAOHONG_ID = this.baohong_id;

                if (document.getElementById("chkHenKS_Tu").checked)
                    rowHen.GIOHEN_TU = this.dtpHenKS_Tu;

                if (document.getElementById("chkHenKS_Den").checked)
                    rowHen.GIOHEN_DEN = this.dtpHenKS_Den;

                rowHen.NHANVIEN_ID = this.$root.token.getNhanVienID()
                rowHen.GHICHU = this.txtGhiChu;
                rowHen.KIEU = 2;//Hẹn khi GDV khảo sát

                rowHen.MAY_CN = "TODO_MAYCN"
                rowHen.NGAY_CN = moment(new Date()).format("DD/MM/yyyy HH:mm:ss")
                rowHen.NGUOI_CN = this.$root.token.getUserName()
                rowHen.IP_CN = "TODO_IPCN"

                this.ds_hen_khaosat.push(rowHen);
            }
        },
        async TaoDuLieu_Hen_KS(so_gio) {
            this.ds_hen_khaosat = []
            if (so_gio != -1) {
                let rowHen = {}

                rowHen.HENKS_ID = await this.GetKey("HENKS_ID");
                rowHen.GIAOPHIEU_KS_ID = this.giaophieu_ks_id;

                rowHen.HDKH_ID = 0;
                rowHen.HDTB_ID = 0;
                rowHen.BAOHONG_ID = this.baohong_id;

                if (so_gio != -1) {
                    var dtx = new Date();
                    dtx.setHours(dtx.getHours() + so_gio);
                    rowHen.GIOHEN_TU = moment(dtx).format("DD/MM/yyyy HH:mm:ss")
                }
                    
                rowHen.NHANVIEN_ID = this.$root.token.getNhanVienID()
                rowHen.GHICHU = this.txtGhiChu;
                rowHen.KIEU = 2;//Hẹn khi GDV khảo sát

                rowHen.MAY_CN = "TODO_MAYCN"
                rowHen.NGAY_CN = moment(new Date()).format("DD/MM/yyyy HH:mm:ss")
                rowHen.NGUOI_CN = this.$root.token.getUserName()
                rowHen.IP_CN = "TODO_IPCN"

                this.ds_hen_khaosat.push(rowHen);
            }
        },
        async Insert_ds_henkhaosat(js_ds_hen_khaosat) {
            try {
                let params = {
                    "js_ds_hen_khaosat": JSON.stringify(js_ds_hen_khaosat)
                }
                let response = await api.ur_41003_5_010_11_sp_insert_hen_khaosat(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000') {
                    
                }   
            } catch (e) {
                console.log(e)
            }
        },
        async sp_capnhat_lanks_baohong(baohong_id) {
            try {
                let params = {
                    "baohong_id": baohong_id
                }
                let response = await api.ur_41003_5_010_12_sp_capnhat_lanks_baohong(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000') {
                    
                }   
            } catch (e) {
                console.log(e)
            }
        },
        async Kiemtra_ct_traloi_khong_goilai(tbtl_id) {
            let result = ""
            try {
                let params = {
                    "tbtl_id": tbtl_id
                }
                let response = await api.ur_41003_5_010_13_kiemtra_ct_traloi_khong_goilai(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }   
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async sp_capnhat_hen_ks(giaophieu_ks_id, baohong_id, trangthai, lydoktl_id, ngayhen_tu, ngayhen_den, giuphieu) {
            try {
                let params = {
                    giaophieu_ks_id: giaophieu_ks_id,
                    baohong_id: baohong_id,
                    trangthai: trangthai,
                    lydoktl_id: lydoktl_id,
                    ngayhen_tu: ngayhen_tu,
                    ngayhen_den: ngayhen_den,
                    giuphieu: giuphieu,
                }
                let response = await api.ur_41003_5_010_14_sp_capnhat_hen_ks(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000') {
                    
                }   
            } catch (e) {
                console.log(e)
            }
        },
        async Capnhat_nguoinhan_phieu_cohen(giaophieu_ks_id, id, loai) {
            try {
                let params = {
                    giaophieu_ks_id: giaophieu_ks_id,
                    id: id,
                    loai: loai
                }
                let response = await api.ur_41003_5_010_15_capnhat_nguoinhan_phieu_cohen(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000') {
                    
                }   
            } catch (e) {
                console.log(e)
            }
        },
        async sp_capnhat_ttks_tb_traloi(ttks_id, tbtl_id) {
            try {
                let params = {
                    ttks_id: ttks_id,
                    tbtl_id: tbtl_id
                }
                let response = await api.ur_41003_5_010_16_sp_capnhat_ttks_tb_traloi(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000') {
                    
                }   
            } catch (e) {
                console.log(e)
            }
        },
        async sp_capnhat_hen_tb_traloi(ngayhen_tu, ngayhen_den, tbtl_id) {
            try {
                let params = {
                    p_ngay_henks_tu: ngayhen_tu,
                    p_ngay_henks_den: ngayhen_den,
                    p_tbtl_id: tbtl_id
                }
                let response = await api.ur_41003_5_010_17_sp_capnhat_hen_tb_traloi(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000') {
                    
                }   
            } catch (e) {
                console.log(e)
            }
        },
        async sp_capnhat_ngay_nhanvien_ks_tmp(p_nhanvien_id) {
            try {
                let params = {
                    p_nhanvien_id: p_nhanvien_id
                }
                let response = await api.ur_41003_5_010_18_sp_capnhat_ngay_nhanvien_ks_tmp(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000') {
                    
                }   
            } catch (e) {
                console.log(e)
            }
        },
        async nhantin_khl(vtbtl_id) {
            try {
                let params = {
                    vtbtl_id: vtbtl_id
                }
                let response = await api.ur_41003_5_010_19_nhantin_khl(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000') {
                    
                }   
            } catch (e) {
                console.log(e)
            }
        },
        async sp_tao_hd_thongtin_lh(tbtl_id) {
            let result = ""
            try {
                let params = {
                    tbtl_id: tbtl_id
                }
                let response = await api.ur_41003_5_010_20_sp_tao_hd_thongtin_lh(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }   
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async dexuat_bsc_khl_nvkt(tbtl_id) {
            try {
                let params = {
                    vtbtl_id: tbtl_id
                }
                let response = await api.ur_41003_5_010_21_dexuat_bsc_khl_nvkt(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000') {
                    
                }   
            } catch (e) {
                console.log(e)
            }
        },
        async Capnhat_phieu_dieulai_xn(vtbtl_id, vnhanvien_id, vdonvi_id) {
            try {
                let params = {
                    vtbtl_id: vtbtl_id,
                    vnhanvien_id: vnhanvien_id,
                    vdonvi_id: vdonvi_id
                }
                let response = await api.ur_41003_5_010_22_capnhat_phieu_dieulai_xn(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000') {
                    
                }   
            } catch (e) {
                console.log(e)
            }
        },
        async Dieu_phieu_xn(vtbtl_id, vhdtb_id) {
            let result = ""
            try {
                let params = {
                    vtbtl_id: vtbtl_id,
                    vhdtb_id: vhdtb_id
                }
                let response = await api.ur_41003_5_010_23_dieu_phieu_xn(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }   
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async tsbtnGhiLai_Click() {
            let vsLog = "";
            try {
                this.loading(true)
                if ((this.isNew && !this.vdacapnhat) || this.vmophieu == 1) {
                    let isContinue = true
                    try {
                        let strKT = await this.fn_tt_tb_traloi(1, this.baohong_id);
                        if (strKT == "1") {
                            this.$bvModal.msgBoxConfirm("Thuê bao " + this.txtMaTB + " đã khảo sát rồi. Bạn có chắc chắn muốn ghi lại?", {
                                title: '', size: 'sm', okTitle: 'Đồng ý', cancelTitle: 'Hủy',
                            }).then(async v => {
                                if (!v) {
                                    isContinue = false
                                }
                            })
                        }
                    } catch (e) {

                    }
                    if (!isContinue) return

                    this.vdacapnhat = false;
                    if (await this.KiemTra_DuLieu(true)) {
                        if (this.isDieuLai) {
                            this.$bvModal.msgBoxConfirm("Cập nhật nội dung này sẽ điều phiếu hỏng cho thuê bao " + this.txtMaTB + ". Bạn có chắc chắn muốn điều?", {
                                title: '', size: 'sm', okTitle: 'Đồng ý', cancelTitle: 'Hủy',
                            }).then(async v => {
                                if (!v) {
                                    isContinue = false
                                }
                            })
                        }
                        if (!isContinue) return

                        await this.TaoDuLieu(true, false);

                        //log tạo mới khảo sát
                        if (this.$root.token.getMaTinh() == "HCM") vsLog = this.Log_EditKhaoSatBH();

                        await this.Insert_dsTb(this.dsTb)
                        await this.Delete(this.tbtl_id)
                        await this.Insert_dsCT(this.dsCT)

                        if (this.OB_KHONGTL_KTC1 == 1 && await this.Kiemtra_ct_traloi_hl_khl(this.tbtl_id, 1) == "1") {
                            this.$toast.error("Bạn vừa chọn trả lời Hài lòng, vừa chọn trả lời Không hài lòng. Hãy kiểm tra lại!");
                            this.vdacapnhat = false;
                            return;
                        }

                        if (this.vkieu_ks == 2) { //Giao tự động -> insert vào bảng chính
                            await this.Chapnhan_phieu_khaosat(this.giaophieu_ks_id, this.$root.token.getNhanVienID());
                        }

                        if (document.getElementById("chkKTL").checked && Number(this.lydoktl_id) == 20  // FIMS_LYDO_KTL.KHACHHANG_HEN
                                && (document.getElementById("chkHenKS_Tu").checked || document.getElementById("chkHenKS_Den").checked)) {
                            await this.TaoDuLieu_Hen_KS();
                            await this.Insert_ds_henkhaosat(this.ds_hen_khaosat);
                        }

                        //Khách hàng không bắt máy, máy bận - 1h sau gọi lại
                        if (document.getElementById("chkKTL").checked && Number(this.lydoktl_id) == 21) {  // FIMS_LYDO_KTL.MAY_BAN = 21
                            await this.TaoDuLieu_Hen_KS(1);
                            await this.Insert_ds_henkhaosat(this.ds_hen_khaosat);
                        }

                        //Không xác nhận dịch vụ - 24h sau gọi lại
                        if (document.getElementById("chkKTL").checked && Number(this.lydoktl_id) == 22) {  // FIMS_LYDO_KTL.KHONG_XACNHAN_DICHVU = 22
                            await this.TaoDuLieu_Hen_KS(24);
                            await this.Insert_ds_henkhaosat(this.ds_hen_khaosat);
                        }

                        await this.sp_capnhat_lanks_baohong(this.baohong_id)

                        if (this.giaophieu_ks_id > 0) {
                            let tt = "6";
                            if (document.getElementById("chkKTL").checked)
                                tt = "3";

                            if (this.OB_KHONGTL_KTC1 == 1 && await this.Kiemtra_ct_traloi_khong_goilai(this.tbtl_id) == "1")
                                tt = "6";//HCM - vẫn tính là đã khảo sát

                            await this.sp_capnhat_hen_ks(this.giaophieu_ks_id, this.baohong_id, tt, Number(this.lydoktl_id), this.dtpHenKS_Tu,
                                            this.dtpHenKS_Den, this.giuphieu)

                            if (document.getElementById("chkKTL").checked && this.KHONGTL_CHUYEN_OB_TUDONG == 1) {
                                await this.Capnhat_nguoinhan_phieu_cohen(this.giaophieu_ks_id, this.baohong_id, 2);
                            }

                            if (this.OB_KHONGTL_KTC1 == 1 && await this.Kiemtra_ct_traloi_khong_goilai(this.tbtl_id) == "1") {
                                await this.sp_capnhat_ttks_tb_traloi(6, this.tbtl_id)
                            }
                        } else {
                            if (document.getElementById("chkKTL").checked && Number(this.lydoktl_id) == 20    // FIMS_LYDO_KTL.KHACHHANG_HEN
                                && (document.getElementById("chkHenKS_Tu").checked || document.getElementById("chkHenKS_Den").checked)) {
                                await this.sp_capnhat_hen_tb_traloi(this.dtpHenKS_Tu, this.dtpHenKS_Den, this.tbtl_id)
                            }
                        }

                        try {
                            await this.sp_capnhat_ngay_nhanvien_ks_tmp(this.$root.token.getNhanVienID())
                        } catch (e) { }

                        this.ds_lanhoi = await this.LanGoi_ChuaTraLoi(this.hdkh_id, this.baohong_id);

                        // Nhắn tin không hài Lòng. đạt tham số mặc định là NHANTIN_KHL với giá trị = 1
                        let kt = await this.sp_lay_gt_thamso_md_tents("NHANTIN_KHL", "1")
                        if (kt.length > 0) {
                            await this.nhantin_khl(this.tbtl_id);
                        }

                        kt = await this.sp_lay_gt_thamso_md_tents("CAPNHAT_SO_DT_OUTBOUND", "1")
                        if (kt.length > 0) {
                            if (!document.getElementById("chkKTL").checked) {
                                if (this.txtSoLH.length == 10) {
                                    this.$bvModal.msgBoxConfirm("Bạn đã liên hệ được với khách hàng. Bạn có muốn cập nhật số điện thoại: " + this.txtSoLH + " cho thuê bao " + this.txtMaTB, {
                                        title: '', size: 'sm', okTitle: 'Đồng ý', cancelTitle: 'Hủy',
                                    }).then(async v => {
                                        if (v) {
                                            await this.capnhat_so_dt_khi_outbound(this.thuebao_id, this.txtSoLH)
                                        }
                                    })
                                }

                                if (this.DieuLH_CD) {
                                    let s = await this.sp_tao_hd_thongtin_lh(this.tbtl_id);
                                    if (s == "1")
                                        this.$toast.success("Điều phiếu thu thập thông tin liên hệ thành công!");
                                    else
                                        this.$toast.error("Điều phiếu thu thập thông tin liên hệ không thành công: " + s);
                                }
                            }
                        }
                    }
                    else return;
                }
                else {
                    if (await this.KiemTra_DuLieu(false)) {
                        let isContinue = true
                        await this.TaoDuLieu(false, false);
                        if (this.isDieuLai) {
                            this.$bvModal.msgBoxConfirm("Cập nhật nội dung này sẽ điều phiếu hỏng cho thuê bao " + this.txtMaTB + ". Bạn có chắc chắn muốn điều?", {
                                title: '', size: 'sm', okTitle: 'Đồng ý', cancelTitle: 'Hủy',
                            }).then(async v => {
                                if (!v) {
                                    isContinue = false
                                }
                            })
                        }

                        if (!isContinue) return

                        //lấy các thay đổi
                        if (this.$root.token.getMaTinh() == "HCM") vsLog = this.Log_EditKhaoSatBH();

                        await this.Insert_dsTb(this.dsTb)
                        await this.Delete(this.tbtl_id)
                        await this.Insert_dsCT(this.dsCT)

                        if (this.OB_KHONGTL_KTC1 == 1 && await this.Kiemtra_ct_traloi_hl_khl(tbtl_id, 2) == "1") {
                            this.$toast.error("Bạn vừa chọn trả lời Hài lòng, vừa chọn trả lời Không hài lòng. Hãy kiểm tra lại!");
                            return;
                        }

                        if (document.getElementById("chkKTL").checked && Number(this.lydoktl_id) == 20 // FIMS_LYDO_KTL.KHACHHANG_HEN = 20
                                && (document.getElementById("chkHenKS_Tu").checked || document.getElementById("chkHenKS_Den").checked)) {
                            await this.TaoDuLieu_Hen_KS();
                            await this.Insert_ds_henkhaosat(this.ds_hen_khaosat);
                        }

                        if (document.getElementById("chkKTL").checked && Number(this.lydoktl_id) == 21) {   // FIMS_LYDO_KTL.MAY_BAN = 21
                            await this.TaoDuLieu_Hen_KS(1);
                            await this.Insert_ds_henkhaosat(this.ds_hen_khaosat);
                        }

                        if (document.getElementById("chkKTL").checked && Number(this.lydoktl_id) == 22) { // FIMS_LYDO_KTL.KHONG_XACNHAN_DICHVU = 22
                            await this.TaoDuLieu_Hen_KS(24);
                            await this.Insert_ds_henkhaosat(this.ds_hen_khaosat);
                        }


                        if (!this.sua_phieu) {
                            await this.sp_capnhat_lanks_baohong(this.baohong_id)
                        }

                        if (this.giaophieu_ks_id > 0) {
                            let tt = "6";
                            if (document.getElementById("chkKTL").checked)
                                tt = "3";

                            if (this.OB_KHONGTL_KTC1 == 1 && await this.Kiemtra_ct_traloi_khong_goilai(this.tbtl_id) == "1")
                                tt = "6";//HCM - vẫn tính là đã khảo sát

                            await this.sp_capnhat_hen_ks(this.giaophieu_ks_id, this.baohong_id, tt, Number(this.lydoktl_id), this.dtpHenKS_Tu,
                                            this.dtpHenKS_Den, this.giuphieu)

                            if (document.getElementById("chkKTL").checked && this.KHONGTL_CHUYEN_OB_TUDONG == 1) {
                                await this.Capnhat_nguoinhan_phieu_cohen(this.giaophieu_ks_id, this.baohong_id, 2);
                            }

                            if (this.OB_KHONGTL_KTC1 == 1 && await this.Kiemtra_ct_traloi_khong_goilai(this.tbtl_id) == "1") {
                                await this.sp_capnhat_ttks_tb_traloi(6, this.tbtl_id)
                            }
                        }
                        else {
                            if (document.getElementById("chkKTL").checked && Number(this.lydoktl_id) == 20    // FIMS_LYDO_KTL.KHACHHANG_HEN
                                && (document.getElementById("chkHenKS_Tu").checked || document.getElementById("chkHenKS_Den").checked)) {
                                await this.sp_capnhat_hen_tb_traloi(this.dtpHenKS_Tu, this.dtpHenKS_Den, this.tbtl_id)
                            }
                        }

                        try {
                            await this.sp_capnhat_ngay_nhanvien_ks_tmp(this.$root.token.getNhanVienID())
                        } catch (e) { }
                    }
                    else return;
                }

                // Gửi đề xuất phạt chất lượng lên iBSC khi kết quả khảo sát là KHL do NVKT
                let kt1 = await this.sp_lay_gt_thamso_md_tents("DEXUAT_BSC_KHL_NVKT", "1")
                if (kt1.length > 0)
                    await this.dexuat_bsc_khl_nvkt(this.tbtl_id);
                    
                //ghi log
                if (this.$root.token.getMaTinh() == "HCM") this.AddLog_EditKS(vsLog);
                this.$toast.success("Cập nhật thành công!");

                if (this.isDieuLai) {
                    await this.DIEUHONGLAI();
                }
                if (this.isDieuLai_XN) {
                    await this.DIEULAI_XN();
                }

                await this.Capnhat_phieu_dieulai_xn(this.tbtl_id, this.$root.token.getNhanVienID(), this.$root.token.getDonViID());

            } catch (ex) {
                this.$toast.error("Có lỗi: " + ex);
            } finally {
                this.loading(false)
            }
        },
        async DIEULAI_XN() {
            try {
                let s = await this.Dieu_phieu_xn(this.tbtl_id, 0); 
                if (s.toLowerCase() == "ok") {
                    this.$toast.success("Cập nhật điều phiếu xác nhận chất lượng NVKT thành công!");
                } else {
                    this.$toast.error("Có lỗi xảy ra trong quá trình điều hỏng: " + s);
                }
            } catch (ex) {
                this.$toast.error(ex);
            }
        },
        async KiemTraDieuHongLai(vbaohong_id, vhdtb_id) {
            let result = ""
            try {
                let params = {
                    "vbaohong_id": vbaohong_id,
                    "vhdtb_id": vhdtb_id
                }
                let response = await api.ur_41003_5_012_fn_kiemtradieuhonglai(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }   
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async lay_ds_cauhoi_phu(rch_id) {
            let result = []
            try {
                let params = {
                    "rch_id": rch_id
                }
                let response = await api.ur_41003_5_007_01_lay_ds_cauhoi_phu(this.axios, params);
                console.log(response)
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }   
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async Kiemtra_cauhoi_hailong(rch_id) {
            let result = ""
            try {
                let params = {
                    "rch_id": rch_id
                }
                let response = await api.ur_41003_5_007_02_kiemtra_cauhoi_hailong(this.axios, params);
                console.log(response)
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }   
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async sp_ds_tb_traloi(traloi_id) {
            let result = []
            try {
                let params = {
                    "traloi_id": traloi_id
                }
                let response = await api.ur_41003_5_007_03_sp_ds_tb_traloi(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }   
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async sp_lay_ds_nhom_cauhoi_bh(nhom_ch) {
            let result = []
            try {
                let params = {
                    "nhom_ch": nhom_ch
                }
                let response = await api.ur_41003_5_007_04_sp_lay_ds_nhom_cauhoi_bh(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }   
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async kiemtra_dieukien_khaosat(vcauhoi, vcauhoi_p) {
            let result = ""
            try {
                let params = {
                    "vcauhoi": vcauhoi,
                    "vcauhoi_p": vcauhoi_p
                }
                let response = await api.ur_41003_5_007_06_kiemtra_dieukien_khaosat(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                } else {
                    result = response.data.message
                }
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async kiemtra_cauhoi_khl(vrch_id, vkieu) {
            let result = ""
            try {
                let params = {
                    "vrch_id": vrch_id,
                    "vkieu": vkieu
                }
                let response = await api.ur_41003_5_007_07_kiemtra_cauhoi_khl(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                } else {
                    result = response.data.message
                }
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async Kiemtra_nhanvien_sua_khaosat(vtbtl_id, vgiaophieu_ks_id, vkieu) {
            let result = ""
            try {
                let params = {
                    "vtbtl_id": vtbtl_id,
                    "vgiaophieu_ks_id": vgiaophieu_ks_id,
                    "vkieu": vkieu
                }
                let response = await api.ur_41003_5_008_09_Kiemtra_nhanvien_sua_khaosat(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                } else {
                    result = response.data.message
                }
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async sp_lay_tb_traloi_nd(vtbtl_id, vma_nd) {
            let result = []
            try {
                let params = {
                    "vtbtl_id": vtbtl_id,
                    "vma_nd": vma_nd
                }
                let response = await api.ur_41003_5_008_10_sp_lay_tb_traloi_nd(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                } else {
                    result = response.data.message
                }
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async fn_quyen_sua_khaosat(vtbtl_id, vgiaophieu_ks_id, vloai_phieu) {
            let result = []
            try {
                let params = {
                    "vtbtl_id": vtbtl_id,
                    "vgiaophieu_ks_id": vgiaophieu_ks_id,
                    "vloai_phieu": vloai_phieu
                }
                let response = await api.ur_41003_5_008_11_fn_quyen_sua_khaosat(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                } else {
                    result = response.data.message
                }
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async kiemtra_phieu_quagio(giaophieu_ks_id) {
            let result = []
            try {
                let params = {
                    "giaophieu_ks_id": giaophieu_ks_id
                }
                let response = await api.ur_41003_5_008_12_kiemtra_phieu_quagio(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                } else {
                    result = response.data.message
                }
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async sp_lay_thuebao_cd(p_thuebao) {
            let result = []
            try {
                let params = {
                    "p_thuebao": p_thuebao,
                    "p_kieu": p_kieu
                }
                let response = await api.ur_41003_5_008_13_sp_lay_thuebao_cd(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                } else {
                    result = response.data.message
                }
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async KiemTra_DuLieu(themmoi) {
            if (this.txtTenNguoiTL == "" && !document.getElementById("chkKTL").checked) {
                this.$toast.error("Chưa nhập tên người trả lời !");
                $("#txtTenNguoiTL").focus()
                return false;
            }
            if (this.txtSoLH == "") {
                this.$toast.error("Chưa nhập điện thoại liên hệ !");
                $("#txtDienThoaiLH").focus()
                return false;
            }
            if (isNaN(this.txtSoLH.trim())) {
                this.$toast.error("Số điện thoại liên hệ của khách hàng phải là kiểu số!");
                $("#txtSoLH").focus()
                return false;
            }

            if (document.getElementById("chkKTL").checked && Number(this.lydoktl_id) == 20) { // FIMS_LYDO_KTL.KHACHHANG_HEN = 20
                if (!document.getElementById("chkHenKS_Tu").checked && !document.getElementById("chkHenKS_Den").checked) {
                    this.$toast.error("Hãy chọn Ngày hẹn");
                    return false;
                }

                if (document.getElementById("chkHenKS_Tu").checked && this.dtpHenKS_Tu == "") {
                    this.$toast.error("Hãy chọn Ngày hẹn khảo sát từ");
                    return false;
                }

                console.log(moment("27/11/1994",'DD/MM/YYYY').format('DDMMYYYY'))
                if (document.getElementById("chkHenKS_Tu").checked && 
                            moment(this.dtpHenKS_Tu,'DD/MM/YYYY HH:mm').format('YYYYMMDD') < moment(this.ngay_cn,'DD/MM/YYYY').format('YYYYMMDD')) {
                    this.$toast.error("Ngày hẹn từ không thể nhỏ hơn ngày hiện tại");
                    return false;
                }

                if (document.getElementById("chkHenKS_Den").checked && this.dtpHenKS_Den == "") {
                    this.$toast.error("Hãy chọn Ngày hẹn khảo sát đến");
                    return false;
                }

                if (document.getElementById("chkHenKS_Den").checked && 
                            moment(this.dtpHenKS_Den,'DD/MM/YYYY HH:mm').format('YYYYMMDD') < moment(this.ngay_cn,'DD/MM/YYYY').format('YYYYMMDD')) {
                    this.$toast.error("Ngày hẹn đến không thể nhỏ hơn ngày hiện tại");
                    return false;
                }

                if (document.getElementById("chkHenKS_Tu").checked && this.dtThuebao[0].ngay_ht != ""
                        && this.AddDays(this.convertDDMMYYYYHHmm_toDate(this.dtThuebao[0].ngay_ht), 3) < this.convertDDMMYYYYHHmm_toDate(dtpHenKS_Tu)) {
                    this.$toast.error("Không thể hẹn quá 72h tính từ thời điểm nghiệm thu: " + this.dtThuebao[0].ngay_ht);
                    return false;
                }

                if (document.getElementById("chkHenKS_Den").checked && this.dtThuebao[0].ngay_ht != ""
                        && this.AddDays(this.convertDDMMYYYYHHmm_toDate(this.dtThuebao[0].ngay_ht), 3) < this.convertDDMMYYYYHHmm_toDate(dtpHenKS_Den)) {
                    this.$toast.error("Không thể hẹn quá 72h tính từ thời điểm nghiệm thu: " + this.dtThuebao[0].ngay_ht);
                    return false;
                }
            }


            for (let i=0; i<this.dsCauHoiDXL.length; i++) {
                for (let j=0; j<this.dsCauHoiDXL[i].ds.length; j++) {
                    let idTl = "checktl_" + this.dsCauHoiDXL[i].id + "_" + this.dsCauHoiDXL[i].ds[j].traloi_id
                    if (document.getElementById(idTl).checked) {
                        let itemTL = {cauhoi_id: this.dsCauHoiDXL[i].id,            traloi_id: this.dsCauHoiDXL[i].ds[j].traloi_id, 
                                      noidung: this.dsCauHoiDXL[i].ds[j].noidung,   cauhoi: this.dsCauHoiDXL[i].ds[j].tencauhoi,
                                      traloi: this.dsCauHoiDXL[i].ds[j].noidung_tl, dieulai: this.dsCauHoiDXL[i].ds[j].dieulai,
                                      rch_id: this.dsCauHoiDXL[i].ds[j].rch_id,     dieulai_xn: this.dsCauHoiDXL[i].ds[j].dieulai_xn,
                                      dieu_kn: this.dsCauHoiDXL[i].ds[j].dieu_kn}
                        this.dsTraLoi.push(itemTL)
                    }
                }
            }
            if (this.dsTraLoi.length > 0 && document.getElementById("chkKTL").checked) //vừa tích vào câu trả lời, vừa check vào "Không trả lời"
            {
                this.$toast.error("Bạn đang chọn \"Không trả lời\" và đồng thời tích chọn trả lời trong Danh sách câu hỏi!");
                return false;
            }

            if (!document.getElementById("chkKTL").checked) { // nếu check vào KTL rồi thì ko cần kiểm tra
                if (this.dsTraLoi.length <= 0) {
                    this.$toast.error("Bạn phải chọn câu trả lời!");
                    return false;
                }

                let kiemtra_quagio = false;
                for (let xy=0; xy<this.dsTraLoi.length; xy++) {
                    let item = this.dsTraLoi[xy]
                    let traloi_id = Number(item.rch_id);
                    //vinhpv kiem tra qua gio HPG
                    if (String(item.traloi_id) == "53") {
                        kiemtra_quagio = true;
                    }
                        
                    let a = await this.lay_ds_cauhoi_phu(traloi_id)
                    if (a.length > 0) {
                        let count = 0
                        for (let i=0; i<this.dsTraLoi.length; i++) {
                            for (let j=0; j<a.length; j++) {
                                if (this.dsTraLoi[i].cauhoi_id == a[j].cauhoi_id) {
                                    count++
                                }
                            }
                        }

                        if (count <= 0) {
                            if (await this.Kiemtra_cauhoi_hailong(traloi_id) != "1") {
                                this.$toast.error("Bạn chọn câu trả lời '" + item.traloi + "', mà chưa chọn lý do ! ");
                                $("#txtTenNguoiTL").focus();
                                return false;
                            }
                        }  
                    }

                    // vinhpv kiểm tra các câu trả lời ý kiến khác mà không nhập text.
                    let tlid = Number(item.traloi_id);
                    a = await this.sp_ds_tb_traloi(tlid)
                    if (a.length > 0) {
                        if ((String(a[0].loai) == "1" || String(a[0].loai) == "2") && item.noidung.trim() == "") {
                            this.$toast.error("Bạn chưa nhập nội dung cho câu trả lời '" + item.traloi + " ! ");
                            $("#txtTenNguoiTL").focus();
                            return false;
                        }

                        // Khong tim thay procedure fims.kiemtra_giaophieu_net_v2 trong DB => khong su dung
                        // Nếu câu trả lời lq đến NET thì fai tồn tại hướng giao về Net - VB 1348/VNPT-CLG-TL
                        // if (String(a[0].ma_tl) == "NET1" || String(a[0].ma_tl) == "NET2" || String(a[0].ma_tl) == "NET3") {
                        //     if (fims.kiemtra_giaophieu_net_v2(baohong_id, 2) != "1") {
                        //         this.$toast.error("Bạn chỉ được chọn hạng mục 'NET1', 'NET2', 'NET3' sau khi kiểm tra có phiếu VNPT Net xử lý");
                        //         return false;
                        //     }
                        // }                
                    }

                    if (this.kt_dambao_ckcl_kh) {
                        if (this.baohong_ckcl)  {
                            let kt8841 = false
                            for (let i=0; i<this.dsTraLoi.length; i++) {
                                if (String(this.dsTraLoi[i].cauhoi_id == "8841")) {
                                    kt8841 = true
                                    break
                                }
                            }
                            if (!kt8841) {
                                this.$toast.error("Thuê bao được hưởng khuyến mại cam kết chất lượng. Bắt buộc chọn Câu hỏi 5!");
                                return false;
                            }

                            let ch_id = Number(item.cauhoi_id);
                            if (ch_id == 8841 && tlid == 263) {
                                this.$toast.error("Thuê bao được hưởng khuyến mại cam kết chất lượng. Câu hỏi 5 không thể chọn trả lời 'Bỏ qua tư vấn'!");
                                return false;
                            }
                        }
                    }
                }

                if (this.$root.token.getMaTinh() == "HPG") {
                    if (this.txtVPXL != "")
                        if (Number(this.txtVPXL) < 0 && kiemtra_quagio) {
                            this.$toast.error("Trường hợp này thi công/xử lý chưa quá giờ! Bạn hay chọn Hài long, đồng thời chuyển sang câu 'Ý kiến hài lòng của khách hàng' và chọn tiếp 'Thời gian sửa chữa, lắp đặt trên hệ thống đúng quy định của VNPT nhưng khách hàng vẫn phàn nàn chậm'");
                            $("#txtTenNguoiTL").focus();
                            return false;
                        }

                    // Kiểm tra nhóm câu hỏi
                    let dtNhomCH = await this.sp_lay_ds_nhom_cauhoi_bh(2)
                    if (dtNhomCH.length > 0) {
                        let sl_tu = Number(dtNhomCH[0].sl_tu);
                        let sl_den = Number(dtNhomCH[0].sl_den);
                        var ch_batbuoc = []
                        dtNhomCH.forEach((nhomch) => {
                            ch_batbuoc.puhs(String(nhomch.cauhoi_id))
                        })
                        let countChon = 0
                        this.dsTraLoi.forEach((itemtl) => {
                            if (ch_batbuoc.includes(String(itemtl.cauhoi_id))) {
                                countChon++
                            }
                        })
                        if (countChon < sl_tu || countChon > sl_den) {
                            this.$toast.error("Bạn phải chọn 1 trong các lựa chọn 'hài lòng', 'không hài lòng', 'không tính mẫu', 'không thành công' ");
                            $("#txtTenNguoiTL").focus();
                            return false;
                        }
                    }
                }

                // BATBUOC_TRALOI_ALL_CAUHOI
                if (this.BATBUOC_TRALOI_ALL_CAUHOI == 1) {
                    // Kiểm tra nhóm câu hỏi
                    let dtNhomCH = await this.sp_lay_ds_nhom_cauhoi_bh(0)
                    if (dtNhomCH.length > 0) {
                        let sl_tu = Number(dtNhomCH[0].sl_tu);
                        let sl_den = Number(dtNhomCH[0].sl_den);
                        var ch_batbuoc = []
                        dtNhomCH.forEach((nhomch) => {
                            ch_batbuoc.push(String(nhomch.cauhoi_id))
                        })
                        let countChon = 0
                        this.dsTraLoi.forEach((itemtl) => {
                            if (ch_batbuoc.includes(String(itemtl.cauhoi_id))) {
                                countChon++
                            }
                        })
                        if (countChon < sl_tu || countChon > sl_den) {
                            this.$toast.error("Có câu hỏi đang chưa chọn câu trả lời!");
                            return false;
                        }
                    }
                }
                //

                // KIEMTRA_CAUHOI_KHL
                if (this.KIEMTRA_CAUHOI_KHL == 1) {
                    let _rch_id = []
                    this.dsTraLoi.forEach((itemtl) => {
                        _rch_id.push(itemtl.rch_id)
                    })

                    let drRCH = []
                    this.dt.forEach((dtitem) => {
                        if (dtitem.kieu == 1 && _rch_id.includes(dtitem.rch_id1)) {
                            drRCH.push(dtitem)
                        }
                    })
                    if (drRCH.length > 0) {
                        let drRCH2 = []
                        this.dt.forEach((dtitem) => {
                            if (dtitem.kieu != 1 && _rch_id.includes(dtitem.rch_id1)) {
                                drRCH2.push(dtitem)
                            }
                        })

                        let js_cauhoi = JSON.stringify(drRCH)
                        let js_cauhoi_p = JSON.stringify(drRCH2)

                        var kq = await this.kiemtra_dieukien_khaosat(js_cauhoi, js_cauhoi_p)
                        if (kq == null || kq == "") {
                            this.$toast.error("Lỗi kiểm tra");
                            return false;
                        }
                        if (kq != "ok") {
                            this.$toast.error(kq);
                            return false;
                        }

                        if (await this.kiemtra_cauhoi_khl(Number(drRCH[0].rch_id1), 1) == "1") { //Câu 1 chọn Chưa hoàn thành lắp đặt/sửa chữa
                            if (drRCH.length < 2) {
                                this.$toast.error("Hãy chọn đủ câu trả lời!");
                                return false;
                            }

                            //-> kiểm tra câu 2
                            //Câu 2 chọn trả lời khác "Không hài lòng kỹ thuật" -> không cho làm
                            if (await this.kiemtra_cauhoi_khl(Number(drRCH[0].rch_id1), 2) != "1") {
                                this.$toast.error("Câu 1 chọn \"Chưa hoàn thành lắp đặt/sửa chữa\" thì câu 2 chọn \"Không hài lòng\" và câu 3 chọn \"KT3\"");
                                return false;
                            } else {//câu 2 chọn đúng -> kiểm tra câu 3
                                // Câu 3 chọn trả lời khác KT3 -> không cho làm
                                if (await this.kiemtra_cauhoi_khl(Number(drRCH[0].rch_id1), 3) != "1") {
                                    this.$toast.error("Câu 3 phải chọn \"KT3- Chưa được lắp đặt/sửa chữa dịch vụ\"");
                                    return false;
                                }
                            }
                        } else if (await this.kiemtra_cauhoi_khl(Number(drRCH[0].rch_id1), 1) == "2") { //Câu 1 chọn Đã HT
                            if (drRCH.length < 2) {
                                this.$toast.error("Hãy chọn đủ câu trả lời!");
                                return false;
                            }

                            if (await this.kiemtra_cauhoi_khl(Number(drRCH[0].rch_id1), 2) != "1") {
                                // Khong tim thay procedure fims.Kiemtra_traloi_fiber trong DB => khong su dung
                                // if (fims.Kiemtra_traloi_fiber(Number(drRCH2[0]["RCH_ID1"]), baohong_id, hdkh_id) == "1")
                                // {
                                //     this.$toast.error("Thuê bao Fiber không thể chọn Không hài lòng với nguyên nhân của Media!");
                                //     return false;
                                // }
                            }
                        } else if (await this.kiemtra_cauhoi_khl(Number(drRCH[0].rch_id1), 1) == "3") { //Câu 1 chọn KTC, KTM
                            if (drRCH.length >= 2) {
                                this.$toast.error("Với câu trả lời \"Không thành công\" hoặc \"Không tính mẫu\" không thể chọn trả lời Câu 2!");
                                return false;
                            }
                        }               
                    }
                }
            }

            // Kiểm tra chỉ user người khảo sát mới được sửa nội dung ks và chỉ sửa trong tháng
            if (!themmoi) {
                if (this.$root.token.getMaTinh() != "HCM") {
                    if (this.$root.token.getUserName() != "admin") {
                        let kt = await this.sp_lay_gt_thamso_md_tents("TOTRUONG_SUA_KHAOSAT", "1")
                        if (kt.length > 0) {
                            let ktra = await this.Kiemtra_nhanvien_sua_khaosat(this.tbtl_id, this.giaophieu_ks_id, 2);
                            if (ktra != "ok") {
                                this.$toast.error(ktra);
                                return false;
                            }
                        } else {
                            let b = await this.sp_lay_tb_traloi_nd(this.tbtl_id, this.$root.token.getUserName())
                            if (b.length <= 0) {
                                this.$toast.error("Bạn không có quyền sửa nội dung cuộc khảo sát này !");
                                $("#txtTenNguoiTL").focus();
                                return false;
                            }
                        }
                    }
                } else {
                    //kiểm tra quyền nhân viên và thời gian sửa phiếu: 12/12/2019
                    let ktra = await this.fn_quyen_sua_khaosat(this.tbtl_id, this.giaophieu_ks_id, 2);
                    if (ktra != "ok") {
                        this.$toast.error(ktra);
                        return false;
                    }
                }
            }

            if (this.$root.token.getMaTinh() == "HCM") {
                //Chưa Khảo Sát + Điều LẠI : Thì kiếm tra vừa ghi lại vừa điều lại được cả 2 luôn ( 
                //CHưa Trả Lời: Còn khi cập nhật nếu chưa trả lời thì mặc định ấn bắt đầu KS sẽ kiểm tra nên không cần nữa
                //trừ khi hên xui vượt qua đoạn chặn-> CHắc ko bao giờ xảy ra
                //Đã Trả Lời:  rồi thì có quyền trên là  được không cần chặn nữa ai thích làm gì thì làm lúc này free style
                if (this.isNew) {
                    if (!await this.KIEMTRA_GIUPHIEU())
                        return false;
                }

                let skt = await this.kiemtra_phieu_quagio(this.giaophieu_ks_id);
                if (skt == "1") {
                    this.$toast.error("Phiếu khảo sát đã quá 30 phút !");
                }
            }

            if (!await this.KiemTraSoDienThoaiCoDinh()) return false;

            return true
        },
        async KiemTraSoDienThoaiCoDinh() {
            //Kiểm tra tham số MĐ
            this.DieuLH_CD = false;
            let dt = await this.sp_lay_gt_thamso_md_tents("BATBUOC_CAPNHAT_SOLH_DD", "1")
            if (dt.length > 0) {
                if (this.txtSoLH.length < 10) {
                    this.$toast.error("Số điện thoại không đúng chuẩn !");
                    $("#txtSoLH").focus();
                    return false;
                }

                if (this.txtSoLH.substring(0, 1) != "0") {
                    this.$toast.error("Số điện thoại không đúng chuẩn !");
                    $("#txtSoLH").focus();
                    return false;
                }

                if (this.txtSoLH.length > 10) {
                    this.$bvModal.msgBoxConfirm("Số liên hệ hiện tại là số cố định/gphone/ims, bạn có muốn thay đổi thành số di động không?", {
                        title: '', size: 'sm', okTitle: 'Đồng ý', cancelTitle: 'Hủy',
                    }).then(async v => {
                        if (v) {
                            $("#txtSoLH").focus();
                            return false;
                        }
                    })

                    if (this.txtSoLH.substring(0, 4) != "0225") {
                        this.$toast.error("Số liên hệ " + this.txtSoLH + " không tồn tại trong danh bạ hoặc không hoạt động bình thường!");
                        $("#txtSoLH").focus();
                        return false;
                    }

                    //Kiểm tra số cố định có trong danh bạ của mình hay không
                    dt = await this.sp_lay_thuebao_cd(this.txtSoLH.substring(5, this.txtSoLH.length), 1)
                    if (dt.length == 0) {
                        this.$toast.error("Số liên hệ " + txtSoLH.Text + " không tồn tại trong danh bạ hoặc không hoạt động bình thường!");
                        $("#txtSoLH").focus();
                        return false;
                    }

                    //Kiểm tra xem KH có dùng dịch vụ khác CĐ, IMS Gphone không, nếu có dùng thì điều thu thập liên hệ
                    dt = await this.sp_lay_thuebao_cd(this.thuebao_id, 2)
                    this.DieuLH_CD = (dt.length > 0);
                }
            }
            return true;
        },
        convertDDMMYYYYHHmm_toDate(sdate) {
            let s = sdate.split(" ")
            let s1 = s[0].split("/")
            let s2 = s[1].split(":")
            return new Date(s1[2], s1[1]-1, s1[0], s2[0], s2[1], 0, 0);
        },
        AddDays(date, days) {
            var result = new Date(date);
            result.setDate(result.getDate() + days);
            return result;
        },
        async GetKey(keyname) {
            let result = ""
            try {
                let params = {
                    "keyname": keyname,
                    "numblocksize": 0,
                    "numretry": 0
                }
                let response = await api.ur_41003_5_000_get_keys(this.axios, params);
                console.log(response)
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async TaoDuLieu(themmoi, xuatlai) {
            this.dsTb = []
            let row = {}
            if (themmoi)
                this.tbtl_id = await this.GetKey("TBTL_ID");

            row.TBTL_ID = this.tbtl_id;
            row.BAOHONG_ID = this.baohong_id;
            row.HDKH_ID = this.hdkh_id;
            row.HINHTHUC = 1;
            row.THUEBAO_ID = this.thuebao_id;
            row.NGUOI_TL = this.txtTenNguoiTL;
            row.DIENTHOAI_LH = this.txtSoLH;
            row.GHICHU = this.txtGhiChu;
            row.TRANGTHAI = document.getElementById("chkKTL").checked ? 0 : 1;
            if (document.getElementById("chkKTL").checked)
                row.LYDOKTL_ID = Number(this.lydoktl_id)

            if (xuatlai)
                row.TRANGTHAI = 2;
            if (themmoi)
                row.NGAY_HOI = this.ngay_cn;
            else
                row.NGAY_HOI = this.ngay_ks;

            if (this.$root.token.getMaTinh() == "HCM") {
                if (this.ngay_bd_ks != null && !this.sua_phieu) {
                    row.NGAY_BD = this.ngay_bd_ks;
                    row.NGAY_HOI = this.ngay_cn;
                }
            }

            if (!this.sua_phieu) {
                row.NGUOI_CN = this.$root.token.getUserName();
                row.MAY_CN = "TODO_MAYCN";
                row.NHANVIEN_ID = this.$root.token.getNhanVienID();
                row.NGUOIDUNG_ID = this.$root.token.getNguoiDungID();
                row.IP_CN = "TODO_IPCN";
            } else {
                row.NGUOI_CN = this.vNGUOI_CN;
                row.MAY_CN = this.vMAY_CN;
                row.NHANVIEN_ID = this.vNHANVIEN_ID;
                row.NGUOIDUNG_ID = this.vNGUOIDUNG_ID;
                row.IP_CN = this.vIP_CN;
            }

            if (document.getElementById("chkKTL").checked) {
                row.TTKS_ID = 1;

                //Hieu: Tinh số lần tối đa vượt thì cho về trạng thái ks kết thúc.
                let count = 0
                for (let i=0; i<this.ds_lanhoi.length; i++) {
                    if (this.ds_lanhoi[i].lydo_ktl != "") {
                        count++
                    }
                }
                if (count + 1 > this.lan_ks_toida) {
                    row.TTKS_ID = 7; //Kết thúc KH không gọi lại KH nữa.
                }
                this.vdacapnhat = true;
            } else {
                row.TTKS_ID = 6;
                this.vdacapnhat = true;
            }

            if (this.OB_KHONGTL_KTC1 == 1 && document.getElementById("chkKTL").checked && Number(this.lydoktl_id) == 1) {  // FIMS_LYDO_KTL.KH_TU_CHOI_TIEP_CHUYEN = 1
                row.TTKS_ID = 6;
                this.vdacapnhat = true;
            }

            if (this.giaophieu_ks_id != 0)
                row.GIAOPHIEU_KS_ID = this.giaophieu_ks_id;

            this.dsTb.push(row);

            this.dsCT = []
            this.isDieuLai = false;
            for (let i=0; i<this.dsCauHoiDXL.length; i++) {
                let rowct = {}
                for (let j=0; j<this.dsCauHoiDXL[i].ds.length; j++) {
                    let idTl = "checktl_" + this.dsCauHoiDXL[i].id + "_" + this.dsCauHoiDXL[i].ds[j].traloi_id
                    if (document.getElementById(idTl).checked) {
                        rowct.CAUHOI_ID = Number(this.dsCauHoiDXL[i].id)
                        rowct.TRALOI_ID = Number(this.dsCauHoiDXL[i].ds[j].traloi_id)
                        rowct.TBTL_ID = this.tbtl_id;
                        rowct.NOIDUNG = this.dsCauHoiDXL[i].ds[j].noidung
                        this.dsCT.push(rowct);

                        if (String(this.dsCauHoiDXL[i].ds[j].dieulai) == "1")
                            this.isDieuLai = true;
                        if (String(this.dsCauHoiDXL[i].ds[j].dieulai_xn) == "1")
                            this.isDieuLai_XN = true;
                    }
                }
            }
        },
        async Insert_dsTb(p_js_tbtraloi) {
            try {
                let params = {
                    "p_js_tbtraloi": JSON.stringify(p_js_tbtraloi)
                }
                let response = await api.ur_41003_5_009_05_sp_insert_tb_traloi(this.axios, params);
                console.log(response)
            } catch (e) {
                console.log(e)
            }
        },
        async Delete(p_tbtl_id) {
            try {
                let params = {
                    "p_tbtl_id": p_tbtl_id
                }
                let response = await api.ur_41003_5_009_06_sp_delete_ct_tbtl(this.axios, params);
                console.log(response)
            } catch (e) {
                console.log(e)
            }
        },
        async Insert_dsCT(p_js_ct_tbtraloi) {
            try {
                let params = {
                    "p_js_ct_tbtraloi": JSON.stringify(p_js_ct_tbtraloi)
                }
                let response = await api.ur_41003_5_009_07_sp_insert_ct_tbtraloi(this.axios, params);
                console.log(response)
            } catch (e) {
                console.log(e)
            }
        },
        async sp_dieuhonglai(vtbtl_id, vbaohong_id, vgiaophieu_ks_id, vtt) {
            let result = []
            try {
                let params = {
                    "vtbtl_id": vtbtl_id,
                    "vbaohong_id": vbaohong_id,
                    "vgiaophieu_ks_id": vgiaophieu_ks_id,
                    "vtt": vtt
                }
                let response = await api.ur_41003_5_011_27_sp_dieuhonglai(this.axios, params);
                console.log(response)
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async DIEUHONGLAI() {
            let tt = "6"; 
            if (document.getElementById("chkKTL").checked) {
                tt = "3";
            }

            await this.sp_dieuhonglai(this.tbtl_id, this.baohong_id, this.giaophieu_ks_id, tt)
        },
        async sp_lay_gt_thamso_md_tents(p_ma_ts, p_ten_ts) {
            let result = []
            try {
                let params = {
                    "p_ma_ts": p_ma_ts,
                    "p_ten_ts": p_ten_ts
                }
                let response = await api.ur_41003_5_008_08_sp_lay_gt_thamso_md_tents(this.axios, params);
                console.log(response)
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async capnhat_so_dt_khi_outbound(vthuebao_id, vsomay) {
            try {
                let params = {
                    "vthuebao_id": vthuebao_id,
                    "vsomay": vsomay,
                }
                let response = await api.ur_41003_5_011_23_capnhat_so_dt_khi_outbound(this.axios, params);
                console.log(response)
            } catch (e) {
                console.log(e)
            }
        },
        async tsbtnDieuLai_Click() {
            try {
                if (this.$root.token.getMaTinh() == "HCM") {
                    this.$toast.error("Hiện tại nghiệp vụ điều lại chưa được hỗ trợ với HCM");
                    return;
                }
                if (await this.KiemTraDieuHongLai(this.baohong_id, 0) == 0) {
                    this.$toast.error("Thuê bao đang có phiếu điều hỏng chưa xử lý xong! Bạn không thể điều lại!");
                    return;
                }
                if (this.isNew) {
                    this.$bvModal.msgBoxConfirm("Bạn có chắc chắn muốn điều hỏng thuê bao " + this.txtMaTB + " ?", {
                        title: '', size: 'sm', okTitle: 'Đồng ý', cancelTitle: 'Hủy',
                    }).then(async v => {
                        if (v) {
                            try {
                                this.loading(true)
                                if (await this.KiemTra_DuLieu(true)) {
                                    await this.TaoDuLieu(true, true);
                                    await this.Insert_dsTb(this.dsTb)
                                    await this.Delete(this.tbtl_id)
                                    await this.Insert_dsCT(this.dsCT)

                                    await this.DIEUHONGLAI();
                                    let kt = await this.sp_lay_gt_thamso_md_tents("CAPNHAT_SO_DT_OUTBOUND", "1")
                                    if (kt.length > 0) {
                                        if (!document.getElementById("chkKTL").checked) {
                                            this.$bvModal.msgBoxConfirm("Bạn đã liên hệ được với khách hàng. Bạn có muốn cập nhật số điện thoại: " + this.txtSoLH + " cho thuê bao " + this.txtMaTB, {
                                                title: '', size: 'sm', okTitle: 'Đồng ý', cancelTitle: 'Hủy',
                                            }).then(async v => {
                                                if (v) {
                                                    await this.capnhat_so_dt_khi_outbound(this.thuebao_id, this.txtSoLH)
                                                    this.$toast.success("Cập nhật thông tin liên hệ thành công!");
                                                }
                                            })
                                        }
                                    }
                                }
                            } catch (e) {
                                console.log(e)
                            } finally {
                                this.loading(false)
                            }     
                        }
                    });     
                }
            } catch (ex) {
                this.$toast.error("Có lỗi: " + ex.ToString());
            }
        },
        toolStripButton2_Click() {
            this.$refs.popupTraCuuTBKS.openDialog()
        },
        toolStripButton1_Click() {
            if (this.thuebao_id == -1)
                return;
            
            this.$refs.popupLichSu_BaoHong.openDialog()
        },
        tsbtnLSHen_Click() {
            if (this.baohong_id == -1 || this.baohong_id == 0)
                return;

            this.$refs.popupLichSu_HenBH_KS.openDialog()
        },
        xlCheck() {
            let checkID = $("#checkID").val()
            if (checkID != null && checkID != "") {
                let tmp = checkID.split("_")        
                let vCauhoi_id = tmp[1], vTraloi_id = tmp[2]
                this.dsCauHoiDXL.forEach((item) => {
                    if (item.id == vCauhoi_id) {
                        if (item.ds.length > 0 && item.ds[0].loaich_id == 1) {                
                            item.ds.forEach((itemTL) => {
                                if (itemTL.traloi_id != vTraloi_id) {
                                    let idT = "#checktl_" + item.id + "_" + itemTL.traloi_id     
                                    $(idT).prop("checked", false)
                                }
                            })
                        }
                    }
                })
            }  
        }
    },
}
function checkRadioTL(e) {
    for (let i=0; i<e.path.length; i++) {
        if (e.path[i].id != undefined && e.path[i].id != null && e.path[i].id != "") {
            $("#checkID").val(e.path[i].id)
            break
        }
    }

    document.getElementById("xlCheck").click()
}
</script>