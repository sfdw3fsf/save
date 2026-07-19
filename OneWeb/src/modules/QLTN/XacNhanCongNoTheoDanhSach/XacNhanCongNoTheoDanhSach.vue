<template src="./XacNhanCongNoTheoDanhSach.html"></template>
<style src="./XacNhanCongNoTheoDanhSach.scss"></style>
<script>
import breadcrumb from "@/components/breadcrumb";
import moment from "moment";
import { mapActions, mapState, mapGetters } from "vuex";
import KyCuoc from "../components/KyCuoc.vue";
import PopupDocFile from "./popup-docfile.vue";

export default {
    name: "XacNhanCongNoTheoDanhSach",
    components: {
        breadcrumb,
        appKyCuoc: KyCuoc,
        PopupDocFile
    },

    async mounted() {
        this.$refs.maTT.focus();
    },
    data() {
        return {
            header: {
                title: "Xác nhận công nợ theo danh sách",
                list: [
                    {
                        name: "Kiểm kê công nợ ",
                        link: { name: "Ui.buttons" }
                    },
                    {
                        name: "Xác nhận công nợ theo mã thanh toán",
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
            lanTao: "",
            isGan: "0",
            listIdCongNo: [],
            listCongNo: [],
            listDichVu: [],
            listNVQL: [],
            listDonVi: [],
            listHTTT: [],
            listPLKH: [],
            listLoaiKH: [],
            listNVTC: [],
            //thông tin tìm kiếm section
            thongTinTimKiem: {
                selectedDonVi: -1,
                checkDonVi: false,

                selectedHTTT: -1,
                checkHTTT: false,

                selectedNhomKH: -1,
                checkNhomKH: false,

                selectedNVQL: null,
                checkNVQL: false,

                selectedNVThuCuoc: null,
                checkNVTC: false,

                selectedPhanLoaiKH: -1,
                checkPhanLoaiKH: false,

                selectedLoaiKhachHang: -1,
                checkLoaiKhachHang: false,

                selectedNVTC: null,
                checkNVTC: false,

                maTT: "",
                listNhomKH: [
                    { id: -1, name: "Tất cả" },
                    { id: 0, name: "Cá nhân" },
                    { id: 1, name: "Doanh nghiệp" }
                ]
            },

            //thông tin cập nhật section
            thongTinCapNhat: {
                //left
                ngayGui: new Date(),
                ngayXacThucKH: new Date(),
                ngayThuHoiThu: new Date(),
                checkNgayThuHoiThu: false,

                //right
                soDu: "0",
                checkSoDu: false,

                selectedKetQua: "-1",

                selectedLyDo: null,
                checkLyDo: false,

                selectedNguyenNhan: null,
                checkNguyenNhan: false,
                listKetQua: [
                    { id: -1, name: "Chọn kết quả" },
                    { id: 1, name: "Xác nhận được - số dư đúng" },
                    { id: 2, name: "Xác nhận được - số dư sai" },
                    { id: 0, name: "Không xác nhận được" }
                ]
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
                    fieldName: "DIACHI_TT",
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
                    fieldName: "FAX_NN",
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
                    type: "number",
                    format: "N0",
                    cellCssClass: "text-right"
                },
                {
                    fieldName: "NOAM",
                    headerText: "Nợ âm",
                    allowFiltering: true,
                    allowSorting: true,
                    cellCssClass: "text-right",
                    type: "number",
                    format: "N0"
                },
                {
                    fieldName: "MA_NVTC",
                    headerText: "Mã NVTC",
                    allowFiltering: true,
                    allowSorting: true
                }
            ]
        };
    },
    watch: {
        "thongTinTimKiem.selectedDonVi": {
            handler: async function(value) {
                await this.getNVQuanLy(value);
                // this.makeNVQLList();
                // this.getTuyenThu({
                //     pNhanVienId: value
                // });
            }
        },
        "thongTinTimKiem.selectedNhomKH": {
            handler: async function(value) {
                await this.getPhanLoaiKH(value);
                this.getDanhSachLoaiKH(value);
            }
        },
        "thongTinTimKiem.selectedNVQL": {
            handler: async function(value) {
                await this.getNVThuCuoc(value);
                this.makeNVTCList();
            }
        },
        kyHoaDon: {
            handler: async function(value) {
                console.log(value);
                var result = this.listKyHoaDonGetter.filter(item => {
                    return item.ID == value;
                });
                console.log(result);
                this.lanTao = result[0].LANTAO_ID;
            }
        },
        "thongTinCapNhat.selectedKetQua": {
            handler: async function(value) {
                if (value == 1) {
                    this.thongTinCapNhat.checkNgayThuHoiThu = true;
                    this.thongTinCapNhat.checkLyDo = false;
                    this.thongTinCapNhat.checkNguyenNhan = false;
                    this.thongTinCapNhat.checkSoDu = false;
                } else if (value == 2) {
                    this.thongTinCapNhat.checkNgayThuHoiThu = true;
                    this.thongTinCapNhat.checkNguyenNhan = true;
                    this.thongTinCapNhat.checkSoDu = true;
                    this.thongTinCapNhat.checkLyDo = false;
                } else if (value == 0) {
                    this.thongTinCapNhat.checkNgayThuHoiThu = true;
                    this.thongTinCapNhat.checkLyDo = true;
                    this.thongTinCapNhat.checkNguyenNhan = false;
                    this.thongTinCapNhat.checkSoDu = false;
                } else {
                    this.thongTinCapNhat.checkNgayThuHoiThu = false;
                    this.thongTinCapNhat.checkLyDo = false;
                    this.thongTinCapNhat.checkNguyenNhan = false;
                    this.thongTinCapNhat.checkSoDu = false;
                }
            }
        },
        listDonViGetter: async function(newValue) {
            console.log({ newValue });
            if (newValue.length > 0) {
                // this.thongTinTimKiem.selectedDonVi = newValue[0].DONVI_ID;
                this.makeDonViList();
                this.getNVQuanLy(newValue[0].DONVI_ID);
                this.getTuyenThu({
                    pNhanVienId: newValue[0].DONVI_ID
                });
            }
        },
        listHTTTGetter: function(newValue) {
            // this.thongTinTimKiem.selectedHTTT =
            //     newValue.length > 0 && newValue[0].HTTT_ID;
            this.makeHTTTList();
        },
        listLoaiKHGetter: function(newValue) {
            // this.thongTinTimKiem.selectedLoaiKhachHang =
            //     newValue.length > 0 && newValue[0].LOAIKH_ID;
            this.makeLoaiKHList();
        },
        listPLKHGetter: function(newValue) {
            // this.thongTinTimKiem.selectedPhanLoaiKH =
            //     newValue.length > 0 && newValue[0].PHANLOAIKH_ID;
            this.makePhanLoaiKHList();
        },
        listNVQuanLyGetter: function(newValue) {
            this.thongTinTimKiem.selectedNVQL =
                newValue.length > 0 && newValue[0].NHANVIEN_ID;
            this.makeNVQLList(newValue);
        },
        listNVThuCuocGetter: function(newValue) {
            this.thongTinTimKiem.selectedNVTC =
                newValue.length > 0 && newValue[0].TUYENTHU_ID;
            this.makeNVTCList();
        },
        listKyHoaDonGetter: function(newValue) {
            this.kyHoaDon = newValue.length > 0 && newValue[0].ID;
            this.lanTao = newValue[0].LANTAO_ID;
            console.log(this.lanTao);
        },
        listTuyenThuGetter: function(newValue) {
            this.thongTinTimKiem.selectedTuyenThu =
                newValue.length > 0 && newValue[0].ID;
        },
        listLyDoGetter: function(newValue) {
            this.thongTinCapNhat.selectedLyDo =
                newValue.length > 0 && newValue[0].LYDOKXN_ID;
        },
        listNguyenNhanGetter: function(newValue) {
            this.thongTinCapNhat.selectedNguyenNhan =
                newValue.length > 0 && newValue[0].NGUYENNHANXN_ID;
        },
        listTuyenThuGetter: function(newValue) {
            this.thongTinTimKiem.selectedTuyenThu =
                newValue.length > 0 && newValue[0].TUYENTHU_ID;
        }
    },
    computed: {
        ...mapGetters("XacNhanCongNoTheoDanhSach", [
            "listDonViGetter",
            "listHTTTGetter",
            "listLoaiKHGetter",
            "listPLKHGetter",
            "listNVQuanLyGetter",
            "listNVThuCuocGetter",
            "listNhomCongNoGetter",
            "listKyHoaDonGetter",
            "listCongNoGetter",
            "listTuyenThuGetter",
            "listLyDoGetter",
            "listNguyenNhanGetter"
        ])
    },
    methods: {
        ...mapActions("XacNhanCongNoTheoDanhSach", [
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
            "getTuyenThu",
            "getLyDo",
            "getNguyenNhan",
            "clearData"
        ]),

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
                    this.getDanhSachLoaiKH(this.thongTinTimKiem.selectedNhomKH),
                    this.getNVThuCuoc(this.thongTinTimKiem.selectedNVQL),
                    this.getNhomCongNo(),
                    this.getKyHoaDon(),
                    this.getLyDo(),
                    this.getNguyenNhan()
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

        onCongNoRowChange(dataItem) {},

        checkNull(data) {
            if (data == -1) {
                return null;
            } else {
                return data;
            }
        },

        async getCongNo() {
            this.loading(true);
            const data = {
                donViId: this.thongTinTimKiem.checkDonVi
                    ? this.checkNull(this.thongTinTimKiem.selectedDonVi)
                    : null,
                nhanVienQLId: this.thongTinTimKiem.checkNVQL
                    ? this.checkNull(this.thongTinTimKiem.selectedNVQL)
                    : null,
                lanTaoId: this.lanTao,
                // lanTaoId: 79,
                // tuyenThuId: this.thongTinTimKiem.checkTuyenThu
                //     ? this.thongTinTimKiem.selectedTuyenThu
                //     : null,
                nhomKH: this.thongTinTimKiem.checkNhomKH
                    ? this.checkNull(this.thongTinTimKiem.selectedNhomKH)
                    : null,
                phanLoaiKHId: this.thongTinTimKiem.checkPhanLoaiKH
                    ? this.checkNull(this.thongTinTimKiem.selectedPhanLoaiKH)
                    : null,
                loaiKH: this.thongTinTimKiem.checkLoaiKhachHang
                    ? this.checkNull(this.thongTinTimKiem.selectedLoaiKhachHang)
                    : null,
                maTT: this.thongTinTimKiem.maTT,
                htttId: this.thongTinTimKiem.checkHTTT
                    ? this.checkNull(this.thongTinTimKiem.selectedHTTT)
                    : null,
                kieu: 2
            };
            console.log({ data });
            try {
                const response = await this.getListCongNo(data);
                if (
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data.length === 0
                ) {
                    this.$root.toastError(
                        "Không tìm thấy danh sách công nợ thỏa mãn điều kiện!"
                    );
                } else if (
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data.length > 0
                ) {
                    // response.data.data.forEach(item => {
                    //     item.TONGNO = Number(item.TONGNO).toLocaleString(
                    //         "en-GB"
                    //     );
                    //     item.NOAM = Number(item.NOAM).toLocaleString("en-GB");
                    // });
                    // console.log(response.data.data);
                    // this.listCongNo = response.data.data;
                    // this.$refs.grid.setCurrentSelectedRow(0);
                } else {
                    this.$root.toastError(response.data.message_detail);
                }
                console.log({ response });
            } catch (e) {
                console.log(e);
            } finally {
                this.loading(false);
            }
        },

        handleCapNhat() {
            if (this.listCongNoGetter.length === 0) {
                this.$root.toastError("Hãy lấy danh sách trước khi cập nhật!");
                return;
            }

            if (this.listIdCongNo.length === 0) {
                this.$root.toastError(
                    "Bạn chưa chọn dữ liệu trên lưới để thực hiện!"
                );
                return;
            }
            // if (this.listIdCongNo.length > 1) {
            // 	this.$root.toastWarning("Chỉ được chọn một bản ghi để cập nhật!");
            // 	return;
            // }
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
                giaoPhieu: this.listIdCongNo,
                ketQua: this.thongTinCapNhat.selectedKetQua,
                ngayBN: moment(this.thongTinCapNhat.ngayXacThucKH).format(
                    "DD/MM/yyyy"
                ), // ngày xác thực
                lyDoKXN: this.thongTinCapNhat.checkLyDo
                    ? this.thongTinCapNhat.selectedLyDo
                    : null,
                soDu: this.thongTinCapNhat.checkSoDu
                    ? this.thongTinCapNhat.soDu
                    : null,
                nguyenNhanXN: this.thongTinCapNhat.checkNguyenNhan
                    ? this.thongTinCapNhat.selectedNguyenNhan
                    : null,
                ngayHT: this.thongTinCapNhat.checkNgayThuHoiThu
                    ? moment(this.thongTinCapNhat.ngayThuHoiThu).format(
                          "DD/MM/yyyy"
                      )
                    : null,
                ngayGui: moment(this.thongTinCapNhat.ngayGui).format(
                    "DD/MM/yyyy"
                )
            };
            try {
                this.loading(true);
                const response = await this.capNhatCongNo(data);
                if (response.data.error_code === "BSS-00000000") {
                    await this.getCongNo();
                    this.$root.toastSuccess("Cập nhật dữ liệu thành công!");
                } else {
                    this.$root.toastError("Đã xảy ra lỗi khi cập nhật!");
                }
            } catch (e) {
                console.log(e);
            } finally {
                this.loading(false);
            }
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
                    text: item.TEN_NV
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
                this.selectedPhanLoaiKH = this.listPLKH[0].id;
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
                this.selectedLoaiKhachHang = this.listLoaiKH[0].id;
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

        docFile() {
            this.$bvModal.show("modal-docfile");
        }
    },
    async created() {
        this.clearData();
        await this.fetchData();
    }
};
</script>
