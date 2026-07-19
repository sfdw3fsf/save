<template src="./ThongSoCoDinh.html"></template>
<script>
import api from "./ThongSoCoDinh.js";
import moment from "moment";
import { LoaiHopDong, LoaiHinhTB } from '../../Enums.js'
import DSTBCungDoiCapBC from '@/modules/install/ChangeADSLSpeed/popups/DSTBCungDoiCapBC/DSTBCungDoiCapBC'
import DSTBLapKemBC from '@/modules/install/ChangeADSLSpeed/popups/DSTBLapKemBC/DSTBLapKemBC'

export default {
    name:'ThongSoCoDinh',
    props:{
        pLoaihinhTB: {
            type: Number,
            default: null
        },
        tinhtc: {
            type: Object,
            default: {}
        },
        _ploaitb_ld: {
            type: Number,
            default: null
        },
        _ploaitb: {
            type: Number,
            default: null
        },
        _thuebao_id_ban: {
            type: Number,
            default: null
        },
        _thuebao_id_thicong: {
            type: Number,
            default: null
        },
        _vloaihd: {
            type: Number,
            default: null
        },
        khachhang_id: {
            type: Number,
            default: null
        },
        hdkh_id: {
            type: Number,
            default: null
        },
        Text: {
            type: String,
            default: ""
        }
    },
    components:{
        DSTBCungDoiCapBC,
        DSTBLapKemBC
    },
    data() {
        return {
            position: {X: 'center', Y: 'top'},
            targetTop: '#app .main-wrapper',
            thongso_Ban: {},
            thongso_TC: {},
            kt_load: true,
            tinh_chuquan_id: "-1",
            ngay: moment(new Date()).format("DD/MM/YYYY"),
            diachi_ban: {},
            diachi_thicong: {},
            model: {
                cboCongNgheBan: {
                    list: [],
                    value: null
                },
                cboDoiTuongBan: {
                    list: [],
                    value: null
                },
                cboTrangBiBan: {
                    list: [],
                    value: null
                },
                cboMucCuocTBBan: {
                    list: [],
                    value: null
                },
                cboChuQuan_Ban: {
                    list: [],
                    value: null
                },
                cboChuQuanLD_Ban: {
                    list: [],
                    value: null
                },
                cboKieuLD: {
                    list: [],
                    value: null
                },
                txtMATNTC: "",
                cboCongNgheTC: {
                    list: [],
                    value: null
                },
                cboDoiTuongTC: {
                    list: [],
                    value: null
                },
                cboTrangBiTC: {
                    list: [],
                    value: null
                },
                cboMucCuocTBTC: {
                    list: [],
                    value: null
                },
                cboChuQuan_TC: {
                    list: [],
                    value: null
                },
                cboChuQuanLD_TC: {
                    list: [],
                    value: null
                },
            },
            enable: {
                cboDoiTuongBan: true,
                cboDoiTuongTC: true,
                cboMucCuocTBBan: true,
                cboMucCuocTBTC: true
            }
        }
    },
    methods: {
        refresh() {
            this.kt_load = true
            this.tinh_chuquan_id = "-1"
            this.thongso_Ban = {}
            this.thongso_TC = {}
            this.cboCongNgheBan = {
                list: [],
                value: null
            }
            this.cboDoiTuongBan = {
                list: [],
                value: null
            }
            this.cboTrangBiBan = {
                list: [],
                value: null
            }
            this.cboMucCuocTBBan = {
                list: [],
                value: null
            }
            this.cboChuQuan_Ban = {
                list: [],
                value: null
            }
            this.cboChuQuanLD_Ban = {
                list: [],
                value: null
            }
            this.cboKieuLD = {
                list: [],
                value: null
            }
            this.txtMATNTC = ""
            this.cboCongNgheTC = {
                list: [],
                value: null
            }
            this.cboDoiTuongTC = {
                list: [],
                value: null
            }
            this.cboTrangBiTC = {
                list: [],
                value: null
            }
            this.cboMucCuocTBTC = {
                list: [],
                value: null
            }
            this.cboChuQuan_TC = {
                list: [],
                value: null
            }
            this.cboChuQuanLD_TC = {
                list: [],
                value: null
            }
        },
        async openDialog(thongsoban, thongsotc) { 
            console.log(thongsoban)
            console.log(thongsotc)
            this.refresh()
            this.thongso_Ban = thongsoban
            this.thongso_TC = thongsotc
            this.$refs.dlg_thongsocodinh.show()
            await this.ThongSoCoDinh_Load()
        },
        Reset_diachi() {
            let diachi = {}
            diachi.diachi_id = 0;
            diachi.quan_id = 0;
            diachi.phuong_id = 0;
            diachi.pho_id = 0;
            diachi.khu_id = 0;
            diachi.ap_id = 0;
            diachi.dacdiem_id = 0;
            return diachi;
        },
        async HT_DoiTuong_Combobox() {
            let result = []
            try {
                let response = await api.ks03_sp_lay_ds_doituong_theo_dieukien(this.axios);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async load_Dm_Doi_tuong(tinhthicong_id) {
            let result = []
            try {
                let params = {
                    "tinhthicong_id": tinhthicong_id
                }
                let response = await api.ks04_load_dm_doi_tuong(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async HT_CONGNGHE_KIEULD_BAN(kieuldId) {
            let result = []
            try {
                let params = {
                    "kieuldId": kieuldId
                }
                let response = await api.ks01_sp_lay_ds_congnghe_theo_kieuld_id(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async HT_TrangBi_TSL_Combobox_DK() {
            let result = []
            try {
                let response = await api.ks05_sp_lay_ds_trangbi_theo_dieukien(this.axios);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async HT_TrangBi_Combobox(tinhthicong_id) {
            let result = []
            try {
                let params = {
                    "tinhthicong_id": tinhthicong_id
                }
                let response = await api.ks06_load_dm_trang_bi(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async LAY_DS_MUCUOC_TB_V2(khuld_id, apld_id, phold_id, phuongld_id, loaitb_id, doituong_id, tocdo_id, muccuoc_id, ngay, trangbi_id) {
            let result = []
            try {
                let params = {
                    "khuld_id": khuld_id,
                    "apld_id": apld_id,
                    "phold_id": phold_id,
                    "phuongld_id": phuongld_id,
                    "loaitb_id": loaitb_id,
                    "doituong_id": doituong_id,
                    "tocdo_id": tocdo_id,
                    "muccuoc_id": muccuoc_id,
                    "ngay": ngay,
                    "trangbi_id": trangbi_id
                }
                let response = await api.ks08_lay_ds_mucuoc_tb_v2(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async LAY_DS_CHUQUAN() {
            let result = []
            try {
                let params = {
                    "cq_tc": 0
                }
                let response = await api.ks10_lay_ds_chuquan(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async LAY_DIACHI_DBTB_BANCHEO(kieu_tinh, tinhthicong_id, thuebao_id) {
            let result = []
            try {
                let params = {
                    "kieu_tinh": kieu_tinh,
                    "tinhthicong_id": tinhthicong_id,
                    "thuebao_id": thuebao_id           
                }
                let response = await api.lay_diachi_dbtb_bancheo(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async HT_CONGNGHE_KIEULD_TC(tinhthicong_id, kieuldId) {
            let result = []
            try {
                let params = {
                    "tinhthicong_id": tinhthicong_id,
                    "kieuldId": kieuldId,    
                }
                let response = await api.ks02_load_dm_cong_nghe(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async LAY_CHUQUAN_MACDINH_THEO_DONVI_BANCHEO() {
            let result = ""
            try {
                let response = await api.lay_chuquan_macdinh_theo_donvi_bancheo(this.axios);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async LOAD_DM_CHU_QUAN(tinhthicong_id) {
            let result = []
            try {
                let params = {
                    "tinhthicong_id": tinhthicong_id  
                }
                let response = await api.ks11_load_dm_chu_quan(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async LOAD_DM_KIEULD(tinhthicong_id, vloaihd, ploaitb_ld, ploaitb) {
            let result = []
            try {
                let params = {
                    "tinhthicong_id": tinhthicong_id,
                    "vloaihd": vloaihd,
                    "ploaitb_ld": ploaitb_ld,
                    "ploaitb": ploaitb,
                }
                let response = await api.ks07_load_dm_kieuld(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async Lay_DS_MucCuoc_TB(tinhthicong_id, khuld_id, apld_id, phold_id, phuongld_id, loaitb_id, doituong_id, ngay, trangbi_id,
                        tocdo_id, muccuoc_id) {
            let vtocdo_id = 0;
            let vmuccuoc_id = 0; //2 Lưu lượng

            if (loaitb_id == LoaiHinhTB.INTERNET_FTTH ||
                loaitb_id == LoaiHinhTB.WIFI_FIBER ||
                loaitb_id == LoaiHinhTB.INTERNET_ADSL ||
                loaitb_id == LoaiHinhTB.INTERNET_ADSL_TINH_KHAC ||
                loaitb_id == LoaiHinhTB.INTERNET_FIBER_TINH_KHAC) {
                vtocdo_id = tocdo_id;
                vmuccuoc_id = muccuoc_id;
            } else if (loaitb_id == LoaiHinhTB.INTERNET_MYTV) {
                vtocdo_id = tocdo_id;
                vmuccuoc_id = 0;
            } else if (loaitb_id == LoaiHinhTB.FSECURE) { //Lamnt: bổ sung hiển thị mức cước theo tốc độ thuê bao FSecure (09/03/2017)
                vtocdo_id = tocdo_id;
                vmuccuoc_id = 0;
            }

            let result = []
            try {
                let params = {
                    "tinhthicong_id": tinhthicong_id,
                    "khuld_id": khuld_id,
                    "apld_id": apld_id,
                    "phold_id": phold_id,
                    "phuongld_id": phuongld_id,
                    "loaitb_id": loaitb_id,
                    "doituong_id": doituong_id,
                    "tocdo_id": vtocdo_id,
                    "muccuoc_id": vmuccuoc_id,
                    "trangbi_id": trangbi_id,
                    "ngay": ngay
                }
                let response = await api.ks09_lay_ds_mucuoc_tb_v2(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async ThongSoCoDinh_Load() {
            try {
                this.loading(true)
                this.kt_load = true;

                let temps = await this.HT_DoiTuong_Combobox();
                if (temps && temps.length > 0) {
                    this.model.cboDoiTuongBan.list = temps.map((x)=>({id:x.doituong_id, text:x.ten_dt}))
                    if (this.thongso_Ban.doituong_id == undefined || this.thongso_Ban.doituong_id == 0)
                        this.model.cboDoiTuongBan.value = temps[0].doituong_id
                    else
                        this.model.cboDoiTuongBan.value = this.thongso_Ban.doituong_id;                
                }
                
                temps = await this.load_Dm_Doi_tuong(this.tinhtc.tinhthicong_id);
                if (temps && temps.length > 0) {
                    this.model.cboDoiTuongTC.list = temps.map((x)=>({id:x.doituong_id, text:x.ten_dt}))
                    if (this.thongso_TC.doituong_id == undefined || this.thongso_TC.doituong_id == 0)
                        this.model.cboDoiTuongTC.value = temps[0].doituong_id
                    else
                        this.model.cboDoiTuongTC.value = this.thongso_TC.doituong_id;                
                }

                temps = await this.HT_CONGNGHE_KIEULD_BAN(this.thongso_Ban.kieuld_id);
                if (temps && temps.length > 0) {
                    this.model.cboCongNgheBan.list = temps.map((x)=>({id:x.congnghe_id, text:x.congnghe}))
                    if (this.thongso_Ban.congnghe_id == undefined || this.thongso_Ban.congnghe_id == 0)
                        this.model.cboCongNgheBan.value = temps[0].congnghe_id
                    else
                        this.model.cboCongNgheBan.value = this.thongso_Ban.congnghe_id;                
                }

                temps = await this.HT_CONGNGHE_KIEULD_TC(this.tinhtc.tinhthicong_id, this.thongso_TC.kieuld_id);
                if (temps && temps.length > 0) {
                    this.model.cboCongNgheTC.list = temps.map((x)=>({id:x.congnghe_id, text:x.congnghe}))
                    if (this.thongso_TC.congnghe_id == undefined || this.thongso_TC.congnghe_id == 0)
                        this.model.cboCongNgheTC.value = temps[0].congnghe_id
                    else
                        this.model.cboCongNgheTC.value = this.thongso_TC.congnghe_id;                
                }

                this.tinh_chuquan_id = await this.LAY_CHUQUAN_MACDINH_THEO_DONVI_BANCHEO()

                let ds_cq_ban = await this.LAY_DS_CHUQUAN()
                if (ds_cq_ban && ds_cq_ban.length > 0) {
                    this.model.cboChuQuan_Ban.list = ds_cq_ban.map((x)=>({id:x.chuquan_id, text:x.tenchuquan}))
                    this.model.cboChuQuanLD_Ban.list = ds_cq_ban.map((x)=>({id:x.chuquan_id, text:x.tenchuquan}))
                }

                let ds_cq_tc = await this.LOAD_DM_CHU_QUAN(this.tinhtc.tinhthicong_id)
                if (ds_cq_tc && ds_cq_tc.length > 0) {
                    this.model.cboChuQuan_TC.list = ds_cq_tc.map((x)=>({id:x.chuquan_id, text:x.tenchuquan}))
                    this.model.cboChuQuanLD_TC.list = ds_cq_tc.map((x)=>({id:x.chuquan_id, text:x.tenchuquan}))  
                }

                let det = [];
                if (this.tinh_chuquan_id == "-1")
                    det = ds_cq_ban.filter(item => item.tinh_id == this.$root.token.getPhanVungID())
                else
                    det = ds_cq_ban.filter(item => item.tinh_id == this.tinh_chuquan_id)

                if (det.length > 0) {
                    let row = det[0];
                    if (row.chuquan_id != undefined && row.chuquan_id != "")
                        this.model.cboChuQuan_Ban.value = Number(row.chuquan_id);
                }

                det = ds_cq_ban.filter(item => item.tinh_id == this.tinhtc.tinhthicong_id)
                if (det.length > 0) {
                    let row = det[0];
                    if (row.chuquan_id != undefined && row.chuquan_id != "")
                        this.model.cboChuQuanLD_Ban.value = Number(row.chuquan_id);
                }

                if (this.tinh_chuquan_id == "-1")
                    det = ds_cq_tc.filter(item => item.tinh_id == this.$root.token.getPhanVungID())
                else
                    det = ds_cq_tc.filter(item => item.tinh_id == this.tinh_chuquan_id)

                if (det.length > 0) {
                    let row = det[0];
                    if (row.chuquan_id != undefined && row.chuquan_id != "")
                        this.model.cboChuQuan_TC.value = Number(row.chuquan_id);
                }

                det = ds_cq_tc.filter(item => item.tinh_id == this.tinhtc.tinhthicong_id)
                if (det.length > 0) {
                    let row = det[0];
                    if (row.chuquan_id != undefined && row.chuquan_id != "")
                        this.model.cboChuQuanLD_TC.value = Number(row.chuquan_id);
                }

                temps = await this.LOAD_DM_KIEULD(this.tinhtc.tinhthicong_id, this._vloaihd, this._ploaitb_ld, this._ploaitb)
                if (temps && temps.length > 0) {
                    this.model.cboKieuLD.list = temps.map((x)=>({id:x.kieuld_id, text:x.ten_kieuld}))
                    if (this.thongso_TC.kieuld_id == undefined || this.thongso_TC.kieuld_id == 0)
                        this.model.cboKieuLD.value = this.model.cboKieuLD.list[0].id;
                    else
                        this.model.cboKieuLD.value = this.thongso_TC.kieuld_id;         
                } else
                    this.$toast.error("Không tìm thấy kiểu lắp đặt tại tỉnh thi công, liên hệ admin để xử lý.");

                if (this._thuebao_id_ban != null && this._thuebao_id_ban != 0)
                    this.diachi_ban = await this.LAY_DIACHI_DBTB_BANCHEO(1, this.tinhtc.tinhthicong_id, this._thuebao_id_ban);
                else
                    this.diachi_ban = this.Reset_diachi();

                temps = await this.HT_TrangBi_TSL_Combobox_DK();
                if (temps && temps.length > 0) {
                    this.model.cboTrangBiBan.list = temps.map((x)=>({id:x.trangbi_id, text:x.tentrangbi}))
                    if (this.thongso_Ban.trangbi_id == undefined || this.thongso_Ban.trangbi_id == 0)
                        this.model.cboTrangBiBan.value = temps[0].trangbi_id
                    else
                        this.model.cboTrangBiBan.value = this.thongso_Ban.trangbi_id;                
                }

                temps = await this.HT_TrangBi_Combobox(this.tinhtc.tinhthicong_id);
                if (temps && temps.length > 0) {
                    this.model.cboTrangBiTC.list = temps.map((x)=>({id:x.trangbi_id, text:x.tentrangbi}))
                    if (this.thongso_TC.trangbi_id == undefined || this.thongso_TC.trangbi_id == 0)
                        this.model.cboTrangBiTC.value = temps[0].trangbi_id
                    else
                        this.model.cboTrangBiTC.value = this.thongso_TC.trangbi_id;                
                }

                if (this._thuebao_id_thicong != null && this._thuebao_id_thicong != 0)
                    this.diachi_thicong = await this.LAY_DIACHI_DBTB_BANCHEO(2, this.tinhtc.tinhthicong_id, this._thuebao_id_thicong);
                else
                    this.diachi_thicong = this.Reset_diachi();

                temps = await this.LAY_DS_MUCUOC_TB_V2(this.diachi_ban.khu_id, this.diachi_ban.ap_id, this.diachi_ban.pho_id, this.diachi_ban.phuong_id, 
                        this.pLoaihinhTB, this.model.cboDoiTuongBan.value, 0, 0, this.ngay, Number(this.model.cboTrangBiBan.value));
                if (temps && temps.length > 0) {
                    this.model.cboMucCuocTBBan.list = temps.map((x)=>({id:x.muccuoctb_id, text:x.tenmuccuoc}))
                    if (this.thongso_Ban.muccuoctb_id != undefined && this.thongso_Ban.muccuoctb_id > 0)
                        this.model.cboMucCuocTBBan.value = this.thongso_Ban.muccuoctb_id;
                } else {
                    this.model.cboMucCuocTBBan.list = []
                }

                temps = await this.Lay_DS_MucCuoc_TB(this.tinhtc.tinhthicong_id, this.diachi_thicong.khu_id, this.diachi_thicong.ap_id, 
                    this.diachi_thicong.pho_id, this.diachi_thicong.phuong_id, this.pLoaihinhTB, Number(this.thongso_TC.doituong_id), 
                    this.ngay, Number(this.model.cboTrangBiTC.value), 0, 0);
                if (temps && temps.length > 0) {
                    this.model.cboMucCuocTBTC.list = temps.map((x)=>({id:x.muccuoctb_id, text:x.tenmuccuoc}))
                    if (this.thongso_TC.muccuoctb_id != undefined && this.thongso_TC.muccuoctb_id > 0)
                        this.model.cboMucCuocTBTC.value = this.thongso_TC.muccuoctb_id;
                }

                this.kt_load = false;
                if (this._vloaihd == LoaiHopDong.CHUYEN_QUYEN || this._vloaihd == LoaiHopDong.DI_CHUYEN) {
                    this.enable.cboDoiTuongBan = false;
                    this.enable.cboDoiTuongTC = false;
                    this.enable.cboMucCuocTBBan = false;
                    this.enable.cboMucCuocTBTC = false;
                }
            } catch (e) {
                console.log(e)
                this.$toast.error(e)
            } finally {
                this.loading(false)
            }
        },
        async fn_tt_kieu_ld(tinhthicong_id, kieuld_id) {
            let result
            try {
                let params = {
                    "tinhthicong_id": tinhthicong_id,
                    "param": kieuld_id
                }
                let response = await api.fn_tt_kieu_ld(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        acceptDSTBCungDoiCapBC(data) {
            this.$refs.DSTBCungDoiCapBCPopup.hide();
            this.thongso_TC.ma_tn = data.ma_tn;
            this.thongso_TC.ma_doicap = data.ma_doicap;
            this.model.txtMATNTC = data.ma_tn;
        },
        acceptDSTBLapKemBC(data){
            this.$refs.DSTBLapKemBCDialog.hide();
            this.thongso_TC.ma_tn = data.ma_tn;
            this.thongso_TC.ma_doicap = data.ma_doicap;
            this.model.txtMATNTC = data.ma_tn;
        },
        async layMaTN_CD() {
            let kieu = Number(await this.fn_tt_kieu_ld(this.tinhtc.tinhthicong_id, this.model.cboKieuLD.value));
            if (kieu == 2 || kieu == 4) //Lắp mới trên đường có sẵn
            {
                if (this.khachhang_id > 0) {
                    this.$refs.DSTBCungDoiCapBCDialog.show();
                    await this.$refs.DSTBLapKemBCPopup.loadData();
                } else {
                    this.$toast.error("Chưa có thông tin thuê bao để lấy danh sách mã truy nhập lắp trên đường có sẵn !");
                    return;
                }
            }
            else if (kieu == 1) //Lắp kèm
            {
                if (this.hdkh_id > 0) {
                    // frm.tinh = tinhtc;
                    this.$refs.DSTBLapKemBCDialog.show();
                    await this.$refs.DSTBCungDoiCapBCPopup.frmDSTBLapKemBC_Load();
                } else {
                    //Kiểm tra xem có thuê bao mega đang thực hiện chuyển đổi không
                    this.$toast.error("Chưa có thông tin thuê bao để lấy danh sách mã truy nhập lắp kèm !");
                    return;
                }
            }
        },
        async tsbtnCapNhat_Click() {
            if (this.model.cboTrangBiBan.value == null || this.model.cboTrangBiTC.value == null) {
                this.$toast.error("Bạn phải chọn trang bị cho 2 đơn vị");
                return;
            }

            if (this.model.cboMucCuocTBBan.value == null || this.model.cboMucCuocTBTC.value == null) {
                this.$toast.error("Bạn phải mức cước thuê bao cho 2 đơn vị");
                return;
            }

            if (this.model.cboDoiTuongBan.value == null || this.model.cboDoiTuongTC.value == null) {
                this.$toast.error("Bạn phải đối tượng thuê bao cho 2 đơn vị");
                return;
            }

            this.thongso_Ban.doituong_id = Number(this.model.cboDoiTuongBan.value);
            if (this.model.cboCongNgheBan.list.length > 0)
                this.thongso_Ban.congnghe_id = Number(this.model.cboCongNgheBan.value);
            this.thongso_Ban.muccuoctb_id = Number(this.model.cboMucCuocTBBan.value);
            this.thongso_Ban.trangbi_id = Number(this.model.cboTrangBiBan.value);
            this.thongso_Ban.chuquan_id = Number(this.model.cboChuQuan_Ban.value);
            this.thongso_Ban.chuquanld_id = Number(this.model.cboChuQuanLD_Ban.value);
            this.thongso_Ban.tocdo_id = 0;
            this.thongso_Ban.muccuoc_id = 0;

            this.thongso_TC.kieuld_id = Number(this.model.cboKieuLD.value);
            this.thongso_TC.doituong_id = Number(this.model.cboDoiTuongTC.value);
            if (this.model.cboCongNgheTC.list.length > 0)
                this.thongso_TC.congnghe_id = Number(this.model.cboCongNgheTC.value);
            this.thongso_TC.muccuoctb_id = Number(this.model.cboMucCuocTBTC.value);
            this.thongso_TC.trangbi_id = Number(this.model.cboTrangBiTC.value);
            this.thongso_TC.chuquan_id = Number(this.model.cboChuQuan_TC.value);
            this.thongso_TC.chuquanld_id = Number(this.model.cboChuQuanLD_TC.value);
            this.thongso_TC.tocdo_id = 0;
            this.thongso_TC.muccuoc_id = 0;

            this.$emit("capnhat", { "thongso_Ban": this.thongso_Ban, "thongso_TC": this.thongso_TC})
            this.$refs.dlg_thongsocodinh.hide()
        },
        async cboTrangBiBan_SelectedIndexChanged() {
            if (!this.kt_load) {
                let temps = await this.LAY_DS_MUCUOC_TB_V2(0, 0, 0, 0, this.pLoaihinhTB, Number(this.model.cboDoiTuongBan.value), 0, 0, this.ngay, Number(this.model.cboTrangBiBan.value));
                if (temps && temps.length > 0) {
                    this.model.cboMucCuocTBBan.list = temps.map((x)=>({id:x.muccuoctb_id, text:x.tenmuccuoc}))
                    this.model.cboMucCuocTBBan.value = this.model.cboMucCuocTBBan.list[0].id
                } else {
                    this.model.cboMucCuocTBBan.list = []
                    this.model.cboMucCuocTBBan.value = null
                }
            }
        },
        async cboDoiTuongBan_SelectedIndexChanged() {
            if (!this.kt_load) {
                let temps = await this.LAY_DS_MUCUOC_TB_V2(0, 0, 0, 0, this.pLoaihinhTB, Number(this.model.cboDoiTuongBan.value), 0, 0, this.ngay, Number(this.model.cboTrangBiBan.value));
                if (temps && temps.length > 0) {
                    this.model.cboMucCuocTBBan.list = temps.map((x)=>({id:x.muccuoctb_id, text:x.tenmuccuoc}))
                    this.model.cboMucCuocTBBan.value = this.model.cboMucCuocTBBan.list[0].id
                } else {
                    this.model.cboMucCuocTBBan.list = []
                    this.model.cboMucCuocTBBan.value = null
                }
            }
        },
        async cboDoiTuongTC_SelectedIndexChanged() {
            if (!this.kt_load) {
                let temps = await this.Lay_DS_MucCuoc_TB(this.tinhtc.tinhthicong_id, 0, 0, 0, 0, this.pLoaihinhTB, Number(this.model.cboDoiTuongTC.value), 
                    this.ngay, Number(this.model.cboTrangBiTC.value), 0, 0);
                if (temps && temps.length > 0) {
                    this.model.cboMucCuocTBTC.list = temps.map((x)=>({id:x.muccuoctb_id, text:x.tenmuccuoc}))
                    this.model.cboMucCuocTBTC.value = this.model.cboMucCuocTBTC.list[0].id
                } else {
                    this.model.cboMucCuocTBTC.list = []
                    this.model.cboMucCuocTBTC.value = null
                }
            }
        },
        async cboTrangBiTC_SelectedIndexChanged() {
            if (!this.kt_load) {
                let temps = await this.Lay_DS_MucCuoc_TB(this.tinhtc.tinhthicong_id, 0, 0, 0, 0, this.pLoaihinhTB, Number(this.model.cboDoiTuongTC.value), 
                    this.ngay, Number(this.model.cboTrangBiTC.value), 0, 0);
                if (temps && temps.length > 0) {
                    this.model.cboMucCuocTBTC.list = temps.map((x)=>({id:x.muccuoctb_id, text:x.tenmuccuoc}))
                    this.model.cboMucCuocTBTC.value = this.model.cboMucCuocTBTC.list[0].id
                } else {
                    this.model.cboMucCuocTBTC.list = []
                    this.model.cboMucCuocTBTC.value = null
                }
            }
        },
        async cboKieuLD_SelectedIndexChanged() {
            if (!this.kt_load) {
                let temps = await this.HT_CONGNGHE_KIEULD_TC(this.tinhtc.tinhthicong_id, Number(this.model.cboKieuLD.value));
                if (temps && temps.length > 0) {
                    this.model.cboCongNgheTC.list = temps.map((x)=>({id:x.congnghe_id, text:x.congnghe}))           
                } else {
                    this.model.cboCongNgheTC.list = []
                }

                temps = await this.Lay_DS_MucCuoc_TB(this.tinhtc.tinhthicong_id, 0, 0, 0, 0, this.pLoaihinhTB, Number(this.model.cboDoiTuongTC.value), 
                    this.ngay, Number(this.model.cboTrangBiTC.value), 0, 0);
                if (temps && temps.length > 0) {
                    this.model.cboMucCuocTBTC.list = temps.map((x)=>({id:x.muccuoctb_id, text:x.tenmuccuoc}))
                } else {
                    this.model.cboMucCuocTBTC.list = []
                }

                // layMaTN_CD();
            }
        }
    },
    watch: {      
        'model.cboTrangBiBan.value': async function(val){      
            this.cboTrangBiBan_SelectedIndexChanged()
        },
        'model.cboDoiTuongBan.value': async function(val){      
            this.cboDoiTuongBan_SelectedIndexChanged()
        },
        'model.cboDoiTuongTC.value': async function(val){      
            this.cboDoiTuongTC_SelectedIndexChanged()
        },
        'model.cboTrangBiTC.value': async function(val){      
            this.cboTrangBiTC_SelectedIndexChanged()
        },
        'model.cboKieuLD.value': async function(val){      
            this.cboKieuLD_SelectedIndexChanged()
        },
    }
}
</script>