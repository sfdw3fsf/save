<template src="./TraPhieuBaoHong.html"></template>
<script>
import breadcrumb from '@/components/breadcrumb'
import DataGridCustom from "@/modules/admin/category/EmployeeAssignmentCriteria/components/DataGrid";
import columns from "./columns.js";
import api from "./TraPhieuBaoHong.js";
import Vue from 'vue';
import { DropDownListPlugin } from "@/components/duybn_components/Dropdowns/ej2-vue-dropdowns";
Vue.use(DropDownListPlugin);
import { Query } from '@syncfusion/ej2-data'
import moment from "moment";
import TRANGTHAI_PH from '../../enums.js'

export default {
    name:'TraPhieuBaoHong',
    components:{
        breadcrumb,
        DataGridCustom,
        TRANGTHAI_PH
    },
    data() {
        return {
            header: {
                title: 'Trả phiếu báo hỏng',
                list: [
                    { name: 'Báo hỏng', link: { name: 'Ui.cards' } },
                    { name: 'Trả phiếu báo hỏng', link: { name: 'Ui.cards' } },
                ]
            },
            ...columns,
            ma_tb: "",
            ngaygiao: "",
            kieugoi: 0,
            ngay_ht: moment(new Date()).format("DD/MM/yyyy"),
            ds_phieuyc_baohong: [],
            ds_phieuyc_baohong_selected: [],
            CHOPHEP_GIUPHIEU: -1,
            KHOAMO_TRAPHIEU_BH: 0,
            KHONG_TRAPHIEU_DV_TIEPNHAN: -1,
            ds_kieutra: [],
            kieutra_id: null,
            txtMaTB: "",
            txtNDTra: "",
            txtGhiChu: "",
            dtpNgayTra: null,
            dtpNgayGiao: null,
            txtSoQD: "",
            txtDonViGiao: "",
            kieu: 0,
            txtNguoiTN: "",
            txtNguoiLH: "",
            dtpTuNgay: null,
            dtpNgayBH: null,
            dtpDenNgay: null,
            txtGhiChuTra: "",
            txtTenTB: "",
            txtDiaChiTB: "",
            lvTinhTranhHong: "",
            txtNhanVienGiao: "",
            PHIEU_ID: 0,
            baohong_id: 0,
            enable: {
                dtpTuNgay: false,
                dtpDenNgay: false,
            },
            rowSelected: null,
            dsTraPhieu: [],

        }
    },
    methods: {
        initPopup(_ma_tb, _ngaygiao) {
            this.ma_tb = _ma_tb
            this.ngaygiao = _ngaygiao
            this.frmTraPhieuBH_Load()
        },
        initPopup(_ma_tb, _ngaygiao, _kieugoi) {
            this.ma_tb = _ma_tb
            this.ngaygiao = _ngaygiao
            this.kieugoi = _kieugoi
            this.frmTraPhieuBH_Load()
        },
        async LAY_DS_THAMSO_MD(p_kieu_id) {
            let result = []
            try {
                let params = {
                    "p_kieu_id": p_kieu_id
                }
                let response = await api.ur_41003_003_001_sp_lay_ds_thamso_md(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }         
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async HT_Loai_PhieuTra_Combobox(kieu) {
            this.ds_kieutra = []
            try {
                let params = {
                    "kieu": kieu
                }
                let response = await api.ur_41003_003_002_sp_ht_loai_phieutra_cbox(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    this.ds_kieutra = response.data.data
                }         
            } catch (e) {
                console.log(e)
            }
        },
        async frmTraPhieuBH_Load() {
            try {
                this.loading(true)
                let ds_kt1 = await this.LAY_DS_THAMSO_MD(0);
                if (ds_kt1.length > 0) {
                    for (let i=0; i<ds_kt1.length; i++) {
                        if (ds_kt1[i].ma_ts == "KHOAMO_TRAPHIEU_BH" && ds_kt1[i].ten_ts == "1") {
                            this.KHOAMO_TRAPHIEU_BH = 1
                        }

                        if (ds_kt1[i].ma_ts == "GIUPHIEU_BH" && ds_kt1[i].ten_ts == "1") {
                            this.CHOPHEP_GIUPHIEU = 1
                        }

                        if (ds_kt1[i].ma_ts == "KHONG_TRAPHIEU_DV_TIEPNHAN" && ds_kt1[i].ten_ts == "1") {
                            this.KHONG_TRAPHIEU_DV_TIEPNHAN = 1
                        }
                    }
                }

                await this.HT_Loai_PhieuTra_Combobox(1);
                await this.HienThiDanhSach(this.ma_tb, this.ngaygiao);
            } catch (e) {
                console.log(e)
                this.$toast.error("Xảy ra lỗi: " + e)
            } finally {
                this.loading(false)
            }         
        },
        async LAY_DS_PHIEU_YC_BH(vma_tb, vngaygiao, vdonvi_nhan_id, vphieu_id, vkieu_id) {
            let result = []
            try {
                let params = {
                    "vma_tb" : vma_tb,
                    "vngaygiao" : vngaygiao,
                    "vdonvi_nhan_id" : vdonvi_nhan_id ,
                    "vphieu_id" : vphieu_id ,
                    "vkieu_id" : vkieu_id
                }
                let response = await api.ur_41003_003_003_lay_ds_phieu_yc_bh_test(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }         
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async HienThiDanhSach(ma_tb, ngay_giao) {
            try {
                let dtList = await this.LAY_DS_PHIEU_YC_BH(ma_tb, ngay_giao, this.$root.token.getDonViID(), this.PHIEU_ID, this.kieu);
                if (dtList.length > 0) {
                    this.ds_phieuyc_baohong = dtList.slice()
                } else {
                    this.ds_phieuyc_baohong = []
                    this.txtMaTB = "";
                    this.txtNDTra = "";
                    this.txtGhiChu = "";
                    this.dtpNgayTra = this.ngay_ht
                    this.kieutra_id = "2"
                    this.dtpNgayGiao = this.ngay_ht
                    this.txtSoQD = "";
                    this.txtDonViGiao = "";
                }
                this.kieu = 0;
            } catch (e) {
                console.log(e)
            }
        },
        onFilterKieuTra(e) {
            var query = new Query();
            query = (e.text !== '') ? query.where('noidung', 'contains', e.text, true) : query;
            e.updateData(this.ds_kieutra, query);
        },
        gridPYCBH_rowSelected(row) {
            try {
                if (row.data == undefined) return;
                this.rowSelected = row.data

                this.PHIEU_ID = Number(row.data.phieu_id);
                this.baohong_id = Number(row.data.baohong_id);
                this.txtMaTB = row.data.ma_tb;
                this.dtpNgayBH = row.data.ngay_bh;
                this.txtNguoiTN = row.data.nguoi_tn;
                this.txtNguoiLH = row.data.nguoi_bh;

                if (row.data.hensuatu != undefined && row.data.hensuatu != "") {
                    this.dtpTuNgay = row.data.hensuatu;
                    $("#chkHenTL").prop("checked", true)
                    this.enable.dtpTuNgay = true;
                } else {
                    $("#chkHenTL").prop("checked", false)
                    this.enable.dtpTuNgay = false;
                }

                if (row.data.hensuaden != undefined && row.data.hensuaden != "") {
                    this.dtpDenNgay = row.data.hensuaden;
                    this.enable.dtpDenNgay = true;
                } else {
                    this.enable.dtpDenNgay = false;
                }
                    
                this.txtGhiChu = row.data.ghichu_hong;
                this.txtTenTB = row.data.ten_tb;
                this.txtDiaChiTB = row.data.diachi_tb;

                //Thong tin phieu giao
                this.dtpNgayGiao = row.data.ngaygiao;
                this.txtDonViGiao = row.data.donvi_giao;
                this.txtNhanVienGiao = row.data.nhanvien_giao;
                this.HienThiTinhTrangHong(this.baohong_id);
            } catch (ex) {
                this.$toast.error("Có lỗi: " + ex);
            }
        },
        async Lay_tinhtrang_theo_bhid(p_baohong_id) {
            let result = []
            try {
                let params = {
                    "p_baohong_id" : p_baohong_id
                }
                let response = await api.ur_41003_003_005_fn_lay_tinhtrang_theo_bhid(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }         
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async HienThiTinhTrangHong(baohong_id) {
            try {
                // this.lvTinhTranhHong = ""
                this.lvTinhTranhHong = await this.Lay_tinhtrang_theo_bhid(baohong_id);
                // if (ds.length > 0) {
                //     for (let i = 0; i < ds.length; i++) {
                //         this.lvTinhTranhHong += (ds[i].tinhtrang + "\n")
                //     }
                // }
            } catch (ex) {
                this.$toast.error("Có lỗi: " + ex);
            }
        },
        async txtMaTB_KeyPress(e) {
            if (e.keyCode == 13) {
                try {
                    this.loading(true)
                    this.ma_tb = this.txtMaTB.trim()
                    await this.HienThiDanhSach(this.ma_tb, this.ngaygiao);
                } catch (e) {
                    console.log(e)
                } finally {
                    this.loading(false)
                }
            }
        },
        async btnLayTTMoi_Click() {
            try {
                this.loading(true)
                await this.HienThiDanhSach(this.ma_tb, this.ngaygiao);
            } catch (ex) {
                this.$toast.error("Có lỗi: " + ex);
            } finally {
                this.loading(false)
            }
        },
        async fn_kt_trangthai_khoamo_tp(p_baohong_id) {
            let result
            try {
                let params = {
                    "p_baohong_id" : p_baohong_id
                }
                let response = await api.ur_41003_003_007_fn_kt_trangthai_khoamo_tp(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }         
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async fn_kt_vattu_baohong(p_baohong_id) {
            let result
            try {
                let params = {
                    "p_baohong_id" : p_baohong_id
                }
                let response = await api.ur_41003_003_007_02_fn_kt_vattu_baohong(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }         
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async btnTraphieu_Click() {
            try {
                this.loading(true)
                if (this.ds_phieuyc_baohong.length <= 0) return;
                if (this.txtNDTra == "") {
                    this.$toast.error("Bạn chưa nhập nội dung trả!");
                    $("#txtNDTra").focus();
                    return;
                }

                this.PHIEU_ID = Number(this.rowSelected.phieu_id);

                if (this.KHOAMO_TRAPHIEU_BH == 1) {
                    let kt = await this.fn_kt_trangthai_khoamo_tp(this.baohong_id)
                    if (Number(kt) > 0) {
                        this.$toast.error("Phiếu này đã bị khóa trả phiếu. Bạn không thể trả!");
                        return;
                    }
                }

                if (this.kieugoi == 19) {
                    this.$bvModal.msgBoxConfirm('Bạn thật sự muốn trả lại phiếu đã nhận không ?', {
                        title: '', size: 'sm', okTitle: 'Đồng ý', cancelTitle: 'Hủy',
                    }).then(async v => {
                        if (v) {
                            this.TraPhieuBD();
                        }
                    })                    
                } else {
                    try {
                        let kt = await this.fn_kt_vattu_baohong(this.baohong_id)
                        if (Number(kt) > 0) {
                            this.$toast.error("Bạn không thể trả phiếu báo hỏng do vẫn còn " + kt + " vật tư chưa được xóa.");
                            return;
                        }
                    } catch (ex) { }

                    if (this.CHOPHEP_GIUPHIEU == 1) {
                        if (!await this.KIEMTRA_PHIEU("0", this.PHIEU_ID, 0))
                            return;
                    }

                    this.$bvModal.msgBoxConfirm('Bạn thật sự muốn trả lại phiếu đã nhận không ?', {
                        title: '', size: 'sm', okTitle: 'Đồng ý', cancelTitle: 'Hủy',
                    }).then(async v => {
                        if (v) {
                            let kq = await this.CapNhat()
                            if (kq) {
                                this.$emit("closeTraPhieuBH")
                            }           
                        }
                    }) 
                }
            } catch (ex) {
                this.$toast.error("Có lỗi: " + ex);
            } finally {
                this.loading(false)
            }
        },
        async TRAPHIEU_BAODUONG_TTDH(vphieu_id, vnd_giao, vghichu) {
            let result = false
            try {
                let params = {
                    "vphieu_id" : vphieu_id,
                    "vnhanvien_id" : this.$root.token.getNhanVienID(),
                    "vnd_giao" : vnd_giao,
                    "vghichu" : vghichu,
                    "vdonvi_id" : this.$root.token.getDonViID(),
                    "vmay_cn" : this.$root.token.getUserName(),
                    "vnguoi_cn" : this.$root.token.getUserName()
                }
                let response = await api.ur_41003_003_007_01_traphieu_baoduong_ttdh(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000') {
                    result = true
                } else {
                    this.$toast.error(response.data.message)
                }        
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async TraPhieuBD() {
            try {
                let phieu_id = 0;
                phieu_id = Number(this.rowSelected.phieu_id);
                if (!await this.TRAPHIEU_BAODUONG_TTDH(phieu_id, this.txtNDTra, this.txtGhiChuTra)) return

                this.$toast.success("Đã trả phiếu thành công!");
                this.txtNDTra = "";
                this.kieutra_id = null
                this.txtMaTB = "";
                this.txtTenTB = "";
                this.txtDiaChiTB = "";
                this.txtNguoiTN = "";
                this.txtNguoiLH = "";
                this.lvTinhTranhHong = ""
                this.kieu = 1;
                this.HienThiDanhSach(this.ma_tb, this.ngaygiao);
            } catch (exp) {
                console.log(exp)
                this.$toast.error("Có lỗi trong quá trình trả phiếu !");
            }
        },
        gridPYCBH_SelectRow(val) {
            this.ds_phieuyc_baohong_selected = [];
            for (let index in val) {
                for (let ix in this.ds_phieuyc_baohong) {
                    if (val[index].phieu_id == this.ds_phieuyc_baohong[ix].phieu_id) {
                        this.ds_phieuyc_baohong_selected.push(val[index]);
                    }
                }
            }
        },
        async fn_kiemtra_traphieu(p_phieu_id) {
            let result
            try {
                let params = {
                    "p_phieu_id" : p_phieu_id,
                    "p_nhanvien_id" : this.$root.token.getNhanVienID()
                }
                let response = await api.ur_41003_003_007_04_fn_kiemtra_traphieu(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }      
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async KIEMTRA_PHIEU(danhsach, p_id, kieu) {
            try {
                let result = await this.fn_kiemtra_traphieu(p_id)
                if (result.ERROR_CODE == 0) {
                    if (result.MESSAGE != "") {
                        this.$toast.error(result.MESSAGE)
                    }
                    return false
                }
                return true
            } catch (ex) {
                this.$toast.error("Có lỗi khi kiểm tra " + ex);
                return false;
            }
        },
        async TraPhieu_BaoHong(ds_giaophieu) {
            let result = false
            try {
                let params = {
                    "ds_giaophieu" : JSON.stringify(ds_giaophieu)
                }
                let response = await api.ur_41003_003_007_03_fn_capnhat_traphieu_baohong(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000') {
                    result = true
                } else {
                    this.$toast.error(response.data.message)
                }        
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async lay_thongtin_phieu(phieu_id) {
            let result = []
            try {
                let params = {
                    "p_type": 1,
                    "p_param": phieu_id
                }
                let response = await api.sp_tt_giaophieu(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }    
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async TaoDuLieu() {
            try {
                this.dsTraPhieu = []
                this.PHIEU_ID = Number(this.rowSelected.phieu_id);

                let row = {}
                let ctPhieu = await this.lay_thongtin_phieu(this.PHIEU_ID)
                
                if (ctPhieu.length > 0) {
                    row.PHIEU_ID = ctPhieu[0].phieu_id
                    row.BAOHONG_ID = ctPhieu[0].baohong_id
                    row.NGAYGIAO = ctPhieu[0].ngaygiao
                    row.NGAY_DKHT = ctPhieu[0].ngay_dkht
                    row.TINHTRANG = ctPhieu[0].tinhtrang
                    row.ND_GIAO = ctPhieu[0].nd_giao
                    row.DONVI_GIAO_ID = ctPhieu[0].donvi_giao_id
                    row.NHANVIEN_GIAO_ID = ctPhieu[0].nhanvien_giao_id
                    row.DONVI_NHAN_ID = ctPhieu[0].donvi_nhan_id
                    row.NHANVIEN_TH_ID = ctPhieu[0].nhanvien_th_id
                    row.TTPH_ID = ctPhieu[0].ttph_id
                    row.PHIEU_CHA_ID = ctPhieu[0].phieu_cha_id
                    row.MAY_CN = ctPhieu[0].may_cn
                    row.NGAY_CN = ctPhieu[0].ngay_cn
                    row.NGUOI_CN = ctPhieu[0].nguoi_cn
                    row.HUONGGIAO_ID = ctPhieu[0].huonggiao_id
                    row.LOAIPT_ID = ctPhieu[0].loaipt_id
                    row.TTBH_ID = ctPhieu[0].ttbh_id
                    row.ND_TRA = ctPhieu[0].nd_tra
                    row.NGAYTRA = ctPhieu[0].ngaytra
                    row.GHICHU_TH = ctPhieu[0].ghichu_th
                    row.NHANVIEN_TRA_ID = ctPhieu[0].nhanvien_tra_id
                    row.LYDOTRA_CON_ID = ctPhieu[0].lydotra_con_id
                    row.ND_TRA_CON = ctPhieu[0].nd_tra_con
                    row.NGAYTRA_CON = ctPhieu[0].ngaytra_con
                    row.DAUCUOI_ID = ctPhieu[0].daucuoi_id
                }
                
                row.NGAYTRA = this.ngay_ht
                row.ND_TRA = this.txtNDTra;
                row.TTPH_ID = 3; // TRANGTHAI_PH.DATRALAI
                row.NHANVIEN_TRA_ID = this.$root.token.getNhanVienID()
                row.GHICHU_TH = this.txtGhiChuTra;
                row.NGUOI_CN = this.$root.token.getNguoiDungID()
                row.LOAIPT_ID = Number(this.kieutra_id);
                this.dsTraPhieu.push(row);
            } catch (exp) {
                console.log(exp)
            }
        },
        async Traphieu_giaophieu_ph(vdsTraPhieu) {
            let result = "Xảy ra lỗi"
            try {
                let params = {
                    "tinhthicong_id": 100,
                    "vdsTraPhieu": JSON.stringify(vdsTraPhieu)
                }
                let response = await api.traphieu_giaophieu_ph(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }    
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async CapNhat() {
            try {
                let phieu_cha_id = 0;
                phieu_cha_id = Number(this.rowSelected.phieu_cha_id);

                await this.TaoDuLieu();
                if (!await this.TraPhieu_BaoHong(this.dsTraPhieu)) return false

                try {
                    if (this.dsTraPhieu != null && this.dsTraPhieu.length > 0) {
                        let dem = await this.Traphieu_giaophieu_ph(this.dsTraPhieu)
                        if (dem == null || String(dem) != "ok") {
                            this.$toast.error("Lỗi khi trả phiếu KHDN: " + dem);
                            return false;
                        }
                    }
                } catch (ex) {
                    this.$toast.error("Lỗi khi trả phiếu KHDN: " + ex);
                    return false
                }

                this.$toast.success("Đã trả phiếu thành công!");
                this.txtNDTra = "";
                this.kieutra_id = null
                this.txtMaTB = "";
                this.txtTenTB = "";
                this.txtDiaChiTB = "";
                this.txtNguoiTN = "";
                this.txtNguoiLH = "";
                this.lvTinhTranhHong = ""
                this.kieu = 1;
                this.HienThiDanhSach(this.ma_tb, this.ngaygiao);
                return true
            } catch (exp) {
                console.log(exp)
                this.$toast.error("Có lỗi trong quá trình trả phiếu !");
                return false
            }
        }
    }
}
</script>