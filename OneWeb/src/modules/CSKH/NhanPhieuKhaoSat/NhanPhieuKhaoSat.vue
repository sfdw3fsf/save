<template src="./NhanPhieuKhaoSat.html"></template>
<style src="./NhanPhieuKhaoSat.scss"></style>
<script>
import breadcrumb from "@/components/breadcrumb";
import moment from "moment";
import { mapActions, mapState, mapGetters } from "vuex";
import TableWithSearch from "../utils/TableWithSearch.vue";
import CustomDataGrid from "../utils/CustomDataGrid.vue";
import NhanPhieuKhaoSatApi from "../api/DanhSachPhieuKhaoSatOutboundApi";
import NhanVienQuanLy from "../DanhSachPhieuKhaoSatOutbound/popup/NhanVienQuanLy.vue";
import downloadexcel from "vue-json-excel";
import ModalXuatDuLieu from "../../ECMS/TraCuuSplitterTheoPortOLT/ModalXuatDuLieu/index.vue";
import frmKhaoSatKH from "../KhaoSat/frmKhaoSatKH";
import frmKhaoSatKH_LM from "../KhaoSat/frmKhaoSatKH_LM";
import { updateFloatLabelState } from "@syncfusion/ej2-dropdowns";
// import XuatFile from "../utils/XuatFile.vue";
import { phieuDaGiaoColumns } from "./common.js";
// import LienHe from "../GiaoPhieuKhaoSatKhachHang/popup/modal-tt-lien-he/index.vue";
import ThongTinLienHe from "../../contract/setup/ChangeContractOwner/popup/ThongTinLienHe.vue"

export default {
    name: "NhanPhieuKhaoSat",
    components: {
        breadcrumb,
        TableWithSearch,
        appNhanVienQuanLy: NhanVienQuanLy,
        datagrid: CustomDataGrid,
        downloadexcel,
        appModalXuatDuLieu: ModalXuatDuLieu,
        frmKhaoSatKH_LM,
        frmKhaoSatKH,
        ThongTinLienHe
        // appLienHe: LienHe
    },
    computed: {
        ...mapGetters("danhsachphieukhaosatoutbound", [
            "getdsLoaiNhomKhaoSatGetter",
            "getdsTrangThaiLuotGiaoKhaoSatGetter",
            "getdsTrangThaiPhieuGetter",
            "getdsLuotGiaoNhanKhaoSatGetter",
            "getdsPhieuDaGiaoKhaoSatGetter"
        ]),
        trangThaiLuotGiaoOpt() {
            return [
                { value: 0, text: "Tất cả" },
                { value: 1, text: "Chưa hoàn thành" },
                { value: 2, text: "Đã hoàn thành" }
            ];
        },
        nhomKhaoSatOpt() {
            return [
                { value: 0, text: "Tất cả" },
                { value: 1, text: "Thi công" },
                { value: 2, text: "Báo hỏng" }
                // { value: 3, text: "Thuê bao không lưu lượng" }
            ];
        },
        trangThaiPhieuOpt() {
            return [
                { value: 1, text: "Chưa khảo sát" },
                { value: 3, text: "Chưa trả lời" },
                { value: 6, text: "Đã khảo sát" }
            ];
        },
        exportExcelFields() {
            return {
                STT: "STT",
                "Số máy/Acc": "MA_TB",
                "Số ảo": "MA_LT",
                "Loại hình TB": "LOAIHINH_TB",
                "Ngày yêu cầu": "NGAY_YC",
                "Ngày hoàn thành": "NGAY_HT",
                "Lần gọi": "LAN_KS",
                "Tên thuê bao": "TEN_TB",
                "Địa chỉ lắp đặt": "DIACHI_LD",
                "Đối tượng": "TEN_DT",
                "Gói KT": "GOI_KT",
                "Chỉ tiêu TG": "CHITIEU_TG",
                "Đơn vị TN": "DONVI_TN",
                "Nhân viên TN": "NHANVIEN_TN",
                "Đơn vị QL": "DONVI_QL",
                "Tổ thi công": "TRAM_VT",
                "Trạng thái": "TRANGTHAI_HD",
                "Điện thoại LH": "DIENTHOAI_LH",
                "Nhà mạng": "NHA_MANG",
                "Người CN": "NGUOI_CN",
                "Ngày CN": "NGAY_CN",
                "Kiểu yêu cầu": "TEN_KIEULD",
                "Người XL": "NGUOI_XL",
                "Lý do hỏng": "LYDO_HONG",
                "Thời gian TH": "THOIGIAN_TH",
                "Ngày hỏi": "NGAY_HOI",
                "Ghi chú": "GHICHU",
                "Nhân viên thực hiện": "GHICHU2",
                "Trạng thái KS": "TRANGTHAI",
                "Loại KH lớn": "TEN_LKHL",
                "Phân loại KH": "TEN_PLKH",
                "Lý do KTL": "LYDO_KTL"
            };
        },
        dsThuocTinhPhieuDaGiao() {
            const columnheader = this.columnsPhieuDaGiao.map(x => x.fieldName)
            return [...columnheader,'HDKH_ID','KHACHHANG_ID','MA_GD','TTHD_ID','LOAITB_ID','TEN_LOAIHD','NHANVIEN_LHD','GHICHU_TB','KHLON_ID','DICHVUVT_ID','SO_DT','HDTB_ID','HDTB_CHA_ID','QUYTRINH_ID','TRANGTHAI','TRANGTHAI_KS','TRANGTHAI_TB'];
        }
    },
    watch: {
        getdsTrangThaiPhieuSelected() {
            console.log(this.luotGiaoKSSelected);
            if (this.luotGiaoKSSelected) {
                this.onRowDSLuotGiaoKhaoSatSelected(this.luotGiaoKSSelected);
            }
        }
    },
    data() {
        return {
            header: {
                title: "NHẬN PHIẾU KHẢO SÁT KHÁCH HÀNG",
                list: [
                    //   { name: "Trang chủ", link: { name: "Ui.cards" } },
                    {
                        name: "Chăm sóc khách hàng",
                        link: "/cskh"
                    },
                    {
                        name: "Nhận phiếu khảo sát khách hàng",
                        link: { name: "Ui.buttons" }
                    }
                ]
            },
            dateConfig: {
                altInput: true,
                dateFormat: "d/m/Y",
                altFormat: "d/m/Y"
            },
            getdsLoaiNhomKhaoSatSelected: 0,
            getdsTrangThaiLuotGiaoKhaoSatSelected: 0,
            getdsTrangThaiPhieuSelected: 1,
            chk_from_date: false,
            today: new Date(),
            fromDate: new Date(),
            toDate: new Date(),
            vid: -1,
            vloai: -1,
            luotGiaoKSSelected: null,
            phieuGiaoKSSelected: null,
            phieuKSId: null,
            phieuKSLoai: null,
            phieuKSKieuKS: null,
            HDBHId: -1,
            tag_TuDong_LayTT: 0,
            LAYPHIEU_OUTBOUND_TUDONG: 0,
            KS_THIETLAP_TGLAYPHIEU: 0,
            THAMSO_THOIGIAN_TIMKIEM_OUTBOUND: 0,
            KHONG_TAMDUNG_LAYPHIEU: 0,
            timer: null,
            tt_btb_LayPhieu: -1,
            btnLayPhieuVisible: true,
            btnBoQuaVisible: true,
            btnLayPhieuText: "Lấy phiếu",
            btnLayPhieuClass: "nc-icon-outline media-1_button-play icon",
            txtThongBao:
                "Đang lấy phiếu: 0 - Chưa gọi: 0 - Chưa XLTG: 0 - Gọi gấp: 0 - Đang gọi: 0",

            pDsLuotGiao: [],
            pDsPhieuGiao: [],

            pGiaoPhieuKsId: 0,
            pTrangThai: 0,
            pHdkhId: 0,
            pBaoHongId: 0,

            currentLuotGiao: null,
            currentPhieuGiao: null,

            API_SUCCESS: "BSS-00000000",

            columnsPhieuDaGiao: phieuDaGiaoColumns,
            totalElementsDSLG: 0,
            dsLuotGiaoKhaoSatItemSelecteds: [],
            dsPhieuDaGiaoKhaoSatItemSelecteds: [],
            pKhachHangId: 0
        };
    },

    created() {
        this.clearData();
    },
    async mounted() {
        try {
            this.loading(true);
        } catch (e) {
        } finally {
            this.loading(false);
        }
    },

    methods: {
        ...mapActions("danhsachphieukhaosatoutbound", [
            "getdsLoaiNhomKhaoSat",
            "getdsTrangThaiLuotGiaoKhaoSat",
            "getdsTrangThaiPhieu",
            "getdsLuotGiaoNhanKhaoSat",
            "getdsPhieuDaGiaoKhaoSat",
            "clearData"
        ]),
        async dsLuotGiaoKhaoSatgridChanged(args) {
            await this.fetchDSLuotGiaoKhaoSat(
                args.pageIndex + 1,
                args.pageSize
            );
        },
        selectedItemsChangedLuotGiaoKhaoSat(value) {
            this.dsLuotGiaoKhaoSatItemSelecteds = value;
        },
        selectedItemsChangedPhieuDaGiaoKhaoSat(value) {
            this.dsPhieuDaGiaoKhaoSatItemSelecteds = value;
        },
        async onRowDSLuotGiaoKhaoSatSelected(item) {
            try {
                this.currentLuotGiao = item;
                this.$root.showLoading(true);
                this.luotGiaoKSSelected = null;
                this.phieuKSLoai = -1;
                this.phieuKSId = -1;
                this.phieuKSKieuKS = -1;
                if (item) {
                    this.luotGiaoKSSelected = item;
                    this.phieuKSId = item.GIAOPHIEU_KS_ID;
                    this.phieuKSLoai = item.LOAI;
                    this.phieuKSKieuKS = item.KIEU_KS;

                    let postData = {
                        giaoPhieuKsId: this.phieuKSId,
                        loai: this.phieuKSLoai,
                        trangThai: this.getdsTrangThaiPhieuSelected,
                        pageNo: 1,
                        pageSize: 10000
                    };

                    let rs = await this.$root.context.post(
                        "/web-cskh/api/nhan-khao-sat-kh/ds-bao-hong-thi-cong?pageNo=1&pageSize=10000",
                        postData
                    );
                    if (rs.error_code === this.API_SUCCESS) {
                        this.pDsPhieuGiao = rs.data.data || [];
                        this.pDsPhieuGiao.forEach((e, i) => (e.STT = i + 1));
                        // this.$nextTick(() =>
                        //     this.$refs[
                        //         "dsPhieuDaGiaoKhaoSat"
                        //     ].setCurrentSelectedRow(0)
                        // );
                    }
                }
            } catch (e) {
                console.log(e);
            } finally {
                this.$root.showLoading(false);
            }
        },
        onRowDSPhieuDaGiaoKhaoSatSelected(item) {
            this.currentPhieuGiao = item;
            this.phieuGiaoKSSelected = null;
            this.HDBHId = -1;
            if (item) {
                this.phieuGiaoKSSelected = item;
                if (this.phieuKSLoai === 1) {
                    this.HDBHId = item.HDKH_ID;
                    this.pHdkhId = item.HDKH_ID;
                    this.vloai = 1;
                } else if (this.phieuKSLoai === 2) {
                    this.HDBHId = item.KHACHHANG_ID;
                    this.pBaoHongId = item.BAOHONG_ID;
                    this.vloai = 2;
                }
            }
        },
        async fetchDSPhieuDaGiaoKhaoSat(pageNo, pageSize) {
            this.loading(true);
            try {
                // let postData = {
                //   giaoPhieuKsId: 19385, //this.phieuKSId,
                //   kieuKs: 1, //this.phieuKSKieuKS,
                //   loai: 2, //this.phieuKSLoai,
                //   trangthai: 1, //this.getdsTrangThaiPhieuSelected,
                // };
                let postData = {
                    giaoPhieuKsId: this.phieuKSId,
                    kieuKs: this.phieuKSKieuKS,
                    loai: this.phieuKSLoai,
                    trangthai: this.getdsTrangThaiPhieuSelected
                };
                let response = await NhanPhieuKhaoSatApi.getdsPhieuDaGiaoKhaoSat(
                    this.axios,
                    pageNo,
                    pageSize,
                    postData
                );
                return response;
            } catch (e) {
            } finally {
                this.loading(false);
            }
        },
        async fetchData() {
            try {
                this.$root.showLoading(true);
                await this.fetchDSLuotGiaoKhaoSat(
                    1,
                    this.$refs.dsLuotGiaoKhaoSat.pageSize
                        ? this.$refs.dsLuotGiaoKhaoSat.pageSize
                        : 10
                );
            } catch (e) {
            } finally {
                this.$root.showLoading(false);
            }
        },
        async fetchDSLuotGiaoKhaoSat(pageNum, pageSize) {
            try {
                this.pDsLuotGiao = [];
                this.pDsPhieuGiao = [];
                let postData = {
                    tuNgay: this.chk_from_date
                        ? moment(this.fromDate).format("DD/MM/YYYY")
                        : "",
                    denNgay: moment(this.toDate).format("DD/MM/YYYY"),
                    nguoiNhanId: this.$auth.getNhanVienID(),
                    loaiNhomId: this.getdsLoaiNhomKhaoSatSelected,
                    ttLuotGiaoId: this.getdsTrangThaiLuotGiaoKhaoSatSelected,
                    pageNum: pageNum,
                    pageSize: pageSize
                        ? pageSize
                        : this.$refs.dsLuotGiaoKhaoSat.pageSize,
                    totalRow: 1
                };
                let rs = await this.$root.context.post(
                    `/web-cskh/api/nhan-khao-sat-kh/tim-kiem?pageNum=${postData.pageNum}&pageSize=${postData.pageSize}&totalRow=${postData.totalRow}`,
                    postData
                );
                if (rs.error_code === this.API_SUCCESS) {
                    this.pDsLuotGiao = rs.data.data || [];
                    this.pDsLuotGiao.forEach((e, i) => (e.STT = i + 1));
                    this.totalElementsDSLG = rs.data.totalElement;
                    // this.$nextTick(() =>
                    //     this.$refs["dsLuotGiaoKhaoSat"].setCurrentSelectedRow(0)
                    // );
                }
            } catch (e) {
                console.log(e);
            } finally {
            }
        },
        onStartXuatXLS() {
            if (this.pDsPhieuGiao.length == 0) {
                this.$root.toastError("Không có dữ liệu để xuất Excel !");
                return;
            }
        },
        onStartXuatFile() {
            if (this.pDsPhieuGiao.length == 0) {
                this.$root.toastError("Không có dữ liệu để xuất file !");
                return;
            } else {
                this.$bvModal.show("modal-xuat-dulieu");
            }
        },
        async onKhaoSatClicked() {
            if (this.phieuKSLoai == 1) {
                this.$refs["frmKhaoSatKH_LM"].hdkhId = this.pHdkhId;
                this.$refs["frmKhaoSatKH_LM"].giaoPhieuKsId = this.phieuKSId;
                this.$refs["frmKhaoSatKH_LM"].openDialog();
            }

            if (this.phieuKSLoai == 2) {
                this.$refs["frmKhaoSatKH"].baoHongId = this.pBaoHongId;
                this.$refs["frmKhaoSatKH"].giaoPhieuKsId = this.phieuKSId;
                this.$refs["frmKhaoSatKH"].openDialog();
            }
        },
        async onTraPhieuClicked() {
            try {
                this.$root.showLoading(true);
                if (this.pDsPhieuGiao.length <= 0) {
                    this.$root.toastError("Không có phiếu để trả !");
                    return;
                }
                let dsPhieu = this.pDsPhieuGiao.filter(phieu =>
                    this.dsPhieuDaGiaoKhaoSatItemSelecteds.includes(phieu.STT)
                );
                if (dsPhieu.length == 0) {
                    this.$root.toastError("Bạn chưa chọn phiếu để trả");
                    return;
                }
                let dsId =
                    this.phieuKSLoai == 1
                        ? dsPhieu.map(x => x.HDKH_ID).join()
                        : dsPhieu.map(x => x.BAOHONG_ID).join();
                let data = {
                    loai: this.vloai,
                    dsId: dsId,
                    giaoPhieuKsId: this.phieuKSId
                };

                this.$root.context
                    .post("/web-cskh/api/nhan-khao-sat-kh/tra-phieu", data)
                    .then(async rs => {
                        if (rs.error_code === this.API_SUCCESS) {
                            this.$root.toastSuccess("Trả phiếu thành công !");
                            await this.fetchData();
                        } else {
                            this.$root.toastError(rs.message);
                        }
                    })
                    .catch(rj => {
                        this.$root.toastError(
                            "Có lỗi: " + rj.response.data.message
                        );
                    });
            } catch (e) {
                console.log(e);
                this.$root.toastError("Có lỗi: " + e);
            } finally {
                this.$root.showLoading(false);
            }
        },
        onNVQLClicked() {
            if (this.pDsPhieuGiao.length == 0) {
                this.$root.toastError("Không có dữ liệu hiển thị!");
                return;
            }
            this.$refs.popupNVQL.vid = this.HDBHId;
            this.$refs.popupNVQL.vloai = this.vloai;
            this.$refs.popupNVQL.openDialog();
            // :vid="HDBHId" :vloai="vloai"
            // this.$bvModal.show("popup-nhanVienQuanLy")
        },
        onLienHeClicked() {
            if (this.pDsPhieuGiao.length <= 0) {
                this.$root.toastError("Không có dữ liệu hiển thị!");
                return;
            }
            // if (this.phieuGiaoKSSelected.KHACHHANG_ID) {
            //     this.$refs.dlgTTLH.khachHangId = this.phieuGiaoKSSelected.KHACHHANG_ID;
            //     this.$refs.dlgTTLH.onShow();
            // }
            this.pKhachHangId = this.phieuGiaoKSSelected.KHACHHANG_ID
            if (this.phieuGiaoKSSelected.KHACHHANG_ID) {
                this.$refs.thongtinlienhe.show()
            } else {
              this.$root.toastError("Không có KHACHHANG_ID !")
              return
            }
        }
    },
    beforeDestroy() {
        clearInterval(this.timer);
    }
};
</script>
