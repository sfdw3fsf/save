<template src="./GanNhomCongNo.html"></template>
<style src="./GanNhomCongNo.scss"></style>
<script>
import breadcrumb from "@/components/breadcrumb";
import moment from "moment";
import { mapActions, mapState, mapGetters } from "vuex";
import KyCuoc from "../components/KyCuoc.vue";

export default {
    name: "GanNhomCongNo",
    components: {
        breadcrumb,
        appKyCuoc: KyCuoc
    },
    async created() {
        this.clearData();
        this.fetchData();
    },
    async mounted() {},
    data() {
        return {
            header: {
                title: "Gán nhóm công nợ",
                list: [
                    {
                        name: "Kiểm kê công nợ",
                        link: { name: "Ui.buttons" }
                    },
                    {
                        name: "Gán nhóm công nợ",
                        link: { name: "Ui.buttons" }
                    }
                ]
            },
            config: {
                dateConfig: {
                    value: new Date(),
                    format: "dd/MM/y",
                    max: new Date()
                }
            },
            kyHoaDon: "",
            isGan: "0",
            listIdCongNo: [],
            valueTatCa: null,
            totalItems: 0,
            //thông tin tìm kiếm section
            thongTinTimKiem: {
                selectedDonVi: -1,
                checkDonVi: false,

                selectedHTTT: -1,
                checkHTTT: false,

                selectedNhomKH: -1,
                checkNhomKH: false,

                selectedNVQL: -1,
                checkNVQL: false,

                selectedNVThuCuoc: null,
                checkNVTC: false,

                selectedPhanLoaiKH: null,
                checkPhanLoaiKH: false,

                selectedLoaiKhachHang: -1,
                checkLoaiKhachHang: false,

                listNhomKH: [
                    { id: -1, name: "Tất cả" },
                    { id: 0, name: "Cá nhân" },
                    { id: 1, name: "Doanh nghiệp" }
                ]
            },

            //thông tin cập nhật section
            thongTinCapNhat: {
                //left
                selectedNhomCongNo: "",
                truongNhom: "",
                thoiHanIn: new Date(),
                thoiHanGui: new Date(),
                thoiHanLH: new Date(),
                thoiHanPH: new Date(),
                thoiHanHT: new Date(),

                //right
                tenCongTy: "",
                diaChi: "",
                phong: "",
                nguoiNhan: "",
                diaChiNN: "",
                fax: "",
                dienThoai: "",
                email: "",
                nguoiDangKy: "",
                chucDanh: ""
            },
            selectedItemCongNo: null,
            fieldDsCongNo: [
                {
                    fieldName: "MA_TT",
                    headerText: "Mã TT",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "TEN_TT",
                    headerText: "Tên TT",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "DIACHI_TT",
                    headerText: "Địa chỉ TT",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "DIACHI_CT",
                    headerText: "Địa chỉ CT",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "TEN_NHOMKH",
                    headerText: "Nhóm KH",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "SO_DT",
                    headerText: "Số ĐT",
                    allowFiltering: true,
                    allowSorting: true,
                    cssClass: "text-right"
                },
                {
                    fieldName: "FAX",
                    headerText: "Fax",
                    allowFiltering: true,
                    allowSorting: true,
                    cssClass: "text-right"
                },
                {
                    fieldName: "TEN_NHOM",
                    headerText: "Nhóm C.Nợ",
                    allowFiltering: true,
                    allowSorting: true
                },
                {
                    fieldName: "TONGNO",
                    headerText: "Tổng nợ",
                    allowFiltering: true,
                    allowSorting: true,
                    cssClass: "text-right",
                    type: "number",
                    format: "N0"
                },
                {
                    fieldName: "NOAM",
                    headerText: "Nợ âm",
                    allowFiltering: true,
                    allowSorting: true,
                    cssClass: "text-right",
                    type: "number",
                    format: "N0"
                },
                {
                    fieldName: "MA_NVTC",
                    headerText: "Mã NVTC",
                    allowFiltering: true,
                    allowSorting: true
                }
            ],
            listCongNo: [],
            lanTao: "",
            listNVQL: [],
            listDonVi: [],
            listHTTT: [],
            listPLKH: [],
            listLoaiKH: [],
            listNVTC: [],
            listNhomCongNo: [],
            currentPageIndex: 0,
            currentPageSize: 10,
            keyReset: 1
        };
    },
    watch: {
        "thongTinTimKiem.selectedDonVi": {
            handler: async function(value) {
                console.log("value", value);
                await this.getNVQuanLy(value);
            }
        },
        "thongTinTimKiem.selectedNhomKH": {
            handler: async function(value) {
                await this.getPhanLoaiKH(value);
            }
        },
        "thongTinTimKiem.selectedNVQL": {
            handler: async function(value) {
                if (value) {
                    await this.getNVThuCuoc(value);
                    this.makeNVTCList();
                }
            }
        },
        "thongTinCapNhat.selectedNhomCongNo": {
            handler: async function(value) {
                if (value) {
                    const result = this.listNhomCongNoGetter.filter(
                        item => item.NHOMCNO_ID == value
                    );
                    console.log({ result });
                    this.thongTinCapNhat.truongNhom = result[0].TEN_TRUONGNHOM
                        ? result[0].TEN_TRUONGNHOM.trim()
                        : "";
                }
            }
        },
        isGan: {
            handler: async function(value) {
                //dùng để reset paging khi chọn tab khác
                this.keyReset = this.keyReset + 1;
                await this.getCongNo(0, 10, true);
            }
        },
        kyHoaDon: {
            handler: async function(value) {
                console.log(value);
                var result = this.listKyHoaDonGetter.filter(item => {
                    return item.ID == value;
                });
                console.log(result);
                if (result.length > 0) this.lanTao = result[0].LANTAO_ID;
            }
        },
        listDonViGetter: async function(newValue) {
            if (newValue.length > 0) {
                this.makeDonViList();
                // this.getNVQuanLy(newValue[0].DONVI_ID);
            }
        },
        listHTTTGetter: function(newValue) {
            // this.thongTinTimKiem.selectedHTTT =
            //     newValue.length > 0 && newValue[0].HTTT_ID;
            this.makeHTTTList();
        },
        listLoaiKHGetter: function(newValue) {
            // this.thongTinTimKiem.selectedLoaiKhachHang =
            //    newValue.length > 0 && newValue[0].LOAIKH_ID;
            this.makeLoaiKHList();
        },
        listPLKHGetter: function(newValue) {
            console.log({ newValue });
            this.thongTinTimKiem.selectedPhanLoaiKH =
                newValue.length > 0 && newValue[0].PHANLOAIKH_ID;
            this.makePhanLoaiKHList();
        },
        listNVQuanLyGetter: function(newValue) {
            this.thongTinTimKiem.selectedNVQL =
                newValue.length > 0 && newValue[0].NHANVIEN_ID;
            if (newValue.length > 0) this.makeNVQLList(newValue);
        },
        listNVThuCuocGetter: function(newValue) {
            this.thongTinTimKiem.selectedNVThuCuoc =
                newValue.length > 0 && newValue[0].TUYENTHU_ID;
            this.makeNVTCList();
        },
        listNhomCongNoGetter: function(newValue) {
            this.thongTinCapNhat.selectedNhomCongNo =
                newValue.length > 0 && newValue[0].NHOMCNO_ID;
            this.makeNhomCongNoList();
        },
        listKyHoaDonGetter: function(newValue) {
            this.kyHoaDon = newValue.length > 0 && newValue[0].ID;
            this.lanTao = newValue.length > 0 && newValue[0].LANTAO_ID;
            console.log(this.lanTao);
        }
    },
    computed: {
        ...mapGetters("GanNhomCongNo", [
            "listDonViGetter",
            "listHTTTGetter",
            "listLoaiKHGetter",
            "listPLKHGetter",
            "listNVQuanLyGetter",
            "listNVThuCuocGetter",
            "listNhomCongNoGetter",
            "listKyHoaDonGetter"
            // "listCongNoGetter",
        ])
    },
    methods: {
        ...mapActions("GanNhomCongNo", [
            "getDonVi",
            "getHTTT",
            "getPhanLoaiKH",
            "getDanhSachKHLon",
            "getNVQuanLy",
            "getNVThuCuoc",
            "getDanhSachLoaiKH",
            "getNhomCongNo",
            "getKyHoaDon",
            "getListCongNo",
            "capNhatCongNo",
            "huyCongNo",
            "clearData"
        ]),
        gridChanged: async function(args) {
            console.log({ args });
            this.currentPageIndex = args.pageIndex;
            this.currentPageSize = args.pageSize;
            this.getCongNo(this.currentPageIndex, this.currentPageSize);
        },
        handleKyCuoc(e) {
            this.pKyCuoc = moment(e.p_kyhoadon).format("yyyyMM") + e.p_chuky_no;
        },

        async fetchData() {
            await this.getList();
        },

        async getList() {
            this.loading(true);
            let data = {
                donViId: this.$auth.getDonViID(),
                nhanVienId: this.$auth.getNhanVienID()
            };
            try {
                await Promise.all([
                    this.getDonVi(data),
                    this.getHTTT(),
                    this.getPhanLoaiKH(this.thongTinTimKiem.selectedNhomKH),
                    this.getDanhSachLoaiKH(),
                    // this.getNVThuCuoc(this.thongTinTimKiem.selectedNVQL),
                    this.getNhomCongNo(),
                    this.getKyHoaDon()
                ]);
            } catch (e) {
                console.log(e);
            } finally {
                this.loading(false);
            }
        },

        onCongNoChange(dataItem) {
            console.log(dataItem);
            this.listIdCongNo = dataItem;
        },

        onCongNoRowChange(dataItem) {
            this.selectedItemCongNo = dataItem;
            this.thongTinCapNhat.selectedNhomCongNo = dataItem.NHOMCNO_ID;
            if (
                dataItem.NHOMCNO_ID == 0 ||
                dataItem.NHOMCNO_ID == "" ||
                !dataItem.NHOMCNO_ID
            ) {
                this.thongTinCapNhat.truongNhom = "";
            }
            this.thongTinCapNhat.tenCongTy = dataItem.TEN_CTY;
            this.thongTinCapNhat.diaChi = dataItem.DIACHI_CTY;
            this.thongTinCapNhat.phong = dataItem.TEN_DV;
            this.thongTinCapNhat.nguoiNhan = dataItem.NGUOINHAN;
            this.thongTinCapNhat.dienThoai = dataItem.SO_DT;
            this.thongTinCapNhat.fax = dataItem.FAX_NN;
            this.thongTinCapNhat.email = dataItem.EMAIL_NN;
            this.thongTinCapNhat.chucDanh = dataItem.CHUCDANH;
            this.thongTinCapNhat.nguoiDangKy = dataItem.NGUOIKY;
            this.thongTinCapNhat.diaChiNN = dataItem.DIACHI_NN;

            if (dataItem.THOIHAN_IN && dataItem.THOIHAN_IN !== "")
                this.thongTinCapNhat.thoiHanIn = dataItem.THOIHAN_IN;
            if (dataItem.THOIHAN_GUI && dataItem.THOIHAN_GUI !== "")
                this.thongTinCapNhat.thoiHanGui = dataItem.THOIHAN_GUI;
            if (dataItem.THOIHAN_LH && dataItem.THOIHAN_LH !== "")
                this.thongTinCapNhat.thoiHanLH = dataItem.THOIHAN_LH;
            if (dataItem.THOIHAN_PH && dataItem.THOIHAN_PH !== "")
                this.thongTinCapNhat.thoiHanPH = dataItem.THOIHAN_PH;
            if (dataItem.THOIHAN_HT && dataItem.THOIHAN_HT !== "")
                this.thongTinCapNhat.thoiHanHT = dataItem.THOIHAN_HT;
            console.log({ dataItem });
        },

        checkNull(data) {
            if (data == -1) {
                return null;
            } else {
                return data;
            }
        },

        async getCongNoCount(isShow) {
            // this.loading(true);

            console.log(this.thongTinTimKiem.selectedPhanLoaiKH);
            const data = {
                donViId: this.thongTinTimKiem.checkDonVi
                    ? this.checkNull(this.thongTinTimKiem.selectedDonVi)
                    : null,
                nhanVienQLId: this.thongTinTimKiem.checkNVQL
                    ? this.checkNull(this.thongTinTimKiem.selectedNVQL)
                    : null,
                lanTaoId: this.lanTao,
                nhomKH: this.thongTinTimKiem.checkNhomKH
                    ? this.checkNull(this.thongTinTimKiem.selectedNhomKH)
                    : null,
                phanLoaiKHId: this.thongTinTimKiem.checkPhanLoaiKH
                    ? this.checkNull(this.thongTinTimKiem.selectedPhanLoaiKH)
                    : null,
                loaiKH: this.thongTinTimKiem.checkLoaiKhachHang
                    ? this.checkNull(this.thongTinTimKiem.selectedLoaiKhachHang)
                    : null,
                htttId: this.thongTinTimKiem.checkHTTT
                    ? this.checkNull(this.thongTinTimKiem.selectedHTTT)
                    : null,
                tuyenThuId: this.thongTinTimKiem.checkNVTC
                    ? this.checkNull(this.thongTinTimKiem.selectedNVThuCuoc)
                    : null,
                chiTiet: 0,
                daGan: this.isGan,
                pageSize: 1,
                pageNum: 0,
                totalRow: 1
            };
            try {
                const response = await this.getListCongNo(data);
                if (response.data.error_code === "BSS-00000000") {
                }
                console.log(response);
                this.totalItems = response.data.data.totalElement
                    ? response.data.data.totalElement
                    : 0;
                if (this.totalItems == 0 || !this.totalItems) {
                    if (isShow) {
                        this.$root.toastError(
                            "Không có dữ liệu, vui lòng thử lại!"
                        );
                    }
                }
                console.log({ count: response.data.data.totalElement });
            } catch (e) {
                this.$root.toastError("Đã có lỗi xảy ra");
            } finally {
                // this.loading(false);
            }
        },

        getCongNo: async function(page, size, isShow) {
            this.loading(true);
            console.log(this.thongTinTimKiem.selectedPhanLoaiKH);
            const data = {
                donViId: this.thongTinTimKiem.checkDonVi
                    ? this.checkNull(this.thongTinTimKiem.selectedDonVi)
                    : null,
                nhanVienQLId: this.thongTinTimKiem.checkNVQL
                    ? this.checkNull(this.thongTinTimKiem.selectedNVQL)
                    : null,
                lanTaoId: this.lanTao,
                nhomKH: this.thongTinTimKiem.checkNhomKH
                    ? this.checkNull(this.thongTinTimKiem.selectedNhomKH)
                    : null,
                phanLoaiKHId: this.thongTinTimKiem.checkPhanLoaiKH
                    ? this.checkNull(this.thongTinTimKiem.selectedPhanLoaiKH)
                    : null,
                loaiKH: this.thongTinTimKiem.checkLoaiKhachHang
                    ? this.checkNull(this.thongTinTimKiem.selectedLoaiKhachHang)
                    : null,
                htttId: this.thongTinTimKiem.checkHTTT
                    ? this.checkNull(this.thongTinTimKiem.selectedHTTT)
                    : null,
                tuyenThuId: this.thongTinTimKiem.checkNVTC
                    ? this.checkNull(this.thongTinTimKiem.selectedNVThuCuoc)
                    : null,
                chiTiet: 0,
                daGan: this.isGan,
                pageSize: size,
                pageNum: page,
                totalRow: 1
            };
            console.log({ data });
            try {
                const response = await this.getListCongNo(data);
                if (
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data.data.length === 0
                ) {
                    if (isShow) {
                        this.$root.toastError(
                            "Không có dữ liệu, vui lòng thử lại!"
                        );
                    }
                    this.totalItems = 0;
                } else {
                    this.totalItems = response.data.data.totalElement;
                }
                console.log(response.data.data);
                this.listCongNo = response.data.data.data;
                this.listIdCongNo = [];
                console.log(response);
            } catch (e) {
                this.$root.toastError("Đã có lỗi xảy ra");
                this.listCongNo = [];
            } finally {
                this.loading(false);
            }
        },

        handleCapNhat() {
            console.log(this.listIdCongNo);
            if (this.listCongNo.length === 0) {
                this.$root.toastError("Hãy lấy danh sách trước khi cập nhật!");
                return;
            }
            if (
                this.thongTinCapNhat.selectedNhomCongNo === "" ||
                this.thongTinCapNhat.selectedNhomCongNo == 0 || this.thongTinCapNhat.selectedNhomCongNo == null
            ) {
                this.$root.toastError("Nhóm công nợ bắt buộc nhập khi cập nhật!");
                return;
            }
            if (this.listIdCongNo.length === 0) {
                this.$root.toastError(
                    "Bạn chưa chọn dữ liệu trên lưới để thực hiện!"
                );
                return;
            }
            this.$confirm(`Bạn thật sự muốn cập nhật không?`, "Xác nhận", {
                confirmButtonText: "Có",
                cancelButtonText: "Không"
            })
                .then(() => {
                    this.capNhat();
                })
                .catch(() => {});
        },

        async capNhat() {
            const data = {
                ds: this.listIdCongNo,
                nhomCNoId: this.thongTinCapNhat.selectedNhomCongNo,
                tenCTy: this.thongTinCapNhat.tenCongTy,
                diaChiCTy: this.thongTinCapNhat.diaChi,
                tenDV: this.thongTinCapNhat.phong,
                nguoiNhan: this.thongTinCapNhat.nguoiNhan,
                sdt: this.thongTinCapNhat.dienThoai,
                fax: this.thongTinCapNhat.fax,
                email: this.thongTinCapNhat.email,
                chucDanh: this.thongTinCapNhat.chucDanh,
                nguoiKy: this.thongTinCapNhat.nguoiDangKy,
                thoiHanIn: moment(
                    this.thongTinCapNhat.thoiHanIn,
                    "DD/MM/YYYY"
                ).format("DD/MM/YYYY"),
                thoiHanGui: moment(
                    this.thongTinCapNhat.thoiHanGui,
                    "DD/MM/YYYY"
                ).format("DD/MM/YYYY"),
                thoiHanLH: moment(
                    this.thongTinCapNhat.thoiHanLH,
                    "DD/MM/YYYY"
                ).format("DD/MM/YYYY"),
                thoiHanPH: moment(
                    this.thongTinCapNhat.thoiHanPH,
                    "DD/MM/YYYY"
                ).format("DD/MM/YYYY"),
                thoiHanHT: moment(
                    this.thongTinCapNhat.thoiHanHT,
                    "DD/MM/YYYY"
                ).format("DD/MM/YYYY"),
                diaChiNguoiNhan: this.thongTinCapNhat.diaChiNN
            };
            console.log({ data });
            try {
                this.loading(true);
                const response = await this.capNhatCongNo(data);
                if (response.data.error_code === "BSS-00000000") {
                    this.keyReset = this.keyReset + 1;
                    await this.getCongNo(0, 10, false);
                    this.$root.toastSuccess("Cập nhật dữ liệu thành công!");
                } else {
                    this.$root.toastError(response.data.message_detail);
                }
            } catch (e) {
                console.log(e);
                this.$root.toastError(e.data.message_detail);
            } finally {
                this.loading(false);
            }
        },

        handleXoaCongNo() {
            if (this.listCongNo.length === 0) {
                this.$root.toastError("Hãy lấy danh sách trước khi hủy gán!");
                return;
            }
            if (this.listIdCongNo.length === 0) {
                this.$root.toastError(
                    "Bạn chưa chọn dữ liệu trên lưới để thực hiện!"
                );
                return;
            }
            this.$confirm(
                `Bạn thật sự muốn hủy gán nhóm công nợ không?`,
                "Xác nhận",
                {
                    confirmButtonText: "Có",
                    cancelButtonText: "Không"
                }
            )
                .then(() => {
                    this.huyListCongNo();
                })
                .catch(() => {});
        },

        makeDonViList() {
            console.log({ listdv: this.listDonViGetter });

            if (
                this.listDonViGetter !== undefined &&
                this.listDonViGetter.length > 0
            ) {
                this.listDonVi.push({ id: -1, text: "Tất cả" });
                this.listDonViGetter.forEach(item => {
                    this.listDonVi.push({
                        id: item.DONVI_ID,
                        text: item.TEN_DV
                    });
                });
            }
        },

        makeNVQLList(value) {
            this.listNVQL = [];
            value.forEach(item => {
                this.listNVQL.push({
                    id: item.NHANVIEN_ID,
                    text: item.GHEPTEN
                });
            });
            // this.listNVQL.sort((a, b) => a.text.localeCompare(b.text));
            this.selectedNVQL = this.listNVQL[0].id;
        },

        makeHTTTList() {
            if (
                this.listHTTTGetter !== undefined &&
                this.listHTTTGetter.length > 0
            ) {
                this.listHTTT.push({ id: -1, text: "Tất cả" });
                this.listHTTTGetter.forEach(item => {
                    this.listHTTT.push({
                        id: item.HTTT_ID,
                        text: item.HTTT
                    });
                });
            }
        },

        makePhanLoaiKHList() {
            this.listPLKH = [];
            if (
                this.listPLKHGetter !== undefined &&
                this.listPLKHGetter.length > 0
            ) {
                this.listPLKHGetter.forEach(item => {
                    this.listPLKH.push({
                        id: item.PHANLOAIKH_ID,
                        text: item.MA_PLKH
                    });
                });
                this.listPLKH.shift();
                this.listPLKH.unshift({ id: -1, text: "Tất cả" });
                this.thongTinTimKiem.selectedPhanLoaiKH = this.listPLKH[0].id;
            }
        },

        makeLoaiKHList() {
            this.listLoaiKH = [];
            if (
                this.listLoaiKHGetter !== undefined &&
                this.listLoaiKHGetter.length > 0
            ) {
                this.listLoaiKH.push({ id: -1, text: "Tất cả" });
                this.listLoaiKHGetter.forEach(item => {
                    this.listLoaiKH.push({
                        id: item.LOAIKH_ID,
                        text: item.TEN_LOAIKH
                    });
                });
                this.thongTinTimKiem.selectedLoaiKhachHang = this.listLoaiKH[0].id;
            }
        },

        makeNVTCList() {
            this.listNVTC = [];
            if (
                this.listNVThuCuocGetter !== undefined &&
                this.listNVThuCuocGetter.length > 0
            ) {
                this.listNVThuCuocGetter.forEach(item => {
                    this.listNVTC.push({
                        id: item.TUYENTHU_ID,
                        text: item.GHEPTUYEN
                    });
                });
                // this.selectedNVTC = this.listNVTC[0].id;
            }
        },

        makeNhomCongNoList() {
            this.listNhomCongNo = [];
            if (
                this.listNhomCongNoGetter !== undefined &&
                this.listNhomCongNoGetter.length > 0
            ) {
                this.listNhomCongNoGetter.forEach(item => {
                    this.listNhomCongNo.push({
                        id: item.NHOMCNO_ID,
                        text: item.TEN_NHOM
                    });
                });
                this.thongTinCapNhat.selectedNhomCongNo = this.listNhomCongNo[0].id;
            }
        },

        async huyListCongNo() {
            console.log({ thoihanin: this.thongTinCapNhat.thoiHanIn });
            const data = {
                ds: this.listIdCongNo,
                // nhomCNoId: this.thongTinCapNhat.selectedNhomCongNo,
                tenCTy: this.thongTinCapNhat.tenCongTy,
                diaChiCTy: this.thongTinCapNhat.diaChi,
                tenDV: this.thongTinCapNhat.phong,
                nguoiNhan: this.thongTinCapNhat.nguoiNhan,
                sdt: this.thongTinCapNhat.dienThoai,
                fax: this.thongTinCapNhat.fax,
                email: this.thongTinCapNhat.email,
                chucDanh: this.thongTinCapNhat.chucDanh,
                nguoiKy: this.thongTinCapNhat.nguoiDangKy,
                thoiHanIn: moment(
                    this.thongTinCapNhat.thoiHanIn,
                    "DD/MM/YYYY"
                ).format("DD/MM/YYYY"),
                thoiHanGui: moment(
                    this.thongTinCapNhat.thoiHanGui,
                    "DD/MM/YYYY"
                ).format("DD/MM/YYYY"),
                thoiHanLH: moment(
                    this.thongTinCapNhat.thoiHanLH,
                    "DD/MM/YYYY"
                ).format("DD/MM/YYYY"),
                thoiHanPH: moment(
                    this.thongTinCapNhat.thoiHanPH,
                    "DD/MM/YYYY"
                ).format("DD/MM/YYYY"),
                thoiHanHT: moment(
                    this.thongTinCapNhat.thoiHanHT,
                    "DD/MM/YYYY"
                ).format("DD/MM/YYYY"),
                diaChiNguoiNhan: this.thongTinCapNhat.diaChiNN
            };
            console.log({ dataxoa: data });

            try {
                this.loading(true);
                const response = await this.huyCongNo(data);
                if (response.data.error_code === "BSS-00000000") {
                    this.keyReset = this.keyReset + 1;
                    await this.getCongNo(0, 10, false);
                    this.$root.toastSuccess("Hủy gán nhóm công nợ thành công!");
                } else {
                    this.$root.toastError(response.data.message_detail);
                }
                console.log(response.data.message_detail);
            } catch (e) {
                console.log({ error: e });
            } finally {
                this.loading(false);
            }
        }
    }
};
</script>
