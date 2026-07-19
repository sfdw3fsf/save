<template src="./GiaoPhieu_PH.html"></template>
<script>
import api from "./GiaoPhieu_PH.js";
import DataGridCustom from "@/modules/admin/category/EmployeeAssignmentCriteria/components/DataGrid";
import columns from "./columns.js";
import moment from 'moment'
import { Query } from '@syncfusion/ej2-data'

export default {
    name:'GiaoPhieu_PH',
    props:{
        vquytrinh_id: {
            type: Number,
            default: null,
        },
        vphieu_id_cur: {
            type: Number,
            default: null,
        },
        vhdtb_id_cur: {
            type: Number,
            default: null,
        }
    },
    components:{
        DataGridCustom
    },
    data() {
        return {
            ...columns,
            ngay_ht: moment(new Date()).format("DD/MM/yyyy"),
            enable: {
                cboDichVuVT: false
            },
            dtpTuNgay: "",
            dtpDenNgay: "",
            lstDichVu: [],
            dichvu_id: null,
            txtMaTB: "",
            txtTenTB: "",
            txtDiaChiTB: "",
            lstHuongGiao: [],
            huonggiao_id: null,
            dtpNgayGiao: "",
            lstDonViNhan: [],
            ds_donvinhan_id: [],
            txtNoiDung: "",
            ds_phieuyeucau: [],
            ds_phieudagiao: [],
            chkTKDichVuVT: false,
            phieu_id: null,
            hdtb_id: null,
            nhanvien_giao_id: null,

        }
    },
    methods: {
        refresh() {
            this.dtpTuNgay = ""
            this.dtpDenNgay = ""
            this.dichvu_id = null
            this.txtMaTB = ""
            this.txtTenTB = ""
            this.txtDiaChiTB = ""
            this.huonggiao_id = null
            this.dtpNgayGiao = ""
            this.ds_donvinhan_id = []
            this.txtNoiDung = ""
            this.ds_phieuyeucau = []
            this.ds_phieudagiao = []
            this.chkTKDichVuVT = false
        },
        async openDialog() { 
            this.refresh()
            try {
                this.loading(true)
                await this.frmGiaoPhieu_PH_Load()
                this.$refs.dlg_giaophieu_ph.show()
            } catch (ex) {
                this.$toast.error(ex)
            } finally {
                this.loading(false)
            }  
        },
        async frmGiaoPhieu_PH_Load() {
            try {
                this.dtpTuNgay = "01/" + moment().format("MM") + "/" + moment().format("YYYY")
                this.dtpDenNgay = this.ngay_ht
                this.dtpNgayGiao = this.ngay_ht

                await this.LoadDichVuVT();
                await this.LoadHuongGiao();
                await this.HienThiDanhSach_ChuaGiao();
            } catch (ex) {
                this.$toast.error(ex)
            }
        },
        async LoadDichVuVT() {
            var params = { 
                "quytrinh_id": this.vquytrinh_id,
            }
            let response = await api.sp_giaophieu_ph_loaddichvuvt(this.axios, params);
            console.log(response)
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                this.lstDichVu = response.data.data.map((x)=>({id:x.dichvuvt_id, text:x.ten_dvvt}))
                if (this.lstDichVu.length > 0) {
                    this.dichvu_id = this.lstDichVu[0].id
                }
            }    
        },
        async LoadHuongGiao() {
            let response = await this.lay_ds_huonggiao_ph(this.vquytrinh_id);
            if (response.length > 0) {
                this.lstHuongGiao = response.map((x)=>({id:x.huonggiao_id, text:x.huonggiao}))
                this.huonggiao_id = this.lstHuongGiao[0].id
            }
        },
        async HienThiDanhSach_ChuaGiao() {
            this.ds_phieuyeucau  = []

            let dichvuvt_id = 0;
            if (this.chkTKDichVuVT) {
                dichvuvt_id = Number(this.dichvu_id);
            }

            var params = { 
                "huonggiao_id": this.huonggiao_id,
                "quytrinh_id": this.vquytrinh_id,
                "dichvuvt_id": dichvuvt_id,
                "dtpTuNgay": this.dtpTuNgay,
                "dtpDenNgay": this.dtpDenNgay,
            }
            let response = await api.lay_ds_phieu_chuagiao(this.axios, params);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                this.ds_phieuyeucau = response.data.data
            }

            let index = this.ds_phieuyeucau.findIndex(item => Number(item.phieu_id) == this.vphieu_id_cur)
            if (index >= 0) {
                this.$refs.ds_phieuyeucau.selectRow(index, true)
            }
        },
        chkTKDichVuVT_CheckedChanged() {
            try {
                if (this.chkTKDichVuVT) {
                    this.enable.cboDichVuVT = true;
                } else {
                    this.enable.cboDichVuVT = false;
                }
            } catch (exp) {
                this.$toast.error("Có lỗi xảy ra: " + exp);
            }
        },
        grvDSDaGiao_FocusedRowChanged() {

        },
        async grvDSChuaGiao_FocusedRowChanged(row) {
            try {
                this.loading(true)
                if (row.data != undefined) {
                    this.txtMaTB = row.data.MA_TB;
                    this.txtTenTB = row.data.TEN_TB;
                    this.txtDiaChiTB = row.data.DIACHI_LD;
                    this.phieu_id = Number(row.data.PHIEU_ID);
                    this.hdtb_id = Number(row.data.HDTB_ID)
                    this.nhanvien_giao_id = Number(row.data.NHANVIEN_GIAO_ID)

                    this.txtNoiDung = ""
                    this.dtpNgayGiao = this.ngay_ht

                    this.lstDonViNhan = []
                    this.ds_donvinhan_id = []

                    await this.LayDanhSachDonViNhan();
                    await this.HienThiDanhSach_DaGiao();
                }           
            } catch (exp) {
                this.$toast.error("Có lỗi xảy ra: " + exp);
            } finally {
                this.loading(false)
            }
        },
        async LayDanhSachDonViNhan() {
            var params = { 
                "huonggiao_id": this.huonggiao_id,
                "phieu_id": this.phieu_id
            }
            let response = await api.lay_ds_donvi_nhan_phieu_bh(this.axios, params);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                this.lstDonViNhan = response.data.data
            }
        },
        async HienThiDanhSach_DaGiao() {
            var params = { 
                "phieu_cha_id": this.phieu_id,
                "nhanvien_giao_id": 0
            }
            let response = await api.sp_lay_danhsach_phieu_ph_dagiao(this.axios, params);
            console.log(response)
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                this.ds_phieudagiao = response.data.data
            }
        },
        async lay_ds_huonggiao_ph(quytrinh_id) {
            let res = []
            try {
                let params = {
                    "quytrinh_id": quytrinh_id
                }
                let response = await api.lay_ds_huonggiao_ph(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    res = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return res
        },
        async fn_check_giaophieu(huonggiao_id, quytrinh_id, phieu_id) {
            let res = "Xảy ra lỗi"
            try {
                let params = {
                    "huonggiao_id": huonggiao_id,
                    "quytrinh_id": quytrinh_id,
                    "phieu_id": phieu_id
                }
                let response = await api.fn_check_giaophieu(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    res = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return res
        },
        async cboHuongGiao_EditValueChanged() {
            try {
                this.loading(true)
                await this.HienThiDanhSach_ChuaGiao();
            } catch (ex) {
                this.$toast.error(ex);
            } finally {
                this.loading(false)
            }
        },
        async tsbtnTimKiem_Click() {
            try {
                this.loading(true)
                await this.HienThiDanhSach_ChuaGiao();
            } catch (ex) {
                this.$toast.error("Có lỗi xảy ra: " + ex);
            } finally {
                this.loading(false)
            }
        },
        async tsbtnGiaoPhieu_Click() {
            try {
                this.loading(true)
                let kqCheck = await this.fn_check_giaophieu(this.huonggiao_id, this.vquytrinh_id, this.phieu_id);
                if (kqCheck != "OK") {
                    this.$toast.error(kqCheck);
                    return;
                }

                if (this.txtNoiDung.trim() == "") {
                    this.$toast.error("Hãy nhập nội dung giao");
                    $("#txtNoiDung").focus();
                    return;
                }

                if (this.ds_donvinhan_id.length == 0) {
                    this.$toast.error("Hãy chọn đơn vị nhận");
                    return;
                }

                let ds_ph = []
                this.ds_donvinhan_id.forEach(async (item) => {
                    let ten_dv = this.lstDonViNhan.find(dv => dv.donvi_id == item.donvi_id).ten_dv
                    let ktdvi = await this.KiemTraDonViGiaoPhieuPH(item.donvi_id, this.phieu_id, ten_dv);
                    if (ktdvi == "OK") {
                        let dr = {}
                        dr.PHIEU_ID = await this.GetKey("GIAOPHIEU");
                        dr.ND_GIAO = this.txtNoiDung.trim();
                        dr.HUONGGIAO_ID = this.huonggiao_id;
                        dr.DONVI_NHAN_ID = item.donvi_id;
                        dr.PHIEU_CHA_ID = this.phieu_id;
                        ds_ph.push(dr);
                    } else {
                        this.$toast.error(ktdvi);
                        return;
                    }
                })

                var kq = await this.GIAOPHIEU_PH_THICONG(ds_ph, this.hdtb_id, this.phieu_id)
                if (kq == "OK") {
                    this.$toast.success("Đã giao phiếu thành công");
                    await this.HienThiDanhSach_DaGiao();
                } else {
                    this.$toast.error(kq);
                }
            } catch (ex) {
                this.$toast.error(ex);
            } finally {
                this.loading(false)
            }
        },
        async KiemTraDonViGiaoPhieuPH(donvi_id, phieu_id, ten_dv) {
            let result = ""
            try {
                let params = {
                    "donvi_id": donvi_id,
                    "phieu_id": phieu_id,
                    "ten_dv": ten_dv
                }
                let response = await api.sp_kiemtra_donvi_giaophieu_ph(this.axios, params);
                console.log(response)
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async GIAOPHIEU_PH_THICONG(vs_giaophieu, vhdtb_id, vphieucha_id) {
            let result = ""
            try {
                let params = {
                    "vs_giaophieu": JSON.stringify(vs_giaophieu),
                    "vhdtb_id": vhdtb_id,
                    "vphieucha_id": vphieucha_id
                }
                let response = await api.sp_giaophieu_ph_thicong(this.axios, params);
                console.log(response)
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async GetKey(keyname) {
            let result = ""
            try {
                let params = {
                    "keyname": keyname,
                    "numblocksize": 0,
                    "numretry": 0
                }
                let response = await api.get_keys(this.axios, params);
                console.log(response)
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        onFilterDVNhan(e) {
            var query = new Query();
            query = (e.text !== '') ? query.where('ten_dv', 'contains', e.text, true) : query;
            e.updateData(this.lstDonViNhan, query);
        },
        async HUYGIAO_PH_THICONG(phieu_id) {
            let result = ""
            try {
                let params = {
                    "phieu_id": phieu_id
                }
                let response = await api.sp_huygiao_ph_thicong(this.axios, params);
                console.log(response)
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async tsbtnHuyGiao_Click(item) {
            this.$bvModal.msgBoxConfirm("Bạn chắc chắn muốn xóa phiếu?", {
                title: '', size: 'sm', okTitle: 'Đồng ý', cancelTitle: 'Hủy',
            }).then(async v => {
                if (v) {
                    try {
                        this.loading(true)
                        let result = await this.HUYGIAO_PH_THICONG(item.phieu_id)
                        if (result == "OK") {
                            this.$toast.success("Hủy giao phiếu thành công");
                            await this.HienThiDanhSach_DaGiao();
                        } else {
                            this.$toast.error(result);
                            return;
                        }
                    } catch (ex) {
                        this.$toast.error("Có lỗi trong quá trình xử lý dữ liệu : " + ex);
                    } finally {
                        this.loading(false)
                    }
                }
            });  
        },
    },
}
</script>