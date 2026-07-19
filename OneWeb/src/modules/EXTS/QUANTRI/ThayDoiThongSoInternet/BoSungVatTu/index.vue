<template src="./index.html">
</template>

<script>
let templateChon = function() {
    return {
        template: {
            template: `
                    <div class="">
                        <input type="checkbox" class="check" :checked="isCheck" :value="value" @click="chon(value)">
                    </div> 
                `,
            data() {
                return {
                    data: {

                    }
                }
            },
            computed: {
                parent() {
                    return this.$parent.$parent.$parent.$parent.$parent;
                },
                value() {
                    return this.data.vattu_id;
                },
                isCheck() {
                    return this.data.ischeck;
                }
            },
            methods: {
                chon: function(id) {
                    this.data.ischeck = this.data.ischeck ? 0 : 1;
                    this.parent.chon(id, this.data.ischeck)
                }
            }
        },
    }
}
let templateSua = function() {
    return {
        template: {
            template: `
                    <div class="">
                        <input type="checkbox" class="check" :checked="isCheck" :value="value" @click="check_sua(value)">
                    </div> 
                `,
            data() {
                return {
                    data: {

                    }
                }
            },
            computed: {
                parent() {
                    return this.$parent.$parent.$parent.$parent.$parent;
                },
                value() {
                    return this.data.vattu_id;
                },
                isCheck() {
                    return this.data.bs_vt;
                }
            },
            methods: {
                check_sua: function(id) {
                    this.data.bs_vt = this.data.bs_vt ? 0 : 1;
                    this.parent.check_sua(id, this.data.bs_vt)
                }
            }
        },
    }
}

export default {
    props: {
        id: String,
        account: String,
        dichvu_vt: Number
    },
    data: function() {
        return {
            txt_MaTB: null,
            txt_TenTB: null,
            txt_SerialNum: null,
            txt_SerialGP: null,
            txt_GhiChu: null,
            txt_SoLuong: 1,
            dtp_NgaySD: null,
            cbo_DichVuVT: null,
            cbo_Kho: null,
            cbo_VatTu: null,
            cbo_LoHang: null,
            cbo_TinhTrangTBI: null,
            cbo_KieuTBI: null,
            cbo_TrangBi: null,
            cbo_DVTinh: null,
            trangthai_id: 2, // 1: hợp đồng, 2: danh bạ

            dsKho: [],
            dsDichVuVT: [],
            dsVatTu: [],
            dsLoHang: [],
            dsTinhTrangTBI: [],
            dsKieuTBI: [],
            dsTrangBi: [],
            dsDVTinh: [],
            dsVT_BS: [],
            dsVT_BS_Temp: [],

            dichvuvt_id: null,
            Ko_Nhap_Dung_Kho: false,
            isEnableMaTB: true,
            isEnableTenTB: true,
            indexRow: 0,

            loai_td: null,
            ma_tb: null,
            selected: null,
            donvi_id: null,
            thuebao_id: null,
            ngay_sd: null,
            thietbi_id: null,
            sudungvt_id: null,
            sua: null,
            sp: null,
            sn: null,
            vSN: null,

            templateChon: templateChon,
            templateSua: templateSua,
            isDisableBtnNhapMoi: false,
            isDisableBtnGhiLai: false,
            isDisableBtnHuy: false,
            isDisableBtnXoa: false
        }
    },
    methods: {
        formatDateYYYYMMDD: function(data) {
            let rs = null;
            if(data) {
                rs = data.substr(0, 10).split('/');
                if(rs[0].length < 4) {
                    rs = rs.reverse().join('-');
                }
            }
            return rs;
        },
        formatDateDDMMYYYY: function(data) {
            let rs = null;
            if(data) {
                rs = data.substr(0, 10).split('-');
                if(rs[0].length > 2) {
                    rs = rs.reverse().join('/');
                }
            }
            return rs;
        },
        showModal: async function() {
            try {
                this.loading(true);
                this.ma_tb = this.account // "hung81268";
                this.txt_MaTB = this.account // "hung81268";
                this.dichvuvt_id = this.dichvu_vt // 4;
                let rsKho = await this.$root.context.post("web-quantri/diaban-nvkt/sp_lay_ds_thamso_md_mats", {
                    ma_ts: 'KO_KT_KHO_DV'
                })
                if(rsKho.data && rsKho.data.length > 0) {
                    this.Ko_Nhap_Dung_Kho = true;
                }
                this.isEnableTenTB = false;
                this.isEnableMaTB = true;
                let date = new Date();
                this.dtp_NgaySD = date.getFullYear() + '-' + ((date.getMonth() > 8) ? (date.getMonth() + 1) : ('0' + (date.getMonth() + 1))) + '-' + ((date.getDate() > 9) ? date.getDate() : ('0' + date.getDate()));
                this.loai_td = -1;
                await this.HT_TT_DM();
                if(this.ma_tb) {
                    this.cbo_DichVuVT = this.dichvuvt_id;
                    await this.HT_TT();
                }
            } catch (error) {

            } finally {
                this.loading(false);
            }
        },
        HT_TT_DM: async function() {
            try {
                let rsDanhMuc = await this.$root.context.get("web-quantri/bosungvattu/lay_dm_vattu_bs");
                this.dsVatTu = rsDanhMuc.data.dsVatTu;
                this.dsKho = rsDanhMuc.data.dsKho;
                this.dsDichVuVT = rsDanhMuc.data.dsDichVuVT;
                this.cbo_DichVuVT = this.dsDichVuVT.length > 0 ? this.dsDichVuVT[0].dichvuvt_id : null;
                this.dsLoHang = rsDanhMuc.data.dsLoHang;
                this.dsTinhTrangTBI = rsDanhMuc.data.dsTinhTrang;
                this.cbo_TinhTrangTBI = this.dsTinhTrangTBI.length > 0 ? this.dsTinhTrangTBI[0].tinhtrangtbi_id : null;
                this.dsKieuTBI = rsDanhMuc.data.dsKieuTB;
                this.cbo_KieuTBI = this.dsKieuTBI.length > 0 ? this.dsKieuTBI[0].kieutbi_id : null;
                this.dsTrangBi = rsDanhMuc.data.dsTrangBi;
                this.cbo_TrangBi = this.dsTrangBi > 0 ? this.this.dsTrangBi[0].trangbi_id : null;
                this.dsDVTinh = rsDanhMuc.data.dsDonViTinh;
            } catch (error) {
                
            }
        },
        HT_TT: async function() {
            try {
                if(this.txt_MaTB) {
                    let rs = await this.$root.context.post("web-quantri/bosungvattu/lay_tt_vattu_tb", {
                        ma_tb: this.txt_MaTB,
                        dichvuvt_id: this.cbo_DichVuVT,
                        loai_id: this.trangthai_id
                    });
                    this.selected = 0;
                    if(rs.data.dsThueBao && rs.data.dsThueBao.length > 0) {
                        let dsVT = rs.data.dsVatTu;
                        let dsTB = rs.data.dsThueBao[0];
                        this.dsVT_BS_Temp = dsVT.map(v => {
                            v.ischeck = 0;
                            return v;
                        })
                        this.isEnableMaTB = true;
                        this.isEnableTenTB = true;
                        this.txt_TenTB = dsTB.ten_tb;
                        this.donvi_id = dsTB.donvi_id;
                        if(!this.Ko_Nhap_Dung_Kho) {
                            this.dsKho.filter(v => v.donvi_id == this.donvi_id);
                        }
                        this.thuebao_id = dsTB.thuebao_id;
                        this.ngay_sd = dsTB.ngay_sd ? this.formatDateYYYYMMDD(dsTB.ngay_sd) : null;
                        if(dsVT.length > 0) {
                            this.dsVT_BS = dsVT;
                            if(this.loai_td != 1) {
                                this.loai_td = 2;
                            }
                            if(this.selected != 1) {
                                await this.HT_TT_CT(0);
                            }
                        }
                        else {
                            this.loai_td = -1;
                            this.dsVT_BS = [];
                            this.dsVT_BS_Temp = [];
                            this.txt_SerialNum = "";
                            this.txt_GhiChu = "";
                            let date = new Date();
                            this.dtp_NgaySD = date.getFullYear() + '-' + ((date.getMonth() > 8) ? (date.getMonth() + 1) : ('0' + (date.getMonth() + 1))) + '-' + ((date.getDate() > 9) ? date.getDate() : ('0' + date.getDate()));;
                            this.thietbi_id = 0;
                            this.sudungvt_id = 0;
                            this.cbo_Kho = null;
                            this.cbo_VatTu = null;
                            this.cbo_LoHang = null;
                            this.cbo_TrangBi = null;
                            this.cbo_TinhTrangTBI = null;
                            this.cbo_KieuTBI = null;
                            this.cbo_DVTinh = null;
                        }
                    }
                    else {
                        this.$root.toastError('Không tìm thấy thông tin thuê bao');
                        this.isEnableTenTB = false;
                        this.isEnableMaTB = true;
                        this.dsVT_BS = [];
                        this.dsVT_BS_Temp = [];
                    }
                }
            } catch (error) {
                
            }
        },
        grid_selectedRowChanged: function(args) {
            this.HT_TT_CT(args.rowIndex);
        },
        HT_TT_CT: function(RowFocus) {
            try {
                this.indexRow = RowFocus;
                if(RowFocus < 0) return;
                if(this.dsVT_BS_Temp == 0) return;
                if(this.loai_td == 1) return;
                this.selected = 1;
                this.vSN = 0;
                this.cbo_VatTu = this.dsVT_BS_Temp[RowFocus].vattu_id;
                if(this.dsVT_BS_Temp[RowFocus].kho_id) {
                    this.cbo_Kho = this.dsVT_BS_Temp[RowFocus].kho_id;
                }
                if(this.dsVT_BS_Temp[RowFocus].lohang) {
                    this.cbo_LoHang = this.dsVT_BS_Temp[RowFocus].lohang;
                }
                if(this.dsVT_BS_Temp[RowFocus].ngay_sd) {
                    this.dtp_NgaySD = this.formatDateYYYYMMDD(this.dsVT_BS_Temp[RowFocus].ngay_sd);
                }
                this.cbo_KieuTBI = this.dsVT_BS_Temp[RowFocus].kieutbi_id;
                this.cbo_TinhTrangTBI = this.dsVT_BS_Temp[RowFocus].tinhtrangtbi_id;
                this.cbo_DVTinh = this.dsVT_BS_Temp[RowFocus].dvitinh_id;
                this.cbo_TrangBi = this.dsVT_BS_Temp[RowFocus].trangbi_id;
                this.txt_SerialGP = this.dsVT_BS_Temp[RowFocus].serial_gp;
                this.txt_SerialNum = this.dsVT_BS_Temp[RowFocus].serial;
                this.sua = this.dsVT_BS_Temp[RowFocus].bs_vt;
                this.txt_GhiChu = this.dsVT_BS_Temp[RowFocus].ghichu;
                this.thietbi_id = this.dsVT_BS_Temp[RowFocus].thietbi_id;
                this.sudungvt_id = this.dsVT_BS_Temp[RowFocus].sdvt_id;
                this.sp = 0;
                this.sn = 0;
                this.setDisableBtn(false, false, false, false);
            } catch (error) {
                
            }
        },
        chon: function(id, check) {
            let index = this.dsVT_BS_Temp.findIndex(v => v.vattu_id == id);
            if(index >= 0) {
                this.dsVT_BS_Temp[index].ischeck = check;
            }
        },
        check_sua: function(id, check) {
            let index = this.dsVT_BS_Temp.findIndex(v => v.vattu_id == id);
            if(index >= 0) {
                this.dsVT_BS_Temp[index].bs_vt = check;
            }
        },
        clear: function() {
            // this.txt_MaTB = null;
            // this.txt_TenTB = null;
            this.txt_SerialGP = null;
            this.txt_SoLuong = 1;
            this.txt_SerialNum = null;
            this.txt_GhiChu = null;
            let date = new Date();
            this.dtp_NgaySD = date.getFullYear() + '-' + ((date.getMonth() > 8) ? (date.getMonth() + 1) : ('0' + (date.getMonth() + 1))) + '-' + ((date.getDate() > 9) ? date.getDate() : ('0' + date.getDate()));
            this.thietbi_id = 0;
            this.sudungvt_id = 0;
            this.cbo_Kho = null;
            this.cbo_VatTu = null;
            this.cbo_LoHang = null;
            this.cbo_TrangBi = null;
            this.cbo_TinhTrangTBI = null;
            this.cbo_KieuTBI = null;
            this.cbo_DVTinh = null;
        },
        layTT: function() {
            if(this.txt_MaTB) {
                this.loai_td = -1;
                this.setDisableBtn(false, false, true, true);
                this.HT_TT();
            }
            else {
                this.$root.toastError('Bạn chưa nhập số máy/account khách hàng');
            }
        },
        nhapMoi: function() {
            if(this.isDisableBtnNhapMoi) {
                return;
            }
            this.clear();
            this.isEnableTenTB = false;
            this.isEnableMaTB = true;
            this.dsVT_BS_Temp = [];
            this.loai_td = 1;
            this.setDisableBtn(true, false, false, true);
        },
        checkForm: async function() {
            let check = true;
            let error = '';
            if(this.sua == 0 && this.loai_td == 2) {
                this.$root.toastError('Bạn không được phép sửa dữ liệu này');
                return false;
            }
            let rs = await this.$root.context.post("web-quantri/bosungvattu/lay_tt_vattu_tb", {
                ma_tb: this.txt_MaTB,
                dichvuvt_id: this.cbo_DichVuVT,
                loai_id: this.trangthai_id
            });
            if(rs.data.dsThueBao == 0) {
                this.$root.toastError('Không tìm thấy thông tin thuê bao');
                return;
            }
            if(!this.cbo_VatTu) {
                error = 'Bạn chưa chọn vật tư\n';
                check = false;
            }
            if(!this.cbo_Kho) {
                error += 'Chưa có thông tin kho\n';
                check = false;
            }
            if(!this.cbo_DVTinh) {
                error += 'Bạn chưa chọn đơn vị tính\n';
                check = false;
            }
            if(!this.cbo_KieuTBI) {
                error += 'Bạn chưa chọn kiểu thiết bị\n';
                check = false;
            }
            if(!this.cbo_TinhTrangTBI) {
                error += 'Bạn chưa chọn trình trạng thiết bị\n';
                check = false;
            }
            if(!this.cbo_TrangBi) {
                error += 'Bạn chưa chọn trang bị\n';
                check = false;
            }
            rs = await this.$root.context.post("web-quantri/bosungvattu/kt_vattu_bs_v2", {
                ma_kt: 'KT_SERIAL',
                thamso: this.txt_SerialGP,
                thuebao_id: this.thuebao_id,
                vattu_id: this.cbo_VatTu,
                loai_td: this.loai_td,
                sdvt_id: this.sudungvt_id,
                serial_number: this.txt_SerialNum
            });
            if(rs.data != 'ok' && rs.data) {
                error += `${rs.data}\n`;
                check = false;
            }
            if(!(Date.parse(this.dtp_NgaySD) <= Date.parse(new Date()) && ((Date.parse(this.dtp_NgaySD) >= Date.parse(this.ngay_sd) && this.ngay_sd) || !this.ngay_sd))) {
                error += 'Ngày sử dụng lớn hơn ngày sử dụng của thuê bào và nhở hơn ngày hiện tại';
                check = false;
            }
            if(!check) {
                this.$root.toastError(error);
            }
            return check;
        },
        capNhat: async function() {
            try {
                if(this.isDisableBtnGhiLai) {
                    return;
                }
                this.loading(true);
                let kq = await this.checkForm();
                if(kq) {
                    let rs = await this.$root.context.post("web-quantri/bosungvattu/day_vattu_bs", {
                        thuebao_id: this.thuebao_id,
                        vattu_id: this.cbo_VatTu,
                        kho_id: this.cbo_Kho,
                        lohang: this.cbo_LoHang,
                        ngay_sd: this.formatDateDDMMYYYY(this.dtp_NgaySD),
                        kieutbi_id: this.cbo_KieuTBI,
                        dvtinh_id: this.cbo_DVTinh,
                        tinhtrangtbi_id: this.cbo_TinhTrangTBI,
                        trangbi_id: this.cbo_TrangBi,
                        serial_gp: this.txt_SerialGP,
                        ghichu: this.txt_GhiChu,
                        dongia: this.dongia,
                        thietbi_id: this.thietbi_id,
                        sudungvt_id: this.sudungvt_id,
                        loai_id: this.trangthai_id,
                        loai_td: this.loai_td,
                        serial_number: this.txt_SerialNum,
                    });
                    if(rs.data && rs.data[0].sdvt_id) {
                        this.$root.toastSuccess('Cập nhật dữ liệu viễn thông tỉnh thành công');
                        this.loai_td = 2;
                        this.setDisableBtn(false, false, false, false);
                        this.HT_TT();
                    }
                    else if(rs.data && rs.data[0].result == 0){
                        this.$root.toastError(rs.data[0].msg);
                    }
                }
            } catch (error) {
                
            } finally {
                this.loading(false);
            }
        },
        timKiem: function() {
            if(this.txt_MaTB && this.ma_tb != this.txt_MaTB) {
                this.loai_td = -1;
            }
            this.ma_tb = this.txt_MaTB;
            this.HT_TT();
        },
        xoa: function() {
            try {
                if(this.isDisableBtnXoa) {
                    return;
                }
                if(this.dsVT_BS_Temp.length == 0) {
                    this.$root.toastError('Danh sách không có vật tư nào để có thể xoá. Bạn hãy kiểm tra lại.');
                    return;
                }
                let t = this.dsVT_BS_Temp.filter(v => v.ischeck == 1);
                if(t.length == 0) {
                    this.$bvModal.msgBoxConfirm('Bạn muốn xóa vật tư không?', {
                        size: 'sm',
                        okTitle: 'Đồng ý',
                        cancelTitle: 'Hủy',
                    }).then(async v => {
                        if(v) {
                            try {
                                let rs = await this.$root.context.post("web-quantri/bosungvattu/day_vattu_bs", {
                                    thuebao_id: this.thuebao_id,
                                    vattu_id: this.cbo_VatTu,
                                    kho_id: this.cbo_Kho,
                                    lohang: this.cbo_LoHang,
                                    ngay_sd: this.formatDateDDMMYYYY(this.dtp_NgaySD),
                                    kieutbi_id: this.cbo_KieuTBI,
                                    dvtinh_id: this.cbo_DVTinh,
                                    tinhtrangtbi_id: this.cbo_TinhTrangTBI,
                                    trangbi_id: this.cbo_TrangBi,
                                    serial_gp: this.txt_SerialGP,
                                    ghichu: this.txt_GhiChu,
                                    dongia: this.dongia,
                                    thietbi_id: this.thietbi_id,
                                    sudungvt_id: this.sudungvt_id,
                                    loai_id: this.trangthai_id,
                                    loai_td: 3,
                                    serial_number: this.txt_SerialNum,
                                });
                                if(rs.data && rs.data[0].result) {
                                    this.$root.toastSuccess("Xoá dữ liệu vật tư thành công");
                                    await this.HT_TT();
                                }
                                else {
                                    this.$root.toastError(rs.data[0].msg);
                                    await this.HT_TT();
                                }
                            } catch (error) {
                                this.$root.toastError('Xóa thất bại');
                            } finally {
                                this.loading(false);
                            }
                        }
                    }).catch(err => {
                        this.$root.toastError('Xóa thất bại');
                    })
                }
                else {
                    this.$bvModal.msgBoxConfirm('Bạn muốn xóa danh sách vật tư đã chọn không?', {
                        size: 'sm',
                        okTitle: 'Đồng ý',
                        cancelTitle: 'Hủy',
                    }).then(async v => {
                        if(v) {
                            try {
                                this.loading(true);
                                for (const element of t) {
                                    await this.$root.context.post("web-quantri/bosungvattu/day_vattu_bs", {
                                        thuebao_id: this.thuebao_id,
                                        vattu_id: element.vattu_id,
                                        kho_id: element.kho_id,
                                        lohang: element.lohang,
                                        ngay_sd: this.formatDateDDMMYYYY(element.ngay_sd),
                                        kieutbi_id: element.kieutbi_id,
                                        dvtinh_id: element.dvitinh_id,
                                        tinhtrangtbi_id: element.tinhtrangtbi_id,
                                        trangbi_id: element.trangbi_id,
                                        serial_gp: element.serial_gp,
                                        ghichu: element.ghichu,
                                        dongia: 0,
                                        thietbi_id: element.thietbi_id,
                                        sudungvt_id: element.sdvt_id,
                                        loai_id: this.trangthai_id,
                                        loai_td: 3,
                                        serial_number: this.txt_SerialNum,
                                    });
                                }
                                this.$root.toastSuccess("Xoá dữ liệu vật tư thành công");
                                await this.HT_TT();
                            } catch (error) {
                                this.$root.toastError('Có lỗi khi thực hiện xoá danh sách');
                                return;
                            } finally {
                                this.loading(false);
                            }
                        }
                    }).catch(err => {
                        this.$root.toastError('Xóa thất bại');
                    })
                }
                this.setDisableBtn(false, true, false, true);
            } catch (error) {
                
            }
        },
        huy: function() {
            if(this.isDisableBtnHuy) {
                return;
            };
            if(this.dsVT_BS_Temp.length == 0) {
                this.clear();
                this.loai_td = -1;
                this.setDisableBtn(false, false, false, true);
            }
            else {
                this.HT_TT_CT(this.indexRow);
                this.loai_td = 2;
                this.setDisableBtn(false, false, false, false);
            }
        },
        setDisableBtn: function(isNhapmoi, isGhilai, isHuy, isXoa) {
            this.isDisableBtnNhapMoi = isNhapmoi;
            this.isDisableBtnGhiLai = isGhilai;
            this.isDisableBtnHuy = isHuy;
            this.isDisableBtnXoa = isXoa;
        }
    },
    computed: {
        dongia: function() {
            let rs = this.dsLoHang.find(v => v.lohang == this.cbo_LoHang);
            if(rs) {
                return rs.dongia;
            }
            return null;
        }
    },
    watch: {
        cbo_VatTu: function(val) {
            if(val) {
                let obj = this.dsVatTu.find(v => v.vattu_id == val);
                this.cbo_DVTinh = obj.dvitinh_id;
            }
        },
        txt_SerialNum: function(val) {
            if(this.vSN == 1) {
                this.txt_SerialGP = val;
            }
            this.vSN = 1;
        }
    }
}
</script>

<style>
.bosung-vattu .select2-container .select2-selection--single  {
    overflow: hidden;
}
.bosung-vattu .select2-container .select2-selection--single .select2-selection__rendered {
    white-space: initial;
}
a.disable-btn-bsvt {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
</style>
