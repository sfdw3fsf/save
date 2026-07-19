<template src="./NhanKhaoSatTB.html"> </template>

<script>
import { DSLoaiNhom, DSTrangThaiLG, DSTrangThaiPhieu } from "./common.js";
import API from "../api/GiaoNhanPhieuKhaoSatTB.js";
import moment from "moment";
export default {
    name: "NhanPhieuKhaoSatTBTrongDB",
    beforeMount() {
        if (this.$route.query.TAG) this.TAG = this.$route.query.TAG;
    },
    data() {
        return {
            TAG: "",
            HPG_PHANVUNG_ID: 26,
            HCM_PHANVUNG_ID: 28,
            tag_TuDong_LayTT: 0,
            /// <summary>
            /// Tự động lấy phiếu
            /// </summary>
            LAYPHIEU_OUTBOUND_TUDONG: -1,
            KS_THIETLAP_TGLAYPHIEU: -1,
            THAMSO_THOIGIAN_TIMKIEM_OUTBOUND: -1,
            /// <summary>
            /// 1 -> Không được ấn tạm dừng
            /// </summary>
            KHONG_TAMDUNG_LAYPHIEU: -1,
            /// <summary>
            /// 1: Lấy phiếu, 0: Tạm dừng
            /// </summary>
            tt_btb_LayPhieu: -1,
            vgiaophieu_ks_id: 0,
            vmophieu: 0,
            hdtb_id: 0,
            hdkh_id: 0,
            baohong_id: 0,
            ct_suyhao_id: 0,
            thuebao_id: 0,
            loai: 0,
            donviID: 0,
            donviIDCHA: -1,
            muc_id_cha: 0,
            nodeText: "",
            donviBC: 0,
            kt_load: true,
            vnhom_id: 0,
            vkieu_ks: 0,
            //design
            chkNgayGiao: false,
            dtpGiaoTu: new Date(),
            dtpGiaoDen: new Date(),
            cboTrangthaiLanGiao: [],
            cboTrangthaiLanGiaoValue: "",
            cboLoaiNhom: [],
            cboLoaiNhomValue: "",
            cboTrangThaiPhieu: [],
            cboTrangThaiPhieuValue: "",
            btnLayPhieuShow: false,
            btnBoQuaShow: false,
            lblCanhBaoQuaGio: false,
            lblTuDongLayTT: false,
            //datagrid
            dtPhieuKS: [],
            dtPhieuKSItemSelecteds: [],
            dtPhieuKSRowSelected: "",
            dtPhieuKSTotalRows: 0,

            dtPhieuDaGiao: [],
            dtPhieuDaGiaoItemSelecteds: [],
            dtPhieuDaGiaoRowSelected: "",
            dtPhieuDaGiaoTotalRows: 0,

            intervalTimer1: "",
            intervalTimer2: ""
        };
    },
    beforeRouteLeave(to, from, next) {
        clearInterval(this.intervalTimer1);
        clearInterval(this.intervalTimer2);
        next();
    },
    watch: {
        async cboTrangThaiPhieuValue() {
            if (this.dtPhieuKS.length <= 0) {
                return;
            }
            let vgpksId = this.dtPhieuKSRowSelected["GIAOPHIEU_KS_ID"];
            let vTrangThai = this.cboTrangThaiPhieuValue;
            let vLoai = this.dtPhieuKSRowSelected["LOAI"];
            if (vLoai == 1 || vLoai == 2) {
                let data = {
                    giaoPhieuKSId: vgpksId,
                    loai: vLoai,
                    trangThai: vTrangThai
                };
                let resData = await this.getDSPhieuDaGiaoKSPaging(
                    data,
                    1,
                    10000000,
                    1
                );
                if (resData != null) {
                    if (resData.data.length > 0) {
                        this.dtPhieuDaGiao = resData.data;
                        this.dtPhieuDaGiaoTotalRows = resData.totalElement;
                    } else this.dtPhieuDaGiao = [];
                }
            } else {
                let data = {
                    giaoPhieuKSId: vgpksId,
                    trangThai: vTrangThai
                };
                let resData = await this.getDSPhieuDaGiaoKSTBPaging(
                    data,
                    1,
                    10000000,
                    1
                );
                if (resData != null) {
                    if (resData.data.length > 0) {
                        this.dtPhieuDaGiao = resData.data;
                        this.dtPhieuDaGiaoTotalRows = resData.totalElement;
                    } else this.dtPhieuDaGiao = [];
                }
            }
        }
    },
    methods: {
        formatDate(value) {
            return moment(value).format("DD/MM/yyyy");
        },
        async dtPhieuKSSelectedRowChanged(value) {
            if (value) {
                this.dtPhieuKSRowSelected = value;
                let vgpks_id = (this.vgiaophieu_ks_id =
                    value["GIAOPHIEU_KS_ID"]);
                let vtrangthai = this.cboTrangThaiPhieuValue;
                this.vkieu_ks = value["KIEU_KS"];
                this.vmophieu = value["MOPHIEU"];
                this.loai = value["LOAI"];
                this.co_hen = value["CO_HEN"];
                let ds_tb = "";
                if (this.vkieu_ks == 1) {
                    if (this.loai == 1 || this.loai == 2) {
                        let data = {
                            giaoPhieuKSId: vgpks_id,
                            loai: this.loai,
                            trangThai: vtrangthai
                        };
                        ds_tb = await this.getDSPhieuDaGiaoKSPaging(
                            data,
                            1,
                            10000000,
                            1
                        );
                    } else {
                        let data = {
                            giaoPhieuKSId: vgpks_id,
                            trangThai: vgpks_id
                        };
                        ds_tb = await this.getDSPhieuDaGiaoKSTBPaging(
                            data,
                            1,
                            10000000,
                            1
                        );
                    }
                } else if (this.vkieu_ks == 2) {
                    let data = {
                        giaoPhieuKSId: vgpks_id,
                        loai: this.loai,
                        trangThai: vtrangthai
                    };
                    ds_tb = await this.getDSPhieuDaGiaoKSV2Paging(
                        data,
                        1,
                        10000000,
                        1
                    );
                }
                if (ds_tb != null) {
                    if (ds_tb.data.length > 0) {
                        this.dtPhieuDaGiao = ds_tb.data;
                        this.dtPhieuDaGiaoTotalRows = ds_tb.totalElement;
                    } else this.dtPhieuDaGiao = [];
                }
            }
        },
        dtPhieuKSSelectedItemsChanged(value) {
            if (value) this.dtPhieuKSItemSelecteds = value;
        },
        dtPhieuKSGridChanged(args) {
            this.timKiem(args.pageIndex + 1, args.pageSize);
        },

        dtPhieuDaGiaoSelectedRowChanged(value) {
            if (value) this.dtPhieuDaGiaoRowSelected = value;
        },
        dtPhieuDaGiaoSelectedItemsChanged(value) {
            if (value) this.dtPhieuDaGiaoItemSelecteds = value;
        },
        dtPhieuDaGiaoGridChanged(args) {},
        btnLayPhieuClick() {},
        btnBoQuaClick() {},
        btnTimKiem() {
            this.timKiem(1, 10);
        },
        //functional
        async timKiem(pageNum, pageSize) {
            let vtungay = null;
            let vdenngay = null;
            let vloainhom_id = this.cboLoaiNhomValue;
            let tt_luotgiao_id = this.cboTrangthaiLanGiaoValue;
            if (this.chkNgayGiao) {
                vtungay = this.formatDate(this.dtpGiaoTu);
                vdenngay = this.formatDate(this.dtpGiaoDen);
            }
            try {
                this.loading(true);
                let data = {
                    nguoiNhanId: this.$auth.getNhanVienID(),
                    tuNgay: vtungay,
                    denNgay: vdenngay,
                    loaiNhomId: vloainhom_id,
                    ttLuotGiaoId: tt_luotgiao_id,
                    pageNum: pageNum,
                    pageSize: pageSize
                        ? pageSize
                        : this.$refs.griddtPhieuKS.pageSize,
                    totalRow: 1
                };
                let response = await API.getDSphieuGiaoNhanKS(this.axios, data);
                if (
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data !== undefined
                ) {
                    this.dtPhieuKS = response.data.data.data;
                    this.dtPhieuKSTotalRows = response.data.data.totalElement;
                }
            } catch (error) {
                console.log(error);
                this.$toast.error(error.data.error_code);
            } finally {
                this.loading(false);
            }
        },

        async getDSPhieuDaGiaoKSPaging(
            data,
            pageNum = 1,
            pageSize = 10,
            totalRow = 1
        ) {
            let payload = { ...data, pageNum, pageSize, totalRow };
            try {
                this.loading(true);
                let response = await API.getDSphieuDaGiaoKS(
                    this.axios,
                    payload
                );
                if (
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data !== undefined
                ) {
                    if (response.data.data.data.length > 0) {
                        return {
                            data: response.data.data.data,
                            totalElement: response.data.data.totalElement
                        };
                    }
                    return null;
                }
            } catch (error) {
                console.log(error);
                this.$toast.error(error.data.error_code);
            } finally {
                this.loading(false);
            }
        },
        async getDSPhieuDaGiaoKSTBPaging(
            data,
            pageNum = 1,
            pageSize = 10,
            totalRow = 1
        ) {
            let payload = { ...data, pageNum, pageSize, totalRow };
            try {
                this.loading(true);
                let response = await API.getDSphieuDaGiaoKSTB(
                    this.axios,
                    payload
                );
                if (
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data !== undefined
                ) {
                    if (response.data.data.data.length > 0) {
                        return {
                            data: response.data.data.data,
                            totalElement: response.data.data.totalElement
                        };
                    }
                    return null;
                }
            } catch (error) {
                console.log(error);
                this.$toast.error(error.data.error_code);
            } finally {
                this.loading(false);
            }
        },
        async getDSPhieuDaGiaoKSV2Paging(
            data,
            pageNum = 1,
            pageSize = 10,
            totalRow = 1
        ) {
            let payload = { ...data, pageNum, pageSize, totalRow };
            try {
                this.loading(true);
                let response = await API.getDSphieuDaGiaoKSV2(
                    this.axios,
                    payload
                );
                if (
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data !== undefined
                ) {
                    if (response.data.data.data.length > 0) {
                        return {
                            data: response.data.data.data,
                            totalElement: response.data.data.totalElement
                        };
                    }
                    return null;
                }
            } catch (error) {
                console.log(error);
                this.$toast.error(error.data.error_code);
            } finally {
                this.loading(false);
            }
        },
        ondtPhieuDaGiaoQueryCellInfo(args) {
            if (
                args.column.field === "MA_TB" ||
                args.column.field === "LOAIHINH_TB" ||
                args.column.field === "NGAY_YC" ||
                args.column.field === "NGAY_HT"
            ) {
                if (args.data.TRANGTHAI == 1) {
                    args.cell.classList.add("text-blue");
                } else {
                    args.cell.classList.remove("text-blue");
                }
            }
        }
    },
    async mounted() {
        if (this.TAG != "") {
            let words = this.TAG.split("+");
            if (words.length >= 1) {
                this.tag_TuDong_LayTT = words[0];
            }
        } else if (this.$auth.getPhanVungID() == this.HCM_PHANVUNG_ID) {
            this.tag_TuDong_LayTT = 1;
        }
        // Loại nhóm
        this.cboLoaiNhom = DSLoaiNhom.filter(e =>
            this.$auth.getPhanVungID() == this.HPG_PHANVUNG_ID
                ? true
                : e.PHANVUNG_ID == 0
        );
        this.cboLoaiNhomValue = DSLoaiNhom[0].NHOM_ID;
        //TrangThai
        this.cboTrangthaiLanGiao = DSTrangThaiLG;
        this.cboTrangthaiLanGiaoValue = DSTrangThaiLG[0].TT_LUOT_ID;
        //TrangThai phieu
        this.cboTrangThaiPhieu = DSTrangThaiPhieu;
        this.cboTrangThaiPhieuValue = DSTrangThaiPhieu[0].TT_PHIEU_ID;

        this.kt_load = false;
        let response = await API.getDSThamSoMD(this.axios);
        let dsTSMD = response.data.data;
        if (dsTSMD.length > 0) {
            let strTS = "LAYPHIEU_OUTBOUND_TUDONG";
            let det = dsTSMD.filter(e => e.MA_TS == strTS);
            if (det.length > 0) {
                if (det[0].TEN_TS == 1) {
                    this.LAYPHIEU_OUTBOUND_TUDONG = 1;
                }
            }
            strTS = "KS_THIETLAP_TGLAYPHIEU";
            det = dsTSMD.filter(e => e.MA_TS == strTS);
            if (det.length > 0) {
                if (det[0].TEN_TS == 1) {
                    this.KS_THIETLAP_TGLAYPHIEU = 1;
                }
            }
            strTS = "KHONG_TAMDUNG_LAYPHIEU";
            det = dsTSMD.filter(e => e.MA_TS == strTS);
            if (det.length > 0) {
                if (det[0].TEN_TS == 1) {
                    this.KHONG_TAMDUNG_LAYPHIEU = 1;
                }
            }
            strTS = "THAMSO_THOIGIAN_TIMKIEM_OUTBOUND";
            det = dsTSMD.filter(e => e.MA_TS == strTS);
            if (det.length > 0) {
                if (det[0].TEN_TS != 0) {
                    this.THAMSO_THOIGIAN_TIMKIEM_OUTBOUND = det[0].TEN_TS;
                }
            }
        }
        if (this.tag_TuDong_LayTT != 0) {
            console.log(this.THAMSO_THOIGIAN_TIMKIEM_OUTBOUND);
            this.intervalTimer1 = setInterval(() => {
                console.log("interval thuc1");
            }, this.THAMSO_THOIGIAN_TIMKIEM_OUTBOUND);
        }
        if (this.LAYPHIEU_OUTBOUND_TUDONG == 1) {
            this.btnLayPhieuShow = true;
            this.tt_btb_LayPhieu = 1;
        } else this.btnLayPhieuShow = false;
        this.lblCanhBaoQuaGio = false;
        this.intervalTimer2 = setInterval(() => {
            console.log("interval thuc2");
        }, 60000);
        this.lblTuDongLayTT = false;
        this.btnBoQuaShow = true;
        //thieu lay thong bao Lay_thongbao_ob_toantinh
    }
};
</script>

<style src="./NhanKhaoSatTB.scss"></style>
