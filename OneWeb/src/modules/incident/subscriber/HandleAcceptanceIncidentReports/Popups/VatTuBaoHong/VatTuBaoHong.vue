<template src="./VatTuBaoHong.html"></template>
<script>
import api from "./VatTuBaoHong.js";
import { Query } from '@syncfusion/ej2-data'
import Vue from 'vue';
import { DropDownListPlugin } from "@/components/duybn_components/Dropdowns/ej2-vue-dropdowns";
Vue.use(DropDownListPlugin);
import DataGridCustom from "@/modules/admin/category/EmployeeAssignmentCriteria/components/DataGrid";
import columns from "./columns.js";
import xlsx from "xlsx";
import moment from "moment";

export default {
    name:'VatTuBaoHong',
    components:{
        DataGridCustom
    },
    props:{
        phieu_id: {
            type: Number,
            default: 0
        },
        baohong_id: {
            type: Number,
            default: 0
        },
        thuebao_id: {
            type: Number,
            default: 0
        },
        vsua_vattu: {
            type: Number,
            default: 0
        },
        vdonvi_nhan_id: {
            type: Number,
            default: 0
        },
        vngay_ht: {
            type: String,
            default: ""
        },
        dichvuvt_id: {
            type: Number,
            default: 0
        },
    },
    data() {
        return {
            ...columns,
            enable: {
                tsbtnNhapMoi: false,
                tsbtnGhiLai: false,
                tsbtnXoa: false,
                tsbtnHuyBo: false,
                cboKieuThietBi: true,
                cboThietBi: true,
                cboTinhTrang: true,
            },
            ngay_ht: moment(new Date()).format("DD/MM/yyyy"),
            ds_trangbiadsl: [],
            trangbiadsl_id: null,
            ds_thietbi: [],
            thietbi_id: null,
            ds_donvitbi: [],
            donvitbi_id: null,
            txtSoLuong: "",
            ds_kieuthietbi: [],
            kieuthietbi_id: null,
            txtSeri: "",
            ds_tinhtrang: [],
            tinhtrang_id: null,
            txtGhiChu: "",
            ds_cap_thuhoi_vattu: [],
            rowVatTu: {},
            ds_vattu_thuebao_dangsudung: [],
            rowVatTuSD: {},
            vattu_id: 0,
            rvt_id: 0,
            donvi_id: 0,
            vdonvi_xuat_vt: 0,
            DONVI_VATTU: {
                CAI: 1,
                MET: 2,
                BO: 3
            },
            dsgiaophieutbi: [],

        }
    },
    methods: {
        resetForm() {
            this.ds_trangbiadsl = []
            this.trangbiadsl_id = null
            this.ds_thietbi = []
            this.thietbi_id = null
            this.ds_donvitbi = []
            this.donvitbi_id = null
            this.txtSoLuong = ""
            this.ds_kieuthietbi = []
            this.kieuthietbi_id = null
            this.txtSeri = ""
            this.ds_tinhtrang = []
            this.tinhtrang_id = null
            this.txtGhiChu = ""
            this.ds_cap_thuhoi_vattu = []
            this.ds_vattu_thuebao_dangsudung = []
        },
        SetButton(kieu) {
            this.enable.tsbtnNhapMoi = false;
            this.enable.tsbtnGhiLai = false;
            this.enable.tsbtnXoa = false;
            this.enable.tsbtnHuyBo = false;
            this.enable.cboKieuThietBi = true;
            this.enable.cboThietBi = true;
            this.enable.cboTinhTrang = true;
            if (kieu == -1) //Bat dau
            {
                this.enable.tsbtnGhiLai = true;
                this.enable.tsbtnHuyBo = true;
            }
            if (kieu == 0) //Bat dau
            {                
                this.enable.tsbtnNhapMoi = true;
            }
            if (kieu == 1) //Them moi
            {  
                this.enable.tsbtnGhiLai = true;
                this.enable.tsbtnHuyBo = true;
            }
            if (kieu == 2) //Huy
            {
                this.enable.tsbtnNhapMoi = true;
                this.enable.tsbtnXoa = true;
            }
            if (kieu == 3) //Edit
            {
                this.enable.tsbtnNhapMoi = true;
                this.enable.tsbtnXoa = true;
                this.enable.tsbtnGhiLai = true;
                this.enable.tsbtnHuyBo = true;
                this.enable.cboKieuThietBi = false;
                this.enable.cboThietBi = false;
            }
        },
        async sp_tt_trangbi(type, param) {
            let result = []
            try {
                let params = {
                    "type": type,
                    "param": param    
                }
                let response = await api.sp_tt_trangbi(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data;
                }
            } catch(e) {
                console.log(e);           
            }
            return result
        },
        async sp_tt_donvi_tbi(type, param) {
            let result = []
            try {
                let params = {
                    "type": type,
                    "param": param    
                }
                let response = await api.sp_tt_donvi_tbi(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data;
                }
            } catch(e) {
                console.log(e);           
            }
            return result
        },
        async sp_tt_kieu_tbi(type, param) {
            let result = []
            try {
                let params = {
                    "type": type,
                    "param": param    
                }
                let response = await api.sp_tt_kieu_tbi(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data;
                }
            } catch(e) {
                console.log(e);           
            }
            return result
        },
        async sp_tt_tinhtrang_tbi(type, param) {
            let result = []
            try {
                let params = {
                    "type": type,
                    "param": param    
                }
                let response = await api.sp_tt_tinhtrang_tbi(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data;
                }
            } catch(e) {
                console.log(e);           
            }
            return result
        },
        async lay_ds_thietbi(baohong_id) {
            let result = []
            try {
                let params = {
                    "baohong_id": baohong_id
                }
                let response = await api.ur_41003_10_000_lay_ds_thietbi(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async lay_ds_vattu_baohong(baohong_id) {
            let result = []
            try {
                let params = {
                    "vbaohong_id": baohong_id
                }
                let response = await api.ur_41003_10_002_css_lay_ds_vattu_baohong(this.axios, params);
                console.log(response)
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result =response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        Clean() {
            this.txtSoLuong = ""
            this.txtSeri = ""
            this.txtGhiChu = ""
            this.rvt_id = 0;
            this.vattu_id = 0;
            this.trangbiadsl_id = "4"    // TRANGBI.VIENTHONG_TRANGBI = 4
        },
        async fn_tt_loai_tbi(type, param) {
            let result
            try {
                let params = {
                    "type": type,
                    "param":param
                }
                let response = await api.fn_tt_loai_tbi(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async HienThiDS() {
            try {
                this.ds_cap_thuhoi_vattu = await this.lay_ds_vattu_baohong(this.baohong_id);
                if (this.ds_cap_thuhoi_vattu.length > 0) {
                    this.SetButton(3);
                } else {
                    this.Clean();
                    if (this.ds_thietbi.length > 0) {
                        this.donvi_id = Number(await this.fn_tt_loai_tbi(1, this.thietbi_id));
                        this.donvitbi_id = String(this.donvi_id);
                    }
                }
            } catch (ex) {
                this.$toast.error("Có lỗi : " + ex);
            }
        },
        async frmVatTuThueBao_Load() {
            try {
                this.loading(true)

                // loadForm
                this.resetForm()
                this.SetButton(-1);

                // loadCombo
                this.ds_trangbiadsl = await this.sp_tt_trangbi(2, "")
                this.trangbiadsl_id = "4"    // TRANGBI.VIENTHONG_TRANGBI = 4
                
                this.ds_donvitbi = await this.sp_tt_donvi_tbi(1, "")
                if (this.ds_donvitbi.length > 0) {
                    this.donvitbi_id = this.ds_donvitbi[0].donvi_tbi_id
                }

                this.ds_kieuthietbi = await this.sp_tt_kieu_tbi(1, "")
                if (this.ds_kieuthietbi.length > 0) {
                    this.kieuthietbi_id = this.ds_kieuthietbi[0].kieutbi_id
                }

                this.ds_tinhtrang = await this.sp_tt_tinhtrang_tbi(1, "")
                if (this.ds_tinhtrang.length > 0) {
                    this.tinhtrang_id = this.ds_tinhtrang[0].tinhtrangtbi_id
                }

                this.ds_thietbi = await this.lay_ds_thietbi(this.baohong_id)
                if (this.ds_thietbi.length > 0) {
                    this.thietbi_id = this.ds_thietbi[0].loaitbi_id
                }
                
                // loadGrid
                await this.HienThiDS();
                await this.HienThiDS_vattu_tb();

            } catch (e) {
                console.log(e)
                this.$toast.error(e)
            } finally {
                this.loading(false)
            }    
        },
        async Lay_ds_vattu_dbtb(thuebao_id) {
            let result = []
            try {
                let params = {
                    "thuebao_id": thuebao_id
                }
                let response = await api.ur_41003_10_003_lay_ds_vattu_dbtb(this.axios, params);
                console.log(response)
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result =response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async HienThiDS_vattu_tb() {
            try {
                if (this.thuebao_id != 0) {
                    this.ds_vattu_thuebao_dangsudung = await this.Lay_ds_vattu_dbtb(this.thuebao_id);
                    for (let i=0; i<this.ds_vattu_thuebao_dangsudung.length; i++) {
                        let id = "onThuHoi_" + this.ds_vattu_thuebao_dangsudung[i].stt
                        this.ds_vattu_thuebao_dangsudung[i].btnThuHoi = "<button class='btn btn-main' id='"+id+"'>Thu hồi</button>"
                    } 
                }
            } catch (ex) {
                this.$toast.error("Có lỗi : " + ex);
            }
        },
        cboTrangBiADSL_onFilter(e) {
            var query = new Query();
            query = (e.text !== '') ? query.where('tentrangbi', 'contains', e.text, true) : query;
            e.updateData(this.ds_trangbiadsl, query);
        },
        cboDonvi_TBI_onFilter(e) {
            var query = new Query();
            query = (e.text !== '') ? query.where('donvi_tbi', 'contains', e.text, true) : query;
            e.updateData(this.ds_donvitbi, query);
        },
        cboThietBi_onFilter(e) {
            var query = new Query();
            query = (e.text !== '') ? query.where('loai_tbi', 'contains', e.text, true) : query;
            e.updateData(this.ds_thietbi, query);
        },
        cboKieuThietBi_onFilter(e) {
            var query = new Query();
            query = (e.text !== '') ? query.where('kieu_tbi', 'contains', e.text, true) : query;
            e.updateData(this.ds_kieuthietbi, query);
        },
        cboTinhTrang_onFilter(e) {
            var query = new Query();
            query = (e.text !== '') ? query.where('tinhtrang_tbi', 'contains', e.text, true) : query;
            e.updateData(this.ds_tinhtrang, query);
        },
        async cboThietBi_EditValueChanged() {
            if (this.ds_thietbi.length > 0) {
                this.donvi_id = Number(await this.fn_tt_loai_tbi(1, this.thietbi_id));
                this.donvitbi_id = String(this.donvi_id);
            }
        },
        txtSoLuong_KeyPress(event) {
            if (event.keyCode == 190) {
                if (this.DONVI_VATTU.CAI == Number(this.donvitbi_id) || this.DONVI_VATTU.BO == Number(this.donvitbi_id)) {
                    event.preventDefault();
                }
            } else {
                if ((event.keyCode >= 96 && event.keyCode <= 105) || (event.keyCode >= 48 && event.keyCode <= 57) || event.keyCode == 8 || event.keyCode == 46) {
                    return true;
                } else {
                    event.preventDefault();    
                }
            }
        },
        gridVatTu_rowSelected(row) {
            this.rowVatTu = {}
            if (row.data != undefined) {
                this.rowVatTu = row.data
                this.SetButton(3);

                this.thietbi_id = String(this.rowVatTu.loaitbi_id);
                this.donvitbi_id = String(this.rowVatTu.donvi_tbi_id);
                this.kieuthietbi_id = String(this.rowVatTu.kieutbi_id);
                this.tinhtrang_id = String(this.rowVatTu.tinhtrangtbi_id);
                this.trangbiadsl_id = String(this.rowVatTu.trangbi_id); 
                this.txtSoLuong = String(this.rowVatTu.sl);
                this.txtSeri = String(this.rowVatTu.seri);
                this.txtGhiChu = String(this.rowVatTu.ghichu);

                this.vattu_id = Number(this.rowVatTu.vattu_id);
                this.rvt_id = Number(this.rowVatTu.rvt_id);
                this.vdonvi_xuat_vt = Number(this.rowVatTu.donvi_id);
            }
        },
        gridVatTuSD_rowSelected(row) {
            this.rowVatTuSD = {}
            if (row.data != undefined) {
                this.rowVatTuSD = row.data
            }   
        },
        gridVatTuSD_actionComplete() {
            if (this.ds_vattu_thuebao_dangsudung.length > 0) {
                for (let i=0; i<this.ds_vattu_thuebao_dangsudung.length; i++) {
                    let id = "onThuHoi_" + this.ds_vattu_thuebao_dangsudung[i].stt
                    var button = document.getElementById(id);
                    if (button != null && button != undefined) {
                        button.addEventListener("click", onThuHoi)
                    }
                }
            }
        },
        async TaoDuLieu_ThuHoi(in_loaitbi_id, in_kieutbi_id, in_trangbi_id, in_vattu_id, in_tinhtrangtbi_id, vsoluong, vseri) {
            try {
                this.dsgiaophieutbi = []
                let rowdata = {}
                rowdata.rvt_id = await this.GetKey("RVT_ID")
                rowdata.vattu_id = in_vattu_id;
                rowdata.tinhtrangtbi_id = in_tinhtrangtbi_id;
                rowdata.loaitbi_id = in_loaitbi_id;
                rowdata.kieutbi_id = in_kieutbi_id;
                rowdata.trangbi_id = in_trangbi_id;
                rowdata.phieu_id = this.phieu_id;
                rowdata.sl = vsoluong;
                rowdata.ngay_th = this.ngay_ht
                rowdata.seri = vseri;
                rowdata.nguoidung_id = this.$root.token.getNguoiDungID()
                rowdata.nhanvien_id = this.$root.token.getNhanVienID()
                rowdata.donvi_id = this.$root.token.getDonViID()
                rowdata.nguoi_cn = this.$root.token.getUserName()
                rowdata.ngay_cn = this.ngay_ht
                rowdata.may_cn = this.$root.token.getMachine()
                rowdata.ip_cn = this.$root.token.getIP()
                rowdata.ghichu = this.txtGhiChu;
                this.dsgiaophieutbi.push(rowdata);
            } catch (exp) {
                this.$toast.error("Có lỗi: " + exp);
            }
        },
        async Kiemtra_cap_vattu_bh(phieu_id, thuebao_id, loaitbi_id, kieutbi_id, seri, vattu_id, kieu) {
            let result = []
            try {
                let params = {
                    "vphieu_id": phieu_id,
                    "vthuebao_id": thuebao_id,
                    "vloaitbi_id": loaitbi_id,
                    "vkieutbi_id": kieutbi_id,
                    "vseri": seri,
                    "vvattu_id": vattu_id,
                    "vkieu": kieu
                }
                let response = await api.ur_41003_10_007_sp_kiemtra_cap_vattu_bh(this.axios, params);
                console.log(response)
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async gridVatTuSD_onThuHoi() {
            let stt = $("#thuhoi_stt").val()
            try {
                if (this.phieu_id == 0 || this.baohong_id == 0) {
                    this.$toast.error("Chưa có thông tin phiếu. Hãy kiểm tra lại!");
                    $("#txtSoLuong").focus();
                    return ;
                }

                let vloaitbi_id = Number(this.rowVatTuSD.loaitbi_id)
                let vkieutbi_id = 3
                let vtrangbi_id = Number(this.rowVatTuSD.trangbi_id)
                let vvattu_id = Number(this.rowVatTuSD.vattu_id)
                let vtinhtrangtbi_id = Number(this.rowVatTuSD.tinhtrangtbi_id)
                let vsoluong = Number(this.rowVatTuSD.sl)
                let vseri = String(this.rowVatTuSD.seri)

                let kt = await this.Kiemtra_cap_vattu_bh(this.phieu_id, this.thuebao_id, vloaitbi_id, vkieutbi_id, vseri, vvattu_id, 2)
                if (kt.length > 0) {
                    this.$toast.error("Dữ liệu đã tồn tại!");
                    return;
                }
                
                this.$bvModal.msgBoxConfirm('Bạn thật sự muốn thu hồi không ?', {
                    title: '', size: 'sm', okTitle: 'Đồng ý', cancelTitle: 'Hủy',
                }).then(async v => {
                    if (v) {
                        try {
                            this.loading(true)
                            await this.TaoDuLieu_ThuHoi(vloaitbi_id, vkieutbi_id, vtrangbi_id, vvattu_id, vtinhtrangtbi_id, vsoluong, vseri);
                            await this.sp_insert_update_vattu(this.vsua_vattu, this.dsgiaophieutbi, this.thuebao_id, this.$root.token.getUserName())
                            
                            await this.HienThiDS()
                            if (this.vsua_vattu == 1)
                                await this.HienThiDS_vattu_tb()
                        } catch (e) {
                            console.log(e)
                            this.$toast.error(e)
                        } finally {
                            this.loading(false)
                        }      
                    }
                })         
            } catch (ex) {
                this.$toast.error(ex);
            }
        },
        tsbtnNhapMoi_Click() {
            if (!this.enable.tsbtnNhapMoi) return
            this.Clean();
            this.SetButton(1);
        },
        Unformat_tien(s){
            s = s.toLowerCase().trim();
            let ketqua = "";
            for (let i = 0; i < s.length; i++) {
                if (String(s[i]) != "," && String(s[i]) != "." && String(s[i]) != " ") {
                    ketqua = ketqua + s[i];
                }
            }
            return ketqua.trim();
        },
        async KiemTraDuLieuNhap() {
            if (this.phieu_id == 0) {
                this.$toast.error("Chưa có thông tin phiếu. Hãy kiểm tra lại!");
                $("#txtSoLuong").focus();
                return false;
            }

            if (this.vsua_vattu == 1) {
                if (this.vdonvi_nhan_id != this.$root.token.getDonViID()) {
                    this.$toast.error("Phiếu không cùng đơn vị với đơn vị của user. Bạn không được phép cập nhật.");
                    return false;
                }
                
                if (this.vngay_ht != "") {
                    if (this.convert_DDMMYYYY_toYYMMMM(this.vngay_ht) != this.convert_DDMMYYYY_toYYMMMM(this.ngay_ht)) {
                        let vthang = await this.Add_months(this.convert_DDMMYYYY_toYYMMMM(this.ngay_ht), -1);
                        if (this.convert_DDMMYYYY_toYYMMMM(this.vngay_ht) != vthang) {
                            this.$toast.error("Bạn chỉ được phép thao tác phiếu thực hiện trong tháng hoặc tháng trước");
                            return;
                        }
                    }
                }
            }

            if (this.dichvuvt_id == 9) { // DichVuVienThong.TSL = 9
                if (this.vsua_vattu == 0 && this.enable.tsbtnNhapMoi == true) {
                    if (this.vdonvi_xuat_vt != this.$root.token.getDonViID()) {
                        this.$toast.error("Bạn không được cập nhật vật tư của đơn vị khác !");
                        return false;
                    }
                }
            }

            if (this.DONVI_VATTU.CAI == Number(this.donvitbi_id) || this.DONVI_VATTU.BO == Number(this.donvitbi_id)) {
                if (isNaN(this.Unformat_tien(this.txtSoLuong))) {
                    this.$toast.error("Dữ liệu nhập phải là số nguyên !");
                    $("#txtSoLuong").focus();
                    return false;
                }
            }

            let kt = await this.Kiemtra_cap_vattu_bh(this.phieu_id, this.thuebao_id, Number(this.thietbi_id), Number(this.kieuthietbi_id), 
                                this.txtSeri.trim(), 0, 1)
            if (this.enable.tsbtnNhapMoi == false && kt.length > 0) {
                this.$toast.error("Dữ liệu đã tồn tại!");
                return false;
            }

            let vkieu_md = await this.fn_tt_loai_tbi(2, this.thietbi_id);
            if (vkieu_md.trim() == "1") {
                if (this.txtSeri.trim() == "") {
                    this.$toast.error("Loại thiết bị này phải nhập seri.");
                    $("#txtSeri").focus();
                    return false;
                }
            }

            if (this.txtSeri.trim() != "") {
                if (Number(this.txtSoLuong.trim()) != 1) {
                    this.$toast.error("Vật tư này có seri, số lượng phải nhập = 1");
                    $("#txtSoLuong").focus();
                    return false;
                }
            }
            
            return true;
        },
        async GetKey(keyname) {
            let result = ""
            try {
                let params = {
                    "keyname": keyname,
                    "numblocksize": 0,
                    "numretry": 0
                }
                let response = await api.ur_41003_10_007_get_keys(this.axios, params);
                console.log(response)
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async TaoDuLieu(themmoi) {
            try {
                this.dsgiaophieutbi = []
                let rowdata = {}
                if (themmoi) {
                    if (Number(this.kieuthietbi_id) == 1)
                        this.vattu_id = await this.GetKey("VATTU_ID");
                    else
                        this.vattu_id = 0;

                    this.rvt_id = await this.GetKey("RVT_ID");
                }

                rowdata.rvt_id = this.rvt_id
                rowdata.vattu_id = this.vattu_id
                rowdata.tinhtrangtbi_id = Number(this.tinhtrang_id)
                rowdata.loaitbi_id = Number(this.thietbi_id)
                rowdata.kieutbi_id = Number(this.kieuthietbi_id)
                rowdata.trangbi_id = Number(this.trangbiadsl_id)
                rowdata.phieu_id = this.phieu_id;
                rowdata.sl = Number(this.txtSoLuong.trim())
                rowdata.ngay_th = this.ngay_ht
                rowdata.seri = this.txtSeri
                rowdata.nguoidung_id = this.$root.token.getNguoiDungID()
                rowdata.nhanvien_id = this.$root.token.getNhanVienID()
                rowdata.donvi_id = this.$root.token.getDonViID()
                rowdata.nguoi_cn = this.$root.token.getUserName()
                rowdata.ngay_cn = this.ngay_ht
                rowdata.may_cn = this.$root.token.getMachine()
                rowdata.ip_cn = this.$root.token.getIP()
                rowdata.ghichu = this.txtGhiChu;
                this.dsgiaophieutbi.push(rowdata);
            } catch (exp) {
               this.$toast.error("Có lỗi: " + exp)
            }
        },
        async sp_insert_update_vattu(p_sua_vattu, p_json_dsgiaophieutbi, p_thuebao_id, p_ma_nd) {
            try {
                let params = {
                    "p_sua_vattu": p_sua_vattu,
                    "p_json_dsgiaophieutbi": JSON.stringify(p_json_dsgiaophieutbi),
                    "p_thuebao_id": p_thuebao_id,
                    "p_ma_nd": p_ma_nd
                }
                let response = await api.ur_41003_10_007_sp_insert_update_vattu(this.axios, params);
                console.log(response)
                if (response && response.data && response.data.error_code === 'BSS-00000000') {
                    
                } else {
                    this.$toast.error(response.data.message)
                }        
            } catch (e) {
                console.log(e)
                this.$toast.error(e)
            }
        },
        async Update_bh(p_json_dsgiaophieutbi) {
            try {
                let params = {
                    "json_giaophieutbi": JSON.stringify(p_json_dsgiaophieutbi)
                }
                let response = await api.ur_41003_10_007_sp_sua_giaophieu_tbi_upd(this.axios, params);
                console.log(response)
                if (response && response.data && response.data.error_code === 'BSS-00000000') {
                    
                } else {
                    this.$toast.error(response.data.message)
                }        
            } catch (e) {
                console.log(e)
                this.$toast.error(e)
            }
        },
        async tsbtnGhiLai_Click() {
            try {
                this.loading(true)
                if (!await this.KiemTraDuLieuNhap()) return;

                let isContinue = true
                if (this.vsua_vattu == 1) {
                    if (this.enable.tsbtnNhapMoi == true) {
                        this.$toast.error("Bạn không được phép sửa đổi vật tư đã cấp cho khách hàng. Hãy thực hiện xóa vật tư và cấp mới lại.");
                        return;
                    }

                    this.$bvModal.msgBoxConfirm('Bạn chắc chắn muốn sửa đổi vật tư cho thuê bao ?', {
                            title: '', size: 'sm', okTitle: 'Đồng ý', cancelTitle: 'Hủy',
                        }).then(async v => {
                            if (!v) {
                                isContinue = false
                            }
                        })
                }

                if (!isContinue) return
                
                if (this.enable.tsbtnNhapMoi == false) {
                    await this.TaoDuLieu(true);
                    await this.sp_insert_update_vattu(this.vsua_vattu, this.dsgiaophieutbi, this.thuebao_id, this.$root.token.getUserName())

                    await this.HienThiDS();
                    if (this.vsua_vattu == 1)
                        await this.HienThiDS_vattu_tb();
                }
                else
                {
                    await this.TaoDuLieu(false);
                    await this.Update_bh(this.dsgiaophieutbi);

                    await this.HienThiDS();
                }

                this.SetButton(3);
                this.$toast.success("Cập nhật dữ liệu lên viễn thông tỉnh thành công!");
            } catch (exp) {
                console.log(e)
                this.$toast.error(e)
            } finally {
                this.loading(false)
            }
        },
        tsbtnHuyBo_Click() {
            if (!this.enable.tsbtnHuyBo) return
            this.SetButton(0);
        },
        async Add_months(vthang, vsothang) {
            let result = ""
            try {
                let params = {
                    "vthang": vthang,
                    "vsothang": vsothang
                }
                let response = await api.ur_41003_10_000_sp_add_months(this.axios, params);
                console.log(response)
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }    
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async tsbtnXoa_Click() {
            if (!this.enable.tsbtnXoa) return
            if (this.rvt_id == 0) {
                this.$toast.error("Chưa có thông tin phiếu để xóa");
                return;
            }

            if (this.vsua_vattu == 1) {
                if (this.vdonvi_nhan_id != this.$root.token.getDonViID()) {
                    this.$toast.error("Phiếu không cùng đơn vị với đơn vị của user. Bạn không được phép xóa.");
                    return;
                }

                if (this.vngay_ht != "") {
                    if (this.convert_DDMMYYYY_toYYMMMM(this.vngay_ht) != this.convert_DDMMYYYY_toYYMMMM(this.ngay_ht)) {
                        let vthang = await this.Add_months(this.convert_DDMMYYYY_toYYMMMM(this.ngay_ht), -1);
                        if (this.convert_DDMMYYYY_toYYMMMM(this.vngay_ht) != vthang) {
                            this.$toast.error("Bạn chỉ được phép thao tác phiếu thực hiện trong tháng hoặc tháng trước");
                            return;
                        }
                    }
                }
            }

            if (this.dichvuvt_id == 9) { // DichVuVienThong.TSL = 9
                if (this.vsua_vattu == 0) {
                    if (this.vdonvi_xuat_vt != this.$root.token.getDonViID()) {
                        this.$toast.error("Bạn không được xóa vật tư của đơn vị khác !");
                        return;
                    }
                }
            }

            this.$bvModal.msgBoxConfirm('Bạn thật sự muốn xóa dữ liệu không ?', {
                    title: '', size: 'sm', okTitle: 'Đồng ý', cancelTitle: 'Hủy',
                }).then(async v => {
                    if (v) {
                        try {
                            this.loading(true)

                            if (this.vsua_vattu == 0)
                                await this.Delete_bh(this.rvt_id);
                            else
                                await this.Xoa_cap_vattu_bh(this.rvt_id, this.thuebao_id);

                            await this.HienThiDS();
                            if (this.vsua_vattu == 1) {
                                await this.HienThiDS_vattu_tb();
                            }
                                
                            if (this.ds_cap_thuhoi_vattu.length <= 0) {
                                this.SetButton(-1);
                            }                  
                        } catch (e) {
                            console.log(e)
                            this.$toast.error(e)
                        } finally {
                            this.loading(false)
                        }
                    }
                })
        },
        async Delete_bh(p_rvt_id) {
            try {
                let params = {
                    "p_rvt_id": p_rvt_id
                }
                let response = await api.ur_41003_10_006_sp_del_giaophieu_tbi_bh(this.axios, params);
                console.log(response)
                if (response && response.data && response.data.error_code === 'BSS-00000000') {
                    
                } else {
                    this.$toast.error(response.data.message)
                }        
            } catch (e) {
                console.log(e)
                this.$toast.error(e)
            }
        },
        async Xoa_cap_vattu_bh(rvt_id, thuebao_id) {
            try {
                let params = {
                    "rvt_id": rvt_id,
                    "thuebao_id": thuebao_id
                }
                let response = await api.ur_41003_10_006_xoa_cap_vattu_bh(this.axios, params);
                console.log(response)
                if (response && response.data && response.data.error_code === 'BSS-00000000') {
                    
                } else {
                    this.$toast.error(response.data.message)
                }        
            } catch (e) {
                console.log(e)
                this.$toast.error(e)
            }
        },
        convert_DDMMYYYY_toYYMMMM(date) {
            let s = date.split('/');
            return s[2] + s[1]
        },
        tsbtnExcel_Click() {
            if (this.ds_cap_thuhoi_vattu.length == 0) {
                this.$toast.error("Không có dữ liệu để xuất Excel !");
                return;
            }

            let worksheet = xlsx.utils.json_to_sheet(this.ds_cap_thuhoi_vattu)
            let workbook = xlsx.utils.book_new()
            xlsx.utils.book_append_sheet(workbook, worksheet, "Sheet1")
            xlsx.writeFile(workbook, "export.xlsx")
        },
    },
}

function setSTTThuHoi(e) {
    var stt = ""
    for (let i=0; i<e.path.length; i++) {
        if (e.path[i].id != undefined && e.path[i].id != null && e.path[i].id != "") {
            stt = e.path[i].id.split("_")[1]
            break
        }
    }
    $("#thuhoi_stt").val(stt)
}

function onThuHoi(e) {
    setSTTThuHoi(e)
    document.getElementById("thuhoi").click()
}

</script>
<style>
    .non-ative a {
        color: #d3d3d3 !important;
    }
</style>