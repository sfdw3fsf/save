<template src="./ThayDoiTTBH.html"></template>
<script>
import api from "./ThayDoiTTBH.js";

export default {
    name:'ThayDoiTTBH',
    props:{
        matb_t: {
            type: String,
            default: ""
        },
        trangthaitb_t: {
            type: String,
            default: ""
        },
        tentb_t: {
            type: String,
            default: ""
        },
        ngaybh_t: {
            type: String,
            default: ""
        },
        nguoibh_t: {
            type: String,
            default: ""
        },
        dienthoailh_t: {
            type: String,
            default: ""
        },
        diachild_t: {
            type: String,
            default: ""
        },
        baohong_id_t: {
            type: Number,
            default: null
        },
    },
    components:{
        
    },
    data() {
        return {
            txtMaTB: "",
            txtTenTB: "",
            txtTrangthaiTB: "",
            dtpNgayBH: "",
            txtNguoiBHcu: "",
            txtDienThoaiLHcu: "",
            txtDiaChiLDCu: "",
            txtNguoiBHmoi: "",
            txtDienthoaiLHmoi: "",
            txtDiaChiLDMoi: "",
            KIEMTRA_QUYEN_SUA_LH_BH: -1,
            CAPNHAT_DIACHI_BAOHONG: -1,
        }
    },
    methods: {
        refresh() {
            this.txtMaTB = ""
            this.txtTenTB = ""
            this.txtTrangthaiTB = ""
            this.dtpNgayBH = ""
            this.txtNguoiBHcu = ""
            this.txtDienThoaiLHcu = ""
            this.txtDiaChiLDCu = ""
            this.txtNguoiBHmoi = ""
            this.txtDienthoaiLHmoi = ""
            this.txtDiaChiLDMoi = ""
        },
        async openDialog() { 
            this.refresh()          
            await this.frmThayDoiTTBH_Load_1()
            this.$refs.dlg_thaydoittbh.show()
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
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async frmThayDoiTTBH_Load_1() {
            try {
                this.loading(true)
                let ds_kt1 = await this.LAY_DS_THAMSO_MD();
                if (ds_kt1.length > 0) {
                    ds_kt1.forEach((item) => {
                        if (item.ma_ts == "KIEMTRA_QUYEN_SUA_LH_BH" && item.ten_ts == "1") {
                            this.KIEMTRA_QUYEN_SUA_LH_BH = 1
                        }
                        if (item.ma_ts == "CAPNHAT_DIACHI_BAOHONG" && item.ten_ts == "1") {
                            this.CAPNHAT_DIACHI_BAOHONG = 1
                        }
                    })
                }

                this.txtMaTB = this.matb_t;
                this.txtTrangthaiTB = this.trangthaitb_t;
                this.txtTenTB = this.tentb_t;
                this.dtpNgayBH = this.ngaybh_t;
                this.txtNguoiBHcu = this.nguoibh_t;
                this.txtDienThoaiLHcu = this.dienthoailh_t;
                this.txtDiaChiLDCu = this.diachild_t;
            } catch (e) {
                console.log(e)
                this.$toast.error(e)
            } finally {
                this.loading(false)
            }
        },
        tsbClear_Click() {
            this.txtNguoiBHmoi = "";
            this.txtDienthoaiLHmoi = "";
            $("#txtNguoiBHmoi").focus();
        },
        async Kiemtra_quyen_kh_goi_baohong_v2(vnhom_nd_id, vkieu) {
            let result = ""
            try {
                let params = {
                    "vnhom_nd_id": vnhom_nd_id,
                    "vkieu": vkieu
                }
                let response = await api.ur_41003_14_002_sp_kiemtra_quyen_kh_goi_baohong_v2(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async lay_nhom_nd_id() {
            let result = -1
            try {
                let response = await api.lay_nhom_nd_id(this.axios);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async sp_cn_thongtin_bh(capnhat_diachi_baohong, baohong_id, nguoi_bh, sodt_lh, donvi_id, nhanvien_id, ma_tb, diachi_ld) {
            let result = false
            try {
                let params = {
                    "capnhat_diachi_baohong": capnhat_diachi_baohong,
                    "baohong_id": baohong_id,
                    "nguoi_bh": nguoi_bh,
                    "sodt_lh": sodt_lh,
                    "donvi_id": donvi_id,
                    "nhanvien_id": nhanvien_id,
                    "ma_tb": ma_tb,
                    "diachi_ld": diachi_ld
                }
                let response = await api.ur_41003_14_002_sp_cn_thongtin_bh(this.axios, params);
                console.log(response)
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
        async tsbCapNhat_Click() {
            try {
                this.loading(true)
                if (this.KIEMTRA_QUYEN_SUA_LH_BH == 1) {
                    let nhom_nd_id = await this.lay_nhom_nd_id()

                    let kt = await this.Kiemtra_quyen_kh_goi_baohong_v2(nhom_nd_id, 2);
                    if (kt != "1")  {
                        this.$toast.error(kt);
                        return;
                    }
                }

                if (this.txtNguoiBHmoi == "") {
                    this.$toast.error("Hãy nhập tên người báo hỏng mới");
                    $("#txtNguoiBHmoi").focus()
                    return;
                }

                if (this.txtDienthoaiLHmoi == "") {
                    this.$toast.error("Hãy nhập số điện thoại liên hệ mới");
                    $("#txtDienthoaiLHmoi").focus()
                    return;
                }

                this.$bvModal.msgBoxConfirm('Bạn có muốn cập nhật thông tin này?', {
                    title: '', size: 'sm', okTitle: 'Đồng ý', cancelTitle: 'Hủy',
                }).then(async v => {
                    if (v) {
                        try {
                            if (await this.sp_cn_thongtin_bh(this.CAPNHAT_DIACHI_BAOHONG, this.baohong_id_t, this.txtNguoiBHmoi, this.txtDienthoaiLHmoi,
                                    this.$root.token.getDonViID(), this.$root.token.getNhanVienID(), this.matb_t, this.txtDiaChiLDMoi)) {
                                this.$toast.success("Cập nhật thành công");
                                this.$refs.dlg_thaydoittbh.hide()
                                this.$emit("updateSuccess")
                            }
                        } catch (error) {
                            this.$toast.error(error)
                        }
                    }
                });
            } catch (e) {
                console.log(e)
                this.$toast.error(e)
            } finally {
                this.loading(false)
            }
        }
    },
}
</script>