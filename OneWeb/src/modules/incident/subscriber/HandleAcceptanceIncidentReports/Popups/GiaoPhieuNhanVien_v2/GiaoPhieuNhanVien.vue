<template src="./GiaoPhieuNhanVien.html"></template>
<script>
import DataGridCustom from "@/modules/admin/category/EmployeeAssignmentCriteria/components/DataGrid";
import columns from "./columns.js";
import api from "./GiaoPhieuNhanVien.js";
import Vue from 'vue';
import { DropDownListPlugin } from "@/components/duybn_components/Dropdowns/ej2-vue-dropdowns";
Vue.use(DropDownListPlugin);
import { Query } from '@syncfusion/ej2-data'
import GiaoViecGrid from "../GiaoViecGrid.vue"

export default {
    name:'GiaoPhieuNhanVien',
    components:{
        DataGridCustom,
        GiaoViecGrid
    },
    data() {
        return {
            ...columns,
            vdonvi: null,
            vnhanvien_giao_id: null,
            vngaygiao: "",
            lstChon: [],
            ds_nhanvien: [],
            ds_nhanvien_dagiao_nhiemvu: [],
            ds_donvi: [],
            donvi_id: null,
            ds_tramtc: [],
            tramtc_id: null,
            tudong_sms_giaoviec: 0,
            GIAOVIEC_PHANCONG_THAYTHE: -1,
            QUYEN_XOA_GIAOVIEC_BH: -1,
            KHONG_THAYDOI_TT_GIAOVIEC_BH: -1,
            enable: {
                cboTramTC: false
            },
            vphieu: null,
            txtMaNV: "",
            vnhanvienid: null,
            dsnhanviengp: [],
            vnoidung: "",
            vghichu: "",
            chkDonViQL: false,
            chkToTC: true,
            columnsNVDG: [
                { fieldName: "ten_dv", headerText: "Đơn vị", textAlign: "left", width: "200px" },
                { fieldName: "ngaygiao", headerText: "Ngày giao", textAlign: "center", autoFit: "true" },
                { fieldName: "ma_nv", headerText: "Mã NV", textAlign: "left", autoFit: "true" },
                { fieldName: "ten_nv", headerText: "Tên NV", textAlign: "left", autoFit: "true" },
                { fieldName: "so_dt", headerText: "Điện thoại", textAlign: "left", autoFit: "true" },
                { fieldName: "nhiemvu", headerText: "Nhiệm vụ", textAlign: "center", autoFit: "true" },
                { fieldName: "trangthai", headerText: "Trạng thái", textAlign: "center", autoFit: "true" },
                { fieldName: "ghichu", headerText: "Ghi chú", textAlign: "left", autoFit: "true" },
                { fieldName: "", headerText: "", textAlign: "center",
                    template: function () {
                        return {
                            template: Vue.component('templateXoa', {
                                template: `
                                    <button class="btn btn-sm btn-danger lh14 pad3" @click="onClickButtonXoa(data)">
                                        <span class="-ap icon-close"></span>
                                    </button>
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
                                    onClickButtonXoa(value) {
                                        this.parent.repositoryItemButtonEdit2_Click(value)
                                    },
                                }
                            })
                        }
                    }
                },
            ]
            
        }
    },
    methods: {
        async Kiemtra_quyen_xoa_giaoviec() {
            let result = "Xảy ra lỗi"
            try {
                let response = await api.kiemtra_quyen_xoa_giaoviec(this.axios);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data                 
                }
            } catch(e) {
                console.log(e);
            }
            return result
        },
        async repositoryItemButtonEdit2_Click(row) {
            console.log(row)
            try {
                this.loading(true)
                if (this.QUYEN_XOA_GIAOVIEC_BH == 1) {
                    let kt = await this.Kiemtra_quyen_xoa_giaoviec();
                    if (kt != "ok") {
                        this.$toast.error(kt);
                        return;
                    }
                }

                await this.Delete_giaophieu_nv(this.vphieu, row.nhanvien_th_id);
                if (this.chkDonViQL)
                    await this.Hienthi_DS_cangiao_v2();
                else
                    await this.Hienthi_DS_cangiao_v1();
                await this.HienThiDanhSach_Dagiao_NV();
                this.$emit("giaophieunv_success")
            } catch (ex) {
               this.$toast.error(ex);
            } finally {
                this.loading(false)
            }
        },
        async LAY_DS_NHANVIEN_THEO_PHIEU_ID(phieu_id, kieu_id) {
            let result = []
            try {
                let params = {
                    "kieu_id": kieu_id,
                    "phieu_id": phieu_id
                }
                let response = await api.ur_41003_002_001_1_lay_ds_nhanvien_theo_phieu_id(this.axios, params);
                console.log(response)
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data                 
                }
            } catch(e) {
                console.log(e);
            }
            return result
        },
        async LAY_DS_NHIEMVU(vhuonggiao_id) {
            let result = []
            try {
                let params = {
                    "vhuonggiao_id": vhuonggiao_id
                }
                let response = await api.ur_41003_002_001_3_lay_ds_nhiemvu_hg_vsecond(this.axios, params);
                console.log(response)
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data                 
                }
            } catch(e) {
                console.log(e);
            }
            return result
        },
        async HT_DonVi_loaidv_Combobox(p_loaidv_id) {
            this.ds_donvi = []
            try {
                let params = {
                    "p_loaidv_id": p_loaidv_id,
                    "p_donvi_id": 0
                }
                let response = await api.ur_41003_002_001_4_sp_lay_donvi_quanly_ldv(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    this.ds_donvi = response.data.data.map((item) => ({id: item.donvi_id, text: item.ten_dv}))
                    if (this.ds_donvi.length > 0) {
                        this.donvi_id = this.ds_donvi[0].id
                    }
                }
            } catch(e) {
                console.log(e);
            }
        },
        async LAY_DS_THAMSO_MD_MATS(ma_ts) {
            let result = []
            try {
                let params = {
                    "ma_ts": ma_ts
                }
                let response = await api.ur_41003_002_001_5_lay_ds_thamso_md_mats(this.axios, params);
                console.log(response)
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data               
                }
            } catch(e) {
                console.log(e);
            }
            return result
        },
        async HienThiDanhSach_Dagiao_NV() {
            let ds = await this.LAY_DS_NHANVIEN_THEO_PHIEU_ID(this.vphieu, 2);
            this.ds_nhanvien_dagiao_nhiemvu = ds.slice()
        },
        async refreshGrid() {
            let ds = await this.LAY_DS_NHANVIEN_THEO_PHIEU_ID(this.vphieu, 1);
            if (ds.length > 0) {
                let revert = {}
                ds.forEach(item => {
                    if (revert[item.ma_nv]) {
                        revert[item.ma_nv].nhiemvu += "," + item.nhiemvu
                        revert[item.ma_nv].ghichu = revert[item.ma_nv].ghichu
                    } else {
                        revert[item.ma_nv] = item
                    }
                })
                this.$refs.gridNhanVien.dataSource = Object.values(revert)
            } else {
                this.$refs.gridNhanVien.dataSource = []
            }
        },
        async frmGiaoPhieuBHNV_v2_Load(lst, vdonvi, vnhanvien_giao_id, vngaygiao) {
            this.vdonvi = vdonvi
            this.vnhanvien_giao_id = vnhanvien_giao_id
            this.vngaygiao = vngaygiao
            try {           
                this.loading(true)
                this.lstChon = lst
                this.vphieu = Number(this.lstChon[0].phieu_id)

                this.ds_nhiemvu = await this.LAY_DS_NHIEMVU(0)

                // await this.HT_DonVi_loaidv_Combobox(3)  // LOAI_DV.DONVIQL_LD = 3
                await this.HT_DonVi_loaidv_Combobox("3,44,46")  // LOAI_DV.DONVIQL_LD = 3
                
                ds = await this.LAY_DS_THAMSO_MD_MATS("TUDONG_SMS_GIAOVIEC")
                if (ds.length > 0) {
                    if (ds[0].ten_ts != "") {
                        this.tudong_sms_giaoviec = Number(ds[0].ten_ts);
                    }
                }

                let ds = await this.LAY_DS_NHANVIEN_THEO_PHIEU_ID(this.vphieu, 1);
                if (ds.length > 0) {
                    let revert = {}
                    ds.forEach(item => {
                        if (revert[item.ma_nv]) {
                            revert[item.ma_nv].nhiemvu += "," + item.nhiemvu
                            revert[item.ma_nv].ghichu = revert[item.ma_nv].ghichu
                        } else {
                            revert[item.ma_nv] = item
                        }
                    })
                    this.$refs.gridNhanVien.dataSource = Object.values(revert)
                } else {
                    this.$toast.error("Không có nhân viên nào !");
                    this.$refs.gridNhanVien.dataSource = []
                    return;
                }

                await this.HienThiDanhSach_Dagiao_NV();
                
            } catch (e) {
                console.log(e)
                this.$toast.error(e)
            } finally {
                this.loading(false)
            }
        },
        async chkToTC_CheckedChanged() {
            try {
                this.loading(true)
                this.enable.cboTramTC = document.getElementById("chkToTC").checked;
                if (this.ds_tramtc != null && this.ds_tramtc.length > 0) {
                    this.tramtc_id = this.ds_tramtc[0].id
                    await this.Hienthi_DS_cangiao_v2();
                }
            } finally {
                this.loading(false)
            }
        },
        async LAY_DS_NHANVIEN_THEO_PHIEU_V2(vphieu_id, vdonvi_id, vkieu_id) {
            let result = []
            try {
                let params = {
                    "vdonvi_id": vdonvi_id,
                    "vkieu_id": vkieu_id,
                    "vphieu_id": vphieu_id
                }
                let response = await api.ur_41003_002_004_lay_ds_nhanvien_theo_phieu_v2(this.axios, params);
                console.log(response)
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data               
                }
            } catch(e) {
                console.log(e);
            }
            return result
        },
        async Hienthi_DS_cangiao_v2() {
            let ds = []
            if (this.chkToTC && this.tramtc_id != null) {
                ds = await this.LAY_DS_NHANVIEN_THEO_PHIEU_V2(this.vphieu, Number(this.tramtc_id), 1);
            } else {
                if (this.chkDonViQL && this.donvi_id != null) {
                    ds = await this.LAY_DS_NHANVIEN_THEO_PHIEU_V2(this.vphieu, Number(this.donvi_id), 2);
                } else {
                    ds = []
                }
            }

            if (ds.length > 0) {
                let revert = {}
                ds.forEach(item => {
                    if (revert[item.ma_nv]) {
                        revert[item.ma_nv].nhiemvu += "," + item.nhiemvu
                        revert[item.ma_nv].ghichu = revert[item.ma_nv].ghichu
                    } else {
                        revert[item.ma_nv] = item
                    }
                })
                this.$refs.gridNhanVien.dataSource = Object.values(revert)
            } else {
                this.$refs.gridNhanVien.dataSource = []
            }
        },
        async Lay_donvi_theo_manv(vma_nv) {
            let result = []
            try {
                let params = {
                    "vma_nv": vma_nv
                }
                let response = await api.ur_41003_002_005_lay_donvi_theo_manv(this.axios, params);
                console.log(response)
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data               
                }
            } catch(e) {
                console.log(e);
            }
            return result
        },
        async txtMaNV_KeyPress(e) {
            if (e.keyCode == 13) {
                try {
                    this.loading(true)
                    let dsDV = await this.Lay_donvi_theo_manv(this.txtMaNV.trim());
                    if (dsDV != null && dsDV.length > 0) {
                        if (dsDV[0].donvi_id == "-1") {
                            this.$toast.error("Không tìm thấy thông tin về mã nhân viên vừa nhập! (Kiểm tra Mã NV, Mã ND hoặc Loại ĐV)");
                            return;
                        } else {
                            $("#chkDonViQL").prop("checked", true)
                            this.donvi_id = dsDV[0].ttvt_id
                            this.tramtc_id = dsDV[0].donvi_id

                            for (let i = 0; i < this.$refs.gridNhanVien.dataSource.length; i++) {
                                if (this.$refs.gridNhanVien.dataSource[i].ma_nv == this.txtMaNV.trim())  {
                                    this.$refs.gridNhanVien.$refs.grid.selectRow(i)
                                    break
                                }
                            }
                        }
                    }
                } catch (ex) {
                    this.$toast.error(ex);
                } finally {
                    this.loading(false)
                }   
            }
        },
        async HT_DonViCon_loaidv_Combobox(donvi_id, loaidv_id) {
            let result = []
            try {
                let params = {
                    "donvi_id": donvi_id,
                    "loaidv_id": loaidv_id,
                    "phanvung_id": this.$root.token.getPhanVungID()
                }
                let response = await api.ur_41003_002_006_ht_donvicon_loaidv_combobox(this.axios, params);

                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data.map((item) => ({id: item.donvi_id, text: item.ten_dv}))

                }
            } catch(e) {
                console.log(e);
            }
            return result
        },
        async cboDonVi_EditValueChanged() {
            try {
                this.loading(true)
                let GIAOPHIEU_BH_NHOM_XLSC = -1;
                let ds1 = await this.LAY_DS_THAMSO_MD_MATS("GIAOPHIEU_BH_NHOM_XLSC");
                if (ds1.length > 0) {
                    if (ds1[0].ten_ts != "") {
                        GIAOPHIEU_BH_NHOM_XLSC = Number(ds1[0].ten_ts);
                    }
                }

                this.ds_tramtc = []
                this.chkToTC = false

                let ldv = "";
                if (GIAOPHIEU_BH_NHOM_XLSC == 1)
                    ldv = "620";   // LOAI_DV.NHOM_XLSC = 620
                else
                    ldv = "5" // LOAI_DV.NHOM_XLSC = 5

                this.ds_tramtc = await this.HT_DonViCon_loaidv_Combobox(Number(this.donvi_id), ldv);
                if (document.getElementById("chkDonViQL").checked || document.getElementById("chkToTC").checked) {
                    await this.Hienthi_DS_cangiao_v2()
                }            
            } catch (ex) {
                this.$toast.error("Có lỗi : " + ex);
            } finally {
                this.loading(false)
            }   
        },
        async cboTramTC_EditValueChanged() {
            try {
                this.loading(true)
                await this.Hienthi_DS_cangiao_v2();
            } finally {
                this.loading(false)
            }        
        },
        onFilterDonVi(e) {
            var query = new Query();
            query = (e.text !== '') ? query.where('TEN_DV', 'contains', e.text, true) : query;
            e.updateData(this.ds_donvi, query);
        },
        onFilterTramTC(e) {
            var query = new Query();
            query = (e.text !== '') ? query.where('TEN_DV', 'contains', e.text, true) : query;
            e.updateData(this.ds_tramtc, query);
        },
        async Delete_giaophieu_nv(phieu_id, vds_nhanvien_id) {
            let result = []
            try {
                let params = {
                    "phieu_id": phieu_id,
                    "p_ds_nhanvien_th_id": vds_nhanvien_id
                }
                let response = await api.ur_41003_002_009_1_sp_delete_giaophieu_nv(this.axios, params);
                console.log(response)
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data               
                }
            } catch(e) {
                console.log(e);
            }
            return result
        },
        async Lay_nhanvien_thaythe_bh(vnhanvien_id) {
            let result = []
            try {
                let params = {
                    "vnhanvien_id": vnhanvien_id
                }
                let response = await api.ur_41003_002_009_2_lay_nhanvien_thaythe_bh(this.axios, params);
                console.log(response)
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data               
                }
            } catch(e) {
                console.log(e);
            }
            return result
        },
        async fn_tt_nhanvien(type, param) {
            let result
            try {
                let params = {
                    "type": type,
                    "param": param
                }
                let response = await api.fn_tt_nhanvien(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data                
                }
            } catch(e) {
                console.log(e);
                this.$toast.error(e)
            }
            return result
        },
        async TaoDuLieu() {
            this.dsnhanviengp = []
            for (let i = 0; i < this.$refs.gridNhanVien.dataSource.length; i++) {
                if (this.$refs.gridNhanVien.dataSource[i].nhiemvu != undefined && this.$refs.gridNhanVien.dataSource[i].nhiemvu != null 
                        && this.$refs.gridNhanVien.dataSource[i].nhiemvu != "") {
                    let arr_nhiemvu = this.$refs.gridNhanVien.dataSource[i].nhiemvu.split(";");
                    for (let j = 0; j < arr_nhiemvu.length; j++) {
                        let row = {}
                        this.vnhanvienid = Number(this.$refs.gridNhanVien.dataSource[i].nhanvien_th_id);
                        this.vnoidung = this.$refs.gridNhanVien.dataSource[i].nhiemvu.replace(";;", ",")
                        this.vghichu = this.$refs.gridNhanVien.dataSource[i].ghichu
                        let vso_dt = this.$refs.gridNhanVien.dataSource[i].so_dt

                        this.lstChon.forEach(item => {
                            let row = {}
                            row.PHIEU_ID = Number(item.phieu_id);
                            row.NHANVIEN_TH_ID = this.vnhanvienid;
                            row.SO_DT = vso_dt;
                            row.NHIEMVU = this.vnoidung;
                            row.GHICHU = this.vghichu;
                            row.NHANVIEN_GIAO_ID = this.vnhanvien_giao_id;
                            row.NGAYGIAO = this.vngaygiao;
                            this.dsnhanviengp.push(row);
                        })
                    }
                }
            }
        },
        async Insert_Giaophieu_NV(dsnhanviengp) {
            try {
                let params = {
                    "json_giaophieu_nv": JSON.stringify(dsnhanviengp)
                }
                let response = await api.ur_41003_002_009_4_sp_insert_giaophieu_nv(this.axios, params);
                console.log(response)
                if (response && response.data && response.data.error_code === 'BSS-00000000') {
                                 
                }
            } catch(e) {
                console.log(e);
            }
        },
        async LAY_NOIDUNG_SMS_GIAOVIEC_V2(vkieu, vhdtb_id, vthuebao_id, vma_tb, vnhiemvu, vghichu, vphieu_id) {
            let result
            try {
                let params = {
                    "vghichu": vghichu,
                    "vhdtb_id": vhdtb_id,
                    "vkieu": vkieu,
                    "vma_tb": vma_tb,
                    "vnhiemvu": vnhiemvu,
                    "vphieu_id": vphieu_id,
                    "vthuebao_id": vthuebao_id
                }
                let response = await api.ur_41003_002_009_5_lay_noidung_sms_giaoviec_nd(this.axios, params);
                console.log(response)
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data         
                }
            } catch(e) {
                console.log(e);
            }
            return result
        },
        async CapNhat_status(p_phieu_id, p_nhanvien_th_id) {
            try {
                let params = {
                    "p_nhanvien_th_id": p_nhanvien_th_id,
                    "p_phieu_id": p_phieu_id
                }
                let response = await api.ur_41003_002_009_6_sp_capnhat_status(this.axios, params);
                console.log(response)
                if (response && response.data && response.data.error_code === 'BSS-00000000') {
                                 
                }
            } catch(e) {
                console.log(e);
            }
        },
        async SendSMS(msisdn, content) {
            try {
                let params = {
                    "msisdn": msisdn,
                    "content": content
                }
                let response = await api.ur_41003_002_009_7_send_SMS(this.axios, params);
                console.log(response)
                if (response && response.data && response.data.error_code === 'BSS-00000000') {
                                 
                }
            } catch(e) {
                console.log(e);
            }
        },
        async tsbtnChapNhan_Click() {
            try {
                this.loading(true)
                let kt = false;

                for (let i = 0; i < this.$refs.gridNhanVien.dataSource.length; i++) {
                    if (this.$refs.gridNhanVien.dataSource[i] != undefined && this.$refs.gridNhanVien.dataSource[i] != null && 
                            this.$refs.gridNhanVien.dataSource[i].nhiemvu != null && String(this.$refs.gridNhanVien.dataSource[i].nhiemvu) != "") {
                        kt = true;

                        if (this.$refs.gridNhanVien.dataSource[i].so_dt != undefined && this.$refs.gridNhanVien.dataSource[i].so_dt != null &&
                                String(this.$refs.gridNhanVien.dataSource[i].so_dt).length > 15) {
                            this.$toast.error("Số điện thoại không được quá 15 ký tự!");
                            this.$refs.gridNhanVien.$refs.grid.selectRow(i)
                            return;
                        }
                    }             
                }

                if (!kt) {
                    this.$toast.error("Hãy nhập nhiệm vụ cho nhân viên!");
                    return;
                }

                let vds_nhanvien_id = ""
                for (let i=0; i<this.$refs.gridNhanVien.dataSource.length; i++) {
                    if (this.$refs.gridNhanVien.dataSource[i].nhiemvu != undefined && this.$refs.gridNhanVien.dataSource[i].nhiemvu != null &&
                        this.$refs.gridNhanVien.dataSource[i].nhiemvu != "") {
                        vds_nhanvien_id += (this.$refs.gridNhanVien.dataSource[i].nhanvien_th_id + ",")
                    }
                }

                if (vds_nhanvien_id.length > 0) {
                    vds_nhanvien_id = vds_nhanvien_id.substring(0, vds_nhanvien_id.length - 1)
                }
                this.lstChon.forEach(async(item) => {
                    await this.Delete_giaophieu_nv(item.phieu_id, vds_nhanvien_id);
                })

                await this.TaoDuLieu();

                await this.Insert_Giaophieu_NV(this.dsnhanviengp);

                if (this.tudong_sms_giaoviec == 1) {
                    for (let i = 0; i < this.dsnhanviengp.length; i++) {
                        try {
                            for (let j=0; j<this.lstChon.length; j++) {
                                let vthuebao_id = Number(this.lstChon[j].thuebao_id);
                                let vma_tb = this.lstChon[j].ma_tb;
                                let vphieu_id = Number(this.lstChon[j].phieu_id);

                                this.vnoidung = await this.LAY_NOIDUNG_SMS_GIAOVIEC_V2(2, 0, vthuebao_id, vma_tb,
                                                this.dsnhanviengp[i].nhiemvu, this.dsnhanviengp[i].ghichu, vphieu_id);

                                await this.SendSMS(this.dsnhanviengp[i].so_dt, this.vnoidung);
                                await this.CapNhat_status(this.vphieu, Number(this.dsnhanviengp[i].nhanvien_th_id))
                            }   
                        } catch (ex) {
                            console.log(ex)
                        }
                    }
                }

                await this.HienThiDanhSach_Dagiao_NV();
                this.$emit("giaophieunv_success")
                this.$toast.success("Cập nhật thành công!");
            } catch (ex) {
                this.$toast.error("Có lỗi trong quá trình giao việc nhân viên " + ex);
            } finally {
                this.loading(false)
            }
        },
        async Hienthi_DS_cangiao_v1() {
            let ds = await this.LAY_DS_NHANVIEN_THEO_PHIEU_ID(this.vphieu, 1);
            if (ds.length > 0) {
                let revert = {}
                ds.forEach(item => {
                    if (revert[item.ma_nv]) {
                        revert[item.ma_nv].nhiemvu += "," + item.nhiemvu
                        revert[item.ma_nv].ghichu = revert[item.ma_nv].ghichu
                    } else {
                        revert[item.ma_nv] = item
                    }
                })
                this.$refs.gridNhanVien.dataSource = Object.values(revert)
            } else {
                this.$refs.gridNhanVien.dataSource = []
            }
        },
        async Delete_giaophieu_nv_all(p_ds_phieu_id) {
            try {
                let params = {
                    "p_ds_phieu_id": p_ds_phieu_id
                }
                let response = await api.ur_41003_002_010_sp_xoa_giaophieu_nv_all(this.axios, params);
                console.log(response)
                if (response && response.data && response.data.error_code === 'BSS-00000000') {
                                 
                }
            } catch(e) {
                console.log(e);
            }
        },
        async tsbtnXoaNVGiao_Click() {
            try {           
                if (this.ds_nhanvien_dagiao_nhiemvu.length <= 0) {
                    this.$toast.error("Không có dữ liệu nhân viên đã giao để xóa!");
                    return;
                }

                this.$confirm('Bạn thật sự muốn xóa tất cả các phiếu giao việc nhân viên không?', {
                    confirmButtonText: 'Có', cancelButtonText: 'Không',
                }).then(async v => {
                    if (v) {
                        try {
                            this.loading(true)
                            await this.Delete_giaophieu_nv_all(this.vphieu);
                            this.ds_nhanvien_dagiao_nhiemvu = []
                            if (document.getElementById("chkDonViQL").checked) {
                                await this.Hienthi_DS_cangiao_v2();
                            } else {
                                await this.Hienthi_DS_cangiao_v1();
                            }
                            this.$emit("giaophieunv_success")
                        } catch (ex) {
                            this.$toast.error(ex);
                        } finally {
                            this.loading(false)
                        }
                    }
                })
            } catch (ex) {
                this.$toast.error(ex);
            }
        },
        async sp_capnhat_status_theophieu(p_phieu_id) {
            try {
                let params = {
                    "p_phieu_id": p_phieu_id
                }
                let response = await api.ur_41003_002_011_sp_capnhat_status_theophieu(this.axios, params);
                console.log(response)
                if (response && response.data && response.data.error_code === 'BSS-00000000') {
                                 
                }
            } catch(e) {
                console.log(e);
            }
        },
        async tsbtnSMS_Click() {
            try {
                if (this.ds_nhanvien_dagiao_nhiemvu.length <= 0) {
                    this.$toast.error("Chưa có thông tin giao việc. Hãy kiểm tra lại!");
                    return;
                }

                this.$confirm('Bạn có chắc chắn gửi SMS đến các nhân viên thực hiện nhiệm vụ không?', {
                    confirmButtonText: 'Có', cancelButtonText: 'Không',
                }).then(async v => {
                    if (v) {
                        try {
                            this.loading(true)
                            let vnoidung = "";
                            for (let i = 0; i < this.ds_nhanvien_dagiao_nhiemvu.length; i++) {
                                if (this.ds_nhanvien_dagiao_nhiemvu[i].so_dt != "") {
                                    for (let j=0; j<this.lstChon.length; j++) {
                                        try {
                                            let vthuebao_id = Number(this.lstChon[j].thuebao_id);
                                            let vma_tb = this.lstChon[j].ma_tb;
                                            let vphieu_id = Number(this.lstChon[j].phieu_id);

                                            vnoidung = await this.LAY_NOIDUNG_SMS_GIAOVIEC_V2(2, 0, vthuebao_id, vma_tb, 
                                                                this.ds_nhanvien_dagiao_nhiemvu[i].nhiemvu, this.ds_nhanvien_dagiao_nhiemvu[i].ghichu, vphieu_id)

                                            await this.SendSMS(this.ds_nhanvien_dagiao_nhiemvu[i].so_dt, vnoidung);
                                            await this.sp_capnhat_status_theophieu(vphieu_id);
                                        } catch (e) {
                                            console.log(e)
                                            continue
                                        }  
                                    }
                                }
                            }
                 
                            await this.HienThiDanhSach_Dagiao_NV();
                            this.$toast.success("Đã gửi tin nhắn đến các nhân viên thực hiện!");
                        } catch (ex) {
                            this.$toast.error(ex);
                        } finally {
                            this.loading(false)
                        }
                    }
                })
            } catch (ex) {
                this.$toast.error(ex)
            }
        }
    },
    watch: {      
        'donvi_id': async function(val){
            this.cboDonVi_EditValueChanged()
        },
        'tramtc_id': async function(val){
            this.cboTramTC_EditValueChanged()
        },
    }
}
</script>
